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
<head>
    <meta charset="UTF-8">
    <title>Schedule create</title>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          rel="stylesheet"/>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.css"
          integrity="sha512-uZTwaYYhJLFXaXYm1jdNiH6JZ1wLCTVnarJza7iZ1OKQmvi6prtk85NMvicoSobylP5K4FCdGEc4vk1AYT8b9Q=="
          rel="stylesheet"/>
    <link href="${contextPath}/resources/scss/calendar.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/schedule-creation.css" rel="stylesheet">
</head>
<body>
<div id="schedule-creation">
    <div class="title"><spring:message code="movie.schedule.creation.title"/></div>
    <div class="underline"></div>
    <div class="form scroll">
        <div class="form-content">
            <div class="movie-name">
                <div class="title-2"><spring:message code="movie.schedule.creation.movie"/></div>
                <div class="input">
                    <div id="search-movie">
                        <div>
                            <div>
                                <input id="movie-search-line" placeholder="<spring:message code="movie.schedule.creation.movie"/>" type="text">
                            </div>
                            <div class="underline"></div>
                        </div>
                        <div id="movie-search-icon"></div>
                    </div>
                    <div id="movie-search-result">
                        <div class="scroll">
                        </div>
                    </div>
                </div>
            </div>
            <div class="seance-time">
                <!--                            todo multiple time selected-->
                <div class="title-2"><spring:message code="time.title.default"/></div>
                <div class="input">
                    <input id="timepicker" type="time">
                </div>
            </div>
            <div class="hall-select">
                <div class="title-2"><spring:message code="hall.title.default"/></div>
                <div class="input">
                    <ul class="list">
                        <li class="selected">
                            <div><spring:message code="list.not.selected.default"/></div>
                            <div class="triangle"></div>
                        </li>
                        <li class="scroll">
                            <div class="halls-container">
                                <%--                                <div identifier="" class="hall"><spring:message code="list.not.selected.default"/></div>--%>
                                <%--                                <div class="underline"></div>--%>
                                <%--                                <div class="hall">2D</div>--%>
                                <%--                                <div class="underline"></div>--%>
                                <%--                                <div class="hall">3D</div>--%>
                                <%--                                <div class="underline"></div>--%>
                                <%--                                <div class="hall">4D</div>--%>
                                <%--                                <div class="underline"></div>--%>
                                <%--                                <div class="hall">RM</div>--%>
                                <%--                                <div class="underline"></div>--%>
                                <%--                                <div class="hall">RM+</div>--%>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="date-from">
                <div class="title-2"><spring:message code="movie.schedule.creation.date.from"/></div>
                <div class="input">
                    <div id="date-from">
                        <div class="title">
                            <div class="value"></div>
                            <div class="triangle"></div>
                        </div>
                        <div class="content"></div>
                    </div>
                </div>
            </div>
            <div class="date-to">
                <div class="title-2"><spring:message code="movie.schedule.creation.date.to"/></div>
                <div class="input">
                    <div id="date-to">
                        <div class="title">
                            <div class="value"></div>
                            <div class="triangle"></div>
                        </div>
                        <div class="content"></div>
                    </div>
                </div>
            </div>
            <div class="days-select">
                <div class="title-2"><spring:message code="movie.schedule.creation.days"/></div>
                <div class="input">
                    <div class="day">
                        <div class="checkbox"><spring:message code="day.monday"/></div>
                    </div>
                    <div class="day">
                        <div class="checkbox"><spring:message code="day.tuesday"/></div>
                    </div>
                    <div class="day">
                        <div class="checkbox"><spring:message code="day.wednesday"/></div>
                    </div>
                    <div class="day">
                        <div class="checkbox"><spring:message code="day.thursday"/></div>
                    </div>
                    <div class="day">
                        <div class="checkbox"><spring:message code="day.friday"/></div>
                    </div>
                    <div class="day">
                        <div class="checkbox"><spring:message code="day.saturday"/></div>
                    </div>
                    <div class="day">
                        <div class="checkbox"><spring:message code="day.sunday"/></div>
                    </div>
                </div>
            </div>
            <div class="price-select">
                <div class="title-2"><spring:message code="price.title.default"/></div>
                <div class="input">
                    <div class="sign" id="minus">&#9866;</div>
                    <div>
                        <input id="price" placeholder="0.00" type="text">
                    </div>
                    <div class="sign" id="plus">&#10010;</div>
                    <div class="currency"></div>
                </div>
            </div>
            <div class="cinema-select">
                <div class="title-2"><spring:message code="movie.schedule.creation.cinema.title"/></div>
                <div class="input">
                    <ul class="list">
                        <li class="selected">
                            <div><spring:message code="list.not.selected.default"/></div>
                            <div class="triangle"></div>
                        </li>
                        <li class="scroll">
                            <div class="cinema-list">
                                <!--                                            todo autoload all cinemas via js-->
                                <%--                                <div identifier="" class="cinema">Not Selected</div>--%>
                                <%--                                <div class="underline"></div>--%>
                                <%--                                <div class="cinema">Lviv (Forum)</div>--%>
                                <%--                                <div class="underline"></div>--%>
                                <%--                                <div class="cinema">Lviv (Viktoria Gardens)</div>--%>
                                <%--                                <div class="underline"></div>--%>
                                <%--                                <div class="cinema">London (King Kross)</div>--%>
                                <%--                                <div class="underline"></div>--%>
                                <%--                                <div class="cinema">Warshaw (Test Cinema)</div>--%>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="button">
        <div class="create-btn"><spring:message code="button.create.title"/></div>
    </div>
