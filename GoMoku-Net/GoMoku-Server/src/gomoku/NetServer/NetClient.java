package gomoku.NetServer;

import gomoku.Controller.GoMokuGameLogic;
import gomoku.Model.GameBoard;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Calendar;
import java.util.Date;

public class NetClient extends Thread
{
    private int m_clientID;
    private Socket m_Socket;
    private GoMokuServer m_topServer;
    private String m_Name;
    private NetGame m_gameSession;
    private boolean m_isInitiator;

    private ObjectOutputStream m_outStream;
    private ObjectInputStream m_inStream;

    public NetClient(int clientID, Socket clientSocket, GoMokuServer topServer) throws IOException
    {
        m_clientID = clientID;
        m_Socket = clientSocket;
        m_topServer = topServer;
        m_Name = "";
        m_gameSession = null;
    }

    public int getClientID() {
        return m_clientID;
    }

    public Socket getSocket() {
        return m_Socket;
    }

    public String getFullName()
    {
        String address = "";
        try
        {
            if (m_Socket != null)
            {
                address = m_Socket.getRemoteSocketAddress().toString();
            }
        }
        catch (Exception ex) { }
        return String.format("%d %s (%s)", m_clientID, m_Name, address);
    }

    public void Send(Object content) throws IOException
    {
        if (m_outStream == null)
        {
            m_outStream = new ObjectOutputStream(m_Socket.getOutputStream());
        }
        m_outStream.flush();
        m_outStream.reset();
        m_outStream.writeObject(content);
        m_outStream.flush();
    }

    @Override
    public void run()
    {
        try
        {
            m_inStream = new ObjectInputStream(m_Socket.getInputStream());

            // Get the name form the user
            m_Name = m_inStream.readObject().toString();

            // Get an oponent
            boolean oponentChosen = false;
            while (!oponentChosen)
            {
                System.out.println("waiting for oponent " + this.getFullName());
                String oponent = m_inStream.readObject().toString();
                int opoentID = parseOponentID(oponent);

                // If i am the initiator
                if (m_gameSession == null)
                {
                    // Start a game with the oponent in the server
                    oponentChosen = m_topServer.startGameWith(this, opoentID);
                    // Confirm game
                    Send(new Boolean(oponentChosen));
                    m_isInitiator = oponentChosen;
                }
                // Someone started a game with me
                else
                {
                    oponentChosen = true;
                    m_isInitiator = false;
                }
            }

            // Play
            GoMokuGameLogic game = m_gameSession.getGameSession();
            System.out.println("ingame init:" + m_isInitiator + " " + this.getFullName());

            while (true)
            {
                // If this client is the initiator it goes first
                // so wait for the white turn
                if (m_isInitiator && game.getCurrPlayer().getName().compareToIgnoreCase("White") == 0)
                {
                    // Get a move
                    System.out.println("turn of " + m_isInitiator + " " + this.getFullName());
                    sendBoard(game);
                    System.out.println("board sent to " + m_isInitiator + " " + this.getFullName());
                    Point move = (Point)m_inStream.readObject();
                    System.out.println("got move " + move.toString() + "from " + m_isInitiator + " " + this.getFullName());
                    game.makeMove(move);
                    System.out.println("move " + move.toString() + "was made by " + m_isInitiator + " " + this.getFullName());

                    // Send the new state
                    sendBoard(game);
                    System.out.println("board2 sent to " + m_isInitiator + " " + this.getFullName());
                }
                // If im not the initiator, and its my turn
                else if (!m_isInitiator && game.getCurrPlayer().getName().compareToIgnoreCase("Black") == 0)
                {
                    // first send the board state
                    System.out.println("n turn of " + m_isInitiator + " " + this.getFullName());
                    sendBoard(game);
                    System.out.println("n board sent to " + m_isInitiator + " " + this.getFullName());

                    // Then make a move
                    Point move = (Point)m_inStream.readObject();
                    System.out.println("n got move " + move.toString() + "from " + m_isInitiator + " " + this.getFullName());
                    game.makeMove(move);
                    System.out.println("n move " + move.toString() + "was made by " + m_isInitiator + " " + this.getFullName());

                    // Send the new state
                    sendBoard(game);
                    System.out.println("n board2 sent to " + m_isInitiator + " " + this.getFullName());
                }
            }
        }
        catch (Exception e)
        {
            m_topServer.disconnectClient(this, e);
        }
        finally
        {
            Terminate();
        }
    }

    public void Terminate()
    {
        try { this.currentThread().interrupt(); } catch (Exception ex) { ; }
        try { m_inStream.close(); } catch (Exception ex) { ; }
        try { m_outStream.close(); } catch (Exception ex) { ; }
        try { m_Socket.close(); } catch (Exception ex) { ; }

        if (m_gameSession != null)
        {
            NetGame gameSession = m_gameSession;
            m_gameSession = null;
            gameSession.Terminate();
        }
    }

    private void sendBoard(GoMokuGameLogic game) throws IOException
    {
        Send(game.getGameBoard());
        sendGameStats(game);        
    }

    private void sendGameStats(GoMokuGameLogic game) throws IOException
    {
        // Send the status of the game
        Send(new Boolean(game.isGameOver()));

        // If the game is over - send the victory
        if (game.isGameOver())
        {
            Send(new Boolean(game.getVictoryAchieved()));

            // If there was a victory - send the winner
            if (game.getVictoryAchieved())
            {
                Send(game.getWinner());
            }

            // Notify the other player
            NetClient otherClient = this.getClientID() == m_gameSession.geClient1().getClientID() ?
                m_gameSession.getClient2() : m_gameSession.geClient1();
            otherClient.sendBoard(game);

            // Close the game
            m_gameSession.Terminate();
        }
    }

    public void setGame(NetGame game) throws IOException
    {
        m_gameSession = game;

        // If im not the initiator - send the opoent to the client
        if (this.getClientID() == game.getClient2().getClientID())
        {
            Send(game.geClient1().getFullName());
        }
    }

    private int parseOponentID(String oponent)
    {
        int id = -1;

        try
        {
            id = Integer.parseInt(oponent.split(" ")[0]);
        }
        catch (Exception e)
        {
        }
        finally
        {
            return id;
        }
    }
}
