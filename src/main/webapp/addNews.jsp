<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form accept-charset="UTF-8" method="post">
  <fieldset>
    <legend>Добавить новость</legend>
    <table>
      <tr><td><p>Заголовок</td><td><input type="text" name="head" size=100></p></td></tr>
      <tr><td><p>Краткое описание</td><td><input type="text" name="briefly" size=100></p></td></tr>
      <tr><td><p>Полный текст</td><td><textarea cols=102 rows=10 name="full"></textarea></p></td></tr>
    </table>
  </fieldset>
<p><input type="submit" value="Добавить" formaction="../add"></p>
</form>
<hr class="line">