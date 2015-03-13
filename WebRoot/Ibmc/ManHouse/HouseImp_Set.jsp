<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title></title>
	<link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/tabs.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/tipcss.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/valid.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Ztree/css/zTreeStyle/zTreeStyle.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
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
	<script language="javascript"> 
		$(function(){
			$("#valid_villagetext,#valid_upfile").hide();
			//确定导入
			$("#submitBtn").bind("click",function(){
				//判断文件是否为空
				var parentid = $("#parentid").val();   //社区id
				var upfile = $("#upfile").val();       //上传的文件路径
				if(parentid==''||upfile==''){
					if(parentid==''){
						$("#valid_villagetext").show();
					}else{
						$("#valid_villagetext").hide();
					}
					if(upfile==''){
						$("#valid_upfile").show();
					}else{
						$("#valid_upfile").hide();
					}
					return;
				}
				$("#form2").attr("action","<c:url value='/Man/House/savemanhouseimp.shtml'/>").trigger("submit");
			});
			
			//数据是否存在错误[1：正确 2：错误]
			if("${errorsign}" == "2"){  
				$("#tr_1").show();
                parent.$.jBox.tip("导入失败，部分数据存在验证失败，请下载系统验证后的文件进行修改！", 'info');
			}else if("${errorsign}" == "1"){
				$("#previewBtn").attr("disabled",false);
				$("#submitBtn").attr("disabled",true);
				$("#submitBtn").removeClass("btn btn-info").addClass("btn btn-disabled");
                parent.$.jBox.tip("导入成功，数据验证通过，请点击按钮进行预览！", 'info');
			}
			
			//页面预览[跳转]
			$("#previewBtn").bind("click",function(){
				$("#form2").attr("action","<c:url value='/Man/House/setmanhouseimpdatapurview.shtml'/>").trigger("submit");
			});
		});
		
		//导出
		function exportExcel(){
			$("#form2").attr("action","<c:url value='/Man/House/expmanhousetempimpdata.shtml'/>").trigger("submit");
		}
		
		//下载文件 
		function downLoad(savename,savepath){
		   downframe.document.form1.inputPath.value = savepath;
		   downframe.document.form1.fileName.value = savename;
		   downframe.document.form1.submit();
		}
	</script>
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
        <div class="titleClass">房产信息导入</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <form action="" name="form2" id="form2" method="post" enctype="multipart/form-data">
	    <table class="editTable">
	        <tbody>
	        <tr style="display:none;">
	            <td class="left">所属区域：</td>
	            <td class="right">-----------</td>
	        </tr>
	        <tr>
	            <td class="left">社区名称：</td>
	            <td class="right">
	            	<span id="villagetext">${not empty querymap.commname?querymap.commname:''}</span>
                    <span id="valid_villagetext" class="validate-error">社区名称<fmt:message key="format.null" bundle="${bms}"/></span>
	            </td>
	        </tr>
	        <tr>
	            <td class="left">文件选择：</td>
	            <td class="right">
					<input type="file" name="upfile" id="upfile" class="textfile_30"/>
                    <span id="valid_upfile" class="validate-error">导入的文件路径<fmt:message key="format.null" bundle="${bms}"/></span>
	            </td>
	        </tr>
	        <tr>
	            <td class="left">模板文件：</td>
	            <td class="right">
	                <span class="onShow"></span><a href="JavaScript:downLoad('house-room.xls','/Ibmc/ManHouse/Download/');" style="color:red;"><strong>房产信息模板文件下载</strong></a>
	            </td>
	        </tr>
	        <tr id="tr_1" style="display:none;">
	            <td class="left">系统验证文件：</td>
	            <td class="right">系统在验证数据过程中，发现部分数据格式存在错误，请“<a href="###" onClick="JavaScript:exportExcel();"><font color="red">点击此链接</font></a>”下载系统验证过的文件，<br/>并修改其中红色标记的纪录后，重新导入！
	            </td>
	        </tr>
	        <tr>
	            <td align="center" colspan="2">
	                <input type="button" name="submitBtn" id="submitBtn" class="btn btn-info" value="验证数据有效性"/>
	                <input type="button" name="previewBtn" id="previewBtn" class="btn btn-disabled" value="数据预览" disabled/>
                    <input type="hidden" name="querymap.parentid" id="parentid" value="${querymap.parentid}" />
                    <input type="hidden" name="errorsign" id="errorsign" value="${errorsign}" />
                    <input type="hidden" name="tokenid" value="${tokenid}" />
                    
                    <input type="hidden" name="querymap.commname" id="commname" value="${querymap.commname}" />
					<input type="hidden" name="querymap.villagelev" value="${querymap.villagelev}" />
                    <input type="hidden" name="querymap.needFilterScope" value="${querymap.needFilterScope}" />
                    <input type="hidden" name="querymap.maxLev" value="${querymap.maxLev}" />
	            </td>
	        </tr>
	        </tbody>
	    </table>
    </form>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<script type="text/javascript">
	//设置checkbox属性
	var setting = {
	    data: {
	        simpleData: {
	            enable: true
	        }
	    },
	    callback: {
			onClick:function(e, treeId, treeNode){
				var commlev = treeNode.commlev;
				if('${querymap.villagelev}'==commlev){
					$("#parentid").val('-'+treeNode.id);
					$("#villagetext").html(treeNode.name);
					$("#commname").val(treeNode.name);
					$("#valid_villagetext").hide();
				}else{
					$("#parentid").val('');
					$("#villagetext").html('');
					$("#commname").val('');
					$("#valid_villagetext").show();
				}
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
	                zNodes[i]=({id:data[i].id, pId:data[i].parentid,name:data[i].commname,commpath:data[i].commpath,commlev:data[i].commlev,open:true});
	            }
	            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	        }
	    });
   }
   
	$(function () {
    	//初始化树状
		initTree();
    });
    
</script>
</body>
</html>
<iframe id="downframe" src="<c:url value='/Include/NoData.jsp'/>" style="display:none"/>