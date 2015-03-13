<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/valid.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Ztree/css/zTreeStyle/zTreeStyle.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
    <script src="<c:url value='/Ztree/js/jquery.ztree.core-3.5.js'/>"></script>
    <script src="<c:url value='/Ztree/js/jquery.ztree.excheck-3.5.js'/>"></script>
	<script src="<c:url value='/Script/plugin/valid/FormValidator.1.0.min.js'/>"></script>
    <style type="text/css">
        #ztree{
            width:30%; float:left; height:340px; border:1px solid red;margin:0px; padding:0px; border:none;
            background:#FFFFFF url('<c:url value="/Images/organ-bg.jpg"/>') right repeat-y;
        }
        .top-nav-bg{background:#f9f9f9 url('<c:url value="/Images/organ-top-bg.jpg"/>') top left repeat-x; height:40px; width:99%;}
        #treeDemo{ height:85%;overflow:auto; background:none;overflow-y:auto; width:96%;}
        .top-nav-bg span{ font: bold 12px/32px Arial, sans-serif; margin-left:30%; }
    </style>
</head>
<body>
<div id="ztree">
    <div class="top-nav-bg"><span>管理区域</span></div>
    <ul id="treeDemo" class="ztree"></ul>
</div>
<div class="editContainer" style="width:70%; float:right;">
    <!-- 查询条件 --->
    <form name="form1" method="post" action="" id="form1">
        <table class="editTable">
            <tbody>
            <tr>
                <!--左侧表格项-->
                <td class="left">所属角色:</td>
                <!--右侧表格项-->
                <td class="right">
                    	 <select name="sysUserBean.roleid" id="roleid" onChange="JavaScript:findOrder();">
                     		<option value="">===请选择===</option>
							<c:forEach items="${roleList}" var="r">
								<option value="${r.id}" ${r.id==sysUserBean.roleid?'selected':''}>${r.rolename}</option>
							</c:forEach>
						</select>
                </td>
            </tr>
             <tr>
                <!--左侧表格项-->
                <td class="left">管理级别：</td>
                <!--右侧表格项-->
                <td class="right">
                    <select name="sysUserBean.levsign" id="q_levsign" >
                     		<option value="">===请选择===</option>
                     		<c:forEach items="${CommLevSign}" var="lev">
                     			<option value="${lev.key}" <c:if test="${lev.key==sysUserBean.levsign}">selected</c:if>>${lev.value}</option>
                     		</c:forEach>
                     	</select>
                </td>
            </tr>
            <tr>
                <!--左侧表格项-->
                <td class="left">用户名：</td>
                <!--右侧表格项-->
                <td class="right">
                    <input type="text" name="sysUserBean.username" id="b_username" class="text_20" value="${sysUserBean.username}" />（系统登录名）
                </td>
            </tr>
            <tr>
                <td class="left">联系电话：</td>
                <td class="right">
                    <input type="text" id="b_telephone" name="sysUserBean.telephone" class="text_20" maxlength="18" value="${sysUserBean.telephone}"/>
                </td>
            </tr>
            <tr>
                <td class="left">手机号：</td>
                <td class="right">
                    <input type="text" id="b_phone" name="sysUserBean.phone" class="text_20" maxlength="18" value="${sysUserBean.phone}"/>
                </td>
            </tr>
            <tr>
                <td nowrap class="left">备注：</td>
                <td class="right"><textarea id="b_remark" name="sysUserBean.remark" class="textarea_50_5" >${sysUserBean.remark}</textarea></td>
            </tr>
            <tr>
                <td nowrap class="left">&nbsp;排序号：</td>
                <td class="right"><input type="text" id="b_sortnum" name="sysUserBean.userorder" class="text_10" value="${sysUserBean.userorder}" maxlength="10"/></td>
            </tr>
            </tbody>
            <!-- 以下为隐藏域 -->
            <tfoot>
            <tr>
                <td colspan="2" align="center">
    				<span id="valid_areaIdStr" class="validate-info"></span>
                    <input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
                    <input type="button" id="closeBtn" class="btn btn-info" value="关闭"/>
                     <input type="hidden" name="tokenid" value="${tokenid}" />
                     <input type="hidden" name="sysUserBean.id" value="${sysUserBean.id}" />
                     <input type="hidden" name="areaIdStr" id="areaIdStr" value="" />
                     <span style="display:none;" id="formerror"></span>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
</div>
</body>
    <script type="text/javascript">
         $(function(){
            $("#submitBtn").bind("click",callBack);
            $("#closeBtn").bind("click",function(){
                parent.$.jBox.close();
            });
       		bindValid();
            initTree();
         });
         
        
         function callBack(){
		 	//获取选择节点值,若无选择则提示
		 	var areaArr = (getSelectedIdArrOfTree());
		 	var areaIdStr  = areaArr.join(",") || "";
		 	$("#areaIdStr").val(areaIdStr);
            $("#form1").attr("action",'<c:url value='/Sys/User/savesysuser.shtml'/>').trigger("submit");
			if($("#formerror").text() == "ok"){
				return true;
			}
			return false;
        }
        
          //验证
        function bindValid(){
			//所属角色
			$("#roleid").attr("fv-empty","false").attr("fv-empty-msg","所属角色<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_roleid");
			$("#roleid").parent().append('<span id="valid_roleid" class="validate-info"></span>');
			
			//管理级别
			$("#q_levsign").attr("fv-empty","false").attr("fv-empty-msg","管理级别<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_q_levsign");
			$("#q_levsign").parent().append('<span id="valid_q_levsign" class="validate-info"></span>');
			
			
			//用户名
			$("#b_username").attr("fv-empty","false").attr("fv-empty-msg","用户名<fmt:message key="format.null" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_username");
			$("#b_username").parent().append('<span id="valid_username" class="validate-info"></span>');
			
			//联系电话
			$("#b_telephone").attr("fv-empty","false").attr("fv-empty-msg","联系电话<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_telephone");
			$("#b_telephone").parent().append('<span id="valid_telephone" class="validate-info"></span>');
			//手机号
			$("#b_phone").attr("fv-empty","false").attr("fv-empty-msg","手机号<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_phone");
			$("#b_phone").parent().append('<span id="valid_phone" class="validate-info"></span>');
			
			
			//管理区域
			$("#areaIdStr").attr("fv-empty","false").attr("fv-empty-msg","管理区域<fmt:message key="format.null" bundle="${bms}"/>")
					  .attr("fv-msg-success","").attr("fv-msgpanel","valid_areaIdStr");
			//绑定form表单自定义验证
			$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
		}
		
		//排序号
        function findOrder(){
         $.ajax({
                url: '<c:url value="/Sys/User/findSysUserOrderByJq.shtml"/>',
                data:{'sysUserBean.roleid':$("#roleid").val()},
                type: 'post',
                dataType: 'json',
                error: function () {
                    alert('Error loading XML document');
                },
                success: function (data) {
                	$("#b_sortnum").val(data);
                }
            });
          }
        
	     function initTree() {
	         $.ajax({
	             url: '<c:url value="/Sys/Comm/listRegionByJq.shtml"/>',
	             data:{'querymap.needFilterScope':'1','querymap.maxLev':'3'},
	             type: 'post',
	             dataType: 'json',
	             error: function () {
	                 alert('Error loading XML document');
	             },
	             success: function (data) {
	             	var newAreaIdStr = '${areaIdStr}'+",";
	     			 var zNodes =[];
	                 for(var i=0;i<data.length;i++){
	                     zNodes[i]=({
		                     			id:data[i].id, 
		                     			pId:data[i].parentid,
		                     			name:data[i].commname,
		                     			commlev:data[i].commlev,
		                     			open:true,
		                     			// checked:("${sysCommunityBean.parentid}"==data[i].id?true:false),
		                     			checked:(newAreaIdStr.indexOf(","+data[i].id+",")>=0?true:false),
		                     			selected:("${sysCommunityBean.id}"==data[i].id?true:false),
		                     			nocheck:false
	                     		    });
	                 }
	                 $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	             }
	         });
	     }
     
	     //设置checkbox属性
	     var setting = {
	         check: {
	             enable: true,
	             chkStyle: "checkbox",
	             chkboxType :{ "Y" : "", "N" : "" },
	             radioType:'all'
	         },
	         data: {
	             simpleData: {
	                 enable:true,
	                 idKey: "id",
	                 pIdKey: "pId"
	             }
	         },
	         view: {
	             dblClickExpand: true,
	             showLine: true,
	             selectedMulti: false
	         }
	     };
			
		//获取选中节点
		function getSelectedIdArrOfTree(){
			//获取管理级别
			var levSignV = $("#q_levsign").val();
			var isBreak = false;
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			nodes = zTree.getCheckedNodes(true);
			var nodeArr = new Array();
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].commlev!=levSignV){
					isBreak = true;
				}
				nodeArr.push(nodes[i].id);
			}
			if(isBreak){
			    //提示

			   //  parent.$.jBox.tip("[管理区域]中所选的区域与[管理级别]不匹配]！", 'info');
	          parent.$.jBox.confirm("<font class=\"fontcolor_red\">[管理区域]</font>中所选的区域与<font class=\"fontcolor_red\">[管理级别]</font>不匹配！", "提示", isBreak);
			 	return new Array();
			}
			return nodeArr;
		}
    </script>
</html>