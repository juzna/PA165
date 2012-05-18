<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Homepage</title>
</head>
<body>
<h1>Welcome</h1>

<p>
	User <c:out value="${user}"/>
</p>

Or login here
<a href="<c:out value="${loginUrl}" />">Login dude</a>

<h1>Cards</h1>
<table>
	<c:forEach var="card" items="${cards}">
		<tr>
			<td>${card.gaeKey}</td>
			<td>${card.owner}</td>
			<td>${card.created}</td>
		</tr>
	</c:forEach>
</table>

</body>
</html>
