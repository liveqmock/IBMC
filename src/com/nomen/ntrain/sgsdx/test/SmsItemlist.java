package com.nomen.ntrain.sgsdx.test;

/**
 * 注意事项：
	1.	不要在测试用例的构造函数中做初始化   答案是重载测试用例的setUp()方法进行初始化。
	2.	不要假定测试用例中测试的执行次序     好的习惯是保持测试之间的独立性，使得它们在任何次序下执行的结果都是相同的。
	3.	测试要避免人工干预   经验二讲的是不同的测试要避免相关性，而经验三讲的其实就是测试要避免自相关。
	4.	在子类中调用父类的setUp()和tearDown()
	5.	不要硬性规定数据文件的路径
	6.	把测试的代码和被测的代码放在同样的目录下
	7.	正确命名测试
	8.	书写测试时要考虑地区和国家设置
	9.	利用Junit的自动异常处理书写简洁的测试代码   事实上在Junit中使用try-catch来捕获异常是没有必要的，Junit会自动捕获异常。那些没有被捕获的异常就被当成错误处理。
	10.	充分利用Junit的assert/fail方法    
	?	assertSame()用来测试两个引用是否指向同一个对象
	?	assertEquals()用来测试两个对象是否相等
	11.	确保测试代码与时间无关
	12.	使用文档生成器做测试文档。
 */

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.nomen.ntrain.sgsdx.bean.SmsItemlistBean;
import com.nomen.ntrain.sgsdx.service.SmsItemlistService;

public class SmsItemlist extends TestCase{
	private SmsItemlistService smsItemlistService;
	
	/**定义你需要测试的类及用到的变量*****************************/  
    /*******************************************************/      
    public SmsItemlist(String name){        
        super(name);//创建子类         
    }   
    /**用setUp进行初始化操作*/  
    protected void setUp() throws Exception {   
        super.setUp();   
        String[] path={
        		"E:/JSP/message/WebRoot/WEB-INF/TapplicationContext.xml",
				"E:/JSP//message/src/com/nomen/message/sms/config/bean/applicationContext_sms.xml"
        };
		ApplicationContext context= new FileSystemXmlApplicationContext(path);
		this.smsItemlistService =(SmsItemlistService)context.getBean("smsItemlistService");
    }   
    /**用tearDown来销毁所占用的资源*/
    protected void tearDown() throws Exception {   
        super.tearDown();   
        //System.gc();   
    } 
    
    /**写一个测试方法断言期望的结果*
     * @throws Exception */  
    public void testUserInReg() throws Exception {
    	System.out.println("------------系统内--------------");
//    	Map map = new HashMap();
//    	map.put("rownum", "60");
//    	map.put("no_send", "true");
//    	this.smsItemlistService.findSmsItemlistList(map);
    	SmsItemlistBean bean = new SmsItemlistBean();
    	bean.setId("2");
    	bean.setUsername("e1e");
    	//this.smsItemlistService.saveSmsItemlist(bean);
//    	this.smsItemlistService.findSmsItemlistBeanById("2");
    	System.out.println("测试成功");
    }
    /**再写一个测试方法断言期望的结果**/  
    public void testBodySame() {           
    }   
    /**suite()方法，使用反射动态的创建一个包含所有的testXxxx方法的测试套件**/  
    public static TestSuite suite()  {   
        return new TestSuite(SmsItemlist.class);   
    }   
    /****写一个main()运行测试*****************/  
    public static void main(String args[]) { 
    	//TestRunner.run(MyJunit.class);
        junit.textui.TestRunner.run(suite());//以文本运行器的方式方便的        
        //junit.swingui.TestRunner.run(JunitB.class);   
    }   

}
