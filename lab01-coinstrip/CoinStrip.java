
// Iam the sole author of the work in this repository
import java.util.Random;
import java.util.Scanner;

public class CoinStrip {

    private static int numSquares = 10; // max number of squares in a coin strip.
    private static int[] coinStrip = new int[numSquares]; // coin strip
    private static int currentPlayer;

    /**
     * Function to create the board and setup the game
     *
     * @param coins holds the set of coins
     * @param max   specifies the max number of coins to make
     */
    public static void createBoard(int[] coins, int max) {
        // random number of coins > 3 but < total num of squares - 2
        Random random = new Random();
        int numCoins = random.nextInt(max - 5) + 3;
        // position coins randomly in the strip and allow for at least one game play
        do {
            for (int i = 0; i < numCoins; i++) {
                // random position to place each coin.
                int position = isSquareEmpty(coins, max);
                coins[position] = i + 1;
            }
        } while (isGameOver(coins));
        // print the board
        printBoard(coins);
    }

    /**
     * Function that checks if a square is empty and if it is returns that index.
     *
     * @param coins holds set of coins
     * @param max   specifies max number of coins to make
     * @return random position which is empty.
     */
    public static int isSquareEmpty(int[] coins, int max) {
        Random random = new Random();
        int position = random.nextInt(max);
        // check if the random position is empty
        while (coins[position] != 0) {
            position = random.nextInt(max);
        }
        return position;
    }

    /**
     * Function to print the board or coin strip.
     *
     * @param coins holds the set of coins.
     */
    public static void printBoard(int[] coins) {
        // print out any coin or number in coins array and empty braces for 0s.
        for (int j = 0; j < coins.length; j++) {
            if (coins[j] == 0) {
                System.out.print("[ ]");
            } else {
                System.out.print("[" + coins[j] + "]");
            }
        }
        System.out.println();
    }

    /**
     * Function to get player coin and number of spaces.
     *
     * @param player specifies the current player.
     * @param coins  holds set of coin.
     * @return array of size 2 with coin in index 0 and num spaces in index 1.
     */
    public static int[] getCoinAndNumSpaces(int player, int[] coins) {
        // validate to make sure player enters valid input for number of spaces to move
        while (true) {
            Scanner scanner = new Scanner(System.in);
            // userInput stores user input first coin and second numSpaces
            int[] userInput = new int[2];
            try {
                // player should enter input in format <cn> <sp>
                System.out.print("Player " + player + ", which coin [ 1 - " + getNumCoins(coins)
                        + " ] & num of spaces <cn> <sp>: ");
                userInput[0] = scanner.nextInt();
                // check if the second number is an int and add it to array
                if (scanner.hasNextInt()) {
                    userInput[1] = scanner.nextInt();
                    return userInput;
                }
                System.out.println("Please enter a valid input? Try again !!!!!");
            } catch (Exception e) {
                System.out.println("Please enter a valid input? Try again !!!!!");
            }
        }
    }

    /**
     * Function validates player move that is makes sure:
     * The inputted num of spaces > 0
     * Avoid indexing out of range of the coins array
     * Avoids coin pass and making sure selected square is empty.
     *
     * @param coins     holds the set of coins
     * @param whichCoin the number of the coin to move
     * @param numSpaces number of spaces to move the coin (left)
     * @return true if the move is valid
     */
    public static boolean isValidMove(int[] coins, int whichCoin, int numSpaces) {
        // numSpaces should be > 0
        if (numSpaces <= 0) {
            System.out.println("Invalid input: number of spaces should be > 0");
            return false;
        }
        // check if user entered a coin present in the coin strip
        if (getCoinPosition(coins, whichCoin) == -1 || whichCoin == 0) {
            System.out.println("Enter a valid coin between 1 and " + getNumCoins(coins) + " !!!!");
            return false;
        }
        int coinPosition = getCoinPosition(coins, whichCoin);
        // player should not index out of range of coins array
        if (numSpaces > coinPosition) {
            System.out.println("Invalid input: num spaces too big or coin at end. ");
            return false;
        }
        // no coin pass and selected square should be empty
        for (int i = 0; i < numSpaces; i++) {
            // coins should only move to left
            if (coins[coinPosition - (i + 1)] != 0) {
                System.out.println("Invalid input: selected square should be empty and no coin pass");
                return false;
            }
        }

        return true;
    }

