package com.example.sudokuandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.sudokuandroid.SudokuView.SudokuThread;

public class PlayGame extends Activity {
    /** A handle to the thread that's actually running the animation. */
    private SudokuThread mSudokuThread;

    /** A handle to the View in which the game is running. */
    private SudokuView mSudokuView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        mSudokuView = (SudokuView) findViewById(R.id.sudoku);
        mSudokuThread = mSudokuView.getThread();

        if (savedInstanceState == null) {
            // we were just launched: set up a new game
            mSudokuThread.setState(SudokuThread.STATE_READY);
            //Log.w(this.getClass().getName(), "SIS is null");
        } else {
            // we are being restored: resume a previous game
            mSudokuThread.restoreState(savedInstanceState);
            //Log.w(this.getClass().getName(), "SIS is nonnull");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSudokuView.getThread().pause(); // pause game when Activity pauses
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSudokuThread.saveState(outState);
        //Log.w(this.getClass().getName(), "SIS called");
    }
}
