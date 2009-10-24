/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku.NetworkAdapter;

import gomoku.Model.*;
import java.io.IOException;
import j2mehelper.net.*;
/**
 *
 * @author Slim
 */
public class GoMokuGameLogic implements IRemoteGameLogic
{
    public static String GOMOKU_SERVER_ADDRESS = "127.0.0.1";
    public static int GOMOKU_SERVER_PORT = 28800;
    public static String PROTOCOL_CLIENT_SEPARATOR = ",";
    public static String PROTOCOL_NO_CLIENTS = "NONE";

    private Socket m_Socket;
    private GameBoard m_GameBoard;
    private String m_PlayerName;
    private String m_OponentName;
    private String m_PlayersFromServer;

    private boolean m_IsGameOver;
    private boolean m_IsVictoryAcheaved;
    private String  m_WinnerName;
    private String  m_CurrPlayerName;
    
    private GomokuObjectSerializer gomokuSer;

    private final int GAME_BOARD_SIZE = 15;

    
    public GoMokuGameLogic(GoMokuGameType type, String serverIP, int serverPort, String playerName)
            throws IOException, ClassNotFoundException
    {
        GOMOKU_SERVER_ADDRESS = serverIP;
        GOMOKU_SERVER_PORT = serverPort;

        m_GameBoard = new GameBoard(GAME_BOARD_SIZE);
//        m_Player = new HumanPlayer(m_GameBoard, playerName);

        m_IsGameOver = false;
        m_IsVictoryAcheaved = false;
        m_WinnerName = null;
        m_CurrPlayerName = playerName;
        m_PlayerName = playerName;

/*
        try
        {
            Properties clientConfig = new Properties();
            FileInputStream configFile = new FileInputStream("Client.Config");
            clientConfig.load(configFile);
            GOMOKU_SERVER_ADDRESS = clientConfig.get("Server_Address").toString();
            GOMOKU_SERVER_PORT = Integer.parseInt(clientConfig.get("Server_Port").toString());
            PROTOCOL_CLIENT_SEPARATOR = clientConfig.get("Protocol_Client_Separator").toString();
            PROTOCOL_NO_CLIENTS = clientConfig.get("Porocol_No_Clients").toString();
            configFile.close();
        }
        catch (Exception e)
        {
            System.err.println("Config error " + e.toString() + " " + e.getMessage());
        }
*/
        m_Socket = new Socket(GOMOKU_SERVER_ADDRESS, GOMOKU_SERVER_PORT);
        gomokuSer = new GomokuObjectSerializer(m_Socket);
        
        getPlayersFromServer();

        gomokuSer.writeObject(playerName);
        
    }

    public void Terminate()
    {
        try { m_Socket.close(); } catch (Exception ex) { ; }
    }

    public boolean choseOponent(String oponentDetails) throws IOException, ClassNotFoundException
    {
        boolean oponentChosen = false;
        
        // Ofer the oponent
        gomokuSer.writeObject(oponentDetails);

        // Get confirmatiion from the server
        Boolean b = (Boolean)gomokuSer.readObject();
        oponentChosen = b.booleanValue();

        // If confirmed - get the oponent
        if (oponentChosen)
        {
            m_OponentName = (String)gomokuSer.readObject();
        }

        return oponentChosen;
    }

    public String getOponentName()
    {
        return m_OponentName;
    }

    public String waitForOponent() throws IOException, ClassNotFoundException
    {
        // release the client from waiting to an intiaition (the player is not choosing an oponent)
        gomokuSer.writeObject(new String("a"));

        String oponent = ((String)gomokuSer.readObject()).toString();

        // Write a byte to release the server from waiting to close the session
        gomokuSer.writeObject(new Boolean(true));
               
        return oponent;
    }

    public void ConfirmOponent() throws IOException, ClassNotFoundException
    {
        // Confirm the offer
        gomokuSer.writeObject(new Boolean(true));
        

        // Get the oponent
        m_OponentName = (String)gomokuSer.readObject();

        // Confirm player
//        m_outStream.flush();
    }

    public void RefuseOponent() throws IOException
    {
        // Refure the player's offer
        gomokuSer.writeObject(new Boolean(false));
    }

    public String getMyPlayerName()
    {
        return m_PlayerName;
    }


    public void waitForMove() throws IOException, ClassNotFoundException
    {
        readBoard();
    }

    private void getPlayersFromServer() throws IOException, ClassNotFoundException
    {
        m_PlayersFromServer = (String)gomokuSer.readObject();
    }

    public String getCurrPlayerName()
    {
        return m_CurrPlayerName;
    }

    public String getWinnerName()
    {
        return m_WinnerName;
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
        gomokuSer.writeObject(move);

        //readBoard();
    }

    public String getAvailablePlayers() {
        return m_PlayersFromServer;
    }

    private void readBoard() throws IOException, ClassNotFoundException
    {
        readBoardView(); 
        readGameStats();
    }

    private void readBoardView() throws IOException, ClassNotFoundException
    {
        m_GameBoard = null;
        m_GameBoard = (GameBoard)gomokuSer.readObject();
//        Point pawnLocation = (Point)readObjectFromInputStream();
//        m_GameBoard.PlaceBlackPawn(pawnLocation);
        
    }
    
    private void readGameStats() throws IOException, ClassNotFoundException
    {
        // Send the status of the game
        System.out.println("D1");
        m_CurrPlayerName = (String)gomokuSer.readObject();
        System.out.println("D2");
        if (m_OponentName != null && m_OponentName.compareTo(m_CurrPlayerName) != 0)
        {
            m_PlayerName = m_CurrPlayerName;
        }
        System.out.println("D3");
        m_IsGameOver = ((Boolean)gomokuSer.readObject()).booleanValue();
        System.out.println("D4");
        // If the game is over - send the victory
        if (m_IsGameOver)
        {
            m_IsVictoryAcheaved = ((Boolean)gomokuSer.readObject()).booleanValue();

            // If there was a victory - send the winner
            if (m_IsVictoryAcheaved)
            {
                m_WinnerName = (String)gomokuSer.readObject();
            }

            try { readBoardView(); }
            catch (Exception e) { }
        }
    }
    
    
      
}
