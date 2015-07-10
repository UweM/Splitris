package tud.tk3.splitris.tetris;

public class ElementTemplate {
    // template to define tetris elements
    public static int COUNT = 7;

    public static boolean[][] getFields(int index) {
        boolean[][] Fields = new boolean[4][4];
        switch (index) {
            case 0: //2x2cube
                Fields[1][1] = true;
                Fields[2][1] = true;
                Fields[1][2] = true;
                Fields[2][2] = true;
                break;
            case 1: //long line
                Fields[1][0] = true;
                Fields[1][1] = true;
                Fields[1][2] = true;
                Fields[1][3] = true;
                break;
            case 2:
                Fields[0][2] = true;
                Fields[1][2] = true;
                Fields[2][2] = true;
                Fields[2][3] = true;
                break;
            case 3:
                Fields[1][2] = true;
                Fields[2][2] = true;
                Fields[3][2] = true;
                Fields[3][1] = true;
                break;
            case 4:
                Fields[0][2] = true;
                Fields[1][2] = true;
                Fields[1][1] = true;
                Fields[2][1] = true;
                break;
            case 5:
                Fields[0][1] = true;
                Fields[1][1] = true;
                Fields[1][2] = true;
                Fields[2][2] = true;
                break;
            case 6:
                Fields[0][2] = true;
                Fields[1][1] = true;
                Fields[1][2] = true;
                Fields[2][2] = true;
                break;
        }
        return Fields;
    }
}
