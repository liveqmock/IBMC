package com.nomen.ntrain.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.nomen.ntrain.core.bean.NetServerBean;
import com.nomen.ntrain.core.service.NetServerService;
import com.nomen.ntrain.webservice.thread.ReloadTaskThread;
/***
 * 调度任务变动拦截
 * @author 连金亮
 * @date 2013-12-16
 */
public class NetQuartAspect{
	private static final Log LOG = LogFactory.getLog(NetQuartAspect.class);
	private NetServerService netServerService;
	
	public void doAfter(JoinPoint jp) {  
    	if(jp.getSignature().getName().indexOf("find")<0 && jp.getSignature().getName().indexOf("IniLoadClass")<0){
	    	//通知所有的web服务器重新加载任务
	    	List<NetServerBean> serverList = this.netServerService.findNetServerList(null);
	    	for(NetServerBean s : serverList){
		    	String server = "http://"+s.getIp()+":"+s.getPort()+"/"+s.getSname();
		    	//LOG.info("--------------------通知服务器["+server+"]开始-----------------");
	    		//逐个通知web服务器重新加载任务
	    		(new ReloadTaskThread(server)).start();
		    	//LOG.info("--------------------通知服务器["+server+"]结束-----------------");
	    	}
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

    
    //以下为set get方法

	public NetServerService getNetServerService() {
		return netServerService;
	}

	public void setNetServerService(NetServerService netServerService) {
		this.netServerService = netServerService;
	}
}
