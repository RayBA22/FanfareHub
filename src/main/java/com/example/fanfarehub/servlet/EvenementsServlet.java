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

            // Calcul des droits côté servlet, transmis à la JSP via un attribut
            req.setAttribute("canManage", isAdminOrPrestation(req));

        } catch (SQLException e) {
            req.setAttribute("message", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/evenements.jsp").forward(req, resp);
    }

    private boolean isAdminOrPrestation(HttpServletRequest req) throws SQLException {
        Fanfaron user = (Fanfaron) req.getSession().getAttribute("user");
        if (user == null) return false;
        if ("admin".equals(user.getRole())) return true;

        List<Comission> commissions = DAOFactory.getCommissionDAO().findByPseudo(user.getPseudo());
        for (Comission c : commissions) {
            if ("Prestation".equals(c.getNom())) return true;
        }
        return false;
    }
}
