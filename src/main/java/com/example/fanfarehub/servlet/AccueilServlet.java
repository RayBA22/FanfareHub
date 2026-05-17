/*
 * Controleur de la page d'accueil connectee.
 * Il recupere l'utilisateur en session, charge les evenements, ses pupitres
 * et ses commissions, puis transmet ces donnees a la vue d'accueil.
 */
package com.example.fanfarehub.servlet;

import com.example.fanfarehub.Model.DAOFactory;
import com.example.fanfarehub.Model.POJO.Fanfaron;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Fanfaron user = (Fanfaron) req.getSession().getAttribute("user");
        try {
            req.setAttribute("evenements", DAOFactory.getEvenementDAO().findAll());
            req.setAttribute("mesPupitres", DAOFactory.getPupitreDAO().findByPseudo(user.getPseudo()));
            req.setAttribute("mesCommissions", DAOFactory.getCommissionDAO().findByPseudo(user.getPseudo()));
        } catch (SQLException e) {
            req.setAttribute("message", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/accueil.jsp").forward(req, resp);
    }
}
