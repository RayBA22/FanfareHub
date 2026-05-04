package model;

import java.util.Date;

public class Fanfaron {

    private String pseudo;
    private String email;
    private byte[] mdp;
    private String prenom;
    private String nom;
    private String genre;
    private String regime;
    private Date dateCreation;
    private Date dateConnexion;
    private String role;

    public Fanfaron() {}

    public Fanfaron(String pseudo, String email, byte[] mdp, String prenom,
                    String nom, String genre, String regime,
                    Date dateCreation, Date dateConnexion, String role) {
        this.pseudo = pseudo;
        this.email = email;
        this.mdp = mdp;
        this.prenom = prenom;
        this.nom = nom;
        this.genre = genre;
        this.regime = regime;
        this.dateCreation = dateCreation;
        this.dateConnexion = dateConnexion;
        this.role = role;
    }

    public String getPseudo()             { return pseudo; }
    public void setPseudo(String pseudo)  { this.pseudo = pseudo; }

    public String getEmail()              { return email; }
    public void setEmail(String email)    { this.email = email; }

    public byte[] getMdp()               { return mdp; }
    public void setMdp(byte[] mdp)       { this.mdp = mdp; }

    public String getPrenom()             { return prenom; }
    public void setPrenom(String prenom)  { this.prenom = prenom; }

    public String getNom()                { return nom; }
    public void setNom(String nom)        { this.nom = nom; }

    public String getGenre()              { return genre; }
    public void setGenre(String genre)    { this.genre = genre; }

    public String getRegime()             { return regime; }
    public void setRegime(String regime)  { this.regime = regime; }

    public Date getDateCreation()                    { return dateCreation; }
    public void setDateCreation(Date dateCreation)   { this.dateCreation = dateCreation; }

    public Date getDateConnexion()                   { return dateConnexion; }
    public void setDateConnexion(Date dateConnexion) { this.dateConnexion = dateConnexion; }

    public String getRole()               { return role; }
    public void setRole(String role)      { this.role = role; }
}
