<%@ page import="com.mycorp.app.group.Group" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
        <tr>
            <td style="width: 30%;"><label>Права группы</label></td>
            <td style="width: 70%;"><input type="checkbox" <% if (group.getPermission().contains("create group")) out.println("checked=\"checked\""); %> name="create group" size="100" />Создание групп</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" <% if (group.getPermission().contains("edit group")) out.println("checked=\"checked\""); %> name="edit group" size="100" />Редактирование групп</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" <% if (group.getPermission().contains("delete group")) out.println("checked=\"checked\""); %> name="delete group" size="100" />Удаление групп</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" <% if (group.getPermission().contains("create news")) out.println("checked=\"checked\""); %> name="create news" size="100" />Создание новостей</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" <% if (group.getPermission().contains("edit news")) out.println("checked=\"checked\""); %> name="edit news" size="100" />Редактирование новостей</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" <% if (group.getPermission().contains("delete news")) out.println("checked=\"checked\""); %> name="delete news" size="100" />Удаление новостей</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" <% if (group.getPermission().contains("create user")) out.println("checked=\"checked\""); %> name="create user" size="100" />Добавление пользователей</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" <% if (group.getPermission().contains("edit user")) out.println("checked=\"checked\""); %> name="edit user" size="100" />Редактирование пользователей</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" <% if (group.getPermission().contains("delete user")) out.println("checked=\"checked\""); %> name="delete user" size="100" />Удаление пользователей</td>
        </tr>
        </tbody>
    </table>
    <input type="hidden" value="<%out.print(group.getId());%>" name="id">
    <p><input type="submit" value="Добавить" formaction="../add_edit_group" /></p>
</fildset>
</form>

    </body>
</html>