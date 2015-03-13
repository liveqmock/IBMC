package com.nomen.ntrain.sgsdx.tool;


/**
 * 短信实体类
 * @author 连金亮
 * @date   2012-01-31
 */
public class SmsItem
{

	private Long id;
	private String phoneNumber;
	private String content;
	private String recdate;
	private int sendCount;	//发送次数
	private int sendSign;	//发送标志
	private String sendMode;//发送模式
	private String mainuser;//维护人
	private SmsCallBack smsCallBack;

	public SmsItem()
	{
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public SmsCallBack getSmsCallBack()
	{
		return smsCallBack;
	}

	public void setSmsCallBack(SmsCallBack smsCallBack)
	{
		this.smsCallBack = smsCallBack;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getRecdate() {
		return recdate;
	}

	public void setRecdate(String recdate) {
		this.recdate = recdate;
	}

	public int getSendCount() {
		return sendCount;
	}

	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

	public int getSendSign() {
		return sendSign;
	}

	public void setSendSign(int sendSign) {
		this.sendSign = sendSign;
	}

	public String getSendMode() {
		return sendMode;
	}

	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}

	public String getMainuser() {
		return mainuser;
	}

	public void setMainuser(String mainuser) {
		this.mainuser = mainuser;
	}
	
}
