package com.example.fanfarehub.Model.DAO;

public class DAOFactory {

    protected static final DBConnectionManager dbManager = DBConnectionManager.getInstance();

    public static FanfaronDAO getFanfaronDAO() {
        return new FanfaronDAO(dbManager);
    }

    public static EvenementDAO getEvenementDAO() {
        return new EvenementDAO(dbManager);
    }

    public static CommissionDAO getCommissionDAO() {
        return new CommissionDAO(dbManager);
    }

    public static PupitreDAO getPupitreDAO() {
        return new PupitreDAO(dbManager);
    }

    public static InscriptionDAO getInscriptionDAO() {
        return new InscriptionDAO(dbManager);
    }

    public static JoueDAO getJoueDAO() {
        return new JoueDAO(dbManager);
    }

    public static ParticipeDAO getParticipeDAO() {
        return new ParticipeDAO(dbManager);
    }
}
