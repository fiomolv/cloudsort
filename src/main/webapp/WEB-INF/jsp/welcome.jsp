<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>


<img src="images/splash.jpg" align="right" style="position:relative;right:70px;">
<h2><!--fmt:message key="welcome"/-->Menu</h2>

<ul>

  <li><a href="<c:url value="/main/status/list"/>">System Status</a></li>
  <li><a href="<c:url value="/main/config/list"/>">Configuration</a></li>
  <li><a href="<c:url value="/main/qualification/list"/>">Qualification</a></li>
</ul>

<p>&nbsp;</p>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
