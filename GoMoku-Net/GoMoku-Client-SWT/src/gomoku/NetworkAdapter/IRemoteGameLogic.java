package gomoku.NetworkAdapter;

import gomoku.Model.*;
import java.io.IOException;


public interface IRemoteGameLogic {

    String getAvailablePlayers();

    boolean choseOponent(String oponent) throws IOException, ClassNotFoundException;
    String waitForOponent() throws IOException, ClassNotFoundException;
    void ConfirmOponent() throws IOException, ClassNotFoundException;
    void RefuseOponent() throws IOException;

    void waitForMove() throws IOException, ClassNotFoundException;

    String getMyPlayerName();
    String getCurrPlayerName();
    String  getWinnerName();

    GameBoard getGameBoard();
    GoMokuGameType getGameType();
    boolean isGameOver();
    boolean getVictoryAchieved();

    void makeFirstComputerMove();
    void makeMove(Point move) throws IOException, ClassNotFoundException;

    void Terminate();
}
