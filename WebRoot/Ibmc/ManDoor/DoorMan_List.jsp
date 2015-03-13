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
    <link rel="stylesheet" href="<c:url value='/Ztree/css/zTreeStyle/zTreeStyle.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid-pagi.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/plugins.js'/>"></script>
    <script src="<c:url value='/Ztree/js/jquery.ztree.core-3.5.js'/>"></script>
    <style type="text/css">
        #ztree {
            width: 18%;
            float: left;
            height: 530px;
            margin: 0px;
            padding: 0px;
            border: none;
            background: #FFFFFF url(<c:url value='/Images/organ-bg.jpg'/>) right repeat-y;
        }
        .top-nav-bg {
            background: #f9f9f9 url(<c:url value='/Images/organ-top-bg.jpg'/>) top left repeat-x;
            height: 40px;
            width: 99%;
        }
 
        #treeDemo {
            height: 90%;
            overflow: auto;
            background: none;
            overflow-y: auto;
            width: 93%;
        }
 
        .top-nav-bg span {
            font: bold 12px/32px Arial, sans-serif;
            margin-left: 30%;
        }
    </style>
</head>
<body>
<div id="ztree">
    <div class="top-nav-bg"><span>所属区域</span></div>
    <ul id="treeDemo" class="ztree"></ul>
</div>
<div id="container" style="width:82%; float:left;">
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
		            <option value="us.username">业主姓名</option>
		            <option value="us.userid">业主身份证号</option>
		            <option value="commname" ${fields=='commname'?'selected':''}>房产地址</option>
		            <option value="commdetail" ${fields=='commdetail'?'selected':''}>房产详细</option>
	        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value="${keyword}"/>
            <input type="hidden" name="querymap.commpath" id="commpath" value=""/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="insert">关联门口机</button>&nbsp;|&nbsp;
        	<button class="btn btn-danger" id="remove">解除关联</button>
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <div class="clear"/>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<script type="text/javascript">
	//列
    var cols = [
        { title: '社区名称', name: 'parentname', width: 180, align: 'left'},
        { title: '房屋地址', name: 'commname', width: 260, align: 'left'},
        { title: '业主', name: 'ownername', width: 80, align: 'center'},
        { title: '门口机', name: 'doorcount', width: 60, align: 'center', renderer: function (val, item, rowIndex) {
            return val;
        }},
        { title: '状态', name: 'status', width: 120, align: 'center', renderer:function(val,item,rowIndex){
            //查询在线状态
            return "<span id=\"status_"+rowIndex+"\"></span>"+getDevStatus(rowIndex,item.id);
            
        }},
        { title: '备注', name: 'remark', width: 30, align: 'center', renderer: function (val, item, rowIndex) {
            return (val!="")?"<span class='onShow' title='"+val+"'></span> ":"&nbsp;";
        }}
    ];
    
	var url = '<c:url value='/Man/Door/findManDoorHouseLinkListByJq.shtml'/>';
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
    
	//设置checkbox属性
	var setting = {
	    data: {
	        simpleData: {
	            enable: true
	        }
	    },
	    callback: {
			onClick:function(e, treeId, treeNode){
				$("#commpath").val(treeNode.commpath);
	            mmg.load();
			}
		}
	};
	
	//只有末端时才能显示checkbox控件
	function initTree() {
		var zNodes = [];
	   	var ztreeurl = "<c:url value='/Sys/Comm/listRegionByJq.shtml'/>";
	   	var queryparam = {'querymap.needFilterScope':'${querymap.needFilterScope}','querymap.maxLev':'${querymap.maxLev}'};
	    $.ajax({
	        url: ztreeurl,
	        data: queryparam,
	        type: 'post',
	        dataType: 'json',
	        error: function () {
				parent.$.jBox.tip("与数据库连失败！请刷新后重试！", 'warning');
	        },
	        success: function (data) {
	            for (var i = 0; i < data.length; i++) {
	                zNodes[i]=({id:data[i].id, pId:data[i].parentid,name:data[i].commname,commpath:data[i].commpath,open:true});
	            }
	            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	        }
	    });
	}
   
    $(function() {
    	//初始化树状
		initTree();
        $("#insert").bind("click",insertOper);
        $("#remove").bind("click",removeOper);
        
        //搜索
        $("#search").bind("click",function(){
	       	$("#commpath").val("");
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
    });
    
    //新增
    function insertOper(){
    	operRecord(2);
    }
    
    //解除关联
    function removeOper(){
    	operRecord(3);
    }
    
    function operRecord(flag){
    	if(flag=="2"){
    		var url = "<c:url value='/Man/Door/setmandoorlinkhouse.shtml'/>";
    		//修改
    		var selectedId = getSelectedRecordId();
    		if(selectedId == "") return ;
    		url += "?manDoorHouseBean.houseid="+selectedId;
    		url += "&querymap.optuserid="+'${querymap.optuserid}';   //门口机数录入谁操作
    		parent.$.jBox.open("iframe:"+url, '关联门口机', 800 , 550, { buttons: {}});
    	}else if(flag=="3"){
    		var url = "<c:url value='/Man/Door/setmandoorcancellinkhouse.shtml'/>";
    		//取消关联
    		var selectedId = getSelectedRecordId();
    		if(selectedId == "") return ;
    		url += "?manDoorHouseBean.houseid="+selectedId;
    		parent.$.jBox.open("iframe:"+url, '取消关联门口机', 800 , 450, { buttons: {}});
    	}
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
    
    //获取设备状态
    function getDevStatus(rowIndex,houseId){
	    $.ajax({
			type: "POST", 
			async:true,
			url:"<c:url value='/Man/Door/findManDoorStatusByHouseId.shtml'/>", //代码数据
			data:{'houseId':houseId},
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
    
    
    //刷新父页面重新加载
    function reloadPage(){
		mmg.load();
	}
	
</script>
</body>
</html>