package tud.tk3.splitris;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.IOError;
import java.io.IOException;

import tud.tk3.splitris.network.GameController;
import tud.tk3.splitris.network.Player;
import tud.tk3.splitris.tetris.Initiator;
import tud.tk3.splitscreen.output.ScreenView;
import tud.tk3.splitscreen.output.Viewport;
import tud.tk3.splitscreen.screen.BlockScreen;

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

            Initiator init = new Initiator();
            //BlockScreen bs = init.configureBlockScreens(ImageContext.Contributers, image_screen);
            //TODO generate ViewScreen

            //ImageContext.startDisplay(bs);
        }
    }
}
