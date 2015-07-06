package tud.tk3.splitris.network;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import tud.tk3.splitris.GameContext;
import tud.tk3.splitscreen.network.Server;
import tud.tk3.splitscreen.network.ServerConnection;

public class GameServer extends Server {
    private GameEventHandler mEventHandler;
    private ArrayList<ServerConnection> mConnections = new ArrayList<>();

    public GameServer(int port, String nickname) throws IOException {
        super(port, nickname);
        getKryo().register(GameControllerInterface.class);
    }


    protected ServerConnection newConnection () {
        ServerConnection con = super.newConnection();
        con.registerObject(GameContext.RMI_GAMECONTROLLER, new GameController(con, this));
        mConnections.add(con);
        return con;
    }

    public void setGameEventHandler(GameEventHandler eventHandler) {
        mEventHandler = eventHandler;
    }

    public GameEventHandler getGameEventHandler() {
        return mEventHandler;
    }
}
