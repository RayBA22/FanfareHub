package com.example.fanfarehub.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PupitreDAO {

    private final DBConnectionManager dbManager;

    public PupitreDAO(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<Pupitre> findAll() throws SQLException {
        String sql = "SELECT * FROM PUPITRE ORDER BY instrument";
        List<Pupitre> list = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Pupitre(rs.getString("instrument")));
            }
        }
        return list;
    }

    // Pupitres d'un fanfaron donné
    public List<Pupitre> findByPseudo(String pseudo) throws SQLException {
        String sql = """
                SELECT p.instrument FROM PUPITRE p
                JOIN JOUE j ON p.instrument = j.instrument
                WHERE j.pseudo = ?
                ORDER BY p.instrument
                """;
        List<Pupitre> list = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Pupitre(rs.getString("instrument")));
            }
        }
        return list;
    }
}
