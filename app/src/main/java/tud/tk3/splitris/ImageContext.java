package tud.tk3.splitris;

import tud.tk3.splitris.image.Image;
import tud.tk3.splitris.network.GameServer;
import tud.tk3.splitscreen.screen.BlockScreen;

public class ImageContext {
    public static Image Image;

    public static void startDisplay(BlockScreen bs) {
        Image = new Image(bs);
    }
}
