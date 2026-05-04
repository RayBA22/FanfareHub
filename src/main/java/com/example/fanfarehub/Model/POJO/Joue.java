package model;

public class Joue {

    private String pseudo;      // FK -> Fanfaron
    private String instrument;  // FK -> Pupitre

    public Joue() {}

    public Joue(String pseudo, String instrument) {
        this.pseudo = pseudo;
        this.instrument = instrument;
    }

    public String getPseudo()                { return pseudo; }
    public void setPseudo(String pseudo)     { this.pseudo = pseudo; }

    public String getInstrument()                   { return instrument; }
    public void setInstrument(String instrument)    { this.instrument = instrument; }
}
