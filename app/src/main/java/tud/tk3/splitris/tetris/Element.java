package tud.tk3.splitris.tetris;

import java.util.Random;

public class Element {
    // class defining an tetris element

    //grid of 4x4
    // +----+----+----+----+
    // |    |    |    |    |
    // +----+----+----+----+
    // |    |    |    |    |
    // +----+----+----+----+
    // |    |    |    |    |
    // +----+----+----+----+
    // |    |    |    |    |
    // +----+----+----+----+

    //if you have a simple tetris cube, the fields can be used as:
    // +----+----+----+----+
    // |    |    |    |    |
    // +----+----+----+----+
    // |    |XXXX|XXXX|    |
    // +----+----+----+----+
    // |    |XXXX|XXXX|    |
    // +----+----+----+----+
    // |    |    |    |    |
    // +----+----+----+----+

    //the empty (false) fields are ignored.
    //the used (true) fields will be displayed and used for collision detection

    private static Random rnd = new Random();

    public boolean[][] mUsedFields = new boolean[4][4];
    public Cube[][] mChildCubes = new Cube[4][4];

    public int mPosX = 0;
    public int mPosY = 0;
    protected Game mGame;

    public Element(Game TetrisMain, int Item) {
        this.mGame = TetrisMain;
        // randomly set the new element in the field (keep the edges free)
        this.mPosX = 4 + rnd.nextInt(Game.FIELD_WIDTH - 4 - 4);
        this.mPosY = Game.FIELD_HEIGHT - 1;

        this.mUsedFields = ElementTemplate.getFields(Item);

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (this.mUsedFields[x][y]) {
                    int lposX = this.mPosX +x;
                    int lposY = this.mPosY -y;
                    this.mChildCubes[x][y] = new Cube(lposX, lposY, this);
                    if (this.mGame.getField(lposX, lposY) != null) {
                        this.mGame.GameOver();
                        return;
                    }
                    this.mGame.setField(lposX, lposY, this.mChildCubes[x][y]);
                }
            }
        }
    }


    protected boolean move(boolean toLeft, boolean toRight, boolean Down) {
        int xExp = 0;
        int yExp = 0;
        if (toLeft) xExp = -1;
        else if (toRight) xExp = 1;
        if (Down) yExp = -1;


        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (this.mUsedFields[x][y]) {
                    if (this.mChildCubes[x][y].X + xExp == Game.FIELD_WIDTH) return false;
                    if (this.mChildCubes[x][y].X + xExp == -1) return false;
                    if (this.mChildCubes[x][y].Y + yExp == -1) return false;

                    Cube cube = this.mGame.getField(this.mChildCubes[x][y].X + xExp,
                            this.mChildCubes[x][y].Y + yExp);

                    //if cube is used, but not from our class then its impossible to move!
                    if (cube != null && cube.Parent != this) return false;
                }
            }
        }


        //all fields checked, move is ok



        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                //remove all old positions
                if (this.mUsedFields[x][y]) {
                    this.mGame.setField(this.mChildCubes[x][y].X, this.mChildCubes[x][y].Y, null);
                }
            }
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                //set new positions
                if (this.mUsedFields[x][y]) {
                    this.mChildCubes[x][y].X += xExp;
                    this.mChildCubes[x][y].Y += yExp;
                    this.mGame.setField(this.mChildCubes[x][y].X, this.mChildCubes[x][y].Y, this.mChildCubes[x][y]);
                }
            }

        }

        this.mPosX += xExp;
        this.mPosY += yExp;

        //all done, move ok
        return true;
    }

    public boolean rotate() {
        boolean[][] newFields = new boolean[4][4];
        //calculate new positions
        for (int x = 0; x < 4; x++) {
            int z = 3;
            for (int y = 0; y < 4; y++) {
                newFields[y][x] = this.mUsedFields[x][z];
                z--;
            }
        }
        //check future fields
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (newFields[x][y]) {
                    if (this.mPosX + x == Game.FIELD_WIDTH) return false;
                    if (this.mPosX + x == 0) return false;
                    if (this.mPosY - y == 0) return false;
                    Cube cube = this.mGame.getField(this.mPosX + x, this.mPosY - y);
                    if (cube != null && cube.Parent != this) return false;

                }
            }
        }
        //remove old positions
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (this.mUsedFields[x][y]) this.mGame.setField(this.mChildCubes[x][y].X, this.mChildCubes[x][y].Y, null);
                this.mChildCubes[x][y] = null;
            }
        }

        this.mUsedFields = newFields;

        //set new positions
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (this.mUsedFields[x][y]) {
                    this.mChildCubes[x][y] = new Cube(this.mPosX + x, this.mPosY - y, this);
                    this.mGame.setField(this.mPosX + x, this.mPosY - y, this.mChildCubes[x][y]);
                }
            }
        }
        return true;
    }


    public boolean moveX(boolean toLeft) {
        return this.move(toLeft, !toLeft, false);
    }

    public boolean moveDown() {
        return this.move(false, false, true);
    }
}
