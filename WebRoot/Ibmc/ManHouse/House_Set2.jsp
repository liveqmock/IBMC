<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	
    <link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/datagrid.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/tipcss.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/valid.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid-pagi.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/plugins.js'/>"></script>
	<script src="<c:url value='/Script/plugin/valid/FormValidator.1.0.min.js'/>"></script>
</head>
<body>
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">${sysCommunityBean.parentname}/${sysCommunityBean.ownername}/${sysCommunityBean.commname}</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
	<div class="editContainer" id="editFormId">
	    <form name="form1" id="form1" method="post">
		    <table class="editTable">
		        <tbody>
		        <tr>
		            <td class="left">&nbsp;房间名称：</td>
		            <td class="right">
		                <input type="text" name="commname" id="commname" value="" class="text_50" size="50" fv-empty="false" fv-empty-msg="房产业主<fmt:message key="format.null" bundle="${bms}"/>" fv-msgpanel="valid_commname"/>
		                <span id="valid_commname" class="validate-info"></span>
		            </td>
		        </tr>
		        <tr>
		            <td class="left">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
		            <td class="right">
		                <textarea name="remark" id="remark" class="textarea_50_3"></textarea>
		            </td>
		        </tr>
		        </tbody>
		        <!-- 以下为隐藏域 -->
		        <tfoot>
		        <tr>
		            <td colspan="2" align="center">
		                <input type="button" id="submitBtn" class="btn btn-info" value="保存房间"/>
		                <input type="button" id="cancelBtn" class="btn btn-info" value="取消保存"/>
		        		<!-- 房间的parentid为房产的id -->
		                <input type="hidden" name="sysCommunityBean.parentid" id="parentid" value="${sysCommunityBean.id}"/>
		                <input type="hidden" name="sysCommunityBean.id" id="id" value=""/>
	                    <span style="display:none;" id="formerror"></span>
		            </td>
		        </tr>
		        </tfoot>
		    </table>
		</form>
	</div>
    <!-- 结果集合 --->
    <div class="gridDiv" id="listFormId">
        <div id="dataPagi"></div>
        <div id="btnGroup">
            <button class="btn btn-info" id="insert">新增房间</button>&nbsp;|&nbsp;
            <button class="btn btn-info" id="update">修改房间</button>&nbsp;|&nbsp;
            <button class="btn btn-danger" id="delete">删除房间</button>
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
</div>
<script language="javascript"> 
    //列
    var cols = [
        { title:'房间名称', name:'commname' ,width:540, align:'left',renderer:null},
        { title:'备注', name: 'remark', width: 40, align: 'center', renderer: function (val, item, rowIndex) {
            return (val!="")?"<span class='onShow' title='"+val+"'></span> ":"&nbsp;";
        }}
    ];
	var url = '<c:url value='/Man/House/findManHouseRoomListByJq.shtml'/>';
	param = {'sysCommunityBean.id':'${sysCommunityBean.id}'};
    //分页
    var mmg = $('#dataTable').mmGrid({
        indexColWidth: 35,
        height:'320',
        cols: cols,
        url: url,
        params: param,
		checkCol:true,
        nowarp: true,
        method: 'post',
        root: 'items',
        plugins: [
            $('#dataPagi').mmPaginator()
        ]
    });
 
    $(function(){
    	bindValid();   //表单验证
        $("#editFormId").hide();
        //按钮操作
        $("#insert").bind("click",insertOper);
        $("#update").bind("click",updateOper);
        $("#delete").bind("click",deleteOper);
        //取消保存
        $("#cancelBtn").bind("click",function(){
        	$("#commname").val('');
        	$("#remark").html('');
        	
	    	$("#listFormId").show();   //显示列表form
	    	$("#editFormId").hide();   //隐藏列表form
        });
        //保存
		$("#submitBtn").bind("click",saveOper);
        
    });
 	
    //新增
    function insertOper(){
    	$("#listFormId").hide();
    	$("#editFormId").show();
    	operRecord(1);
    }
    
    //更新
    function updateOper(){
    	operRecord(2);
    }

    //删除
    function deleteOper(){
    	operRecord(3);
    }
    
    //保存
    function saveOper(){
    	operRecord(4);
    }
    
    //对数据进行操作
    function operRecord(flag){
    	if(flag=="1"){
            $("#editFormId").show();  //显示新增表格
    	}else if(flag=="2"){
    		//修改
    		var selectedId = getSelectedRecordId();
    		if(selectedId == "") return ;
    		var param = {'sysCommunityBean.id':selectedId}
    		
	    	$("#listFormId").hide();   //列表form隐藏
	    	$("#editFormId").show();   //编辑form显示
    		//使用ajax查询需要修改房间的信息
    		$.ajax({
				type: "POST", 
				url: "<c:url value='/Man/House/findManHouseRoomBeanByJq.shtml'/>", //代码数据
				async: false,   //同步
				data: param,
				dataType:'json',
				cache: false,
				error: function(){
				    parent.$.jBox.tip("与数据库连接失败！", 'warning');
				},
				success:function(data) {
					$("#id").val(data[0].id);        //主键ID
					$("#commname").val(data[0].commname);  //房间名称
					$("#remark").html(data[0].remark);     //备注
				}
			});
	    	
    	}else if(flag=="3"){
    		//删除
    		var selectedId = getSelectedRecordId();
    		if(selectedId == "") return ;
	        var submit = function (v, h, f) {
	            if (v == 'ok'){
	            	$.ajax({
						type: "POST", 
						url:"<c:url value='/Man/House/delManHouseByJq.shtml'/>", //代码数据
						async: false,   //同步
						data:{'sysCommunityBean.id':selectedId,'querymap.dellev':'${querymap.roomlev}'},  //标识房间删除
						error:function(data){
						    parent.$.jBox.tip("与数据库连接失败！", 'warning');
						},
						success:function(data) {
							if(data == '1'){
		            			parent.$.jBox.tip("操作成功！", 'info');
			                    mmg.load();
							}else{
		            			parent.$.jBox.tip("操作失败！", 'info');
							}
						}
					});
	            }
	            return true;
	        };
	        parent.$.jBox.confirm("删除该记录后该房间下关联的卡将删除，您确定删除该记录？", "提示", submit);
    	}else if(flag=="4"){
    		var commname = $("#commname").val();  //房间名称
    		if(commname==''){
			    parent.$.jBox.tip("房间名称不能为空！请重新输入后再保存", 'warning');
			    return;
    		}
    		//保存[使用ajax处理保存操作]
    		var id = $("#id").val();  //房间主键id
    		var parentid = $("#parentid").val();  //房间父id[即房产id]
    		var remark = $("#remark").html();  //房间备注
    		var param = {'sysCommunityBean.id':id,'sysCommunityBean.parentid':parentid,'sysCommunityBean.commname':encodeURIComponent(commname),'sysCommunityBean.remark':encodeURIComponent(remark)}
			$.ajax({
				type: "POST", 
				url: "<c:url value='/Man/House/saveManRoomByJq.shtml'/>", //代码数据
				async: false,
				data: param,
				error:function(data){
				    parent.$.jBox.tip("与数据库连接失败！", 'warning');
				},
				success:function(data) {
					if(data == '1'){
						parent.$.jBox.tip("操作成功！", 'info');
						
				    	$("#listFormId").show();    //显示列表数据form
				    	$("#editFormId").hide();    //隐藏编辑新增数据form
				    	
        				$("#id").val('');
        				$("#commname").val('');  //清空[房间名称]
        				$("#remark").val('');  //清空[房间备注]
						mmg.load();
					}else{
						parent.$.jBox.tip("操作失败！请刷新页面后重试！", 'info');
					}
				}
			});
    	}
    }

    //检测是否选择记录
    function getSelectedRecordId(){
        var rows = mmg.selectedRows();
        var selecedId = "";
        for(var i=0;i<rows.length;i++){
        	selecedId = (rows[i].id);
        }
        if(selecedId==""){
            parent.$.jBox.tip("请选择要操作的记录！", 'warning');
            return "";
        }
        return selecedId;
    }
    
    //验证
	function bindValid(){
		//绑定form表单自定义验证
		$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
	}
    
</script>
</body>
</html>