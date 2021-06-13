$(document).ready(function () {
    let calendarHidden = true;
    let calendarAnimate = false;
    let nextClickedElement = $();
    let calendarDateClick, stringDate;

    {
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
            $.getScript("/resources/js/calendar.js");
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

    $(document).on("click", ".calendar-section .calendar .date", function () {
        if (!calendarHidden) {
            calendarDateClick = true;
            let newDate = new Date();
            $("#schedules .curtain").css("display", "block");
            newDate.setFullYear(parseInt($(".calendar-section .calendar .header-label").text().trim().slice(-4)));
            newDate.setMonth(parseInt(monthsCalendarList.indexOf($(".calendar-section .calendar .header-label").text().trim().slice(0, -5))));
            newDate.setDate(parseInt($(this).text().trim()));

            stringDate = "" + (newDate.getDate() / 10 >= 1 ? newDate.getDate() : "0" + newDate.getDate());
            stringDate += "." + ((newDate.getMonth() + 1) / 10 >= 1 ? newDate.getMonth() + 1 : "0" + (newDate.getMonth() + 1));
            stringDate += "." + (newDate.getFullYear());

            let genres = '', techs = '';
            $(".genre.selected").each(function () {
                genres += $(this).attr("identifier") + ",";
            })
            $(".tech.selected").each(function () {
                techs += $(this).text().trim() + ",";
            })
            genres = genres.slice(0, -1);
            techs = techs.slice(0, -1);

            $(".calendar-section .selected-date").text(stringDate);
            $(".date-top").click();
            if (!isNaN(page)) {
                if (typeof startLoading !== 'undefined') {
                    startLoading();
                }
                $.ajax({
                    url: window.location.origin + '/schedule/page/' + page + '?cinemaId=' + cinemaId + (genres === '' ? '' : '&genres=' + genres) +
                        (techs === '' ? '' : '&technologies=' + techs) + '&date=' + stringDate,
                    method: 'GET'
                }).done(function (data) {
                    mapa = data.slice(1);
                    $("#pages").html("");
                    lastPage = data[0][0];
                    page = parseInt(new URLSearchParams(window.location.search).get("page"));
                    page = isNaN(page) ? 1 : page;
                    if (lastPage < 1) {
                        $("#pages").html("");
                    } else {
                        setPagesValues(lastPage);
                        pagesAnimation();
                    }
                    $("#schedules").html("");
                    for (let i = 0; i < 8; i++) {
                        $("#home-schedules-containers").append('<div class="schedule-container"></div>');
                        $("#schedules").append('<div class="schedule-container"></div>');
                    }
                    generateSchedule(new Date(stringDate.slice(-4) + '-' + stringDate.slice(3, 5) + '-' + stringDate.slice(0, 2)));
                    $("#schedules .curtain").css("display", "none");
                })
            }
        }
    })

    $(".date-top").click(function () {
        if (!calendarAnimate) {
            calendarAnimate = true;

            if (calendarHidden) {
                $(".calendar-section").find(".triangle").addClass("triangle-0");
                $(".calendar-section .content").animate({
                    "width": "284px"
                }, 300, "easeInOutQuint").delay(300).animate({
                    "height": "218px"
                }, 300, "easeInOutQuint", function () {
                    calendarAnimate = false;
                    calendarHidden = false;
                    nextClickedElement.click();
                    nextClickedElement = $();
                });
            } else {
                let delay = calendarDateClick ? 200 : 0;
                $(".calendar-section").find(".triangle").delay(delay).removeClass("triangle-0");
                $(".calendar-section .content").delay(delay).animate({
                    "height": "34px"
                }, 300, "easeInOutQuint").delay(300).animate({
                    "width": "155px"
                }, 300, "easeInOutQuint", function () {
                    calendarAnimate = false;
                    calendarHidden = true;
                    calendarDateClick = false;
                    nextClickedElement.click();
                    nextClickedElement = $();
                });
            }
        } else {
            nextClickedElement = $(this);
        }
    })

    $(".calendar-section").mouseleave(function () {
        if (!calendarHidden && !calendarAnimate) {
            $(".date-top").click();
        }
    })
})