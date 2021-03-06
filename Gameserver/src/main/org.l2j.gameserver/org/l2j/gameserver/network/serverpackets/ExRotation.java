package org.l2j.gameserver.network.serverpackets;

import org.l2j.gameserver.network.L2GameClient;
import org.l2j.gameserver.network.OutgoingPackets;

import java.nio.ByteBuffer;

/**
 * @author JIV
 */
public class ExRotation extends IClientOutgoingPacket {
    private final int _charId;
    private final int _heading;

    public ExRotation(int charId, int heading) {
        _charId = charId;
        _heading = heading;
    }

    @Override
    public void writeImpl(L2GameClient client, ByteBuffer packet) {
        OutgoingPackets.EX_ROTATION.writeId(packet);

        packet.putInt(_charId);
        packet.putInt(_heading);
    }
}
