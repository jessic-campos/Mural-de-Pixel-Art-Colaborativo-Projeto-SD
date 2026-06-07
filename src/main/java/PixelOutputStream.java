import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;

public class PixelOutputStream
        extends OutputStream {

    private OutputStream out;

    public PixelOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b)
            throws IOException {

        out.write(b);
    }

    public void writePixel(Pixel pixel)
            throws IOException {

        DataOutputStream dos =
                new DataOutputStream(out);

        dos.writeInt(pixel.getX());
        dos.writeInt(pixel.getY());
        dos.writeUTF(pixel.getCor());

        dos.flush();
    }
}