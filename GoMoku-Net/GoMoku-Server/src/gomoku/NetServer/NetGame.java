package gomoku.NetServer;

import gomoku.Controller.*;
import gomoku.Model.*;

public class NetGame {

    private NetClient m_Client1;
    private NetClient m_Client2;
    private GoMokuGameLogic m_GameSession;
    private boolean m_gameInSession;
    private GoMokuServer m_topServer;
    private int m_gameID;

    public NetGame(GoMokuServer topServer, int gameID, NetClient client1, NetClient client2)
    {
        m_Client1 = client1;
        m_Client2 = client2;
        m_GameSession = new GoMokuGameLogic(GoMokuGameType.UserVSUser);
        m_GameSession.getPlayer(GoMokuGame.WHITE_PLAYER_INDEX).setUserName(client1.getClientUsername());
        m_GameSession.getPlayer(GoMokuGame.BLACK_PLAYER_INDEX).setUserName(client2.getClientUsername());
        m_gameInSession = true;
        m_topServer = topServer;
        m_gameID = gameID;
    }

    public NetClient getClient1() {
        return m_Client1;
    }

    public NetClient getClient2() {
        return m_Client2;
    }

    public void Terminate()
    {
        m_gameInSession = false;
        m_Client1.Terminate();
        m_Client2.Terminate();
        m_GameSession = null;
    }

    public boolean isGameInSession()
    {
        return m_gameInSession;
    }

    public void updateGameSession(boolean gameInSession)
    {
        m_gameInSession = gameInSession;
    }

    public GoMokuGameLogic getGameSession() {
        return m_GameSession;
    }

    public void EndGame()
    {
        try { m_Client1.EndGame(); } catch (Exception e) { }
        try { m_Client2.EndGame(); } catch (Exception e) { }
        m_GameSession = null;
        m_topServer.EndGame(this);
        Terminate();
    }

    /**
     * @return the m_gameID
     */
    public int getGameID() {
        return m_gameID;
    }
}
