<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title></title>
	<link rel="stylesheet" href="../../Style/main.css"/>
	<link rel="stylesheet" href="../../Style/bootstrap.css"/>
	<link rel="stylesheet" href="../../Style/datagrid.css"/>
	<script type="text/javascript" src="../../Script/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="../../Script/plugin/grid/grid.js"></script>
	<script type="text/javascript" src="../../Script/plugin/grid/plugins.js"></script>
</head>
<body>
<!-- 后面新增的样式 -->
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
    <div class="titleClass">机构维护[<span class="switchSearch">数据搜索</span>]</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
    	<form method="post" name="form" id="form1" action="">
            &nbsp;部门名称：<select name="querymap.id" id="id">
                       		<option value="">全部</option>
                          </select>
            &nbsp;<input type="checkbox" name="querymap.showsign" id="showsign" value="1" ${'1'==querymap.showsign?'checked':''} />仅显示部门级别记录&nbsp;&nbsp;&nbsp;
			关键字：<select name="fields">
                  	<option value="organname" <c:if test="${fields=='organname'}">selected</c:if>>部门名称</option>
                  </select>
               <input type="text" name="keyword" id="keyword" class="text_10" value="${keyword}" maxlength="10" />
               <input type="button" name="btn_query " class="btn btn-primary" value="查询" onclick="toSearch();" />
               
	           <input type="hidden" name="querymap.unitid" value="${querymap.unitid}" />
	           <input type="hidden" name="querymap.fatherid" value="${querymap.fatherid}" />
	           <input type="hidden" name="fatherid" value="${fatherid}" />
	           <input type="hidden" name="sortfield" value="${sortfield}" />
	    </form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="btn_insert">新增</button>
        </div>
        <div class="clear"></div>
        <table id="dataTable"></table>
    </div>
