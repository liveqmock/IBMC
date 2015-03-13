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
	<script src="<c:url value='/Script/plugin/grid/plugins.js'/>"></script>
</head>
<body>
<!-- 后面新增的样式 -->
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">${sysCommunityBean.commname}--关联门口机列表</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
	    <form method="post" name="form1" id="form1" action="">
	    	 关键字：<select name="fields" id="b_fields">
		            <option value="name" ${fields=='name'?'selected':''}>门口机名称</option>
	        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value="${keyword}"/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
            
			<input type="hidden" name="tokenid" value="${tokenid}"/>
            <input type="hidden" name="manDoorHouseBean.houseid" value="${manDoorHouseBean.houseid}"/>
            <input type="hidden" name="manDoorHouseBean.doorid" value="${manDoorHouseBean.doorid}" id="doorid"/>
		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
        	<select name="command" id="command" style="display:none;">
        		<option value="">请选择</option>
	        	<c:forEach items="${getEquipCommandMap}" var="k">
        			<option value="${k.key}">${k.value}</option>
	        	</c:forEach>
        	</select>
        	<c:forEach items="${getEquipCommandMap}" var="k">
        		<button class="btn btn-info" onclick="equcommand('${k.key}');">${k.value}</button>
        	</c:forEach>
            <button class="btn btn-danger" id="btn_cancellink">取消关联</button>
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
        { title: '名称', name: 'name', width: 180, align: 'left', renderer: null},
        { title: 'IP', name: 'doorip', width: 100, align: 'center', renderer: null},
        { title: 'MAC', name: 'doormac', width: 130, align: 'center', renderer:null},
        { title: '厂商', name: 'facname', width: 150, align: 'left', renderer: null},
        { title: '型号', name: 'facmodel', width: 50, align: 'left', renderer: null},
        { title: '状态', name: 'status', width: 60, align: 'center', renderer:function(val,item,rowIndex){
            //查询在线状态
            return "<span id=\"status_"+rowIndex+"\"></span>"+getDevStatus(rowIndex,item.equipid);
        }},
    ];
    
	var url = '<c:url value='/Man/Door/findManHouseLinkDoorListByJq.shtml'/>';
	//列表数据加载
    var mmg = $('#dataTable').mmGrid({
        indexColWidth: 35,
        height: '300',
        cols: cols,
        url: url,
        multiSelect: false,
		checkCol:true,
        nowarp: true,
        method: 'post'
    });
    
    $(function(){
    	$("#btn_cancellink").bind("click",function(){
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
					$("#form1").attr("action",'<c:url value='/Man/Door/delmandoorhouse.shtml'/>').trigger("submit");
	            }
	            return true;
	        };
        	parent.$.jBox.confirm("执行\"取消关联门口机\"操作，将会导致原房产对应的门卡，无权限打开该门口机，该功能一般用于门口机更换操作，确定执行该操作？", "提示", submit);
    	});
        //搜索
        $("#search").bind("click",function(){
        	mmg.load();
        });
    });
    
    function equcommand(commandSign){
    	var url = '<c:url value='/Man/Door/setManDoorCommandByJq.shtml'/>';
   		var equipId = getSelectedRecordId();
   		if(equipId == ""){
			return ;
   		}else{
   			if(commandSign == ""){
	            parent.$.jBox.tip("参数错误！请刷新页面后重试", 'warning');
				return ;
   			}
   		}

        var submit = function (v, h, f) {
            if (v == 'ok'){
            	$.ajax({
					type: "POST", 
					async: false,  //同步操作
					url: url,     //url请求
					data:{'equipId':equipId,'commandSign':commandSign},
					error:function(data){
					    parent.$.jBox.tip("与数据库连接失败！", 'warning');
					},
					success:function(data) {
						if(data=='-1'){
					    	parent.$.jBox.tip("设备命令操作失败！", 'info');
						}else if(data=="1"){
					    	parent.$.jBox.tip("设备命令操作成功！", 'info');
						}
					}
				});
            }
            return true;
        };
        var tipMsg = "";
        if(commandSign == "1"){
           //设备重置
           tipMsg = "执行\"重置设备\"后，门口机将自动重启，会自动下行部分未下行的门卡列表，确定执行该操作吗？";
        }else if(commandSign == "3"){
           //恢复出厂设置
           tipMsg = "执行\"恢复出厂设置\"后，门口机将会清空门卡列表数据，原先关联的门卡将无权限开该门口机，确定执行该操作？";
        }else{
        	return ;
        }
        parent.$.jBox.confirm(tipMsg, "提示", submit);
    	
    }
    
    //获取设备状态
    function getDevStatus(equipId){
    alert(equipId);
	    $.ajax({
			type: "POST", 
			async:true,
			url:"<c:url value='/Man/Door/findManDoorStatusByEquipId.shtml'/>", //代码数据
			data:{'equipId':equipId},
			error:function(data){
			    parent.$.jBox.tip("与数据库连接失败！", 'info');
			},
			success:function(data) {
				$("#status_"+rowIndex).html(data);
				return "";
			}
		});
		return "";
    }
    
    //检测是否选择记录
    function getSelectedRecordId(){
        var rows = mmg.selectedRows();
        var selecedId = "";
        for(var i=0;i<rows.length;i++){
        	selecedId = (rows[i].equipid);
        }
        if(selecedId==""){
            parent.$.jBox.tip("请选择要操作的记录！", 'warning');
            return "";
        }
        return selecedId;
    }
</script>
</body>
</html>