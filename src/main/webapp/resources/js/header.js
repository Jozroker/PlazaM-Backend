let headerLocationHidden = true;
let localHidden = true;
let messagesHidden = true;
let menuHidden = true;
let settingsMenuHidden = true;
let scrollUserHidden = false;
let settingsHidden = false;
let sexHidden = true;
let countryHidden = true;
let languageHidden = true;
let phoneCodeHidden = true;
let ticketsHidden = true;
let comingSoonHidden = true;

let searchLineAnimate = false;
let headerLocationAnimate = false;
let localAnimate = false;
let messagesAnimate = false;
let menuAnimate = false;
let settingsAnimate = false;
let sexAnimate = false;
let countryAnimate = false;
let languageAnimate = false;
let phoneCodeAnimate = false;
let settingsMenuAnimate = false;
let ticketsAnimate = false;
let comingSoonAnimate = false;

let scrollBar, cinemaId, language, loadingCount = 0, loadingTimeout;
let windowResize = false;
// let currentScrollPosition;
let settingsPosition = "-808px";
// let settingsPosition = "-858px";
let nextClickedElement = $();
let userImage;
let userImageFormat;
let addedFavMovies = [],
    removedFavMovies = [],
    addedWaitedMovies = [],
    removedWaitedMovies = [],
    addedViewedMovies = [],
    addedTickets = [],
    removedTickets = [],
    removedMessages = [],
    addedMessages = [];
// removedTicketsSeances = [];

window.onbeforeunload = function () {
    listsSaving();
}

