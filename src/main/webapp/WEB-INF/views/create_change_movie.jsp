<%--
  Created by IntelliJ IDEA.
  User: Illamurialis
  Date: 02.06.2021
  Time: 22:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>
        <c:if test="${movie.id == null}">
            <spring:message code="button.create.title"/> <spring:message code="movie.schedule.creation.movie"/>
        </c:if>
        <c:if test="${movie.id != null}">
            <spring:message code="button.change.title"/> <spring:message code="movie.schedule.creation.movie"/>
        </c:if>
    </title>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          rel="stylesheet"/>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.css"
          integrity="sha512-uZTwaYYhJLFXaXYm1jdNiH6JZ1wLCTVnarJza7iZ1OKQmvi6prtk85NMvicoSobylP5K4FCdGEc4vk1AYT8b9Q=="
          rel="stylesheet"/>
    <link href="${contextPath}/resources/scss/header.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/footer.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/create_change_movie.css" rel="stylesheet">
</head>
<body>
<c:if test="${movie.id == null}">
    <c:set var="id" value=""/>
    <c:set var="movieNameEng" value=""/>
    <c:set var="movieNameUkr" value=""/>
    <c:set var="movieNamePol" value=""/>
    <c:set var="movieSurnameEng" value=""/>
    <c:set var="movieSurnameUkr" value=""/>
    <c:set var="movieSurnamePol" value=""/>
    <c:set var="movieSurameEng" value=""/>
    <c:set var="movieSurameUkr" value=""/>
    <c:set var="movieSuramePol" value=""/>
    <c:set var="duration" value=""/>
    <c:set var="directedByEng" value=""/>
    <c:set var="directedByUkr" value=""/>
    <c:set var="directedByPol" value=""/>
    <c:set var="releaseDate" value=""/>
    <c:set var="releaseMonth" value=""/>
    <c:set var="releaseYear" value=""/>
    <c:set var="countryEng" value=""/>
    <c:set var="countryUkr" value=""/>
    <c:set var="countryPol" value=""/>
    <c:set var="imdb" value=""/>
    <c:set var="descEng" value=""/>
    <c:set var="descUkr" value=""/>
    <c:set var="descPol" value=""/>
</c:if>
<c:if test="${movie.id != null}">
    <c:set var="id" value="${movie.id}"/>
    <c:set var="movieNameEng" value="${movie.name.get('eng')}"/>
    <c:set var="movieNameUkr" value="${movie.name.get('ukr')}"/>
    <c:set var="movieNamePol" value="${movie.name.get('pol')}"/>
    <c:set var="movieSurnameEng" value="${movie.surname.get('eng')}"/>
    <c:set var="movieSurnameUkr" value="${movie.surname.get('eng')}"/>
    <c:set var="movieSurnamePol" value="${movie.surname.get('eng')}"/>
    <c:set var="movieSurameEng" value="${movie.surname.get('eng')}"/>
    <c:set var="movieSurameUkr" value="${movie.surname.get('ukr')}"/>
    <c:set var="movieSuramePol" value="${movie.surname.get('pol')}"/>
    <c:set var="duration" value="${movie.duration}"/>
    <c:set var="directedByEng" value="${movie.directedBy.get('eng')}"/>
    <c:set var="directedByUkr" value="${movie.directedBy.get('ukr')}"/>
    <c:set var="directedByPol" value="${movie.directedBy.get('pol')}"/>
    <c:set var="releaseDate" value="${movie.releaseDate.dayOfMonth}"/>
    <c:set var="releaseMonth" value="${movie.releaseDate.monthValue}"/>
    <c:set var="releaseYear" value="${movie.releaseDate.year}"/>
    <c:set var="countryEng" value="${movie.movieCountry.get('eng')}"/>
    <c:set var="countryUkr" value="${movie.movieCountry.get('ukr')}"/>
    <c:set var="countryPol" value="${movie.movieCountry.get('pol')}"/>
    <c:set var="imdb" value="${movie.imdbRating}"/>
    <c:set var="descEng" value="${movie.description.get('eng')}"/>
    <c:set var="descUkr" value="${movie.description.get('ukr')}"/>
    <c:set var="descPol" value="${movie.description.get('pol')}"/>
