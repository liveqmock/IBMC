String.prototype.replaceAll = function(AFindText,ARepText) {
    return this.replace(new RegExp(AFindText,"gm"),ARepText);   
}

/*
 *  （1）提示操作。
 *  函数名称：alertInfo(sign)
 *  输入参数：sign，整型，信息标志。
 *  输出参数：
 *  应用实例：
 */
function alertInfo(sign) {
    switch(sign) {
        case 11: alert('操作成功！'); break;
        case 12: alert('操作失败！'); break;
        case 21: alert('请选择要操作的记录！'); break;
        case 51: alert("仅一条记录不能移除！"); break;
        case 52: alert("首项记录，不能上移！"); break;
        case 53: alert("末项记录，不能下移！"); break;
        case 61: alert('文件格式有误！'); break;
        case 62: alert('文件大小超出！'); break;
        case 63: alert('文件域限制10项及以内！'); break;
        case 91: alert('使用周期与审核通过记录的使用周期存在冲突！'); break;
    }
}
/*
 *  （2）确认操作。
 *  函数名称：confInfo(sign)
 *  输入参数：sign，整型，信息标志。
 *  输出参数：布尔型。
 *  应用实例：
 */
function confInfo(sign) {
    var str="";
    switch(sign) {
        /*维护类*/
        case 11: str = "确定导入记录？"; break;
        case 12: str = ""; break;
        case 13: str = "确定引入记录？"; break;
        case 14: str = ""; break;
        case 15: str = "确定更新记录？"; break;
        case 16: str = ""; break;
        /*删除类*/
        case 21: str = "确定删除记录？"; break;
        case 22: str = ""; break;
        case 23: str = "确定清除？"; break;
        case 24: str = ""; break;
        case 25: str = "确定移除记录？"; break;
        case 26: str = ""; break;
        /*保存类*/
        case 31: str = "确定保存记录？"; break;
        case 32: str = ""; break;
        case 33: str = "确定归档记录？"; break;
        case 34: str = ""; break;
        case 35: str = "确定提交记录？"; break;
        case 37: str = "确定确认记录？"; break;
        case 38: str = ""; break;
        case 39: str = "确定分配记录？"; break;
        case 40: str = "确定取消分配记录"; break;
        /*发布类*/
        case 41: str = "确定发布记录？"; break;
        case 42: str = "确定取消发布记录"; break;
        case 43: str = "确定公示记录？"; break;
        case 44: str = "确定报名？"; break;
        /*流程类*/
        case 51: str = "确定报审记录？"; break;
        case 52: str = "确定取消报审记录？"; break;
        case 53: str = "确定回退记录？"; break;
        case 54: str = ""; break;
        /*常用类*/
        case 71: str = "确定启用记录？"; break;
        case 72: str = "确定禁止记录？"; break;
        case 73: str = "确定取消？"; break;
        case 74: str = ""; break;
        case 75: str = "确定撤销？"; break;
        case 76: str = ""; break;
        /*其他类*/
        case 79: str = "确定自动排序？"; break;
        case 80: str = ""; break;
        case 81: str = "确定自动编码？"; break;
        case 82: str = ""; break;
        case 83: str = "确定复制？"; break;
        case 84: str = ""; break;
        case 85: str = "确定计算？"; break;
        case 86: str = ""; break;
        case 87: str = "确定更新？"; break;
        case 88: str = ""; break;
        case 91: str = "确定设定书签？"; break;
        case 92: str = ""; break;
        case 93: str = "确定提交试卷？"; break;
        case 94: str = ""; break;
        /*技能鉴定类*/
        case 101: str = "确定审核（同意）记录？"; break;
		case 102: str = "确定审核（不同意）记录？"; break;
        case 103: str = "确定审批（同意）记录？"; break;
		case 104: str = "确定审批（不同意）记录？"; break;
        case 105: str = "确定所有人员报名资料都已更新，并完成调整？"; break;
        case 106: str = "确认按照当前的鉴定任务分配下发给各鉴定站？"; break;
        case 109: str = "确认载入去年的鉴定任务设置？本操作将清除当前已设置的内容！"; break;
        /*考勤管理类*/
        case 111: str = "确定数据写入终端？"; break;
        case 113: str = "确定上行数据？"; break;
        case 115: str = "确定下行数据？"; break;
        /*劳保用品类*/
        case 121: str = "确定全部领用？"; break;
        /*办班类*/
        case 161: str = "确定办班？"; break;
        case 163: str = "点菜人数少于10人！确定办班？"; break;
        case 165: str = "确定再办班？"; break;
        /*其他类*/
        case 933: str = "确定同意并归档记录？"; break;
        case 934: str = ""; break;
        case 951: str = "确定申报记录？"; break;
        case 952: str = "确定撤回申报记录？"; break;
        case 953: str = "确定退回记录？"; break;
        case 54: str = ""; break;
    }
    return sign == 0?true:confirm(str);
}
/*
 *  （3）复合操作。
 *  函数名称：collInfo(sign,msgsign,flag,url)
 *  输入参数：sign，整型，确认信息标志；
              msgsign，整型，操作完继续提示信息标志；
              flag，整型，“0”表示操作完毕后停留在页面，“1”表示操作完毕后跳转页面，“2”表示操作完毕后关闭页面；
              url，字符串型，跳转的目标页面。
 *  输出参数：
 *  应用实例：
 */
