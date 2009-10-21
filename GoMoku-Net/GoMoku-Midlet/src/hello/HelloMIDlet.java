/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hello;

import gomoku.Model.GoMokuGameType;
import gomoku.NetworkAdapter.GoMokuGameLogic;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import j2mehelper.net.*;
import java.io.IOException;
import org.netbeans.microedition.lcdui.SplashScreen;

/**
 * @author אלכס וולפמן
 */
public class HelloMIDlet extends MIDlet implements CommandListener {

    private boolean midletPaused = false;
    GoMokuGameLogic game;
    Display display;
    Alert alert = new Alert("","",null,AlertType.ALARM);

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command exitCommand;
    private Command okCommand;
    private Command cancelCommand;
    private Command exitCommand1;
    private LoginForm loginForm;
    private SplashScreen Gomoku;
    private Form gameBoardForm;
    //</editor-fold>//GEN-END:|fields|0|

    /**
     * The HelloMIDlet constructor.
     */
    public HelloMIDlet() {
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
        switchDisplayable(null, getGomoku());//GEN-LINE:|3-startMIDlet|1|3-postAction
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
        if (displayable == Gomoku) {//GEN-BEGIN:|7-commandAction|1|38-preAction
            if (command == SplashScreen.DISMISS_COMMAND) {//GEN-END:|7-commandAction|1|38-preAction
                // write pre-action user code here
                switchDisplayable(null, getLoginForm());//GEN-LINE:|7-commandAction|2|38-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|3|32-preAction
        } else if (displayable == loginForm) {
            if (command == okCommand) {//GEN-END:|7-commandAction|3|32-preAction
                // write pre-action user code here
//GEN-LINE:|7-commandAction|4|32-postAction
                // write post-action user code here
                startGame();
            }//GEN-BEGIN:|7-commandAction|5|7-postCommandAction
        }//GEN-END:|7-commandAction|5|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|6|
    //</editor-fold>//GEN-END:|7-commandAction|6|




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





    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: loginForm ">//GEN-BEGIN:|28-getter|0|28-preInit
    /**
     * Returns an initiliazed instance of loginForm component.
     * @return the initialized component instance
     */
    public LoginForm getLoginForm() {
        if (loginForm == null) {//GEN-END:|28-getter|0|28-preInit
            // write pre-init user code here
            loginForm = new LoginForm();//GEN-BEGIN:|28-getter|1|28-postInit
            loginForm.setTitle("loginForm");
            loginForm.addCommand(getOkCommand());
            loginForm.setCommandListener(this);//GEN-END:|28-getter|1|28-postInit
            // write post-init user code here
        }//GEN-BEGIN:|28-getter|2|
        return loginForm;
    }
    //</editor-fold>//GEN-END:|28-getter|2|

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

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: Gomoku ">//GEN-BEGIN:|36-getter|0|36-preInit
    /**
     * Returns an initiliazed instance of Gomoku component.
     * @return the initialized component instance
     */
    public SplashScreen getGomoku() {
        if (Gomoku == null) {//GEN-END:|36-getter|0|36-preInit
            // write pre-init user code here
            Gomoku = new SplashScreen(getDisplay());//GEN-BEGIN:|36-getter|1|36-postInit
            Gomoku.setTitle("Gomoku");
            Gomoku.setCommandListener(this);
            Gomoku.setText("Loading\nGomoku \n...");
            Gomoku.setTimeout(3000);//GEN-END:|36-getter|1|36-postInit
            // write post-init user code here
        }//GEN-BEGIN:|36-getter|2|
        return Gomoku;
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

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: gameBoardForm ">//GEN-BEGIN:|52-getter|0|52-preInit
    /**
     * Returns an initiliazed instance of gameBoardForm component.
     * @return the initialized component instance
     */
    public Form getGameBoardForm() {
        if (gameBoardForm == null) {//GEN-END:|52-getter|0|52-preInit
            // write pre-init user code here
            gameBoardForm = new Form("Game Board");//GEN-LINE:|52-getter|1|52-postInit
            // write post-init user code here
        }//GEN-BEGIN:|52-getter|2|
        return gameBoardForm;
    }
    //</editor-fold>//GEN-END:|52-getter|2|



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

    private void showAlert(String title, String message) {
        alert.setString(message);
        alert.setTitle(title);
        alert.setTimeout(2000);
        Display.getDisplay(this).setCurrent(alert);
    }
    private void showAlert(String message) {
        showAlert("",message);
    }

    void startGame() {
        try {
            showAlert("Connecting...");
            game = new GoMokuGameLogic(GoMokuGameType.UserVSUser,
                        loginForm.getServerIP(),
                        loginForm.getServerPort(),
                        loginForm.getNickname());

            //display.setCurrent(getGameBoardForm());
            // Get the available players from the server
            String players = game.getAvailablePlayers();

            // If there are no players - wailt for players
            if (players.compareTo(GoMokuGameLogic.PROTOCOL_NO_CLIENTS) == 0)
            {
                //gameStatText.setText("Waiting for players");
                //gameStatText.setVisible(true);
                //if (type == GoMokuGameType.UserVSComputer)
                //{
                    System.out.println(players);
                     showAlert("There are no players in the server. ",
                             "You can wait for a game invitation from another player or start a new game");

                //}
                //waitForGame();
            }
            // There are players - let the user chose
            else
            {
                // Chose an oponent
                chooseOponent(players);
            }

        } catch(Exception e) {
            showAlert("Cannot connect to server");
        }

        game.Terminate();
    }

    private void chooseOponent(String players) throws IOException, ClassNotFoundException    {
        boolean bGotGame = false;
        //String oponent = getOponentFromPlayer(players);
        showAlert(players);
    }

/*
    private void waitForGame()
    {
        try
        {
            // Update GUI
            //gameStatText.setText("Waiting for players");
            //gameStatText.setVisible(true);
            //disableGame();
            //final GamePanel thisPanel = this;
            new Thread(new Runnable() {
            public void run()
            {
                try
                {
                    int response = JOptionPane.NO_OPTION;

                    while (response != JOptionPane.YES_OPTION)
                    {

                        // Wait for the player
                        String oponent = game.waitForOponent();
                        String oponentName = oponent;

                        // When got an oponent
                        // Confirm with the user
                        response = JOptionPane.showConfirmDialog(
                                thisPanel,
                                "You got a game offer from: " + oponentName + " !\n"+
                                "Do you want to play GoMoku?",
                                "GoMoku Offer!",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);

                        // If the user cofirmed
                         if (response == JOptionPane.YES_OPTION)
                         {
                            game.ConfirmOponent();

                            GoMokuAppView.topFrame.setTitle(
                                    String.format("Gomoku Game: %s (White) VS %s (Black)",
                                    oponentName, playerName.getText()));

                            // Wait for a move to be made by the oponent
                            gameStatText.setText("Wait for  move");
                            gameStatText.setVisible(true);
                            new Thread(thisPanel).start();
                         }
                         else
                         {
                            game.RefuseOponent();
                            newGame(GoMokuGameType.UserVSUser);
                         }
                    }
                }
                catch (IOException ioe)
                {
                }
                catch (Exception e)
                {
                    showAlert("Unknown error occured " + e.getMessage());
                }
            }
        }).start();
        }
        catch (Exception e)
        {
            showAlert("Unknown error occured " + e.getMessage());
        }
    }
   */
}
