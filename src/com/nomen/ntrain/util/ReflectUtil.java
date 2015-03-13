package com.nomen.ntrain.util;

/**
 * 反射类
 * @author 连金亮
 * @date 2011-05-20
 */
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReflectUtil 
{
	private static final Log logger = LogFactory.getLog(ReflectUtil.class);
	
	/**
	 * 获取所有方法名
	 * @param classname 类名
	 * @return
	 */
	public static List<String> getMethods(String classname) {
		List<String> list = new ArrayList<String>();
		try {
			Class clazz = Class.forName(classname);
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				list.add(method.getName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取所有方法名[不包含Object继承来的方法]
	 * @param classname 类名
	 * @return
	 */
	public static List<String> getMethodsWithoutObject(String classname) {
		List<String> list = getMethods(classname);
		Method[] methodArr = Object.class.getMethods();
		for (Method method : methodArr) {
			list.remove(method.getName());
		}
		return list;
	}
	
	/**
	 * 执行反射方法
	 * @param classname 类名
	 * @param methodname 方法名
	 * @throws Exception
	 */
	public static void execDeclaredMethod(String classname,String methodname) throws Exception{
		Class clazz = Class.forName(classname);
		// 获取的是类自身声明的所有方法，包含public、protected和private方法
		Method method = clazz.getDeclaredMethod(methodname);
		// 设置权限是可访问
		method.setAccessible(true);
		method.invoke(clazz.newInstance());	
	}
	
	/**
	 * 字符串转对象类型
	 * @param classname 类路径[例如 com.nsoft.Person]
	 * @return 转换后的对象
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static Object stringToObject(String classname) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		return Class.forName(classname).newInstance();
	}
	
	@SuppressWarnings("unchecked")
	public static void setFieldValue(Object target, String fname, Class ftype,Object fvalue) {
		if (target == null || fname == null || "".equals(fname) 
				|| (fvalue != null && !ftype.isAssignableFrom(fvalue.getClass()))) {
			return;
		}
		Class clazz = target.getClass();
		try {
			Method method = clazz.getDeclaredMethod("set"
					+ Character.toUpperCase(fname.charAt(0))
					+ fname.substring(1), ftype);
			if (!Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
			}
			method.invoke(target, fvalue);
        } catch (Exception ex) {
        	if (logger.isDebugEnabled()) {
        		logger.debug(ex);
        	}
        	try {
        		Field field = clazz.getDeclaredField(fname);
        		if (!Modifier.isPublic(field.getModifiers())) {
        			field.setAccessible(true);
        		}
        		field.set(target, fvalue);
        	} catch (Exception e) {
        		if (logger.isDebugEnabled()) {
        			logger.debug(e);
        		}
        	}
        }
    }
	
	public static Object getFieldValue(Object target, String fname)
	{
		Object reslut = null;
		if (target == null || fname == null || "".equals(fname)) {
			return null;
		}
		Class clazz = target.getClass();
		try {
			Field field = clazz.getDeclaredField(fname);
			reslut =  field.get(fname);
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger.debug(ex);
			}
		}
		return reslut;
	}
}
