package com.example.fanfarehub.Model.POJO;

public class Participe {
    private String pseudo;
    private String nom;

    public Participe(String pseudo, String nom) {
        this.pseudo = pseudo;
        this.nom = nom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
