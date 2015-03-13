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
            width:18%; float:left; height:530px;margin:0px; padding:0px; border:none;
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
</div>
<div id="container" style="width:82%; float:left;">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">正式卡管理</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
        <form method="post" name="form" id="form1" action="">
            关键字：<select name="fields">
            <option value="ownername">业主姓名</option>
            <option value="owneridcard">业主身份证号</option>
            <option value="commname">房产地址</option>
            <option value="commdetail">房产详细</option>
        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value=""/>
            <input type="hidden" name="cardtype" id="q_cardtype" value="${cardtype }"/>
            <input type="hidden" name="querymap.commpath" id="q_commpath" value=""/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
        </form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="insert" onClick="setManCard();">正式卡管理</button>
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
</div>
<script type="text/javascript">
    //列
    var cols = [
        { title: '社区名称', name: 'parentname', width: 210, align: 'left'},
        { title: '房屋地址', name: 'commname', width: 240, align: 'left'},
        { title: '业主', name: 'ownername', width: 60, align: 'center'},
        { title: '业主卡', name: 'mcardnum', width: 60, align: 'center', renderer: function (val, item, rowIndex) {
            return val + "张";
        }},
        { title: '正式卡', name: 'rcardnum', width: 60, align: 'center', renderer: function (val, item, rowIndex) {
            return val + "张";
        }},
        { title: '临时卡', name: 'tcardnum', width: 60, align: 'center', renderer: function (val, item, rowIndex) {
            return val + "张";
        }}
    ];
    //列表数据加载
    var mmg = $('#dataTable').mmGrid({
        indexCol:true,
        checkCol:true,
        height: '420',
        cols: cols,
        url: '<c:url value="/Man/Card/listManCardsByJq.shtml"/>',
        method: 'post',
        nowarp: true,
        root: 'items',
        plugins: [
            $('#dataPagi').mmPaginator()
        ]
    });

    $(function () {
        initTree();
        //搜索
        $("#search").bind("click",function(){
	       	$("#q_commpath").val("");
        	mmg.load();
        });
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
             data:{'querymap.needFilterScope':'1','querymap.maxLev':'3'},
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

    //门卡管理
    function setManCard() {
    	//获取选择的记录
    	var idArr = getSelectedRecordIdArr();
   		if(idArr.length == 0) return ;
	    var selectedId = idArr.join(",");
	    var url = "<c:url value="/Man/Card/regularcardwin.shtml"/>?querymap.houseid="+selectedId;
        parent.$.jBox.open("iframe:"+url, "正式卡管理", 800, 480, { buttons: {}});
    }
    
    //检测是否选择记录
    function getSelectedRecordIdArr(){
    	//若新增，则跳出
        var rows = mmg.selectedRows();
		var idAttr=new Array();
        if(rows.length==0){
            parent.$.jBox.tip("请选择要操作的记录！", 'warning');
            return idAttr;
        }
        else if(rows.length>1){
            parent.$.jBox.tip("只能选择一条记录！", 'warning');
            return idAttr;
        }
        else{
        	idAttr.push(rows[0].id);
            return idAttr;
        }
        return idAttr;
    }
</script>
</body>
</html>