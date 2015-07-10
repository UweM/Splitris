package tud.tk3.splitscreen.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

import tud.tk3.splitscreen.output.IScreenView;

public class Network {

    // These IDs are used to register objects in ObjectSpaces.
    static public final short SCREEN_BASE = 5000;

    // This registers objects that are going to be sent over the network.
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        // This must be called in order to use ObjectSpaces.
        ObjectSpace.registerClasses(kryo);
        // The interfaces that will be used as remote objects must be registered.
        kryo.register(IScreenView.class);

        // The classes of all method parameters and return values
        // for remote objects must also be registered.
        kryo.register(byte[].class);
    }
}
