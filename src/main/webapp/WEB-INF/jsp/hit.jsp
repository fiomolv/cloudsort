<%@ include file="/WEB-INF/jsp/includes.jsp" %>

<html>
  <head>
    <style type="text/css">
        @import "http://o.aolcdn.com/dojo/1.0/dijit/themes/tundra/tundra.css";
        @import "http://o.aolcdn.com/dojo/1.0/dojo/resources/dojo.css"
    </style>
    <script type="text/javascript" src="http://o.aolcdn.com/dojo/1.0/dojo/dojo.xd.js"
        djConfig="parseOnLoad: true"></script>
    <script type="text/javascript">
       dojo.require("dojo.parser");
       dojo.require("dijit.form.FilteringSelect");
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
<body class="tundra">




<form id="mturk_form" 
name="hitform'
method="POST" 
action="http://www.mturk.com/mturk/externalSubmit">
<input type="hidden" id="assignmentId" name="assignmentId" value="">


<center>

<table border="0">
<tr><td colspan="2">
<h2>Select a category for the product shown below.</h2>
Product: <a href="${product.productUrl} target="_new">${product.title}</a>


</td></tr>

<tr><td>

<img id="pageFrame" src="${product.imageUrl}"/>

</td><td>


<p>
If you see the correct category among these six suggestions, select it.<p/>
</p>

<p>

  <% int i=0;%>
  <c:forEach var="suggestion" items="${product.suggestions}">
    <input id="radio<%=i%>" type="radio" name="category" value="${suggestion.categoryCode}"
    onClick="radioClicked()"/>${suggestion.categoryName}<br/>
    <% i++; %>
  </c:forEach>
</p>

<p>
Otherwise, select from the comprehensive list below.
<p/>
<p>
        
        <select dojoType="dijit.form.FilteringSelect"
          name="category" id="category"
          autocomplete="true"
          onChange="selectChanged()"
          value="0">
          <option value="0"></option>
     
          <c:forEach var="category" items="${categories}">
            <option value="${category.categoryCode}">${category.name}</option>
          </c:forEach>        
     
        </select>        

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

