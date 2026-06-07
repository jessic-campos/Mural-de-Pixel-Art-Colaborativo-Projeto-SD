import java.net.Socket;

public class ClienteTeste {

    public static void main(String[] args)
            throws Exception {

        Socket socket =
                new Socket("192.168.100.208", 5000);

        Pixel pixel =
                new Pixel(20, 15, "AMARELO");

        PixelOutputStream pos =
                new PixelOutputStream(
                        socket.getOutputStream());

        pos.writePixel(pixel);

        socket.close();
    }
}