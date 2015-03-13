<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/tipcss.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/valid.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
	<script src="<c:url value='/Script/plugin/valid/FormValidator.1.0.min.js'/>"></script>
</head>
<body>
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">系统配置</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <div class="lineSpac2"></div>
    <!-- 查询条件 --->
    <form action="" method="post" id="form1" name="form1" >
        <table class="editTable">
        	<thead>
                <tr>
                    <td width="30%">卡类型</td>
                    <td width="30%">押金（元）</td>
                    <td width="30%">有效期限</td>
                </tr>
        	</thead>
            <tbody>
                <tr>
                    <td>业主卡</td>
                    <td>
                    	<input type="text" value="${sysConfigBean.configval4 }" name="sysConfigBean.configval4"  id="b_configval4" class="text_06"/>元
                    	<span id="valid_configval4"></span>
                    </td>
                    <td>
                    	<select name="sysConfigBean.configval1" id="b_configval1">
                    			<option value="">==请选择==</option>
	                    	<c:forEach items="${configValidList}" var="c">
	                    		<option value="${c.key}" <c:if test="${c.key == sysConfigBean.configval1}">selected</c:if>>${c.value }</option>
	                    	</c:forEach>
                    	</select>
                    	<span id="valid_configval1"></span>
                    </td>
                </tr>
                <tr>
                    <td>临时卡</td>
                    <td>
                    	<input type="text" value="${sysConfigBean.configval5 }" name="sysConfigBean.configval5"  id="b_configval5" class="text_06"/>元
                    	<span id="valid_configval5"></span>
                    </td>
                    <td>
                    	<select name="sysConfigBean.configval2"  id="b_configval2">
                    			<option value="">==请选择==</option>
	                    	<c:forEach items="${configValidList}" var="c">
	                    		<option value="${c.key}" <c:if test="${c.key == sysConfigBean.configval2}">selected</c:if>>${c.value }</option>
	                    	</c:forEach>
                    	</select>
                    	<span id="valid_configval2"></span>
                    </td>
                </tr>
                <tr>
                    <td>正式卡</td>
                    <td>
                    	<input type="text" value="${sysConfigBean.configval6 }" name="sysConfigBean.configval6"  id="b_configval6" class="text_06"/>元
                    	<span id="valid_configval6"></span>
                    </td>
                    <td>
                    	<select name="sysConfigBean.configval3"  id="b_configval3">
                    			<option value="">==请选择==</option>
	                    	<c:forEach items="${configValidList}" var="c">
	                    		<option value="${c.key}" <c:if test="${c.key == sysConfigBean.configval3}">selected</c:if>>${c.value }</option>
	                    	</c:forEach>
                    	</select>
                    	<span id="valid_configval3"></span>
                    </td>
                </tr>
            </tbody>
            <tfoot>
				<tr> 
					<td colspan="3" align="center">
                    	<input type="hidden" name="tokenid" value="${tokenid}" />
                    	<input type="hidden" name="sysConfigBean.configkey" value="${sysConfigBean.configkey}"/>
                    	<input type="hidden" name="sysConfigBean.commid" value="${sysConfigBean.commid}"/>
                    	
                        <input type="button" id="submitBtn" class="btn btn-info" value="保存"/>
                        <input type="button" id="resetBtn" class="btn btn-disabled" value="重置"/>
                    	<span id="formerror"></span>
					</td>
				</tr>
            </tfoot>
        </table>
    </form>
</div>
</body>
	<script type="text/javascript">
		//验证
	     function bindValid(){
			//押金
			$("#b_configval4").attr("fv-empty","false").attr("fv-empty-msg","押金<fmt:message key="format.int" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_configval4");
			$("#b_configval5").attr("fv-empty","false").attr("fv-empty-msg","押金<fmt:message key="format.int" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_configval5");
			$("#b_configval6").attr("fv-empty","false").attr("fv-empty-msg","押金<fmt:message key="format.int" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_configval6");
			//有效期
			$("#b_configval1").attr("fv-empty","false").attr("fv-empty-msg","有效期<fmt:message key="format.null" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_configval1");
			$("#b_configval2").attr("fv-empty","false").attr("fv-empty-msg","有效期<fmt:message key="format.null" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_configval2");
			$("#b_configval3").attr("fv-empty","false").attr("fv-empty-msg","有效期<fmt:message key="format.null" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_configval3");
			
			//绑定form表单自定义验证
			$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
		}
        $(function(){
	         $("#submitBtn").bind("click",function(){
		       	$("#form1").attr("action",'<c:url value='/Sys/Config/savesysconfigofstandar.shtml'/>').trigger("submit");
				if($("#formerror").text() == "ok"){
					return true;
				}
				return false;
	         });
	         $("#resetBtn").bind("click",function(){
		       	location.href = '<c:url value='/Sys/Config/setsysconfigofstandar.shtml'/>';				
	         });
	         bindValid();
        });
    </script>
</html>