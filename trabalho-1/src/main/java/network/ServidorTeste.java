package network;

import model.Pixel;
import stream.PixelInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTeste {

    public static void main(String[] args)
            throws Exception {

        ServerSocket server =
                new ServerSocket(5000);

        System.out.println("Aguardando...");

        Socket cliente =
                server.accept();

        PixelInputStream pis =
                new PixelInputStream(
                        cliente.getInputStream());

        Pixel[] pixels = pis.readPixels();

        System.out.println("Pixels recebidos:");

        for (Pixel pixel : pixels) {

            System.out.println(
                    pixel.getX() + " "
                    + pixel.getY() + " "
                    + pixel.getCor()
            );
        }

        cliente.close();
        server.close();
    }
}
