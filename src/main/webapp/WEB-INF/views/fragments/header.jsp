<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    com.example.fanfarehub.Model.POJO.Fanfaron navUser =
            (com.example.fanfarehub.Model.POJO.Fanfaron) session.getAttribute("user");
    String role = (navUser != null) ? navUser.getRole() : "";
    boolean canManageEvents = Boolean.TRUE.equals(session.getAttribute("canManageEvents"));
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty pageTitle ? pageTitle : 'FanfareHub'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <% if (navUser != null) { %>
<nav class="navbar">
    <a href="${pageContext.request.contextPath}/accueil" class="navbar-brand">FanfareHub</a>
    <div class="navbar-links">
        <a href="${pageContext.request.contextPath}/evenements">Evenements</a>
        <a href="${pageContext.request.contextPath}/options">Mes options</a>
        <% if (canManageEvents) { %>
        <a href="${pageContext.request.contextPath}/evenement/gestion?action=ajouter">+ Evenement</a>
        <% } %>
        <% if ("admin".equals(role)) { %>
        <a href="${pageContext.request.contextPath}/fanfarons">Fanfarons</a>
        <% } %>
        <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Deconnexion</a>
    </div>
</nav>
    <% } %>
<main class="container">
