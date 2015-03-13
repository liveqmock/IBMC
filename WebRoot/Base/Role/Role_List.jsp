<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title></title>
	<link rel="stylesheet" href="../../Style/main.css"/>
	<link rel="stylesheet" href="../../Style/datagrid.css"/>
	<link rel="stylesheet" href="../../Style/bootstrap.css"/>
	<script type="text/javascript" src="../../Script/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="../../Script/plugin/grid/grid.js"></script>
	<script type="text/javascript" src="../../Script/plugin/grid/grid-pagi.js"></script>
	<script type="text/javascript" src="../../Script/plugin/grid/plugins.js"></script>
</head>
<body>
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">角色定义</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="btn_insert">新增 </button> 
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
    	{ title:'序号', name:'' ,width:40,lockWidth:true, align:'center',renderer: function(val,item,rowIndex){
             return rowIndex+1;
        }},
        { title:'所在组', name:'rgroup' ,width:100, align:'center',renderer: function(val,item,rowIndex){
        	if(val ==0){
             return "用户组";
            }else{
           	 return "系统组";
            }
        }},
        { title:'角色名称', name:'rolename' ,width:300, align:'left'},
        { title:'备注', name:'remark' ,width:340, align:'left'},
        { title:'权值', name:'weight' ,width:40,lockWidth:true, align:'center'},
        { title:'操作', name:'' ,width:120, align:'left', lockWidth:true, lockDisplay: true, renderer: function(val,item,rowIndex){
        	if(item.rgroup == 1){
        		return "<button class=\"btn btn-info\" onClick=setData("+item.id+",'2');>修改</button>" 
        	}else{
        		return "<button class=\"btn btn-info\" onClick=setData("+item.id+",'2');>修改</button>&nbsp;<button class=\"btn btn-danger\" onClick=deleteOper("+item.id+");>删除</button>"
        	}
        	return "";
        }}
    ];
	var url = "<c:url value='/Base/Role/listBaseRoleByJq.shtml'/>";
    //分页
    var mmg = $('#dataTable').mmGrid({
        indexColWidth: 35,
        height:'420',
        cols: cols,
        url: url,
        params:'',
        method: 'get',
        root: 'items',
		checkCol:true,
		nowarp:true,
        plugins : [
            $('#dataPagi').mmPaginator()
        ]
    });
    
	$(function(){
    	//新增
    	$("#btn_insert").bind("click",function(){
    		setData("","1");
    	});
    });
    
	//清除
	function deleteOper(id){
    	if(id == ''){
        	parent.$.jBox.tip("参数错误！请刷新页面后重试！", 'info');
          	return ;
        }
        var url = "<c:url value='/Base/Role/deleteBaseRoleByJq.shtml'/>";
        var submit = function (v, h, f) {
            if (v == 'ok')
            	$.ajax({
					type: "POST", 
					url: url, //代码数据
					async:false,
					dataType:"json",
					data:{'baseRoleBean.id':id},
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
            else if (v == 'cancel')
            return true;
        };
        parent.$.jBox.confirm("确定删除该记录？", "提示", submit);
	}

	
	
    //新增,修改代码设置
	function setData(id,fun){
	    var url = '';
		var msg = '';
		if(fun == '1'){
			msg = '新增';
			url = "<c:url value='/Base/Role/setbaserole.shtml'/>?fun="+fun;
		}else if(fun == '2'){
			msg = '修改';
			url = "<c:url value='/Base/Role/setbaserole.shtml'/>?fun="+fun;
			url +="&baseRoleBean.id="+id;
		}
	    parent.$.jBox.open("iframe:"+url, msg, 750, 400, { buttons: {}});
	}
	//子页面重新刷新父页面
	function reloadPage(){
		mmg.load();
	}
</script>
</body>
</html>