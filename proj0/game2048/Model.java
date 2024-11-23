package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    public static final int[] drow_arr = new int[]{1,0,-1,0};
    public static final int[] dcolumn_arr = new int[]{0,1,0,-1};

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        board.setViewingPerspective(side);
        int len = board.size();
        //这里是处理了所有的列
        for (int c = 0; c < len; c++) {
            if(processColumn(c)) {
                changed = true;
            }
        }
        board.setViewingPerspective(Side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** process every column
     * 其实这里还可以用一个helper函数来得到每一个砖块要移动到的行数
     * */
    private boolean processColumn(int column) {
        boolean flag = false;
        boolean[] row_moved = {false,false,false,false};
       for (int r = board.size() - 2; r >= 0;r--) {
           Tile t = board.tile(column, r);
           //如果当前砖块是空，直接不处理
           if (t == null) continue;
           int k = getMoveRow(column, r, row_moved);
           if( k != r) {
               flag = true;
           }
           //这里只能判断是否发生了合并
           if (board.move(column, k, t)) {
               this.score += t.value() * 2;
           }
       }
       return flag;
    }

    /** 获取每一个砖块要移动到的行数
     * */
    private int getMoveRow(int column, int r, boolean[] row_moved) {
       //应该首先处理上面砖块是空的情况
       int k = r;
       Tile t = board.tile(column, r);
       //跳出时，k==board.size() - 1 或者 碰到非空块
       while(k < board.size()-1 && board.tile(column,k+1) == null) {
           k++;
       }
        //如果k == board.size(),说明此砖块的上面就全为空，直接移动到最上面
       if(k == board.size() - 1) {
           return k;
       } else {
           //如果此砖块的值和空之上的一块砖值相同，那么也移动到k+1行对应的砖块处，此时发生合并
           if (t.value() == board.tile(column, k + 1).value() && ! row_moved[k + 1]) {
               row_moved[k + 1] = true;
               return k + 1;
           } else { //这里也可能是不发生移动
                   return k;
           }
       }
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        int len = b.size();
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                if (b.tile(j,i) == null) {
                   return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        int len = b.size();
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                Tile t = b.tile(j,i);
                if(t == null)continue;
                if(t.value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this functiono.
        if (emptySpaceExists(b)) {
            return true;
        }
        int len = b.size();
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                //没有空块，看块的上下左右能否合并
                for(int k = 0; k < 4; k++) {
                    int drow = i + drow_arr[k];
                    int dcolumn = j + dcolumn_arr[k];
                    if(drow >=0 && drow < len && dcolumn >=0 && dcolumn < len && b.tile(dcolumn,drow) != null) {
                        if(b.tile(dcolumn,drow).value() == b.tile(j,i).value()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal.*/
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
