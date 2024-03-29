package tud.tk3.splitris.image;

import tud.tk3.splitscreen.screen.BlockScreen;
import tud.tk3.splitscreen.screen.VirtualScreen;

public class Image {
    // class for handling the blockscreen and image
    private VirtualScreen mScreen;

    public Image(VirtualScreen vs) {
        mScreen = vs;
    }

    public void render() {
        mScreen.render();
    }
}
