package model;

public class Commission {

    private String nom;

    public Commission() {}

    public Commission(String nom) {
        this.nom = nom;
    }

    public String getNom()          { return nom; }
    public void setNom(String nom)  { this.nom = nom; }
}
