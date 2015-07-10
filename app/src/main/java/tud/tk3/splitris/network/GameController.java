package tud.tk3.splitris.network;

import android.os.AsyncTask;
import android.util.Log;

import tud.tk3.splitris.GameContext;
import tud.tk3.splitscreen.OnMainThread;
import tud.tk3.splitscreen.network.ServerConnection;

// Server side: RMI interface to clients
public class GameController implements GameControllerInterface {
    // class for handling the server side RMI interface to clients

    private final static String TAG = "GameControllerInterface";

    private ServerConnection mConnection;
    private GameServer mSrv;
    private Player mPlayer;
    private boolean mCanControl = true;

    public GameController(ServerConnection con, GameServer srv) {

        mConnection = con;
        mSrv = srv;
    }

    public void setCanControl(boolean c) {
        mCanControl = c;
    }

    public void enterGame(String nickname) {
        mPlayer = new Player(mConnection, nickname);
        new OnMainThread() {
            @Override
            public void run() {
                mSrv.getGameEventHandler().onNewPlayer(mPlayer);
            }
        };
    }

    public void moveX(boolean toLeft) {
        if(mCanControl) GameContext.Game.moveX(toLeft);
    }

    public void moveRight() {
        moveX(false);
    }

    public void moveLeft() {
        moveX(true);
    }

    public void moveDown() {
        if(mCanControl) GameContext.Game.fastTick();
    }

    public void rotate() {
        if(mCanControl) GameContext.Game.rotate();
    }
}
