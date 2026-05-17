<%--
  Vue publique de creation de compte.
  Elle recueille l'identite, les identifiants, les preferences alimentaires,
  les instruments et les commissions avant envoi a InscriptionServlet.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.fanfarehub.Model.POJO.*,java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-page">
<main class="auth-container wide">
    <h1 class="auth-title">FanfareHub</h1>
    <h2>Créer un compte</h2>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-error">${error}</div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/inscription">

        <%-- Identifiant unique --%>
        <div class="form-group">
            <label for="pseudo">Nom de fanfaron *<span class="field-hint"> (identifiant de connexion)</span></label>
            <input type="text" id="pseudo" name="pseudo" required>
        </div>

        <%-- Email saisi deux fois --%>
        <div class="form-row">
            <div class="form-group">
                <label for="email">Adresse email *</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="emailConfirm">Confirmer l'adresse email *</label>
                <input type="email" id="emailConfirm" name="emailConfirm" required>
            </div>
        </div>

        <%-- Mot de passe saisi deux fois --%>
        <div class="form-row">
            <div class="form-group">
                <label for="mdp">Mot de passe *</label>
                <input type="password" id="mdp" name="mdp" required>
            </div>
            <div class="form-group">
                <label for="mdpConfirm">Confirmer le mot de passe *</label>
                <input type="password" id="mdpConfirm" name="mdpConfirm" required>
            </div>
        </div>

        <%-- Prénom et Nom --%>
        <div class="form-row">
            <div class="form-group">
                <label for="prenom">Prénom</label>
                <input type="text" id="prenom" name="prenom">
            </div>
            <div class="form-group">
                <label for="nom">Nom</label>
                <input type="text" id="nom" name="nom">
            </div>
        </div>

        <%-- Genre --%>
        <div class="form-row">
            <div class="form-group">
                <label for="genre">Genre</label>
                <select id="genre" name="genre">
                    <option value="">-- Sélectionner --</option>
                    <option value="homme">Homme</option>
                    <option value="femme">Femme</option>
                    <option value="autre">Autre</option>
                </select>
            </div>

            <%-- Contraintes alimentaires --%>
            <div class="form-group">
                <label for="contrainteAlimentaire">Contraintes alimentaires</label>
                <select id="contrainteAlimentaire" name="contrainteAlimentaire">
                    <option value="aucune">Aucune</option>
                    <option value="vegetarien">Végétarien</option>
                    <option value="vegan">Vegan</option>
                    <option value="sans_porc">Sans porc</option>
                </select>
            </div>
        </div>

        <%-- Instruments --%>
        <%
            List<Pupitre> pupitres = (List<Pupitre>) request.getAttribute("pupitres");
            if (pupitres != null && !pupitres.isEmpty()) {
        %>
        <div class="form-group">
            <label>Instruments joués</label>
            <div class="checkbox-grid">
                <% for (Pupitre p : pupitres) { %>
                <label class="checkbox-label">
                    <input type="checkbox" name="instruments" value="<%= p.getInstrument() %>">
                    <%= p.getInstrument() %>
                </label>
                <% } %>
            </div>
        </div>
        <% } %>

        <%-- Commissions --%>
        <%
            List<Comission> commissions = (List<Comission>) request.getAttribute("commissions");
            if (commissions != null && !commissions.isEmpty()) {
        %>
        <div class="form-group">
            <label>Commissions</label>
            <div class="checkbox-grid">
                <% for (Comission c : commissions) { %>
                <label class="checkbox-label">
                    <input type="checkbox" name="commissions" value="<%= c.getNom() %>">
                    <%= c.getNom() %>
                </label>
                <% } %>
            </div>
        </div>
        <% } %>

        <button type="submit" class="btn btn-primary btn-full">Créer le compte</button>
    </form>
    <p class="auth-link">Déjà un compte ? <a href="${pageContext.request.contextPath}/login">Se connecter</a></p>
</main>
</body>
</html>
