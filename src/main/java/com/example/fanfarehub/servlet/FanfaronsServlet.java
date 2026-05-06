package com.example.fanfarehub.servlet;

import com.example.fanfarehub.Model.DAOFactory;
import com.example.fanfarehub.Model.POJO.Fanfaron;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Liste de tous les fanfarons. Accessible uniquement à l'admin.
 */
@WebServlet("/fanfarons")
public class FanfaronsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!isAdmin(req)) { resp.sendError(HttpServletResponse.SC_FORBIDDEN); return; }
        try {
            req.setAttribute("fanfarons", DAOFactory.getFanfaronDAO().findAll());
        } catch (SQLException e) {
            req.setAttribute("message", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/fanfarons.jsp").forward(req, resp);
    }

    private boolean isAdmin(HttpServletRequest req) {
        Fanfaron user = (Fanfaron) req.getSession().getAttribute("user");
        return user != null && "admin".equals(user.getRole());
    }
}
