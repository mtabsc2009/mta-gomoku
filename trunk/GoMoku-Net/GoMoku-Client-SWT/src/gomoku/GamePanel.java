/*
 * GamePanel.java
 *
 * Created on 20 ספטמבר 2009, 18:35
 */

package gomoku;

import com.sun.org.apache.bcel.internal.generic.ICONST;
import gomoku.NetworkAdapter.GoMokuGameLogic;
import java.awt.Point;
import java.awt.Color;
import javax.swing.JOptionPane;
import gomoku.Model.GoMokuGameType;
import java.awt.TrayIcon.MessageType;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.swing.SwingUtilities;
import javax.swing.plaf.IconUIResource;
import org.jdesktop.application.Action;
        

public class GamePanel extends javax.swing.JPanel 
        implements GoMokuActionListener, Serializable, Runnable {

    final private static char  BOARD_START_COLUMN = 'A';
    final private static char  BOARD_LAST_COLUMN = 'Z';
    LinkedList<GoMokuActionListener> gameActionsListeners;
    private GoMokuGameLogic game;
    
    /** Creates new form GamePanel */
    public GamePanel() {
        initComponents();
        gameActionsListeners = new LinkedList<GoMokuActionListener>();

        try
        {
            playerName.setText(System.getProperty ( "user.name" ));
            playerTitle.setVisible(true);
        }
        catch (Exception e)
        {
        }

        gameBoardView.initBoard(gameActionsListeners);
    }

    public void initBoard(GoMokuGameLogic game)
    {
        gameBoardView.initBoard(gameActionsListeners);
    }

      public void registerMoveHandler(GoMokuActionListener listener) {
        gameActionsListeners.add(listener);
    }

    
/*
 * Private helper functions -
 *      convertStringToMove,
 *      convertColumnNameToNumber,
 *      convertStringToGameType
 * 
*/      
private  Point convertStringToMove(String input)
    {
        Point move = null;

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
        }

        // Check if the input was really a row number and a column name
        try
        {
            int rowNumber = Integer.parseInt(rowString);
            int colNumber = convertColumnNameToNumber(colString);
            move = new Point ( colNumber, rowNumber);
        }
        catch (NumberFormatException e)
        {
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
        if (symbol < BOARD_START_COLUMN  || symbol > BOARD_LAST_COLUMN)
        {
            throw new NumberFormatException();
        }

        return (symbol - BOARD_START_COLUMN + 1);
    }

    private GoMokuGameType convertStringToGameType(String str) {
        GoMokuGameType gameType;
        if (GoMokuGameType.UserVSComputer.name().equals(str))
                gameType = GoMokuGameType.UserVSComputer;
        else if (GoMokuGameType.ComputerVSUser.name().equals(str))
            gameType = GoMokuGameType.ComputerVSUser;
        else
            gameType = GoMokuGameType.UserVSUser;
        return gameType;
    }    
    
    /**
     *  End of private helper functions 
     */ 
    
    
    public void updateGameView()
    {
        this.gameBoardView.updateBoardView(game);

        Color player = game.getCurrPlayer().getName().compareTo("White") == 0 ? Color.white : Color.black;
        this.currentPlayer.setBackground(player);
    }

    public void makeMove(Point location) {

        if (!makeMoveButton.isEnabled())
        {
            return;
        }

        try
        {
            disableGame();
            this.updateUI();
            game.makeMove(location);

            // If the move ended the game
            updateGameStat();

            // update GUI
            updateGameView();

            if (!game.isGameOver())
            {
                new Thread(this).start();
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void updateGameStat()
    {
        if (game.isGameOver())
        {
            disableGame();
            // Not victory attribute and winner if not tie
            boolean victoryAchieved = game.getVictoryAchieved();
            if (victoryAchieved)
            {
                String winnerName = game.getWinner().getName();
                Color winnerColor = winnerName.compareTo("White") == 0 ? Color.white : Color.black;
                currentPlayer.setBackground(winnerColor);
                gameOverText.setText(winnerName + " Wins!");
            }
            else
            {
                gameOverText.setText("Game over with a tie!");
            }
            gameOverText.setVisible(true);
            currentPlayerText.setVisible(false);
            JOptionPane.showMessageDialog(null, gameOverText.getText(), "Game over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void run()
    {
        try
        {
            game.waitForMove();
            SwingUtilities.invokeAndWait(
                new Runnable()
                {
                    public void run()
                    {
                        updateGameStat();
                        updateGameView();

                        if (!game.isGameOver())
                        {
                            enableGame();
                        }
                    }
                }
            );
        }
        catch (Exception ex)
        {
        }
    }

    private void disableGame()
    {
        makeMoveButton.setEnabled(false);
//        jButton1.setEnabled(true);
        makeMoveButton.updateUI();
    }

    private void enableGame()
    {
        makeMoveButton.setEnabled(true);
//        jButton1.setEnabled(false);
        makeMoveButton.updateUI();
    }

    public void newGame(GoMokuGameType type)
    {
        currentPlayerText.setText("Current Player:");
        currentPlayerText.setVisible(true);
        gameOverText.setVisible(false);

        disableGame();

        this.gameBoardView.resetBoard();
        boolean bGotGame = false;
        try
        {
            // Terminate the old game it existed
            if (game != null)
            {
                game.Terminate();
            }
            
            game = new GoMokuGameLogic(type, playerName.getText());
            String players = game.getAvailablePlayers();

            // If there are no players - wailt for players
            if (players.compareToIgnoreCase(GoMokuGameLogic.PROTOCOL_NO_CLIENTS) == 0)
            {
                JOptionPane.showMessageDialog(this, "There are no players in the server, waiting for players..", "GoMoku Game", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                // Chose an oponent
                String oponent = choseOponent(players);
                if (!oponent.isEmpty())
                {
                    bGotGame = game.choseOponent(oponent);
                }
            }

            // I am the initiator - I get to play first
            if (bGotGame)
            {
//                JOptionPane.showMessageDialog(null, "Got game");
                enableGame();
                game.waitForMove();
            }
            // Waiting for someone to start a game with me
            else
            {
                String oponent = game.waitForOponent();
//                JOptionPane.showMessageDialog(null, "Got game from " + oponent);

                game.waitForMove();
                enableGame();
            }
            updateGameView();
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Cannot connect to server", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }

        
    
//        ListIterator<GoMokuActionListener> itr = gameActionsListeners.listIterator();
//        while (itr.hasNext()) {
//            GoMokuActionListener l =itr.next();
//            if (l != this) {
//                l.newGame(type);
//            }
//        }
        
    }

    private String choseOponent(String players)
    {
            ChooseOponentDialog dialog = new ChooseOponentDialog(GoMokuAppView.topFrame , true);
            Point thisLocation;
            try
            {
                thisLocation = this.getLocationOnScreen();
            }
            catch (Exception e) { }
            thisLocation = new Point(400,400);
            int newX = thisLocation.x + this.getWidth()/2 - dialog.getWidth()/2;
            int newY = thisLocation.y + this.getHeight()/2 - dialog.getHeight()/2;
            dialog.setLocation(new Point(newX, newY));
            dialog.setPlayers(players);
            dialog.setVisible(true);
            return dialog.getOponent();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        gameBoardView = new gomoku.GameBoardView();
        jLabel2 = new javax.swing.JLabel();
        moveLocationString = new javax.swing.JTextField();
        makeMoveButton = new javax.swing.JButton();
        currentPlayer = new javax.swing.JPanel();
        currentPlayerText = new javax.swing.JTextField();
        gameOverText = new javax.swing.JTextField();
        playerName = new javax.swing.JTextField();
        playerTitle = new javax.swing.JTextField();

        setName("Form"); // NOI18N

        jLabel1.setName("jLabel1"); // NOI18N

        gameBoardView.setName("gameBoardView"); // NOI18N
        gameBoardView.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout gameBoardViewLayout = new javax.swing.GroupLayout(gameBoardView);
        gameBoardView.setLayout(gameBoardViewLayout);
        gameBoardViewLayout.setHorizontalGroup(
            gameBoardViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 486, Short.MAX_VALUE)
        );
        gameBoardViewLayout.setVerticalGroup(
            gameBoardViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        jLabel2.setName("jLabel2"); // NOI18N

        moveLocationString.setName("moveLocationString"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gomoku.GoMokuApp.class).getContext().getResourceMap(GamePanel.class);
        makeMoveButton.setText(resourceMap.getString("makeMoveButton.text")); // NOI18N
        makeMoveButton.setName("makeMoveButton"); // NOI18N
        makeMoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeMoveButtonActionPerformed(evt);
            }
        });

        currentPlayer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        currentPlayer.setName("currentPlayer"); // NOI18N

        javax.swing.GroupLayout currentPlayerLayout = new javax.swing.GroupLayout(currentPlayer);
        currentPlayer.setLayout(currentPlayerLayout);
        currentPlayerLayout.setHorizontalGroup(
            currentPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        currentPlayerLayout.setVerticalGroup(
            currentPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        currentPlayerText.setBackground(resourceMap.getColor("currentPlayerText.background")); // NOI18N
        currentPlayerText.setEditable(false);
        currentPlayerText.setText(resourceMap.getString("currentPlayerText.text")); // NOI18N
        currentPlayerText.setBorder(null);
        currentPlayerText.setName("currentPlayerText"); // NOI18N

        gameOverText.setEditable(false);
        gameOverText.setText(resourceMap.getString("gameOverText.text")); // NOI18N
        gameOverText.setBorder(null);
        gameOverText.setName("gameOverText"); // NOI18N

        playerName.setText(resourceMap.getString("playerName.text")); // NOI18N
        playerName.setName("playerName"); // NOI18N

        playerTitle.setBackground(resourceMap.getColor("playerTitle.background")); // NOI18N
        playerTitle.setEditable(false);
        playerTitle.setText(resourceMap.getString("playerTitle.text")); // NOI18N
        playerTitle.setBorder(null);
        playerTitle.setName("playerTitle"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(gameBoardView, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(moveLocationString, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(makeMoveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currentPlayerText, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(currentPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(gameOverText, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(playerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playerName)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gameBoardView, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(makeMoveButton)
                                    .addComponent(moveLocationString, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(currentPlayerText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(currentPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(gameOverText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(playerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(playerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(514, 514, 514)
                        .addComponent(jLabel2)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

 private void makeMoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeMoveButtonActionPerformed
     Point move = convertStringToMove(moveLocationString.getText());
    if (move != null) {
        makeMove(move);
    } else {
        JOptionPane.showMessageDialog(null,"Invalid move was entered.");
    }
}//GEN-LAST:event_makeMoveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel currentPlayer;
    private javax.swing.JTextField currentPlayerText;
    private gomoku.GameBoardView gameBoardView;
    private javax.swing.JTextField gameOverText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton makeMoveButton;
    private javax.swing.JTextField moveLocationString;
    private javax.swing.JTextField playerName;
    private javax.swing.JTextField playerTitle;
    // End of variables declaration//GEN-END:variables

}
