<%--
  Vue generique d'erreur applicative.
  Elle affiche le message fourni par la servlet, ou un message par defaut,
  et propose un retour vers l'accueil.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<% request.setAttribute("pageTitle", "Erreur"); %>
<%@ include file="fragments/header.jsp" %>

<div class="page-header">
    <h1>Erreur</h1>
</div>
<div class="card">
    <div class="alert alert-error">
        ${not empty message ? message : 'Une erreur est survenue.'}
    </div>
    <a href="${pageContext.request.contextPath}/accueil" class="btn btn-primary">Retour a l'accueil</a>
</div>

<%@ include file="fragments/footer.jsp" %>
