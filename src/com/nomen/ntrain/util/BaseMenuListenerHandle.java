package com.nomen.ntrain.util;   

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nomen.ntrain.base.service.BaseMenuService;
import com.nomen.ntrain.base.service.BaseRoleService;


/**  
* 全文菜单
*/ 
public class BaseMenuListenerHandle implements ServletContextListener{
    // 声明一个ServletContext对象   
	private static final Log LOG = LogFactory.getLog(BaseMenuListenerHandle.class);
    private ServletContext application = null;
     
    public void contextInitialized(ServletContextEvent sce) {
    	LOG.info("NetMenuListenerHandle-contextInitialized load...");
    	this.application = sce.getServletContext();   
        ServletContext context=sce.getServletContext();
        ApplicationContext applicationContext=WebApplicationContextUtils.getWebApplicationContext(context);
        BaseMenuService baseMenuService=(BaseMenuService) applicationContext.getBean("baseMenuService");
        Map mM = new HashMap();
        mM.put("fatherid", Constant.MENU_FATHERID_LEV1);
        mM.put("purfilter", "1");
        mM.put("usesign", "1");
        this.application.setAttribute(Constant.MENU_LIST_NAME, baseMenuService.findBaseMenuListByThread());
        mM = null;
        BaseRoleService baseRoleService = (BaseRoleService)SpringBeanUtils.getBean("baseRoleService");
        this.application.setAttribute(Constant.ROLE_LIST_NAME, baseRoleService.findBaseRoleList(null));
        LOG.info("刷新菜单和角色...");
    }
  
    public void contextDestroyed(ServletContextEvent sce) {
    	LOG.info("NetMenuListenerHandle-contextDestroyed load...");
    	//this.contextThread.destroy();
        this.application = sce.getServletContext();  
        this.application.setAttribute(Constant.MENU_LIST_NAME, null);
        this.application.setAttribute(Constant.ROLE_LIST_NAME, null);
    } 
}