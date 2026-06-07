import java.net.Socket;

public class ClienteTeste {

    public static void main(String[] args)
            throws Exception {

        Socket socket =
                new Socket("192.168.100.208", 5000);

        PixelOutputStream pos =
                new PixelOutputStream(
                        socket.getOutputStream());

        Pixel[] pixels = {

                new Pixel(1, 1, "VERMELHO"),
                new Pixel(2, 1, "VERMELHO"),
                new Pixel(3, 1, "VERMELHO"),

                new Pixel(2, 2, "AZUL"),

                new Pixel(1, 3, "VERDE"),
                new Pixel(2, 3, "VERDE"),
                new Pixel(3, 3, "VERDE")
        };

        pos.writePixels(pixels);

        System.out.println("Pixel Art enviada!");

        socket.close();
    }
}