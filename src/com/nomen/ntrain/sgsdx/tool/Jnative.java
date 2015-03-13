package com.nomen.ntrain.sgsdx.tool;

import java.util.ArrayList;
import java.util.List;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

public class Jnative {

	private final static String PRIONT_DLL = "SMSDLL.dll";
	
	public JNative getDllFuncByName(String funcName){
		try{
			return new JNative(PRIONT_DLL,funcName);
		}
		catch(Exception ex){
			return null;
		}
	}
	
	
	public void send(){
		Jnative jTest = new Jnative();
		JNative n = null;
		JNative n2 = null;
		String res = "";
		try {
			System.loadLibrary("SMSDLL");
			//给出库名方法名
			n = jTest.getDllFuncByName("SMSStartService");	
			n.setRetVal(Type.INT); 		  // 设定返回类型
			int i = 0;
			n.setParameter(i++, 1);		 // 传入方法参数
			n.setParameter(i++, 115200);
			n.setParameter(i++, 2);
			n.setParameter(i++, 8);
			n.setParameter(i++, 0);
			n.setParameter(i++, 0); 
			n.setParameter(i++, "card");
			n.invoke(); 			    // 执行程序
			
			res = n.getRetVal(); // 得到返回结果
			System.out.println("短信猫服务启动："+res);
			System.out.println("准备发送短信............"); 
			n = jTest.getDllFuncByName("SMSSendMessage");
			n.setRetVal(Type.INT); // 设定返回类型
			n.setParameter(0, "你是sb呀");
			n.setParameter(1, "15860292278");
			n.invoke(); 			    // 执行程序
			res = n.getRetVal();
			System.out.println("发送短信已完成，并返回短信序列号："); 
			
			
			
			int rtn = -1;
			//定时轮询发送状态
			n2 = jTest.getDllFuncByName("SMSQuery");
			try {
				n2.setRetVal(Type.INT);
				n2.setParameter(0, Integer.parseInt(res));
			} catch (NativeException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}   
			while(rtn <0){
				try {
					n2.invoke(); 			    // 执行程序
					rtn = Integer.parseInt(n2.getRetVal());
					System.out.println(res+"+++++"+rtn+"::::");
					Thread.sleep(500); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}catch (Exception e1) {
			e1.printStackTrace();
		}finally {
			if (n != null) {
				try {// 释放资源
					n.dispose();
				} catch (NativeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
	}
 
	/**
	 * @param args
	 */
	public static void main(String[] args) throws NoSuchMethodException{

		Jnative jTest = new Jnative();
		JNative n = null;
		JNative n2 = null;
		String res = "";
		try { 
			System.loadLibrary("SMSDLL");
			//给出库名方法名
			n = jTest.getDllFuncByName("SMSStartService");	
			n.setRetVal(Type.INT); 		  // 设定返回类型
			int i = 0;
			n.setParameter(i++, 1);		 // 传入方法参数
			n.setParameter(i++, 115200);
			n.setParameter(i++, 2);
			n.setParameter(i++, 8);
			n.setParameter(i++, 0);
			n.setParameter(i++, 0); 
			n.setParameter(i++, "card");
			n.invoke(); 			    // 执行程序
			
			res = n.getRetVal(); // 得到返回结果
			System.out.println("短信猫服务启动："+res);
			
			
			Pointer pTar = new Pointer(MemoryBlockFactory.createMemoryBlock(320));
			/*返回格式为：
			*
			**短信内容 256
			**对方手机号码 32
			**接收时间    32
			**/
			n.setParameter(0, pTar);
			
			//定时轮询发送状态
			n = jTest.getDllFuncByName("SMSGetNextMessage");
			try {
				n.setRetVal(Type.INT);
				n.setParameter(0, pTar);
			} catch (NativeException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}   
			
			while (true) {
				n.invoke();
				String revStatus = n.getRetVal();
				if (Integer.parseInt(revStatus) == 1) {
					String content = pTar.getAsString();
					System.out.println(content);
					
					if(content.indexOf("李刚")>=0){
						//SmsEngineSender sender = new SmsEngineSender();
						//单条发送
						//sender.sendSms("15280038494","他是sb");
					}
					
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
					String st = new String(dest); 
					System.out.println(st);

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
					String st1 = new String(dest1);
					System.out.println(st1);

				}
			}
			
			
		}catch (Exception e1) {
			e1.printStackTrace();
		}finally {
			if (n != null) {
				try {// 释放资源
					n.dispose();
				} catch (NativeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
	}
}
