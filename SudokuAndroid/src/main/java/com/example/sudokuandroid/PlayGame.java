package com.example.sudokuandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sudokuandroid.SudokuView.SudokuThread;
import com.example.sudokuandroid.models.ConversationItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        getActionBar().hide();

        List<ConversationItem> chatArray = new ArrayList<ConversationItem>() {{
            add(new ConversationItem("ken2@yogax", "hello"));
            add(new ConversationItem("ken2@yogax", "world"));
            add(new ConversationItem("ken2@yogax", "yay"));
        }};

        ChatArrayAdapter adapter = new ChatArrayAdapter(this, R.layout.chat_list_item, chatArray);

        ListView listView = (ListView)findViewById(R.id.chatListView);
        listView.setAdapter(adapter);
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
