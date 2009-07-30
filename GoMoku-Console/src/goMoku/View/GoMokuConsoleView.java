package goMoku.View;

import java.io.*;
import goMoku.Model.*;
import java.awt.Point;

public class GoMokuConsoleView implements IGoMokuView {

    // Constants
    public static final String USERVSUSER = "UU";
    public static final String USERVSCOMPUTER = "UC";
    public static final String COMPUTERVSUSER = "CU";
    public static final String COMPUTERVSCOMPUTER = "CC";
    protected static final String QUIT_STRING = "q";

    public static char BLACK_PLAYER_MARK = 'X';
    public static char WHITE_PLAYER_MARK = '0';
    public static String BLACK_PLAYER_TITLE = "Black";
    public static String WHITE_PLAYER_TITLE = "White";
    private static char EMPTY_MARK = '-';

    private final int QUIT_CODE = -1;

    // Members
    private String m_blackTitle;
    private String m_whiteTitle;

    public void showGameUsage()
    {
        showWelcome();

        //outputMessage("Invalid command line parameters.");
        outputMessage("Run GoMoku using the following format:");
        outputMessage("    java Main <GameType>:");
        outputMessage("");
        outputMessage("    Where <GameType> can be:");
        outputMessage("      '" + COMPUTERVSCOMPUTER 	+	"' - Computer VS Computer"	);
        outputMessage("      '" + USERVSUSER 			+	"' - User VS User"			);
        outputMessage("      '" + COMPUTERVSUSER 		+	"' - Computer VS User"		);
        outputMessage("      '" + USERVSCOMPUTER 		+	"' - User VS Computer"		);
        outputMessage("");
        outputMessage("    Example: java Main CC");
    }

    public void showWelcome() {
        outputMessage("Welcome to GoMoku game!");
        outputMessage("===========================================================");
    }

    public void showGoodbye() {
        outputMessage("Thank you and goodbye.");
    }

    public int getBoardSize(int minSize, int maxSize) {
        int size = 0;
        String sizeString;


        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            do {
                outputMessage(String.format("Enter board size (%d to %d) or '%s' to quit: ",
                        minSize, maxSize, QUIT_STRING));
                sizeString = in.readLine();

                if (sizeString.compareTo(QUIT_STRING) == 0) {
                    size = QUIT_CODE;
                } else {
                    try {
                        size = Integer.parseInt(sizeString);
                       /// FIXME: (why?) we should do make this check somewhere else.
                        if (size < minSize || size > maxSize) {
                            size = 0;
                            outputMessage("Board size out of range");
                        }
                        
                        
                    } catch (NumberFormatException en) {
                        size = 0;
                        outputMessage("Invalid board size");
                    }
                }
            } while (size == 0);


        }  catch (IOException e) {
            size = QUIT_CODE;
        }

        return size;
    }
    
    public void setPlayersTitle(String blackPlayerTitle, String whitePlayerTitle) {
            m_blackTitle = blackPlayerTitle;
            m_whiteTitle = whitePlayerTitle;
    }


    public void showStart() {

        outputMessage("");
        outputMessage("New game starting:");
        outputMessage("------------------");

        showPlayers();
    }

    private void showPlayers()
    {
        outputMessage("Players are:");
        outputMessage(String.format("Black Player (first): \t%s  marked as %s",m_blackTitle ,BLACK_PLAYER_MARK));
        outputMessage(String.format("White Player (second): \t%s marked as %s",m_whiteTitle ,WHITE_PLAYER_MARK));
    }

    public void printBoard(GameBoard board) {

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
                if (board.hasPawn(line,col)) {
                	
                	if (board.getPawnType(line,col) == PawnType.White) {
                		pawnMark = WHITE_PLAYER_MARK;	
                	} else {
                		pawnMark = BLACK_PLAYER_MARK;
                	}
                }

                out.print(String.format("%3c", pawnMark));
            }

            out.println();
        }

        // Print the column titles
        out.print("  + ");
        for (int coltitle = 0; coltitle < size; coltitle++) {
            out.print(String.format("%3c", (char)((int)(GameBoard.BOARD_START_COLUMN) + coltitle)));
        }
        out.println();
        out.println();
    }

    public int convertColumnNameToNumber(String colName) throws NumberFormatException {
    	
        if (colName.length() != 1) {
        	throw new NumberFormatException();
        }
        
        char symbol = colName.charAt(0);
        if (symbol < GameBoard.BOARD_START_COLUMN || symbol > GameBoard.BOARD_LAST_COLUMN) {
        	throw new NumberFormatException();
        }
        	
    	return (symbol - GameBoard.BOARD_START_COLUMN + 1);
    }

    public char convertColumnNumberToName(int colNumber) throws NumberFormatException
    {
        if (colNumber < 0 || colNumber > GameBoard.BOARD_LAST_COLUMN - GameBoard.BOARD_START_COLUMN + 1) {
        	throw new NumberFormatException();
        }
    	return (char)(GameBoard.BOARD_START_COLUMN + colNumber - 1);
    }
    
    public Point readMove(String playerName, char playerMark) {

        Point move = null;
        boolean isGoodInput = false;

    	String input = readString(String.format("%s Player ('%s') move (row and col. ie: 1a) or '%s' to quit: ", playerName, playerMark, QUIT_STRING));
        while (!isGoodInput && input.compareToIgnoreCase(QUIT_STRING) != 0)
        {
            input = input.toUpperCase();
            String colString = null;
            String rowString = null;

            // Check if the user gave a column placement
            // (Split the input after the numbers and check if theres anything)
            String[] numberSplit = input.split("(\\d)+");
            String[] literalSplit = input.split("[A-Z]");
            if ((numberSplit.length == 2) && (literalSplit.length == 1))
            {
                colString = numberSplit[1];
                rowString = literalSplit[0];
                isGoodInput = true;
            }

            // Check if the input was really a row number and a column name
            try
            {
        	int rowNumber = Integer.parseInt(rowString);
        	int colNumber = convertColumnNameToNumber(colString);

        	move = new Point ( colNumber, rowNumber);
                isGoodInput = true;

            }
            catch (NumberFormatException e)
            {
                isGoodInput = false;
                move = null;
            }

            if (!isGoodInput)
            {
                input = readString("Invalid input, please enter again (row and col. ie: 1a): ");
            }

            System.out.println("[DEBUG] recvd: "+ move.x + " " + move.y );
        }

        return move;

    }
    
    private String readString() {
    	return readString(null);
    }
    private String readString(String prompt) {
    	
    	if (prompt != null) {
    		outputMessage(prompt);
    	}
    	
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    	String inputLine = null;
    	
    	try {
    		inputLine = in.readLine();
    		
    	} catch (IOException e) {
    		return null;
    	}
    	
    	return inputLine;
    }
    
    public void outputMessage(String msg) {
    	System.out.println(msg);
    }
    
    public void showOccupiedMoveMessage() {
    	outputMessage("The position you've entered is occupied. Please Try again. ");
    }

    public void showIllegalMoveMessage() {
        outputMessage("The position you've entered is outside of the board. Please Try again. ");
    }


    public void showNutralGameOver()
    {
        outputMessage("Game is over, no victory! The game board is full.");
    }

    public void showVictoryGameOver(String playerTitle, Point lastMove)
    {
        outputMessage(String.format("Congratulations! %s is the winner!", playerTitle));
        outputMessage(String.format("Victory move was (%d, %c) placement", lastMove.y, convertColumnNumberToName(lastMove.x)));
    }

}
