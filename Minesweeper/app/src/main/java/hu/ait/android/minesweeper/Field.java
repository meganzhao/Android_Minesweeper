package hu.ait.android.minesweeper;

/**
 * Created by zhaozhaoxia on 9/26/17.
 */

public class Field {

    private int x;
    private int y;
    private int numOfNearbyMines;
    private boolean isMine;
    private boolean hasUserClickHere;
    private boolean isFlag;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNumOfNearbyMines() {
        return numOfNearbyMines;
    }

    public void setNumOfNearbyMines(int numOfNearbyMines) {
        this.numOfNearbyMines = numOfNearbyMines;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isHasUserClickHere() {
        return hasUserClickHere;
    }

    public void setHasUserClickHere(boolean hasUserClickHere) {
        this.hasUserClickHere = hasUserClickHere;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }
}