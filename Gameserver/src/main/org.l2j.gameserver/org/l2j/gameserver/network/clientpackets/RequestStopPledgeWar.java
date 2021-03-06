package org.l2j.gameserver.network.clientpackets;

import org.l2j.gameserver.data.sql.impl.ClanTable;
import org.l2j.gameserver.model.ClanPrivilege;
import org.l2j.gameserver.model.L2Clan;
import org.l2j.gameserver.model.L2ClanMember;
import org.l2j.gameserver.model.actor.instance.L2PcInstance;
import org.l2j.gameserver.network.SystemMessageId;
import org.l2j.gameserver.network.serverpackets.ActionFailed;
import org.l2j.gameserver.taskmanager.AttackStanceTaskManager;

import java.nio.ByteBuffer;

public final class RequestStopPledgeWar extends IClientIncomingPacket {
    private String _pledgeName;

    @Override
    public void readImpl(ByteBuffer packet) {
        _pledgeName = readString(packet);
    }

    @Override
    public void runImpl() {
        final L2PcInstance player = client.getActiveChar();
        if (player == null) {
            return;
        }
        final L2Clan playerClan = player.getClan();
        if (playerClan == null) {
            return;
        }

        final L2Clan clan = ClanTable.getInstance().getClanByName(_pledgeName);

        if (clan == null) {
            player.sendMessage("No such clan.");
            player.sendPacket(ActionFailed.STATIC_PACKET);
            return;
        }

        if (!playerClan.isAtWarWith(clan.getId())) {
            player.sendMessage("You aren't at war with this clan.");
            player.sendPacket(ActionFailed.STATIC_PACKET);
            return;
        }

        // Check if player who does the request has the correct rights to do it
        if (!player.hasClanPrivilege(ClanPrivilege.CL_PLEDGE_WAR)) {
            player.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
            return;
        }

        for (L2ClanMember member : playerClan.getMembers()) {
            if ((member == null) || (member.getPlayerInstance() == null)) {
                continue;
            }
            if (AttackStanceTaskManager.getInstance().hasAttackStanceTask(member.getPlayerInstance())) {
                player.sendPacket(SystemMessageId.A_CEASE_FIRE_DURING_A_CLAN_WAR_CAN_NOT_BE_CALLED_WHILE_MEMBERS_OF_YOUR_CLAN_ARE_ENGAGED_IN_BATTLE);
                return;
            }
        }

        // Reduce reputation.
        playerClan.takeReputationScore(500, true);

        ClanTable.getInstance().deleteClanWars(playerClan.getId(), clan.getId());

        for (L2PcInstance member : playerClan.getOnlineMembers(0)) {
            member.broadcastUserInfo();
        }

        for (L2PcInstance member : clan.getOnlineMembers(0)) {
            member.broadcastUserInfo();
        }
    }
}