$(document).ready(function () {
    language = $("#current-local .flag").attr("identifier");
    cinemaId = $("#current-cinema a").attr("identifier");

    {
        $($(".link")[4]).css("height", $("#current-cinema").height() <= 18 ? "32px" : "54px");
        $($(".link")[4]).css("top", $("#current-cinema").height() <= 18 ? "0" : "-10px");
        generateCinemas();
        $("#menu-btn").css("position", "absolute")
            .parent().css("width", $("#menu-btn").width());
        if ($("#menu-top .menu-item").length == 7) {
            $("#menu-top").css("margin-bottom", "369px");
        } else {
            $("#menu-top").css("margin-bottom", "420px");
        }
        $("#new-phone").inputmask({
            mask: "(999) 999-99-99",
            placeholder: "(***) ***-**-**",
            showMaskOnFocus: false,
            showMaskOnHover: false
        });
        $(".sex, .country, .language-settings").find(".list .selected").each(function () {
            let width = $(this).find("div").first().width();
            $(this).css("width", 26 + width + "px");
        })

        if (typeof tickets !== 'undefined' && Array.isArray(tickets)) {
            let ticketsList = '<div class="scroll">';
            for (let ticket in tickets) {
                ticketsList += createTicket(tickets[ticket][0], tickets[ticket][1], tickets[ticket][2], tickets[ticket][3], tickets[ticket][4], tickets[ticket][5],
                    tickets[ticket][6], tickets[ticket][7], tickets[ticket][8], tickets[ticket][9], tickets[ticket][10], tickets[ticket][11]);
            }
            ticketsList += '</div>';
            $("#tickets .page").html(ticketsList);

            $("#tickets .scroll").each(function (index) {
                new SimpleBar($("#tickets .scroll")[index], {
                    autoHide: false
                });
            })

            $("#tickets .ticket").each(function () {
                JsBarcode(".barcode-value", $(this).find(".identifier").text(), {
                    height: 59,
                    width: 2,
                    textMargin: 0,
                    displayValue: false,
                    lineColor: "#0D0E0D",
                    margin: 0
                })
            })
        }

    }

    $(document).on("mouseenter", "#header .scroll", function () {
        currentScrollPosition = window.scrollY;
        window.addEventListener("scroll", noScroll);
    })

    $(document).on("mouseleave", "#header .scroll", function () {
        window.removeEventListener("scroll", noScroll);
    })

    $(window).on("resize", function () {
        if ($(window).width() < 1770) {
            $("#menu-btn div").css("left", "-20px");
        } else {
            $("#menu-btn div").css("left", "-40px");
        }

        if (window.innerHeight === window.screen.height) {
            if ($("#menu-top .menu-item").length == 7) {
                $("#menu-top").css("margin-bottom", "438px");
            } else {
                $("#menu-top").css("margin-bottom", "489px");
            }
        } else {
            if ($("#menu-top .menu-item").length == 7) {
                $("#menu-top").css("margin-bottom", "369px");
            } else {
                $("#menu-top").css("margin-bottom", "420px");
            }
        }

        if ($(window).width() >= 1440) {
            if (window.innerHeight === window.screen.height) {
                settingsPosition = "-877px";
            } else {
                settingsPosition = "-808px";
            }
        } else if ($(window).width() < 960) {
            settingsPosition = "-788px";
        } else {
            settingsPosition = "-796px";
        }

        if ($(window).width() >= 955) {
            $(".language-settings").css("display", "none");
        } else {
            $(".language-settings").css("display", "block");
        }

        if (!settingsMenuHidden) {
            $(".settings-btn").css("top", settingsPosition);
        }

        clearTimeout(windowResize);
        windowResize = setTimeout(function () {

            windowResize = false;
            $(window).trigger('resizeend');

        }, 100);
    }).on("resizeend", function () {
        $($(".link")[4]).css("height", $("#current-cinema").height() <= 18 ? "32px" : "54px");
        $($(".link")[4]).css("top", $("#current-cinema").height() <= 18 ? "0" : "-10px");
    })

    $(document).mousemove(function () {
        if (!$("#menu:hover").length && !menuHidden && !menuAnimate && settingsHidden && !$("#menu-btn:hover").length &&
            !$("#settings-bar:hover").length && !$("#tickets:hover").length && ticketsHidden && !settingsMenuAnimate &&
            !ticketsAnimate && !settingsAnimate) {
            $($(".link")[6]).click();
            //todo replase all hover pseudo elements in js files to add/remove classes and css
        }
    })

    $("#movies-link, #schedule-link").click(function () {
        if ($(this).hasClass("select")) {
            $(this).removeClass("select");
        } else {
            $(this).parent().find(".select").removeClass("select");
            $(this).addClass("select");
        }
        $("#coming-soon").animate({
            "top": "-52.6vw"
        }, 500, "easeInOutQuint");
        $("#coming-soon").slick("slickPause");
    })

    $("#coming-soon").mouseleave(function () {
        if (((comingSoonHidden && comingSoonAnimate) || (!comingSoonHidden && !comingSoonAnimate)) && !$("#header:hover").length) {
            $("#coming-soon-link").click();
        }
    })

    $("#coming-soon-link").click(function () {
        if (window.location.pathname === '/home') {
            if ($(this).hasClass("select")) {
                $(this).removeClass("select");
            } else {
                $(this).parent().find(".select").removeClass("select");
                $(this).addClass("select");
            }
            if (!comingSoonAnimate) {
                comingSoonAnimate = true;

                if (comingSoonHidden) {
                    $("#coming-soon").animate({
                        "top": "0"
                    }, 1000, "easeInOutQuint", function () {
                        comingSoonAnimate = false;
                        comingSoonHidden = false;
                        nextClickedElement.click();
                        nextClickedElement = $();
                    });
                    $("#coming-soon").slick("slickPlay");
                } else {
                    $("#coming-soon").animate({
                        "top": "-52.6vw"
                    }, 1000, "easeInOutQuint", function () {
                        $("#coming-soon").slick("slickPause");
                        comingSoonAnimate = false;
                        comingSoonHidden = true;
                        nextClickedElement.click();
                        nextClickedElement = $();
                    });
                }
            } else {
                nextClickedElement = $("#coming-soon-link");
            }
        } else {
            window.location.href = window.location.origin + '/home';
        }
    })

    $("#old-password, #new-password, #new-password-confirm, #new-phone, #new-email").focusin(function () {
        $(this).parents(".fields").first().prev().find("span:first-child").css("color", "#D7D7D7");
        $(this).parents(".fields").first().prev().find("span:not(:first-child)").css("display", "none");
    })

    $("#security .password .button").click(function () {
        let regex = new RegExp($("#new-password").attr("regex"));
        if ($("#old-password").val() == "" || $("#new-password").val() == "" || $("#new-password-confirm").val() == "") {
            $(this).parents(".fields").first().prev().find("span:first-child").css("color", "#AF2341");
            $(this).parents(".fields").first().prev().find(".empty").css("display", "inline");
        } else if (!regex.test($("#new-password").val())) {
            $(this).parents(".fields").first().prev().find("span:first-child").css("color", "#AF2341");
            $(this).parents(".fields").first().prev().find(".weak").css("display", "inline");
        } else if ($("#new-password").val() != $("#new-password-confirm").val()) {
            $(this).parents(".fields").first().prev().find("span:first-child").css("color", "#AF2341");
            $(this).parents(".fields").first().prev().find(".not-confirmed").css("display", "inline");
        } else {
            let url = window.location.origin + '/user/' + userId + '/update/password';
            let oldPassword = $("#old-password").val().trim();
            let newPassword = $("#new-password").val().trim();
            let confirmPassword = $("#new-password-confirm").val().trim();

            startLoading();
            $.ajax({
                url: url,
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                // contentType: false,
                // processData: false,
                // data: data
                data: JSON.stringify({
                    oldPassword: oldPassword,
                    newPassword: newPassword,
                    confirmPassword: confirmPassword
                })
            }).done(function () {
                $("#old-password").val('');
                $("#new-password").val('');
                $("#new-password-confirm").val('');
                stopLoading();
            })
        }
    })

    $("#security .email .button").click(function () {
        let regex = new RegExp($("#new-email").attr("regex"));
        if ($("#new-email").val() == "") {
            $(this).parents(".fields").first().prev().find("span:first-child").css("color", "#AF2341");
            $(this).parents(".fields").first().prev().find(".empty").css("display", "inline");
        } else if (!regex.test($("#new-email").val())) {
            $(this).parents(".fields").first().prev().find("span:first-child").css("color", "#AF2341");
            $(this).parents(".fields").first().prev().find(".not-email").css("display", "inline");
        } else {
            let url = window.location.origin + '/user/' + userId + '/update/email';
            let email = $("#new-email").val().trim();

            startLoading();
            $.ajax({
                url: url,
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    email: email
                })
            }).done(function () {
                $(".email .value").text($("#new-email").val());
                $("#new-email").val("");
                stopLoading();
            })
        }
    })

    $("#security .phone .button").click(function () {
        let regex = new RegExp($("#new-phone").attr("regex"));
        if ($("#new-phone").val() == "") {
            $(this).parents(".fields").first().prev().find("span:first-child").css("color", "#AF2341");
            $(this).parents(".fields").first().prev().find(".empty").css("display", "inline");
        } else if (!regex.test($("#new-phone").val())) {
            $(this).parents(".fields").first().prev().find("span:first-child").css("color", "#AF2341");
            $(this).parents(".fields").first().prev().find(".not-phone").css("display", "inline");
        } else {
            startLoading();
            let url = window.location.origin + '/user/' + userId + '/update/phone';
            let phone = $(".phone-code .selected > div").first().text() + $("#new-phone").val().trim();

            $.ajax({
                url: url,
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    phone: phone
                })
            }).done(function () {
                $("#new-phone").val("");
                stopLoading();
            })
        }
    })

    $("#user").mouseover(function () {
        $("#menu-btn .triangle").css("border-bottom-color", "#AF2341");
    })

    $("#user").mouseout(function () {
        $("#menu-btn .triangle").css("border-bottom-color", "");
    })

    $(".tickets-btn").click(function () {
        if (!ticketsAnimate && !messagesAnimate && !settingsAnimate && !menuAnimate) {
            ticketsAnimate = true;

            ticketsFunction();
        } else {
            nextClickedElement = $(this);
        }
    })

    $(document).on("click", "#tickets .ticket .cross.active", function () {
        startLoading();
        let ticketElem = $(this);
        // $.ajax({
        //     url: window.location.origin + '/ticket/' + $(ticketElem).parent().attr("identifier") + '/remove',
        //     method: 'POST'
        // }).done(function (data) {
        //     if (data === 'success') {
        removedTickets.push($(ticketElem).parents(".ticket").first().attr("identifier"));
        // removedTicketsSeances.push($(this).parents(".ticket").first().find(".barcode .identifier").text().trim());
        $(ticketElem).removeClass("active");
        $(ticketElem).parents(".ticket").first().animate({
            "height": "0"
        }, 300, "easeInOutQuint", function () {
            $(this).remove();
        })
        // }
        stopLoading();
        // })
    })

    $(".personal-btn, .site-btn, .security-btn").click(function () {
        $("#settings .settings-menu .menu-item").removeClass("selected");
        $(this).addClass("selected");

        if ((!settingsMenuHidden && !settingsAnimate) || (settingsMenuHidden && settingsAnimate)) {
            if (!settingsMenuAnimate) {
                settingsMenuAnimate = true;

                let position = $("#settings .settings-menu .menu-item").index($(this));
                $("#menu").css("background-color", "#0A0B0B");

                $("#settings-bar").animate({
                    "top": (position * -100) + "vh"
                }, 1000, "easeInOutQuint");
                if ($(this).is(".security-btn")) {
                    $("#save-btn").animate({
                        "bottom": "-35px"
                    }, 500, "easeInOutQuint");
                } else {
                    $("#save-btn").animate({
                        "bottom": "7.96vh"
                    }, 500, "easeInOutQuint");
                }
                $("#background").animate({
                    "top": "0"
                }, 1000, "easeInOutQuint", function () {
                    settingsMenuAnimate = false;
                    nextClickedElement.click();
                    nextClickedElement = $();
                });
                $("#save-btn").attr("data", $(this).attr("id"));
                settingsHidden = false;
            } else {
                nextClickedElement = $(this);
            }
        }
    })

    $("#header .scroll").each(function (index) {
        if ($(this).attr("id") === "menu") {
            scrollBar = new SimpleBar($("#header .scroll")[index], {
                autoHide: false
            });
            scrollBar.getScrollElement().addEventListener("scroll", function () {
                if (!messagesAnimate && !settingsAnimate && messagesHidden && settingsMenuHidden) {
                    $("#menu-btn").finish();
                    if (scrollBar.getScrollElement().scrollTop === 0) {
                        if (scrollUserHidden) {
                            scrollUserHidden = false;
                            $("#user").find("img").animate({
                                "top": "0"
                            }, 100, "linear");
                            if (!menuHidden || menuAnimate) {
                                $("#menu-btn").animate({
                                    "top": "5px"
                                }, 100, "linear");
                            }
                        }
                    } else {
                        if (!scrollUserHidden) {
                            scrollUserHidden = true;
                            $("#user").find("img").animate({
                                "top": "-100px"
                            }, 100, "linear");
                            $("#menu-btn").animate({
                                "top": "-100px"
                            }, 100, "linear");
                        }
                    }

                } else {
                    scrollBar.getScrollElement().scrollTop = 0;
                }
            })
        } else {
            new SimpleBar($("#header .scroll")[index], {
                autoHide: false
            });
        }
    });

    $(".menu-item").mouseenter(function () {
        $(this).next(".line-between").addClass("selected-line");
        $(this).prev(".line-between").addClass("selected-line");
    }).mouseleave(function () {
        $(this).next(".line-between").removeClass("selected-line");
        $(this).prev(".line-between").removeClass("selected-line");
    })

    $("#save-btn .button").click(function () {
        if (typeof userId !== 'undefined') {
            let url = window.location.origin + '/user/' + userId + '/update/info';
            console.log(url)

            let username = $("#username").val().trim();
            let firstName = $("#firstName").val().trim();
            let lastName = $("#lastName").val().trim();
            let sex = $(".sex .selected > div").first().attr("identifier");
            let country = $(".country .selected > div").first().attr("identifier");
            let city = $("#city").val().trim();
            let about = $(".about textarea").val().trim();
            let hide18movies = Boolean($("#site-settings .movie-list .checked").length);
            let lightTheme = Boolean($("#site-settings .personalisation .checked").index() - 1);

            startLoading();
            $.ajax({
                url: url,
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                // contentType: false,
                // processData: false,
                // data: data
                data: JSON.stringify({
                    username: username,
                    firstName: firstName,
                    lastName: lastName,
                    sex: sex,
                    country: country,
                    city: city,
                    about: about,
                    image: userImage,
                    format: userImageFormat,
                    hide18movies: hide18movies,
                    useLightTheme: lightTheme,
                    language: language,
                    cinemaId: cinemaId
                })
            }).done(function () {
                $("#settings .title").click();
                $("#user img").attr("src", $(".avatar img").attr("src"));
                $("#menu-btn > div").first().text(username);
                stopLoading();
            })
        }
    })

    $("#user").click(function () {
        $($(".link")[6]).click();
    })

    $($(".link")[6]).click(function () {
        if (!menuAnimate && !messagesAnimate && !settingsAnimate && !settingsMenuAnimate && !ticketsAnimate && typeof userIsLogged !== 'undefined') {
            menuAnimate = true;
            currentScrollPosition = window.scrollY;
            window.addEventListener("scroll", noScroll);

            if (!settingsHidden) {
                $("#settings-bar").animate({
                    "top": "100vh"
                }, 1000, "easeInOutQuint");
                $("#save-btn").animate({
                    "bottom": "-35px"
                }, 500, "easeInOutQuint");
                $("#background").animate({
                    "top": "100vh"
                }, 1000, "easeInOutQuint");
                settingsHidden = true;
            }

            if (!messagesHidden || !settingsMenuHidden || scrollUserHidden) {
                $("#menu-btn").css("opacity", "0");
            }
            scrollUserHidden = false;

            if (menuHidden) {

                let leftPosition = $(window).width() >= 1770 ? 40 : 20;

                $(".simplebar-track").css("opacity", "1");

                $("#menu").animate({
                    "right": "0"
                }, 1000, "easeInOutQuint", function () {

                    $("#menu").find(".menu-item").each(function (index) {
                        let wait = 100 * ($("#menu").find(".menu-item").length +
                            $("#menu").find(".line-between").length -
                            $("#messages").find(".line-between").length -
                            $("#settings").find(".line-between").length -
                            $("#settings").find(".menu-item").length);
                        let waiting = index * 100;

                        setTimeout(function () {
                            $($("#menu").find(".menu-item")[index]).animate({
                                "opacity": "1"
                            }, 100, "linear");
                        }, waiting);

                        setTimeout(function () {
                            menuAnimate = false;
                            menuHidden = false;
                            nextClickedElement.click();
                            nextClickedElement = $();
                        }, wait);
                    });

                    setTimeout(function () {
                        $("#menu").find(".line-between").each(function (index) {
                            if ($(this).parent("#menu-top").length
                                || $(this).parent("#menu-bottom").length) {
                                let waiting = index * 100;

                                setTimeout(function () {
                                    $($("#menu").find(".line-between")[index]).animate({
                                        "opacity": "1"
                                    }, 100, "linear");
                                }, waiting);
                            }
                        })
                    }, 50);
                });

                $("#menu-btn").animate({
                    "font-size": "20px",
                    "top": "5px"
                }, 1000, "easeInOutQuint");

                $("#menu-btn div").css({
                    "position": "relative",
                    "left": "0"
                }).animate({
                    "left": -leftPosition + "px"
                }, 1000, "easeInOutQuint");

                $("#menu-btn .triangle").css("transform", "rotate(90deg)");

            } else {

                if (!ticketsHidden) {
                    ticketsFunction();
                }

                $("#menu").css("background-color", "rgba(13, 14, 13, 0.95)");

                $("#menu").animate({
                    "right": "-320px"
                }, 1000, "easeInOutQuint");

                $("#messages").animate({
                    "right": "-320px"
                }, 1000, "easeInOutQuint", function () {
                    $("#messages").find(".line-between").css("opacity", "0");

                    $("#messages").find(".messages").css("right", "-320px");

                    $(".messages-btn").css({
                        "position": "",
                        "top": ""
                    });

                    $("#menu").find(".menu-item").each(function () {
                        if (!$(this).hasClass("messages-btn")) {
                            $(this).css("opacity", "1");
                        }
                    });

                    $("#menu").find(".line-between").css("opacity", "1");

                    $("#user").find("img").css("top", "");

                    messagesAnimate = false;
                    messagesHidden = true;
                });

                $("#settings").animate({
                    "right": "-320px"
                }, 1000, "easeInOutQuint", function () {
                    $("#settings").find(".line-between").css("opacity", "0");

                    $("#settings").find(".menu-item").css("opacity", "0");

                    $(".settings-btn").css({
                        "position": "",
                        "top": ""
                    });

                    $("#menu").find(".menu-item").each(function () {
                        if (!$(this).hasClass("settings-btn")) {
                            $(this).css("opacity", "1");
                        }
                    });

                    $("#menu").find(".line-between").css("opacity", "1");

                    $("#user").find("img").css("top", "");

                    settingsAnimate = false;
                    settingsMenuHidden = true;
                });

                $("#menu-btn").animate({
                    "font-size": $("#current-cinema").css("font-size"),
                    "top": ""
                }, 1000, "easeInOutQuint", function () {
                    $("#menu-btn").animate({
                        "opacity": "1"
                    }, 300, function () {
                        $("#menu").find(".menu-item").css("opacity", "0");
                        $("#menu").find(".line-between").css("opacity", "0");

                        window.removeEventListener("scroll", noScroll);
                        scrollBar.getScrollElement().scrollTop = 0;
                        menuHidden = true;
                        menuAnimate = false;
                        $(".menu-item").removeClass("selected");
                        nextClickedElement.click();
                        nextClickedElement = $();
                    });
                });

                $("#menu-btn div").animate({
                    "left": "0"
                }, 1000, "easeInOutQuint", function () {
                    $(this).css("position", "static");
                });

                $("#menu-btn .triangle").css("transform", "rotate(-90deg)");
            }
        } else {
            nextClickedElement = $(this);
        }
    })

    $("#search-icon").click(function () {
        if (!searchLineAnimate) {
            searchLineAnimate = true;
            let elem = $("#search-line");
            let visible = elem.css("opacity");
            if (visible == 0) {
                $($('.link')[3]).css({
                    "width": "inherit",
                    "padding-left": "10px",
                    "padding-right": "0"
                }).animate({"background-position-x": "100%"}, 500, "easeInOutQuint");
                $($(".underline")[3]).delay(100).animate({"width": "100%"}, 500, "easeInOutQuint", function () {
                    elem.animate({
                        "opacity": "1"
                    }, 100, "linear").css("pointer-events", "auto");
                    $("#search-result").css("display", "block").animate({
                        "height": ($(".movie-result").length * 36) + "px"
                    }, 300, "easeInOutQuint");
                });
            } else {
                $("#search-result").animate({
                    "height": "0"
                }, 300, "easeInOutQuint", function () {
                    $(this).css("display", "none");
                    elem.animate({
                        "opacity": "0",
                    }, 100, "linear", function () {
                        $($(".underline")[3]).animate({"width": "0"}, 500, "easeInOutQuint");
                        $($('.link')[3]).delay(100).animate({"background-position-x": "0%"}, 500, "easeInOutQuint", function () {
                            $(this).css({
                                "width": "0",
                                "padding-left": "0",
                                "padding-right": "32px"
                            })
                        })
                    }).css("pointer-events", "none");
                });
            }
            setTimeout(function () {
                searchLineAnimate = false;
                nextClickedElement.click();
                nextClickedElement = $();
            }, 1000);
        } else {
            nextClickedElement = $(this);
        }
    })

    $("#current-cinema").click(function () {
        if (!headerLocationAnimate) {
            headerLocationAnimate = true;
            if (!headerLocationHidden) {
                $($("#current-cinema .triangle")[0]).removeClass("triangle-0");
                $($(".link")[4]).animate({
                    "height": $(this).height() + 16 + "px"
                }, 300, "linear", function () {
                    $($(".link")[4]).find(".background").animate({
                        "height": "0"
                    }, 100, "linear", function () {
                        headerLocationHidden = true;
                        headerLocationAnimate = false;
                        nextClickedElement.click();
                        nextClickedElement = $();
                    });
                });
                setTimeout(function () {
                    $(".cinema-list").each(function () {
                        $(this).css("left", "100%");
                        $(".city").css("width", "100%");
                        $(".city-line").css("width", "75%");
                    })
                }, 500);
            } else {
                let newHeight = 16 + $("#header-location").outerHeight(true);
                $($("#current-cinema .triangle")[0]).addClass("triangle-0");
                $($(".link")[4]).find(".background").animate({
                    "height": "100%"
                }, 100, "linear", function () {
                    if ($($(".link")[4]).filter(":hover").length) {
                        $($(".link")[4]).animate({
                            "height": newHeight + "px"
                        }, 300, "linear");
                    }
                    headerLocationHidden = false;
                    headerLocationAnimate = false;
                    nextClickedElement.click();
                    nextClickedElement = $();
                });
            }
            // setTimeout(function () {
            //     headerLocationAnimate = false;
            //     nextClickedElement.click();
            //     nextClickedElement = $();
            // }, 500);
        } else {
            nextClickedElement = $(this);
        }
    })

    $($(".link")[4]).mouseleave(function () {
        // headerLocationHidden = true;
        // headerLocationAnimate = true;
        // let newHeight = $("#current-cinema").height() <= 18 ? 32 : 54;
        // $($("#current-cinema .triangle")[0]).removeClass("triangle-0");
        // $($(".link")[4]).animate({
        //     "height": newHeight + "px"
        // }, 300, "linear", function () {
        //     $($(".link")[4]).find(".background").animate({
        //         "height": "0"
        //     }, 100, "linear", function () {
        //         headerLocationAnimate = false;
        //     });
        // });
        // setTimeout(function () {
        //     $(".cinema-list").each(function () {
        //         $(this).css("left", "100%");
        //         $(".city").css("width", "100%");
        //         $(".city-line").css("width", "100%");
        //     })
        // }, 500);
        if ((!headerLocationAnimate && !headerLocationHidden) || (headerLocationAnimate && headerLocationHidden)) {
            $("#current-cinema").click();
        }
    })

    $(document).on("click", ".cinema-list a", function () {
        // headerLocationHidden = false;
        // $.ajax({
        //     url: window.location.origin + '/cinema?cinemaId=' + $(this).attr("identifier"),
        //     method: 'POST'
        // });
        cinemaId = $(this).attr("identifier");
        $("#current-cinema a").text($(this).text());

        if (window.location.pathname === '/home' || window.location.pathname === '/schedule') {
            $("#home-schedules-containers .curtain").css("display", "block");
            $("#schedules .curtain").css("display", "block");
            let genres = '', techs = '';
            $(".genre.selected").each(function () {
                genres += $(this).attr("identifier") + ",";
            });
            $(".tech.selected").each(function () {
                techs += $(this).text().trim() + ",";
            });
            genres = genres.slice(0, -1);
            techs = techs.slice(0, -1);
            // let pagesUrl = window.location.origin + '/page/count' + '?cinemaId=' + cinemaId + (genres === '' ? '' : '&genres=' + genres) +
            //     (techs === '' ? '' : '&technologies=' + techs);
            let schedulesUrl = window.location.origin + '/page/' + (isNaN(page) ? 1 : page) + '?cinemaId=' + cinemaId + (genres === '' ? '' : '&genres=' + genres) +
                (techs === '' ? '' : '&technologies=' + techs);
            if (window.location.pathname === '/schedule') {
                let selectedDate = $("#schedule-content .selected-date").text().trim();
                // pagesUrl += "&date=" + selectedDate;
                schedulesUrl += "&date=" + selectedDate;
            }

            startLoading();
            $.ajax({
                url: schedulesUrl,
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
                $("#home-schedules-containers .curtain").css("display", "none");
                $("#schedules .curtain").css("display", "none");
            })
        } else if (window.location.pathname.startsWith('/movie')) {
            let url = new URL(window.location);
            url.searchParams.set("cinemaId", cinemaId);
            window.location.href = url;
        }

        let newHeight = $("#current-cinema").height() <= 18 ? 32 : 54;
        let newPosition = $("#current-cinema").height() <= 18 ? 0 : -10;
        $($("#current-cinema .triangle")[0]).removeClass("triangle-0");
        $($(".link")[4]).find(".background").css("height", "0");
        $($(".link")[4]).css({
            "height": newHeight + "px"
        }).animate({
            "top": newPosition + "px"
        }, 200, "linear");
        $($(".link")[4]).mouseleave();
        $("#save-btn .button").click();
        setTimeout(function () {
            $(".cinema-list").each(function () {
                $(this).css("left", "100%");
                $(".city").css("width", "100%");
                $(".city-line").css("width", "100%");
            })
            // headerLocationHidden = true;
        }, 500);
    })

    $("#current-local").click(function () {
        if (!localAnimate) {
            localAnimate = true;
            if (!localHidden) {
                $($("#current-local .triangle")[0]).removeClass("triangle-0");
                $($(".link")[5]).animate({
                    "height": $(this).height() + 16 + "px"
                }, 300, "linear", function () {
                    $($(".link")[5]).find(".background").animate({
                        "height": "0"
                    }, 100, "linear", function () {
                        localHidden = true;
                        localAnimate = false;
                        nextClickedElement.click();
                        nextClickedElement = $();
                    });
                });
            } else {
                let newHeight = 16 + $("#local").outerHeight(true);
                $($("#current-local .triangle")[0]).addClass("triangle-0");
                $($(".link")[5]).find(".background").animate({
                    "height": "100%"
                }, 100, "linear", function () {
                    // if ($($(".link")[5]).filter(":hover").length) {
                    $($(".link")[5]).animate({
                        "height": newHeight + "px"
                    }, 300, "linear", function () {
                        localAnimate = false;
                        localHidden = false;
                        nextClickedElement.click();
                        nextClickedElement = $();
                    });
                    // }
                });
            }
        } else {
            nextClickedElement = $(this);
        }
    })

    $($(".link")[5]).mouseleave(function () {
        // localHidden = true;
        // localAnimate = true;
        // $($("#current-local .triangle")[0]).removeClass("triangle-0");
        // $($(".link")[5]).animate({
        //     "height": "32px"
        // }, 300, "linear", function () {
        //     $($(".link")[5]).find(".background").animate({
        //         "height": "0"
        //     }, 100, "linear", function () {
        //         localAnimate = false;
        //     });
        // });
        if ((!localHidden && !localAnimate) || (localHidden && localAnimate)) {
            $("#current-local").click();
        }
    })

    $("#locals li").click(function () {
        // localHidden = false;
        language = $(this).find(".flag").attr("identifier");
        let flag = $(this).find(".flag").attr("class").split(" ")[1];
        $("#current-local").find(".flag").removeClass().addClass("flag").addClass(flag);
        $("#current-local a").text($(this).find("a").text());
        $("#save-btn .button").click();
        $($("#current-cinema .triangle")[0]).removeClass("triangle-0");
        $($(".link")[5]).find(".background").css("height", "0");
        $($(".link")[5]).css({
            "height": "32px"
        });
        // $($(".link")[5]).mouseleave();
        // localHidden = true;
    })

    $(document).on("click", ".city", function () {
        $(".city").animate({
            "width": "0"
        }, 200, "linear");

        $(".city-line").animate({
            "width": "0",
            "margin": "0"
        }, 200, "linear");

        $(this).next(".cinema-list").delay(250).animate({
            "left": "0"
        }, 200, "linear");
    })

    $(document).on("click", ".cinema-back", function () {
        // $(this).click(function () {
            $(".cinema-list").animate({
                "left": "100%"
            }, 200, "linear");

            $(".city").delay(250).animate({
                "width": "100%"
            }, 200, "linear");

            $(".city-line").animate({
                "width": "75%"
            }, 200, "linear", function () {
                $(".city-line").css("margin", "auto");
            });
        // })
    })

    $(".messages-btn").click(function () {
        if (messagesHidden) {
            if (!ticketsHidden) {
                $(".tickets-btn").click();
                nextClickedElement = $(this);
            } else {
                if (!messagesAnimate && !menuAnimate && !settingsAnimate && !ticketsAnimate) {
                    messagesAnimate = true;
                    $($("#menu .simplebar-vertical")[1]).css("opacity", "0");

                    $("#user").find("img").animate({
                        "top": "-100px"
                    }, 500, "easeInOutQuint");

                    setTimeout(function () {
                        $("#menu-btn").animate({
                            "top": "-100px"
                        }, 500, "easeInOutQuint");
                    }, 200);

                    $("#menu").find(".line-between").animate({
                        "opacity": "0"
                    }, 600, "linear");

                    $("#menu").find(".menu-item").each(function () {
                        if (!$(this).hasClass("messages-btn")) {
                            $(this).animate({
                                "opacity": "0"
                            }, 700, "linear");
                        }
                    })

                    setTimeout(function () {
                        let elem = $(".messages-btn");
                        let position = (elem.outerHeight(true) - elem.height()) / 2 + elem.height();
                        elem.css({
                            "position": "absolute",
                            "top": position
                        }).animate({
                            "top": "-102px"
                        }, 500, "easeInOutQuint");

                        setTimeout(function () {
                            scrollBar.getScrollElement().scrollTop = 0;
                        }, 200);
                    }, 800);

                    setTimeout(function () {
                        $("#messages").animate({
                            "right": "0"
                        }, 300, "easeInOutQuint");

                        setTimeout(function () {
                            $("#messages").find(".messages").animate({
                                "right": "0"
                            }, 300, "easeInOutQuint");

                            setTimeout(function () {
                                if ($("#messages").find(".line-between").length) {
                                    $("#messages").find(".line-between").animate({
                                        "opacity": "1"
                                    }, 300, "linear", function () {
                                        messagesHidden = false;
                                        messagesAnimate = false;
                                        nextClickedElement.click();
                                        nextClickedElement = $();
                                    });
                                } else {
                                    messagesHidden = false;
                                    messagesAnimate = false;
                                    nextClickedElement.click();
                                    nextClickedElement = $();
                                }
                            }, 200);
                        }, 200);
                    }, 900);
                } else {
                    nextClickedElement = $(this);
                }
            }
        }
    })

    $("#messages .title").click(function () {
        if (!messagesHidden) {
            if (!messagesAnimate) {
                messagesAnimate = true;
                $("#messages").find(".line-between").animate({
                    "opacity": "0"
                }, 300, "linear")

                setTimeout(function () {
                    $("#messages").find(".messages").animate({
                        "right": "-320px"
                    }, 300, "easeInOutQuint");
                }, 200);

                setTimeout(function () {
                    let elem = $(".messages-btn");
                    let position = (elem.outerHeight(true) - elem.height()) / 2 + elem.height();
                    elem.animate({
                        "top": position
                    }, 500, "easeInOutQuint", function () {
                        $(this).css({
                            "position": "",
                            "top": ""
                        });
                    });
                }, 400);

                setTimeout(function () {
                    $("#messages").animate({
                        "right": "-320px"
                    }, 300, "easeInOutQuint");
                }, 500);

                setTimeout(function () {
                    $("#menu").find(".menu-item").each(function () {
                        if (!$(this).hasClass("messages-btn")) {
                            $(this).animate({
                                "opacity": "1"
                            }, 700, "linear");
                        }
                    });

                    $("#menu-btn").animate({
                        "top": "5px"
                    }, 500, "easeInOutQuint");

                    setTimeout(function () {
                        $("#menu").find(".line-between").animate({
                            "opacity": "1"
                        }, 600, "linear");
                    }, 100);

                    setTimeout(function () {
                        $("#user").find("img").animate({
                            "top": ""
                        }, 500, "easeInOutQuint", function () {
                            messagesAnimate = false;
                            messagesHidden = true;
                            $($("#menu .simplebar-vertical")[1]).css("opacity", "1");
                            scrollUserHidden = false;
                            nextClickedElement.click();
                            nextClickedElement = $();
                        });
                    }, 200);
                }, 900);
            } else {
                nextClickedElement = $(this);
            }
        }
    })

    $(document).on("click", "#messages .cross", function () {
        let messageElem = $(this);
        // $.ajax({
        //     url: window.location.origin + '/message/' + $(messageElem).parent().attr("identifier") + '/remove',
        //     method: 'POST'
        // }).done(function (data) {
        //     if (data === 'success') {
        if (!messagesAnimate) {
            messagesAnimate = true;
            let elem = $(messageElem).parent();
            if (elem.prev(".line-between").length) {
                elem.prev(".line-between").remove();
            } else if (elem.next(".line-between").length) {
                elem.next(".line-between").remove();
            }
            removedMessages.push($(messageElem).parents(".message").first().attr("identifier"));
            elem.remove();
            messagesAnimate = false;
            nextClickedElement.click();
            nextClickedElement = $();
        } else {
            nextClickedElement = $(messageElem);
        }
        //     }
        // })
    })

    $(".settings-btn").click(function () {
        if (settingsMenuHidden) {
            if (!ticketsHidden) {
                $(".tickets-btn").click();
                nextClickedElement = $(this);
            } else {
                if (!settingsAnimate && !menuAnimate && !messagesAnimate && !ticketsAnimate) {
                    settingsAnimate = true;

                    $($("#menu .simplebar-vertical")[1]).css("opacity", "0");
                    $(window).resize();

                    $("#user").find("img").animate({
                        "top": "-100px"
                    }, 500, "easeInOutQuint");

                    setTimeout(function () {
                        $("#menu-btn").animate({
                            "top": "-100px"
                        }, 500, "easeInOutQuint");
                    }, 200);

                    $("#menu").find(".line-between").animate({
                        "opacity": "0"
                    }, 600, "linear");

                    $("#menu").find(".menu-item").each(function () {
                        if (!$(this).hasClass("settings-btn")) {
                            $(this).animate({
                                "opacity": "0"
                            }, 700, "linear");
                        }
                    })

                    setTimeout(function () {
                        let elem = $(".settings-btn");
                        scrollBar.getScrollElement().scrollTop = 0;
                        elem.css({
                            "position": "absolute",
                            "top": "-10px"
                        }).animate({
                            "top": settingsPosition
                        }, 500, "easeInOutQuint");
                    }, 800);

                    setTimeout(function () {
                        $("#settings").animate({
                            "right": "0"
                        }, 300, "easeInOutQuint", function () {
                            $("#settings").find(".menu-item").each(function (index) {
                                let wait = 100 * ($("#settings").find(".menu-item").length +
                                    $("#settings").find(".line-between").length);
                                let waiting = index * 100;

                                setTimeout(function () {
                                    $($("#settings").find(".menu-item")[index]).animate({
                                        "opacity": "1"
                                    }, 100, "linear");
                                }, waiting);

                                setTimeout(function () {
                                    settingsAnimate = false;
                                    settingsMenuHidden = false;
                                    nextClickedElement.click();
                                    nextClickedElement = $();
                                }, wait);
                            });

                            setTimeout(function () {
                                $("#settings").find(".line-between").each(function (index) {
                                    let waiting = index * 100;

                                    setTimeout(function () {
                                        $($("#settings").find(".line-between")[index]).animate({
                                            "opacity": "1"
                                        }, 100, "linear");
                                    }, waiting);
                                })
                            }, 50);
                        });

                    }, 900);
                } else {
                    nextClickedElement = $(this);
                }
            }
        }
    })

    //todo check personal-settings click (selection)

    $("#settings .title").click(function () {
        if (!settingsMenuHidden) {
            if (!settingsAnimate && !settingsMenuAnimate && !ticketsAnimate) {
                settingsAnimate = true;

                if (!settingsHidden) {
                    $("#settings-bar").animate({
                        "top": "100vh"
                    }, 1000, "easeInOutQuint");
                    $("#save-btn").animate({
                        "bottom": "-35px"
                    }, 500, "easeInOutQuint");
                    $("#background").animate({
                        "top": "100vh"
                    }, 1000, "easeInOutQuint");
                    settingsHidden = true;
                }

                $("#menu").css("background-color", "rgba(13, 14, 13, 0.95)");

                $("#settings").find(".menu-item").get().reverse().forEach(function (elem, index) {
                    let waiting = index * 100;
                    $(elem).removeClass("selected");

                    setTimeout(function () {
                        $(elem).animate({
                            "opacity": "0"
                        }, 100, "linear");
                    }, waiting);

                });

                setTimeout(function () {
                    $("#settings").find(".line-between").get().reverse().forEach(function (elem, index) {
                        let waiting = index * 100;

                        setTimeout(function () {
                            $(elem).animate({
                                "opacity": "0"
                            }, 100, "linear");
                        }, waiting);
                    })
                }, 50);

                setTimeout(function () {
                    let elem = $(".settings-btn");
                    elem.animate({
                        "top": "-10px"
                    }, 500, "easeInOutQuint", function () {
                        $(this).css({
                            "position": "",
                            "top": ""
                        });
                    });
                }, 300);

                setTimeout(function () {
                    $("#settings").animate({
                        "right": "-320px"
                    }, 300, "easeInOutQuint");
                }, 400);

                setTimeout(function () {
                    $("#menu").find(".menu-item").each(function () {
                        if (!$(this).hasClass("settings-btn")) {
                            $(this).animate({
                                "opacity": "1"
                            }, 700, "linear");
                        }
                    });

                    $("#menu-btn").animate({
                        "top": "5px"
                    }, 500, "easeInOutQuint");

                    setTimeout(function () {
                        $("#menu").find(".line-between").animate({
                            "opacity": "1"
                        }, 600, "linear");
                    }, 100);

                    setTimeout(function () {
                        $("#user").find("img").animate({
                            "top": ""
                        }, 500, "easeInOutQuint", function () {
                            settingsAnimate = false;
                            settingsMenuHidden = true;
                            $($("#menu .simplebar-vertical")[1]).css("opacity", "1");
                            scrollUserHidden = false;
                            nextClickedElement.click();
                            nextClickedElement = $();
                        });
                    }, 200);
                }, 800);
            } else {
                nextClickedElement = $(this);
            }
        }
    })

    $(".sex").click(function () {
        if (!settingsHidden) {
            if (!sexAnimate) {
                sexAnimate = true;
                if (sexHidden) {
                    $(this).find(".selected").animate({
                        "width": "100%"
                    }, 250, "easeInOutQuint");
                    $(this).find(".list").animate({
                        "background-position-x": "100%"
                    }, 250, "easeInOutQuint", function () {
                        $(this).find(".triangle").addClass("triangle-0");
                        $(this).animate({
                            "height": "120px"
                        }, 250, "easeInOutQuint", function () {
                            sexHidden = false;
                            sexAnimate = false;
                            nextClickedElement.click();
                            nextClickedElement = $();
                        });
                    });
                } else {
                    let width = $(this).find(".selected > div").first().width();
                    $(this).find(".triangle").removeClass("triangle-0");
                    $(this).find(".list").animate({
                        "height": "25px"
                    }, 250, "easeInOutQuint", function () {
                        $(this).animate({
                            "background-position-x": "199%"
                        }, 250, "easeInOutQuint");
                        $(this).find(".selected").animate({
                            "width": width + 26 + "px"
                        }, 250, "easeInOutQuint", function () {
                            sexHidden = true;
                            sexAnimate = false;
                            nextClickedElement.click();
                            nextClickedElement = $();
                        });
                    })
                }
            } else {
                nextClickedElement = $(this);
            }
        }
    })

    $(document).on("click", ".country", function () {
        if (!settingsHidden) {
            if (!countryAnimate) {
                countryAnimate = true;
                if (countryHidden) {
                    $(this).find(".selected").animate({
                        "width": "100%"
                    }, 250, "easeInOutQuint");
                    $(this).find(".list").animate({
                        "background-position-x": "100%"
                    }, 250, "easeInOutQuint", function () {
                        $(this).find(".triangle").addClass("triangle-0");
                        $(this).animate({
                            "height": "145px"
                        }, 250, "easeInOutQuint", function () {
                            countryHidden = false;
                            countryAnimate = false;
                            nextClickedElement.click();
                            nextClickedElement = $();
                        });
                    });
                } else {
                    let width = $(this).find(".selected > div").first().width();
                    $(this).find(".triangle").removeClass("triangle-0");
                    $(this).find(".list").animate({
                        "height": "25px"
                    }, 250, "easeInOutQuint", function () {
                        $(this).animate({
                            "background-position-x": "199%"
                        }, 250, "easeInOutQuint");
                        $(this).find(".selected").animate({
                            "width": width + 26 + "px"
                        }, 250, "easeInOutQuint", function () {
                            countryHidden = true;
                            countryAnimate = false;
                            nextClickedElement.click();
                            nextClickedElement = $();
                        });
                    })
                }
            } else {
                nextClickedElement = $(this);
            }
        }
    })

    $(".language-settings").click(function () {
        if (!settingsHidden) {
            if (!languageAnimate) {
                languageAnimate = true;
                if (languageHidden) {
                    $(this).find(".selected").animate({
                        "width": "100%"
                    }, 250, "easeInOutQuint");
                    $(this).find(".list").animate({
                        "background-position-x": "100%"
                    }, 250, "easeInOutQuint", function () {
                        $(this).find(".triangle").addClass("triangle-0");
                        $(this).animate({
                            "height": "120px"
                        }, 250, "easeInOutQuint", function () {
                            languageHidden = false;
                            languageAnimate = false;
                            nextClickedElement.click();
                            nextClickedElement = $();
                        });
                    });
                } else {
                    let width = $(this).find(".selected > div").first().width();
                    $(this).find(".triangle").removeClass("triangle-0");
                    $(this).find(".list").animate({
                        "height": "25px"
                    }, 250, "easeInOutQuint", function () {
                        $(this).animate({
                            "background-position-x": "199%"
                        }, 250, "easeInOutQuint");
                        $(this).find(".selected").animate({
                            "width": width + 26 + "px"
                        }, 250, "easeInOutQuint", function () {
                            languageHidden = true;
                            languageAnimate = false;
                            nextClickedElement.click();
                            nextClickedElement = $();
                        });
                    })
                }
            } else {
                nextClickedElement = $(this);
            }
        }
    })

    $(".phone-code").click(function () {
        if (!settingsHidden) {
            if (!phoneCodeAnimate) {
                phoneCodeAnimate = true;
                if (phoneCodeHidden) {
                    $(this).find(".selected").animate({
                        "width": "100%"
                    }, 250, "easeInOutQuint");
                    $(this).find(".list").animate({
                        "background-position-x": "100%"
                    }, 250, "easeInOutQuint", function () {
                        $(this).find(".triangle").addClass("triangle-0");
                        $(this).animate({
                            "height": "95px"
                        }, 250, "easeInOutQuint", function () {
                            phoneCodeHidden = false;
                            phoneCodeAnimate = false;
                            nextClickedElement.click();
                            nextClickedElement = $();
                        });
                    });
                } else {
                    let width = $(this).find(".selected > div").first().width();
                    $(this).find(".triangle").removeClass("triangle-0");
                    $(this).find(".list").animate({
                        "height": "25px"
                    }, 250, "easeInOutQuint", function () {
                        $(this).animate({
                            "background-position-x": "199%"
                        }, 250, "easeInOutQuint");
                        $(this).find(".selected").animate({
                            "width": width + 26 + "px"
                        }, 250, "easeInOutQuint", function () {
                            phoneCodeHidden = true;
                            phoneCodeAnimate = false;
                            nextClickedElement.click();
                            nextClickedElement = $();
                        });
                    })
                }
            } else {
                nextClickedElement = $(this);
            }

        }
    })

    $(".sex").mouseleave(function () {
        if (!sexAnimate && !sexHidden) {
            $(this).click();
        }
    })

    $(document).on("mouseleave", ".country", function () {
        if (!countryAnimate && !countryHidden) {
            $(this).click();
        }
    })

    $(".language-settings").mouseleave(function () {
        if (!languageAnimate && !languageHidden) {
            $(this).click();
        }
    })

    $(".phone-code").mouseleave(function () {
        if (!phoneCodeAnimate && !phoneCodeHidden) {
            $(this).click();
        }
    })

    $(".sex, .country, .language-settings, .phone-code").find("li:not(.selected)").click(function () {
        $(this).parents(".list").find(".selected > div").first().text($(this).text());
        $(this).parents(".list").find(".selected > div").first().attr("identifier", $(this).attr("identifier"));
    })

    $("#personal-settings .change").click(function () {
        $("#change-avatar").trigger("click");
    })

    $("#change-avatar").change(function () {
        if (this.files && this.files[0]) {
            if (this.files[0].size <= 1048576) {
                let reader = new FileReader();
                reader.addEventListener("load", function (e) {
                    $("#personal-settings img").attr("src", e.target.result);
                    userImageFormat = e.target.result.slice(11, e.target.result.indexOf(";base64"));
                    userImage = e.target.result.slice(e.target.result.indexOf("base64") + 7);
                });
                reader.readAsDataURL(this.files[0]);
            } else {
                alert("File is too large!");
            }
        }
    });

    $(".checkbox").click(function () {
        if ($($(this).parents(".field")[0]).hasClass("checked")) {
            $($(this).parents(".field")[0]).removeClass("checked");
        } else {
            $($(this).parents(".field")[0]).addClass("checked");
        }
    })

    $(".radiobox").click(function () {
        $($(this).parents(".personalisation")[0]).find(".checked").removeClass("checked");
        $($(this).parents(".field")[0]).addClass("checked");
    })

    $("#locals a").click(function () {
        let url = new URL(document.location);
        url.searchParams.set("language", $(this).attr("language"));
        window.location.href = url.href;
    })

    $("#search-line").focusin(function () {
        if (!searchLineAnimate) {
            $("#search-result").animate({
                "height": $(".movie-result").length * 36 + "px"
            }, 300, "easeInOutQuint");
        }
    })

    $("#search-line").focusout(function () {
        // if (!searchLineAnimate) {
            $("#search-result").animate({
                "height": "0"
            }, 300, "easeInOutQuint");
        // }
    })

    $("#search-line").keyup(function () {
        let movieName = $(this).val();
        let resultList = "";
        if (movieName === "") {
            $("#search-result .simplebar-content").html(resultList);
            $("#search-result").animate({
                "height": "0"
            }, 300, "easeInOutQuint");
        } else {
            $.ajax({
                url: window.location.origin + "/movies/result?movieFullName=" + movieName,
                method: "GET"
            }).done(function (movies) {
                if (typeof isAdmin !== 'undefined') {
                    resultList += '<div class="movie-result"><a href="' + window.location.origin + '/admin/movie/create">' + createMovieValue + '</a></div>';
                }
                for (let movie in movies) {
                    resultList += '<div class="movie-result"><a href="' + window.location.origin + '/movie/' +
                        movies[movie].id + '?cinemaId=' + cinemaId + '">' + movies[movie].fullName + '</a></div>';
                }
                $("#search-result .simplebar-content").html(resultList);
                $("#search-result").animate({
                    "height": $(".movie-result").length * 36 + "px"
                }, 300, "easeInOutQuint");
            })
        }
    })

    function noScroll() {
        window.scrollTo(0, currentScrollPosition);
    }
})

