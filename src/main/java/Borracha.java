public class Borracha extends Ferramenta
        implements Utilizavel {

    public Borracha() {
        super("Borracha");
    }

    @Override
    public void aplicar(
            Mural mural,
            Coordenada coordenada) {

        mural.pintarPixel(
                coordenada.getX(),
                coordenada.getY(),
                "BRANCO"
        );
    }
}