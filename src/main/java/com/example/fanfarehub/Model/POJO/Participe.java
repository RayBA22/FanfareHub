package model;

public class Participe {

    private String pseudo;         // FK -> Fanfaron
    private String nomCommission;  // FK -> Commission

    public Participe() {}

    public Participe(String pseudo, String nomCommission) {
        this.pseudo = pseudo;
        this.nomCommission = nomCommission;
    }

    public String getPseudo()                    { return pseudo; }
    public void setPseudo(String pseudo)         { this.pseudo = pseudo; }

    public String getNomCommission()                       { return nomCommission; }
    public void setNomCommission(String nomCommission)     { this.nomCommission = nomCommission; }
}
