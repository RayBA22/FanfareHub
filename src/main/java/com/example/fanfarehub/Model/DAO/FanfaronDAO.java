package com.example.fanfarehub.Model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FanfaronDAO {

    private final DBConnectionManager dbManager;

    public FanfaronDAO(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }

    // -------------------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------------------

    public void create(String pseudo, String email, String mdpClair,
                       String prenom, String nom, String genre, String regime) throws SQLException {
        String sql = """
                INSERT INTO FANFARON (pseudo, email, mdp, prenom, nom, genre, regime, date_creation, role)
                VALUES (?, ?, crypt(?, gen_salt('bf')), ?, ?, ?, ?, NOW(), 'fanfaron')
                """;
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.setString(2, email);
            ps.setString(3, mdpClair);
            ps.setString(4, prenom);
            ps.setString(5, nom);
            ps.setString(6, genre);
            ps.setString(7, regime);
            ps.executeUpdate();
        }
    }

    // -------------------------------------------------------------------------
    // READ
    // -------------------------------------------------------------------------

    public Fanfaron findByPseudo(String pseudo) throws SQLException {
        String sql = "SELECT * FROM FANFARON WHERE pseudo = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public Fanfaron findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM FANFARON WHERE email = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public List<Fanfaron> findAll() throws SQLException {
        String sql = "SELECT * FROM FANFARON ORDER BY pseudo";
        List<Fanfaron> list = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    // Vérifie pseudo + mdp pour la connexion
    public Fanfaron login(String pseudo, String mdpClair) throws SQLException {
        String sql = "SELECT * FROM FANFARON WHERE pseudo = ? AND mdp = crypt(?, mdp)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.setString(2, mdpClair);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null; // null = mauvais identifiants
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------

    public void update(Fanfaron f) throws SQLException {
        String sql = """
                UPDATE FANFARON
                SET email = ?, prenom = ?, nom = ?, genre = ?, regime = ?, role = ?
                WHERE pseudo = ?
                """;
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getEmail());
            ps.setString(2, f.getPrenom());
            ps.setString(3, f.getNom());
            ps.setString(4, f.getGenre());
            ps.setString(5, f.getRegime());
            ps.setString(6, f.getRole());
            ps.setString(7, f.getPseudo());
            ps.executeUpdate();
        }
    }

    public void updateMotDePasse(String pseudo, String mdpClair) throws SQLException {
        String sql = "UPDATE FANFARON SET mdp = crypt(?, gen_salt('bf')) WHERE pseudo = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mdpClair);
            ps.setString(2, pseudo);
            ps.executeUpdate();
        }
    }

    public void updateDateConnexion(String pseudo) throws SQLException {
        String sql = "UPDATE FANFARON SET date_connexion = NOW() WHERE pseudo = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.executeUpdate();
        }
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------

    public void delete(String pseudo) throws SQLException {
        String sql = "DELETE FROM FANFARON WHERE pseudo = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.executeUpdate();
        }
    }

    // -------------------------------------------------------------------------
    // EXISTS (pour les vérifications d'inscription)
    // -------------------------------------------------------------------------

    public boolean pseudoExists(String pseudo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM FANFARON WHERE pseudo = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM FANFARON WHERE email = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    // -------------------------------------------------------------------------
    // MAPPING ResultSet -> Fanfaron
    // -------------------------------------------------------------------------

    private Fanfaron map(ResultSet rs) throws SQLException {
        Fanfaron f = new Fanfaron();
        f.setPseudo(rs.getString("pseudo"));
        f.setEmail(rs.getString("email"));
        f.setMdp(rs.getBytes("mdp"));
        f.setPrenom(rs.getString("prenom"));
        f.setNom(rs.getString("nom"));
        f.setGenre(rs.getString("genre"));
        f.setRegime(rs.getString("regime"));
        f.setDateCreation(rs.getDate("date_creation"));
        f.setDateConnexion(rs.getDate("date_connexion"));
        f.setRole(rs.getString("role"));
        return f;
    }
}
