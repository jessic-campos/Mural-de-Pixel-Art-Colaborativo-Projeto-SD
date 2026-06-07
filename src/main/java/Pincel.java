public class Pincel extends Ferramenta
        implements Utilizavel {

    private String cor;

    public Pincel(String cor) {
        super("Pincel");
        this.cor = cor;
    }

    @Override
    public void aplicar(
            Mural mural,
            Coordenada coordenada) {

        mural.pintarPixel(
                coordenada.getX(),
                coordenada.getY(),
                cor
        );
    }
}