</c:if>
<div id="header-container"></div>
<div id="movie" identifier="${id}">
    <div>
        <div class="pictures">
            <div class="wide-picture">
                <div class="title"><spring:message code="create.movie.wide.image.title"/></div>
                <div class="image">
                    <c:if test="${movie.id == null}">
                        <img alt="" src="${contextPath}/resources/img/jpg/wide/default_wide.jpg">
                    </c:if>
                    <c:if test="${movie.id != null}">
                        <img alt="" src="data:image/${movie.widePicture.format};base64,${movie.widePicture.pictureString}">
                    </c:if>
                    <div class="button"><spring:message code="button.upload"/></div>
                    <input accept="image/jpeg,image/png,image/gif" id="change-wide-picture" type="file">
                </div>
            </div>
            <div class="poster-picture">
                <div class="title"><spring:message code="create.movie.poster.image.title"/></div>
                <div class="image">
                    <c:if test="${movie.id == null}">
                        <img alt="" src="${contextPath}/resources/img/jpg/poster/default_poster.jpg">
                    </c:if>
                    <c:if test="${movie.id != null}">
                        <img alt="" src="data:image/${movie.posterPicture.format};base64,${movie.posterPicture.pictureString}">
                    </c:if>
                    <div class="button"><spring:message code="button.upload"/></div>
                    <input accept="image/jpeg,image/png,image/gif" id="change-poster-picture" type="file">
                </div>
            </div>
        </div>
        <div class="info">
            <div>
                <div class="name">
                    <div class="top">
                        <div class="title">
                            <div><spring:message code="name.title.default"/></div>