</div>
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
            }else if(parseInt(item.lev)==3){
                k+=1;
                myIndex=i+'.'+j+'.'+k;
            }
            return myIndex;
        }},
        { title:'机构名称', name:'organname' ,width:240, align:'left', renderer: function(val,item,rowIndex){
            if(item.usesign == 1){
            	if(item.specsign=='1'){
                	return val+'＊';
            	}else{
                	return val;
            	} 
            }else if(item.usesign == 0){
            	if(item.specsign=='1'){
                	return '[<span class="fontcolor_red">禁</span>]'+val+'＊';
            	}else{
                	return '[<span class="fontcolor_red">禁</span>]'+val;
            	}
            }
        }},
        { title:'机构简称', name:'shortname' ,width:180, align:'left'},
        { title:'部门缩写', name:'py' ,width:80, align:'center'},
        { title:'编码', name:'organvalue' ,width:150, align:'left'},
        { title:'操作', name:'usesign' ,width:220, align:'left', lockWidth:true, lockDisplay: true, renderer: function(val,item,rowIndex){
        	var bigadd = '';
        	if(parseInt(item.lev)==1){
        		bigadd = "<button class=\"btn btn-danger\" onClick=setDataWin("+item.id+",'1');>新增子项</button>&nbsp;"
        	}
       		if(val == 1){
        		return bigadd+"<button class=\"btn btn-info\" onClick=setDataWin("+item.id+",'2');>修改</button>&nbsp;<button class=\"btn btn-disabled\" onClick=operUsesign("+item.id+",'0');>禁用</button>" 
        	}else if (val == 0){
        		return bigadd+"<button class=\"btn btn-info\" onClick=setDataWin("+item.id+",'2');>修改</button>&nbsp;<button class=\"btn btn-disabled\" onClick=operUsesign("+item.id+",'1');>启用</button>"
        	}
        }}
    ];
    
	var url = "<c:url value='/Data/OrgZ/findDataOrganZTreeListByJq.shtml'/>";
	var paramsV={
    	'querymap.unitid':'${querymap.unitid}',
    	'querymap.fatherid':'${fatherid}',
		'querymap.showsign':'${querymap.showsign}',
		'sortfield':'${sortfield}',
		'fields':'organname',
		'keyword':'${keyword}'
    }
	//分页
    var mmg = $('#dataTable').mmGrid({
        cols: cols,
        width:'100%',
        height:'390',
        params:paramsV,
        url: url,
        method: 'post'
    });
    
	$(function(){
		//构建部门名称列表
		mkDeptList();
		
		//仅显示
		$("#showsign").bind("click",function(){
			toSearch();
		});
		
		//部门change事件触发班组下拉列表框
		$("#id").bind("change",function(){
			toSearch();
		});
		
		//新增部门
		$("#btn_insert").bind("click",function(){
			var id = '${fatherid}';
			setDataWin(id,'1');
		});
		
    });
    
    //构造部门列表
    function mkDeptList(){
    	$.ajax({
             type: 'post',
             async: false,
             url: '<c:url value='/Data/OrgZ/findDataOrganZNoTreeListByJq.shtml'/>',
             data: {'querymap.unitid':'${querymap.unitid}','querymap.fatherid':'${fatherid}','sortfield':'${sortfield}'},
             dataType: 'json',
             cache: false,
             success:function(data){
             	var str = "";
				//通过jquery方法进行循环编译
				$.each(data,function(i,d){
					var sel = ""
					if('${querymap.fatherid}'==d.id){
						sel = "selected";
					}
					str += "<option "+sel+" value="+d.id+" >"+d.organname+"</option>";
				});
				$("#id").append(str);
             },
			error:function(msg){
			    parent.$.jBox.tip("与数据库连接失败！", 'info');
			}
        });
    }
    
    //查询
    function toSearch(){
		//点击查询时页码置为1
		var tagpage = '1';
		var unitid = '${querymap.unitid}';   //单位
	    var fields = $("#fields").val();      //关键字段
	    var keyword = $("#keyword").val();    //关键字
	    var showsign = $("#showsign").is(":checked")?$("#showsign").val():"";
		var id = $("#id").val();     //部门名称
		var fatherid = '${fatherid}';
		if(id!=''){
			fatherid = '';
		}
		mmg.load({'querymap.id':id,
	    		  'querymap.fatherid':fatherid});
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
					url: "<c:url value='/Data/OrgZ/updateDataOrganZUseSignByJq.shtml'/>", //启用
					async:false,
					data:{'dataOrganZBean.id':id,'dataOrganZBean.usesign':usesign},
					error:function(data){
				    parent.$.jBox.tip("与数据库连接失败！", 'info');
					},
					success:function(data) {
						if(data == '1'){
            				parent.$.jBox.tip("操作成功！", 'info');
		                    reloadPage();
						}else{
            				parent.$.jBox.tip("操作失败！", 'info');
						}
					}
				});
            return true; //close
        };
        parent.$.jBox.confirm("确定启用|禁止操作？", "提示", submit);
    }
    
    //新增,修改[弹出]
	function setDataWin(id,fun){
		var url = '';
		var msg = '';
		if(fun == '1'){
			msg = '机构维护-新增';
			url = "<c:url value='/Data/OrgZ/setdataorganzbean.shtml'/>?querymap.fatherid="+id+"&querymap.unitid=${querymap.unitid}";
			url += "&fatherid=${fatherid}"
			url += "&fun=1";
		}else if(fun == '2'){
			msg = '机构维护-修改';
			url = "<c:url value='/Data/OrgZ/setdataorganzbean.shtml'/>?dataOrganZBean.id="+id+"&querymap.unitid=${querymap.unitid}";
			url += "&fatherid=${fatherid}"
			url += "&fun=2";
		}
	    parent.$.jBox.open("iframe:"+url, msg, 600, 470, {buttons: {}});
	}
	
	//子页面重新刷新父页面
	function reloadPage(){
		toSearch();
	}
</script>
</body>
</html>