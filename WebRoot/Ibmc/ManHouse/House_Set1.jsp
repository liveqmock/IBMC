<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/tipcss.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/valid.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Ztree/css/zTreeStyle/zTreeStyle.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
    <script src="<c:url value='/Ztree/js/jquery.ztree.core-3.5.js'/>"></script>
	<script src="<c:url value='/Ztree/js/jquery.ztree.excheck-3.5.js'/>"></script>
	<script src="<c:url value='/Script/plugin/valid/FormValidator.1.0.min.js'/>"></script>
    <script type="text/javascript">
    	$(function(){
            initTree();    //初始化树状
        	bindValid();   //表单验证
        	if(${not empty sysCommunityBean.id}){
        		$("#tr_3").show();
        	}
        	if('${sysCommunityBean.commtype}'=='2'){
        		$("#tr_1").hide();
        		$("#tr_2").show();
        	}
            $("#submitBtn").bind("click",function(){
            	callBack();
            });
            $("#closeBtn").bind("click",function(){
                parent.$.jBox.close();
            });
            //房产类型
            $("input[name='sysCommunityBean.commtype'][type='radio']").bind("click",function(){
            	if($(this).val()=='1'){   //独立业主
            		$("#tr_1").show();
            		$("#tr_2").hide();
            		$("#ownername1").val('');
            	}else if($(this).val()=='2'){   //共有业主（商业小区）
            		$("#tr_1").hide();
            		$("#tr_2").show();
            		$("#ownername2").val('/');
            	}
            });
        });
        
        function getFont(treeId, node) {
            return node.font ? node.font : {};
        }
        var setting = {
            check: {
                enable: true,
                chkStyle: "radio",
                radioType: "all"   //level同一级内  all整棵树内
            },
            data: {
                simpleData: {
                    enable:true
                }
            },
			callback: {
				onCheck: onCheck
			}
        };
        function initTree() {
        	var url = "<c:url value='/Sys/Comm/listRegionByJq.shtml'/>";
        	var queryparam = {'querymap.needFilterScope':'${querymap.needFilterScope}','querymap.maxLev':'${querymap.maxLev}'};
            var zNodes =[];
            $.ajax({
                url: url,
                data: queryparam,
                type: 'post',
                dataType: 'json',
                error: function () {
					parent.$.jBox.tip("与数据库连失败！请刷新后重试！", 'warning');
                },
                success: function (data) {
                    for(var i=0;i<data.length;i++){
                        zNodes[i]=({id:data[i].id, pId:data[i].parentid,
                                   name:data[i].commname,open:true,
                                   //(${not empty sysCommunityBean.parentid})判断parentid不为空时对所有的radio进行disabled
                                   //(data[i].id!='${sysCommunityBean.parentid}')只针对parentid==id时不进行disabled处理
                                   chkDisabled:(((${not empty sysCommunityBean.parentid})&&(data[i].id!='${sysCommunityBean.parentid}'))?'true':'false'),
                                   checked:(data[i].id=='${sysCommunityBean.parentid}'?true:false),
                                   nocheck:(data[i].commlev=='3'?false:true)});
                    }
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                }
            });
        }
        
        function callBack() {
			bindValid();   //表单中有隐藏/显示的列因此这里需要重新调用
        	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = zTree.getCheckedNodes(true);
			if(nodes.length==0){
				parent.$.jBox.tip("请选择房产所属的区域！", 'warning');
				return ;
			}else{
				$("#parentid").val(nodes[0].id);
			}
			
			var idcard = $("#owneridcard").val();
			if(idcard !=''){
				//验证该业主是否存在人员库中
				$.ajax({
					type: "POST", 
					url: "<c:url value='/Man/Peo/findManPeoBeanByJq.shtml'/>", //代码数据
					data:{'manPeoBean.idcard':idcard},
					async:false,   //ajax同步
					dataType:'json',
					cache: false,
					error:function(){
						parent.$.jBox.tip("与数据库连失败！请刷新后重试！", 'warning');
						$("#ownername1").val('');  //业主名称
						$("#ownerid").val('');  //业主主键id
					},
					success:function(data) {
						if(data[0].id=="-1"){   //表示该条人员记录不存在查询不出结果集[系统在后台将会自动将其主键ID设置为-1]
							var submit = function (v, h, f) {
					            if(v == 'ok'){
					            	var url = "<c:url value='/Man/Peo/setmanpeomess_master.shtml'/>";
							    	url += "?manPeoBean.id=";
							    	var msg = '业主管理-新增';
							        parent.$.jBox.open("iframe:"+url, msg, 490 , 460, { buttons: {}});
					            }
					            return true;
				        	};
				        	parent.$.jBox.confirm("该条人员记录不存在！是否进行新增", "提示", submit);
						}else{
							$("#ownerid").val(data[0].id);     //业主主键id
							//保存房产信息
							$("#form1").attr("action",'<c:url value='/Man/House/savemanhouse.shtml'/>').trigger("submit");
							if($("#formerror").text() == "ok"){
								return true;
							}
							return false;
						}
					}
				});
			}else{
				//保存房产信息
				$("#form1").attr("action",'<c:url value='/Man/House/savemanhouse.shtml'/>').trigger("submit");
				if($("#formerror").text() == "ok"){
					return true;
				}
				return false;
			}
        }
        
        //查询下一个排序号
        function onCheck(e, treeId, treeNode){
        	var parentid = treeNode.id;
        	if('${sysCommunityBean.parentid}'==parentid){
				$("#tr_3").show();
				$("#commorder").val('${sysCommunityBean.commorder}');
				return;
        	}
        	$.ajax({
				type: "POST", 
				url: "<c:url value='/Man/House/findManHouseNextOrderByJq.shtml'/>", //代码数据
				data:{'sysCommunityBean.parentid':parentid},
				async:false,
				error:function(){
					parent.$.jBox.tip("与数据库连失败！请刷新后重试！", 'warning');
					$("#tr_3").show();
					$("#commorder").val("-1");
				},
				success:function(data) {
					$("#tr_3").show();
					$("#commorder").val(data);
				}
			});
        }
        
        //获取业主的姓名
        function getOwnername(){
        	var idcard = '';
       		var name = '';
			var CVR_IDCard = document.getElementById("CVR_IDCard");
			var strReadResult = CVR_IDCard.ReadCard();
			strReadResult = 0;
			if(strReadResult == "0"){
	            idcard = CVR_IDCard.CardNo;
            	name = CVR_IDCard.Name;
	            idcard = '362523199111257654';
	            name = '12345';
			}else{
	            parent.$.jBox.tip(strReadResult, 'warning');
	            return;
			}
			$("#owneridcard").val(idcard);  //身份证idcard
			$("#ownername1").val(name);  //身份证信息名称
        }
        
        //验证
        function bindValid(){
			//房产类型
			$("input[name='sysCommunityBean.commtype']").attr("fv-minlength","1").attr("fv-minlength-msg","房产类型<fmt:message key="format.null" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_commtype");
			
			//房产业主[独立业主]
			$("#ownername1").attr("fv-empty","true");
			if($("#ownername1").is(":visible")){
				$("#ownername1").attr("fv-empty","false").attr("fv-empty-msg","房产业主<fmt:message key="format.null" bundle="${bms}"/>")
						   .attr("fv-msg-success","").attr("fv-msgpanel","valid_ownername1");
				$("#ownername1").parent().append('<span id="valid_ownername1" class="validate-info"></span>');
			}
			//房产业主[共有业主（商业小区）]
			$("#ownername2").attr("fv-empty","true");
			if($("#ownername2").is(":visible")){
				$("#ownername2").attr("fv-empty","false").attr("fv-empty-msg","房产业主<fmt:message key="format.null" bundle="${bms}"/>")
						   .attr("fv-msg-success","").attr("fv-msgpanel","valid_ownername2");
				$("#ownername2").parent().append('<span id="valid_ownername2" class="validate-info"></span>');
			}
			//房产地址
			$("#commname").attr("fv-empty","false").attr("fv-empty-msg","房产地址<fmt:message key="format.null" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_commname");
			$("#commname").parent().append('<span id="valid_commname" class="validate-info"></span>');
			//排序号
			$("#commorder").attr("fv-empty","false").attr("fv-empty-msg","排序号<fmt:message key="format.null" bundle="${bms}"/>")
							.attr("fv-datatype","Int16").attr("fv-datatype-msg","排序号<fmt:message key="format.int" bundle="${bms}"/>")
							.attr("fv-msg-success","").attr("fv-msgpanel","valid_commorder");
			$("#commorder").parent().append('<span id="valid_commorder" class="validate-info"></span>');
			//邮编
			$("#commzip").attr("fv-empty","true");
			if($("#commzip").is(":visible")){
				$("#commzip").attr("fv-empty","false").attr("fv-empty-msg","邮编<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_commzip");
				$("#commzip").parent().append('<span id="valid_commzip" class="validate-info"></span>');
			}
			//绑定form表单自定义验证
			$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
		}
        
    </script>
    <style type="text/css">
    	#ztree {
            width: 30%;
            float: left;
            height: 392px;
            margin: 0px;
            padding: 0px;
            border: none;
            background: #FFFFFF url(<c:url value='/Images/organ-bg.jpg'/>) right repeat-y;
        }
        .top-nav-bg {
            background: #f9f9f9 url(<c:url value='/Images/organ-top-bg.jpg'/>) top left repeat-x;
            height: 40px;
            width: 99%;
        }
 
        #treeDemo {
            height: 90%;
            overflow: auto;
            background: none;
            overflow-y: auto;
            width: 93%;
        }
 
        .top-nav-bg span {
            font: bold 12px/32px Arial, sans-serif;
            margin-left: 30%;
        }
    </style>
