package com.example.fanfarehub.servlet;

import com.example.fanfarehub.Model.DAOFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/evenements")
public class EvenementsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String type = req.getParameter("type");
        try {
            if (type != null && !type.isBlank()) {
                req.setAttribute("evenements", DAOFactory.getEvenementDAO().findByType(type));
                req.setAttribute("filtreType", type);
            } else {
                req.setAttribute("evenements", DAOFactory.getEvenementDAO().findAll());
            }
        } catch (SQLException e) {
            req.setAttribute("message", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/evenements.jsp").forward(req, resp);
    }
}
