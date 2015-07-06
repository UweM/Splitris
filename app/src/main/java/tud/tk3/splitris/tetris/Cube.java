package tud.tk3.splitris.tetris;

public class Cube {
    public Element Parent;
    public int X = 0;
    public int Y = 0;

    public Cube(int X, int Y, Element parent) {
        this.X = X;
        this.Y = Y;
        this.Parent = parent;
    }
}
