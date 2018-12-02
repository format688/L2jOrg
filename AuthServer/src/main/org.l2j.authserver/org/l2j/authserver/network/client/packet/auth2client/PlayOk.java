package org.l2j.authserver.network.client.packet.auth2client;

import org.l2j.authserver.network.client.packet.L2LoginServerPacket;

public final class PlayOk extends L2LoginServerPacket {
    private final int serverId;

    public PlayOk(int serverId) {
        this.serverId = serverId;
    }

    @Override
    protected void writeImpl() {
        var sessionKey = client.getSessionKey();
        writeByte(0x07);
        writeInt(sessionKey.gameServerSessionId);
        writeInt(sessionKey.gameServerAccountId);
        writeByte(serverId);
    }

    @Override
    protected int packetSize() {
        return super.packetSize() + 10;
    }
}