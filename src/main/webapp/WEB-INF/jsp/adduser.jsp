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
<%-- modelAttribute是从后台接收到一个空的对象模型，不指定action的话是 --%>
<%-- 文件上传需要指定表单提交请求类型enctype --%>
<form:form action="/user/addSave" modelAttribute="user" method="post" enctype="multipart/form-data">
    <%-- path相当于普通input中的name，都是modelAttribute指定模型的属性名称，默认input是text类型 --%>
    id:<form:input path="id"/><br>
    name:<form:input path="name"/><form:errors path="name"/><br><%-- path名与对应表单name值相同，注意：JSR303的错误验证信息显示只能通过springMVC的标签来显示，而其他的表单元素则可以用普通标签来代替 --%>
    password:<form:password path="password"/><form:errors path="password"/><br>
    <input type="file" name="attaches"><br>
    <input type="file" name="attaches"><br><%-- 多文件上传需要指定相同的name值 --%>
    <input type="submit" value="保存"/>
</form:form>
</body>
</html>
