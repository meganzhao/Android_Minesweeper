package hu.ait.android.minesweeper;

import java.util.Random;

public class MinesweeperModel {

    private static MinesweeperModel minesweeperModel = null;
    private Field[][] FieldModel = new Field[5][5];

    private MinesweeperModel() {
        setFieldModel();
    }

    public static MinesweeperModel getInstance() {
        if (minesweeperModel == null) {
            minesweeperModel = new MinesweeperModel();
        }
        return minesweeperModel;
    }

    public Field getField(int i, int j){
        return FieldModel[i][j];
    }

    public void setFieldModel(){
        createFields();
        setMines();
        setNumOfMines();
    }

    public void createFields() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Field field = new Field();
                field.setX(i);
                field.setY(j);
                field.setMine(false);
                field.setHasUserClickHere(false);
                field.setFlag(false);
                FieldModel[i][j] = field;
            }
        }
    }

    public void setMines() {
        int numOfMines = 0;
        Random random = new Random();
        while (numOfMines < 3) {
            int randomNumber = random.nextInt(25);
            Field randomField = FieldModel[randomNumber/5][randomNumber%5];
            if (!randomField.isMine()) {
                randomField.setMine(true);
                numOfMines++;
            }
        }
    }

    public void setNumOfMines() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!FieldModel[i][j].isMine())
                    FieldModel[i][j].setNumOfNearbyMines(getNumOfMines(i, j));
            }
        }
    }


    public int getNumOfMines(int i, int j) {
        int numOfMines = 0;
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (i+k >= 0 && i+k < 5 && j+l >= 0 && j+l < 5){
                    if (FieldModel[i+k][j+l].isMine())
                        numOfMines++;
                }
            }
        }
        return numOfMines;
    }
}
