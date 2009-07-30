package goMoku.Controller;

import goMoku.Model.GameBoard;
import goMoku.View.IGoMokuView;
import java.awt.Point;
import java.util.Random;

public class ComputerPlayer extends Player {
	
	public ComputerPlayer (GameBoard board, IGoMokuView view, String name) {
		super(board, view, name);
	}
	
	public Point makeMove() {
		
		int board_size = m_board.getSize();
		
		Random random_generator = new Random();	// will be used for guessing the next move
		int x,y;
		
		do {
			// nextInt returns number in range 0 to (board_size-1)
			// we need to add one to both coordinates to convert it to the right range  
			x = random_generator.nextInt(board_size) + 1;
			y = random_generator.nextInt(board_size) + 1;
		
		}while (m_board.hasPawn(x, y));
		
		
		return new Point(x,y);
	}
	
}
