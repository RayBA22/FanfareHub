<%--
  Vue publique de connexion.
  Elle affiche le formulaire d'authentification, les erreurs de login
  et un lien vers l'inscription pour les nouveaux utilisateurs.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-page">
<main class="auth-container">
    <h1 class="auth-title">FanfareHub</h1>
    <h2>Connexion</h2>

    <% if ("ok".equals(request.getParameter("inscription"))) { %>
    <div class="alert alert-success">Compte cree avec succes ! Connectez-vous.</div>
    <% } %>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-error">${error}</div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <div class="form-group">
            <label for="pseudo">Pseudo</label>
            <input type="text" id="pseudo" name="pseudo" required autofocus>
        </div>
        <div class="form-group">
            <label for="mdp">Mot de passe</label>
            <input type="password" id="mdp" name="mdp" required>
        </div>
        <button type="submit" class="btn btn-primary btn-full">Se connecter</button>
    </form>
    <p class="auth-link">Pas encore de compte ? <a href="${pageContext.request.contextPath}/inscription">S'inscrire</a></p>
</main>
</body>
</html>
