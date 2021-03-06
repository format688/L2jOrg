package org.l2j.gameserver.network.clientpackets.attributechange;

import org.l2j.gameserver.Config;
import org.l2j.gameserver.enums.AttributeType;
import org.l2j.gameserver.model.actor.instance.L2PcInstance;
import org.l2j.gameserver.model.itemcontainer.PcInventory;
import org.l2j.gameserver.model.items.enchant.attribute.AttributeHolder;
import org.l2j.gameserver.model.items.instance.L2ItemInstance;
import org.l2j.gameserver.network.SystemMessageId;
import org.l2j.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2j.gameserver.network.serverpackets.InventoryUpdate;
import org.l2j.gameserver.network.serverpackets.SystemMessage;
import org.l2j.gameserver.network.serverpackets.attributechange.ExChangeAttributeFail;
import org.l2j.gameserver.network.serverpackets.attributechange.ExChangeAttributeOk;
import org.l2j.gameserver.util.Util;

import java.nio.ByteBuffer;

/**
 * @author Mobius
 */
public class RequestChangeAttributeItem extends IClientIncomingPacket {
    private int _consumeItemId;
    private int _itemObjId;
    private int _newElementId;

    @Override
    public void readImpl(ByteBuffer packet) {
        _consumeItemId = packet.getInt();
        _itemObjId = packet.getInt();
        _newElementId = packet.getInt();
    }

    @Override
    public void runImpl() {
        final L2PcInstance activeChar = client.getActiveChar();
        if (activeChar == null) {
            return;
        }

        final PcInventory inventory = activeChar.getInventory();
        final L2ItemInstance item = inventory.getItemByObjectId(_itemObjId);

        // attempting to destroy item
        if (activeChar.getInventory().destroyItemByItemId("ChangeAttribute", _consumeItemId, 1, activeChar, item) == null) {
            client.sendPacket(SystemMessageId.INCORRECT_ITEM_COUNT_2);
            client.sendPacket(ExChangeAttributeFail.STATIC);
            Util.handleIllegalPlayerAction(activeChar, "Player " + activeChar.getName() + " tried to change attribute without an attribute change crystal.", Config.DEFAULT_PUNISH);
            return;
        }

        // get values
        final int oldElementId = item.getAttackAttributeType().getClientId();
        final int elementValue = item.getAttackAttribute().getValue();
        item.clearAllAttributes();
        item.setAttribute(new AttributeHolder(AttributeType.findByClientId(_newElementId), elementValue), true);

        // send packets
        final SystemMessage msg = SystemMessage.getSystemMessage(SystemMessageId.S1_S_S2_ATTRIBUTE_HAS_SUCCESSFULLY_CHANGED_TO_S3_ATTRIBUTE);
        msg.addItemName(item);
        msg.addAttribute(oldElementId);
        msg.addAttribute(_newElementId);
        activeChar.sendPacket(msg);
        InventoryUpdate iu = new InventoryUpdate();
        iu.addModifiedItem(item);
        for (L2ItemInstance i : activeChar.getInventory().getItemsByItemId(_consumeItemId)) {
            iu.addItem(i);
        }
        activeChar.sendPacket(iu);
        activeChar.broadcastUserInfo();
        activeChar.sendPacket(ExChangeAttributeOk.STATIC);
    }
}
