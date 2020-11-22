<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
        <tr>
            <td style="width: 30%;"><label>Права группы</label></td>
            <td style="width: 70%;"><input type="checkbox" name="create group" size="100" />Создание групп</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" name="edit group" size="100" />Редактирование групп</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" name="delete group" size="100" />Удаление групп</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" name="create news" size="100" />Создание новостей</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" name="edit news" size="100" />Редактирование новостей</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" name="delete news" size="100" />Удаление новостей</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" name="create user" size="100" />Добавление пользователей</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" name="edit user" size="100" />Редактирование пользователей</td>
        </tr>
        <tr>
            <td style="width: 30%;"></td>
            <td style="width: 70%;"><input type="checkbox" name="delete user" size="100" />Удаление пользователей</td>
        </tr>
        </tbody>
    </table>
    <p><input type="submit" value="Добавить" formaction="../add" /></p>
</fildset>
</form>