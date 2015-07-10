package tud.tk3.splitscreen.filter;

import android.graphics.Bitmap;


public class HightlightFilter implements IViewportFilter{
    // class implementing the viewport filter for handling the filtering on the bitmap

    private Boolean mAddLeft, mAddRight;
    private int mHighlightWidth;
    public HightlightFilter(int highlightWidth, Boolean addLeft, Boolean addRight) {
        mHighlightWidth = highlightWidth;
        mAddLeft = addLeft;
        mAddRight = addRight;
    }

    @Override
    public void apply(Bitmap b) {
        // method to apply the filtering onto the bitmap

        int mask = 0x00ff0000; //set all red color bits (currently our default masking scheme)

        if(mAddLeft)
        {
            // calculate left addition
            for(int i=0; i<mHighlightWidth; ++i) {
                for(int j=0; j<b.getHeight(); ++j){
                    setHighlight(b, i, j, mask);
                }
            }
        }

        if(mAddRight)
        {
            // calculate right addition
            for(int i=b.getWidth()-mHighlightWidth; i<b.getWidth(); ++i) {
                for(int j=0; j<b.getHeight(); ++j){
                    setHighlight(b, i, j, mask);
                }
            }
        }
    }

    private void setHighlight(Bitmap b, int i, int j, int mask)
    {
        int color = b.getPixel(i,j);
        color |= mask;
        b.setPixel(i,j,color);
    }
}
