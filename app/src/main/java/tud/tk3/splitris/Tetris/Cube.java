package tud.tk3.splitris.Tetris;

public class Cube {
    public Element Parent;
    public int X = 0;
    public int Y = 0;
    public boolean used = false;

    public Cube() {
    }

    public Cube(int X, int Y, Element parent) {
        this.X = X;
        this.Y = Y;
        this.Parent = parent;
    }
}
