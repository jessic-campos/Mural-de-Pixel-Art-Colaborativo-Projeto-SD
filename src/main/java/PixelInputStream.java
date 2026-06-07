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

    public Pixel readPixel()
            throws IOException {

        DataInputStream dis =
                new DataInputStream(in);

        int x = dis.readInt();
        int y = dis.readInt();
        String cor = dis.readUTF();

        return new Pixel(x, y, cor);
    }
}