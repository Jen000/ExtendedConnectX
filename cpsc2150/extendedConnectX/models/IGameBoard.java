package cpsc2150.extendedConnectX.models;

/**
 *
 * A GameBoard containing characters.
 * A GameBoard is a structure of a 6 row 9 column array.
 * Players take turns dropping tokens (x or o) until a player wins
 * by placing 5 uninterrupted tokens in a line or they tie.
 *
 *
 * @author Jenna Adams
 * @version 1.0
 * @invariant
 * [GameBoard will hold a 6 x 9 2d array] AND
 * [GameBoard has no gaps between tokens]
 *
 * Initialization ensures:
 * GameBoard holds an 2d array of board[6][9] AND
 * GameBoard 2d array holds empty (" ") elements
 *
 * Defines:
 * L_NUM = -1;
 *
 * Constraints:
 * 0 <= pos.r < R_NUM AND
 * 0 <= pos.c < C_NUM
 *
 *
 *
 */
public interface IGameBoard {

    public static final int L_NUM = -1;

    /**
     *
     *(boolean function to determine if the column can accept another token or not)
     *
     * @param c to take the integer of the column to be checked
     *
     * @return true if the column can accept another token; false
     * otherwise
     *
     * @pre
     * c >=0 AND c <= 9
     *
     * @post
     * CheckIfFree(c) = true iff  , otherwise false AND
     * GameBoard = #GameBoard AND
     * [GameBoard 2d array is 6 rows and 9 columns]
     *
     */
    @SuppressWarnings("unchecked")
    default public boolean checkIfFree(int c){
        for(int i = 0; i < getNumRows(); i++){
            BoardPosition temp  = new BoardPosition(i,c);
            if (whatsAtPos(temp) == ' '){
                return true;
            }
        }
        return false;
    }


    /**
     *(places the character p in column c. The token will be placed in
     * the lowest available row in column c.)
     *
     * @param c to take the integer of the column where the token will be placed
     * @param p to take the Character of the token (x or o)
     *
     * @pre
     * p == 'x' or p == 'o' AND
     * c >=0 and c <= 9 AND
     * checkIfFree(c) == true
     *
     * @post
     * [GameBoard 2d array holds p at the lowest available available row in column c] AND
     * [GameBoard 2d array is 6 rows and 9 columns]
     *
     */
    public void placeToken(char p, int c);


    /**
     *(this function will check to see if the last token placed in
     * column c resulted in the player winning the game. If so it will return
     * true, otherwise false.)
     *
     * Note: Note: this is not checking the entire board for a win, it is
     * just checking if the last token placed results in a win.
     *
     * @param c to take the integer of the column to be checked
     *
     * @return If the last placed token resulted in a win it will return true, otherwise false
     *
     * @pre
     * c >=0 and c <= 9 AND
     * [BoardGame column c must contain Characters of 'x' or 'o'] AND
     * [pos is the GameBoard position on the latest play]
     *
     * @post
     * checkForWin(c) = [true iff placed token is the last ot make up the maximum number of
     * consecutive same tokens need to win either Vertically, Horizontally, or Diagonally, else false] AND
     * GameBoard = #GameBoard AND
     * [GameBoard 2d array is 6 rows and 9 columns] AND
     * [pos is the GameBoard on the latest play]
     *
     */
    @SuppressWarnings("unchecked")
    default boolean checkForWin(int c){
        BoardPosition pos = null;
        for (int i = getNumRows()-1; i >= 0; i--){
            pos = new BoardPosition(i,c);
            if (whatsAtPos(pos) != ' '){
                i = -1;
            }
        }
        char p = whatsAtPos(pos);
        if (checkDiagWin(pos, p) == true|| checkHorizWin(pos, p) == true || checkVertWin(pos, p) == true){
            return true;
        }
        return false;
    }


