/*
 * Controleur de connexion.
 * Il affiche le formulaire, authentifie le fanfaron, met a jour sa derniere
 * connexion et initialise la session avec ses droits de gestion.
 */
package com.example.fanfarehub.servlet;

import com.example.fanfarehub.Model.DAOFactory;
import com.example.fanfarehub.Model.POJO.Comission;
import com.example.fanfarehub.Model.POJO.Fanfaron;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            session.setAttribute("canManageEvents", isAdminOrPrestation(req));
            resp.sendRedirect(req.getContextPath() + "/accueil");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String pseudo = req.getParameter("pseudo");
        String mdp    = req.getParameter("mdp");

        try {
            Fanfaron fanfaron = DAOFactory.getFanfaronDAO().login(pseudo, mdp);
            if (fanfaron == null) {
                req.setAttribute("error", "Pseudo ou mot de passe incorrect.");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            }
            DAOFactory.getFanfaronDAO().updateDateConnexion(pseudo);

            HttpSession session = req.getSession(true);
            session.setAttribute("user", fanfaron);
            // user en session avant l'appel pour que isAdminOrPrestation puisse le lire
            session.setAttribute("canManageEvents", isAdminOrPrestation(req));

            resp.sendRedirect(req.getContextPath() + "/accueil");

        } catch (SQLException e) {
            req.setAttribute("error", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

    private boolean isAdminOrPrestation(HttpServletRequest req) {
        Fanfaron user = (Fanfaron) req.getSession().getAttribute("user");
        if (user == null) return false;
        if ("admin".equals(user.getRole())) return true;
        try {
            List<Comission> commissions = DAOFactory.getCommissionDAO().findByPseudo(user.getPseudo());
            for (Comission c : commissions) {
                if ("Prestation".equals(c.getNom())) return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}
