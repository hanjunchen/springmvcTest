<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/10/9
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<form action="/user/login" method="post">
  用户名：<input type="text" name="name"><br>
  密码：<input type="password" name="password"><br>
  <input type="submit" value="登录">
</form>
</body>
</html>
