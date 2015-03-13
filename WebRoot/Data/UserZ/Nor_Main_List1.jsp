<%@ page language="java" pageEncoding="UTF-8"%>
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
<!-- 后面新增的样式 -->
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
    <div class="titleClass">人员管理[<span class="switchSearch">数据搜索</span>]</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
    	<form method="post" name="form" id="form1" action="">
    		&nbsp;部门名称：<select name="querymap.deptid" id="deptid">
                       		<option value="">全部</option>
                          </select>
			关键字：<select id="fields" name="fields">
					<option value="us.username" ${fields=='us.username'?'selected':''}>姓名</option>
					<option value="us.userid" ${fields=='us.userid'?'selected':''} >身份证号</option>
					<option value="u.workid" ${fields=='u.workid'?'selected':''}>工号</option>
				</select>
				
               <input type="text" name="keyword" id="keyword" class="text_10" value="${keyword}" maxlength="10" />
               <input type="button" name="btn_query" class="btn btn-primary" value="查询" onclick="toSearch();" />
	           <input type="hidden" name="querymap.unitid" value="${querymap.unitid}" />
	           <input type="hidden" name="querymap.fatherid" value="${querymap.fatherid}" />
	           <input type="hidden" name="fatherid" value="${fatherid}" />
	           <input type="hidden" name="sortfield" value="${sortfield}" />
	    </form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="btn_insert">新增</button>
        	<button class="btn btn-danger" id="b_batchdel">批量删除</button>
        </div>
        <div class="clear"></div>
        <table id="dataTable"></table>
    </div>
</div>
<script type="text/javascript">
    //列
    var i=0,j=0,k=0,myIndex;
    var cols = [
    	{ title:'序号', name:'id' ,width:40,lockWidth:true, align:'center',renderer: function(val,item,rowIndex){
             return rowIndex+1+'<input type="hidden" name="pkid" value='+val+' />';
        }},
        { title:'部门名称', name:'deptname' ,width:150, align:'left'},
        { title:'班组（或科室）名称', name:'groupname' ,width:150, align:'left'},
        { title:'姓名', name:'username' ,width:80, align:'center'},
        { title:'性别', name:'sex' ,width:50, align:'center'},
        { title:'岗位名称', name:'mpost' ,width:250, align:'left'},
        { title:'操作', name:'' ,width:220, align:'left', lockWidth:true, lockDisplay: true, renderer: function(val,item,rowIndex){
        	return "<button class=\"btn btn-info\" onClick=setDataWin("+item.id+",'2');>修改</button>&nbsp;<button class=\"btn btn-danger\" onClick=delData("+item.id+");>删除</button>"
        }}
    ];
    
	var url = "<c:url value='/Data/UserZ/findDataUserZListByJq.shtml'/>";
	var paramsV={
    	'querymap.unitid':'${querymap.unitid}',
		'sortfield':'${sortfield}',
		'fields':'us.username',
		'keyword':'${keyword}'
    }
	//分页
    var mmg = $('#dataTable').mmGrid({
        cols: cols,
        width:'100%',
        height:'390',
        url: url,
        params:paramsV,
        method: 'post',
		checkCol:true,
        multiSelect: true,
        root: 'items',
        plugins : [
            $('#dataPagi').mmPaginator(),
        ]
    });
    
	$(function(){
		//构建部门名称列表
		mkDeptList();
		
		//部门change事件触发班组下拉列表框
		$("#deptid").bind("change",function(){
			toSearch();
		});
		
		//新增人员
		$("#btn_insert").bind("click",function(){
			setDataWin('','1');
		});
		
		//批量删除
    	$("#b_batchdel").bind("click",function(){
    		delData('');
    	});
    });
    
    //构造部门列表
    function mkDeptList(){
    	$.ajax({
             type: 'post',
             async: false,
             url: '<c:url value='/Data/OrgZ/findDataOrganZNoTreeListByJq.shtml'/>',
             data: {'querymap.unitid':'${querymap.unitid}','querymap.fatherid':'${querymap.fatherid}'},
             dataType: 'json',
             cache: false,
             success:function(data){
             	var str = "";
				//通过jquery方法进行循环编译
				$.each(data,function(i,d){
					var sel = ""
					if('${querymap.deptid}'==d.id){
						sel = "selected";
					}
					str += "<option "+sel+" value="+d.id+" >"+d.organname+"</option>";
				});
				$("#deptid").append(str);
             },
			error:function(msg){
			    parent.$.jBox.tip("与数据库连接失败！", 'info');
			}
        });
    }
    
    //查询
    function toSearch(){
		//点击查询时页码置为1
		var tagpage = '1';
		mmg.load({'tagpage':tagpage});
    }
    
    //新增,修改[弹出]
	function setDataWin(id,fun){
		var url = '';
		var msg = '';
		if(fun == '1'){
			msg = '人员管理-新增';
			url = "<c:url value='/Data/UserZ/setdatauserzbean.shtml'/>?dataUserZBean.id=&querymap.unitid=${querymap.unitid}";
			url += "&querymap.fatherid=${querymap.fatherid}"
			url += "&fun=1";
		}else if(fun == '2'){
			msg = '人员管理-修改';
			url = "<c:url value='/Data/UserZ/setdatauserzbean.shtml'/>?dataUserZBean.id="+id+"&querymap.unitid=${querymap.unitid}";
			url += "&querymap.fatherid=${querymap.fatherid}"
			url += "&fun=2";
		}
	    parent.$.jBox.open("iframe:"+url, msg, 800, 450, {buttons: {}});
	}
	
	//删除
    function delData(id){
    	var newid = id;
        if(''==id){
			var idAttr=new Array();
			$.each($("#dataTable input[type='checkbox']"),function(i,n){
				if(n.checked){
					var obj = $(this).parent().parent().parent();
					$("td:eq(1) input[name='pkid']",obj).val();
					idAttr.push($("td:eq(1) input[name='pkid']",obj).val());
				}
			});
			newid = idAttr.join(",");
			if(newid == ''){
	        	parent.$.jBox.tip("请选中您所要操作的数据！", 'info');
	          	return ;
	        }
        }
    	if(newid == ''){
        	parent.$.jBox.tip("参数错误！请刷新页面后重试！", 'info');
          	return ;
        }
        var submit = function (v, h, f) {
            if (v == 'ok')
            	$.ajax({
					type: "POST", 
					url: "<c:url value='/Data/UserZ/delDataUserZBeanIdStrByJq.shtml'/>", //代码数据
					async:false,
					data:{'querymap.idstr':newid},
					error:function(data){
					    parent.$.jBox.tip("与数据库连接失败！", 'info');
					},
					success:function(data) {
						if(data == '1'){
	            			parent.$.jBox.tip("操作成功！", 'info');
		                    //toSearch();
		                    mmg.load();
						}else{
	            			parent.$.jBox.tip("操作失败！", 'info');
						}
					}
				});
            return true;
        };
        parent.$.jBox.confirm("确定删除该记录？", "提示", submit);
    }
	
	//子页面重新刷新父页面
	function reloadPage(){
		//toSearch();
		mmg.load();
	}
</script>
</body>
</html>