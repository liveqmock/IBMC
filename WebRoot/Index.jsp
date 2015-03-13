<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="Style/main.css" rel="stylesheet" type="text/css"/>
    <link href="Style/tabs.css" rel="stylesheet" type="text/css"/>
    <link href="Style/win.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="Ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
    <link rel="stylesheet" href="Ztree/css/demo.css" type="text/css"/>
    <script type="text/javascript" src="Script/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="Script/plugin/win/jquery.win-2.3.min.js"></script>
    <script type="text/javascript" src="Script/plugin/win/jquery.win-zh-cn.js"></script>
    <script type="text/javascript" src="Script/plugin/menu/jquery.slider.js"></script>
    <script type="text/javascript" src="Script/plugin/tab/jquery.tabs.js"></script>
    <script type="text/javascript" src="Ztree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript">
    </script>
    <style type="text/css">
        html {
            overflow-y: hidden;
        }
		#iframeing {
		    width:expression(this.nextSibling.offsetWidth);
		    height:expression(this.nextSibling.offsetHeight);
		    position:absolute;
		    border:1px solid red;
		    height:300px;
		}
    </style>
</head>
<body>
<div id="header">
    <div class="top_logo">
        <div class="logo"></div>
    </div>
    <div class="top_link">
        ${loginSession.username}
        &nbsp;&nbsp;&nbsp;&nbsp;
        <!-- 
        <a href="JavaScript:void(0);" id="fontPage">查看前台</a>&nbsp;|&nbsp;
         -->
        <a href="JavaScript:void(0);" id="out">退出</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </div>
</div>
<div class="clear"></div>
<div id="leftMenuBar">
    <div class="accordion">
    	<c:forEach items="${menuList }" var="m">
        <div class="sysMenu"><span id="m_${m.id}" class="sysText">${m.name }</span>
            <ul class="ztree" id="sysSubMenu_m_${m.id}"></ul>
        </div>
    	</c:forEach>
    </div>
</div>
<div id="containerContent">
    <div class="tabsDiv">
        <ul id="tabs">
            <li class="current indexCurrent" id="li_index">
                <span class="tab" id="index" title="首页">首页</span>
            </li>
        </ul>
        <div class="switchTabs">
            <span class="tabMore"><!--下拉图片--></span>
    	     <iframe id="switchIframe" style="position:absolute;z-index:0;width:expression(this.nextSibling.offsetWidth);
            height:expression(this.nextSibling.offsetHeight);top:expression(this.nextSibling.offsetTop);
            left:expression(this.nextSibling.offsetLeft);
            filter: alpha(opacity=50); /*IE*/
            opacity: 0.5; /*FF*/display:none;" frameborder="0" ></iframe>
         <ul id="collect">
                <li><span id="closeAllSwitch">关闭全部</span></li>
                <li class="currentSwitch">
                    <span class="collectTab" id="index" title="首页">首&nbsp;&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;</span>
                </li>
            </ul>
            
        </div>
    </div>
    <div class="dv"></div>
    <div id="content" >
        <iframe id="index_content"  width="100%"
                marginheight="0" marginwidth="0" frameborder="0" scrolling="no" height=100% onLoad="iFrameHeight()" >
        </iframe>
    </div>
</div>
    <script type="text/javascript" src="Script/Index.js"></script>
</body>
</html> 