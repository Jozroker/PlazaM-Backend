<%--
  Created by IntelliJ IDEA.
  User: Illamurialis
  Date: 02.06.2021
  Time: 22:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Not Found</title>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          rel="stylesheet"/>
    <link href="${contextPath}/resources/scss/error.css" rel="stylesheet">
</head>
<body>
<img alt="" id="back" src="${contextPath}/resources/img/jpg/wide/hall.jpg">
<div>
    <div class="error-code">
        <div>${errorCode.toString().substring(0, 1)}</div>
        <div class="zero">
            <svg height="220" viewBox="0 0 51 51" width="220" xmlns="http://www.w3.org/2000/svg">
                <g>
                    <g class="circle">
                        <path d="M51 25.35366058C51 11.3605957 39.57383728 0 25.5 0 11.42616653 0 0 11.3605957 0 25.35366058s11.42616653 25.35366059 25.5 25.35366059c14.07383728 0 25.5-11.3605957 25.5-25.35366059zM25.5 5.63414574c2.81476593 0 5.09999847 2.27211952 5.09999847 5.0707302 0 2.7986145-2.28523254 5.07073212-5.09999847 5.07073212s-5.09999847-2.27211761-5.09999847-5.07073211c0-2.7986107 2.28523254-5.07073021 5.09999847-5.07073021zm-7.74444199 12.95853614c0-2.7986145-2.28523254-5.07073211-5.10000228-5.07073211-2.81476594 0-5.09999848 2.27211761-5.09999848 5.07073211S9.8407898 23.663414 12.65555573 23.663414c2.81476974 0 5.10000228-2.27211761 5.10000228-5.07073212zm25.68888474 0c0-2.7986145-2.28523254-5.07073211-5.09999848-5.07073211-2.81476593 0-5.09999847 2.27211761-5.09999847 5.07073211s2.28523254 5.07073212 5.09999847 5.07073212c2.81476594 0 5.09999848-2.27211761 5.09999848-5.07073212zm-13.06381226 6.8603859l-4.80832672-4.80832672-4.7807312 4.7807312 4.80832672 4.80832672 4.7807312-4.7807312zm-12.62507248 6.66156769c0-2.7986145-2.28523254-5.07073212-5.10000228-5.07073212-2.81476594 0-5.09999848 2.27211762-5.09999848 5.07073212 0 2.79861068 2.28523254 5.07073211 5.09999848 5.07073211 2.81476974 0 5.10000228-2.27212143 5.10000228-5.07073211zm20.58888626-5.07073212c2.81476594 0 5.09999848 2.27211762 5.09999848 5.07073212 0 2.79861068-2.28523254 5.07073211-5.09999848 5.07073211-2.81476593 0-5.09999847-2.27212143-5.09999847-5.07073211 0-2.7986145 2.28523254-5.07073212 5.09999847-5.07073212zM25.5 34.93170547c2.81476593 0 5.09999847 2.27211762 5.09999847 5.07073212 0 2.79861069-2.28523254 5.0707283-5.09999847 5.0707283s-5.09999847-2.27211761-5.09999847-5.0707283c0-2.7986145 2.28523254-5.07073212 5.09999847-5.07073212z"
                              fill="#D7D7D7"
                              fill-rule="evenodd"/>
                    </g>
                </g>
            </svg>
        </div>
        <div>${errorCode % 10}</div>
    </div>
    <div class="message">
        <c:if test="${errorCode == 404}">
            <spring:message code="error.page.not.found.message"/>
        </c:if>
        <c:if test="${errorCode == 403}">
            <spring:message code="error.access.denied"/>
        </c:if>
        <c:if test="${errorCode != 404 && errorCode != 403}">
            <spring:message code="error.title"/>
        </c:if>
    </div>
    <div class="button">
        <div class="home-btn"><a href="${contextPath}/home"><spring:message code="button.home.title"/></a></div>
    </div>
</div>
</body>
<script crossorigin="anonymous"
        integrity="sha512-bLT0Qm9VnAYZDflyKcBaQ2gg0hSYNQrJ8RilYldYQ1FxQYoCLtUjuuRuZo+fjqhx/qtq/1itJ0C2ejDxltZVFg=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</html>
