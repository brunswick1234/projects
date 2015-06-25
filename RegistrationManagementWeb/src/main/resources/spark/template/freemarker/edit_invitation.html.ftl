<#-- @ftlvariable name="status" type="java.lang.String" -->
<#-- @ftlvariable name="lastUpdate" type="java.lang.String" -->
<#-- @ftlvariable name="recipientEmail" type="java.lang.String" -->
<#-- @ftlvariable name="timeValue" type="java.lang.String" -->
<#-- @ftlvariable name="invitation" type="flymetomars.model.Invitation" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->


<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Fly me to Mars: a mission registration system.</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Fly me to Mars: create mission.">
</head>

<body>

<div id="title_pane">
    <h3>Invitation Creation</h3>
</div>

<p>${errorMsg!""}</p>

<div>
    <p>* Fields are required.</p>
</div>
<form name="create_event" action="/invitation/${invitation.id}/edit" method="POST">
    <div id="admin_left_pane" class="fieldset_without_border">
        <div><p>Invitation Details</p></div>
        <ol>
            <li>
                <label for="recipientEmail" class="bold">Recipient Email:*</label>
                <input id="recipientEmail" name="recipientEmail" type="text" value="${invitation.recipient.email!""}">
            </li>
            <li>
                <label for="status" class="bold">Status(created/send/accepted/declined):*</label>
                <input id="status" name="status" type="text" value="${invitation.status!""}">
            </li>
            <li>
                <label for="lastUpdate" class="bold">Last Updated(dd/mm/yyyy HH AM/PM):*</label>
                <input id="lastUpdate" name="lastUpdate" type="text" value="${lastUpdate!""}">
            </li>
        </ol>
    </div>

<#if errorMsg?? && errorMsg?has_content>
    <div id="error">
        <p>Error: ${errorMsg}</p>
    </div>
</#if>
    <div id="buttonwrapper">
        <button type="submit" id="saveInvitation">Save</button>
        <a href="/">Cancel</a>
    </div>
</form>

</body>