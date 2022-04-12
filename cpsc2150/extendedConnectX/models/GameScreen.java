package cpsc2150.extendedConnectX.models;
import java.util.Scanner;

/**
 *
 * @author Jenna Adams
 * @version 1.0
 */
public class GameScreen {

    /**
     * (runs the GameScreen program)
     *
     * @pre none
     * @post main() = [running program of ConnectX]
     */
    public static void main(String[] args) {
        // create scanner
        Scanner input = new Scanner(System.in);
        IGameBoard game;
        int players, tokens, rows, columns;
        //welcome and ask for board input
        System.out.println("Welcome to ConnectX!");
        players = inputPlayers();
        char[] playerCharacters = playerChars(players);
        rows = inputRows();
        columns = inputColumns();
        tokens = inputTokens(rows, columns);
        //***************************************************************
        // if's for implementation
        char gameMode = memoryOrFast();
        if (gameMode == 'f' || gameMode == 'F'){
            //fast
            game = new GameBoard(tokens, rows, columns);
        }
        else{
            //memory
            game = new GameBoardMem(tokens, rows, columns);
        }
        //****************************************************************
        // set variables
        char play = 'Y';
        // print rules
        System.out.println("Rules:\n" +
                "The game will alternate between players.\n" +
                "Each player will place a token within a column, if a column is full the player will choose again.\n" +
                "The first player to connect" + tokens + " tokens in a diagonal, vertical, or horizontal line first, wins.\n" +
                "Good Luck!\n");
        // set placement to 1 (so the if statements in the while loop work
        int placement = 1;
        //while loop for the whole game
        while (play == 'Y' || play == 'y') {
            for (int playerNum = 0; playerNum < players; playerNum++) {
                char player = playerCharacters[playerNum];
                do {
                    // if placement is not clear return full statement
                    if (!(game.checkIfFree(placement)))
                        System.out.println("Column is full");
                    // print board and ask for input
                    do {
                        System.out.print(game.toString());
                        System.out.println("Player " + player + ", what column do you want to place your marker in?");
                        // take user input
                        placement = input.nextInt();
                    }
                    while (!(validInput(placement, columns)));
                }
                // checkIfFree if not ask for input again
                while (!(game.checkIfFree(placement)));
                // place token
                game.placeToken(player, placement);
                // check wins
                if (game.checkForWin(placement)) {
                    // print board and results
                    System.out.print(game.toString());
                    System.out.println("Player " + player + " Won!");
                    do {
                        // if they want to play again create a new gameboard and set the player back to x (even to true)
                        System.out.println("Would you like to play again? Y/N");
                        play = input.next().charAt(0);
                        if (play == 'Y' || play == 'y') {
                            // ask for tokens rows and columns again
                            playerNum = -1;
                            players = inputPlayers();
                            playerCharacters = playerChars(players);
                            rows = inputRows();
                            columns = inputColumns();
                            tokens = inputTokens(rows, columns);
                            gameMode = memoryOrFast();
                            if (gameMode == 'f' || gameMode == 'F'){
                                //fast
                                game = new GameBoard(tokens, rows, columns);
                            }
                            else{
                                //memory
                                game = new GameBoardMem(tokens, rows, columns);
                            }
                        }
                        else
                            playerNum = players;
                    }
                    while (play != 'Y' && play != 'y' && play != 'N' && play != 'n');
                }
                // check if tie
                if (game.checkTie()) {
                    // print board and results
                    System.out.print(game.toString());
                    System.out.println("This game is a Tie!");
                    do {
                        // ask if they want to play again, if so create new clean gameboard
                        System.out.println("Would you like to play again? Y/N");
                        play = input.next().charAt(0);
                        if (play == 'Y' || play == 'y') {
                            // ask for tokens rows and columns again
                            playerNum = -1;
                            players = inputPlayers();
                            playerCharacters = playerChars(players);
                            rows = inputRows();
                            columns = inputColumns();
                            tokens = inputTokens(rows, columns);
                            gameMode = memoryOrFast();
                            if (gameMode == 'f' || gameMode == 'F'){
                                //fast
                                game = new GameBoard(tokens, rows, columns);
                            }
                            else{
                                //memory
                                game = new GameBoardMem(tokens, rows, columns);
                            }
                        }
                        else
                            playerNum = players;
                    }
                    while (play != 'Y' && play != 'y' && play != 'N' && play != 'n');
                }
            }
        }
    }


