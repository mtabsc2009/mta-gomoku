package goMoku.Controller;

import goMoku.Model.GameBoard;
import goMoku.View.IGoMokuView;

import java.awt.Point;

public abstract class Player {

	protected GameBoard m_board;
	protected IGoMokuView m_view;
	protected String m_name;

	// TODO: ctor should receive a IGoMokuView (maybe should be changed to an abstract class) 
	Player(GameBoard board, IGoMokuView view, String name) {
		m_board = board;
		m_view = view;
		m_name = name;
	}
	abstract Point makeMove();

}
