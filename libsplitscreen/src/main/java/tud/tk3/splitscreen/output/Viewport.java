package tud.tk3.splitscreen.output;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import tud.tk3.splitscreen.screen.VirtualScreen;
import tud.tk3.splitscreen.filter.IViewportFilter;

public class Viewport {
    // class for handling our viewport (+ low level bitmap rendering)

    private ArrayList<IViewportFilter> mFilters = new ArrayList<>();
    private ArrayList<IScreenView> mViews = new ArrayList<>();
    private VirtualScreen mScreen;
    private int mLeft, mTop, mWidth, mHeight;
    private Bitmap mLastBitmap;

    public Viewport(VirtualScreen screen, int left, int top, int width, int height) {
        // create new viewport
        mScreen = screen;
        mLeft = left;
        mTop = top;
        mWidth = width;
        mHeight = height;
        screen.registerViewport(this);
    }

    public void addFilter(IViewportFilter filter) {
        // add the selected filter
        mFilters.add(filter);
    }

    public void addView(IScreenView view) {
        mViews.add(view);
    }

    public void render() {
        // rending method to render the bitmap
        Bitmap newBitmap = renderBitmap();
        if(!newBitmap.sameAs(mLastBitmap)) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            newBitmap.compress(Bitmap.CompressFormat.PNG, 9, stream);
            mLastBitmap = newBitmap;

            for(IScreenView view : mViews) {
                view.setBitmap(stream.toByteArray());
            }
        }
    }

    private Bitmap renderBitmap() {
        // actuall render bitmap & crop a piece from the screen bitmap
        Bitmap b = Bitmap.createBitmap(mScreen.getBitmap(), mLeft, mTop, mWidth, mHeight);

        for(IViewportFilter filter : mFilters) {
            filter.apply(b);
        }

        return b;
    }

}
