let scrollbar;
let scrollbarPosition;
let currentScrollPosition;
nextClickedElement = $();
// let commentsScroll;

let yearFromHidden = true;
let yearToHidden = true;
let filterHidden = true;

let yearFromAnimate = false;
let yearToAnimate = false;
let filterAnimate = false;
let selectedGenres = [];
// let mapa;

$(document).ready(function () {

    {
        $(".sort .underline").css("width", $(".category.selected").first().width() + 14 + "px");
        yearsGenerator(2020, new Date().getFullYear());
        // genresGenerator(["action", "fantasy", "horror"]);
        moviesGenerator();

        $.ajax({
            url: window.location.origin + '/footer',
            method: "GET"
        }).done(function (page) {
            $("#footer-container").html(page);
            // $.getScript("../js/footer.js");
            $.getScript("/resources/js/footer.js");
        })

        $.ajax({
            url: window.location.origin + '/header?path=' + window.location.pathname,
            method: "GET"
        }).done(function (page) {
            // $("#header-container").html($(page).find("#header"));
            $("#header-container").html(page);
            // $.getScript("../js/footer.js");
            $.getScript("/resources/js/header.js");
            $.getScript("/resources/js/pages.js");
        })
    }

    $("#filter .scroll").each(function (index) {
        if (index === 0) {
            scrollbar = new SimpleBar($("#filter .scroll")[index], {
                autoHide: false
            });
            scrollbar.getScrollElement().addEventListener("scroll", function () {
                if (!yearFromHidden || !yearToHidden) {
                    scrollbar.getScrollElement().scrollTop = scrollbarPosition;
                }
            })
        }
            // else if ($(this).hasClass("comments-scroll")) {
            //     commentsScroll = new SimpleBar($("#filter .scroll")[index], {
            //         autoHide: false
            //     });
        // }
        else {
            new SimpleBar($("#filter .scroll")[index], {
                autoHide: false
            });
        }
    })

    $(".year-from .simplebar-content-wrapper").scroll(function () {
        $(".year-from .simplebar-vertical .simplebar-scrollbar").css("transform", "translate3d(0px, " +
            ((Math.abs($(".year-from .simplebar-content").position().top) * ($(".year-from .simplebar-vertical").height() -
                $(".year-from .simplebar-vertical .simplebar-scrollbar").height())) / ($(".year-from .simplebar-content").height() -
                54)) + "px, 0px)");
    })

    $(".year-to .simplebar-content-wrapper").scroll(function () {
        $(".year-to .simplebar-vertical .simplebar-scrollbar").css("transform", "translate3d(0px, " +
            ((Math.abs($(".year-to .simplebar-content").position().top) * ($(".year-to .simplebar-vertical").height() -
                $(".year-to .simplebar-vertical .simplebar-scrollbar").height())) / ($(".year-to .simplebar-content").height() -
                54)) + "px, 0px)");
    })

    $(".category").click(function () {
        if (!$(this).hasClass("selected")) {
            $("#movies .curtain").css("display", "block");
            let genres = '';
            $(".genre.selected").each(function () {
                genres += $(this).attr("identifier") + ",";
            });
            genres = genres.slice(0, -1);
            let url = window.location.origin + '/movies/1?sort=' + $(this).attr("identifier") + (genres === '' ? '' : '&genres=' + genres);
            if (!isNaN(parseInt($(".year-from .selected > div").first().text()))) {
                url += '&yearFrom=' + parseInt($(".year-from .selected > div").first().text());
            }
            if (!isNaN(parseInt($(".year-to .selected > div").first().text()))) {
                url += '&yearTo=' + parseInt($(".year-to .selected > div").first().text());
            }

            $.ajax({
                url: url,
                method: 'GET'
            }).done(function (data) {
                moviesList = data.slice(1);
                $("#pages").html("");
                lastPage = data[0];
                page = parseInt(new URLSearchParams(window.location.search).get("page"));
                page = isNaN(page) ? 1 : page;
                if (lastPage < 1) {
                    $("#pages").html("");
                } else {
                    setPagesValues(lastPage);
                    pagesAnimation();
                }
                $("#movies").html("");
                moviesGenerator();
            })

            $(".categories").find(".selected").removeClass("selected");
            $(this).addClass("selected");
            $(".sort .underline").animate({
                left: $(this).position().left + "px",
                width: $(this).width() + 14 + "px"
            }, 300, "easeInOutQuint")
        }
    })

    $(document).on("click", ".like", function () {
        let id = $(this).parents(".movie").first().attr("identifier");
        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected");
            addedFavMovies.splice(addedFavMovies.indexOf(id), 1);
            removedFavMovies.push(id);
        } else {
            $(this).addClass("selected");
            addedFavMovies.push(id);
            removedFavMovies.splice(removedFavMovies.indexOf(id), 1);
        }
    })

    $(".genre").click(function () {
        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected");
            selectedGenres.splice(selectedGenres.indexOf($(this).attr("identifier")), 1);
        } else {
            $(this).addClass("selected");
            selectedGenres.push($(this).attr("identifier"));
        }
    })

    $(".year-from").find(".selected").click(function () {
        if (!yearFromAnimate) {
            yearFromAnimate = true;
            scrollbarPosition = scrollbar.getScrollElement().scrollTop;

            if (yearFromHidden) {
                $(".year-from").addClass("selected").animate({
                    "height": "82px"
                }, 300, "linear", function () {
                    yearFromHidden = false;
                    yearFromAnimate = false;
                    $("#filter").find(".simplebar-vertical").last().css("display", "none");
                    nextClickedElement.click();
                    nextClickedElement = $();
                })
            } else {
                $(".year-from").animate({
                    "height": "28px"
                }, 300, "linear", function () {
                    yearFromHidden = true;
                    yearFromAnimate = false;
                    $("#filter").find(".simplebar-vertical").last().css("display", "block");
                    $(this).removeClass("selected");
                    nextClickedElement.click();
                    nextClickedElement = $();
                })
            }
        } else {
            nextClickedElement = $(this);
        }
    })

    $(".year-to").find(".selected").click(function () {
        if (!yearToAnimate) {
            yearToAnimate = true;
            scrollbarPosition = scrollbar.getScrollElement().scrollTop;

            if (yearToHidden) {
                $(".year-to").addClass("selected").animate({
                    "height": "82px"
                }, 300, "linear", function () {
                    yearToHidden = false;
                    yearToAnimate = false;
                    $("#filter").find(".simplebar-vertical").last().css("display", "none");
                    nextClickedElement.click();
                    nextClickedElement = $();
                })
            } else {
                $(".year-to").animate({
                    "height": "28px"
                }, 300, "linear", function () {
                    yearToHidden = true;
                    yearToAnimate = false;
                    $("#filter").find(".simplebar-vertical").last().css("display", "block");
                    $(this).removeClass("selected");
                    nextClickedElement.click();
                    nextClickedElement = $();
                })
            }
        } else {
            nextClickedElement = $(this);
        }
    })

    $(".year-from").mouseleave(function () {
        if ((!yearFromHidden && !yearFromAnimate) || (yearFromHidden && yearFromAnimate)) {
            $(this).find(".selected").click();
        }
    })

    $(".year-to").mouseleave(function () {
        if ((!yearToHidden && !yearToAnimate) || (yearToHidden && yearToAnimate)) {
            $(this).find(".selected").click();
        }
    })

    $(".year").click(function () {
        $($(this).parents(".list")[0]).find(".selected div").first().text($(this).text());
        $($(this).parents(".list")[0]).find(".selected").click();
        compareYears();
    })

    $(".arrow").click(function () {
        if (!filterAnimate) {
            filterAnimate = true;

            if (filterHidden) {
                currentScrollPosition = window.scrollY;
                window.addEventListener("scroll", noScroll);
                $("#filter").animate({
                    "left": "0"
                }, 400, "linear", function () {
                    filterAnimate = false;
                    filterHidden = false;
                    $("body").css("overflow-y", "hidden");
                    nextClickedElement.click();
                    nextClickedElement = $();
                })
                $(this).addClass("viewed");
            } else {
                window.removeEventListener("scroll", noScroll);
                $("#filter").animate({
                    "left": "-300px"
                }, 400, "linear", function () {
                    filterAnimate = false;
                    filterHidden = true;
                    $("body").css("overflow-y", "auto");
                    nextClickedElement.click();
                    nextClickedElement = $();
                })
                $(this).removeClass("viewed");
            }
        }
    })

    $("#filter").mouseleave(function () {
        if ((!filterHidden && !filterAnimate) || (filterHidden && filterAnimate)) {
            $(".arrow").click();
        }
    })

    $(".apply-btn").click(function () {
        $("#movies .curtain").css("display", "block");
        let genres = '';
        $(".genre.selected").each(function () {
            genres += $(this).attr("identifier") + ",";
        });
        genres = genres.slice(0, -1);
        let url = window.location.origin + '/movies/1?sort=' + $(".category.selected").attr("identifier") + (genres === '' ? '' : '&genres=' + genres);
        if (!isNaN(parseInt($(".year-from .selected > div").first().text()))) {
            url += '&yearFrom=' + parseInt($(".year-from .selected > div").first().text());
        }
        if (!isNaN(parseInt($(".year-to .selected > div").first().text()))) {
            url += '&yearTo=' + parseInt($(".year-to .selected > div").first().text());
        }

        $.ajax({
            url: url,
            method: 'GET'
        }).done(function (data) {
            moviesList = data.slice(1);
            $("#pages").html("");
            lastPage = data[0];
            page = parseInt(new URLSearchParams(window.location.search).get("page"));
            page = isNaN(page) ? 1 : page;
            if (lastPage < 1) {
                $("#pages").html("");
            } else {
                setPagesValues(lastPage);
                pagesAnimation();
            }
            $("#movies").html("");
            moviesGenerator();
        })
        $(".arrow").click();
    })
})

