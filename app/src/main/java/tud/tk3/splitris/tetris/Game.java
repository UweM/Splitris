package tud.tk3.splitris.tetris;


import java.util.Random;

import tud.tk3.splitscreen.screen.BlockScreen;

public class Game {

    public static int FIELD_HEIGHT;
    public static int FIELD_WIDTH;

    private Cube[][] mFields;
    private Element mActiveElement;
    private int mNextItem;
    private Random rnd;
    private BlockScreen mScreen;
    private boolean mGameRunning = true;

    public Game(BlockScreen screen, int width, int height) {
        mScreen = screen;
        FIELD_WIDTH = width;
        FIELD_HEIGHT = height;
        mFields = new Cube[FIELD_WIDTH][FIELD_HEIGHT];
        rnd = new Random(System.currentTimeMillis());
        mActiveElement = new Element(this, this.rnd.nextInt(ElementTemplate.COUNT));
        mNextItem = this.rnd.nextInt(ElementTemplate.COUNT);
    }

    public boolean tick() {
        if (!this.mActiveElement.moveDown()) {
            int MoveDown = 0;
            for (int y = 0; y < FIELD_HEIGHT; y++) {
                boolean  ltest = true;
                for (int x = 0; x < FIELD_WIDTH; x++) {
                    if (this.mFields[x][y] == null) ltest = false;
                    if (MoveDown > 0) this.setField(x, y - MoveDown, this.mFields[x][y]);
                }

                if (ltest) {
                    MoveDown++;
                }
            }
            //if (MoveDown > 0) Tetris.form.LinesComplete(MoveDown);
            this.mActiveElement = new Element(this, this.mNextItem);
            this.mNextItem = this.rnd.nextInt(ElementTemplate.COUNT);
            //Tetris.form.NewElement();
        }
        mScreen.render();
        return mGameRunning;
    }

    public void fastTick(){
        while(this.mActiveElement.moveDown());
    }

    public void setField(int x, int y, Cube cube) {
        this.mFields[x][y] = cube;
        mScreen.setActive(x, FIELD_HEIGHT-1-y, cube != null);
    }

    public Cube getField(int x, int y) {
        return mFields[x][y];
    }

    public void GameOver() {
        mGameRunning = false;
    }

    public boolean moveX(boolean toLeft) {
        boolean ret = this.mActiveElement.moveX(toLeft);
        mScreen.render();
        return ret;
    }

    public boolean moveRight() {
        return this.moveX(false);
    }

    public boolean moveLeft() {
        return this.moveX(true);
    }

    public boolean rotate() {
        boolean ret = this.mActiveElement.rotate();
        mScreen.render();
        return ret;
    }
}
