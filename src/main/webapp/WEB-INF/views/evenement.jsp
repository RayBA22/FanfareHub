<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.fanfarehub.Model.POJO.*,java.util.List,java.text.SimpleDateFormat" %>
<%
    Evenement evenement = (Evenement) request.getAttribute("evenement");
    request.setAttribute("pageTitle", evenement != null ? evenement.getNom() : "Evenement");
%>
<%@ include file="fragments/header.jsp" %>

<%
    List<Inscrit> inscrits     = (List<Inscrit>) request.getAttribute("inscrits");
    Inscrit monInscription     = (Inscrit) request.getAttribute("monInscription");
    List<Pupitre> mesPupitres  = (List<Pupitre>) request.getAttribute("mesPupitres");
    Fanfaron user              = (Fanfaron) session.getAttribute("user");
    String role                = user.getRole();
    SimpleDateFormat sdf       = new SimpleDateFormat("dd/MM/yyyy");
%>

<div class="page-header">
    <div>
        <a href="${pageContext.request.contextPath}/evenements" class="btn btn-link">&larr; Retour</a>
        <h1><%= evenement.getNom() %></h1>
    </div>
    <% if ("commission".equals(role) || "admin".equals(role)) { %>
    <div>
        <a href="${pageContext.request.contextPath}/evenement/gestion?action=modifier&nom=<%= java.net.URLEncoder.encode(evenement.getNom(), "UTF-8") %>"
           class="btn btn-warning">Modifier</a>
        <form method="post" action="${pageContext.request.contextPath}/evenement/gestion" style="display:inline"
              onsubmit="return confirm('Supprimer cet evenement ?')">
            <input type="hidden" name="action" value="supprimer">
            <input type="hidden" name="nom" value="<%= evenement.getNom() %>">
            <button type="submit" class="btn btn-danger">Supprimer</button>
        </form>
    </div>
    <% } %>
</div>

<div class="cards-grid">
    <div class="card">
        <h3>Details</h3>
        <table class="table-simple">
            <tr><td>Type</td><td><%= evenement.getType() != null ? evenement.getType() : "-" %></td></tr>
            <tr><td>Date</td><td><%= evenement.getDate() != null ? sdf.format(evenement.getDate()) : "-" %></td></tr>
            <tr><td>Duree</td><td><%= evenement.getDuree() != null ? evenement.getDuree() : "-" %></td></tr>
            <tr><td>Lieu</td><td><%= evenement.getLieu() != null ? evenement.getLieu() : "-" %></td></tr>
            <tr><td>Description</td><td><%= evenement.getDescription() != null ? evenement.getDescription() : "-" %></td></tr>
            <tr><td>Organise par</td><td><%= evenement.getPseudo() %></td></tr>
        </table>
    </div>

    <div class="card">
        <h3>Mon inscription</h3>
        <% if (monInscription != null) { %>
        <p>
            Instrument : <strong><%= monInscription.getInstrument() %></strong><br>
            Statut : <strong><%= monInscription.getStatus() %></strong>
        </p>
        <form method="post" action="${pageContext.request.contextPath}/evenement/inscrire">
            <input type="hidden" name="nomEvenement" value="<%= evenement.getNom() %>">
            <input type="hidden" name="action" value="desinscrire">
            <button type="submit" class="btn btn-danger">Se desinscrire</button>
        </form>
        <% } else { %>
        <% if (mesPupitres != null && !mesPupitres.isEmpty()) { %>
        <form method="post" action="${pageContext.request.contextPath}/evenement/inscrire">
            <input type="hidden" name="nomEvenement" value="<%= evenement.getNom() %>">
            <input type="hidden" name="action" value="inscrire">
            <div class="form-group">
                <label for="instrument">Instrument</label>
                <select id="instrument" name="instrument" required>
                    <% for (Pupitre p : mesPupitres) { %>
                    <option value="<%= p.getInstrument() %>"><%= p.getInstrument() %></option>
                    <% } %>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">S'inscrire</button>
        </form>
        <% } else { %>
        <p class="text-muted">Vous n'avez aucun instrument enregistre. <a href="${pageContext.request.contextPath}/options">Ajouter un instrument</a></p>
        <% } %>
        <% } %>
    </div>
</div>

<div class="section">
    <h2>Participants (<%= inscrits != null ? inscrits.size() : 0 %>)</h2>
    <% if (inscrits != null && !inscrits.isEmpty()) { %>
    <table class="table">
        <thead>
            <tr><th>Pseudo</th><th>Instrument</th><th>Statut</th></tr>
        </thead>
        <tbody>
            <% for (Inscrit i : inscrits) { %>
            <tr>
                <td><%= i.getPseudo() %></td>
                <td><%= i.getInstrument() %></td>
                <td><span class="badge badge-<%= i.getStatus() %>"><%= i.getStatus() %></span></td>
            </tr>
            <% } %>
        </tbody>
    </table>
    <% } else { %>
    <p class="text-muted">Aucun participant inscrit.</p>
    <% } %>
</div>

<%@ include file="fragments/footer.jsp" %>
