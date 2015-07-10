package tud.tk3.splitris;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

import tud.tk3.splitris.network.GameController;
import tud.tk3.splitris.network.Player;
import tud.tk3.splitris.tetris.Initiator;
import tud.tk3.splitscreen.output.ScreenView;
import tud.tk3.splitscreen.output.Viewport;
import tud.tk3.splitscreen.screen.BlockScreen;
import tud.tk3.splitscreen.screen.VirtualScreen;

public class ImageActivity extends Activity {
    // class for handling the image activity

    private final static String TAG = "ImageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // invoked when starting the activity - load all configurations
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageactivity);

        ScreenView image_screen = (ScreenView) findViewById(R.id.image_screen);

        Bundle extras = getIntent().getExtras();
        Uri selected_image = null;

        if (extras != null) {
            selected_image = Uri.parse(extras.getString("SELECTED_IMAGE"));
        }

        Log.d(TAG, "selected image name: " + selected_image);

        //We are at the Client
        if(ImageContext.Client != null) {
            ImageContext.Client.registerView(0, image_screen);

            ImageContext.initClient();
        }
        //We are at the Server
        else {
            //initServer() was already executed at this point

            Bitmap my_image = null;
            try {
                my_image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selected_image);
            }catch (IOException e) {
                e.printStackTrace();
            }

            //TODO FIX THIS SHIT

            VirtualScreen vs = new VirtualScreen(my_image.getWidth(), my_image.getHeight());

            if(my_image != null) {
                Log.d(TAG, "loading image was successful, size: " + my_image.getHeight() + "x" + my_image.getWidth());
                //copy bitmap to be mutable
                Bitmap bitmap = my_image.copy(Bitmap.Config.ARGB_8888, true);
                vs.setBitmap(bitmap);
            } else {
                Log.e(TAG, "could not load image: "+selected_image);
            }

            //TODO I'M NOT SURE ABOUT THIS PART
            Viewport vp = new Viewport(vs, 0, 0, my_image.getWidth(), my_image.getHeight());

            ImageContext.Contributers = new ArrayList<>();
            ImageContext.Contributers.add(new Player(null, "Hans Wurst"));
            for(Player p : ImageContext.Contributers) {
                if(p.getConnection() != null)
                    vp.addView(p.getConnection().getRemoteView(0));
            }

            ImageContext.startDisplay(vs);
        }
    }
}
