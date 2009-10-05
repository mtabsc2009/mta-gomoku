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
    private int m_GameIDSequence;

    public GoMokuServer()
    {
        // Create a new clients table
        m_FreeClientsTable = new Hashtable();
        m_CurrentGames = new Hashtable();
        m_GameIDSequence = 1;
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

    public boolean startGameWith(NetClient client1, int client2ID) throws IOException
    {
        boolean gameStarted = false;
        if (m_FreeClientsTable.containsKey(client2ID))
        {
            // Confirm the game with the other user
            NetClient client2 = (NetClient)m_FreeClientsTable.get(client2ID);
            if (client2.confirmGameWith(client1))
            {
                // Start a new game
                NetGame newGame = new NetGame(this, m_GameIDSequence++, client1, client2);
                m_CurrentGames.put(newGame.getGameID(), this);

                // The players are no longer available - remove them from the list
                client1 = (NetClient)m_FreeClientsTable.remove(client1.getClientID());
                client2 = (NetClient)m_FreeClientsTable.remove(client2ID);

                // They are given a game
                client1.setGame(newGame);
                client2.setGame(newGame);
                gameStarted = true;
                System.out.println("New game starting: " + client1.getClientFullName());
            }
        }

        return gameStarted;
    }

    public void EndGame(NetGame gameOver)
    {
        try
        {
            m_CurrentGames.remove(gameOver.getGameID());
//            m_FreeClientsTable.put(gameOver.getClient1().getClientID(), gameOver.getClient1());
//            m_FreeClientsTable.put(gameOver.getClient2().getClientID(), gameOver.getClient2());
        }
        catch (Exception e)
        {
            try { gameOver.getClient1().Terminate(); } catch (Exception e2) { }
            try { gameOver.getClient2().Terminate(); } catch (Exception e2) { }
        }
    }
}
