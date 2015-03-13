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
</head>
<body>
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
        <form method="post" name="form2" id="form2" action="">
                  部门名称：<select name="querymap.id" id="b_organname">
                  <option value="">全部</option>
                  <c:forEach items="${deptList}" var="d">
                  	<option value="${d.id }" <c:if test="${querymap.id==d.id }">selected</c:if>>${d.organname}</option>
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
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<script type="text/javascript">
    //列
    var cols = [
    	{ title:'序号', name:'id' ,width:40,lockWidth:true, align:'center',renderer: function(val,item,rowIndex){
             return rowIndex+1+'<input type="hidden" name="pkid" value='+val+' />';
        }},
        { title:'部门名称', name:'deptname' ,width:280, align:'left'},
        { title:'姓名', name:'username' ,width:80, align:'center'},
        { title:'身份证号', name:'userid' ,width:160, align:'center'}
    ];
    
     //分页
    var paramsV={
    	'fields':'username',
    	'keyword':'${keyword}',
    	'querymap.id':'${querymap.id}'
    }
	var url = "<c:url value='/Base/RoleUser/listBaseUserByJq.shtml'/>";
    var mmg = $('#dataTable').mmGrid({
        indexColWidth: 35,
        cols: cols,
        url: url,
        params:paramsV,
        method: 'post',
        root: 'items',
		checkCol:true,
        multiSelect:true,
		nowarp:true,
        plugins : [
            $('#dataPagi').mmPaginator()
        ]
    });
    
	$(function(){
	   $("#b_organname").bind("change",queryData);
    });
    
     //数据搜索
    function queryData(){
    	var keyword=$("#searchkey").val();
    	//默认重置为第一页
    	var	tagpage = '1'; 
    	_searparam = {'querymap.id':$("#b_organname").val(),'keyword':keyword,'tagpage':tagpage};
	    mmg.load({'querymap.id':$("#b_organname").val(),'keyword':keyword,'tagpage':tagpage});
    }
		
	//批量引入
	function callBack(){
		//判断是否有选择人员
		var newid = '';
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
          	return "-1";
        }else{
        	return newid;
        }
        return "-1";
	}
</script>
</body>
</html>