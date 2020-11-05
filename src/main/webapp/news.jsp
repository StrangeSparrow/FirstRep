<%@ page import="java.util.List" %>
<%@ page import="com.mycorp.app.UtilNews" %>
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
            int offset = UtilNews.getPageSize() * (UtilNews.getCurrentPage() - 1);

            for (int i = 0; i < listNews.size(); i++) {
                out.println("<h2>" + listNews.get(i).getHead() + "</h2>");
                out.println("<i>" + listNews.get(i).getBriefly() + "</i><p>");
                out.println("<form>" +
                "<input type=\"button\" value=\"Читать полностью\" onClick=\'location.href=\"../../news/" + (i + offset) + "\"\'>" +
                "</form>");
            }
        %>
        <br>
        <p align="center">
        <%
            int totalPage = UtilNews.getTotalPages();

            for (int i = 1; i <= totalPage; i++) {
                out.print("<a href=" + i + ">" + i + "</a>");
                out.print("   ");
            }
        %>
        </p>
    </body>
</html>