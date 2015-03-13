<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
</head>
<body>
<div class="editContainer">
    <div class="errorBlock"></div>
    <!-- 查询条件 --->
    <div style="float:left;width:51%;">
        <table class="editTable">
            <tbody>
            <tr>
                <td colspan="5" style="font-weight: bold;">租客信息</td>
            </tr>
            <tr>
                <td class="left">姓名：</td>
                <td width="30%" colspan="3" class="right">${manPeoBean.name}</td>
                <td width="22%" rowspan="5" class="right">
                    <img src="<c:url value='${manPeoBean.photopath}'/>" name="photoImg" id="photoImg" alt="照片" height="156" width="112"/>
                </td>
            </tr>
            <tr>
                <td class="left">性别：</td>
                <td class="right"><c:if test="${not empty manPeoBean.sex}">${manPeoBean.sex=='0'?'男':'女'}</c:if></td>
                <td class="left">民族：</td>
                <td class="right">${manPeoBean.nation}</td>
            </tr>
            <tr>
                <td class="left">出生年月：</td>
                <td class="right" colspan="3">${manPeoBean.birthday}</td>
            </tr>
            <tr>
                <td class="left">身份证号码：</td>
                <td class="right" colspan="3">${manPeoBean.idcard}</td>
            </tr>
            <tr>
                <td class="left">联系号码：</td>
                <td class="right" colspan="3">${manPeoBean.telephone}</td>
            </tr>
            <tr>
                <td class="left">地址：</td>
                <td colspan="4" class="right">${manPeoBean.address}</td>
            </tr>
            <tr>
                <td class="left">工作单位：</td>
                <td colspan="4" class="right">${manPeoBean.unitname}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div style="float:right;width:49%;">
        <table class="editTable">
            <tbody>
            <tr>
                <td colspan="3" style="font-weight: bold;">刷卡信息</td>
            </tr>
            <tr>
                <td width="22%" rowspan="5" class="right">
                    <img src="<c:url value='/Man/Card/findCardRecordImageByJq.shtml'/>?touchimg=${manCardRecordBean.touchimg}" id="photoImg1" alt="刷卡相片" height="156" width="112" />
                </td>
                <td class="left">卡ID：</td>
                <td width="30%" class="right">${manCardRecordBean.cardno}</td>
            </tr>
            <tr>
                <td class="left">房间：</td>
                <td class="right">${manCardRecordBean.roomname}</td>
            </tr>
            <tr>
                <td class="left">类别：</td>
                <td class="right">
                	<c:choose>
                		<c:when test="${CARDTYPE_OWNER==manCardRecordBean.cardtype}">业主卡</c:when>
                		<c:when test="${CARDTYPE_TEMP==manCardRecordBean.cardtype}">临时卡</c:when>
                		<c:when test="${CARDTYPE_REGULAR==manCardRecordBean.cardtype}">正式卡</c:when>
                	</c:choose>
                </td>
            </tr>
            <tr>
                <td class="left">业主：</td>
                <td class="right">${manCardRecordBean.ownername}</td>
            </tr>
            <tr>
                <td class="left">门口机：</td>
                <td class="right">${manCardRecordBean.equipname}</td>
            </tr>
            <tr >
                <td class="left">租住地址：</td>
                <td colspan="2" class="right">${manCardRecordBean.housename}</td>
            </tr>
            <tr>
                <td class="left">刷卡时间：</td>
                <td colspan="2" class="right">${manCardRecordBean.touchdate}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>