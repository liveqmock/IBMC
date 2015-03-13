package com.nomen.ntrain.quart.task;

import java.util.List;
import org.apache.commons.beanutils.MethodUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.nomen.ntrain.util.ReflectUtil;

/**
 * @description 调度器任务
 * @author 梁桂钊
 * @date 2013-09-16
 */
public class QuartzJobBean implements Job {
	private final static String splitStr = "!";
	private final static String sufferClass = "com.nomen.ntrain.quart.task";
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	boolean flag = false;
    	// jobid
        String targetBeanId = (String) context.getMergedJobDataMap().get("targetObjectId");
        // job对应的方法名称
        String targetObjectMethod = (String)context.getMergedJobDataMap().get("targetObjectMethod");
        // job对应的方法的参数以及对应值1111!333!444 
        String targetObjectMethodParam = (String)context.getMergedJobDataMap().get("targetObjectMethodParam");
        // 判断是否为空
        if("".equals(targetBeanId)&&targetBeanId==null){
        	flag=true;
        }else{
        	flag=false;
        }
        if (flag) return;
        
        try {
        	String className = sufferClass+"."+targetBeanId;
			List<String> methods = ReflectUtil.getMethods(className);
			for(String m : methods){
				if(m.equals(targetObjectMethod)){
					if(targetObjectMethodParam == null || targetObjectMethodParam.length()==0){
						ReflectUtil.execDeclaredMethod(className, targetObjectMethod);
					}
					else{
						//带参数的方法
						Object classObj = ReflectUtil.stringToObject(className);
						Object[] param = targetObjectMethodParam.split(splitStr);
						MethodUtils.invokeMethod(classObj, targetObjectMethod, param);
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
}