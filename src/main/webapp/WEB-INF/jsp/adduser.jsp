<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/9/29
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- springMVC内置标签 --%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>add</title>
</head>
<body>
<%-- modelAttribute是从后台接收到一个空的对象模型 --%>
<form:form action="/user/add" modelAttribute="user" method="post">
    <%-- path相当于普通input中的name，都是modelAttribute指定模型的属性名称，默认type是text类型 --%>
    id:<form:input path="id"/><br>
    name:<form:input path="name"/><br>
    <%--password:<fm:password path="password"/><br> 这是密码类型，其他类型都有对应名称的标签--%>
    age:<form:input path="age"/><br>
    <input type="submit" value="保存"/>
</form:form>
</body>
</html>
