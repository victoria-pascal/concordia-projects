package snakes_and_ladders;

/**
 * Represents a player who has a location on the board and a pictogram for their visual representation on the board.
 * 
 * @author Victoria Pascal;
 */
public class Player {
	/**
	 * Holds player's location on the board.
	 * 0 at the beginning, representing the player being outside the board.
	 */
	private int location = 0;
	/**
	 * Holds player's pictogram used to represent them on the board.
	 */
	private String pictogram;
	
	/**
	 * Returns player's pictogram used to represent them on the board.
	 * 
	 * @param pictogram pictogram used to represent player.
	 */
	public Player(String pictogram) {
		this.pictogram = pictogram;
	}
	
	/**
	 * Returns player's location on the board.
	 * Location 0 represents the player being outside the board.
	 * 
	 * @return board location.
	 */
	public int getLocation() {
		return this.location;
	}
	
	/**
	 * Sets player's location on the board.
	 * 
	 * @param new_location board location.
	 */
	void setLocation(int new_location) {
		this.location = new_location;
	}
	
	/**
	 * Returns player's pictogram for its visual representation on the board.
	 * 
	 * @return pictogram.
	 */
	public String getPictogram() {
		return this.pictogram;
	}
}
