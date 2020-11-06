<%@ page import="java.util.List" %>
<%@ page import="com.mycorp.app.Paginator" %>
<%@ page import="com.mycorp.app.PaginatorImpl" %>
<%@ page import="com.mycorp.app.News" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>My super project!</title>
    </head>
    <body>
        <%
            Paginator paginator = (Paginator)request.getAttribute("list");
            List<News> listNews = paginator.getDataPage();

            int offset = paginator.getPageSize() * (paginator.getCurrentPage() - 1);

            for (int i = 0; i < listNews.size(); i++) {
                out.println("<h2>" + listNews.get(i).getHead() + "</h2>");
                out.println("<i>" + listNews.get(i).getBriefly() + "</i><p>");
                out.print("<a href=../../news/" + (i + offset) + ">Читать полностью</a>");
            }
        %>
        <br>
        <br>
        <p align="center">
        <%
            if (! paginator.isFirstPage()) {
                out.print("<a href=" + (paginator.getCurrentPage() - 1) + ">Предыдущая</a>   ");
            }

            int totalPage = paginator.getTotalPage();

            for (int i = 1; i <= totalPage; i++) {
                if (i == paginator.getCurrentPage()) {
                    out.print("<b>  " + i + "  </b>");
                    continue;
                }
                out.print("   <a href=" + i + ">" + i + "</a>   ");
            }

            if (! paginator.isLastPage()) {
                out.print("   <a href=" + (paginator.getCurrentPage() + 1) + ">Следующая</a>");
            }
        %>
        </p>
    </body>
</html>