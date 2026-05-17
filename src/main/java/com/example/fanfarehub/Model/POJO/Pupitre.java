/*
 * Modele simple d'un pupitre.
 * Il porte le nom de l'instrument affiche dans les formulaires
 * et associe aux fanfarons.
 */
package com.example.fanfarehub.Model.POJO;

public class Pupitre {
    private String instrument;

    public Pupitre(String instrument) {
        this.instrument = instrument;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
