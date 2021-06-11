nextClickedElement = $();

$(document).ready(function () {

    let filterHidden = true;
    let banListHidden = true;

    let filterAnimate = false;
    let banListAnimate = false;

    {
        $(".sort .underline").css("width", $(".category.selected").first().width() + 14 + "px");

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
            $("#header-container").html(page);
            $.getScript("/resources/js/header.js");
            $.getScript("/resources/js/pages.js");
        })
    }

    $("#filter .scroll").each(function (index) {
        new SimpleBar($("#filter .scroll")[index], {
            autoHide: false
        });
    })

    $(".users-page-container .scroll").each(function (index) {
        new SimpleBar($(".users-page-container .scroll")[index], {
            autoHide: false
        });
    })

    $(".category, #user-search-icon").click(function () {
        if (!$(this).hasClass("selected") && typeof setPagesValues !== 'undefined') {
            if ($(this).hasClass("category")) {
                $(".categories").find(".selected").removeClass("selected");
                $(this).addClass("selected");
                $(".sort .underline").animate({
                    left: $(this).position().left + "px",
                    width: $(this).width() + 14 + "px"
                }, 300, "easeInOutQuint");
            }
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
            let url = window.location.origin + '/admin/users/1?sort=' + $(".sort .category.selected").attr("identifier") + (roles === '' ? '' : '&roles=' + roles) +
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
    })

    $(document).on("click", ".selected-role", function () {
        if ($(this).parent().css("height") == "18px") {
            $(this).parent().find(".triangle").addClass("triangle-0");
            $(this).parent().css("background-color", "rgba(85, 85, 85, 0.2)").animate({
                "height": "75px"
            }, 300, "linear");
        } else {
            $(this).parent().find(".triangle").removeClass("triangle-0");
            $(this).parent().css("background-color", "transparent").animate({
                "height": "18px"
            }, 300, "linear");
        }
    })

    $(document).on("click", ".user-right-side .role li:not(.selected-role)", function () {
        $.ajax({
            url: window.origin.origin + '/admin/user/' + $(this).parents(".user").first().attr("identifier") + '/' + $(this).attr("identifier"),
            method: 'POST'
        }).done(function (data) {
            if (data === 'success') {
                $(this).parent().find(".selected-role > div:first-child").text($(this).text().trim());
                $(this).parent().find(".selected-role").click();
            }
        })
    })

    $("#filter .country, #filter .ban-status, #filter .role").click(function () {
        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            $(this).addClass("selected");
        }
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
                    // $("body").css("overflow-y", "hidden");
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
                    // $("body").css("overflow-y", "auto");
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
        $(".arrow").click();
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
        let url = window.location.origin + '/admin/users/1?sort=' + $(".sort .category.selected").attr("identifier") + (roles === '' ? '' : '&roles=' + roles) +
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
    })

    $("#users .nav-item").click(function () {
        if (!$(this).hasClass("selected") && typeof setPagesValues !== 'undefined') {
            $(this).parent().find(".selected").removeClass("selected");
            $(this).addClass("selected");
            $(".users-container").animate({
                "left": "-" + $("#users").find(".nav-item").index($(this)) * 100 + "%"
            }, 500, "easeInOutQuint");
            $("#pages").html("");
            if ($(this).attr("identifier") === 'ALL') {
                lastPage = allUsersLastPage;
            } else if ($(this).attr("identifier") === 'REPORTED') {
                lastPage = reportedUsersLastPage;
            } else {
                lastPage = bannedUsersLastPage;
            }
            page = parseInt(new URLSearchParams(window.location.search).get("page"));
            page = isNaN(page) ? 1 : page;
            if (lastPage < 1) {
                $("#pages").html("");
            } else {
                setPagesValues(lastPage);
                pagesAnimation();
            }
        }
    })

    $(document).on("click", ".ban-button .text", function () {
        if (!banListAnimate) {
            banListAnimate = true;

            if (banListHidden) {
                $(this).parent().removeClass("selectable").addClass("selected").delay(200).addClass("selectable");
                $(this).parent().find(".selection").animate({
                    "height": "112px"
                }, 500, "easeInOutQuint", function () {
                    banListAnimate = false;
                    banListHidden = false;
                    nextClickedElement.click();
                    nextClickedElement = $();
                })
            } else {
                $(this).parent().removeClass("selectable selected").delay(200).addClass("selectable");
                $(this).parent().find(".selection").animate({
                    "height": "0"
                }, 500, "easeInOutQuint", function () {
                    banListAnimate = false;
                    banListHidden = true;
                    nextClickedElement.click();
                    nextClickedElement = $();
                })
            }
        } else {
            nextClickedElement = $(this);
        }
    })

    $(document).on("mouseleave", ".ban-button", function () {
        if ($(this).hasClass("selected")) {
            $(this).find(".text").click();
        }
    })

    $(document).on("click", ".skip-button", function () {
        let currentElement = $(this);
        $.ajax({
            url: window.location.origin + '/admin/comment/' + $(currentElement).parents(".user").first().find(".comment").first().attr("identifier") + '/skip',
            method: 'POST'
        }).done(function (data) {
            if (data === 'success') {
                $(currentElement).parents(".user").animate({
                    "opacity": "0"
                }, 500, "linear", function () {
                    $(currentElement).remove();
                })
            }
        })
    })

    $(document).on("click", ".ban", function () {
        let currentElement = $(this);
        $(this).parents(".ban-button").find(".selection").animate({
            "height": "0"
        }, 500, "linear");

        //todo write trigger to database, and then, if writing is success - callback parameters and creating in Banned section
        let element = '<div class="button unban-button selectable"><div class="text">Unban</div></div>';
        let id = $(this).parents(".user").first().attr("identifier");
        let query = ".user[identifier='" + id + "']";
        let image = $("#users .all-users").find(query).first().find("img").attr("src");
        let username = $("#users .all-users").find(query).first().find(".username .value").text();
        let email = $("#users .all-users").find(query).first().find(".email .value").text();
        let phone = $("#users .all-users").find(query).first().find(".phone .value").text();
        let bannedToDate = new Date();
        switch ($(this).index()) {
            case 0:
                bannedToDate.setDate(bannedToDate.getDate() + 1);
                bannedToDate = bannedToDate.toLocaleDateString();
                break;
            case 1:
                bannedToDate.setDate(bannedToDate.getDate() + 7);
                bannedToDate = bannedToDate.toLocaleDateString();
                break;
            case 2:
                bannedToDate.setDate(bannedToDate.getDate() + 30);
                bannedToDate = bannedToDate.toLocaleDateString();
                break;
            case 3:
                bannedToDate.setDate(bannedToDate.getDate() + 365);
                bannedToDate = bannedToDate.toLocaleDateString();
                break;
            case 4:
                bannedToDate = foreverValue;
                break;
            default:
                break;
        }

        $.ajax({
            url: window.location.origin + '/admin/user/' + id + '/ban' + (bannedToDate === foreverValue ? '' : '?dateTo=' +
                $(currentElement).attr("identifier")),
            method: 'POST'
        }).done(function (data) {
            if (data === 'success') {
                $("#users .all-users, #users .banned-users").find(query).find(".user-left-side").first().append(element);
                $("#users .all-users, #users .banned-users").find(query).find(".ban-button").remove();

                let bannedUser = '<div identifier="' + id + '" class="user"><div class="user-left-side"><img src="' + image +
                    '" alt=""><div class="button unban-button selectable"><div class="text">' + unbanValue + '</div></div></div>' +
                    '<div class="user-right-side"><div class="fields"><div class="field username"><div class="title-2">' + usernameValue + '</div>' +
                    '<div class="value">' + username + '</div></div><div class="field email"><div class="title-2">' + emailValue + '</div>' +
                    '<div class="value">' + email + '</div></div><div class="field phone"><div class="title-2">' + phoneValue + '</div>' +
                    '<div class="value">' + phone + '</div></div><div class="field banned-to-date"><div class="title-2">' + bannedToValue + '</div>' +
                    '<div class="value">' + bannedToDate + '</div></div></div></div></div>';
                $("#users .banned-users").prepend(bannedUser);

                if ($("#users .navbar .selected").index() == 1) {
                    $(currentElement).parents(".user").animate({
                        "opacity": "0"
                    }, 500, "linear", function () {
                        $(this).remove();
                    })
                } else {
                    $("#users .reported-users").find(query).remove();
                    banListHidden = true;
                    banListAnimate = false;
                }
            }
        })
    })

    $(document).on("click", ".unban-button", function () {
        let currentElement = $(this);
        $.ajax({
            url: window.location.origin + '/admin/user/' + $(currentElement).parents(".user").first().attr("identifier") + '/unban',
            method: 'POST'
        }).done(function (data) {
            if (data === 'success') {
                let element = '<div class="button ban-button selectable"><div class="text">' + banValue + '</div><div class="selection">' +
                    '<div identifier="1" class="ban">1 ' + dayValue + '</div><div identifier="7" class="ban">1 ' + weekValue + '</div>' +
                    '<div identifier="30" class="ban">1 ' + monthValue + '</div><div identifier="365" class="ban">1 ' + yearValue + '</div>' +
                    '<div identifier="forever" class="ban">' + foreverValue + '</div></div></div>';
                let query = ".user[identifier='" + $(currentElement).parents(".user").first().attr("identifier") + "']";
                if ($("#users .navbar .selected").index() === 0) {
                    $("#users .banned-users").find(query).remove();
                    $(currentElement).parents(".user-left-side").first().append(element);
                    $(currentElement).remove();
                } else {
                    $(currentElement).parents(".user").animate({
                        "opacity": "0"
                    }, 500, "linear", function () {
                        $(this).remove();
                    })
                    $("#users .all-users").find(query).find(".user-left-side").first().append(element);
                    $("#users .all-users").find(query).find(".unban-button").remove();
                }
            }
        })
    })

    function noScroll() {
        window.scrollTo(0, currentScrollPosition);
    }
})

