/*
 * Modele d'inscription d'un fanfaron a un evenement.
 * Il relie un pseudo a un evenement, avec l'instrument choisi
 * et le statut de participation annonce.
 */
package com.example.fanfarehub.Model.POJO;

public class Inscrit {
    private String pseudo;
    private String nom;
    private String instrument;
    private String status;

    public Inscrit(String pseudo, String nom, String instrument, String status) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.instrument = instrument;
        this.status = status;
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

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
