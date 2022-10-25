
function comment2target(parentId, type, content){
    if(!content){
        layer.msg("评论不能为空");
        return;
    }
    $.ajax({
        url: "/yuanzuoBlog/comment",
        type: "POST", contentType:"application/json",
        dataType:"json",
        data: JSON.stringify({
            "parentId":parentId,
            "content": content,
            "type":type,
        }),
        success:function(data){
            if(data.code === 0){
                layer.msg("发布成功");
                $("#comment_section").hide();
            }else if(data.code === 500209){
                let isAccepted = confirm(data.msg);
                if(isAccepted){
                    window.open("https://github.com/login/oauth/authorize?client_id=1aa465cf359c8e1dc8f9&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                    window.localStorage.setItem("closable", "true");
                }
            }else{
                layer.msg(data.msg);
            }
        },
    });
}

function publish(){
    let blogId = $("#blogId").val();
    let content = $("#content").val();
    comment2target(blogId, 1,content)
}

function comment(e){
    let commentId = e.getAttribute("data-id");
    let content = $("#input-" + commentId).val();
    comment2target(commentId, 2,content);
}
//展开二级评论
function collapseComments(e){
    let id = e.getAttribute("data-id");
    let comments = $("#comment-" + id);

    //获取打开状态
    let collapse = e.getAttribute("data-collapse");
    if(collapse){
        comments.removeClass("in");
        // e.removeClass("active");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else {
        let subCommentContainer = $("#comment-"+id);
        if(subCommentContainer.children().length === 1){
            $.getJSON( "/yuanzuoBlog/comment/"+id, function( data ) {
                $.each( data.data, function(index,comment) {
                    let mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl,
                    }));

                    let mediaBodyElement = $("<div/>",{
                        "class":"media-body",
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "class":"media-heading",
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu",
                    }).append($("<span/>",{
                        "class":"glyphicon glyphicon-thumbs-up icon",
                    })).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtModify).format("YYYY-mm-DD")
                    })));

                    let mediaElement = $("<div/>",{
                       "class":"media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);

                    let commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    });
                    commentElement.append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
            });
        }
        //展开二级评论
        $(comments).addClass("in");
        // e.addClass("active");
        //标记二级评论展开状态
        e.setAttribute("data-collapse","in");
        e.classList.add("active");
    }
}
//发布文章
function publishBlog(){
    let title = $("#title").val();
    let content = $("#content").val();
    let tag = $("#tag").val();
    let id = $("#id").val();
    $.ajax({
        url: "/yuanzuoBlog/publish",
        type: "POST",
        data:{
            title:title,
            content: content,
            tag:tag,
            creator:id,
        },
        success:function(data){
            if(data.code === 0){
                layer.msg("发布成功");
            }else{
                layer.msg(data.msg);
            }
        },

    });
}
//选择标签
function selectTag(e){
    let value = e.getAttribute("data-tag");
    let previous = $("#tag").val();
    if(previous.indexOf(value) === -1){
        if(previous){
            $("#tag").val(previous+','+value);
        }else{
            $("#tag").val(value);
        }
    }
}

function showSelectTag(){
    $("#select-tag").show();
}
