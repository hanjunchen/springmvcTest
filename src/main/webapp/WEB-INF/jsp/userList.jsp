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
<a href="/user/add">添加</a><br>
<c:forEach var="user" items="${userList}">
    id：${user.id}---name：<a href="/user/view/${user.id}">${user.name}</a>---age：${user.age}
    ---<a href="/user/${user.id}/update">修改</a><br>
</c:forEach>
<%-- 使用JSTL和EL遍历Map，分别通过key和value属性来访问map的键值 --%>
<c:forEach var="item" items="${userMap}">
    key:${item.key}---id：${item.value.id}---name：<a href="/user/view/${item.value.id}">${item.value.name}</a>---age：${item.value.age+1+item.key}
    ---<a href="/user/${item.value.id}/update">修改</a>&nbsp;<a href="/user/${item.value.id}/delete">删除</a><br>
</c:forEach>
</body>
</html>
