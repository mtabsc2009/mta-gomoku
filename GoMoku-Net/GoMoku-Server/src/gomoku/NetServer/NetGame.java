package gomoku.NetServer;

import gomoku.Model.GoMokuGameType;
import gomoku.Controller.*;
import gomoku.Model.*;

public class NetGame {

    private NetClient m_Client1;
    private NetClient m_Client2;
    private GoMokuGameLogic m_GameSession;

    public NetGame(NetClient client1, NetClient client2)
    {
        m_Client1 = client1;
        m_Client2 = client2;
        m_GameSession = new GoMokuGameLogic(GoMokuGameType.UserVSUser);
    }

    public NetClient geClient1() {
        return m_Client1;
    }

    public NetClient getClient2() {
        return m_Client2;
    }

    public void Terminate()
    {
        m_Client1.Terminate();
        m_Client2.Terminate();
        m_GameSession = null;
    }

    /**
     * @return the m_GameSession
     */
    public GoMokuGameLogic getGameSession() {
        return m_GameSession;
    }
}
