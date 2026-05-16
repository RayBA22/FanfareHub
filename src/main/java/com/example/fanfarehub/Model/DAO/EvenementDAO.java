package com.example.fanfarehub.Model.DAO;

import com.example.fanfarehub.Model.*;
import com.example.fanfarehub.Model.POJO.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementDAO {
    private final DBConnectionManager dbManager;

    public EvenementDAO(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }

    public void create(Evenement e) throws SQLException {
        String sql = """
                INSERT INTO EVENEMENT (nom, type, date, duree, lieu, description, pseudo)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNom());
            ps.setString(2, e.getType());
            ps.setDate(3, new java.sql.Date(e.getDate().getTime()));
            ps.setString(4, e.getDuree());
            ps.setString(5, e.getLieu());
            ps.setString(6, e.getDescription());
            ps.setString(7, e.getPseudo());
            ps.executeUpdate();
        }
    }

    public Evenement findByNom(String nom) throws SQLException {
        String sql = "SELECT * FROM EVENEMENT WHERE nom = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public List<Evenement> findAll() throws SQLException {
        String sql = "SELECT * FROM EVENEMENT ORDER BY date";
        List<Evenement> list = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Evenement> findByType(String type) throws SQLException {
        String sql = "SELECT * FROM EVENEMENT WHERE type = ? ORDER BY date";
        List<Evenement> list = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public void update(Evenement e) throws SQLException {
        String sql = """
                UPDATE EVENEMENT
                SET type = ?, date = ?, duree = ?, lieu = ?, description = ?
                WHERE nom = ?
                """;
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getType());
            ps.setDate(2, new java.sql.Date(e.getDate().getTime()));
            ps.setString(3, e.getDuree());
            ps.setString(4, e.getLieu());
            ps.setString(5, e.getDescription());
            ps.setString(6, e.getNom());
            ps.executeUpdate();
        }
    }

    public void delete(String nom) throws SQLException {
        String sql = "DELETE FROM EVENEMENT WHERE nom = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.executeUpdate();
        }
    }

    public void deleteAllByPseudo(String pseudo) throws SQLException {

        String sql = "DELETE FROM EVENEMENT WHERE pseudo = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pseudo);

            ps.executeUpdate();
        }
    }

    private Evenement map(ResultSet rs) throws SQLException {
        Evenement e = new Evenement(rs.getString("nom"),
                rs.getString("type"),
                rs.getDate("date"),
                rs.getString("duree"),
                rs.getString("lieu"),
                rs.getString("description"),
                rs.getString("pseudo"));
        return e;
    }
}