    /**
     *(returns a boolean based on if the user's input is within valid bounds)
     *
     * @param c
     *
     * @pre none
     *
     * @post validInput() = iff 0 < c < columns return true else false
     *
     *
     * @return validInput() = iff c >= 0 and c < 9 return true, else false
     *
     */
    // add contracts add uml and activity diagram
    private static boolean validInput(int c, int columns){
        int max = columns;
        int min  = 0;
        if (c >= min && c < max){
            return true;
        }
        else
            return false;
    }

    /**
     *(returns user input between range of 2 to 10, this is asking the user for the number of players to play the game)
     *
     * @pre none
     *
     * @post
     * inputPlayers() = [user input int while input is less than 11 and greater than 1]
     *
     */
    private static int inputPlayers(){
        // create scanner
        Scanner input = new Scanner(System.in);
        int max = 10;
        int min = 2;
        int players;
        do {
            System.out.println("How many players?");
            players = input.nextInt();
        }
        while(players > max || players < min);
        return players;
    }

    /**
     *(returns user input of type char, this is asking the user for the char to be assigned to each player)
     *
     * @param players
     *
     * @pre 2 <= players <= 10
     *
     * @post
     * 2 <= players <= 10 AND
     * playerChars(int) = [user assigns each player a char]
     *
     */
    private static char[] playerChars(int players){
        Scanner input = new Scanner(System.in);
        char[] playerCharacters = new char[players];
        char playerChar = ' ';
        // remember to check that char isn't already taken
        for (int i = 0; i < players; i++){
            do {
                System.out.println("Enter the character to represent player " + (i+1));
                playerChar = input.next().charAt(0);
            }
            while(linearSearch(playerCharacters, playerChar));
            playerCharacters[i] = playerChar;
        }
        return playerCharacters;
    }

    /**
     *(returns user input of type int, allows the user to select how many tokens will win the game between the range of 25 and the amount of columns and rows (whichever is lower))
     *
     * @param rows
     * @param columns
     *
     * @pre 3 < rows <= 100 AND
     * 3 < columns <= 100
     *
     * @post
     * 3 < rows <= 100 AND
     * 3 < columns <= 100 AND
     * inputTokens(int,int) = [user input for the amount of tokens] tokens < 25 && tokens > 0 && tokens < rows && tokens < columns
     *
     */
    private static int inputTokens(int rows, int columns){
        // create scanner
        Scanner input = new Scanner(System.in);
        int max = 25;
        int min = 0;
        int tokens;
        do {
            System.out.println("How many in a row to win?");
            tokens = input.nextInt();
        }
        while(tokens > max || tokens < min || tokens > rows || tokens > columns);
        return tokens;
    }

    /**
     *(returns the user input, user is asked how many rows the gameboard should have in a range of 3 to 100)
     *
     * @pre none
     *
     * @post inputRows() = [user input for the amount of rows] rows > 3 && rows < 100
     *
     */
    private static int inputRows(){
        // create scanner
        Scanner input = new Scanner(System.in);
        int max = 100;
        int min = 3;
        int rows;
        do {
            System.out.println("How many rows should be on the board?");
            rows = input.nextInt();
        }
        while(rows < min || rows > max);
        return rows;
    }

    /**
     *(returns the user input, user is asked how many columns the gameboard should have in a range of 3 to 100)
     *
     * @pre none
     *
     * @post inputColumns() = [user input for the amount of columns] columns > 3 && columns < 100
     *
     */
    private static int inputColumns(){
        // create scanner
        Scanner input = new Scanner(System.in);
        int max = 100;
        int min = 3;
        int columns;
        do {
            System.out.println("How many columns should be on the board?");
            columns = input.nextInt();
        }
        while(columns < min || columns > max);
        return columns;
    }

    /**
     *(This function returns a boolean that determines if a playerToken is already in use, this ensures that two players may not use the same char)
     *
     * @param array
     * @param playerInputToken
     *
     * @pre |char[] array| > 0
     *
     * @post linearSearch() = [if playerInputToken is found within array, return true, else false]
     * #char[] array = |char[] array|
     *
     */
    private static boolean linearSearch(char[] array, char playerInputToken){
        for (char player : array) {
            if (player == playerInputToken){
                return true;
            }
        }
        return false;
    }

    /**
     *(This function asks the user for input and returns a char that will determine if they will use a Fast game implementation or a memory efficent implementation)
     *
     * @pre none
     *
     * @post memoryOfFast() = [user input of type char] 'f' || 'F' || 'm' || 'M'
     *
     */
    private static char memoryOrFast(){
        // create scanner
        Scanner input = new Scanner(System.in);
        char gameMode;
        do {
            System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)?");
            gameMode = input.next().charAt(0);
        }
        while(gameMode != 'F' && gameMode != 'f' && gameMode != 'm' && gameMode != 'M');
        return gameMode;
    }

}


