package tud.tk3.splitris.tetris;

import android.graphics.Color;

import java.util.ArrayList;

import tud.tk3.splitris.network.Player;
import tud.tk3.splitscreen.filter.HightlightFilter;
import tud.tk3.splitscreen.output.ScreenView;
import tud.tk3.splitscreen.output.Viewport;
import tud.tk3.splitscreen.screen.BlockScreen;

public class Initiator {

    private final static int SCREEN_BLOCKWIDTH  = 15;
    private final static int SCREEN_BLOCKHEIGHT = 20;
    private final static int SCREEN_BLOCKOVERLAP  = 1;
    private final static int TETRIX_BLOCKLEN  = 1;

    public BlockScreen configureBlockScreens(ArrayList<Player> list, ScreenView localView) {
        final int height = SCREEN_BLOCKHEIGHT;
        final int width = list.size() * SCREEN_BLOCKWIDTH;
        final BlockScreen bs = new BlockScreen(TETRIX_BLOCKLEN, width, height);
        bs.setOccupied(Color.BLUE);

        final int viewwidth    = TETRIX_BLOCKLEN * SCREEN_BLOCKWIDTH;
        final int viewheight   = TETRIX_BLOCKLEN * SCREEN_BLOCKHEIGHT;
        final int overlapwidth = TETRIX_BLOCKLEN * SCREEN_BLOCKOVERLAP;




        int i=0;
        for (Player p : list) {
            boolean first = false, last = false;
            if(i == 0) first = true;
            if(i == list.size() - 1) last = true;

            // calculate width of viewport.
            // first and last only have one overlap, all other have two
            int vpwidth = viewwidth;
            if(!first) vpwidth += overlapwidth;
            if(!last) vpwidth += overlapwidth;

            int vpleft = 0;
            if(!first) vpleft = i * viewwidth - overlapwidth;

            Viewport vp = new Viewport(bs, vpleft, 0, vpwidth, viewheight);

            HightlightFilter filter = new HightlightFilter(overlapwidth, !first, !last);
            vp.addFilter(filter);

            if(p.getConnection() != null) {
                vp.addView(p.getConnection().getRemoteView(0));
            } else {
                vp.addView(localView);
            }

            i++;
        }







/*        new Thread() {
            @Override
            public void run() {


                try {

                    boolean active = true;
                    while(true) {
                        for(int y=0;y<height;y++) {
                            for(int x=0;x<width;x++) {
                                bs.setActive(x, y, active);
                                bs.render();
                                Thread.sleep(25);
                            }
                        }
                        active = !active;
                    }

                } catch (InterruptedException e) {

                }
            }
        }.start();
*/
        return bs;
    }
}
