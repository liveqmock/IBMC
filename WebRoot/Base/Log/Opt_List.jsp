<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
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
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">操作日志[<span class="switchSearch">数据搜索</span>]</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
      <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
        <form method="post" name="form" id="form1" action="">
                操作类型： <select name="querymap.opttype" id="b_opttype">
                    <option value="">全部</option>
                  <c:forEach items="${optEnums}" var="r">
                  	<option value="${r.key}" ${r.key==querymap.opttype?'selected':'' }>${r.desc}</option>
                  </c:forEach>
                </select> 
                    关键字：<select name="fields" id="b_fields">
		            <option value="optusername" ${fields=='optusername'?'selected':''}>姓名</option>
		        </select>
    		 <input type="text" name="keyword" id="searchkey" class="text_10" value="${keyword}"/>
             <input type="button" name="search" id="search" class="btn btn-primary" value="搜索" onclick="queryData();"/>
    		 <input type="hidden" name="querymap.intflag" value="1"/>
 		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-danger" id="b_batchdel">批量删除</button> 
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<script type="text/javascript">
    //列
    var i=0,j=0,k=0,myIndex;
    var cols = [
         { title:'序号', name:'' ,width:40,lockWidth:true, align:'center', rowspan:2, renderer: function(val,item,rowIndex){
            return rowIndex+1+'<input type="hidden" name="pkid" value='+item.id+' />';
        }},
        { title:'操作时间', name:'opttime' ,width:80, align:'center', renderer:null},
        { title:'操作人', name:'optusername' ,width:40, align:'center',renderer: null},
        { title:'内容', name:'optremark' ,width:20, align:'left',renderer: null},
        { title:'操作类型', name:'opttype' ,width:20, align:'center',renderer: function(val,item,rowIndex){
        	if(val==100){
            	return "<font color=green>新增</font>";
            }else if(val==101){
            	return "<font color=green>修改</font>";
            }else if(val==102){
            	return "<font color=green>保存</font>";
            }else if(val==103){
            	return "<font color=red>删除</font>";
            }
        }},  
        { title:'操作', name:'' ,width:20, align:'left', renderer: function(val,item,rowIndex){
  
           return  "<button class=\"btn btn-info\" onClick=\"optWin('"+item.id+"');\">详细</button>";
        }}
       
    ];
    
    var url = '<c:url value='/Base/Log/listBaseOptLogByJq.shtml'/>';   
    var mmg = $('#dataTable').mmGrid({
        indexColWidth: 35,
        height:'420',
        cols: cols,
        url: url,
        method: 'post',
        root: 'items',
        checkCol:true,
        multiSelect: true,
		nowarp:true,
        fullWidthRows: true,
        plugins: [
            $('#dataPagi').mmPaginator()
        ]
    });
    
    $(function(){
      	$("#b_deptid").bind("change",queryData);
	   	$("#b_opttype").bind("change",queryData);
	   	//批量删除
    	$("#b_batchdel").bind("click",delOper);
    });
    
    //数据搜索
    function queryData(){
	    mmg.load();
    }
		
    //批量删除
    function delOper(){
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
          	return ;
        }
        var submit = function (v, h, f) {
            if (v == 'ok')
            	$.ajax({
					type: "POST", 
					url: "<c:url value='/Base/Log/deleteBaseOptLogByJq.shtml'/>", 
					async:false,
					data:{'querymap.idStr':newid},
					error:function(data){
					    parent.$.jBox.tip("与数据库连接失败！", 'info');
					},
					success:function(data) {
						if(data == '1'){
	            			parent.$.jBox.tip("操作成功！", 'info');
                            //刷新
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
    
    //详细页
    function optWin(id){
    	var url = "<c:url value='/Base/Log/listbaseoptdesc.shtml'/>?querymap.id="+id;
    	var msg ="操作日志-详细"; 
    	 parent.$.jBox.open("iframe:"+url, msg, 750, 500, { buttons: {'关闭':false},
	        submit: function (v, h, f) {
	            if(v == 0){
	               return true;  //close the window
	               mm.load();
	            }
	            return false;
	        }
	    });
    }
</script>
</body>
</html>