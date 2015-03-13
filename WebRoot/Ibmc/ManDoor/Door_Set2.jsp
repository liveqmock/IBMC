<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/valid.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
	<script src="<c:url value='/Script/plugin/valid/FormValidator.1.0.min.js'/>"></script>
	<script type="text/javascript">
		//保存
		function saveData(){
			//文件路径
			var temp="";
			$.each($('input[name=upfile]'),function(i,n){
				temp+=$(this).val();
			});
			if(temp==""){
				parent.$.jBox.tip("文件路径不能为空！", 'warning');
				return false;
			}
			$("input[name='btn_save'],input[name='btn_sure'],input[name='btn_reset'],input[name='btn_back']").attr("disabled","disabled");
			
			openProcessDiv();
			$("#form1").attr("action",'<c:url value='/Man/Door/toforwardlistpage.shtml'/>').trigger("submit");
		}
		
		$(function(){
			//返回
			$("#backBtn").bind("click",function(){
				$("#form1").attr("action",'<c:url value='/Man/Door/toforwardlistpage.shtml'/>').trigger("submit");
			});
			//验证数据有效性
			$("#submitBtn").bind("click",function(){
				//1:这里使用ajax进行验证,2:提交表单后返回页面
			});
			//数据预览
			$("#previewBtn").bind("click",function(){
				//跳转到预览页面中
				$("#form1").attr("action",'<c:url value='/Man/Door/toforwardlistpage.shtml'/>').trigger("submit");
			});
		});
		
	    //下载文件 
		function downLoad(savename,savepath){
		   downframe.document.form1.inputPath.value = savepath;
		   downframe.document.form1.fileName.value = savename;
		   downframe.document.form1.submit();
		}
		
	</script>
</head>
<body>
<!-- 后面新增的样式 -->
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">门口机导入</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <form name="form1" action="" method="post" id="form1" enctype="multipart/form-data">
        <table class="editTable">
            <tbody>
                <tr>
                    <td nowrap class="left">*&nbsp;文件路径：</td>
                    <td class="right">
                        <input type="hidden" name="filefat" value="xls" />
                        <input type="hidden" name="filesize" value="50" />
                        <input type="file" name="upfile" class="textfile_50" onChange="JavaScript:checkFile();"/>（≤50MB，*.xls）
                    </td>
                </tr>
		        <tr>
		            <td class="left">模板文件：</td>
		            <td class="right">
		                <span class="onShow"></span><a href="JavaScript:downLoad('Table01.xls','/Ibmc/ManDoor/DownLoad/')" class="fontcolor_red">&gt;&gt;&nbsp;格式下载</a>
		            </td>
		        </tr>
                <!-- 以下为隐藏域 -->
	            <tr>
	                <td colspan="2">
	                	<input type="button" id="submitBtn" class="btn btn-info" value="验证数据有效性"/>
		                <input type="button" class="btn btn-disabled" value="数据预览" disabled/>/
		                <input type="button" id="previewBtn" class="btn btn-info" value="数据预览"/>
		                <input type="button" id="backBtn" class="btn btn-info" value="返回"/>
                        
                        <input type="hidden" name="tokenid" value="${tokenid}"/>
                        <input type="hidden" name="fields" value="${fields}" id="fields" />
                        <input type="hidden" name="keyword" value="${keyword}" id="keyword" />
                        <input type="hidden" name="tagpage" value="${tagpage}" id="tagpage" />
                        <input type="hidden" name="record" value="${record}" id="record" />
	                </td>
	            </tr>
	            <tr>
	                <td colspan="2" style="display:none;">
						<span id="formerror"></span>
	                </td>
	            </tr>
		    </tbody>
        </table>
   </form>
	<div class="errorBlock"></div>
</div>
</body>
</html>
<iframe id="downframe" src="<c:url value='/Include/NoData.jsp'/>" style="display:none"/>