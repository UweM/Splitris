package tud.tk3.splitris.image;

import tud.tk3.splitscreen.screen.BlockScreen;

public class Image {
    // class for handling the blockscreen and image
    private BlockScreen mScreen;

    public Image(BlockScreen bs) {
        mScreen = bs;
    }

    public void render() {
        mScreen.render();
    }
}
