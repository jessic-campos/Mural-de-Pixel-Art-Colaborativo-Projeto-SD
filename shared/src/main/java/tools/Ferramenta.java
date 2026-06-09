package tools;

import model.Coordenada;
import model.Mural;

import java.io.Serializable;

public abstract class Ferramenta implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String nome;

    public Ferramenta(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }

    public abstract void aplicar(Mural mural, Coordenada coordenada);
}
