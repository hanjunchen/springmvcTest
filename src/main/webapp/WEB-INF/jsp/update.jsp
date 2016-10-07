<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/10/7
  Time: 13:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>修改</title>
</head>
<body>
<form action="/user/${user.id}/updateSave" method="post">
  id:${user.id}<br>
  <%-- 这里使用普通input标签，只需要name属性值对应user对象属性，而如果直接使用spring标签的话又没有在form中指定modelAttribute值，那么path的值就需要这样指定：user.name --%>
  name:<input type="text" name="name" value="${user.name}"><form:errors path="user.name" /><br>
  age:<input type="text" name="age" value="${user.age}"><br>
  <input type="submit" value="修改">
</form>
</body>
</html>
