<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.fanfarehub.Model.POJO.*,java.util.List,java.text.SimpleDateFormat" %>
<% request.setAttribute("pageTitle", "Evenements"); %>
<%@ include file="fragments/header.jsp" %>

<%
    List<Evenement> evenements = (List<Evenement>) request.getAttribute("evenements");
    String filtreType = (String) request.getAttribute("filtreType");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String role = ((Fanfaron) session.getAttribute("user")).getRole();
%>

<div class="page-header">
    <h1>Evenements</h1>
    <% if ("commission".equals(role) || "admin".equals(role)) { %>
    <a href="${pageContext.request.contextPath}/evenement/gestion?action=ajouter" class="btn btn-primary">+ Ajouter</a>
    <% } %>
</div>

<form method="get" action="${pageContext.request.contextPath}/evenements" class="filter-form">
    <label for="type">Filtrer par type :</label>
    <input type="text" id="type" name="type" value="<%= filtreType != null ? filtreType : "" %>" placeholder="concert, repetition...">
    <button type="submit" class="btn btn-secondary">Filtrer</button>
    <% if (filtreType != null) { %>
    <a href="${pageContext.request.contextPath}/evenements" class="btn btn-link">Effacer</a>
    <% } %>
</form>

<% if (evenements != null && !evenements.isEmpty()) { %>
<table class="table">
    <thead>
        <tr>
            <th>Nom</th><th>Type</th><th>Date</th><th>Duree</th><th>Lieu</th><th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <% for (Evenement e : evenements) { %>
        <tr>
            <td><a href="${pageContext.request.contextPath}/evenement?nom=<%= java.net.URLEncoder.encode(e.getNom(), "UTF-8") %>"><%= e.getNom() %></a></td>
            <td><%= e.getType() != null ? e.getType() : "-" %></td>
            <td><%= e.getDate() != null ? sdf.format(e.getDate()) : "-" %></td>
            <td><%= e.getDuree() != null ? e.getDuree() : "-" %></td>
            <td><%= e.getLieu() != null ? e.getLieu() : "-" %></td>
            <td>
                <a href="${pageContext.request.contextPath}/evenement?nom=<%= java.net.URLEncoder.encode(e.getNom(), "UTF-8") %>" class="btn btn-sm btn-secondary">Voir</a>
                <% if ("commission".equals(role) || "admin".equals(role)) { %>
                <a href="${pageContext.request.contextPath}/evenement/gestion?action=modifier&nom=<%= java.net.URLEncoder.encode(e.getNom(), "UTF-8") %>" class="btn btn-sm btn-warning">Modifier</a>
                <form method="post" action="${pageContext.request.contextPath}/evenement/gestion" style="display:inline"
                      onsubmit="return confirm('Supprimer cet evenement ?')">
                    <input type="hidden" name="action" value="supprimer">
                    <input type="hidden" name="nom" value="<%= e.getNom() %>">
                    <button type="submit" class="btn btn-sm btn-danger">Supprimer</button>
                </form>
                <% } %>
            </td>
        </tr>
        <% } %>
    </tbody>
</table>
<% } else { %>
<p class="text-muted">Aucun evenement trouve.</p>
<% } %>

<%@ include file="fragments/footer.jsp" %>
