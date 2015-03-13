package com.nomen.ntrain.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @description 根据表快速生成Java代码 
 * @author 
 * @date 2015-01-18
 */
public class GetBaseJavaCodeFromTable {	  
	public String filepaths = "/src/com/nomen/ntrain/ibmc";
	public String table_name = "man_card_record";
	public String author = "  ";
	public Connection con;
	
	public static void main(String[] args) {
		
		GetBaseJavaCodeFromTable test = new GetBaseJavaCodeFromTable();
		try {
			test.init(new FileInputStream(System.getProperty("user.dir")+"/WebRoot/WEB-INF/source.properties"));
			List<Map<String,String>> datalist = new ArrayList<Map<String,String>>();
			Map<String,String> datamap = new HashMap<String,String>();
			datalist = test.findTableInfo();
			datamap = test.findTableName();
			test.GenerationBeanJavaFile(datalist,datamap);//生成Bean.java文件
			
			List<Map<String,String>> typelist = test.GenerationSqlXmlFile(datalist,datamap);//生成Sql.XML文件
			test.GenerationDAOJavaFile(typelist,datamap);//生成DAO.java文件
			test.GenerationDAOImplJavaFile(typelist,datamap);//生成DAOImpl.java文件			
			test.GenerationServiceJavaFile(typelist,datamap);//生成Service.java文件
			test.GenerationServiceImplJavaFile(typelist,datamap);//生成ServiceImpl.java文件			
//			test.GenerationActionJavaFile(datamap);//生成Action.java文件
//			test.GenerationActionXMLFile(datamap);//生成Action.XML文件
//			
			//test.SetSqlXMLFile(datamap);//配置Sql.XML文件
			//test.SetStrutsXMLFile(datamap);//配置Action.XML文件
			System.out.println("生出来了！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
//		PubFunc func = new PubFunc();
//		System.out.println(func.NumberToChinese("12"));
	}
	
	public void init(FileInputStream fs) throws Exception
	{
		Properties props = new Properties();
		props.load(fs);
		String url = props.getProperty("source.url");
		String userName = props.getProperty("source.username");
		String password = props.getProperty("source.password");
		Class.forName(props.getProperty("source.driverClassName"));
		con=DriverManager.getConnection(url, userName, password);
	}
	
	public List<Map<String,String>> findTableInfo() throws SQLException, IOException{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT USER_TAB_COLS.TABLE_NAME as 表名,");
		sql.append("USER_TAB_COLS.COLUMN_NAME  as 列名,");
		sql.append("USER_TAB_COLS.DATA_TYPE    as 数据类型,");
		sql.append("USER_TAB_COLS.DATA_LENGTH  as 长度,");
		sql.append("USER_TAB_COLS.NULLABLE     as 是否为空,");
		sql.append("USER_TAB_COLS.COLUMN_ID    as 列序号,");
		sql.append("user_col_comments.comments as 备注 ");
		sql.append("FROM USER_TAB_COLS ");
		sql.append("inner join user_col_comments on user_col_comments.TABLE_NAME = USER_TAB_COLS.TABLE_NAME ");
		sql.append("and user_col_comments.COLUMN_NAME = USER_TAB_COLS.COLUMN_NAME ");
		sql.append("and USER_TAB_COLS.TABLE_NAME='"+table_name.toUpperCase()+"'");
		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		List<Map<String,String>> datalist = new ArrayList<Map<String,String>>();
		while (rs.next()){
			Map<String,String> datamap = new HashMap<String,String>();
			datamap.put("table_name", rs.getString(1));
			datamap.put("column_name", rs.getString(2).toLowerCase());
			datamap.put("data_type", rs.getString(3));
			datamap.put("data_length", rs.getString(4));
			datamap.put("nullable", rs.getString(5));
			datamap.put("column_id", rs.getString(6));
			datamap.put("comments", rs.getString(7));
			datalist.add(datamap);
		}
		rs.close();
		ps.close();
		return datalist;
	}
	
	public Map<String,String> findTableName() throws SQLException, IOException{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT A.TABLE_NAME,A.COMMENTS FROM USER_TAB_COMMENTS A WHERE  A.TABLE_NAME='"+table_name.toUpperCase()+"'");
		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		Map<String,String> datamap = new HashMap<String,String>();
		while (rs.next()){	
			datamap.put("table_name", rs.getString(1));
			datamap.put("comments", rs.getString(2));
		}
		rs.close();
		ps.close();
		return datamap;
	}
	
	
	/**
	 * 生成Bean代码
	 * @param datalist
	 * @param datamap
	 * @throws IOException
	 */
	public void GenerationBeanJavaFile(List<Map<String,String>> datalist,Map<String,String> datamap) throws IOException{
		PubFunc pubFunc = new PubFunc();
		String tablename = datamap.get("table_name").toString().toLowerCase();
		String comments = datamap.get("comments").toString();
		//String filename = (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();
		//filename += (tablename.split("_")[2]).substring(0,1).toUpperCase()+(tablename.split("_")[2]).substring(1,(tablename.split("_")[2]).length()).toLowerCase()+"Bean";
		String filename = (tablename.split("_")[0]).substring(0,1).toUpperCase()+(tablename.split("_")[0]).substring(1,(tablename.split("_")[0]).length()).toLowerCase();
		filename += (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase()+"Bean";
		String beantype = (filepaths.replace("/src/", "")).replace("/", ".")+".bean";
		//生成文件
		File file = new File(System.getProperty("user.dir")+filepaths+"/bean"); 
		file.mkdirs();	//生成目录
		FileWriter xmlfile =new FileWriter(System.getProperty("user.dir")+filepaths+"/bean/"+filename+".java"); 
		PrintWriter pw = new PrintWriter(xmlfile); 
		pw.println("package "+beantype+";"); 
		pw.println(""); 
		pw.println("/**"); 
		pw.println(" * @description "+comments+"POJO "); 
		pw.println(" * @author "+author); 
		pw.println(" * @date "+pubFunc.getYear()+"-"+pubFunc.getMonth()+"-"+pubFunc.getDay());  
		pw.println(" */"); 
		pw.println("public class "+filename+" {"); 
		
		int len = 0;//开始注释的位置
		for(Map temp : datalist){
			String info = "    private String "+pubFunc.ChkNull((String)temp.get("column_name"), "")+";";
			if(info.length()>len){
				len = info.length();
			}
		}
		for(Map temp : datalist){
			String info = "    private String "+pubFunc.ChkNull((String)temp.get("column_name"), "")+";";
			pw.println(info+this.AddSpaces(len-info.length())+"//"+pubFunc.ChkNull((String)temp.get("comments"), "")); 
		}
		pw.println(""); 
		pw.println("	//Get和Set方法"); 
		for(Map temp : datalist){
			String element = temp.get("column_name").toString(); 
			String element1 = element.substring(0,1).toUpperCase()+element.substring(1,element.length()).toLowerCase();
			pw.println("    public String get"+element1+"() {"); 
			pw.println("        return "+element+";"); 
			pw.println("    }"); 
			pw.println("    public void set"+element1+"(String "+element+") {"); 
			pw.println("        this."+element+" = "+element+";"); 
			pw.println("    }"); 
		}
		pw.println("}"); 		
		pw.flush(); 
		xmlfile.close();
	}

	
	/**
	 * 生成Sql配置文件
	 * @param datalist
	 * @param datamap
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> GenerationSqlXmlFile(List<Map<String,String>> datalist,Map<String,String> datamap) throws IOException{
		PubFunc pubFunc = new PubFunc();
		List<Map<String,String>> typelist = new ArrayList<Map<String,String>>();
		String tablename = datamap.get("table_name").toString().toLowerCase();
		String comments = datamap.get("comments").toString();
		String elements ="";
		String elementsForSelect ="";
		String elementsForInsert ="";
		for(Map temp : datalist){
			String data_type = pubFunc.ChkNull((String)temp.get("data_type"), "");
			String column_name = pubFunc.ChkNull((String)temp.get("column_name"), "");
			if("DATE".equals(data_type)){
				elementsForSelect += ",to_char("+column_name+",'yyyy-mm-dd') "+column_name;
				elementsForInsert += ",sysdate";
			}else{
				elementsForSelect += ","+column_name;
				elementsForInsert += ","+column_name;
			}
			elements += ","+column_name;
		}
		elements = elements.substring(1);
		elementsForSelect = elementsForSelect.substring(1);
		elementsForInsert = elementsForInsert.substring(1);
		String filename = (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();
		//filename += (tablename.split("_")[2]).substring(0,1).toUpperCase()+(tablename.split("_")[2]).substring(1,(tablename.split("_")[2]).length()).toLowerCase();
		filename += (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();

		
		String beanname =(filename.substring(0,1)).toLowerCase()+filename.substring(1,filename.length())+"Bean";
		String beantype = (filepaths.replace("/src/", "")).replace("/", ".")+".bean."+filename+"Bean";
		//生成文件
		File file = new File(System.getProperty("user.dir")+filepaths+"/config/sqlmap"); 
		file.mkdirs();	//生成目录
		FileWriter xmlfile =new FileWriter(System.getProperty("user.dir")+filepaths+"/config/sqlmap/"+filename+".xml"); 
		PrintWriter pw = new PrintWriter(xmlfile); 
		pw.println("<?xml version=\"1.0\" encoding=\"gbk\" ?>"); 
		pw.println("<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\">"); 
		pw.println("<!--"); 
		pw.println("@description "+comments); 
		pw.println("@author "+author); 
		pw.println("@date "+pubFunc.getYear()+"-"+pubFunc.getMonth()+"-"+pubFunc.getDay()); 
		pw.println("-->"); 
		pw.println("<sqlMap namespace=\""+filename+"\">"); 
		pw.println("	<typeAlias alias=\""+beanname+"\" type=\""+beantype+"\"/>"); 
		
		/********************************************Select——查找表信息**********************************************************/
		pw.println("	<!-- 查找"+comments+"信息 -->"); 
		pw.println("	<select id=\"find"+filename+"Bean\" resultClass=\""+beanname+"\" parameterClass=\"map\">"); 
		pw.println("		select "+elementsForSelect+" from "+tablename+" where 1=1"); 
		//关键字加入查询
		for(Map temp : datalist){
			if("N".equals(temp.get("nullable"))){
				String element = temp.get("column_name").toString();
				pw.println("		<isNotEmpty prepend=\"and\" property=\""+element+"\">"); 
				pw.println("			"+element+" = #"+element+"#"); 
				pw.println("		</isNotEmpty>"); 
			}
		}
		pw.println("	</select>"); 
		pw.println("");
		
		//保存配置文件信息以供DAO读取使用
		Map temps = new HashMap();
		temps.put("id", "find"+filename+"Bean");
		temps.put("type", "queryForObject");
		temps.put("resultClass", filename+"Bean");
		temps.put("parameterClass", "Map");
		temps.put("comments", "查找"+comments+"信息");
		typelist.add(temps);
		/********************************************Select——查找表信息（列表）**********************************************************/
		pw.println("	<!-- 查找"+comments+"列表信息 -->"); 
		pw.println("	<select id=\"find"+filename+"List\" resultClass=\""+beanname+"\" parameterClass=\"map\">"); 
		pw.println("		select "+elementsForSelect+" from "+tablename+" where 1=1"); 
		//关键字加入查询
		for(Map temp : datalist){
			if("N".equals(temp.get("nullable"))){
				String element = temp.get("column_name").toString();
				pw.println("		<isNotEmpty prepend=\"and\" property=\""+element+"\">"); 
				pw.println("			"+element+" = #"+element+"#"); 
				pw.println("		</isNotEmpty>"); 
			}
		}
		pw.println("	</select>"); 
		pw.println(""); 
//		保存配置文件信息以供DAO读取使用
		temps = new HashMap();
		temps.put("id", "find"+filename+"List");
		temps.put("type", "queryForList");
		temps.put("resultClass", "List<"+filename+"Bean>");
		temps.put("parameterClass", "Map");
		temps.put("comments", "查找"+comments+"列表信息");
		typelist.add(temps);
		/********************************************Insert——添加表信息**********************************************************/
		pw.println("	<!-- 添加"+comments+"信息 -->"); 
		pw.println("	<insert id=\"insert"+filename+"\" parameterClass=\""+beanname+"\" >");
		if("id".equals(datalist.get(0).get("column_name"))){ 
			pw.println("		<selectKey resultClass=\"java.lang.String\" keyProperty=\"id\">"); 
			pw.println("			select "+tablename.replace("tb_", "seq_")+".nextval as id from dual"); 
			pw.println("		</selectKey>"); 
		}
		pw.println("		insert into "+tablename); 
		pw.println("		  ("+elements+")"); 
		pw.println("		values"); 
		pw.println(("		  (#"+elementsForInsert.replace(",", "#,#")+"#)").replace(",#sysdate#,", ",sysdate,")); 
		pw.println("	</insert> "); 
		pw.println(""); 
		
//		保存配置文件信息以供DAO读取使用
		temps = new HashMap();
		temps.put("id", "insert"+filename);
		temps.put("type", "insert");
		temps.put("comments", "添加"+comments+"信息");
		temps.put("resultClass", "String");
		temps.put("parameterClass", filename+"Bean");
		typelist.add(temps);
		/********************************************Updatet——更新表信息**********************************************************/
		pw.println("	<!-- 更新"+comments+"信息 -->"); 
		pw.println("	<update id=\"update"+filename+"\" parameterClass=\""+beanname+"\">"); 
		String updatekey = "";
		for(Map temp : datalist){
			if("N".equals(temp.get("nullable"))){
				String element = temp.get("column_name").toString();
				updatekey += ", "+element+" = #"+element+"#";
			}
		}
		if(!"".equals(updatekey)){
			updatekey = updatekey.substring(1);
		}
		pw.println("		update "+tablename+" set "+updatekey); 
		for(Map temp : datalist){
			if("Y".equals(temp.get("nullable"))){
				String element = temp.get("column_name").toString();
				pw.println("		<isNotEmpty prepend=\",\" property=\""+element+"\">"); 
				pw.println("			"+element+" = #"+element+"#"); 
				pw.println("		</isNotEmpty>");
			}
		}
		pw.println("		where "+updatekey); 
		pw.println("	</update>"); 
		pw.println(""); 
		
//		保存配置文件信息以供DAO读取使用
		temps = new HashMap();
		temps.put("id", "update"+filename);
		temps.put("type", "update");
		temps.put("comments", "更新"+comments+"信息");
		temps.put("parameterClass", filename+"Bean");
		typelist.add(temps);
		/********************************************Delete——删除表信息**********************************************************/
		pw.println("	<!-- 删除"+comments+"信息 -->"); 
		pw.println("	<delete id=\"delete"+filename+"\" parameterClass=\"map\">"); 
		pw.println("		delete from " + tablename + " where id=#id# "); 
		for(Map temp : datalist){
			if("N".equals(temp.get("nullable"))){
				String element = temp.get("column_name").toString();
				pw.println("		<isNotEmpty prepend=\"and\" property=\""+element+"\">"); 
				pw.println("			"+element+" = #"+element+"#"); 
				pw.println("		</isNotEmpty>"); 
			}
		}
		pw.println("	</delete>"); 
		pw.println(""); 
		
//		保存配置文件信息以供DAO读取使用
		temps = new HashMap();
		temps.put("id", "delete"+filename);
		temps.put("type", "delete");
		temps.put("comments", "删除"+comments+"信息");
		temps.put("parameterClass", "Map");
		typelist.add(temps);
		/********************************************表信息**********************************************************/
		pw.println("</sqlMap>"); 
		pw.flush(); 
		xmlfile.close();
		
		return typelist;
	}
	
	/**
	 * 获取DAOJAVA代码
	 * @param typelist
	 * @param datamap
	 * @throws IOException
	 */
	public void GenerationDAOJavaFile(List<Map<String,String>> typelist,Map<String,String> datamap) throws IOException{
		PubFunc pubFunc = new PubFunc();
		String tablename = datamap.get("table_name").toString().toLowerCase();
		String comments = datamap.get("comments").toString();
		//String filename = (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();
		//filename += (tablename.split("_")[2]).substring(0,1).toUpperCase()+(tablename.split("_")[2]).substring(1,(tablename.split("_")[2]).length()).toLowerCase();
		String filename = (tablename.split("_")[0]).substring(0,1).toUpperCase()+(tablename.split("_")[0]).substring(1,(tablename.split("_")[0]).length()).toLowerCase();
		filename += (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase()+"Bean";

		String beantype = (filepaths.replace("/src/", "")).replace("/", ".")+".dao";
//		生成文件
		File file = new File(System.getProperty("user.dir")+filepaths+"/dao"); 
		file.mkdirs();	//生成目录
		FileWriter xmlfile =new FileWriter(System.getProperty("user.dir")+filepaths+"/dao/"+filename+"DAO.java"); 
		PrintWriter pw = new PrintWriter(xmlfile); 
		pw.println("package "+beantype+";");
		pw.println("");
		pw.println("import java.util.List;"); 
		pw.println("import java.util.Map;"); 
		pw.println(""); 
		pw.println("import "+(filepaths.replace("/src/", "")).replace("/", ".")+".bean."+filename+"Bean;");
		pw.println(""); 
		pw.println("/**"); 
		pw.println(" * @description "+comments+"数据库操作接口"); 
		pw.println(" * @author "+author); 
		pw.println(" * @date "+pubFunc.getYear()+"-"+pubFunc.getMonth()+"-"+pubFunc.getDay());  
		pw.println(" */"); 
		pw.println("public interface "+filename+"DAO {"); 
		
		for(Map temp : typelist){
			String id = pubFunc.ChkNull(temp.get("id").toString(),"");
			String parameterClass = pubFunc.ChkNull(temp.get("parameterClass").toString(),"");
			String resultClass = pubFunc.ChkNull((String)temp.get("resultClass"),"void");
			pw.println("");
			pw.println("	/**"); 
			pw.println("	 * "+pubFunc.ChkNull(temp.get("comments").toString(),"")); 
			pw.println("	 * @param "+this.Conversion(parameterClass)); 
			pw.println("	 * @return"); 
			pw.println("	 */"); 
			pw.println("	public "+resultClass+" "+id+"("+parameterClass+" "+this.Conversion(parameterClass)+");"); 
		}
		pw.println("");
		pw.println("}"); 		
		pw.flush(); 
		xmlfile.close();
	}
	
	
	/**
	 * 获取DAOImplJAVA代码
	 * @param typelist
	 * @param datamap
	 * @throws IOException
	 */
	public void GenerationDAOImplJavaFile(List<Map<String,String>> typelist,Map<String,String> datamap) throws IOException{
		PubFunc pubFunc = new PubFunc();
		String tablename = datamap.get("table_name").toString().toLowerCase();
		//String filename = (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();
		//filename += (tablename.split("_")[2]).substring(0,1).toUpperCase()+(tablename.split("_")[2]).substring(1,(tablename.split("_")[2]).length()).toLowerCase();
		String filename = (tablename.split("_")[0]).substring(0,1).toUpperCase()+(tablename.split("_")[0]).substring(1,(tablename.split("_")[0]).length()).toLowerCase();
		filename += (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase()+"Bean";
		String beantype = (filepaths.replace("/src/", "")).replace("/", ".")+".dao.implement";
//		生成文件
		File file = new File(System.getProperty("user.dir")+filepaths+"/dao/implement"); 
		file.mkdirs();	//生成目录
		FileWriter xmlfile =new FileWriter(System.getProperty("user.dir")+filepaths+"/dao/implement/"+filename+"DAOImpl.java"); 
		PrintWriter pw = new PrintWriter(xmlfile); 
		pw.println("package "+beantype+";");
		pw.println("");
		pw.println("import java.util.List;"); 
		pw.println("import java.util.Map;"); 
		pw.println(""); 
		pw.println("import "+(filepaths.replace("/src/", "")).replace("/", ".")+".bean."+filename+"Bean;");
		pw.println("import "+(filepaths.replace("/src/", "")).replace("/", ".")+".dao."+filename+"DAO;");
		pw.println("import "+filepaths.split("/")[2]+"."+filepaths.split("/")[3]+"."+filepaths.split("/")[4]+".util.NsoftBaseDao;");
		pw.println("@SuppressWarnings(\"all\")"); 
		pw.println("public class "+filename+"DAOImpl extends NsoftBaseDao implements "+filename+"DAO {"); 
		
		for(Map temp : typelist){
			String id = pubFunc.ChkNull(temp.get("id").toString(),"");
			String type = pubFunc.ChkNull(temp.get("type").toString(),"");
			String parameterClass = pubFunc.ChkNull(temp.get("parameterClass").toString(),"");
			String resultClass = pubFunc.ChkNull((String)temp.get("resultClass"),"void");
			pw.println("");
			pw.println("	public "+resultClass+" "+id+"("+parameterClass+" "+this.Conversion(parameterClass)+"){"); 
			String returnstr = "";
			if("void".equals(resultClass)){
				returnstr = "";
			}else if(resultClass.indexOf("List")>0){
				returnstr = "return ";
			}else if(resultClass.indexOf("Bean")>0){
				returnstr = "return ("+resultClass+")";
			}else if("String".equals(resultClass)){
				returnstr = "return ("+resultClass+")";
			}
			pw.println("		"+returnstr+"this.getSqlMapClientTemplate()."+type+"(\""+filename+"."+id+"\", "+this.Conversion(parameterClass)+");");
			pw.println("	}");
		}
		pw.println("");
		pw.println("}"); 		
		pw.flush(); 
		xmlfile.close();
	}
	
	/**
	 * 获取Service JAVA代码
	 * @param typelist
	 * @param datamap
	 * @throws IOException
	 */
	public void GenerationServiceJavaFile(List<Map<String,String>> typelist,Map<String,String> datamap) throws IOException{
		PubFunc pubFunc = new PubFunc();
		String tablename = datamap.get("table_name").toString().toLowerCase();
		String comments = datamap.get("comments").toString();
		//String filename = (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();
		//filename += (tablename.split("_")[2]).substring(0,1).toUpperCase()+(tablename.split("_")[2]).substring(1,(tablename.split("_")[2]).length()).toLowerCase();
		String filename = (tablename.split("_")[0]).substring(0,1).toUpperCase()+(tablename.split("_")[0]).substring(1,(tablename.split("_")[0]).length()).toLowerCase();
		filename += (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase()+"Bean";		
		String beantype = (filepaths.replace("/src/", "")).replace("/", ".")+".service";
//		生成文件
		File file = new File(System.getProperty("user.dir")+filepaths+"/service"); 
		file.mkdirs();	//生成目录
		FileWriter xmlfile =new FileWriter(System.getProperty("user.dir")+filepaths+"/service/"+filename+"Service.java"); 
		PrintWriter pw = new PrintWriter(xmlfile); 
		pw.println("package "+beantype+";");
		pw.println("");
		pw.println("import java.util.List;"); 
		pw.println("import java.util.Map;"); 
		pw.println(""); 
		pw.println("import "+(filepaths.replace("/src/", "")).replace("/", ".")+".bean."+filename+"Bean;");
		pw.println(""); 
		pw.println("/**"); 
		pw.println(" * @description "+comments+"业务逻辑层"); 
		pw.println(" * @author "+author); 
		pw.println(" * @date "+pubFunc.getYear()+"-"+pubFunc.getMonth()+"-"+pubFunc.getDay());  
		pw.println(" */"); 
		pw.println("public interface "+filename+"Service {"); 
		
		for(Map temp : typelist){
			String id = pubFunc.ChkNull(temp.get("id").toString(),"");
			String parameterClass = pubFunc.ChkNull(temp.get("parameterClass").toString(),"");
			String resultClass = pubFunc.ChkNull((String)temp.get("resultClass"),"void");
			pw.println("");
			pw.println("	/**"); 
			pw.println("	 * "+pubFunc.ChkNull(temp.get("comments").toString(),"")); 
			pw.println("	 * @param "+this.Conversion(parameterClass)); 
			pw.println("	 * @return"); 
			pw.println("	 */"); 
			pw.println("	public "+resultClass+" "+id+"("+parameterClass+" "+this.Conversion(parameterClass)+");"); 
		}
		pw.println("");
		pw.println("}"); 		
		pw.flush(); 
		xmlfile.close();
	}
	
	/**
	 * 获取DAOImplJAVA代码
	 * @param typelist
	 * @param datamap
	 * @throws IOException
	 */
	public void GenerationServiceImplJavaFile(List<Map<String,String>> typelist,Map<String,String> datamap) throws IOException{
		PubFunc pubFunc = new PubFunc();
		String tablename = datamap.get("table_name").toString().toLowerCase();
		//String filename = (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();
		//filename += (tablename.split("_")[2]).substring(0,1).toUpperCase()+(tablename.split("_")[2]).substring(1,(tablename.split("_")[2]).length()).toLowerCase();
		String filename = (tablename.split("_")[0]).substring(0,1).toUpperCase()+(tablename.split("_")[0]).substring(1,(tablename.split("_")[0]).length()).toLowerCase();
		filename += (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase()+"Bean";		
		String beantype = (filepaths.replace("/src/", "")).replace("/", ".")+".service.implement";
//		生成文件
		File file = new File(System.getProperty("user.dir")+filepaths+"/service/implement"); 
		file.mkdirs();	//生成目录
		FileWriter xmlfile =new FileWriter(System.getProperty("user.dir")+filepaths+"/service/implement/"+filename+"ServiceImpl.java"); 
		PrintWriter pw = new PrintWriter(xmlfile); 
		pw.println("package "+beantype+";");
		pw.println("");
		pw.println("import java.util.List;"); 
		pw.println("import java.util.Map;"); 
		pw.println(""); 
		//导入部分
		pw.println("import "+filepaths.split("/")[2]+"."+filepaths.split("/")[3]+"."+filepaths.split("/")[4]+".base.bean.BaseOptLogBean;");
		pw.println("import "+filepaths.split("/")[2]+"."+filepaths.split("/")[3]+"."+filepaths.split("/")[4]+".base.service.implement.BaseServiceImpl;"); 
		pw.println("import "+(filepaths.replace("/src/", "")).replace("/", ".")+".bean."+filename+"Bean;");
		pw.println("import "+(filepaths.replace("/src/", "")).replace("/", ".")+".dao."+filename+"DAO;");
		pw.println("import "+(filepaths.replace("/src/", "")).replace("/", ".")+".service."+filename+"Service;");
		
		pw.println("@SuppressWarnings(\"all\")"); 
		pw.println("public class "+filename+"ServiceImpl extends BaseServiceImpl implements "+filename+"Service {"); 
		pw.println("");
		pw.println("	private "+filename+"DAO "+this.Conversion(filename)+"DAO;");
		for(Map temp : typelist){
			String id = pubFunc.ChkNull(temp.get("id").toString(),"");
			String parameterClass = pubFunc.ChkNull(temp.get("parameterClass").toString(),"");
			String resultClass = pubFunc.ChkNull((String)temp.get("resultClass"),"void");
			pw.println("");
			pw.println("	public "+resultClass+" "+id+"("+parameterClass+" "+this.Conversion(parameterClass)+"){"); 
			String returnstr = "";
			if("void".equals(resultClass)){
				returnstr = this.Conversion(filename)+"DAO."+id+"("+this.Conversion(parameterClass)+");";
			}else {
				returnstr = "return "+this.Conversion(filename)+"DAO."+id+"("+this.Conversion(parameterClass)+");";
			}
			pw.println("		"+returnstr);
			pw.println("	}");
		}
		pw.println("");
		pw.println("	//Get和Set方法"); 
		pw.println("	public "+filename+"DAO get"+filename+"DAO() {");
		pw.println("		return "+this.Conversion(filename)+"DAO;");
		pw.println("	}");
		pw.println("	public void set"+filename+"DAO("+filename+"DAO "+this.Conversion(filename)+"DAO) {");
		pw.println("		this."+this.Conversion(filename)+"DAO = "+this.Conversion(filename)+"DAO;");
		pw.println("	}");
		pw.println("}"); 		
		pw.flush(); 
		xmlfile.close();
	}
	
	/**
	 * 获取DAOImplJAVA代码
	 * @param typelist
	 * @param datamap
	 * @throws IOException
	 */
	public void GenerationActionJavaFile(Map<String,String> datamap) throws IOException{
		PubFunc pubFunc = new PubFunc();
		String comments = datamap.get("comments").toString();
		String tablename = datamap.get("table_name").toString().toLowerCase();
		//String filename = (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();
		//filename += (tablename.split("_")[2]).substring(0,1).toUpperCase()+(tablename.split("_")[2]).substring(1,(tablename.split("_")[2]).length()).toLowerCase();
		String filename = (tablename.split("_")[0]).substring(0,1).toUpperCase()+(tablename.split("_")[0]).substring(1,(tablename.split("_")[0]).length()).toLowerCase();
		filename += (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase()+"Bean";		
		String beantype = (filepaths.replace("/src/", "")).replace("/", ".")+".action";
		String conversion = this.Conversion(filename);
//		生成文件
		File file = new File(System.getProperty("user.dir")+filepaths+"/action"); 
		file.mkdirs();	//生成目录
		FileWriter xmlfile =new FileWriter(System.getProperty("user.dir")+filepaths+"/action/"+filename+"Action.java"); 
		PrintWriter pw = new PrintWriter(xmlfile); 
		pw.println("package "+beantype+";");
		pw.println("");
		pw.println("import java.util.HashMap;");
		pw.println("import java.util.List;");
		pw.println("import java.util.Map;");
		pw.println("");
		pw.println("import javax.servlet.http.HttpServletRequest;");
		pw.println("import org.apache.struts2.ServletActionContext;");
		pw.println("");
		pw.println("import "+(filepaths.replace("/src/", "")).replace("/", ".")+".bean."+filename+"Bean;");
		pw.println("import "+(filepaths.replace("/src/", "")).replace("/", ".")+".service."+filename+"Service;");
		pw.println(""); 
		pw.println("/**"); 
		pw.println(" * @description "+comments+"action层"); 
		pw.println(" * @author "+author); 
		pw.println(" * @date "+pubFunc.getYear()+"-"+pubFunc.getMonth()+"-"+pubFunc.getDay());  
		pw.println(" */"); 
		pw.println("@SuppressWarnings(\"all\")");
		pw.println("public class "+filename+"Action extends "+this.Uppercase(filepaths.split("/")[5])+"Action{");
		pw.println("");                 
		pw.println("private "+filename+"Service      "+conversion+"Service;  //代码列表业务接口");
		pw.println("private "+filename+"Bean         "+conversion+"Bean;     //岗位类别代码信息表");
		pw.println("private Map<String,String>   parammap;         //传参map");
		pw.println("");
		pw.println("	/**");
		pw.println("	 * @description列表信息");
		pw.println("	 */");
		pw.println("	public String list"+filename+"() {");
		pw.println("		this.showValue=this.getMenu();");
		pw.println("		//权限判断【是否有该功能权限】");
		pw.println("//		if(this.testPurview(\""+this.Conversion(filepaths.split("/")[5])+"_system\",\""+this.Conversion(filepaths.split("/")[5])+"_system\"))return Constant.LOGIN_NOPUR;");
		pw.println("		HttpServletRequest request = ServletActionContext.getRequest();");
		pw.println("		Map param = new HashMap();");
		pw.println("		request.setAttribute(\"datalist\", this."+conversion+"Service.find"+filename+"List(param));");
		pw.println("		return SUCCESS;");
		pw.println("	}");
		pw.println("");
		pw.println("	/**");
		pw.println("	 * @description新增、修改跳转");
		pw.println("	 */");
		pw.println("	public String set"+filename+"() {");
		pw.println("		this.showValue=this.getMenu();");
		pw.println("		//不保留用户记录的信息");
		pw.println("		if(\"0\".equals(this.taksign)) {");
		pw.println("			if(\"1\".equals(this.fun)) {");
		pw.println("				//清空bean中角色和角色信息的内容");
		pw.println("				this.gosign = \"1\";");
		pw.println("			} else {");
		pw.println("				this."+conversion+"Bean = this."+conversion+"Service.find"+filename+"Bean(map);");
		pw.println("			}");
		pw.println("		}");
		pw.println("		return SUCCESS;");
		pw.println("	}");
		pw.println("");
		pw.println("	/**");
		pw.println("	 * @description新增信息");
		pw.println("	 */");
		pw.println("	public String insert"+filename+"() {");
		pw.println("		String rValue=INPUT;");
		pw.println("		try {");
		pw.println("			if(this.isValidToken()) {");
		pw.println("				this."+conversion+"Service.insert"+filename+"(this."+conversion+"Bean);");
		pw.println("			}");
		pw.println("			if(func.Trim(this.gosign).equals(\"1\")) {");
		pw.println("				this.set"+filename+"();");
		pw.println("			} else {");
		pw.println("				rValue=this.list"+filename+"();");
		pw.println("			}");
		pw.println("		} catch(Exception ex) {");
		pw.println("			this.setActother(\"insert.error\");");
		pw.println("			this.taksign=\"1\";");
		pw.println("			this.set"+filename+"();");
		pw.println("		}");
		pw.println("		return rValue;");
		pw.println("	}");
		pw.println("");
		pw.println("	/**");
		pw.println("	 * @description更新信息");
		pw.println("	 */");
		pw.println("	public String update"+filename+"() {");
		pw.println("		String rValue=INPUT;");
		pw.println("		try {");
		pw.println("			if(this.isValidToken()) {");
		pw.println("				this."+conversion+"Service.update"+filename+"(this."+conversion+"Bean);");
		pw.println("			}");
		pw.println("			if(func.Trim(this.gosign).equals(\"1\")) {");
		pw.println("				this.set"+filename+"();");
		pw.println("			} else {");
		pw.println("				rValue=this.list"+filename+"();");
		pw.println("			}");
		pw.println("		} catch(Exception ex) {");
		pw.println("			this.setActother(\"update.error\");");
		pw.println("			this.taksign=\"1\";");
		pw.println("			this.set"+filename+"();");
		pw.println("		}");
		pw.println("		return rValue;");
		pw.println("	}");
		pw.println("");
		pw.println("	/**");
		pw.println("	 * @description删除信息");
		pw.println("	 */");
		pw.println("	public String delete"+filename+"() {");
		pw.println("		try {");
		pw.println("			this."+conversion+"Service.delete"+filename+"(this.parammap);");
		pw.println("		} catch(Exception ex) {");
		pw.println("			this.setActother(\"delete.error\");");
		pw.println("			this.set"+filename+"();");
		pw.println("		}");
		pw.println("		return SUCCESS;");
		pw.println("	}");
		pw.println("");
		pw.println("	//Get和Set方法"); 
		pw.println("	public "+filename+"Bean get"+filename+"Bean() {");
		pw.println("		return "+this.Conversion(filename)+"Bean;");
		pw.println("	}");
		pw.println("	public void set"+filename+"Bean("+filename+"Bean "+this.Conversion(filename)+"Bean) {");
		pw.println("		this."+this.Conversion(filename)+"Bean = "+this.Conversion(filename)+"Bean;");
		pw.println("	}");
		pw.println("	public "+filename+"Service get"+filename+"Service() {");
		pw.println("		return "+this.Conversion(filename)+"Service;");
		pw.println("	}");
		pw.println("	public void set"+filename+"Service("+filename+"Service "+this.Conversion(filename)+"Service) {");
		pw.println("		this."+this.Conversion(filename)+"Service = "+this.Conversion(filename)+"Service;");
		pw.println("	}");
		pw.println("	public Map<String, String> getParammap() {");
		pw.println("		return parammap;");
		pw.println("	}");
		pw.println("	public void setParammap(Map<String, String> parammap) {");
		pw.println("		this.parammap = parammap;");
		pw.println("	}");
		pw.println("}");
		pw.flush(); 
		xmlfile.close();
	}
	
	/**
	 * 获取DAOImplJAVA代码
	 * @param typelist
	 * @param datamap
	 * @throws IOException
	 */
	public void GenerationActionXMLFile(Map<String,String> datamap) throws IOException{
		PubFunc pubFunc = new PubFunc();
		String comments = datamap.get("comments").toString();
		String tablename = datamap.get("table_name").toString().toLowerCase();
		//String filename = (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();
		//filename += (tablename.split("_")[2]).substring(0,1).toUpperCase()+(tablename.split("_")[2]).substring(1,(tablename.split("_")[2]).length()).toLowerCase();
		String filename = (tablename.split("_")[0]).substring(0,1).toUpperCase()+(tablename.split("_")[0]).substring(1,(tablename.split("_")[0]).length()).toLowerCase();
		filename += (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase()+"Bean";		
		String conversion = this.Conversion(filename);
		String namespace = this.Uppercase(tablename.split("_")[0])+"/"+this.Uppercase(tablename.split("_")[1]);
		String pagepath = this.Uppercase(tablename.split("_")[0])+"_"+this.Uppercase(tablename.split("_")[1]);
//		生成文件
		File file = new File(System.getProperty("user.dir")+filepaths+"/config/action"); 
		file.mkdirs();	//生成目录
		FileWriter xmlfile =new FileWriter(System.getProperty("user.dir")+filepaths+"/config/action/"+filename+"Action.xml"); 
		PrintWriter pw = new PrintWriter(xmlfile); 
		pw.println("<?xml version=\"1.0\" encoding=\"gbk\" ?>"); 
		pw.println("<!DOCTYPE struts PUBLIC"); 
		pw.println("    \"-//Apache Software Foundation//DTD Struts Configuration 2.0//EN\""); 
		pw.println("    \"http://struts.apache.org/dtds/struts-2.0.dtd\">"); 
		pw.println("<!--"); 
		pw.println("@description "+comments); 
		pw.println("@author "+author); 
		pw.println("@date "+pubFunc.getYear()+"-"+pubFunc.getMonth()+"-"+pubFunc.getDay()); 
		pw.println("-->"); 
		pw.println("<struts>");
		pw.println("    <package name=\""+filename+"\" extends=\"nsoft-ic\" namespace=\"/"+namespace+"\">");
		pw.println("");
		pw.println("		<!-- "+comments+" 列表信息 -->");
		pw.println("    	<action name=\"list"+filename.toLowerCase()+"\" class=\""+conversion+"Action\" method=\"list"+filename+"\">");
		pw.println("    		<result name=\"success\">/"+namespace+"/"+pagepath+"_List.jsp</result>");
		pw.println("		</action>");
		pw.println("");
		pw.println("		<!-- "+comments+" 新增、修改页面 -->");
		pw.println("    	<action name=\"set"+filename.toLowerCase()+"\" class=\""+conversion+"Action\" method=\"set"+filename+"\">");
		pw.println("    		<result name=\"success\">/"+namespace+"/"+pagepath+"_Set.jsp</result>");
		pw.println("		</action>");
		pw.println("");
		pw.println("		<!-- "+comments+" 新增 -->");
		pw.println("    	<action name=\"insert"+filename.toLowerCase()+"\" class=\""+conversion+"Action\" method=\"insert"+filename+"\">");
		pw.println("    		<result name=\"success\">/"+namespace+"/"+pagepath+"_List.jsp</result>");
		pw.println("    		<result name=\"input\">/"+namespace+"/"+pagepath+"_Set.jsp</result>");
		pw.println("		</action>");
		pw.println("");
		pw.println("		<!-- "+comments+" 修改-->");
		pw.println("    	<action name=\"update"+filename.toLowerCase()+"\" class=\""+conversion+"Action\" method=\"update"+filename+"\">");
		pw.println("    		<result name=\"success\">/"+namespace+"/"+pagepath+"_List.jsp</result>");
		pw.println("    		<result name=\"input\">/"+namespace+"/"+pagepath+"_Set.jsp</result>");
		pw.println("		</action>");
		pw.println("");
		pw.println("		<!-- "+comments+" 删除 -->");
		pw.println("    	<action name=\"delete"+filename.toLowerCase()+"\" class=\""+conversion+"Action\" method=\"delete"+filename+"\">");
		pw.println("    		<result name=\"success\">/"+namespace+"/"+pagepath+"_List.jsp</result>");
		pw.println("		</action>");
		pw.println("");
		pw.println("	</package>");
		pw.println("</struts>");
		pw.println("");

		pw.flush(); 
		xmlfile.close();
	}
	
	/**
	 * 设置sqlMapConfig文件
	 * @param typelist
	 * @param datamap
	 * @throws IOException
	 */
	public void SetSqlXMLFile(Map<String,String> datamap) throws IOException{
		try{
			String tablename = datamap.get("table_name").toString().toLowerCase();
			String filename = (tablename.split("_")[0]).substring(0,1).toUpperCase()+(tablename.split("_")[0]).substring(1,(tablename.split("_")[0]).length()).toLowerCase();
			filename += (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();
			String filepath = System.getProperty("user.dir")+"/WebRoot/WEB-INF/sqlMapConfig.xml";
			Document document = getDocument(filepath);  
		    NodeList nodeList = document.getElementsByTagName("sqlMapConfig");  
			Element sqlMap=document.createElement("sqlMap");
			String resource = filepaths.replace("/src/", "")+"/config/sqlmap/"+filename+".xml";
			Element element = document.getDocumentElement();  
			NodeList nodes = element.getElementsByTagName("sqlMap");   
			//配置重复处理
			for(int i=0;i<nodes.getLength();i++){
				if(resource.equals(((Element)nodes.item(i)).getAttribute("resource"))){
					resource = "";
				}
			}
			if(!"".equals(resource)){
				sqlMap.setAttribute("resource", resource);
				nodeList.item(0).appendChild(sqlMap);  
				modifyFile(document,filepath);
			}
		}catch(Exception e){ 
			e.printStackTrace(); 
		} 
	} 
	
	/**
	 * 设置struts文件
	 * @param typelist
	 * @param datamap
	 * @throws IOException
	 */
	public void SetStrutsXMLFile(Map<String,String> datamap) throws IOException{
		try{
			String tablename = datamap.get("table_name").toString().toLowerCase();
			String filename = (tablename.split("_")[0]).substring(0,1).toUpperCase()+(tablename.split("_")[0]).substring(1,(tablename.split("_")[0]).length()).toLowerCase();
			filename += (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();
			String filepath = System.getProperty("user.dir")+"/src/struts.xml";
			Document document = getDocument(filepath);  
		    NodeList nodeList = document.getElementsByTagName("struts");  
			Element include=document.createElement("include");
			String file = filepaths.replace("/src/", "")+"/config/action/"+filename+"Action.xml";
			Element element = document.getDocumentElement();  
			NodeList nodes = element.getElementsByTagName("sqlMap");  
			//配置重复处理
			for(int i=0;i<nodes.getLength();i++){
				if(file.equals(((Element)nodes.item(i)).getAttribute("file"))){
					file = "";
				}
			}
			if(!"".equals(file)){
				include.setAttribute("file", file);
				nodeList.item(0).appendChild(include);  
				modifyFile(document,filepath);
			}
		}catch(Exception e){ 
			e.printStackTrace(); 
		} 
	} 
	public void SetApplicationContextXMLFile(Map<String,String> datamap) throws IOException{
		PubFunc pubFunc = new PubFunc();
		String comments = pubFunc.ChkNull((String)datamap.get("comments"), "");
		String tablename = datamap.get("table_name").toString().toLowerCase();
		String filename = (tablename.split("_")[0]).substring(0,1).toUpperCase()+(tablename.split("_")[0]).substring(1,(tablename.split("_")[0]).length()).toLowerCase();
		filename += (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();
		String filepath = System.getProperty("user.dir")+filepaths+"/config/bean/applicationContext_"+filepaths.split("/")[5]+".xml";
		File file = new File(System.getProperty("user.dir")+filepaths+"/config/bean"); 
		if(file.mkdirs()){//判断config/bean目录是否存在
			FileWriter xmlfile =new FileWriter(filepath); 
			PrintWriter pw = new PrintWriter(xmlfile); 
			pw.println("<?xml version=\"1.0\" encoding=\"gbk\" ?>"); 
			pw.println("<!--"); 
			pw.println("@description "); 
			pw.println("@author "+author); 
			pw.println("@date "+pubFunc.getYear()+"-"+pubFunc.getMonth()+"-"+pubFunc.getDay()); 
			pw.println("-->"); 
			pw.println("<beans xmlns=\"http://www.springframework.org/schema/beans\""); 
			pw.println("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""); 
			pw.println("	xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd\">"); 
			pw.println(""); 
			pw.println("<!-- =========================================="+comments+"========================================== -->");
			pw.println("	<bean id=\""+this.Conversion(filename)+"DAO\" class=\""+(filepaths.replace("/src/", "")).replace("/", ".")+".dao.implement."+filename+"DAOImpl\" parent=\"nsoftDao\"/>"); 
			pw.println("</beans>"); 
			pw.flush(); 
			xmlfile.close();
		}else{
			Document document = getDocument(filepath);  
		    NodeList nodeList = document.getElementsByTagName("beans");  
			Element bean=document.createElement("bean");
			String beanid = this.Conversion(filename)+"DAO";
			Element element = document.getDocumentElement();  
			NodeList nodes = element.getElementsByTagName("bean");  
			//配置重复处理
			for(int i=0;i<nodes.getLength();i++){
				if(beanid.equals(((Element)nodes.item(i)).getAttribute("id"))){
					beanid = "";
				}
			}
			if(!"".equals(beanid)){
				bean.setAttribute("id", beanid);
				bean.setAttribute("class", (filepaths.replace("/src/", "")).replace("/", ".")+".dao.implement."+filename+"DAOImpl");
				bean.setAttribute("parent", "nsoftDao");
				nodeList.item(0).appendChild(bean);  
				modifyFile(document,filepath);
			}
		}
	}
	
	public void SetApplicationContextXMLFileForService(Map<String,String> datamap){
		String tablename = datamap.get("table_name").toString().toLowerCase();
		String filename = (tablename.split("_")[0]).substring(0,1).toUpperCase()+(tablename.split("_")[0]).substring(1,(tablename.split("_")[0]).length()).toLowerCase();
		filename += (tablename.split("_")[1]).substring(0,1).toUpperCase()+(tablename.split("_")[1]).substring(1,(tablename.split("_")[1]).length()).toLowerCase();
		String filepath = System.getProperty("user.dir")+filepaths+"/config/bean/applicationContext_"+filepaths.split("/")[5]+".xml";
			Document document = getDocument(filepath); 
			
			String beanid = this.Conversion(filename)+"Service";
			NodeList nodeList = document.getElementsByTagName("beans");  
			Element bean=document.createElement("bean");
			bean.setAttribute("id", beanid);
			bean.setAttribute("class", (filepaths.replace("/src/", "")).replace("/", ".")+".service.implement."+filename+"ServiceImpl");
			
			Element property = document.createElement("property");
			property.setAttribute("name", this.Conversion(filename)+"DAO");
			
			Element ref = document.createElement("ref");
			ref.setAttribute("bean", this.Conversion(filename)+"DAO");
			
			
//			配置重复处理
			Element element = document.getDocumentElement();  
			NodeList nodes = element.getElementsByTagName("bean");  
			for(int i=0;i<nodes.getLength();i++){
				if(beanid.equals(((Element)nodes.item(i)).getAttribute("id"))){
					beanid = "";
				}
			}
			if(!"".equals(beanid)){
				property.appendChild(ref);
				bean.appendChild(property);
				nodeList.item(0).appendChild(bean);  
				modifyFile(document,filepath);     
			}		
	}
	
	/**
	 * 获得doc对象  
	 * @param fileName
	 * @return
	 */
	public Document getDocument(String fileName){    
		Document document = null;    
		try{    
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();    
			DocumentBuilder builder = factory.newDocumentBuilder();    
			document = builder.parse(new File(fileName));         
		}catch(Exception e){    
			e.printStackTrace();   
		} 
		return document;
	}
	
	/**
	 * 将改动持久到文件 
	 * @param doc
	 * @param distFileName
	 */
	public void modifyFile(Document doc,String distFileName){    
		try{    
			TransformerFactory tf = TransformerFactory.newInstance();    
			Transformer tfer = tf.newTransformer(); 
			Properties properties = tfer.getOutputProperties(); 
			//设置语言
			properties.setProperty(OutputKeys.ENCODING,"UTF-8"); 
			//缩进开启
			properties.setProperty(OutputKeys.INDENT,"yes");

			DOMSource dsource = new DOMSource(doc);    
			StreamResult sr = new StreamResult(new File(distFileName)); 
			tfer.setOutputProperties(properties); 
			tfer.transform(dsource, sr);

		}catch(Exception e){    
			e.printStackTrace();    
		}        
	} 

	/**
	 * 把单词的头一个字母转换成小写
	 * @param str
	 * @return
	 */
	public String Conversion(String str){
		String str1 = "";
		str1 =(str.substring(0,1)).toLowerCase()+str.substring(1,str.length());
		return str1;
	}
	/**
	 * 把单词的头一个字母转换成大写
	 * @param str
	 * @return
	 */
	public String Uppercase(String str){
		String str1 = "";
		str1 =(str.substring(0,1)).toUpperCase()+str.substring(1,str.length());
		return str1;
	}

	/**
	 * 添加空格用于对齐注释
	 * @param l
	 * @return
	 */
	public String AddSpaces(int l){
		StringBuffer str = new StringBuffer();
		for(int i=0;i<l;i++){
			str.append(" ");
		}
		return str.toString();
	}

}
