<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title></title>
<link rel="stylesheet" href="../../Style/main.css"/>
<link rel="stylesheet" href="../../Style/datagrid.css"/>
<link rel="stylesheet" href="../../Style/bootstrap.css"/>
<script type="text/javascript" src="../../Script/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../../Script/plugin/grid/grid.js"></script>
<script type="text/javascript" src="../../Script/plugin/grid/grid-pagi.js"></script>
<script type="text/javascript" src="../../Script/plugin/grid/plugins.js"></script>
<title></title>
</head>
<body>
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">全局控件按钮设置</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="insert">新增 </button> 
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
</body>

<script type="text/javascript">
    //列
    var i=0,j=0,k=0,myIndex;
    var cols = [
		        { title:'按钮显示名称', name:'codename' ,width:80, align:'center',renderer: null},
		        { title:'按钮编码', name:'codevalue' ,width:100, align:'center', renderer: null},
		        { title:'clickEvent', name:'clickevent' ,width:100, align:'center',renderer: null},
		        { title:'按钮样式', name:'styleclass' ,width:100, align:'center', renderer: null},
		        { title:'按钮效果', name:'express' ,width:80, align:'center',renderer: null},   
		        { title:'创建时间', name:'createdate' ,width:140, align:'center',renderer: null},  
		        { title:'修改时间', name:'updatedate' ,width:140, align:'center',renderer: null},  
		        { title:'操作', name:'' ,width:120, align:'left', renderer: function(val,item,rowIndex){
		            var updateStr="&nbsp;<button class=\"btn btn-info\" onClick=\"setCode("+item.id+",'2');\">修改</button>";
					var delStr="&nbsp;<button class=\"btn btn-danger\" onClick=\"delOper("+item.id+");\">删除</button>";
		            return updateStr+delStr;
		        }}
    ];
    //列表数据加载
    var url = '<c:url value='/Base/Menu/listbaseelement.shtml'/>';   
     var mmg = $('#dataTable').mmGrid({
        indexCol:true,
        height:'420',
        cols: cols,
        url: url,
        method: 'get',
        root: 'items'
    });
    
    $(function(){
        $("#insert").bind("click",function(){setCode("","1");});
    });
     
	
	
     //新增/修改（一级跳转）
    function setCode(id,fun){
        var url = '';
        var msg = '';
        if(fun == '1'){
        	msg = '全局控件按钮设置-新增';
			url = "<c:url value='/Base/Menu/setbaseelement.shtml'/>?fun="+fun;
        }else if(fun == '2'){
        	msg = '全局控件按钮设置-修改';
			url = "<c:url value='/Base/Menu/setbaseelement.shtml'/>?fun="+fun+"&baseElementBean.id="+id;
        }
        parent.$.jBox.open("iframe:"+url, msg , 600, 420, { buttons: {},
            submit: function (v, h, f) {
                var we = h.find("#jbox-iframe")[0].contentWindow;
                if (v == 0) {
                    return true; // close the window
                } else {
                      var rtn = we.callBack();
                      if(rtn){
                        parent.$.jBox.tip("操作成功！", 'info');
                        mmg.load();
                      }
                      return rtn;
                }
                return false;
            }
        });
    }
   
		
    //删除
    function delOper(id){
        if(id == ''){
        	alert('参数错误！请刷新页面后重试');
          	return ;
        }
        var submit = function (v, h, f) {
            if (v == 'ok'){
            $.ajax({
					type: "POST", 
					url:"<c:url value='/Base/Menu/deletebaseelement.shtml'/>", //代码数据
					async:false,
					data:{'baseElementBean.id':id},
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
            else if (v == 'cancel')
                parent.$.jBox.tip("操作失败！", 'info');
            return true; //close
        };
        parent.$.jBox.confirm("确定删除该记录？", "提示", submit);
    }
	//子页面重新刷新父页面
	function reloadPage(){
		mmg.load();
	}
</script>
</html>