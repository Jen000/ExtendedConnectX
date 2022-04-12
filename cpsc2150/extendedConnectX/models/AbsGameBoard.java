package cpsc2150.extendedConnectX.models;


public abstract class AbsGameBoard implements IGameBoard {
    /**
     *
     * (overrides object toString to make BoardGame 2d array of Characters into a string)
     *
     * @return string of Characters in the BoardGame 2d array
     *
     *
     * @pre none
     *
     *
     * @post
     *
     * toString() = toString() = [String of GameBoard 2d Array with readable formatting] AND
     *      * [GameBoard 2d array is 6 rows and 9 columns]
     *
     */
    @Override
    public String toString(){
        String print = "";
        print += "|";
        for (int i = 0; i < getNumColumns(); i++){
            print += " ";
            print += i;
            print += "|";
        }
        print += "\n";
        // flip rows to print out backwards
        for (int i = getNumRows()-1; i >= 0; i--){
            print += "|";
            for (int j = 0; j < getNumColumns(); j++){
                BoardPosition temp = new BoardPosition(i,j);
                print += ' ';
                print += whatsAtPos(temp);
                print += "|";
            }
            print += "\n";
        }
        return print;

    }
}
