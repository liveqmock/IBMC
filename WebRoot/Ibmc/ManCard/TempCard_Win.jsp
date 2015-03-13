<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/datagrid.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/valid.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
	<script src="<c:url value='/My97DatePicker/WdatePicker.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/grid-pagi.js'/>"></script>
	<script src="<c:url value='/Script/plugin/grid/plugins.js'/>"></script>
</head>
<body>
<div id="container">
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 标题区域 --->
    <div class="titleArea">
        <div class="titleClass">${houseBean.commname}[${houseBean.ownername }]-临时卡列表</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
        <form method="post" name="form" id="form1" action="">
            关键字：<select name="fields">
            <option value="cardno">卡序列号</option>
        </select>
            <input type="hidden" name="cardtype" id="q_cardtype" value="${cardtype }"/>
            <input type="hidden" name="querymap.houseid" id="q_houseid" value="${querymap.houseid }"/>
            <input type="text" name="keyword" id="searchkey" class="text_15" value=""/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
            <input type="button" class="btn btn-info" id="insert"  style="float:right;margin-right:10px;" value="生成临时卡"/>
        </form>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 结果集合 --->
    <div class="gridDiv" id="listFormId">
        <div id="dataPagi">
        </div>
        <div id="btnGroup">
            <button class="btn btn-info" id="update">修改</button>&nbsp;|&nbsp;
            <button class="btn btn-info" id="reset">重置</button>&nbsp;|&nbsp;
            <button class="btn btn-danger" id="delete">注销</button>
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
    </div>
