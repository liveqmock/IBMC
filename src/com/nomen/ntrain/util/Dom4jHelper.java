package com.nomen.ntrain.util;



import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
/**
 * dom4j+xpath解析xml工具类
 * @author 梁桂钊
 * @date 2013-09-01
 */
public class Dom4jHelper {
	
	/**
	 * 创建解析器并加载xml文件
	 * @param xmlpath xml文件路径
	 * @return
	 * @throws Exception
	 */
	private static Document getDocument(String xmlpath) throws Exception{
		// 创建Dom4j解析器
		SAXReader reader = new SAXReader();
		// 加载需要解析的xml文件
		Document document = reader.read(new File(xmlpath));
		return document;
	}
	
	/**
	 * 写入xml文件
	 * @param document xml文件
	 * @param xmlpath xml文件路径
	 * @throws Exception
	 */
	private static void write(Document document,String xmlpath) throws Exception{
		OutputStream os = new FileOutputStream(xmlpath);
		OutputFormat format = OutputFormat.createPrettyPrint();//缩减型格式
		//OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding("utf-8");
		XMLWriter xmlWriter = new XMLWriter(os,format);
		xmlWriter.write(document);
		xmlWriter.close();
	}
	
	/**
	 * 解析Spring的xml文件
	 * @param xmlpath xml文件路径
	 * @return
	 * @throws Exception
	 */
	public static List<Element> parseSpring(String xmlpath) throws Exception{
		Document document = getDocument(xmlpath);
		// 创建命名空间容器
		Map<String,String> map = new HashMap<String,String>();
		// 插入命名空间
		map.put("ns","http://www.springframework.org/schema/beans");
		// 创建beans/bean的查询路径
	    XPath xSubPath = document.createXPath("//ns:beans/ns:bean");
	    // 设置命名空间
	    xSubPath.setNamespaceURIs(map);    
		return (List<Element>) xSubPath.selectNodes(document);
	}
	
	/**
	 * 解析Struts的xml文件
	 * @param xmlpath	xml文件路径
	 * @param nodepath	结点路径(xpath表达式)
	 * @return
	 * @throws Exception 
	 */
	public static List<Element> parseStruts(String xmlpath,String nodepath) throws Exception {
		
		SAXReader reader = new SAXReader();
		reader.setEntityResolver(new MyEntityResolver());
		// 加载需要解析的xml文件
		Document document = reader.read(new File(xmlpath));
		// 解析域空间（DTD）
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
	    nameSpaceMap.put("xmlns", "http://struts.apache.org/dtds/struts-2.0.dtd");   
	    reader.getDocumentFactory().setXPathNamespaceURIs(nameSpaceMap); 
	    // 获取document
	    document = reader.read(xmlpath);

		return document.selectNodes(nodepath);
	}
	
	/**
	 * 获取所需元素所有结点
	 * @param xmlpath xml文件路径
	 * @param nodepath 结点路径(xpath表达式)
	 * @return
	 * @throws Exception
	 */
	public static List<Element> selectNodes(String xmlpath,String nodepath) throws Exception{
		Document document = getDocument(xmlpath);
		return (List<Element>)document.selectNodes(nodepath);	 
	}
	
	/**
	 * 获取所需元素第一个结点
	 * @param xmlpath xml文件路径
	 * @param nodepath 结点路径(xpath表达式)
	 * @return
	 * @throws Exception
	 */
	public static Node selectSingleNode(String xmlpath,String nodepath) throws Exception{
		Document document = getDocument(xmlpath);
		return document.selectSingleNode(nodepath);
	}
	
	/**
	 * 获取所需元素的所有结点值
	 * @param xmlpath xml文件路径
	 * @param nodepath 结点路径(xpath表达式)
	 * @return
	 * @throws Exception
	 */
	public static List<String> selectNodesValue(String xmlpath,String nodepath) throws Exception{
		Document document = getDocument(xmlpath);
		List<Element> dataList = document.selectNodes(nodepath);
		List<String> resultList = new ArrayList<String>();
		for (Element element : dataList) {
			resultList.add(element.getText());
		}
		return resultList;
	}

