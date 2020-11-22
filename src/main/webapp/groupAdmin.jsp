<%@ page import="java.util.List" %>
<%@ page import="com.mycorp.app.paginator.Paginator" %>
<%@ page import="com.mycorp.app.paginator.PaginatorImpl" %>
<%@ page import="com.mycorp.app.group.Group" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Group</title>
    </head>

    <body>
        <table align="center">
            <tr><td width="20%"></td>
            <td>
                <jsp:include page="addGroup.jsp" />
                <hr class="line">
            </td><td width="20%"></td></tr>

            <tr><td width="20%"></td>
            <td>
                <h3>Список групп</h3>

                <table style="border-collapse: collapse; width: 100%; height: 18px;">
                <tbody>
                    <tr style="height: 20px;">
                    <td style="width: 10%; height: 18px; text-align: center;"><strong>ID</strong></td>
                    <td style="width: 30%; height: 18px; text-align: left;"><strong>Имя группы</strong></td>
                    <td style="width: 60%; height: 18px; text-align: right;"><strong>Права группы</strong></td>
                    </tr>

                    <%
                        Paginator paginator = (Paginator)request.getAttribute("list");
                        List<Group> listGroup = paginator.getDataPage();

                        for (int i = 0; i < listGroup.size(); i++) {
                            out.println("<tr style=\"height: 20px;\">" +
                                        "<td style=\"width: 10%; height: 18px; text-align: center;\">" + listGroup.get(i).getId() + "</td>" +
                                        "<td style=\"width: 30%; height: 18px; text-align: left;\">" + listGroup.get(i).getName() + "</td>" +
                                        "<td style=\"width: 60%; height: 18px; text-align: right;\">");
                            if (listGroup.get(i).getPermission() != null) {
                                for (String s : listGroup.get(i).getPermission()) {
                                    out.println(" | " + s.toUpperCase());
                                }
                                out.println("<p><a href=../edit/" + listGroup.get(i).getId() + ">Редактировать</a> <a href=../delete/" + listGroup.get(i).getId() + ">Удалить</a></p>");
                                out.println("<hr class=\"line\"></td></tr>");
                            } else {
                                out.println("</td></tr>");
                            }
                        }
                    %>
                </tbody>
                </table>
            </td>
            <td width="20%"></td></tr>
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