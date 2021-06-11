<%--
  Created by IntelliJ IDEA.
  User: Illamurialis
  Date: 08.06.2021
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><spring:message code="navbar.users"/></title>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          rel="stylesheet"/>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.css"
          integrity="sha512-uZTwaYYhJLFXaXYm1jdNiH6JZ1wLCTVnarJza7iZ1OKQmvi6prtk85NMvicoSobylP5K4FCdGEc4vk1AYT8b9Q=="
          rel="stylesheet"/>
    <link href="${contextPath}/resources/scss/header.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/footer.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/pages.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/users.css" rel="stylesheet">
</head>
<body>
<div id="header-container"></div>
<img alt="" id="back" src="${contextPath}/resources/img/jpg/wide/hall.jpg">
<div id="filter">
    <div class="scroll">
        <div class="content">
            <div class="title"><spring:message code="filter.title.default"/></div>
            <div class="subtitle"><spring:message code="users.role.title"/></div>
            <div class="underline"></div>
            <div class="roles">
                <div identifier="ADMIN" class="role"><spring:message code="users.role.admin"/></div>
                <div identifier="WORKER" class="role"><spring:message code="users.role.worker"/></div>
                <div identifier="USER" class="role"><spring:message code="users.role.user"/></div>
            </div>
            <div class="subtitle"><spring:message code="country.title.default"/></div>
            <div class="underline"></div>
            <div class="countries">
                <div identifier="UNITED_KINGDOM" class="country"><spring:message code="united.kingdom.default"/></div>
                <div identifier="UKRAINE" class="country"><spring:message code="ukraine.default"/></div>
                <div identifier="POLAND" class="country"><spring:message code="poland.default"/></div>
            </div>
            <div class="subtitle"><spring:message code="users.ban.status.title"/></div>
            <div class="underline"></div>
            <div class="ban-statuses">
                <div identifier="true" class="ban-status"><spring:message code="users.ban.status.banned"/></div>
                <div identifier="false" class="ban-status"><spring:message code="users.ban.status.not.banned"/></div>
            </div>
            <div class="button">
                <div class="apply-btn"><spring:message code="button.apply.title"/></div>
            </div>
        </div>
    </div>
    <div class="arrow">
        <div class="triangle triangle-wide"></div>
    </div>
