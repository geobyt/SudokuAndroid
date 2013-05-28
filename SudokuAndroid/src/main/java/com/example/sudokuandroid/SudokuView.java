package com.example.sudokuandroid;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class SudokuView extends SurfaceView implements SurfaceHolder.Callback {
    private Context mContext;
    private SudokuThread thread;

    public SudokuView(Context context, AttributeSet attrs){
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        thread = new SudokuThread(holder, context, new Handler() {

        });

        setFocusable(true); // make sure we get key events
    }

    public SudokuThread getThread() {
        return thread;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) thread.pause();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setSurfaceSize(width, height);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    class SudokuThread extends Thread {
        public static final int STATE_LOSE = 1;
        public static final int STATE_PAUSE = 2;
        public static final int STATE_READY = 3;
        public static final int STATE_RUNNING = 4;
        public static final int STATE_WIN = 5;

        private Bitmap mBackgroundImage;
        private int mCanvasHeight = 1;
        private int mCanvasWidth = 1;
        private Handler mHandler;
        private boolean mRun = false;
        private SurfaceHolder mSurfaceHolder;
        private int mMode; //state of the game
        private int mNumHeight;

        //number images
        private Drawable mNum1;
        private Drawable mNum2;
        private Drawable mNum3;
        private Drawable mNum4;
        private Drawable mNum5;
        private Drawable mNum6;
        private Drawable mNum7;
        private Drawable mNum8;
        private Drawable mNum9;

        //gameboard
        List<Integer> sudokuBoard = new ArrayList<Integer>();
        //this is hardcoded for testing, later will come from server
        private String hardcodedBoard = "006701800270309005000006004040800090603100580100530000400920708082070900930000250";

        public SudokuThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
            mSurfaceHolder = surfaceHolder;
            mHandler = handler;
            mContext = context;

            Resources res = context.getResources();
            mBackgroundImage = BitmapFactory.decodeResource(res, R.drawable.sudoku_board);

            //load the sudokuBoard array
            for (char c : hardcodedBoard.toCharArray()){
                sudokuBoard.add(Character.getNumericValue(c));
            }

            //cache number images
            mNum1 = context.getResources().getDrawable(R.drawable.num_1);
            mNum2 = context.getResources().getDrawable(R.drawable.num_2);
            mNum3 = context.getResources().getDrawable(R.drawable.num_3);
            mNum4 = context.getResources().getDrawable(R.drawable.num_4);
            mNum5 = context.getResources().getDrawable(R.drawable.num_5);
            mNum6 = context.getResources().getDrawable(R.drawable.num_6);
            mNum7 = context.getResources().getDrawable(R.drawable.num_7);
            mNum8 = context.getResources().getDrawable(R.drawable.num_8);
            mNum9 = context.getResources().getDrawable(R.drawable.num_9);
            mNumHeight = mNum1.getIntrinsicHeight();
        }

        public void doStart(){
            synchronized (mSurfaceHolder) {

            }
        }

        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        doDraw(c);
                    }
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        private void doDraw(Canvas canvas) {
            canvas.drawBitmap(mBackgroundImage, 0, 0, null);

            canvas.save();
            //render the board
            for (int i = 0; i < sudokuBoard.size(); i++){
                switch (sudokuBoard.get(i)){
                    case 0: break;
                    case 1: mNum1.setBounds((i%9)*36, (i/9)*36, (i%9)*36 + mNumHeight, (i/9)*36 + mNumHeight);
                        mNum1.draw(canvas);
                        break;
                    case 2: mNum2.setBounds((i%9)*36, (i/9)*36, (i%9)*36 + mNumHeight, (i/9)*36 + mNumHeight);
                        mNum2.draw(canvas);
                        break;
                    case 3: mNum3.setBounds((i%9)*36, (i/9)*36, (i%9)*36 + mNumHeight, (i/9)*36 + mNumHeight);
                        mNum3.draw(canvas);
                        break;
                    case 4: mNum4.setBounds((i%9)*36, (i/9)*36, (i%9)*36 + mNumHeight, (i/9)*36 + mNumHeight);
                        mNum4.draw(canvas);
                        break;
                    case 5: mNum5.setBounds((i%9)*36, (i/9)*36, (i%9)*36 + mNumHeight, (i/9)*36 + mNumHeight);
                        mNum5.draw(canvas);
                        break;
                    case 6: mNum6.setBounds((i%9)*36, (i/9)*36, (i%9)*36 + mNumHeight, (i/9)*36 + mNumHeight);
                        mNum6.draw(canvas);
                        break;
                    case 7: mNum7.setBounds((i%9)*36, (i/9)*36, (i%9)*36 + mNumHeight, (i/9)*36 + mNumHeight);
                        mNum7.draw(canvas);
                        break;
                    case 8: mNum8.setBounds((i%9)*36, (i/9)*36, (i%9)*36 + mNumHeight, (i/9)*36 + mNumHeight);
                        mNum8.draw(canvas);
                        break;
                    case 9: mNum9.setBounds((i%9)*36, (i/9)*36, (i%9)*36 + mNumHeight, (i/9)*36 + mNumHeight);
                        mNum9.draw(canvas);
                        break;
                }
            }
            canvas.restore();
        }

        public void pause() {
            synchronized (mSurfaceHolder) {
                if (mMode == STATE_RUNNING) setState(STATE_PAUSE);
            }
        }

        public void unpause() {
            synchronized (mSurfaceHolder) {
                //TODO: add logic here
            }
            setState(STATE_RUNNING);
        }

        public synchronized void restoreState(Bundle savedState) {
            synchronized (mSurfaceHolder) {
                setState(STATE_PAUSE);
            }
        }

        public void setState(int mode) {
            synchronized (mSurfaceHolder) {
                setState(mode, null);
            }
        }

        public void setState(int mode, CharSequence message) {
            synchronized (mSurfaceHolder) {
                mMode = mode;
            }
        }

        public void setRunning(boolean b) {
            mRun = b;
        }

        public void setSurfaceSize(int width, int height) {
            synchronized (mSurfaceHolder) {
                mCanvasWidth = width;
                mCanvasHeight = height;

                mBackgroundImage = Bitmap.createScaledBitmap(mBackgroundImage, width, height, true);
            }
        }

        public Bundle saveState(Bundle map) {
            synchronized (mSurfaceHolder) {
                if (map != null) {
                    //TODO: save state here
                }
            }
            return map;
        }
    }
}
