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
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PlazaM</title>
    <link rel="icon" href="${contextPath}/resources/img/png/logo.png" type="image/icon type">
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
                                      d="M51 25.35366058C51 11.3605957 39.57383728 0 25.5 0 11.42616653 0 0 11.3605957 0 25.35366058s11.42616653 25.35366059 25.5 25.35366059c14.07383728 0 25.5-11.3605957 25.5-25.35366059zM25.5 5.63414574c2.81476593 0 5.09999847 2.27211952 5.09999847 5.0707302 0 2.7986145-2.28523254 5.07073212-5.09999847 5.07073212s-5.09999847-2.27211761-5.09999847-5.07073211c0-2.7986107 2.28523254-5.07073021 5.09999847-5.07073021zm-7.74444199 12.95853614c0-2.7986145-2.28523254-5.07073211-5.10000228-5.07073211-2.81476594 0-5.09999848 2.27211761-5.09999848 5.07073211S9.8407898 23.663414 12.65555573 23.663414c2.81476974 0 5.10000228-2.27211761 5.10000228-5.07073212zm25.68888474 0c0-2.7986145-2.28523254-5.07073211-5.09999848-5.07073211-2.81476593 0-5.09999847 2.27211761-5.09999847 5.07073211s2.28523254 5.07073212 5.09999847 5.07073212c2.81476594 0 5.09999848-2.27211761 5.09999848-5.07073212zm-13.06381226 6.8603859l-4.80832672-4.80832672-4.7807312 4.7807312 4.80832672 4.80832672 4.7807312-4.7807312zm-12.62507248 6.66156769c0-2.7986145-2.28523254-5.07073212-5.10000228-5.07073212-2.81476594 0-5.09999848 2.27211762-5.09999848 5.07073212 0 2.79861068 2.28523254 5.07073211 5.09999848 5.07073211 2.81476974 0 5.10000228-2.27212143 5.10000228-5.07073211zm20.58888626-5.07073212c2.81476594 0 5.09999848 2.27211762 5.09999848 5.07073212 0 2.79861068-2.28523254 5.07073211-5.09999848 5.07073211-2.81476593 0-5.09999847-2.27212143-5.09999847-5.07073211 0-2.7986145 2.28523254-5.07073212 5.09999847-5.07073212zM25.5 34.93170547c2.81476593 0 5.09999847 2.27211762 5.09999847 5.07073212 0 2.79861069-2.28523254 5.0707283-5.09999847 5.0707283s-5.09999847-2.27211761-5.09999847-5.0707283c0-2.7986145 2.28523254-5.07073212 5.09999847-5.07073212z"/>
                            </g>
                            <g class="logo-bottom">
                                <path fill="#D7D7D7" fill-rule="evenodd"
                                      d="M17.37777778 64.9804878h16.24444444V72c0 2.75957512-2.24042488 5-5 5h-6.24444444c-2.75957512 0-5-2.24042488-5-5v-7.0195122z"/>
                                <path fill="#D7D7D7" fill-rule="evenodd"
                                      d="M17.50243902 62h15.88401084c.82921868 0 1.50243903.67322035 1.50243903 1.50243902 0 .82921868-.67322035 1.50243903-1.50243903 1.50243903H17.50243902C16.67322035 65.00487805 16 64.3316577 16 63.50243902 16 62.67322035 16.67322035 62 17.50243902 62z"/>
                            </g>
                            <g class="logo-middle">
                                <rect width=".76" height="26" x="5.8" y="39" fill="#D7D7D7"/>
                                <rect width=".76" height="26" x="33" y="39" fill="#D7D7D7"/>
                                <rect width="1" height="15" x="25" y="49" fill="#D7D7D7"/>
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
                    <!--                    todo search result-->
                </div>
            </div>
            <div class="link">
                <div id="header-location">
                    <%--                    <div id="current-cinema">--%>
                    <%--                        <div>--%>
                    <%--                            <a href="#">Lviv (Forum)</a>--%>
                    <%--                        </div>--%>
                    <%--                        <div class="triangle"></div>--%>
                    <%--                    </div>--%>
                    <%--                    <div id="countries">--%>
                    <%--                        <ul>--%>
                    <%--                            <li>--%>
                    <%--                                <div class="city">--%>
                    <%--                                    <div>--%>
                    <%--                                        <a href="#">Ukraine</a>--%>
                    <%--                                    </div>--%>
                    <%--                                    <div>--%>
                    <%--                                        <div class="triangle"></div>--%>
                    <%--                                    </div>--%>
                    <%--                                </div>--%>
                    <%--                                <div class="cinema-list">--%>
                    <%--                                    <div class="cinema-back">--%>
                    <%--                                        <div class="triangle"></div>--%>
                    <%--                                    </div>--%>
                    <%--                                    <div class="scroll">--%>
                    <%--                                        <ul>--%>
                    <%--                                            <li>--%>
                    <%--                                                <a href="#">Lviv (Forum)</a>--%>
                    <%--                                            </li>--%>
                    <%--                                            <div class="line-between"></div>--%>
                    <%--                                            <li>--%>
                    <%--                                                <a href="#">Lviv (Viktoria Gardens)</a>--%>
                    <%--                                            </li>--%>
                    <%--                                        </ul>--%>
                    <%--                                    </div>--%>
                    <%--                                </div>--%>
                    <%--                            </li>--%>
                    <%--                            <div class="line-between city-line"></div>--%>
                    <%--                            <li>--%>
                    <%--                                <div class="city">--%>
                    <%--                                    <div>--%>
                    <%--                                        <a href="#">United Kingdom</a>--%>
                    <%--                                    </div>--%>
                    <%--                                    <div>--%>
                    <%--                                        <div class="triangle"></div>--%>
                    <%--                                    </div>--%>
                    <%--                                </div>--%>
                    <%--                                <div class="cinema-list">--%>
                    <%--                                    <div class="cinema-back">--%>
                    <%--                                        <div class="triangle"></div>--%>
                    <%--                                    </div>--%>
                    <%--                                    <div class="scroll">--%>
                    <%--                                        <ul>--%>
                    <%--                                            <li>--%>
                    <%--                                                <a href="#">London (King Kross)</a>--%>
                    <%--                                            </li>--%>
                    <%--                                        </ul>--%>
                    <%--                                    </div>--%>
                    <%--                                </div>--%>
                    <%--                            </li>--%>
                    <%--                            <div class="line-between city-line"></div>--%>
                    <%--                            <li>--%>
                    <%--                                <div class="city">--%>
                    <%--                                    <div>--%>
                    <%--                                        <a href="#">Poland</a>--%>
                    <%--                                    </div>--%>
                    <%--                                    <div>--%>
                    <%--                                        <div class="triangle"></div>--%>
                    <%--                                    </div>--%>
                    <%--                                </div>--%>
                    <%--                                <div class="cinema-list">--%>
                    <%--                                    <div class="cinema-back">--%>
                    <%--                                        <div class="triangle"></div>--%>
                    <%--                                    </div>--%>
                    <%--                                    <div class="scroll">--%>
                    <%--                                        <ul>--%>
                    <%--                                            <li>--%>
                    <%--                                                <a href="#">Warshaw (Test Cinema)</a>--%>
                    <%--                                            </li>--%>
                    <%--                                            <div class="line-between"></div>--%>
                    <%--                                            <li>--%>
                    <%--                                                <a href="#">Warshaw (Test Cinema)</a>--%>
                    <%--                                            </li>--%>
                    <%--                                            <div class="line-between"></div>--%>
                    <%--                                            <li>--%>
                    <%--                                                <a href="#">Warshaw (Test Cinema)</a>--%>
                    <%--                                            </li>--%>
                    <%--                                        </ul>--%>
                    <%--                                    </div>--%>
                    <%--                                </div>--%>
                    <%--                            </li>--%>
                    <%--                        </ul>--%>
                    <%--                    </div>--%>
                </div>
                <div class="background"></div>
            </div>
            <div class="link">
                <div id="local">
                    <div id="current-local">
                        <div class="flag ua-flag"></div>
                        <div>
                            <a href="#">UA</a>
                        </div>
                        <div class="triangle"></div>
                    </div>
                    <div id="locals">
                        <ul>
                            <li>
                                <div class="flag ua-flag"></div>
                                <div>
                                    <a href="#">UA</a>
                                </div>
                            </li>
                            <div class="line-between"></div>
                            <li>
                                <div class="flag uk-flag"></div>
                                <div>
                                    <a href="#">UK</a>
                                </div>
                            </li>
                            <div class="line-between"></div>
                            <li>
                                <div class="flag pl-flag"></div>
                                <div>
                                    <a href="#">PL</a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="background"></div>
            </div>
            <div class="link">
                <div id="menu-btn">
                    <div>
                        <a href="#">Zadyraichuk</a>
                    </div>
                    <div class="triangle"></div>
                </div>
            </div>
        </div>
    </div>
    <div id="menu" class="scroll">
        <div id="user">
            <div>
                <img src="data:image/gif;base64,R0lGODlhAQABAIAAAMLCwgAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="">
            </div>
        </div>
        <nav id="menu-top">
            <div class="menu-item favour-btn">
                <a href="#">Favourites</a>
            </div>
            <div class="line-between"></div>
            <div class="menu-item messages-btn">
                <a href="#">Messages</a>
            </div>
            <div class="line-between"></div>
            <div class="menu-item tickets-btn">
                <a href="#">Tickets</a>
            </div>
            <div class="line-between"></div>
            <div class="menu-item comments-btn">
                <a href="#">Comments</a>
            </div>
            <div class="line-between"></div>
            <div class="menu-item viewed-btn">
                <a href="#">Viewed</a>
            </div>
            <div class="line-between"></div>
            <div class="menu-item waitlist-btn">
                <a href="#">Wait List</a>
            </div>
            <div class="line-between"></div>
            <div class="menu-item users-btn">
                <a href="#">Users</a>
            </div>
        </nav>
        <div id="menu-bottom">
            <div class="menu-item settings-btn">
                <a href="#">Settings</a>
            </div>
            <div class="line-between"></div>
            <div class="menu-item sign-out">
                <a href="#">Sign out</a>
            </div>
        </div>

        <div id="messages">
            <div class="title">
                <div class="triangle"></div>
            </div>
            <div class="messages scroll">
                <div class="message">
                    <div class="message-content">
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                    </div>
                    <div class="cross"></div>
                </div>
                <div class="line-between"></div>
                <div class="message">
                    <div class="message-content">
                        Architecto beatae eos.
                    </div>
                    <div class="cross"></div>
                </div>
                <div class="line-between"></div>
                <div class="message">
                    <div class="message-content">
                        harum praesentium quas! Aspernatur aut error et eum, id, laboriosam.
                    </div>
                    <div class="cross"></div>
                </div>
            </div>
        </div>
        <div id="settings">
            <div class="title">
                <div class="triangle"></div>
            </div>
            <div class="settings-menu">
                <div class="menu-item personal-btn">Personal Information</div>
                <div class="line-between"></div>
                <div class="menu-item site-btn">Site Settings</div>
                <div class="line-between"></div>
                <div class="menu-item security-btn">Security</div>
            </div>
        </div>
    </div>
    <div id="settings-bar">
        <div class="settings-page" id="personal-settings">
            <div class="scroll">
                <div class="content">
                    <div>
                        <div class="avatar">
                            <div class="title">Avatar</div>
                            <div class="image">
                                <img alt=""
                                     src="data:image/gif;base64,R0lGODlhAQABAIAAAMLCwgAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==">
                                <div class="change">Change</div>
                                <input accept="image/jpeg,image/png,image/gif" id="change-avatar" style="display: none"
                                       type="file">
                            </div>
                        </div>
                        <div class="fields">
                            <div class="first-column">
                                <div class="username">
                                    <div class="title">Username</div>
                                    <div class="field">
                                        <input placeholder="Not Selected" type="text">
                                    </div>
                                </div>
                                <div class="firstname">
                                    <div class="title">First Name</div>
                                    <div class="field">
                                        <input placeholder="Not Selected" type="text">
                                    </div>
                                </div>
                                <div class="lastname">
                                    <div class="title">Last Name</div>
                                    <div class="field">
                                        <input placeholder="Not Selected" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="second-column">
                                <div class="sex">
                                    <div class="title">Sex</div>
                                    <ul class="list">
                                        <li class="selected">
                                            <div>Not Selected</div>
                                            <div class="triangle"></div>
                                        </li>
                                        <li>Not Selected</li>
                                        <li>Male</li>
                                        <li>Female</li>
                                    </ul>
                                </div>
                                <div class="country">
                                    <div class="title">Country</div>
                                    <ul class="list">
                                        <li class="selected">
                                            <div>Not Selected</div>
                                            <div class="triangle"></div>
                                        </li>
                                        <li>Not Selected</li>
                                        <li>Ukraine</li>
                                        <li>Poland</li>
                                        <li>Great Britain</li>
                                    </ul>
                                </div>
                                <div class="home-city">
                                    <div class="title">City</div>
                                    <div class="field">
                                        <input placeholder="Not Selected" type="text">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="about">
                        <div class="title">About Me</div>
                        <textarea class="field" placeholder="Text" spellcheck="false" type="text"></textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="settings-page" id="site-settings">
            <div class="scroll">
                <div class="content">
                    <div class="movie-list">
                        <div class="title">Movie list</div>
                        <div class="checked field">
                            <div class="checkbox">hide 18+ movies</div>
                        </div>
                    </div>
                    <div class="personalisation">
                        <div class="title">Personalisation</div>
                        <div class="light-theme field">
                            <div class="radiobox">use light theme</div>
                        </div>
                        <div class="dark-theme field checked">
                            <div class="radiobox">use dark theme</div>
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
                        <div class="title">Language</div>
                        <ul class="list">
                            <li class="selected">
                                <div>Ukrainian</div>
                                <div class="triangle"></div>
                            </li>
                            <li>
                                <div>Ukrainian</div>
                            </li>
                            <li>
                                <div>Polish</div>
                            </li>
                            <li>
                                <div>United Kingdom</div>
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
                        <div class="title"><span>Change Password</span><span class="error empty">*empty fields</span>
                            <span class="error weak">*weak password</span><span class="error not-confirmed">*not confirmed</span>
                        </div>
                        <div class="fields">
                            <div class="field">
                                <input id="old-password" placeholder="old password" type="password">
                            </div>
                            <div class="field">
                                <input id="new-password" placeholder="new password"
                                       regex="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[_!#$%^&*-]).{8,}$"
                                       type="password">
                            </div>
                            <div class="field">
                                <input id="new-password-confirm" placeholder="confirm new password" type="password">
                            </div>
                            <div class="button">
                                <div>save</div>
                            </div>
                        </div>
                    </div>
                    <div class="email">
                        <div class="title"><span>Change Email</span><span class="error empty">*empty field</span>
                            <span class="error not-email">*not email syntax</span></div>
                        <div class="fields">
                            <div><span class="title-2">Email:</span><span class="space">_</span><span class="value">current@mail</span>
                            </div>
                            <div><span class="title-2">New email:</span></div>
                            <div class="field">
                                <input id="new-email" placeholder="email" regex="^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$"
                                       type="email">
                            </div>
                            <div class="button">
                                <div>save</div>
                            </div>
                        </div>
                    </div>
                    <div class="phone">
                        <!--                    todo    <div class="title">Change Phone</div>-->
                        <div class="title"><span>Add Phone</span><span class="error empty">*empty field</span>
                            <span class="error not-phone">*not phone syntax</span></div>
                        <div class="fields">
                            <div class="current-phone"><span class="title-2">Current phone:</span><span
                                    class="space">_</span>
                                <span class="value">+38 (050) 669-37-93</span></div>
                            <div><span class="title-2">New phone:</span></div>
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
                                    <input id="new-phone" maxlength="15" placeholder="phone number"
                                           regex="^\([0-9][0-9][0-9]\) [0-9][0-9][0-9]\-[0-9][0-9]\-[0-9][0-9]$"
                                           type="text">
                                </div>
                            </div>
                            <div class="button">
                                <!--                    todo            <div>change</div>-->
                                <div>add</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="save-btn">
            <div class="button">Save</div>
        </div>
    </div>
    <div id="tickets">
        <div class="page">
            <div class="scroll"></div>
        </div>
    </div>
    <div id="background"></div>
