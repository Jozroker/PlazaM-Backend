let inputValueLoop = false;
let loop;
let speed = 200;
let element;
nextClickedElement = $();
let fieldParent;
let positionLeft;
let userClick = true;
let actorPicture, movieWidePicture, moviePosterPicture, actorPictureFormat, movieWidePictureFormat, moviePosterPictureFormat;
let galleryPictures = [];
let galleryPicturesFormats = [];

let movieLanguageHidden = true;
let mpaaRatingHidden = true;
let genreCreationHidden = true;
let actorCreationHidden = true;

let movieLanguageAnimate = false;
let mpaaRatingAnimate = false;
let changeLanguageAnimate = false;
let genreCreationAnimate = false;
let actorCreationAnimate = false;

$(document).ready(function () {

    {
        $(".movie-language, .mpaa-rating").each(function () {
            $(this).find(".field").css("width", parseInt($(this).find(".selected div")
                .first().css("width").slice(0, -2)) + 39 + "px");
        })
        if ($("#day").val() === "") {
            let date = new Date();
            $("#day").val(date.getDate());
            $("#month").val(date.getMonth() + 1);
            $("#year").val(date.getFullYear());
        }

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
        })

        emptyGenres();
        generateActors();
        selectGenres();
    }

    $(window).resize(function () {
        emptyGenres();
    })

    $("#movie .scroll").each(function (index) {
        new SimpleBar($("#movie .scroll")[index], {
            autoHide: false
        });
    })

    $(".wide-picture .button").click(function () {
        $("#change-wide-picture").trigger("click");
    })

    $(".poster-picture .button").click(function () {
        $("#change-poster-picture").trigger("click");
    })

    $(".avatar-picture .button").click(function () {
        $("#change-actor-avatar").trigger("click");
    })

    $("#change-actor-avatar").change(function () {
        if (this.files && this.files[0]) {
            if (this.files[0].size <= 5001708) {
                let reader = new FileReader();
                reader.addEventListener("load", function (e) {
                    $(".avatar-picture img").attr("src", e.target.result);
                    actorPictureFormat = e.target.result.slice(11, e.target.result.indexOf(";base64"));
                    actorPicture = e.target.result.slice(e.target.result.indexOf("base64") + 7);
                });
                reader.readAsDataURL(this.files[0]);
            } else {
                alert("File is too large!");
            }
        }
    });

    $("#change-wide-picture").change(function () {
        if (this.files && this.files[0]) {
            if (this.files[0].size <= 5001708) {
                let reader = new FileReader();
                reader.addEventListener("load", function (e) {
                    $(".wide-picture img").attr("src", e.target.result);
                    movieWidePictureFormat = e.target.result.slice(11, e.target.result.indexOf(";base64"));
                    movieWidePicture = e.target.result.slice(e.target.result.indexOf("base64") + 7);
                });
                reader.readAsDataURL(this.files[0]);
            } else {
                alert("File is too large!");
            }
        }
    });

    $("#change-poster-picture").change(function () {
        if (this.files && this.files[0]) {
            if (this.files[0].size <= 5001708) {
                let reader = new FileReader();
                reader.addEventListener("load", function (e) {
                    $(".poster-picture img").attr("src", e.target.result);
                    moviePosterPictureFormat = e.target.result.slice(11, e.target.result.indexOf(";base64"));
                    moviePosterPicture = e.target.result.slice(e.target.result.indexOf("base64") + 7);
                });
                reader.readAsDataURL(this.files[0]);
            } else {
                alert("File is too large!");
            }
        }
    });

    $(".movie-language .selected").click(function () {
        if (!movieLanguageAnimate) {
            movieLanguageAnimate = true;

            if (movieLanguageHidden) {
                $(this).find(".triangle").addClass("triangle-0 selected-triangle");
                $(this).parents(".field").first().animate({
                    "width": "100%"
                }, 300, "easeInOutQuint").find(".list").animate({
                    "background-position-x": "100%"
                }, 300, "easeInOutQuint", function () {
                    $(this).animate({
                        "height": "112px"
                    }, 300, "easeInOutQuint", function () {
                        movieLanguageAnimate = false;
                        movieLanguageHidden = false;
                        nextClickedElement.click();
                        nextClickedElement = $();
                    })
                })
            } else {
                $(this).find(".triangle").removeClass("triangle-0 selected-triangle");
                $(this).parent(".list").animate({
                    "height": "22px"
                }, 300, "easeInOutQuint", function () {
                    $(this).animate({
                        "background-position-x": "200%"
                    }, 300, "easeInOutQuint").parent(".field").animate({
                        "width": parseInt($(this).find(".selected div").first().css("width")
                            .slice(0, -2)) + 39 + "px"
                    }, 300, "easeInOutQuint", function () {
                        movieLanguageAnimate = false;
                        movieLanguageHidden = true;
                        nextClickedElement.click();
                        nextClickedElement = $();
                    })
                })
            }
        } else {
            nextClickedElement = $(this);
        }
    })

    $(".movie-language .list").mouseleave(function () {
        if (!movieLanguageHidden && !movieLanguageAnimate) {
            $(this).find(".selected").click();
        }
    })

    $(".mpaa-rating .selected").click(function () {
        if (!mpaaRatingAnimate) {
            mpaaRatingAnimate = true;

            if (mpaaRatingHidden) {
                $(this).find(".triangle").addClass("triangle-0 selected-triangle");
                $(this).parents(".field").first().animate({
                    "width": "100%"
                }, 300, "easeInOutQuint").find(".list").animate({
                    "background-position-x": "100%"
                }, 300, "easeInOutQuint", function () {
                    $(this).animate({
                        "height": "152px"
                    }, 300, "easeInOutQuint", function () {
                        mpaaRatingHidden = false;
                        mpaaRatingAnimate = false;
                        nextClickedElement.click();
                        nextClickedElement = $();
                    })
                })
            } else {
                $(this).find(".triangle").removeClass("triangle-0 selected-triangle");
                $(this).parent(".list").animate({
                    "height": "22px"
                }, 300, "easeInOutQuint", function () {
                    $(this).animate({
                        "background-position-x": "200%"
                    }, 300, "easeInOutQuint").parent(".field").animate({
                        "width": parseInt($(this).find(".selected div").first().css("width")
                            .slice(0, -2)) + 39 + "px"
                    }, 300, "easeInOutQuint", function () {
                        mpaaRatingHidden = true;
                        mpaaRatingAnimate = false;
                        nextClickedElement.click();
                        nextClickedElement = $();
                    })
                })
            }
        } else {
            nextClickedElement = $(this);
        }
    })

    $(".mpaa-rating").mouseleave(function () {
        if (!mpaaRatingHidden && !mpaaRatingAnimate) {
            $(this).find(".selected").click();
        }
    })

    $(".movie-language li:not(.selected), .mpaa-rating li:not(.selected)").click(function () {
        let value = $(this).find("div").text();
        $(this).parent(".list").first().find(".selected").attr("identifier", $(this).attr("identifier"))
        $(this).parent(".list").first().find(".selected").click().find("div:first-child").text(value);
    })

    $(".triangle-left").click(function () {
        //todo universal calculations
        if (!changeLanguageAnimate) {
            changeLanguageAnimate = true;

            fieldParent = $(this).next().find(".languages");
            switch (parseInt(fieldParent.css("left").slice(0, -2))) {
                case 0:
                    positionLeft = "-200%"
                    break;
                case parseInt(fieldParent.css("width").slice(0, -2)) * (-1):
                case parseInt(fieldParent.css("width").slice(0, -2)) * (-1) + 1:
                case parseInt(fieldParent.css("width").slice(0, -2)) * (-1) - 1:
                    positionLeft = "0";
                    break;
                case parseInt(fieldParent.css("width").slice(0, -2)) * (-2):
                case parseInt(fieldParent.css("width").slice(0, -2)) * (-2) + 1:
                case parseInt(fieldParent.css("width").slice(0, -2)) * (-2) - 1:
                    positionLeft = "-100%"
                    break;
                default:
                    break;
            }
            fieldParent.animate({
                "left": positionLeft
            }, 500, "easeInOutQuint", function () {
                changeLanguageAnimate = false;
            });
            fieldParent.parents(".top").first().next().find(".fields-container").animate({
                "left": positionLeft
            }, 500, "easeInOutQuint");
            fieldParent.parents(".create-genre").first().find(".fields-container").animate({
                "left": positionLeft
            }, 500, "easeInOutQuint");
            fieldParent.parents(".actor-form").first().find(".fields-container").animate({
                "left": positionLeft
            }, 500, "easeInOutQuint");
        }
    })

    $(".triangle-right").click(function () {
        //todo universal calculations
        if (!changeLanguageAnimate) {
            changeLanguageAnimate = true;

            fieldParent = $(this).prev().find(".languages");
            switch (parseInt(fieldParent.css("left").slice(0, -2))) {
                case 0:
                    positionLeft = "-100%"
                    break;
                case parseInt(fieldParent.css("width").slice(0, -2)) * (-1):
                case parseInt(fieldParent.css("width").slice(0, -2)) * (-1) + 1:
                case parseInt(fieldParent.css("width").slice(0, -2)) * (-1) - 1:
                    positionLeft = "-200%";
                    break;
                case parseInt(fieldParent.css("width").slice(0, -2)) * (-2):
                case parseInt(fieldParent.css("width").slice(0, -2)) * (-2) + 1:
                case parseInt(fieldParent.css("width").slice(0, -2)) * (-2) - 1:
                    positionLeft = "0";
                    break;
                default:
                    break;
            }
            fieldParent.animate({
                "left": positionLeft
            }, 500, "easeInOutQuint", function () {
                changeLanguageAnimate = false;
            });
            fieldParent.parents(".top").first().next().find(".fields-container").animate({
                "left": positionLeft
            }, 500, "easeInOutQuint");
            fieldParent.parents(".create-genre").first().find(".fields-container").animate({
                "left": positionLeft
            }, 500, "easeInOutQuint");
            fieldParent.parents(".actor-form").first().find(".fields-container").animate({
                "left": positionLeft
            }, 500, "easeInOutQuint");
        }
    })

    $("textarea").on("input", function () {
        $(this).css("height", "auto").delay(10).css("height",
            $(this).get(0).scrollHeight + "px");
    })

    $("#imdb-rating").focusout(function () {
        if (parseFloat($(this).val()) > 10) {
            $(this).val(10);
        } else if (parseFloat($(this).val()) <= 0) {
            $(this).val(0.1);
        }
        $(this).val(parseFloat($(this).val().replace(',', '.')));
    })

    $("#year").focusout(function () {
        if (parseInt($(this).val()) > 9999) {
            $(this).val(9999);
        } else if (parseInt($(this).val()) < 2020) {
            $(this).val(2020);
        }
    })

    $("#month").focusout(function () {
        if (parseInt($(this).val()) > 12) {
            $(this).val(12);
        } else if (parseInt($(this).val()) < 1) {
            $(this).val(1);
        }
    })

    $("#day").focusout(function () {
        let date = new Date(parseInt($("#year").val()), parseInt($("#month").val()), 0);
        if (parseInt($(this).val()) > date.getDate()) {
            $(this).val(date.getDate());
        } else if (parseInt($(this).val()) < 1) {
            $(this).val(1);
        }
    })

    $(document).on("click", ".genre", function () {
        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected").animate({
                "background-position-x": "100%"
            }, 300, "easeInOutQuint", function () {
                selectedGenres = selectedGenres.filter(function (value, index, arr) {
                    value != $(this).attr("id");
                })
            });
        } else {
            $(this).addClass("selected").animate({
                "background-position-x": "0%"
            }, 300, "easeInOutQuint", function () {
                if (!selectedGenres.includes($(this).attr("id"))) {
                    selectedGenres.push($(this).attr("id"));
                }
            });
        }
    })

    // $(".name .title").click(function () {
    //     $("#movie .name, #movie .surname").animate({
    //         "left": "-100%"
    //     }, 600, "easeInOutQuint");
    // })

    // $(".surname .title").click(function () {
    //     $("#movie .name, #movie .surname").animate({
    //         "left": "0"
    //     }, 600, "easeInOutQuint");
    // })

    $(document).on("click", ".actor", function () {
        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected").animate({
                "background-position-x": "100%"
            }, 300, "easeInOutQuint", function () {
                selectedActors = selectedActors.filter(function (value, index, arr) {
                    value != $(this).attr("identifier");
                })
                if ($(".actor.selected").length > 0) {
                    $($(".actor.selected").last()).after($(this));
                }
            });
        } else {
            $(this).addClass("selected").animate({
                "background-position-x": "0%"
            }, 300, "easeInOutQuint", function () {
                if (!selectedActors.includes($(this).attr("identifier"))) {
                    selectedActors.push($(this).attr("identifier"));
                }
                if ($(".actor.selected").length > 1) {
                    $($(".actor.selected").first()).before($(this));
                } else {
                    $($(".actor").first()).before($(this));
                }
            });
        }
    })

    $(".plus").mousedown(function () {
        element = $(this);
        element.parent("div").find("input").get()[0].stepUp();
        inputValueLoop = true;

        setTimeout(function () {
            if (inputValueLoop) {
                loopInputValue(true);
            }
        }, 100);

        setTimeout(function () {
            if (inputValueLoop) {
                speed = 100;
                setTimeout(function () {
                    if (inputValueLoop) {
                        speed = 10;
                        setTimeout(function () {
                            if (inputValueLoop) {
                                speed = 1;
                            }
                        }, 2000);
                    }
                }, 2000);
            }
        }, 2000);
    })

    $(".create-genre .button").click(function () {
        if (!genreCreationAnimate) {
            genreCreationAnimate = true;

            if (genreCreationHidden) {
                $(".create-genre").animate({
                    "bottom": "0px"
                }, 500, "easeInOutQuint", function () {
                    genreCreationHidden = false;
                    genreCreationAnimate = false;
                    nextClickedElement.click();
                    nextClickedElement = $();
                })
            } else {
                if (userClick) {
                    //todo if field is not empty create genre request start
                    $(".create-genre input").val("");
                } else {
                    userClick = true;
                }
                $(".create-genre").animate({
                    "bottom": "-57px"
                }, 500, "easeInOutQuint", function () {
                    genreCreationHidden = true;
                    genreCreationAnimate = false;
                    nextClickedElement.click();
                    nextClickedElement = $();
                })
            }
        } else {
            nextClickedElement = $(this);
        }
    })

    $(".create-genre").mouseleave(function () {
        if ((!genreCreationHidden && !genreCreationAnimate) || (genreCreationHidden && genreCreationAnimate)) {
            userClick = false;
            $(this).find(".button").click();
        }
    })

    $(".actor-form .button").first().click(function () {
        if (!actorCreationAnimate) {
            actorCreationAnimate = true;

            if (actorCreationHidden) {
                $(".actor-form").animate({
                    "bottom": "0px"
                }, 500, "easeInOutQuint", function () {
                    actorCreationHidden = false;
                    actorCreationAnimate = false;
                    nextClickedElement.click();
                    nextClickedElement = $();
                })
            } else {
                if (userClick) {
                    //todo if field is not empty create genre request start
                    let fieldsIsFilled = true;
                    $(".actor-form input:not(#change-actor-avatar)").each(function () {
                        if ($(this).val() === '') {
                            fieldsIsFilled = false;
                        }
                    })
                    if (fieldsIsFilled) {
                        if (typeof startLoading !== 'undefined') {
                            startLoading();
                        }
                        let firstNameEng = $($(".actor-form input")[1]).val().trim();
                        let lastNameEng = $($(".actor-form input")[4]).val().trim();
                        let firstNameUkr = $($(".actor-form input")[2]).val().trim();
                        let lastNameUkr = $($(".actor-form input")[5]).val().trim();
                        let firstNamePol = $($(".actor-form input")[3]).val().trim();
                        let lastNamePol = $($(".actor-form input")[6]).val().trim();
                        $.ajax({
                            url: window.location.origin + '/admin/create/actor',
                            method: 'POST',
                            contentType: "application/json; charset=utf-8",
                            data: JSON.stringify({
                                firstNameEng: firstNameEng,
                                lastNameEng: lastNameEng,
                                firstNameUkr: firstNameUkr,
                                lastNameUkr: lastNameUkr,
                                firstNamePol: firstNamePol,
                                lastNamePol: lastNamePol,
                                picture: actorPicture,
                                pictureFormat: actorPictureFormat
                            })
                        }).done(function (actor) {
                            let actorResponse = JSON.parse(actor);
                            let newActor = '<div identifier="' + actorResponse.id + '" class="actor"><div class="icon-tick"></div><img alt="" class="actor-avatar" ' +
                                'src="data:image/' + actorResponse.picture.format + ';base64,' + actorResponse.picture.pictureString + '"><div class="actor-name">' +
                                actorResponse.firstName + ' ' + actorResponse.lastName + '</div></div>';
                            $(".actors-container").append(newActor);
                            $(".actor-form input").val("");
                            $("#actor-avatar").attr("src", "/resources/img/jpg/default_avatar.jpg");
                            actorPicture = null;
                            actorPictureFormat = null;
                            if (typeof stopLoading !== 'undefined') {
                                stopLoading();
                            }
                        })
                    }
                } else {
                    userClick = true;
                }
                $(".actor-form").animate({
                    "bottom": "-148px"
                }, 500, "easeInOutQuint", function () {
                    actorCreationHidden = true;
                    actorCreationAnimate = false;
                    nextClickedElement.click();
                    nextClickedElement = $();
                })
            }
        } else {
            nextClickedElement = $(this);
        }
    })

    // $(".actor-form").mouseleave(function () {
    //     if ((!actorCreationHidden && !actorCreationAnimate) || (actorCreationHidden && actorCreationAnimate)) {
    //         userClick = false;
    //         $(this).find(".button").first().click();
    //     }
    // })

    $(".gallery .add-button").click(function () {
        $("#add-movie-picture").trigger("click");
    })

    $("#actor-search-line").keyup(function () {
        $(".actor").each(function () {
            if ($(this).find(".actor-name").text().toLowerCase().includes($("#actor-search-line").val().trim().toLowerCase())) {
                $(this).css("display", "flex");
            } else {
                $(this).css("display", "none");
            }
        })
    })

    $(document).on("click", ".gallery-image .cross", function () {
        let elementIndex = $(this).parents(".gallery-image").first().index();
        galleryPictures.splice(elementIndex, 1);
        galleryPicturesFormats.splice(elementIndex, 1);
        $(this).parents(".gallery-image").first().remove();
    })

    $("#create-button, #change-button").click(function () {
        let nameEng = $(".name .en-field input").val().trim();
        let nameUkr = $(".name .ua-field input").val().trim();
        let namePol = $(".name .pl-field input").val().trim();
        let surnameEng = $(".surname .en-field input").val().trim();
        let surnameUkr = $(".surname .ua-field input").val().trim();
        let surnamePol = $(".surname .pl-field input").val().trim();
        let duration = $("#duration").val().trim();
        let directedByEng = $(".directed-by .en-field input").val().trim();
        let directedByUkr = $(".directed-by .ua-field input").val().trim();
        let directedByPol = $(".directed-by .pl-field input").val().trim();
        let releaseDate = new Date(parseInt($("#year").val().trim()), parseInt($("#month").val().trim()) - 1, parseInt($("#day").val().trim()));
        let countryEng = $(".country .en-field input").val().trim();
        let countryUkr = $(".country .ua-field input").val().trim();
        let countryPol = $(".country .pl-field input").val().trim();
        let movieLang = $(".movie-language .selected").attr("identifier");
        let mpaa = $(".mpaa-rating .selected").attr("identifier");
        let imdb = $("#imdb-rating").val().trim().replace(',', '.');
        let descEng = $(".description .en-field textarea").val().trim();
        let descUkr = $(".description .ua-field textarea").val().trim();
        let descPol = $(".description .pl-field textarea").val().trim();

        if (nameEng !== '' && nameUkr !== '' && namePol !== '' && duration !== '' && directedByEng !== '' && directedByUkr !== '' && directedByPol !== '' &&
            !isNaN(releaseDate) && countryEng !== '' && countryUkr !== '' && countryPol !== '' && movieLang !== 'NULL' && mpaa !== 'NULL' && descEng !== '' &&
            descUkr !== '' && descPol !== '' && selectedActors.length > 0 && selectedGenres.length > 0) {
            if (typeof startLoading !== 'undefined') {
                startLoading();
            }
            $.ajax({
                url: window.location.origin + '/admin/movie' + (typeof movieId === 'undefined' ? '/create' : '/change/' + movieId),
                method: 'POST',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    nameEng: nameEng,
                    nameUkr: nameUkr,
                    namePol: namePol,
                    surnameEng: surnameEng,
                    surnameUkr: surnameUkr,
                    surnamePol: surnamePol,
                    duration: duration,
                    directedByEng: directedByEng,
                    directedByUkr: directedByUkr,
                    directedByPol: directedByPol,
                    releaseDate: releaseDate.getFullYear() + '-' + (parseInt(releaseDate.getMonth()) + 1 < 10 ? '0' + (parseInt(releaseDate.getMonth()) + 1) : releaseDate.getMonth() + 1) +
                        '-' + (parseInt(releaseDate.getDate()) < 10 ? '0' + parseInt(releaseDate.getDate()) : releaseDate.getDate()),
                    countryEng: countryEng,
                    countryUkr: countryUkr,
                    countryPol: countryPol,
                    movieLang: movieLang,
                    mpaa: mpaa,
                    imdb: imdb,
                    descEng: descEng,
                    descUkr: descUkr,
                    descPol: descPol,
                    movieWidePicture: movieWidePicture,
                    movieWidePictureFormat: movieWidePictureFormat,
                    moviePosterPicture: moviePosterPicture,
                    moviePosterPictureFormat: moviePosterPictureFormat,
                    galleryPictures: galleryPictures,
                    galleryPicturesFormats: galleryPicturesFormats,
                    selectedGenres: selectedGenres,
                    selectedActors: selectedActors
                })
            }).done(function (url) {
                window.location.href = url;
            })
        }
    })

    $("#add-movie-picture").change(function () {
        //todo create multiple picture load
        if (this.files && this.files[0]) {
            if (this.files[0].size <= 5001708) {
                let reader = new FileReader();
                reader.addEventListener("load", function (e) {
                    let picture = '<div class="gallery-image editable">' +
                        '<img src="' + e.target.result + '" alt="">' +
                        '<div class="cross"><div class="icon-cross"></div></div></div>';
                    galleryPicturesFormats.unshift(e.target.result.slice(11, e.target.result.indexOf(";base64")));
                    galleryPictures.unshift(e.target.result.slice(e.target.result.indexOf("base64") + 7));
                    $(".gallery").prepend(picture);
                    // $(".gallery").prepend(picture).find(".cross").click(function () {
                    //     console.log(1)
                    //     let img = $(this).parents(".gallery-image").first().find("img");
                    //     let imgIndex = galleryPictures.indexOf($(img).attr("src").slice($(img).attr("src").indexOf("base64") + 7));
                    //     console.log(imgIndex);
                    //     galleryPictures = galleryPictures.filter(function(value, index, arr) {
                    //         index !== elementIndex;
                    //     })
                    //     galleryPicturesFormats = galleryPicturesFormats.filter(function(value, index, arr) {
                    //         index !== elementIndex;
                    //     })
                    //     $(this).parents(".gallery-image").first().remove();
                    // })
                });
                reader.readAsDataURL(this.files[0]);
            } else {
                alert("File is too large!");
            }
        }
    });

    $(".plus").mouseup(function () {
        inputValueLoop = false;
        speed = 200;
        clearTimeout(loop);
    })

    $(".minus").mousedown(function () {
        element = $(this);
        element.parent("div").find("input").get()[0].stepDown();
        inputValueLoop = true;

        setTimeout(function () {
            if (inputValueLoop) {
                loopInputValue(false);
            }
        }, 100);

        setTimeout(function () {
            if (inputValueLoop) {
                speed = 100;
                setTimeout(function () {
                    if (inputValueLoop) {
                        speed = 10;
                        setTimeout(function () {
                            if (inputValueLoop) {
                                speed = 1;
                            }
                        }, 2000);
                    }
                }, 2000);
            }
        }, 2000);
    })

    $(".minus").mouseup(function () {
        inputValueLoop = false;
        speed = 200;
        clearTimeout(loop);
    })

    //todo create validators for fields by creation event

    function generateActors() {
        let currentActor = '';
        let actorsList = '';
        let selectedActorsList = '';
        let selected = false;
        for (let actor in actors) {
            currentActor += '<div identifier="' + actors[actor][0] + '" class="actor';
            if (typeof selectedActors !== 'undefined') {
                if (selectedActors.includes(actors[actor][0])) {
                    currentActor += ' selected';
                    selected = true;
                }
            }
            currentActor += '"><div class="icon-tick"></div><img alt="" class="actor-avatar" src="data:image/' + actors[actor][3] + ';base64,' + actors[actor][4] + '">' +
                '<div class="actor-name">' + actors[actor][1] + ' ' + actors[actor][2] + '</div></div>';
            if (selected) {
                selected = false;
                selectedActorsList += currentActor;
            } else {
                actorsList += currentActor;
            }
            currentActor = '';
        }
        $(".actors-container").html(selectedActorsList + actorsList);
        setTimeout(function () {
            selectActors();
        }, 100);
        // if (typeof selectedActors !== 'undefined') {
        //     let startIndex = 0;
        //     $(".actor.selected").each(function () {
        //         let currentSelectedActor = $(this).clone();
        //         $(this).replaceWith($(".actor:not(.selected)").first());
        //         $(".actor:not(.selected)").first().replaceWith(currentSelectedActor);
        //     })
        // }
    }

    function selectActors() {
        $(".actor.selected").each(function () {
            $(this).removeClass("selected").click();
        })
        // if (typeof selectedActors !== 'undefined') {
        //     $(".actor")
        //     if (selectedActors.includes(actors[actor][0])) {
        //         console.log(1)
        //         actorsList += ' selected';
        //     }
        // }
    }

    function selectGenres() {
        if (typeof selectedGenres !== 'undefined') {
            for (let genre in selectedGenres) {
                let id = '#' + selectedGenres[genre];
                setTimeout(function () {
                    $(id).click();
                }, 100);
            }
        }
    }
})

function loopInputValue(value) {
    loop = setTimeout(function () {
        // $("#price").val(parseFloat($("#price").val()) + value);
        if (value) {
            element.parent("div").find("input").get()[0].stepUp();
        } else {
            element.parent("div").find("input").get()[0].stepDown();
        }
        if (inputValueLoop) {
            loopInputValue(value);
        }
    }, speed);
}

function emptyGenres() {
    if ($(".genres-container").width() > 409) {
        if ($(".genre").length % 3 === 0) {
            $(".empty-genre").css("display", "none");
        } else if ($(".genre").length % 3 === 1) {
            $(".empty-genre").css("display", "block");
        } else {
            $(".empty-genre").first().css("display", "block");
            $(".empty-genre").last().css("display", "none");
        }
    } else if ($(".genres-container").width() <= 409 && $(".genres-container").width() > 266) {
        if ($(".genre").length % 2 === 0) {
            $(".empty-genre").css("display", "none");
        } else {
            $(".empty-genre").first().css("display", "block");
            $(".empty-genre").last().css("display", "none");
        }
    } else {
        $(".empty-genre").css("display", "none");
    }
}

