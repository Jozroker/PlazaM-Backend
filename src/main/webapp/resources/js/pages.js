let page = new URLSearchParams(window.location.search);
let path = $(location).attr("href");
// let lastPage = parseInt($(".last").text());
let url = new URL(path);
let position;

$(document).ready(function () {

    {
        page = parseInt(page.get("page"));
        page = isNaN(page) ? 1 : page;
        if (lastPage < 1) {
            $("#pages").html("");
        } else {
            setPagesValues(lastPage);
            pagesAnimation();
        }
    }

    $("#pages .page").not(".selected").click(function () {
        url.searchParams.set("page", parseInt($(this).text()));
        page = parseInt($(this).text());
        window.history.replaceState("", "", url);
        pagesAnimation();

        replaceContent(page);

    })

    $("#pages .first").click(function () {
        url.searchParams.set("page", 1);
        page = 1;
        window.history.replaceState("", "", url);
        pagesAnimation();

        replaceContent(1);
    })

    $("#pages .last").click(function () {
        url.searchParams.set("page", lastPage);
        page = lastPage;
        window.history.replaceState("", "", url);
        pagesAnimation();

        replaceContent(lastPage);
    })

    $("#pages-arrow-left").click(function () {
        if (page !== 1) {
            page--;
            url.searchParams.set("page", page);
            window.history.replaceState("", "", url);
            pagesAnimation();

            replaceContent(page);
        }
    })

    $("#pages-arrow-right").click(function () {
        if (page !== lastPage) {
            page++;
            url.searchParams.set("page", page);
            window.history.replaceState("", "", url);
            pagesAnimation();

            replaceContent(page);
        }
    })

    $("#pages .page, #pages .first, #pages .last").mouseover(function () {
        let newPos = parseInt($("#pages .pages").find("div:visible").index($(this))) - 1;
        $("#pages .line").find(".underline").css("left", newPos * 32 + "px");
    })

    $("#pages .pages").mouseleave(function () {
        $("#pages .line").find(".underline").css("left", position * 32 + "px");
    })


})

function pagesAnimation() {
    if (page <= 4) {
        if (page <= 3) {
            $("#pages .first").css("display", "none");
            $("#pages .page").last().css("display", "none");
        } else {
            $("#pages .first").css("display", "inline-block");
            $("#pages .page").last().css("display", "inline-block");
        }
        $("#pages .first-dots").css("display", "none");
    } else {
        $("#pages .first").css("display", "inline-block");
        $("#pages .page").last().css("display", "inline-block");
        $("#pages .first-dots").css("display", "inline-block");
    }

    if (page > lastPage - 4) {
        if (page > lastPage - 3) {
            $("#pages .last").css("display", "none");
            $("#pages .page").first().css("display", "none");
        } else {
            $("#pages .last").css("display", "inline-block");
            $("#pages .page").first().css("display", "inline-block");
        }
        $("#pages .last-dots").css("display", "none");
    } else {
        $("#pages .last").css("display", "inline-block");
        $("#pages .page").first().css("display", "inline-block");
        $("#pages .last-dots").css("display", "inline-block");
    }

    let pages = page - 2;
    $("#pages .pages").find(".page").each(function () {
        if (pages <= 0 || pages > lastPage) {
            $(this).css("display", "none");
        } else {
            $(this).css("display", "inline-block");
        }

        $(this).text(pages++);
    })

    $("#pages .pages").css("width", (($("#pages .pages").find("div:visible").length - 2) * 32 + 124) + "px");
    $("#pages .line").css("width", (($("#pages .pages").find("div:visible").length - 2) * 32) + "px");
    position = parseInt($("#pages .pages").find("div:visible").index($("#pages .pages").find(".selected"))) - 1;
    $("#pages .line").find(".underline").css("left", position * 32 + "px");
    setTimeout(function () {
        $("#pages .line").find(".underline").css("transition", "left 0.3s ease-in-out");
    }, 100)
}

function setPagesValues(lastPageNumber) {
    let pagesElement = '<div class="pages-container"><div class="pages"><div id="pages-arrow-left"></div><div class="first">' +
        '1</div><div class="first-dots">...</div>';
    for (let i = page - 2; i <= page + 2; i++) {
        pagesElement += '<div class="page' + ((i == page) ? ' selected' : '') + '">' + i + '</div>';
    }
    pagesElement += '<div class="last-dots">...</div><div class="last">' + lastPageNumber + '</div>' +
        '<div id="pages-arrow-right"></div></div><div class="line"><div class="underline"></div></div></div>';
    $("#pages").html(pagesElement);
}

