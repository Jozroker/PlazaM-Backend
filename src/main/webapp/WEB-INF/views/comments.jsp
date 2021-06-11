<%--
  Created by IntelliJ IDEA.
  User: Illamurialis
  Date: 07.06.2021
  Time: 14:36
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
    <title><spring:message code="comments.title.default"/></title>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          rel="stylesheet"/>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.css"
          integrity="sha512-uZTwaYYhJLFXaXYm1jdNiH6JZ1wLCTVnarJza7iZ1OKQmvi6prtk85NMvicoSobylP5K4FCdGEc4vk1AYT8b9Q=="
          rel="stylesheet"/>
    <link href="${contextPath}/resources/scss/header.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/footer.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/pages.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/comments.css" rel="stylesheet">
</head>
<body>
<div id="header-container"></div>
<img alt="" id="back" src="${contextPath}/resources/img/jpg/wide/hall.jpg">
<div id="comments-container">
    <div class="top">
        <div class="title"><spring:message code="comments.title"/></div>
        <div class="underline"></div>
        <div class="sort">
            <div class="title"><spring:message code="sort.by.title.default"/></div>
            <div class="categories">
                <div identifier="date" class="category selected"><spring:message code="date.title.default"/></div>
                <div identifier="movie" class="category"><spring:message code="create.movie.name.placeholder"/></div>
            </div>
            <div class="underline"></div>
        </div>
    </div>
    <div id="comments">
        <c:forEach var="comment" items="${comments}">
            <div class="comment" identifier="${comment.id}">
                <div class="comment-left-side">
                    <div class="picture">
                        <img alt="" src="data:image/${comment.movie.widePicture.format};base64,${comment.movie.widePicture.pictureString}">
                    </div>
                    <div class="info">
                        <div class="title">
                            <a href="/movie/${comment.movie.id}">
                                <div class="first-name">${comment.movie.name}</div>
                                <div class="last-name">${comment.movie.surname}</div>
                            </a>
                        </div>
                        <div class="date">
                            <div class="title-2"><spring:message code="date.title.default"/>:</div>
                            <div class="space">-</div>
                            <div class="value">
                                <c:if test="${comment.date.dayOfMonth < 10}">0</c:if>${comment.date.dayOfMonth}.<c:if test="${comment.date.monthValue < 10}">0</c:if>${comment.date.monthValue}
                                .${comment.date.year}
                            </div>
                        </div>
                        <div class="time">
                            <div class="title-2"><spring:message code="time.title.default"/>:</div>
                            <div class="space">-</div>
                            <div class="value">
                                <c:if test="${comment.time.hour < 10}">0</c:if>${comment.time.hour}:<c:if test="${comment.time.minute < 10}">0</c:if>${comment.time.minute}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="comment-right-side">
                    <div class="comment-container">
                        <div class="title-2"><spring:message code="comment.title.default"/></div>
                        <div class="scroll">
                            <div class="value">${comment.text}</div>
                        </div>
                    </div>
                    <div class="buttons">
                        <div class="button delete-btn">
                            <div><spring:message code="button.delete.title"/></div>
                            <div>
                                <svg xmlns="http://www.w3.org/2000/svg">
                                    <line x1="0" x2="100%" y1="0" y2="100%"></line>
                                    <line x1="100%" x2="0" y1="0" y2="100%"></line>
                                </svg>
                            </div>
                        </div>
                        <div class="button change-btn"><spring:message code="button.change.title"/></div>
                    </div>
                </div>
            </div>
        </c:forEach>

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

    let lastPage = parseInt(${pagesCount});
    let ukraine = '<spring:message code="ukraine.default"/>';
    let unitedKingdom = '<spring:message code="united.kingdom.default"/>';
    let poland = '<spring:message code="poland.default"/>';
    let createMovieValue = '<spring:message code="button.create.movie.title"/>';
    let dateValue = '<spring:message code="date.title.default"/>';
    let timeValue = '<spring:message code="time.title.default"/>';
    let commentValue = '<spring:message code="comment.title.default"/>';
    let deleteValue = '<spring:message code="button.delete.title"/>';
    let changeValue = '<spring:message code="button.change.title"/>';
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
<script src="${contextPath}/resources/js/comments.js"></script>
</html>
