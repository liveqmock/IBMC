
$(function () {
    $("#fontPage").bind("click", function () {
        location.href = "Index.jsp";
    });
    $("#out").bind("click", function () {
        var submit = function (v, h, f) {
            if (v == 'ok'){
            	location.href = "saveloginout.shtml";
            }
            else if (v == 'cancel')
            return true;
        };
        $.jBox.confirm("确定退出吗？", "提示", submit);
    });

    $(".switchTabs>span").hover(
	function(){ 
     	 $(this).removeClass('tabMore').addClass('tabMoreHover'); 
      },function(){
  		 $(this).removeClass('tabMoreHover').addClass('tabMore');
       }
    );
   
   $(".switchTabs>span").bind("click",function(){
	 var menuObj = $("#collect");
	 if(menuObj.is(":hidden")){
		menuObj.show();
		$("#switchIframe").show();
	 }else{
			menuObj.hide();
	 }
	 menuObj.hover(
		 function(){
		 },
		function(){
			$(this).hide();
			$("#switchIframe").hide();
		 });
	 return false;
   });
   
   smallMenuEvent();
   $(window).resize(reSizeLeftMenuHeiht);
});




//加载子菜单
var setting = {
    data: {
        simpleData: {
            enable: true   ,
            idKey: "id",
            pIdKey: "pid"
        }
    },
    callback: {
        onClick:function(e, id, node){
            if(!node.isParent) {
                $("#leftMenuBar").tabs({tabId: $("#tabs"), conId: $("#content"),nodeLink:node});
            }
        }
    },
	view: {
		fontCss: getFont,
		nameIsHTML: true,
		dblClickExpand: true,
		showLine: true,
		selectedMulti: false
	}
};
function getFont(treeId, node) {
	return node.font ? node.font : {};
}	
function initMenuTree(sys) {
	fatherid = sys.split("_")[1];
	//查询页面缓存中是否存在该系统的菜单
	if($("#sysSubMenu_"+sys+" li").size()>0) return;
    $.ajax({
        url: 'leftsubmenu.shtml?fatherid='+fatherid,
        type: 'post',
        dataType: 'json',
        error: function () {
            alert('Error loading XML document');
        },
        success: function (jsons) {
            $.fn.zTree.init($("#sysSubMenu_"+sys), setting, jsons);
        }
    });
}

//绑定子菜单事件
function smallMenuEvent(flag){
   reSizeLeftMenuHeiht();

    //左侧菜单栏
    var leftMenu_head = $('.sysMenu > span');
    var leftMenu_body = $('.ztree');
    leftMenu_head.bind('click', function() {
        $this = $(this).parent();
        // 点击触发“隐藏与显示”事件
        if ($this.attr('class').indexOf('active')<0){
            leftMenu_body.hide();
            $('ul:first',$this).toggle();
            leftMenu_head.parent().removeClass('active');
            $this.addClass('active');
            initMenuTree($(this).attr("id"));
        }else{
            $this.find('ul:first').hide();
            $this.removeClass('active');
        }
    });
    if(flag == undefined){
        leftMenu_head.first().trigger("click");
        leftMenu_head.parent().first().addClass('active').find('ul:first').slideDown('normal');
    }
}

//重新设置左侧菜单的高度
function reSizeLeftMenuHeiht(){
    var len = $(".sysMenu").size();
    var leftMenuBarHeight = leftMenuBar;
    var menuHeight = $(".sysMenu span").height();
    var menuPaddTop = parseInt(($("#leftMenuBar").css("padding-top").replace("px","")));
    var height = $('#leftMenuBar').height()-($("#header").height()+menuPaddTop*2)-menuHeight*len;
    $('#leftMenuBar ul.ztree').css({"overflow-y":"auto","margin":0,"height":height});
    //右侧内容
    $("#content").height($('#leftMenuBar').height()-($("#header").height()+menuPaddTop*2));

}
    
    
function iFrameHeight() {

    var ifm= document.getElementById("index_content");

    var subWeb = document.frames ? document.frames["index_content"].document :

            ifm.contentDocument;

    if(ifm != null && subWeb != null) {

        ifm.height = subWeb.body.scrollHeight;
    }
}


function reloadForSubPage(){
	//获取当前选中的iframe，并调用其内部方法reloadPage
    var iframeId = ($("#tabs .current .tab").attr("id")+"_content");
    
    var ifm= document.getElementById(iframeId);
    var subWeb = document.frames ? document.frames[iframeId].window :ifm.contentWindow;
    subWeb.reloadPage();
}