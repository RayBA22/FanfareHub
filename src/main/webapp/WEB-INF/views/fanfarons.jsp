<%--
  Vue d'administration des fanfarons.
  Elle liste les membres avec leurs informations principales
  et propose les actions de modification ou suppression.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.fanfarehub.Model.POJO.Fanfaron,java.util.List,java.text.SimpleDateFormat" %>
<% request.setAttribute("pageTitle", "Gestion des fanfarons"); %>
<%@ include file="fragments/header.jsp" %>

<%
    List<Fanfaron> fanfarons = (List<Fanfaron>) request.getAttribute("fanfarons");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>

<div class="page-header">
    <h1>Fanfarons</h1>
    <a href="${pageContext.request.contextPath}/fanfaron/gestion?action=ajouter" class="btn btn-primary">+ Ajouter</a>
</div>

<% if (fanfarons != null && !fanfarons.isEmpty()) { %>
<table class="table">
    <thead>
        <tr>
            <th>Pseudo</th><th>Prenom Nom</th><th>Email</th>
            <th>Role</th><th>Regime</th><th>Depuis</th><th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <% for (Fanfaron f : fanfarons) { %>
        <tr>
            <td><strong><%= f.getPseudo() %></strong></td>
            <td><%= f.getPrenom() != null ? f.getPrenom() : "" %> <%= f.getNom() != null ? f.getNom() : "" %></td>
            <td><%= f.getEmail() != null ? f.getEmail() : "-" %></td>
            <td><span class="badge badge-<%= f.getRole() %>"><%= f.getRole() %></span></td>
            <td><%= f.getRegime() != null ? f.getRegime() : "-" %></td>
            <td><%= f.getDateCreation() != null ? sdf.format(f.getDateCreation()) : "-" %></td>
            <td>
                <a href="${pageContext.request.contextPath}/fanfaron/gestion?action=modifier&pseudo=<%= java.net.URLEncoder.encode(f.getPseudo(), "UTF-8") %>"
                   class="btn btn-sm btn-warning">Modifier</a>
                <form method="post" action="${pageContext.request.contextPath}/fanfaron/gestion"
                      style="display:inline"
                      onsubmit="return confirm('Supprimer <%= f.getPseudo() %> ?')">
                    <input type="hidden" name="action" value="supprimer">
                    <input type="hidden" name="pseudo" value="<%= f.getPseudo() %>">
                    <button type="submit" class="btn btn-sm btn-danger">Supprimer</button>
                </form>
            </td>
        </tr>
        <% } %>
    </tbody>
</table>
<% } else { %>
<p class="text-muted">Aucun fanfaron enregistre.</p>
<% } %>

<%@ include file="fragments/footer.jsp" %>
