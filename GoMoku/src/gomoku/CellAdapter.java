/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Point;

public class CellAdapter extends MouseAdapter {
    private GoMokuEvents handler = null;
    private Point location = null;
    
    public CellAdapter(GoMokuEvents handler, Point loc) {
        this.handler = handler;
        location = loc;
    }
    
    @Override
    public void mouseClicked (MouseEvent e) {
        if (handler!=null) {
            handler.makeMove(location);
        }
    }
}
