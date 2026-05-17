/*
 * Controleur d'administration des fanfarons.
 * Il permet a l'admin d'ajouter, modifier ou supprimer un membre,
 * ainsi que de mettre a jour ses instruments, commissions, role et mot de passe.
 */
package com.example.fanfarehub.servlet;

import com.example.fanfarehub.Model.DAOFactory;
import com.example.fanfarehub.Model.POJO.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

@WebServlet("/fanfaron/gestion")
public class FanfaronGestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!isAdmin(req)) { resp.sendError(HttpServletResponse.SC_FORBIDDEN); return; }

        String action = req.getParameter("action");
        try {
            req.setAttribute("pupitres",    DAOFactory.getPupitreDAO().findAll());
            req.setAttribute("commissions", DAOFactory.getCommissionDAO().findAll());

            if ("modifier".equals(action)) {
                String pseudo = req.getParameter("pseudo");
                Fanfaron f = DAOFactory.getFanfaronDAO().findByPseudo(pseudo);
                if (f == null) {
                    req.setAttribute("message", "Fanfaron introuvable.");
                    req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
                    return;
                }
                req.setAttribute("fanfaron",       f);
                req.setAttribute("mesPupitres",    DAOFactory.getPupitreDAO().findByPseudo(pseudo));
                req.setAttribute("mesCommissions", DAOFactory.getCommissionDAO().findByPseudo(pseudo));
            }
        } catch (SQLException e) {
            req.setAttribute("message", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("action", action != null ? action : "ajouter");
        req.getRequestDispatcher("/WEB-INF/views/fanfaron-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!isAdmin(req)) { resp.sendError(HttpServletResponse.SC_FORBIDDEN); return; }
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        try {
            if ("supprimer".equals(action)) {
                String pseudo = req.getParameter("pseudo");
                DAOFactory.getJoueDAO().replaceAll(pseudo, Collections.emptyList());
                DAOFactory.getParticipeDAO().replaceAll(pseudo, Collections.emptyList());
                DAOFactory.getInscriptionDAO().deleteAllByPseudo(pseudo);
                DAOFactory.getEvenementDAO().deleteAllByPseudo(pseudo);
                DAOFactory.getFanfaronDAO().delete(pseudo);
                resp.sendRedirect(req.getContextPath() + "/fanfarons");

            } else if ("modifier".equals(action)) {
                String pseudo     = req.getParameter("pseudo");
                String email      = req.getParameter("email");
                String prenom     = req.getParameter("prenom");
                String nom        = req.getParameter("nom");
                String genre      = req.getParameter("genre");
                String regime     = req.getParameter("regime");
                String role       = req.getParameter("role");
                String mdp        = req.getParameter("mdp");
                String mdpConfirm = req.getParameter("mdpConfirm");

                // Confirmaton
                if (mdp != null && !mdp.isBlank()) {
                    if (!mdp.equals(mdpConfirm)) {
                        req.setAttribute("error", "Les deux mots de passe ne correspondent pas.");
                        req.setAttribute("action", "modifier");
                        req.setAttribute("fanfaron",       DAOFactory.getFanfaronDAO().findByPseudo(pseudo));
                        req.setAttribute("pupitres",       DAOFactory.getPupitreDAO().findAll());
                        req.setAttribute("commissions",    DAOFactory.getCommissionDAO().findAll());
                        req.setAttribute("mesPupitres",    DAOFactory.getPupitreDAO().findByPseudo(pseudo));
                        req.setAttribute("mesCommissions", DAOFactory.getCommissionDAO().findByPseudo(pseudo));
                        req.getRequestDispatcher("/WEB-INF/views/fanfaron-form.jsp").forward(req, resp);
                        return;
                    }
                    // Mise à jour du mot de passe uniquement si un nouveau est fourni
                    DAOFactory.getFanfaronDAO().updateMotDePasse(pseudo, mdp);
                }

                // ── Mise à jour des informations du fanfaron ──────────────────
                Fanfaron f = DAOFactory.getFanfaronDAO().findByPseudo(pseudo);
                if (f == null) {
                    req.setAttribute("message", "Fanfaron introuvable.");
                    req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
                    return;
                }
                f.setEmail(email);
                f.setPrenom(prenom);
                f.setNom(nom);
                f.setGenre(genre);
                f.setRegime(regime);
                f.setRole(role != null ? role : "fanfaron");
                DAOFactory.getFanfaronDAO().update(f);

                // ── Mise à jour instruments et commissions ────────────────────
                String[] instrumentsArr = req.getParameterValues("instruments");
                String[] commissionsArr = req.getParameterValues("commissions");
                DAOFactory.getJoueDAO().replaceAll(pseudo,
                        instrumentsArr != null ? Arrays.asList(instrumentsArr) : Collections.emptyList());
                DAOFactory.getParticipeDAO().replaceAll(pseudo,
                        commissionsArr != null ? Arrays.asList(commissionsArr) : Collections.emptyList());

                resp.sendRedirect(req.getContextPath() + "/fanfarons");

            } else { // ajouter
                String pseudo  = req.getParameter("pseudo");
                String email   = req.getParameter("email");
                String mdp     = req.getParameter("mdp");
                String prenom  = req.getParameter("prenom");
                String nom     = req.getParameter("nom");
                String genre   = req.getParameter("genre");
                String regime  = req.getParameter("regime");
                String role    = req.getParameter("role");

                if (DAOFactory.getFanfaronDAO().pseudoExists(pseudo)) {
                    req.setAttribute("error", "Ce pseudo est déjà utilisé.");
                    req.setAttribute("action", "ajouter");
                    req.setAttribute("pupitres",    DAOFactory.getPupitreDAO().findAll());
                    req.setAttribute("commissions", DAOFactory.getCommissionDAO().findAll());
                    req.getRequestDispatcher("/WEB-INF/views/fanfaron-form.jsp").forward(req, resp);
                    return;
                }

                DAOFactory.getFanfaronDAO().create(pseudo, email, mdp, prenom, nom, genre, regime);

                if (role != null && !role.equals("fanfaron")) {
                    Fanfaron f = DAOFactory.getFanfaronDAO().findByPseudo(pseudo);
                    f.setRole(role);
                    DAOFactory.getFanfaronDAO().update(f);
                }

                String[] instrumentsArr = req.getParameterValues("instruments");
                String[] commissionsArr = req.getParameterValues("commissions");
                if (instrumentsArr != null)
                    DAOFactory.getJoueDAO().replaceAll(pseudo, Arrays.asList(instrumentsArr));
                if (commissionsArr != null)
                    DAOFactory.getParticipeDAO().replaceAll(pseudo, Arrays.asList(commissionsArr));

                resp.sendRedirect(req.getContextPath() + "/fanfarons");
            }

        } catch (SQLException e) {
            req.setAttribute("message", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    private boolean isAdmin(HttpServletRequest req) {
        Fanfaron user = (Fanfaron) req.getSession().getAttribute("user");
        return user != null && "admin".equals(user.getRole());
    }
}
