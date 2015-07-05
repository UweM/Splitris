package tud.tk3.splitscreen.network;


import android.util.Log;

import com.esotericsoftware.kryonet.Serialization;
import com.esotericsoftware.kryonet.ServerDiscoveryHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

public class Server extends com.esotericsoftware.kryonet.Server {


    protected com.esotericsoftware.kryonet.Connection newConnection () {
        return new ServerConnection();
    }


    public Server(int port, final String nickname) throws IOException {
        super();
        Network.register(this);
        bind(port, port);
        // register discovery handler that responses with the server nickname
        setDiscoveryHandler(new ServerDiscoveryHandler() {
            @Override
            public boolean onDiscoverHost(DatagramChannel datagramChannel, InetSocketAddress fromAddress, Serialization serialization) throws IOException {
                CharBuffer c = CharBuffer.wrap(nickname);
                ByteBuffer buf = Charset.forName("UTF-8").newEncoder().encode(c);
                Log.d("Lobby", "send len: " + buf.limit());
                datagramChannel.send(buf, fromAddress);
                Log.d("Server", "Discovery req");
                return true;
            }
        });
    }


}
