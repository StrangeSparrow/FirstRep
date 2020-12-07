<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Set" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Admin</title>
</head>

<body>
    <p><a>Главная страница администрирования</a>

    <%
        Set<String> permissions = (Set<String>) request.getAttribute("permissions");
        if (permissions.contains("edit_news"))
            out.println("<p><a href=\"/my-app-3.5/app/admin/news/page/1\">Управление новостями</a>");
        if (permissions.contains("edit_group"))
            out.println("<p><a href=\"/my-app-3.5/app/admin/group/page/1\">Управление группами</a>");
        if (permissions.contains("edit_user"))
            out.println("<p><a href=\"/my-app-3.5/app/admin/user/page/1\">Управление пользователями</a>");
    %>

    <br>
    <br>
    <br>
    <p><a href="/my-app-3.5/app/logout">Выйти из аккаунта</a>
</body>
</html>