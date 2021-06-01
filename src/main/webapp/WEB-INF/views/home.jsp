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
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          rel="stylesheet"/>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.css"
          integrity="sha512-uZTwaYYhJLFXaXYm1jdNiH6JZ1wLCTVnarJza7iZ1OKQmvi6prtk85NMvicoSobylP5K4FCdGEc4vk1AYT8b9Q=="
          rel="stylesheet"/>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css"
          integrity="sha512-yHknP1/AwR+yx26cB1y0cjvQUMvEa2PFzt1c9LlS4pRQ5NOTZFWbhBig+X9G9eYW/8m0/4OXNx8pxJ6z57x0dw=="
          rel="stylesheet"/>
    <link href="${contextPath}/resources/scss/calendar.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/header.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/footer.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/pages.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/filter.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/movie_schedule.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/home.css" rel="stylesheet">
</head>
<body>
<div id="header-container"></div>
<%--<jsp:include page="header.jsp"/>--%>
<div id="home-content">
    <div id="slider">
        <div id="coming-soon">
        </div>
        <div id="movies-in-route">
            <div class="slider">
                <div class="content">
                    <div class="circle"></div>
                    <div class="circle"></div>
                    <div class="circle selected"></div>
                    <div class="circle"></div>
                    <div class="circle"></div>
                </div>
            </div>

            <div class="curtain"></div>
            <div class="borders">
                <div id="bottom-border"></div>
            </div>

            <c:forEach var="movie" items="${sliderMovies}" varStatus="loop">
                <c:if test="${loop.index == 2}">
                    <div class="movie selected">
                        <img alt="" src="data:image/${movie.widePicture.format};base64,${movie.widePicture.pictureString}">
                        <div class="name">
                            <a href="/movie/${movie.id}">
                                <div class="movie-first-name">${movie.name}</div>
                                <div class="movie-second-name">${movie.surname}</div>
                            </a>
                        </div>
                    </div>
                </c:if>
                <c:if test="${loop.index != 2}">
                    <div class="movie">
                        <img alt="" src="data:image/${movie.widePicture.format};base64,${movie.widePicture.picture}">
                        <div class="name">
                            <a href="/movie/${movie.id}">
                                <div class="movie-first-name">${movie.name}</div>
                                <div class="movie-second-name">${movie.surname}</div>
                            </a>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
    <div id="filter-block">
        <div class="title">
            <div><spring:message code="home.weekly.schedule.title"/></div>
            <div><a href="/schedule"><spring:message code="home.select.other.date"/></a></div>
        </div>
        <div id="filter"></div>
    </div>
    <div id="home-schedules-containers">
        <div class="schedule-container"></div>
        <div class="schedule-container"></div>
        <div class="schedule-container"></div>
        <div class="schedule-container"></div>
        <div class="schedule-container"></div>
        <div class="schedule-container"></div>
        <div class="schedule-container"></div>
        <div class="schedule-container"></div>

        <div class="curtain"></div>
    </div>
    <div id="pages"></div>
</div>
<div id="footer-container"></div>
</body>
<sec:authorize access="hasAuthority('ADMIN')">
    <script>
        let isAdmin = true;
    </script>
</sec:authorize>
<script>
    let monthsList = [
        '<spring:message code="month.january.coming.soon"/>',
        '<spring:message code="month.february.coming.soon"/>',
        '<spring:message code="month.march.coming.soon"/>',
        '<spring:message code="month.april.coming.soon"/>',
        '<spring:message code="month.may.coming.soon"/>',
        '<spring:message code="month.june.coming.soon"/>',
        '<spring:message code="month.july.coming.soon"/>',
        '<spring:message code="month.august.coming.soon"/>',
        '<spring:message code="month.september.coming.soon"/>',
        '<spring:message code="month.october.coming.soon"/>',
        '<spring:message code="month.november.coming.soon"/>',
        '<spring:message code="month.december.coming.soon"/>'
    ];
    let daysList = [
        '<spring:message code="day.monday"/>',
        '<spring:message code="day.tuesday"/>',
        '<spring:message code="day.wednesday"/>',
        '<spring:message code="day.thursday"/>',
        "<spring:message code="day.friday"/>",
        '<spring:message code="day.saturday"/>',
        '<spring:message code="day.sunday"/>'
    ];
    let fromLabel = '<spring:message code="movie.from.default"/>';
    let lastPage = parseInt(${pagesCount});
    let ukraine = '<spring:message code="ukraine.default"/>';
    let unitedKingdom = '<spring:message code="united.kingdom.default"/>';
    let poland = '<spring:message code="poland.default"/>';
</script>
<script crossorigin="anonymous"
        integrity="sha512-bLT0Qm9VnAYZDflyKcBaQ2gg0hSYNQrJ8RilYldYQ1FxQYoCLtUjuuRuZo+fjqhx/qtq/1itJ0C2ejDxltZVFg=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script crossorigin="anonymous"
        integrity="sha512-0QbL0ph8Tc8g5bLhfVzSqxe9GERORsKhIn1IrpxDAgUsbBGz/V7iSav2zzW325XGd1OMLdL4UiqRJj702IeqnQ=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script crossorigin="anonymous"
        integrity="sha512-XtmMtDEcNz2j7ekrtHvOVR4iwwaD6o/FUJe6+Zq+HgcCsk3kj4uSQQR8weQ2QVj1o0Pk6PwYLohm206ZzNfubg=="
        src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>
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
<%--<script src="${contextPath}/resources/js/header.js"></script>--%>
<script src="${contextPath}/resources/js/home.js"></script>
</html>