function createTicket(ticketId, seanceDate, seanceTime, seanceHall, price, row, column, id, format, moviePicture, movieName, movieSurname) {
    let date = new Date(seanceDate.slice(-4) + "-" + seanceDate.slice(3, 5) + "-" + seanceDate.slice(0, 2));
    let current = new Date();
    current.setDate(current.getDate() + 1);
    if (current.getDate() <= 1) {
        current.setMonth(current.getMonth() + 1);
        if (current.getMonth() === 0) {
            current.setFullYear(current.getFullYear() + 1);
        }
    }

    let ticket = '<div class="ticket" identifier="' + ticketId + '"><div class="ticket-info"><div class="movie-name"><span class="first-name">' +
        movieName + '</span><span class="space">_</span><span class="last-name">' + movieSurname + '</span></div>' +
        '<div class="info-items"><div class="info-item price-info">' +
        '<span class="title">' + ticketPriceValue + ':</span><span class="space">_</span><span class="value">' + price + '</span>' +
        '<span class="space"></span><span class="value">$</span></div><div class="info-item date-info">' +
        '<span class="title">' + ticketDateValue + ':</span><span class="space">_</span><span class="value">' + seanceDate +
        '</span></div><div class="info-item row-info"><span class="title">' + ticketRowValue + ':</span><span class="space">_</span>' +
        '<span class="value">' + row + '</span></div><div class="info-item hall-info"><span class="title">' + ticketHallValue + ':</span>' +
        '<span class="space">_</span><span class="value">' + seanceHall + '</span></div><div class="info-item time-info">' +
        '<span class="title">' + ticketTimeValue + ':</span><span class="space">_</span><span class="value">' + seanceTime + '</span>' +
        '</div><div class="info-item seat-info"><span class="title">' + ticketSeatValue + ':</span><span class="space">_</span>' +
        '<span class="value">' + column + '</span></div></div><div class="barcode"><div class="barcode-container">' +
        '<svg class="barcode-value"></svg></div><div class="identifier">' + id + '</div></div><div class="copyright">' +
        'PlazaM</div></div><div class="picture"><img class="background-picture" src="data:image/' + format + ';base64,' + moviePicture +
        '" alt=""><div class="movie-name"><div class="first-name">' + movieName + '</div><div class="last-name">' + movieSurname +
        '</div></div><div class="blur"></div></div>';
    if (current <= date) {
        ticket += '<div class="cross active"><div class="icon-cross"></div></div>';
    }
    ticket += '</div>';

    return ticket;

    // $("#tickets .scroll .simplebar-content").append(ticketString);
    //
    // JsBarcode(".barcode-value", id, {
    //     height: 59,
    //     width: 2,
    //     textMargin: 0,
    //     displayValue: false,
    //     lineColor: "#0D0E0D",
    //     margin: 0
    // })
}

