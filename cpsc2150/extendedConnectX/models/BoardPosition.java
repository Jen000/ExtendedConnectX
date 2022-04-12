 package cpsc2150.extendedConnectX.models;
/**
 *
 * @author Jenna Adams
 * @version 1.0
 * @invariant row > 0 AND row < 10 AND
 *            column > 0 AND column < 7
 */
public class BoardPosition {

    private int row;
    private int column;
    /**
     * @param r to reference to a certain row in the board
     * @param c to reference to a certain column in the board
     *
     *
     * @pre 0 < r >= 9 AND 0 < c >= 6
     *
     * @post row = r AND column = c
     *
     */
    BoardPosition(int r, int c){
        row = r;
        column = c;
    }

    /**
     *
     * @return row
     *
     *
     * @post row = #row AND
     * column = #column AND
     * getRow() = row
     *
     */
    public int getRow(){
        return row;
    }

    /**
     *
     * @return the column selected
     *
     *
     * @post column = #column AND
     * row = #row AND
     * getColumn() = column
     *
     */
    public int getColumn(){
        return column;
    }

    /**
     *
     * @param x is instanceof BoardPosition
     *
     * @return true OR false depending on if two board positions are at the same row and column
     *
     * @pre BoardPosition
     *
     * @post BoardPosition = #BoardPosition AND
     * row = #row AND
     * column = #column AND
     * equals() = iff x.row == this.row && x.column == this.column return true, else false
     *
     */
    public boolean equals(Object x){
        // check if at the same memory location
        if (x == this){
            return true;
        }
        // if it's not an instance of BoardPosition
        if (!(x instanceof BoardPosition)){
            return false;
        }
        // cast object x to a BoardPosition
        BoardPosition y = (BoardPosition) x;
        // if the row and column data are the same return true, else false
        if (y.row == this.row && y.column == this.column){
            return true;
        }
        return false;
    }

    /**
     *
     * @return String of the entire board
     *
     *
     * @post BoardPosition = #BoardPosition AND
     * row = #row AND
     * column = #column AND
     * toString() = [string of x.row and x.column]
     *
     */
    @SuppressWarnings("unchecked")
    @Override
    public String toString(){
        return this.row + "," + this.column;
    }

}
