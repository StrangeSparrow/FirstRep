<%@ page import="java.util.List" %>
<%@ page import="com.mycorp.app.News" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>My super project!</title>
</head>
<body>

<%
    List<News> listNews = (List<News>)request.getAttribute("list");

    for (News news : listNews) {
         out.println("<h2>" + news.getHead() + "</h2>");
        out.println("<i>" + news.getBriefly() + "</i><p>");
        out.println(news.getFull());
    }
%>

</body>
</html>