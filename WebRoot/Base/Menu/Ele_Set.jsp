<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
			$("#submitBtn").bind("click",callBack);
			$("#closeBtn").bind("click",function(){
				parent.$.jBox.close();
			});
		});
	   function callBack(){
	    	$("#form1").attr("action",'<c:url value='/Base/Menu/savebaseelement.shtml'/>').trigger("submit");
			  if($("#formerror").text() == "ok"){
				return true;
			  }
			 return false;
	   }
	   function bindValid(){
			//页面元素显示名称
			$("#b_value").attr("fv-empty","false").attr("fv-empty-msg","页面元素显示名称")
						.attr("fv-msg-success","").attr("fv-msgpanel","valid_value");
			$("#b_value").parent().append('<span id="valid_value" class="validate-info"></span>');

			//绑定页面元素编码
			$("#b_code").attr("fv-empty","false").attr("fv-empty-msg","页面元素编码")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_code");
			$("#b_code").parent().append('<span id="valid_code" class="validate-info"></span>');
			
			//页面元素对应的function
			$("#b_func").attr("fv-empty","false").attr("fv-empty-msg","页面元素对应的function")
						.attr("fv-msg-success","").attr("fv-msgpanel","valid_func");
			$("#b_func").parent().append('<span id="valid_func" class="validate-info"></span>');
			
			//页面元素对应的样式：
			$("#b_stycla").attr("fv-empty","false").attr("fv-empty-msg","页面元素对应的样式")
						.attr("fv-msg-success","").attr("fv-msgpanel","valid_stycla");
			$("#b_stycla").parent().append('<span id="valid_stycla" class="validate-info"></span>');
			
			//绑定form表单自定义验证
			$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
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
                <td  class="left" width="35%">
                  	显示名称：
                </td>
                <td class="right"> 
					<input type="text" id="b_value" name="baseElementBean.codename" class="text_30" value="${baseElementBean.codename}"/>                 
                </td>
            </tr>
            
            <tr>
                <td  class="left">
                 	按钮编码：
                </td>
                <td class="right">
                   <input type="text" id="b_code" name="baseElementBean.codevalue" class="text_30" value="${baseElementBean.codevalue}"/>
                </td>
            </tr>
          
            <tr>
                <td  class="left">
                	clickEvent：
                </td>
                <td class="right">
                   <input type="text" id="b_func" name="baseElementBean.clickevent" class="text_30" value="${baseElementBean.clickevent}"/>
                </td>
            </tr>
            <tr>
                <td  class="left">
                 	按钮样式：
                </td>
                <td class="right">
                	<select id="b_stycla" name="baseElementBean.styleclass">
                		<c:forEach items="${classList}" var="c">
                			<option value="${c.btnClass }" <c:if test="${c.btnClass==baseElementBean.styleclass}">selected</c:if>>${c.btnValue }</option>
                		</c:forEach>
                	</select>
                </td>
            </tr>
            </tbody>
             <!-- 以下为隐藏域 -->
            <tfoot>
            <tr>
                <td  colspan="2">
	               	<input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
	               	<input type="button" id="closeBtn" class="btn btn-info" value="关闭"/>
	                 <input type="hidden" name="tokenid" value="${tokenid}" />
	                 <input type="hidden" name="baseElementBean.id" value="${baseElementBean.id}" />
					<span id="formerror"></span>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
</div>
</body>
</html>