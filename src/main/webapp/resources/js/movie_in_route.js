$(document).ready(function () {
    // let hallSelectHidden = true;
    // let cinemaSelectHidden = true;
    // let dateFromHidden = true;
    // let dateToHidden = true;
    // let scheduleCreationHidden = true;
    //
    // let hallSelectAnimate = false;
    // let cinemaSelectAnimate = false;
    // let scheduleCreationAnimate = false;
    // let dateFromAnimate = false;
    // let dateToAnimate = false;

    let nextClickedElement = $();
    let calendarDateClick = false;
    let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
        "November", "December"];
    let stringDate = "";
    let currentScrollPosition;

    // let priceLoop = false;
    // let loop;
    // let speed = 200;

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
        $(".comments .scroll").css("height", $(".gallery .slider").css("height"));
        // $("#timepicker").timepicker({
        //     template: false,
        //     minuteStep: 5,
        //     showSeconds: false,
        //     showMeridian: false
        // });

        $.ajax({
            url: window.location.origin + '/footer',
            method: "GET"
        }).done(function (page) {
            $("#footer-container").html(page);
            $.getScript("/resources/js/footer.js");
        })

        $.ajax({
            url: window.location.origin + '/header?path=' + window.location.pathname,
            method: "GET"
        }).done(function (page) {
            $("#header-container").html(page);
            $.getScript("/resources/js/header.js");
            $.getScript("/resources/js/movie_schedule.js");
            genresChanger();
        })

        if (typeof isAdmin !== 'undefined') {
            let url = window.location.origin + '/admin/movie/schedule-creation';
            if (typeof seanceId !== 'undefined') {
                url += '?seanceId=' + seanceId;
            }
            $.ajax({
                url: url,
                method: 'GET'
            }).done(function (page) {
                $("#schedule-creation").html(page);
                $.getScript("/resources/js/schedule-creation.js");
                $.getScript("/resources/js/calendar.js");
            })
        }
    }

    $("#movie .scroll").each(function (index) {
        new SimpleBar($("#movie .scroll")[index], {
            autoHide: false
        });
    })

    $(window).resize(function () {
        setTimeout(function () {
            $(".actors .scroll").css("height", $(".description .text").css("height"));
            $(".comments .scroll").css("height", $(".gallery .slider").css("height"));
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
            let commentElement = '<div class="comment" id="' + comment.id + '">\<div class="user-info"><div class="avatar"><img alt=""' +
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

    // $(".button.time-button .change-schedule").click(function () {
    //     if ($(this).attr("action") == "create") {
    //         $("#schedule-creation .title").first().text("Schedule creation");
    //     } else {
    //         if ($(".button.time-button .title .value").text() == $(".button.time-button .time").first().text()) {
    //             $(".button.time-button .title .value").css("color", "#AF2341");
    //         } else {
    //             if ($(".button.hall-button .title .value").text() == $(".button.hall-button .hall").first().text()) {
    //                 $(".button.hall-button .title .value").css("color", "#AF2341");
    //             } else {
    //                 $("#schedule-creation .title").first().text("Schedule modification");
    //                 let time = "" + $(".button.time-button .title .value").text().slice(0, 2) + ":" +
    //                     $(".button.time-button .title .value").text().slice(-2);
    //                 $("#timepicker").val(time);
    //                 let hall = $(".button.hall-button .title .value").text();
    //                 $(".hall-select .list").find(".selected > div").first().text(hall);
    //             }
    //         }
    //     }
    //     if (scheduleCreationHidden) {
    //         $("#add-schedule").click();
    //     }
    // })

    // $(".seance .change").click(function () {
    //     $("#schedule-creation .title").first().text("Schedule modification");
    //     let seanceTime = $(this).parents(".seance").first().find(".value").text();
    //     let time = seanceTime.slice(0, seanceTime.indexOf("h")) + ":" + seanceTime.slice(seanceTime.indexOf("h") + 2,
    //         -1);
    //     if (time[1] == ":") {
    //         time = "0" + time;
    //     }
    //     if (time[time.indexOf(":") + 1] == "0") {
    //         time += "0";
    //     }
    //     $("#timepicker").val(time);
    //     let hall = $(this).parents(".hall").first().find(".name").text();
    //     $(".hall-select .list").find(".selected > div").first().text(hall);
    //     if (scheduleCreationHidden) {
    //         $("#add-schedule").click();
    //     }
    // })

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