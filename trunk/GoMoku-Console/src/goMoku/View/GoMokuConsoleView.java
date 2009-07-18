package goMoku.View;

import java.io.*;
import goMoku.Controller.GoMokuGame;
import goMoku.Model.*;
import java.awt.Point;

public class GoMokuConsoleView implements IGoMokuView {

    // Constants
    public static final String USERVSUSER = "UU";
    public static final String USERVSCOMPUTER = "UC";
    public static final String COMPUTERVSUSER = "CU";
    public static final String COMPUTERVSCOMPUTER = "CC";

    private static char BLACK_PLAYER_MARK = 'X';
    private static char WHITE_PLAYER_MARK = '0';
    private static char EMPTY_MARK = '-';

    private final int QUIT_CODE = -1;

    // Members
    private String m_blackTitle = "";
    private String m_whiteTitle = "";

    public void ShowGameTypeError()
    {
        ShowWelcome();

        System.out.println("Invalid command line parameters.");
        System.out.println("Run GoMoku using the following format:");
        System.out.println("    java Main <GameType>:");
        System.out.println();
        System.out.println("    Where <GameType> can be:");
        System.out.println("      '" + COMPUTERVSCOMPUTER +"' - Computer VS Computer");
        System.out.println("      '" + USERVSUSER +"' - User VS User");
        System.out.println("      '" + COMPUTERVSUSER +"' - Computer VS User");
        System.out.println("      '" + USERVSCOMPUTER +"' - User VS Computer");
        System.out.println();
        System.out.println("    Example: java Main CC");
    }

    public void ShowWelcome() {
        System.out.println("Welcome to GoMoku game!");
        System.out.println("===========================================================");
    }

    public void ShowGoodbye() {
        System.out.println("Thank you and goodbye.");
    }

    public int GetBoardSize(int minSize, int maxSize) {
        final String QUIT_STRING = "q";
        int size = 0;
        String sizeString;


        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            do {
                System.out.println(String.format("Enter board size (%d to %d) or '%s' to quit: ",
                        minSize, maxSize, QUIT_STRING));
                sizeString = in.readLine();

                if (sizeString.compareTo(QUIT_STRING) == 0) {
                    size = QUIT_CODE;
                } else {
                    try {
                        size = Integer.parseInt(sizeString);
                        if (size < minSize || size > maxSize) {
                            size = 0;
                            System.out.println("Board size out of range");
                        }
                    } catch (NumberFormatException en) {
                        size = 0;
                        System.out.println("Invalid board size");
                    }
                }
            } while (size == 0);


        }  catch (IOException e) {
            size = QUIT_CODE;
        }

        return size;
    }
    
    public void SetPlayersTitle(String blackPlayerTitle, String whitePlayerTitle) {
            m_blackTitle = blackPlayerTitle;
            m_whiteTitle = whitePlayerTitle;
    }


    public void ShowStart() {

        System.out.println();
        System.out.println("New game starting:");
        System.out.println("------------------");

        ShowPlayers();
    }

    private void ShowPlayers()
    {
        System.out.println("Players are:");
        System.out.println(String.format("Black Player (first): \t%s  marked as %s",m_blackTitle ,BLACK_PLAYER_MARK));
        System.out.println(String.format("White Player (second): \t%s marked as %s",m_whiteTitle ,WHITE_PLAYER_MARK));
    }

    public void PrintBoard(GameBoard board) {

        PrintStream out = System.out;

        out.println();
        int size = board.getSize();

        // print the board lines
        for (int line = 1; line <= size; line++) {

            out.print(String.format("%2d| ", line));

            // in each line, print a column
            for (int col = 1; col <= size; col++) {
                char pawnMark = EMPTY_MARK;

                // Get the pawn and its marker
                Pawn pawn = board.getPawn(line, col);
                if (pawn == null) {
                    pawnMark = EMPTY_MARK;
                } else if (pawn instanceof BlackPawn) {
                    pawnMark = BLACK_PLAYER_MARK;
                } else if (pawn instanceof WhitePawn) {
                    pawnMark = WHITE_PLAYER_MARK;
                }

                out.print(String.format("%3c", pawnMark));
            }

            out.println();
        }

        // Print the column titles
        out.print("  - ");
        for (int coltitle = 0; coltitle < size; coltitle++) {
            out.print(String.format("%3c", (char)((int)(GameBoard.BOARD_START_COLUMN) + coltitle)));
        }
        out.println();
        out.println();
    }

    public String GetMove() {
        String move = null;
        return move;

        // TODO: implement get move
    }
}
