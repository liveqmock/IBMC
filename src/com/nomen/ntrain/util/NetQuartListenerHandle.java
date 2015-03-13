package com.nomen.ntrain.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.nomen.ntrain.quart.service.NetQuartService;

/** 
 * 调度器任务加载器
 * @author 梁桂钊
 * @date 2013-09-16
 */ 
public class NetQuartListenerHandle implements ServletContextListener {
	private static final Log LOG = LogFactory.getLog(NetQuartListenerHandle.class);
    private NetQuartService netQuartService;

    public void contextDestroyed(ServletContextEvent arg0) {
    	LOG.info("web加载调度器加载器，进行销毁操作！");
    }

    /**
     * 项目启动时加载所有已启动的定时任务
     * 
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	LOG.info("web加载调度器加载器，开始加载！");
        ServletContext context=servletContextEvent.getServletContext();
        ApplicationContext applicationContext=WebApplicationContextUtils.getWebApplicationContext(context);
        netQuartService=(NetQuartService) applicationContext.getBean("netQuartService");
        netQuartService.IniLoadClass();
    }
}