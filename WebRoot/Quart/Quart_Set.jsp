<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/valid.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
	<script src="<c:url value='/Script/alertdiv.js'/>"></script>
	<script src="<c:url value='/My97DatePicker/WdatePicker.js'/>"></script>
	<script src="<c:url value='/Script/plugin/valid/FormValidator.1.0.min.js'/>"></script>
    <script type="text/javascript">
        $(function () {
        	bindValid();      //表单提交验证
        	
			checkNetQuartMethods();  //查询验证方法下拉列表
			$("#q_jobid").bind("change",checkNetQuartMethods);
			//$("#q_methname").bind("propertychange",checkNetQuartMethods);
			$("#q_cronexpr").bind("propertychange",checkNetQuartCronexpr);
			
            $("#submitBtn").bind("click", callBack);
            $("#closeBtn").bind("click", function () {
                parent.$.jBox.close();
            });
        });

        function callBack() {
        	$("#form1").attr("action",'<c:url value='/Net/Quart/savenetquart.shtml'/>').trigger("submit");
			if($("#formerror").text() == "ok"){
				return true;
			}
			return false;
        }
		
		//根据select中选中JOBID类中通过jquery查询其相应的方法列表
		function checkNetQuartMethods(){
			$("#q_methname").attr("length",0);
			var temp = '';
			$.ajax({
				url:'<c:url value='/Net/Quart/checkNetQuartMethodsByJq.shtml'/>',   //接收页面
				type: 'post',      													//POST方式发送数据
				async: true,      													//ajax异步
				cache: false, 
				data:  {'netQuartBean.jobid':$("#q_jobid").val()},
				dataType: 'json',
				success: function(data) {
					if(data.length==0){
						temp += '<option value="">'+请选择+'</option>';
					}else{
						var sel ='';
						for(var i =0 ;i<data.length;i++){
							sel ='';
							if('${netQuartBean.methname}'==data[i]){
								sel = 'selected';
							}
							temp += '<option value="'+data[i]+'" '+sel+'>'+data[i]+'</option>';
						}
					} 
					$("#q_methname").append(temp);
				}
			});
		}
		
		function checkNetQuartCronexpr(){
			$("#submitBtn")[0].disabled = true;
			$.ajax({
				url:'<c:url value='/Net/Quart/checkNetQuartCronexprByJq.shtml'/>',  //接收页面
				type: 'post',      													//POST方式发送数据
				async: true,      													//ajax异步
				cache: false, 
				data:  {'netQuartBean.cronexpr':$("#q_cronexpr").val()},
				success: function(data) {   
					if(data=="err"){
						alert("操作失败，与数据库交互是发生错误！");
					}else{
					 	if($.trim(data)=="false"){
					 		$("#showmsg2").html("时间规则不正确！");
					 		$("#showmsg2").addClass("fontcolor_red");
					 		$("#submitBtn")[0].disabled = true;
					 	}else{
					 		$("#showmsg2").removeClass("fontcolor_red");
					 		$("#showmsg2").html("√");
					 		$("#submitBtn")[0].disabled = false;
					 	}
					}
				}
			});
		}
				 
        //验证
        function bindValid(){
			//时间规则
			$("#q_cronexpr").attr("fv-empty","false").attr("fv-empty-msg","时间规则<fmt:message key="format.null" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_q_cronexpr");
			$("#q_cronexpr").parent().append('<span id="valid_q_cronexpr" class="validate-info"></span>');
			
			//JOBID
			$("#q_jobid").attr("fv-empty","false").attr("fv-empty-msg","JOBID<fmt:message key="format.null" bundle="${bms}"/>")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_nation");
			$("#q_jobid").parent().append('<span id="valid_nation" class="validate-info"></span>');
			
			//状态
			$("input[name='netQuartBean.jobstatus']").attr("fv-minlength","1").attr("fv-minlength-msg","状态<fmt:message key="format.null" bundle="${bms}"/>")
					    .attr("fv-msg-success","").attr("fv-msgpanel","valid_jobstatus");
			$("input[name='netQuartBean.jobstatus']").parent().append('<span id="valid_jobstatus" class="validate-info"></span>');
			
			//JOB组
			$("input[name='netQuartBean.jobgroup']").attr("fv-minlength","1").attr("fv-minlength-msg","JOB组<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_jobgroup");
			$("input[name='netQuartBean.jobgroup']").parent().append('<span id="valid_jobgroup" class="validate-info"></span>');
			
			//绑定form表单自定义验证
			$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
		}
		
		//弹出时间规则
		function alertMessageBox(ev){
			
			var objPos = mousePosition(ev);
			var msg = "规则说明"
			   + "<br/>秒 0-59 , - * / "
			   + "<br/>分 0-59 , - * / "
			   + "<br/>小时 0-23 , - * / "
			   + "<br/>日期 1-31 , - * ? / L W C "
			   + "<br/>月份 1-12 或者 JAN-DEC , - * / "
			   + "<br/>星期 1-7 或者 SUN-SAT , - * ? / L C # "
			   + "<br/>年(可选) 留空, 1970-2099 , - * / "
			   + "<br/>"
			   + "<br/>配置案例"
			   + "<br/>0 15 10 * * ? 每天上午10:15触发"
			   + "<br/>0 15 10 15 * ? 每月15日上午10:15触发"
			   + "<br/>0 0/5 14 * * ? 在每天下午2点到下午2:55期间的每5分钟触发";
			messContent="<div style='padding:20px 0 20px 0;text-align:left'>"+msg+"</div>";
			showMessageBox('时间规则',messContent,objPos,350);
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
				<td nowrap class="left">*&nbsp;JOBID：</td>
				<c:choose>
					<c:when test="${empty netQuartBean.id}">
						<td class="right">
							<select id="q_jobid" name="netQuartBean.jobid">
								<c:forEach items="${fileList}" var="d">
									<option value="${d}">${d}</option>
								</c:forEach> 	 		                       	 		
							</select>
						</td>
					</c:when>
					<c:otherwise>
						<td class="right">
							<select id="q_jobid" name="netQuartBean.jobid" disabled>
								<option value="${netQuartBean.jobid}">${netQuartBean.jobid}</option>
                            </select>
                        </td>
					</c:otherwise>
				</c:choose>
				</tr>
				<tr>
					<td nowrap class="left">*&nbsp;JOB组：</td>
					<td class="right">
						<input type="radio" id="q_jobgroup" name="netQuartBean.jobgroup" value="1" ${netQuartBean.jobgroup=='1'?'checked':''}/>永久
						<input type="radio" id="q_jobgroup" name="netQuartBean.jobgroup" value="0" ${netQuartBean.jobgroup=='0'?'checked':''}/>临时
					</td>
				</tr>
				<c:if test="${not empty netQuartBean.id}">
					<tr>
						<td nowrap class="left">*&nbsp;状态：</td>
						<td class="right">
							<input type="radio" id="q_jobstatus" name="netQuartBean.jobstatus" value="1" ${netQuartBean.jobstatus=='1'?'checked':''}/>启用
							<input type="radio" id="q_jobstatus" name="netQuartBean.jobstatus" value="0" ${netQuartBean.jobstatus=='0'?'checked':''}/>禁止                    
                         </td>
                     </tr>
				</c:if>
                <tr>
                    <td nowrap class="left">*&nbsp;时间规则：</td>
                    <td class="right">
                    	<input type="text" id="q_cronexpr" name="netQuartBean.cronexpr" class="text_30" value="${netQuartBean.cronexpr}" maxlength="30" /><span id="showmsg2"></span>
                    </td>
                </tr>
                <tr>
                    <td nowrap class="left">执行方法：</td>
                    <td class="right"><select id="q_methname" name="netQuartBean.methname"></select></td>
                </tr>
                <tr>
                    <td nowrap class="left">方法参数：</td>
                    <td class="right"><input type="text" id="q_param" name="netQuartBean.param" class="text_30" value="${netQuartBean.param}" maxlength="30" /></td>
                </tr>
                <tr>
                    <td nowrap class="left">任务描述：</td>
                    <td class="right"><textarea name="netQuartBean.remark" class="textarea_50_5">${netQuartBean.remark}</textarea></td>
                </tr>
            <c:if test="${empty netQuartBean.id}">
	            <tr>
	                <td class="left">继续新增：</td>
	                <td class="right">
	                	<input type="checkbox" name="gosign" id="gosign" value="1" ${gosign=='1'?'checked':''}/>是否继续新增
	                </td>
	            </tr>
            </c:if>
            </tbody>
            <!-- 以下为隐藏域 -->
            <tfoot>
            <tr>
                <td colspan="2" align="center">
                    <input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
                    <input type="button" id="closeBtn" class="btn btn-info" value="关闭"/>
                    
                    <input type="hidden" name="tokenid" value="${tokenid}" />
                    <input type="hidden" name="netQuartBean.id" value="${netQuartBean.id}" id="pk_id"/>
                    <span style="display:none;" id="formerror"></span>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
</div>
<div class="errorBlock" style="display:block;">
    规则说明: <br/>
    （1）秒 0-59 , - * / <br/> 
    （2） 分 0-59 , - * / <br/> 
    （3）小时 0-23 , - * / <br/>
     （4）日期 1-31 , - * ? / L W C  <br/>
     （5）月份 1-12 或者 JAN-DEC , - * / <br/>
     （6）星期 1-7 或者 SUN-SAT , - * ? / L C #  <br/>
     （7）年(可选) 留空, 1970-2099 , - * / <br/>
     （8）配置案例 ： <br/>
   &nbsp;&nbsp;&nbsp;&nbsp;A-- 0 15 10 * * ? 每天上午10:15触发  <br/>
   &nbsp;&nbsp;&nbsp;&nbsp;B--0 15 10 15 * ? 每月15日上午10:15触发<br/>
   &nbsp;&nbsp;&nbsp;&nbsp;C-- 0 0/5 14 * * ? 在每天下午2点到下午2:55期间的每5分钟触发
</div>
</body>
</html>