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
        	bindValid();
            $("#submitBtn").bind("click", callBack);
            $("#closeBtn").bind("click", function () {
                parent.$.jBox.close();
            });
        });

        function callBack() {
       		var idcard = $("#idcard").val();
        	//表示进行新增操作
        	if(${empty manPeoBean.id}){
        		if(idcard == ''){
        			//在这里进行提交表单是为了能够在页面获取提示的验证信息
        			subForm();
			    	//parent.$.jBox.tip("请输入人员身份信息！", 'info');
        			return;
        		}
        		chkIdCard();    //进行身份验证
        	}else{   //进行修改操作
        		if(idcard != '${manPeoBean.idcard}'){   //修改人员的身份证号码与原先不一致,请确认后重新修改
			    	parent.$.jBox.tip("修改人员的身份证号码与原先不一致,请确认后重新修改！", 'info');
			    	return;
        		}
				subForm();  //允许提交表单
        	}
        }
        
        function subForm(){
        	$("#form1").attr("action",'<c:url value='/Man/Peo/savemanpeomess_rent.shtml'/>').trigger("submit");
			if($("#formerror").text() == "ok"){
				return true;
			}
			return false;
        }
        
        //验证该条记录是否已存在
        function chkIdCard(){
        	var idcard = $("#idcard").val();
        	var querymap = {'manPeoBean.idcard':idcard};
        	$.ajax({
				url:"<c:url value='/Man/Peo/findManPeoBeanByJq.shtml'/>",   //接收页面
				type: 'post',      	//POST方式发送数据
				async: false,      		//ajax同步
				dataType:'json',
				cache: false, 
				data:  querymap,
				error:function(data){
			    	parent.$.jBox.tip("与数据库连接失败！", 'info');
				},
				success:function(data){
					if(data[0].id=="-1"){   //表示该条人员记录不存在查询不出结果集[系统在后台将会自动将其主键ID设置为-1]
						subForm();  //允许提交表单
					}else{
						//获取人员身份中是否是业主标识,租客标识
						var ownersign = data[0].ownersign;
						var rentsign = data[0].rentsign;
						if(rentsign == '${rentsign}'){   //表明该租客人员的身份已存在无法进行新增
			    			parent.$.jBox.tip("证件号码重复！", 'error');
						}else{   //表明该人员在系统中存在只不过系统人员的身份为业主
							$("#ownersign").val(ownersign);
							$("#pk_id").val(data[0].id);  //人员主键ID
							subForm();  //允许提交表单
						}
					}
				}
			});
        }
        
        //验证
        function bindValid(){
			//姓名
			$("#name").attr("fv-empty","false").attr("fv-empty-msg","姓名<fmt:message key="format.null" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_name");
			$("#name").parent().append('<span id="valid_name" class="validate-info"></span>');
			//性别
			$("input[name='manPeoBean.sex']").attr("fv-minlength","1").attr("fv-minlength-msg","性别<fmt:message key="format.null" bundle="${bms}"/>")
						.attr("fv-msg-success","").attr("fv-msgpanel","valid_sex");
			$("input[name='manPeoBean.sex']").parent().append('<span id="valid_sex" class="validate-info"></span>');
			//民族
			$("#nation").attr("fv-empty","false").attr("fv-empty-msg","民族<fmt:message key="format.null" bundle="${bms}"/>")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_nation");
			$("#nation").parent().append('<span id="valid_nation" class="validate-info"></span>');
			//出生年月
			$("#birthday").attr("fv-empty","false").attr("fv-empty-msg","出生年月<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_birthday");
			$("#birthday").parent().append('<span id="valid_birthday" class="validate-info"></span>');
			//证件号码
			$("#idcard").attr("fv-empty","false").attr("fv-empty-msg","证件号码<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_idcard");
			$("#idcard").parent().append('<span id="valid_idcard" class="validate-info"></span>');
			//住址
			$("#address").attr("fv-empty","false").attr("fv-empty-msg","住址<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_address");
			$("#address").parent().append('<span id="valid_address" class="validate-info"></span>');
			//联系电话
			$("#telephone").attr("fv-empty","false").attr("fv-empty-msg","联系电话<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_telephone");
			$("#telephone").parent().append('<span id="valid_telephone" class="validate-info"></span>');
			
			//绑定form表单自定义验证
			$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
		}
        
    </script>