    /**
     * Updates the game board to reflect a move,
     * Behavior is undefined if the described move is invalid.
     *
     * @param coins     holds the set of coins
     * @param whichCoin the number of the coin
     * @param numSpaces number of spaces to move the coin (left)
     */
    public static void makeMove(int[] coins, int whichCoin, int numSpaces) {
        int coinPosition = getCoinPosition(coins, whichCoin);
        coins[coinPosition] = 0; // set the old position of coin to be empty.
        // move selected coin numSpaces times to the left.
        coins[coinPosition - numSpaces] = whichCoin;
    }

    /**
     * Function to change the current player
     *
     * @param player is the current player in the game
     * @return the next player to play as an int.
     */
    public static int flipPlayer(int player) {
        if (player == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Returns true if the game is over.
     *
     * @param coins holds the set of coins
     * @return True if the game is over, false otherwise.
     */
    public static boolean isGameOver(int[] coins) {
        // using number of coins in strip, check if all of them are to the left of
        // strip.
        int numCoins = getNumCoins(coins);
        for (int i = 0; i < numCoins; i++) {
            if (coins[i] == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the number of coins on the CoinStrip game board.
     *
     * @param coins holds the set of coins
     * @return the number of counts on the CoinStrip game board.
     */
    public static int getNumCoins(int[] coins) {
        int count = 0; // keep track of number of coins
        for (int k = 0; k < coins.length; k++) {
            if (coins[k] != 0) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Returns the 0-indexed location of a specific coin or -1 if coin not found
     * Coins are one-indexed. So, if the CoinStrip had 3 coins, the coins
     * would be indexed 1, 2 or 3.
     *
     * @param coins   holds the set of coins
     * @param coinNum the 0-indexed coin number
     * @return the 0-indexed location of the coin on the CoinStrip or -1 if not
     *         found
     */
    public static int getCoinPosition(int[] coins, int whichCoin) {
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] == whichCoin) {
                return i;
            }
        }
        return -1;
    }

    /**
     * `public static void main(String[] args)` is a program's entry point.
     * This main method implements the functionality to play the CoinStrip
     * game.
     *
     * @param Command-line arguments are ignored.
     */
    public static void main(String[] args) {
        // print out welcome message and some game rules
        String gameRules = "----Some basic rules \n"
                + "1. No 'going right': Coins can only move to the left\n"
                + "2. No 'double occupancy': At most one coin can occupy a space at a given time. \n"
                + "3. No 'jumps': Coins cannot pass another coin. \n"
                + "4. 'Mandatory progress': A coin must move one or more spaces. \n"
                + "5. Enter coin first and number of spaces next to it: <cn> <ns>";
        System.out.println("**Welcome to the Silver Dollar Game!!!!**");
        System.out.println(gameRules);
        // create board and setup the game
        System.out.println();
        createBoard(coinStrip, numSquares);
        // Select a random player to start the game 0 or 1
        Random random = new Random();
        currentPlayer = random.nextInt(2);
        // start the game
        while (!isGameOver(coinStrip)) {
            int coin; // user selected coin
            int numSpaces = 0; // num of spaces to move coin
            // ask player move and validate it.
            do {
                System.out.println(); // leave a line after printing board
                // prompt the player to select a coin and numSpaces
                int[] userInput = getCoinAndNumSpaces(currentPlayer, coinStrip);
                coin = userInput[0];
                numSpaces = userInput[1];
            } while (!isValidMove(coinStrip, coin, numSpaces));
            // move coin and print board
            makeMove(coinStrip, coin, numSpaces);
            printBoard(coinStrip);
            // flip the player
            currentPlayer = flipPlayer(currentPlayer);
            // notify the next player to play
            System.out.println("**** Nice move, now its player " + currentPlayer + "'s turn**** ");
            System.out.println();

        }
        // flip player
        currentPlayer = flipPlayer(currentPlayer);
        // tell the player if they have won
        System.out.println("Player " + currentPlayer + " has won !!!!!");
    }
}
