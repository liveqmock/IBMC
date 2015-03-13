<%@ page contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
    <link href="Style/win.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="<c:url value='/Script/jquery-1.4.2.min.js'/>"></script>
    <script type="text/javascript" src="Script/plugin/win/jquery.win-2.3.min.js"></script>
    <script type="text/javascript" src="Script/plugin/win/jquery.win-zh-cn.js"></script>
	<style type="text/css">
		body{border:none;margin:0px;padding:0px;}
	</style>
</head>	
<body>
	<!-- 显示错误信息 -->
	<s:property value="#request.htmlError" escape="false"/>
</body>
</html>
