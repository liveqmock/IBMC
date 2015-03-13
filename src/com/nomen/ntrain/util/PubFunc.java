package com.nomen.ntrain.util;

import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Calendar;
import java.util.List;
import java.io.File; 
import java.io.Serializable;

/**
 *  常用方法类  
 * @author 连金亮
 * @date 2011-05-20
 */

public class PubFunc implements Serializable {

	private static final long serialVersionUID = 8553493813375610013L; 

	public static void main(String[] args){
		PubFunc func = new PubFunc();
		System.out.println(func.Replace("253552；256912；256680；284871", "；", ","));
	}
	
	public PubFunc() {

	}

	/**
	 * 查找字符串s1在s的pos(位置)
	 * @param i 起始查找位置
	 * @param s 字符串
	 * @param s1 被查找字符串
	 * @return 目标字符串在源字符串的位置
	 */
	public int Instr(int i, String s, String s1){
        if(i == 0)
            i = 1;
        return 1 + s.indexOf(s1, i - 1);
    }

	/**
	 * 
	 * 数组转字符串
	 * @param as 数组
	 * @param s 分隔符
	 * @return
	 */
	public String ArrayToString(String as[], String s) {
		StringBuffer stringbuffer = new StringBuffer();
		if (as != null) {
			int i = as.length;
			for (int j = 0; j < i - 1; j++) {
				stringbuffer.append(as[j]);
				stringbuffer.append(s);
			}
			stringbuffer.append(as[i - 1]);
		}
		String s1 = stringbuffer.toString();
		return s1;
	}

	/**
	 * 
	 * hashtable转数组
	 * @param hashtable  hashtable对象
	 * @return 
	 */
	public String[] HashToArray(Hashtable hashtable) throws Exception {
		if (hashtable.size() == 0)
			return null;
		String as[] = new String[hashtable.size()];
		try {
			// int i = hashtable.size();
			Enumeration enumeration = hashtable.keys();
			for (int j = 0; enumeration.hasMoreElements(); j++) {
				Object obj = enumeration.nextElement();
				as[j] = (String) hashtable.get(obj);
			}

		} catch (Exception exception) {
			as[0] = exception.getMessage();
		}
		return as;
	}

	
	//字符串转换成double类型
	public double Cdouble(String s) {
		double d = 0.0D;
		if (s != null && s.trim().length() != 0)
			d = Double.parseDouble(s);
		return d;
	}
	
	//字符串转换成int
	public int Cint(String s) {
		int i = 0;
		if (s != null && s.trim().length() != 0)
			i = Integer.parseInt(s);
		return i;
	}
 
	/*
	 * 判断是否为null 或空
	 * @return 若s为空则返回s1，否则返回s
	 */
	public String ChkNull(String s, String s1) {
		if (s == null || s.trim().length() == 0)
			return s1;
		else
			return s;
	}


	//doublue转换成String
	public String Cstr(double d) {
		return Double.toString(d);
	}

	//int转换成String
	public String Cstr(int i) {
		return Integer.toString(i);
	}

	//long转换成String
	public String Cstr(long l) {
		return Long.toString(l);
	}
	
