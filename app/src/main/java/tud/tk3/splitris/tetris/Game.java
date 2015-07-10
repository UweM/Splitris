package tud.tk3.splitris.tetris;


import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;

import java.util.Random;

import tud.tk3.splitscreen.screen.BlockScreen;

public class Game {
    // class to define a tetris game

    public static int FIELD_HEIGHT;
    public static int FIELD_WIDTH;

    private Cube[][] mFields;
    private Element mActiveElement;
    private int mNextItem;
    private Random rnd;
    private BlockScreen mScreen;
    private BlockScreen mPreview;
    private boolean mGameRunning = true;
    private int mMoveDown;
    private int mPoints;
    private int mlevel;

    public Game(BlockScreen screen, BlockScreen preview, int width, int height) {
        // main method for starting a new game
        mScreen = screen;
        mPoints = 0;
        mMoveDown = 0;
        mlevel = 1;
        mPreview = preview;
        FIELD_WIDTH = width;
        FIELD_HEIGHT = height;
        mFields = new Cube[FIELD_WIDTH][FIELD_HEIGHT];
        rnd = new Random(System.currentTimeMillis());
        mActiveElement = new Element(this, rnd.nextInt(ElementTemplate.COUNT));
        mNextItem = rnd.nextInt(ElementTemplate.COUNT);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                onNewElement();
                return null;
            }
        }.execute();
    }

    public Boolean isGameRunning()
    {
        return mGameRunning;
    }

    public boolean tick() {
        // method to handle one tick
        if (!mActiveElement.moveDown()) {
            onLineComplete();
        }
        mScreen.render();
        return mGameRunning;
    }

    public void fastTick(){
        while(mActiveElement.moveDown());
        onLineComplete();
        mScreen.render();
    }

    private void onNewElement() {
        boolean[][] fields = ElementTemplate.getFields(mNextItem);
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                mPreview.setActive(x, 3 - y, fields[x][y]);
            }
        }

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(10);
        paint.setStyle(Paint.Style.FILL);

        mPreview.redraw();
        mPreview.drawText(Integer.toString(mPoints), 0, 23, paint);
        mPreview.render();
    }

    private void calculatePoints()
    {
        // calculate the achieved points for each game
        // Rules: 40 * level for one complete row, 100 * level for two complete rows, 300 * level for three complete rows, 1200 * level for four complete rows.
        if (mMoveDown == 1)
        {
            mPoints += 40 * mlevel ;
        }
        else if (mMoveDown == 2)
        {
            mPoints += 100 * mlevel ;
        }
        else if (mMoveDown == 3)
        {
            mPoints += 300 * mlevel ;
        }
        else if (mMoveDown >= 4)
        {
            mPoints += 1200 * mlevel ;
        }
    }

    private void onLineComplete() {
        // method to handle line completion
        int MoveDown = 0;
        for (int y = 0; y < FIELD_HEIGHT; y++) {
            boolean  ltest = true;
            for (int x = 0; x < FIELD_WIDTH; x++) {
                if (mFields[x][y] == null) ltest = false;
                if (MoveDown > 0) setField(x, y - MoveDown, mFields[x][y]);
            }
            if (ltest) {
                MoveDown++;
            }
        }
        //if (MoveDown > 0) Tetris.form.LinesComplete(MoveDown);
        mActiveElement = new Element(this, mNextItem);
        mNextItem = rnd.nextInt(ElementTemplate.COUNT);
        mMoveDown = MoveDown; // save completed rows
        calculatePoints(); // after this points are actualized
        onNewElement();
    }

    public void setField(int x, int y, Cube cube) {
        mFields[x][y] = cube;
        mScreen.setActive(x, FIELD_HEIGHT - 1 - y, cube != null);
    }

    public int getmMoveDown()
    {
        return mMoveDown;
    }

    public Cube getField(int x, int y) {
        return mFields[x][y];
    }

    public void GameOver() {
        mGameRunning = false;
    }

    public boolean moveX(boolean toLeft) {
        boolean ret = mActiveElement.moveX(toLeft);
        mScreen.render();
        return ret;
    }

    public boolean moveRight() {
        return moveX(false);
    }

    public boolean moveLeft() {
        return moveX(true);
    }

    public boolean rotate() {
        boolean ret = mActiveElement.rotate();
        mScreen.render();
        return ret;
    }
}
