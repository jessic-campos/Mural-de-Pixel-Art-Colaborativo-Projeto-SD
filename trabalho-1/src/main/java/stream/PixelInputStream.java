package stream;

import model.Pixel;

import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;

public class PixelInputStream
        extends InputStream {

    private InputStream in;

    public PixelInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read()
            throws IOException {

        return in.read();
    }

    public Pixel[] readPixels() throws IOException {

    DataInputStream dis = new DataInputStream(in);

    int quantidade = dis.readInt();

    Pixel[] pixels = new Pixel[quantidade];

    for (int i = 0; i < quantidade; i++) {

        int x = dis.readInt();
        int y = dis.readInt();
        String cor = dis.readUTF();

        pixels[i] = new Pixel(x, y, cor);
    }

    return pixels;
}
}