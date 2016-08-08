<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>

	<h1>Error Page</h1>
	<p>Application has encountered an error. Ref ${uuid}</p>
	<p style="display:none">

	Failed URL: ${url} Exception: ${exception.message}
	<c:forEach items="${exception.stackTrace}" var="ste">    ${ste} 
    </c:forEach>
    </p>

</body>
</html>