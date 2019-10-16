/*
* 提交回复
* */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    console.log(questionId);
    console.log(content);
    commentadd(questionId, 1, content);
}


function commentadd(targetId,type,content) {
    if (!content) {
        alert("评论内容不能为空哦亲~~~")
        return
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId" : targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                $("#comment_section").hide();
                window.location.reload();
            }else if (response.code == 2003) {
                var confirm1 = confirm(response.message);
                if (confirm1) {
                    window.open("https://github.com/login/oauth/authorize?client_id=Iv1.2ac2e219d61d6cf3&redirect_uri=http://localhost:8080/callBack&scope=user&state=1");
                    window.location.reload();
                    window.localStorage.setItem("closable", true);
                }
            } else {
                alert(response.message);
            }
        },
        dataType : "json"
    });
}

function comment2add(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    commentadd(commentId,2,content);
}


/*
* 二级回复展开
* */

function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comment = $("#comment-" + id);
    console.log(comment);
    //获取一下二级评论展开的状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comment.removeClass("in");
        e.removeAttribute("data-collapse");
        console.log(collapse);
        e.classList.remove("active");
    } else {

        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comment.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }else {
            $.getJSON("/comment/" + id,function (data) {
                $.each(data.data.reverse(),function (index,comment) {

                    var mediaLeftElement = $("<div />",{
                        class: "media-left",
                    }).append($("<img/>",{
                        class:"img-rounded media-object",
                        src : comment.user.avatars
                    }));

                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h4/>",{
                        "class":"media-heading",
                        "html":comment.user.username
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu",
                    }).append($("<span/>",{
                        "class":"comment-date",
                        "html": moment(comment.createTime).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        class: "col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-hr",
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    subCommentContainer.prepend(mediaElement); //在父元素前面
                });
                //展开二级评论
                comment.addClass("in");
                e.setAttribute("data-collapse", "in");
                console.log(collapse);
                e.classList.add("active");
            });
        }
    }
}

function selecTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if(previous.indexOf(value) == -1){
        if (previous) {
            $("#tag").val(previous + ',' + value);
        }else {
            $("#tag").val(value);
        }
    }
}

function showSelecTag() {
    $("#select-tag").show();
}

