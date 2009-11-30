<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2>Products</h2>


<table>
  <tr>
  <thead>
    <th>ID</th><th>OID</th><th nowrap>Created</th><th nowrap>Modified</th><th nowrap>Category Code</th>
  </thead>
  </tr>
  <c:forEach var="product" items="${products}">
    <tr>
      <td>${product.id}</td>
      <td>${product.oid}</td>
      <td>${product.createdDate}</td>
      <td>${product.modifiedDate}</td>
      <td>${product.categoryCode}</td>
    </tr>
  </c:forEach>
</table>

<br/>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