	/**
	 * 获取所需元素的所有属性值
	 * @param xmlpath xml文件路径
	 * @param nodepath 结点路径(xpath表达式)
	 * @param val 属性值(id,class)
	 * @return
	 * @throws Exception
	 */
	public static List<String> selectNodesByAttribute(String xmlpath,String nodepath,String val) throws Exception{
		List<Element> qList = selectNodes(xmlpath,nodepath);
		List<String> attrList = new ArrayList<String>();
		for (Element e : qList) {
			attrList.add(e.attributeValue(val));
		}	
		return  attrList;	
	}
	
	/**
	 * 获取Spring所需元素的所有属性值
	 * @param xmlpath xml文件路径
	 * @param val 属性值(id,class)
	 * @return
	 * @throws Exception
	 */
	public static List<String> selectSpringNodesByAttribute(String xmlpath,String val) throws Exception{
		List<Element> qList = parseSpring(xmlpath);
		List<String> attrList = new ArrayList<String>();
		for (Element e : qList) {
			attrList.add(e.attributeValue(val));
		}	
		return  attrList;	
	}
	
	/**
	 * 获取Struts所需元素的所有属性值
	 * @param xmlpath	xml文件路径
	 * @param nodepath	结点路径(xpath表达式)
	 * @param val		属性值(id,class)
	 * @return
	 * @throws Exception
	 */
	public static List<String> selectStrutsNodesByAttribute(String xmlpath,String nodepath,String val) throws Exception{
		List<Element> qList = parseStruts(xmlpath,nodepath);
		List<String> attrList = new ArrayList<String>();
		for (Element e : qList) {
			attrList.add(e.attributeValue(val));
		}	
		return  attrList;	
	}
	
	/**
	 * 更新XML内容
	 * @param vCont	内容值
	 * @param xmlpath  xml文件路径
	 * @param nodepath 结点路径(xpath表达式)
	 * @throws Exception
	 */
	public static void update(String vCont,String xmlpath,String nodepath) throws Exception{
		Document document = getDocument(xmlpath);
		List<Element> list = (List<Element>)document.selectNodes(nodepath);
		for (Element e : list) {
			e.setText(vCont);
		}
		write(document, xmlpath);
	}
	
	/**
	 * 插入XML内容
	 * @param eCont 子元素
	 * @param vCont 内容值
	 * @param xmlpath xml文件路径
	 * @param nodepath 结点路径(xpath表达式)
	 * @throws Exception
	 */
	public static void insert(String eCont,String vCont,String xmlpath,String nodepath) throws Exception{
		Document document = getDocument(xmlpath);
		List<Element> list = (List<Element>)document.selectNodes(nodepath);
		for (Element e : list) {
			e.addElement(eCont).setText(vCont);			
		}
		write(document, xmlpath);
	}
	
	/**
	 * 删除XML内容
	 * @param xmlpath xml文件路径
	 * @param nodepath 结点路径(xpath表达式)
	 * @throws Exception
	 */
	public static void delete(String xmlpath,String nodepath) throws Exception{
		Document document = getDocument(xmlpath);
		List<Element> list = (List<Element>)document.selectNodes(nodepath);
		for (Element e : list) {
			e.getParent().remove(e);
		}
		write(document, xmlpath);
	}
	
	/**
	 * 创建XML文件
	 * @param eCont 子元素
	 * @param xmlpath xml文件路径
	 * @throws Exception
	 */
	public static void create(String eCont,String xmlpath) throws Exception{
		Document document = DocumentHelper.createDocument();
		document.addElement(eCont);
		write(document, xmlpath);
	}
}

/**
 * dom4j解析dtd文件时使用
 * @author Administrator
 *
 */
@SuppressWarnings("all")
class MyEntityResolver implements EntityResolver {
	
	public InputSource resolveEntity(String publicId, String systemId) {
		return new InputSource(new StringBufferInputStream(""));
    }
}
