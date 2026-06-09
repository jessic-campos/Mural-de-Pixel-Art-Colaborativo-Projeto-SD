package tools;

import model.Coordenada;
import model.Mural;

public class Pincel extends Ferramenta implements Utilizavel {
    private static final long serialVersionUID = 1L;

    private String cor;

    public Pincel(String cor) {
        super("Pincel");
        this.cor = cor;
    }

    public String getCor() { return cor; }

    @Override
    public void aplicar(Mural mural, Coordenada coordenada) {
        mural.pintarPixel(coordenada.getX(), coordenada.getY(), cor);
    }
}
