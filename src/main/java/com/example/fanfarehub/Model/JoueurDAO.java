package com.example.fanfarehub.Model;

import java.sql.*;

public class JoueurDAO {
    private final DBConnectionManager db_manager;

    public JoueurDAO(DBConnectionManager db_manager) {
        this.db_manager = db_manager;
    }

    public void create(Joueur joueur) {
        try (Connection conn = this.db_manager.getConnection()) {
            String query = """
                INSERT INTO public.joueur(jno, pseudo, email, pwd, elo)
                VALUES (?, ?, ?, ?, ?);
                """;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, joueur.get_jno());
            ps.setString(2, joueur.get_pseudo());
            ps.setString(3, joueur.get_email());
            ps.setString(4, joueur.get_pwd());
            ps.setInt(5, joueur.get_elo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Joueur findByID(int id) {
        Joueur joueur = null;

        try (Connection conn = this.db_manager.getConnection()) {
            String query = """
                SELECT jno, pseudo, email, pwd, elo
                FROM public.joueur
                WHERE jno = ?;
                """;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String jno = rs.getString("jno");
                String pseudo = rs.getString("pseudo");
                String email = rs.getString("email");
                String pwd = rs.getString("pwd");
                String elo = rs.getString("elo");
                
                joueur = new Joueur(Integer.valueOf(jno), pseudo, email, pwd, Integer.valueOf(elo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return joueur;
    }
}
