let commentsList;

$(document).ready(function () {

    {
        $("#comments-container .sort .underline").css("width", $("#comments-container .category.selected")
            .first().width() + 14 + "px");
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
            $.getScript("/resources/js/pages.js");
        })
    }

    $("#comments-container .scroll").each(function (index) {
        new SimpleBar($("#comments-container .scroll")[index], {
            autoHide: false
        });
    })

    $(".category").click(function () {
        if (!$(this).hasClass("selected")) {
            $(".categories").find(".selected").removeClass("selected");
            $(this).addClass("selected");
            $(".sort .underline").animate({
                left: $(this).position().left + "px",
                width: $(this).width() + 14 + "px"
            }, 300, "easeInOutQuint")

            $.ajax({
                url: window.location.origin + '/user/comments/1?sort=' + $(this).attr("identifier"),
                method: 'GET'
            }).done(function (data) {
                let comments = JSON.parse(data);
                $("#pages").html("");
                lastPage = comments[0];
                page = parseInt(new URLSearchParams(window.location.search).get("page"));
                page = isNaN(page) ? 1 : page;
                if (lastPage < 1) {
                    $("#pages").html("");
                } else {
                    setPagesValues(lastPage);
                    pagesAnimation();
                }
                $("#movies").html("");
                generateComments(comments[1]);
            })
        }
    })

    $(document).on("click", ".delete-btn", function () {
        $(this).parents(".comment").animate({
            "opacity": "0"
        }, 500, "linear", function () {
            $.ajax({
                url: window.location.origin + '/comment/' + $(this).attr("identifier") + '/remove',
                method: 'POST'
            });

            $(this).remove();

            if ($(".comment").length === 0) {
                $("#pages").html("");
            }
        })
    })

    $(document).on("focusin", "textarea", function () {
        $(this).css("height", "auto").delay(10).css("height",
            $(this).get(0).scrollHeight + "px");
    })

    $(document).on("focusout", "textarea", function () {
        let element = '<div class="value">' + $(this).val() + '</div>';
        $(this).parents(".comment").first().find(".change-btn").css("opacity", "1");

        $.ajax({
            url: window.location.origin + '/comment/' + $(this).parents(".comment").first().attr("identifier") + '/update?text=' + $(this).val(),
            method: 'POST'
        });

        $(this).replaceWith(element);
    })

    $(document).on("click", ".change-btn", function () {
        $(this).css("opacity", "0");
        let text = $(this).parents(".comment").first().find(".scroll .value").first().text();
        $(this).parents(".comment").first().find(".simplebar-content .value").replaceWith('<textarea spellcheck="false"></textarea>');
        $(this).parents(".comment").first().find("textarea").focus().val(text);
    })
})

function generateComments(comments) {
    let commentsElem = '';
    for (let comment in comments) {
        commentsElem += '<div class="comment" identifier="' + comments[comment].id + '"><div class="comment-left-side"><div class="picture">' +
            '<img alt="" src="data:image/' + comments[comment].movie.widePicture.format + ';base64,' + comments[comment].movie.widePicture.pictureString + '">' +
            '</div><div class="info"><div class="title"><a href="/movie/' + comments[comment].movie.id + '"><div class="first-name">' + comments[comment].movie.name +
            '</div><div class="last-name">' + comments[comment].movie.surname + '</div></a></div><div class="date"><div class="title-2">' + dateValue + ':</div>' +
            '<div class="space">-</div><div class="value">' + comments[comment].date.slice(-2) + '.' + comments[comment].date.slice(5, 7) + '.' +
            comments[comment].date.slice(0, 4) + '</div></div><div class="time"><div class="title-2">' + timeValue + ':</div><div class="space">-</div><div class="value">' +
            comments[comment].time.slice(11, 13) + ':' + comments[comment].time.slice(14, 16) + '</div></div></div></div><div class="comment-right-side">' +
            '<div class="comment-container"><div class="title-2">' + commentValue + '</div><div class="scroll"><div class="value">' + comments[comment].text + '</div>' +
            '</div></div><div class="buttons"><div class="button delete-btn"><div>' + deleteValue + '</div><div><svg xmlns="http://www.w3.org/2000/svg">' +
            '<line x1="0" x2="100%" y1="0" y2="100%"></line><line x1="100%" x2="0" y1="0" y2="100%"></line></svg></div></div><div class="button change-btn">' +
            changeValue + '</div></div></div></div>'
    }
    commentsElem += '<div class="curtain"></div>';
    $("#comments").html(commentsElem);
}