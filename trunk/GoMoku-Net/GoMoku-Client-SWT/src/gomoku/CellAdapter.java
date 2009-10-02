/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Point;
import java.util.LinkedList;
import java.util.ListIterator;

public class CellAdapter extends MouseAdapter {
    private LinkedList<GoMokuActionListener> handlerList;
    private Point location = null;
    
    public CellAdapter(LinkedList<GoMokuActionListener> handlerList, Point loc) {
        this.handlerList = handlerList;
        location = loc;
    }
    
    @Override
    public void mouseClicked (MouseEvent e) {
        ListIterator<GoMokuActionListener> itr = handlerList.listIterator();
        while (itr.hasNext()) {
            itr.next().makeMove(location);
        }
    }
}
