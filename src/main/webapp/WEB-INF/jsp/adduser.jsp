<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/9/29
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- springMVC内置标签 --%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="fm" %>
<html>
<head>
    <title>add</title>
</head>
<body>
<%-- modelAttribute是从后台接收到一个空的对象模型 --%>
<fm:form action="/user/add" modelAttribute="user" method="post">
    <%-- path相当于普通input中的name --%>
    id:<fm:input path="id"/><br>
    name:<fm:input path="name"/><br>
    age:<fm:input path="age"/><br>
    <input type="submit" value="保存"/>
</fm:form>
</body>
</html>
