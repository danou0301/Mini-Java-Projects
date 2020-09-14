import java.util.Scanner;

/**
 * The Competition class represents a Nim competition between two players, consisting of a given number of rounds. 
 * It also keeps track of the number of victories of each player.
 */
public class Competition {

    /* the two players of the competition */
    private Player player1;
    private Player player2;
    /* Game with human */
    private boolean displayMessage;

    private static final String MSG_WELCOME1 = "Starting a Nim competition of ";
    private static final String MSG_WELCOME2 = " rounds between a ";
    private static final String MSG_WELCOME3 = " player and a ";
    private static final String MSG_WELCOME4 = " player.";
    private static final String MSG_RESULT1 = "The results are ";
    private static final String MSG_RESULT2 = ":";
    private static final int STARTING_SCORE = 0;
    private static final String MSG_WELCOME = "Welcome to the sticks game!";
    private static final String MSG_INVALID_MOVE = "Invalid move. Enter another:";
    private static final String MSG_PLAYER = "Player ";

    /* players scores */
    private int scorePlayer1 = STARTING_SCORE;
    private int scorePlayer2 = STARTING_SCORE;

    /*-----Constructors-----*/
    /** Receives two Player objects, representing the two competing opponents, and a flag determining whether
    * messages should be displayed.
    * @param player1 - The Player objects representing the first player.
    * @param player2 - The Player objects representing the second player.
    * @param displayMessage - a flag indicating whether game play messages should be printed to the console.
     **/
	public Competition(Player player1, Player player2, boolean displayMessage){
        this.player1 = player1;
        this.player2 = player2;
        this.displayMessage = displayMessage;
	}

    /**
	 * Play a single game
	 **/
	private void playSingleGame(){
	    Board theBoard = new Board();
        Player currentPlayer = player1;
	    if (displayMessage){
            System.out.println(MSG_WELCOME);
        }
        while (theBoard.getNumberOfUnmarkedSticks() != 0){ //While the game is not finished
            if (displayMessage) {
                System.out.println(MSG_PLAYER + currentPlayer.getPlayerId() + ", it is now your turn!");
            }
            Move playerMove = currentPlayer.produceMove(theBoard);
            while (theBoard.markStickSequence(playerMove) != 0){ // check illegal move

                if (displayMessage){
                    System.out.println(MSG_INVALID_MOVE);
                }
                playerMove = currentPlayer.produceMove(theBoard);
            }
            if (displayMessage){
                System.out.println(MSG_PLAYER + currentPlayer.getPlayerId() + " made the move: " + playerMove.toString());

            }

            if (currentPlayer == player1){ // change the current player
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }

        }
        // set the score
        if (displayMessage) {
            System.out.println(MSG_PLAYER + currentPlayer.getPlayerId() + " won!");
        }
        if (currentPlayer == player1){
            scorePlayer1++;
        } else {
            scorePlayer2++;
        }

    }
    /**
	 * If playerPosition = 1, the results of the first player is returned. If playerPosition = 2, the result
	 * of the second player is returned. If playerPosition equals neiter, -1 is returned.
	 * @param playerPosition - id of the player
	 * @return the number of victories of a player.
	 **/
	public int getPlayerScore(int playerPosition){
        switch (playerPosition){
            case 1:
                return scorePlayer1;
            case 2:
                return scorePlayer2;
            default:
                return -1;
        }
    }

    /**
	 * Run the game for the given number of rounds.
	 * @param numberOfRounds - number of rounds to play.
	 **/
    public void playMultipleRounds(int numberOfRounds){

        System.out.println(MSG_WELCOME1 + numberOfRounds + MSG_WELCOME2 + player1.getTypeName()
                + MSG_WELCOME3 + player2.getTypeName() + MSG_WELCOME4);

	    for (int i = 0; i < numberOfRounds; i++){
	        playSingleGame();
        }

        System.out.println(MSG_RESULT1 + scorePlayer1 + MSG_RESULT2 + scorePlayer2);
    }

	/*
	 * Returns the integer representing the type of player 1; returns -1 on bad
	 * input.
	 */
	private static int parsePlayer1Type(String[] args){
		try{
			return Integer.parseInt(args[0]);
		} catch (Exception E){
			return -1;
		}
	}
	
	/*
	 * Returns the integer representing the type of player 2; returns -1 on bad
	 * input.
	 */
	private static int parsePlayer2Type(String[] args){
		try{
			return Integer.parseInt(args[1]);
		} catch (Exception E){
			return -1;
		}
	}
	
	/*
	 * Returns the integer representing the type of player 2; returns -1 on bad
	 * input.
	 */
	private static int parseNumberOfGames(String[] args){
		try{
			return Integer.parseInt(args[2]);
		} catch (Exception E){
			return -1;
		}
	}

	/**
	 * The method runs a Nim competition between two players according to the three user-specified arguments. 
	 * (1) The type of the first player, which is a positive integer between 1 and 4: 1 for a Random computer
	 *     player, 2 for a Heuristic computer player, 3 for a Smart computer player and 4 for a human player.
	 * (2) The type of the second player, which is a positive integer between 1 and 4.
	 * (3) The number of rounds to be played in the competition.
	 * @param args an array of string representations of the three input arguments, as detailed above.
	 */
	public static void main(String[] args) {
		
		int p1Type = parsePlayer1Type(args);
		int p2Type = parsePlayer2Type(args);
		int numGames = parseNumberOfGames(args);

        Scanner scanner = new Scanner(System.in);
		Player player1 = new Player(p1Type,1, scanner);
        Player player2 = new Player(p2Type,2, scanner);

        Competition theCompetition = new Competition(player1, player2, true);
        // Starting the competition
        theCompetition.playMultipleRounds(numGames);
        scanner.close();
    }
}
