package com.nomen.ntrain.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.nomen.ntrain.base.service.BaseMenuService;
import com.nomen.ntrain.base.service.BaseRoleService;
/***
 * 菜单、角色变动拦截
 * @author 连金亮
 * @date 2013-12-16
 */
public class BaseMenuAndRoleAspect{
	private static final Log LOG = LogFactory.getLog(BaseMenuAndRoleAspect.class);
	
	public void doAfter(JoinPoint jp) {  
    	if(jp.getSignature().getName().indexOf("find")<0){
    		//通知该服务器重新加载上下文的菜单和角色
    		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
            ServletContext application = webApplicationContext.getServletContext(); 
            BaseMenuService netMenuService = (BaseMenuService)SpringBeanUtils.getBean("baseMenuService");
            Map mM = new HashMap();
            mM.put("fatherid", Constant.MENU_FATHERID_LEV1);
            mM.put("purfilter", "1");
            mM.put("usesign", "1");
            application.setAttribute(Constant.MENU_LIST_NAME, netMenuService.findBaseMenuListByThread());
            mM = null;
            BaseRoleService netRoleService = (BaseRoleService)SpringBeanUtils.getBean("baseRoleService");
            application.setAttribute(Constant.ROLE_LIST_NAME, netRoleService.findBaseRoleList(null));
    	    LOG.info("服务器--刷新菜单和角色...");
    	}
    }  
  
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
        long time = System.currentTimeMillis();  
        Object retVal = pjp.proceed();  
        time = System.currentTimeMillis() - time;  
        System.out.println("process time: " + time + " ms");  
        return retVal;  
    }  
  
    public void doBefore(JoinPoint jp) {  
        System.out.println("log Begining method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());  
    }  
  
    public void doThrowing(JoinPoint jp, Throwable ex) { 
        //System.out.println("异常数据：method " + jp.getTarget().getClass().getName() + "=====" + jp.getSignature().getName() + " throw exception");  
        //System.out.println(ex.getMessage());  
    }
}
