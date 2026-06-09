package stream;

import model.Pixel;
import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;

public class PixelOutputStream extends OutputStream {

    private OutputStream out;

    public PixelOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b)
            throws IOException {

        out.write(b);
    }

    public void writePixels(Pixel[] pixels) throws IOException {

    DataOutputStream dos = new DataOutputStream(out);

    // envia quantidade de pixels
    dos.writeInt(pixels.length);

    for (Pixel p : pixels) {

        dos.writeInt(p.getX());
        dos.writeInt(p.getY());
        dos.writeUTF(p.getCor());
    }

    dos.flush();
}
}