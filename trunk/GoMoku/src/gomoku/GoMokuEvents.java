/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku;

import gomoku.Controller.GoMokuGameType;
import java.awt.Point;

public interface GoMokuEvents {
    public void makeMove(Point location);
    public void newGame(GoMokuGameType type);
}
