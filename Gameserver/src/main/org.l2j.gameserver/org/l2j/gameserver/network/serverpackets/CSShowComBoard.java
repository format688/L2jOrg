package org.l2j.gameserver.network.serverpackets;

import org.l2j.gameserver.network.L2GameClient;
import org.l2j.gameserver.network.OutgoingPackets;

import java.nio.ByteBuffer;

public final class CSShowComBoard extends IClientOutgoingPacket {
    private final byte[] _html;

    public CSShowComBoard(byte[] html) {
        _html = html;
    }

    @Override
    public void writeImpl(L2GameClient client, ByteBuffer packet) {
        OutgoingPackets.SHOW_BOARD.writeId(packet);

        packet.put((byte) 0x01); // c4 1 to show community 00 to hide
        packet.put(_html);
    }
}