</div>
<div class="users-page-container">
    <div class="top">
        <div class="top-bar">
            <div class="title"><spring:message code="navbar.users"/></div>
            <div class="search">
                <div id="user-search">
                    <div>
                        <div>
                            <input id="user-search-line" placeholder="<spring:message code="users.search.placeholder"/>" type="text">
                        </div>
                        <div class="underline"></div>
                    </div>
                    <div id="user-search-icon"></div>
                </div>
            </div>
        </div>
        <div class="underline"></div>
        <div class="sort">
            <div class="title"><spring:message code="sort.by.title.default"/></div>
            <div class="categories">
                <div identifier="username" class="category selected"><spring:message code="username.title.default"/></div>
                <!--                todo on first anyone are selected and line is hide-->
                <!--                todo create reverse order button-->
                <!--                todo for reported and banned hide real name sorting-->
                <div identifier="realname" class="category"><spring:message code="sort.by.real.name"/></div>
                <!--                <div class="category">Country</div>-->
            </div>
            <div class="underline"></div>
        </div>
    </div>
    <div id="users">
        <div class="navbar">
            <div identifier="ALL" class="nav-item selected" id="all-users">
                <div><spring:message code="users.all.title"/></div>
                <div class="underline"></div>
            </div>
            <div identifier="REPORTED" class="nav-item" id="reported-users">
                <div><spring:message code="users.reported.title"/></div>
                <div class="underline"></div>
            </div>
            <div identifier="BANNED" class="nav-item" id="banned-users">
                <div><spring:message code="users.banned.title"/></div>
                <div class="underline"></div>
            </div>
        </div>
        <div class="users">
            <div class="users-container">
                <div class="all-users">
                    <c:forEach var="user" items="${allUsers}">
                        <div class="user" identifier="${user.id}">
                            <div class="user-left-side">
                                <img alt="" src="data:image/${user.picture.format};base64,${user.picture.pictureString}">
                                <c:if test="${user.banned}">
                                    <div class="button unban-button selectable">
                                        <div class="text"><spring:message code="button.unban.title"/></div>
                                    </div>
                                </c:if>
                                <c:if test="${!user.banned}">
                                    <div class="ban-button selectable">
                                        <div class="text"><spring:message code="button.ban.title"/></div>
                                        <div class="selection">
                                            <div identifier="1" class="ban">1 <spring:message code="users.ban.day.title"/></div>
                                            <div identifier="7" class="ban">1 <spring:message code="users.ban.week.title"/></div>
                                            <div identifier="30" class="ban">1 <spring:message code="users.ban.month.title"/></div>
                                            <div identifier="365" class="ban">1 <spring:message code="users.ban.year.title"/></div>
                                            <div identifier="forever" class="ban"><spring:message code="users.ban.forever.title"/></div>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                            <div class="user-right-side">
                                <div class="fields">
                                    <div class="field username">
                                        <div class="title-2"><spring:message code="username.title.default"/></div>
                                        <div class="value">${user.username}</div>
                                    </div>
                                    <div class="field email">
                                        <div class="title-2"><spring:message code="email.title.default"/></div>
                                        <div class="value">${user.email}</div>
                                    </div>
                                    <div class="field first-name">
                                        <div class="title-2"><spring:message code="first.name.title.default"/></div>
                                        <div class="value">${user.firstName}</div>
                                    </div>
                                    <div class="field last-name">
                                        <div class="title-2"><spring:message code="last.name.title.default"/></div>
                                        <div class="value">${user.lastName}</div>
                                    </div>
                                    <div class="field role">
                                        <div class="title-2"><spring:message code="users.role.title"/></div>
                                        <ul class="list">
                                            <li class="selected-role">
                                                <c:if test="${user.role.name().equals('ADMIN')}">
                                                    <div identifier="ADMIN"><spring:message code="users.role.admin.title"/></div>
                                                </c:if>
                                                <c:if test="${user.role.name().equals('WORKER')}">
                                                    <div identifier="WORKER"><spring:message code="users.role.worker.title"/></div>
                                                </c:if>
                                                <c:if test="${user.role.name().equals('USER')}">
                                                    <div identifier="USER"><spring:message code="users.role.user.title"/></div>
                                                </c:if>
                                                <div class="triangle"></div>
                                            </li>
                                            <li identifier="ADMIN"><spring:message code="users.role.admin.title"/></li>
                                            <li identifier="WORKER"><spring:message code="users.role.worker.title"/></li>
                                            <li identifier="USER"><spring:message code="users.role.user.title"/></li>
                                        </ul>
                                    </div>
                                    <div class="field phone">
                                        <div class="title-2"><spring:message code="phone.title.default"/></div>
                                        <div class="value">${user.phone}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="reported-users">
                    <c:forEach var="user" items="${reportedUsers}">
                        <div class="user" identifier="${user.user.id}">
                            <div class="user-left-side">
                                <img alt="" src="data:image/${user.user.picture.format};base64,${user.user.picture.pictureString}">
                                <div class="button ban-button selectable">
                                    <div class="text"><spring:message code="button.ban.title"/></div>
                                    <div class="selection">
                                        <div identifier="1" class="ban">1 <spring:message code="users.ban.day.title"/></div>
                                        <div identifier="7" class="ban">1 <spring:message code="users.ban.week.title"/></div>
                                        <div identifier="30" class="ban">1 <spring:message code="users.ban.month.title"/></div>
                                        <div identifier="365" class="ban">1 <spring:message code="users.ban.year.title"/></div>
                                        <div identifier="forever" class="ban"><spring:message code="users.ban.forever.title"/></div>
                                    </div>
                                </div>
                                <div class="button skip-button">
                                    <div class="text"><spring:message code="button.skip.title"/></div>
                                    <!--                                    todo if 3 days left auto skip-->
                                </div>
                            </div>
                            <div class="user-right-side">
                                <div class="fields">
                                    <div class="field username">
                                        <div class="title-2"><spring:message code="username.title.default"/></div>
                                        <div class="value">${user.user.username}</div>
                                    </div>
                                    <div class="field comment" identifier="${user.id}">
                                        <div class="title-2"><spring:message code="comment.title.default"/></div>
                                        <div class="value">${user.text}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="banned-users">
                    <c:forEach var="user" items="${bannedUsers}">
                        <div class="user" identifier="${user.id}">
                            <div class="user-left-side">
                                <img alt="" src="data:image/${user.picture.format};base64,${user.picture.pictureString}">
                                <div class="button unban-button selectable">
                                    <div class="text"><spring:message code="button.unban.title"/></div>
                                    <!--                                todo remove user if he come in 1 year ago-->
                                </div>
                            </div>
                            <div class="user-right-side">
                                <div class="fields">
                                    <div class="field username">
                                        <div class="title-2"><spring:message code="username.title.default"/></div>
                                        <div class="value">${user.username}</div>
                                    </div>
                                    <div class="field email">
                                        <div class="title-2"><spring:message code="email.title.default"/></div>
                                        <div class="value">${user.email}</div>
                                    </div>
                                    <div class="field phone">
                                        <div class="title-2"><spring:message code="phone.title.default"/></div>
                                        <div class="value">${user.phone}</div>
                                    </div>
                                    <div class="field banned-to-date">
                                        <div class="title-2"><spring:message code="users.banned.to.title"/></div>
                                        <c:if test="${user.bannedTo == null}">
                                            <div class="value"><spring:message code="users.ban.forever.title"/></div>
                                        </c:if>
                                        <c:if test="${user.bannedTo != null}">
                                            <div class="value"><c:if test="${user.bannedTo.dayOfMonth < 10}">0</c:if>${user.bannedTo.dayOfMonth}.<c:if test="${user.bannedTo.monthValue
                                                    < 10}">0</c:if>${user.bannedTo.monthValue}.${user.bannedTo.year}</div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="curtain"></div>
    </div>
    <div id="pages"></div>
