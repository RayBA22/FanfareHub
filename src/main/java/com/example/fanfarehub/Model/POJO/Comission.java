/*
 * Modele simple d'une commission.
 * Il porte le nom de la commission utilisee dans les listes, les formulaires
 * et les associations avec les fanfarons.
 */
package com.example.fanfarehub.Model.POJO;

public class Comission {
    private String nom;

    public Comission(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
