<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="text"/>

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="https://unpkg.com/papercss@1.8.1/dist/paper.min.css">
        <title><fmt:message key="title_records"/></title>
    </head>

    <body>
        <header>
            <nav class="border fixed split-nav">
                <div>
                    <h3><a href="catalog"><fmt:message key="library"/></a></h3>
                </div>
                <div>
                    <input id="collapsible1" type="checkbox" name="collapsible1">
                    <button>
                        <label for="collapsible1">
                            <div class="bar1"></div>
                            <div class="bar2"></div>
                            <div class="bar3"></div>
                            <div class="bar4"></div>
                        </label>
                    </button>
                    <div>
                        <ul class="inline">
                            <li><a href="records"><fmt:message key="records"/></a></li>
                            <custom:lang path="/library/records"/>
                            <li>
                                <form action="${pageContext.request.contextPath}/library/logout">
                                    <button class="btn-small btn-danger-outline">
                                        <fmt:message key="logout"/>
                                    </button>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>

        <main>
            <div>
                <div class="row flex-center">
                    <div>
                        <div class="text-center">
                            <h1><p><fmt:message key="records"/></p></h1>
                            <h2>~~~</h2>
                        </div>

                        <c:if test="${not empty requestScope.errorMessage}">
                            <div>
                                <input class="alert-state" id="alert" type="checkbox">
                                <div class="alert alert-danger dismissible">
                                        ${requestScope.errorMessage}
                                    <label class="btn-close" for="alert">X</label>
                                </div>
                            </div>
                        </c:if>

                        <div class="row flex-spaces tabs">
                            <input id="tab1" type="radio" name="tabs" checked>
                            <label for="tab1"><fmt:message key="processing"/></label>

                            <input id="tab2" type="radio" name="tabs">
                            <label for="tab2"><fmt:message key="active"/></label>

                            <div class="content" id="content1">
                                <c:if test="${not empty requestScope.noProcessing}">
                                    <div class="row flex-center">
                                        <h4><p>${requestScope.noProcessing}</p></h4>
                                    </div>
                                </c:if>

                                <c:forEach var="record" items="${requestScope.orders}">
                                    <c:if test="${order.status == 'Processing'}">
                                        <form action="${pageContext.request.contextPath}/library/records" method="get">
                                            <div class="card margin-bottom" style="width: 40rem;">
                                                <div class="card-body">
                                                    <h4 class="card-title"><p>${order.book.name}</p></h4>
                                                    <h5 class="card-subtitle"><p>${order.book.author}</p></h5>
                                                    <h5 class="card-subtitle"><p>${order.book.genre}</p></h5>
                                                    <c:if test="${sessionScope.user.role eq 'User'}">
                                                        <input type="hidden" name="action" value="cancel">
                                                        <input type="hidden" name="bookId" value="${record.book.id}">
                                                        <button class="btn-danger-outline">
                                                            <fmt:message key="cancel_recordr"/>
                                                        </button>
                                                    </c:if>
                                                    <c:if test="${sessionScope.user.role eq 'Librarian'}">
                                                        <p class="card-text">
                                                            <fmt:message key="requests"/> ${order.user.name} "${order.user.login}" ${order.user.surname}
                                                        </p>
                                                        <input type="hidden" name="userLogin" value="${order.user.login}">
                                                        <input type="hidden" name="bookId" value="${order.book.id}">
                                                        <button name="action" value="cancel" class="btn-danger-outline">
                                                            <fmt:message key="cancel_order"/>
                                                        </button>
                                                        <button name="action" value="library_room" class="btn-primary-outline">
                                                            <fmt:message key="give_library_room"/>
                                                        </button>
                                                        <button name="action" value="season_ticket" class="btn-secondary-outline">
                                                            <fmt:message key="give_season_ticket"/>
                                                        </button>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </form>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div>
                                <c:if test="${not empty requestScope.noActive}">
                                    <div>
                                        <h4><p>${requestScope.noActive}</p></h4>
                                    </div>
                                </c:if>

                                <c:forEach var="record" items="${requestScope.records}">
                                    <c:if test="${order.status != 'Processing'}">
                                        <form action="${pageContext.request.contextPath}/library/records" method="get">
                                            <div>
                                                <div class="card-body">
                                                    <h4 class="card-title"><p>
                                                            ${order.book.title}
                                                        <c:if test="${order.status eq 'LibraryRoom'}">
                                                            <span class="badge"><fmt:message key="library_room"/></span>
                                                        </c:if>
                                                        <c:if test="${order.status eq 'SeasonTicket'}">
                                                            <span class="badge secondary"><fmt:message key="season_ticket"/></span>
                                                        </c:if>
                                                    </p></h4>
                                                    <h5 class="card-subtitle"><p>${order.book.author}</p></h5>
                                                    <c:if test="${sessionScope.user.role eq 'User'}">
                                                        <input type="hidden" name="action" value="giveback">
                                                        <input type="hidden" name="bookId" value="${order.book.id}">
                                                        <button class="btn-success-outline">
                                                            <fmt:message key="return_book"/>
                                                        </button>
                                                    </c:if>
                                                    <c:if test="${sessionScope.user.role eq 'Librarian'}">
                                                        <p class="card-text">
                                                                ${order.user.name} "${order.user.login}" ${order.user.surname} <fmt:message key="has_on_hand"/>
                                                        </p>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </form>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </body>

</html>