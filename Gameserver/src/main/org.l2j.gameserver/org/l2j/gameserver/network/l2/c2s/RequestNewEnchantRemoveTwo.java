package org.l2j.gameserver.network.l2.c2s;

import org.l2j.gameserver.model.Player;
import org.l2j.gameserver.model.items.ItemInstance;
import org.l2j.gameserver.network.l2.s2c.ExEnchantTwoRemoveFail;
import org.l2j.gameserver.network.l2.s2c.ExEnchantTwoRemoveOK;

/**
 * @author Bonux
**/
public class RequestNewEnchantRemoveTwo extends L2GameClientPacket
{
	private int _item2ObjectId;

	@Override
	protected void readImpl()
	{
		_item2ObjectId = readInt();
	}

	@Override
	protected void runImpl()
	{
		final Player activeChar = getClient().getActiveChar();
		if(activeChar == null)
			return;

		final ItemInstance item2 = activeChar.getInventory().getItemByObjectId(_item2ObjectId);
		if(item2 == null)
		{
			activeChar.sendPacket(ExEnchantTwoRemoveFail.STATIC);
			return;
		}

		if(activeChar.getSynthesisItem2() != item2)
		{
			activeChar.sendPacket(ExEnchantTwoRemoveFail.STATIC);
			return;
		}

		activeChar.setSynthesisItem2(null);
		activeChar.sendPacket(ExEnchantTwoRemoveOK.STATIC);
	}
}