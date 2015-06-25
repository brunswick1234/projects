<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="mission" type="flymetomars.model.Mission" -->
<#-- @ftlvariable name="participants" type="java.util.Set<flymetomars.model.Person>" -->
<#-- @ftlvariable name="invitations" type="java.util.Set<flymetomars.model.Invitation>" -->

<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Fly me to Mars: a mission registration system.</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Fly me to Mars - a mission registration system.">
</head>

<body>
<div id="title_pane">
    <h3>Mission Details Page</h3>
</div>

<div>
<#if errorMsg?? && errorMsg?has_content>
    <li><h4 class="errorMsg">${errorMsg}</h4></li>
<#else>
    <p>Mission details:</p>
    <ul>
        <li>Name: ${mission.name}</li>
        <li>Location: ${mission.location}</li>
        <li>Time: ${mission.time?datetime}</li>
        <li>Captain: <a href="/person/${mission.captain.id}">${mission.captain.firstName} ${mission.captain.lastName}</a></li>
        <li>Description: ${mission.description}</li>
        <li>Participants:</li>
            <ul>
            <#list participants as p>
                <li><a href="/person/${p.id}">${p.firstName} ${p.lastName}</a></li>
            </#list>
            </ul>
        <li>Invitations:</li>
        <ul>
            <#list invitations as i>
                <li><a href="/invitation/${i.id}">Invitation to ${i.recipient.firstName} ${i.recipient.lastName}</a></li>
            </#list>
        </ul>
        <li><p> <a href="/mission/${mission.id}/create_invitation">Create an invitation </a> for this mission now!</p></li>
    </ul>
</#if>

</div>

</body>
</html>