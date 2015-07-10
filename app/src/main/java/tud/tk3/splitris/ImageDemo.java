package tud.tk3.splitris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import tud.tk3.splitris.network.Player;
import tud.tk3.splitscreen.filter.HightlightFilter;
import tud.tk3.splitscreen.output.ScreenView;
import tud.tk3.splitscreen.output.Viewport;
import tud.tk3.splitscreen.screen.VirtualScreen;

public class ImageDemo {
    private final static String TAG = "ImageDemo";

    public static void showImage(Context ctx, ArrayList<Player> list, Uri uri, ScreenView localView) {

        final int maxWidth = 300 * list.size();
        final int maxHeight = 1200;


        Bitmap img = null;
        try {
            img = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), uri);
        }catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if(img.getWidth() > maxWidth || img.getHeight() > maxHeight) {
            img = Bitmap.createScaledBitmap(img, maxWidth, maxHeight, false);
        }

        VirtualScreen vs = new VirtualScreen(img.getWidth(), img.getHeight());


        Log.d(TAG, "loading image was successful, size: " + img.getHeight() + "x" + img.getWidth());

        vs.drawBitmap(img,0, 0, new Paint());

        int vpwidth = img.getWidth() / list.size();
        int vpleft = 0;


        for (Player p : list) {
            int twidth = vpwidth;
            // in the last viewport, the width may be less than a full width
            if(vpleft + vpwidth > img.getWidth())
                twidth = img.getWidth() - vpleft;

            Viewport vp = new Viewport(vs, vpleft, 0, twidth, img.getHeight());
            vpleft += vpwidth;
            if(p.getConnection() != null) {
                vp.addView(p.getConnection().getRemoteView(0));
            } else {
                vp.addView(localView);
            }
        }
        vs.render();
    }
}