function ticketsFunction() {
    if (ticketsHidden) {
        $("#tickets .scroll .simplebar-content").remove(".ticket");

        // createTicket("99.99.9999", "99:99", "3D", "15", "3", "20", "identifier",
        //     "../img/jpg/wide/avenger_wide.jpg");
        // createTicket("99.99.9999", "99:99", "3D", "15", "3", "20", "identifier",
        //     "../img/jpg/wide/avenger_wide.jpg");

        $(".tickets-btn").animate({
            "font-size": "36px",
            "line-height": "36px",
            "margin": "6px 0"
        }, 500, "easeInOutQuint");
        $("#tickets").animate({
            "top": "0"
        }, 1000, "easeInOutQuint");
        $("#background").animate({
            "top": "0"
        }, 1000, "easeInOutQuint", function () {
            ticketsAnimate = false;
            ticketsHidden = false;
            nextClickedElement.click();
            nextClickedElement = $();
        });
    } else {
        $(".tickets-btn").animate({
            "font-size": "30px",
            "line-height": "30px",
            "margin": "10px 0"
        }, 500, "easeInOutQuint");
        $("#tickets").animate({
            "top": "100vh"
        }, 1000, "easeInOutQuint");
        $("#background").animate({
            "top": "100vh"
        }, 1000, "easeInOutQuint", function () {
            ticketsAnimate = false;
            ticketsHidden = true;
            nextClickedElement.click();
            nextClickedElement = $();
        });
    }
}

