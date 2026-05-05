package com.example.fanfarehub.Model.DAO;

import com.example.fanfarehub.Model.*;
import com.example.fanfarehub.Model.POJO.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComissionDAO {
    private final DBConnectionManager dbManager;

    public ComissionDAO(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<Comission> findAll() throws SQLException {
        String sql = "SELECT * FROM COMISSION ORDER BY nom";
        List<Comission> list = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Comission(rs.getString("nom")));
            }
        }
        return list;
    }

    // Comissions d'un fanfaron donné
    public List<Comission> findByPseudo(String pseudo) throws SQLException {
        String sql = """
                SELECT c.nom FROM COMISSION c
                JOIN PARTICIPE p ON c.nom = p.nom
                WHERE p.pseudo = ?
                ORDER BY c.nom
                """;
        List<Comission> list = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Comission(rs.getString("nom")));
            }
        }
        return list;
    }
}
