/**
* 功能描述 选项卡Jquery自定义插件
* @author 许东雄
* @date:2014-09-29
* 参数：	tabId 选项卡 ID
		conId 内容	ID
**/
(function($){
	$.fn.extend({
			//插件名称
			tabs:function(options){
				//参数和默认值
				var defaults = {
					tabId : "",	//选项卡ID
					conId : "",//内容DIV ID
					nodeLink : ""
				};
				var options = $.extend(defaults,options);
				var o = options;
				num = 1;  //隐藏卡中相对于导航新增的li个数
				//$("span",$(this)).bind("click",function(){
						//jQuery.fn.addTab($(this),o.tabId,o.conId);
				//加载数据并且添加右上导航选项卡
				jQuery.fn.addTab(o.nodeLink,o.tabId,o.conId);
						
				//});
				//绑定选项卡点击事件
				$("span.tab",o.tabId).live("click",function(){
                                     
					var Index = $("li",o.tabId).index($(this).parent());//获取当前关闭选项卡下表
					var objId = $(this).attr("id") + "_content";
					 $("iframe",o.conId).hide();
					 $("li",o.tabId).removeClass("current");
                    //删除非当前选中的tab删除按钮
                     $("li",o.tabId).not(".current").find('span:eq(1)').removeClass("remove");
					  //删除隐藏卡中当前Tab定位
					 $("li",$('#collect')).removeClass("currentSwitch");
					 $("#" + objId).show();
					 $(this).parent().addClass("current"); 
                    //增加当前选中的tab删除按钮
                     $(this).next().addClass('remove');
		             //显示隐藏卡中当前Tab定位
					 $("li:eq("+(Index+num)+")",$('#collect')).addClass("currentSwitch");
					 return false;
					
				});
                
				//选项卡浮动效果
				$('span.remove',o.tabId).live('mouseover',function(){
					$('span.remove',$(this).parent()).removeClass('remove').addClass('removeHover');
					  //绑定关闭选项卡事件
						$('span.removeHover',o.tabId).live('click', function() {
							var Index = $("li",o.tabId).index($(this).parent());//获取当前关闭选项卡下表
							var tabId = $(this).parent().find(".tab").attr("id");
							//移除隐藏卡中相应的li
							$("li:eq("+(Index+num)+")",$('#collect')).remove();
							 $("#" + tabId +"_content").remove();
							$(this).parent().remove();
							if($("li.current",o.tabId).length == 0 && $("li",o.tabId).length>0){
								 if(Index>0)Index = Index-1;
								 var objTab = $("li:eq("+Index+")",o.tabId);
								 objTab.addClass("current");
								 objTab.find('span:eq(1)').addClass('remove');
								  //显示隐藏卡中当前Tab定位
								 $("li:eq("+(Index+num)+")",$('#collect')).addClass("currentSwitch");
								 
								 var objTabId = $(objTab).find("span.tab").attr("id");
								$("#" + objTabId + "_content").show();
							}
							
							return false;
						});
					return false;
				});
				$('span.removeHover',o.tabId).live('mouseout',function(){
					$('span.removeHover',o.tabId).removeClass('removeHover').addClass('remove');
					return false;
				});
				
				
				
				//绑定隐藏卡点击事件
				$('span.collectTab',$("#collect")).live('click',function(){
					var Index = $("li",$('#collect')).index($(this).parent());
					//当前在隐藏卡中被选中的选项卡
					var objTab = $("li:eq("+(Index-num)+")",o.tabId);
					//当前选中的选项卡
					var preTab = $('li',o.tabId).filter(".current");
					preTab.removeClass("current");
					preTab.find('span:eq(1)').removeClass('remove');
					  //删除隐藏卡中当前Tab定位
					 $("li",$('#collect')).removeClass("currentSwitch");
					 objTab.addClass("current");
					 objTab.find('span:eq(1)').addClass('remove');
					   //显示隐藏卡中当前Tab定位
					 $(this).parent().addClass("currentSwitch");
					 $("iframe",o.conId).hide();
					 var collectTabId = $(this).attr("id");
					 $("#" + collectTabId + "_content").show();
					 $("#collect").hide();
				});
				
                //隐藏卡中的删除span的悬浮效果
				$('span.cancel',$("#collect")).live('mouseover',function(){
						 $(this).removeClass('cancel').addClass('cancelHover');
						 //删除隐藏卡中的li
						$('span.cancelHover',$("#collect")).live('click', function() {
							var Index = $("li",$('#collect')).index($(this).parent());
							$(this).parent().remove();
							$("li:eq("+(Index-num)+")",o.tabId).remove();
							 //删除隐藏卡中当前Tab定位
							// $("li",$('#collect')).removeClass("currentSwitch");
							if($("li.currentSwitch",$("#collect")).length == 0 && $("li",$("#collect")).length>0){
								 if(Index>0)Index = Index-1;
								 //导航中选项卡
								 var objTab = $("li:eq("+(Index-num)+")",o.tabId);
								 objTab.addClass("current");
								 objTab.find('span:eq(1)').addClass('remove');
								 $("iframe",o.conId).hide();
								 var objTabId = $(objTab).find("span.tab").attr("id");
								 $("#" + objTabId + "_content").show();
								 //显示隐藏卡中当前Tab定位
								  var curTab = $("li:eq("+Index+")",$("#collect"));
								  curTab.addClass("currentSwitch");
							}
							 $("#collect").hide();
							 return false;
						});
						return false;
				});
				$('span.cancelHover',$("#collect")).live('mouseout',function(){  
					$(this).removeClass('cancelHover').addClass('cancel');
					return false;
				});
				
				//隐藏卡中全部关闭
				$("#closeAllSwitch").live('click',function(){
					$("li",o.tabId).not("li:first").remove();
					$("li",$("#collect")).not("li:lt(2)").remove();
				    $("li:first span.tab",o.tabId).trigger("click");
				});

             //隐藏卡中li背景色悬浮动变化
			  $('li',$('#collect')).hover(
				 function(){
					 $(this).addClass('hover');
				 },function(){
				   $(this).removeClass('hover');
				}
			  );
			
			}
		});		
})(jQuery);
jQuery.fn.addTab=function(objLink,objTab,objCon){
    var maxTabNum = 9;
    if($("li",objTab).length>=maxTabNum){
        if($("#" + objLink.rel).length == 0){
            var firstTab = $("li:eq(1)",objTab).find('span').html();

            $.jBox.confirm("选项卡打开超过"+maxTabNum+"个，将会关闭[<span class='fontcolor_red'>"+firstTab+"</span>]选项卡，是否继续！", "提示", function (v, h, f) {
                if (v == 'ok'){
                	//删除Iframe
					var delTabId = $("li:eq(1) span:eq(0)",objTab).attr("id");
					$("#" + delTabId + "_content").remove();
					//删除tab标签
                    $("li:eq(1)",objTab).remove();
                    addNewTab();
				    $("li:eq(2)",$("#collect")).remove();
					addDownMenu();
                }
                else if (v == 'cancel'){
                    isCanContinue = false ;
                }
                return true;
            });
        }else{
      
            redirectTab();
            return;
        }
    }else{
        if(objLink.src==undefined) return;
        //定位已打开的选项卡
        if($("#" + objLink.rel).length != 0){
            redirectTab();
            return;
        }
        addNewTab();
		addDownMenu();
    }

    function redirectTab(){
        var objId = objLink.rel + "_content";
        $("iframe",objCon).hide();
        $("li",objTab).removeClass("current");
        //删除非当前选中的tab删除按钮
        $("li",objTab).not(".current").find('span:eq(1)').removeClass("remove");
       // $("#" + objId).show();
        $("#" + objId).remove();
        objCon.append("<iframe name=\"iframe\" id='" +objLink.rel + "_content' src=\""+objLink.src+"\" frameborder=\"0\" scrolling=\"no\" height=\"100%\" width=\"100%\"></iframe> ");
        $("#"+objLink.rel).parent().addClass("current");
        $("#"+objLink.rel).parent().find('span:eq(1)').addClass('remove');
    }
	
	
	
  	
    function addNewTab(){
		var title = objLink.name;
		if(title.length>6){
			title = title.substr(0,6);
		}
        $("li",objTab).removeClass("current");
        $("iframe",objCon).hide();
        objTab.append("<li class='current'><span class='tab' id='" +objLink.rel + "' title='"+objLink.name+"'>" + title + "</span><span class='remove empty'>&nbsp;</span></li>");
        objCon.append("<iframe name=\"iframe\" id='" +objLink.rel + "_content' src=\""+objLink.src+"\" frameborder=\"0\" scrolling=\"no\" height=\"100%\" width=\"100%\"></iframe> ");
        $("#" + objLink.rel + "_content").show();
        $('li',objTab).not(".current").find('span:eq(1)').removeClass("remove");
    }
	//加载至隐藏卡下拉菜单中
	function addDownMenu(){
		var title = objLink.name;
		if(title.length>6){
			title = title.substr(0,6)+"..";
		}
		$("li",$("#collect")).removeClass("currentSwitch");
		$('#collect').append("<li  class='currentSwitch'><span  class='collectTab' id='" +objLink.rel + "' title='"+objLink.name+"'>"+title+"</span><span class='cancel'>&nbsp;</span></li>");
	}
}