</div>
</body>
<script>
    let monthsList = [
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
    let daysList = [
        '<spring:message code="day.monday.short"/>',
        '<spring:message code="day.tuesday.short"/>',
        '<spring:message code="day.wednesday.short"/>',
        '<spring:message code="day.thursday.short"/>',
        '<spring:message code="day.friday.short"/>',
        '<spring:message code="day.saturday.short"/>',
        '<spring:message code="day.sunday.short"/>'
    ];
    let createMovie = '<spring:message code="button.create.title"/>';
    let notSelected = '<spring:message code="list.not.selected.default"/>';
</script>
<sec:authorize access="hasAuthority('ADMIN')">
    <script>
        let isAdmin = true;
    </script>
</sec:authorize>
<%--<script crossorigin="anonymous"--%>
<%--        integrity="sha512-bLT0Qm9VnAYZDflyKcBaQ2gg0hSYNQrJ8RilYldYQ1FxQYoCLtUjuuRuZo+fjqhx/qtq/1itJ0C2ejDxltZVFg=="--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>--%>
<%--<script crossorigin="anonymous"--%>
<%--        integrity="sha512-0QbL0ph8Tc8g5bLhfVzSqxe9GERORsKhIn1IrpxDAgUsbBGz/V7iSav2zzW325XGd1OMLdL4UiqRJj702IeqnQ=="--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>--%>
<%--<script crossorigin="anonymous"--%>
<%--        integrity="sha512-AS9rZZDdb+y4W2lcmkNGwf4swm6607XJYpNST1mkNBUfBBka8btA6mgRmhoFQ9Umy8Nj/fg5444+SglLHbowuA=="--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.js"></script>--%>
<%--<script crossorigin="anonymous"--%>
<%--        integrity="sha512-sR3EKGp4SG8zs7B0MEUxDeq8rw9wsuGVYNfbbO/GLCJ59LBE4baEfQBVsP2Y/h2n8M19YV1mujFANO1yA3ko7Q=="--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/5.0.5/jquery.inputmask.min.js"></script>--%>
<%--<script src="${contextPath}/resources/js/calendar.js"></script>--%>
<%--<script src="${contextPath}/resources/js/schedule-creation.js"></script>--%>
</html>
