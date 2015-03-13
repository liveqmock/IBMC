<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link rel="stylesheet" href="../../Style/main.css"/>
	<link rel="stylesheet" href="../../Style/bootstrap.css"/>
    <script type="text/javascript" src="../../Script/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
				
	</script>
</head>
<body>
<div class="editContainer">
    <!-- 查询条件 --->
    <form name="form1" method="post" action="" id="form1">
        <table class="editTable">
            <tbody>
			        <tr> 
			            <td class="left">操作人：</td>
			            <td class="right">
			            	${baseOptLogBean.optusername}
			            </td>
			        </tr>
			        <tr> 
			            <td class="left">部门名称：</td>
			            <td class="right">
			           		 ${baseOptLogBean.optdeptname}
			            </td>
			        </tr>
					<tr> 
			            <td class="left">功能名称：</td>
			            <td class="right">
			            	${baseOptLogBean.optremark}
			            </td>
			        </tr>
					<tr> 
			            <td class="left">操作内容：</td>
			            <td class="right">
			            	 <c:forEach items="${optEnums}" var="r">
			                  	 <c:if test="${r.key==baseOptLogBean.opttype}">${r.desc}</c:if>
			                  </c:forEach>
			            </td>
			        </tr>
			        	
			        <tr> 
			            <td class="left">参数：</td>
			            <td class="right">
			            	${baseOptLogBean.argvalues}
			            </td>
			        </tr>
			        <tr> 
			            <td class="left">操作时间：</td>
			            <td class="right">
			            ${baseOptLogBean.opttime}
			            </td>
			        </tr>
            </tbody>
             <!-- 以下为隐藏域 -->
            <tfoot>
            <tr>
                <td  colspan="2">
	                <input type="hidden" name="baseOptLogBean.opttype" value="${baseOptLogBean.opttype}" />
					<span id="formerror"></span>
	            </tr>
	         </tfoot>
        </table>
    </form>
</div>
</body>
</html>