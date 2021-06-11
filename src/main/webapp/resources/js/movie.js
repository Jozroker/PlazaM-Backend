$(document).ready(function () {
    {
        $("#pictures").slick({
            slidesToShow: 1,
            slidesToScroll: 1,
            // autoplay: true,
            autoplaySpeed: 0,
            speed: 300,
            pauseOnHover: false,
            cssEase: 'ease-in-out',
            arrows: false,
            draggable: false,
            touchMove: false,
            swipe: false,
            accessibility: false
        })

        $(".actors .scroll").css("height", $(".description .text").css("height"));
        $(".comments .scroll").first().css("height", $(".gallery .slider").css("height"));

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
        })

        if (typeof isAdmin !== 'undefined') {
            $.ajax({
                url: window.location.origin + '/admin/movie/schedule-creation',
                method: 'GET'
            }).done(function (page) {
                $("#schedule-creation").html(page);
                $.getScript("/resources/js/schedule-creation.js");
                $.getScript("/resources/js/calendar.js");
            })
        }

        genresChanger();
    }

    $("#movie .scroll").each(function (index) {
        new SimpleBar($("#movie .scroll")[index], {
            autoHide: false
        });
    })

    $("textarea").on("input", function () {
        $(this).css("height", "auto").delay(10).css("height",
            $(this).get(0).scrollHeight + "px");
    })

    $(window).resize(function () {
        setTimeout(function () {
            $(".actors .scroll").css("height", $(".description .text").css("height"));
            $(".comments .scroll").first().css("height", $(".gallery .slider").css("height"));
        }, 100);
    })

    $(".like").click(function () {
        let id = $("#movie").attr("identifier");
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

    $("#info .star").click(function () {
        if (!$("#info .user-rating").hasClass("selected")) {
            $("#info .user-rating").addClass("selected");

            $.ajax({
                url: window.location.origin + '/movie/' + $("#movie").attr("identifier") + '/add/rating?rating=' + $("#info .user-rating .star.full").length,
                method: 'POST'
            }).done(function (id) {
                $("#info .user-rating").attr("identifier", id);
            })
        }
    })

    $("#info .star").mouseover(function () {
        if (!$("#info .user-rating").hasClass("selected")) {
            $("#info .star").removeClass("full");
            for (let i = 0; i <= $("#info .star").index($(this)); i++) {
                $($("#info .star")[i]).addClass("full");
            }
        }
    })

    $("#info .user-rating").mouseleave(function () {
        if (!$("#info .user-rating").hasClass("selected")) {
            $("#info .star").removeClass("full");
        }
    })

    $(".gallery .left-arrow").click(function () {
        $("#pictures").slick("slickPrev");
    })

    $(".gallery .right-arrow").click(function () {
        $("#pictures").slick("slickNext");
    })

    $(".add-comment").click(function () {
        if ($(".comment-form").css("bottom") == "-164px") {
            $(".comment-form").css("bottom", "0");
        } else {
            $(".comment-form").css("bottom", "-164px");
        }
    })

    $(".comment-btn").click(function () {
        let url = window.location.origin + '/movie/' + $("#movie").attr("identifier") + '/add/comment?text=' + $(this).parents(".comment-form").first().find("textarea").val();
        if ($(".user-rating")[0].hasAttribute("identifier")) {
            url += '&ratingId=' + $(".user-rating").attr("identifier");
        }
        $.ajax({
            url: url,
            method: 'POST'
        }).done(function (data) {
            let comment = JSON.parse(data);
            let commentElement = '<div class="comment" identifier="' + comment.id + '">\<div class="user-info"><div class="avatar"><img alt=""' +
                'src="data:image/' + comment.user.picture.format + ';base64,' + comment.user.picture.pictureString + '></div><div class="info"><div class="user">' +
                '<div><div class="flag"><img alt="" src="' + comment.user.country.flagPicture + '"></div><div class="username">' + comment.user.username + '</div>' +
                '</div><div class="date">' + comment.date.slice(-2) + '.' + comment.date.slice(5, 7) + '.' + comment.date.slice(0, 4) + '</div></div>' +
                '<div class="other"><div class="user-rating">';

            if (comment.userRating == null) {
                commentElement += '<div>' + notRatedValue + '</div>';
            } else {
                for (let i = 1; i < 6; i++) {
                    if (comment.userRating.userRating >= i) {
                        commentElement += '<div class="star full"></div>';
                    } else {
                        commentElement += '<div class="star"></div>';
                    }
                }
            }

            commentElement += '</div><div class="complain-btn">' + complainValue + '</div></div></div></div><div class="text">' + comment.text + '</div></div>';
        })

    })

    $(document).on("click", ".complain-btn", function () {
        $(this).css("display", "none");
        let id = $(this).parents(".comment").first().attr("identifier");

        $.ajax({
            url: window.location.origin + '/comment/' + id + '/complain',
            method: 'POST'
        })
    })

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
})