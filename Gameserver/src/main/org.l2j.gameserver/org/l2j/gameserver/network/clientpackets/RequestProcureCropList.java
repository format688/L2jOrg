package org.l2j.gameserver.network.clientpackets;

import org.l2j.gameserver.Config;
import org.l2j.gameserver.datatables.ItemTable;
import org.l2j.gameserver.instancemanager.CastleManorManager;
import org.l2j.gameserver.model.CropProcure;
import org.l2j.gameserver.model.actor.L2Npc;
import org.l2j.gameserver.model.actor.instance.L2MerchantInstance;
import org.l2j.gameserver.model.actor.instance.L2PcInstance;
import org.l2j.gameserver.model.holders.UniqueItemHolder;
import org.l2j.gameserver.model.items.L2Item;
import org.l2j.gameserver.model.items.instance.L2ItemInstance;
import org.l2j.gameserver.network.InvalidDataPacketException;
import org.l2j.gameserver.network.SystemMessageId;
import org.l2j.gameserver.network.serverpackets.ActionFailed;
import org.l2j.gameserver.network.serverpackets.SystemMessage;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author l3x
 */
public class RequestProcureCropList extends IClientIncomingPacket {
    private static final int BATCH_LENGTH = 20; // length of the one item

    private List<CropHolder> _items = null;

    @Override
    public void readImpl(ByteBuffer packet) throws InvalidDataPacketException {
        final int count = packet.getInt();
        if ((count <= 0) || (count > Config.MAX_ITEM_IN_PACKET) || ((count * BATCH_LENGTH) != packet.remaining())) {
            throw new InvalidDataPacketException();
        }

        _items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            final int objId = packet.getInt();
            final int itemId = packet.getInt();
            final int manorId = packet.getInt();
            final long cnt = packet.getLong();
            if ((objId < 1) || (itemId < 1) || (manorId < 0) || (cnt < 0)) {
                _items = null;
                throw new InvalidDataPacketException();
            }
            _items.add(new CropHolder(objId, itemId, cnt, manorId));
        }
    }

    @Override
    public void runImpl() {
        if (_items == null) {
            return;
        }

        final L2PcInstance player = client.getActiveChar();
        if (player == null) {
            return;
        }

        final CastleManorManager manor = CastleManorManager.getInstance();
        if (manor.isUnderMaintenance()) {
            client.sendPacket(ActionFailed.STATIC_PACKET);
            return;
        }

        final L2Npc manager = player.getLastFolkNPC();
        if (!(manager instanceof L2MerchantInstance) || !manager.canInteract(player)) {
            client.sendPacket(ActionFailed.STATIC_PACKET);
            return;
        }

        final int castleId = manager.getCastle().getResidenceId();
        if (manager.getParameters().getInt("manor_id", -1) != castleId) {
            client.sendPacket(ActionFailed.STATIC_PACKET);
            return;
        }

        int slots = 0;
        int weight = 0;
        for (CropHolder i : _items) {
            final L2ItemInstance item = player.getInventory().getItemByObjectId(i.getObjectId());
            if ((item == null) || (item.getCount() < i.getCount()) || (item.getId() != i.getId())) {
                client.sendPacket(ActionFailed.STATIC_PACKET);
                return;
            }

            final CropProcure cp = i.getCropProcure();
            if ((cp == null) || (cp.getAmount() < i.getCount())) {
                client.sendPacket(ActionFailed.STATIC_PACKET);
                return;
            }

            final L2Item template = ItemTable.getInstance().getTemplate(i.getRewardId());
            weight += (i.getCount() * template.getWeight());

            if (!template.isStackable()) {
                slots += i.getCount();
            } else if (player.getInventory().getItemByItemId(i.getRewardId()) == null) {
                slots++;
            }
        }

        if (!player.getInventory().validateWeight(weight)) {
            player.sendPacket(SystemMessageId.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
            return;
        } else if (!player.getInventory().validateCapacity(slots)) {
            player.sendPacket(SystemMessageId.YOUR_INVENTORY_IS_FULL);
            return;
        }

        // Used when Config.ALT_MANOR_SAVE_ALL_ACTIONS == true
        final int updateListSize = Config.ALT_MANOR_SAVE_ALL_ACTIONS ? _items.size() : 0;
        final List<CropProcure> updateList = new ArrayList<>(updateListSize);

        // Proceed the purchase
        for (CropHolder i : _items) {
            final long rewardPrice = ItemTable.getInstance().getTemplate(i.getRewardId()).getReferencePrice();
            if (rewardPrice == 0) {
                continue;
            }

            final long rewardItemCount = i.getPrice() / rewardPrice;
            if (rewardItemCount < 1) {
                final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.FAILED_IN_TRADING_S2_OF_S1_CROPS);
                sm.addItemName(i.getId());
                sm.addLong(i.getCount());
                player.sendPacket(sm);
                continue;
            }

            // Fee for selling to other manors
            final long fee = (castleId == i.getManorId()) ? 0 : ((long) (i.getPrice() * 0.05));
            if ((fee != 0) && (player.getAdena() < fee)) {
                SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.FAILED_IN_TRADING_S2_OF_S1_CROPS);
                sm.addItemName(i.getId());
                sm.addLong(i.getCount());
                player.sendPacket(sm);

                sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                player.sendPacket(sm);
                continue;
            }

            final CropProcure cp = i.getCropProcure();
            if (!cp.decreaseAmount(i.getCount()) || ((fee > 0) && !player.reduceAdena("Manor", fee, manager, true)) || !player.destroyItem("Manor", i.getObjectId(), i.getCount(), manager, true)) {
                continue;
            }
            player.addItem("Manor", i.getRewardId(), rewardItemCount, manager, true);

            if (Config.ALT_MANOR_SAVE_ALL_ACTIONS) {
                updateList.add(cp);
            }
        }

        if (Config.ALT_MANOR_SAVE_ALL_ACTIONS) {
            manor.updateCurrentProcure(castleId, updateList);
        }
    }

    private final class CropHolder extends UniqueItemHolder {
        private final int _manorId;
        private CropProcure _cp;
        private int _rewardId = 0;

        public CropHolder(int objectId, int id, long count, int manorId) {
            super(id, objectId, count);
            _manorId = manorId;
        }

        public final int getManorId() {
            return _manorId;
        }

        public final long getPrice() {
            return getCount() * _cp.getPrice();
        }

        public final CropProcure getCropProcure() {
            if (_cp == null) {
                _cp = CastleManorManager.getInstance().getCropProcure(_manorId, getId(), false);
            }
            return _cp;
        }

        public final int getRewardId() {
            if (_rewardId == 0) {
                _rewardId = CastleManorManager.getInstance().getSeedByCrop(_cp.getId()).getReward(_cp.getReward());
            }
            return _rewardId;
        }
    }
}
