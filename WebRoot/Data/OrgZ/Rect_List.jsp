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
    <div class="titleClass">机构调整[<span class="switchSearch">数据搜索</span>]</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
    	<form method="post" name="form" id="form1" action="">
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
            <button class="btn btn-info" id="b_marorgan">机构合并</button>
        </div>
        <div class="clear"></div>
        <table id="dataTable"></table>
    </div>
</div>
<script type="text/javascript">
    //列
    var i=0,j=0,k=0,myIndex;
    var cols = [
        { title:'<input name="chkAll" type="checkbox" onClick="chkAll(this.checked);">', name:'' ,width:30, align:'center',renderer: function(val,item,rowIndex){
            if((parseInt(item.lev)==2&&parseInt(item.chilcount)==0)||(parseInt(item.lev)==3)){
                return "<input type=\"checkbox\" name=\"chk\" value='"+item.id+"' />";
            }
        }},
    	{ title:'序号', name:'' ,width:45,lockWidth:true, align:'center',renderer: function(val,item,rowIndex){
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
        { title:'机构名称', name:'organname' ,width:220, align:'left'},
        { title:'机构简称', name:'shortname' ,width:180, align:'left'},
        { title:'部门缩写', name:'py' ,width:80, align:'center'},
        { title:'编码', name:'organvalue' ,width:150, align:'left'},
        { title:'操作', name:'' ,width:220, align:'left', lockWidth:true, lockDisplay: true, renderer: function(val,item,rowIndex){
        	var uplev = '';
        	if((parseInt(item.lev)!=1)&&(parseInt(item.chilcount)==0)){
                uplev = "<button class=\"btn btn-info\" onClick=\"upLev("+item.id+","+item.lev+");\">升级</button>&nbsp;&nbsp;&nbsp;&nbsp;";
            }
            return "<button class=\"btn btn-info\" onClick=\"upName('"+item.organname+"','"+item.id+"');\">更名</button>"+uplev;
        }}
    ];
    
	var url = "<c:url value='/Data/OrgZ/findDataOrganZTreeListByJq.shtml'/>";
	var paramsV={
    	'querymap.unitid':'${querymap.unitid}',
    	'querymap.fatherid':'${fatherid}',
    	'querymap.usesign':'1',
		'querymap.showsign':'${querymap.showsign}',
		'sortfield':'${sortfield}',
		'fields':'organname',
		'keyword':'${keyword}'
    }
	//分页
    var mmg = $('#dataTable').mmGrid({
        method: 'post',
        cols: cols,
        width:'100%',
        height:'380',
        params:paramsV,
        url: url,
        fullWidthRows: true
    });
    
	$(function(){
		//仅显示
		$("#showsign").bind("click",function(){
			toSearch();
		});
		
		//机构合并
		$("#b_marorgan").bind("click",function(){
			var idArr = new Array();
			$("#dataTable tr").each(function(i,obj){
				if($("input[name='chk']",obj).is(":checked")){
	    			idArr.push($("input[name='chk']",obj).val());
				}
	    	});
	    	if(idArr.join(',')==''){
				parent.$.jBox.tip("请选择需要操作合并的机构！", 'info');
				return;
	    	}
			setDataWin(idArr.join(','));
		});
		
    });
    
    //升级
    function upLev(id,lev){
    	var url = '';
		var msg = '';
		msg = '机构升级';
		url = "<c:url value='/Data/OrgZ/setdataorganzuplev.shtml'/>?dataOrganZBean.id="+id+"&querymap.lev="+lev;
		url += "&fatherid=${fatherid}"
		url += "&fun=1";
	    parent.$.jBox.open("iframe:"+url, msg, 600, 320, {buttons: {}});
    }
 
    //机构更名
    function upName(organname,id){
        var html = "<div style='padding:10px;'>机构原名："+organname+"</div>"+
                    "<div style='padding:10px;'>变更名称：<input type='text' id='yourname2' name='yourname2' class='text_20'/></div>";
        var submit = function (v, h, f) {
            h.find('.errorBlock').hide('fast', function () { $(this).remove(); });
            if (v == 1) {
                if (f.yourname2 == '') {
                    parent.$.jBox.tip("请输入变更名称。", 'error', { focusId: "yourname2" }); // 关闭设置 yourname 为焦点
                    return false;
                }
 				$.ajax({
		             type: 'post',
		             async: false,
		             url: '<c:url value='/Data/OrgZ/saveDataOrganZRenameByJq.shtml'/>',
		             data: {'dataOrganZBean.id':id,'dataOrganZBean.organname':f.yourname2},
		             cache: false,
		             success:function(data){
		             	if(parseInt(data)==1){
		             		parent.$.jBox.tip("操作成功！", 'info');
		             		toSearch();
		             	}
		             },
					 error:function(msg){
					    parent.$.jBox.tip("与数据库连接失败！", 'info');
					 }
		        });
            }
            return true;
        };
        parent.$.jBox(html, { title: "机构名称变更",buttons:{'确定':1,'取消':0}, submit: submit });
    }
    
    //查询
    function toSearch(){
		//点击查询时页码置为1
		var tagpage = '1';
		var unitid = '${querymap.unitid}';   //单位
	    var fields = $("#fields").val();      //关键字段
	    var keyword = $("#keyword").val();    //关键字
	    var showsign = $("#showsign").is(":checked")?$("#showsign").val():"";
		var fatherid = '${fatherid}';
		mmg.load();
    }
    
    function chkAll(bool){
    	$("#dataTable tr").each(function(i,obj){
    		$("input[name='chk']",obj).attr("checked",bool);
    	});
	}
    
    //机构合并[弹出]
	function setDataWin(idstr){
		var url = '';
		var msg = '';
		msg = '机构合并';
		url = "<c:url value='/Data/OrgZ/setdataorganzmerge.shtml'/>?querymap.idstr="+idstr+"&querymap.unitid=${querymap.unitid}"+"&querymap.fatherid=${fatherid}";
		url += "&fatherid=${fatherid}"
		url += "&fun=1";
	    parent.$.jBox.open("iframe:"+url, msg, 600, 360, {buttons: {}});
	}
	
	//子页面重新刷新父页面
	function reloadPage(){
		toSearch();
	}
</script>
</body>
</html>