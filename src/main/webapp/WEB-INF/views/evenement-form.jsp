<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.fanfarehub.Model.POJO.Evenement,java.text.SimpleDateFormat" %>
<%
    String action    = (String)    request.getAttribute("action");
    Evenement ev     = (Evenement) request.getAttribute("evenement");
    boolean isEdit   = "modifier".equals(action);
    request.setAttribute("pageTitle", isEdit ? "Modifier evenement" : "Ajouter evenement");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>
<%@ include file="fragments/header.jsp" %>

<div class="page-header">
    <a href="${pageContext.request.contextPath}/evenements" class="btn btn-link">&larr; Retour</a>
    <h1><%= isEdit ? "Modifier l'evenement" : "Ajouter un evenement" %></h1>
</div>

<% if (request.getAttribute("error") != null) { %>
<div class="alert alert-error">${error}</div>
<% } %>

<div class="card form-card">
<form method="post" action="${pageContext.request.contextPath}/evenement/gestion">
    <input type="hidden" name="action" value="<%= action %>">
    <% if (isEdit && ev != null) { %>
    <input type="hidden" name="nom" value="<%= ev.getNom() %>">
    <% } %>

    <% if (!isEdit) { %>
    <div class="form-group">
        <label for="nom">Nom *</label>
        <input type="text" id="nom" name="nom" required value="<%= ev != null ? ev.getNom() : "" %>">
    </div>
    <% } else { %>
    <div class="form-group">
        <label>Nom</label>
        <input type="text" value="<%= ev.getNom() %>" disabled class="input-disabled">
    </div>
    <% } %>

    <div class="form-row">
        <div class="form-group">
            <label for="type">Type</label>
            <input type="text" id="type" name="type" placeholder="concert, repetition..."
                   value="<%= ev != null && ev.getType() != null ? ev.getType() : "" %>">
        </div>
        <div class="form-group">
            <label for="date">Date</label>
            <input type="date" id="date" name="date"
                   value="<%= ev != null && ev.getDate() != null ? sdf.format(ev.getDate()) : "" %>">
        </div>
    </div>

    <div class="form-row">
        <div class="form-group">
            <label for="duree">Duree</label>
            <input type="text" id="duree" name="duree" placeholder="ex: 2h30"
                   value="<%= ev != null && ev.getDuree() != null ? ev.getDuree() : "" %>">
        </div>
        <div class="form-group">
            <label for="lieu">Lieu</label>
            <input type="text" id="lieu" name="lieu"
                   value="<%= ev != null && ev.getLieu() != null ? ev.getLieu() : "" %>">
        </div>
    </div>

    <div class="form-group">
        <label for="description">Description</label>
        <input type="text" id="description" name="description"
               value="<%= ev != null && ev.getDescription() != null ? ev.getDescription() : "" %>">
    </div>

    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/evenements" class="btn btn-secondary">Annuler</a>
        <button type="submit" class="btn btn-primary"><%= isEdit ? "Enregistrer" : "Creer" %></button>
    </div>
</form>
</div>

<%@ include file="fragments/footer.jsp" %>
