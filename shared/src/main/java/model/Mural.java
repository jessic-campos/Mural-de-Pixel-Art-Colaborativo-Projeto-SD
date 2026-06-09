package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Mural implements Serializable {
    private static final long serialVersionUID = 1L;

    private int largura;
    private int altura;
    private Pixel[][] pixels;

    public Mural(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        this.pixels = new Pixel[largura][altura];

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                pixels[x][y] = new Pixel(x, y, "BRANCO");
            }
        }
    }

    public int getLargura() { return largura; }
    public int getAltura()  { return altura; }

    public void pintarPixel(int x, int y, String cor) {
        validarCoordenada(x, y);
        pixels[x][y] = new Pixel(x, y, cor);
    }

    public Pixel getPixel(int x, int y) {
        validarCoordenada(x, y);
        return pixels[x][y];
    }

    public List<Pixel> listarPixelsColoridos() {
        List<Pixel> lista = new ArrayList<>();
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                if (!"BRANCO".equalsIgnoreCase(pixels[x][y].getCor())) {
                    lista.add(pixels[x][y]);
                }
            }
        }
        return lista;
    }

    public List<Pixel> listarTodosPixels() {
        List<Pixel> lista = new ArrayList<>();
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                lista.add(pixels[x][y]);
            }
        }
        return lista;
    }

    private void validarCoordenada(int x, int y) {
        if (x < 0 || x >= largura || y < 0 || y >= altura) {
            throw new IllegalArgumentException("Coordenada fora do mural: (" + x + ", " + y + ")");
        }
    }
}
