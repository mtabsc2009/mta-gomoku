package gomoku.NetworkAdapter;

import gomoku.Model.*;
import java.awt.Point;
import java.io.IOException;

public interface IRemoteGameLogic {

    String getAvailablePlayers();

    boolean choseOponent(String oponent) throws IOException, ClassNotFoundException;
    String waitForOponent() throws IOException, ClassNotFoundException;
    void ConfirmOponent() throws IOException, ClassNotFoundException;
    void RefuseOponent() throws IOException;

    void waitForMove() throws IOException, ClassNotFoundException;

    Player getMyPlayer();
    Player getCurrPlayer();
    Player getWinner();

    GameBoard getGameBoard();
    GoMokuGameType getGameType();
    boolean isGameOver();
    boolean getVictoryAchieved();

    void makeFirstComputerMove();
    void makeMove(Point move) throws IOException, ClassNotFoundException;

    void Terminate();
}
