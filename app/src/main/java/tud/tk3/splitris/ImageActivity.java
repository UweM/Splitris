package tud.tk3.splitris;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import tud.tk3.splitris.network.GameController;
import tud.tk3.splitris.network.Player;
import tud.tk3.splitris.tetris.Initiator;
import tud.tk3.splitscreen.output.ScreenView;
import tud.tk3.splitscreen.output.Viewport;
import tud.tk3.splitscreen.screen.BlockScreen;

public class ImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageactivity);

        ScreenView image_screen = (ScreenView) findViewById(R.id.image_screen);

        //We are at the Client
        if(ImageContext.Client != null) {
            ImageContext.Client.registerView(0, image_screen);
        }
        //We are at the Server
        else {
            Initiator init = new Initiator();
            BlockScreen bs = init.configureBlockScreens(ImageContext.Contributers, image_screen);
            ImageContext.startDisplay(bs);
        }
    }
}
