<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create an account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">

    <form:form method="POST" modelAttribute="userForm" class="form-signin">
        <h2 class="form-signin-heading">Create your account</h2>
        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="username" class="form-control" placeholder="Username"
                            autofocus="true"></form:input>
                <form:errors path="username"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="password" class="form-control" placeholder="Password"></form:input>
                <form:errors path="password"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="passwordConfirm">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="passwordConfirm" class="form-control"
                            placeholder="Confirm your password"></form:input>
                <form:errors path="passwordConfirm"></form:errors>
            </div>
        </spring:bind>
        
         <spring:bind path="securityquestion">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <!--<form:input type="text" path="securityquestion" class="form-control" placeholder="Username"
                            autofocus="true"></form:input>
                <form:errors path="securityquestion"></form:errors>-->
                <select name="securityquestion">
                <option value="What is your Petname" selected>What is your Petname</option>
   				<option value="What is your Mother's Maiden name">What is your Mother's Maiden name"</option>
  				<option value="What is your Native Place">What is your Native Place</option>
 				<option value="What is your Mother tongue">What is your Mother tongue</option>
                </select>
            </div>
        </spring:bind>
        
         <spring:bind path="securityanswer">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="securityanswer" class="form-control"
                            placeholder="Type answer"></form:input>
                <form:errors path="securityanswer"></form:errors>
            </div>
        </spring:bind>
        
         <spring:bind path="Location">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="location" class="form-control" placeholder="Location"
                            autofocus="true"></form:input>
                <form:errors path="location"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
