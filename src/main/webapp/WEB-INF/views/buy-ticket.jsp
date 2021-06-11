<%--
  Created by IntelliJ IDEA.
  User: Illamurialis
  Date: 09.06.2021
  Time: 19:11
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
    <title>Seance</title>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          rel="stylesheet"/>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.css"
          integrity="sha512-uZTwaYYhJLFXaXYm1jdNiH6JZ1wLCTVnarJza7iZ1OKQmvi6prtk85NMvicoSobylP5K4FCdGEc4vk1AYT8b9Q=="
          rel="stylesheet"/>
    <link href="${contextPath}/resources/scss/header.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/footer.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/calendar.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/buy-ticket.css" rel="stylesheet">
</head>
<body>
<div id="header-container"></div>
<div class="buy-ticket">
    <div class="buy-ticket-content">
        <div class="head">
            <img alt="" id="movie-poster" src="data:image/${seance.movie.posterPicture.format};base64,${seance.movie.posterPicture.pictureString}">
            <div class="tickets-price">
                <div class="scroll"></div>
                <div class="total-price">
                    <div><spring:message code="buy.ticket.total.price"/>:&nbsp;</div>
                    <div class="price"></div>
                    <div class="dollar">$</div>
                </div>
            </div>
        </div>
        <div class="ticket-form">
            <div class="title">
                <div class="first-name">${seance.movie.name}</div>
                <div class="last-name">${seance.movie.surname}</div>
            </div>
            <div class="navbar">
                <div class="nav-item selected" id="choose-places">
                    <div><spring:message code="buy.ticket.choose.places"/></div>
                    <div class="underline"></div>
                </div>
                <div class="nav-item disabled" id="choose-payment-method">
                    <div><spring:message code="buy.ticket.payment.method"/></div>
                    <div class="underline"></div>
                </div>
                <div class="nav-item disabled" id="confirm">
                    <div><spring:message code="buy.ticket.confirm"/></div>
                    <div class="underline"></div>
                </div>
            </div>
            <div class="buy-ticket-info">
                <div class="buy-ticket-container">
                    <div class="choose-places">
                        <div class="places">
                            <div class="screen-image">
                                <svg class="screen" height='60px' preserveAspectRatio="none" viewBox="0 0 100 100"
                                     width='100%'>
                                    <polygon fill="#D7D7D7" points="0,0 100,0 95,100 5,100"></polygon>
                                </svg>
                                <svg class="screen-revert" height='45px' preserveAspectRatio="none"
                                     viewBox="0 0 100 100" width='100%'>
                                    <defs>
                                        <linearGradient id="gradient" x1="0" x2="0" y1="0" y2="1">
                                            <stop offset="0%" stop-color="#FFFFFF" stop-opacity="0.03"></stop>
                                            <stop offset="100%" stop-color="#FFFFFF" stop-opacity="0"></stop>
                                        </linearGradient>
                                    </defs>
                                    <polygon fill="url(#gradient)" points="5,0 95,0 100,100 0,100"></polygon>
                                </svg>
                            </div>
                            <div class="places-grid">
                                <!--                                todo create hall places generator-->
                                <div class="table"></div>
                            </div>
                        </div>
                        <div class="lists">
                            <div class="date-container">
                                <div class="title-2"><spring:message code="date.title.default"/></div>
                                <div class="list" id="date-select">
                                    <div class="selected selected-date" year="${selectedDate.year}">
                                        <div class="date">${selectedDate.dayOfMonth}</div>
                                        <c:if test="${selectedDate.monthValue == 1}">
                                            <div class="month"><spring:message code="month.january.short"/></div>
                                        </c:if>
                                        <c:if test="${selectedDate.monthValue == 2}">
                                            <div class="month"><spring:message code="month.february.short"/></div>
                                        </c:if>
                                        <c:if test="${selectedDate.monthValue == 3}">
                                            <div class="month"><spring:message code="month.march.short"/></div>
                                        </c:if>
                                        <c:if test="${selectedDate.monthValue == 4}">
                                            <div class="month"><spring:message code="month.april.short"/></div>
                                        </c:if>
                                        <c:if test="${selectedDate.monthValue == 5}">
                                            <div class="month"><spring:message code="month.may.short"/></div>
                                        </c:if>
                                        <c:if test="${selectedDate.monthValue == 6}">
                                            <div class="month"><spring:message code="month.june.short"/></div>
                                        </c:if>
                                        <c:if test="${selectedDate.monthValue == 7}">
                                            <div class="month"><spring:message code="month.july.short"/></div>
                                        </c:if>
                                        <c:if test="${selectedDate.monthValue == 8}">
                                            <div class="month"><spring:message code="month.august.short"/></div>
                                        </c:if>
                                        <c:if test="${selectedDate.monthValue == 9}">
                                            <div class="month"><spring:message code="month.september.short"/></div>
                                        </c:if>
                                        <c:if test="${selectedDate.monthValue == 10}">
                                            <div class="month"><spring:message code="month.october.short"/></div>
                                        </c:if>
                                        <c:if test="${selectedDate.monthValue == 11}">
                                            <div class="month"><spring:message code="month.november.short"/></div>
                                        </c:if>
                                        <c:if test="${selectedDate.monthValue == 12}">
                                            <div class="month"><spring:message code="month.december.short"/></div>
                                        </c:if>
                                    </div>
                                    <div class="selection date-selection">
                                        <div id="calendar"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="hall-container">
                                <div class="title-2"><spring:message code="hall.title.default"/></div>
                                <div class="list" id="hall-select">
                                    <div identifier="${seance.hall.id}" class="selected selected-hall">${seance.hall.technology.type} (${seance.hall.number})</div>
                                    <div class="selection hall-selection">
                                        <!--                                    todo js generator-->
                                        <%--                                        <div class="scroll">--%>
                                        <%--                                            <div class="hall">2D</div>--%>
                                        <%--                                            <div class="hall">3D</div>--%>
                                        <%--                                            <div class="hall">3D</div>--%>
                                        <%--                                            <div class="hall">3D</div>--%>
                                        <%--                                            <div class="hall">4D</div>--%>
                                        <%--                                        </div>--%>
                                    </div>
                                </div>
                            </div>
                            <div class="time-container">
                                <div class="title-2"><spring:message code="time.title.default"/></div>
                                <div class="list" id="time-select">
                                    <div class="selected selected-time" identifier="${seance.id}">
                                        <span class="hour"><c:if test="${seance.startSeance.hour < 10}">0</c:if>${seance.startSeance.hour}</span><span>:</span>
                                        <span class="min"><c:if test="${seance.startSeance.minute < 10}">0</c:if>${seance.startSeance.minute}</span>
                                    </div>
                                    <div class="selection time-selection">
                                        <!--                                    todo js generator-->
                                        <%--                                        <div class="scroll">--%>
                                        <%--                                            <div class="time">10:00</div>--%>
                                        <%--                                            <div class="time">14:00</div>--%>
                                        <%--                                            <div class="time">11:50</div>--%>
                                        <%--                                            <div class="time">14:00</div>--%>
                                        <%--                                            <div class="time">16:10</div>--%>
                                        <%--                                        </div>--%>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="choose-payment">
                        <div class="method cash-method">
                            <div class="top">
                                <div class="circle"></div>
                                <div>Cash</div>
                                <sec:authorize access="!hasRole('WORKER')">
                                    <div class="annotation">
                                        <div>*<spring:message code="payment.method.cash.info.part.one"/></div>
                                        <div><spring:message code="payment.method.cash.info.part.two"/></div>
                                    </div>
                                </sec:authorize>
                            </div>
                        </div>
                        <div class="method card-method">
                            <div class="top">
                                <div class="circle"></div>
                                <div><spring:message code="payment.method.card"/></div>
                            </div>
                            <div class="card">
                                <div>
                                    <div class="title-2">
                                        <div><spring:message code="payment.method.card.details"/></div>
                                    </div>
                                    <div class="card-picture">
                                        <div class="card-rect">
                                            <div class="top">
                                                <img alt="" class="mastercard-logo" src="${contextPath}/resources/img/png/mastercard.png">
                                                <img alt="" class="visa-logo" src="${contextPath}/resources/img/png/visa.png">
                                            </div>
                                            <div class="card-number">
                                                ****<span>_</span>****<span>_</span>****<span>_</span>****
                                            </div>
                                            <div class="bottom">
                                                <div class="card-name"><spring:message code="payment.method.card.owner.name"/></div>
                                                <div class="card-date"><spring:message code="payment.method.card.month.placeholder"/>/<spring:message
                                                        code="payment.method.card.year.placeholder"/></div>
                                            </div>
                                        </div>
                                        <div class="card-rect-back">
                                            <div class="card-line"></div>
                                            <div class="cv-code">***</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="fields">
                                    <div class="field">
                                        <div class="title-2">
                                            <div><spring:message code="payment.method.card.number"/></div>
                                            <div class="annotation required">*<spring:message code="error.required.field"/></div>
                                            <div class="annotation incorrect">*<spring:message code="error.incorrect.value"/></div>
                                        </div>
                                        <div>
                                            <input id="card-number-field" placeholder="**** **** **** ****"
                                                   regex="^[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{4}$"
                                                   spellcheck="false" type="text">
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div class="title-2">
                                            <div><spring:message code="payment.method.card.owner.name"/></div>
                                            <div class="annotation required">*<spring:message code="error.required.field"/></div>
                                            <div class="annotation incorrect">*<spring:message code="error.incorrect.value"/></div>
                                        </div>
                                        <div>
                                            <input id="card-name-field" maxlength="20"
                                                   placeholder="Mastercard" regex="^(.*\S+)$" spellcheck="false"
                                                   type="text">
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div class="title-2">
                                            <div><spring:message code="payment.method.card.expiration.date"/></div>
                                            <div class="annotation required">*<spring:message code="error.required.field"/></div>
                                            <div class="annotation incorrect">*<spring:message code="error.incorrect.value"/></div>
                                        </div>
                                        <div class="complex-field">
                                            <div class="field-2">
                                                <div>
                                                    <input id="card-month-field" maxlength="2"
                                                           placeholder="<spring:message code="payment.method.card.month.placeholder"/>" regex="^[0-1][0-9]$"
                                                           spellcheck="false"
                                                           type="text">
                                                </div>
                                            </div>
                                            <div class="field-2">
                                                <div>
                                                    <input id="card-year-field" maxlength="2"
                                                           placeholder="<spring:message code="payment.method.card.year.placeholder"/>" regex="^[0-9]{2}"
                                                           spellcheck="false"
                                                           type="text">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="field">
                                        <div class="title-2">
                                            <div><spring:message code="payment.method.card.cv.code"/></div>
                                            <div class="annotation required">*<spring:message code="error.required.field"/></div>
                                            <div class="annotation incorrect">*<spring:message code="error.incorrect.value"/></div>
                                        </div>
                                        <div>
                                            <input id="card-cv-field" maxlength="3"
                                                   placeholder="***" regex="^[0-9]{3}" spellcheck="false"
                                                   type="password">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tickets-confirm">
                        <div class="scroll"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="buttons">
        <div class="button" id="prev-tile"><spring:message code="button.back.title"/></div>
        <div class="button disabled" id="next-tile"><spring:message code="button.next.title"/></div>
    </div>
    <div class="background">
        <img alt="" src="data:image/${seance.movie.widePicture.format};base64,${seance.movie.widePicture.pictureString}">
        <div class="borders"></div>
    </div>
