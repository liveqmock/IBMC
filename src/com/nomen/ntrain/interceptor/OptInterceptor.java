package com.nomen.ntrain.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.nomen.ntrain.annotation.OptResource;
import com.nomen.ntrain.base.bean.BaseOptLogBean;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.service.BaseOptLogService;
import com.nomen.ntrain.util.Constant;

@Aspect //该注解标示该类为切面类 
@Component //注入依赖
public class OptInterceptor {
	private static final Log LOG = LogFactory.getLog(OptInterceptor.class);
	private BaseOptLogService  baseOptLogService;
      
	@Pointcut("@annotation(com.nomen.ntrain.annotation.OptResource)")
    public void detectPointcut() {
    }
	
	/**
	 * 记录操作日志
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@AfterReturning(pointcut="detectPointcut()",returning="retVal") 
    public void detectOption(JoinPoint joinPoint,Object retVal)  
            throws Throwable {  
    	//获取目标类名 
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
    	//获取目标方法体参数
        Object[] parames = joinPoint.getArgs();  
    	//解析目标方法体的参数  
        Class targetClass = Class.forName(targetName);  
        Method[] method = targetClass.getMethods();
        for (Method m : method) {  
            if (m.getName().equals(methodName)) {  
                Class[] tmpCs = m.getParameterTypes();  
                if (tmpCs.length == parames.length) { 
                	//获得Session  
                	HttpServletRequest req = ServletActionContext.getRequest();
                	if( null != req){
	                    HttpSession session = req.getSession();  
	                    // 取到当前的操作用户  
	                    LoginBean userSession = (LoginBean)session.getAttribute(Constant.LOGIN_PARAM);
	                    //System.out.println(userSession.getUserid());
	                	//记录操作日志
	                    this.insertOptLog(userSession,m,parames);
	                    break;
                	}
                }  
            }  
        }  
    }  
    
    /** 
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 
     * 将参数值拼接为操作内容 
     */  
    public void insertOptLog(LoginBean session,Method m,Object[] args) throws Exception{  
    	OptResource methodCache = m.getAnnotation(OptResource.class); 
        StringBuffer rs = new StringBuffer();  
        rs.append(m.getName());  
        String className = null;  
        int index = 1;  
        // 遍历参数对象  
        for (Object info : args) {  
            //获取对象类型  
            className = info.getClass().getName();  
            className = className.substring(className.lastIndexOf(".") + 1);
            rs.append("[参数" + index + "，类型：" + className + "，值：");
            // 获取对象的所有方法  
            Method[] methods = info.getClass().getDeclaredMethods();  
            // 遍历方法，判断get方法  
            for (Method method : methods) {  
                String methodName = method.getName();  
                // 判断是不是get方法  
                //if (methodName.indexOf("get") == -1) {// 不是get方法  
                //    continue;// 不处理  
               // }  
                Object rsValue = null;  
                try {  
                    // 调用get方法，获取返回值  
                    rsValue = method.invoke(info); 
                    if (rsValue == null) {//没有返回值  
                        continue;  
                    }  
                } catch (Exception e) {  
                    continue;  
                }  
                //将值加入内容中  
                rs.append("(" + methodName + " : " + rsValue + ")");  
            }  
            rs.append("]");  
            index++;  
        }  
        //插入操作日志
        BaseOptLogBean baseOptLogBean = new BaseOptLogBean(); 
        if(null == baseOptLogBean){return ;}
        baseOptLogBean.setOptremark(methodCache.optRemark());
        baseOptLogBean.setOpttype(methodCache.optType().getKey()+"");
        baseOptLogBean.setArgvalues(rs.toString());
        baseOptLogBean.setOptuserid(session.getId());
        baseOptLogBean.setOptusername(session.getUsername());
        baseOptLogBean.setIntflag("1");
        this.baseOptLogService.insertBaseOptLog(baseOptLogBean);
    }  
    //set和get

	public BaseOptLogService getBaseOptLogService() {
		return baseOptLogService;
	}

	public void setBaseOptLogService(BaseOptLogService baseOptLogService) {
		this.baseOptLogService = baseOptLogService;
	}
    
}