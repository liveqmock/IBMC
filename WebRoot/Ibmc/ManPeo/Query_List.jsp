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
        <div class="titleClass">人员查询</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
	    <form method="post" name="form1" id="form1" action="">
	    	类型：<select name="querymap.type" id="type">
	            <option value="" ${querymap.type==''?'selected':''}>全部</option>
	            <option value="1" ${querymap.type=='1'?'selected':''}>业主</option>
	            <option value="2" ${querymap.type=='2'?'selected':''}>租客</option>
		    	</select>
		     关键字：<select name="fields" id="b_fields">
	            <option value="name" ${fields=='name'?'selected':''}>姓名</option>
	            <option value="idcard" ${fields=='idcard'?'selected':''}>身份证号码</option>
	            <option value="address" ${fields=='address'?'selected':''}>住址</option>
	        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value="${keyword}"/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
            <input type="hidden" name="querymap.ownersign" value="${querymap.ownersign}"/>
		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
			<button class="btn btn-info" id="btn_exp">导出人员列表</button>
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<script type="text/javascript">
	//列
    var cols = [
        { title: '序号', name: '', width: 40, lockWidth: true, align: 'center', rowspan: 2, renderer: function (val, item, rowIndex) {
            return rowIndex + 1 ;
        }},
        { title: '类型', name: '', width: 90, align: 'center', renderer: function(val, item, rowIndex){
        	var temp = '';
        	if(item.ownersign == '1'){
        		temp = '业主';
        	}
        	if(item.rentsign == '1'){
        		temp = (temp==''?'租客':temp+'/租客');
        	}
            return temp;
        }},
        { title: '姓名', name: 'name', width: 80, align: 'center', renderer: null},
        { title: '性别', name: 'sex', width: 40, align: 'center', renderer: function(val, item, rowIndex){
            return val=='${SEX_BOY}'?'男':'女';
        }},
        { title: '出生年月', name: 'birthday', width: 100, align: 'center', renderer: null},
        { title: '民族', name: 'nation', width: 80, align: 'center', renderer: null},
        { title: '联系电话', name: 'telephone', width: 120, align: 'center', renderer: null},
        { title: '住址', name: 'address', width: 320, align: 'left', renderer: null},
        { title:'备注', name:'remark' ,width:40, align:'center',renderer: function(val,item,rowIndex){
            return val!=''? '<span class="onShow" title="'+val+'"></span>':'&nbsp;';
        }},
        { title:'操作', name:'' ,width:50, align:'center',renderer: function(val,item,rowIndex){
            return '<button class="btn btn-info" onClick="setWin('+item.id+');">详细</button>';
        }}
    ];
    
	var url = '<c:url value='/Man/Peo/findManPeoListByJq.shtml'/>';
	
	//列表数据加载
    var mmg = $('#dataTable').mmGrid({
        indexColWidth: 35,
        height: '420',
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
        //绑定回车事件
        document.onkeydown = function (e) { 
			var theEvent = window.event || e; 
			var code = theEvent.keyCode || theEvent.which; 
			if (code == 13) {
				$("#search").trigger("click");
				return false;
			} 
		};
        //类型
        $("#type").bind("change",function(){
        	mmg.load();
        });
        //导出Excel
        $("#btn_exp").bind("click",function(){
        	$("#form1").attr("action",'<c:url value='/Man/Peo/savemanpeoexpexcel.shtml'/>').trigger("submit");
        });
    });
    
    //弹出详细页面
    function setWin(id){
    	var url = "<c:url value='/Man/Peo/setmanpeo_query.shtml'/>";
    	url += "?manPeoBean.id="+id;
    	var msg = '人员管理-详细';
        parent.$.jBox.open("iframe:"+url, msg, 490 , 460, { buttons: {}});
    }
    
</script>
</body>
</html>