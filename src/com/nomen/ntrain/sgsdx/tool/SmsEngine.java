package com.nomen.ntrain.sgsdx.tool;

import java.util.*;

import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 * 短信猫引擎
 * @author 连金亮
 * @date   2012-01-31
 */
public class SmsEngine
{

	private static final Log LOG = LogFactory.getLog(SmsEngine.class);
	private boolean connectFlag;
	private SmsCallBack smsCallBack;
	private SmsItem smsItem;
	private SmsSenderThread smsSenderThread;
	private SmsReceiveThread smsReceiveThread;
	private List smsItemList;
	private boolean isStop;
	private final static String PRIONT_DLL = "SMSDLL.dll";
	private JNative n = null;
	private String res = "";
	private int comNamePort ;//端口号
	private int baudRate    ;//波特率


	public boolean getIsStop()
	{
		return isStop;
	}
	
	
	public void setIsStop(boolean isStop)
	{
		this.isStop = isStop;
	} 
	
	public JNative getDllFuncByName(String funcName){
		try{
			return new JNative(PRIONT_DLL,funcName);
		}
		catch(Exception ex){
			return null;
		}
	}
	
	/**
	 * 初始化短信猫操作
	 * @param comNamePort
	 * @param baudRate
	 * @throws Exception
	 */
	public SmsEngine() throws Exception
	{
		if(0 == this.comNamePort){
			this.comNamePort = 1;
		}
		if(0 == this.baudRate){
			this.baudRate = 115200;
		}
		this.connectSmsService(comNamePort, baudRate);
		LOG.info("正在启动短信猫服务进程...");
		if (connectFlag)
		{
			LOG.info((new StringBuilder()).append("端口连接：").append(res).toString());
			//启动短信发送线程
			smsSenderThread = new SmsSenderThread();
			smsSenderThread.setSmsEngine(this);
			smsSenderThread.start();
			
			//启动短信接收线程
//			smsReceiveThread = new SmsReceiveThread();
//			smsReceiveThread.setSmsEngine(this);
//			smsReceiveThread.start();
		}
		else{
			LOG.info((new StringBuilder()).append("端口连接：").append(res).toString());
		}
		LOG.info("启动短信猫服务进程成功!");
	}

	/**
	 * 连接短信猫操作
	 * @param comName  端口
	 * @param baudRate 波特率
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void connectSmsService(int comName, int baudRate) throws Exception{
		deconnectSmsService();
		
		LOG.info("connectSmsService...........");
		connectFlag = false;
		smsItemList = Collections.synchronizedList(new ArrayList());
		System.loadLibrary("SMSDLL");
		//给出库名方法名
		n = this.getDllFuncByName("SMSStartService");	
		n.setRetVal(Type.INT); 		  // 设定返回类型
		int i = 0;
		n.setParameter(i++, comName);		 // 传入方法参数
		n.setParameter(i++, baudRate);
		n.setParameter(i++, 2);
		n.setParameter(i++, 8);
		n.setParameter(i++, 0);
		n.setParameter(i++, 0); 
		n.setParameter(i++, "card");
		n.invoke(); 		 // 执行程序 
		res = n.getRetVal(); // 得到返回结果
		if(!res.equals("0")) connectFlag = true;
		LOG.info((new StringBuilder()).append("正在连接").append(comName).append("通讯端口...").toString());
		LOG.info((new StringBuilder()).append("波特率为").append(baudRate).toString());
	}
	
	
	
	/**
	 * 断开短信猫连接
	 *
	 */
	@SuppressWarnings("deprecation")
	public void deconnectSmsService(){
		LOG.info("deconnectSmsService...........");
		if (n != null) {
			try {// 释放资源
				n.dispose();
			} catch (NativeException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}	
		}
	}
	
	
	
	/**
	 * 发送短信
	 * @param smsItem
	 * @return
	 */
	public synchronized boolean sendSMS(SmsItem smsItem)
	{
		System.out.println("sendSMS......."); 
		this.smsItem = smsItem;
		smsCallBack = smsItem.getSmsCallBack();
		if (!connectFlag)
		{
			LOG.info("COM通讯端口未正常打开/短信猫连接不上");
			if (smsCallBack != null)
				smsCallBack.doCallBack(smsItem, 5);
			return false;
		}
		this.sendSmsMessage();
		return true;
	}
	
	/**
	 * 接收短信息
	 *
	 */
	public void receiveSmsMessage(){ 
		//接收到的短信体
		this.receiveSmsMessagePro();
	}
	
