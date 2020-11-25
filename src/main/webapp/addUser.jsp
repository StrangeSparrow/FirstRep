<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mycorp.app.group.Group" %>
<%@ page import="com.mycorp.app.group.GroupService" %>
<%@ page import="com.mycorp.app.group.GroupServiceImpl" %>

<%
    GroupService gs = new GroupServiceImpl();
    List<Group> group = gs.fetchGroup();
%>

<form accept-charset="UTF-8" method="post">
<fildset><legend><h3 align=center>Добавить пользователя</h3></legend>
    <table style="border-collapse: collapse; width: 100%; height: 57px;">
        <tbody>
        <tr style="height: 21px;">
        <td style="width: 50%; height: 21px; text-align: right;">Login&nbsp;</td>
        <td style="width: 50%; height: 21px;"><input type="text" name="login" size="50%&quot;" /></td>
        </tr>
        <tr style="height: 18px;">
        <td style="width: 50%; text-align: right; height: 18px;">Password&nbsp;</td>
        <td style="width: 50%; height: 18px;"><input type="text" name="password" size="50%&quot;" /></td>
        </tr>
        <tr style="height: 18px;">
        <td style="width: 50%; height: 18px; text-align: right;">Group&nbsp;</td>
        <td style="width: 50%; height: 18px;"><div><select name="group">
        <%
            for (Group g : group) {
                out.println("<option value=" + g.getId() + ">" + g.getName() + "</option>");
            }
        %>
        </select></div></td>
        </tr>
        <tr style="height: 58px;">
        <td style="width: 50%; text-align: right; height: 58px;"><input type="submit" value="Добавить" formaction="../add" /></td>
        <td style="width: 50%; height: 58px;"></td>
        </tr>
        </tbody>
    </table>
</fildset></form>