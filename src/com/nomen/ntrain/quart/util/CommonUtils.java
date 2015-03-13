package com.nomen.ntrain.quart.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 调度器常用操作工具类
 * @author 梁桂钊
 * @version 2013-10-17
 */
public class CommonUtils {
	
	/**
	 * 集合转字符串
	 * @param list		需集合的集合
	 * @param separator	字符串分隔符[例如："," "@"]
	 * @return 转换后的字符串
	 */
	public static String listToString(List<?> list,String separator){
		if(null == list || list.size() ==0) return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0,lSize=list.size(); i <lSize -1; i++) {
			if(null!=list.get(i)){
				sb.append(list.get(i)).append(separator);
			}else{
				sb.append("").append(separator);
			}
		}
		sb.append(list.get(list.size()-1));
		return sb.toString();
	}
	
	/**
	 * 集合转字符串[若有空值转换成带有一个空格" "的字符串]
	 * @param list		需集合的集合
	 * @param separator	字符串分隔符[例如："," "@"]
	 * @return 转换后的字符串
	 */
	public static String listToStringNull(List<?> list,String separator){
		if(null == list || list.size() ==0) return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0,lSize=list.size(); i < lSize; i++) {
			if(null!=list.get(i) && !("".equals(list.get(i)))){
				sb.append(list.get(i)).append(separator);
			}else{
				sb.append(" ").append(separator);
			}
		}
		return StringUtils.chop(sb.toString());
	}
	
	/**
	 * 获取指定目录下所有文件名[去掉.class]
	 * [没有递归，只能获取一级文件名]
	 * @param filename 文件名
	 * @return
	 */
	public static List<String> getFileNames(String filename){
		File file = new File(filename);
		File[] files = file.listFiles();
		List<String> list = new ArrayList<String>();
		for (File file2 : files) {
			String name = file2.getName();
			list.add(name.substring(0, name.length()-6));
		}
		return list;
	}

}
