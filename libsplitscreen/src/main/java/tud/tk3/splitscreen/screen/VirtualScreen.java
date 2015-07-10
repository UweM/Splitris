package tud.tk3.splitscreen.screen;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

import tud.tk3.splitscreen.output.Viewport;

public class VirtualScreen extends Canvas {
    // virtual screen class - contains and manages the overall image of the later so called split screen
    // provides the abstraction layer necessary to handle all kind of underlying image technologies

    private Bitmap mBitmap;
    private int mWidth, mHeight;
    private ArrayList<Viewport> mViewports = new ArrayList<>();

    public VirtualScreen(int width, int height) {
        // create virtual screen
        mWidth = width;
        mHeight = height;
        createBitmap();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void redraw() {
        createBitmap();
    }

    private void createBitmap() {
        // create our bitmap (initially & after a redraw)
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_4444);
        setBitmap(mBitmap);
    }

    public int getVirtualWidth() {
        return mWidth;
    }

    public int getVirtualHeight() {
        return mHeight;
    }

    public void registerViewport(Viewport vp) {
        mViewports.add(vp);
    }

    public void render() {
        // render ALL the viewports
        for(Viewport vp : mViewports) {
            vp.render();
        }
    }
}
