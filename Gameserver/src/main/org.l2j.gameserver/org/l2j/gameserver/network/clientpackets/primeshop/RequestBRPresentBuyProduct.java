package org.l2j.gameserver.network.clientpackets.primeshop;

import org.l2j.gameserver.data.database.dao.PrimeShopDAO;
import org.l2j.gameserver.data.sql.impl.CharNameTable;
import org.l2j.gameserver.data.xml.impl.PrimeShopData;
import org.l2j.gameserver.enums.MailType;
import org.l2j.gameserver.instancemanager.MailManager;
import org.l2j.gameserver.model.actor.instance.L2PcInstance;
import org.l2j.gameserver.model.actor.request.PrimeShopRequest;
import org.l2j.gameserver.model.entity.Message;
import org.l2j.gameserver.model.itemcontainer.Mail;
import org.l2j.gameserver.model.primeshop.PrimeShopItem;
import org.l2j.gameserver.model.primeshop.PrimeShopProduct;
import org.l2j.gameserver.network.serverpackets.primeshop.ExBRBuyProduct;
import org.l2j.gameserver.network.serverpackets.primeshop.ExBRGamePoint;

import java.nio.ByteBuffer;

import static org.l2j.commons.database.DatabaseAccess.getDAO;

/**
 * @author Gnacik, UnAfraid
 */
public final class RequestBRPresentBuyProduct extends RequestBuyProduct {

    private int productId;
    private int count;
    private String _charName;
    private String _mailTitle;
    private String _mailBody;

    @Override
    public void readImpl(ByteBuffer packet) {
        productId = packet.getInt();
        count = packet.getInt();
        _charName = readString(packet);
        _mailTitle = readString(packet);
        _mailBody = readString(packet);
    }

    @Override
    public void runImpl() {
        final L2PcInstance activeChar = client.getActiveChar();

        if (activeChar == null) {
            return;
        }

        final int receiverId = CharNameTable.getInstance().getIdByName(_charName);
        if (receiverId <= 0) {
            activeChar.sendPacket(new ExBRBuyProduct(ExBRBuyProduct.ExBrProductReplyType.INVENTORY_FULL0));
            return;
        }

        if (activeChar.hasItemRequest() || activeChar.hasRequest(PrimeShopRequest.class)) {
            activeChar.sendPacket(new ExBRBuyProduct(ExBRBuyProduct.ExBrProductReplyType.INVENTORY_FULL));
            return;
        }

        activeChar.addRequest(new PrimeShopRequest(activeChar));

        final PrimeShopProduct item = PrimeShopData.getInstance().getItem(productId);
        if (validatePlayer(item, count, activeChar) && processPayment(activeChar, item, count)) {

            client.sendPacket(new ExBRBuyProduct(ExBRBuyProduct.ExBrProductReplyType.SUCCESS));
            client.sendPacket(new ExBRGamePoint());

            final Message mail = new Message(receiverId, _mailTitle, _mailBody, MailType.PRIME_SHOP_GIFT);

            final Mail attachement = mail.createAttachments();
            for (PrimeShopItem subItem : item.getItems()) {
                attachement.addItem("Prime Shop Gift", subItem.getId(), subItem.getCount(), activeChar, this);
            }
            MailManager.getInstance().sendMessage(mail);
            getDAO(PrimeShopDAO.class).addHistory(productId, count, activeChar.getObjectId());
        }

        activeChar.removeRequest(PrimeShopRequest.class);
    }
}
