package com.example.fanfarehub.Model.POJO;

import java.util.Date;

public class Evenement {
    private String nom;
    private String type;
    private Date date;
    private String duree;
    private String lieu;
    private String description;
    private String pseudo;

    public Evenement(String nom, String type, Date date, String duree, String lieu, String description, String pseudo) {
        this.nom = nom;
        this.type = type;
        this.date = date;
        this.duree = duree;
        this.lieu = lieu;
        this.description = description;
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
