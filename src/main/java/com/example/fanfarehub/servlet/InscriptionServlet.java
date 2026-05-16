package com.example.fanfarehub.servlet;

import com.example.fanfarehub.Model.DAOFactory;
import com.example.fanfarehub.Model.DAO.FanfaronDAO;
import com.example.fanfarehub.Model.POJO.Fanfaron;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("pupitres",    DAOFactory.getPupitreDAO().findAll());
            req.setAttribute("commissions", DAOFactory.getCommissionDAO().findAll());
        } catch (SQLException e) {
            req.setAttribute("message", "Erreur base de données : " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/inscription.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String pseudo              = req.getParameter("pseudo");
        String email               = req.getParameter("email");
        String emailConfirm        = req.getParameter("emailConfirm");
        String mdp                 = req.getParameter("mdp");
        String mdpConfirm          = req.getParameter("mdpConfirm");
        String prenom              = req.getParameter("prenom");
        String nom                 = req.getParameter("nom");
        String genre               = req.getParameter("genre");
        String contrainteAlimentaire = req.getParameter("contrainteAlimentaire");

        String[] instrumentsArr  = req.getParameterValues("instruments");
        String[] commissionsArr  = req.getParameterValues("commissions");

        //  Champs obligatoires
        if (pseudo == null || pseudo.isBlank() || mdp == null || mdp.isBlank()) {
            forwardWithError(req, resp, "Le nom de fanfaron et le mot de passe sont obligatoires.");
            return;
        }

        //  Confirmation de l'adresse email
        if (email == null || email.isBlank()) {
            forwardWithError(req, resp, "L'adresse email est obligatoire.");
            return;
        }
        if (!email.equals(emailConfirm)) {
            forwardWithError(req, resp, "Les deux adresses email ne correspondent pas.");
            return;
        }

        //  Confirmation du mot de passe
        if (!mdp.equals(mdpConfirm)) {
            forwardWithError(req, resp, "Les deux mots de passe ne correspondent pas.");
            return;
        }

        try {
            FanfaronDAO dao = DAOFactory.getFanfaronDAO();

            //  Unicité du nom de fanfaron
            if (dao.pseudoExists(pseudo)) {
                forwardWithError(req, resp, "Ce nom de fanfaron est déjà utilisé.");
                return;
            }

            //  Unicité de l'adresse email
            if (dao.emailExists(email)) {
                forwardWithError(req, resp, "Cette adresse email est déjà utilisée.");
                return;
            }

            //  Création du compte
            dao.create(pseudo, email, mdp, prenom, nom, genre, contrainteAlimentaire);

            //  Connexion automatique
            HttpSession session = req.getSession(true);
            Fanfaron fanfaron = dao.findByPseudo(pseudo);
            session.setAttribute("user", fanfaron);

            // Enregistrer les instruments joués
            if (instrumentsArr != null && instrumentsArr.length > 0) {
                DAOFactory.getJoueDAO().replaceAll(pseudo, Arrays.asList(instrumentsArr));
            }

            // Enregistrer les commissions
            if (commissionsArr != null && commissionsArr.length > 0) {
                DAOFactory.getParticipeDAO().replaceAll(pseudo, Arrays.asList(commissionsArr));
            }

            resp.sendRedirect(req.getContextPath() + "/login");

        } catch (SQLException e) {
            req.setAttribute("error", "Erreur base de données : " + e.getMessage());
            try {
                req.setAttribute("pupitres",    DAOFactory.getPupitreDAO().findAll());
                req.setAttribute("commissions", DAOFactory.getCommissionDAO().findAll());
            } catch (SQLException ex) { /* ignore */ }
            req.getRequestDispatcher("/WEB-INF/views/inscription.jsp").forward(req, resp);
        }
    }

    /**
     * Réaffiche le formulaire d'inscription en y injectant un message d'erreur.
     * Recharge aussi les listes pupitres/commissions nécessaires à la JSP.
     */
    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        try {
            req.setAttribute("pupitres",    DAOFactory.getPupitreDAO().findAll());
            req.setAttribute("commissions", DAOFactory.getCommissionDAO().findAll());
        } catch (SQLException e) { /* les listes resteront null, la JSP gère ce cas */ }
        req.getRequestDispatcher("/WEB-INF/views/inscription.jsp").forward(req, resp);
    }
}