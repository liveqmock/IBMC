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
	<script src="<c:url value='/My97DatePicker/WdatePicker.js'/>"></script>
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
        <div class="titleClass">短信发送记录查询</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
	    <form method="post" name="form1" id="form1" action="">                    
	    	 刷卡时间：<input type="text" name="querymap.startdate" id="startdate" value="${querymap.startdate}" class="text_12 Wdate" onClick="WdatePicker()"/>至
	    	 <input type="text" name="querymap.enddate" id="enddate" value="${querymap.enddate}" class="text_12 Wdate" onClick="WdatePicker()"/>
	            关键字：<select name="fields" id="b_fields">
	            <option value="phone" ${fields=='phone'?'selected':''}>短信通知号码</option>
	            <option value="ownername" ${fields=='ownername'?'selected':''}>业主姓名</option>
	            <option value="owneridcard" ${fields=='owneridcard'?'selected':''}>业主身份证号</option>
	            <option value="rentname" ${fields=='rentname'?'selected':''}>租客姓名</option>
	            <option value="rentidcard" ${fields=='rentidcard'?'selected':''}>租户身份证号</option>
	        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value="${keyword}"/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
            <input type="hidden" name="querymap.optuserid" value="${querymap.optuserid}" />
            
			<input type="hidden" name="querymap.needFilterScope" value="${querymap.needFilterScope}"/>
			<input type="hidden" name="querymap.maxLev" value="${querymap.maxLev}"/>
            <input type="hidden" name="querymap.commpath" id="commpath" value=""/>
		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="cardimp">引入</button>
            <button class="btn btn-info" id="insert" onClick="showData();">刷卡短信详细</button>
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
    	{ title: '类别', name: 'cardtype', width: 40, align: 'center', renderer:function(val,item,rowIndex){
            var tempval = '';
            if(val=='${CARDTYPE_OWNER}'){
            	tempval = '业主卡'
            }else if(val=='${CARDTYPE_TEMP}'){
            	tempval = '临时卡'
            }else if(val=='${CARDTYPE_REGULAR}'){
            	tempval = '正式卡'
            }
            return tempval;
        }},
        { title: '卡ID', name: 'cardno', width: 140, align: 'center', renderer:null},
        { title: '房产地址', name: 'housename', width: 120, align: 'left', renderer:null},
        { title: '房间名称', name: 'roomname', width: 120, align: 'left', renderer:null},
        { title: '租户', name: 'rentname', width: 80, align: 'center', renderer:null},
        { title: '业主', name: 'ownername', width: 80, align: 'center'},
        { title: '通知号码', name: 'phone', width: 120, align: 'center', renderer: null}
    ];
    
	var url = '<c:url value='/Man/Card/findManCardLinkNoticeMessListByJq.shtml'/>';
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
        
        //搜索
        $("#search").bind("click",function(){
	       	$("#commpath").val("");    //设置清除区域关联查询
        	mmg.load();
        });
        
        //门卡引入
        $("#cardimp").bind("click",function(){
	    	var url = "<c:url value='/Man/Card/setmancardinmessimp.shtml'/>";
	   		//详细
	   		parent.$.jBox.open("iframe:"+url, '门卡引入', 960 , 520, { buttons: {}});
        });
        
    });
    
    function showData(){
   		var selectedId = getSelectedRecordId();
   		if(selectedId == "") return ;
    	var url = "<c:url value='/Man/Card/setmancardlinknoticemess.shtml'/>";
    	url += "?querymap.traceid="+selectedId;
   		//详细
   		parent.$.jBox.open("iframe:"+url, '短信通知-详细', 800 , 450, { buttons: {}});
    }

    //检测是否选择记录
    function getSelectedRecordId(){
        var rows = mmg.selectedRows();
        var selecedId = "";
        for(var i=0;i<rows.length;i++){
        	selecedId = (rows[i].traceid);
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