package org.l2j.gameserver.network.serverpackets;

import org.l2j.gameserver.model.L2Clan;
import org.l2j.gameserver.network.L2GameClient;
import org.l2j.gameserver.network.OutgoingPackets;
import org.l2j.gameserver.settings.ServerSettings;

import java.nio.ByteBuffer;

import static org.l2j.commons.configuration.Configurator.getSettings;

public class PledgeShowInfoUpdate extends IClientOutgoingPacket {
    private final L2Clan _clan;

    public PledgeShowInfoUpdate(L2Clan clan) {
        _clan = clan;
    }

    @Override
    public void writeImpl(L2GameClient client, ByteBuffer packet) {
        OutgoingPackets.PLEDGE_SHOW_INFO_UPDATE.writeId(packet);

        // sending empty data so client will ask all the info in response ;)
        packet.putInt(_clan.getId());
        packet.putInt(getSettings(ServerSettings.class).serverId());
        packet.putInt(_clan.getCrestId());
        packet.putInt(_clan.getLevel()); // clan level
        packet.putInt(_clan.getCastleId());
        packet.putInt(0x00); // castle state ?
        packet.putInt(_clan.getHideoutId());
        packet.putInt(_clan.getFortId());
        packet.putInt(_clan.getRank());
        packet.putInt(_clan.getReputationScore()); // clan reputation score
        packet.putInt(0x00); // ?
        packet.putInt(0x00); // ?
        packet.putInt(_clan.getAllyId());
        writeString(_clan.getAllyName(), packet); // c5
        packet.putInt(_clan.getAllyCrestId()); // c5
        packet.putInt(_clan.isAtWar() ? 1 : 0); // c5
        packet.putInt(0x00); // TODO: Find me!
        packet.putInt(0x00); // TODO: Find me!
    }
}