    /**
     *
     *(this function will check to see if the game has resulted in a
     * tie. A game is tied if there are no free board positions remaining.
     * You do not need to check for any potential wins because we can assume
     * that the players were checking for win conditions as they played the
     * game. It will return true if the game is tied and false otherwise)
     *
     * @return true if there are no free board positions remaining and no player has won; otherwise false
     *
     * @pre
     * [BoardGame must contain Characters of 'x' or 'o']
     * checkForWin() = false
     *
     * @post
     * checkTie() = iff there are no more available spaces in the GameBoard return true, else false AND
     * GameBoard = #GameBoard AND
     * [GameBoard 2d array is 6 rows and 9 columns]
     *
     */
    @SuppressWarnings("unchecked")
    default boolean checkTie(){
        for (int i = 0; i < getNumColumns(); i++){
            if (checkIfFree(i)){
                return false;
            }
        }
        return true;
    }


    /**
     *(checks to see if the last token placed (which was placed in
     * position pos by player p) resulted in 5 in a row horizontally. Returns
     * true if it does, otherwise false)
     *
     * @param pos to take in an instance of BoardPosition
     * @param p to take in a players token char of x or o
     *
     *
     * @return true if the last token placed resulted in 5 in a row horizontally; otherwise false
     *
     * @pre
     * [BoardGame must contain at least 5 Characters of 'x' or 'o']
     *
     * @post
     * checkHorizWin(pos, p) = iff 5 consecutive tokens of x or 5 consecutive tokens of y are in a row return true, else false AND
     * GameBoard = #GameBoard AND
     * [GameBoard 2d array is 6 rows and 9 columns] AND
     *
     *
     */
    @SuppressWarnings("unchecked")
    default public boolean checkHorizWin(BoardPosition pos, char p){
        int col = pos.getColumn();
        int row = pos.getRow();
        int count = 0;
        for (int i = col; i < getNumColumns(); i++){
            BoardPosition right = new BoardPosition(row,i);
            if (whatsAtPos(right) == p)
                count++;
            else
                i = getNumColumns();
        }

        for (int i = col-1; i > -1; i--){
            BoardPosition left = new BoardPosition(row,i);
            if (whatsAtPos(left) == p)
                count++;
            else
                i = -1;
        }
        if (count >= getNumToWin()){
            return true;
        }
        else
            return false;
    }

    /**
     *
     * (checks to see if the last token placed resulted in 5 in a row vertically.)
     *
     * @param pos to take an instance of BoardPosition
     * @param p to take in a players token char of x or o
     *
     * @return true if the last placed token resulted in 5 p in a row vertically; otherwise false
     *
     * @pre
     * [BoardGame must contain at least 5 Characters of 'x' or 'o'] AND
     * [0 <= lastPos.getRow() < MAX_ROW_NUM AND 0 <= lastPos.getColumn() < MAX_COLUMN_NUM AND[lastPos was the location where the last token was placed in] AND
     * [pos is a position on the latest play] AND
     * [pos is within valid bounds]
     *
     * @post
     * checkVertWin(pos, p) = true iff a placed taken is the last to make up the maximum number of consecutive same tokens needed to win vertically, else false] AND
     * GameBoard = #GameBoard AND
     * [GameBoard 2d array is 6 rows and 9 columns] AND
     *
     */
    @SuppressWarnings("unchecked")
    default public boolean checkVertWin(BoardPosition pos, char p){
        int column = pos.getColumn();
        int row = pos.getRow();
        int count = 0;
        for(int i = 0; i <= row; i++){
            BoardPosition temp  = new BoardPosition(i,column);
            if (isPlayerAtPos(temp,p)){
                count++;
            }
            else
                if (count > 0){
                    count = 0;
                }
        }
        if (count == getNumToWin())
            return true;
        else
            return false;
    }


