package com.example.fanfarehub.Model.DAO;

import com.example.fanfarehub.Model.*;
import com.example.fanfarehub.Model.POJO.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FanfaronDAO {
    private final DBConnectionManager dbManager;

    public FanfaronDAO(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }

    public void create(String pseudo, String email, String mdpClair,
                       String prenom, String nom, String genre, String regime) throws SQLException {
        String sql = """
        INSERT INTO FANFARON (pseudo, email, mdp, prenom, nom, genre, regime, date_creation, role)
        VALUES (?, ?, digest(?, 'sha256'), ?, ?, ?, ?, NOW(), 'fanfaron')
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

    public Fanfaron login(String pseudo, String mdpClair) throws SQLException {
        String sql = "SELECT * FROM FANFARON WHERE pseudo = ? AND mdp = digest(?, 'sha256')";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.setString(2, mdpClair);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

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
        String sql = "UPDATE FANFARON SET mdp = digest(?, 'sha256') WHERE pseudo = ?";
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

    public void delete(String pseudo) throws SQLException {
        String sql = "DELETE FROM FANFARON WHERE pseudo = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.executeUpdate();
        }
    }

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

    private Fanfaron map(ResultSet rs) throws SQLException {
        Fanfaron f = new Fanfaron(rs.getString("pseudo"),
                rs.getString("email"),
                rs.getString("mdp"),
                rs.getString("prenom"),
                rs.getString("nom"),
                rs.getString("genre"),
                rs.getString("regime"),
                rs.getDate("date_creation"),
                rs.getDate("date_connexion"),
                rs.getString("role"));
        return f;
    }
}
