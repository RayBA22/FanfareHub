<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.fanfarehub.Model.POJO.*,java.util.List" %>
<%
    String action   = (String)    request.getAttribute("action");
    Fanfaron f      = (Fanfaron)  request.getAttribute("fanfaron");
    boolean isEdit  = "modifier".equals(action);
    request.setAttribute("pageTitle", isEdit ? "Modifier fanfaron" : "Ajouter fanfaron");

    List<Pupitre>  tousPupitres       = (List<Pupitre>)  request.getAttribute("pupitres");
    List<Comission> toutesCommissions  = (List<Comission>) request.getAttribute("commissions");
    List<Pupitre>  mesPupitres        = (List<Pupitre>)  request.getAttribute("mesPupitres");
    List<Comission> mesCommissions     = (List<Comission>) request.getAttribute("mesCommissions");

    java.util.Set<String> pupitreCoche = new java.util.HashSet<>();
    if (mesPupitres != null) for (Pupitre p : mesPupitres) pupitreCoche.add(p.getInstrument());
    java.util.Set<String> commCochee   = new java.util.HashSet<>();
    if (mesCommissions != null) for (Comission c : mesCommissions) commCochee.add(c.getNom());
%>
<%@ include file="fragments/header.jsp" %>

<div class="page-header">
    <a href="${pageContext.request.contextPath}/fanfarons" class="btn btn-link">&larr; Retour</a>
    <h1><%= isEdit ? "Modifier un fanfaron" : "Ajouter un fanfaron" %></h1>
</div>

<% if (request.getAttribute("error") != null) { %>
<div class="alert alert-error">${error}</div>
<% } %>

<div class="card form-card">
<form method="post" action="${pageContext.request.contextPath}/fanfaron/gestion">
    <input type="hidden" name="action" value="<%= action %>">
    <% if (isEdit && f != null) { %>
    <input type="hidden" name="pseudo" value="<%= f.getPseudo() %>">
    <% } %>

    <% if (!isEdit) { %>
    <div class="form-row">
        <div class="form-group">
            <label for="pseudo">Pseudo *</label>
            <input type="text" id="pseudo" name="pseudo" required>
        </div>
        <div class="form-group">
            <label for="mdp">Mot de passe *</label>
            <input type="password" id="mdp" name="mdp" required>
        </div>
    </div>
    <% } else { %>
    <div class="form-group">
        <label>Pseudo</label>
        <input type="text" value="<%= f.getPseudo() %>" disabled class="input-disabled">
    </div>
    <% } %>

    <div class="form-row">
        <div class="form-group">
            <label for="prenom">Prenom</label>
            <input type="text" id="prenom" name="prenom"
                   value="<%= f != null && f.getPrenom() != null ? f.getPrenom() : "" %>">
        </div>
        <div class="form-group">
            <label for="nom">Nom</label>
            <input type="text" id="nom" name="nom"
                   value="<%= f != null && f.getNom() != null ? f.getNom() : "" %>">
        </div>
    </div>

    <div class="form-row">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email"
                   value="<%= f != null && f.getEmail() != null ? f.getEmail() : "" %>">
        </div>
        <div class="form-group">
            <label for="role">Role</label>
            <select id="role" name="role">
                <option value="fanfaron" <%= (f == null || "fanfaron".equals(f.getRole())) ? "selected" : "" %>>fanfaron</option>
                <option value="commission" <%= f != null && "commission".equals(f.getRole()) ? "selected" : "" %>>commission</option>
                <option value="admin" <%= f != null && "admin".equals(f.getRole()) ? "selected" : "" %>>admin</option>
            </select>
        </div>
    </div>

    <div class="form-row">
        <div class="form-group">
            <label for="genre">Genre</label>
            <select id="genre" name="genre">
                <option value="">--</option>
                <option value="homme"  <%= f != null && "homme".equals(f.getGenre())  ? "selected" : "" %>>Homme</option>
                <option value="femme"  <%= f != null && "femme".equals(f.getGenre())  ? "selected" : "" %>>Femme</option>
                <option value="autre"  <%= f != null && "autre".equals(f.getGenre())  ? "selected" : "" %>>Autre</option>
            </select>
        </div>
        <div class="form-group">
            <label for="regime">Regime</label>
            <select id="regime" name="regime">
                <option value="">--</option>
                <option value="actif"     <%= f != null && "actif".equals(f.getRegime())     ? "selected" : "" %>>Actif</option>
                <option value="inactif"   <%= f != null && "inactif".equals(f.getRegime())   ? "selected" : "" %>>Inactif</option>
                <option value="honoraire" <%= f != null && "honoraire".equals(f.getRegime()) ? "selected" : "" %>>Honoraire</option>
            </select>
        </div>
    </div>

    <% if (tousPupitres != null && !tousPupitres.isEmpty()) { %>
    <div class="form-group">
        <label>Instruments</label>
        <div class="checkbox-grid">
            <% for (Pupitre p : tousPupitres) { %>
            <label class="checkbox-label">
                <input type="checkbox" name="instruments" value="<%= p.getInstrument() %>"
                       <%= pupitreCoche.contains(p.getInstrument()) ? "checked" : "" %>>
                <%= p.getInstrument() %>
            </label>
            <% } %>
        </div>
    </div>
    <% } %>

    <% if (toutesCommissions != null && !toutesCommissions.isEmpty()) { %>
    <div class="form-group">
        <label>Commissions</label>
        <div class="checkbox-grid">
            <% for (Comission c : toutesCommissions) { %>
            <label class="checkbox-label">
                <input type="checkbox" name="commissions" value="<%= c.getNom() %>"
                       <%= commCochee.contains(c.getNom()) ? "checked" : "" %>>
                <%= c.getNom() %>
            </label>
            <% } %>
        </div>
    </div>
    <% } %>

    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/fanfarons" class="btn btn-secondary">Annuler</a>
        <button type="submit" class="btn btn-primary"><%= isEdit ? "Enregistrer" : "Creer" %></button>
    </div>
</form>
</div>

<%@ include file="fragments/footer.jsp" %>
