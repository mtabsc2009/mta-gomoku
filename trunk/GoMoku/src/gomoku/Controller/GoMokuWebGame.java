package gomoku.Controller;

import java.awt.Point;


public class GoMokuWebGame extends GoMokuGame {

    public GoMokuWebGame(GoMokuGameType gameType) {
        super(gameType);
    }

    private void makeComputerMove(int nextPlayer) {
        if (m_players[nextPlayer] instanceof ComputerPlayer) {
             
                Point computerMove = m_players[nextPlayer].makeMove();
                if (isWhiteTurn) {
                    m_gameBoard.PlaceWhitePawn(computerMove);
                } else {
                    m_gameBoard.PlaceBlackPawn(computerMove);
                }

            // If the game is not over - switch turns
            if (!isGameOver()) {
                isWhiteTurn = !isWhiteTurn;
            }
        }
    }

    public void makeMove(Point move)
    {

        // Play the game until its over (quit, full board, or winning)
        if (!isGameOver())
        {
            // one of the players has entered an occupied position
            if (!m_gameBoard.hasPawn(move) )
            {
                // the specified location is free - now we can place the pawn
                boolean isLegalMove = false;
                if (isWhiteTurn) {
                    isLegalMove = m_gameBoard.PlaceWhitePawn(move);
                } else {
                    isLegalMove = m_gameBoard.PlaceBlackPawn(move);
                }

                // Move is legal
                // If the computer plays next, make a move
                if (isLegalMove && !isGameOver())
                {
                    int nextPlayer = isWhiteTurn ? BLACK_PLAYER_INDEX : WHITE_PLAYER_INDEX;
                    isWhiteTurn = !isWhiteTurn;
                    makeComputerMove(nextPlayer);
                }
            }
        }
    }

    public void makeFirstComputerMove()
    {
        int computerIndex = !isWhiteTurn ? BLACK_PLAYER_INDEX : WHITE_PLAYER_INDEX;
        makeComputerMove(computerIndex);
    }

    public Player getWinner()
    {
        Player winningPlayer = null;

        // If indeed theres a victory
        if (getVictoryAchieved())
        {
            winningPlayer = isWhiteTurn ? m_players[WHITE_PLAYER_INDEX] : m_players[BLACK_PLAYER_INDEX];
        }

        return winningPlayer;
    }
}