    /**
     *
     * (checks to see if the last token placed (which was placed in
     * position pos by player p) resulted in 5 in a row diagonally. Returns
     * true if it does, otherwise false)
     *
     * @param pos to take an instance of BoardPosition
     * @param p to take in a players token char of x or o
     *
     * @return true if the last placed token resulted in 5 p in a row diagonally; otherwise false
     *
     * @pre
     * [BoardGame must contain at least 5 Characters of 'x' or 'o']
     *
     * @post
     * checkDiagWin(pos, p) = iff 5 of the same player tokens are found consectutively diagonal on the GameBaord return true, else false AND
     * GameBoard = #GameBoard AND
     * [GameBoard 2d array is 6 rows and 9 columns]
     *
     */
    @SuppressWarnings("unchecked")
    default public boolean checkDiagWin(BoardPosition pos, char p){
        int col = pos.getColumn();
        int row = pos.getRow();
        int count = 0;
        for (int i = col, j = row; i < getNumColumns() && j < getNumRows(); i++, j++){
            BoardPosition topright = new BoardPosition(j,i);
            if (whatsAtPos(topright) == p)
                count++;
            else
                i = getNumColumns();
        }

        for (int i = col-1, j = row-1; i >= 0 && j >= 0; i--, j--){
            BoardPosition botLeft = new BoardPosition(j,i);
            if (whatsAtPos(botLeft) == p)
                count++;
            else
                i = L_NUM;
        }

        if (count >= getNumToWin()){
            return true;
        }
        else {
            count = 0;
            for (int i = col, j = row; i >= 0 && j < getNumRows(); i--, j++) {
                BoardPosition topLeft = new BoardPosition(j, i);
                if (whatsAtPos(topLeft) == p)
                    count++;
                else
                    i = L_NUM;
            }

            for (int i = col + 1, j = row - 1; i < getNumColumns() && j >= 0; i++, j--) {
                BoardPosition botRight = new BoardPosition(j, i);
                if (whatsAtPos(botRight) == p)
                    count++;
                else
                    i = getNumColumns();
            }
        }

        if (count >= getNumToWin()){
            return true;
        }
        else
            return false;
    }


    /**
     *
     * (/returns what is in the GameBoard at position pos
     * If no marker is there, it returns a blank space char.)
     *
     *
     * @param pos to take an instance of BoardPosition
     *
     * @return char at pos
     *
     * @pre pos is within valid bounds
     *
     * @post
     * whatsAtPos(pos) = 'x' || 'o' || ' ' AND
     * GameBoard = #GameBoard AND
     * [GameBoard 2d array is 6 rows and 9 columns]
     *
     */
    public char whatsAtPos(BoardPosition pos);


    /**
     *
     * (determines if a player is at pos)
     *
     * @param pos to take in an instance of BoardPosition
     * @param player to take in the char of the player to place at the specified board position
     *
     * @return true if the player is at pos; otherwise false
     *
     * @pre pos is within valid bounds
     *
     * @post
     * isPlayerAtPos(Board Position, char) = true || false AND
     * [GameBoard 2d array is 6 rows and 9 columns]
     *
     */
    @SuppressWarnings("unchecked")
    default boolean isPlayerAtPos(BoardPosition pos, char player){
        if (whatsAtPos(pos) == player){
            return true;
        }
        return false;
    }


    /**
     *
     * (returns the number of rows in the GameBoard)
     *
     * @return number of rows in the GameBoard
     *
     *
     * @pre none
     *
     *
     * @post getNumRows() = [number of rows in GameBoard]
     *
     *
     */
    public int getNumRows();


    /**
     *
     * (returns the number of columns in the GameBoard)
     *
     * @return the number of columns in the GameBoard
     *
     *
     * @pre none
     *
     *
     * @post getNumColumns() = [number of columns in GameBoard]
     *
     *
     */
    public int getNumColumns();


    /**
     *
     * (returns the number of subsequent tokens in a row/column or diagonal needed to win)
     *
     * @return the number of subsequent tokens in a row/column or diagonal needed to win
     *
     *
     * @pre none
     *
     *
     * @post getNumToWin() = [number of consecutive tokens needed to win]
     *
     *
     */
    public int getNumToWin();
}
