<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="../../Include/TagLib.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link rel="stylesheet" href="../../Style/main.css"/>
	<link rel="stylesheet" href="../../Style/bootstrap.css"/>
	<link rel="stylesheet" href="../../Ztree/css/zTreeStyle/zTreeStyle.css" />
	<link rel="stylesheet" href="../../Style/valid.css"/>
	<script type="text/javascript" src="../../Script/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="../../Ztree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="../../Ztree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="../../Script/plugin/valid/FormValidator.1.0.min.js"></script>
	<script type="text/javascript" src="../../My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		$(function(){
			//初始化部门,班组树状
			onInitTree();
			bindValid();
			$("#submitBtn").bind("click",callBack);
			$("#closeBtn").bind("click",function(){
				parent.$.jBox.close();
			});
			
		});
		
		//设置radio属性
        var setting = {
            check: {
                enable: true,
                chkStyle: "radio",
                radioType: "level"
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };
        setting.check.radioType = "all";
		
		function onInitTree(){
			//获取部门班组列表
			var zNodes = new Array();
			var url = "<c:url value='/Data/OrgZ/findDataOrganZTreeListByJq.shtml'/>";
			var querymap = {'querymap.fatherid':'${querymap.fatherid}','querymap.unitid':'${querymap.unitid}'};
			$.ajax({
				type: "POST", 
				url: url,
				data:querymap,
				async : true,
				dataType:"json",
				success:
				function(data) {
					var fatherid = ","+'${dataUserZBean.groupid}'+",";
					for(var i=0;i<data.length;i++){
						zNodes[i]=({id:data[i].id, pId:data[i].fatherid,name:(data[i].organname),checked:(fatherid.indexOf(","+data[i].id+",")>=0)?true:false});
					}
					//初始化题库类别
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
					//初始化下拉列表框[展开子节点]
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					nodes = zTree.getCheckedNodes(true);
					for (var i=0, l=nodes.length; i<l; i++) {
						//展开树状图
						zTree.expandNode(nodes[i].getParentNode(),true,false,false,false);
					}
				}
			});
		}
		
		function callBack(){
			var str = getFatheridStr();
			if(str ==''){
				parent.$.jBox.tip("请选择需要新增人员的部门班组！", 'info');
	          	return ;
			}
			//获取部门id若为-1标识非法选择机构
			var tempDeptIdV = $("#b_deptid").val();
			if(tempDeptIdV =='-1'){
				parent.$.jBox.tip("人员只能挂靠在班组或则自班组下！", 'info');
	          	return ;
			}
			
			$("#b_groupid").val(str);
			$("#form1").attr("action",'<c:url value='/Data/UserZ/savedatauserzbean.shtml'/>').trigger("submit");
			if($("#formerror").text() == "ok"){
				return true;
			}
			return false;
		}
		
		function getFatheridStr(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = zTree.getCheckedNodes(true);
			var id  = "";
			var level = '';
			var deptid = '';
			if(nodes.length>0){
		        id = (nodes[0].id);
		        level = nodes[0].level;
		        if(level=='2'){
		        	deptid = nodes[0].getParentNode().getParentNode().id
		        }else if(level=='1'){
		        	deptid = nodes[0].getParentNode().id
		        }else{
		        	deptid = "-1";
		        }
		        $("#b_deptid").val(deptid);
		    }
			return id
		}
		
		function bindValid(){
			//绑定姓名
			$("#b_username").attr("fv-maxlength","30").attr("fv-maxlength-msg","字数不能超过30字")
							.attr("fv-empty","false").attr("fv-empty-msg","姓名不能为空")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_username");
			$("#b_username").parent().append('<span id="valid_username" class="validate-info"></span>');
			
			//绑定身份证号
			$("#b_userid").attr("fv-empty","false").attr("fv-empty-msg","身份证号不能为空")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_userid");
			$("#b_userid").parent().append('<span id="valid_userid" class="validate-info"></span>');
			
			//绑定说明[备注]
			$("#b_remark").attr("fv-maxlength","1000").attr("fv-maxlength-msg","说明字数不能超过1000字")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_remark");
			$("#b_remark").parent().append('<span id="valid_remark" class="validate-info"></span>');
			
			//绑定排序号
			$("#b_sortnum").attr("fv-empty","false").attr("fv-empty-msg","排序号不能为空")
							.attr("fv-datatype","Int16").attr("fv-datatype-msg","排序号只能为数字")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_sortnum");
			$("#b_sortnum").parent().append('<span id="valid_sortnum" class="validate-info"></span>');
			
			//绑定form表单自定义验证
			$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
		}
		
	</script>
	<style type="text/css">
         #ztree{
            width:34.5%; float:left; height:350px; border:1px solid red;margin:0px; padding:0px; border:none;
            background:#FFFFFF url(../../Images/organ-bg.jpg) right repeat-y;
         }
         .top-nav-bg{background:#f9f9f9 url(../../Images/organ-top-bg.jpg) top left repeat-x; height:40px; width:99%;}
         #treeDemo{ height:85%;overflow:auto; background:none;overflow-y:auto; width:96%;}
         .top-nav-bg span{ font: bold 12px/32px Arial, sans-serif; margin-left:30%; }
    </style>
</head>
<body>
<div id="ztree">
    <div class="top-nav-bg"><span>关联部门班组</span></div>
    <ul id="treeDemo" class="ztree"></ul>
</div>
<div class="editContainer" style="width:65%; float:right;">
    <!-- 查询条件 --->
    <form name="form1" method="post" action="" id="form1">
        <table class="editTable">
			<tbody>
		        <tr>
					<!--左侧表格项-->
					<td nowrap class="left">姓名：</td>
					<!--右侧表格项-->
					<td class="right">
					    <input type="text" name="dataUserZBean.username" id="b_username" class="text_20" value="${dataUserZBean.username}" />
					</td>
				</tr>
				<tr>
					<td nowrap class="left">身份证号：</td>
					<td class="right">
						<input type="text" id="b_userid" name="dataUserZBean.userid" class="text_20" maxlength="18" value="${dataUserZBean.userid}"/>
						<span id="showmsg1" class=""></span>
					</td>
				</tr>
				<tr>
					<td nowrap class="left">工号：</td>
					<td class="right">
						<input type="text" id="b_workid" name="dataUserZBean.workid" class="text_10" maxlength="10" value="${dataUserZBean.workid}"/>
						<span id="showmsg2" class=""></span>
					</td>
				</tr>
				<tr>
					<td nowrap class="left">&nbsp;性别：</td>
					<td class="right">
						<c:forEach items="${sexlist}" var="sex">
							<input type="radio" id="b_sex" name="dataUserZBean.sex" value="${sex.id}" ${dataUserZBean.sex==sex.id?'checked':''} />${sex.codename}
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td nowrap class="left">&nbsp;密码：</td>
					<td class="right">
						<input type="password" id="pass" name="dataUserZBean.loginpsd" value="" />
					</td>
				</tr>
				<tr>
					<td nowrap class="left">&nbsp;岗位名称：</td>
					<td class="right">
						<input type="text" id="b_mpost" name="dataUserZBean.mpost" class="text_20" maxlength="18" value="${dataUserZBean.mpost}"/>
					</td>
				</tr>
				<tr>
					<td nowrap class="left">&nbsp;出生时间：</td>
				 	<td class="right"><input type="text" id="b_birthday" name="dataUserZBean.birthday" value="${dataUserZBean.birthday}" class="text_10" maxlength="10" onClick="WdatePicker()" /><span class="fontcolor_gray">（格式：1950-01-01，或双击文本框选择）</span></td>
				</tr>
				<tr>
					<td nowrap class="left">&nbsp;人员状态：</td>
					<td class="right" id="t">
						<input type="text" class="text_10line" maxlength="10" value="${dataUserZBean.statename}" />
						<input type="hidden"  name="dataUserZBean.stateid"  value="${dataUserZBean.stateid}"/>
    				</td>
				</tr>
				<tr>
					<td nowrap class="left">备注：</td>
					<td class="right"><textarea id="b_remark" name="dataUserZBean.remark" class="textarea_50_3" >${dataUserZBean.remark}</textarea></td>
				</tr>
				<tr>
				    <td nowrap class="left">&nbsp;排序号：</td>
				    <td class="right"><input type="text" id="b_sortnum" name="dataUserZBean.sortnum" class="text_10" value="${dataUserZBean.sortnum}" maxlength="10"/></td>
				</tr>
	            <!-- 以下为隐藏域 -->
	            <tr>
	                <td colspan="2">
	                	<input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
	                	<input type="button" id="closeBtn" class="btn btn-info" value="关闭"/>
					    <input type="hidden" name="tokenid" value="${tokenid}" />
		                <input type="hidden" name="querymap.fatherid" value="${querymap.fatherid}" />
		                <input type="hidden" name="dataUserZBean.id" value="${dataUserZBean.id}" />
		                <input type="hidden" name="dataUserZBean.unitid" id="b_unitid" value="${dataUserZBean.unitid}"/>
		                <input type="hidden" name="dataUserZBean.groupid" id="b_groupid" value="${dataUserZBean.groupid}"/>
		                <input type="hidden" name="dataUserZBean.deptid" id="b_deptid" value="${dataUserZBean.deptid}"/>
	                </td>
	            </tr>
	            <tr style="display:none;">
	                <td colspan="2">
						<span id="formerror"></span>
	                </td>
	            </tr>
		    </tbody>
        </table>
   </form>
</div>
</body>
</html>