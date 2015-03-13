<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link rel="stylesheet" href="../../Style/main.css"/>
	<link rel="stylesheet" href="../../Style/bootstrap.css"/>
	<link rel="stylesheet" href="../../Style/valid.css"/>
	<link rel="stylesheet" href="../../Ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
    <script type="text/javascript" src="../../Script/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="../../Script/plugin/valid/FormValidator.1.0.min.js"></script>
    <script type="text/javascript" src="../../Ztree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="../../Ztree/js/jquery.ztree.excheck-3.5.js"></script>
    <script type="text/javascript">
       $(function(){
			bindValid();
			$("#submitBtn").bind("click",callBack);
			$("#closeBtn").bind("click",function(){
				parent.$.jBox.close();
			});
			onInitTree();
		});
	   function onInitTree(){
			var url = "<c:url value='/Base/Role/listBaseRoleMenuTreeByJq.shtml'/>"
			var zNodes = new Array();
			$.ajax({
				type: "POST", 
				url: url,
				async : true,
				dataType:"json",
				success:
				function(data) {
				   var Str = ","+'${baseRoleBean.purview}'+",";
				   var flag = false; 
					for(var i in data){
					    flag = false; 
						if(Str.indexOf(","+data[i].id+",")>=0){
						   flag=true;
						}
						zNodes[i]=({id:data[i].id, pId:data[i].fatherid,name:data[i].name,rel:data[i].rel,src:data[i].url,usesign:data[i].usesign,lev:data[i].lev,checked:flag,open:flag});
					}
					//初始化
					 $.fn.zTree.init($("#treeDemo"), setting, zNodes);
					 /* var treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
					  nodes = treeObj.getCheckedNodes(true);
						if (nodes.length>0) {
							treeObj.expandNode(nodes[0], true, true, true);
						}*/

					
				    }
			});
		}
		//设置checkbox属性
		//设置树状图属性
		var setting = {
			view: {
			    fontCss: getFont,
			    nameIsHTML: true,
				dblClickExpand: true,  //关闭双击展开节点的功能
				showLine: true         //显示连接线
				
			},
			async: {
				enable: true
			},
			check: {
			    enable: true,
			    chkStyle: "checkbox",
				chkboxType: {"Y":"ps", "N":"ps"}     //设置checkbox属性
			},
			data: {
				simpleData: {
					enable: true
				}
			}
			
		};
		function getFont(treeId, node) {
			return node.font ? node.font : {};
		}	
		
		 //获取选中的节点
		function getSelectStr(){
			//循环获取
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			nodes = zTree.getCheckedNodes(true);
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				//if(!(nodes[i].isParent)){
					v += nodes[i].id + ",";
				//}
				//根节点 level = 0，依次递增
				//if(nodes[i].level==1){
				//	v += nodes[i].id + ",";
				//}

			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			return v
		}
	
		
	   function callBack(){
	        var ChkPurView = getSelectStr();
	    	$("#form1").attr("action","<c:url value='/Base/Role/savebaserole.shtml'/>?ChkPurView="+ChkPurView).trigger("submit");
			if($("#formerror").text() == "ok"){
					return true;
			 }
			return false;
	   }
	   function bindValid(){
			//绑定角色名称
			$("#b_rolename").attr("fv-empty","false").attr("fv-empty-msg","角色名称不能为空")
						.attr("fv-msg-success","").attr("fv-msgpanel","valid_codename");
			$("#b_rolename").parent().append('<span id="valid_codename" class="validate-info"></span>');

			//绑定权值
			$("#b_weight").attr("fv-empty","false").attr("fv-empty-msg","权值不能为空")
							.attr("fv-datatype","Int16").attr("fv-datatype-msg","权值只能为数字")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_sortnum");
			$("#b_weight").parent().append('<span id="valid_sortnum" class="validate-info"></span>');
			
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
    <div class="top-nav-bg"><span>关联菜单</span></div>
    <ul id="treeDemo" class="ztree"></ul>
</div>
<div class="editContainer" style="width:65%; float:right;">
    <!-- 查询条件 --->
    <form name="form1" method="post" action="" id="form1">
        <table class="editTable">
            <tbody>
			        <tr> 
			            <td class="left">所&nbsp;&nbsp;在&nbsp;&nbsp;组：</td>
			            <td class="right">
			            	<input type="radio" name="baseRoleBean.rgroup" value="1" ${baseRoleBean.rgroup=="1"?'checked':''}/>系统组
			        		<input type="radio"  name="baseRoleBean.rgroup" value="0"  ${baseRoleBean.rgroup=="0"?'checked':''}/>用户组    
			            </td>
			        </tr>
			        <tr> 
			            <td class="left">*&nbsp;角色名称：</td>
			            <td class="right">
			            	<input name="baseRoleBean.rolename" id="b_rolename" type="text" class="text_30" size="50" value="${baseRoleBean.rolename}">
			            </td>
			        </tr>
					<tr> 
			            <td class="left">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
			            <td class="right">
			            	<input name="baseRoleBean.remark" type="text" class="text_30" size="50" value="${baseRoleBean.remark}">			
			            </td>
			        </tr>
					<tr> 
			            <td class="left">*&nbsp;权&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值：</td>
			            <td class="right">
			            	<input name="baseRoleBean.weight" id="b_weight" type="text" class="text_10" size="10" value="${baseRoleBean.weight}">（权值越小，权限越大）
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
	                <input type="hidden" name="baseRoleBean.id" value="${baseRoleBean.id}" />
	                <input type="hidden" name="baseRoleBean.purview" value="${baseRoleBean.purview}" />
					<span id="formerror"></span>
	            </tr>
	         </tfoot>
        </table>
    </form>
</div>
</body>
</html>