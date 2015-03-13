function makeRandomString(n){ 
	var baseStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; 
	for (var i = 0, r =""; i < n; i++) r += baseStr.charAt(Math.floor(Math.random() * 62));
	return r;
};
var PLUG_FINAL_RANDOMSTR = makeRandomString(32);
//将该变量动态加入到form表单中，并命名表单的名字为FINAL_RANDOMSTR
//获取确定表单
$(function(){
	var btnSureJqObj = $(document).find("form");
	btnSureJqObj.append("<input name='FINAL_RANDOMSTR' type='hidden' value='"+PLUG_FINAL_RANDOMSTR+"' size='40'/>");
 }
);

function FileDBUtil(){
	this.uploadFileId =  "";    //文件控件ID
	this.saveFolder   =  "";    //保存路径
	this.relaDataId   =  "";    //主记录ID
	this.fileUpLoadStyle = "1"; //1表示执行替换/新增上传 不等于1表示实现多个文件上传
	this.modsign         = "";  //模块标记
	this.multiFileUpload = false; //是否进行多文件上传
	this.uploadFileDesc =  ""; //如果配置了以下的'fileExt'属性，那么这个属性是必须的 [支持格式:jpg/gif/jpeg/png/bmp.]
    this.uploadFileExt = "" ;//允许的格式[*.jpg;*.gif;*.jpeg;*.png;*.bmp]
    this.uploadFileSizeLimit = 1024*1024*50; //默认50M
	this.FINAL_RANDOMSTR   = "";  //令牌--修改文件外健时使用【建议都生成】
	this.IS_DEBUG          = false; //是否进行调试
	this.insertFileMap          ={};    //该参数用于辅助使用
	this.isOnlyShowFile         = false;
	this.isAutoCommit           = true;
	this.fileSysUrl             = "";//http://192.168.1.168:13080/hr
	//附件删除、新增、上传JQUERY url
	this.configValues = {
		'custom_file_delete_jqurl':'',
		'custom_file_insert_jqurl':'',
		'custom_file_upload_jqurl':''
	};
	
	//外部接口
	this.setConfigValues = function(p){ 
		this.configValues = $.extend(this.configValues, p || {}); 
	};
	
	this.setInsertFileMap = function(p){ 
		this.insertFileMap = $.extend(this.insertFileMap, p || {}); 
	};
	
	//删除附件操作			
	this.deleteFileOper = function(index,fileid){
		 var $this = this;
		 if(confirm("确定禁止记录？")) {   
			 var parentFileSpan = $("#"+$this.uploadFileId+"FileList");
			 var spanJqObj = $("#"+$this.uploadFileId+"_efile_"+index);
			 $(spanJqObj,parentFileSpan).remove();
			 parentFileSpan.remove(spanJqObj);
			 var uploadJqObj = $("#"+$this.uploadFileId); 
			 var url = $this.configValues['custom_file_delete_jqurl'];
			 if(url == ""){
				alert('请重载附件【删除】JQUERY方法！');return;
			 }
		 	 url = $this.tranToFileServicePath(url);
			 queryparam = {"fileid":fileid};
			 $.ajax({
				 type: "get", 
				 url:url,
				 async:true,
				 data:queryparam, 
				 dataType : "jsonp",
				 jsonp: 'jsoncallback', //默认callback
				 success:
				 function(data) {
					if(data=="-1"){
						alert("删除文件失败！");
					}
					else{
						//$this.onLoadFileUploader();
						//$("#"+$this.uploadFileId+"_span").show();
					}
				 }
			 });	
		  }
	}; 
	//新增附件操作
	this.insertBaseFileByJq = function(modsign,savename,filename){
		 var $this = this;
		 var url = $this.configValues['custom_file_insert_jqurl'];
		 if(url == ""){
			alert('请重载附件【新增】JQUERY方法！');return;
		 }
		 url = $this.tranToFileServicePath(url);
		 queryparam = {
			"fileStyle":$this.fileUpLoadStyle,
			"savePath":$this.saveFolder,
			"modsign":modsign,
			"recid":$this.relaDataId,
			"savename":savename,
			"filename":encodeURIComponent(filename),
			"FINAL_RANDOMSTR" : $this.FINAL_RANDOMSTR
		};
		queryparam = $.extend(queryparam, $this.insertFileMap || {}); 
		 $.ajax({
			 type: "post", 
			 url:url,
			 async:false,   //一定要同步
			 dataType : "jsonp",
			 jsonp: 'jsoncallback', //默认callback
			 data:queryparam,
			 success:
			 function(json) {
				if(json=="-1"){
					alert('新增数据库文件出错！');
				}
				else{
					$this.reloadAfterInsert(json);
				}
			 }
		 });	
	}; 
	
	this.reloadAfterInsert = function(data){
		
	};
	
	//注册事件
	this.onLoadFileUploader=function(){
		var $this = this;
		if($this.uploadFileId == ""){
			alert("请设置要上传文件控件的ID！");return ;
		}
		if($this.isOnlyShowFile == false){
			if($this.saveFolder == ""){
				alert("请设置要上传文件的文件夹！");return ;
			}
			if($this.modsign == ""){
				alert("请设置上传文件模块标记！");return ;
			}
			//实现一个随机数
			if($this.FINAL_RANDOMSTR == "null" || $this.FINAL_RANDOMSTR==null || $this.FINAL_RANDOMSTR == ""){
				$this.FINAL_RANDOMSTR = PLUG_FINAL_RANDOMSTR;
			}
			if($this.FINAL_RANDOMSTR == ""){
				alert("请设置FINAL_RANDOMSTR，然后再上传！");return ;
			}
			
			//实现flash帮本的检测
			$this.checkFlashInfo();
			var uploadFileUrl = $this.configValues['custom_file_upload_jqurl'];
			if(uploadFileUrl==""){
				alert("请重载附件【上传】JQUERY方法！");return ;
			}
		    uploadFileUrl = $this.tranToFileServicePath(uploadFileUrl);
			var uploadJqObj  = $("#"+$this.uploadFileId);
			uploadJqObj.uploadify({
				'uploader'       : '../../Uploadify/uploadify.swf?v'+((new Date()).getTime()), // The path to the uploadify swf file
				'script'         : uploadFileUrl+'?savePath='+$this.saveFolder,
				'fileDataName'   : 'uploads',
				'auto'           : $this.isAutoCommit,
				'buttonText'     : 'BROWSE',//按钮上的文字
				'buttonImg'      : '../../Uploadify/show.gif'+'?timestep1'+((new Date()).getTime()),
				'height'         : 20,
				'width'          : 62,
				'multi'          : $this.multiFileUpload,//是否支持多文件上传
				'fileDesc'       : $this.uploadFileDesc, //如果配置了以下的'fileExt'属性，那么这个属性是必须的 [支持格式:jpg/gif/jpeg/png/bmp.]
				'fileExt'        : $this.uploadFileExt,//允许的格式[*.jpg;*.gif;*.jpeg;*.png;*.bmp]
				'sizeLimit'      : $this.uploadFileSizeLimit,
				'onComplete'     : function (event, queueID, fileObj, response, data){
					$this.insertBaseFileByJq($this.modsign,response,fileObj.name);
					
					if($this.IS_DEBUG){
						//弹出详细窗口
						alert("是否替换文件:==="+($this.fileUpLoadStyle=="1"?"是":"否")+"====\n 文件保存路径:==="+$this.saveFolder+"====\n 文件modsign:==="+$this.modsign+"====\n 外键id:==="+$this.relaDataId+"====\n 随机码:==="+$this.FINAL_RANDOMSTR+"====");
					}
				}
			});
		}
	};
	//附件下载方法
	this.downLoad = function(savepath,savename){
		$this = this;
		if(($this.fileSysUrl || "") != ""){
		    downframe.document.form1.action = $this.fileSysUrl+"/downfile.shtml";
		}
		else{
		    //downframe.document.form1.action = "/downfile.shtml";
		}
	    downframe.document.form1.inputPath.value = savepath;
	    downframe.document.form1.fileName.value = savename;
	    downframe.document.form1.submit();
	}; 
	
	//js构建文件列表
	this.makeFileList = function(fileListArr){ 
		var $this = this;
		var fileParentSpan = $("#"+$this.uploadFileId+"_span");
		//动态构建附件列表命名格式为 fileId+FileList如（uploads1FileList）
		var fileListSpan = $("<span id='"+$this.uploadFileId+"FileList'></span>");
		for(i=0;i<fileListArr.length;i++){
			(function(o){
					
				 var savepath = o.savepath;
				 var savename = o.savename;
				 var filename = o.filename;
				 var divTmp = null;
				 if($this.isOnlyShowFile == false){
					 divTmp   = $('<div id="'+$this.uploadFileId+'_efile_'+o.index+'">\
												<a href=\'JavaScript:void(0);\'>&gt;&gt;&nbsp;删除</a>：\
												<a href=\'JavaScript:void(0);\'>'+filename+'</a>\
											</div>');
					$("a:eq(0)",divTmp).bind("click",function(){
						$this.deleteFileOper(o.index,o.id);
					});
					$("a:eq(1)",divTmp).bind("click",function(){
						$this.downLoad(o.savepath,o.savename);
					});
				 }
				 else{
					divTmp   = $('<div id="'+$this.uploadFileId+'_efile_'+o.index+'">\
												附件'+(i+1)+'：<a href=\'JavaScript:void(0);\'>'+filename+'</a>\
											</div>');
					$("a:eq(0)",divTmp).bind("click",function(){
						$this.downLoad(o.savepath,o.savename);
					});
				 }
				fileListSpan.append(divTmp);
			})(fileListArr[i]);
		}
		fileParentSpan.after(fileListSpan);
	};
	
	//jquery辅助获取控件方法
	this.getFormFieldByJq = function(fieldname){
		var ftypestr = "text,hidden,checkbox,radio,submit,button";
		var ftypeArr = ftypestr.split(",");
		for(i=0;i<ftypeArr.length;i++){
			if($("input[name='"+fieldname+"'][@type="+ftypeArr[i]+"]").size()>0){
				return $("input[name='"+fieldname+"'][@type="+ftypeArr[i]+"]");
			}
		}		
		if($("select[name='"+fieldname+"']").size()>0){
			return $("select[name='"+fieldname+"']");
		}
		
		if($("textarea[name='"+fieldname+"']").size()>0){
			return $("textarea[name='"+fieldname+"']");
		}
		alert("找不到名字为："+fieldname+"的控件");
		return ;
	};
	//转换成文件服务器请求路径
	this.tranToFileServicePath = function(url){
		$this = this;
		var fileSysUrl  = $this.fileSysUrl || "";
		if(fileSysUrl == ""){return url;}
		else{
			//表示请求的是跨域文件服务器
			//因为传递的url有自带系统名称如/hr故要求把hr去除
			uA = url.split("/");
			var rtn = "";
			alert(uA.length);
			for(var i=2;i<uA.length;i++){
				rtn +="/"+uA[i];
			}
			return $this.fileSysUrl+rtn;
		}
	}
	//检测浏览器是否安装flash以及flash的版本
	this.doCheckFlash = function(){
		$this = this;
		var hasFlash = 0; //是否安装了flash插件
		var flashVersion = 0; //flash版本
		if(document.all){
			var isInstall = true;
			try {
				// version will be set for 7.X or greater players
				var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
				VSwf=swf.GetVariable("$version");
			} catch (e) {
				isInstall = false;
				return {f:0,v:0};
			}
			var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
			if(swf) {
				hasFlash=1;
				VSwf=swf.GetVariable("$version");
				flashVersion=parseInt(VSwf.split(" ")[1].split(",")[0]); 
			}
		}
		else{
			if(navigator.plugins && navigator.plugins.length > 0){
				var swf=navigator.plugins["Shockwave Flash"];
				if(swf){
					hasFlash = 1;
					var words = swf.description.split(" ");
					for(var i=0;i<words.length;i++){
						if(isNaN(parseInt(words[i]))) continue;
						flashVersion = parseInt(words[i]);
					}
				}
			}
		}
		return {f:hasFlash,v:flashVersion};
	};
	//实现检测操作
	this.checkFlashInfo = function(){
		$this = this;
		var fls = $this.doCheckFlash();
		if(fls.f){
			//判断flash的版本是否小于10，若是，则提示升级操作
			if(parseInt(fls.v)<10){
				$("#"+$this.uploadFileId).after("<div style=\"background-color: #FDE5DD !important;border: 2px solid #FBCBBC !important;width:90%;\"><a href=\"JavaScript:$this.downLoad('/Pelite/Download/','flash.rar');\">&nbsp;&nbsp;当前flash插件版本为: "+fls.v+"；不适用于本系统-附件上传功能，点此链接升级[安装后请重启浏览器]！</a></div>");
			}
		}
		else{
				$("#"+$this.uploadFileId).after("<div style=\"background-color: #FDE5DD !important;border: 2px solid #FBCBBC !important;width:90%;\"><a href=\"JavaScript:$this.downLoad('/Pelite/Download/','flash.rar');\">&nbsp;&nbsp;本电脑尚未安装Flash插件--不能使用附件上传功能，点此链接安装[安装后请重启浏览器]！</a></div>");
		}
	};
};