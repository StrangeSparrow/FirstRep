<%@ page import="java.util.List" %>
<%@ page import="com.mycorp.app.paginator.Paginator" %>
<%@ page import="com.mycorp.app.paginator.PaginatorImpl" %>
<%@ page import="com.mycorp.app.user.User" %>
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

                <hr class="line">
            </td><td width="20%"></td></tr>

            <tr><td width="20%"></td>
            <td>
                <h3>Список пользователей</h3>

                <table style="border-collapse: collapse; width: 100%; height: 18px;">
                <tbody>
                    <tr style="height: 20px;">
                    <td style="width: 10%; height: 18px; text-align: center;"><strong>ID</strong></td>
                    <td style="width: 30%; height: 18px; text-align: left;"><strong>Имя пользователя</strong></td>
                    <td style="width: 60%; height: 18px; text-align: right;"><strong>Группа пользователя</strong></td>
                    </tr>

                    <%
                        Paginator paginator = (Paginator)request.getAttribute("users");
                        List<User> listUser = paginator.getDataPage();

                        for (int i = 0; i < listUser.size(); i++) {
                            out.println("<tr style=\"height: 30px;\">" +
                                        "<td style=\"width: 10%; height: 18px; text-align: center;\">" + listUser.get(i).getId() + "</td>" +
                                        "<td style=\"width: 30%; height: 18px; text-align: left;\">" + listUser.get(i).getLogin() + "</td>" +
                                        "<td style=\"width: 60%; height: 18px; text-align: right;\">" + listUser.get(i).getGroup() + "</td></tr>");
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