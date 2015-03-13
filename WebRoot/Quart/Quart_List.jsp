<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../Include/TagLib.jsp"%> 
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
        <div class="titleClass">调度管理</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
	    <form method="post" name="form" id="form1" action="">
		     关键字：<select name="fields" id="b_fields">
		     	<option value="remark" ${fields=='remark'?'selected':''}>任务描述</option>
	        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value="${keyword}"/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="btn_insert">新增</button>
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
        { title: '序号', name: '', width: 30, lockWidth: true, align: 'center', rowspan: 2, renderer: function (val, item, rowIndex) {
            return rowIndex + 1;
        }},
        { title: '类型', name: 'jobgroup', width: 40, align: 'center', renderer: function(val,item,rowIndex){
        	return val=='0'?'临时':'永久';
        }},
        { title: 'JOB', name: 'jobid', width: 140, align: 'center', renderer: function(val,item,rowIndex){
        	return '<span class="fontcolor_red_b">'+(item.jobstatus=='0'?'！':'')+'</span>'+val;
        }},
        { title: '任务描述', name: 'remark', width: 240, align: 'left', renderer: null},
        { title: '时间规则', name: 'cronexpr', width: 120, align: 'center', renderer: null},
        { title: '执行方法', name: 'methname', width: 120, align: 'center', renderer: null},
        { title: '方法参数', name: 'param', width: 60, align: 'center', renderer: null},
        { title: '维护时间', name:'maindate' ,width: 140, align:'center',renderer: null},
        { title: '操作', name: 'jobstatus', width: 140, align: 'left', renderer: function (val, item, rowIndex) {
        	if(val == 1){
        		return "<button class=\"btn btn-info\" onClick=setData("+item.id+",'2');>修改</button>&nbsp;<button class=\"btn btn-disabled\" onClick=operUsesign("+item.id+",'0');>禁用</button>" 
        	}else if (val == 0){
        		return "<button class=\"btn btn-info\" onClick=setData("+item.id+",'2');>修改</button>&nbsp;<button class=\"btn btn-danger\" onClick=delData("+item.id+");>删除</button>&nbsp;<button class=\"btn btn-info\" onClick=operUsesign("+item.id+",'1');>启用</button>"
        	}
        }}
    ];
    
	var url = '<c:url value='/Net/Quart/findNetQuartListByJq.shtml'/>';
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
    
    //启用|禁止
    function operUsesign(id,operSign){
    	if(id == ''){
        	parent.$.jBox.tip("参数错误！请刷新页面后重试！", 'info');
          	return ;
        }
        var submit = function (v, h, f) {
            if (v == 'ok')
           		$.ajax({
					type: "POST", 
					url: "<c:url value='/Net/Quart/updateNetQuartByJq.shtml'/>", //启用
					async:false,   //ajax同步
					data:{'netQuartBean.id':id,'netQuartBean.jobstatus':operSign},
					error:function(data){
				    	parent.$.jBox.tip("与数据库连接失败！", 'info');
					},
					success:function(data) {
						if(data == '1'){
            				parent.$.jBox.tip("操作成功！", 'info');
		                    mmg.load();
						}else{
            				parent.$.jBox.tip("操作失败！", 'info');
						}
					}
				});
            return true; //close
        };
        parent.$.jBox.confirm("确定启用|禁止操作？", "提示", submit);
    }
    
    //删除
    function delData(id){
    	if(id == ''){
        	parent.$.jBox.tip("参数错误！请刷新页面后重试！", 'info');
          	return ;
        }
        var submit = function (v, h, f) {
            if (v == 'ok')
            	$.ajax({
					type: "POST", 
					url:"<c:url value='/Net/Quart/deleteNetQuartByJq.shtml'/>", //代码数据
					async:false,    //ajax同步
					data:{'netQuartBean.id':id},
					error:function(data){
					    parent.$.jBox.tip("与数据库连接失败！", 'info');
					},
					success:function(data) {
						if(data == '1'){
	            			parent.$.jBox.tip("操作成功！", 'info');
		                    mmg.load();
						}else{
	            			parent.$.jBox.tip("操作失败！", 'info');
						}
					}
				});
            return true; //close
        };
        parent.$.jBox.confirm("确定删除该记录？", "提示", submit);
    }
    
    $(function () {
        //新增
        $("#btn_insert").bind("click",function(){setData('');});
        //搜索
        $("#search").bind("click",function(){
        	mmg.load();
        });
    });
    
    //新增,修改弹出页面
    function setData(id){
    	var url = "<c:url value='/Net/Quart/setnetquart.shtml'/>";
    	url += "?netQuartBean.id="+id;
    	var msg = '调度管理-新增';
    	if(id!=''){
    		msg = '调度管理-修改';
    	}
        parent.$.jBox.open("iframe:"+url, msg, 600 , 420, { buttons: {}});
    }
    
    function reloadPage(){
	    mmg.load();
	}
	
</script>
</body>
</html>