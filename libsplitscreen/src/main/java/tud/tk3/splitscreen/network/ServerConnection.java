package tud.tk3.splitscreen.network;

import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;

import tud.tk3.splitscreen.output.IScreenView;

public class ServerConnection extends com.esotericsoftware.kryonet.Connection {
    // class handling the server connection

    private ObjectSpace mObjSpace;

    public void registerObject(int id, Object o) {
        // register the object
        if(mObjSpace == null)
            mObjSpace = new ObjectSpace(this);
        mObjSpace.register(id, o);
    }

    public IScreenView getRemoteView(int id) {
        // get the remote view
        IScreenView view = getRemoteObject(Network.SCREEN_BASE + id, IScreenView.class);
        ((RemoteObject)view).setNonBlocking(true);
        ((RemoteObject)view).setTransmitReturnValue(false);
        return view;
    }

    public <T> T getRemoteObject (int objectID, Class<T> iface) {
        // get the remote object
        return ObjectSpace.getRemoteObject(this, objectID, iface);
    }
}
