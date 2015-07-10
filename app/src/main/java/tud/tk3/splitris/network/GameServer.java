package tud.tk3.splitris.network;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import tud.tk3.splitris.GameContext;
import tud.tk3.splitscreen.network.Server;
import tud.tk3.splitscreen.network.ServerConnection;

public class GameServer extends Server {
    // class representing a game server (server connection + RMI stuff)
    private GameEventHandler mEventHandler;
    private ArrayList<GameController> mControllers = new ArrayList<>();

    public GameServer(int port, String nickname) throws IOException {
        super(port, nickname);
        getKryo().register(GameControllerInterface.class);
    }


    protected ServerConnection newConnection () {
        ServerConnection con = super.newConnection();
        GameController ctrl = new GameController(con, this);
        con.registerObject(GameContext.RMI_GAMECONTROLLER, ctrl);
        mControllers.add(ctrl);
        return con;
    }

    public void setGameEventHandler(GameEventHandler eventHandler) {
        mEventHandler = eventHandler;
    }

    public GameEventHandler getGameEventHandler() {
        return mEventHandler;
    }

    public ArrayList<GameController> getGameControllers() {
        return mControllers;
    }
}
