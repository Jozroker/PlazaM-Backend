<%--
  Created by IntelliJ IDEA.
  User: Illamurialis
  Date: 26.05.2021
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PlazaM</title>
    <link rel="icon" href="${contextPath}/resources/img/logo.ico" type="image/icon type">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.css"
          integrity="sha512-uZTwaYYhJLFXaXYm1jdNiH6JZ1wLCTVnarJza7iZ1OKQmvi6prtk85NMvicoSobylP5K4FCdGEc4vk1AYT8b9Q=="
          crossorigin="anonymous"/>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Baumans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${contextPath}/resources/scss/header.css">
</head>
<body>
<header id="header">
    <div class="nav" id="left-side">
        <div id="loader">
            <div class="bar"></div>
        </div>
        <div class="links">
            <div id="logo">
                <a href="${contextPath}/home">
                    <svg xmlns="http://www.w3.org/2000/svg" width="59" height="77" viewBox="0 -4 51 81">
                        <g>
                            <g class="logo-top">
                                <path fill="#D7D7D7" fill-rule="evenodd"
                                      d="M51 25.35366058C51 11.3605957 39.57383728 0 25.5 0 11.42616653 0 0 11.3605957 0 25.35366058s11.42616653 25.35366059 25.5 25.35366059c14.07383728 0 25.5-11.3605957 25.5-25.35366059zM25.5 5.63414574c2.81476593 0 5.09999847 2.27211952 5.09999847 5.0707302 0 2.7986145-2.28523254 5.07073212-5.09999847 5.07073212s-5.09999847-2.27211761-5.09999847-5.07073211c0-2.7986107 2.28523254-5.07073021 5.09999847-5.07073021zm-7.74444199 12.95853614c0-2.7986145-2.28523254-5.07073211-5.10000228-5.07073211-2.81476594 0-5.09999848 2.27211761-5.09999848 5.07073211S9.8407898 23.663414 12.65555573 23.663414c2.81476974 0 5.10000228-2.27211761 5.10000228-5.07073212zm25.68888474 0c0-2.7986145-2.28523254-5.07073211-5.09999848-5.07073211-2.81476593 0-5.09999847 2.27211761-5.09999847 5.07073211s2.28523254 5.07073212 5.09999847 5.07073212c2.81476594 0 5.09999848-2.27211761 5.09999848-5.07073212zm-13.06381226 6.8603859l-4.80832672-4.80832672-4.7807312 4.7807312 4.80832672 4.80832672 4.7807312-4.7807312zm-12.62507248 6.66156769c0-2.7986145-2.28523254-5.07073212-5.10000228-5.07073212-2.81476594 0-5.09999848 2.27211762-5.09999848 5.07073212 0 2.79861068 2.28523254 5.07073211 5.09999848 5.07073211 2.81476974 0 5.10000228-2.27212143 5.10000228-5.07073211zm20.58888626-5.07073212c2.81476594 0 5.09999848 2.27211762 5.09999848 5.07073212 0 2.79861068-2.28523254 5.07073211-5.09999848 5.07073211-2.81476593 0-5.09999847-2.27212143-5.09999847-5.07073211 0-2.7986145 2.28523254-5.07073212 5.09999847-5.07073212zM25.5 34.93170547c2.81476593 0 5.09999847 2.27211762 5.09999847 5.07073212 0 2.79861069-2.28523254 5.0707283-5.09999847 5.0707283s-5.09999847-2.27211761-5.09999847-5.0707283c0-2.7986145 2.28523254-5.07073212 5.09999847-5.07073212z"></path>
                            </g>
                            <g class="logo-bottom">
                                <path fill="#D7D7D7" fill-rule="evenodd"
                                      d="M17.37777778 64.9804878h16.24444444V72c0 2.75957512-2.24042488 5-5 5h-6.24444444c-2.75957512 0-5-2.24042488-5-5v-7.0195122z"></path>
                                <path fill="#D7D7D7" fill-rule="evenodd"
                                      d="M17.50243902 62h15.88401084c.82921868 0 1.50243903.67322035 1.50243903 1.50243902 0 .82921868-.67322035 1.50243903-1.50243903 1.50243903H17.50243902C16.67322035 65.00487805 16 64.3316577 16 63.50243902 16 62.67322035 16.67322035 62 17.50243902 62z"></path>
                            </g>
                            <g class="logo-middle">
                                <rect width=".76" height="26" x="5.8" y="39" fill="#D7D7D7"></rect>
                                <rect width=".76" height="26" x="33" y="39" fill="#D7D7D7"></rect>
                                <rect width="1" height="15" x="25" y="49" fill="#D7D7D7"></rect>
                            </g>
                        </g>
                    </svg>
                </a>
            </div>
            <div>
                <div class="link" id="schedule-link">
                    <div>
                        <a href="${contextPath}/schedule"><spring:message code="navbar.schedule"/></a>
                    </div>
                    <div class="underline"></div>
                </div>
                <div class="link" id="movies-link">
                    <div>
                        <a href="${contextPath}/movies"><spring:message code="navbar.movies"/></a>
                    </div>
                    <div class="underline"></div>
                </div>
                <div class="link" id="coming-soon-link">
                    <div>
                        <%--                        <a href="">--%>
                        <spring:message code="navbar.coming.soon"/>
                        <%--                        </a>--%>
                    </div>
                    <div class="underline"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="nav" id="right-side">
        <div class="links">
            <div class="link">
                <div id="search">
                    <div>
                        <div>
                            <input id="search-line" type="text" placeholder="<spring:message code="navbar.search"/>">
                        </div>
                        <div class="underline"></div>
                    </div>
                    <div id="search-icon"></div>
                </div>
                <div id="search-result">
                    <div class="scroll"></div>
                </div>
            </div>
            <div class="link">
                <div id="header-location">
                    <div id="current-cinema">
                        <div>
                            <a identifier="${currentCinema.id}">${currentCinema.city} (${currentCinema.name})</a>
                        </div>
                        <div class="triangle"></div>
                    </div>
                    <div id="countries"></div>
                </div>
                <div class="background"></div>
            </div>
            <div class="link">
                <div id="local">
                    <div id="current-local">
                        <c:if test="${currentLanguage.name().equals('ENG')}">
                            <div identifier="ENG" class="flag uk-flag"></div>
                            <div><a>Eng</a></div>
                        </c:if>
                        <c:if test="${currentLanguage.name().equals('UKR')}">
                            <div identifier="UKR" class="flag ua-flag"></div>
                            <div><a>Укр</a></div>
                        </c:if>
                        <c:if test="${currentLanguage.name().equals('POL')}">
                            <div identifier="POL" class="flag pl-flag"></div>
                            <div><a>Pol</a></div>
                        </c:if>
                        <div class="triangle"></div>
                    </div>
                    <div id="locals">
                        <ul>
                            <li>
                                <a language="uk">
                                    <div class="flag ua-flag"></div>
                                    <div>Укр</div>
                                </a>
                            </li>
                            <div class="line-between"></div>
                            <li>
                                <a language="en">
                                    <div class="flag uk-flag"></div>
                                    <div>Eng</div>
                                </a>
                            </li>
                            <div class="line-between"></div>
                            <li>
                                <a language="pl">
                                    <div class="flag pl-flag"></div>
                                    <div>Pol</div>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="background"></div>
            </div>
            <div class="link">
                <div id="menu-btn">
                    <sec:authorize access="isAuthenticated()">
                        <div identifier="${user.id}">
                                <%--                        <a href="#">--%>
                                <%--                            Zadyraichuk--%>
                            <c:if test="${user.useRealName}">
                                ${user.firstName} ${user.lastName}
                            </c:if>
                            <c:if test="${!user.useRealName}">
                                ${user.username}
                            </c:if>
                                <%--                        </a>--%>
                        </div>
                        <div class="triangle"></div>
                    </sec:authorize>
                    <sec:authorize access="!isAuthenticated()">
                        <div>
                            <a href="/authorize">
                                <spring:message code="navbar.sign.in"/>
                            </a>
                        </div>
                    </sec:authorize>
                </div>
            </div>
        </div>
    </div>
    <sec:authorize access="isAuthenticated()">
    <div id="menu" class="scroll">
        <div id="user">
            <div>
                <img src="data:image/${user.picture.format};base64,${user.picture.pictureString}" alt="">
            </div>
        </div>
        <nav id="menu-top">
            <div class="menu-item favour-btn">
                <a href="${contextPath}/movies?userId=${user.id}&type=favourites"><spring:message code="navbar.favourites"/></a>
            </div>
            <div class="line-between"></div>
            <div class="menu-item messages-btn">
                    <%--                    <a href="#">--%>
                <spring:message code="navbar.messages"/>
                    <%--                    </a>--%>
            </div>
            <div class="line-between"></div>
            <div class="menu-item tickets-btn">
                <spring:message code="navbar.tickets"/>
            </div>
            <div class="line-between"></div>
            <div class="menu-item comments-btn">
                <a href="${contextPath}/user/comments"><spring:message code="comments.title.default"/></a>
            </div>
            <div class="line-between"></div>
            <div class="menu-item viewed-btn">
                <a href="${contextPath}/movies?userId=${user.id}&type=viewed"><spring:message code="navbar.viewed"/></a>
            </div>
            <div class="line-between"></div>
            <div class="menu-item waitlist-btn">
                <a href="${contextPath}/movies?userId=${user.id}&type=waited"><spring:message code="navbar.wait.list"/></a>
            </div>
            <sec:authorize access="hasRole('ADMIN')">
                <div class="line-between"></div>
                <div class="menu-item users-btn">
                    <a href="${contextPath}/admin/users"><spring:message code="navbar.users"/></a>
                </div>
            </sec:authorize>
        </nav>
        <div id="menu-bottom">
            <div class="menu-item settings-btn">
                <spring:message code="navbar.settings"/>
            </div>
            <div class="line-between"></div>
            <div class="menu-item sign-out">
                <a href="${contextPath}/logout"><spring:message code="navbar.sign.out"/></a>
            </div>
        </div>

        <div id="messages">
            <div class="title">
                <div class="triangle"></div>
            </div>
            <div class="messages scroll">
                <c:if test="${user.messages.size() != 0}">
                    <c:forEach var="message" items="${user.messages}" varStatus="loop">
                        <div class="message" identifier="${message.id}">
                            <div class="message-content">
                                    ${message.text}
                            </div>
                            <div class="cross"></div>
                        </div>
                        <c:if test="${!loop.last}">
                            <div class="line-between"></div>
                        </c:if>
                    </c:forEach>
                </c:if>
            </div>
        </div>
        <div id="settings">
            <div class="title">
                <div class="triangle"></div>
            </div>
            <div class="settings-menu">
                <div class="menu-item personal-btn"><spring:message code="navbar.settings.personal"/></div>
                <div class="line-between"></div>
                <div class="menu-item site-btn"><spring:message code="navbar.settings.site"/></div>
                <div class="line-between"></div>
                <div class="menu-item security-btn"><spring:message code="navbar.settings.security"/></div>
            </div>
        </div>
    </div>
    <div id="settings-bar">
            <spring:message code="list.not.selected.default" var="notSelected"/>
        <div class="settings-page" id="personal-settings">
            <div class="scroll">
                <div class="content">
                    <div>
                        <div class="avatar">
                            <div class="title"><spring:message code="user.image"/></div>
                            <div class="image">
                                <img alt="" src="data:image/${user.picture.format};base64,${user.picture.pictureString}">
                                <div class="change"><spring:message code="button.change.title"/></div>
                                <input accept="image/jpeg,image/png,image/gif" id="change-avatar" style="display: none"
                                       type="file">
                            </div>
                        </div>
                        <div class="fields">
                            <div class="first-column">
                                <div class="username">
                                    <div class="title"><spring:message code="username.title.default"/></div>
                                    <div class="field">
                                        <input placeholder="" type="text" value="${user.username}" id="username">
                                    </div>
                                </div>
                                <div class="firstname">
                                    <div class="title"><spring:message code="first.name.title.default"/></div>
                                    <div class="field">
                                        <input placeholder="<spring:message code="list.not.selected.default"/>" type="text" value="${user.firstName}"
                                               id="firstName">
                                    </div>
                                </div>
                                <div class="lastname">
                                    <div class="title"><spring:message code="last.name.title.default"/></div>
                                    <div class="field">
                                        <input placeholder="<spring:message code="list.not.selected.default"/>" type="text" value="${user.lastName}"
                                               id="lastName">
                                    </div>
                                </div>
                            </div>
                            <div class="second-column">
                                <div class="sex">
                                    <div class="title"><spring:message code="user.sex"/></div>
                                    <ul class="list">
                                        <li class="selected">
                                            <div>
                                                <c:if test="${user.sex == null || user == null}">
                                                    <spring:message code="list.not.selected.default"/>
                                                </c:if>
                                                <c:if test="${user.sex.name().equals('MALE')}">
                                                    <spring:message code="user.sex.male"/>
                                                </c:if>
                                                <c:if test="${user.sex.name().equals('FEMALE')}">
                                                    <spring:message code="user.sex.female"/>
                                                </c:if>
                                            </div>
                                            <div class="triangle"></div>
                                        </li>
                                        <li identifier="NULL"><spring:message code="list.not.selected.default"/></li>
                                        <li identifier="MALE"><spring:message code="user.sex.male"/></li>
                                        <li identifier="FEMALE"><spring:message code="user.sex.female"/></li>
                                    </ul>
                                </div>
                                <div class="country">
                                    <div class="title">Country</div>
                                    <ul class="list">
                                        <li class="selected">
                                            <div><spring:message code="list.not.selected.default"/></div>
                                            <div class="triangle"></div>
                                        </li>
                                        <li identifier="NULL"><spring:message code="list.not.selected.default"/></li>
                                        <li identifier="UKRAINE"><spring:message code="ukraine.default"/></li>
                                        <li identifier="POLAND"><spring:message code="poland.default"/></li>
                                        <li identifier="UNITED_KINGDOM"><spring:message code="united.kingdom.default"/></li>
                                    </ul>
                                </div>
                                <div class="home-city">
                                    <div class="title"><spring:message code="city.title.default"/></div>
                                    <div class="field">
                                        <input placeholder="<spring:message code="list.not.selected.default"/>" type="text" id="city">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="about">
                        <div class="title"><spring:message code="user.about.me"/></div>
                        <textarea class="field" placeholder="<spring:message code="text.title.default"/>" spellcheck="false" type="text"></textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="settings-page" id="site-settings">
            <div class="scroll">
                <div class="content">
                    <div class="movie-list">
                        <div class="title"><spring:message code="settings.movie.list"/></div>
                        <c:if test="${user.hide18PlusMovies}">
                        <div class="checked field">
                            </c:if>
                            <c:if test="${!user.hide18PlusMovies}">
                            <div class="field">
                                </c:if>
                                <div class="checkbox"><spring:message code="settings.hide.eighteen.plus.movies"/></div>
                            </div>
                        </div>
                        <div class="personalisation">
                            <div class="title"><spring:message code="settings.personalization"/></div>
                            <c:if test="${user.useLightTheme}">
                            <div class="dark-theme field">
                                </c:if>
                                <c:if test="${!user.useLightTheme}">
                                <div class="dark-theme field checked">
                                    </c:if>
                                    <div class="radiobox"><spring:message code="settings.use.dark.theme"/></div>
                                </div>
                                <c:if test="${user.useLightTheme}">
                                <div class="light-theme field checked">
                                    </c:if>
                                    <c:if test="${!user.useLightTheme}">
                                    <div class="light-theme field">
                                        </c:if>
                                        <div class="radiobox"><spring:message code="settings.use.light.theme"/></div>
                                    </div>
                                </div>
                                <!--                    <div class="nickname-display">-->
                                <!--                        <div class="title">Display Nickname</div>-->
                                <!--                        <div class="light-theme field">-->
                                <!--                            <div class="radiobox">use light theme</div>-->
                                <!--                        </div>-->
                                <!--                        <div class="dark-theme field checked">-->
                                <!--                            <div class="radiobox">use dark theme</div>-->
                                <!--                        </div>-->
                                <!--                    </div>-->
                                <div class="language-settings">
                                    <div class="title"><spring:message code="language.title.default"/></div>
                                    <ul class="list">
                                        <li class="selected">
                                            <div>
                                                <c:if test="${currentLanguage.name().equals('ENG')}">
                                                    <spring:message code="english.lang.title.default"/>
                                                </c:if>
                                                <c:if test="${currentLanguage.name().equals('UKR')}">
                                                    <spring:message code="ukrainian.lang.title.default"/>
                                                </c:if>
                                                <c:if test="${currentLanguage.name().equals('POL')}">
                                                    <spring:message code="polish.lang.title.default"/>
                                                </c:if>
                                            </div>
                                            <div class="triangle"></div>
                                        </li>
                                        <li>
                                            <div><spring:message code="ukrainian.lang.title.default"/></div>
                                        </li>
                                        <li>
                                            <div><spring:message code="polish.lang.title.default"/></div>
                                        </li>
                                        <li>
                                            <div><spring:message code="english.lang.title.default"/></div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="settings-page" id="security">
                        <div class="scroll">
                            <div class="content">
                                <div class="password">
                                    <div class="title"><span><spring:message code="settings.change.password"/></span><span class="error empty">*
                                <spring:message code="error.empty.fields"/></span>
                                        <span class="error weak">*<spring:message code="error.weak.password"/></span><span class="error not-confirmed">*<spring:message
                                                code="error.not.confirmed"/></span>
                                    </div>
                                    <div class="fields">
                                        <div class="field">
                                            <input id="old-password" placeholder="<spring:message code="settings.old.password"/>" type="password">
                                        </div>
                                        <div class="field">
                                            <input id="new-password" placeholder="<spring:message code="settings.new.password"/>"
                                                   regex="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[_!#$%^&*-]).{8,}$"
                                                   type="password">
                                        </div>
                                        <div class="field">
                                            <input id="new-password-confirm" placeholder="<spring:message code="settings.confirm.new.password"/>" type="password">
                                        </div>
                                        <div class="button">
                                            <div id="save-password"><spring:message code="button.save"/></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="email">
                                    <div class="title"><span><spring:message code="settings.change.email"/></span><span class="error empty">*<spring:message
                                            code="error.empty.field"/></span>
                                        <span class="error not-email">*<spring:message code="error.not.email.syntax"/></span></div>
                                    <div class="fields">
                                        <div><span class="title-2"><spring:message code="email.title.default"/>:</span><span class="space">_</span><span
                                                class="value">${user.email}</span>
                                        </div>
                                        <div><span class="title-2"><spring:message code="settings.new.email"/>:</span></div>
                                        <div class="field">
                                            <input id="new-email" placeholder="<spring:message code="email.default"/>" regex="^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$"
                                                   type="email">
                                        </div>
                                        <div class="button">
                                            <div id="save-email"><spring:message code="button.save"/></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="phone">
                                    <div class="title"><span>
                            <c:if test="${user.phone == null}">
                                <span><spring:message code="settings.add.phone.number"/>
                            </c:if>
                            <c:if test="${user.phone != null}">
                                <spring:message code="settings.change.phone.number"/>
                            </c:if>
                            </span><span class="error empty">*<spring:message
                                            code="error.empty.field"/></span>
                            <span class="error not-phone">*<spring:message code="error.not.phone.syntax"/></span></div>
                                    <div class="fields">
                                        <div class="current-phone"><span class="title-2"><spring:message code="phone.number.my"/>:</span><span
                                                class="space">_</span>
                                                <%--                                    <span class="value">+38 (050) 669-37-93</span></div>--%>
                                            <span class="value">${user.phone}</span></div>
                                        <div><span class="title-2"><spring:message code="settings.new.phone.number"/>:</span></div>
                                        <div class="phone-field">
                                            <div class="phone-code">
                                                <ul class="list">
                                                    <li class="selected">
                                                        <div>+38</div>
                                                        <div class="triangle"></div>
                                                    </li>
                                                    <li>+38</li>
                                                    <li>+44</li>
                                                    <li>+49</li>
                                                </ul>
                                            </div>
                                            <div class="field">
                                                <input id="new-phone" maxlength="15" placeholder="<spring:message code="phone.number"/>"
                                                       regex="^\([0-9][0-9][0-9]\) [0-9][0-9][0-9]\-[0-9][0-9]\-[0-9][0-9]$"
                                                       type="text">
                                            </div>
                                        </div>
                                        <div class="button">
                                            <c:if test="${user.phone == null}">
                                                <div id="save-phone"><spring:message code="button.add"/></div>
                                            </c:if>
                                            <c:if test="${user.phone != null}">
                                                <div id="save-phone"><spring:message code="button.change"/></div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="save-btn">
                        <div class="button"><spring:message code="button.save.title"/></div>
                    </div>
                </div>
                <div id="tickets">
                    <div class="page">
                            <%--                <div class="scroll"></div>--%>
                    </div>
                </div>
                </sec:authorize>
                <div id="background"></div>
