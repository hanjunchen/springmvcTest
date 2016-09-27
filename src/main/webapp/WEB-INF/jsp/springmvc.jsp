<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/9/21
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %><!-- jsp版本默认忽略EL表达式，默认值为true，需要设为false才能使用EL表达式 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!-- 导入jstl jar包 -->
<html>
<head>
  <title>111</title>
</head>
<body>
<h1>注解扫描controller方式！</h1>
<p>${name}</p>
<p>${string}</p>
<c:out value="${name}"></c:out>
</body>
</html>
