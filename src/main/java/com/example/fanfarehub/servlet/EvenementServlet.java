package com.example.fanfarehub.servlet;

import com.example.fanfarehub.Model.DAOFactory;
import com.example.fanfarehub.Model.POJO.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/evenement")
public class EvenementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String nom = req.getParameter("nom");
        if (nom == null || nom.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/evenements");
            return;
        }

        Fanfaron user = (Fanfaron) req.getSession().getAttribute("user");
        try {
            Evenement evenement = DAOFactory.getEvenementDAO().findByNom(nom);
            if (evenement == null) {
                req.setAttribute("message", "Événement introuvable.");
                req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
                return;
            }
            req.setAttribute("evenement",       evenement);
            req.setAttribute("inscrits",         DAOFactory.getInscriptionDAO().findByEvenement(nom));
            req.setAttribute("monInscription",   DAOFactory.getInscriptionDAO().findByPseudoAndEvenement(user.getPseudo(), nom));
            req.setAttribute("mesPupitres",      DAOFactory.getPupitreDAO().findByPseudo(user.getPseudo()));
        } catch (SQLException e) {
            req.setAttribute("message", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/evenement.jsp").forward(req, resp);
    }
}