</div>
<object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0" ></object>
<!-- 分隔符 --->
<div class="lineSpac"></div>
<div class="editContainer" id="editFormId">
    <div class="errorBlock"></div>
    <!-- 查询条件 --->
    <div>
        <form method="post" name="form2" id="form2" action="">
        <table class="editTable">
            <tbody>
            <tr>
                <td colspan="2" style="font-weight: bold;">生成临时卡</td>
            </tr>
            <tr>
                <td class="left">身份证信息：</td>
                <td class="right">
                    <input type="text" id="b_rentname" value="" class="text_20line" disabled/>
                    <input type="button" id="submitBtn3" class="btn btn-danger" value="读取身份信息"/>（租客姓名：张三）
                    <span id="valid_b_rentname" class="validate-error">身份证信息<fmt:message key="format.null" bundle="${bms}"/></span>
                </td>
            </tr>
            <tr>
                <td class="left">门禁卡绑定：</td>
                <td class="right">
                    <input type="text" id="b_cardno" value="" class="text_20line"/>（绑定IC卡）
                    <span id="valid_b_cardno" class="validate-error">门禁卡ID<fmt:message key="format.null" bundle="${bms}"/></span>
                </td>
            </tr>
            <tr>
                <td class="left">失效时间：</td>
                <td class="right">
                    <input type="text" id="b_enddate" value="" class="text_15 Wdate" onClick="WdatePicker()"/>
                </td>
            </tr>
            <tr>
                <td class="left">操作人：</td>
                <td class="right"><input type="text" value="${opraName}" readonly="readonly" class="text_20line"/></td>
            </tr>
            </tbody>
            <!-- 以下为隐藏域 -->
            <tfoot>
            <tr>
                <td colspan="2" align="center">
                    <input type="hidden" id="b_cardid" value=""/>
            		<input type="hidden" id="b_cardtype" value="${cardtype }"/>
            		<input type="hidden" id="b_rentid" value=""/>
            		<input type="hidden" id="b_rentidcard" value=""/>
            		<input type="hidden" id="b_houseid" value="${querymap.houseid }"/>
                    <input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
                    <input type="button" id="closeBtn" class="btn btn-info" value="取消"/>
                    <span id="formerror"></span>
                </td>
            </tr>
            </tfoot>
        </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    //列
    var cols = [
        { title: '卡序列号', name: 'cardno', width: 220, align: 'left'},
        { title: '生效时间', name: 'startdate', width: 120, align: 'left'},
        { title: '失效时间', name: 'enddate', width: 120, align: 'left'},
        { title: '操作人', name: 'operusername', width: 100, align: 'left'}
    ];
    //列表数据加载
    var mmg = $('#dataTable').mmGrid({
        indexCol:true,
        checkCol:true,
        height: '320',
        cols: cols,
        url: '<c:url value="/Man/Card/listManCardByJq.shtml"/>',
        method: 'post',
        nowarp: true,
        root: 'items',
        plugins: [
            $('#dataPagi').mmPaginator()
        ]
    });
    
    
    $(function(){
        $("#editFormId").hide();
        //搜索
        $("#search").bind("click",function(){
        	mmg.load();
        });
        $("#insert").bind("click",function(){
            showOrHide(1);
        });
        $("#update").bind("click",updateOper);     //修改
        $("#closeBtn").bind("click",function(){
            showOrHide(0);
        });
        $("#reset").bind("click",resetOper);
        $("#delete").bind("click",deleteOper);
        $("#submitBtn").bind("click",function(){
        	bindValid();
            //保存操作
            var showlen = 0;
			$("#form2").find("span").each(function(i,dom){
				if($(this).is(":visible")){
					showlen = showlen+parseInt(1);
				}
			});
			//当showlen大于0时表示
			if(parseInt(showlen)>0){
				return;
			}
	        saveData();
        });
        
        $("#submitBtn3").bind("click",getRentInfoByIdcard);    //读取身份信息
    });
	
	//获取租客信息
	function getRentInfoByIdcard(){
       	var idcard = '';
       	var name = '';
		var CVR_IDCard = document.getElementById("CVR_IDCard");
		var strReadResult = CVR_IDCard.ReadCard();
		//strReadResult = '0';
		if(strReadResult == "0"){
            idcard = CVR_IDCard.CardNo;
            name = CVR_IDCard.Name;
            //idcard = '362523199111258765';
            //name = '12345';
		}else{
            parent.$.jBox.tip(strReadResult, 'warning');
            return;
		}
		$("#b_rentidcard").val(idcard);  //身份证idcard
		$("#b_rentname").val(name);  //身份证信息名称
    }
	
	function saveData(){
		//验证读取的身份证人员信息是否存在
		var idcard = $("#b_rentidcard").val();   //获取租客的身份证id
		$.ajax({
			type: "POST", 
			url: "<c:url value='/Man/Peo/findManPeoBeanByJq.shtml'/>", //代码数据
			data: {'manPeoBean.idcard':idcard},
			async: false,   //ajax同步
			dataType: 'json',
			cache: false,
			error:function(){
				parent.$.jBox.tip("与数据库连失败！请刷新后重试！", 'warning');
				$("#b_rentname").val('');  //租客姓名
				$("#b_rentid").val('');    //租客主键id
			},
			success:function(data) {
				if(data[0].id=="-1"){   //表示该条人员记录不存在查询不出结果集[系统在后台将会自动将其主键ID设置为-1]
					var submit = function (v, h, f) {
			            if(v == 'ok'){
			            	var url = "<c:url value='/Man/Peo/setmanpeomess_rent.shtml'/>";
					    	url += "?manPeoBean.id=";
					    	var msg = '租客管理-新增';
					        parent.$.jBox.open("iframe:"+url, msg, 490 , 460, { buttons: {}});
			            }
			            return true;
		        	};
		        	parent.$.jBox.confirm("该条人员记录不存在！是否进行新增", "提示", submit);
				}else{
					$("#b_rentid").val(data[0].id);     //租客主键id
					//对门卡进行新增
					saveCard();
				}
			}
		});
	}
	
	function saveCard(){
		$.ajax({
			url: '<c:url value="/Man/Card/saveManCardByJq.shtml"/>',
			data:{
				'manCardBean.id':$("#b_cardid").val(),
				'manCardBean.cardtype':$("#b_cardtype").val(),
                'manCardBean.houseid':$("#b_houseid").val(),
                'manCardBean.cardno':$("#b_cardno").val(),
				'manCardBean.rentid':$("#b_rentid").val(),
				'manCardBean.enddate':$("#b_enddate").val() 
			},
			type: 'post',
			async: false,
			dataType: 'json',
			error: function () {
				parent.$.jBox.tip("与数据库连失败！请刷新后重试！", 'warning');
			},
			success: function (data) {
				if(data == "-1" || data=="-2"){
					parent.$.jBox.tip("操作失败！", 'warning');
				}else{
					parent.$.jBox.tip("操作成功！", 'info');
		            //隐藏表单
		            showOrHide(0);
		            mmg.load();
				}
			}
		});
	}
	
    function showOrHide(sign){
        clearEditFormVal();
        if(sign == 1){
            $("#editFormId").show();
            $("#listFormId").hide();
            $("#b_cardno").attr("disabled",false);    //卡的内置序列号可录入
            $("#insert").val("取消生成临时卡");
            $("#insert").unbind("click").bind("click",function(){
                showOrHide(0);
            });
        }else{
            $("#editFormId").hide();
            $("#listFormId").show();
            $("#insert").val("生成临时卡");
            $("#insert").unbind("click").bind("click",function(){
                showOrHide(1);
            });
        }
    }
    
    function deleteOper(){
    	//获取选择的记录
    	var idArr = getSelectedRecordIdArr();
   		if(idArr.length == 0) return ;
	    
	    var selectedId = idArr.join(",");
        var submit = function (v, h, f) {
            if (v == 'ok'){
            	$.ajax({
					type: "POST", 
					url:"<c:url value='/Man/Card/deleteManCardByJq.shtml'/>", //代码数据
					async:false,
					data:{'manCardBean.id':selectedId},
					error:function(data){
					    parent.$.jBox.tip("与数据库连接失败！", 'info');
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
        parent.$.jBox.confirm("注销操作，将会删除该卡对应的刷卡记录以及配置表，确定进行该操作？", "提示", submit);
    }
    
    function resetOper(){
    	//获取选择的记录
    	var idArr = getSelectedRecordIdArr();
   		if(idArr.length == 0) return ;
	    
	    var selectedId = idArr.join(",");
        var submit = function (v, h, f) {
            if (v == 'ok'){
            	$.ajax({
					type: "POST", 
					url:"<c:url value='/Man/Card/resetManCardByJq.shtml'/>", //代码数据
					async:false,
					data:{'manCardBean.id':selectedId},
					error:function(data){
					    parent.$.jBox.tip("与数据库连接失败！", 'info');
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
        parent.$.jBox.confirm("重置操作，将会清空该卡的有效时间和生效时间，确定进行该操作？", "提示", submit);
    }
    
    //清空form表单中的ID 和 卡序列号
    function clearEditFormVal(){
    	//清空身份证上人员信息
    	$("#b_rentname").val("");
    	$("#b_rentid").val("");
    	$("#valid_b_rentname").hide();
    	$("#b_enddate").val("");     //清空失效时间
		//卡序列号
		$("#valid_b_cardno").hide();
    	$("#b_cardid").val("");
    	$("#b_cardno").val("");
    }	
    
    //检测是否选择记录
    function getSelectedRecordIdArr(){
    	//若新增，则跳出
        var rows = mmg.selectedRows();
		var idAttr=new Array();
        if(rows.length==0){
            parent.$.jBox.tip("请选择要操作的记录！", 'warning');
            return idAttr;
        }
        else{
        	for(var i=0;i<rows.length;i++){
        		idAttr.push(rows[i].id);
        	}
            return idAttr;
        }
        return idAttr;
    }
    
    function updateOper(){
    	//获取选择的记录
    	var idArr = getSelectedRecordIdArr();
   		if(idArr.length == 0) return ;
	    var selectedId = idArr.join(",");
	    //查询卡信息
        $.ajax({
			type: "POST", 
			url:"<c:url value='/Man/Card/findManCardBeanByJq.shtml'/>",
			async:false,   //同步
			data:{'manCardBean.id':selectedId},
            dataType: 'json',
			error:function(data){
			    parent.$.jBox.tip("与数据库连接失败！", 'info');
			},
			success:function(data) {
				if(data[0].id=="-1"){   
					//表示该条记录不存在查询不出结果集[系统在后台将会自动将其主键ID设置为-1]
					parent.$.jBox.confirm("该条记录不存在请刷新页面后重试！", "提示", submit);
				}else{
					$("#b_cardno").attr("disabled",true); //卡序列号[卡内置序列号]
					$("#b_cardno").val(data[0].cardno); //卡序列号[卡内置序列号]
					$("#b_cardid").val(data[0].id);  //卡主键ID
					$("#b_cardtype").val(data[0].cardtype);  //卡类型
					$("#b_rentname").val(data[0].rentname); //身份证姓名
					$("#b_rentid").val(data[0].rentid);  //身份证姓名ID
					$("#b_rentidcard").val(data[0].rentidcard);  //身份证ID
					$("#b_houseid").val(data[0].houseid);  //房产ID
					$("#b_enddate").val(data[0].enddate.substr(0,10));  //失效时间
					
		            $("#editFormId").show();
		            $("#listFormId").hide();
		            
		            //js验证隐藏
			    	$("#valid_b_cardno").hide();
			    	$("#valid_b_enddate").hide();
				}
			}
		});
    }
    
    //验证
	function bindValid(){
		//租客身份证信息
		var rentname = $("#b_rentname").val();
		if(rentname == ''){
			$("#valid_b_rentname").show();
		}else{
			$("#valid_b_rentname").hide();
		}
		
		//IC卡，身份证卡序列号
		var cardno = $("#b_cardno").val();
		if(cardno==''){
			$("#valid_b_cardno").show();
		}else{
			$("#valid_b_cardno").hide();
		}
	}
    
</script>
</body>
</html>