</head>
<body>
<div id="ztree">
    <div class="top-nav-bg"><span>所属区域</span></div>
    <ul id="treeDemo" class="ztree"></ul>
</div>
<div class="editContainer" style="width:70%; float:right;">
	<object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0" ></object>
    <div class="errorBlock"></div>
    <!-- 查询条件 --->
    <form action="" method="post" id="form1" name="form1">
    	<table class="editTable">
            <tbody>
            <tr>
                <td class="left">房产类型：</td>
                <td class="right">
                    <input type="radio" name="sysCommunityBean.commtype" value="1" ${sysCommunityBean.commtype=='1'?'checked':''}/>独立业主
                    <input type="radio" name="sysCommunityBean.commtype" value="2" ${sysCommunityBean.commtype=='2'?'checked':''}/>共有业主（商业小区）
                	<span id="valid_commtype" class="validate-info"></span>
                </td>
            </tr>
            <tr id="tr_1">
                <td class="left">房产业主：</td>
                <td class="right">
                    <input type="text" name="sysCommunityBean.ownername" id="ownername1" class="text_20line" size="20" value="${sysCommunityBean.ownername}" readonly/>
                    <input type="button" class="btn btn-danger" id="btn_" onclick="getOwnername();" value="获取业主信息" />
                </td>
            </tr>
            <tr id="tr_2" style="display:none;">
                <td class="left">房产业主：</td>
                <td class="right">
                    <input type="text" name="sysCommunityBean.ownername" id="ownername2" class="text_20line" size="20" value="${empty sysCommunityBean.ownername?'/':sysCommunityBean.ownername}" readonly/>
                </td>
            </tr>
            <tr>
                <td class="left">房产地址：</td>
                <td class="right">
                    <input type="text" name="sysCommunityBean.commname" id="commname" class="text_30" size="30" value="${sysCommunityBean.commname}"/>
                </td>
            </tr>
            <tr style="display:none;">
                <td class="left">邮编：</td>
                <td class="right">
                    <input type="text" name="sysCommunityBean.commzip" id="commzip" class="text_10" value="${sysCommunityBean.commzip}"/>
                </td>
            </tr>
            <tr id="tr_3" style="display:none;">
                <td class="left">排序号：</td>
                <td class="right">
                    <input type="text" name="sysCommunityBean.commorder" id="commorder" class="text_06" value="${sysCommunityBean.commorder}"/>
                </td>
            </tr>
            <tr>
                <td class="left">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
                <td class="right">
                    <textarea name="sysCommunityBean.remark" class="textarea_50_3">${sysCommunityBean.remark}</textarea>
                </td>
            </tr>
            <c:if test="${empty sysCommunityBean.id}">
	            <tr>
	                <td class="left">继续新增：</td>
	                <td colspan="2" class="right">
	                	<input type="checkbox" name="gosign" id="gosign" value="1" ${gosign=='1'?'checked':''}/>是否继续新增
	                </td>
	            </tr>
            </c:if>
            </tbody>
            <!-- 以下为隐藏域 -->
            <tfoot>
            <tr>
                <td align="center" colspan="2">
                    <input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
                    <input type="button" id="closeBtn" class="btn btn-info" value="关闭"/>
                    <input type="hidden" name="querymap.needFilterScope" value="${querymap.needFilterScope}" />
                    <input type="hidden" name="querymap.maxLev" value="${querymap.maxLev}" />
                    
                    <input type="hidden" name="tokenid" value="${tokenid}" />
                    <input type="hidden" name="sysCommunityBean.id" value="${sysCommunityBean.id}" />
                    <input type="hidden" name="sysCommunityBean.ownerid" value="${sysCommunityBean.ownerid}" id="ownerid"/>
                    <input type="hidden" name="sysCommunityBean.owneridcard" value="" id="owneridcard"/>
                    <input type="hidden" name="sysCommunityBean.usesign" value="${sysCommunityBean.usesign}" />
                    <input type="hidden" name="sysCommunityBean.commlev" value="${sysCommunityBean.commlev}" />
                    <input type="hidden" name="sysCommunityBean.parentid" value="${sysCommunityBean.parentid}" id="parentid"/>
                    <span style="display:none;" id="formerror"></span>
                </td>
            </tr>
            <tr>
                <td align="left" colspan="2">
                    <span class="onShow"></span>若业主信息不存在，可到人员管理--人员维护中查询或者新增业主信息
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
</div>
</body>
</html>