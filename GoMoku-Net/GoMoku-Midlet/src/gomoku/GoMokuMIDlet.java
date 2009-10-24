/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku;

import gomoku.Model.GoMokuGameType;
import gomoku.Model.Point;
import gomoku.NetworkAdapter.GoMokuGameLogic;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.io.IOException;
import org.netbeans.microedition.lcdui.SplashScreen;



public class GoMokuMIDlet extends MIDlet implements CommandListener {

    private boolean midletPaused = false;
    private GoMokuGameLogic game;
    private Display display;
    private Alert alert = new Alert("Error");
    private int ALERT_TIMEOUT = 3000;
    String players;
    boolean gameSelected;
    BoardCanvas boardCanvas = new BoardCanvas(this);
    
    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command loginOkCommand;
    private Command startNewGameCommand;
    private Command exitCommand;
    private Command okCommand;
    private Command exitCommand1;
    private Command cancelCommand;
    private Command waitCommand;
    private Command newGameComannd;
    private Command chooseGameOkCommand;
    private ChooseGameForm chooseGameForm;
    private SplashScreen GomokuSplashScreen;
    private LoginForm loginForm;
    //</editor-fold>//GEN-END:|fields|0|

    /**
     * The GoMokuMIDlet constructor.
     */
    public GoMokuMIDlet() {
        display = Display.getDisplay(this);
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        switchDisplayable(null, getGomokuSplashScreen());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        if (displayable == GomokuSplashScreen) {//GEN-BEGIN:|7-commandAction|1|38-preAction
            if (command == SplashScreen.DISMISS_COMMAND) {//GEN-END:|7-commandAction|1|38-preAction
                // write pre-action user code here
                switchDisplayable(null, getLoginForm());//GEN-LINE:|7-commandAction|2|38-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|3|77-preAction
        } else if (displayable == chooseGameForm) {
            if (command == chooseGameOkCommand) {//GEN-END:|7-commandAction|3|77-preAction
                if (chooseGameForm.getOpponent()!=null) {
                    gameSelected = true;
                }
//GEN-LINE:|7-commandAction|4|77-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|65-preAction
        } else if (displayable == loginForm) {
            if (command == loginOkCommand) {//GEN-END:|7-commandAction|5|65-preAction
                // write pre-action user code here
//GEN-LINE:|7-commandAction|6|65-postAction
                // write post-action user code here
                new Thread(new Runnable() {
                    public void run() {
                        startGame();
                    }
                }).start();
            }//GEN-BEGIN:|7-commandAction|7|7-postCommandAction
        }//GEN-END:|7-commandAction|7|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|8|
    //</editor-fold>//GEN-END:|7-commandAction|8|




    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
            exitCommand = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|18-getter|1|18-postInit
            // write post-init user code here
        }//GEN-BEGIN:|18-getter|2|
        return exitCommand;
    }
    //</editor-fold>//GEN-END:|18-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand ">//GEN-BEGIN:|31-getter|0|31-preInit
    /**
     * Returns an initiliazed instance of okCommand component.
     * @return the initialized component instance
     */
    public Command getOkCommand() {
        if (okCommand == null) {//GEN-END:|31-getter|0|31-preInit
            // write pre-init user code here
            okCommand = new Command("Ok", Command.OK, 1);//GEN-LINE:|31-getter|1|31-postInit
            // write post-init user code here
        }//GEN-BEGIN:|31-getter|2|
        return okCommand;
    }
    //</editor-fold>//GEN-END:|31-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: GomokuSplashScreen ">//GEN-BEGIN:|36-getter|0|36-preInit
    /**
     * Returns an initiliazed instance of GomokuSplashScreen component.
     * @return the initialized component instance
     */
    public SplashScreen getGomokuSplashScreen() {
        if (GomokuSplashScreen == null) {//GEN-END:|36-getter|0|36-preInit
            // write pre-init user code here
            GomokuSplashScreen = new SplashScreen(getDisplay());//GEN-BEGIN:|36-getter|1|36-postInit
            GomokuSplashScreen.setTitle("Gomoku");
            GomokuSplashScreen.setCommandListener(this);
            GomokuSplashScreen.setText("Loading   Gomoku   ...");
            GomokuSplashScreen.setTimeout(3000);//GEN-END:|36-getter|1|36-postInit
            // write post-init user code here
        }//GEN-BEGIN:|36-getter|2|
        return GomokuSplashScreen;
    }
    //</editor-fold>//GEN-END:|36-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand ">//GEN-BEGIN:|41-getter|0|41-preInit
    /**
     * Returns an initiliazed instance of cancelCommand component.
     * @return the initialized component instance
     */
    public Command getCancelCommand() {
        if (cancelCommand == null) {//GEN-END:|41-getter|0|41-preInit
            // write pre-init user code here
            cancelCommand = new Command("Cancel", Command.CANCEL, 0);//GEN-LINE:|41-getter|1|41-postInit
            // write post-init user code here
        }//GEN-BEGIN:|41-getter|2|
        return cancelCommand;
    }
    //</editor-fold>//GEN-END:|41-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand1 ">//GEN-BEGIN:|43-getter|0|43-preInit
    /**
     * Returns an initiliazed instance of exitCommand1 component.
     * @return the initialized component instance
     */
    public Command getExitCommand1() {
        if (exitCommand1 == null) {//GEN-END:|43-getter|0|43-preInit
            // write pre-init user code here
            exitCommand1 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|43-getter|1|43-postInit
            // write post-init user code here
        }//GEN-BEGIN:|43-getter|2|
        return exitCommand1;
    }
    //</editor-fold>//GEN-END:|43-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: newGameComannd ">//GEN-BEGIN:|56-getter|0|56-preInit
    /**
     * Returns an initiliazed instance of newGameComannd component.
     * @return the initialized component instance
     */
    public Command getNewGameComannd() {
        if (newGameComannd == null) {//GEN-END:|56-getter|0|56-preInit
            // write pre-init user code here
            newGameComannd = new Command("New Game", Command.OK, 0);//GEN-LINE:|56-getter|1|56-postInit
            // write post-init user code here
        }//GEN-BEGIN:|56-getter|2|
        return newGameComannd;
    }
    //</editor-fold>//GEN-END:|56-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: waitCommand ">//GEN-BEGIN:|58-getter|0|58-preInit
    /**
     * Returns an initiliazed instance of waitCommand component.
     * @return the initialized component instance
     */
    public Command getWaitCommand() {
        if (waitCommand == null) {//GEN-END:|58-getter|0|58-preInit
            // write pre-init user code here
            waitCommand = new Command("Wait", Command.BACK, 0);//GEN-LINE:|58-getter|1|58-postInit
            // write post-init user code here
        }//GEN-BEGIN:|58-getter|2|
        return waitCommand;
    }
    //</editor-fold>//GEN-END:|58-getter|2|









    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: loginOkCommand ">//GEN-BEGIN:|64-getter|0|64-preInit
    /**
     * Returns an initiliazed instance of loginOkCommand component.
     * @return the initialized component instance
     */
    public Command getLoginOkCommand() {
        if (loginOkCommand == null) {//GEN-END:|64-getter|0|64-preInit
            // write pre-init user code here
            loginOkCommand = new Command("Connect", Command.OK, 0);//GEN-LINE:|64-getter|1|64-postInit
            // write post-init user code here
        }//GEN-BEGIN:|64-getter|2|
        return loginOkCommand;
    }
    //</editor-fold>//GEN-END:|64-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: loginForm ">//GEN-BEGIN:|63-getter|0|63-preInit
    /**
     * Returns an initiliazed instance of loginForm component.
     * @return the initialized component instance
     */
    public LoginForm getLoginForm() {
        if (loginForm == null) {//GEN-END:|63-getter|0|63-preInit
            // write pre-init user code here
            loginForm = new LoginForm();//GEN-BEGIN:|63-getter|1|63-postInit
            loginForm.setTitle("loginForm");
            loginForm.addCommand(getLoginOkCommand());
            loginForm.setCommandListener(this);//GEN-END:|63-getter|1|63-postInit
            // write post-init user code here
        }//GEN-BEGIN:|63-getter|2|
        return loginForm;
    }
    //</editor-fold>//GEN-END:|63-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: startNewGameCommand ">//GEN-BEGIN:|72-getter|0|72-preInit
    /**
     * Returns an initiliazed instance of startNewGameCommand component.
     * @return the initialized component instance
     */
    public Command getStartNewGameCommand() {
        if (startNewGameCommand == null) {//GEN-END:|72-getter|0|72-preInit
            // write pre-init user code here
            startNewGameCommand = new Command("Start new game", Command.OK, 0);//GEN-LINE:|72-getter|1|72-postInit
            // write post-init user code here
        }//GEN-BEGIN:|72-getter|2|
        return startNewGameCommand;
    }
    //</editor-fold>//GEN-END:|72-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: chooseGameForm ">//GEN-BEGIN:|69-getter|0|69-preInit
    /**
     * Returns an initiliazed instance of chooseGameForm component.
     * @return the initialized component instance
     */
    public ChooseGameForm getChooseGameForm() {
        if (chooseGameForm == null) {//GEN-END:|69-getter|0|69-preInit
            // write pre-init user code here
            chooseGameForm = new ChooseGameForm(players);
            chooseGameForm.addCommand(getChooseGameOkCommand());
            chooseGameForm.setCommandListener(this);
//GEN-LINE:|69-getter|1|69-postInit
            // write post-init user code here
        }//GEN-BEGIN:|69-getter|2|
        return chooseGameForm;
    }
    //</editor-fold>//GEN-END:|69-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: chooseGameOkCommand ">//GEN-BEGIN:|76-getter|0|76-preInit
    /**
     * Returns an initiliazed instance of chooseGameOkCommand component.
     * @return the initialized component instance
     */
    public Command getChooseGameOkCommand() {
        if (chooseGameOkCommand == null) {//GEN-END:|76-getter|0|76-preInit
            // write pre-init user code here
            chooseGameOkCommand = new Command("Ok", Command.OK, 0);//GEN-LINE:|76-getter|1|76-postInit
            // write post-init user code here
        }//GEN-BEGIN:|76-getter|2|
        return chooseGameOkCommand;
    }
    //</editor-fold>//GEN-END:|76-getter|2|



    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }

