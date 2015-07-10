package tud.tk3.splitris.network;

import tud.tk3.splitscreen.network.ServerConnection;

public class Player {
    // class representing a player (nickname + ip address etc.)
    private ServerConnection mConnection;
    private String mNickname;

    public Player(ServerConnection con, String nickname) {
        mConnection = con;
        mNickname = nickname;
    }

    public String getNickname() {
        return mNickname;
    }
    public void setNickname(String nickname) {mNickname = nickname;}
    public String toString() { return mNickname; }

    public ServerConnection getConnection() {
        return mConnection;
    }
}
