<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>




<h2>Qualification</h2>

<form:form modelAttribute="qualification">


	<table>



<tr>
  <td>
  Category: <b>${qualification.name}</b><form:errors path="name" cssClass="errors"/>

          <form:hidden path="id" />
          <form:hidden path="name" />
   
  </td>
</tr>



<tr><td>General ID:<br/><form:input path="qualTypeIdGeneral"/></td></tr>
<tr><td>General Score:<br/><form:input path="qualTypeScoreGeneral"/></td></tr>
<tr><td>Trusted ID:<br/><form:input path="qualTypeIdTrusted"/></td></tr>
<tr><td>Trusted ID:<br/><form:input path="qualTypeScoreTrusted"/></td></tr>


		<tr>
            <td colspan="2" class="submit">
            <input type="submit" value="Submit" />
            </td>
		</tr>
		
	</table>
</form:form>

<br />

<p>
<a href="<c:url value="/main/qualification/list"/>">Back</a>
</p>



<%@ include file="/WEB-INF/jsp/footer.jsp"%>

