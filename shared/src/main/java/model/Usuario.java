package model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }
}