function noScroll() {
    window.scrollTo(0, currentScrollPosition);
}

function yearsGenerator(minYear, maxYear) {
    let yearsList = '<div class="year">' + notSelectedValue + '</div>';
    for (let i = maxYear; i >= minYear; i--) {
        yearsList += '<div class="year">' + i + '</div>';
    }
    $(".years-container").html(yearsList);
}

function compareYears() {
    if ($(".year-from .selected > div").first().text() > $(".year-to .selected > div").first().text()) {
        let temp = $(".year-from .selected > div").first().text();
        $(".year-from .selected > div").first().text($(".year-to .selected > div").first().text());
        $(".year-to .selected > div").first().text(temp);
    }
}

function genresChanger() {
    $(".genre").each(function () {
        switch ($(this).text()) {
            case 'ACTION':
                $(this).text(actionGenre);
                break;
            case 'COMEDY':
                $(this).text(comedyGenre);
                break;
            case 'CARTOON':
                $(this).text(cartoonGenre);
                break;
            case 'ROMANCE':
                $(this).text(romanceGenre);
                break;
            case 'CRIMINAL':
                $(this).text(criminalGenre);
                break;
            case 'SCIENCE_FICTION':
                $(this).text(scienceFictionGenre);
                break;
            case 'DOCUMENTARY':
                $(this).text(documentaryGenre);
                break;
            case 'HORROR':
                $(this).text(horrorGenre);
                break;
            case 'FANTASY':
                $(this).text(fantasyGenre);
                break;
            case 'ADVENTURE':
                $(this).text(adventureGenre);
                break;
            case 'DETECTIVE':
                $(this).text(detectiveGenre);
                break;
            case 'THRILLER':
                $(this).text(thrillerGenre);
                break;
            case 'HISTORICAL':
                $(this).text(historicalGenre);
                break;
            case 'DRAMA':
                $(this).text(dramaGenre);
                break;
        }
    })
}

