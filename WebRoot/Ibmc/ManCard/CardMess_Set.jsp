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
	<script src="<c:url value='/My97DatePicker/WdatePicker.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid-pagi.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/plugins.js'/>"></script>
    <script src="<c:url value='/Ztree/js/jquery.ztree.core-3.5.js'/>"></script>
    <style type="text/css">
        #ztree {
            width: 18%;
            float: left;
            height: 400px;
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
        <div class="titleClass">短信通知引入</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
	    <form method="post" name="form1" id="form1" action="">
	     类别：<select name="querymap.cardtype" id="b_cardtype">
            <option value="">全部</option>
	    	<c:forEach items="${cardTypeMap}" var="k">
            	<option value="${k.key}" ${querymap.type==k.key?'selected':''}>${k.value}</option>
            </c:forEach>
        </select>
		     关键字：<select name="fields" id="b_fields">
	            <option value="cardno" ${fields=='cardno'?'selected':''}>卡ID</option>
	            <option value="ownername" ${fields=='ownername'?'selected':''}>业主姓名</option>
	            <option value="rentname" ${fields=='rentname'?'selected':''}>租客姓名</option>
	        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value="${keyword}"/>
            <input type="hidden" name="querymap.commpath" id="commpath" value=""/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
            
            <input type="hidden" name="tokenid" value="${tokenid}" />
			<input type="hidden" name="querymap.userid" value="${querymap.userid}"/>
			<input type="hidden" name="querymap.needFilterScope" value="${querymap.needFilterScope}"/>
			<input type="hidden" name="querymap.maxLev" value="${querymap.maxLev}"/>
			<input type="hidden" name="querymap.phone" value="" id="phone"/>
			<input type="hidden" name="querymap.enddate" value="" id="enddate"/>
			<input type="hidden" name="querymap.cardidstr" value="" id="cardidstr"/>
		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup"></div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <div class="clear"/>
    <div style="text-align:right;padding-right:20px;">
        通知号码：<input type="text" id="b_phone" class="text_12"/>&nbsp;&nbsp;&nbsp;
        通知截止时间：<input type="text" id="b_enddate" value="" class="text_20 Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
        <input type="button" id="saveBtn" class="btn btn-info" value="确定"/>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<script type="text/javascript">
	//列
    var cols = [
    	{ title: '类别', name: 'cardtype', width: 60, align: 'center', renderer: function (val, item, rowIndex) {
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
        { title: '卡ID', name: 'cardno', width: 160, align: 'center', renderer:null},
        { title: '房产地址', name: 'housename', width: 160, align: 'left', renderer:null},
        { title: '房间名称', name: 'roomname', width: 100, align: 'left', renderer:null},
        { title: '业主', name: 'ownername', width: 90, align: 'center'},
        { title: '租户', name: 'rentname', width: 90, align: 'center', renderer:null}
    ];
    
	var url = '<c:url value='/Man/Card/findManCardListInMessImpByCommIdByJq.shtml'/>';
	//列表数据加载
    var mmg = $('#dataTable').mmGrid({
        indexColWidth: 35,
        height: '350',
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
        //类别
        $("#b_cardtype").bind("change",function(){
	       	$("#commpath").val("");
        	mmg.load();
        });
        
        //搜索
        $("#search").bind("click",function(){
	       	$("#commpath").val("");
        	mmg.load();
        });
        
        //保存
        $("#saveBtn").bind("click",function(){
        	callBack();
        });
        
    });
    
    //详细信息
    function showData(){
    	operRecord();
    }
    
    function operRecord(){
   		var selectedId = getSelectedRecordId();
   		if(selectedId == "") return ;
    	var url = "<c:url value='/Man/Card/setmancardrecorddesc.shtml'/>?manCardRecordBean.id="+selectedId;
   		//详细信息
   		parent.$.jBox.open("iframe:"+url, '刷卡详细', 800 , 380, { buttons: {}});
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
    
    function callBack() {
    	var cardidStr = getSelectedRecordId();
    	if(cardidStr =='')return;
    	var phone = $("#b_phone").val();
    	var enddate = $("#b_enddate").val();
    	if(phone==''||enddate==''){
            parent.$.jBox.tip("请输入您需要通知的手机号码或填入通知截止时间！", 'warning');
            return;
    	}
    	$("#phone").val(phone);
    	$("#enddate").val(enddate);
    	$("#cardidstr").val(cardidStr);
		$("#form1").attr("action",'<c:url value='/Man/Card/savemancardmessimp.shtml'/>').trigger("submit");
		return false;
	}
		
</script>
</body>
</html>