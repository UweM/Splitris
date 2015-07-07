package tud.tk3.splitscreen.filter;

import android.graphics.Bitmap;


public class HightlightFilter implements IViewportFilter{

    private Boolean mAddLeft, mAddRight;
    private int mHighlightWidth;
    public HightlightFilter(int highlightWidth, Boolean addLeft, Boolean addRight) {
        mHighlightWidth = highlightWidth;
        mAddLeft = addLeft;
        mAddRight = addRight;
    }

    @Override
    public void apply(Bitmap b) {

        int mask = 0x00ff0000; //set all red color bits

        if(mAddLeft)
        {
            for(int i=0; i<mHighlightWidth; ++i) {
                for(int j=0; j<b.getHeight(); ++j){
                    setHighlight(b, i, j, mask);
                }
            }
        }

        if(mAddRight)
        {
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
