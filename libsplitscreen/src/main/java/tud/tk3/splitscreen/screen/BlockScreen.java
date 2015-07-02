package tud.tk3.splitscreen.screen;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;


public class BlockScreen extends VirtualScreen {
    boolean[][] mBlocks;
    Bitmap mOccupied;
    Bitmap mVacant;
    Paint mPaint = new Paint();
    int mSidelength, mWidth, mHeight;

    public BlockScreen(int sidelength, int width, int height) {
        super(sidelength*width, sidelength*height);
        mBlocks = new boolean[width][height];
        mSidelength = sidelength;
        mWidth = width;
        mHeight = height;
        setVacant(Color.GRAY);
        redraw();
    }

    public void setVacant(int color) {
        mVacant = fillBlock(color);
    }

    public void setVacant(Bitmap b) {
        mVacant = scaleBlock(b);
    }

    public void setOccupied(int color) {
        mOccupied = fillBlock(color);
    }

    public void setOccupied(Bitmap b) {
        mOccupied = scaleBlock(b);
    }


    public void setActive(int x, int y, boolean active) throws IllegalArgumentException {
        checkBounds(x, y);
        mBlocks[x][y] = active;
        drawBlock(x, y);
    }

    public boolean isActive(int x, int y) throws IllegalArgumentException {
        checkBounds(x, y);
        return mBlocks[x][y];
    }

    public void redraw() {
        super.redraw();
        for(int x=0;x<mWidth;x++) {
            for (int y = 0; y < mHeight; y++) {
                drawBlock(x, y);
            }
        }
    }

    private Bitmap fillBlock(int color) {
        Bitmap b = Bitmap.createBitmap(mSidelength, mSidelength, Bitmap.Config.ARGB_4444);
        for(int x=0;x<mSidelength;x++) {
            for (int y = 0; y < mSidelength; y++) {
                b.setPixel(x, y, color);
            }
        }
        return b;
    }

    private Bitmap scaleBlock(Bitmap b) {
        return Bitmap.createScaledBitmap(b, mSidelength, mSidelength, false);
    }

    private void checkBounds(int x, int y) throws IllegalArgumentException {
        if(x < 0 || x >= mWidth || y < 0 || y >= mHeight)
            throw new IllegalArgumentException("Index out of bound");
    }

    private void drawBlock(int x, int y) {
        drawBitmap(mBlocks[x][y] ? mOccupied : mVacant, x*mSidelength, y*mSidelength, mPaint);
    }
}
