<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="user" type="flymetomars.model.Person" -->
<#-- @ftlvariable name="person" type="flymetomars.model.Person" -->
<#-- @ftlvariable name="missions" type="java.util.Set<flymetomars.model.Mission>" -->
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
<#if person??>
    <h3>${person.firstName} ${person.lastName}'s Page</h3>
<#else>
    <h3>Person page</h3>
    <p>Error: ${errorMsg}</p>
</#if>
</div>

<div>

<#if user??>
    <span><h3>Welcome back: ${user.firstName} ${user.lastName}!</h3></span>
    <p><a href="/logout">Logout</a></p>
<#else>
    <span><h3>Welcome to Fly Me To Mars!</h3></span>
    <p>You can <a href="/login">Login</a> or <a href="/register">register</a>.</p>
</#if>
<#if person??>
    <#if invitations?? && invitations?has_content>
        <p>Invitations.</p>
        <ul>
            <#list invitations as invitation>
                <li>${invitation.mission.name}, ${invitation.mission.time?datetime}, ${invitation.status}</li>
            </#list>
        </ul>
    <#else>
        <p>It appears there isn't any invitation yet.</p>
    </#if>

    <#if missions?? && missions?has_content>
        <p>Missions.</p>
        <ul>
            <#list missions as mission>
                <li><a href="/mission/${mission.id}">${mission.name}</a>, with captain ${mission.captain.firstName} ${mission.captain.lastName}, at ${mission.time?datetime}, ${mission.location}.</li>
            </#list>
        </ul>
    <#else>
        <p>It appears there isn't any mission yet.</p>
    </#if>
</#if>
</div>

</body>
</html>