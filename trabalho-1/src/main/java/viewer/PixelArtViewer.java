package viewer;

import model.Pixel;
import javax.swing.*;
import java.awt.*;

public class PixelArtViewer extends JPanel {

    private Pixel[] pixels;
    private int largura;
    private int altura;

    private final int TAMANHO_PIXEL = 40;

    public PixelArtViewer(
            Pixel[] pixels,
            int largura,
            int altura) {

        this.pixels = pixels;
        this.largura = largura;
        this.altura = altura;

        setPreferredSize(
                new Dimension(
                        largura * TAMANHO_PIXEL,
                        altura * TAMANHO_PIXEL
                )
        );
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
        );

        for (Pixel p : pixels) {

            g.setColor(
                    converterCor(
                            p.getCor()
                    )
            );

            g.fillRect(
                    p.getX() * TAMANHO_PIXEL,
                    p.getY() * TAMANHO_PIXEL,
                    TAMANHO_PIXEL,
                    TAMANHO_PIXEL
            );

            g.setColor(Color.BLACK);

            g.drawRect(
                    p.getX() * TAMANHO_PIXEL,
                    p.getY() * TAMANHO_PIXEL,
                    TAMANHO_PIXEL,
                    TAMANHO_PIXEL
            );
        }
    }

    private Color converterCor(String cor) {

        switch (cor.toUpperCase()) {

            case "VERMELHO":
                return Color.RED;

            case "AZUL":
                return Color.BLUE;

            case "VERDE":
                return Color.GREEN;

            case "AMARELO":
                return Color.YELLOW;

            case "PRETO":
                return Color.BLACK;

            default:
                return Color.LIGHT_GRAY;
        }
    }

    public static void mostrar(
            Pixel[] pixels,
            int largura,
            int altura) {

        JFrame frame =
                new JFrame("Pixel Art Viewer");

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.add(
                new PixelArtViewer(
                        pixels,
                        largura,
                        altura
                )
        );

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}