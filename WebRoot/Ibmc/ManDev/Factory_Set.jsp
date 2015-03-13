<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../Include/TagLib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="<c:url value='/Style/main.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/Style/bootstrap.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/Style/valid.css'/>"/>
	<script src="<c:url value='/Script/jquery-1.9.1.min.js'/>"></script>
	<script src="<c:url value='/My97DatePicker/WdatePicker.js'/>"></script>
	<script src="<c:url value='/Script/plugin/valid/FormValidator.1.0.min.js'/>"></script>
    <script type="text/javascript">
        $(function () {
       		 bindValid();
            $("#submitBtn").bind("click", callBack);
            $("#closeBtn").bind("click", function () {
                parent.$.jBox.close();
            });
            $("#getBtn").bind("click", addModel);
        });

        function callBack() {
        	$("#form1").attr("action",'<c:url value='/Man/Dev/savemanfactory.shtml'/>').trigger("submit");
			if($("#formerror").text() == "ok"){
				return true;
			}
			return false;
        }
        
        //验证
        function bindValid(){
			//名称
			$("#facname").attr("fv-empty","false").attr("fv-empty-msg","厂商名称<fmt:message key="format.null" bundle="${bms}"/>")
						  .attr("fv-msg-success","").attr("fv-msgpanel","valid_facname");
			$("#facname").parent().append('<span id="valid_facname" class="validate-info"></span>');

			//地址
			$("#address").attr("fv-empty","false").attr("fv-empty-msg","地址<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_address");
			$("#address").parent().append('<span id="valid_address" class="validate-info"></span>');
			//联系人
			$("#contact").attr("fv-empty","false").attr("fv-empty-msg","联系人<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_contact");
			$("#contact").parent().append('<span id="valid_contact" class="validate-info"></span>');
			//联系电话
			$("#telephone").attr("fv-empty","false").attr("fv-empty-msg","联系电话<fmt:message key="format.null" bundle="${bms}"/>")
							  .attr("fv-msg-success","").attr("fv-msgpanel","valid_telephone");
			$("#telephone").parent().append('<span id="valid_telephone" class="validate-info"></span>');
			
			//绑定form表单自定义验证
			$("#form1").attr("fv-validate","true").attr("fv-msg-success","ok").attr("fv-msgpanel","formerror");
		}
		
		//动态添加型号
		function addModel(){
			var index = $("tbody tr",$(".addTable")).size();
		    var Tr = "<tr><td>"+(index+1)+"</td><td><input type=\"hidden\" name=\"modelId\" value=\"${manFacConfigBean.id}\" /><input type=\"text\" class=\"text_20\" name=\"facmodel\" value=\"${manFacConfigBean.facmodel}\"/></td><td><button class=\"btn btn-danger\"  onclick=\"Javascript:delModel(this)\">删除</button></td></tr>";
		    $("tbody",$(".addTable")).append(Tr);
			resetAddTableTrIndex();
		    return false;
		}
		
	
		 //删除
	    function delModel(obj){
			$(obj).parent().parent().remove();
			resetAddTableTrIndex();
			return false;
	    }
	    
	    function resetAddTableTrIndex(){
	    	$("tbody tr",$(".addTable")).each(function(i,dom){
	    		$("td:eq(0)",$(dom)).html(i+1);
	    	});
	    }
    </script>
    <style type="text/css">
    	.addTable{
    		width:95%;
    	}
	    .addTable tbody td{
	    	   border: #CCCCCC solid 1px;
	    }
    </style>
</head>
<body>
<div class="editContainer">
    <div class="errorBlock"></div>
    <!-- 查询条件 --->
    <form action="" method="post" id="form1" name="form1" >
         <table class="editTable">
            <tbody>
            <tr>
                <td class="left">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
                <td width="60%" class="right">
                    <input type="text" name="manFactoryBean.facname" id="facname" value="${manFactoryBean.facname}" class="text_30"/>
                </td>
            </tr>
            <tr>
                <td class="left">联&nbsp;系&nbsp;&nbsp;人：</td>
                <td class="right">
                    <input type="text" name="manFactoryBean.contact" id="contact" value="${manFactoryBean.contact}" class="text_30"/>
                </td>
            </tr>
            <tr>
                <td class="left">联系电话：</td>
                <td class="right">
                    <input type="text" name="manFactoryBean.telephone" id="telephone" value="${manFactoryBean.telephone}" class="text_30"/>
                </td>
            </tr>
            <tr>
                <td class="left">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</td>
                <td class="right">
                    <input type="text" name="manFactoryBean.address" id="address" value="${manFactoryBean.address}" class="text_30"/>
                </td>
            </tr>
            <tr>
                <td class="left">型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
                <td class="right">
	                  <table class="addTable">
	                  	<thead>
	                  	 <tr><td width="6%">序号</td><td width="64%">设备型号</td><td  width="10%"><button id="getBtn" class="btn btn-info">添加</button></td></tr>
	                  	</thead>
	                  	<tbody>
	                  	  <c:forEach items="${dataList}" var="data" varStatus="st">
	                  		<tr><td>${st.index+1}</td><td><input type="hidden" name="modelId" value="${data.id}" /><input type="text"  class="text_20" name="facmodel" value="${data.facmodel}"/></td><td><button  class="btn btn-danger" onclick="Javascript:delModel(this)">删除</button></td></tr>
	                  	 </c:forEach>
	                   </tbody>
	                 </table>
                </td>
            </tr>
            <tr>
                <td class="left">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
                <td class="right">
                    <textarea name="manFactoryBean.remark" class="textarea_50_5">${manFactoryBean.remark}</textarea>
                </td>
            </tr>
            </tbody>
            <!-- 以下为隐藏域 -->
            <tfoot>
            <tr>
                <td colspan="2" align="center">
                    <input type="button" id="submitBtn" class="btn btn-info" value="确定"/>
                    <input type="button" id="closeBtn" class="btn btn-info" value="关闭"/>
                    <input type="hidden" name="tokenid" value="${tokenid}" />
                    <input type="hidden" name="manFactoryBean.id" value="${manFactoryBean.id}" />
                    <span id="formerror"></span>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
	
</div>
</body>
</html>