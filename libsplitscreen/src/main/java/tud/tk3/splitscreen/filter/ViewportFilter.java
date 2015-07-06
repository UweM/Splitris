package tud.tk3.splitscreen.filter;

import android.graphics.Bitmap;

/**
 * Created by fee on 7/6/15.
 */
public class ViewportFilter implements IViewportFilter{

    @Override
    public void apply(Bitmap b, Boolean addLeft, Boolean addRight) {

        int mask = 0x00ff0000; //set all red color bits
        int highlightWidth = b.getWidth()/15;

        if(addLeft)
        {
            for(int i=0; i<highlightWidth; ++i) {
                for(int j=0; j<b.getHeight(); ++j){
                    setHighlight(b, i, j, mask);
                }
            }
        }

        if(addRight)
        {
            for(int i=b.getWidth()-highlightWidth; i<b.getWidth(); ++i) {
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