</header>
</body>
<%--<sec:authorize access="hasRole('ADMIN')">--%>
<%--    <script>--%>
<%--        let isAdmin = true;--%>
<%--    </script>--%>
<%--</sec:authorize>--%>
<script>
    <c:if test="${user != null}">
    let userId = '${user.id}';
    let userIsLogged = true;
    let ticketDate, ticketMonth, ticketYear, ticketHour, ticketMinute;
    <c:if test="${user.tickets.size() != 0}">
    let tickets = [];
    let ticket;
    <c:forEach var="ticket" items="${user.tickets}">
    ticket = [];
    ticketDate = '${ticket.date.dayOfMonth}';
    ticketMonth = '${ticket.date.monthValue}';
    ticketYear = '${ticket.date.year}';
    ticketHour = '${ticket.seance.startSeance.hour}';
    ticketMinute = '${ticket.seance.startSeance.minute}';
    ticketDate = parseInt(ticketDate) < 10 ? '0' + ticketDate : ticketDate;
    ticketMonth = parseInt(ticketMonth) < 10 ? '0' + ticketMonth : ticketMonth;
    ticketHour = parseInt(ticketHour) < 10 ? '0' + ticketHour : ticketHour;
    ticketMinute = parseInt(ticketMinute) < 10 ? '0' + ticketMinute : ticketMinute;
    ticket.push('${ticket.id}');
    ticket.push(ticketDate + "." + ticketMonth + "." + ticketYear);
    ticket.push(ticketHour + ":" + ticketMinute);
    ticket.push('${ticket.seance.hall.technology.name()}'.slice(1));
    ticket.push(${ticket.seance.ticketPrice + ticket.placeAllowance});
    ticket.push(${ticket.placeRow});
    ticket.push(${ticket.placeSeat});
    ticket.push('${ticket.id}');
    ticket.push('${ticket.seance.movie.widePicture.pictureString}');
    ticket.push('${ticket.seance.movie.name}');
    ticket.push('${ticket.seance.movie.surname}');
    tickets.push(ticket);
    </c:forEach>
    </c:if>
    </c:if>

    <%--let cinemas = ${cinemasForHeader};--%>
