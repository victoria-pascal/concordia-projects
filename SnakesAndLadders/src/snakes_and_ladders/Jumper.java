package snakes_and_ladders;

/**
 * Represents Snakes and Ladders on the board. They have a beginning location 
 * from which the user is transported when they land on it, and an end location 
 * to which the user is thrown.
 * 
 * @author Victoria Pascal;
 */
public class Jumper {
	/**
	 * Represents the location from which the <code>Jumper</code> transports a player.
	 */
	private int beginning;
	/**
	 * Represents the location towards which the <code>Jumper</code> transports a player.
	 */
	private int end;
	
	/**
	 * Creates a <code>Jumper</code> with its respective beginning and end locations.
	 * 
	 * @param beginning location from which the player is rerouted.
	 * @param end location to which the player is rerouted.
	 */
	public Jumper(int beginning, int end) {
		this.beginning = beginning;
		this.end = end;
	}
	
	/**
	 * Returns the location to which the jumper reroutes the player.
	 * 
	 * @return integer representing a board location.
	 */
	public int getJumperEnd() {
		return this.end;
	}
	
	/**
	 * Returns a string representing the type of jumper which can be either a ladder or a snake.
	 * 
	 * @return a string with the value of either <code>ladder</code> or <code>snake</code>.
	 */
	public String getJumperType() {
		return beginning < end ? "ladder" : "snake";
	}
}
