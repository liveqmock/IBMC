<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title></title>
<link rel="stylesheet" href="../../Style/main.css"/>
<style>
	.tipNoPur{
		color:red;
		font-weight:bold;
		font-size:36px;
	}
</style>
</head>
<body>
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">提示信息</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <div class="tipNoPur">&nbsp;&nbsp;${htmlError}</div>
</div>
</body>
</html>