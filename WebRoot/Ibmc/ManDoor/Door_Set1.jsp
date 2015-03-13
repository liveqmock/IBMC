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
        	if(${not empty manDoorBean.id}){
        		$("#td_1,#td_2").show();
        	}
        	bindValid();
            $("#submitBtn").bind("click", callBack);
            $("#closeBtn").bind("click", function () {
                parent.$.jBox.close();
            });
            //设备厂商
            $("#facid").bind("change",function(){
            	$("#showmsg").hide();
            	var facid = $("#facid").val();
            	if(facid==''){
            		$("#td_1,#td_2").hide();
            	}else{
					$("#td_1,#td_2").show();
            		mkModel();
            	}
            });
        });

        function callBack() {
			$("#form1").attr("action",'<c:url value='/Man/Door/savemandoor.shtml'/>').trigger("submit");
			if($("#formerror").text() == "ok"){
				return true;
			}
			return false;
        }
        
        //验证
        function bindValid(){
			//设备名称
			$("#name").attr("fv-empty","false").attr("fv-empty-msg","设备名称<fmt:message key="format.null" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_name");
			$("#name").parent().append('<span id="valid_name" class="validate-info"></span>');
			//设备厂商
			$("#facid").attr("fv-empty","false").attr("fv-empty-msg","设备厂商<fmt:message key="format.null" bundle="${bms}"/>")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_facid");
			$("#facid").parent().append('<span id="valid_facid" class="validate-info"></span>');
			//设备型号
			$("#confid").attr("fv-empty","false").attr("fv-empty-msg","设备型号<fmt:message key="format.null" bundle="${bms}"/>")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_confid");
			$("#confid").parent().append('<span id="valid_confid" class="validate-info"></span>');
			//设备IP
			$("#doorip").attr("fv-empty","false").attr("fv-empty-msg","设备IP<fmt:message key="format.null" bundle="${bms}"/>")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_doorip");
			$("#doorip").parent().append('<span id="valid_doorip" class="validate-info"></span>');
			//设备MAC
			$("#doormac").attr("fv-empty","false").attr("fv-empty-msg","设备MAC<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_doormac");
			$("#doormac").parent().append('<span id="valid_doormac" class="validate-info"></span>');
			//网关
			$("#gateway").attr("fv-empty","false").attr("fv-empty-msg","网关<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_gateway");
			$("#gateway").parent().append('<span id="valid_gateway" class="validate-info"></span>');
			//子网掩码
			$("#submask").attr("fv-empty","false").attr("fv-empty-msg","子网掩码<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_submask");
			$("#submask").parent().append('<span id="valid_submask" class="validate-info"></span>');
			
			//绑定form表单自定义验证
			$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
		}
        
        function mkModel(){
        	var facid = $("#facid").val();
        	$.ajax({
				type: "POST", 
				url:"<c:url value='/Man/Dev/findManFacModelByJq.shtml'/>", //代码数据
				async:false,
				data:{'manFactoryBean.id':facid},
				dataType:"json",
				error:function(data){
				    parent.$.jBox.tip("与数据库连接失败！", 'info');
				},
				success:function(data) {
					if(data.length==0){
						$("#showmsg").addClass("validate-error").html("请新增该厂商型号！");
						$("#showmsg").show();
					}else{
						var str = "";
						//通过jquery方法进行循环编译
						$.each(data,function(i,d){ 
							str += "<option value="+d.id+">"+d.facmodel+"</option>";
						});
						//先清空在添加
						$("#confid option").not(":first").remove();
						$("#confid").append(str);
					}
				}
			});
		}
        
    </script>
    </head>
<body>
<div class="editContainer">
    <div class="errorBlock"></div>
    <!-- 查询条件 --->
    <form action="" method="post" id="form1" name="form1">
    	<table class="editTable">
            <tbody>
            <tr>
                <td class="left">设备名称：</td>
                <td class="right" colspan="3">
                    <input type="text" name="manDoorBean.name" id="name" value="${manDoorBean.name}" class="text_50" />
                </td>
            </tr>
            <tr>
                <td class="left">设备厂商：</td>
                <td class="right">
                	<select name="manDoorBean.facid" id="facid">
                		<option value=''>请选择</option>
                		<c:forEach items="${facList}" var="d">
                			<option value='${d.id}' ${manDoorBean.facid==d.id?'selected':''}>${d.facname}</option>
                		</c:forEach>
               		</select>
                </td>
                <td class="left"><span style="display:none;" id="td_1">设备型号：</span></td>
                <td class="right">
                	<span  id="td_2" style="display:none;">
                	<select name="manDoorBean.confid" id="confid">
                		<option value=''>请选择</option>
                		<c:forEach items="${modelList}" var="d">
                			<option value='${d.id}' ${manDoorBean.confid==d.id?'selected':''}>${d.facmodel}</option>
                		</c:forEach>
               		</select>
               		</span>
               		<span style="display:none;" id="showmsg"></span>
                </td>
            </tr>
            <tr>
                <td class="left">设备IP：</td>
                <td class="right" width="350px">
                    <input type="text" name="manDoorBean.doorip" id="doorip" value="${manDoorBean.doorip}" class="text_20" />
                </td>
                <td class="left">设备MAC：</td>
                <td class="right" width="380px">
                    <input type="text" name="manDoorBean.doormac" id="doormac" value="${manDoorBean.doormac}" class="text_20" />
                </td>
            </tr>
            <tr>
                <td class="left">网关：</td>
                <td class="right">
                    <input type="text" name="manDoorBean.gateway" id="gateway" value="${manDoorBean.gateway}" class="text_20" />
                </td>
                <td class="left">子网掩码：</td>
                <td class="right">
                    <input type="text" name="manDoorBean.submask" id="submask" value="${manDoorBean.submask}" class="text_20" />
                </td>
            </tr>
            <c:if test="${not empty manDoorBean.id}">
	            <tr>
	                <td class="left">软件版本：</td>
	                <td class="right">${manDoorBean.softver}</td>
	                <td class="left">硬件版本：</td>
	                <td class="right">${manDoorBean.hardver}</td>
	            </tr>
            </c:if>
            <tr>
                <td class="left">备注：</td>
                <td class="right" colspan="3">
                    <textarea name="manDoorBean.remark" class="textarea_all_3">${manDoorBean.remark}</textarea>
                </td>
            </tr>
            <tr>
                <td class="left">操作人：</td>
                <td class="right" colspan="3">${request.opraName}</td>
            </tr>
            <c:if test="${empty manDoorBean.id}">
	            <tr>
	                <td class="left">继续新增：</td>
	                <td colspan="3" class="right">
	                	<input type="checkbox" name="gosign" id="" value="1" ${gosign=='1'?'checked':''}/>是否继续新增
	                </td>
	            </tr>
            </c:if>
            </tbody>
            <!-- 以下为隐藏域 -->
            <tfoot>
            <tr>
                <td colspan="4" align="center">
                    <input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
                    <input type="button" id="closeBtn" class="btn btn-info" value="关闭"/>
                    
                    <input type="hidden" name="tokenid" value="${tokenid}" />
                    <input type="hidden" name="manDoorBean.id" value="${manDoorBean.id}" />
                    <input type="hidden" name="manDoorBean.optuserid" value="${manDoorBean.optuserid}" />
                    <span style="display:none;" id="formerror"></span>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
</div>
</body>
</html>