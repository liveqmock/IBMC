package com.nomen.ntrain.util;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/*
 * 构造关键字的变色
 * @author 连金亮
 * @date 2011-05-20
 */

public class WordChangColorInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 2365641900033439481L;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception { 
		Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
		parameters.put("searchColor", "");
		for (String key : parameters.keySet()) { 
			Object value = parameters.get(key);
			if (value instanceof String[]) {
				String[] valueArray = (String[]) value;
				/**
				for (int i = 0; i < valueArray.length; i++) {
					valueArray[i] = valueArray[i].trim(); 
				}**/
				if(key!=null && key.endsWith("keyword")){ 
					PubFunc func = new PubFunc();
					String keyValue = func.Trim(valueArray[0]);
					keyValue = func.Replace(keyValue," ","");
					parameters.put(key, keyValue);
					parameters.put("searchColor", "<span class='espec_c1'>"+keyValue+"</span>");
				}
				else{
					parameters.put(key, valueArray);
				}
			}
		}
		return invocation.invoke(); 
	}

}
