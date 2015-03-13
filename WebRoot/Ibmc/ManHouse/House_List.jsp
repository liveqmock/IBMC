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
        <div class="titleClass">房产信息维护</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
	    <form method="post" name="form" id="form1" action="">
		     关键字：<select name="fields" id="b_fields">
	            <option value="ownername" ${fields=='ownername'?'selected':''}>业主姓名</option>
	            <option value="commname" ${fields=='commname'?'selected':''}>房产地址</option>
	        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value="${keyword}"/>
            <input type="hidden" name="querymap.commpath" id="commpath" value=""/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
            
			<input type="hidden" name="querymap.needFilterScope" value="${querymap.needFilterScope}"/>
			<input type="hidden" name="querymap.maxLev" value="${querymap.maxLev}"/>
		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="insert">新增房产</button>&nbsp;|&nbsp;
            <button class="btn btn-info" id="update">修改房产</button>&nbsp;|&nbsp;
            <button class="btn btn-danger" id="delete">删除房产</button>&nbsp;|&nbsp;
            <button class="btn btn-info" id="btn_export">导出房产列表</button>&nbsp;|&nbsp;
            <button class="btn btn-danger" id="detail">房间详细</button>
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
        { title: '社区名称', name: 'parentname', width: 200, align: 'left'},
        { title: '房屋地址', name: 'commname', width: 360, align: 'left'},
        { title: '业主', name: 'ownername', width: 120, align: 'center'},
        { title: '房间数', name: 'childnum', width: 80, align: 'center', renderer: function (val, item, rowIndex) {
            return val+"间";
        }},
        { title: '备注', name: 'remark', width: 40, align: 'center', renderer: function (val, item, rowIndex) {
            return (val!="")?"<span class='onShow' title='"+val+"'></span> ":"&nbsp;";
        }}
    ];
    
	var url = '<c:url value='/Man/House/findManHouseListByJq.shtml'/>';
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
   
    $(function () {
    	//初始化树状
		initTree();
        $("#insert").bind("click",insertOper);
        $("#update").bind("click",updateOper);
        $("#delete").bind("click",deleteOper);
        $("#btn_export").bind("click",exportOper);
        $("#detail").bind("click",detailRoom);
        
        //搜索
        $("#search").bind("click",function(){
	       	$("#commpath").val("");
        	mmg.load();
        });
    });
    
    //新增
    function insertOper(){
    	operRecord(1);
    }
    
    //更新
    function updateOper(){
    	operRecord(2);
    }

    //删除
    function deleteOper(){
    	operRecord(3);
    }
    
    //导出excel
    function exportOper(){
    	$("#form1").attr("action",'<c:url value='/Man/House/savemanhouseexpexcel.shtml'/>').trigger("submit");
    }
    
    //显示房间信息
    function detailRoom(){
    	operRecord(9);
    }
    
    function operRecord(flag){
    	var url = "<c:url value='/Man/House/setmanhouse.shtml'/>?sysCommunityBean.commlev=4";
    	url += "&querymap.needFilterScope=${querymap.needFilterScope}";
    	url += "&querymap.maxLev=${querymap.maxLev}";
    	if(flag=="1"){
    		//新增
    		parent.$.jBox.open("iframe:"+url, '房产信息-新增', 800 , 450, { buttons: {}});
    	}else if(flag=="2"){
    		//修改
    		var selectedId = getSelectedRecordId();
    		if(selectedId == "") return ;
    		url += "&sysCommunityBean.id="+selectedId;
    		parent.$.jBox.open("iframe:"+url, '房产信息-修改', 800 , 450, { buttons: {}});
    	}else if(flag=="3"){      //删除
    		//验证查询该房产是否有关联门口机，门卡
    		var selectedId = getSelectedRecordId();
    		if(selectedId == "") return ;
			$.ajax({
				type: "POST", 
				url:"<c:url value='/Man/House/findHouseLinkDoorAndCardCountByJq.shtml'/>", //代码数据
				async:false,   //同步
				data:{'houseId':selectedId},
				error:function(data){
				    parent.$.jBox.tip("与数据库连接失败！", 'info');
				},
				success:function(data) {
					if(data=="-1"){
						parent.$.jBox.tip("操作失败！", 'info');
					}else{
						if(parseInt(data) >= 1){
							parent.$.jBox.tip("该房产有关联门口机，或房产下面有门卡，请先删除门口机关联或门卡后，再进行删除操作！", 'info');
							return;
						}else{
					        var submit = function (v, h, f) {
					            if (v == 'ok'){
					            	$.ajax({
										type: "POST", 
										url:"<c:url value='/Man/House/delManHouseByJq.shtml'/>", //代码数据
										async:false,
										data:{'sysCommunityBean.id':selectedId,'querymap.dellev':'${querymap.houselev}'},  //标识房产删除
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
					            }
					            return true;
					        };
					        parent.$.jBox.confirm("您确定删除该房产记录？", "提示", submit);
						}
					}
				}
			});
    	}else if(flag=="9"){
    		//显示房间信息
    		var selectedId = getSelectedRecordId();
    		if(selectedId == "") return ;
    		url = "<c:url value='/Man/House/setmanhouseromm.shtml'/>";
    		url += "?sysCommunityBean.id="+selectedId;
    		parent.$.jBox.open("iframe:"+url, '房间信息列表', 660 , 450, { buttons: {}});
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
    
    //刷新父页面重新加载
    function reloadPage(){
		mmg.load();
	}
	
</script>
</body>
</html>