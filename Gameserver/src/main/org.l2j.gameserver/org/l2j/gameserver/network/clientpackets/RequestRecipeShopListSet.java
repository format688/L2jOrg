package org.l2j.gameserver.network.clientpackets;

import org.l2j.gameserver.Config;
import org.l2j.gameserver.data.xml.impl.RecipeData;
import org.l2j.gameserver.enums.PrivateStoreType;
import org.l2j.gameserver.model.L2ManufactureItem;
import org.l2j.gameserver.model.L2RecipeList;
import org.l2j.gameserver.model.actor.instance.L2PcInstance;
import org.l2j.gameserver.model.zone.ZoneId;
import org.l2j.gameserver.network.InvalidDataPacketException;
import org.l2j.gameserver.network.SystemMessageId;
import org.l2j.gameserver.network.serverpackets.ActionFailed;
import org.l2j.gameserver.network.serverpackets.RecipeShopMsg;
import org.l2j.gameserver.taskmanager.AttackStanceTaskManager;
import org.l2j.gameserver.util.Broadcast;
import org.l2j.gameserver.util.Util;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import static org.l2j.gameserver.model.itemcontainer.Inventory.MAX_ADENA;

/**
 * RequestRecipeShopListSet client packet class.
 */
public final class RequestRecipeShopListSet extends IClientIncomingPacket {
    private static final int BATCH_LENGTH = 12;

    private L2ManufactureItem[] _items = null;

    @Override
    public void readImpl(ByteBuffer packet) throws InvalidDataPacketException {
        final int count = packet.getInt();
        if ((count <= 0) || (count > Config.MAX_ITEM_IN_PACKET) || ((count * BATCH_LENGTH) != packet.remaining())) {
            throw new InvalidDataPacketException();
        }

        _items = new L2ManufactureItem[count];
        for (int i = 0; i < count; i++) {
            final int id = packet.getInt();
            final long cost = packet.getLong();
            if (cost < 0) {
                _items = null;
                throw new InvalidDataPacketException();
            }
            _items[i] = new L2ManufactureItem(id, cost);
        }
    }

    @Override
    public void runImpl() {
        final L2PcInstance player = client.getActiveChar();
        if (player == null) {
            return;
        }

        if (_items == null) {
            player.setPrivateStoreType(PrivateStoreType.NONE);
            player.broadcastUserInfo();
            return;
        }

        if (AttackStanceTaskManager.getInstance().hasAttackStanceTask(player) || player.isInDuel()) {
            client.sendPacket(SystemMessageId.WHILE_YOU_ARE_ENGAGED_IN_COMBAT_YOU_CANNOT_OPERATE_A_PRIVATE_STORE_OR_PRIVATE_WORKSHOP);
            client.sendPacket(ActionFailed.STATIC_PACKET);
            return;
        }

        if (player.isInsideZone(ZoneId.NO_STORE)) {
            client.sendPacket(SystemMessageId.YOU_CANNOT_OPEN_A_PRIVATE_WORKSHOP_HERE);
            client.sendPacket(ActionFailed.STATIC_PACKET);
            return;
        }

        final List<L2RecipeList> dwarfRecipes = Arrays.asList(player.getDwarvenRecipeBook());
        final List<L2RecipeList> commonRecipes = Arrays.asList(player.getCommonRecipeBook());

        player.getManufactureItems().clear();

        for (L2ManufactureItem i : _items) {
            final L2RecipeList list = RecipeData.getInstance().getRecipeList(i.getRecipeId());
            if (!dwarfRecipes.contains(list) && !commonRecipes.contains(list)) {
                Util.handleIllegalPlayerAction(player, "Warning!! Player " + player.getName() + " of account " + player.getAccountName() + " tried to set recipe which he dont have.", Config.DEFAULT_PUNISH);
                return;
            }

            if (i.getCost() > MAX_ADENA) {
                Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " tried to set price more than " + MAX_ADENA + " adena in Private Manufacture.", Config.DEFAULT_PUNISH);
                return;
            }

            player.getManufactureItems().put(i.getRecipeId(), i);
        }

        player.setStoreName(!player.hasManufactureShop() ? "" : player.getStoreName());
        player.setPrivateStoreType(PrivateStoreType.MANUFACTURE);
        player.sitDown();
        player.broadcastUserInfo();
        Broadcast.toSelfAndKnownPlayers(player, new RecipeShopMsg(player));
    }
}
