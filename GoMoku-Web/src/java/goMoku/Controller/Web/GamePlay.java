package goMoku.Controller.Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import goMoku.Model.*;
import goMoku.Controller.GoMokuGameType;
import goMoku.Controller.GoMokuWebGame;
import goMoku.Controller.UserAbortException;
import goMoku.View.GoMokuConsoleView;
import java.awt.Point;

public class GamePlay extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static final String ATT_GAME = "Game";
    public static final String ATT_GAME_TYPE = "GameType";
    public static final String ATT_MOVE = "move";
    public static final String ATT_NEW_GAME = "newGame";
    public static final String ATT_VICTORY = "victory";
    public static final String ATT_WINNER = "winner";
    private static final goMoku.Controller.GoMokuGameType DEFAULT_GAME_TYPE = GoMokuGameType.UserVSComputer;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try
        {
            HttpSession session = request.getSession();
            GoMokuGameType gameType = DEFAULT_GAME_TYPE;
            GoMokuWebGame gameFlow = null;

            // If there is no game in session
            if (session.getAttribute(ATT_GAME) == null || request.getParameter(ATT_NEW_GAME) != null)
            {
                // Get the default game type
                String gameTypeParameter = request.getParameter(ATT_GAME_TYPE);
                if (gameTypeParameter != null)
                {
                     gameType = parseGameTypeParam(gameTypeParameter);
                }

                // Create a new game
                try
                {
                    gameFlow = new GoMokuWebGame(gameType, new GoMokuConsoleView());
                    if (gameType == gameType.ComputerVSUser)
                    {
                        gameFlow.makeFirstComputerMove();
                    }
                    session.setAttribute(ATT_GAME, gameFlow);
                    session.setAttribute(ATT_VICTORY, null);
                }
                catch (UserAbortException e)
                {
                }
            }
            // A game is going on
            else
            {
                // A new move was given
                gameFlow = (GoMokuWebGame)(session.getAttribute(ATT_GAME));
                if (request.getParameter(ATT_MOVE) != null)
                {
                    Point givenMove = readMove(request.getParameter(ATT_MOVE));
                    if (givenMove != null)
                    {
                        gameFlow.makeMove(givenMove);

                        // If the move ended the game
                        if (gameFlow.isGameOver())
                        {
                            // Not victory attribute and winner if not tie
                            boolean victoryAchieved = gameFlow.getVictoryAchieved();
                            session.setAttribute(ATT_VICTORY, victoryAchieved);
                            if (victoryAchieved)
                            {
                                session.setAttribute(ATT_WINNER, gameFlow.getWinner());
                            }
                        }
                    }
                }
            }
        }
        finally
        {
            response.sendRedirect("GoMokuGame.jsp");
        }
    }

    public static GoMokuGameType parseGameTypeParam(String typeParameter) {

    	/*
    	 * iterate over the game types and compare the user command line
    	 * to game type's command line
    	 */
    	for (GoMokuGameType gameType : GoMokuGameType.values()) {
    		if (gameType.toString().compareTo(typeParameter) == 0) {
    			return gameType;
    		}
    	}

        // the specified command line argument didn't match to any of the game types
        return DEFAULT_GAME_TYPE;

    }

    public Point readMove(String input)
    {
        Point move = null;
        boolean isGoodInput = false;

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

        return move;

    }

    protected int convertColumnNameToNumber(String colName) throws NumberFormatException
    {

        if (colName.length() != 1)
        {
            throw new NumberFormatException();
        }

        char symbol = colName.charAt(0);
        if (symbol < GameBoard.BOARD_START_COLUMN || symbol > GameBoard.BOARD_LAST_COLUMN)
        {
            throw new NumberFormatException();
        }

        return (symbol - GameBoard.BOARD_START_COLUMN + 1);
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