	/**
	 * 返回接收到的短信息
	 * @return
	 */
	public void receiveSmsMessagePro(){
		SmsItem smsItemPro = null;
		Pointer pTar;
		try {
			pTar = new Pointer(MemoryBlockFactory.createMemoryBlock(320));
			/*返回格式为：
			**短信内容 256
			**对方手机号码 32
			**接收时间    32
			**/
			//定时轮询发送状态
			n = this.getDllFuncByName("SMSGetNextMessage"); 
			n.setRetVal(Type.INT);
			n.setParameter(0, pTar); 
			while (true) {
				LOG.info("receiveSmsMessagePro....");
				n.invoke();
				Thread.sleep(10000);
				String revStatus = n.getRetVal();
				if (Integer.parseInt(revStatus) == 1) {
					smsItemPro = new SmsItem();
					String content = pTar.getAsString(); 
					smsItemPro.setContent(content);
					
					
					List temp = new ArrayList();
					for (int k = 256; k < 256 + 32; k++) {
						if (pTar.getAsByte(k) != 0) {
							temp.add(pTar.getAsByte(k));
						}
					}
					char[] dest = new char[temp.size()];
					for (int k = 256; k < 256 + temp.size(); k++) { 
						dest[k - 256] = (char) pTar.getAsByte(k);
					}
					String phoneNumber = new String(dest); 
					System.out.println(phoneNumber);
					smsItemPro.setPhoneNumber(phoneNumber);

					List temp1 = new ArrayList();
					for (int k = 256 + 32; k < 256 + 32 + 32; k++) {
						if (pTar.getAsByte(k) != 0) {
							temp1.add(pTar.getAsByte(k));
						}
					}
					char[] dest1 = new char[temp1.size()];

					for (int k = 256 + 32; k < 256 + 32 + 32; k++) {
						if (pTar.getAsByte(k) != 0) {
							dest1[k - 256 - 32] = (char) pTar.getAsByte(k);
						}
					}
					String recdate = new String(dest1);
					System.out.println(recdate);
					smsItemPro.setRecdate(recdate);

				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 发送短信 并对短信内容进行判断（如果长度超过70则进行多条发送操作）
	 *
	 */
	public void sendSmsMessage(){
		String content = smsItem.getContent();
		boolean sendSuccess = true;
		if(content.length()>70){
			int index=0;
			String subContent = "";
			while(index*70<content.length()){
				int startPos = index*70;
				int endPos   = (index+1)*70;
				index ++;
				if(endPos>=content.length()){
					endPos = content.length();
				}
				subContent = content.substring(startPos,endPos);
				sendSuccess = sendSuccess && this.sendSmsMessagePro(subContent);
				
				//调用回调函数
				if (smsCallBack != null)
					smsCallBack.doCallBack(smsItem, sendSuccess?1:0);
			}
		}
		else{
			sendSuccess = sendSuccess && this.sendSmsMessagePro(content);
			
			//调用回调函数
			if (smsCallBack != null)
				smsCallBack.doCallBack(smsItem, sendSuccess?1:0);
		}
	}
	
	/**
	 * 发送短信过程处理
	 * @param subContent  短信内容
	 */
	public boolean sendSmsMessagePro(String subContent){
		int sendStatus = -1;
		try {
			n = this.getDllFuncByName("SMSSendMessage");
			n.setRetVal(Type.INT);// 设定返回类型
			n.setParameter(0, subContent);
			n.setParameter(1, smsItem.getPhoneNumber());
			n.invoke(); 			    // 执行程序
			res = n.getRetVal();
			System.out.println("发送短信已完成，并返回短信编码："+res); 
			//定时轮询发送状态
			n = this.getDllFuncByName("SMSQuery");
			n.setRetVal(Type.INT);
			n.setParameter(0, Integer.parseInt(res));
			while(sendStatus <0){
				try {
					n.invoke(); 			    // 执行程序
					sendStatus = Integer.parseInt(n.getRetVal());
					System.out.println("        定时检测短信发送状态："+"短信手机号码："+smsItem.getPhoneNumber()+"::短信编码："+res+"::短信发送状态："+sendStatus);
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(sendStatus == 1){
				LOG.info("     手机号码："+smsItem.getPhoneNumber()+"  短信发送成功！");
			}
			else{
				LOG.info("     手机号码："+smsItem.getPhoneNumber()+"  短信发送失败！");
			}
		} catch (NativeException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} 
		return sendStatus==1?true:false;
	}
	
	/**
	 * 追加短信记录
	 * @param smsItem
	 */
	@SuppressWarnings("unchecked")
	public synchronized void addMessageItem(SmsItem smsItem)
	{
		if (smsItem.getPhoneNumber() == null||smsItem.getPhoneNumber().equals("") )
			return;
		if (smsItem.getContent() == null||smsItem.getContent().equals(""))
		{
			return;
		} else
		{
			smsItemList.add(smsItem);
			return;
		}
	}

	/**
	 * 获取下一条短信息
	 * @return
	 */
	public synchronized SmsItem getNextMessageItem()
	{
		SmsItem smsItem = null;
		if (smsItemList.isEmpty())
		{
			return smsItem;
		} else
		{
			//smsItem = (SmsItem)smsItemList.get(smsItemList.size() - 1);
			smsItem = (SmsItem)smsItemList.get(0);
			smsItemList.remove(smsItem);
			return smsItem;
		}
	}
	
	/**
	 * 获取当前剩余的短信记录
	 * @return
	 */
	public synchronized int getMessageItemCount(){
		return smsItemList.size();
	}
	
	
	
	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}
	public int getComNamePort() {
		return comNamePort;
	}
	public void setComNamePort(int comNamePort) {
		this.comNamePort = comNamePort;
	}
	
}
