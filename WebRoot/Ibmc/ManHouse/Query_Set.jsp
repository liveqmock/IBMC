<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/datagrid.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/tipcss.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/valid.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid-pagi.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/plugins.js'/>"></script>
	<script src="<c:url value='/Script/plugin/valid/FormValidator.1.0.min.js'/>"></script>
</head>
<body>
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">${sysCommunityBean.parentname}/${sysCommunityBean.ownername}/${sysCommunityBean.commname}</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<script language="javascript"> 
    //列
    var cols = [
        { title:'房间名称', name:'commname' ,width:560, align:'left',renderer:null},
        { title:'备注', name: 'remark', width: 40, align: 'center', renderer: function (val, item, rowIndex) {
            return (val!="")?"<span class='onShow' title='"+val+"'></span> ":"&nbsp;";
        }}
    ];
	var url = '<c:url value='/Man/House/findManHouseRoomListByJq.shtml'/>';
	param = {'sysCommunityBean.id':'${sysCommunityBean.id}'};
    //分页
    var mmg = $('#dataTable').mmGrid({
        indexColWidth: 35,
        height:'320',
        cols: cols,
        url: url,
        params: param,
		checkCol:true,
        nowarp: true,
        method: 'post',
        root: 'items',
        plugins: [
            $('#dataPagi').mmPaginator()
        ]
    });
	
</script>
</body>
</html>