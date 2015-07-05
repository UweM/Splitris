package tud.tk3.splitscreen.network;

import android.os.AsyncTask;
import android.util.Log;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.ClientDiscoveryHandler;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import tud.tk3.splitscreen.output.IScreenView;

public class Client extends com.esotericsoftware.kryonet.Client {
    private ObjectSpace mObjSpace;

    public Client() {
        super();
        super.start();
        Network.register(this);
        mObjSpace = new ObjectSpace(this);
    }

    public void registerView(int id, IScreenView v) {
        mObjSpace.register(Network.SCREEN_BASE + id, v);
    }

    public <T> T getRemoteObject (int objectID, Class<T> iface) {
        return ObjectSpace.getRemoteObject(this, objectID, iface);
    }

    public void discoverScreenServers(final int port, final DiscoveryHandler handler) {

        setDiscoveryHandler(new ClientDiscoveryHandler() {

            @Override
            public DatagramPacket onRequestNewDatagramPacket() {
                return new DatagramPacket(new byte[20], 20);
            }

            @Override
            public void onDiscoveredHost(final DatagramPacket datagramPacket, Kryo kryo) {
                Log.d("Lobby", "recv len: " + datagramPacket.getData().length);
                ByteBuffer buf = ByteBuffer.wrap(datagramPacket.getData(), 0, datagramPacket.getLength());
                final String nickname = Charset.forName("UTF-8").decode(buf).toString();

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        handler.onFound(datagramPacket.getAddress(), nickname);
                    }
                }.execute();


            }

            @Override
            public void onFinally() {

            }
        });
        new Thread() {

            @Override
            public void run() {
                discoverHosts(port, 2000);
            }
        }.start();
    }
}
