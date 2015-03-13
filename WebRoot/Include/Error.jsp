<%@ page contentType="text/html;charset=gbk" isErrorPage="true"%>
<%@ include file="TagLib.jsp"%> 
<HTML>
<HEAD>
<TITLE></TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk">
<link href="<c:url value='/Style/Main.css'/>" rel="stylesheet" type="text/css">
	<script language="javascript" src="<c:url value='/Script/Main.js'/>"></script>
</HEAD>

<body>
<div id="container">    
    <div id="nav"> 
      <div id="nav_sec"> 
	     <div class="nav_fir">错误提示</div>
	  </div>  
    </div>
    <div class="frm_tab">
        <table cellpadding="0">
            <tr>
                <th class="frm_top_left"></th>
                <th class="frm_top_middle"></th>
                <th class="frm_top_right"></th>
            </tr>
            <tr>
                <td class="frm_middle_left"></td>
                <td nowrap="nowrap">
                    <table class="em_tab">
		              	  <tr>
				            <td align="left">&nbsp;错误提示：</td>
				          </tr>
                         <tr>
                        	<td>
                    			&nbsp;&nbsp;<b>${exception}</b>
                    		</td>
                         </tr>
		              	  <tr>
				            <td align="left">&nbsp;详细提示：</td>
				          </tr>
                         <tr>
                        	<td>
                    			 ${exceptionStack }
                    		</td>
                         </tr>
                    </table>
                </td>
                <td class="frm_middle_right"></td>
            </tr>
            <tr>
                <th class="frm_bottom_left"></th>
                <th class="frm_bottom_middle"></th>
                <th class="frm_bottom_right"></th>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
