package com.example.fanfarehub.Model;

import java.sql.*;
import java.util.List;

// Table PARTICIPE : relation N-N entre Fanfaron et Commission
public class ParticipeDAO {

    private final DBConnectionManager dbManager;

    public ParticipeDAO(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }

    public void add(String pseudo, String nomCommission) throws SQLException {
        String sql = "INSERT INTO PARTICIPE (pseudo, nom) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.setString(2, nomCommission);
            ps.executeUpdate();
        }
    }

    public void delete(String pseudo, String nomCommission) throws SQLException {
        String sql = "DELETE FROM PARTICIPE WHERE pseudo = ? AND nom = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ps.setString(2, nomCommission);
            ps.executeUpdate();
        }
    }

    // Remplace toutes les commissions d'un fanfaron d'un coup
    public void replaceAll(String pseudo, List<String> commissions) throws SQLException {
        String deleteSql = "DELETE FROM PARTICIPE WHERE pseudo = ?";
        String insertSql = "INSERT INTO PARTICIPE (pseudo, nom) VALUES (?, ?)";
        try (Connection conn = dbManager.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement del = conn.prepareStatement(deleteSql)) {
                del.setString(1, pseudo);
                del.executeUpdate();
            }
            try (PreparedStatement ins = conn.prepareStatement(insertSql)) {
                for (String commission : commissions) {
                    ins.setString(1, pseudo);
                    ins.setString(2, commission);
                    ins.addBatch();
                }
                ins.executeBatch();
            }
            conn.commit();
        }
    }
}
