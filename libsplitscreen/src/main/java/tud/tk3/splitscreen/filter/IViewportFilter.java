package tud.tk3.splitscreen.filter;

import android.graphics.Bitmap;

public interface IViewportFilter {
    // Defining an interface for the filtering of the viewport
    void apply(Bitmap b);
}
