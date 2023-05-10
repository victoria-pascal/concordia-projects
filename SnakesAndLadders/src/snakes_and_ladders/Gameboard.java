package snakes_and_ladders;

/**
 * Represents the board of the game. The layout of the board is predefined.
 * The board consists of a 2D array of <code>Jumper</code> objects. If there is no <code>Jumper</code> (<code>null</code>)
 * at a certain index, the corresponding location does not have a snake or ladder.
 * 
 * @author Victoria Pascal;
 */
public class Gameboard {
	/**
	 * The width (number of columns) of the board.
	 */
	private static final int WIDTH = 10;
	/**
	 * The height (number of rows) of the board.
	 */
	private static final int HEIGHT = 10;
	/**
	 * Holds a 2D array representation of the board consisting of
	 * <code>Jumper</code> objects representing the snakes and ladders. 
	 */
	private Jumper[][] board;
	
	/**
	 * Creates a <code>Gameboard</code> object with a predefined board layout.
	 */
	public Gameboard() {
		board = new Jumper[WIDTH][HEIGHT];		
		
		board[0][0] = new Jumper(1, 38);
		board[0][3] = new Jumper(4, 14);
		board[0][8] = new Jumper(9, 31);
		board[2][7] = new Jumper(28, 84);
		board[2][0] = new Jumper(21, 42);
		board[3][5] = new Jumper(36, 44);
		board[5][0] = new Jumper(51, 67);
		board[7][0] = new Jumper(71, 91);
		board[7][9] = new Jumper(80, 100);
		
		board[1][5] = new Jumper(16, 6);
		board[4][7] = new Jumper(48, 30);
		board[6][3] = new Jumper(64, 60);
		board[7][8] = new Jumper(79, 19);
		board[9][2] = new Jumper(93, 68);
		board[9][4] = new Jumper(95, 24);
		board[9][6] = new Jumper(97, 76);
		board[9][7] = new Jumper(98, 78);
	}
	
	/**
	 * Returns the <code>Jumper</code> object at a given board location.
	 * 
	 * @param location on board from 1 to 100.
	 * @return a <code>Jumper</code> object.
	 */
	public Jumper getJumperAtLocation(int location) {
		return board[getRowFromLocation(location)][getColumnFromLocation(location)];
	}
	
	/**
	 * Calculates the corresponding row index in the <code>board</code> array for a given board location.
	 * 
	 * @param location on board from 1 to 100.
	 * @return row index.
	 */
	private static int getRowFromLocation(int location) {
		int row = (location - 1) / HEIGHT;
		return row;
	}
	
	/**
	 * Calculates the corresponding column index in the <code>board</code> array for a given board location.
	 * 
	 * @param location on board from 1 to 100.
	 * @return column index.
	 */
	private static int getColumnFromLocation(int location) {
		int column = (location - 1) % WIDTH;
		return column;
	}
	
	
	/**
	 * Calculates the end location on the board.
	 * 
	 * @return finish/end location.
	 */
	public int getFinishLocation() {
		return HEIGHT * WIDTH;
	}
}
