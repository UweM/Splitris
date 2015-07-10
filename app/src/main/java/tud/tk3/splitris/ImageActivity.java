package tud.tk3.splitris;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // invoked when starting the activity - load all configurations
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageactivity);

        ScreenView image_screen = (ScreenView) findViewById(R.id.image_screen);

        //We are at the Client
        if(ImageContext.Client != null) {
            ImageContext.Client.registerView(0, image_screen);

            ImageContext.initClient();
        }
        //We are at the Server
        else {
            try {
                ImageContext.initServer();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Initiator init = new Initiator();
            BlockScreen bs = init.configureBlockScreens(ImageContext.Contributers, image_screen);
            ImageContext.startDisplay(bs);
        }
    }
}
