package snakes_and_ladders;

import java.util.Scanner;

/**
 * Represents an instance of the game itself with all its core mechanics.
 * 
 * @author Victoria Pascal;
 */
public class LadderAndSnake {
	/**
	 * Holds the number of players playing the game.
	 */
	private int number_of_players;
	/**
	 * Holds the <code>Gameboard</code> object representing the board and its features.
	 */
	private static Gameboard gameboard = new Gameboard();
	/**
	 * Holds the <code>Player</code> objects. The sequence of players determines the turn sequence as well.
	 */
	private Player[] players;

	/**
	 * Creates an instance of the game with the passed number of players.
	 * Also validates the number of players that was passed. Only exactly 2 players are allowed.
	 * If number passed is lower than 2 the game will terminate. If number is greater it will be reset to 2.
	 * 
	 * @param number_of_players participating in the match.
	 */
	public LadderAndSnake(int number_of_players) {
		if (number_of_players < 2) {
			System.out.println("Error: Cannot execute the game with less than 2 players! Will exit.");
			System.exit(0);
		}
		else if (number_of_players == 2) {
			this.number_of_players = number_of_players;
			System.out.println("The game is played by " + number_of_players + " players.");
		}
		else {
			System.out.println("Initialization was attempted for " + number_of_players + " players; however, this is only "
					+ "expected for an extended version of the game. Value will be set to 2.");
			this.number_of_players = 2;
		}
		
		// The players are created with predefined colored pictograms and stored in the players array.
		players = new Player[this.number_of_players];
		String[] pictograms = {"\u001B[31mA\u001B[0m", "\u001B[32mB\u001B[0m"};
		for (int i = 0; i < this.number_of_players; i++) {
			players[i] = new Player(pictograms[i]);
		} 
	}
	
	/**
	 * Generates a random integer from 1 to 6 representing a dice value.
	 * 
	 * @return integer from 1 to 6.
	 */
	private static int flipDice() {
		int max = 6;
		int min = 1;
		int range = max - min;
		return (int) Math.round((Math.random() * range) + min);
	}
	
	/**
	 * Decides the first player by throwing the dice and picking the player who got the greatest value.
	 * 
	 * @return the index of the player from the <code>players</code> array that will start its turn first.
	 */
	private int getFirstPlayerIndex() {
		int max = 0;
		int index_of_max = -1;
		int attempts_count = 0;
		boolean decided = false;
		int[] dice_values = new int[players.length];
		
		System.out.println("Now deciding which player will start playing.");
		
		// Continues throwing dice until there is no tie.
		while (!decided) {
			// Throwing dice for every player and storing the value in an array.
			for  (int i = 0; i < players.length; i++) {
				dice_values[i] = flipDice();
				System.out.println("Player " + (i + 1) + " (" + players[i].getPictogram() + ") got a dice value of " + dice_values[i]);
			}
			
			boolean max_duplicate = false;
			index_of_max = -1;
			max = 0;
			
			// Comparing and finding the max value among the thrown dice values,
			// and if there were any duplicates of the max value.
			for (int i = 0; i < players.length; i++) {
				if (dice_values[i] == max) {
					max_duplicate = true;
				}
				else if (dice_values[i] > max) {
					max = dice_values[i];
					index_of_max = i;
					max_duplicate = false;
				}
			}
			
			// If no duplicate max dice values were generated. First player can be decided.
			if (!max_duplicate) {
				decided = true;
			}
			// Else, informing players that a tie was achieved.
			else {
				System.out.print("A tie was achieved between ");
				for (int i = 0; i < players.length; i++) {
					System.out.print("Player " + (i + 1) + " (" + players[i].getPictogram() + ")");
					if (i < players.length - 1) {
						System.out.print(" and ");
					}
					else {
						System.out.print(".");
					}
				}
				System.out.println(" Attempting to break the tie");
			}
			
			// Counting attempts taken to determine first player.
			attempts_count++;
		}
		
		// Informing players of the results and who will be the first player.
		System.out.println("First player will be player " + (index_of_max + 1) + " (" + players[index_of_max].getPictogram() + "). "
				+ "It took " + attempts_count + " attempt(s) for the decision to be made.");
		return index_of_max;
	}
	
