package test;

import model.Pixel;
import stream.PixelInputStream;
import viewer.PixelArtViewer;
import java.io.FileInputStream;

public class TesteLeitura {

    public static void main(String[] args) {

        try {

            FileInputStream fis =
                    new FileInputStream("pixels.dat");

            PixelInputStream pis =
                    new PixelInputStream(fis);

            Pixel[] pixels =
                    pis.readPixels();

            fis.close();

            PixelArtViewer.mostrar(
                    pixels,
                    10,
                    10
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}