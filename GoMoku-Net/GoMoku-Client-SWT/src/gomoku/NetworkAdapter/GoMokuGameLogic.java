/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku.NetworkAdapter;

import gomoku.Model.*;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 *
 * @author Slim
 */
public class GoMokuGameLogic implements IRemoteGameLogic
{
    private final String GOMOKU_SERVER_ADDRESS = "127.0.0.1";
    private final int GOMOKU_SERVER_PORT = 28800;
    public static final String PROTOCOL_CLIENT_SEPARATOR = ",";
    public static final String PROTOCOL_NO_CLIENTS = "NONE";

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

    public boolean choseOponent(String oponent)
    {
        boolean oponentChosen = false;
        try
        {
            m_outStream.writeObject(oponent);
            m_outStream.flush();
            Boolean b = (Boolean)m_inStream.readObject();
            oponentChosen = b.booleanValue();
            if (oponentChosen)
            {
                m_Oponent = (Player)m_inStream.readObject();
            }
        }
        catch (Exception e)
        {
        }

        return oponentChosen;
    }

    public Player waitForOponent() throws IOException, ClassNotFoundException
    {
        m_Oponent = (Player)m_inStream.readObject();

        // Confirm player
        m_outStream.writeObject(m_Oponent);
        m_outStream.flush();

        return m_Oponent;
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
        m_GameBoard = null;
        m_GameBoard = (GameBoard)m_inStream.readObject();
        readGameStats();
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
        }
    }
}
