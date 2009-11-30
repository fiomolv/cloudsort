<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<!-- Main content begin -->
<div id="fullwidthcontent">

<c:if test="${not empty param.login_error}">
    <font color="red">
        Your login attempt was not successful, try again.<br/>
        Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.<P>
    </font>
</c:if>

<form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">


<table width="100%">
<tr>
<td width="100"><B>Username:</B></td><td><%-- <form:input path="username" size="30" maxlength="80"/>--%><input type="text" name="j_username"/></td>
</tr>
<tr>
<td width="100"><B>Password:</B></td><td><%--<form:password path="password" size="30" maxlength="80"/>--%><input type="password" name="j_password"/></td>
</tr>

<tr><td>&nbsp;</td><td style="color:gray"><input type='checkbox' name='_spring_security_remember_me'/> Remember me on this computer.</td></tr>


<tr>
<td colspan="2">
<p class="submit"><input type="submit" value="Login"/>
</td>
</tr>
</table>
</form>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
