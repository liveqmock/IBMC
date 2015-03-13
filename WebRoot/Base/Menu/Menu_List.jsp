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
        <div class="titleClass">菜单设置</div>
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
         { title:'序号', name:'' ,width:40,lockWidth:true, align:'center', rowspan:2, renderer: function(val,item,rowIndex){
            if(0==rowIndex){
                i=0;
                j=0;
                k=0;
            }
            if(parseInt(item.lev)==1){
                i+=1;
                j=0;
                myIndex=i;
            }else if(parseInt(item.lev)==2){
                j+=1;
                k=0;
                myIndex=i+'.'+j;
            }
            return myIndex;
        }},
        { title:'菜单名称', name:'name' ,width:80, align:'left', renderer: function(val,item,rowIndex){
        	if(parseInt(item.lev)==1){
        		if(parseInt(item.usesign) == 1){
	                return val;
	            }else{
	               return "[<span class=\"fontcolor_red\">禁</span>]"+val;
	            }
        	}else{
        		if(parseInt(item.usesign) == 1){
	                return "&nbsp;&nbsp;"+val;
	            }else{
	               return "[<span class=\"fontcolor_red\">禁</span>]&nbsp;&nbsp;"+val;
	            }
        	}
            
        }},
        { title:'菜单ID', name:'rel' ,width:140, align:'left',renderer: null},
        { title:'菜单链接', name:'url' ,width:140, align:'left',renderer: null},
        { title:'权限标记', name:'pur' ,width:10, align:'center', renderer: null},
        { title:'特殊标记', name:'specsign' ,width:10, align:'center',renderer: null},   
        { title:'操作', name:'usesign' ,width:90, align:'left', renderer: function(val,item,rowIndex){
            var addStr="&nbsp;<button class=\"btn btn-info\" onClick=\"setCodeTwo("+item.id+",'1');\">新增子项</button>";
			var usesignStr="&nbsp;<button class=\"btn btn-disabled\" onClick=operUsesign("+item.id+",'0');>禁用</button>";
			var delStr="";
            if(val == 0) {
                usesignStr="&nbsp;<button class=\"btn btn-info\" onClick=operUsesign("+item.id+",'1');>启用</button>";
                if(item.lev==1){
               		 delStr="&nbsp;<button class=\"btn btn-info\" onClick=\"setCode("+item.id+",'2');\">修改</button>";
                }else{
               		 delStr="&nbsp;<button class=\"btn btn-info\" onClick=\"setCodeTwo("+item.id+",'2');\">修改</button>";
                }
				delStr+="&nbsp;<button class=\"btn btn-danger\" onClick=\"delOper("+item.id+");\">删除</button>";
            }
			if(parseInt(item.lev)!=1){
				addStr='';
			}
            return addStr+delStr+usesignStr;
        }}
    ];
    //列表数据加载
    var url = '<c:url value='/Base/Menu/listBaseMenuByJq.shtml'/>';   
     var mmg = $('#dataTable').mmGrid({
        height:'420',
        cols: cols,
        url: url,
        method: 'get',
        root: 'items',
        fullWidthRows: true
    });
    
    $(function(){
        $("#insert").bind("click",function(){setCode("","1");});
    });
     
	
	
     //新增/修改（一级跳转）
    function setCodeTwo(id,fun){
        var url = '';
        var msg = '';
        if(fun == '1'){
        	msg = '新增';
			url = "<c:url value='/Base/Menu/setbasemenu2.shtml'/>?fun="+fun;
			url +="&baseMenuBean.fatherid="+id;
        }else if(fun == '2'){
        	msg = '修改';
			url = "<c:url value='/Base/Menu/setbasemenu2.shtml'/>?fun="+fun+"&baseMenuBean.id="+id;
        }
        parent.$.jBox.open("iframe:"+url, msg , 600, 420, { buttons: {'确定':true,'关闭':false},
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
    //新增/修改（二级跳转）
    
     function setCode(id,fun){
        var fatherid = ${querymap.fatherid };
        var url = '';
        var msg = '';
        if(fun == '1'){
        	msg = '新增子项';
			url = "<c:url value='/Base/Menu/setbasemenu1.shtml'/>?fun="+fun;
			url +="&querymap.fatherid="+fatherid;
        }else if(fun == '2'){
        	msg = '修改';
			url = "<c:url value='/Base/Menu/setbasemenu1.shtml'/>?fun="+fun+"&baseMenuBean.id="+id;
        }
        parent.$.jBox.open("iframe:"+url, msg , 600, 420, { buttons: {'确定':true,'关闭':false},
            submit: function (v, h, f) {
                h.find('.errorBlock').hide('fast', function () { $(this).remove(); });
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
					url:"<c:url value='/Base/Menu/deleteBaseMenuByJq.shtml'/>", //代码数据
					async:false,
					data:{'baseMenuBean.id':id},
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

   //启用|禁止
    function operUsesign(id,usesign){
    	if(id == ''){
        	parent.$.jBox.tip("参数错误！请刷新页面后重试！", 'info');
          	return ;
        }
        var submit = function (v, h, f) {
            if (v == 'ok')
           		$.ajax({
					type: "POST", 
					url: "<c:url value='/Base/Menu/updateBaseMenuUsesignByJq.shtml'/>", //启用
					async:false,
					data:{'baseMenuBean.id':id,'baseMenuBean.usesign':usesign},
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
            else if (v == 'cancel')
                parent.$.jBox.tip("操作失败！", 'info');
            return true; //close
        };
        parent.$.jBox.confirm("确定启用|禁止操作？", "提示", submit);
    }
    
</script>
</html>