/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku.NetworkAdapter;

import gomoku.Model.*;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Properties;

/**
 *
 * @author Slim
 */
public class GoMokuGameLogic implements IRemoteGameLogic
{
    public static String GOMOKU_SERVER_ADDRESS = "127.0.0.1";
    public static int GOMOKU_SERVER_PORT = 28800;
    public static String PROTOCOL_CLIENT_SEPARATOR = ",";
    public static String PROTOCOL_NO_CLIENTS = "NONE";

    private Socket m_Socket;
    private GameBoard m_GameBoard;
    private Player m_Player;
    private Player m_Oponent;
    private String m_PlayersFromServer;

    private boolean m_IsGameOver;
    private boolean m_IsVictoryAcheaved;
    private Player m_Winner;
    private Player m_CurrPlayer;
    
    private ObjectInputStream m_inStream;
    private ObjectOutputStream m_outStream;

    public GoMokuGameLogic(GoMokuGameType type, String playerName) throws UnknownHostException, IOException, ClassNotFoundException
    {
        m_GameBoard = new GameBoard(15);
        m_Player = new HumanPlayer(m_GameBoard, playerName);

        m_IsGameOver = false;
        m_IsVictoryAcheaved = false;
        m_Winner = null;
        m_CurrPlayer = m_Player;


        try
        {
            Properties clientConfig = new Properties();
            FileInputStream configFile = new FileInputStream("Client.Config");
            clientConfig.load(configFile);
            GOMOKU_SERVER_ADDRESS = clientConfig.get("Server_Address").toString();
            GOMOKU_SERVER_PORT = Integer.parseInt(clientConfig.get("Server_Port").toString());
            PROTOCOL_CLIENT_SEPARATOR = clientConfig.get("Protocol_Client_Separator").toString();
            PROTOCOL_NO_CLIENTS = clientConfig.get("Porocol_No_Clients").toString();
            configFile.close();
        }
        catch (Exception e)
        {
            System.err.println("Config error " + e.toString() + " " + e.getMessage());
        }

        m_Socket = new Socket(GOMOKU_SERVER_ADDRESS, GOMOKU_SERVER_PORT);
        m_inStream = new ObjectInputStream(m_Socket.getInputStream());
        getPlayersFromServer();
        m_outStream = new ObjectOutputStream(m_Socket.getOutputStream()) ;
        m_outStream.writeObject(playerName);
        m_outStream.flush();
    }

    public void Terminate()
    {
        try { m_inStream.close(); } catch (Exception ex) { ; }
        try { m_outStream.close(); } catch (Exception ex) { ; }
        try { m_Socket.close(); } catch (Exception ex) { ; }
    }

    public boolean choseOponent(String oponentDetails) throws IOException, ClassNotFoundException
    {
        boolean oponentChosen = false;
        
        // Ofer the oponent
        m_outStream.writeObject(oponentDetails);
        m_outStream.flush();

        // Get confirmatiion from the server
        Boolean b = (Boolean)m_inStream.readObject();
        oponentChosen = b.booleanValue();

        // If confirmed - get the oponent
        if (oponentChosen)
        {
            m_Oponent = (Player)m_inStream.readObject();
        }

        return oponentChosen;
    }

    public Player getOponent()
    {
        return m_Oponent;
    }

    public String waitForOponent() throws IOException, ClassNotFoundException
    {
        // release the client from waiting to an intiaition (the player is not choosing an oponent)
        m_outStream.writeObject("a");
        m_outStream.flush();

        String oponent = m_inStream.readObject().toString();

        // Write a byte to release the server from waiting to close the session
        m_outStream.writeObject(new Boolean(true));
        m_outStream.flush();
       
        return oponent;
    }

    public void ConfirmOponent() throws IOException, ClassNotFoundException
    {
        // Confirm the offer
        m_outStream.flush();
        m_outStream.writeObject(new Boolean(true));
        m_outStream.flush();

        // Get the oponent
        m_Oponent = (Player)m_inStream.readObject();

        // Confirm player
        m_outStream.flush();
    }

    public void RefuseOponent() throws IOException
    {
        // Refure the player's offer
        m_outStream.writeObject(new Boolean(false));
        m_outStream.flush();
    }

    public Player getMyPlayer()
    {
        return m_Player;
    }


    public void waitForMove() throws IOException, ClassNotFoundException
    {
        readBoard();
    }

    private void getPlayersFromServer() throws IOException, ClassNotFoundException
    {
        m_PlayersFromServer = (String)m_inStream.readObject();
    }

    public Player getCurrPlayer()
    {
        return m_CurrPlayer;
    }

    public Player getWinner()
    {
        return m_Winner;
    }

    public GameBoard getGameBoard()
    {
        return m_GameBoard;
    }

    public GoMokuGameType getGameType()
    {
        return GoMokuGameType.UserVSUser;
    }


    /**
     *
     * @return true if [at least one of] the game-over conditions are met:
     * <li> no more free cells on the board
     * <li> one of the player achieved victory
     * <br>
     * otherwise returns false
     *
     */
    public boolean isGameOver()
    {
        return m_IsGameOver;
    }

    public boolean getVictoryAchieved()
    {
        return m_IsVictoryAcheaved;
    }


    public void makeFirstComputerMove()
    {
    }

    public void makeMove(Point move) throws IOException, ClassNotFoundException
    {
        m_outStream.writeObject(move);
        m_outStream.flush();
        readBoard();
    }

    public String getAvailablePlayers() {
        return m_PlayersFromServer;
    }

    private void readBoard() throws IOException, ClassNotFoundException
    {
        readBoardView();
        readGameStats();
    }

    private void readBoardView() throws IOException, ClassNotFoundException
    {
        m_GameBoard = null;
        m_GameBoard = (GameBoard)m_inStream.readObject();
    }
    
    private void readGameStats() throws IOException, ClassNotFoundException
    {
        // Send the status of the game
        m_CurrPlayer = (Player)m_inStream.readObject();
        if (m_Oponent != null && m_Oponent.getName().compareTo(m_CurrPlayer.getName()) != 0)
        {
            m_Player = m_CurrPlayer;
        }

        m_IsGameOver = ((Boolean)m_inStream.readObject()).booleanValue();

        // If the game is over - send the victory
        if (m_IsGameOver)
        {
            m_IsVictoryAcheaved = ((Boolean)m_inStream.readObject()).booleanValue();

            // If there was a victory - send the winner
            if (m_IsVictoryAcheaved)
            {
                m_Winner = (Player)m_inStream.readObject();
            }

            try { readBoardView(); }
            catch (Exception e) { }
        }
    }
}
