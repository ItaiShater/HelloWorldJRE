<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

Hello World!

<!-- VULNERABILITY - cookies/session id printed in html -->
<%= session.getId() %>

<p>Cookies:</p>
<table>
	<c:forEach items="${cookie}" var="currentCookie">  
		<tr>
			<td>${currentCookie.key}</td>
			<td>${currentCookie.value}</td>
			<td>${currentCookie.value.name}</td>
			<td>${currentCookie.value.value}</td>	<!-- Cookie values are valid input to Stored XSS -->
		</tr>
	</c:forEach>
</table>

<p>Pages:</p>
<a href="login?user=user&pass=password">login page</a>
<a href="welcome?user=user">welcome page</a>
<a href="encodingsniffing?user=user">Encoding Sniffing page</a>
<a href="logout">logout page</a>

<p>Denial of Service</p>
<a href="dos?type=1&input=abcde">type 1</a>
<a href="dos?type=2&input=5">type 2</a>
<a href="dos?type=3&input=10000">type 3</a>
<a href="dos?type=4&input=aaaaaaaaaaaaaaaaaaaaaaaaaaj">type 4</a>