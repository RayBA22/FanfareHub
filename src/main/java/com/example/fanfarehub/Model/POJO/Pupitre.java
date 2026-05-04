package model;

public class Pupitre {

    private String instrument;

    public Pupitre() {}

    public Pupitre(String instrument) {
        this.instrument = instrument;
    }

    public String getInstrument()                   { return instrument; }
    public void setInstrument(String instrument)    { this.instrument = instrument; }
}
