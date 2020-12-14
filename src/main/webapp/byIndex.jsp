<%@ page import="com.mycorp.app.news.News" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>My super project!</title>
    </head>
    <body>
    <table>
    <tr><td width="20%"></td>
    <td>
    <%
        News news = (News)request.getAttribute("news");

        out.println("<h2>" + news.getHead() + "</h2>");
        out.println("<p align=\"justify\">" + news.getFull() + "</p>");
        if (news.getAuthorNews() != null) {
            out.println("<br>");
            out.println("<p align=\"right\"><i>Автор: " + news.getAuthorNews().getName() + "</i></p>");
        }
    %>
    </td>
    <td width="20%"></td>
    </tr>
    </table>
    </body>
</html>