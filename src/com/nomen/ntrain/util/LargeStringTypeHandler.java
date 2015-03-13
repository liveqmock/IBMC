package com.nomen.ntrain.util;
  
import java.sql.PreparedStatement;   
import java.sql.SQLException;   
  
import com.ibatis.sqlmap.engine.type.StringTypeHandler;   
  
/**  
 * For using LargeStringTypeHandler, you must add a line in SqlMapConfig.xml as following:<br/>  
 * &lt;typeHandler javaType="java.lang.String" callback="com.sinosoft.component.ibatis.typehandler.LargeStringTypeHandler"/&gt;  
 * @author airlink  
 * @see http://blog.csdn.net/ruanee/archive/2006/03/24/637213.aspx  
 * @see http://www.tomjamescn.cn/?p=63  
 * need add sqlmap.xml 
 */  
public class LargeStringTypeHandler extends StringTypeHandler {   
  
      
    public void setParameter(PreparedStatement ps, int i, Object parameter, String jdbcType)   
    throws SQLException {   
//        String s = (String)parameter;   
        super.setParameter(ps, i, parameter, jdbcType);   
        /**
        System.out.println("START======================"+s.length());
        if (s.length() < 667) {   
            System.out.println("SMALL======================"+s.length());
            //assume that all characters are chinese characters.   
            super.setParameter(ps, i, parameter, jdbcType);   
        }else{   
            //use setCharacterStream can insert more characters.   
            System.out.println("BIG======================"+s.length());
            ps.setCharacterStream(i, new StringReader(s), s.length());   
        }   **/
    }   
      
}  
