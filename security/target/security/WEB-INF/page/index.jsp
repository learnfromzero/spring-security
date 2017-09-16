<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/resources/inc.jsp"%>
<html>
<head>
    <title>首页</title>
    <script type="text/javascript" src="<%=basePath%>/resources/js/jquery/jquery-1.9.1.js"></script>
</head>
<body>
<h1 style="color: #0b08d0">HELLO <%=request.getSession().getAttribute("userName")%>!</h1>
</body>
</html>
