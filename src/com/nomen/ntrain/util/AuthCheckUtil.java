package com.nomen.ntrain.util;

import java.util.List;
import javax.servlet.ServletContext;
import org.apache.struts2.StrutsStatics;
import com.nomen.ntrain.base.bean.BaseMenuBean;
import com.nomen.ntrain.base.bean.BaseRoleBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * 访问权限判断
 * @author 连金亮
 * @date   2013-11-24
 */
public class AuthCheckUtil {
	public boolean checkAccess(ActionInvocation ac,String userRoles){
		//获取url 
		String nameSpace = ac.getProxy().getNamespace();
		if((nameSpace !=null) && nameSpace.length()>0){
			if("/".equals(nameSpace.trim())){
				//说明是根路径，不需要增加反斜杠
			}
			else{
				nameSpace += "/";
			}
		}
		ActionContext actionContext = ac.getInvocationContext();
        String actionName = ac.getInvocationContext().getName(); // action 的名称，在xml中配置的  
        String url = nameSpace+actionName+".shtml";
        //根据url查询出对应的id 在查询出id所在的角色，之后与人员的角色比较
        ServletContext application = (ServletContext)actionContext.get(StrutsStatics.SERVLET_CONTEXT);
        List<BaseMenuBean> menuList = (List)application.getAttribute(Constant.MENU_LIST_NAME);
        List<BaseRoleBean> roleList = (List)application.getAttribute(Constant.ROLE_LIST_NAME);

        boolean isCanAccess = false;
        boolean isFoundMenu = false;//是否在菜单表中找到该链接，若无，则表示该功能无权限限制
        for(BaseMenuBean m : menuList){
        	if(!isCanAccess){
            	if((m.getUrl()!=null && m.getUrl().length()>0) && url.trim().indexOf(m.getUrl().trim())>=0){
            		isFoundMenu = true;
            		//表示该菜单需要权限验证
            		if(!"0".equals(m.getPur()) ){
            			//点击的是该菜单
                        for(BaseRoleBean r : roleList){
                        	if((","+r.getPurview()+",").indexOf(m.getId())>=0){
                        		if((","+userRoles+",").indexOf(r.getId())>=0){
                        			isCanAccess = true;
                        		}
                        	}
                        }
            		}
            		else if("0".equals(m.getPur())){
            			return true;
            		}
            	}
        	}
        }
        //功能缺 角色、菜单变动要求重新加载
        if(isCanAccess){
        	return true;
        }
        else{
        	if(!isFoundMenu){
        		return true;
        	}
        	else{
        		return false;
        	}
        }
	}
}
