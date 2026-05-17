/*
 * Modele d'association entre un fanfaron et un instrument.
 * Il represente un pupitre joue par un membre dans la table de liaison.
 */
package com.example.fanfarehub.Model.POJO;

public class Joue {
    private String pseudo;
    private String instrument;

    public Joue(String pseudo, String instrument) {
        this.pseudo = pseudo;
        this.instrument = instrument;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
