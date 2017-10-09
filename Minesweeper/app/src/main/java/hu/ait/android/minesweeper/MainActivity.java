package hu.ait.android.minesweeper;

import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private MinesweeperView minesweeperview;
    private LinearLayout layoutContent;
    private Chronometer chronometer;
    private Switch btnSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minesweeperview = findViewById(R.id.MinesweeperView);
        layoutContent = findViewById(R.id.layoutContent);
        chronometer = findViewById(R.id.chronometer);
        btnSwitch = findViewById(R.id.btnSwitch);
        Button btnResetGame = findViewById(R.id.reset_game);

        btnResetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minesweeperview.clearBoard();
            }
        });

        chronometer.start();
    }

    public boolean placeFlag(){
        return btnSwitch.isChecked();
    }

    public void gameEndMsg(){
        chronometer.stop();
        Snackbar.make(layoutContent, R.string.game_over_msg, Snackbar.LENGTH_LONG).setAction(
                R.string.start_new_game, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        minesweeperview.clearBoard();
                    }
                }
        ).show();
    }

    public void gameWinMsg(){
        chronometer.stop();
        Snackbar.make(layoutContent, R.string.game_win_msg, Snackbar.LENGTH_LONG).setAction(
                R.string.start_new_game, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        minesweeperview.clearBoard();
                    }
                }
        ).show();
    }

    public void resetGame(){
        btnSwitch.setChecked(false);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }
}
