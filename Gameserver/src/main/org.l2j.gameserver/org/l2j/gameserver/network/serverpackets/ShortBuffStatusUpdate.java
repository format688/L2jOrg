package org.l2j.gameserver.network.serverpackets;

import org.l2j.gameserver.network.L2GameClient;
import org.l2j.gameserver.network.OutgoingPackets;

import java.nio.ByteBuffer;

public class ShortBuffStatusUpdate extends IClientOutgoingPacket {
    public static final ShortBuffStatusUpdate RESET_SHORT_BUFF = new ShortBuffStatusUpdate(0, 0, 0, 0);

    private final int _skillId;
    private final int _skillLvl;
    private final int _skillSubLvl;
    private final int _duration;

    public ShortBuffStatusUpdate(int skillId, int skillLvl, int skillSubLvl, int duration) {
        _skillId = skillId;
        _skillLvl = skillLvl;
        _skillSubLvl = skillSubLvl;
        _duration = duration;
    }

    @Override
    public void writeImpl(L2GameClient client, ByteBuffer packet) {
        OutgoingPackets.SHORT_BUFF_STATUS_UPDATE.writeId(packet);

        packet.putInt(_skillId);
        packet.putShort((short) _skillLvl);
        packet.putShort((short) _skillSubLvl);
        packet.putInt(_duration);
    }
}
