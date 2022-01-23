<#-- @ftlvariable name="data" type="com.ndambukiconsolidate.BlogEntry" -->
<html>
<head>
    <title>Ricky Journal</title>
    <link rel="stylesheet" href="/static/main.css">
</head>
    <body style="text-align:centre; font-family:sans-serif">
    <h1>RICKY JOURNAL</h1>
    <img src="/static/r_sensei.png" alt="ricky image" width="auto" height="auto">
    <p><i>Powered by NDAMBUKI CONSOLIDATE</i></p>
    <hr>
    <#list entries as item>
    <div>
        <h3>${item.headline}</h3>
        <p>${item.body}</p>
    </div>
    </#list>
<hr>
<div>
    <h3>Add a new journal entry</h3>
    <form action="/submit" method="post">
        <input type="text" name="headline">
        <br>
        <textarea name="body"></textarea>
        <br>
        <input type="submit">
    </form>
</div>
    </body>
</html>