function collInfo(sign,msgsign,flag,url) {
    if(confInfo(sign)) {
        switch(msgsign) {
            case 11: alertInfo(11); break;
        }
        if(flag == 1) {
            location.href = url;
        } else if(flag == 2) {
            window.close();
        }
    }
}
/*******************************************************************************************************************************
3、嵌入页面，弹出窗口、层类。
*******************************************************************************************************************************/

/*
 * （5）弹出“数据操作中...”层。
 *  函数名称：openProcessDiv()
 *  输入参数：
 *  输出参数：
 *  应用实例：
 */ 
function openProcessDiv() { 
    var iframeObj = document.getElementById("iframeing");  
    if(!iframeObj ) {  
        var proceObj = document.getElementById("processing");
        if(proceObj) {
            proceObj.style.display = "";
            var iframeObj2 = document.createElement("iframe"); 
            iframeObj2.id = "iframeing";
            var containerObj = document.getElementById("container");
            containerObj.insertBefore(iframeObj2,proceObj); 
        }
    }
}
/*******************************************************************************************************************************
4、显示/隐藏类。
*******************************************************************************************************************************/

/*
 * （3）点击复选框时显示/隐藏对象。
 *  函数名称：disChk1(sobjname,tobjname,sign)
 *  输入参数：sobjname，字符串型，源对象名称（复选框）；tobjnames，字符串型，目标对象名称串（显示/隐藏），多个时用“,”隔开；
              sign，整型，“0”表示表示复选框勾选时，对象显示，“1”表示表示复选框勾选时，对象不显示。
 *  输出参数：
 *  应用实例：
 */ 
function disChk1(sobjname,tobjnames,sign) {
    var obj1 = document.getElementsByName(sobjname)[0];
    var obj2 = tobjnames.split(",");
    switch(sign){
        case 0:{
            for(i=0;i<obj2.length;i++){
                document.getElementsByName(obj2[i])[0].style.display=obj1.checked?"":"none";
            } 
            break;
        }
        case 1:{
            for(i=0;i<obj2.length;i++){
                document.getElementsByName(obj2[i])[0].style.display=obj1.checked?"none":"";
            } 
            break;
        } 
    } 
}
/*
 * （4）点击单选框时显示/隐藏对象。
 *  函数名称：disRadio1(sobjname,sign,str)
 *  输入参数：sobjname：字符串型，源对象名称（单选框）；
              sign：整型，“0”表示分隔符前面对象显示，后面对象隐藏，“1”表示分隔符前面对象隐藏，后面对象显示；
              str：字符串型，格式：“单选框值：对象名称-对象名称”。（多个值之间用“；”隔开，多个对象之间用“，”隔开）。
 *  输出参数：
 *  应用实例：
 */ 
