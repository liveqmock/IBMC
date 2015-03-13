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
        <div class="titleClass">厂商管理</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
	    <form method="post" name="form" id="form1" action="">
		     关键字：<select name="fields" id="b_fields">
	            <option value="facname" ${fields=='facname'?'selected':''}>厂商名称</option>
	            <option value="address" ${fields=='address'?'selected':''}>厂商地址</option>
	        </select>
            <input type="text" name="keyword" id="searchkey" class="text_15" value="${keyword}"/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
		</form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="btn_insert">新增</button>&nbsp;|&nbsp;
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
   var cols = [
        { title: '序号', name: '', width: 40, lockWidth: true, align: 'center', rowspan: 2, renderer: function (val, item, rowIndex) {
            return rowIndex + 1 + '<input type="hidden" name="pkid" value=' + item.id + ' />';
        }},
        { title: '厂商名称', name: 'facname', width: 220, align: 'left', renderer: null},
        { title: '厂商联系人', name: 'contact', width: 120, align: 'center', renderer: null},
        { title: '厂商联系人电话', name: 'telephone', width: 120, align: 'center', renderer:null},
        { title: '厂商住址', name: 'address', width: 280, align: 'left', renderer: null},
        { title:'备注', name:'remark' ,width:40, align:'center',renderer: function(val,item,rowIndex){
            return val!=''? '<span class="onShow" title="'+val+'"></span>':'';
        }},
        { title: '操作', name: '', width: 100, align: 'center', renderer: function (val, item, rowIndex) {
            return "<button class=\"btn btn-info\" onClick=\"setData('" + item.id + "');\">修改</button>&nbsp;"+
                    "<button class=\"btn btn-danger\" onClick=\"delOper('" + item.id + "');\">删除</button>";
        }}
    ];
    
	var url = "<c:url value='/Man/Dev/listManFactoryByJq.shtml'/>";
	//列表数据加载
    var mmg = $('#dataTable').mmGrid({
        indexColWidth: 35,
        height: '420',
        cols: cols,
        url: url,
        method: 'post',
        checkCol: true,
        multiSelect: true,
        nowarp: true,
        root: 'items',
        plugins: [
            $('#dataPagi').mmPaginator()
        ]
    });
    
    $(function () {
        //新增
        $("#btn_insert").bind("click",function(){setData('');});
         //删除
        $("#b_batchdel").bind("click", function () {
            delOper('')
        });
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
    });
    
    //新增,修改弹出页面
    function setData(id){
    	var url = "<c:url value='/Man/Dev/setmanfactory.shtml'/>";
    	url += "?manFactoryBean.id="+id;
    	var msg = '厂商管理-新增';
    	if(id!=''){
    		msg = '厂商管理-修改';
    	}
        parent.$.jBox.open("iframe:"+url, msg, 560 , 420, { buttons: {}});
    }
    
    function reloadPage(){
	    mmg.load();
	}
	
	  //批量删除
	  function delOper(id){
        var newid = id||'';
        if(newid==''){
			var idAttr=new Array();
			$.each($("#dataTable input[type='checkbox']"),function(i,n){
				if(n.checked){
					var obj = $(this).parent().parent().parent();
					$("td:eq(1) input[name='pkid']",obj).val();
					idAttr.push($("td:eq(1) input[name='pkid']",obj).val());
				}
			});
			newid = idAttr.join(",");
		}
		if(newid == ''){
        	parent.$.jBox.tip("请选中您所要操作的数据！", 'info');
          	return ;
        }
        var submit = function (v, h, f) {
            if (v == 'ok')
            	$.ajax({
					type: "POST", 
					url: "<c:url value='/Man/Dev/deleteManFactoryByJq.shtml'/>", 
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
    
   
</script>
</body>
</html>