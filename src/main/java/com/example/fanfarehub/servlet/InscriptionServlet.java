package com.example.fanfarehub.servlet;

import com.example.fanfarehub.Model.DAOFactory;
import com.example.fanfarehub.Model.DAO.FanfaronDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("pupitres",    DAOFactory.getPupitreDAO().findAll());
            req.setAttribute("commissions", DAOFactory.getCommissionDAO().findAll());
        } catch (SQLException e) {
            req.setAttribute("message", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/inscription.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String pseudo  = req.getParameter("pseudo");
        String email   = req.getParameter("email");
        String mdp     = req.getParameter("mdp");
        String prenom  = req.getParameter("prenom");
        String nom     = req.getParameter("nom");
        String genre   = req.getParameter("genre");
        String regime  = req.getParameter("regime");

        String[] instrumentsArr  = req.getParameterValues("instruments");
        String[] commissionsArr  = req.getParameterValues("commissions");

        // Validation basique
        if (pseudo == null || pseudo.isBlank() || mdp == null || mdp.isBlank()) {
            req.setAttribute("error", "Le pseudo et le mot de passe sont obligatoires.");
            doGet(req, resp);
            return;
        }

        try {
            FanfaronDAO dao = DAOFactory.getFanfaronDAO();

            if (dao.pseudoExists(pseudo)) {
                req.setAttribute("error", "Ce pseudo est déjà utilisé.");
                doGet(req, resp);
                return;
            }
            if (email != null && !email.isBlank() && dao.emailExists(email)) {
                req.setAttribute("error", "Cet email est déjà utilisé.");
                doGet(req, resp);
                return;
            }

            dao.create(pseudo, email, mdp, prenom, nom, genre, regime);

            // Enregistrer les instruments joués
            if (instrumentsArr != null && instrumentsArr.length > 0) {
                List<String> instruments = Arrays.asList(instrumentsArr);
                DAOFactory.getJoueDAO().replaceAll(pseudo, instruments);
            }

            // Enregistrer les commissions
            if (commissionsArr != null && commissionsArr.length > 0) {
                List<String> commissions = Arrays.asList(commissionsArr);
                DAOFactory.getParticipeDAO().replaceAll(pseudo, commissions);
            }

            resp.sendRedirect(req.getContextPath() + "/login?inscription=ok");

        } catch (SQLException e) {
            req.setAttribute("error", "Erreur base de données : " + e.getMessage());
            try {
                req.setAttribute("pupitres",    DAOFactory.getPupitreDAO().findAll());
                req.setAttribute("commissions", DAOFactory.getCommissionDAO().findAll());
            } catch (SQLException ex) { /* ignore */ }
            req.getRequestDispatcher("/WEB-INF/views/inscription.jsp").forward(req, resp);
        }
    }
}