function disRadio1(sobjname,sign,str) {
    var radio = document.getElementsByName(sobjname);
    var arrstr = str.split(";");
    for(var i = 0;i<radio.length;i++) {
        if(radio[i].checked){
            for(var j = 0; j < arrstr.length; j++) {
                var val = arrstr[j].split(":")[0];
                if(val == radio[i].value) {
                    var arrobj = arrstr[j].split(":")[1].split("-");
                    if(arrobj[0] != "") {
                        var arrobj1 = arrobj[0].split(",");
                        for(var k = 0;k<arrobj1.length;k++) {
                            if(arrobj1[k] != "") {
                                var obj = document.getElementsByName(arrobj1[k])[0];
                                if(obj == undefined) {
                                    obj = document.getElementById(arrobj1[k]);
                                }
                                if(parseInt(sign) == 1) {
                                    obj.style.display = "none";
                                } else {
                                    obj.style.display = "";
                                }
                            }
                        }
                    }
                    if(arrobj[1]!="") {
                        var arrobj2 = arrobj[1].split(",");
                        for(var k = 0;k<arrobj2.length;k++) {
                            if(arrobj2[k] != "") {
                                var obj = document.getElementsByName(arrobj2[k])[0];
                                if(obj==undefined){
                                    obj = document.getElementById(arrobj2[k]);
                                }
                                if(parseInt(sign) == 1) {
                                    obj.style.display = "";
                                } else {
                                    obj.style.display = "none";
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
/*
 * （5）选择框改变时显示/隐藏对象。
 *  函数名称：disSelect1(sobjname,sign,tobjnames,str)
 *  输入参数：sobjname：字符串型，源对象名称（选择框）；
              sign：整型，“0”表示隐藏，“1”表示显示；
              tobjnames：字符串型，目标对象名称串（默认），用“,”分隔；
              str：字符串型，格式：“选择框值：对象名称”（多个值之间用“；”隔开，多个对象名称之间用“，”隔开）；
 *  输出参数：
 *  备    注：
 *  应用实例：
 */ 
function disSelect1(sobjname,sign,tobjnames,str) { 
    var obj = document.getElementsByName(sobjname)[0];
    var arrtobjnames = tobjnames.split(",");
    var arrstr = str.split(";");
    //初始化对象（根据sign显示、隐藏tobjnames）
    for(var i = 0;i<arrtobjnames.length;i++) {
        if(sign == 1){
            document.getElementById(arrtobjnames[i]).style.display = "";
        }else{
            document.getElementById(arrtobjnames[i]).style.display = "none";
        }
    }
	//初始化 
    var sid = tobjnames;
    for(var i = 0;i<arrstr.length;i++) {
        var arrobj = arrstr[i].split(":");
        if(arrobj.length == 2){
            if(arrobj[1] != ""){
                //表示有对象组
                sid = arrobj[1];
            }
        }
        arrid = sid.split(",");
        //显示或隐藏相应对象（触发的对象在显示对象中）
        if(obj.value == arrobj[0]){
            for(var j = 0;j<arrid.length;j++) {
                if(sign == 1) {
                    document.getElementById(arrid[j]).style.display = "none";
                }else{
                    document.getElementById(arrid[j]).style.display = "";
                }
            }
        }
    }
}
/*
 * （6）选择框初始化。
 *  函数名称：disSelect2(tobjnames)
 *  输入参数：tobjnames：字符串型，目标对象名称（选择框），用“,”分隔。
 *  输出参数：
 *  备    注：
 *  应用实例：
 */ 
function disSelect2(tobjnames){
    var arrtobjnames = tobjnames.split(",");
    for(var i = 0;i<arrtobjnames.length;i++) {
        document.getElementsByName(arrtobjnames[i])[0].selectedIndex=0;
    }
}

/*******************************************************************************************************************************
6、文件域类。
*******************************************************************************************************************************/
/*
 * （1）文件域文件格式判断。
 *  函数名称：checkFileFat(fat)
 *  输入参数：fat，字符串型，文件后缀名，多个时用“,”隔开。
 *  输出参数：布尔型。
 *  应用实例：
 */ 
function checkFileFat(fat) {
    var obj=event.srcElement;
    if(obj.value.indexOf(".")<0) {
        clearFile(obj);
        return false;
    }
    var str = obj.value.substring(obj.value.lastIndexOf(".")+1);
    if((","+fat.toUpperCase()+",").indexOf(","+str.toUpperCase()+",") < 0) {
        clearFile(obj);
        return false;
    }
    return true;
}
/*
 * （2）文件域文件大小判断。
 *  函数名称：checkFileSize(filepath,maxsize)
 *  输入参数：filepath，字符串型，文件路径；maxsize，整型，文件限定大小。
 *  输出参数：布尔型。
 *  应用实例：
 */ 
function checkFileSize(filepath,maxsize) {
    try {
        var img = new Image();
        img.dynsrc = filepath;
        var filesize = (img.fileSize/(1024*1024)).toFixed(2);
        if(filesize <= parseFloat(maxsize)) {
            return true;
        } else {
            return false;
        }
    } catch(err) {
        return true;
    }
}
/*
 * （3）文件域文件格式和大小判断。
 *  函数名称：checkFile()
 *  输入参数：
 *  输出参数：
 *  应用实例：
 */ 
function checkFile() {
    var obj = event.srcElement;
    var filefat = document.getElementsByName("filefat")[0].value;
    var filesize = document.getElementsByName("filesize")[0].value;
    var bool = true;
    //判断文件后缀名
    if(filefat != "") {
        if(!checkFileFat(filefat)) {
            alertInfo(61);
            bool = false;
        }
    }
    //判断文件大小
//    if(filesize != "" && bool){
//        if(!checkFileSize(obj.value,filesize)) {
//            alertInfo(62);
//            clearFile(obj);
//        }
//    }
}
/*
 * （12）列表框赋值给对象。
 *  函数名称：setSelectValue3(sobj,tobj,sign)
 *  输入参数：sobj：对象型，源对象；tobj：对象型，目标对象；
              sign：数值型，“11”表示选择，“19”表示全选；“91”表示移除，“99”表示全移。
 *  输出参数：
 *  应用实例：
 */ 
function setSelectValue3(sign,sobj,tobj) {
    sobj = eval("document.form1.preview") || sobj;
    tobj = eval("document.form1.selview") || tobj;
    var j = 0,obj;
    switch(sign) {
        case 11:
            if(sobj.selectedIndex == -1) {
                alertInfo(21);
            } else {
                sendSelectValue(sobj,tobj);
            }
            break;
        case 19:
            for(j = 0;j<sobj.options.length;j++){
                obj=sobj.options[j];
                obj.selected=true;
            }
            sendSelectValue(sobj,tobj);
            break;
        case 91:
            if(tobj.selectedIndex == -1) {
                alertInfo(21);
            } else {
            sendSelectValue(tobj,sobj);
            }
            break;
        case 99:
            for(j = 0;j<tobj.options.length;j++){
                obj=tobj.options[j];
                obj.selected=true;
            }
            sendSelectValue(tobj,sobj);
            break;
    }
}
function sendSelectValue(sobj,tobj){
    try {
        for(i=0;i<sobj.options.length;i++){
            var obj = sobj.options[i];
            if(obj.selected){
                tobj.options.add(new Option(obj.text,obj.value));
                sobj.options.remove(i);
                i--;
            }
        }
    }
    catch(err) {}
}