</header>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"
        integrity="sha512-bLT0Qm9VnAYZDflyKcBaQ2gg0hSYNQrJ8RilYldYQ1FxQYoCLtUjuuRuZo+fjqhx/qtq/1itJ0C2ejDxltZVFg=="
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"
        integrity="sha512-0QbL0ph8Tc8g5bLhfVzSqxe9GERORsKhIn1IrpxDAgUsbBGz/V7iSav2zzW325XGd1OMLdL4UiqRJj702IeqnQ=="
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.js"
        integrity="sha512-AS9rZZDdb+y4W2lcmkNGwf4swm6607XJYpNST1mkNBUfBBka8btA6mgRmhoFQ9Umy8Nj/fg5444+SglLHbowuA=="
        crossorigin="anonymous"></script>
<script crossorigin="anonymous"
        integrity="sha512-sR3EKGp4SG8zs7B0MEUxDeq8rw9wsuGVYNfbbO/GLCJ59LBE4baEfQBVsP2Y/h2n8M19YV1mujFANO1yA3ko7Q=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/5.0.5/jquery.inputmask.min.js"></script>
<script crossorigin="anonymous"
        integrity="sha512-XtmMtDEcNz2j7ekrtHvOVR4iwwaD6o/FUJe6+Zq+HgcCsk3kj4uSQQR8weQ2QVj1o0Pk6PwYLohm206ZzNfubg=="
        src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>
<script crossorigin="anonymous"
        src="https://cdn.jsdelivr.net/npm/jsbarcode@3.11.4/dist/barcodes/JsBarcode.code128.min.js"></script>
<script src="../js/header.js"></script>
</html>
