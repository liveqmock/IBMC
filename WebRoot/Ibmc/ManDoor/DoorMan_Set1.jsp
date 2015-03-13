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
        <div class="titleClass">门口机管理</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
	    <form method="post" name="form1" id="form1" action="">
		     关键字：<select name="fields" id="b_fields">
	            <option value="doormac" ${fields=='doormac'?'selected':''}>门口机MAC</option>
	            <option value="doorip" ${fields=='doorip'?'selected':''}>门口机IP</option>
	            <option value="name" ${fields=='name'?'selected':''}>门口机名称</option>
	        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value="${keyword}"/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
            <input type="hidden" name="querymap.optuserid" value="${querymap.optuserid}" />
            <input type="hidden" value="1" name="querymap.showsign" id="showsign" />
            
			<input type="hidden" name="tokenid" value="${tokenid}" />
            <input type="hidden" name="manDoorHouseBean.houseid" value="${manDoorHouseBean.houseid}" />
            <input type="hidden" name="manDoorHouseBean.doorid" value="${manDoorHouseBean.doorid}" id="doorid" />
		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="btn_link">关联门口机</button>
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <div class="btnGroup"></div>
    <div class="clear"/>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<script type="text/javascript">
	//列
    var cols = [
        { title: '序号', name: '', width: 40, lockWidth: true, align: 'center', renderer: function (val, item, rowIndex) {
            return rowIndex + 1 + '<input type="hidden" name="pkid" value=' + item.id + ' />';
        }},
        { title: '名称', name: 'name', width: 240, align: 'left', renderer: null},
        { title: 'IP', name: 'doorip', width: 100, align: 'center', renderer: null},
        { title: 'MAC', name: 'doormac', width: 140, align: 'center', renderer:null},
        { title: '操作员', name: 'optusername', width: 100, align: 'center', renderer:null},
        { title:'备注', name:'remark' ,width:60, align:'center',renderer: function(val,item,rowIndex){
            return val!=''? '<span class="onShow" title="'+val+'"></span>':'&nbsp;';
        }}
    ];
    
	var url = '<c:url value='/Man/Door/findManDoorListByJq.shtml'/>';
	//列表数据加载
    var mmg = $('#dataTable').mmGrid({
        height: '380',
        cols: cols,
        url: url,
        multiSelect:false,
		checkCol:true,
        nowarp: true,
        method: 'post',
        root: 'items',
        plugins: [
            $('#dataPagi').mmPaginator()
        ]
    });
    
    $(function () {
    	//仅显示
    	$("#showsign").bind("click",function(){
        	mmg.load();
    	})
    	
    	$("#btn_link").bind("click",function(){
    		var rows = mmg.selectedRows();
	        var selecedId = "";
	        if(rows.length == 0){
	            parent.$.jBox.tip("请选择要操作的记录！", 'warning');
	            return "";
	        }
	        for(var i=0;i<rows.length;i++){
	        	selecedId = rows[i].id;
	        }
	        
			var submit = function (v, h, f) {
	            if (v == 'ok'){
			        $("#doorid").val(selecedId);
					$("#form1").attr("action",'<c:url value='/Man/Door/savemandoorlinkhouse.shtml'/>').trigger("submit");
	            }
	            return true;
	        };
        	parent.$.jBox.confirm("系统要求一个房产只能关联一个门口机，若原房产\"已关联门口机\"，那么本次关联操作将无效，请先执行\"取消门口机关联\"操作后在进行该操作，确定执行该操作？", "提示", submit);
    	});
    	
        //搜索
        $("#search").bind("click",function(){
        	mmg.load();
        });
        
    });
    
    function reloadPage(){
	    mmg.load();
	}
	
</script>
</body>
</html>