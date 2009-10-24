package gomoku;

import gomoku.Model.GameBoard;
import gomoku.Model.PawnType;
import gomoku.Model.Point;
import gov.nist.core.InternalErrorHandler;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;

public class BoardCanvas extends Canvas implements CommandListener {
    GameBoard gameBoard = new GameBoard(15);
    int CELL_SIZE = 15;    
    int COLOR_GREEN = 0x00ff00;
    int COLOR_GREEN2 = 0x00c800;
    int COLOR_WHITE = 0xffffff;
    int COLOR_BLACK = 0x000000;
    int COLOR_GRAY = 0x646464;
    int X_CORD_SKIP_OFFSET = 10;
    int Y_CORD_SKIP_OFFSET = 20;
    GoMokuMIDlet moveAdapter;
    Point cursorPosition = new Point(0, 0);

    public BoardCanvas(GoMokuMIDlet _moveAdapter) {
        moveAdapter = _moveAdapter;
    }
    
    public void updateBoard(GameBoard newBoard) {
        gameBoard = newBoard;
        repaint();
        
    }
    
    protected void paint(Graphics g) {
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        int n = gameBoard.getSize();
        
        int x,y;
        g.setColor(COLOR_WHITE);
        g.fillRect(0, 0, 500, 500);
        
        g.setColor(0);
        g.fillRect(X_CORD_SKIP_OFFSET, Y_CORD_SKIP_OFFSET, n*CELL_SIZE+1, n*CELL_SIZE+1);
        for ( x =0 ; x < n ; ++x)  {
            for( y = 0 ; y < n ; ++y ) {
                if (cursorPosition.x == x && cursorPosition.y == y) {
                    g.setColor(COLOR_GRAY); // cursor color
                } else if (gameBoard.hasPawn(y+1, x+1)) {
                    if (gameBoard.getPawnType(y+1, x+1).getPawnType() == PawnType.WhiteId) {
                        g.setColor(COLOR_WHITE);
                    } else {
                        g.setColor(COLOR_BLACK);
                    }
                } else if (x%2 == 0){
                    g.setColor(COLOR_GREEN);
                }else {
                    g.setColor(COLOR_GREEN2);
                }
                 g.fillRect(X_CORD_SKIP_OFFSET+x*n+1, Y_CORD_SKIP_OFFSET+y*n+1, CELL_SIZE-1, CELL_SIZE-1);
            }
        }
    }

    protected void keyPressed(int keyCode) {
        System.out.println("key pressed!!");
        switch(getGameAction(keyCode)) {
            case RIGHT:
                if (cursorPosition.x < gameBoard.getSize() - 1)
                    cursorPosition.x++;
                    break;
            case LEFT:
                if (cursorPosition.x > 0)
                    cursorPosition.x--;
                break;
            case UP:
                if (cursorPosition.y > 0)
                    cursorPosition.y--;
                break;
            case DOWN:
                if (cursorPosition.y < gameBoard.getSize() - 1)
                    cursorPosition.y++;
                break;
            case FIRE:
                moveAdapter.makeMove(new Point(cursorPosition.x+1, cursorPosition.y+1));
                break;
            default:
//                super.keyPressed(keyCode);
        }
        
        repaint();
        
    }
    
    
   
    public void commandAction(Command arg0, Displayable arg1) {
        System.out.println("!!!!!!!!!!!!!!!!");
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
