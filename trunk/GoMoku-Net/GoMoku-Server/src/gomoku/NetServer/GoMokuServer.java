package gomoku.NetServer;

import gomoku.Controller.*;
import gomoku.Model.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.net.*;
import java.util.Enumeration;
import java.util.Hashtable;


public class GoMokuServer
{
    private final int GOMOKU_SERVER_PORT = 28800;
    private final String PROTOCOL_CLIENT_SEPARATOR = ",";
    private final String PROTOCOL_NO_CLIENTS = "NONE";
    private Hashtable<Integer,NetClient> m_FreeClientsTable;
    private Hashtable<Integer,NetGame> m_CurrentGames;

    public GoMokuServer()
    {
        // Create a new clients table
        m_FreeClientsTable = new Hashtable<Integer, NetClient>();
        m_CurrentGames = new Hashtable<Integer, NetGame>();
    }

    public void startServer()
    {
        ServerSocket serverSocket = null;
        try {
            // Start listening and wait for players
            serverSocket = new ServerSocket(GOMOKU_SERVER_PORT);
            System.out.println("Server is ready...");
            for (int clientID = 1; ;clientID++)
            {
                // When a new player connects
                try
                {
                    // Accept it and add it to the server
                    Socket clientSocket = serverSocket.accept();
                    connectNewClient(clientID, clientSocket);
                    System.out.println("Player #" + clientID + " has connected");
                }
                catch (Exception ex)
                {
                    System.err.println(ex.getMessage());
                }
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            if (serverSocket != null)
                try { serverSocket.close(); }
                catch (IOException x) {}
        }
    }

    private void connectNewClient(int clientID, Socket clientSocket)
    {
        // Add the user to the list and welcome it
        NetClient newClient = new NetClient(clientID, clientSocket);
        m_FreeClientsTable.put(new Integer(clientID), newClient);
        sendWelcome(newClient);
    }

    private void sendWelcome(NetClient newClient)
    {
        // Get the socket
        Socket clientSocket = newClient.getSocket();

        // Create the users list
        StringBuilder availablePlayers = new StringBuilder(m_FreeClientsTable.size());

        // If there are no users - send empty
        if (m_FreeClientsTable.size() == 1)
        {
            availablePlayers.append(PROTOCOL_NO_CLIENTS);
        }
        else
        {
            Enumeration<NetClient> clients = m_FreeClientsTable.elements();
            while (clients.hasMoreElements())
            {
                NetClient currClient = clients.nextElement();

                // Add the players to the list except the new player itself
                if (currClient.getClientID() != newClient.getClientID())
                {
                    availablePlayers.append(currClient.getClientID());
                    availablePlayers.append(" (" + currClient.getSocket().getRemoteSocketAddress().toString() + ")");
                    availablePlayers.append(PROTOCOL_CLIENT_SEPARATOR);
                }
            }
        }

        // Sennd the players list
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(availablePlayers.toString());
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }
}
