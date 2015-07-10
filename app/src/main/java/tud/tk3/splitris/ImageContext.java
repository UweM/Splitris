package tud.tk3.splitris;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

import tud.tk3.splitris.image.Image;
import tud.tk3.splitris.network.GameServer;
import tud.tk3.splitris.network.Player;
import tud.tk3.splitscreen.network.Client;
import tud.tk3.splitscreen.screen.BlockScreen;
import tud.tk3.splitscreen.screen.VirtualScreen;

public class ImageContext {
    // class for handling the image context
    public static Image Image;
    public static int PORT = 45832;
    public static GameServer Server;
    public static tud.tk3.splitscreen.network.Client Client;
    public static ArrayList<Player> Contributers;

    public static void startDisplay(VirtualScreen vs) {
        Image = new Image(vs);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Image.render();
                return null;
            }

        }.execute();
    }

    public static void initClient() {
        Client = new Client();
    }

    public static void initServer() throws IOException{
        Server = new GameServer(PORT, "");
        Server.start();
    }
}
