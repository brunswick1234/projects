<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="invitation" type="flymetomars.model.Invitation" -->
<#-- @ftlvariable name="time" type="java.lang.String" -->

<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Fly me to Mars: a mission registration system.</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Fly me to Mars - a mission registration system.">
</head>

<body>
<div id="title_pane">
    <h3>Invitation Details Page</h3>
</div>

<div>
<#if errorMsg?? && errorMsg?has_content>
    <li><h4 class="errorMsg">${errorMsg}</h4></li>
<#else>
    <p>Mission details:</p>
    <ul>
        <li>Mission Name: <a href="/mission/${invitation.mission.id}"> ${invitation.mission.name} </a></li>
        <li>Creator : <a href="/person/${invitation.creator.id}"> ${invitation.creator.firstName} ${invitation.creator.lastName}</a></li>
        <li>Recipient: <a href="/person/${invitation.recipient.id}"> ${invitation.recipient.firstName} ${invitation.recipient.lastName}</a></li>
        <li>Status: ${invitation.status}</li>
        <li>Last updated: ${time}</li>
    </ul>
    <p> <a href="/invitation/${invitation.id}/edit">Edit </a></p>
    <p> <a href="/invitation/${invitation.id}/send">Send </a></p>

</#if>

</div>

</body>
</html>