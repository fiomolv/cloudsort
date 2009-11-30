<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2>Qualifications</h2>


<table>
  <tr>
  <thead>
    <th>Category</th><th nowrap>General ID</th><th nowrap>General Score</th><th nowrap>Trusted ID</th><th nowrap>Trusted Score</th>
  </thead>
  </tr>
  <c:forEach var="qualification" items="${qualifications}">
    <tr>
      <td>
      <a href="/cloudsort/main/qualification/edit?categoryId=${qualification.categoryId}">${qualification.name}</td>
      <td>${qualification.qualTypeIdGeneral}</td>
      <td>${qualification.qualTypeScoreGeneral}</td>
      <td>${qualification.qualTypeIdTrusted}</td>
      <td>${qualification.qualTypeScoreTrusted}</td>
    </tr>
  </c:forEach>
</table>

<br/>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