</div>
<div id="footer-container"></div>
</body>
<script>
    <sec:authorize access="hasRole('ADMIN')">
    let isAdmin = true;
    </sec:authorize>

    let allUsersLastPage = parseInt(${allUsersPages});
    let reportedUsersLastPage = parseInt(${reportedUsersPages});
    let bannedUsersLastPage = parseInt(${bannedUsersPages});
    let lastPage = allUsersLastPage;
    let ukraine = '<spring:message code="ukraine.default"/>';
    let unitedKingdom = '<spring:message code="united.kingdom.default"/>';
    let poland = '<spring:message code="poland.default"/>';
    let createMovieValue = '<spring:message code="button.create.movie.title"/>';

    let banValue = '<spring:message code="button.ban.title"/>';
    let unbanValue = '<spring:message code="button.unban.title"/>';
    let dayValue = '<spring:message code="users.ban.day.title"/>';
    let weekValue = '<spring:message code="users.ban.week.title"/>';
    let monthValue = '<spring:message code="users.ban.month.title"/>';
    let yearValue = '<spring:message code="users.ban.year.title"/>';
    let foreverValue = '<spring:message code="users.ban.forever.title"/>';
    let usernameValue = '<spring:message code="username.title.default"/>';
    let firstNameValue = '<spring:message code="first.name.title.default"/>';
    let lastNameValue = '<spring:message code="last.name.title.default"/>';
    let emailValue = '<spring:message code="email.title.default"/>';
    let phoneValue = '<spring:message code="phone.title.default"/>';
    let roleValue = '<spring:message code="users.role.title"/>';
    let bannedToValue = '<spring:message code="users.banned.to.title"/>';
    let banStatusValue = '<spring:message code="users.ban.status.title"/>';
    let adminValue = '<spring:message code="users.role.admin.title"/>';
    let workerValue = '<spring:message code="users.role.worker.title"/>';
    let userValue = '<spring:message code="users.role.user.title"/>';
    let skipValue = '<spring:message code="button.skip.title"/>';
    let commentValue = '<spring:message code="comment.title.default"/>';
</script>
<script crossorigin="anonymous"
        integrity="sha512-bLT0Qm9VnAYZDflyKcBaQ2gg0hSYNQrJ8RilYldYQ1FxQYoCLtUjuuRuZo+fjqhx/qtq/1itJ0C2ejDxltZVFg=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script crossorigin="anonymous"
        integrity="sha512-0QbL0ph8Tc8g5bLhfVzSqxe9GERORsKhIn1IrpxDAgUsbBGz/V7iSav2zzW325XGd1OMLdL4UiqRJj702IeqnQ=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script crossorigin="anonymous"
        integrity="sha512-AS9rZZDdb+y4W2lcmkNGwf4swm6607XJYpNST1mkNBUfBBka8btA6mgRmhoFQ9Umy8Nj/fg5444+SglLHbowuA=="
        src="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.js"></script>
<script async
        crossorigin="anonymous"
        defer
        integrity="sha512-sR3EKGp4SG8zs7B0MEUxDeq8rw9wsuGVYNfbbO/GLCJ59LBE4baEfQBVsP2Y/h2n8M19YV1mujFANO1yA3ko7Q=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/5.0.5/jquery.inputmask.min.js"></script>
<script async
        crossorigin="anonymous" defer
        src="https://cdn.jsdelivr.net/npm/jsbarcode@3.11.4/dist/barcodes/JsBarcode.code128.min.js"></script>
<script src="${contextPath}/resources/js/users.js"></script>
</html>
