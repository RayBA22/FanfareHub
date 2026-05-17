/*
 * DAO de l'association entre fanfarons et pupitres.
 * Il ajoute, supprime ou remplace en transaction tous les instruments
 * joues par un membre.
 */
package com.example.fanfarehub.Model.DAO;

import com.example.fanfarehub.Model.*;
import com.example.fanfarehub.Model.POJO.*;

import java.sql.*;
import java.util.List;

public class JoueDAO {
    private final DBConnectionManager dbManager;

    public JoueDAO(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }

    public void add(String pseudo, String instrument) throws SQLException {
        String sql = "INSERT INTO JOUE (pseudo, instrument) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.setString(2, instrument);
            ps.executeUpdate();
        }
    }

    public void delete(String pseudo, String instrument) throws SQLException {
        String sql = "DELETE FROM JOUE WHERE pseudo = ? AND instrument = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.setString(2, instrument);
            ps.executeUpdate();
        }
    }

    // Remplace tous les pupitres d'un fanfaron d'un coup
    public void replaceAll(String pseudo, List<String> instruments) throws SQLException {
        String deleteSql = "DELETE FROM JOUE WHERE pseudo = ?";
        String insertSql = "INSERT INTO JOUE (pseudo, instrument) VALUES (?, ?)";
        try (Connection conn = dbManager.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement del = conn.prepareStatement(deleteSql)) {
                del.setString(1, pseudo);
                del.executeUpdate();
            }
            try (PreparedStatement ins = conn.prepareStatement(insertSql)) {
                for (String instrument : instruments) {
                    ins.setString(1, pseudo);
                    ins.setString(2, instrument);
                    ins.addBatch();
                }
                ins.executeBatch();
            }
            conn.commit();
        }
    }
}
