<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	<style>

		#operRoleDiv{
			display:none;
		}
		.blankSpan{
			width:660px;text-align:right;display:block;
		}
	</style>
</head>
<body>
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">权限设置[<span class="switchSearch">数据搜索</span>]</div>
    </div>
     <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
        <form method="post" name="form" id="form1" action="">
                  部门名称：<select name="querymap.id" id="b_organname">
                  <option value="">全部</option>
                  <c:forEach items="${deptList}" var="d">
                  	<option value="${d.id }">${d.organname}</option>
                  </c:forEach>
              </select>
                    角色名称： <select name="querymap.roleid" id="b_roleid">
                    <option value="">全部</option>
                  <c:forEach items="${roleList}" var="r">
                  	<option value="${r.id }">${r.rolename}</option>
                  </c:forEach>
                </select>
                    关键字：
    		 <input type="hidden" name="fields" value="username"/>
    		 <input type="text" name="keyword" id="searchkey" class="text_10" value="${keyword}"/>
             <input type="button" name="search" id="search" class="btn btn-primary" value="搜索" onclick="queryData();"/>
    		
 		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" onClick="impUser();">引入</button>
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
     <!-- 下面代码在角色设置时使用 -->
     <div id="operRoleDiv">
     	<span class="blankSpan">
     	请选择对应的角色：
     	    <span id="operRoleSpan">
	     	<select id="operRoleSel" name="operRoleSel">
                  <option value="">------------</option>
                  <c:forEach items="${operRoleList}" var="op">
                  	<option value="${op.id }">${op.rolename}</option>
                  </c:forEach>
	     	</select>
     	    </span>
     	</span>
     </div>
