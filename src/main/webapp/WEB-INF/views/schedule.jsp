<%--
  Created by IntelliJ IDEA.
  User: Illamurialis
  Date: 02.06.2021
  Time: 16:11
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
    <title>Schedule</title>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          rel="stylesheet"/>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.css"
          integrity="sha512-uZTwaYYhJLFXaXYm1jdNiH6JZ1wLCTVnarJza7iZ1OKQmvi6prtk85NMvicoSobylP5K4FCdGEc4vk1AYT8b9Q=="
          rel="stylesheet"/>
    <link href="${contextPath}/resources/scss/calendar.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/header.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/footer.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/pages.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/filter.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/movie_schedule.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/schedule.css" rel="stylesheet">
</head>
<body>
<div id="header-container"></div>
<div id="schedule-content">
    <div class="top">
        <div class="title"><spring:message code="schedule.select.date.title"/></div>
        <div class="calendar-section">
            <div class="content">
                <div class="date-top">
                    <c:if test="${currentDate.dayOfMonth < 10}">
                        <c:set value="0${currentDate.dayOfMonth}" var="day"/>
                    </c:if>
                    <c:if test="${currentDate.dayOfMonth >= 10}">
                        <c:set value="${currentDate.dayOfMonth}" var="day"/>
                    </c:if>
                    <c:if test="${currentDate.monthValue < 10}">
                        <c:set value="0${currentDate.monthValue}" var="month"/>
                    </c:if>
                    <c:if test="${currentDate.monthValue >= 10}">
                        <c:set value="${currentDate.monthValue}" var="month"/>
                    </c:if>
                    <div class="selected-date">${day}.${month}.${currentDate.year}</div>
                    <div class="triangle"></div>
                </div>
                <div id="calendar"></div>
            </div>
        </div>
    </div>
    <div id="filter"></div>
    <div id="schedules">
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
    <div class="background">
        <img alt="" src="${contextPath}/resources/img/jpg/wide/hall.jpg">
    </div>
    <div class="border"></div>
</div>
<div id="footer-container"></div>
</body>
<sec:authorize access="hasRole('ADMIN')">
    <script>
        let isAdmin = true;
    </script>
</sec:authorize>
<script>
    let hallValue = '<spring:message code="hall.title.default"/>';
    let timeValue = '<spring:message code="time.title.default"/>';
    let monthsCalendarList = [
        '<spring:message code="month.january"/>',
        '<spring:message code="month.february"/>',
        '<spring:message code="month.march"/>',
        '<spring:message code="month.april"/>',
        '<spring:message code="month.may"/>',
        '<spring:message code="month.june"/>',
        '<spring:message code="month.july"/>',
        '<spring:message code="month.august"/>',
        '<spring:message code="month.september"/>',
        '<spring:message code="month.october"/>',
        '<spring:message code="month.november"/>',
        '<spring:message code="month.december"/>'
    ];
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
    let daysCalendarList = [
        '<spring:message code="day.friday.short"/>',
        '<spring:message code="day.monday.short"/>',
        '<spring:message code="day.tuesday.short"/>',
        '<spring:message code="day.wednesday.short"/>',
        '<spring:message code="day.thursday.short"/>',
        "<spring:message code="day.friday.short"/>",
        '<spring:message code="day.saturday.short"/>'
    ];
    let daysList = [
        '<spring:message code="day.sunday"/>',
        '<spring:message code="day.monday"/>',
        '<spring:message code="day.tuesday"/>',
        '<spring:message code="day.wednesday"/>',
        '<spring:message code="day.thursday"/>',
        "<spring:message code="day.friday"/>",
        '<spring:message code="day.saturday"/>'
    ];
    let lastPage = parseInt(${pagesCount});
    let ukraine = '<spring:message code="ukraine.default"/>';
    let unitedKingdom = '<spring:message code="united.kingdom.default"/>';
    let poland = '<spring:message code="poland.default"/>';
    let createMovieValue = '<spring:message code="button.create.movie.title"/>';
    let minuteValue = '<spring:message code="minute.default"/>';
    let minuteShortValue = '<spring:message code="minute.short.default"/>';
    let hourShortValue = '<spring:message code="hour.short.default"/>';
    let changeValue = '<spring:message code="button.change"/>';
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
<script src="${contextPath}/resources/js/schedule.js"></script>
</html>
