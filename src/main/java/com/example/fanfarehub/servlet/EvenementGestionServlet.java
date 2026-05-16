package com.example.fanfarehub.servlet;

import com.example.fanfarehub.Model.DAOFactory;
import com.example.fanfarehub.Model.POJO.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/evenement/gestion")
public class EvenementGestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!isAdminOrPrestation(req)) { resp.sendError(HttpServletResponse.SC_FORBIDDEN); return; }

        String action = req.getParameter("action");
        if ("modifier".equals(action)) {
            String nom = req.getParameter("nom");
            try {
                Evenement e = DAOFactory.getEvenementDAO().findByNom(nom);
                if (e == null) {
                    req.setAttribute("message", "Événement introuvable.");
                    req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
                    return;
                }
                req.setAttribute("evenement", e);
            } catch (SQLException e) {
                req.setAttribute("message", "Erreur base de données : " + e.getMessage());
                req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
                return;
            }
        }
        req.setAttribute("action", action != null ? action : "ajouter");
        req.getRequestDispatcher("/WEB-INF/views/evenement-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!isAdminOrPrestation(req)) { resp.sendError(HttpServletResponse.SC_FORBIDDEN); return; }
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        Fanfaron user = (Fanfaron) req.getSession().getAttribute("user");

        try {
            if ("supprimer".equals(action)) {
                String nom = req.getParameter("nom");
                List<Inscrit> inscrits = DAOFactory.getInscriptionDAO().findByEvenement(nom);
                for (Inscrit i : inscrits) {
                    DAOFactory.getInscriptionDAO().delete(i.getPseudo(), nom);
                }
                DAOFactory.getEvenementDAO().delete(nom);
                resp.sendRedirect(req.getContextPath() + "/evenements");

            } else if ("modifier".equals(action)) {
                String nom         = req.getParameter("nom");
                String type        = req.getParameter("type");
                String dateStr     = req.getParameter("date");
                String duree       = req.getParameter("duree");
                String lieu        = req.getParameter("lieu");
                String description = req.getParameter("description");

                java.sql.Date date = (dateStr != null && !dateStr.isBlank())
                        ? java.sql.Date.valueOf(dateStr) : null;

                Evenement existing = DAOFactory.getEvenementDAO().findByNom(nom);
                if (existing == null) {
                    req.setAttribute("message", "Événement introuvable.");
                    req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
                    return;
                }
                existing.setType(type);
                existing.setDate(date);
                existing.setDuree(duree);
                existing.setLieu(lieu);
                existing.setDescription(description);
                DAOFactory.getEvenementDAO().update(existing);
                resp.sendRedirect(req.getContextPath() + "/evenement?nom=" + encodeUrl(nom));

            } else { // ajouter
                String nom         = req.getParameter("nom");
                String type        = req.getParameter("type");
                String dateStr     = req.getParameter("date");
                String duree       = req.getParameter("duree");
                String lieu        = req.getParameter("lieu");
                String description = req.getParameter("description");

                if (nom == null || nom.isBlank()) {
                    req.setAttribute("error", "Le nom est obligatoire.");
                    req.setAttribute("action", "ajouter");
                    req.getRequestDispatcher("/WEB-INF/views/evenement-form.jsp").forward(req, resp);
                    return;
                }

                java.sql.Date date = (dateStr != null && !dateStr.isBlank())
                        ? java.sql.Date.valueOf(dateStr) : null;

                Evenement e = new Evenement(nom, type, date, duree, lieu, description, user.getPseudo());
                DAOFactory.getEvenementDAO().create(e);
                resp.sendRedirect(req.getContextPath() + "/evenement?nom=" + encodeUrl(nom));
            }

        } catch (SQLException e) {
            req.setAttribute("message", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    // SQLException absorbée ici : doGet/doPost ne la déclarent pas → plus d'UnhandledException
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
            return false; // erreur base = accès refusé par sécurité
        }
        return false;
    }

    private String encodeUrl(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            return value;
        }
    }
}