	/**
	 * Returns the <code>Player</code> object at a given board location.
	 * 
	 * @param location on the board from 1 to 100.
	 * @return a <code>Player</code> object.
	 */
	private Player getPlayerAtLocation(int location) {
		// Checking every Player's location and returning the player with the location passed as a parameter.
		for (Player p : players) {
			if (p.getLocation() == location) {
				return p;
			}
		}
		
		// Returns null if no Players found with a matching location.
		return null;
	}
	
	/**
	 * The turn is divided into 4 steps:<br>
	 * 	- Throwing the dice; incrementing player's location and saving as a preliminary location.<br>
	 *  - Checking if the new location is bigger than the end location, and recalculating user's location if it's the case.<br>
	 *  - Checking if there are any snakes or ladders at the new location and recalculating user's location.<br>
	 *  - Checking if there is a collision with another player and throwing that other player outside the board (location 0).
	 *  
	 * @param player whose turn has to be played out.
	 */
	private void newTurn(Player player) {
		// Throwing the dice; incrementing player's location and saving as a preliminary location.
		System.out.println("Player's location at the start of the turn: " + player.getLocation());
		int dice_value = flipDice();
		System.out.println("The dice value : " + dice_value);
		int new_location = player.getLocation() + dice_value;
		
		// Checking if the new location is bigger than the end location, and recalculating user's location if it's the case.
		if (new_location > gameboard.getFinishLocation()) {
			new_location = player.getLocation() + (gameboard.getFinishLocation() - player.getLocation()) - (new_location - gameboard.getFinishLocation());
			System.out.println("The player location went over the maximum of 100, so the player was thrown at the location: " + new_location);
		}
		
		// Checking if there are any snakes or ladders at the new location and recalculating user's location.
		Jumper jumper = gameboard.getJumperAtLocation(new_location);
		if (jumper != null) {
			int jumped_location = jumper.getJumperEnd();
			String type = jumper.getJumperType();
			System.out.println("There was a " + type + " at the new location. The player was thrown " + (type == "ladder" ? "forward" : "back") + " at location: " + jumped_location);
			new_location = jumped_location;
		}
		else {
			System.out.println("There was no snake or a ladder at the new location.");
		}
		
		// Checking if there is a collision with another player and throwing that other player outside the board (location 0).
		Player colided_player = getPlayerAtLocation(new_location);
		if (colided_player != null) {
			colided_player.setLocation(0);
			System.out.println("There already was a player in the new location, so the player was thrown back at location 0.");
		}
		else {
			System.out.println("There was no other player at the new location.");
		}
		
		// Informing player about the final location.
		System.out.println("The new location of the player is: " + new_location);
		
		// Setting player's location.
		player.setLocation(new_location);
	}
	
	/**
	 * Calculates the corresponding location on the board
	 * given a pair of row and column indexes in the <code>board</code> array.
	 * Location numbers go from left to right (ascending) for even row indexes,
	 * and from right to left (descending) for the odd rows.
	 * 
	 * @param board_row_index in the <code>board</code> array.
	 * @param board_column_index in the <code>board</code> array.
	 * @return corresponding location on the board from 1 to 100.
	 */
	private int getBoardLocation(int board_row_index, int board_column_index) {
		if (board_row_index % 2 == 0) {
			return (board_row_index * 10) + (board_column_index + 1);
		}
		else {
			return (board_row_index * 10) + (10 - board_column_index);
		}
	}
	
