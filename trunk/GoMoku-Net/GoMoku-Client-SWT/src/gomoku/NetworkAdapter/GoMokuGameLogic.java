/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku.NetworkAdapter;

import gomoku.Model.*;
import java.awt.Point;
import java.net.*;

/**
 *
 * @author Slim
 */
public class GoMokuGameLogic implements IRemoteGameLogic
{
//    Socket m_Socket;
    GameBoard m_GameBoard;
    HumanPlayer m_Player;

    public GoMokuGameLogic(GoMokuGameType type)
    {
        m_GameBoard = new GameBoard(15);
        m_Player = new HumanPlayer(m_GameBoard, "You");
//        m_Socket = new Socket();
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

}