function generateAllUsers(users) {
    let allUsersElem = '';
    for (let user in users) {
        allUsersElem += '<div class="user" identifier="' + users[user].id + '"><div class="user-left-side"><img alt="" src="data:image/' + users[user].picture.format +
            ';base64,' + users[user].picture.pictureString + '">';

        if (users[user].banned) {
            allUsersElem += '<div class="button unban-button selectable"><div class="text">' + unbanValue + '</div></div>';
        } else {
            allUsersElem += '<div class="ban-button selectable"><div class="text">' + banValue + '</div><div class="selection">' +
                '<div identifier="1" class="ban">1 ' + dayValue + '</div><div identifier="7" class="ban">1 ' + weekValue + '</div><div identifier="30" class="ban">1 ' +
                monthValue + '</div><div identifier="365" class="ban">1 ' + yearValue + '</div><div identifier="forever" class="ban">' + foreverValue + '</div></div></div>';
        }
        allUsersElem += '</div><div class="user-right-side"><div class="fields"><div class="field username"><div class="title-2">' + usernameValue + '</div>' +
            '<div class="value">' + users[user].username + '</div></div><div class="field email"><div class="title-2">' + emailValue + '</div><div class="value">' +
            users[user].email + '</div></div><div class="field first-name"><div class="title-2">' + firstNameValue + '</div><div class="value">' + users[user].firstName +
            '</div></div><div class="field last-name"><div class="title-2">' + lastNameValue + '</div><div class="value">' + users[user].lastName + '</div></div>' +
            '<div class="field role"><div class="title-2">' + roleValue + '</div><ul class="list"><li class="selected-role">';

        if (users[user].role === 'ADMIN') {
            allUsersElem += '<div identifier="ADMIN">' + adminValue + '</div>';
        } else if (users[user].role === 'WORKER') {
            allUsersElem += '<div identifier="WORKER">' + workerValue + '</div>';
        } else {
            allUsersElem += '<div identifier="USER">' + userValue + '</div>';
        }

        allUsersElem += '<div class="triangle"></div></li><li identifier="ADMIN">' + adminValue + '</li><li identifier="WORKER">' + workerValue + '</li>' +
            '<li identifier="USER">' + userValue + '</li></ul></div><div class="field phone"><div class="title-2">' + phoneValue + '</div><div class="value">' +
            users[user].phone + '</div></div></div></div></div>';
    }
    $(".users-container .all-users").html(allUsersElem);
}

