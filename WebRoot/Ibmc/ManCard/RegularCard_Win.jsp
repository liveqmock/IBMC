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
        <div class="titleClass">${houseBean.commname}[${houseBean.ownername }]-正式卡列表</div>
    </div>
    <!-- 分隔符 --->
    <div class="lineSpac"></div>
    <!-- 查询条件 --->
    <div class="mySearchMain" id="mySearchMain">
        <form method="post" name="form" id="form1" action="">
            关键字：<select name="fields">
            <option value="cardno">卡序列号</option>
            <option value="rentname">租客姓名</option>
        </select>
            <input type="hidden" name="cardtype" id="q_cardtype" value="${cardtype }"/>
            <input type="hidden" name="querymap.houseid" id="q_houseid" value="${querymap.houseid }"/>
            <input type="text" name="keyword" id="searchkey" class="text_15" value=""/>
            <input type="button" name="search" id="search" class="btn btn-primary" value="搜索"/>
            <input type="checkbox" value="1" name="showsign" id="showsign"/>仅显示即将逾期的门卡
            <input type="button" class="btn btn-info" id="insert"  style="float:right;margin-right:10px;" value="生成正式卡"/>
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
            <button class="btn btn-danger" id="delete">注销</button>
        </div>
        <div class="clear"/>
        <table id="dataTable"></table>
        <div id="btnGroup">
            <button class="btn btn-info" id="keyexten">一键延期</button>
        </div>
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
                <td colspan="2" style="font-weight: bold;">生成正式卡</td>
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
                <td class="left" rowspan="2">门禁卡绑定：</td>
                <td class="right">
                	<input type="text" id="b_cardno1" value="" class="text_20line"/>（绑定IC卡）
                	<span id="valid_b_cardno1" class="validate-error">IC卡序列号<fmt:message key="format.null" bundle="${bms}"/></span>
                	<span id="valid-repeat" class="validate-error valid-repeat"></span>
               	</td>
            </tr>
            <tr>
                <td class="right">
                	<input type="text" id="b_cardno2" value="" class="text_20line"/>（绑定二代身份证）
                	<span id="valid_b_cardno2" class="validate-error">身份证卡序列号<fmt:message key="format.null" bundle="${bms}"/></span>
                	<span id="valid-repeat" class="validate-error valid-repeat"></span>
                </td>
            </tr>
            <tr>
                <td class="left">租住房间：</td>
                <td class="right">
                	<select id="b_roomid" name="roomid">
                		<option value="">===请选择房间===</option>
                	</select>
                    <input type="text" id="b_roomname" value="" class="text_10"/>&nbsp;&nbsp;房间
                    <span id="valid_b_roomid" class="validate-error">租住房间<fmt:message key="format.null" bundle="${bms}"/></span>
                    <span id="valid_b_roomname" class="validate-error">租住房间[文本框]<fmt:message key="format.int" bundle="${bms}"/></span>
                </td>
            </tr>
            <tr>
                <td class="left">失效时间：</td>
                <td class="right">
                    <input type="text" id="b_enddate" value="" class="text_15 Wdate" onClick="WdatePicker()"/>
                	<span id="valid_b_enddate" class="validate-error">失效时间<fmt:message key="format.null" bundle="${bms}"/></span>
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
            		<input type="hidden" id="b_cardtype" value="${cardtype}"/>
            		<input type="hidden" id="b_rentid" value=""/>
            		<input type="hidden" id="b_rentidcard" value=""/>
            		<input type="hidden" id="b_houseid" value="${querymap.houseid }"/>
                    <input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
                    <input type="button" id="closeBtn" class="btn btn-info" value="取消"/>
            		<input type="hidden" id="b_defEndDate" value="${defEndDate}"/>
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
        { title: '房间', name: 'roomid', width: 100, align: 'left'},
        { title: '卡序列号', name: 'cardno', width: 160, align: 'left'},
        { title: '租客', name: 'rentid', width: 60, align: 'center'},
        { title: '生效时间', name: 'startdate', width: 120, align: 'center'},
        { title: '失效时间', name: 'enddate', width: 120, align: 'center'},
        { title: '操作人', name: 'operusername', width: 100, align: 'center'}
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
        //搜索,仅显示即将逾期的门卡
        $("#search,#showsign").bind("click",function(){
        	mmg.load();
        });
        //生成正式卡
        $("#insert").bind("click",function(){
        	/**
        	1:首先需要对房间列表进行构造
        	2:显示隐藏验证提示
        	**/
        	mkManRoom();
            showOrHide(1);
        });
        //取消
        $("#closeBtn").bind("click",function(){
            showOrHide(0);
        });
        $("#update").bind("click",updateOper);     //正式门卡修改
        $("#delete").bind("click",deleteOper);     //正式门卡删除
        $("#submitBtn3").bind("click",getRentInfoByIdcard);    //读取身份信息
        
        //正式门卡保存
        $("#submitBtn").bind("click",function(){
        	bindValid();
			//验证IC卡，身份证卡序列号是否重复
            var cardid = $("#b_cardid").val();
            if(cardid==''){   //只有新增时才需要验证,修改时卡的序列号不能修改
            	var cardno1 = $("#b_cardno1").val();     //IC卡序列号
            	var cardno2 = $("#b_cardno2").val();     //身份证卡序列号
            	if(cardno1!=''){
					chkcardnoisrepeat($("#b_cardno1"));
            	}
            	if(cardno2!=''){
					chkcardnoisrepeat($("#b_cardno2"));
            	}
			}
			var showlen = 0;
			$("#form2").find("span").each(function(i,dom){
				if($(this).is(":visible")){
					showlen = showlen+parseInt(1);
				}
			})
			//当showlen大于0时表示
			if(parseInt(showlen)>0){
				return;
			}else{
				saveData();
			}
			
			/**
			var rentid = $("#b_rentid").val();     //租客主键id
			var rentidcard = $("#b_rentidcard").val();     //租客身份证idcard
			var cardno1 = $("#b_cardno1").val();   //IC卡序列号
			var cardno2 = $("#b_cardno2").val();   //身份证卡内置序列号
			var roomid = $("#b_roomid").val();     //房间id
			var roomname = $("#b_roomname").val();     //房间名称
			var enddate = $("#b_enddate").val();     //失效时间
			if((rentidcard=='') || (enddate=='') || (cardno1==''&&cardno2=='') || (roomid==''&&roomname=='')){
				return;
			}else{
				//saveData();
			}
			**/
			
        });
        
        //一键延期
        $("#keyexten").bind("click",function(){
	    	//获取选择的记录
	    	var idArr = getSelectedRecordIdArr();
	    	var houseidArr = getSelectedHouseIdArr();
	   		if(idArr.length == 0 ||houseidArr.length == 0) return ;
		    var cardidstr = idArr.join(",");
		    var houseidstr = houseidArr.join(",");
		    var submit = function (v, h, f) {
	            if (v == 'ok'){
				    $.ajax({
		                url: '<c:url value="/Man/Card/delayManCardByJq.shtml"/>',
		                data:{'querymap.cardidstr':cardidstr,'querymap.houseidstr':houseidstr},
		                type: 'post',
		                async:false,
		                error: function (data) {
							parent.$.jBox.tip("与数据库连失败！请刷新后重试！", 'warning');
		                },
		                success: function (data) {
		                	if(data=='-1'){
								parent.$.jBox.tip("操作失败！", 'info');
		                	}else if(data=='0'){
								parent.$.jBox.tip("参数错误！请刷新页面后重试", 'warning');
		                	}else if(data=='1'){
								parent.$.jBox.tip("操作成功！", 'info');
        						mmg.load();
		                	}
		                }
			         });
		         }
             	return true;
        	};
        	parent.$.jBox.confirm("一键延期，将会对门卡有效期进行延期一年，确定进行该操作？", "提示", submit);
        });
        
        //房间id
        $("#b_roomid").bind("change",function(){
        	var roomid = $("#b_roomid").val();
        	if(roomid!=''){
	            //获取房间名称
	            var roomname = $("#b_roomid option:selected").text();
				$("#b_roomname").val(roomname.replace("房间",""));  //房间名称
        	}else{
				$("#b_roomname").val('');  //房间名称
        	}
        });
        
        //房间名称
        $("#b_roomname").bind("blur",function(){
       		autoselroomid();
        });
        
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
	
	//对门卡进行保存ajax处理操作
	function saveCard(){
		var cardno1 = $("#b_cardno1").val();   //IC卡序列号
		var cardno2 = $("#b_cardno2").val();   //身份证卡序列号
		var cardno = '';
		if(cardno1 !=''){
			cardno = cardno1;
		}
		if(cardno2 !=''){
			cardno = (cardno!=''?cardno+','+cardno2:cardno2);
		}
		//保存正式卡操作
		$.ajax({
			url: '<c:url value="/Man/Card/saveManCardByJq.shtml"/>',
			data:{
				'manCardBean.id':$("#b_cardid").val(),
				'manCardBean.cardtype':$("#b_cardtype").val(),
				'manCardBean.houseid':$("#b_houseid").val(),
				'manCardBean.cardno':cardno,
				'manCardBean.rentid':$("#b_rentid").val(),
				'manCardBean.roomid':$("#b_roomid").val(),
				'manCardBean.roomname':$("#b_roomname").val(),
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
	
	//验证IC卡，身份证卡序列号是否重复
	function chkcardnoisrepeat(jqobj){
		var cardTypeMap = {};
        <c:forEach items="${cardTypeMap}" var="cM">
        	cardTypeMap["${cM.key}"] = "${cM.value}";
        </c:forEach>
        //验证该卡序列号是否已经存在于库中,若存在,则提示该卡的类型
        //只有新增时才需要验证,修改时卡的序列号不能修改
        var cardno = jqobj.val();
		$.ajax({
			url: '<c:url value="/Man/Card/findManCardByCardNo.shtml"/>',
			data:{'manCardBean.cardno':cardno},
			type: 'post',
			async: false,
			dataType: 'json',
			error: function () {
				parent.$.jBox.tip("与数据库连失败！请刷新后重试！", 'warning');
			},
			success: function (d) {
				if(d[0].id!=""){
					var msg = "卡序列号为：\""+cardno+",\"已经登记过了,不能重复登记！";
             		msg += "该卡原登记为\""+(cardTypeMap[d[0].cardtype])+"\"。";
             		$("#valid-repeat",jqobj.parent()).html(msg);
             		$("#valid-repeat",jqobj.parent()).show();
             	}else{
             		$("#valid-repeat",jqobj.parent()).html('');
             		$("#valid-repeat",jqobj.parent()).hide();
             	}
			}
       });
	}
	
	//构造房间列表
	function mkManRoom(){
		var houseid = $("#b_houseid").val();  //获取房产ID
		$.ajax({
			url: '<c:url value="/Man/Card/findManRoomListByJq.shtml"/>',
			data:{'houseid':houseid},
			type: 'post',
			async: false,   //同步
			dataType: 'json',
			error: function () {
				parent.$.jBox.tip("与数据库连失败！请刷新后重试！", 'warning');
			},
			success: function (data) {
				if(data=="-1"){
					parent.$.jBox.tip("获取房间列表操作失败！请刷新后重试！", 'warning');
             	}else{
             		if(data.length==0){
             			//清空房间列表
             			$("#b_roomid option:gt(0)").remove();
					}else{
						var str = "";
						//通过jquery方法进行循环编译
						$.each(data,function(i,d){ 
							str += "<option value="+d.id+">"+d.commname+"</option>";
						});
						//先清空在添加
						$("#b_roomid option").not(":first").remove();
						$("#b_roomid").append(str);
					}
             	}
			}
       });
	}
	
	//生成正式卡页面，正式卡列表页面切换
    function showOrHide(sign){
        clearEditFormVal();
        if(sign == 1){      //生成正式卡页面
            $("#editFormId").show();
            $("#listFormId").hide();
            $("#b_cardno1").attr("disabled",false);    //卡的内置序列号可录入
            $("#b_cardno2").attr("disabled",false);    //身份证卡序列号可录入
            $("#b_enddate").val($("#b_defEndDate").val());   //重新赋值失效时间
            $("#submitBtn2").show();    //刷卡按钮显示
            $("#insert").val("取消生成正式卡");
            $("#insert").unbind("click").bind("click",function(){
                showOrHide(0);
            });
        }else{           //正式卡列表页面
            $("#editFormId").hide();
            $("#listFormId").show();
            $("#insert").val("生成正式卡");
            $("#insert").unbind("click").bind("click",function(){
        		mkManRoom();
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
    
    function updateOper(){
    	mkManRoom();
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
				if(data[0].id=="-1"){   //表示该条记录不存在查询不出结果集[系统在后台将会自动将其主键ID设置为-1]
					parent.$.jBox.confirm("该条记录不存在请刷新页面后重试！", "提示", submit);
				}else{
					//判断卡序列号的长度=16为身份证
					if((data[0].cardno).length==16){
						$("#b_cardno2").val(data[0].cardno); //身份证卡序列号[卡内置序列号]
					}else{
						$("#b_cardno1").val(data[0].cardno); //卡序列号[卡内置序列号]
					}
					$("#b_rentname").val(data[0].rentname); //租客姓名
					$("#b_roomid").val(data[0].roomid);  //租客房间
					$("#b_cardid").val(data[0].id);  //卡主键ID
					$("#b_cardtype").val(data[0].cardtype);  //卡类型
					$("#b_rentid").val(data[0].rentid);  //租客ID
					$("#b_rentidcard").val(data[0].rentidcard);  //租客身份证ID
					$("#b_houseid").val(data[0].houseid);  //房产ID
					$("#b_enddate").val(data[0].enddate.substr(0,10));  //失效时间
					
					//修改时卡的内置序列号不允许修改
					$("#submitBtn2").hide();
					$("#b_cardno1").attr("disabled",true);    //卡序列号[卡内置序列号]
					$("#b_cardno2").attr("disabled",true);    //身份证卡序列号[卡内置序列号]
					
		            $("#editFormId").show();
		            $("#listFormId").hide();
		            
		            //获取房间名称
		            var roomname = $("#b_roomid option:selected").text();
					$("#b_roomname").val(roomname.replace("房间",""));  //房间名称
		            
		            //js验证隐藏
			    	$("#valid_b_rentname").hide();
			    	$("#valid_b_cardno1").hide();
			    	$("#valid_b_cardno2").hide();
			    	$("#valid_b_roomid").hide();
			    	$("#valid_b_roomname").hide();
			    	$("#valid_b_enddate").hide();
			    	
			    	$(".valid-repeat").hide();
				}
			}
		});
    }
    
    //清空form表单中的ID 和 卡序列号
    function clearEditFormVal(){
    	//清空租客信息
    	$("#b_rentname").val("");
    	$("#b_rentid").val("");
    	
    	$("#b_cardid").val("");
    	$("#b_cardno1").val("");
    	$("#b_cardno2").val("");
    	
    	//清空房间信息
    	$("#b_roomid").val("");
    	$("#b_roomname").val("");
    	
    	//js验证隐藏
    	$("#valid_b_rentname").hide();
    	$("#valid_b_cardno1").hide();
    	$("#valid_b_cardno2").hide();
    	$("#valid_b_roomid").hide();
    	$("#valid_b_roomname").hide();
    	$("#valid_b_enddate").hide();
    	
    	$(".valid-repeat").hide();
    }	
    
    //检测是否选择记录
    function getSelectedRecordIdArr(){
    	//若新增，则跳出
        var rows = mmg.selectedRows();
		var idAttr=new Array();
        if(rows.length==0){
            parent.$.jBox.tip("请选择要操作的记录！", 'warning');
            return idAttr;
        }else{
        	for(var i=0;i<rows.length;i++){
        		idAttr.push(rows[i].id);
        	}
            return idAttr;
        }
        return idAttr;
    }
    
    //获取选择房产id
    function getSelectedHouseIdArr(){
    	//若新增，则跳出
        var rows = mmg.selectedRows();
		var idAttr=new Array();
        if(rows.length==0){
            parent.$.jBox.tip("请选择要操作的记录！", 'warning');
            return idAttr;
        }else{
        	for(var i=0;i<rows.length;i++){
        		idAttr.push(rows[i].houseid);
        	}
            return idAttr;
        }
        return idAttr;
    }
    
    //根据填入的房间名称进行自动选择
    function autoselroomid(){
    	var roomname = $("#b_roomname").val();   //获取房间名称
    	roomname = roomname + '房间';
    	$("#b_roomid option:gt(0)").each(function(i,dom){
    		var roomtext = $(this).text();
    		var roomid = $(this).val();
    		if(roomtext==roomname){
    			$("#b_roomid").val(roomid);   //设置选中的值
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
		var cardno1 = $("#b_cardno1").val();
		var cardno2 = $("#b_cardno2").val();
		if(cardno1=='' && cardno2==''){
			$("#valid_b_cardno1,#valid_b_cardno2").show();
		}else{
			$("#valid_b_cardno1,#valid_b_cardno2").hide();
		}
		
		var roomid = $("#b_roomid").val();      //租住房间id
		var roomname = $("#b_roomname").val();  //租住房间名称
		if(roomid == '' && roomname == ''){
			//租住房间
			$("#valid_b_roomid").show();
			$("#valid_b_roomname").hide();
		}else{
			$("#valid_b_roomid").hide();
			$("#valid_b_roomname").hide();
		}
		//当房间名称不为空，且房间名称不全都为数字时
		if(roomname!='' && isNaN(roomname) && roomid == ''){
			$("#valid_b_roomname").show();
		}else{
			$("#valid_b_roomname").hide();
		}
		
		//失效时间
		var enddate = $("#b_enddate").val();
		if(enddate == ''){
			$("#valid_b_enddate").show();
		}else{
			$("#valid_b_enddate").hide();
		}
	}
    
</script>
</body>
</html>