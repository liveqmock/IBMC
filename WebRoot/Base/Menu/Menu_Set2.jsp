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
	    	$("#form1").attr("action",'<c:url value='/Base/Menu/savebasemenu.shtml'/>').trigger("submit");
			if($("#formerror").text() == "ok"){
				return true;
			}
			return false;
	   }
	   
	   
		function bindValid(){
			//绑定类别
			$("#b_name").attr("fv-empty","false").attr("fv-empty-msg","菜单名称不能为空")
						.attr("fv-msg-success","").attr("fv-msgpanel","valid_codename");
			$("#b_name").parent().append('<span id="valid_codename" class="validate-info"></span>');
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
   <div class="errorBlock"></div>
    <!-- 查询条件 --->
    <form name="form1" method="post" action="" id="form1">
        <table class="editTable">
            <tbody>
            <tr>
                <td  class="left">
                   *&nbsp;菜单名称：
                </td>
                <td  class="right">
                   <input type="text" id="b_name" name="baseMenuBean.name" class="text_30" value="${baseMenuBean.name}"/>
                </td>
            </tr>
            <!--
            <tr>
                <td  class="left">
                    菜单ID：
                </td>
                <td  class="right">
                  <input type="text" name="baseMenuBean.rel " class="text_30" value="${baseMenuBean.rel}"/>
                </td>
            </tr>
    -->
             <tr>
                <td  class="left">
                    菜单链接：
                </td>
                <td  class="right">
                  <input type="text"  name="baseMenuBean.url " class="text_30" value="${baseMenuBean.url}"/>
                </td>
            </tr>
    
            <tr>
                <td  class="left">
                   菜单权限：
                </td>
                <td  class="right"> 
                        <input type="radio" name="baseMenuBean.pur" value="1" ${baseMenuBean.pur=="1"?'checked':''}/>有
				        <input type="radio"  name="baseMenuBean.pur" value="0"  ${baseMenuBean.pur=="0"?'checked':''}/>无                    
                </td>
            </tr>
            <tr>
                <td  class="left">
                   特殊标记：
                </td>
                <td class="right">
                     <input type="radio"  name="baseMenuBean.specsign" value="1" ${baseMenuBean.specsign=="1"?'checked':''}/>有
					 <input type="radio"  name="baseMenuBean.specsign" value="0" ${baseMenuBean.specsign=="0"?'checked':''}/>无    
                </td>
            </tr>
            <tr>
                <td  class="left">
                   *&nbsp;排序号：
                </td>
                <td class="right">
                   <input type="numberbox" id="b_sortnum" name="baseMenuBean.sortnum" class="text_10" value="${baseMenuBean.sortnum }" maxlength="10"/>
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
           	     <input type="hidden" name="baseMenuBean.lev" value="${baseMenuBean.lev }" />
                 <input type="hidden" name="baseMenuBean.usesign" value="1"/>
                 <input type="hidden" name="baseMenuBean.fatherid" value="${baseMenuBean.fatherid}"/>
                 <input type="hidden" name="querymap.fatherid" value="${querymap.fatherid}" />
                 <input type="hidden" name="baseMenuBean.id" value="${baseMenuBean.id}"/>
				 <span id="formerror"></span>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
</div>

</body>
</html>