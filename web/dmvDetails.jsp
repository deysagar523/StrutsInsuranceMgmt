<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${DmvList}" var="dmv">
   ${dmv.driverName}
   ${dmv.driverId}
</c:forEach>