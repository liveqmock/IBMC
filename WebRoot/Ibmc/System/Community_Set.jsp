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
    <div class="top-nav-bg"><span>所属区域</span></div>
    <ul id="treeDemo" class="ztree"></ul>
    <span id="valid_parentid" class="validate-info"></span>
</div>
<div class="editContainer" style="width:70%; float:right;">
    <!-- 查询条件 --->
    <form action="" method="post" id="form1" name="form1" >
        <table class="editTable">
            <tbody>
            <tr>
                <td  class="left">社区简称：</td>
                <td class="right">
                    <input type="text" name="sysCommunityBean.commname" id="b_commname" value="${sysCommunityBean.commname}" class="text_50"/>
                	<span id="valid_commname" class="validate-info"></span>
                </td>
            </tr>
            <tr>
                <td  class="left">社区详细：</td>
                <td  class="right">
                    <textarea name="sysCommunityBean.commdetail" id="b_commdetail" class="textarea_50_5">${sysCommunityBean.commdetail}</textarea>
                </td>
            </tr>
            <tr>
                <td  class="left">备注：</td>
                <td class="right">
                    <textarea name="sysCommunityBean.remark" id="b_remark" class="textarea_50_5">${sysCommunityBean.remark }</textarea>
                </td>
            </tr>
            <tr>
                <td  class="left">邮编：</td>
                <td class="right">
                    <input type="text" name="sysCommunityBean.commzip" id="b_commzip" class="text_06" value="${sysCommunityBean.commzip }"/>
                </td>
            </tr>
            <tr>
                <td class="left">排序号：</td>
                <td class="right">
                    <input type="text" name="sysCommunityBean.commorder" id="b_commorder" class="text_06" value="${sysCommunityBean.commorder }"/>
                </td>
            </tr>
            </tbody>
            <!-- 以下为隐藏域 -->
            <tfoot>
            <tr>
                <td  colspan="2" align="center">
                    <input type="hidden" name="tokenid" value="${tokenid}" />
                    <input type="hidden" name="sysCommunityBean.id" value="${sysCommunityBean.id}" />
                    <input type="hidden" name="sysCommunityBean.usesign" value="${sysCommunityBean.usesign}" />
                    <input type="hidden" name="sysCommunityBean.parentid" id="b_parentid" value="${sysCommunityBean.parentid}" />
                    <input type="hidden" name="sysCommunityBean.commlev" value="${sysCommunityBean.commlev}" />
                    
                    <input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
                    <input type="button" id="closeBtn" class="btn btn-info" value="关闭"/>
                    <span id="formerror"></span>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
</div>
<script type="text/javascript">
     $(function(){
         $("#submitBtn").bind("click",callBack);
         $("#closeBtn").bind("click",function(){
             parent.$.jBox.close();
         });
         bindValid();
         initTree();
     });
     
     function initTree() {
         $.ajax({
             url: '<c:url value="/Sys/Comm/listRegionByJq.shtml"/>',
             data:{'querymap.needFilterScope':'1','querymap.maxLev':'2'},
             type: 'post',
             dataType: 'json',
             error: function () {
                 alert('Error loading XML document');
             },
             success: function (data) {
     			 var zNodes =[];
                 for(var i=0;i<data.length;i++){
                     zNodes[i]=({
	                     			id:data[i].id, 
	                     			pId:data[i].parentid,
	                     			name:data[i].commname,
	                     			open:true,
                                    //(${not empty sysCommunityBean.parentid})判断parentid不为空时对所有的radio进行disabled
                                    //(data[i].id!='${sysCommunityBean.parentid}')只针对parentid==id时不进行disabled处理
                                   	chkDisabled:(((${not empty sysCommunityBean.parentid})&&(data[i].id!='${sysCommunityBean.parentid}'))?'true':'false'),
	                     			checked:("${sysCommunityBean.parentid}"==data[i].id?true:false),
	                     			selected:("${sysCommunityBean.id}"==data[i].id?true:false),
	                     			nocheck:(data[i].commlev=='2'?false:true)
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
             chkStyle: "radio",
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
	function getSelectedValueOfTree(){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		nodes = zTree.getCheckedNodes(true);
		var v = "";
		for (var i=0, l=nodes.length; i<l; i++) {
			v = nodes[i].id;
		}
		return v;
	}
	 function callBack(){
	 	//获取选择节点值,若无选择则提示
	 	var countyIdV = (getSelectedValueOfTree());
	 	countyIdV = countyIdV || "";
	 	$("#b_parentid").val("");
	 	if(countyIdV != ""){
	 		$("#b_parentid").val(countyIdV);
	 	}
       	$("#form1").attr("action",'<c:url value='/Sys/Comm/savesyscommunity.shtml'/>').trigger("submit");
		if($("#formerror").text() == "ok"){
			return true;
		}
		return false;
	 }
	 
     //验证
     function bindValid(){
		//社区简称
		$("#b_commname").attr("fv-empty","false").attr("fv-empty-msg","社区简称<fmt:message key="format.null" bundle="${bms}"/>")
					  .attr("fv-msg-success","").attr("fv-msgpanel","valid_commname");
		//所属区域
		$("#b_parentid").attr("fv-empty","false").attr("fv-empty-msg","所属区域<fmt:message key="format.null" bundle="${bms}"/>")
					  .attr("fv-msg-success","").attr("fv-msgpanel","valid_parentid");
		
		//绑定form表单自定义验证
		$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
	}
</script>
</body>
</html>
