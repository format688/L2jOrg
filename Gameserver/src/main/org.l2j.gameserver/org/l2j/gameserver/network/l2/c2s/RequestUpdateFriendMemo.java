package org.l2j.gameserver.network.l2.c2s;

import org.l2j.gameserver.model.Player;

/**
 * @author Bonux
**/
public class RequestUpdateFriendMemo extends L2GameClientPacket
{
	private String _name;
	private String _memo;

	@Override
	protected void readImpl()
	{
		_name = readString();
		_memo = readString();
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = getClient().getActiveChar();
		if(activeChar == null)
			return;

		activeChar.getFriendList().updateMemo(_name, _memo);
	}
}