package model;

import java.io.Serializable;

public class ProjetoPixelArt implements Serializable {
    private static final long serialVersionUID = 1L;

    private Usuario dono; // agregacao: Projeto tem Usuario
    private Mural mural;  // agregacao: Projeto tem Mural

    public ProjetoPixelArt(Usuario dono, Mural mural) {
        this.dono = dono;
        this.mural = mural;
    }

    public Usuario getDono() { return dono; }
    public Mural getMural()  { return mural; }
}
