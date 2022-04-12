package cpsc2150.extendedConnectX.models;

import java.util.*;

public class GameBoardMem extends AbsGameBoard implements IGameBoard{

    private Map<Character, ArrayList<BoardPosition>> boardMap;
    private int tokensToWin;
    private int rows;
    private int columns;

    //constructor
    GameBoardMem(int inputTokens, int inputRows, int inputColumns){
        tokensToWin = inputTokens;
        rows = inputRows;
        columns = inputColumns;
        boardMap = new TreeMap<Character, ArrayList<BoardPosition>>();
    }


    public void placeToken(char p, int c){
        Character ch = p;
        if(boardMap.size() == 0){
            BoardPosition temp = new BoardPosition(0,c);
            ArrayList<BoardPosition> list = new ArrayList<BoardPosition>();
            list.add(temp);
            boardMap.put(ch, list);
        }
        else if(boardMap.containsKey(ch)){
            ArrayList<BoardPosition> list = boardMap.get(ch);
            BoardPosition pos = null;
            for (int i = getNumRows()-1; i >= 0; i--){
                pos = new BoardPosition(i,c);
                if (whatsAtPos(pos) != ' '){
                    pos = new BoardPosition(i+1,c);
                    i = -1;
                }
                else if ( i-1 < 0){
                    pos = new BoardPosition(i,c);
                    i = -1;
                }
            }
            list.add(pos);
            boardMap.replace(ch, list);
        }
        else{
            ArrayList<BoardPosition> list = new ArrayList<BoardPosition>();
            BoardPosition pos = null;
            for (int i = getNumRows()-1; i >= 0; i--){
                pos = new BoardPosition(i,c);
                if (whatsAtPos(pos) != ' '){
                    pos = new BoardPosition(i+1,c);
                }
                else{
                    pos = new BoardPosition(i,c);
                }
            }
            list.add(pos);
            boardMap.put(ch, list);
        }
    }


    public char whatsAtPos(BoardPosition pos){
        if (boardMap.size() > 0){
           for (Map.Entry<Character, ArrayList<BoardPosition>> entry : boardMap.entrySet()){
               Character key = entry.getKey();
               ArrayList<BoardPosition> list = boardMap.get(key);
               for (int i = 0; i < list.size(); i++){
                   if (list.get(i).equals(pos)){
                       return key;
                   }
               }
           }
        }
        else{
            return ' ';
        }
        return ' ';
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isPlayerAtPos(BoardPosition pos, char player){
        Character ch = player;
        ArrayList<BoardPosition> list = new ArrayList<BoardPosition>();
        list = boardMap.get(ch);
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).equals(pos)){
                return true;
            }
        }
        return false;
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