function generateReportedUsers(users) {
    let reportedUsersElem = '';
    for (let user in users) {
        reportedUsersElem += '<div class="user" identifier="' + users[user].user.id + '"><div class="user-left-side"><img alt="" src="data:image/' +
            users[user].user.picture.format + ';base64,' + users[user].user.picture.pictureString + '"><div class="button ban-button selectable"><div class="text">' +
            banValue + '</div><div class="selection"><div identifier="1" class="ban">1 ' + dayValue + '</div><div identifier="7" class="ban">1 ' + weekValue +
            '</div><div identifier="30" class="ban">1 ' + monthValue + '</div><div identifier="365" class="ban">1 ' + yearValue + '</div><div identifier="forever"' +
            ' class="ban">' + foreverValue + '</div></div></div><div class="button skip-button"><div class="text">' + skipValue + '</div></div></div>' +
            '<div class="user-right-side"><div class="fields"><div class="field username"><div class="title-2">' + usernameValue + '</div><div class="value">' +
            users[user].user.username + '</div></div><div class="field comment" identifier="' + users[user].id + '"><div class="title-2">' + commentValue +
            '</div><div class="value">' + users[user].text + '</div></div></div></div></div>';
    }
    $(".users-container .reported-users").html(reportedUsersElem);
}

function generateBannedUsers(users) {
    let bannedUsersElem = '';
    for (let user in users) {
        bannedUsersElem += '<div class="user" identifier="' + users[user].id + '"><div class="user-left-side"><img alt="" src="data:image/' +
            users[user].picture.format + ';base64,' + users[user].picture.pictureString + '"><div class="button unban-button selectable"><div class="text">' +
            unbanValue + '</div></div></div><div class="user-right-side"><div class="fields"><div class="field username"><div class="title-2">' + usernameValue +
            '</div><div class="value">' + users[user].username + '</div></div><div class="field email"><div class="title-2">' + emailValue + '</div>' +
            '<div class="value">' + users[user].email + '</div></div><div class="field phone"><div class="title-2">' + phoneValue + '</div><div class="value">' +
            users[user].phone + '</div></div><div class="field banned-to-date"><div class="title-2">' + bannedToValue + '</div><div class="value">';

        if (typeof users[user].bannedTo === 'undefined') {
            bannedUsersElem += foreverValue;
        } else {
            bannedUsersElem += users[user].bannedTo;
        }

        bannedUsersElem += '</div></div></div></div></div>';
    }
    $(".users-container .banned-users").html(bannedUsersElem);
}