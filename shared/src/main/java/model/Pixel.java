package model;

import java.io.Serializable;

public class Pixel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int x;
    private int y;
    private String cor;

    public Pixel(int x, int y, String cor) {
        this.x = x;
        this.y = y;
        this.cor = cor;
    }

    public int getX()      { return x; }
    public int getY()      { return y; }
    public String getCor() { return cor; }

    @Override
    public String toString() {
        return "Pixel{x=" + x + ", y=" + y + ", cor='" + cor + "'}";
    }
}
