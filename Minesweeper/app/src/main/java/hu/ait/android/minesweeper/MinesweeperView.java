package hu.ait.android.minesweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MinesweeperView extends View {

    private Paint paintBg;
    private Paint paintLine;
    private Paint paintNumber;
    private Bitmap bitmapFlag;
    private Bitmap bitmapBomb;
    private boolean gameOver = false;

    public MinesweeperView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.argb(200, 125, 207, 212));
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStrokeWidth(5);
        paintLine.setStyle(Paint.Style.STROKE);

        paintNumber = new Paint();
        paintNumber.setColor(Color.WHITE);
        paintNumber.setTextSize(110);
        paintNumber.setStrokeWidth(5);
        paintNumber.setStyle(Paint.Style.FILL);

        bitmapFlag = BitmapFactory.decodeResource(getResources(), R.drawable.flag);
        bitmapBomb = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmapFlag = Bitmap.createScaledBitmap(bitmapFlag, getWidth()/6, getHeight()/6, false);
        bitmapBomb = Bitmap.createScaledBitmap(bitmapBomb, getWidth()/6, getHeight()/6, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,getWidth(),getHeight(), paintBg);
        drawGameArea(canvas);
        drawFields(canvas);
    }

    private void drawGameArea(Canvas canvas) {
        canvas.drawRect(0,0,getWidth(),getHeight(), paintLine);

        canvas.drawLine(getWidth() / 5, 0, getWidth() / 5, getHeight(), paintLine);
        canvas.drawLine(2 * (getWidth() / 5), 0, 2 * (getWidth() / 5), getHeight(), paintLine);
        canvas.drawLine(3 * (getWidth() / 5), 0, 3 * (getWidth() / 5), getHeight(), paintLine);
        canvas.drawLine(4 * (getWidth() / 5), 0, 4 * (getWidth() / 5), getHeight(), paintLine);

        canvas.drawLine(0, getHeight() / 5, getWidth(), getHeight() / 5, paintLine);
        canvas.drawLine(0, 2 * (getHeight() / 5), getWidth(), 2 * (getHeight() / 5), paintLine);
        canvas.drawLine(0, 3 * (getHeight() / 5), getWidth(), 3 * (getHeight() / 5), paintLine);
        canvas.drawLine(0, 4 * (getHeight() / 5), getWidth(), 4 * (getHeight() / 5), paintLine);
    }

    private void drawFields(Canvas canvas) {
        int widthGrid = getWidth() / 5;
        int heightGrid = getHeight() / 5;
        int flagBombs = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Field field = MinesweeperModel.getInstance().getField(i, j);

                if (!gameOver && field.isHasUserClickHere()){
                    if (field.isFlag()) {
                        canvas.drawBitmap(bitmapFlag, (i+(float)0.1) * widthGrid, (j+(float)0.1) * heightGrid, null);
                        flagBombs++;
                    } else {
                        canvas.drawText(String.valueOf(field.getNumOfNearbyMines()), (i+(float)0.25) * widthGrid, (j+1-(float)0.15) * heightGrid, paintNumber);
                    }
                } else if (gameOver) {
                    if (field.isMine()) {
                        if (field.isFlag()) {
                            canvas.drawBitmap(bitmapFlag, (i+(float)0.1) * widthGrid, (j+(float)0.1) * heightGrid,null);
                        } else {
                            canvas.drawBitmap(bitmapBomb, (i+(float)0.1) * widthGrid, (j+(float)0.1) * heightGrid, null);
                        }
                    } else if (field.isFlag()) {
                        canvas.drawBitmap(bitmapFlag, (i+(float)0.1) * widthGrid, (j+(float)0.1) * heightGrid,null);
                        canvas.drawLine(i * getWidth() / 5, j * getHeight() / 5,
                                (i + 1) * getWidth() / 5, (j + 1) * getHeight() / 5, paintLine);
                        canvas.drawLine((i + 1) * getWidth() / 5, j * getHeight() / 5,
                                i * getWidth() / 5, (j + 1) * getHeight() / 5, paintLine);
                    } else if (field.isHasUserClickHere()) {
                        canvas.drawText(String.valueOf(field.getNumOfNearbyMines()),(i+(float)0.25) * widthGrid, (j+1-(float)0.15) * heightGrid, paintNumber);
                    }
                }
            }
        }
        if (flagBombs == 3){
            gameOver = true;
            ((MainActivity)getContext()).gameWinMsg();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!gameOver && event.getAction() == MotionEvent.ACTION_DOWN) {

            int tX = ((int) event.getX()) / (getWidth() / 5);
            int tY = ((int) event.getY()) / (getHeight() / 5);

            boolean placeFlag = ((MainActivity) getContext()).placeFlag();

            if (!MinesweeperModel.getInstance().getField(tX, tY).isHasUserClickHere()) {
                MinesweeperModel.getInstance().getField(tX, tY).setHasUserClickHere(true);
                if (placeFlag) {
                    MinesweeperModel.getInstance().getField(tX, tY).setFlag(true);
                    if (!MinesweeperModel.getInstance().getField(tX, tY).isMine()) {
                        gameOver = true;
                        ((MainActivity)getContext()).gameEndMsg();
                    }
                } else if (MinesweeperModel.getInstance().getField(tX, tY).isMine()) {
                    gameOver = true;
                    ((MainActivity)getContext()).gameEndMsg();
                }
            }
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void clearBoard(){
        gameOver = false;
        MinesweeperModel.getInstance().setFieldModel();
        ((MainActivity)getContext()).resetGame();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }
}
