package model;

public class Inscription {

    private String pseudo;        // FK -> Fanfaron
    private String nomEvenement;  // FK -> Evenement
    private String instrument;    // FK -> Pupitre
    private String status;        // "présent", "absent", "incertain"

    public Inscription() {}

    public Inscription(String pseudo, String nomEvenement, String instrument, String status) {
        this.pseudo = pseudo;
        this.nomEvenement = nomEvenement;
        this.instrument = instrument;
        this.status = status;
    }

    public String getPseudo()                   { return pseudo; }
    public void setPseudo(String pseudo)        { this.pseudo = pseudo; }

    public String getNomEvenement()                      { return nomEvenement; }
    public void setNomEvenement(String nomEvenement)     { this.nomEvenement = nomEvenement; }

    public String getInstrument()                        { return instrument; }
    public void setInstrument(String instrument)         { this.instrument = instrument; }

    public String getStatus()                   { return status; }
    public void setStatus(String status)        { this.status = status; }
}
