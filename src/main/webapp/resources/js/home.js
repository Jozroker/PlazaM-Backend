$(document).ready(function () {
    let selectedMovie = $("#movies-in-route .movie.selected").first();
    let selectedCircle = $("#movies-in-route .circle.selected").first();
    let index = $("#movies-in-route .circle").index($("#movies-in-route .circle.selected"));

    let timer;
    let movieRouteAnimate = false;
    let nextClickedElement = $();

    {
        generateComingSoon();
        $("#movies-in-route .movie.selected").find(".name").css("left", "9.38%");

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
            $.getScript("/resources/js/movie_schedule.js");
        })

        $.ajax({
            url: window.location.origin + '/filter',
            method: "GET"
        }).done(function (page) {
            $("#filter").html(page);
            // $.getScript("../js/footer.js");
            $.getScript("/resources/js/filter.js");
        })
    }

    $(document).on("click", "#movies-in-route .circle", function () {
        if (!$(this).hasClass("selected")) {
            index = $("#movies-in-route .circle").index($(this));
            loop(false);
            slide(true, true);
        }
    })

    $(document).on("click", "#movies-in-route .borders", function () {
        loop(false);
        slide(false, true);
    })

    function slide(circleSelected, userClick) {
        if (!movieRouteAnimate) {
            movieRouteAnimate = true;
            if (!circleSelected) {
                if (selectedMovie.next().length == 0) {
                    index = 0;
                } else {
                    index++;
                }
            }

            $("#movies-in-route .movie.selected").find(".name").animate({
                "left": "-100vw"
            }, 300, "easeInOutQuint", function () {
                selectedMovie.removeClass("selected");
                selectedCircle.removeClass("selected");
                $($("#movies-in-route .movie")[index]).addClass("selected");
                $($("#movies-in-route .circle")[index]).addClass("selected");

                setTimeout(function () {
                    $("#movies-in-route .movie.selected").find(".name").animate({
                        "left": "9.38%"
                    }, 300, "easeInOutQuint", function () {
                        selectedMovie = $("#movies-in-route .movie.selected").first();
                        selectedCircle = $("#movies-in-route .circle.selected").first();
                        movieRouteAnimate = false;
                        // nextClickedElement.click();
                        // nextClickedElement = $();
                        if (userClick) {
                            loop(true);
                        }
                    });
                }, 1000);
            })
        } else {
            // if (circleSelected) {
            //     nextClickedElement = $($("#movies-in-route .circle")[index]);
            // } else {
            //     nextClickedElement = $("#movies-in-route .borders");
            // }
            //todo fix this trouble
        }
    }

    function loop(repeat) {
        if (repeat) {
            timer = setTimeout(function () {
                slide(false, false);
                loop(true);
            }, 10000);
        } else {
            clearTimeout(timer);
        }
    }

    function generateComingSoon() {
        $.ajax({
            url: window.location.origin + '/coming-soon',
            method: 'GET'
        }).done(function (movies) {
            let comingSoonMovies = "";
            let releaseDate, releaseMonth;
            for (let movie in movies) {
                releaseDate = parseInt(movies[movie].releaseDate.substr(8, 2));
                releaseMonth = parseInt(movies[movie].releaseDate.substr(5, 2));
                comingSoonMovies += '<div><a href="/movie/' + movies[movie].id + '"><div class="movie-coming">' +
                    '<div class="pg-rating">' + movies[movie].mpaaRating.replace("_", "-") + '</div><div class="user-rating"><div>' +
                    '<div>' + movies[movie].usersRating + '</div></div></div><div class="start-date">' + fromLabel + ' ' + releaseDate + ' ' + monthsList[releaseMonth - 1] +
                    '</div><img alt="" src="data:image/' + movies[movie].posterPicture.format + ';base64,' + movies[movie].posterPicture.pictureString + '">' +
                    '</div></a></div>';
            }
            loop(true);
            $("#coming-soon").html(comingSoonMovies).slick({
                slidesToShow: 5,
                slidesToScroll: 1,
                // autoplay: true,
                autoplaySpeed: 0,
                speed: 5000,
                pauseOnHover: false,
                cssEase: 'linear',
                arrows: false,
                draggable: false,
                touchMove: false,
                swipe: false,
                accessibility: false
            });
        })
    }
})