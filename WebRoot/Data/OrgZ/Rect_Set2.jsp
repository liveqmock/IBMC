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
	<script type="text/javascript">
		$(function(){
			//班组,子班组
			$("#b_groupid,#b_subgroupid").hide();
			//初始化部门,班组树状
			onInitTree();
			mkDeptList();
			bindValid();
			
			$("#submitBtn").bind("click",callBack);
			$("#closeBtn").bind("click",function(){
				parent.$.jBox.close();
			});
			
			//部门change事件触发班组下拉列表框
			$("#b_deptid").bind("change",function(){
				var deptid = $("#b_deptid").val();
				if(deptid == ''){
					$("#b_groupid,#b_subgroupid,#b_organname,#b_shortname").val('');
					$("#b_groupid,#b_subgroupid").hide();
					return;
				}else{
					$("#b_groupid").val('');
					$("#b_subgroupid").hide();
					mkGroupidList();      //构造班组下拉列表框
				}
				//判断是否是最后一项
				if($("#b_groupid").is(":hidden")){
					makeOrganZ(deptid);
				}
			});
			
			//班组change事件触发班组下拉列表框
			$("#b_groupid").bind("change",function(){
				var groupid = $("#b_groupid").val();
				if(groupid == ''){
					$("#b_subgroupid,#b_organname,#b_shortname").val('');
					$("#b_subgroupid").hide();
					return;
				}
				mkSubGroupidList();
				if($("#b_subgroupid").is(":hidden")){
					makeOrganZ(groupid);
				}
			});
			
			//子班组change事件触发班组下拉列表框
			$("#b_subgroupid").bind("change",function(){
				var groupid = $("#b_subgroupid").val();
				if(groupid == ''){
					$("#b_organname,#b_shortname").val('');
					return;
				}else{
					makeOrganZ(groupid);
				}
			});
			
		});
	
		//设置checkbox属性
		var setting = {
			check: {
				enable: true,
				chkboxType: {"Y":"ps", "N":"ps"}
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		
		function onInitTree(){
			//获取部门班组列表
			var zNodes = new Array();
			var url = "<c:url value='/Data/OrgZ/findDataOrganZTreeListIdStrByJq.shtml'/>";
			var querymap = {'querymap.fatherid':'${fatherid}','querymap.idstr':'${querymap.idstr}'};
			$.ajax({
				type: "POST", 
				url: url,
				data:querymap,
				async : true,
				dataType:"json",
				success:
				function(data) {
					var idstr = ","+'${querymap.idstr}'+",";
					for(var i=0;i<data.length;i++){
						zNodes[i]=({id:data[i].id, pId:data[i].fatherid,name:(data[i].organname),chkDisabled:(idstr.indexOf(","+data[i].id+",")>=0)?false:true,checked:(idstr.indexOf(","+data[i].id+",")>=0)?true:false});
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
			$("#b_newdeptid").val($("#b_deptid").val());
			var newGroupidV = "";
			if(!$("#b_subgroupid").is(":hidden")){
				newGroupidV = $("#b_subgroupid").val();
			}else if(!$("#b_groupid").is(":hidden")){
				newGroupidV = $("#b_groupid").val();
			}
			$("#b_newgroupid").val(newGroupidV);
			$("#b_id").val(newGroupidV);
			$("#form1").attr("action",'<c:url value='/Data/OrgZ/savedataorganzmerge.shtml'/>').trigger("submit");
			if($("#formerror").text() == "ok"){
				return true;
			}
			return false;
		}
		
		function getFatheridStr(){
			//循环获取(specid)
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			nodes = zTree.getCheckedNodes(true);
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].id + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			return v
		}
		
		function bindValid(){
			$("#b_deptid,#b_groupid,#b_subgroupid").removeAttr("fv-empty");
			if($("#b_subgroupid").is(":visible")){
				$("#b_subgroupid").attr("fv-empty","false").attr("fv-empty-msg","合并目标子班组不能为空")
								.attr("fv-msg-success","").attr("fv-msgpanel","valid_subgroupid");
				$("#b_subgroupid").parent().append('<span id="valid_subgroupid" class="validate-info"></span>');
			}else{
				if($("#b_groupid").is(":visible")){
					$("#b_groupid").attr("fv-empty","false").attr("fv-empty-msg","合并目标班组不能为空")
									.attr("fv-msg-success","").attr("fv-msgpanel","valid_groupid");
					$("#b_groupid").parent().append('<span id="valid_groupid" class="validate-info"></span>');
				}else{
					if($("#b_deptid").is(":visible")){
						$("#b_deptid").attr("fv-empty","false").attr("fv-empty-msg","合并目标部门不能为空")
										.attr("fv-msg-success","").attr("fv-msgpanel","valid_deptid");
						$("#b_deptid").parent().append('<span id="valid_deptid" class="validate-info"></span>');
					}
				}
			}
			
			//目标更名
			$("#b_organname").attr("fv-empty","false").attr("fv-empty-msg","目标更名不能为空")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_organname");
			$("#b_organname").parent().append('<span id="valid_organname" class="validate-info"></span>');
			
			//机构简称[备注]
			$("#b_shortname").attr("fv-empty","false").attr("fv-empty-msg","机构简称不能为空")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_shortname");
			$("#b_shortname").parent().append('<span id="valid_shortname" class="validate-info"></span>');
			
			//绑定form表单自定义验证
			$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
		}
		
		//构造部门列表
	    function mkDeptList(){
	    	var url = '<c:url value='/Data/OrgZ/findDataOrganZNoTreeListByJq.shtml'/>';
	    	var querymap = {'querymap.unitid':'${querymap.unitid}','querymap.fatherid':'${fatherid}'};
	    	var defsel = '';
	    	var tarobj = $("#b_deptid");
	    	//先清空在添加
			$("#b_deptid option").not(":first").remove();
	    	makeSel(url,querymap,defsel,tarobj);
	    }
	    
	    //构造班组列表
	    function mkGroupidList(){
	    	var url = '<c:url value='/Data/OrgZ/findDataOrganZNoTreeListByJq.shtml'/>';
	    	var fatherid = $("#b_deptid").val();
	    	var querymap = {'querymap.unitid':'${querymap.unitid}','querymap.fatherid':fatherid};
	    	var defsel = '';
	    	var tarobj = $("#b_groupid");
	    	//先清空在添加
			$("#b_groupid option").not(":first").remove();
	    	makeSel(url,querymap,defsel,tarobj);
	    }
	    
	     //构造子班组列表
	    function mkSubGroupidList(){
	    	var url = '<c:url value='/Data/OrgZ/findDataOrganZNoTreeListByJq.shtml'/>';
	    	var fatherid = $("#b_groupid").val();
	    	var querymap = {'querymap.unitid':'${querymap.unitid}','querymap.fatherid':fatherid};
	    	var defsel = '';
	    	var tarobj = $("#b_subgroupid");
	    	//先清空在添加
			$("#b_subgroupid option").not(":first").remove();
	    	makeSel(url,querymap,defsel,tarobj);
	    }
	    
	    /**
	    	构造下拉列表框
	     	url      请求的地址
	     	querymap 请求传递的参数
	     	defsel   默认选择的值
	     	tarobj   当前操作的对象
	    **/
	    function makeSel(url,querymap,defsel,tarobj){
	    	$.ajax({
	             type: 'post',
	             async: false,
	             url: url,
	             data: querymap,
	             dataType: 'json',
	             cache: false,
	             success:function(data){
	             	var str = "";
					//通过jquery方法进行循环编译
					if(data.length==0){
						tarobj.hide();
					}else{
						$.each(data,function(i,d){
							var sel = ""
							if((defsel !='')&&(defsel == d.id)){
								sel = "selected";
							}
							str += "<option "+sel+" value="+d.id+" >"+d.organname+"</option>";
						});
						tarobj.append(str);
						tarobj.show();
					}
	             },
				error:function(msg){
				    parent.$.jBox.tip("与数据库连接失败！", 'info');
				}
	        });
	    }
		
		/**
	    	构造 目标更名,机构简称
	     	id  主键id
	    **/
	    function makeOrganZ(id){
	    	var url = '<c:url value='/Data/OrgZ/findDataOrganZBeanIdByJq.shtml'/>'
	    	var querymap = {'querymap.id':id};
	    	$.ajax({
	             type: 'post',
	             async: false,
	             url: url,
	             data: querymap,
	             dataType: 'json',
	             cache: false,
	             success:function(data){
	             	var organname = data[0].organname;
	             	var shortname = data[0].shortname;
	             	$("#b_organname").val(organname);
	             	$("#b_shortname").val(shortname);
	             },
				 error:function(msg){
				    parent.$.jBox.tip("与数据库连接失败！", 'info');
				 }
	        });
	    }
		
	</script>
</head>
<body>
<div class="editContainer">
    <!-- 查询条件 --->
    <form id="form1" action="" name="form1">
        <table class="editTable">
            <tbody>
            <tr>
                <td class="left">合并源：</td>
                <td colspan="3" class="right">
					<ul id="treeDemo" class="ztree" style="height:150px;"></ul>
                </td>
            </tr>
			<tr>
                <td class="left">合并目标：</td>
                <td colspan="3" class="right">
                    <select name="querymap.deptid" id="b_deptid">
                        <option value="">请选择</option>
                    </select>
                    <select name="querymap.groupid" id="b_groupid">
                        <option value="">请选择</option>
                    </select>
                    <select name="querymap.subgroupid" id="b_subgroupid">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="left">目标更名：</td>
                <td colspan="3" class="right">
                    <input type="text" name="dataOrganZBean.organname" id="b_organname" value="${dataOrganZBean.organname}" />
                </td>
            </tr>
            <tr>
                <td class="left">机构简称：</td>
                <td colspan="3" class="right">
                    <input type="text" name="dataOrganZBean.shortname" id="b_shortname" value="${dataOrganZBean.shortname}"/>
                </td>
            </tr>
            <!-- 以下为隐藏域 -->
            <tr>
                <td colspan="4">
                	<input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
                	<input type="button" id="closeBtn" class="btn btn-info" value="关闭"/>
				    <input type="hidden" name="tokenid" value="${tokenid}" />
	                <input type="hidden" name="fatherid" value="${fatherid}" />
	                <input type="hidden" name="dataOrganZBean.id" value="${dataOrganZBean.id}" id="b_id"/>
	                <input type="hidden" name="dataOrganZBean.fatherid" value="${dataOrganZBean.fatherid}" id="b_fatherid"/>
	                
	                <input type="hidden" name="querymap.idstr" value="${querymap.idstr}"/>
	                <input type="hidden" name="querymap.newdeptid" value="" id="b_newdeptid"/>
	                <input type="hidden" name="querymap.newgroupid" value="" id="b_newgroupid"/>
                </td>
            </tr>
            <tr style="display:none;">
                <td colspan="4"><span id="formerror"></span><br/></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>