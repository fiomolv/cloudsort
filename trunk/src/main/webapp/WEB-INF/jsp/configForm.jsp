<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>




<h2>Configuration</h2>

<form:form modelAttribute="config">


	<table>



<tr>
  <td>
  Name: <b>${config.name}</b><form:errors path="name" cssClass="errors"/>

          <form:hidden path="id" />
          <form:hidden path="name" />
   
  </td>
</tr>



<tr><td>Value:<br/><form:input path="value"/></td></tr>


		<tr>
            <td colspan="2" class="submit">
            <input type="submit" value="Submit" />
            </td>
		</tr>
		
	</table>
</form:form>

<br />

<p>
<a href="<c:url value="/main/config/list"/>">Back</a>
</p>



<%@ include file="/WEB-INF/jsp/footer.jsp"%>