	/*
	 * 格式化double的小数位数
	 * @param d double数据
	 * @param i 小数位数
	 */
	public String FormatDouble(double d, int i) {
		NumberFormat numberformat = NumberFormat.getNumberInstance();
		numberformat.setMaximumFractionDigits(i);
		return numberformat.format(d);
	}

	
	/*
	 * 格式化String为yyyy-MM-dd格式的日期
	 * @param s
	 * @return
	 */
	public Date CDate(String s) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		if (!IsEmpty(s))
			try {
				date = simpledateformat.parse(s);
			} catch (Exception _ex) {
			}
		return date;
	}
	
	/*
	 * 自定义格式化日期格式。
	 * @param time 待格式化的日期
	 * @param formatmodel 格式化样式
	 * @return 
	 */
	public Date DateFormat(Date time,String formatmodel) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(formatmodel);
		return CDate(simpledateformat.format(time));
	}


	/*
	 * 字符数据转换成日期数据 格式参见 java.text.SimpleDateFormat 
	 * @param strData 待转换的字符串数据 
	 * 		  strDateFormat 格式化样式
	 * @return 返回转换后的日期数据（根据日期格式）,如传入参数为空则返回null
	 */
	public static Date toDate(String strData, String strDateFormat) {
		try {
			if (strData == null) {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
			Date dReturn = sdf.parse(strData);
			return dReturn;
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * 字符数据转换成特定日期格式的字符数据(与日期相关) 格式参见 java.text.SimpleDateFormat 
	 * @param strData 待格式化的数据
	 * 		  strStringFormat 字符串原格式
	 * 		  strDateFormat 转换成的日期格式 
	 *  eg: (train.getBegindate(),"yyyy/mm/dd" , "yyyy/mm/dd HH:mm:ss")
	 * @return 返回转换后的字符数据（根据字符格式）(为空返回null)
	 * 
	 */
	public static String formatString(String strData, String strStringFormat,
			String strDateFormat) {
		try {
			String strReturn = new String();
			if (strData == null) {
				return "";
			}

			SimpleDateFormat sdf = new SimpleDateFormat(strStringFormat);
			strReturn = sdf.format(toDate(strData, strDateFormat));
			return strReturn;
		} catch (Exception e) {
			return "";
		}
	}

	/*
	 * 将日期转换成字符串，格式参见 java.text.SimpleDateFormat。 
	 * @param date 待转换的日期数据
	 * @param strDateFormat 转换成字符串数据的格式
	 * @return 返回转换后的字符串数据（为空返回null)
	 */
	public static String dateToString(Date dtValue, String strDateFormat) {
		try {
			String strReturn = "";
			if (dtValue == null) {
				return "";
			}
			SimpleDateFormat formatter = new SimpleDateFormat(strDateFormat);
			strReturn = formatter.format(dtValue);
			return strReturn;
		} catch (Exception e) {
			return "";
		}
	}
 
	/*
	 * 判断字符串是否为空或null
	 * @param s
	 * @return
	 */
	public boolean IsEmpty(String s) {
		return s == null  || s.trim().length() == 0 || "null".equals(s);
	}

	/*
	 * 判断列表是否为空
	 * @param list
	 * @return
	 */
	public boolean isEmpty(List list){
		if(null == list || list.size()==0){
			return true;
		}
		return false;
	}
	
	/*
	 * 字符串转小写
	 */
	public String Lcase(String s) {
		return s.toLowerCase();
	}
	
	/*
	 * 字符串转大写
	 */
	public String Ucase(String s) {
		return s.toUpperCase();
	}

	//取字符串左边的i个字符
	public String Left(String s, int i) {
		if (IsEmpty(s))
			return "";
		if (i > s.length())
			i = s.length();
		return s.substring(0, i);
	}

	//字符串长度
	public int Len(String s) {
		if (!IsEmpty(s))
			return s.length();
		else
			return 0;
	}
 
	//去左空格
	public String Ltrim(String s) {
		for (; s.charAt(0) == ' '; s = s.substring(1))
			;
		return s;
	}

	/*
	 * 获取字符串s中的位置从i到j的字符
	 * @param s 获取的源字符串
	 * @param i 获取开始位置
	 * @param j 获取结束位置
	 */
	public String Mid(String s, int i, int j) {
		s = s.substring(s.length() - ((s.length() - i) + 1));
		return s.substring(0, j);
	}

	/*
	 * 替换字符串
	 * @param s  替换的源字符串
	 * @param s1 替换前的字符
	 * @param s2 替换后的字符
	 */
	public String Replace(String s, String s1, String s2) {
		if (s == null)
			return null;
		StringBuffer stringbuffer = new StringBuffer(s.length());
		int i = 0;
		for (int j = 0; (j = s.indexOf(s1, i)) != -1;) {
			stringbuffer.append(s.substring(i, j)).append(s2);
			i = j + s1.length();
		}
		stringbuffer.append(s.substring(i));
		return stringbuffer.toString();
	}

	//取字符串右边的i个字符
	public String Right(String s, int i) {
		if (IsEmpty(s))
			return "";
		if (i > s.length())
			i = s.length();
		return s.substring(s.length() - i);
	}


	//去右空格
	public String Rtrim(String s) {
		for (int i = s.length(); s.charAt(i - 1) == ' '; i--)
			s = s.substring(0, i - 1);

		return s;
	}  

	/*
	 * 字符串转换成数组 eg : StrToArray("111,222,333",",") return String[]
	 * @param s  待转换的字符串
	 * @param s1 分隔符
	 */
	public String[] StrToArray(String s, String s1) {
		try {
			int i = s1.length();
			int j = s.length();
			String s2 = s + s1;
			int k = 0;
			for (int l = 0; l <= j; l++) {
				int i1 = l + i;
				if (i1 < j) {
					String s3 = s.substring(l, i1);
					if (s3.equalsIgnoreCase(s1))
						k++;
				}
			}

			String as[] = new String[++k];
			int j1 = 0;
			// int k1 = s.indexOf(s1);
			for (int l1 = 0; l1 < k; l1++) {
				int i2 = s2.indexOf(s1, j1);
				as[l1] = s2.substring(j1, i2);
				j1 = i2 + i;
			}

			return as;
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		return null;
	}

	/*
	 * 返回以','分隔的字段串 eg : 将111,222,333 转换为'111','222','333'
	 * @param s  待转换的字符串
	 * @param s1 分隔符
	 */
	public String getStrd(String s) {
		String s1 = "";
		if (s.length() > 0) {
			String[] s2 = StrToArray(s, ",");
			for (int i = 0; i < s2.length; i++) {
				if (s2[i].length() > 0) {
					s1 = s1 + "'" + s2[i] + "',";
				}
			}
		}
		if (s1.length() > 0) {
			s1 = Left(s1, s1.length() - 1);
		}
		return s1;
	}


	/*
	 * 将String 转换为hashtable
	 * @param s  待转换的字符串
	 * @param s1 分隔符
	 */
	@SuppressWarnings("unchecked")
	public Hashtable StrToHashtable(String s, String s1) {
		Hashtable hashtable = new Hashtable();
		char c = s1.charAt(0);
		String s2 = s + c;
		int i = 0;
		int j = s2.length();
		for (int k = 0; k < j; k++)
			if (s2.charAt(k) == c)
				i++;

		int l = 0;
		for (int i1 = 0; i1 < i; i1++) {
			int j1 = s2.indexOf(c, l);
			hashtable.put(new Integer(i1), s2.substring(l, j1));
			l = j1 + 1;
		}

		return hashtable;
	} 

	//获取当前时间的day
	public int getDay() {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(5);
	}

	//获取date时间中的day
	public int getDay(Date date) {
		if (date == null)
			return 0;
		else {
			Calendar calendar = null;
			calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar.get(5);
		}
	}

	//获取两个date之间的时间差
	public long getDays(Date date, Date date1) {
		System.out.println("========0=============="+date);
		System.out.println("========1=============="+date1);
		return (date1.getTime() - date.getTime());
	}

	//获取当前时间的月份
	public int getMonth() {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return 1 + calendar.get(2);
	}

	//获取date时间中的月份
	public int getMonth(Date date) {
		if (date == null)
			return 0;
		else {
			Calendar calendar = null;
			calendar = Calendar.getInstance();
			calendar.setTime(date);
			return 1 + calendar.get(2);
		}
	}

	//获取当前时间的年度
	public int getYear() {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(1);
	}

	//获取date时间中的年度
	public int getYear(Date date) {
		if (date == null)
			return 0;
		else {
			Calendar calendar = null;
			calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar.get(1);
		}
	}

	//判断s是否是数字 [不包括小数]
	public boolean isNumeric(String s) {
		int i = s.length();
		for (int j = 0; j < i; j++)
			if (!Character.isDigit(s.charAt(j)))
				return false;
		return true;
	}
	
	//判断s是否是数字 [包括小数]
	public boolean isNumberOrDecimal(String number){  
		int index = number.indexOf(".");  
		if(index<0){  
			return this.isNumeric(number);  
		}else{  
			String num1 = number.substring(0,index);  
			String num2 = number.substring(index+1);     
			return this.isNumeric(num1) && this.isNumeric(num2);  
		}  
	 }
 
	/*
	 * 删除由 realPath 指定的文件
	 */
	public boolean delFile(String sRealPath){
		sRealPath = sRealPath.replace('\\', '/');
		File f1 = new File(sRealPath);
		if (f1.exists()) {
			return f1.delete();
		}
		return true;
	}
	
	/*
	 * 格式化textarea中的字符串,输出到页面
	 */
	public String formatStr(String s) {
		if (IsEmpty(s))
			return s;
		// s = Replace(s,"<","&lt;");
		// s = Replace(s,">","&gt;");
		s = Replace(s, "\n", "<br>");
		// s = Replace(s," ","&nbsp;");
		// s = Replace(s,"&","&amp;");
		return s;
	} 

	//当字符串大于某个长度时,用".."截取
	public String formatString(String s, int iLength) {
		if (s.length() > iLength) {
			s = s.substring(0, iLength) + "..";
		}
		return s;
	}

	
    //去空
    public String Trim(String s){
        String s1 = "";
        if(!IsEmpty(s))
            s1 = s.trim();
        return s1;
    }
     
    
    //返回某一年某月的最大日期
	public final static int getMaxDay(int year,int month)
    {
		if(month==0||year==0) return 0;
		int ryear=28;
		if(year%100==0)
		{
			if(year%4==0) ryear=29;
		}
		else
			if(year%4==0) ryear=29;

		int []yearArr={31,ryear,31,30,31,30,31,31,30,31,30,31};
		return yearArr[month-1];
    } 

	//获得时间类型的年份
	public final static int getYearValue(Date date)
	{
		if(date == null)
        	return 0;
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        }
	}
	
    //获得时间类型的月份
	public final static int getMonthValue(Date date)
	{
		if(date == null)
        	return 0;
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH)+1;
        }
	}
	//获得时间类型的日期
	public final static int getDayValue(Date date)
	{
		if(date == null)
        	return 0;
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_MONTH);
        }
	}
	//获得时间类型的小时
	public final static int getHour(Date date)
	{
        if(date == null)
        	return 0;
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.HOUR_OF_DAY);
        }
    }
	
	//获得时间类型的分钟
	public final static int getMinute(Date date)
	{
        if(date == null)
        	return 0;
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MINUTE);
        }
    }
	
	//获得时间类型的秒钟
	public final static int getSecond(Date date)
	{
        if(date == null)
        	return 0;
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.SECOND);
        }
    }  
	 
	/*
	 * 计算字符在字符串中出现的次数
	 * @param str 源字符串
	 * @param charStr 要查找的字符
	 */
	public final static int getCharInStr(String str,String charStr)
	{
		int lenStr=str.length();
		int lenChar=str.replaceAll(charStr, "").length();
		return (lenStr-lenChar)/charStr.length();
	} 

	/*
	 * 根据charStr中的符号,从右边开始截取掉num个字符串
	 * @param str 源字符串
	 * @param charStr 要查找的字符
	 * @param num 截取的数量
	 */
	public final static String getStrByChar(String str,String charStr,int num)
	{
		if(str==""||str.indexOf(charStr)<0) return str;
		String testStr=str;
		for(int d=0;d<num;d++)
		{ 
			testStr=testStr.substring(0,testStr.lastIndexOf(charStr));
		}
		return testStr;
	} 
    
    //替换符号
    public final static String formatUsers(String str)
    {
    	PubFunc func=new PubFunc();
		String strAfter = "";
		strAfter = func.Replace(str,  "\r\n", "");   //取消回车
		strAfter = func.Replace(strAfter, " " , "");     //替换空格
		strAfter = func.Replace(strAfter, "，", ",");    //替换大逗号
		strAfter = func.Replace(strAfter, "，，", ",");   //替换重复的逗号
		strAfter = func.Replace(strAfter, "、", ",");     //替换重复的顿号
		strAfter = func.Replace(strAfter, "、、", ",");   //替换重复的顿号
		strAfter = func.Replace(strAfter, "；", ",");     //替换重复的分号
		strAfter = func.Replace(strAfter, ";", ",");     //替换重复的分号
		strAfter = func.Replace(strAfter, ";;", ",");    //替换重复的分号
		strAfter = func.Replace(strAfter, "；；", ",");   //替换重复的分号
		strAfter = func.Replace(strAfter, "；", ",");    //替换重复的分号
		strAfter = func.Replace(strAfter, ",,", ",");   //替换重复的分号
		strAfter = func.Replace(strAfter, "。", ",");   //替换重复的分号
		strAfter = func.Replace(strAfter, "。。", ",");   //替换重复的分号
		//将所有的“,”转化为"；"
		strAfter = func.Replace(strAfter, ",", "；");   //替换重复的分号
		return strAfter;
	}
	
	//身份证号15位转化为18位，并为大写字母
	public final static String SetCardID15To18(String CardId15)
	{
		PubFunc func=new PubFunc();
		String CardId18="";
		CardId15=func.Trim(CardId15);
		if(CardId15.length()==15)
		{
			int[] w={7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1};
			char[] A={'1','0','X','9','8','7','6','5','4','3','2'};
			String CardId17=CardId15.substring(0,6)+"19"+CardId15.substring(6,15);
			int[] arr=new int[17];
			int i=0;
			for(i=0;i<17;i++)
			{
				arr[i]=Integer.parseInt(CardId17.substring(i,i+1));
			}
			int s=0;
			for(i=0;i<17;i++)
			{
				s=s+arr[i]*w[i];
			}
			s=s%11;
			CardId18=CardId17+Character.toString(A[s]);
		}
		else CardId18=CardId15.toUpperCase();
		return CardId18;
	}
	
    //身份证号18位转化为15位
	public final static String SetCardID18To15(String CardId18)
	{
		PubFunc func=new PubFunc();
		String CardId15="";
		CardId18=func.Trim(CardId18);
		if(CardId18.length()==18)
		{
			String prefix=CardId18.substring(0,6);
			String subfix=CardId18.substring(8,CardId18.length()-1);
			CardId15=prefix+subfix;
		}
		else CardId15=CardId18;
		return CardId15;
	}
	 
	/*
	 * 分解字符串，形成SQL语句的条件（如：aa,bb分解为'aa','bb'）
	 * @param str 进行分解的字符串
	 * @param dot 字符串中的分割符
	 */
	public final static String formatSqlString(String str,String dot)
	{
		String rValue="";
		if(str==null||str==""||str.equals(dot)) return "''";
		String []arr=str.split(dot);
		for(int i=0;i<arr.length;i++)
		{
			rValue=rValue==""?"'"+arr[i]+"'":rValue+","+"'"+arr[i]+"'";
		}
		return rValue;
	}
	 
	
	/*
	 * 清除字符串前后指定的符号
	 * @param str 原字符串
	 * @param dot 指定的符号
	 * @return
	 */
	public String trimSymbol(String str,String dot)
	{
		String rValue = str;
		if(this.IsEmpty(str)||str.equals(dot)) {
			return "";
		}
		if(str.startsWith(dot)) {
			rValue = str.substring(dot.length());
		}
		if(str.endsWith(dot)) {
			rValue = str.substring(0,str.length()-1-(dot.length()-1));
		}
		return rValue;
	}
	
	/*
	 * 返回字符串中无重复的元素 
	 * @param tempString 用；分割的字符串
	 * @param splitChar 分隔的符号
	 * @return
	 */
	public String getNoSameString(String tempString,String splitChar)
	{ 
		String emp="";
		if(tempString.length()==0) {
			return "";
		}
		if(tempString.indexOf(splitChar)<0) {
			emp = tempString;
			return emp;
		} else {
			String[] array = tempString.split(splitChar);
			for(int i=0;i<array.length;i++)  
			{ 
				String temp = array[i]; 
				for(int j=i+1;j<array.length;j++) { 
					if(temp.equals(array[j])) { 
						array[i] = ""; 
					} 
				}
				if(!array[i].equals("")) {
						emp += splitChar+array[i];
				}
			}
			if(emp.length()>0) {
				emp=  emp.substring(1);
			}
			return emp;
		}
	}
	
	/*
	 * 获取日期date前的days天
	 * @param date 日期
	 * @param days 间隔的天数
	 * @param formatDay  date的格式
	 */
	public static String getBeforeDate(Date date,int days,String formatDay)   
	{   
	    SimpleDateFormat df = new SimpleDateFormat(formatDay);   
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - days);   
	    return df.format(calendar.getTime());   
	}  
	/*
	 * 获取日期date后的days天
	 * @param date 日期
	 * @param days 间隔的天数
	 * @param formatDay  date的格式
	 */
	public static String getAfterDate(Date date,int days,String formatDay)   
	{   
	    SimpleDateFormat df = new SimpleDateFormat(formatDay);   
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + days);   
	    return df.format(calendar.getTime());   
	}  
 
	/*
	 * 构造灰色[disabled]的下拉菜单
	 * @param name  下拉框名
	 * @param value （opion的value值）
	 * @param text （opion的text值）
	 */
	public final static String SLTDISABLED(String name, String value,String text) 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<select name=\""+name+"\" disabled>");
		sb.append("<option value=" + value);
		sb.append(" selected");
	    sb.append(">" + text + "</option>");
		sb.append("</select>");
		return sb.toString();
	}
	 
	/*
	 * 构造非灰色的下拉菜单
	 * @param name  下拉框名
	 * @param value （opion的value值）
	 * @param text （opion的text值）
	 */
	public final static String SLTSELECTED(String name, String value,String text) 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<select name=\""+name+"\">");
		sb.append("<option value=" + value);
		sb.append(" selected");
	    sb.append(">" + text + "</option>");
		sb.append("</select>");
		return sb.toString();
	}
 
	/*
	 * 截取字符串某一长度并返回
	 * @param p_StrValue 要截取的字符串
	 * @param p_StrLen   要截取的字符串长度
	 * @param p_HavePoints 截取长度小于总长度时是否要加省略号 
	 */
	public static String LeftStr(String p_StrValue, int p_StrLen,
			boolean p_HavePoints) {
		String m_NewStr = "";
		int m_Num = 0;
		int i = 0;
		for (i = 0; i < p_StrValue.length(); i++) {
			char c = p_StrValue.charAt(i);
			if (((int) c > 255) || ((int) c < 0)) {
				m_Num = m_Num + 2;
			} else {
				m_Num = m_Num + 1;
			}
			if (m_Num >= p_StrLen) {
				m_NewStr = p_StrValue.substring(0, i + 1);
				if (p_HavePoints == true && i < p_StrValue.length()) {
					m_NewStr = m_NewStr + "...";
				}
				return m_NewStr;
			}
		}
		return p_StrValue.substring(0, i);
	}
	
	public static String NumberToChinese(String input){   
        String s1="零壹贰叁肆伍陆柒捌玖";   
        String s4="分角整元拾佰仟万拾佰仟亿拾佰仟";   
        String temp="";   
        String result="";   
        if (input==null) return "输入的字串不是数字串只能包括以下字符（'0'~'9','.'),输入字串最大只能精确到仟亿，小数点只能两位！";   
        temp=input.trim();   
        @SuppressWarnings("unused")
		float f;   
        try{   
            f=Float.parseFloat(temp);   
        }catch(Exception e){   
            return "输入的字串不是数字串只能包括以下字符（'0'~'9','.'),输入字串最大只能精确到仟亿，小数点只能两位！";   
        }   
        int len=0;   
        if(temp.indexOf(".")==-1) len=temp.length();   
        else len=temp.indexOf(".");   
        if(len>s4.length()-3) return("输入字串最大只能精确到仟亿，小数点只能两位！");   
        int n1=0;   
        String num="";   
        String unit="";   
        for(int i=0;i<temp.length();i++){   
            if(i>len+2){break;}   
            if(i==len) {continue;}   
            n1=Integer.parseInt(String.valueOf(temp.charAt(i)));   
            num=s1.substring(n1,n1+1);   
            n1=len-i+2;   
            unit=s4.substring(n1,n1+1);   
            result=result.concat(num).concat(unit);   
        }   
        if((len==temp.length())||(len==temp.length()-1)) result=result.concat("整");   
        if(len==temp.length()-2) result=result.concat("零分");   
        return result;   
    } 
	/**
	 * 这样的字符串aaaa,aaaa,aaa,返回'aaa','aaa','aaa'
	 * @param str
	 * @param chr
	 * @return
	 */
	public String strFormatNeed(String str, String chr){
 		String[]	tempArr		=	str.split(chr);
		String		rStr	=	"";
		for(int i=0;i<tempArr.length;i++){
			rStr = rStr + "'" + tempArr[i] + "',";
		}
		if(tempArr.length>=1)
		{
			rStr=rStr.substring(0, rStr.length()-1);   
		}
        if(str.indexOf(",")<0)
		{
			rStr = "'"+str+"'";
		}
		return rStr; 
	} 
}