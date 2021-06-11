$(document).ready(function () {

    {
        generateGenres();
        generateTechnologies();
    }

    $("#filter .scroll").each(function (index) {
        new SimpleBar($("#filter .scroll")[index], {
            autoHide: false
        });
    })

    $(document).on("click", ".genre, .tech", function () {
        if ($(this).is(".selected")) {
            $(this).removeClass("selected");
        } else {
            $(this).addClass("selected");
        }
        $("#home-schedules-containers .curtain").css("display", "block");
        $("#schedules .curtain").css("display", "block");
        let genres = '', techs = '';
        $(".genre.selected").each(function () {
            genres += $(this).attr("identifier") + ",";
        })
        $(".tech.selected").each(function () {
            techs += $(this).text().trim() + ",";
        })
        genres = genres.slice(0, -1);
        techs = techs.slice(0, -1);
        let pagesUrl = window.location.origin + '/page/' + page + '?cinemaId=' + cinemaId + (genres === '' ? '' : '&genres=' + genres) +
            (techs === '' ? '' : '&technologies=' + techs);
        // let countUrl = window.location.origin + '/page/count' + '?cinemaId=' + cinemaId + (genres === '' ? '' : '&genres=' + genres) +
        //     (techs === '' ? '' : '&technologies=' + techs);
        if (window.location.pathname === '/schedule') {
            let selectedDate = $("#schedule-content .selected-date").text().trim();
            pagesUrl += "&date=" + selectedDate;
            // countUrl += "&date=" + selectedDate;
        }

        $.ajax({
            url: pagesUrl,
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
            $("#home-schedules-containers").html("");
            $("#schedules").html("");
            for (let i = 0; i < 8; i++) {
                $("#home-schedules-containers").append('<div class="schedule-container"></div>');
                $("#schedules").append('<div class="schedule-container"></div>');
            }
            generateSchedule();
            // let parent = $($(this).parents(".movie-schedule")[0]);
            // animateSchedule(parent)
            $("#home-schedules-containers .curtain").css("display", "none");
            $("#schedules .curtain").css("display", "none");
        })
    })

    function generateGenres() {
        let genresList = "";
        $.ajax({
            url: window.location.origin + "/genres",
            method: "GET"
        }).done(function (data) {
            let genres = JSON.parse(data);
            for (let genre in genres) {
                genresList += ' <div><div identifier="' + genre + '" class="genre">' + genres[genre] + '</div></div>';
            }
            $("#filter .left-side .content").html(genresList);
        })
    }

    function generateTechnologies() {
        let techsList = "";
        $.ajax({
            url: window.location.origin + "/technologies",
            method: "GET"
        }).done(function (techs) {
            for (let tech in techs) {
                techsList += ' <div><div class="tech">' + techs[tech] + '</div></div>';
            }
            $("#filter .right-side .content").html(techsList);
        })
    }
})