public class Mural {

    private Pixel[][] pixels;

    public Mural(int largura, int altura) {

        pixels = new Pixel[largura][altura];

        for(int i = 0; i < largura; i++) {

            for(int j = 0; j < altura; j++) {

                pixels[i][j] =
                    new Pixel(i, j, "BRANCO");
            }
        }
    }

    public void pintarPixel(
            int x,
            int y,
            String cor) {

        pixels[x][y] =
            new Pixel(x, y, cor);
    }
}