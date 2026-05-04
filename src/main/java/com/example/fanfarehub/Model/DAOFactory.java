package com.example.fanfarehub.Model;

public class DAOFactory {
    protected static final DBConnectionManager dbManager = DBConnectionManager.getInstance();

    public static JoueurDAO getClientDAO() {
        return new JoueurDAO(dbManager);
    }
}
