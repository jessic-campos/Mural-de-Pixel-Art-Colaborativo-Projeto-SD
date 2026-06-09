package model;

public class Borracha extends Ferramenta {
    private static final long serialVersionUID = 1L;

    public Borracha() { super("Borracha"); }

    @Override
    public void aplicar(Mural mural, int x, int y, String cor) {
        mural.apagarPixel(x, y);
    }
}
