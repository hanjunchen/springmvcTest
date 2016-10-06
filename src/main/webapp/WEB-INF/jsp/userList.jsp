<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/9/27
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>用户列表</title>
</head>
<body>
<c:forEach var="user" items="${userList}">
    id：${user.id}---name：${user.name}---age：${user.age}<br>
</c:forEach>
<%-- 使用jstl和el遍历Map，分别通过key和value属性来访问map的键值 --%>
<c:forEach var="item" items="${userMap}">
    key:${item.key}---id：${item.value.id}---name：${item.value.name}---age：${item.value.age+1+item.key}<br>
</c:forEach>
<a href="/user/add">添加</a>
</body>
</html>