</script>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"--%>
<%--        integrity="sha512-bLT0Qm9VnAYZDflyKcBaQ2gg0hSYNQrJ8RilYldYQ1FxQYoCLtUjuuRuZo+fjqhx/qtq/1itJ0C2ejDxltZVFg=="--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"--%>
<%--        integrity="sha512-0QbL0ph8Tc8g5bLhfVzSqxe9GERORsKhIn1IrpxDAgUsbBGz/V7iSav2zzW325XGd1OMLdL4UiqRJj702IeqnQ=="--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.js"--%>
<%--        integrity="sha512-AS9rZZDdb+y4W2lcmkNGwf4swm6607XJYpNST1mkNBUfBBka8btA6mgRmhoFQ9Umy8Nj/fg5444+SglLHbowuA=="--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script crossorigin="anonymous"--%>
<%--        integrity="sha512-sR3EKGp4SG8zs7B0MEUxDeq8rw9wsuGVYNfbbO/GLCJ59LBE4baEfQBVsP2Y/h2n8M19YV1mujFANO1yA3ko7Q=="--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/5.0.5/jquery.inputmask.min.js"></script>--%>
<%--<script crossorigin="anonymous"--%>
<%--        integrity="sha512-XtmMtDEcNz2j7ekrtHvOVR4iwwaD6o/FUJe6+Zq+HgcCsk3kj4uSQQR8weQ2QVj1o0Pk6PwYLohm206ZzNfubg=="--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>--%>
<%--<script crossorigin="anonymous"--%>
<%--        src="https://cdn.jsdelivr.net/npm/jsbarcode@3.11.4/dist/barcodes/JsBarcode.code128.min.js"></script>--%>
<%--<script src="${contextPath}/resources/js/header.js"></script>--%>
</html>