function generateCinemas() {
    startLoading();
    $.ajax({
        url: window.location.origin + "/cinemas/header",
        method: 'GET'
    }).done(function (data) {
        let cinemas = JSON.parse(data);
        let cinemaList = '<ul>';
        let count = 1;
        for (let cinema in cinemas) {
            cinemaList += '<li><div class="city"><div>';
            if (cinema === 'UKRAINE') {
                cinemaList += ukraine;
            }
            if (cinema === 'POLAND') {
                cinemaList += poland;
            }
            if (cinema === 'UNITED_KINGDOM') {
                cinemaList += unitedKingdom;
            }
            cinemaList += '</div><div><div class="triangle"></div>' +
                '</div></div><div class="cinema-list"><div class="cinema-back"><div class="triangle"></div></div><div class="scroll"><ul>';
            for (let i = 0; i < cinemas[cinema].length; i++) {
                cinemaList += '<li><a identifier="' + cinemas[cinema][i].id + '">' + cinemas[cinema][i].city + ' (' + cinemas[cinema][i].name + ')</a></li>';
                if (i < cinemas[cinema].length - 1) {
                    cinemaList += '<div class="line-between"></div>';
                }
            }
            cinemaList += '</ul></div></div></li>';
            if (count++ < Object.keys(cinemas).length) {
                cinemaList += '<div class="line-between city-line"></div>';
            }
        }
        $("#countries").html(cinemaList);
        $("#countries .scroll").each(function (index) {
            new SimpleBar($("#countries .scroll")[index], {
                autoHide: false
            });
        });
        stopLoading();
    });
}

function listsSaving() {
    if (typeof userId !== "undefined") {
        $.ajax({
            url: window.location.origin + "/user/" + userId + "/update/lists",
            method: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                addedFavouriteMovies: addedFavMovies,
                removedFavouriteMovies: removedFavMovies,
                addedWaitedMovies: addedWaitedMovies,
                removedWaitedMovies: removedWaitedMovies,
                addedViewedMovies: addedViewedMovies,
                addedTickets: addedTickets,
                removedTickets: removedTickets,
                addedMessages: addedMessages,
                removedMessages: removedMessages
                // removedTicketsSeances: removedTicketsSeances
            })
        });
    }
}

function startLoading() {
    clearTimeout(loadingTimeout);
    loadingTimeout = setTimeout(function () {
        if (loadingCount > 0) {
            stopLoading();
        }
    }, 5000);
    if (loadingCount++ === 0) {
        $("#loader .bar").css({
            "display": "block",
            "animation-iteration-count": "infinite"
        })
    }
}

function stopLoading() {
    if (--loadingCount === 0) {
        $("#loader .bar").css({
            "display": "none",
            "animation-iteration-count": "0"
        })
    }
}

// function generateMessages() {
//
//     }
//
// function generateTickets() {
//
// }