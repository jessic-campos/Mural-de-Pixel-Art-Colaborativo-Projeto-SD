import java.io.FileInputStream;

public class TesteLeitura {

    public static void main(String[] args) {

        try {

            FileInputStream fis =
                    new FileInputStream("pixels.dat");

            PixelInputStream pis =
                    new PixelInputStream(fis);

            Pixel pixel = pis.readPixel();

            System.out.println("X: " + pixel.getX());
            System.out.println("Y: " + pixel.getY());
            System.out.println("Cor: " + pixel.getCor());

            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}