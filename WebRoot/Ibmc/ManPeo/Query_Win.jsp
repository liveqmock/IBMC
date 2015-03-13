<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/valid.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
	<script src="<c:url value='/My97DatePicker/WdatePicker.js'/>"></script>
	<script src="<c:url value='/Script/plugin/valid/FormValidator.1.0.min.js'/>"></script>
    <script type="text/javascript">
        $(function () {
            $("#closeBtn").bind("click", function () {
                parent.$.jBox.close();
            });
        });
		
    </script>
<body>
<div class="editContainer">
    <div class="errorBlock"></div>
    <!-- 查询条件 --->
    <form action="" method="post" id="form1" name="form1" >
        <table class="editTable">
            <tbody>
            <tr>
                <td width="41%" class="left">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
                <td width="58%" class="right">${manPeoBean.name}</td>
                <td rowspan="5" class="right" >
                	<img src="<c:url value='${manPeoBean.photopath}'/>" name="photoImg" id="photoImg" alt="照片" style="width: 102px; height: 126px" />
                </td>
            </tr>
            <tr>
                <td class="left">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
                <td class="right">${manPeoBean.sex=='0'?'男':'女'}</td>
            </tr>
            <tr>
                <td class="left">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</td>
                <td class="right">${manPeoBean.nation}</td>
            </tr>
            <tr>
                <td class="left">出生年月：</td>
                <td class="right">${manPeoBean.birthday}</td>
            </tr>
            <tr>
                <td class="left">证件号码：</td>
                <td class="right">${manPeoBean.idcard}</td>
            </tr>
            <tr>
                <td class="left">住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</td>
                <td colspan="2" class="right">${manPeoBean.address}</td>
            </tr>
            <tr>
                <td class="left">联系电话：</td>
                <td colspan="2" class="right">${manPeoBean.telephone}</td>
            </tr>
            <tr>
                <td class="left">工作单位：</td>
                <td colspan="2" class="right">${manPeoBean.unitname}</td>
            </tr>
            <tr>
                <td class="left">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
                <td colspan="2" class="right">${manPeoBean.remark}</td>
            </tr>
            </tbody>
            <!-- 以下为隐藏域 -->
            <tfoot>
            <tr>
                <td colspan="3" align="center">
                    <input type="button" id="closeBtn" class="btn btn-info" value="关闭"/>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
</div>
</body>
</html>