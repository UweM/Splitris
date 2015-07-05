package tud.tk3.splitris;

import java.io.IOException;

import tud.tk3.splitscreen.network.Server;
import tud.tk3.splitscreen.network.Client;

public class GameContext {
    public static int PORT = 45832;
    public static Server Server;
    public static Client Client;

    public static void initServer(String nickname) throws IOException{
        Server = new Server(PORT, nickname);
    }

    public static void initClient() {
        Client = new Client();
    }
}
