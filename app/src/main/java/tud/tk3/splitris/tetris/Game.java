package tud.tk3.splitris.tetris;


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

    public Game(BlockScreen screen, BlockScreen preview, int width, int height) {
        // main method for starting a new game
        mScreen = screen;
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
        mPreview.render();
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
        onNewElement();
    }

    public void setField(int x, int y, Cube cube) {
        mFields[x][y] = cube;
        mScreen.setActive(x, FIELD_HEIGHT-1-y, cube != null);
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
