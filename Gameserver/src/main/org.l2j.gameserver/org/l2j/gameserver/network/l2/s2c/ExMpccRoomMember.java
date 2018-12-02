package org.l2j.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.l2j.gameserver.instancemanager.MatchingRoomManager;
import org.l2j.gameserver.model.Player;
import org.l2j.gameserver.model.matching.MatchingRoom;

/**
 * @author VISTALL
 */
public class ExMpccRoomMember extends L2GameServerPacket
{
	private int _type;
	private List<MpccRoomMemberInfo> _members = Collections.emptyList();

	public ExMpccRoomMember(MatchingRoom room, Player player)
	{
		_type = room.getMemberType(player);
		_members = new ArrayList<MpccRoomMemberInfo>(room.getPlayers().size());

		for(Player member : room.getPlayers())
			_members.add(new MpccRoomMemberInfo(member, room.getMemberType(member)));
	}

	@Override
	public void writeImpl()
	{
		writeInt(_type);
		writeInt(_members.size());
		for(MpccRoomMemberInfo member : _members)
		{
			writeInt(member.objectId);
			writeString(member.name);
			writeInt(member.classId);
			writeInt(member.level);
			writeInt(member.location);
			writeInt(member.memberType);
		}
	}

	static class MpccRoomMemberInfo
	{
		public final int objectId;
		public final int classId;
		public final int level;
		public final int location;
		public final int memberType;
		public final String name;

		public MpccRoomMemberInfo(Player member, int type)
		{
			this.objectId = member.getObjectId();
			this.name = member.getName();
			this.classId = member.getClassId().ordinal();
			this.level = member.getLevel();
			this.location = MatchingRoomManager.getInstance().getLocation(member);
			this.memberType = type;
		}
	}
}