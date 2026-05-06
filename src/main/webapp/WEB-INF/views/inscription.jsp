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
    <h2>Creer un compte</h2>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-error">${error}</div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/inscription">
        <div class="form-row">
            <div class="form-group">
                <label for="pseudo">Pseudo *</label>
                <input type="text" id="pseudo" name="pseudo" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group">
                <label for="prenom">Prenom</label>
                <input type="text" id="prenom" name="prenom">
            </div>
            <div class="form-group">
                <label for="nom">Nom</label>
                <input type="text" id="nom" name="nom">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group">
                <label for="genre">Genre</label>
                <select id="genre" name="genre">
                    <option value="">-- Selectioner --</option>
                    <option value="homme">Homme</option>
                    <option value="femme">Femme</option>
                    <option value="autre">Autre</option>
                </select>
            </div>
            <div class="form-group">
                <label for="regime">Regime</label>
                <select id="regime" name="regime">
                    <option value="">-- Selectioner --</option>
                    <option value="actif">Actif</option>
                    <option value="inactif">Inactif</option>
                    <option value="honoraire">Honoraire</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="mdp">Mot de passe *</label>
            <input type="password" id="mdp" name="mdp" required>
        </div>

        <%
            List<Pupitre> pupitres = (List<Pupitre>) request.getAttribute("pupitres");
            if (pupitres != null && !pupitres.isEmpty()) {
        %>
        <div class="form-group">
            <label>Instruments joues</label>
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

        <button type="submit" class="btn btn-primary btn-full">Creer le compte</button>
    </form>
    <p class="auth-link">Deja un compte ? <a href="${pageContext.request.contextPath}/login">Se connecter</a></p>
</main>
</body>
</html>
