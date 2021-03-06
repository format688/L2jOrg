package org.l2j.gameserver.network.clientpackets;

import org.l2j.gameserver.Config;
import org.l2j.gameserver.data.sql.impl.OfflineTradersTable;
import org.l2j.gameserver.enums.PrivateStoreType;
import org.l2j.gameserver.model.ItemRequest;
import org.l2j.gameserver.model.L2World;
import org.l2j.gameserver.model.TradeList;
import org.l2j.gameserver.model.actor.instance.L2PcInstance;
import org.l2j.gameserver.model.ceremonyofchaos.CeremonyOfChaosEvent;
import org.l2j.gameserver.network.InvalidDataPacketException;
import org.l2j.gameserver.network.SystemMessageId;
import org.l2j.gameserver.network.serverpackets.ActionFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

import static org.l2j.gameserver.model.actor.L2Npc.INTERACTION_DISTANCE;

public final class RequestPrivateStoreSell extends IClientIncomingPacket {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestPrivateStoreSell.class);
    private int _storePlayerId;
    private ItemRequest[] _items = null;

    @Override
    public void readImpl(ByteBuffer packet) throws InvalidDataPacketException {
        _storePlayerId = packet.getInt();
        int itemsCount = packet.getInt();
        if ((itemsCount <= 0) || (itemsCount > Config.MAX_ITEM_IN_PACKET)) {
            throw new InvalidDataPacketException();
        }
        _items = new ItemRequest[itemsCount];

        for (int i = 0; i < itemsCount; i++) {
            final int slot = packet.getInt();
            final int itemId = packet.getInt();
            packet.getShort(); // TODO analyse this
            packet.getShort(); // TODO analyse this
            final long count = packet.getLong();
            final long price = packet.getLong();
            packet.getInt(); // visual id
            packet.getInt(); // option 1
            packet.getInt(); // option 2
            int soulCrystals = packet.get();
            for (int s = 0; s < soulCrystals; s++) {
                packet.getInt(); // soul crystal option
            }
            int soulCrystals2 = packet.get();
            for (int s = 0; s < soulCrystals2; s++) {
                packet.getInt(); // sa effect
            }
            if (/* (slot < 1) || */ (itemId < 1) || (count < 1) || (price < 0)) {
                _items = null;
                throw new InvalidDataPacketException();
            }
            _items[i] = new ItemRequest(slot, itemId, count, price);
        }
    }

    @Override
    public void runImpl() {
        final L2PcInstance player = client.getActiveChar();
        if (player == null) {
            return;
        }

        if (_items == null) {
            client.sendPacket(ActionFailed.STATIC_PACKET);
            return;
        }

        // Cannot set private store in Ceremony of Chaos event.
        if (player.isOnEvent(CeremonyOfChaosEvent.class)) {
            client.sendPacket(SystemMessageId.YOU_CANNOT_OPEN_A_PRIVATE_STORE_OR_WORKSHOP_IN_THE_CEREMONY_OF_CHAOS);
            return;
        }

        if (player.isOnEvent()) // custom event message
        {
            player.sendMessage("You cannot open a private store while participating in an event.");
            return;
        }

        if (!client.getFloodProtectors().getTransaction().tryPerformAction("privatestoresell")) {
            player.sendMessage("You are selling items too fast.");
            return;
        }

        final L2PcInstance storePlayer = L2World.getInstance().getPlayer(_storePlayerId);
        if ((storePlayer == null) || !player.isInsideRadius3D(storePlayer, INTERACTION_DISTANCE)) {
            return;
        }

        if (player.getInstanceWorld() != storePlayer.getInstanceWorld()) {
            return;
        }

        if ((storePlayer.getPrivateStoreType() != PrivateStoreType.BUY) || player.isCursedWeaponEquipped()) {
            return;
        }

        final TradeList storeList = storePlayer.getBuyList();
        if (storeList == null) {
            return;
        }

        if (!player.getAccessLevel().allowTransaction()) {
            player.sendMessage("Transactions are disabled for your Access Level.");
            client.sendPacket(ActionFailed.STATIC_PACKET);
            return;
        }

        if (!storeList.privateStoreSell(player, _items)) {
            client.sendPacket(ActionFailed.STATIC_PACKET);
            LOGGER.warn("PrivateStore sell has failed due to invalid list or request. Player: " + player.getName() + ", Private store of: " + storePlayer.getName());
            return;
        }

        // Update offline trade record, if realtime saving is enabled
        if (Config.OFFLINE_TRADE_ENABLE && Config.STORE_OFFLINE_TRADE_IN_REALTIME && ((storePlayer.getClient() == null) || storePlayer.getClient().isDetached())) {
            OfflineTradersTable.onTransaction(storePlayer, storeList.getItemCount() == 0, false);
        }

        if (storeList.getItemCount() == 0) {
            storePlayer.setPrivateStoreType(PrivateStoreType.NONE);
            storePlayer.broadcastUserInfo();
        }
    }
}
