<%--
  Created by IntelliJ IDEA.
  User: Illamurialis
  Date: 04.06.2021
  Time: 14:00
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
    <title><spring:message code="navbar.movies"/></title>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          rel="stylesheet"/>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.css"
          integrity="sha512-uZTwaYYhJLFXaXYm1jdNiH6JZ1wLCTVnarJza7iZ1OKQmvi6prtk85NMvicoSobylP5K4FCdGEc4vk1AYT8b9Q=="
          rel="stylesheet"/>
    <link href="${contextPath}/resources/scss/header.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/footer.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/pages.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/movies.css" rel="stylesheet">
</head>
<body>
<div id="header-container"></div>
<img alt="" id="back" src="${contextPath}/resources/img/jpg/wide/hall.jpg">
<div id="filter">
    <div class="scroll">
        <div class="content">
            <div class="title"><spring:message code="filter.title.default"/></div>
            <div class="subtitle"><spring:message code="genre.title.default"/></div>
            <div class="underline"></div>
            <div class="genres">
                <div>
                    <div identifier="ACTION" class="genre"><spring:message code="genre.action"/></div>
                </div>
                <div>
                    <div identifier="COMEDY" class="genre"><spring:message code="genre.comedy"/></div>
                </div>
                <div>
                    <div identifier="CARTOON" class="genre"><spring:message code="genre.cartoon"/></div>
                </div>
                <div>
                    <div identifier="ROMANCE" class="genre"><spring:message code="genre.romance"/></div>
                </div>
                <div>
                    <div identifier="CRIMINAL" class="genre"><spring:message code="genre.criminal"/></div>
                </div>
                <div>
                    <div identifier="SCIENCE_FICTION" class="genre"><spring:message code="genre.science.fiction"/></div>
                </div>
                <div>
                    <div identifier="DOCUMENTARY" class="genre"><spring:message code="genre.documentary"/></div>
                </div>
                <div>
                    <div identifier="HORROR" class="genre"><spring:message code="genre.horror"/></div>
                </div>
                <div>
                    <div identifier="FANTASY" class="genre"><spring:message code="genre.fantasy"/></div>
                </div>
                <div>
                    <div identifier="ADVENTURE" class="genre"><spring:message code="genre.adventure"/></div>
                </div>
                <div>
                    <div identifier="DETECTIVE" class="genre"><spring:message code="genre.detective"/></div>
                </div>
                <div>
                    <div identifier="THRILLER" class="genre"><spring:message code="genre.thriller"/></div>
                </div>
                <div>
                    <div identifier="HISTORICAL" class="genre"><spring:message code="genre.historical"/></div>
                </div>
                <div>
                    <div identifier="DRAMA" class="genre"><spring:message code="genre.drama"/></div>
                </div>
            </div>
            <%--            todo add technologies section--%>
            <div class="subtitle"><spring:message code="movies.year.title"/></div>
            <div class="underline"></div>
            <div class="from">
                <div class="title"><spring:message code="movies.year.from"/></div>
                <div class="year-from">
                    <ul class="list">
                        <li class="selected">
                            <div><spring:message code="list.not.selected.default"/></div>
                            <div class="triangle"></div>
                        </li>
                        <li class="scroll">
                            <div class="years-container">
                                <!--                                <div class="year">Not Selected</div>-->
                                <!--                                <div class="year">2021</div>-->
                                <!--                                <div class="year">2020</div>-->
                                <!--                                <div class="year">2019</div>-->
                                <!--                                <div class="year">2018</div>-->
                            </div>
                        </li>
                        <!--                        todo js year generator-->
                    </ul>
                </div>
            </div>
            <div class="to">
                <div class="title"><spring:message code="movies.year.to"/></div>
                <div class="year-to">
                    <ul class="list">
                        <li class="selected">
                            <div><spring:message code="list.not.selected.default"/></div>
                            <div class="triangle"></div>
                        </li>
                        <li class="scroll">
                            <div class="years-container">
                                <!--                                <div class="year">Not Selected</div>-->
                                <!--                                <div class="year">2021</div>-->
                                <!--                                <div class="year">2020</div>-->
                                <!--                                <div class="year">2019</div>-->
                                <!--                                <div class="year">2018</div>-->
                            </div>
                        </li>
                        <!--                        todo js year generator-->
                        <!--                        todo js mouse dragging-->
                    </ul>
                </div>
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
<div class="container">
    <div class="top">
        <div class="title"><spring:message code="movies.catalog.title"/></div>
        <div class="underline"></div>
        <div class="sort">
            <div class="title"><spring:message code="sort.by.title.default"/></div>
            <div class="categories">
                <div identifier="newest" class="category selected"><spring:message code="sort.by.newest.default"/></div>
                <div identifier="popular" class="category"><spring:message code="sort.by.popular.default"/></div>
                <div identifier="expected" class="category"><spring:message code="sort.by.expected.default"/></div>
            </div>
            <div class="underline"></div>
        </div>
    </div>
    <div id="movies">
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
    <sec:authorize access="isAuthenticated()">
    let favouriteMovies = [];
    <c:forEach var="id" items="${favouriteMovies}">
    favouriteMovies.push('${id}');
    </c:forEach>
    </sec:authorize>
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

    let lastPage = parseInt(${pagesCount});
    let ukraine = '<spring:message code="ukraine.default"/>';
    let unitedKingdom = '<spring:message code="united.kingdom.default"/>';
    let poland = '<spring:message code="poland.default"/>';
    let createMovieValue = '<spring:message code="button.create.movie.title"/>';
    let minuteValue = '<spring:message code="minute.short.default"/>';
    let inRentalValue = '<spring:message code="movies.in.rental"/>';
    let fromValue = '<spring:message code="movie.from.default"/>';
    let notSelectedValue = '<spring:message code="list.not.selected.default"/>';
    let addValue = '<spring:message code="button.add"/>';

    let moviesList = [];
    let movie;
    <c:forEach var="movie" items="${movies}">
    movie = {};
    movie.id = '${movie.id}';
    movie.name = '${movie.name}';
    movie.surname = '${movie.surname}';
    movie.releaseDate = '${movie.releaseDate.year}-${movie.releaseDate.monthValue}-${movie.releaseDate.dayOfMonth}';
    movie.genres = [];
    <c:forEach var="genre" items="${movie.genres}">
    movie.genres.push('${genre.name()}')
    </c:forEach>
    movie.imdbRating = '${movie.imdbRating}';
    movie.availableTechnologies = [];
    <c:forEach var="tech" items="${movie.availableTechnologies}">
    movie.availableTechnologies.push('${tech.name()}')
    </c:forEach>
    movie.durationInMinutes = ${movie.durationInMinutes};
    movie.posterPicture = {};
    movie.posterPicture.format = '${movie.posterPicture.format}';
    movie.posterPicture.pictureString = '${movie.posterPicture.pictureString}';
    moviesList.push(movie);
    </c:forEach>
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
<script src="${contextPath}/resources/js/movies.js"></script>
</html>
