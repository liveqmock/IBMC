package com.nomen.ntrain.sgsdx.tool;

/**
 *	@description  短信内容表更新和日志记录接口
 */
public interface SmsSendLogDAO {
	/**
	 * 更新发送信息及保存日志
	 * @param	smsItem 当前短信
	 * 			nextSmsItem 下一条短信
	 */
	public void saveSmsItemListAndLog(SmsItem smsItem,int nextSmsItem);//,String oldId,String sendSign
}