</div>
<div class="hall-background">
    <img alt="" src="${contextPath}/resources/img/jpg/wide/hall.jpg">
</div>
<div id="footer-container"></div>
</body>
<script>
    <sec:authorize access="hasRole('ADMIN')">
    let isAdmin = true;
    </sec:authorize>
    <sec:authorize access="hasRole('WORKER')">
    let isWorker = true;
    </sec:authorize>

    let daysList = [
        '<spring:message code="day.sunday"/>',
        '<spring:message code="day.monday"/>',
        '<spring:message code="day.tuesday"/>',
        '<spring:message code="day.wednesday"/>',
        '<spring:message code="day.thursday"/>',
        "<spring:message code="day.friday"/>",
        '<spring:message code="day.saturday"/>'
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
    let monthsShortList = [
        '<spring:message code="month.january.short"/>',
        '<spring:message code="month.february.short"/>',
        '<spring:message code="month.march.short"/>',
        '<spring:message code="month.april.short"/>',
        '<spring:message code="month.may.short"/>',
        '<spring:message code="month.june.short"/>',
        '<spring:message code="month.july.short"/>',
        '<spring:message code="month.august.short"/>',
        '<spring:message code="month.september.short"/>',
        '<spring:message code="month.october.short"/>',
        '<spring:message code="month.november.short"/>',
        '<spring:message code="month.december.short"/>'
    ];
    let ukraine = '<spring:message code="ukraine.default"/>';
    let unitedKingdom = '<spring:message code="united.kingdom.default"/>';
    let poland = '<spring:message code="poland.default"/>';
    <%--let createMovieValue = '<spring:message code="button.create.movie.title"/>';--%>
    let rowValue = '<spring:message code="buy.ticket.row"/>';
    let seatValue = '<spring:message code="buy.ticket.seat"/>';
    let nextValue = '<spring:message code="button.next.title"/>';
    let confirmValue = '<spring:message code="buy.ticket.confirm"/>';

    let priceValue = '<spring:message code="price.title.default"/>';
    let dateValue = '<spring:message code="date.title.default"/>';
    let rowTicketValue = '<spring:message code="ticket.row"/>';
    let seatTicketValue = '<spring:message code="ticket.seat"/>';
    let hallValue = '<spring:message code="hall.title.default"/>';
    let timeValue = '<spring:message code="time.title.default"/>';

    let rowsCount = ${seance.hall.rows};
    let columnsCount = ${seance.hall.columns};
    let seanceId = '${seance.id}';
    let ticketPrice = '${seance.ticketPrice}';
    let movieId = '${seance.movie.id}';
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
<script crossorigin="anonymous"
        integrity="sha512-uto9mlQzrs59VwILcLiRYeLKPPbS/bT71da/OEBYEwcdNUk8jYIy+D176RYoop1Da+f9mvkYrmj5MCLZWEtQuA=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script crossorigin="anonymous"
        integrity="sha512-sR3EKGp4SG8zs7B0MEUxDeq8rw9wsuGVYNfbbO/GLCJ59LBE4baEfQBVsP2Y/h2n8M19YV1mujFANO1yA3ko7Q=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/5.0.5/jquery.inputmask.min.js"></script>
<script async
        crossorigin="anonymous" defer
        src="https://cdn.jsdelivr.net/npm/jsbarcode@3.11.4/dist/barcodes/JsBarcode.code128.min.js"></script>
<!--todo if cdn not find load downloaded from source folder-->
<script src="${contextPath}/resources/js/buy-ticket.js"></script>
</html>