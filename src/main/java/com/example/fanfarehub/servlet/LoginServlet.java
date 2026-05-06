package com.example.fanfarehub.servlet;

import com.example.fanfarehub.Model.DAOFactory;
import com.example.fanfarehub.Model.POJO.Fanfaron;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Si déjà connecté, rediriger vers accueil
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
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
            resp.sendRedirect(req.getContextPath() + "/accueil");

        } catch (SQLException e) {
            req.setAttribute("error", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
