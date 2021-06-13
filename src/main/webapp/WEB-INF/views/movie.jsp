<%--
  Created by IntelliJ IDEA.
  User: Illamurialis
  Date: 05.06.2021
  Time: 15:18
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
    <title><spring:message code="movie.schedule.creation.movie"/></title>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          rel="stylesheet"/>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.css"
          integrity="sha512-uZTwaYYhJLFXaXYm1jdNiH6JZ1wLCTVnarJza7iZ1OKQmvi6prtk85NMvicoSobylP5K4FCdGEc4vk1AYT8b9Q=="
          rel="stylesheet"/>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css"
          integrity="sha512-yHknP1/AwR+yx26cB1y0cjvQUMvEa2PFzt1c9LlS4pRQ5NOTZFWbhBig+X9G9eYW/8m0/4OXNx8pxJ6z57x0dw=="
          rel="stylesheet"/>
    <link href="${contextPath}/resources/scss/header.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/footer.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/calendar.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/schedule-creation.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/movie.css" rel="stylesheet">
</head>
<body>
<div id="header-container"></div>
<div id="movie" identifier="${movie.id}">
    <div class="image">
        <img alt="" src="data:image/${movie.widePicture.format};base64,${movie.widePicture.pictureString}">
        <div class="name">${movie.name}</div>
        <div class="borders">
            <div id="bottom-border"></div>
        </div>
    </div>
    <div class="content">
        <div id="info">
            <div class="left-side">
                <div class="first">
                    <div>${movie.name}</div>
                    <sec:authorize access="isAuthenticated()">
                        <c:if test="${isFavourite}">
                            <div class="like selected"></div>
                        </c:if>
                        <c:if test="${!isFavourite}">
                            <div class="like"></div>
                        </c:if>
                    </sec:authorize>
                </div>
                <div class="second">${movie.surname}</div>
                <div class="genres">
                    <c:forEach var="i" begin="0" end="${movie.genres.size() > 3 ? 2 : movie.genres.size() - 1}">
                        <c:if test="${i != 0}">
                            <div class="line">|</div>
                        </c:if>
                        <div class="genre">${movie.genres.get(i).name()}</div>
                    </c:forEach>
                </div>
            </div>
            <div class="right-side">
                <div class="imdb-rating">IMDb ${movie.imdbRating}</div>
                <div class="pg-rating">${movie.mpaaRating.name().replace('_', '-').substring(1)}</div>
                <sec:authorize access="isAuthenticated()">
                <c:if test="${userRating == null}">
                <div class="user-rating">
                    </c:if>
                    <c:if test="${userRating != null}">
                    <div identifier="${userRating.id}" class="user-rating selected">
                        </c:if>
                        <c:forEach begin="1" end="5" var="i">
                            <c:if test="${userRating.userRating >= i}">
                                <div class="star full"></div>
                            </c:if>
                            <c:if test="${userRating.userRating < i || userRating == null}">
                                <div class="star"></div>
                            </c:if>
                        </c:forEach>
                        <div class="rate-number">${movie.usersRating}</div>
                    </div>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ADMIN')">
                        <div class="change-button"><a href="/admin/movie/change/${movie.id}"><spring:message code="button.change"/></a></div>
                    </sec:authorize>
                </div>
            </div>
            <div id="schedule">
                <div class="movie">
                    <div class="top">
                        <div id="poster">
                            <img alt=""
                                 src="data:image/${movie.posterPicture.format};base64,${movie.posterPicture.pictureString}">
                        </div>
                        <div class="details">
                            <div class="title"><spring:message code="details.title.default"/></div>
                            <div class="content">
                                <div>
                                    <div class="title"><spring:message code="movie.age.rating.title"/></div>
                                    <div class="mpaa-rating">${movie.mpaaRating.rating}</div>
                                </div>
                                <div>
                                    <div class="title"><spring:message code="create.movie.release.date.title"/></div>
                                    <div class="announcement">
                                        <c:if test="${movie.releaseDate.dayOfMonth < 10}">0</c:if>${movie.releaseDate.dayOfMonth}.<c:if
                                            test="${movie.releaseDate.monthValue < 10}">0</c:if>${movie.releaseDate.monthValue}.${movie.releaseDate.year}
                                    </div>
                                </div>
                                <div>
                                    <div class="title"><spring:message code="country.title.default"/></div>
                                    <div class="country">${movie.movieCountry}</div>
                                </div>
                                <%--                                <div>--%>
                                <%--                                    <div class="title"><spring:message code="language.title.default"/></div>--%>
                                <%--                                    <div class="language">${movie.movieLang.name()}</div>--%>
                                <%--                                </div>--%>
                                <div>
                                    <div class="title"><spring:message code="duration.title.default"/></div>
                                    <div class="duration">${movie.durationInMinutes} <spring:message code="minute.default"/></div>
                                </div>
                                <div>
                                    <div class="title"><spring:message code="movie.director.title"/></div>
                                    <div class="director">${movie.directedBy}</div>
                                </div>
                            </div>
                        </div>
                        <sec:authorize access="hasRole('ADMIN')">
                            <div id="add-schedule"><spring:message code="button.create.schedule"/></div>
                        </sec:authorize>
                    </div>
                </div>
                <sec:authorize access="hasRole('ADMIN')">
                    <div id="schedule-creation"></div>
                </sec:authorize>
            </div>
            <div class="additionally">
                <div class="description">
                    <div class="title">Description</div>
                    <div class="underline"></div>
                    <div class="text"><p>
                        ${movie.description}
                    </p></div>
                </div>
                <div class="actors">
                    <div class="title"><spring:message code="actors.title.default"/></div>
                    <div class="underline"></div>
                    <div class="scroll">
                        <div class="actors-list">
                            <!--                        todo js generator-->
                            <c:forEach var="actor" items="${movie.actors}">
                                <div class="actor">
                                    <div>
                                        <img alt=""
                                             src="data:image/${actor.picture.format};base64,${actor.picture.pictureString}">
                                    </div>
                                    <div>${actor.firstName} ${actor.lastName}</div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="additionally">
                <div class="gallery">
                    <div class="title"><spring:message code="gallery.title.default"/></div>
                    <div class="underline"></div>
                    <c:if test="${movie.galleryPictures.size() != 0}">
                        <div class="slider">
                            <div class="left-arrow"></div>
                            <div class="pictures" id="pictures">
                                <c:forEach var="image" items="${movie.galleryPictures}">
                                    <div>
                                        <img alt="" src="data:image/${image.format};base64,${image.pictureString}">
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="right-arrow"></div>
                        </div>
                    </c:if>
                </div>
                <div class="comments">
                    <div class="title"><spring:message code="comments.title.default"/></div>
                    <div class="underline"></div>
                    <div class="scroll">
                        <div id="comments">
                            <c:forEach var="comment" items="${comments}">
                                <div class="comment" identifier="${comment.id}">
                                    <div class="user-info">
                                        <div class="avatar">
                                            <img alt=""
                                                 src="data:image/${comment.user.picture.format};base64,${comment.user.picture.pictureString}">
                                        </div>
                                        <div class="info">
                                            <div class="user">
                                                <div>
                                                    <c:if test="${comment.user.country != null}">
                                                        <div class="flag">
                                                            <img alt="" src="${comment.user.country.flagPicture}">
                                                        </div>
                                                    </c:if>
                                                    <div class="username">${comment.user.username}</div>
                                                </div>
                                                <div class="date">
                                                    <c:if test="${comment.date.dayOfMonth < 10}">0</c:if>${comment.date.dayOfMonth}.<c:if
                                                        test="${comment.date.monthValue < 10}">0</c:if>${comment.date.monthValue}.${comment.date.year}
                                                </div>
                                            </div>
                                            <div class="other">
                                                <div class="user-rating">
                                                    <c:if test="${comment.userRating == null}">
                                                        <div><spring:message code="movie.user.not.rated"/></div>
                                                    </c:if>
                                                    <c:if test="${comment.userRating != null}">
                                                        <c:forEach begin="1" end="5" var="i">
                                                            <c:if test="${comment.userRating.userRating >= i}">
                                                                <div class="star full"></div>
                                                            </c:if>
                                                            <c:if test="${comment.userRating.userRating < i}">
                                                                <div class="star empty"></div>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                                </div>
                                                <div class="complain-btn"><spring:message code="button.complain"/></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="text">${comment.text}</div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <sec:authorize access="isAuthenticated()">
                        <div class="comment-form">
                            <div class="add-comment"><spring:message code="button.add.comment"/></div>
                            <div class="field">
                                <div class="scroll">
                                    <textarea id="comment" placeholder="<spring:message code="comment.title.default"/>" spellcheck="false" type="text"></textarea>
                                </div>
                            </div>
                            <div class="button">
                                <div class="comment-btn"><spring:message code="button.comment.title"/></div>
                            </div>
                        </div>
                    </sec:authorize>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="footer-container"></div>
