<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="text"/>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title><fmt:message key="title_500"/></title>
    </head>
    <body>
        <div>
            <div>
                <h1><p><fmt:message key="library"/></p></h1>
                <h2>~~~</h2>
                <h3><p><fmt:message key="error_500"/></p></h3>
                <h4><p><fmt:message key="error_500_message"/></p></h4>
            </div>
        </div>
    </body>
</html>