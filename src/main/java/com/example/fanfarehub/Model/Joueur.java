package com.example.fanfarehub.Model;

public class Joueur {
    private int jno;

    private String pseudo;

    private String email;

    private String pwd;

    private int elo;

    public Joueur(int jno, String pseudo, String email, String pwd, int elo) {
        this.jno = jno;
        this.pseudo = pseudo;
        this.email = email;
        this.pwd = pwd;
        this.elo = elo;
    }

    public void set_jno(int jno) {
        this.jno = jno;
    }

    public void set_pseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void set_email(String email) {
        this.email = email;
    }

    public void set_pwd(String pwd) {
        this.pwd = pwd;
    }

    public void set_elo(int elo) {
        this.elo = elo;
    }

    public int get_jno() {
        return this.jno;
    }

    public String get_pseudo() {
        return this.pseudo;
    }

    public String get_email() {
        return this.email;
    }

    public String get_pwd() {
        return this.pwd;
    }

    public int get_elo() {
        return this.elo;
    }

    @Override
    public String toString() {
        return "Joueur{numéro: " + this.jno + " pseudo: " + this.pseudo + " email: " + this.email + " elo: " + this.elo + "}";
    }
}
