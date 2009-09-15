/*
 * GoMokuView.java
 */

package gomoku;

import gomoku.Controller.GoMokuWebGame;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.Point;


import gomoku.Controller.GoMokuGameType;
/**
 * The application's main frame.
 */
public class GoMokuView extends FrameView implements GoMokuEvents {

    // constants
    final private static char  BOARD_START_COLUMN = 'A';
    final private static char  BOARD_LAST_COLUMN = 'Z';
    private GoMokuWebGame game;
    
    public GoMokuView(SingleFrameApplication app) {
        super(app);
        initComponents();
        
        gameBoardView.registerMoveHandler(this);
        gameBoardView.initBoard();
        
        game = new GoMokuWebGame(GoMokuGameType.UserVSUser);
        
    
    }


    public void makeMove(Point location) {
        JOptionPane.showMessageDialog(null,"make move was called");
        
        game.makeMove(location);

        // update GUI

        
    }

    public void newGame(GoMokuGameType type) {
        game = new GoMokuWebGame(type);

    }
    
     private  Point convertStringToMove(String input)
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
        if (symbol < BOARD_START_COLUMN  || symbol > BOARD_LAST_COLUMN)
        {
            throw new NumberFormatException();
        }

        return (symbol - BOARD_START_COLUMN + 1);
    }
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = GoMokuApp.getApplication().getMainFrame();
            aboutBox = new GoMokuAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        GoMokuApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        gameTypeComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        gameBoardView = new gomoku.GameBoardView();
        jLabel2 = new javax.swing.JLabel();
        moveLocationString = new javax.swing.JTextField();
        makeMoveButton = new javax.swing.JButton();
        startNewGameButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N

        gameTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UserVSUser", "UserVSComputer", "ComputerVSUser" }));
        gameTypeComboBox.setName("gameTypeComboBox"); // NOI18N

        jLabel1.setName("jLabel1"); // NOI18N

        gameBoardView.setName("gameBoardView"); // NOI18N
        gameBoardView.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout gameBoardViewLayout = new javax.swing.GroupLayout(gameBoardView);
        gameBoardView.setLayout(gameBoardViewLayout);
        gameBoardViewLayout.setHorizontalGroup(
            gameBoardViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
        );
        gameBoardViewLayout.setVerticalGroup(
            gameBoardViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        jLabel2.setName("jLabel2"); // NOI18N

        moveLocationString.setName("moveLocationString"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gomoku.GoMokuApp.class).getContext().getResourceMap(GoMokuView.class);
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

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gameBoardView, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(gameTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(startNewGameButton))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(moveLocationString, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(makeMoveButton)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(gameTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startNewGameButton))
                .addGap(18, 18, 18)
                .addComponent(gameBoardView, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(makeMoveButton)
                    .addComponent(moveLocationString, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(gomoku.GoMokuApp.class).getContext().getActionMap(GoMokuView.class, this);
        newMenuItem.setAction(actionMap.get("startNewGame")); // NOI18N
        newMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newMenuItem.setText(resourceMap.getString("newMenuItem.text")); // NOI18N
        newMenuItem.setName("newMenuItem"); // NOI18N
        fileMenu.add(newMenuItem);

        openMenuItem.setAction(actionMap.get("openGame")); // NOI18N
        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setText(resourceMap.getString("openMenuItem.text")); // NOI18N
        openMenuItem.setName("openMenuItem"); // NOI18N
        fileMenu.add(openMenuItem);

        saveMenuItem.setAction(actionMap.get("saveGame")); // NOI18N
        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setText(resourceMap.getString("saveMenuItem.text")); // NOI18N
        saveMenuItem.setName("saveMenuItem"); // NOI18N
        fileMenu.add(saveMenuItem);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

private void makeMoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeMoveButtonActionPerformed
    Point move = convertStringToMove(moveLocationString.getText());
    if (move != null) {
        makeMove(move);;
    } else {
        JOptionPane.showMessageDialog(null,"Invalid move was entered.");
    }
}//GEN-LAST:event_makeMoveButtonActionPerformed

private void startNewGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startNewGameButtonActionPerformed
    
    newGame( convertStringToGameType( gameTypeComboBox.getSelectedItem().toString() ) );

}//GEN-LAST:event_startNewGameButtonActionPerformed

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

    @Action
    public void startNewGame() {
        // disable the save option
        saveMenuItem.setEnabled(false);
        startNewGameButtonActionPerformed(null);
        newGame( convertStringToGameType( gameTypeComboBox.getSelectedItem().toString() ) );
    }

    @Action
    public void openGame() {
    
        JFileChooser menu = new JFileChooser();
        menu.setDialogType(JFileChooser.OPEN_DIALOG);
        menu.setDialogTitle("Load Game");

        int res = menu.showOpenDialog(null);
            
        if(res == JFileChooser.APPROVE_OPTION)
        {
                if(menu.getSelectedFile().exists())
                {
                    JOptionPane.showMessageDialog(null,menu.getSelectedFile().getName());
                    
                    
                    // disable the save option
                    saveMenuItem.setEnabled(false);
                    
                    
                } else {
                    JOptionPane.showMessageDialog(null,"Error opening game. File not found.");
                }
        }
    }

    @Action
    public void saveGame() {
        JFileChooser menu = new JFileChooser();
        menu.setDialogType(JFileChooser.SAVE_DIALOG);
        menu.setDialogTitle("Save Game");

        int res = menu.showOpenDialog(null);
            
        if(res == JFileChooser.APPROVE_OPTION)
        {
                if(menu.getSelectedFile().exists())
                {
                    // file exists but we won't overwrite
                    JOptionPane.showMessageDialog(null,"Error saving game. This file already exists.");
                    //menu.getSelectedFile();
                } else {
                    JOptionPane.showMessageDialog(null,menu.getSelectedFile().getName());
                }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gomoku.GameBoardView gameBoardView;
    private javax.swing.JComboBox gameTypeComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton makeMoveButton;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextField moveLocationString;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JButton startNewGameButton;
    // End of variables declaration//GEN-END:variables


    private JDialog aboutBox;
}
