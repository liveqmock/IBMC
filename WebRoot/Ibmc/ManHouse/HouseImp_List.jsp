<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title></title>
	<link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/tabs.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/datagrid.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/tipcss.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/plugins.js'/>"></script>
	<script language="javascript"> 
		$(function(){
			//确定导入
			$("#b_batchdel").bind("click",function(){
				//判断文件是否为空
				$("#form1").attr("action","<c:url value='/Man/House/savemanhousebyimptempdata.shtml'/>").trigger("submit");
			});
			//返回
			$("#back").bind("click",function(){
				$("#form1").attr("action","<c:url value='/Man/House/toforwardlistpage_imp.shtml'/>").trigger("submit");
			});
		});
		
	</script>
</head>
<body>
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">[${querymap.commname}]房产信息导入-预览</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
	    <form method="post" name="form" id="form1" action="">
		     关键字：<select name="fields" id="b_fields">
	            <option value="ownername" ${fields=='ownername'?'selected':''}>业主姓名</option>
            	<option value="owneridcard" ${fields=='owneridcard'?'selected':''}>业主身份证号</option>
	            <option value="commname" ${fields=='commname'?'selected':''}>房产地址</option>
	        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value="${keyword}"/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
            <input type="checkbox" name="querymap.showsign" id="showsign" value="1" ${querymap.showsign=='1'?'checked':''}/>仅显示导入的房产信息
            
			<input type="hidden" name="tokenid" value="${tokenid}" />
            <input type="hidden" name="querymap.parentid" value="${querymap.parentid}"/>
			<input type="hidden" name="querymap.commname" value="${querymap.commname}" />
			<input type="hidden" name="querymap.villagelev" value="${querymap.villagelev}" />
			<input type="hidden" name="querymap.needFilterScope" value="${querymap.needFilterScope}"/>
			<input type="hidden" name="querymap.maxLev" value="${querymap.maxLev}"/>
		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-danger" id="b_batchdel">将数据导入至正式库中</button>&nbsp;|&nbsp;
            <button class="btn btn-disabled" id="back">返回</button>
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<script type="text/javascript">
	var houseIndex = 0;
	var roomIndex = 0;
	var index ;
    //列
    var cols = [
        { title: '序号', name: 'commlev', width: 50, align: 'center',renderer:function(val,item,rowIndex){
			if(val == "${COMM_LEV_HOUSE}"){
				houseIndex = parseInt(houseIndex)+1;
				roomIndex = 0;
				index = houseIndex;
           	}else if(val == "${COMM_LEV_ROOM}"){
				roomIndex = parseInt(roomIndex)+1;
				index = houseIndex+'.'+roomIndex;
           	}
           	return index;
        }},
        { title: '房产类型', name: 'commtype', width: 80, align: 'center',renderer:function(val,item,rowIndex){
			if(val == "1"){
				return "<span class='fontcolor_red'>独立业主</span>";
           	}else if(val == "2"){
				return "<span class='fontcolor_red'>共有业主</span>";
           	}
        }},
        { title: '房屋地址/房间名称', name: 'commname', width: 400, align: 'left',renderer:function(val,item,rowIndex){
            var tmp = "";
            var lev = item.commlev;
            if(lev == "${COMM_LEV_HOUSE}"){
				tmp = "<span class='fontcolor_red'>"+val+"</span>";
           	}else if(lev == "${COMM_LEV_ROOM}"){
           		tmp = "&nbsp;&nbsp;&nbsp;&nbsp;"+val;
           	}
           	return tmp;
        }},
       	{ title: '类型', name: 'commlev', width: 60, align: 'center',renderer:function(val,item,rowIndex){
			if(val == "${COMM_LEV_HOUSE}"){
				return "<span class='fontcolor_red'>房屋</span>";
           	}else if(val == "${COMM_LEV_ROOM}"){
				return "房间";
           	}
        }},
        { title: '房间数', name: 'childnum', width: 60, align: 'center',renderer:function(val,item,rowIndex){
			if(item.commlev == "${COMM_LEV_HOUSE}"){
				return "<span class='fontcolor_red'>"+val+"间</span>";
           	}
           	return "";
        }},
        { title: '业主', name: 'ownername', width: 100, align: 'center'},
        { title: '身份证', name: 'owneridcard', width: 180, align: 'center'},
        { title: '备注', name: 'remark', width: 40, align: 'center', renderer: function (val, item, rowIndex) {
            return (val!="")?"<span class='onShow' title='"+val+"'></span> ":"&nbsp;";
        }}
    ];
    var url = '<c:url value='/Man/House/findManHouseImpDataListByJq.shtml'/>';
	//列表数据加载
    var mmg = $('#dataTable').mmGrid({
        indexColWidth: 35,
        height: '420',
        cols: cols,
        url: url,
        nowarp: true,
        method: 'post',
        root: ''
    });
    
	//搜索
	$("#search").bind("click",function(){
		houseIndex = 0;
		mmg.load();
	});
	
	//仅显示导入的房产信息
	$("#showsign").bind("click",function(){
		houseIndex = 0;
		mmg.load();
	});
    
</script>
</body>
</html>