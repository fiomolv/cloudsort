<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html dir="ltr">
    
    <head>
        <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/dojo/1.3/dijit/themes/tundra/tundra.css">
        <style type="text/css">
            body, html { font-family:helvetica,arial,sans-serif; font-size:90%; }
        </style>
    </head>
    
    <body class="tundra ">
        <input id="stateSelect">
        <p>
            <button onClick="alert(dijit.byId('stateSelect').attr('value'))">
                Get value
            </button>
        </p>
    </body>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/dojo/1.3/dojo/dojo.xd.js"
    djConfig="parseOnLoad: true">
    </script>
    <script type="text/javascript">
        dojo.require("dijit.form.FilteringSelect");
        dojo.require("dojo.data.ItemFileReadStore");
    </script>
    <script type="text/javascript">
        dojo.addOnLoad(function() {
            var stateStore = new dojo.data.ItemFileReadStore({
                url: "states.json"
            });
            var filteringSelect = new dijit.form.FilteringSelect({
                id: "stateSelect",
                name: "state",
                value: "KY",
                store: stateStore,
                queryExpr: "${0}*",
                searchAttr: "name"
            },
            "stateSelect");
        });
    </script>
    <!-- NOTE: the following script tag is not intended for usage in real
    world!! it is part of the CodeGlass and you should just remove it when
    you use the code -->
    <script type="text/javascript">
        dojo.addOnLoad(function() {
            if (window.pub) {
                window.pub();
            }
        });
    </script>

</html>