package tud.tk3.splitris;

import java.io.IOException;
import java.util.ArrayList;

import tud.tk3.splitris.image.Image;
import tud.tk3.splitris.network.GameServer;
import tud.tk3.splitris.network.Player;
import tud.tk3.splitscreen.network.Client;
import tud.tk3.splitscreen.screen.BlockScreen;

public class ImageContext {
    public static Image Image;
    public static int PORT = 45832;
    public static GameServer Server;
    public static tud.tk3.splitscreen.network.Client Client;
    public static ArrayList<Player> Contributers;

    public static void startDisplay(BlockScreen bs) {
        Image = new Image(bs);

        new Thread() {
            @Override
            public void run() {

                try {

                    //TODO

                } catch (InterruptedException ignored) {
                }
            }
        }.start();
    }

    public static void initClient() {
        Client = new Client();
    }

    public static void initServer(String nickname) throws IOException {
        Server = new GameServer(PORT, nickname);
        Server.start();
    }
}
