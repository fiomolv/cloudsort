<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2>Configuration</h2>


<table>
  <tr>
  <thead>
    <th>Name</th><th nowrap>Value</th>
  </thead>
  </tr>
  <c:forEach var="config" items="${configs}">
    <tr>
      <td>
      <a href="/cloudsort/main/config/edit?configName=${config.name}">${config.name}</td>
      <td>${config.value}</td>
    </tr>
  </c:forEach>
</table>

<br/>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
