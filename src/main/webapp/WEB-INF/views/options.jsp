<%--
  Vue des options personnelles du fanfaron connecte.
  Elle affiche tous les pupitres et commissions, pre-coche les choix actuels
  et permet d'enregistrer une nouvelle selection.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.fanfarehub.Model.POJO.*,java.util.List" %>
<% request.setAttribute("pageTitle", "Mes options"); %>
<%@ include file="fragments/header.jsp" %>

<%
    List<Pupitre>  tousPupitres      = (List<Pupitre>)  request.getAttribute("tousPupitres");
    List<Pupitre>  mesPupitres       = (List<Pupitre>)  request.getAttribute("mesPupitres");
    List<Comission> toutesCommissions = (List<Comission>) request.getAttribute("toutesCommissions");
    List<Comission> mesCommissions    = (List<Comission>) request.getAttribute("mesCommissions");

    // Construire les sets pour pre-cocher
    java.util.Set<String> mesPupitresSet = new java.util.HashSet<>();
    if (mesPupitres != null) for (Pupitre p : mesPupitres) mesPupitresSet.add(p.getInstrument());
    java.util.Set<String> mesCommissionsSet = new java.util.HashSet<>();
    if (mesCommissions != null) for (Comission c : mesCommissions) mesCommissionsSet.add(c.getNom());
%>

<div class="page-header">
    <h1>Mes options</h1>
</div>

<% if ("1".equals(request.getParameter("succes"))) { %>
<div class="alert alert-success">Options enregistrees avec succes.</div>
<% } %>

<div class="card form-card">
<form method="post" action="${pageContext.request.contextPath}/options">

    <% if (tousPupitres != null && !tousPupitres.isEmpty()) { %>
    <div class="form-group">
        <label>Instruments joues (pupitre)</label>
        <div class="checkbox-grid">
            <% for (Pupitre p : tousPupitres) { %>
            <label class="checkbox-label">
                <input type="checkbox" name="instruments" value="<%= p.getInstrument() %>"
                       <%= mesPupitresSet.contains(p.getInstrument()) ? "checked" : "" %>>
                <%= p.getInstrument() %>
            </label>
            <% } %>
        </div>
    </div>
    <% } %>

    <% if (toutesCommissions != null && !toutesCommissions.isEmpty()) { %>
    <div class="form-group">
        <label>Commissions (groupes)</label>
        <div class="checkbox-grid">
            <% for (Comission c : toutesCommissions) { %>
            <label class="checkbox-label">
                <input type="checkbox" name="commissions" value="<%= c.getNom() %>"
                       <%= mesCommissionsSet.contains(c.getNom()) ? "checked" : "" %>>
                <%= c.getNom() %>
            </label>
            <% } %>
        </div>
    </div>
    <% } %>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Enregistrer</button>
    </div>
</form>
</div>

<%@ include file="fragments/footer.jsp" %>
