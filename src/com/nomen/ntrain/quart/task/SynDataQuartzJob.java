package com.nomen.ntrain.quart.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.nomen.ntrain.ibmc.util.SynWebServiceUtil;
import com.nomen.ntrain.util.SpringBeanUtils;

/**
 * 数据同步到各个终端
 * @author 连金亮
 * @date   2015-01-30
 */
public class SynDataQuartzJob {
	
	private static final Log    LOG = LogFactory.getLog(SynDataQuartzJob.class);
	private SynWebServiceUtil	synWebServiceUtil;
	public SynDataQuartzJob(){
		this.synWebServiceUtil = (SynWebServiceUtil)SpringBeanUtils.getBean("synWebServiceUtil");
	}
	
	/**
	 * 对特定区域的数据进行同步
	 */
	@SuppressWarnings("unchecked")
	public void synDataByArea(String commId){
		this.synWebServiceUtil.synDataToWebService(commId);
	}
}
