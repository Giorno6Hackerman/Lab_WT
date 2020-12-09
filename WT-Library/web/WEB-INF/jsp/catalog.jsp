%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="text"/>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="https://unpkg.com/papercss@1.8.1/dist/paper.min.css">
        <title><fmt:message key="title_catalog"/></title>
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
                            <custom:lang path="/library/catalog"/>
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
                <div>
                    <div>
                        <form>
                            <div class="text-center">
                                <h1><p><fmt:message key="catalog"/></p></h1>
                                <h2>~~~</h2>
                            </div>

                            <form action="${pageContext.request.contextPath}/library/catalog" method="get">
                                <c:if test="${sessionScope.user.role eq 'User'}">
                                    <div class="row flex-center form-group">
                                        <input type="text" placeholder="Search" name="search">
                                    </div>
                                </c:if>
                                <c:if test="${sessionScope.user.role eq 'Librarian'}">
                                    <div class="row flex-center">
                                        <button name="action" value="add">
                                            <fmt:message key="add_book"/>
                                        </button>
                                    </div>
                                </c:if>
                            </form>

                            <c:if test="${not empty requestScope.errorMessage}">
                                <div>
                                    <input class="alert-state" id="alert" type="checkbox">
                                    <div class="alert alert-danger dismissible">
                                            ${requestScope.errorMessage}
                                        <label class="btn-close" for="alert">X</label>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty requestScope.noElements}">
                                <div>
                                    <h4>${requestScope.noElements}</h4>
                                </div>
                            </c:if>

                            <div class="row flex-spaces">
                                <c:forEach var="book" items="${requestScope.books}">
                                <c:if test="${sessionScope.user.role eq 'User'}">
                                <form action="${pageContext.request.contextPath}/library/records" method="get">
                                    </c:if>
                                    <c:if test="${sessionScope.user.role eq 'Librarian'}">
                                    <form action="${pageContext.request.contextPath}/library/catalog" method="get">
                                        </c:if>
                                        <div>
                                            <div class="card-body">
                                                <h4 class="card-title"><p>${book.name}</p></h4>
                                                <h5 class="card-subtitle"><p>${book.author}</p></h5>
                                                <h5 class="card-subtitle"><p>${book.genre}</p></h5>
                                                <input type="hidden" name="bookId" value="${book.id}">
                                                <c:if test="${sessionScope.user.role eq 'User'}">
                                                    <c:if test="${book.count gt 0}">
                                                        <button><fmt:message key="to_order"/></button>
                                                    </c:if>
                                                    <c:if test="${book.count eq 0}">
                                                        <label class="paper-btn" for="modal-1">
                                                            <fmt:message key="to_order"/>
                                                        </label>
                                                        <input class="modal-state" id="modal-1" type="checkbox">
                                                        <div class="modal">
                                                            <label class="modal-bg" for="modal-1"></label>
                                                            <div>
                                                                <label class="btn-close" for="modal-1">X</label>
                                                                <h4 class="modal-title"><fmt:message key="oops"/></h4>
                                                                <p class="modal-text"><fmt:message key="oops_message"/></p>
                                                                <label for="modal-1" class="paper-btn">
                                                                    <fmt:message key="ok"/>
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${sessionScope.user.role eq 'Librarian'}">
                                                    <button class="btn-danger-outline" name="action" value="delete">
                                                        <fmt:message key="delete_book"/>
                                                    </button>
                                                    <button name="action" value="edit">
                                                        <fmt:message key="edit_bok"/>
                                                    </button>
                                                </c:if>
                                            </div>
                                        </div>
                                    </form>
                                    </c:forEach>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>
    </body>

</html>