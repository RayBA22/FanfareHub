<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.fanfarehub.Model.POJO.*,java.util.List,java.text.SimpleDateFormat" %>
<% request.setAttribute("pageTitle", "Accueil"); %>
<%@ include file="fragments/header.jsp" %>

<%
    Fanfaron user = (Fanfaron) session.getAttribute("user");
    List<Evenement> evenements = (List<Evenement>) request.getAttribute("evenements");
    List<Pupitre> mesPupitres = (List<Pupitre>) request.getAttribute("mesPupitres");
    List<Comission> mesCommissions = (List<Comission>) request.getAttribute("mesCommissions");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>

<div class="page-header">
    <h1>Bienvenue, <%= user.getPrenom() != null && !user.getPrenom().isBlank() ? user.getPrenom() : user.getPseudo() %> !</h1>
    <span class="badge badge-<%= user.getRole() %>"><%= user.getRole() %></span>
</div>

<div class="cards-grid">
    <div class="card">
        <h3>Mon profil</h3>
        <table class="table-simple">
            <tr><td>Pseudo</td><td><strong><%= user.getPseudo() %></strong></td></tr>
            <tr><td>Email</td><td><%= user.getEmail() != null ? user.getEmail() : "-" %></td></tr>
            <tr><td>Nom</td><td><%= user.getPrenom() != null ? user.getPrenom() : "" %> <%= user.getNom() != null ? user.getNom() : "" %></td></tr>
            <tr><td>Genre</td><td><%= user.getGenre() != null ? user.getGenre() : "-" %></td></tr>
            <tr><td>Regime</td><td><%= user.getRegime() != null ? user.getRegime() : "-" %></td></tr>
            <tr><td>Membre depuis</td><td><%= user.getDateCreation() != null ? sdf.format(user.getDateCreation()) : "-" %></td></tr>
        </table>
        <a href="${pageContext.request.contextPath}/options" class="btn btn-secondary">Modifier mes options</a>
    </div>

    <div class="card">
        <h3>Mes instruments</h3>
        <% if (mesPupitres != null && !mesPupitres.isEmpty()) { %>
        <ul class="tag-list">
            <% for (Pupitre p : mesPupitres) { %>
            <li class="tag"><%= p.getInstrument() %></li>
            <% } %>
        </ul>
        <% } else { %>
        <p class="text-muted">Aucun instrument enregistre.</p>
        <% } %>
    </div>

    <div class="card">
        <h3>Mes commissions</h3>
        <% if (mesCommissions != null && !mesCommissions.isEmpty()) { %>
        <ul class="tag-list">
            <% for (Comission c : mesCommissions) { %>
            <li class="tag"><%= c.getNom() %></li>
            <% } %>
        </ul>
        <% } else { %>
        <p class="text-muted">Aucune commission.</p>
        <% } %>
    </div>
</div>

<div class="section">
    <div class="section-header">
        <h2>Prochains evenements</h2>
        <a href="${pageContext.request.contextPath}/evenements" class="btn btn-secondary">Voir tous</a>
    </div>
    <% if (evenements != null && !evenements.isEmpty()) { %>
    <table class="table">
        <thead>
            <tr><th>Nom</th><th>Type</th><th>Date</th><th>Lieu</th></tr>
        </thead>
        <tbody>
            <%
                int count = 0;
                for (Evenement e : evenements) {
                    if (count++ >= 5) break;
            %>
            <tr>
                <td><a href="${pageContext.request.contextPath}/evenement?nom=<%= java.net.URLEncoder.encode(e.getNom(), "UTF-8") %>"><%= e.getNom() %></a></td>
                <td><%= e.getType() != null ? e.getType() : "-" %></td>
                <td><%= e.getDate() != null ? sdf.format(e.getDate()) : "-" %></td>
                <td><%= e.getLieu() != null ? e.getLieu() : "-" %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
    <% } else { %>
    <p class="text-muted">Aucun evenement prevu.</p>
    <% } %>
</div>

<%@ include file="fragments/footer.jsp" %>
