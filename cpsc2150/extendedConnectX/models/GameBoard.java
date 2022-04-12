package cpsc2150.extendedConnectX.models;

/**
 *
 * A GameBoard containing characters.
 * A GameBoard is a structure of 1-100 rows and 1-100 columns array.
 * Players take turns dropping tokens until a player wins
 * by placing 1-100 uninterrupted tokens in a line or they tie.
 *
 *
 * @author Jenna Adams
 * @version 1.0
 *
 * @invariant
 * [GameBoard will hold 2d array of length being a number 1-100 and width being a number 1-100] AND
 * [GameBoard has no gaps between tokens] AND
 *
 *
 * Correspondances:
 * self = board[1...100][1...100]
 *
 *
 *
 *
 */

public class GameBoard extends AbsGameBoard implements IGameBoard {

    private char[][] board;
    private int tokensToWin;
    private int rows;
    private int columns;

    /**
     *(initalizes an object of Gameboard)
     *
     * @param inputTokens needed to win the game
     * @param inputRows to set board length to size rows
     * @param inputColumns to set board width to size columns
     *
     * @pre tokens <= 25 and tokens >= 3 AND
     * rows <= 100 and rows >= 3 AND
     * columns <= 100 and columns >= 3
     *
     * @post
     * [GameBoard 2d array is 6 rows and 9 columns]
     * [GameBoard 2d array holds a space " " in each position]
     *
     */
    GameBoard(int inputTokens, int inputRows, int inputColumns){
        //set tokens
        tokensToWin = inputTokens;
        rows = inputRows;
        columns = inputColumns;
        //allocating memory to private array board
        board = new char[rows][columns];
        // for loop to set each element in the array to ' ' (a space)
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                board[i][j] = ' ';
            }
        }
    }

    public void placeToken(char p, int c){
        // for loop to loop through each element in the column from the bottom to the top, then drops the token in the lowest available space
        for (int i = 0; i < rows; i++){
            if(board[i][c] == ' '){
                board[i][c] = p;
                i = rows;
            }
        }
    }

    public char whatsAtPos(BoardPosition pos){
        // set the element at the position to the item then return
        char item = board[pos.getRow()][pos.getColumn()];
        return item;
    }

    public int getNumRows(){
        return rows;
    }

    public int getNumColumns(){
        return columns;
    }

    public int getNumToWin(){
        return tokensToWin;
    }
}
