package tud.tk3.splitscreen.network;

import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;

import tud.tk3.splitscreen.output.IScreenView;

public class ServerConnection extends com.esotericsoftware.kryonet.Connection {

    public IScreenView getRemoteView(int id) {
        IScreenView view = getRemoteObject(Network.SCREEN_BASE + id, IScreenView.class);
        ((RemoteObject)view).setNonBlocking(true);
        ((RemoteObject)view).setTransmitReturnValue(false);
        return view;
    }

    public <T> T getRemoteObject (int objectID, Class<T> iface) {
        return ObjectSpace.getRemoteObject(this, objectID, iface);
    }

}