</body>
<script>
    <sec:authorize access="hasRole('ADMIN')">
    let isAdmin = true;
    </sec:authorize>

    let actionGenre = '<spring:message code="genre.action"/>'
    let comedyGenre = '<spring:message code="genre.comedy"/>'
    let cartoonGenre = '<spring:message code="genre.cartoon"/>'
    let romanceGenre = '<spring:message code="genre.romance"/>'
    let criminalGenre = '<spring:message code="genre.criminal"/>'
    let scienceFictionGenre = '<spring:message code="genre.science.fiction"/>'
    let documentaryGenre = '<spring:message code="genre.documentary"/>'
    let horrorGenre = '<spring:message code="genre.horror"/>'
    let fantasyGenre = '<spring:message code="genre.fantasy"/>'
    let adventureGenre = '<spring:message code="genre.adventure"/>'
    let detectiveGenre = '<spring:message code="genre.detective"/>'
    let historicalGenre = '<spring:message code="genre.historical"/>'
    let thrillerGenre = '<spring:message code="genre.thriller"/>'
    let dramaGenre = '<spring:message code="genre.drama"/>'

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
    let createValue = '<spring:message code="button.create.title"/>';

    let ukraine = '<spring:message code="ukraine.default"/>';
    let unitedKingdom = '<spring:message code="united.kingdom.default"/>';
    let poland = '<spring:message code="poland.default"/>';
    let createMovieValue = '<spring:message code="button.create.movie.title"/>';
    let notRatedValue = '<spring:message code="movie.user.not.rated"/>';
    let complainValue = '<spring:message code="button.complain"/>';
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
        integrity="sha512-XtmMtDEcNz2j7ekrtHvOVR4iwwaD6o/FUJe6+Zq+HgcCsk3kj4uSQQR8weQ2QVj1o0Pk6PwYLohm206ZzNfubg=="
        src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>
<script async
        crossorigin="anonymous"
        defer
        integrity="sha512-sR3EKGp4SG8zs7B0MEUxDeq8rw9wsuGVYNfbbO/GLCJ59LBE4baEfQBVsP2Y/h2n8M19YV1mujFANO1yA3ko7Q=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/5.0.5/jquery.inputmask.min.js"></script>
<script async
        crossorigin="anonymous" defer
        src="https://cdn.jsdelivr.net/npm/jsbarcode@3.11.4/dist/barcodes/JsBarcode.code128.min.js"></script>
<script src="${contextPath}/resources/js/movie.js"></script>
</html>