function moviesGenerator() {
    let movies = "";
    let date;
    if (typeof isAdmin !== 'undefined') {
        movies += '<div class="movie add-button"><a href="/admin/movie/create"><div><div>&#10010;</div><div>' + addValue + '</div></div></a></div>'
    }
    for (let movie of moviesList) {
        date = new Date(movie.releaseDate);
        movies += '<div class="movie" identifier="' + movie.id + '"><div><img alt="" src="data:image/' + movie.posterPicture.format + ';base64,' + movie.posterPicture.pictureString + '">' +
            '</div><div class="card"><div class="name"><a href="/movie/' + movie.id + '"><div class="first-name">' + movie.name + '</div>' +
            '<div class="second-name">' + movie.surname + '</div></a></div><div class="info"><div class="info-left">';

        for (let tech in movie.availableTechnologies) {
            if (movie.availableTechnologies[tech] === '_RM_PLUS') {
                movies += '<div>' + movie.availableTechnologies[tech].slice(1).replace('_PLUS', '+') + '</div>';
            } else {
                movies += '<div>' + movie.availableTechnologies[tech].slice(1) + '</div>';
            }
        }

        movies += '</div><div class="info-right"><div>' + movie.durationInMinutes + minuteValue + '</div><div>IMDb ' + movie.imdbRating + '</div></div>' +
            '</div><div class="bottom"><div class="genres">';

        let counter = 0;
        for (let genre in movie.genres) {
            if (counter++ === 3) {
                break;
            }
            movies += '<div class="genre">' + movie.genres[genre] + '</div>';
        }

        movies += '</div>';

        if (date > new Date()) {
            movies += '<div class="date"><div class="title">' + inRentalValue + '</div><div class="rental-date"><div class="number">' +
                fromValue + ' ' + date.getDate() + '</div> <div class="month">' + monthsList[date.getMonth()] + ' ' + date.getFullYear() + '</div></div></div>';
        }

        if (typeof favouriteMovies !== 'undefined') {
            if (typeof addedFavMovies !== 'undefined') {
                if ((favouriteMovies.includes(movie.id) || addedFavMovies.includes(movie.id)) && !removedFavMovies.includes(movie.id)) {
                    movies += '<div class="like selected"><div>';
                } else {
                    movies += '<div class="like"><div>';
                }
            } else {
                if (favouriteMovies.includes(movie.id)) {
                    movies += '<div class="like selected"><div>';
                } else {
                    movies += '<div class="like"><div>';
                }
            }
        }

        movies += '</div></div></div></div></div><div class="curtain"></div>';
    }

    $("#movies").html(movies);
    genresChanger();
}