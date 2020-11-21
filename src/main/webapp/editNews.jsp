<%@ page import="com.mycorp.app.news.News" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <meta charset="UTF-8" http-equiv="Cache-Control" content="no-cache">
        <title>Edit News</title>
    </head>
    <body>
    <% News news = (News)request.getAttribute("news"); %>

    <form accept-charset="UTF-8" method="post">
      <fieldset>
        <legend>Редактор новости</legend>
        <table>
          <tr><td><p>Заголовок</td><td><input type="text" name="head" size=100 value="<%out.print(news.getHead());%>"></p></td></tr>
          <tr><td><p>Краткое описание</td><td><input type="text" name="briefly" size=100 value="<%out.print(news.getBriefly());%>"></p></td></tr>
          <tr><td><p>Полный текст</td><td><textarea cols=102 rows=25 name="full"><%out.print(news.getFull());%></textarea></p></td></tr>
          <input type="hidden" value="<%out.print(news.getId());%>" name="id">
        </table>
      </fieldset>
    <p><input type="submit" value="Добавить" formaction="../addEditNews"></p>
    </form>

    </body>
</html>