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
	<script type="text/javascript">
        $(function(){
        	$("#b_oldpwd").bind("blur",function(){
        		//验证是否和原密码一致
        		var userid = $("#userid").val();   //用户主键ID
        		var oldpwd = $("#b_oldpwd").val();   //原密码
        		$.ajax({
					type: "POST", 
					url: "<c:url value='/Sys/User/chkSysUserPwdIsTrueByJq.shtml'/>", //代码数据
					async: false,
					data:{'sysUserBean.id':userid,'sysUserBean.userpsd':oldpwd},  //标识房产删除
					error:function(data){
					    parent.$.jBox.tip("与数据库连接失败！", 'info');
					},
					success:function(data) {
						//1:表示密码一致允许修改,2:表示密码不一致不允许修改
						if(data == '-1'){
	            			parent.$.jBox.tip("操作失败！", 'info');
						}else if(data == '2'){
							$("#b_msg").html("您输入的密码与原先的不一致，请确认密码后再重新输入！");
							$("#b_msg").show();
	            			$("#saveBtn").attr("disabled",true);   //将确认按钮disabled
						}else{
							$("#b_msg").html("");
							$("#b_msg").hide();
	            			$("#saveBtn").attr("disabled",false);  //启用确认按钮
						}
					}
				});
        	});
        	
        	//保存
            $("#saveBtn").bind("click",function(){
            	//保存修改密码
        		var userid = $("#userid").val();   //用户主键ID
        		var newpwd = $("#b_newpwd").val();   //新密码
        		var repeatpwd = $("#b_repeatpwd").val();   //重复密码
        		if((newpwd!=repeatpwd)||(newpwd=='')){
				    parent.$.jBox.tip("两次输入的密码不一致，请确认！", 'info');
				    return;
        		}
        		$.ajax({
					type: "POST", 
					url: "<c:url value='/Sys/User/saveSysUserPwdByJq.shtml'/>", //代码数据
					async: false,   //同步
					data:{'sysUserBean.id':userid,'sysUserBean.userpsd':newpwd},  //标识房产删除
					error:function(data){
					    parent.$.jBox.tip("与数据库连接失败！", 'info');
					},
					success:function(data) {
						//1:操作成功,-1:操作失败
						if(data == '-1'){
	            			parent.$.jBox.tip("操作失败！", 'info');
						}else if(data == '1'){
	            			parent.$.jBox.tip("操作成功！", 'info');
	            			//清空旧密码，新密码
	            			$("#b_oldpwd").val('');
	            			$("#b_newpwd").val('');
	            			$("#b_repeatpwd").val('');
						}
					}
				});
            });
            
        	//密码初始化
        	$("#initBtn").bind("click",function(){
        		var userid = $("#userid").val();   //用户主键ID
        		if(userid ==''){
           			parent.$.jBox.tip("参数错误，请刷新页面后重试！", 'info');
           			return;
        		}
            	var submit = function (v, h, f) {
		            if (v == 'ok')
		            	$.ajax({
							type: "POST", 
							url: "<c:url value='/Sys/User/saveSysUserInitPwdByJq.shtml'/>", //代码数据
							async: false,   //同步
							data:{'sysUserBean.id':userid},  //标识房产删除
							error:function(data){
							    parent.$.jBox.tip("与数据库连接失败！", 'info');
							},
							success:function(data) {
								//1:操作成功,-1:操作失败
								if(data == '-1'){
			            			parent.$.jBox.tip("操作失败！", 'info');
								}else if(data == '1'){
			            			parent.$.jBox.tip("初始化密码操作成功！", 'info');
			            			//清空旧密码，新密码
			            			$("#b_oldpwd").val('');
			            			$("#b_newpwd").val('');
			            			$("#b_repeatpwd").val('');
									$("#b_msg").html("");
								}
							}
						});
		            return true; //close
		        };
		        parent.$.jBox.confirm("该操作将会还原密码为a_123456，确定还原吗？", "提示", submit);
            });
        });
        
		
    </script>
</head>
<body>
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">密码设置</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<div class="editContainer">
    <div class="errorBlock"></div>
    <!-- 查询条件 --->
    <form action="" method="post" id="form1">
        <table class="editTable">
            <tbody>
				<tr> 
					<td class="left">登录账户：</td>
					<td class="right">${sysUserBean.username}</td>
				</tr>
				<tr> 
					<td class="left">原始密码：</td>
					<td class="right"><input type="password" value="" id="b_oldpwd" class="text_20"/><span id="b_msg" class="validate-error" style="display:none;"></span></td>
				</tr>
				<tr> 
					<td class="left">新密码：</td>
					<td class="right"><input type="password" name="sysUserBean.userpsd" id="b_newpwd" value="" class="text_20"/></td>
				</tr>
				<tr> 
					<td class="left">确认密码：</td>
					<td class="right"><input type="password" name="" id="b_repeatpwd" value="" class="text_20"/></td>
				</tr>
				<tr> 
					<td colspan="2" class="center">
						<input type="button" class="btn btn-info" name="saveBtn" id="saveBtn" value="保存" />
						<input type="button" class="btn btn-disabled" name="initBtn" id="initBtn" value="密码重置" />
						<input type="hidden" name="sysUserBean.id" id="userid" value="${sysUserBean.id}" />
					</td>
				</tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>