package tud.tk3.splitscreen.output;

// interface to simplify rmi proxy generation by kryonet (see https://github.com/EsotericSoftware/kryonet)
public interface IScreenView {
    void setBitmap(final byte[] bytes);
}
