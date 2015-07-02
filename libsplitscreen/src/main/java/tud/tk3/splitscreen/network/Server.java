package tud.tk3.splitscreen.network;


import java.io.IOException;

public class Server extends com.esotericsoftware.kryonet.Server {


    protected com.esotericsoftware.kryonet.Connection newConnection () {
        return new ServerConnection();
    }


    public Server(int port) throws IOException {
        super();
        Network.register(this);
        bind(port);
    }

}
