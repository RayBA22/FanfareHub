<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
    <jsp:forward page="WEB-INF/view/accueil.jsp">
        <jsp:param name="" value=""/>
    </jsp:forward>
    <p>Should not appear</p>
</body>
</html>
