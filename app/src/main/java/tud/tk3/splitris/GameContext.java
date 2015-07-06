package tud.tk3.splitris;

import java.io.IOException;
import java.util.ArrayList;

import tud.tk3.splitris.network.GameControllerInterface;
import tud.tk3.splitris.network.GameServer;
import tud.tk3.splitris.network.Player;
import tud.tk3.splitris.tetris.Game;
import tud.tk3.splitscreen.network.Client;

public class GameContext {
    public static int PORT = 45832;
    public static GameServer Server;
    public static Client Client;
    public static Game Game;
    public static GameControllerInterface Controller;
    public static ArrayList<Player> Players;

    public static final int RMI_GAMECONTROLLER = 5;

    public static void initServer(String nickname) throws IOException{
        Server = new GameServer(PORT, nickname);
        Server.start();
    }

    public static void initClient() {
        Client = new Client();
    }
}
