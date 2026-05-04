package com.example.fanfarehub.Model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommissionDAO {

    private final DBConnectionManager dbManager;

    public CommissionDAO(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<Commission> findAll() throws SQLException {
        String sql = "SELECT * FROM COMISSION ORDER BY nom";
        List<Commission> list = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Commission(rs.getString("nom")));
            }
        }
        return list;
    }

    // Commissions d'un fanfaron donné
    public List<Commission> findByPseudo(String pseudo) throws SQLException {
        String sql = """
                SELECT c.nom FROM COMISSION c
                JOIN PARTICIPE p ON c.nom = p.nom
                WHERE p.pseudo = ?
                ORDER BY c.nom
                """;
        List<Commission> list = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Commission(rs.getString("nom")));
            }
        }
        return list;
    }
}
