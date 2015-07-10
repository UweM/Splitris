package tud.tk3.splitscreen.network;


import android.util.Log;

import com.esotericsoftware.kryonet.Serialization;
import com.esotericsoftware.kryonet.ServerDiscoveryHandler;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

public class Server extends com.esotericsoftware.kryonet.Server {
    // server class extending the kryonet class (see https://github.com/EsotericSoftware/kryonet)

    protected ServerConnection newConnection () {
        return new ServerConnection();
    }

    public Server(int port, final String nickname) throws IOException {
        // method to start the server for a given port & nickname
        super();
        Network.register(this);
        bind(port, port);
        // register discovery handler that responses with the server nickname
        setDiscoveryHandler(new ServerDiscoveryHandler() {
            @Override
            public boolean onDiscoverHost(DatagramChannel datagramChannel, InetSocketAddress fromAddress, Serialization serialization) throws IOException {
                CharBuffer c = CharBuffer.wrap(nickname);
                ByteBuffer buf = Charset.forName("UTF-8").newEncoder().encode(c);
                datagramChannel.send(buf, fromAddress);
                return true;
            }
        });
    }


}
