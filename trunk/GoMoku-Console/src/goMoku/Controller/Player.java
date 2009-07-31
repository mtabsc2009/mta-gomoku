package goMoku.Controller;

import goMoku.Model.GameBoard;
import goMoku.View.IGoMokuView;

import java.awt.Point;

public abstract class Player {

	protected GameBoard m_board;
	protected IGoMokuView m_view;
	protected String m_name;
	protected char m_mark;

	Player(GameBoard board, IGoMokuView view, String name, char mark) {
		m_board = board;
		m_view = view;
		m_name = name;
		m_mark = mark;
	}
	abstract Point makeMove();

    public String getFullPlayerTitle() {
    	return String.format("%s (%c)", m_name, m_mark);
    }

}
