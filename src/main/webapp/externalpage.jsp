<!-- This file needs to be hosted on an external server.  -->
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
<form id="mturk_form" method="POST" action="http://www.mturk.com/mturk/externalSubmit">
<input type="hidden" id="assignmentId" name="assignmentId" value="">
<table border="0" height="100%" width="100%">
<tr><td>
<h2>Select a category for the product shown below.</h2>
<p>

  <div id="categoryStore" dojoType="dojo.data.ItemFileReadStore" jsId="categoryStore" url="/cloudsort/category?type=<%=request.getParameter("type")%>">
        </div>
        <input dojoType="dijit.form.FilteringSelect" value="" store="categoryStore"
        searchAttr="name" name="category" id="category">

<input id="submitButton" type="submit" name="Submit" value="Submit">
<p>
</td></tr>
<tr><td height="100%">

<img id="pageFrame" src="<%=request.getParameter("type")%>"/>

</td></tr>
</table>
</form>
<script language="Javascript">
    document.getElementById('pageFrame').src = decode(gup('url'));
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
