<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/10/9
  Time: 22:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>error页面</title>
</head>
<body>
<h1>${e.message}</h1>
<%-- 全局异常默认属性是exception --%>
<h1>${exception.message}</h1>
</body>
</html>
