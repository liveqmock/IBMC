package com.nomen.ntrain.quart.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.nomen.ntrain.ibmc.service.ManCardRecordService;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockRecordInfo;
import com.nomen.ntrain.ibmc.webservice.WebUnlockRecord;
import com.nomen.ntrain.util.SpringBeanUtils;

/**
 * 同步刷卡历史
 * @author 连金亮
 * @date   2015-02-02
 */
public class SynRecordQuartzJob {
	
	private static final Log    LOG = LogFactory.getLog(SynRecordQuartzJob.class);
	private ManCardRecordService	manCardRecordService;
	public SynRecordQuartzJob(){
		this.manCardRecordService = (ManCardRecordService)SpringBeanUtils.getBean("manCardRecordService");
	}
	
	/**
	 * 同步刷卡记录
	 *   1、按时间范围获取[每过5分钟执行一次]
	 */
	@SuppressWarnings("unchecked")
	public void synUnlockRecord(){
		//当前时间前10分钟
		//String sDateStr = this.tenMinAgo();
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//String eDateStr = (df.format(new Date()));
		int maxUnLockId = this.manCardRecordService.findManCardRecordMaxSynId(null);
		WebUnlockRecord webService = new WebUnlockRecord();
		UnlockRecordInfo[] unLockInfoArr =  webService.unlockSearch2(null,null,maxUnLockId);
		if(null!= unLockInfoArr){
			this.manCardRecordService.saveSynUnlockData(unLockInfoArr);
		}
	}
}