</head>
<body>
<div class="editContainer">
    <div class="errorBlock"></div>
    <!-- 查询条件 --->
    <form action="" method="post" id="form1" name="form1" >
        <table class="editTable">
            <tbody>
            <tr>
                <td width="41%" class="left">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
                <td width="58%" class="right">
                    <input type="text" name="manPeoBean.name" id="name" value="${manPeoBean.name}" class="text_15" />
                </td>
                <td rowspan="5" class="right">
                <c:choose>
               		<c:when test="${not empty manPeoBean.id}">
               			<img src="<c:url value='${manPeoBean.photopath}'/>" name="photoImg" id="photoImg" alt="照片" style="width: 102px; height: 126px" />
               		</c:when>
               		<c:otherwise>
               			<img src="" name="photoImg" id="photoImg" alt="照片" style="width: 102px; height: 126px" />
               		</c:otherwise>
               	</c:choose>
                </td>
            </tr>
            <tr>
                <td class="left">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
                <td class="right">
                    <input type="radio" name="manPeoBean.sex" id="gen" value="0" ${manPeoBean.sex=='0'?'checked':''}/>男 &nbsp;&nbsp;&nbsp;
                    <input type="radio" name="manPeoBean.sex" id="wom" value="1" ${manPeoBean.sex=='1'?'checked':''}/>女
                </td>
            </tr>
            <tr>
                <td class="left">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</td>
                <td class="right">
                    <input type="text" name="manPeoBean.nation" id="nation" value="${manPeoBean.nation}" class="text_10" />
                </td>
            </tr>
            <tr>
                <td class="left">出生年月：</td>
                <td class="right">
                    <input type="text" name="manPeoBean.birthday" id="birthday" value="${manPeoBean.birthday}" class="text_12 Wdate" onClick="WdatePicker()"/>
                </td>
            </tr>
            <tr>
                <td class="left">证件号码：</td>
                <td class="right">
                    <input type="text" name="manPeoBean.idcard" id="idcard" value="${manPeoBean.idcard}" class="text_20" />
                    <span id="showmsg"></span>
                </td>
            </tr>
            <tr>
                <td class="left">住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</td>
                <td colspan="2" class="right">
                    <input type="text" name="manPeoBean.address" id="address" value="${manPeoBean.address}" class="text_30" />
                </td>
            </tr>
            <tr>
                <td class="left">联系电话：</td>
                <td colspan="2" class="right">
                    <input type="text" name="manPeoBean.telephone" id="telephone" value="${manPeoBean.telephone}" class="text_30" />
                </td>
            </tr>
            <tr>
                <td class="left">工作单位：</td>
                <td colspan="2" class="right">
                    <input type="text" name="manPeoBean.unitname" id="unitname" value="${manPeoBean.unitname}" class="text_50" />
                </td>
            </tr>
            <tr>
                <td class="left">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
                <td colspan="2" class="right">
                    <textarea name="manPeoBean.remark" class="textarea_50_3">${manPeoBean.remark}</textarea>
                </td>
            </tr>
            </tbody>
            <!-- 以下为隐藏域 -->
            <tfoot>
            <tr>
                <td colspan="3" align="center">
                    <input type="button" name="beginread" id="beginread" class="btn btn-danger" value="获取身份证信息" onclick="readmess();"/>
                    <input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
                    <input type="button" id="closeBtn" class="btn btn-info" value="关闭"/>
                    
                    <input type="hidden" name="tokenid" value="${tokenid}" /> 
                    <input type="hidden" name="ownersign" value="${ownersign}" />
                    <input type="hidden" name="rentsign" value="${rentsign}" />
                    
                    <input type="hidden" name="manPeoBean.id" value="${manPeoBean.id}" id="pk_id"/>
                    <input type="hidden" name="manPeoBean.photobase64" value="${manPeoBean.photobase64}" id="photobase64" />
                    <input type="hidden" name="manPeoBean.ownersign" value="${manPeoBean.ownersign}" id="ownersign"/>
                    <input type="hidden" name="manPeoBean.rentsign" value="${manPeoBean.rentsign}" id="rentsign"/>
                    <span style="display:none;" id="formerror"></span>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
    <object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0" ></object>
	<script language="javascript" type ="text/javascript">
		function ClearForm() {
			document.all['manPeoBean.name'].value = ""; 
			document.all['manPeoBean.sex'].value = ""; 
	        document.all['manPeoBean.nation'].value = ""; 
	        document.all['manPeoBean.birthday'].value = ""; 
	        document.all['manPeoBean.address'].value = ""; 
	        document.all['manPeoBean.idcard'].value = ""; 
            document.all['photoImg'].src = "";
            document.all['photobase64'].value = "";
	        return true;
		}
		function readmess() {
			var CVR_IDCard = document.getElementById("CVR_IDCard");					
			var strReadResult = CVR_IDCard.ReadCard();
			if(strReadResult == "0"){
				ClearForm();
				document.all['manPeoBean.name'].value = CVR_IDCard.Name;
	            var sextemp = CVR_IDCard.Sex;
	            if(sextemp=='男'){
	            	document.all['manPeoBean.sex'].value='1';
	            	$("#gen").attr("checked",true);
	            }else if(sextemp=='女'){
	            	document.all['manPeoBean.sex'].value='2';
	            	$("#wom").attr("checked",true);
	            }else{
	            	document.all['manPeoBean.sex'].value='0';
	            }
	            document.all['manPeoBean.nation'].value = CVR_IDCard.Nation; 
	            var birthday = CVR_IDCard.Born;
	            document.all['manPeoBean.birthday'].value = birthday.replace("年","-").replace("月","-").replace("日","");     
	            document.all['manPeoBean.address'].value = CVR_IDCard.Address; 
	            document.all['manPeoBean.idcard'].value = CVR_IDCard.CardNo; 
	            document.all['photoImg'].src = "data:image/jpg;base64,"+CVR_IDCard.Picture;
                document.all['photobase64'].value = CVR_IDCard.Picture;
			}else{
	            //ClearForm();
	            parent.$.jBox.tip(strReadResult, 'warning');
			}
		}
	</script>
</div>
</body>
</html>