<%--
    Document   : index
    Created on : Jan 24, 2012, 6:01:31 AM
    Author     : blecherl
    This is the login JSP for the online chat application
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@page import="utils.*" %>
    <%@ page import="constants.Constants" %>
    <head>
        <link rel="stylesheet" href="../signup/signup.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
<!--        Link the Bootstrap (from twitter) CSS framework in order to use its classes-->
<!--        Link jQuery JavaScript library in order to use the $ (jQuery) method-->
<!--        <script src="script/jquery-2.0.3.min.js"></script>-->
<!--        and\or any other scripts you might need to operate the JSP file behind the scene once it arrives to the client-->
    </head>
    <body>
        <% String usernameFromSession = SessionUtils.getUsername(request);%>
        <% String usernameFromParameter = request.getParameter(Constants.USERNAME) != null ? request.getParameter(Constants.USERNAME) : "";%>
        <% if (usernameFromSession == null) {%>
        <h1>Welcome to battleship game</h1>
        <h4>Please select a nickname:</h4>
        <form method="GET" action="/login">
            <input id="nameInput" name="username"/>
            <input type="submit" value="Login"/>
        </form>
        <% Object errorMessage = request.getAttribute(Constants.USER_NAME_ERROR);%>
        <% if (errorMessage != null) {%>
        <span class="bg-danger" style="color:red;"><%=errorMessage%></span>
        <% } %>
        <% } else {%>
        <h1>Welcome back, <%=usernameFromSession%></h1>
        <a href="../chatroom/chatroom.html">Click here to enter the chat room</a>
        <br/>
        <a href="login?logout=true" id="logout">logout</a>
        <% }%>
    </body>
</html>