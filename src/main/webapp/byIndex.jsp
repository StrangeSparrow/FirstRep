<%@ page import="com.mycorp.app.News" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>My super project!</title>
    </head>
    <body>
    <%
        News news = (News)request.getAttribute("news");

        out.println("<h2>" + news.getHead() + "</h2>");
        out.println(news.getFull());
    %>
    </body>
</html>