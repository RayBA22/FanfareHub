package com.example.fanfarehub.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscriptionDAO {

    private final DBConnectionManager dbManager;

    public InscriptionDAO(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }

    // -------------------------------------------------------------------------
    // CREATE / UPDATE (upsert)
    // -------------------------------------------------------------------------

    // Inscrit un fanfaron à un événement ou met à jour son statut/instrument
    public void upsert(Inscription i) throws SQLException {
        String sql = """
                INSERT INTO INSCRIT (pseudo, nom, instrument, status)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (pseudo, nom) DO UPDATE
                    SET instrument = EXCLUDED.instrument,
                        status     = EXCLUDED.status
                """;
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, i.getPseudo());
            ps.setString(2, i.getNomEvenement());
            ps.setString(3, i.getInstrument());
            ps.setString(4, i.getStatus());
            ps.executeUpdate();
        }
    }

    // -------------------------------------------------------------------------
    // READ
    // -------------------------------------------------------------------------

    // Toutes les inscriptions pour un événement, triées par instrument puis statut
    public List<Inscription> findByEvenement(String nomEvenement) throws SQLException {
        String sql = """
                SELECT * FROM INSCRIT
                WHERE nom = ?
                ORDER BY instrument, status
                """;
        List<Inscription> list = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomEvenement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    // Inscription d'un fanfaron à un événement précis
    public Inscription findByPseudoAndEvenement(String pseudo, String nomEvenement) throws SQLException {
        String sql = "SELECT * FROM INSCRIT WHERE pseudo = ? AND nom = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.setString(2, nomEvenement);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------

    public void delete(String pseudo, String nomEvenement) throws SQLException {
        String sql = "DELETE FROM INSCRIT WHERE pseudo = ? AND nom = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.setString(2, nomEvenement);
            ps.executeUpdate();
        }
    }

    // -------------------------------------------------------------------------
    // MAPPING ResultSet -> Inscription
    // -------------------------------------------------------------------------

    private Inscription map(ResultSet rs) throws SQLException {
        return new Inscription(
                rs.getString("pseudo"),
                rs.getString("nom"),
                rs.getString("instrument"),
                rs.getString("status")
        );
    }
}
