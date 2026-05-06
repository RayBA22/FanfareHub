<%@ page contentType="text/html;charset=UTF-8" %>
<%
    if (session.getAttribute("user") != null) {
        response.sendRedirect(request.getContextPath() + "/accueil");
    } else {
        response.sendRedirect(request.getContextPath() + "/login");
    }
%>
