package gomoku.NetServer;

import java.net.*;

public class NetClient
{
    private int m_ClientID;
    private Socket m_Socket;

    public NetClient(int clientID, Socket clientSocket)
    {
        m_ClientID = clientID;
        m_Socket = clientSocket;
    }

    public int getClientID() {
        return m_ClientID;
    }

    public Socket getSocket() {
        return m_Socket;
    }
}
