package org.l2j.gameserver.network.authcomm.as2gs;

import org.l2j.commons.network.SessionKey;
import org.l2j.gameserver.Config;
import org.l2j.gameserver.cache.HtmCache;
import org.l2j.gameserver.model.actor.instance.L2PcInstance;
import org.l2j.gameserver.network.ConnectionState;
import org.l2j.gameserver.network.Disconnection;
import org.l2j.gameserver.network.L2GameClient;
import org.l2j.gameserver.network.SystemMessageId;
import org.l2j.gameserver.network.authcomm.AuthServerCommunication;
import org.l2j.gameserver.network.authcomm.ReceivablePacket;
import org.l2j.gameserver.network.authcomm.gs2as.PlayerInGame;
import org.l2j.gameserver.network.serverpackets.*;

import java.nio.ByteBuffer;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class PlayerAuthResponse extends ReceivablePacket {
    private String account;
    private boolean authed;
    private int gameserverSession;
    private int gameserverAccountId;
    private int authAccountId;
    private int authKey;
    private int points;
    private String hwid;
    private long phoneNumber;

    @Override
    public void readImpl(ByteBuffer buffer) {
        account = readString(buffer);
        authed = buffer.get() == 1;
        if(authed) {
            gameserverSession = buffer.getInt();
            gameserverAccountId = buffer.getInt();
            authAccountId = buffer.getInt();
            authKey = buffer.getInt();
        }
    }

    @Override
    protected void runImpl() {
        L2GameClient client = AuthServerCommunication.getInstance().removeWaitingClient(account);
        if(isNull(client)) {
            return;
        }

        SessionKey skey = new SessionKey(authAccountId, authKey, gameserverSession, gameserverAccountId);
        if(authed && client.getSessionId().equals(skey)) {
            if(Config.MAX_ACTIVE_ACCOUNTS_ON_ONE_IP > 0 && isNull(AuthServerCommunication.getInstance().getAuthedClient(account))) {
                boolean ignored = false;
                for(String ignoredIP : Config.MAX_ACTIVE_ACCOUNTS_IGNORED_IP) {
                    if(ignoredIP.equalsIgnoreCase(client.getHostAddress())) {
                        ignored = true;
                        break;
                    }
                }

                if(!ignored) {
                    int limit = Config.MAX_ACTIVE_ACCOUNTS_ON_ONE_IP;

                    List<L2GameClient> clients = AuthServerCommunication.getInstance().getAuthedClientsByIP(client.getHostAddress());
                    clients.add(client);
                    if (hasMoreClientThanLimit(client, limit, clients)) {
                        return;
                    }
                }
            }

            if(Config.MAX_ACTIVE_ACCOUNTS_ON_ONE_HWID > 0 && isNull(AuthServerCommunication.getInstance().getAuthedClient(account))) {
                int limit = Config.MAX_ACTIVE_ACCOUNTS_ON_ONE_HWID;

                var whId = client.getHardwareInfo().getMacAddress();

                List<L2GameClient> clients = AuthServerCommunication.getInstance().getAuthedClientsByHWID(whId);
                clients.add(client);
                if(hasMoreClientThanLimit(client, limit, clients)) {
                    return;
                }
            }

            if(Config.MAX_ACTIVE_ACCOUNTS_ON_ONE_IP > 0 || Config.MAX_ACTIVE_ACCOUNTS_ON_ONE_HWID > 0) {
                client.sendPacket(TutorialCloseHtml.STATIC_PACKET);
            }

            client.setConnectionState(ConnectionState.AUTHENTICATED);
            client.sendPacket(LoginFail.LOGIN_SUCCESS);

            L2GameClient oldClient = AuthServerCommunication.getInstance().addAuthedClient(client);
            if(nonNull(oldClient))  {
                oldClient.setConnectionState(ConnectionState.DISCONNECTED);
                L2PcInstance activeChar = oldClient.getActiveChar();

                if(nonNull(activeChar )) {
                    activeChar.sendPacket(SystemMessageId.YOU_ARE_LOGGED_IN_TO_TWO_PLACES_IF_YOU_SUSPECT_ACCOUNT_THEFT_WE_RECOMMEND_CHANGING_YOUR_PASSWORD_SCANNING_YOUR_COMPUTER_FOR_VIRUSES_AND_USING_AN_ANTI_VIRUS_SOFTWARE);
                    Disconnection.of(activeChar).defaultSequence(false);
                } else  {
                    oldClient.close(ServerClose.STATIC_PACKET);
                }
            }

            sendPacket(new PlayerInGame(client.getAccountName()));
            var charSelectionInfo = new CharSelectionInfo(client.getAccountName(), client.getSessionId().getGameServerSessionId());
            client.sendPacket(charSelectionInfo);
            client.setCharSelection(charSelectionInfo.getCharInfo());
        } else {
            client.close(new LoginFail(LoginFail.ACCESS_FAILED_TRY_LATER));
        }
    }

    private boolean hasMoreClientThanLimit(L2GameClient client, int limit, List<L2GameClient> clients) {
        int activeWindows = clients.size();

        if(activeWindows >= limit) {
            String html = HtmCache.getInstance().getHtm(null,"windows_limit_ip.htm");
            if(nonNull(html)) {
                html = html.replace("<?active_windows?>", String.valueOf(activeWindows));
                html = html.replace("<?windows_limit?>", String.valueOf(limit));
                client.close(new TutorialShowHtml(html));
            }
            else {
                client.close(new LoginFail(LoginFail.ACCESS_FAILED_TRY_LATER));
            }
            return true;
        }
        return false;
    }
}