    /**
     * showAlert will display an alert message for ALERT_TIMEOUT seconds. 
     * @param title
     * @param message
     */
    private  void showAlert(String title, String message) {
        alert.setString(message);
        alert.setTimeout(ALERT_TIMEOUT);
        alert.setType(AlertType.ERROR);
        display.setCurrent(alert);
        debugPrint("showAlert: "+message);
    }
    private void showAlert(String message) {
        showAlert("",message);
    }

    private void startGame() {
        if (game != null) {
            debugPrint("startGame: game already started");
            return;
        }
            
        try {    
            game = new GoMokuGameLogic(GoMokuGameType.UserVSUser,
                        loginForm.getServerIP(),
                        loginForm.getServerPort(),
                        loginForm.getNickname());

            players = game.getAvailablePlayers();
            gameSelected = false;
            display.setCurrent(getChooseGameForm());
            //display.setCurrent(getGameBoardForm());
            // Get the available players from the server
            
            debugPrint("startGame: players list: " + players);
           
            if (players.equals(GoMokuGameLogic.PROTOCOL_NO_CLIENTS)) {
                String oponent = game.waitForOponent();
                debugPrint("received request from "+oponent);
                game.ConfirmOponent();
            } else {
                boolean accepted = false;
                while (accepted == false) {
                    while(gameSelected==false) {
                        try {
                            Thread.sleep(200);
                        } catch(Exception e) {}
                    }
                    String oponent = chooseGameForm.getOpponent();
                    accepted  = game.choseOponent(oponent); 
                    gameSelected = false;
                    }
                }
            // game is an active game now
            // display game canvas
            display.setCurrent(boardCanvas);
            
            while(game.isGameOver() == false)  {
                debugPrint("move#");
                game.waitForMove();
                debugPrint("pawns count: !"+game.getGameBoard().getPawnsCount());
                boardCanvas.updateBoard(game.getGameBoard());
            }
            
        } // end of try block
        catch(IOException e) {
            showAlert("Cannot connect to server. ");
        }
        catch(Exception e) {
            debugPrint("startGame: Exception: "+e.toString() + "  " + e.getMessage());
        }
        
        if (game != null) {
            debugPrint("game terminated!!");
            game.Terminate();
            game = null;
            
        }
        
    }
    
    void makeMove(Point move) {
        try {
        game.makeMove(move);
        } catch(Exception e) {
            debugPrint("EXCEPTION in makeMove: "+e.toString() + "  "+ e.getMessage());
        }
    }   

    private void chooseOponent(String players) throws IOException, ClassNotFoundException    {
        boolean bGotGame = false;
        //String oponent = getOponentFromPlayer(players);
        showAlert(players);
    }

    
    void debugPrint(String msg) {
        System.out.println(msg);
    }
}
