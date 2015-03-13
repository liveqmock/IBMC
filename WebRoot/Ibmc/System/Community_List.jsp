<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
        #ztree{
            width:18%; float:left; height:475px;margin:0px; padding:0px; border:none;
            background:#FFFFFF url('<c:url value="/Images/organ-bg.jpg"/>') right repeat-y;
        }
        .top-nav-bg{background:#f9f9f9 url('<c:url value="/Images/organ-top-bg.jpg"/>') top left repeat-x; height:40px; width:99%;}
        #treeDemo{ height:90%;overflow:auto; background:none;overflow-y:auto; width:93%;}
        .top-nav-bg span{ font: bold 12px/32px Arial, sans-serif; margin-left:30%; }
    </style>
</head>
<body>
<div id="ztree">
    <div class="top-nav-bg"><span>所属区域</span></div>
    <ul id="treeDemo" class="ztree"></ul>
    <div class="top-nav-bg"><a href="JavaScript:setScope();" style="color:red;"><span>编辑区域</span></a></div>
</div>
<div id="container" style="width:82%; float:left;">
<!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">社区管理</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
	    <form method="post" name="form" id="form1" action="">
        <table>
            <tr>
                <td>
                                                      关键字： <select name="fields" id="b_fields"><option value="commname">社区名称</option></select>
            		<input type="hidden" name="querymap.commpath" id="q_commpath" value=""/>
                    <input type="text" name="keyword" id="b_keyword" class="text_15" value=""/>
            		<input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
                </td>
            </tr>
        </table>
        </form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="insert">新增社区</button>&nbsp;|&nbsp;
            <button class="btn btn-info" id="update">修改社区</button>&nbsp;|&nbsp;
            <button class="btn btn-danger" id="delete">删除社区</button>
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
</body>
<script language="javascript">     
    //结果集合
    var cols = [
        { title:'所属区域', name:'parentname' ,width:220, align:'left', renderer: null},
        { title:'社区简称', name:'commname' ,width:220, align:'left', renderer: function(val,item,rowIndex){
            if(item.USESIGN == "0") {
                return "[<font color='red'>禁用</font>]"+val;
            }else{
                return val;
            }
        }},
        { title:'社区详细信息', name:'commdetail' ,width:200, align:'left'},
        { title:'邮编', name:'commzip' ,width:60, align:'center', renderer: null},
        { title:'备注', name:'remark' ,width:60, align:'center',renderer: function(val,item,rowIndex){
            if(''!=val){
            	return '<span class="onShow" title="'+val+'"></span>';
            }else{
            	return "&nbsp;";
            }
        }}
    ];
    //分页
    var mmg = $('#dataTable').mmGrid({
        checkCol:true,
        indexCol:true,
        height:'420',
        cols: cols,
        url: '<c:url value="/Sys/Comm/listVillageByJq.shtml"/>',
        method: 'post',
        root: 'items',
        plugins : [
            $('#dataPagi').mmPaginator()
        ]
    });
	//构造树状条件
     var setting = {
         data: {
             simpleData: {
                 enable: true
             }
         },
	     callback: {
	       onClick:function(e, id, node){
	       		$("#q_commpath").val(node.commpath);
	            mmg.load();
	       }
	     }
     };
     
     function initTree() {
         $.ajax({
             url: '<c:url value="/Sys/Comm/listRegionByJq.shtml"/>',
             data:{'querymap.needFilterScope':'1','querymap.maxLev':'2'},
             type: 'post',
             dataType: 'json',
             error: function () {
                 alert('Error loading XML document');
             },
             success: function (data) {
     			 var zNodes =[];
                 for(var i=0;i<data.length;i++){
                     zNodes[i]=({id:data[i].id, pId:data[i].parentid,name:data[i].commname,commpath:data[i].commpath,open:false});
                 }
                 $.fn.zTree.init($("#treeDemo"), setting, zNodes);
             }
         });
     }


    $(function(){
        initTree();
         
        $("#insert").bind("click",insertOper);
        $("#update").bind("click",updateOper);
        $("#delete").bind("click",deleteOper);
        //搜索
        $("#search").bind("click",function(){
	       	$("#q_commpath").val("");
        	mmg.load();
        });
    });
    
    //新增
    function insertOper(){
    	operRecord(1);
    }
    
    function updateOper(){
    	operRecord(2);
    }

    //删除
    function deleteOper(){
    	operRecord(3);
    }
    
    function operRecord(flag){
    	var url = "<c:url value='/Sys/Comm/setsyscommunity_vill.shtml'/>?sysCommunityBean.commlev=3";
    	if(flag=="1"){
    		//新增
    		parent.$.jBox.open("iframe:"+url, '社区管理-新增', 800 , 400, { buttons: {}});
    	}else if(flag=="2"){
    		//修改
    		var selectedId = getSelectedRecordId();
    		if(selectedId == "") return ;
    		url += "&sysCommunityBean.id="+selectedId;
    		parent.$.jBox.open("iframe:"+url, '社区管理-修改', 800 , 400, { buttons: {}});
    		
    	}else if(flag=="3"){
    		//删除
    		var selectedId = getSelectedRecordId();
    		if(selectedId == "") return ;
	        var submit = function (v, h, f) {
	            if (v == 'ok'){
	            	$.ajax({
						type: "POST", 
						url:"<c:url value='/Sys/Comm/deleteVillageByJq.shtml'/>", //代码数据
						async:false,
						data:{'sysCommunityBean.id':selectedId},
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
	        parent.$.jBox.confirm("确定删除该记录？", "提示", submit);
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
    function reloadPage(){
	    mmg.load();
	}
</script>
</html>