function replaceContent(page) {
    if (window.location.pathname === '/home' || window.location.pathname === '/schedule') {
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
        let url = window.location.origin + '/page/' + page + '?cinemaId=' + cinemaId + (genres === '' ? '' : '&genres=' + genres) +
            (techs === '' ? '' : '&technologies=' + techs);
        if (window.location.pathname === '/schedule') {
            let selectedDate = $("#schedule-content .selected-date").text().trim();
            url += "&date=" + selectedDate;
        }
        if (!isNaN(page)) {
            $.ajax({
                url: url,
                method: 'GET'
            }).done(function (data) {
                mapa = data.slice(1);
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
                $("#home-schedules-containers").html("");
                $("#schedules").html("");
                for (let i = 0; i < 8; i++) {
                    $("#home-schedules-containers").append('<div class="schedule-container"></div>');
                    $("#schedules").append('<div class="schedule-container"></div>');
                }
                generateSchedule();
                $("#home-schedules-containers .curtain").css("display", "none");
                $("#schedules .curtain").css("display", "none");
            })
        }
    } else if (window.location.pathname === '/movies') {
        $("#movies .curtain").css("display", "block");
        let genres = '';
        $(".genre.selected").each(function () {
            genres += $(this).attr("identifier") + ",";
        });
        genres = genres.slice(0, -1);
        let url = window.location.origin + '/movies/' + page + '?sort=' + $(".category.selected").attr("identifier") + (genres === '' ? '' : '&genres=' + genres);
        if (!isNaN(parseInt($(".year-from .selected > div").first().text()))) {
            url += '&yearFrom=' + parseInt($(".year-from .selected > div").first().text());
        }
        if (!isNaN(parseInt($(".year-to .selected > div").first().text()))) {
            url += '&yearTo=' + parseInt($(".year-to .selected > div").first().text());
        }
        if (window.location.search.indexOf("userId") > 0) {
            let searchParams = new URL(document.location).searchParams;
            url += '&userId=' + searchParams.get("userId") + "&type=" + searchParams.get("type");
        }
        if (!isNaN(page)) {
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
        }
    } else if (window.location.pathname === '/user/comments') {
        $("#comments .curtain").css("display", "block");
        let url = window.location.origin + '/user/comments/' + page + '?sort=' + $(".category.selected").attr("identifier");
        if (!isNaN(page)) {
            $.ajax({
                url: url,
                method: 'GET'
            }).done(function (data) {
                commentsList = JSON.parse(data)[1];
                $("#pages").html("");
                lastPage = JSON.parse(data)[0];
                page = parseInt(new URLSearchParams(window.location.search).get("page"));
                page = isNaN(page) ? 1 : page;
                if (lastPage < 1) {
                    $("#pages").html("");
                } else {
                    setPagesValues(lastPage);
                    pagesAnimation();
                }
                $("#comments").html("");
                generateComments(commentsList);
            })
        }
    } else if (window.location.pathname === '/admin/users') {
        $("#users .curtain").css("display", "block");
        let roles = '', countries = '', banStatuses = '', name = $("#user-search-line").val().trim();
        $("#filter .role.selected").each(function () {
            roles += $(this).attr("identifier") + ',';
        });
        roles = roles.slice(0, -1);
        $("#filter .country.selected").each(function () {
            countries += $(this).attr("identifier") + ',';
        });
        countries = countries.slice(0, -1);
        $("#filter .ban-status.selected").each(function () {
            banStatuses += $(this).attr("identifier") + ',';
        });
        banStatuses = banStatuses.slice(0, -1);
        let url = window.location.origin + '/admin/users/' + page + '?sort=' + $(".sort .category.selected").attr("identifier") + (roles === '' ? '' : '&roles=' + roles) +
            (countries === '' ? '' : '&countries=' + countries) + (banStatuses === '' ? '' : '&banStatuses=' + banStatuses) + (name === '' ? '' : '&name=' + name);
        $.ajax({
            url: url,
            method: 'GET'
        }).done(function (data) {
            allUsersLastPage = JSON.parse(data)[0];
            reportedUsersLastPage = JSON.parse(data)[2];
            bannedUsersLastPage = JSON.parse(data)[4];
            $("#pages").html("");
            if ($("#users .nav-item.selected").attr("identifier") === 'ALL') {
                lastPage = allUsersLastPage;
            } else if ($("#users .nav-item.selected").attr("identifier") === 'REPORTED') {
                lastPage = reportedUsersLastPage;
            } else {
                lastPage = bannedUsersLastPage;
            }
            if (lastPage < 1) {
                $("#pages").html("");
            } else {
                setPagesValues(lastPage);
                pagesAnimation();
            }
            generateAllUsers(JSON.parse(data)[1]);
            generateReportedUsers(JSON.parse(data)[3]);
            generateBannedUsers(JSON.parse(data)[5]);
            $("#users .curtain").css("display", "none");
        })
    }
}