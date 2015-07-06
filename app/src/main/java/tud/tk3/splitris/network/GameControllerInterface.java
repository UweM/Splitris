package tud.tk3.splitris.network;

public interface GameControllerInterface {
    void enterGame(String nickname);

    void moveX(boolean toLeft);

    void moveRight();

    void moveLeft();

    void moveDown();

    void rotate();
}
