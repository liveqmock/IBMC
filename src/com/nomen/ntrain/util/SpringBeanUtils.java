package com.nomen.ntrain.util;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/*
 * Spring Bean 工具类
 * @author 连金亮
 * @date 2011-05-20
*/
public class SpringBeanUtils implements ApplicationContextAware {
	private final Logger log =Logger.getLogger(SpringBeanUtils.class);
    private static  ApplicationContext appContext;

	public void setApplicationContext(ApplicationContext applicationcontext)
			throws BeansException {
		log.info("SpringBeanUtil is loading...");
        appContext = applicationcontext;
	}

    /**
     * 获取 Bean
     * @param beanName
     *            bean 名称
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) appContext.getBean(beanName);
    }

    /**
     * 获取 Bean
     * @param clazz
     *            bean 类
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        Map<?, ?> map = appContext.getBeansOfType(clazz);
        return map.isEmpty() ? null : (T) map.values().iterator().next();
    }

}
