package org.l2j.gameserver.network.serverpackets;

import org.l2j.gameserver.model.matching.PartyMatchingRoom;
import org.l2j.gameserver.network.L2GameClient;
import org.l2j.gameserver.network.OutgoingPackets;

import java.nio.ByteBuffer;

/**
 * @author Gnacik
 */
public class PartyRoomInfo extends IClientOutgoingPacket {
    private final PartyMatchingRoom _room;

    public PartyRoomInfo(PartyMatchingRoom room) {
        _room = room;
    }

    @Override
    public void writeImpl(L2GameClient client, ByteBuffer packet) {
        OutgoingPackets.PARTY_ROOM_INFO.writeId(packet);

        packet.putInt(_room.getId());
        packet.putInt(_room.getMaxMembers());
        packet.putInt(_room.getMinLvl());
        packet.putInt(_room.getMaxLvl());
        packet.putInt(_room.getLootType());
        packet.putInt(_room.getLocation());
        writeString(_room.getTitle(), packet);
    }
}
