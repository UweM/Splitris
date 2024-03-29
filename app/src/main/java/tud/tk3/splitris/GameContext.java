package tud.tk3.splitris;

import java.io.IOException;
import java.util.ArrayList;

import tud.tk3.splitris.network.GameControllerInterface;
import tud.tk3.splitris.network.GameServer;
import tud.tk3.splitris.network.Player;
import tud.tk3.splitris.tetris.Game;
import tud.tk3.splitscreen.network.Client;
import tud.tk3.splitscreen.screen.BlockScreen;

public class GameContext {
    // Main class for managing the game context
    public static int PORT = 45832;
    public static GameServer Server;
    public static Client Client;
    public static Game Game;
    public static GameControllerInterface Controller;
    public static ArrayList<Player> Players;

    public static final int RMI_GAMECONTROLLER = 5;

    public static void initServer(String nickname) throws IOException{
        // initiate the gameserver
        Server = new GameServer(PORT, nickname);
        Server.start();
    }

    public static void initClient() {
        // initiate the client
        Client = new Client();
    }

    public static void startGame(BlockScreen bs, BlockScreen preview) {
        // start the game (using an own thread)
        Game = new Game(bs, preview, bs.getWidth(), bs.getHeight());

        new Thread() {
            @Override
            public void run() {

                try {

                    while(true) {
                        if(!Game.tick()) return;
                        int sleepTime = 500 - (Game.getStandardizedLevel() * 50);
                        Thread.sleep(sleepTime);
                    }

                } catch (InterruptedException ignored) {
                }
            }
        }.start();
    }
}
