package tud.tk3.splitscreen.network;

import com.esotericsoftware.kryonet.rmi.ObjectSpace;

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
}
