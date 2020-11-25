<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mycorp.app.permission.*" %>
<%@ page import="java.util.List" %>

<form accept-charset="UTF-8" method="post"><fildset>
    <legend><h3>Добавить группу</h3></legend>

    <table style="width: 100%;">
        <tbody>
        <tr>
            <td style="width: 30%;">
                <p><label>Имя группы</label></p>
            </td>
            <td style="width: 70%;"><input type="text" name="name" size="30" /></td>
        </tr>
        <%
            PermissionService permServ = new PermissionServiceImpl();
            List<Permission> permissionList = permServ.fetchPermissions();

            for (Permission permission : permissionList) {
                out.println("<tr>");
                out.println("<td style=\"width: 30%;\"><label></label></td>");
                out.println("<td style=\"width: 70%;\"><input type=\"checkbox\" value=\"" + permission.getId() + "\" name=\"permission[]\" id=" + permission.getId() + "size=\"100\" />" + permission.getDescription() + "</td>");
                out.println("</tr>");
            }
        %>
        </tbody>
    </table>
    <p><input type="submit" value="Добавить" formaction="../add" /></p>
</fildset>
</form>