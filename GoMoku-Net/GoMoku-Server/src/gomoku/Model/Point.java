/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku.Model;


/**
 *
 * @author Michael
 */
public class Point  {
    public int x,y;
    public Point(Point p) {
        x = p.x;
        y = p.y;
    }
    
    public Point(int xCord, int yCord) {
        x = xCord;
        y = yCord;
    }
    
}