	/**
	 * Prints out board layout and the players at their locations.
	 */
	private void printBoard() {
		// The loop below prints the board by iterating 10 times (1 row per iteration).
		for (int i = 9; i >= 0; i--) {
			// Printing the top edge of the row.
			System.out.println("╬═══════╬═══════╬═══════╬═══════╬═══════╬═══════╬═══════╬═══════╬═══════╬═══════╬");
			
			// Printing the line containing the cell's location.
			for (int j = 0; j < 10; j++) {
					System.out.printf("║    %3d", getBoardLocation(i, j));
			}
			System.out.println("║");
			
			// Printing the line containing the player's pictogram if there is a player at the location.
			for (int k = 0; k < 10; k++) {
				Player player_at_current_cell = getPlayerAtLocation(getBoardLocation(i, k));
				if (player_at_current_cell != null) {
					System.out.print("║   " + player_at_current_cell.getPictogram() + "   ");
				}
				else {
					System.out.print("║       ");
				}
			}
			System.out.println("║");
			
			// Printing the line containing the snake and ladder markers and their end locations.
			for (int l = 0; l < 10; l++) {
				Jumper jumper_at_current_cell = gameboard.getJumperAtLocation(getBoardLocation(i, l));
				if (jumper_at_current_cell != null) {
					char jumper_type_letter = jumper_at_current_cell.getJumperType().toUpperCase().charAt(0);
						System.out.printf("║" + jumper_type_letter + "->%3d ", jumper_at_current_cell.getJumperEnd());
				}
				else {
					System.out.print("║       ");
				}
			}
			System.out.println("║");
		}
		// Printing the last bottom edge of the board.
		System.out.println("╬═══════╬═══════╬═══════╬═══════╬═══════╬═══════╬═══════╬═══════╬═══════╬═══════╬");
	}
	
	/**
	 * Core method of the game instance responsible for executing the game
	 * and going through all the steps until a winner is determined.
	 */
	public void play() {
		Scanner keyboard = new Scanner(System.in);
		// Getting the index of the first player to start its turn.
		int player_index = getFirstPlayerIndex();
		
		// Printing the board.
		printBoard();
		
		// Playing turns until winner is determined.
		while(true) {
			// Prompting player to press enter to continue.
			System.out.print("Please press Enter to continue: ");
			
			// Waiting for player to press Enter to start his turn.
			keyboard.nextLine();
			
			// Clearing the console and resetting the caret to the upper left corner.
			System.out.print("\033[H\033[2J");
			System.out.flush();
			
			// Announcing the beginning of the player's turn.
			System.out.println("Turn of player " + (player_index + 1) + " (" + players[player_index].getPictogram() + ")");
			// Playing out the turn.
			newTurn(players[player_index]);
			
			// Checking if current player has reached the finish location.
			if (players[player_index].getLocation() == gameboard.getFinishLocation()) {
				// Announcing the winner.
				System.out.println("The game is over. Player " + players[player_index].getPictogram() + " won.");
				// Printing the board to display the outcomes of the final turn.
				printBoard();
				break;
			}
			else {
				System.out.println("Game not over; flipping again.");
			}
			
			// Printing the board to display the outcomes of the turn.
			printBoard();
			
			// Incrementing the index of the player, or resetting it to 0 if last player in the array just played their turn,
			// so that the next player could start their turn.
			player_index = player_index == players.length - 1 ? 0 : ++player_index;
		}
		keyboard.close();
	}
	
	/**
	 * Returns the number of players participating in the game.
	 * 
	 * @return the number of players.
	 */
	public int getNumberOfPlayers() {
		return number_of_players;
	}

	/**
	 * Basic getter for <code>gameboard</code> attribute.
	 * 
	 * @return <code>Gameboard</code> object.
	 */
	public static Gameboard getGameboard() {
		return gameboard;
	}

	/**
	 * Basic getter for <code>players</code> attribute.
	 * 
	 * @return <code>players</code> array.
	 */
	public Player[] getPlayers() {
		return players;
	}
}
