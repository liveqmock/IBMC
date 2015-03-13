package com.nomen.ntrain.util;   

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.nomen.ntrain.base.bean.LoginBean;


/**  
* 监听HttpSession对象，统计在线人数 
*/ 
public class HttpSessionListenerHandle implements ServletContextListener, HttpSessionListener,   
        HttpSessionAttributeListener {     
    // 声明一个ServletContext对象   
    private ServletContext application = null;    
     
    public void contextInitialized(ServletContextEvent sce) {   
        // 容器初始化时，向application中存放一个空的容器   
        this.application = sce.getServletContext();   
        this.application.setAttribute("onLineNum", 0);    
    }   
  
    public void contextDestroyed(ServletContextEvent sce) {   
    }   
  
    public void sessionCreated(HttpSessionEvent se) {   
    }   
  
    public void sessionDestroyed(HttpSessionEvent se) { 
        // 将用户名称从列表中删除  
        LoginBean loginBean = (LoginBean)(se.getSession()).getAttribute("loginSession");
    	if(null != loginBean){
    		//只有当注销的session不为空
            int onLineNum = Integer.valueOf(this.application.getAttribute("onLineNum").toString());   
            onLineNum--;
            this.application.setAttribute("onLineNum", onLineNum);     
    	}
    }   
  
    public void attributeAdded(HttpSessionBindingEvent se) {   
        // 如果登陆成功，则将用户名保存在列表之中   
        if(se.getName().equals("loginSession")){ //过滤掉不是存user的属性 
        	//se.getSession()
            int onLineNum = Integer.valueOf(this.application.getAttribute("onLineNum").toString());    
            onLineNum++;
            this.application.setAttribute("onLineNum", onLineNum);  
       }  
    }   
  
    public void attributeRemoved(HttpSessionBindingEvent se) {   
    }   
  
    public void attributeReplaced(HttpSessionBindingEvent se) {   
    }   
}