/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku.NetworkAdapter;

import gomoku.Model.*;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

/**
 *
 * @author Slim
 */
public class GoMokuGameLogic implements IRemoteGameLogic
{
    private final String GOMOKU_SERVER_ADDRESS = "127.0.0.1";
    private final int GOMOKU_SERVER_PORT = 28800;
    private final String PROTOCOL_CLIENT_SEPARATOR = ",";
    private final String PROTOCOL_NO_CLIENTS = "NONE";

    Socket m_Socket;
    GameBoard m_GameBoard;
    HumanPlayer m_Player;
    private String m_PlayersFromServer;

    public GoMokuGameLogic(GoMokuGameType type) throws UnknownHostException, IOException, ClassNotFoundException
    {
        m_GameBoard = new GameBoard(15);
        m_Player = new HumanPlayer(m_GameBoard, "You");
        m_Socket = new Socket(GOMOKU_SERVER_ADDRESS, GOMOKU_SERVER_PORT);
        getPlayersFromServer();
    }

    private void connect() throws IOException, ClassNotFoundException
    {
        m_Socket.connect(m_Socket.getRemoteSocketAddress());

    }

    private void getPlayersFromServer() throws IOException, ClassNotFoundException
    {
        ObjectInputStream in = new ObjectInputStream(m_Socket.getInputStream());
        m_PlayersFromServer = (String)in.readObject();
    }

    public Player getCurrPlayer()
    {
        return m_Player;
    }

    public Player getWinner()
    {
        return null;
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
        return false;
    }

    public boolean getVictoryAchieved()
    {
        return false;
    }


    public void makeFirstComputerMove()
    {
    }

    public void makeMove(Point move)
    {
    }

    public String getAvailablePlayers() {
        return m_PlayersFromServer;
    }

}