<%--                            <div class="triangle"></div>--%>
                        </div>
                        <div class="language-slider">
                            <div class="triangle triangle-left"></div>
                            <div class="slider">
                                <div class="languages">
                                    <div class="language"><spring:message code="english.lang.default"/></div>
                                    <div class="language"><spring:message code="ukrainian.lang.default"/></div>
                                    <div class="language"><spring:message code="polish.lang.default"/></div>
                                </div>
                            </div>
                            <div class="triangle triangle-right"></div>
                        </div>
                    </div>
                    <div class="fields">
                        <div class="fields-container">
                            <div class="field en-field">
                                <div>
                                    <input placeholder="<spring:message code="create.movie.name.placeholder"/>" spellcheck="false" type="text" value="${movieNameEng}">
                                </div>
                            </div>
                            <div class="field ua-field">
                                <div>
                                    <input placeholder="<spring:message code="create.movie.name.placeholder"/>" spellcheck="false" type="text" value="${movieNameUkr}">
                                </div>
                            </div>
                            <div class="field pl-field">
                                <div>
                                    <input placeholder="<spring:message code="create.movie.name.placeholder"/>" spellcheck="false" type="text" value="${movieNamePol}">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%--                <div class="surname">--%>
                <%--                    <div class="top">--%>
                <%--                        <div class="title">--%>
                <%--                            <div class="triangle"></div>--%>
                <%--                            <div><spring:message code="surname.title.default"/></div>--%>
                <%--                        </div>--%>
                <%--                        <div class="language-slider">--%>
                <%--                            <div class="triangle triangle-left"></div>--%>
                <%--                            <div class="slider">--%>
                <%--                                <div class="languages">--%>
                <%--                                    <div class="language"><spring:message code="english.lang.default"/></div>--%>
                <%--                                    <div class="language"><spring:message code="ukrainian.lang.default"/></div>--%>
                <%--                                    <div class="language"><spring:message code="polish.lang.default"/></div>--%>
                <%--                                </div>--%>
                <%--                            </div>--%>
                <%--                            <div class="triangle triangle-right"></div>--%>
                <%--                        </div>--%>
                <%--                    </div>--%>
                <%--                    <div class="fields">--%>
                <%--                        <div class="fields-container">--%>
                <%--                            <div class="field en-field">--%>
                <%--                                <div>--%>
                <%--                                    <input placeholder="<spring:message code="create.movie.surname.placeholder"/>" spellcheck="false" type="text"--%>
                <%--                                           value="${movieSurameEng}">--%>
                <%--                                </div>--%>
                <%--                            </div>--%>
                <%--                            <div class="field ua-field">--%>
                <%--                                <div>--%>
                <%--                                    <input placeholder="<spring:message code="create.movie.surname.placeholder"/>" spellcheck="false" type="text"--%>
                <%--                                           value="${movieSurameUkr}">--%>
                <%--                                </div>--%>
                <%--                            </div>--%>
                <%--                            <div class="field pl-field">--%>
                <%--                                <div>--%>
                <%--                                    <input placeholder="<spring:message code="create.movie.surname.placeholder"/>" spellcheck="false" type="text"--%>
                <%--                                           value="${movieSuramePol}">--%>
                <%--                                </div>--%>
                <%--                            </div>--%>
                <%--                        </div>--%>
                <%--                    </div>--%>
                <%--                </div>--%>
            </div>
            <div>
                <div class="surname">
                    <div class="top">
                        <div class="title">
                            <%--                            <div class="triangle"></div>--%>
                            <div><spring:message code="surname.title.default"/></div>
                        </div>
                        <div class="language-slider">
                            <div class="triangle triangle-left"></div>
                            <div class="slider">
                                <div class="languages">
                                    <div class="language"><spring:message code="english.lang.default"/></div>
                                    <div class="language"><spring:message code="ukrainian.lang.default"/></div>
                                    <div class="language"><spring:message code="polish.lang.default"/></div>
                                </div>
                            </div>
                            <div class="triangle triangle-right"></div>
                        </div>
                    </div>
                    <div class="fields">
                        <div class="fields-container">
                            <div class="field en-field">
                                <div>
                                    <input placeholder="<spring:message code="create.movie.surname.placeholder"/>" spellcheck="false" type="text"
                                           value="${movieSurameEng}">
                                </div>
                            </div>
                            <div class="field ua-field">
                                <div>
                                    <input placeholder="<spring:message code="create.movie.surname.placeholder"/>" spellcheck="false" type="text"
                                           value="${movieSurameUkr}">
                                </div>
                            </div>
                            <div class="field pl-field">
                                <div>
                                    <input placeholder="<spring:message code="create.movie.surname.placeholder"/>" spellcheck="false" type="text"
                                           value="${movieSuramePol}">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <div class="duration">
                    <div class="top">
                        <div class="title"><spring:message code="duration.title.default"/></div>
                    </div>
                    <div class="field">
                        <div class="sign minus">&#9866;</div>
                        <div>
                            <input id="duration" max="300" min="1" placeholder="0" type="number" value="${duration}">
                        </div>
                        <div class="sign plus">&#10010;</div>
                        <div><spring:message code="minute.default"/></div>
                    </div>
                </div>
            </div>
            <div>
                <div class="directed-by">
                    <div class="top">
                        <div class="title"><spring:message code="create.movie.directed.by.title"/></div>
                        <div class="language-slider">
                            <div class="triangle triangle-left"></div>
                            <div class="slider">
                                <div class="languages">
                                    <div class="language"><spring:message code="english.lang.default"/></div>
                                    <div class="language"><spring:message code="ukrainian.lang.default"/></div>
                                    <div class="language"><spring:message code="polish.lang.default"/></div>
                                </div>
                            </div>
                            <div class="triangle triangle-right"></div>
                        </div>
                    </div>
                    <div class="fields">
                        <div class="fields-container">
                            <div class="field en-field">
                                <div>
                                    <input placeholder="<spring:message code="create.movie.directed.by.placeholder"/>" spellcheck="false" type="text"
                                           value="${directedByEng}">
                                </div>
                            </div>
                            <div class="field ua-field">
                                <div>
                                    <input placeholder="<spring:message code="create.movie.directed.by.placeholder"/>" spellcheck="false" type="text"
                                           value="${directedByUkr}">
                                </div>
                            </div>
                            <div class="field pl-field">
                                <div>
                                    <input placeholder="<spring:message code="create.movie.directed.by.placeholder"/>" spellcheck="false" type="text"
                                           value="${directedByPol}">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <div class="release-date">
                    <div class="top">
                        <div class="title"><spring:message code="create.movie.release.date.title"/></div>
                    </div>
                    <div class="field">
                        <div class="day-selection">
                            <div class="sign minus">&#9866;</div>
                            <div>
                                <input id="day" max="31" min="1" placeholder="0" type="number" value="${releaseDate}">
                            </div>
                            <div class="sign plus">&#10010;</div>
                        </div>
                        <div class="month-selection">
                            <div class="sign minus">&#9866;</div>
                            <div>
                                <input id="month" max="12" min="1" placeholder="0" type="number" value="${releaseMonth}">
                            </div>
                            <div class="sign plus">&#10010;</div>
                        </div>
                        <div class="year-selection">
                            <div class="sign minus">&#9866;</div>
                            <div>
                                <input id="year" max="9999" min="2020" placeholder="0" type="number" value="${releaseYear}">
                            </div>
                            <div class="sign plus">&#10010;</div>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <div class="country">
                    <div class="top">
                        <div class="title"><spring:message code="country.title.default"/></div>
                        <div class="language-slider">
                            <div class="triangle triangle-left"></div>
                            <div class="slider">
                                <div class="languages">
                                    <div class="language"><spring:message code="english.lang.default"/></div>
                                    <div class="language"><spring:message code="ukrainian.lang.default"/></div>
                                    <div class="language"><spring:message code="polish.lang.default"/></div>
                                </div>
                            </div>
                            <div class="triangle triangle-right"></div>
                        </div>
                    </div>
                    <div class="fields">
                        <div class="fields-container">
                            <div class="field en-field">
                                <div>
                                    <input placeholder="<spring:message code="create.movie.country.placeholder"/>" spellcheck="false" type="text" value="${countryEng}">
                                </div>
                            </div>
                            <div class="field ua-field">
                                <div>
                                    <input placeholder="<spring:message code="create.movie.country.placeholder"/>" spellcheck="false" type="text" value="${countryUkr}">
                                </div>
                            </div>
                            <div class="field pl-field">
                                <div>
                                    <input placeholder="<spring:message code="create.movie.country.placeholder"/>" spellcheck="false" type="text" value="${countryPol}">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--            <div>--%>
            <%--                <div class="movie-language">--%>
            <%--                    <div class="top">--%>
            <%--                        <div class="title"><spring:message code="create.movie.language.title"/></div>--%>
            <%--                    </div>--%>
            <%--                    <div class="field">--%>
            <%--                        <ul class="list">--%>
            <%--                            <c:if test="${movie.id == null}">--%>
            <%--                            <li class="selected" identifier="NULL">--%>
            <%--                                <div><spring:message code="list.not.selected.default"/></div>--%>
            <%--                                </c:if>--%>
            <%--                                <c:if test="${movie.id != null}">--%>
            <%--                            <li class="selected" identifier="${movie.movieLang.name()}">--%>
            <%--                                <div>--%>
            <%--                                    <c:if test="${movie.movieLang.name().equals('ENG')}">--%>
            <%--                                        <spring:message code="english.lang.title.default"/>--%>
            <%--                                    </c:if>--%>
            <%--                                    <c:if test="${movie.movieLang.name().equals('UKR')}">--%>
            <%--                                        <spring:message code="ukrainian.lang.title.default"/>--%>
            <%--                                    </c:if>--%>
            <%--                                    <c:if test="${movie.movieLang.name().equals('POL')}">--%>
            <%--                                        <spring:message code="polish.lang.title.default"/>--%>
            <%--                                    </c:if>--%>
            <%--                                </div>--%>
            <%--                                </c:if>--%>
            <%--                                <div class="triangle"></div>--%>
            <%--                            </li>--%>
            <%--                            <li identifier="NULL">--%>
            <%--                                <div><spring:message code="list.not.selected.default"/></div>--%>
            <%--                            </li>--%>
            <%--                            <li identifier="ENG">--%>
            <%--                                <div><spring:message code="english.lang.title.default"/></div>--%>
            <%--                            </li>--%>
            <%--                            <li identifier="UKR">--%>
            <%--                                <div><spring:message code="ukrainian.lang.title.default"/></div>--%>
            <%--                            </li>--%>
            <%--                            <li identifier="POL">--%>
            <%--                                <div><spring:message code="polish.lang.title.default"/></div>--%>
            <%--                            </li>--%>
            <%--                        </ul>--%>
            <%--                    </div>--%>
            <%--                </div>--%>
            <%--            </div>--%>
            <div>
                <div class="mpaa-rating">
                    <div class="top">
                        <div class="title"><spring:message code="create.movie.mpaa.rating.title"/></div>
                    </div>
                    <div class="field">
                        <ul class="list">
                            <c:if test="${movie.id == null}">
                            <li class="selected" identifier="NULL">
                                <div><spring:message code="list.not.selected.default"/></div>
                                </c:if>
                                <c:if test="${movie.id != null}">
                            <li class="selected" identifier="${movie.mpaaRating.name()}">
                                <div>${movie.mpaaRating.rating}</div>
                                </c:if>
                                <div class="triangle"></div>
                            </li>
                            <li identifier="NULL">
                                <div><spring:message code="list.not.selected.default"/></div>
                            </li>
                            <li identifier="G">
                                <div>G</div>
                            </li>
                            <li identifier="PG">
                                <div>PG</div>
                            </li>
                            <li identifier="PG_13">
                                <div>PG-13</div>
                            </li>
                            <li identifier="R">
                                <div>R</div>
                            </li>
                            <li identifier="NC_17">
                                <div>NC-17</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div>
                <div class="imdb-rating">
                    <div class="top">
                        <div class="title">IMDb</div>
                    </div>
                    <div class="field">
                        <div class="sign minus">&#9866;</div>
                        <div>
                            <input id="imdb-rating" max="10.0" min="0.1" placeholder="0.0" step="0.1" type="number" value="${imdb}">
                        </div>
                        <div class="sign plus">&#10010;</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div>
        <div class="description">
            <div class="top">
                <div class="title"><spring:message code="description.title.default"/></div>
                <div class="language-slider">
                    <div class="triangle triangle-left"></div>
                    <div class="slider">
                        <div class="languages">
                            <div class="language"><spring:message code="english.lang.default"/></div>
                            <div class="language"><spring:message code="ukrainian.lang.default"/></div>
                            <div class="language"><spring:message code="polish.lang.default"/></div>
                        </div>
                    </div>
                    <div class="triangle triangle-right"></div>
                </div>
            </div>
            <div class="fields">
                <div class="fields-container">
                    <div class="field en-field">
                        <div class="scroll">
                            <textarea spellcheck="false">${descEng}</textarea>
                        </div>
                    </div>
                    <div class="field ua-field">
                        <div class="scroll">
                            <textarea spellcheck="false">${descUkr}</textarea>
                        </div>
                    </div>
                    <div class="field pl-field">
                        <div class="scroll">
                            <textarea spellcheck="false">${descPol}</textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="selection-lists">
            <div class="genres">
                <div class="top">
                    <div class="title"><spring:message code="genres.title.default"/></div>
                </div>
                <div class="field">
                    <div class="scroll">
                        <div class="genres-container">
                            <!--                        todo js generator-->
                            <div id="ACTION" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.action"/></div>
                            </div>
                            <div id="FANTASY" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.fantasy"/></div>
                            </div>
                            <div id="SCIENCE_FICTION" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.science.fiction"/></div>
                            </div>
                            <div id="DOCUMENTARY" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.documentary"/></div>
                            </div>
                            <div id="ROMANCE" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.romance"/></div>
                            </div>
                            <div id="COMEDY" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.comedy"/></div>
                            </div>
                            <div id="HORROR" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.horror"/></div>
                            </div>
                            <div id="CARTOON" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.cartoon"/></div>
                            </div>
                            <div id="CRIMINAL" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.criminal"/></div>
                            </div>
                            <div id="ADVENTURE" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.adventure"/></div>
                            </div>
                            <div id="DETECTIVE" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.detective"/></div>
                            </div>
                            <div id="THRILLER" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.thriller"/></div>
                            </div>
                            <div id="HISTORICAL" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.historical"/></div>
                            </div>
                            <div id="DRAMA" class="genre">
                                <div class="icon-tick"></div>
                                <div class="value"><spring:message code="genre.drama"/></div>
                            </div>

                            <div class="genre empty-genre">
                                <div class="icon-tick"></div>
                                <div class="value"></div>
                            </div>
                            <%--                            <div class="genre empty-genre">--%>
                            <%--                                <div class="icon-tick"></div>--%>
                            <%--                                <div class="value"></div>--%>
                            <%--                            </div>--%>
                        </div>
                    </div>
                    <!--                    <div class="create-genre">-->
                    <!--                        <div class="button">create new</div>-->
                    <!--                        <div class="language-slider">-->
                    <!--                            <div class="triangle triangle-left"></div>-->
                    <!--                            <div class="slider">-->
                    <!--                                <div class="languages">-->
                    <!--                                    <div class="language">english</div>-->
                    <!--                                    <div class="language">ukrainian</div>-->
                    <!--                                    <div class="language">polish</div>-->
                    <!--                                </div>-->
                    <!--                            </div>-->
                    <!--                            <div class="triangle triangle-right"></div>-->
                    <!--                        </div>-->
                    <!--                        <div class="genre-form">-->
                    <!--                            <div>Genre name</div>-->
                    <!--                            <div class="fields">-->
                    <!--                                <div class="fields-container">-->
                    <!--                                    <div class="field en-field">-->
                    <!--                                        <div>-->
                    <!--                                            <input placeholder="fantasy" spellcheck="false" type="text">-->
                    <!--                                        </div>-->
                    <!--                                    </div>-->
                    <!--                                    <div class="field ua-field">-->
                    <!--                                        <div>-->
                    <!--                                            <input placeholder="fantasy" spellcheck="false" type="text">-->
                    <!--                                        </div>-->
                    <!--                                    </div>-->
                    <!--                                    <div class="field pl-field">-->
                    <!--                                        <div>-->
                    <!--                                            <input placeholder="fantasy" spellcheck="false" type="text">-->
                    <!--                                        </div>-->
                    <!--                                    </div>-->
                    <!--                                </div>-->
                    <!--                            </div>-->
                    <!--                        </div>-->
                    <!--                    </div>-->
                    <!--                    todo convert genre enum to entity class and add deleting genre in interface-->
                    <!--                    todo add update genre in interface-->
                    <!--                    todo add deleting actor in interface-->
                    <!--                    todo add update actor in interface-->
                </div>
            </div>
            <div class="actors">
                <div class="top">
                    <div class="title"><spring:message code="actors.title.default"/></div>
                </div>
                <div class="field">
                    <div id="search-actor">
                        <div>
                            <div>
                                <input id="actor-search-line" placeholder="<spring:message code="create.movie.actor.placeholder"/>" spellcheck="false" type="text">
                            </div>
                            <div class="underline"></div>
                        </div>
                        <div id="actor-search-icon"></div>
                    </div>
                    <div class="actors-field">
                        <div class="scroll">
                            <!--                    todo js generator-->
                            <div class="actors-container">
                                <%--                                <div class="actor">--%>
                                <%--                                    <div class="icon-tick"></div>--%>
                                <%--                                    <img alt="" class="actor-avatar"--%>
                                <%--                                         src="data:image/gif;base64,R0lGODlhAQABAIAAAMLCwgAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==">--%>
                                <%--                                    <div class="actor-name">Rostyslav Zadyraichuk</div>--%>
                                <%--                                </div>--%>
                                <%--                                <div class="actor">--%>
                                <%--                                    <div class="icon-tick"></div>--%>
                                <%--                                    <img alt="" class="actor-avatar"--%>
                                <%--                                         src="data:image/gif;base64,R0lGODlhAQABAIAAAMLCwgAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==">--%>
                                <%--                                    <div class="actor-name">Rostyslav Zadyraichuk</div>--%>
                                <%--                                </div>--%>
                                <%--                                <div class="actor">--%>
                                <%--                                    <div class="icon-tick"></div>--%>
                                <%--                                    <img alt="" class="actor-avatar"--%>
                                <%--                                         src="data:image/gif;base64,R0lGODlhAQABAIAAAMLCwgAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==">--%>
                                <%--                                    <div class="actor-name">Rostyslav Zadyraichuk</div>--%>
                                <%--                                </div>--%>
                                <%--                                <div class="actor">--%>
                                <%--                                    <div class="icon-tick"></div>--%>
                                <%--                                    <img alt="" class="actor-avatar"--%>
                                <%--                                         src="data:image/gif;base64,R0lGODlhAQABAIAAAMLCwgAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==">--%>
                                <%--                                    <div class="actor-name">Rostyslav Zadyraichuk</div>--%>
                                <%--                                </div>--%>
                            </div>
                        </div>
                    </div>
                    <div class="actor-form">
                        <div class="button"><spring:message code="button.create.new"/></div>
                        <div class="language-slider">
                            <div class="triangle triangle-left"></div>
                            <div class="slider">
                                <div class="languages">
                                    <div class="language"><spring:message code="english.lang.default"/></div>
                                    <div class="language"><spring:message code="ukrainian.lang.default"/></div>
                                    <div class="language"><spring:message code="polish.lang.default"/></div>
                                </div>
                            </div>
                            <div class="triangle triangle-right"></div>
                        </div>
                        <div class="container">
                            <div class="avatar-picture">
                                <img alt="" id="actor-avatar" src="${contextPath}/resources/img/jpg/default_avatar.jpg">
                                <div class="button"><spring:message code="button.upload"/></div>
                                <input accept="image/jpeg,image/png,image/gif" id="change-actor-avatar" type="file">
                            </div>
                            <div class="form-info">
                                <div class="first-name">
                                    <div class="top">
                                        <div class="title"><spring:message code="first.name.title.default"/></div>
                                    </div>
                                    <div class="fields">
                                        <div class="fields-container">
                                            <div class="field en-field">
                                                <div>
                                                    <input placeholder="<spring:message code="create.movie.create.actor.first.name.placeholder"/>" spellcheck="false"
                                                           type="text">
                                                </div>
                                                <div class="underline"></div>
                                            </div>
                                            <div class="field ua-field">
                                                <div>
                                                    <input placeholder="<spring:message code="create.movie.create.actor.first.name.placeholder"/>" spellcheck="false"
                                                           type="text">
                                                </div>
                                                <div class="underline"></div>
                                            </div>
                                            <div class="field pl-field">
                                                <div>
                                                    <input placeholder="<spring:message code="create.movie.create.actor.first.name.placeholder"/>" spellcheck="false"
                                                           type="text">
                                                </div>
                                                <div class="underline"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="last-name">
                                    <div class="top">
                                        <div class="title"><spring:message code="last.name.title.default"/></div>
                                    </div>
                                    <div class="fields">
                                        <div class="fields-container">
                                            <div class="field en-field">
                                                <div>
                                                    <input placeholder="<spring:message code="create.movie.create.actor.last.name.placeholder"/>" spellcheck="false"
                                                           type="text">
                                                </div>
                                                <div class="underline"></div>
                                            </div>
                                            <div class="field ua-field">
                                                <div>
                                                    <input placeholder="<spring:message code="create.movie.create.actor.last.name.placeholder"/>" spellcheck="false"
                                                           type="text">
                                                </div>
                                                <div class="underline"></div>
                                            </div>
                                            <div class="field pl-field">
                                                <div>
                                                    <input placeholder="<spring:message code="create.movie.create.actor.last.name.placeholder"/>" spellcheck="false"
                                                           type="text">
                                                </div>
                                                <div class="underline"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div>
        <div class="top">
            <div class="title"><spring:message code="gallery.title.default"/></div>
        </div>
        <div class="field">
            <div class="scroll">
                <div class="gallery">
                    <c:if test="${movie.galleryPictures.size() != 0}">
                        <c:forEach var="picture" items="${movie.galleryPictures}">
                            <div class="gallery-image editable">
                                <img src="data:image/${picture.format};base64,${picture.pictureString}" alt="">
                                <div class="cross">
                                    <div class="icon-cross"></div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                    <div class="gallery-image add-button">
                        <div>
                            <div>&#10010;</div>
                            <div><spring:message code="button.add.picture"/></div>
                        </div>
                    </div>
                    <input accept="image/jpeg,image/png,image/gif" id="add-movie-picture" type="file">
                </div>
            </div>
        </div>
    </div>
    <c:if test="${movie.id != null}">
        <div id="change-button"><spring:message code="button.change.title"/></div>
    </c:if>
    <c:if test="${movie.id == null}">
        <div id="create-button"><spring:message code="button.create.title"/></div>
    </c:if>
</div>
<div id="footer-container"></div>
</body>
<script>
    let ukraine = '<spring:message code="ukraine.default"/>';
    let unitedKingdom = '<spring:message code="united.kingdom.default"/>';
    let poland = '<spring:message code="poland.default"/>';
    let createMovieValue = '<spring:message code="button.create.movie.title"/>';
    let selectedGenres = [];
    let selectedActors = [];
    <c:if test="${movie.id != null}">
    let movieId = '${movie.id}';
    <c:forEach var="actor" items="${movie.actors}">
    selectedActors.push('${actor.id}');
    </c:forEach>
    <c:forEach var="genre" items="${movie.genres}">
    selectedGenres.push('${genre.name()}');
    </c:forEach>
    </c:if>
    let actors = [];
    let actor;
    <c:forEach var="actor" items="${actors}">
    actor = [];
    actor.push('${actor.id}');
    actor.push("${actor.firstName}");
    actor.push("${actor.lastName}");
    actor.push('${actor.picture.format}');
    actor.push('${actor.picture.pictureString}');
    actors.push(actor);
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
<script src="${contextPath}/resources/js/create_change_movie.js"></script>
</html>