</div>
<script type="text/javascript">
    //列
    var cols = [
    	{ title:'序号', name:'' ,width:40,lockWidth:true, align:'center',renderer: function(val,item,rowIndex){
             return rowIndex+1;
        }},
        { title:'角色名称', name:'rolename' ,width:120, align:'center',renderer: null},
        { title:'单位名称', name:'unitid' ,width:320, align:'left'},
        { title:'部门名称', name:'organname' ,width:270, align:'left'},
        { title:'姓名', name:'username' ,width:70, align:'center'},
        { title:'维护人', name:'mainusername' ,width:70, align:'center'},
        { title:'操作', name:'' ,width:120, align:'left', lockWidth:true, lockDisplay: true, renderer: function(val,item,rowIndex){
        	if("${isSuper}" == "1"){
        		if("${loginUserId}" == item.userid){
        			return "&nbsp;";
        		}else{
        		    if(item.roleids == ''){
		        		return "<button class=\"btn btn-info\" onClick=setUser('"+item.organname+"','"+item.username+"','"+item.userid+"','"+item.roleids+"');>请设置</button>&nbsp;&nbsp;<button class=\"btn btn-danger\" onClick=deleteOper('"+item.userid+"');>删除</button>" 
		        	}else{
		        		return "<button class=\"btn btn-info\" onClick=setUser('"+item.organname+"','"+item.username+"','"+item.userid+"','"+item.roleids+"');>设置</button>&nbsp;<button class=\"btn btn-danger\" onClick=deleteOper('"+item.userid+"');>删除</button>"
		        	}
        		}
        	}
        	else{
        	    if(item.mainuser != ""){
        	        if(item.mainuser.indexOf("${estauserid}")>=0){
        		    	if(item.roleids == ''){
		        			return "<button class=\"btn btn-info\" onClick=setUser('"+item.organname+"','"+item.username+"','"+item.userid+"','"+item.roleids+"');>请设置</button>&nbsp;&nbsp;<button class=\"btn btn-danger\" onClick=deleteOper('"+item.userid+"');>删除</button>" 
		        		}else{
		        			return "<button class=\"btn btn-info\" onClick=setUser('"+item.organname+"','"+item.username+"','"+item.userid+"','"+item.roleids+"');>设置</button>&nbsp;<button class=\"btn btn-danger\" onClick=deleteOper('"+item.userid+"');>删除</button>"
		        		}
        	        }
        	    }
        	}
        	return "";
        }}
    ];
    
     //分页
    var paramsV={
    	'querymap.roleid':'${querymap.roleid}',
    	'querymap.id':'${querymap.id}',
    	'fields':'username',
    	'keyword':'${keyword}'
    }
	var url = "<c:url value='/Base/RoleUser/listBaseRoleUserByJq.shtml'/>";
    var mmg = $('#dataTable').mmGrid({
        indexColWidth: 35,
        height:'420',
        cols: cols,
        url: url,
        params:paramsV,
        method: 'post',
        root: 'items',
		checkCol:true,
		nowarp:true,
        plugins : [
            $('#dataPagi').mmPaginator()
        ]
    });
    
	$(function(){
	   $("#b_roleid").bind("change",queryData);
	   $("#b_organname").bind("change",queryData);
    });
     //数据搜索
    function queryData(){
    	var keyword=$("#searchkey").val();
    	//默认重置为第一页
    	var	tagpage = '1'; 
    	_searparam = {'querymap.id':$("#b_organname").val(),'querymap.roleid':$("#b_roleid").val(),'keyword':keyword,'tagpage':tagpage};
	    mmg.load({'querymap.id':$("#b_organname").val(),'querymap.roleid':$("#b_roleid").val(),'keyword':keyword,'tagpage':tagpage});
    }
    
	//清除
	function deleteOper(id){
    	if(id == ''){
        	parent.$.jBox.tip("参数错误！请刷新页面后重试！", 'info');
          	return ;
        }
        var url = "<c:url value='/Base/RoleUser/deleteBaseRoleUserByJq.shtml'/>";
        var submit = function (v, h, f) {
            if (v == 'ok')
            	$.ajax({
					type: "POST", 
					url: url, //代码数据
					async:false,
					dataType:"json",
					data:{'baseRoleUserBean.userid':id},
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
	
	
   //人员引入
   function impUser(){
        $("#operRoleSel").val('');
   		var bottomTextV = $("#operRoleDiv").html();
   		var url = '<c:url value='/Base/RoleUser/setbaseuser.shtml'/>';
		var msg = '权限设置-人员引入';
	    parent.$.jBox.open("iframe:"+url, msg, 800, 435, { bottomText: bottomTextV,buttons: {'确定':true,'关闭':false},
	        submit: function (v, h, f) {
	           if(!v){return true;}
	           var we = h.find("#jbox-iframe")[0].contentWindow;
               var rtn = we.callBack();
               if(rtn != "-1"){
		            var roleid = h.parent().parent().find("#operRoleSel").val();
		        	if(roleid == ""){
		               	 parent.$.jBox.tip("请选择要绑定的角色！", 'info');
		               	 return false;
		        	}
		        	var url = "<c:url value='/Base/RoleUser/savebaseroleuser.shtml'/>";
		        	var flag = false;
		        	//保存
	            	$.ajax({
						type: "POST", 
						url: url, //代码数据
						async:false,
						dataType:"json",
						data:{'baseRoleUserBean.userid':rtn,'baseRoleUserBean.roleids':roleid},
						error:function(data){
						    parent.$.jBox.tip("与数据库连接失败！", 'info');
						},
						success:function(data) {
			                parent.$.jBox.tip("操作成功！", 'info');
			         	    mmg.load();
			                flag = true;
						}
					});
					return flag;
               }
               return false;
	        }
	    });
   
   }
	
	
    //角色设置
	function setUser(organname,username,userid,roleids){
   		$("#operRoleSpan #operRoleSel option").each(function(i,dom){
   			if($(this).val() == roleids){
   				$(this).attr("selected",true);
   			}
   		});
   		var operRoleSpan = $("#operRoleSpan").html();
   		var html = "<div style=\"margin:5px 0px 0px 15px;\">部门名称："+organname+"</div>";
   		html += "<div style=\"margin:8px 0px 0px 15px;\">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名："+username+"</div>";
   		html += "<div style=\"margin:8px 0px 5px 15px;\">关联角色："+operRoleSpan+"</div>";
		var submit = function (v, h, f) {
		    var flag = false;
		    if(f.operRoleSel!=""){
		        	var url = "<c:url value='/Base/RoleUser/updatebaseroleport.shtml'/>";
		        	//保存
	            	$.ajax({
						type: "POST", 
						url: url, //代码数据
						async:false,
						dataType:"json",
						data:{'baseRoleUserBean.userid':userid,'baseRoleUserBean.roleids':f.operRoleSel},
						error:function(data){
						    parent.$.jBox.tip("与数据库连接失败！", 'info');
						},
						success:function(data) {
			                parent.$.jBox.tip("操作成功！", 'info');
			         	    mmg.load();
			                flag = true;
						}
					});
		    }else{
               	 parent.$.jBox.tip("请选择要绑定的角色！", 'info');
               	 return false;
		    }
		    return flag;
		};
		
		parent.$.jBox(html, { top: '35%', title: "权限设置-角色修改", submit: submit,buttons: {'确定':true,'关闭':false} });
	}
</script>
</body>
</html>