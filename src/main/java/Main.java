import java.io.FileOutputStream;
import java.io.FileInputStream;


public class Main {

    public static void main(String[] args) {

        try {

            Pixel[] pixels = {

    new Pixel(2,1,"VERMELHO"),
    new Pixel(3,1,"VERMELHO"),
    new Pixel(4,1,"VERMELHO"),

    new Pixel(1,2,"VERMELHO"),
    new Pixel(5,2,"VERMELHO"),

    new Pixel(2,3,"AZUL"),
    new Pixel(4,3,"AZUL"),

    new Pixel(3,4,"VERDE"),

    new Pixel(1,5,"AMARELO"),
    new Pixel(2,5,"AMARELO"),
    new Pixel(3,5,"AMARELO"),
    new Pixel(4,5,"AMARELO"),
    new Pixel(5,5,"AMARELO")
};

            FileOutputStream fos =
                    new FileOutputStream("pixels.dat");

            PixelOutputStream pos =
                    new PixelOutputStream(fos);

            pos.writePixels(pixels);

            fos.close();

            System.out.println("Pixel Art gravada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}