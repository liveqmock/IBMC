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
	    <form method="post" name="form" id="form1" action="">
		     关键字：<select name="fields" id="b_fields">
	            <option value="name" ${fields=='name'?'selected':''}>门口机名称</option>
	            <option value="doormac" ${fields=='doormac'?'selected':''}>门口机MAC</option>
	            <option value="doorip" ${fields=='doorip'?'selected':''}>门口机IP</option>
	        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value="${keyword}"/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
            <input type="hidden" name="querymap.optuserid" value="${querymap.optuserid}" />
            <input type="checkbox" value="1" name="querymap.showsign" id="showsign" ${querymap.showsign=='1'?'checked':''} />仅显示未绑定房屋的门口机
		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" style="display:none;"  id="btn_imp">导入门口机</button>
            <button class="btn btn-info" id="btn_insert">新增</button>
            <button class="btn btn-info" id="btn_exp" style="display:none;">导出数据</button>
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <div class="btnGroup" >
    <!-- 
       	<select name="command" id="command">
       		<option value="">==请选择==</option>
        	<c:forEach items="${getEquipCommandMap}" var="k">
       			<option value="${k.key}">${k.value}</option>
        	</c:forEach>
       	</select>
        <button class="btn btn-danger" id="b_run">执行</button>
     -->
    </div>
    <div class="clear"/>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<script type="text/javascript">
	//列
    var cols = [
        { title: '序号', name: '', width: 40, lockWidth: true, align: 'center', rowspan: 2, renderer: function (val, item, rowIndex) {
            return rowIndex + 1 + '<input type="hidden" name="pkid" value=' + item.id + ' />';
        }},
        { title: '名称', name: 'name', width: 320, align: 'left', renderer: null},
        { title: 'IP', name: 'doorip', width: 140, align: 'center', renderer: null},
        { title: 'MAC', name: 'doormac', width: 180, align: 'center', renderer:null},
        { title: '操作员', name: 'optusername', width: 80, align: 'center', renderer:null},
        { title:'备注', name:'remark' ,width:40, align:'center',renderer: function(val,item,rowIndex){
            return val!=''? '<span class="onShow" title="'+val+'"></span>':'&nbsp;';
        }},
        { title: '操作', name: '', width: 100, align: 'center', renderer: function (val, item, rowIndex) {
            return "<button class=\"btn btn-info\" onClick=\"setData('" + item.id + "');\">修改</button>"+
                    "<button class=\"btn btn-danger\" onClick=\"delOper('" + item.id + "');\">删除</button>";
        }}
    ];
    
	var url = '<c:url value='/Man/Door/findManDoorListByJq.shtml'/>';
	//列表数据加载
    var mmg = $('#dataTable').mmGrid({
        indexColWidth: 35,
        height: '380',
        cols: cols,
        url: url,
		checkCol:true,
        nowarp: true,
        method: 'post',
        root: 'items',
        plugins: [
            $('#dataPagi').mmPaginator()
        ]
    });
    
    //删除
    function delOper(id) {
    	var newid = id;
        if(newid == '') {
			parent.$.jBox.tip("请选中您所要操作的数据！", 'info');
            return;
        }
        //查询该门口机是否有关联房产
		$.ajax({
			type: "POST", 
			url: "<c:url value='/Man/Door/findDoorLinkHouseCountByJq.shtml'/>", //代码数据
			async:false,   //同步
			data:{'manDoorHouseBean.doorid':newid},
			error:function(data){
			    parent.$.jBox.tip("与数据库连接失败！", 'info');
			},
			success:function(data) {
				if(data=="-1"){
					parent.$.jBox.tip("操作失败！", 'info');
				}else{
					if(parseInt(data) >= 1){
						parent.$.jBox.tip("该门口机有关联房产信息请先解除关联后在删除门口机记录！", 'info');
						return;
					}else{
						var submit = function (v, h, f) {
				            if (v == 'ok')
				            	$.ajax({
									type: "POST", 
									url:"<c:url value='/Man/Door/delManDoorByJq.shtml'/>", //代码数据
									async:false,
									data:{'manDoorBean.id':newid},
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
				        parent.$.jBox.confirm("您确定删除该记录？", "提示", submit);
					}
				}
				
			}
		});
    }
    
    $(function () {
    	//仅显示
    	$("#showsign").bind("click",function(){
        	mmg.load();
    	});
    	
        //删除
        $("#btn_batchdel").bind("click", function () {
            delOper('');
        });
        
        //新增
        $("#btn_insert").bind("click",function(){setData('');});
        
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
        
        //导入门口机
        $("#btn_imp").bind("click",function(){
        	$("#form1").attr("action",'<c:url value='/Man/Door/setmandoorimp.shtml'/>').trigger("submit");
        })
        
        //导出数据
        $("#btn_exp").bind("click",function(){
        	$("#form1").attr("action",'<c:url value='/Man/Door/savemandoorexpexcel.shtml'/>').trigger("submit");
        })
        
        //设备命令
        $("#b_run").bind("click",function(){
        	var commandsign = $("#command").val();
        	if(commandsign == ''){
		    	parent.$.jBox.tip("请选择需要操作的命令！", 'info');
        	}else{
        		equcommand(commandsign);
        	}
        });
        
    });
    
    //设备命令
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
   		//设备命令为恢复出厂设置[提醒提示]
   		if(commandSign == '${EQUIP_RESETUNLOCK}'){
   			//查询该门口机是否有关联房产
			$.ajax({
				type: "POST", 
				url:"<c:url value='/Man/Door/findDoorLinkHouseCountByJq.shtml'/>", //代码数据
				async:false,   //同步
				data:{'manDoorHouseBean.doorid':newid},
				error:function(data){
				    parent.$.jBox.tip("与数据库连接失败！", 'info');
				},
				success:function(data) {
					if(data=="-1"){
						parent.$.jBox.tip("操作失败！", 'info');
					}else{
						if(parseInt(data) >= 1){
							var submit = function (v, h, f) {
					            if (v == 'ok')
   									doorcommand(equipId,commandSign);   //设备命令操作接口
					            return true; //close
					        };
					        parent.$.jBox.confirm("该门口机有关联房产信息恢复出厂设置后，会丢失所有数据是否恢复？", "提示", submit);
						}
					}
				}
			});
   		}else{
   			doorcommand(equipId,commandSign);    //设备命令操作接口
   		}
    }
    
    //设备命令操作接口
    function doorcommand(equipId,commandSign){
    	var url = '<c:url value='/Man/Door/setManDoorCommandByJq.shtml'/>';
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
    
    //新增,修改弹出页面
    function setData(id){
    	var url = "<c:url value='/Man/Door/setmandoor.shtml'/>";
    	url += "?manDoorBean.id="+id;
    	var msg = '门口机管理-新增';
    	if(id!=''){
    		msg = '门口机管理-修改';
    	}
        parent.$.jBox.open("iframe:"+url, msg, 860 , 480, { buttons: {}});
    }
    
    //检测是否选择记录
    function getSelectedRecordId(){
        var rows = mmg.selectedRows();
        var selecedId = "";
        for(var i=0;i<rows.length;i++){
        	selecedId = (rows[i].id);
        }
        if(selecedId==""){
            parent.$.jBox.tip("请选择要操作的记录！", 'warning');
            return "";
        }
        return selecedId;
    }
    
    function reloadPage(){
	    mmg.load();
	}
	
</script>
</body>
</html>