<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="user" type="flymetomars.model.Person" -->

<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Fly me to Mars: a mission registration system.</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Fly me to Mars - a mission registration system.">
</head>

<body>

<div>
    <#if user??>
        <span><h3>Welcome back: ${user.firstName} ${user.lastName}!</h3></span>
        <p><a href="/logout" id ="logoutLink">Logout</a></p>
    <#else>
        <span><h3>Welcome to Fly Me To Mars!</h3></span>
    </#if>

    <p>What do you want to do today?</p>

	<ul>
        <#if user??>
		    <li>Go to your <a href="/person/${user.id}" id = "personLink">person page.</a></li>
        <#else>
            <li><a href="/login">Login</a> or <a href="/register">register</a>.</li>
        </#if>
		<li>See the list of <a href="/persons" id = "personsLink">persons</a> registered with the system.</li>
		<li>See the list of <a href="/missions" id = "missionsLink">missions</a>.</li>
	</ul>
</div>

</body>
</html>