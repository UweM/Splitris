package tud.tk3.splitris.network;

import tud.tk3.splitscreen.network.ServerConnection;

public class Player {
    private ServerConnection mConnection;
    private String mNickname;

    public Player(ServerConnection con, String nickname) {
        mConnection = con;
        mNickname = nickname;
    }

    public String getNickname() {
        return mNickname;
    }
    public String toString() { return mNickname; }

    public ServerConnection getConnection() {
        return mConnection;
    }
}
