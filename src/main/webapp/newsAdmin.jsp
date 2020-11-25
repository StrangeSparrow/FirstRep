<%@ page import="java.util.List" %>
<%@ page import="com.mycorp.app.paginator.Paginator" %>
<%@ page import="com.mycorp.app.paginator.PaginatorImpl" %>
<%@ page import="com.mycorp.app.news.News" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin News</title>
    </head>
    <body>
    <table>
    <tr><td width="20%"></td>
    <td>
    <jsp:include page="addNews.jsp" />
        <%
            Paginator paginator = (Paginator)request.getAttribute("list");
            List<News> listNews = paginator.getDataPage();

            int offset = paginator.getPageSize() * (paginator.getCurrentPage() - 1);

            for (int i = 0; i < listNews.size(); i++) {
                out.println("<h2>" + listNews.get(i).getHead() + "</h2>");
                out.println("<i>" + listNews.get(i).getBriefly() + "</i><p>");
                out.println("<a href=../../../news/" + listNews.get(i).getId() + ">Читать полностью</a>");
                out.println("<p><a href=../edit/" + listNews.get(i).getId() + ">Редактировать</a>");
                out.println("<a href=../delete/" + listNews.get(i).getId() + ">Удалить</a>");
                out.print("<hr class=\"line\">");
            }
        %>
    </td>
    <td width="20%"></td>
    </tr>
    </table>
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