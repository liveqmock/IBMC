package com.nomen.ntrain.util;

/**
 * MD5加密类
 * @author 连金亮
 * @date 2011-05-20
 */
import java.security.MessageDigest; 
public class MD5 {
	public final static String EncryptData(String s) {   
		  char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',   
		    'a', 'b', 'c', 'd', 'e', 'f' };   
		  try {   
		   byte[] strTemp = s.getBytes();   
		   MessageDigest mdTemp = MessageDigest.getInstance("MD5");   
		   mdTemp.update(strTemp);   
		   byte[] md = mdTemp.digest();   
		   int j = md.length;   
		   char str[] = new char[j * 2];
		   int k = 0;   
		   for (int i = 0; i < j; i++) {   
		    byte byte0 = md[i];   
		    str[k++] = hexDigits[byte0 >>> 4 & 0xf];   
		    str[k++] = hexDigits[byte0 & 0xf];   
		   }   
		   return new String(str);   
		  } catch (Exception e) {   
		   return null;   
		  }   
		 }   
		  
		 public static void main(String[] args) {    
			 String t = "a05b4a372d51c1f888f6b098013d8a17";
			 System.out.print(MD5.EncryptData("a_123456")+"\n"); 
			 if(t.equals(MD5.EncryptData("lianjinliang"))){
				 System.out.println("right");
			 }
		 }   
}
