<%@ include file="/WEB-INF/jsp/includes.jsp" %>

<html>
  <head>
    
  <script src="/cloudsort/js/jquery-1.3.2.min.js"></script>
  <link rel="stylesheet" href="/cloudsort/js/jquery-autocomplete/demo/main.css" type="text/css" />
  <link rel="stylesheet" href="/cloudsort/js/jquery-autocomplete/jquery.autocomplete.css" type="text/css" />
  <script type="text/javascript" src="/cloudsort/js/jquery-autocomplete/lib/jquery.bgiframe.min.js"></script>
  <script type="text/javascript" src="/cloudsort/js/jquery-autocomplete/lib/jquery.dimensions.js"></script>
  <script type="text/javascript" src="/cloudsort/js/jquery-autocomplete/jquery.autocomplete.js"></script>
   <script type="text/javascript">
       $(document).ready(function(){
    var data = [];
      <c:forEach var="category" items="${categories}">
            data.push("${category.name}");</c:forEach>   
          $("#category2").autocomplete(data,{matchContains: true}); 
});
  
     </script>
     
     
     
     
     
<script language="Javascript">
//
// This method Gets URL Parameters (GUP)
//
function gup( name )
{
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var tmpURL = window.location.href;
  var results = regex.exec( tmpURL );
  if( results == null )
    return "";
  else
    return results[1];
}

function radioClicked(){
   document.getElementById('category').value='';
}

function selectChanged(){
   document.getElementById('radio0').checked=false;
   document.getElementById('radio1').checked=false;
   document.getElementById('radio2').checked=false;
   document.getElementById('radio3').checked=false;
   document.getElementById('radio4').checked=false;
   document.getElementById('radio5').checked=false;
}

//
// This method decodes the query parameters that were URL-encoded
//
function decode(strToDecode)
{
  var encoded = strToDecode;
  return unescape(encoded.replace(/\+/g,  " "));
}

</script>
</head>
<body>

<form id="mturk_form" 
name="hitform'
method="POST" 
action="http://www.mturk.com/mturk/externalSubmit">
<input type="hidden" id="assignmentId" name="assignmentId" value="">


<center>

<table border="0">
<tr><td colspan="2">
<h2>Select a category for the product shown below.</h2>
Product: <a href="${product.productUrl}" target="_new">${product.title}</a>



</td></tr>

<tr><td>

<img id="pageFrame" src="${product.imageUrl}"/>

</td><td>


<p>

<c:if test="${tierOneAnswers !=null}">
<p>

Two other workers have previously accepted this HIT but have selected different categories.  The answers these workers
selected are shown below.
If you can verify that one of these answers is correct, please select it.
</p>
  <c:forEach var="tierOneAnswer" items="${tierOneAnswers}">
     <input type="radio" name="category0" value="${tierOneAnswer.categoryCode}"
    onClick="radioClicked()"/>${tierOneAnswer.name}<br/>
  </c:forEach>
</p>

</c:if>




<c:if test="${tierOneAnswers !=null}">
If you feel that neither of the answers from the original workers is correct, look for the correct category 
among these six suggestions, select it.
</c:if>

<c:if test="${tierOneAnswers ==null}">
If you see the correct category among these six suggestions, select it.
</c:if>



<p/>
</p>

<p>

  <% int i=0;%>
  <c:forEach var="suggestion" items="${product.suggestions}">
    <input id="radio<%=i%>" type="radio" name="category" value="${suggestion.categoryCode}"
    onClick="radioClicked()"/>${suggestion.categoryName}<br/>
    <% i++; %>
  </c:forEach>
</p>


Otherwise, start typing a category into the field below and let the auto-complete logic select the best match.
<p/>
<p>
        
    <input id="category2" name="category2" />

<p/>


 

<p>
<input id="submitButton" type="submit" name="Submit" value="Submit">
<p/>
</td>


</tr>
<tr><td height="100%">


</td></tr>
</table>

</center>



</form>
<script language="Javascript">
    document.getElementById('assignmentId').value = gup('assignmentId');

    //
    // Check if the worker is PREVIEWING the HIT or if they've ACCEPTED the HIT
    //
    if (gup('assignmentId') == "ASSIGNMENT_ID_NOT_AVAILABLE")
    {
  // If we're previewing, disable the button and give it a helpful message
  document.getElementById('submitButton').disabled = true;
  document.getElementById('submitButton').value = "You must ACCEPT the HIT before you can submit the results.";
    } else {
        var form = document.getElementById('mturk_form');
        if (document.referrer && ( document.referrer.indexOf('workersandbox') != -1) ) {
            form.action = "http://workersandbox.mturk.com/mturk/externalSubmit";
        }
    }

</script>
</body>
</html>

