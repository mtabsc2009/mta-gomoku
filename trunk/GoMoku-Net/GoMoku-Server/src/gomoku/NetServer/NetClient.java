package gomoku.NetServer;

import gomoku.Controller.GoMokuGameLogic;
import gomoku.Model.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;


public class NetClient
{
    private int m_clientID;
    private Socket m_Socket;
    private GoMokuServer m_topServer;
    private String m_Name;
    private NetGame m_gameSession;
    private boolean m_isInitiator;
    private boolean m_confimingProposal;

    private Thread m_Thread;

    private ObjectOutputStream m_outStream;
    private ObjectInputStream m_inStream;

    public NetClient(int clientID, Socket clientSocket, GoMokuServer topServer) throws IOException
    {
        m_clientID = clientID;
        m_Socket = clientSocket;
        m_topServer = topServer;
        m_Name = "";
        m_gameSession = null;
        m_confimingProposal = false;
    }

    public int getClientID()
    {
        return m_clientID;
    }

    public String getClientUsername()
    {
        return m_Name;
    }

    public String getClientAddress()
    {
        return m_Socket.getRemoteSocketAddress().toString();
    }

    public Socket getSocket()
    {
        return m_Socket;
    }

    public String getClientFullName()
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
        System.out.println("sent to " + m_clientID + ":" + content.getClass().getName() + " " + content);
        m_outStream.flush();
    }

    public boolean confirmGameWith(NetClient requestingClient)
    {
        boolean clientConfirmed = false;
        m_confimingProposal = true;

        try
        {
            // Send the username of the requesting client
            System.out.println("confirming client " + requestingClient.getClientUsername());
            Send(requestingClient.getClientUsername());

            // Now the client sends a byte to realease the wait for initition
            if (m_Thread != null) { m_Thread.join(); }

            // Get a confirmation response from the client
            Boolean b = (Boolean)(m_inStream.readObject());
            clientConfirmed = b.booleanValue();
        }
        catch (Exception e)
        {
            System.out.println("cofirm failed " + e.toString() + " " + e.getMessage());
        }
        finally
        {
            m_confimingProposal = clientConfirmed;
            return clientConfirmed;
        }
    }

    public void WaitForInitiation()
    {
        try
        {
            m_inStream = new ObjectInputStream(m_Socket.getInputStream());

            // Get the name form the user
             m_Name = m_inStream.readObject().toString();

             // Wait for initiation
             final NetClient thisClient = this;
             m_Thread = new Thread(new Runnable()
             {
                public void run()
                {
                    try
                    {
                        // See if the user chose an oponent
                        if (waitForInitiation())
                        {
                            playGame();
                        }
                        else
                        {
                            // Block the thread, if the client exits - it will close
                            m_inStream.readObject();
                            System.out.println("stopped waiting for oponents " + thisClient.getClientFullName());
                        }
                    }
                    catch (Exception e)
                    {
                        System.err.println("waiting failed "+e.getMessage() +" "+ thisClient.getClientFullName() + " " + e.toString() + " " + e.getMessage());
                        m_topServer.disconnectClient(thisClient, e);
                        Terminate();
                    }

                }
            });
            m_Thread.start();
        }
        catch (Exception e)
        {
            m_topServer.disconnectClient(this, e);
            Terminate();
        }
    }

    protected boolean waitForInitiation() throws IOException, ClassNotFoundException
    {
        // IF i didnt reciece an invictation,
        // see if the user initiated something
        boolean oponentChosen = false;
        int oponentID = -1;
        if (!m_confimingProposal)
        {
            // Get an oponent id from the client
            System.out.println(getClientID() + " waiting for oponent " + this.getClientFullName());
            String oponent = m_inStream.readObject().toString();
            System.out.println(getClientID() + " got oponent details " + oponent);
            oponentID = parseOponentID(oponent);

            // Got a valid client id
            if (oponentID != -1)
            {
                // Offer the game to the other client
                // Start a game with the oponent in the server
                oponentChosen = m_topServer.startGameWith(this, oponentID);
                System.out.println(getClientID() + " sending from server to game " + oponentChosen);
                
                // Confirm game
                Send(new Boolean(oponentChosen));
                System.out.println(getClientID() + " sent from server to game " + oponentChosen);
                m_isInitiator = oponentChosen;
                if (oponentChosen)
                {
                    Send(m_gameSession.getGameSession().getPlayer(GoMokuGameLogic.BLACK_PLAYER_INDEX).getName());
                } else
                {
                    // Refused ior unsuccesfull, WaitForInitiation waiting for proposals
                    oponentID = -1;
                }
            }
        }
        return oponentChosen;
    }

    protected void playGame() throws IOException, ClassNotFoundException
    {
        GoMokuGameLogic game = m_gameSession.getGameSession();
        System.out.println("starting game " + this.getClientFullName());

        while (m_gameSession != null && m_gameSession.isGameInSession())
        {
            // If this client is the initiator his turn is the white turn
            // If not, its turn is the black turn
            // On any other way (the thread is not in its turn, loop until it is or the game is terminated)
            if ((m_isInitiator && game.getCurrPlayer().getName().compareToIgnoreCase("White") == 0) ||
                (!m_isInitiator && game.getCurrPlayer().getName().compareToIgnoreCase("Black") == 0))
            {
                // Get a move
                System.out.println("turn of " + m_isInitiator + " " + this.getClientFullName());
                sendBoard(game);  //TODO: remove it?
                System.out.println("board sent to " + m_isInitiator + " " + this.getClientFullName());

                // Then make a move
                Point move = (Point) m_inStream.readObject();
                Boolean  moveWasPerformed = game.makeMove(move);
                System.out.println("got move " + (moveWasPerformed?"legal ":"ilegal ") + move.toString() + "from " + m_isInitiator + " " + this.getClientFullName());
                
                ///Send(moveWasPerformed);
                System.out.println("move " + move.toString() + "was made by " + m_isInitiator + " " + this.getClientFullName());
//                if (!moveWasPerformed)
   //                 continue;
                // Send the new state
      //          sendBoard(game,move);
                sendBoard(game);
                System.out.println("board2 sent to " + m_isInitiator + " " + this.getClientFullName());
            }
            
            // avoid cpu exhaustion
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        }

        // Close the game
        m_gameSession.EndGame();
    }

    public void Terminate()
    {
        try { this.m_Thread.interrupt(); } catch (Exception ex) { }
        try { m_inStream.close(); } catch (Exception ex) { }
        try { m_outStream.close(); } catch (Exception ex) {}
        try { m_Socket.close(); } catch (Exception ex) { }

        if (m_gameSession != null)
        {
            NetGame gameSession = m_gameSession;
            m_gameSession = null;
            gameSession.Terminate();
        }
    }

    private void sendBoard(GoMokuGameLogic game/*, Point move*/) throws IOException
    {
        Send(game.getGameBoard());  // TODO: remove it?
        sendGameStats(game/*,move*/);        
    }

    private void sendGameStats(GoMokuGameLogic game/*, Point move*/) throws IOException
    {
        // Send the status of the game
        Send(game.getCurrPlayer().getName());
        Send(new Boolean(game.isGameOver()));

        // If the game is over - send the victory
        boolean isGameOn = !game.isGameOver();
        m_gameSession.updateGameSession(isGameOn);
        if (!isGameOn)
        {
            Send(new Boolean(game.getVictoryAchieved()));

            // If there was a victory - send the winner
            if (game.getVictoryAchieved())
            {
                Send(game.getWinner().getName());
            }

            // Notify the other player
            
            NetClient otherClient = this.getClientID() == m_gameSession.getClient1().getClientID() ?
                m_gameSession.getClient2() : m_gameSession.getClient1();
//            otherClient.Send(move);
            otherClient.sendBoard(game);
            
        }
    }

    public void StartGame(NetGame game) throws IOException
    {
        m_gameSession = game;

        // If im not the initiator - send the opoent to the client
        if (this.getClientID() == game.getClient2().getClientID())
        {
            Send(game.getGameSession().getPlayer(GoMokuGameLogic.WHITE_PLAYER_INDEX).getName());
            final NetClient thisClient = this;
            m_Thread = new Thread(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        playGame();
                    }
                    catch (Exception e)
                    {
                        m_topServer.disconnectClient(thisClient, e);
                        Terminate();
                    }
                }
            });
            m_Thread.start();
        }
    }

    public void EndGame()
    {
        try
        {
            if (m_Thread != null)
            {
                m_Thread.join();
            }
        }
        catch (Exception e)
        {
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
