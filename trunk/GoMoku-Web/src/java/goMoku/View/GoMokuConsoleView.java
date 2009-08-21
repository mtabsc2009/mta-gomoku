package goMoku.View;

import java.io.*;

import goMoku.Controller.GoMokuGameType;
import goMoku.Controller.UserAbortException;
import goMoku.Model.*;
import java.awt.Point;

/**
 * 
 * This class is responsible for all the UI (input and output) operations.
 * <br>
 * It implements the IGoMokuView interface for the console UI mode.
 * <br>
 * Exceptions: All input operations within this class might throw UserAbortException 
 * 
 */
public class GoMokuConsoleView implements IGoMokuView {

    // Constants
    protected static final String QUIT_STRING = "q";
    
    public static char BLACK_PLAYER_MARK = 'X';
    public static char WHITE_PLAYER_MARK = '0';
    public static String BLACK_PLAYER_TITLE = "Black";
    public static String WHITE_PLAYER_TITLE = "White";
    private static char EMPTY_MARK = '-';

    // Members
    private String m_blackTitle;
    private String m_whiteTitle;

    // Public print-out and input-read methods
    
    /**
     * Prints the game usage, and the various command line options
     */
    public void showGameUsage()
    {
        outputMessage("Run GoMoku using the following format:");
        outputMessage("    java Main <GameType>:");
        outputMessage("");
        outputMessage("    Where <GameType> can be:");
        outputMessage("      '" + GoMokuGameType.UserVSUser.getCmdline() 		+	"' - User VS User"			);
        outputMessage("      '" + GoMokuGameType.ComputerVSUser.getCmdline() 	+	"' - Computer VS User"		);
        outputMessage("      '" + GoMokuGameType.UserVSComputer.getCmdline() 	+	"' - User VS Computer"		);
        outputMessage("");
        outputMessage("    Example: java Main CC");
    }
    
    /**
     * Prints welcome message
     */
    public void showWelcome() {
        outputMessage("Welcome to GoMoku game!");
        outputMessage("===========================================================");
    }

    /**
     * Prints goodbye message
     */
    public void showGoodbye() {
        outputMessage("Thank you and goodbye.");
    }

    /**
     * Prints game-start message and presenting the players
     */
    public void showStart() {

        outputMessage("");
        outputMessage("New game starting:");
        outputMessage("------------------");

        showPlayers();
    }
    
    /**
     * Prints the players names and marks
     */
    private void showPlayers()
    {
        outputMessage("Players are:");
        outputMessage(String.format("White Player (first): \t%s marked as %s",m_whiteTitle ,WHITE_PLAYER_MARK));
        outputMessage(String.format("Black Player (second): \t%s marked as %s",m_blackTitle ,BLACK_PLAYER_MARK));
    }

    /**
     * Prints the game board directly to stdout
     * @param board the game board that should be printed to screen
     */
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

    /**
     * Prints message indicating that the chosen move is illegal because 
     * the position is occupied. 
     */
    public void showOccupiedMoveMessage() {
    	outputMessage("The position you've entered is occupied. Please Try again. ");
    }

    /**
     * Prints message indicating that the chosen move is illegal because 
     * the position is out of board boundaries. 
     */
    public void showIllegalMoveMessage() {
        outputMessage("The position you've entered is outside of the board. Please Try again. ");
    }

    /**
     * Prints game-over due exhaustion of all moves 
     */
    public void showNutralGameOver() {
        outputMessage("Game is over, no victory! The game board is full.");
    }

    /**
     * Prints victory message
     * @param whiteWon boolean value indicating who is the winner 
     * @param lastMove the winning move 
     */
    public void showVictoryGameOver(boolean whiteWon, Point lastMove) {
        String playerTitle = whiteWon ? WHITE_PLAYER_TITLE : BLACK_PLAYER_TITLE;
        char playerMark = whiteWon ? WHITE_PLAYER_MARK : BLACK_PLAYER_MARK;
        
    	outputMessage(String.format("Congratulations! %s (%c) is the winner!", playerTitle, playerMark));
        outputMessage(String.format("Victory move was (%d, %c) placement", lastMove.y, convertColumnNumberToName(lastMove.x)));
    }
    
    
    /**
     * Prints a custom message. 
     * Most print-methods also use this function.
     * @param msg that will be printed
     */
    public void outputMessage(String msg) {
    	System.out.println(msg);
    }
    
    /**
     * reads board size from input, validates it is within range, and returns it.
     * @param minSize - minimum allowed number
     * @param maxSize - maximum allowed number
     * @return the received number
     * @throws  UserAbortException is user requested to quit 
     */
    public int getBoardSize(int minSize, int maxSize) throws UserAbortException {
    	
        int size = 0;
        String prompt = String.format("Enter board size (%d to %d) or '%s' to quit: ",
                minSize, maxSize, QUIT_STRING);

        
        do {
        	String sizeString = readString(prompt);

            if (sizeString.compareTo(QUIT_STRING) == 0) {
            	throw new UserAbortException("User requested to quit");
            } else {
            	try {
            		size = Integer.parseInt(sizeString);
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

        return size;
    }  
    
    /**
     * reads move from input, validates it, and returns it.
     * @param playerName - name of the player who's about to enter his move
     * @param playerMark - mark of the player who's about to enter his move
     * @return Point which a location of the received move  
     * @throws  UserAbortException is user requested to quit 
     */
    public Point readMove(String playerName, char playerMark) throws UserAbortException{

        Point move = null;
        boolean isGoodInput = false;

    	String input = readString(String.format("%s Player ('%s') move (row and col. ie: 1a) or '%s' to quit: ", playerName, playerMark, QUIT_STRING));
        if (input.compareToIgnoreCase(QUIT_STRING) == 0) {
        	throw new UserAbortException(playerName + " is a looser");
        }
    	
    	while (!isGoodInput)
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

        }

        return move;

    }
    
    /**
     * 
     * @return a line read from the input object
     */
    private String readString() {
    	return readString(null);
    }
    
    /**
     * 
     * @param string which describes the expected input 
     * @return a line read from the input object
     */
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
    
    /**
     * sets the players names for the view object
     */
    public void setPlayersTitle(String blackPlayerTitle, String whitePlayerTitle) {
            m_blackTitle = blackPlayerTitle;
            m_whiteTitle = whitePlayerTitle;
    }

    /**
     * 
     * @param colName is the name (symbol) of the column on the board
     * @return the number of the column
     * @throws NumberFormatException if column name is invalid of is out of range
     */
    protected int convertColumnNameToNumber(String colName) throws NumberFormatException {
    	
        if (colName.length() != 1) {
        	throw new NumberFormatException();
        }
        
        char symbol = colName.charAt(0);
        if (symbol < GameBoard.BOARD_START_COLUMN || symbol > GameBoard.BOARD_LAST_COLUMN) {
        	throw new NumberFormatException();
        }
        	
    	return (symbol - GameBoard.BOARD_START_COLUMN + 1);
    }

    /**
     * 
     * @param colNumber number of the column
     * @return the character symbol on the board for the column 
     * @throws NumberFormatException if colNumber of out of range
     */
    protected char convertColumnNumberToName(int colNumber) throws NumberFormatException
    {
        if (colNumber < 0 || colNumber > GameBoard.BOARD_LAST_COLUMN - GameBoard.BOARD_START_COLUMN + 1) {
        	throw new NumberFormatException();
        }
    	return (char)(GameBoard.BOARD_START_COLUMN + colNumber - 1);
    }

}
