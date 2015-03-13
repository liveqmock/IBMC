<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<fmt:setBundle basename="globalMessages" var="bms"/>
<c:forEach items="${actionMessages}" var="message">${message}</c:forEach>
<%@ include file="NoCahe.jsp"%>