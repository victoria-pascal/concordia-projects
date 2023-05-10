// This program is a Snakes and Ladders game for 2 players. 
// The program will first ask for the number of players, but will only allow 2 players per game.
// Then it will decide who will be the first player to start their turn by rolling the dice. 
// The game will print the actions and the board for each turn. 
// Players will pass their turns by pressing 'Enter'.
// The game will continue until one of the players reaches exactly 100.

// ***NOTE***: The application should be run in the standalone terminal!
// On Windows please use the new Windows Terminal for proper visualization.
package snakes_and_ladders;

import java.util.Scanner;

/**
 * Application's root class.
 * 
 * @author Victoria Pascal;
 */
public class PlayLadderAndSnake {
	
	/**
	 * Creates an instance of the <code>PlayLadderAndSnake</code> program.<br>
	 * <em><small>Created so JavaDoc would not complain.</small></em>
	 */
	public PlayLadderAndSnake() { }

	/**
	 * Main method of the application.
	 * 
	 * @param args application parameters.
	 */
	public static void main(String[] args) {
		
		//Welcome message and request for the user input of the number of players.
		System.out.print("Welcome to Victoria's Snakes and Ladders game.\n"
				+ "You are about to start the game.\n"
				+ "Please enter the number of players as an integer: ");
		Scanner in = new Scanner(System.in);
		
		// Validating user input and creating the game once it is valid.
		while(!in.hasNextInt()) {
			System.out.println("Invalid input. Please try again: " );
			in.nextLine();
		}
		LadderAndSnake game = new LadderAndSnake(in.nextInt());
		
		// Starting the game.
		game.play();
		
		// Closing message.
		System.out.print("The program is terminated.");
		
		in.close();
	}

}
