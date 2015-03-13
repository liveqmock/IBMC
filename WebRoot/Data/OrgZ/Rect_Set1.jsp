<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="../../Include/TagLib.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link rel="stylesheet" href="../../Style/main.css"/>
	<link rel="stylesheet" href="../../Style/bootstrap.css"/>
	<link rel="stylesheet" href="../../Style/valid.css"/>
	<script type="text/javascript" src="../../Script/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="../../Script/plugin/valid/FormValidator.1.0.min.js"></script>
	<script type="text/javascript">
		$(function(){
			bindValid();
			$("#submitBtn").bind("click",callBack);
			$("#closeBtn").bind("click",function(){
				parent.$.jBox.close();
			});
		});
		
		function checkOrganValue(){
			var unitid = '${dataOrganZBean.unitid}';
			var fatherid = '${dataOrganZBean.grandfatherid}';
			var organvalue = $("#b_organvalue").val();
			//当前编码为空
			if(organvalue == ''){
				$("#showmsg").text("");
				$("#showmsg").removeClass("fontcolor_red");
				return ;	
			}
			var querymap = {'querymap.unitid':unitid,'querymap.fatherid':fatherid,'querymap.organvalue':organvalue};
			$.ajax({
					url:"<c:url value='/Data/OrgZ/chkDataOrganZCodeValueByJq.shtml'/>",   //接收页面
					type: 'post',      	//POST方式发送数据
					async: true,      		//ajax异步
					cache: false, 
					data:  querymap,
					error:function(data){
				    	alert( "与数据库操作失败！");
					},
					success:function(data){
						if(parseInt(data)>0){
							$("#showmsg").removeClass("validate-info")
										 .removeClass("validate-error").addClass("validate-error")
										 .html("编码重复");
						}else{
							$("#showmsg").removeClass("validate-error")
										 .removeClass("validate-info").addClass("validate-info")
										 .html("√");		
						}
					}
			});
		}
		
		function callBack(){
			$("#form1").attr("action",'<c:url value='/Data/OrgZ/savedataorganzuplev.shtml'/>').trigger("submit");
			if($("#formerror").text() == "ok"){
				return true;
			}
			return false;
		}
		
		function bindValid(){
			//机构名称
			$("#b_organname").attr("fv-empty","false").attr("fv-empty-msg","机构名称不能为空")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_organname");
			$("#b_organname").parent().append('<span id="valid_organname" class="validate-info"></span>');
			
			//机构简称[备注]
			$("#b_shortname").attr("fv-empty","false").attr("fv-empty-msg","机构简称不能为空")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_shortname");
			$("#b_shortname").parent().append('<span id="valid_shortname" class="validate-info"></span>');
			
			//绑定编码
			$("#b_organvalue").attr("fv-empty","false").attr("fv-empty-msg","编码不能为空")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_codevalue");
			$("#b_organvalue").parent().append('<span id="valid_organvalue" class="validate-info"></span>');
			
			//绑定排序号
			$("#b_sortnum").attr("fv-empty","false").attr("fv-empty-msg","排序号不能为空")
							.attr("fv-datatype","Int16").attr("fv-datatype-msg","排序号只能为数字")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_sortnum");
			$("#b_sortnum").parent().append('<span id="valid_sortnum" class="validate-info"></span>');
			
			//绑定form表单自定义验证
			$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
		}
	</script>
</head>
<body>
<div class="editContainer">
    <!-- 查询条件 --->
    <form id="form1" action="" name="form1">
        <table class="editTable">
            <tbody>
            <tr>
                <td class="left">升级源：</td>
                <td colspan="3" class="right">${dataOrganZBean.firstr}</td>
            </tr>
            <tr>
                <td class="left">升级目标：</td>
                <td colspan="3" class="right">
                	<c:choose><c:when test="${querymap.lev=='2'}">${dataOrganZBean.organname}</c:when><c:otherwise>${dataOrganZBean.secstr}</c:otherwise></c:choose>
                </td>
            </tr>
            <tr>
                <td class="left">目标更名：</td>
                <td colspan="3" class="right">
					<input type="text" name="dataOrganZBean.organname" id="b_organname" value="${dataOrganZBean.organname}"/>
                </td>
            </tr>
            <tr>
                <td class="left">机构简称：</td>
                <td colspan="3" class="right">
                    <input type="text" name="dataOrganZBean.shortname" id="b_shortname" value="${dataOrganZBean.shortname}"/>
                </td>
            </tr>
            <tr>
                <td class="left">编码：</td>
                <td colspan="3" class="right">
                    <input type="text" name="dataOrganZBean.organvalue" id="b_organvalue" value="${dataOrganZBean.organvalue}" autocomplete="off" onkeyup="JavaScript:checkOrganValue();"/>
                    <span id="showmsg"></span>
                </td>
            </tr>
            <tr>
                <td class="left">排序号：</td>
                <td colspan="3" class="right">
                    <input type="text" name="dataOrganZBean.sortnum" id="b_sortnum" value="${dataOrganZBean.sortnum}"/>
                </td>
            </tr>
            <!-- 以下为隐藏域 -->
            <tr>
                <td colspan="4">
                	<input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
                	<input type="button" id="closeBtn" class="btn btn-info" value="关闭"/>
				    <input type="hidden" name="tokenid" value="${tokenid}" />
	                <input type="hidden" name="fatherid" value="${fatherid}" />
	                <input type="hidden" name="dataOrganZBean.id" value="${dataOrganZBean.id}" />
	                <input type="hidden" name="dataOrganZBean.fatherid" value="${dataOrganZBean.grandfatherid}"/>
                </td>
            </tr>
            <tr style="display:none;">
                <td colspan="4">
					<span id="formerror"></span>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>