/*
 * GamePanel.java
 *
 * Created on 20 ספטמבר 2009, 18:35
 */

package gomoku;

import gomoku.NetworkAdapter.GoMokuGameLogic;
import java.awt.Point;
import java.awt.Color;
import javax.swing.JOptionPane;
import gomoku.Model.GoMokuGameType;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;
        

public class GamePanel extends javax.swing.JPanel 
        implements GoMokuActionListener, Serializable {

    final private static char  BOARD_START_COLUMN = 'A';
    final private static char  BOARD_LAST_COLUMN = 'Z';
    LinkedList<GoMokuActionListener> gameActionsListeners;
    private GoMokuGameLogic game;
    
    /** Creates new form GamePanel */
    public GamePanel() {
        initComponents();
        gameActionsListeners = new LinkedList<GoMokuActionListener>();   
        gameBoardView.initBoard(gameActionsListeners);
    }

    public void initBoard(GoMokuGameLogic game)
    {
        gameBoardView.initBoard(gameActionsListeners);
    }

      public void registerMoveHandler(GoMokuActionListener listener) {
        gameActionsListeners.add(listener);
    }

    
    public boolean LoadGame(File file)     {  
            ObjectInputStream input = null;
            try {            
                    input = new ObjectInputStream(new FileInputStream(file));        
                    Object savedGame = null;
                    
                    savedGame = input.readObject();
                    input.close();
                    if(savedGame instanceof GoMokuGameLogic) {    
                        this.game = (GoMokuGameLogic)savedGame;
                        this.gameTypeComboBox.setSelectedItem(this.game.getGameType().toString());
                        gameBoardView.resetBoard();
                        gameBoardView.updateBoardView(this.game);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error, Invalid Data.!");  
                        return false;
                    }
            }
                    
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "I/O Error!");
            }
            catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error, Invalid Data.!");  
            }
            
            return  true;
    }

   public boolean SaveGame(File file)   {
    try {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
        output.writeObject((Object)this.game);
        output.close();
        return true;
    }
    catch (IOException e) {
        JOptionPane.showMessageDialog(null, "I/O Error!"+e.getMessage());
        
    }
            
    return false;
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
        game.makeMove(location);

        // If the move ended the game
        if (game.isGameOver())
        {
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

        // update GUI
        updateGameView();
    }
    
    public void newGame(GoMokuGameType type)
    {
        
        currentPlayerText.setText("Current Player:");
        currentPlayerText.setVisible(true);
        gameOverText.setVisible(false);

        this.gameBoardView.resetBoard();
        game = new GoMokuGameLogic(type);
        if (type == type.ComputerVSUser)
        {
            game.makeFirstComputerMove();
        }
        updateGameView();
        
    
        ListIterator<GoMokuActionListener> itr = gameActionsListeners.listIterator();
        while (itr.hasNext()) {
            GoMokuActionListener l =itr.next();
            if (l != this) {
                l.newGame(type);
            }
        }
        
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gameTypeComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        gameBoardView = new gomoku.GameBoardView();
        jLabel2 = new javax.swing.JLabel();
        moveLocationString = new javax.swing.JTextField();
        makeMoveButton = new javax.swing.JButton();
        startNewGameButton = new javax.swing.JButton();
        currentPlayer = new javax.swing.JPanel();
        currentPlayerText = new javax.swing.JTextField();
        gameOverText = new javax.swing.JTextField();

        setName("Form"); // NOI18N

        gameTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UserVSUser", "UserVSComputer", "ComputerVSUser" }));
        gameTypeComboBox.setName("gameTypeComboBox"); // NOI18N

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

        startNewGameButton.setText(resourceMap.getString("startNewGameButton.text")); // NOI18N
        startNewGameButton.setName("startNewGameButton"); // NOI18N
        startNewGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startNewGameButtonActionPerformed(evt);
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
            .addGap(0, 22, Short.MAX_VALUE)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gameBoardView, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(moveLocationString, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(makeMoveButton)
                        .addGap(32, 32, 32)
                        .addComponent(currentPlayerText, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currentPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gameOverText, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(gameTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(startNewGameButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(gameTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startNewGameButton))
                .addGap(18, 18, 18)
                .addComponent(gameBoardView, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(makeMoveButton)
                                .addComponent(moveLocationString, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(currentPlayerText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(currentPlayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(gameOverText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

private void startNewGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startNewGameButtonActionPerformed
    newGame( convertStringToGameType( gameTypeComboBox.getSelectedItem().toString() ) );
    
}//GEN-LAST:event_startNewGameButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel currentPlayer;
    private javax.swing.JTextField currentPlayerText;
    private gomoku.GameBoardView gameBoardView;
    private javax.swing.JTextField gameOverText;
    private javax.swing.JComboBox gameTypeComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton makeMoveButton;
    private javax.swing.JTextField moveLocationString;
    private javax.swing.JButton startNewGameButton;
    // End of variables declaration//GEN-END:variables

}
