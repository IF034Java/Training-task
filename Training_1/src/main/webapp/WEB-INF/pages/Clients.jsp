<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>

Products Add
</<div>
<form action="/Training_1">
  Name: <input type="text" name="fname"><br>
  Price: <input type="text" name="price"><br>
  Experation Date: <input type="text" name="edate"><br>
  <input type="submit" value="Submit">
</form> 
</div>
<p>

Products Delete
</<div>
<form action="/Training_1">
  ID: <input type="text" name="id"><br>  
  <input type="submit" value="Submit">
</form> 
</div>
<p>

<c:forEach items="${message}" var="clients">
   	<h3>${clients.id} ${clients.name} ${clients.surname} ${clients.profit}</h3>                 
</c:forEach>
</body>
</html>