package tools;

import model.Coordenada;
import model.Mural;

public class Borracha extends Ferramenta implements Utilizavel {
    private static final long serialVersionUID = 1L;

    public Borracha() {
        super("Borracha");
    }

    @Override
    public void aplicar(Mural mural, Coordenada coordenada) {
        mural.pintarPixel(coordenada.getX(), coordenada.getY(), "BRANCO");
    }
}
