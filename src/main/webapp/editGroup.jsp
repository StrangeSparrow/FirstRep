<%@ page import="com.mycorp.app.group.Group" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mycorp.app.permission.*" %>
<%@ page import="java.util.List" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit News</title>
    </head>
    <body>
    <% Group group = (Group)request.getAttribute("group"); %>

<form accept-charset="UTF-8" method="post"><fildset>
    <legend><h3>Редактировать группу</h3></legend>

    <table style="width: 100%;">
        <tbody>
        <tr>
            <td style="width: 30%;">
                <p><label>Имя группы</label></p>
            </td>
            <td style="width: 70%;"><input type="text" name="name" size="30" value="<% out.println(group.getName()); %>" /></td>
        </tr>

        <%
            List<Permission> permissionList = new  PermissionDao().fetchPermissions();
            List<Permission> groupPermission = group.getPermission();

            for (Permission permission : permissionList) {
                String check = "";
                if (groupPermission.contains(permission))
                    check = "checked=\"checked\"";

                out.println("<tr>");
                out.println("<td style=\"width: 30%;\"><label></label></td>");
                out.println("<td style=\"width: 70%;\"><input type=\"checkbox\"" + check + " value=\"" + permission.getId() +
                            "\" name=\"permission[]\" id=" + permission.getId() +
                            "size=\"100\" />" + permission.getDescription() + "</td>");
                out.println("</tr>");
            }
        %>

        </tbody>
    </table>
    <input type="hidden" value="<%out.print(group.getId());%>" name="id">
    <p><input type="submit" value="Добавить" formaction="../add_edit_group" /></p>
</fildset>
</form>

    </body>
</html>