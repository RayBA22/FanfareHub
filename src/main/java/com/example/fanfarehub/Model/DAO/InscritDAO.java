package com.example.fanfarehub.Model.DAO;

import com.example.fanfarehub.Model.*;
import com.example.fanfarehub.Model.POJO.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscritDAO {
    private final DBConnectionManager dbManager;

    public InscritDAO(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }
    
    public void upsert(Inscrit i) throws SQLException {
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
            ps.setString(2, i.getNom());
            ps.setString(3, i.getInstrument());
            ps.setString(4, i.getStatus());
            ps.executeUpdate();
        }
    }

    public List<Inscrit> findByEvenement(String nomEvenement) throws SQLException {
        String sql = """
                SELECT * FROM INSCRIT
                WHERE nom = ?
                ORDER BY instrument, status
                """;
        List<Inscrit> list = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomEvenement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Inscrit findByPseudoAndEvenement(String pseudo, String nomEvenement) throws SQLException {
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

    public void delete(String pseudo, String nomEvenement) throws SQLException {
        String sql = "DELETE FROM INSCRIT WHERE pseudo = ? AND nom = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.setString(2, nomEvenement);
            ps.executeUpdate();
        }
    }

    public void deleteAllByPseudo(String pseudo) throws SQLException {

        String sql = """
            DELETE FROM INSCRIT
            WHERE nom IN (
                SELECT nom
                FROM EVENEMENT
                WHERE pseudo = ?
            )
            """;

        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pseudo);

            ps.executeUpdate();
        }
    }

    private Inscrit map(ResultSet rs) throws SQLException {
        return new Inscrit(
                rs.getString("pseudo"),
                rs.getString("nom"),
                rs.getString("instrument"),
                rs.getString("status")
        );
    }
}
