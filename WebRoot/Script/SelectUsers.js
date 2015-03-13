/**
*   功能：人员选择辅助类[参考页面WebRoot\Base\Purview\User_Win1.jsp]
*   描述：
    initBody            : 初始化页面方法
	setFormControlNames : {集合}里边的id可以自行命名 eg 新的页面单位下拉框id为 unitid2 --> {f_unitid:'unitid2'}
	setQueryUserUrl     : {集合}覆盖人员查询链接
	setDefaultVals      : {集合}1、一般不须重新赋值，若要设置部门性质、部门、班组默认选中值，则须对它重新赋值 2、获取班组、子班组是否需要加入全部
	reloadCheckForm  : 确定按钮点击触发的方法[该方法为必须覆盖方法] eg CommSelectUsers实例.reloadCheckForm = 你的方法
	      只能调用以上五个方法，其他的不能调用
*   连金亮 2011-06-04（删除了部门性质功能）
*/  
function CommSelectUsers(){
	//表单上面的字段对应的id，可以覆盖该对象的属性[不允许“.”形式的id] 
	//eg 新的页面单位下拉框id为 unitid2 --> this.formControlNames['f_unitid']='unitid2'
	//以下为必须定义的表单id
	this.formControlNames = {
		f_unitid : 'unitid',
		f_deptid : 'deptid',
		f_groupid: 'groupid',
		f_subgroupid:'subgroupid',
		f_preview:'preview',
		f_selview:'selview',
		f_selview_help:'',
		f_orgs_hide:true,//关键自不为空时是否隐藏部门班组
		f_btn_query:'btn_query',
		f_keyword:'keyword',
		f_fields:'fields',
		f_btn_sure:'btn_sure'
	};
	
	this.setFormControlNames = function(p){
        this.formControlNames = $.extend(this.formControlNames, p || {}); 
	};
	
	//请求地址
	this.actionUrls = { 
		action_dept_url :'listbasedept.shtml',
		action_group_url :'listbasegroup.shtml',
		action_subgroup_url :'listbasegroupsub.shtml',
		action_query_userurl:'listbaseunitusers.shtml'
	};
	
	this.setQueryUserUrl = function(p){
        this.actionUrls = $.extend(this.actionUrls, p || {}); 
	};
	//页面第一次加载时对选择框的默认选择值集合
	this.defaultVals = {
		d_deptid : $("#deptid2").val(),
		d_groupid: '',
		d_dept_addall:false,
		d_group_addall:false,
		d_subgroup_addall:false
	};
	
	
	this.setDefaultVals = function(p){ 
        this.defaultVals = $.extend(this.defaultVals, p || {}); 
	}; 
	
	//页面初始化数据
	this.initBody = function(){
		var $thisClass = this; 
		
		
		//绑定单位变动事件
		$thisClass.mkJqueryObj($thisClass.formControlNames['f_unitid']).bind("change",function(){$thisClass.queryDeptList();});
		//联动查询部门列表
		$thisClass.queryDeptList();
		
		//初始化为未默认
		var p = {
			d_nature : '',
			d_deptid : '',
			d_groupid: ''
		};
		//绑定部门变动事件
		$thisClass.mkJqueryObj($thisClass.formControlNames['f_deptid']).bind("change",function(){$thisClass.queryGroupList();});
		//绑定班组变动事件
		$thisClass.mkJqueryObj($thisClass.formControlNames['f_groupid']).bind("change",function(){$thisClass.querySubGroupList();});
		//绑定子班组变动事件
		$thisClass.mkJqueryObj($thisClass.formControlNames['f_subgroupid']).bind("change",function(){$thisClass.queryUsersList();});
		//绑定关键字变动事件
		$thisClass.mkJqueryObj($thisClass.formControlNames['f_keyword']).bind("propertychange",function(){$thisClass.detailDeptGroupTrShowOrHide();});
		//绑定查询按钮点击事件
		$thisClass.mkJqueryObj($thisClass.formControlNames['f_btn_query']).bind("click",function(){$thisClass.buttonQuery();});
		//绑定确定按钮点击事件
		$thisClass.mkJqueryObj($thisClass.formControlNames['f_btn_sure']).bind("click",function(){$thisClass.reloadCheckForm();});
		
	};
	//获取jquery对象[辅助]
	this.mkJqueryObj = function(name){
		return $("#"+name);
	};
	
	//查询部门列表
	this.queryDeptList = function(){
		var url = this.actionUrls['action_dept_url'];
		if(this.replaceNullToEmpty('f_unitid')!==""){
			var queryparam = {
				unitid:this.replaceNullToEmpty('f_unitid')
			};
			var jqObj = this.mkJqueryObj(this.formControlNames['f_deptid']);
			var sedValTmp = (this.defaultVals['d_deptid']===''?this.replaceNullToEmpty('f_deptid'):this.defaultVals['d_deptid']);
			var needDefTmp   = ((this.defaultVals['d_deptid']===''&& !this.defaultVals['d_dept_addall'])?true:false);
			var setparam = {
				codename:'organname',
				sedVal:sedValTmp,
				needDef:needDefTmp,
				addDefText:(this.defaultVals['d_dept_addall']?'全部':'')
			};
			this.ajaxQuery(jqObj,url,queryparam,setparam);
			this.queryGroupList();
		}
		else{
			//查询人员
			$("#"+this.formControlNames['f_deptid']+",#"+this.formControlNames['f_groupid']+",#"+this.formControlNames['f_subgroupid']).each(function(){
				$(this).attr("length",0).hide();
			});
			this.queryUsersList();
		}
	};
	
	//查询班组列表
	this.queryGroupList = function(){
		var url = this.actionUrls['action_group_url'];
		if(this.replaceNullToEmpty('f_deptid')!==""){
			var queryparam = {
				deptid:this.replaceNullToEmpty('f_deptid')
			};
			var jqObj = this.mkJqueryObj(this.formControlNames['f_groupid']);
			var sedValTmp = (this.defaultVals['d_groupid']===''?this.replaceNullToEmpty('f_groupid'):this.defaultVals['d_groupid']);
			var needDefTmp   = ((this.defaultVals['d_groupid']===''&& !this.defaultVals['d_group_addall'])?true:false);
			var setparam = {
				codename:'organname',
				sedVal:sedValTmp,
				needDef:needDefTmp,
				addDefText:(this.defaultVals['d_group_addall']?'全部':'')
			};
			this.ajaxQuery(jqObj,url,queryparam,setparam);
			this.querySubGroupList();
		}
		else{
			//查询人员
			$("#"+this.formControlNames['f_groupid']+",#"+this.formControlNames['f_subgroupid']).each(function(){
				$(this).attr("length",0).hide();
			});
			this.queryUsersList();
		}
	};
	
	//查询子班组列表
	this.querySubGroupList = function(){
		var url = this.actionUrls['action_subgroup_url'];
		var queryparam = {
			groupid: this.replaceNullToEmpty('f_groupid')
		};
		
		//判断该部门是否是末端;2011-09-19
		if(""!=this.replaceNullToEmpty('f_groupid')){
			if(this.findDataOrganBeanByJq(this.replaceNullToEmpty('f_groupid')).endsign=="1"){
				$("#"+this.formControlNames['f_subgroupid']).attr("length",0).hide();
				this.queryUsersList();
				return ;
			}
			else{
				var jqObj = this.mkJqueryObj(this.formControlNames['f_subgroupid']);
				var setparam = {
					codename:'organname',
					sedVal:this.replaceNullToEmpty('f_subgroupid'),
					needDef:false,
					addDefText:(this.defaultVals['d_subgroup_addall']?'全部':'')
				};
				this.ajaxQuery(jqObj,url,queryparam,setparam);
			}
		}
		else{
			$("#"+this.formControlNames['f_subgroupid']).attr("length",0).hide();
		}
		this.queryUsersList();
	};
	
	//查询人员
	this.queryUsersList = function(){  
		//获取selview的列表值
		var selViewId = this.formControlNames['f_selview_help'];
		if(""===selViewId){
			selViewId = this.formControlNames['f_selview'];
		}
		var jQSelView = $("#"+selViewId+" option");  
		var url = this.actionUrls['action_query_userurl'];
		var queryparam ={
			unitid: this.replaceNullToEmpty('f_unitid'),
			deptid: this.replaceNullToEmpty('f_deptid'),
			groupid: this.replaceNullToEmpty('f_groupid'),
			groupid2: this.replaceNullToEmpty('f_subgroupid'),
			fields: this.replaceNullToEmpty('f_fields'),
			keyword: encodeURIComponent(this.replaceNullToEmpty('f_keyword'))
		};
		var jQobj = this.mkJqueryObj(this.formControlNames['f_preview']);
		jQobj.attr('length',0);
		$.ajax({
			 type: "POST", 
			 url:url,
			 async:true,
			 data:queryparam, 
			 dataType:"json",
			 success:
			 function(data) {  
				for(var i in data){
					var isSelected = false;
					for(j=0;j<jQSelView.size();j++){
						if(jQSelView[j].value == data[i].id){
							isSelected = true;break;
						}
					}
					if(!isSelected){
						$("<option value='"+data[i].id+"' >"+eval("data[i].username")+"</option>").appendTo(jQobj);
					}
				} 
			 }
		 });			
	};
	
	//点击查询按钮触发事件有值
	this.buttonQuery = function(){  
		this.detailDeptGroupTrShowOrHide();
		this.queryUsersList();
	};
	
	//设置部门、班组行显示或者隐藏
	this.detailDeptGroupTrShowOrHide = function(){
		if(this.isHasKeyWordVal()&&this.formControlNames['f_orgs_hide']){
			this.getDeptGroupTr().hide();
		}
		else{
			this.getDeptGroupTr().show();
		}
	};
	
	//获取部门班组行对象
	this.getDeptGroupTr = function(){
		var $thisClass = this; 
		var jQNatureObj = $thisClass.mkJqueryObj($thisClass.formControlNames['f_deptid']);
		var jQTrObj = jQNatureObj.parent().parent();
		return jQTrObj;
	};
	
	//检测关键字是否
	this.isHasKeyWordVal = function(){
	    var keyWordVal = this.replaceNullToEmpty('f_keyword');
	    keyWordVal = $.trim(keyWordVal);
	    return keyWordVal==""?false:true;
	};
	
	//替换null为空[辅助]
	this.replaceNullToEmpty=function(f_field_temp){
		var str = this.mkJqueryObj(this.formControlNames[f_field_temp]).val();
		if(str == null || str == "null"){
			str = "";
		} 
		return str;
	}; 	
	
	
	/**
	* jQobj jQuery对象
	* url   请求路径
	* param 请求参数[不对外部开放]
	* setparam 设置参数
	* [   
		1、addDefText：给下拉框加入如“请选择”的项 
		2、codename：查询的列名称 
		3、sedVal：下拉框默认选择值 
		4、needDef：是否需要默认选择第一项
	   ]
	**/
	this.ajaxQuery = function(jQobj,url,queryparam,setparam){
		var rtnlist = null;
		queryparam['timestmp'] = (new Date()).getTime();
		setparam['needasync']  = setparam['needasync'] || false;
		$.ajax({
			 type: "POST", 
			 url:url,
			 async:setparam['needasync'],
			 data:queryparam, 
			 dataType:"json",
			 success:
			 function(json) { 
				jQobj.show();
				
				mkSelectObj(jQobj,json,setparam['addDefText'],setparam['codename'],setparam['sedVal'],setparam['needDef']);
				//构造下拉列表
				function mkSelectObj(jQObj,data,addDefText,codename,sedVal,needDef){ 
					codename = codename || 'codename'; 
					addDefText = addDefText || '';
					try{
						jQObj.css("width", "auto");
						jQObj.attr("length",0);
					}catch(e){
					}
					if(addDefText != ''){
						$("<option value=''>"+addDefText+"</option>").appendTo(jQObj);
					}else{
						if(''==data)jQobj.hide();
					}
					for(var i in data){
						$("<option value='"+data[i].id+"' "+(((data[i].id==sedVal)||(needDef && i==0))?'selected':'')+">"+eval("data[i]."+codename)+"</option>").appendTo(jQObj);
					} 
				}
			}
		});   
	};  
	//机构详细
	this.findDataOrganBeanByJq = function(orgId){
		var url = "finddataorganbeanbyjq.shtml";
		var dataOrganBeanJq = null;
		$.ajax({
			 type: "POST", 
			 url:url,
			 async:false,
			 data:{"id":orgId}, 
			 dataType:"json",
			 success:
			 function(json) { 
				dataOrganBeanJq = json[0];
			 }
		});
		return dataOrganBeanJq;
	};
	//页面提交时重载用的[必须重载该方法]
	this.reloadCheckForm = function(){
		alert('请重载该方法！！');
	};
	//重写回车事件
	this.reloadKeyDown = function(){  
		$thisClass = this;
		document.onkeydown = function (e) { 
			var theEvent = window.event || e; 
			var code = theEvent.keyCode || theEvent.which; 
			if (code == 13) {
				$thisClass.mkJqueryObj($thisClass.formControlNames['f_btn_query']).trigger("click");
				return false;
			} 
		} 
	};
};
	