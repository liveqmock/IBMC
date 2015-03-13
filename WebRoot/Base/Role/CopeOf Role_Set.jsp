<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link rel="stylesheet" href="../../Style/main.css"/>
	<link rel="stylesheet" href="../../Style/bootstrap.css"/>
	<link rel="stylesheet" href="../../Style/valid.css"/>
    <script type="text/javascript" src="../../Script/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="../../Script/plugin/valid/FormValidator.1.0.min.js"></script>
    <script type="text/javascript">
       $(function(){
			bindValid();
			chkEach("ChkPurView","${baseRoleBean.purview}");
		});
	   function callBack(){
	    	$("#form1").attr("action",'<c:url value='/Base/Role/savebaserole.shtml'/>').trigger("submit");
			if($("#formerror").text() == "ok"){
				return true;
			}
			return false;
	   }
	   function bindValid(){
			//绑定角色名称
			$("#b_rolename").attr("fv-empty","false").attr("fv-empty-msg","角色名称不能为空")
						.attr("fv-msg-success","").attr("fv-msgpanel","valid_codename");
			$("#b_rolename").parent().append('<span id="valid_codename" class="validate-info"></span>');

			//绑定权值
			$("#b_weight").attr("fv-empty","false").attr("fv-empty-msg","权值不能为空")
							.attr("fv-datatype","Int16").attr("fv-datatype-msg","权值只能为数字")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_sortnum");
			$("#b_weight").parent().append('<span id="valid_sortnum" class="validate-info"></span>');
			
			//绑定form表单自定义验证
			$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
		}
		
					
		//恢复已勾选的项
		function chkEach(sign,type){
			$.each($("input[name='"+sign+"']"),function(i,domv){
				if((","+type+",").indexOf(","+domv.value+",")>=0){
					$(this).attr("checked",true);
				}
			});
		}
	</script>
</head>
<body>
<div class="editContainer">
   <div class="errorBlock"></div>
    <!-- 查询条件 --->
    <form name="form1" method="post" action="" id="form1">
        <table class="editTable">
            <tbody>
				        <tr> 
				            <td class="left">所在组：</td>
				            <td class="right">
				            	<input type="radio" name="baseRoleBean.rgroup" value="1" ${baseRoleBean.rgroup=="1"?'checked':''}/>系统组
				        		<input type="radio"  name="baseRoleBean.rgroup" value="0"  ${baseRoleBean.rgroup=="0"?'checked':''}/>用户组    
				            </td>
				        </tr>
				        <tr> 
				            <td class="left">*&nbsp;角色名称：</td>
				            <td class="right">
				            	<input name="baseRoleBean.rolename" id="b_rolename" type="text" class="text_50" size="50" value="${baseRoleBean.rolename}">
				            </td>
				        </tr>
						<tr> 
				            <td class="left">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
				            <td class="right">
				            	<input name="baseRoleBean.remark" type="text" class="text_50" size="50" value="${baseRoleBean.remark}">			
				            </td>
				        </tr>
						<tr> 
				            <td class="left">*&nbsp;权&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值：</td>
				            <td class="right">
				            	<input name="baseRoleBean.weight" id="b_weight" type="text" class="text_10" size="10" value="${baseRoleBean.weight}">（权值越小，权限越大）
				            </td>
				        </tr>
          
            </tbody>
             <!--表格内容部分-->
	         <tbody>
	           <tr>
	           	<td colspan="4">
	           		<table class="editTable">
	           		<c:set var = "inds" value="0"/>
	           		<c:set var = "len" value="0"/>
	           		<c:set var = "count" value="0"/>
	           		<c:forEach items="${dataList}" var="data">
	           			<c:choose>
	           				<c:when test="${data.lev=='1'}">
	           				 <tr style="background:red;">
		           			  	<td colspan="4" class="right" style="border-bottom:#999999 solid 1px;border-top:#999999 solid 1px; height:30px;"><font color="#00000"><b>${data.name}</b></font></td>
		           			  </tr>
		           			  <c:set var = "inds" value="0"/>
	           		          <c:set var = "len" value="0"/>
	           		          <c:set var = "count" value="4"/>
	           				</c:when>
	           				<c:otherwise>
	           					<c:set var = "len" value="${len+1}"/>
	           					<c:if test = "${((inds-1)%4==0 || inds==0)}">
	           						<tr>
	           					</c:if>
	           					<td width="25%">
	           					<c:choose>
			           			<c:when test="${data.pur=='1' || data.specsign=='1'}">
			           				<input type="checkbox" name="ChkPurView" value="${data.id}"/>
			           			</c:when>
			           			<c:otherwise>
			           				<input type="checkbox" value="${data.id}" disabled checked/>
			           			</c:otherwise>
			           			</c:choose>${data.name}
		           			</td>
	           				</c:otherwise>
	           			</c:choose>

		           			  <c:set var = "inds" value="${inds+1}"/>
	           			
	           		</c:forEach>	
	           	   </table>
	           	</td>
	           </tr>
	            <!-- 以下为隐藏域 -->
	            <tr style="display:none;">
	            	<input type="hidden" name="tokenid" value="${tokenid}" />
	                <input type="hidden" name="baseRoleBean.id" value="${baseRoleBean.id}" />
	                <input type="hidden" name="baseRoleBean.purview" value="${baseRoleBean.purview}" />
	            </tr>
	            <tr>
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