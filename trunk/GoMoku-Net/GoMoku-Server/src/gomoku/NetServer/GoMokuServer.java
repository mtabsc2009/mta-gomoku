package gomoku.NetServer;

import gomoku.Controller.*;
import gomoku.Model.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.net.*;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;


public class GoMokuServer
{
    private final int GOMOKU_SERVER_PORT = 28800;
    public static final String PROTOCOL_CLIENT_SEPARATOR = ",";
    public static final String PROTOCOL_NO_CLIENTS = "NONE";
    private Hashtable m_FreeClientsTable;
    private Hashtable m_CurrentGames;

    public GoMokuServer()
    {
        // Create a new clients table
        m_FreeClientsTable = new Hashtable();
        m_CurrentGames = new Hashtable();
    }

    public void startServer() throws IOException

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
                    System.out.println("Player #" + clientID + " has connected");
                    connectNewClient(clientID, clientSocket);
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
                try {
                    closeConnections();
                    serverSocket.close();
                }
                catch (IOException x) {}
        }
    }

    private void closeConnections()
    {
        // Close free clients
        Enumeration<NetClient> clients = m_FreeClientsTable.elements();
        while (clients.hasMoreElements())
        {
            NetClient currClient = clients.nextElement();
            currClient.Terminate();
        }

        // Close games
        Enumeration<NetGame> games = m_CurrentGames.elements();
        while (games.hasMoreElements())
        {
            NetGame currGame = games.nextElement();
            currGame.Terminate();
        }
    }

    private void connectNewClient(int clientID, Socket clientSocket) throws IOException
    {
        // Add the user to the list and welcome it
        NetClient newClient = new NetClient(clientID, clientSocket, this);
        m_FreeClientsTable.put(clientID, newClient);
        sendWelcome(newClient);
        newClient.start();
    }

    public void disconnectClient(NetClient client, Exception e)
    {
        try
        {
            // Remove the client
            m_FreeClientsTable.remove(client.getClientID());
            String error = "";
            if (e != null)
            {
                error = "(" + e.getMessage() + ")";
            }
            System.out.println(String.format("Client %s has disconnected. %s", client.getClientFullName(), error));
        }
        catch (Exception ex)
        {
        }
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
                    availablePlayers.append(currClient.getClientFullName());
                    availablePlayers.append(PROTOCOL_CLIENT_SEPARATOR);
                }
            }
        }

        // Sennd the players list
        try
        {
//            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
//            out.writeObject(availablePlayers.toString());
              newClient.Send(availablePlayers.toString());
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    public boolean startGameWith(NetClient player1, int player2ID) throws IOException
    {
        boolean gameStarted = false;
        if (m_FreeClientsTable.containsKey(player2ID))
        {
            // Start a new game
            NetGame newGame = new NetGame(player1, (NetClient)m_FreeClientsTable.get(player2ID));


            // The players are no longer available
            // They are given a game
            NetClient client1 = (NetClient)m_FreeClientsTable.remove(player1.getClientID());
            NetClient client2 = (NetClient)m_FreeClientsTable.remove(player2ID);
            client1.setGame(newGame);
            client2.setGame(newGame);
            gameStarted = true;
            System.out.println("New game starting: " + player1.getClientFullName());
        }

        return gameStarted;
    }
}
