package com.example.fanfarehub.servlet;

import com.example.fanfarehub.Model.DAOFactory;
import com.example.fanfarehub.Model.POJO.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Inscription / modification / désinscription d'un fanfaron à un événement.
 * POST uniquement.
 */
@WebServlet("/evenement/inscrire")
public class InscriptionEvenementServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Fanfaron user       = (Fanfaron) req.getSession().getAttribute("user");
        String nomEvenement = req.getParameter("nomEvenement");
        String action       = req.getParameter("action");

        try {

            if ("desinscrire".equals(action)) {

                DAOFactory.getInscriptionDAO()
                        .delete(user.getPseudo(), nomEvenement);

            } else {

                String instrument = req.getParameter("instrument");
                String status     = req.getParameter("status");

                Inscrit i = new Inscrit(
                        user.getPseudo(),
                        nomEvenement,
                        instrument,
                        status
                );

                DAOFactory.getInscriptionDAO().upsert(i);
            }

            resp.sendRedirect(
                    req.getContextPath()
                            + "/evenement?nom="
                            + encodeUrl(nomEvenement)
            );

        } catch (SQLException e) {

            req.setAttribute(
                    "message",
                    "Erreur base de données : " + e.getMessage()
            );

            req.getRequestDispatcher("/WEB-INF/views/error.jsp")
                    .forward(req, resp);
        }
    }

    private String encodeUrl(String value) {

        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            return value;
        }
    }
}