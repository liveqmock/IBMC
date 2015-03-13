<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title></title>
	<link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/datagrid.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/tipcss.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid-pagi.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/plugins.js'/>"></script>
</head>
<body>
<!-- 后面新增的样式 -->
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">刷卡短信通知</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
	    <form method="post" name="form" id="form1" action="">
		    关键字：<select name="fields" id="b_fields">
	            <option value="sendtxt" ${fields=='sendtxt'?'selected':''}>短信内容</option>
	        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value="${keyword}"/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
            <input type="hidden" name="querymap.traceid" value="${querymap.traceid}" />
		</form>
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
    <div class="clear"/>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<script type="text/javascript">
	//列
    var cols = [
        { title: '序号', name: '', width: 30, lockWidth: true, align: 'center', rowspan: 2, renderer: function (val, item, rowIndex) {
            return rowIndex + 1 + '<input type="hidden" name="pkid" value=' + item.id + ' />';
        }},
        { title: '租户姓名', name: 'rentname', width: 60, align: 'center', renderer: null},
        { title: '刷卡时间', name: 'touchdate', width: 120, align: 'center', renderer: null},
        { title: '发送时间', name: 'senddate', width: 120, align: 'center', renderer:null},
        { title: '发送状态', name: 'status', width: 60, align: 'center', renderer: function(val,item,rowIndex){
        	var tempval = '';
        	if(val == '${MESSSTATE_WAIT}'){
       			tempval = '待发送';
        	}else if(val == '${MESSSTATE_SUCC}'){
        		tempval = '发送成功';
        	}else if(val == '${MESSSTATE_FAIL}'){
        		tempval = '发送失败';
        	}
        	return tempval;
        }},
        { title:'短信内容', name:'sendtxt' ,width:200, align:'left',renderer:null},
        { title: '通知手机号码', name: 'phone', width: 100, align: 'center', renderer:null}
    ];
    
	var url = '<c:url value='/Man/Card/findManCardNoticeMessListByJq.shtml'/>';
	//列表数据加载
    var mmg = $('#dataTable').mmGrid({
        height: '290',
        cols: cols,
        url: url,
        nowarp: true,
        method: 'post',
        root: 'items',
        plugins: [
            $('#dataPagi').mmPaginator()
        ]
    });
    
    
    $(function () {
        //搜索
        $("#search").bind("click",function(){
        	mmg.load();
        });
    });
		
</script>
</body>
</html>