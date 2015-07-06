package tud.tk3.splitris.network;

import tud.tk3.splitris.GameContext;
import tud.tk3.splitscreen.network.ServerConnection;

// Server side: RMI interface to clients
public class GameController implements GameControllerInterface {
    private ServerConnection mConnection;
    private GameServer mSrv;
    private Player mPlayer;
    private boolean mCanControl;

    public GameController(ServerConnection con, GameServer srv) {

        mConnection = con;
        mSrv = srv;
    }

    public void setCanControl(boolean c) {
        mCanControl = c;
    }

    public void enterGame(String nickname) {
        mPlayer = new Player(mConnection, nickname);
        mSrv.getGameEventHandler().onNewPlayer(mPlayer);
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
        if(mCanControl) GameContext.Game.tick();
    }

    public void rotate() {
        if(mCanControl) GameContext.Game.rotate();
    }
}
