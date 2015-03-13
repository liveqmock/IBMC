package com.nomen.ntrain.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import com.nomen.ntrain.base.bean.BaseExceptionBean;
import com.nomen.ntrain.base.service.BaseExceptionService;
/***
 * 错误日志拦截
 * @author 连金亮
 * @date 2013-11-22
 */
public class ExceptionAspect{
	private static final Log LOG = LogFactory.getLog(ExceptionAspect.class);
	private BaseExceptionService baseExceptionService;
	private BaseExceptionBean    baseExceptionBean;
	
	public void doAfter(JoinPoint jp) {  
        System.out.println("log Ending method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());  
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
    	if(jp.getTarget().getClass().getName().indexOf("BaseException")<0){
	    	LOG.info("--------------------错误日志开始记录-----------------");
	    	baseExceptionBean = new BaseExceptionBean();
	    	baseExceptionBean.setErrorclass(jp.getTarget().getClass().getName());
	    	baseExceptionBean.setErrormethod(jp.getSignature().getName());
	    	baseExceptionBean.setErrormsg(ex.getMessage());
	    	this.baseExceptionService.insertBaseExceptionBean(baseExceptionBean);
	    	LOG.info("--------------------错误日志结束记录-----------------");
    	}
    }

    
    //以下为set get方法
	public BaseExceptionService getBaseExceptionService() {
		return baseExceptionService;
	}

	public void setBaseExceptionService(BaseExceptionService baseExceptionService) {
		this.baseExceptionService = baseExceptionService;
	}  
    
}
