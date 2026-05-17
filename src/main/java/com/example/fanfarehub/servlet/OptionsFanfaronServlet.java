/*
 * Controleur des options du fanfaron connecte.
 * Il affiche les pupitres et commissions disponibles, preselectionne ceux
 * de l'utilisateur et remplace ses choix lors de l'enregistrement.
 */
package com.example.fanfarehub.servlet;

import com.example.fanfarehub.Model.DAOFactory;
import com.example.fanfarehub.Model.POJO.Fanfaron;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Options du fanfaron connecté : modifier ses pupitres (instruments joués)
 * et ses commissions.
 */
@WebServlet("/options")
public class OptionsFanfaronServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Fanfaron user = (Fanfaron) req.getSession().getAttribute("user");
        try {
            req.setAttribute("tousPupitres",    DAOFactory.getPupitreDAO().findAll());
            req.setAttribute("mesPupitres",     DAOFactory.getPupitreDAO().findByPseudo(user.getPseudo()));
            req.setAttribute("toutesCommissions", DAOFactory.getCommissionDAO().findAll());
            req.setAttribute("mesCommissions",  DAOFactory.getCommissionDAO().findByPseudo(user.getPseudo()));
        } catch (SQLException e) {
            req.setAttribute("message", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/options.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Fanfaron user = (Fanfaron) req.getSession().getAttribute("user");

        String[] instrumentsArr  = req.getParameterValues("instruments");
        String[] commissionsArr  = req.getParameterValues("commissions");

        List<String> instruments  = instrumentsArr  != null ? Arrays.asList(instrumentsArr)  : Collections.emptyList();
        List<String> commissions  = commissionsArr  != null ? Arrays.asList(commissionsArr)  : Collections.emptyList();

        try {
            DAOFactory.getJoueDAO().replaceAll(user.getPseudo(), instruments);
            DAOFactory.getParticipeDAO().replaceAll(user.getPseudo(), commissions);
            resp.sendRedirect(req.getContextPath() + "/options?succes=1");
        } catch (SQLException e) {
            req.setAttribute("message", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
