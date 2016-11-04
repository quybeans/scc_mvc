/**
 * Created by QuyBean on 11/2/2016.
 */

//Ask me if you are confused QUYDV
var happyicon = 'fa fa-smile-o pull-right happy';
var sadicon = 'fa fa-frown-o pull-right sad';
var listPageFilter = new Array();
//Hard image link for account user.
var graphImage = "https://graph.facebook.com/";


startup();

function startup() {
//Get post every 5s
    getAllCrawlPage();

    $('#list-fb-account').empty();
    getAllFBAccount();
    getAllPageAccount();

}

//Reply to comment
function replyToComment(objId) {
    $('#send-progress').attr('class', 'fa fa-paper-plane');
    $('#replyModal').modal('toggle');
    $('#btnReply').unbind().click(function () {
        var token = $('#reply-token').val();
        if ($('#txtReply').val().length > 0) {
            sendComment(objId, $('#txtReply').val(), token);
        }
        else {
            alert("Enter some thing to comment..")
        }
    });
}


//Set onclick account
function setOnclickReplyAccount(imgsrc, name, token) {
    $('#reply-img').attr("src", "http://graph.facebook.com/" + imgsrc + "/picture");
    $('#reply-name').html(name);
    $('#reply-token').val(token);
}

//get All Facebook account belong to user
function getAllFBAccount() {
    $.ajax({
        url: '/allFbAccount',
        type: "GET",
        dataType: "json",
        success: function (data) {
            $('#list-fb-account').empty();
            $('#list-fb-account').html('<li class="dropdown-header" style="text-align: center">Facebook account</li>');
            $.each(data, function (index) {
                $('#list-fb-account').append(
                    '<li onclick="setOnclickReplyAccount(' + data[index].facebookuserid + ',\'' + data[index].facebookusername + '\',\'' + data[index].accesstoken + '\')">'
                    + '<a><img height="30px" width="30px" style="border-radius: 3px" src="http://graph.facebook.com/' + data[index].facebookuserid + '/picture" alt="user image"> &nbsp;' + data[index].facebookusername + '</a>' +
                    '</li>'
                )
            })
        }
    });
}
//get All Facebook account belong to user
function getAllPageAccount() {
    $.ajax({
        url: '/allPageAccount',
        type: "GET",
        dataType: "json",
        success: function (data) {
            $('#list-fb-account').append('<li class="dropdown-header" style="text-align: center">Page account</li>');
            $.each(data, function (index) {
                $('#list-fb-account').append(
                    '<li onclick="setOnclickReplyAccount(' + data[index].pageid + ',\'' + data[index].name + '\',\'' + data[index].accesstoken + '\')">'
                    + '<a><img height="30px" width="30px" style="border-radius: 3px" src="http://graph.facebook.com/' + data[index].pageid + '/picture" alt="user image"> &nbsp;' + data[index].name + '</a>' +
                    '</li>'
                )
            })
        }
    });
}

//Get all posts
function getAllPosts(pagelist) {
    if (pagelist.length != 0) {
        $.ajax({
            url: '/allPostsByBrand',
            type: "POST",
            data: {pagelist: pagelist.toString()},
            dataType: "json",
            success: function (data) {
                $('#post-box').empty();
                $.each(data, function (index) {
                    $('#post-box').append(
                        '<div class="item" style="position:relative" onclick="getCommentById(' + data[index].id + ',1)">'
                        + '<img onload="http://localhost:8080/img/user_img.jpg" src="http://graph.facebook.com/' + data[index].createdBy + '/picture" alt="user image">'
                        + '<p class="message">'
                        + '<a class="name">'
                        + '<small class="text-muted pull-right">'
                        + jQuery.format.prettyDate(new Date(data[index].createdAt))
                        + '</small>'
                        + data[index].createdByName
                        + ' </a>'
                        + '</p>'
                        + '<p style="margin-top: 30px;height: 35px;overflow: hidden">' + data[index].content + '</p>'
                        + '<div style="color: gray; bottom: 0px; position: absolute; width: 90%;text-align: center">'
                        + '<div class="inline"  style="margin-right: 10px"><span class="glyphicon glyphicon-thumbs-up"></span>&nbsp;' + data[index].likesCount + '</div>'
                        + '<div class="inline"style="margin-right: 10px"><span class="glyphicon glyphicon-comment"></span>&nbsp;' + data[index].commentsCount + ' </div>'
                        + '<div class="inline" style="margin-right: 10px"> <span class="glyphicon glyphicon-share-alt"></span>&nbsp;' + data[index].sharesCount + '</div>'
                        + '</div>'
                        + '</div>'
                    )
                });
            }
        });
    }
    else {
        $('#post-box').empty();
        $('#post-box').html('<div style="text-align: center; margin-top: 50%; font-weight: bold">Select a page to show posts</div>');
    }
}

//Ger reply for comment
function getReplyByCommentId(commentId) {

    $('#reply-list').empty();
    $('#reply-list').html('Loading....');
    $.ajax({
        url: '/commentbypost',
        type: "GET",
        data: {postId: commentId},
        dataType: "json",
        success: function (data) {
            $('#reply-list').empty();
            $.each(data, function (index) {
                $('#reply-list').append(
                    '<div class="cmt" style="max-height:10vh;">'
                    + '<div class="cmtContent">'
                    + '<img src="http://graph.facebook.com/' + data[index].createdBy + '/picture" height="50px" width="50px">'
                    + '<p class="message">'
                    + '<a>' + data[index].createdByName + '</a>'
                    + '<p style="margin-left: 65px; margin-top: -10px">' + data[index].content + '</p>'
                    + '</p>'
                    + '</div>'
                    + '</div>'
                )
            })

        }
    })
}
//Get comments for posts
function getCommentById(postId,page) {
    var happycount = 0;
    var sadcount = 0;
    $('#comment-box').empty();
    $('#comment-box').html('Loading....');
    getPostById(postId);

    $.ajax({
        url: 'post/sentimentcount',
        type: "GET",
        data: {postid: postId},
        dataType: "json",
        success: function (result) {happycount=result[0];sadcount=result[1]}});
    
    $.ajax({
        url: 'comment/getallcomment',
        type: "GET",
        data: {postid: postId, page:page},
        dataType: "json",
        success: function (result) {

            // Pagination
            var data = result.content;
            commentPagination(parseInt(result.totalPages),postId);


            $('#comment-box').empty();
            $.each(data, function (index) {
                 var senIcon = sadicon;
                if (data[index].sentimentScore == 1)
                    senIcon = happyicon;



                var cmtId = "'" + postId + "_" + data[index].id + "'";
                // groupbutton chua 2 nut là reply, danh tag
                var groupbutton = '<button  onclick="replyToComment(' + cmtId + ');getReplyByCommentId(' + data[index].id + ');" class="btn btn-default btn-xs inline"' +
                    ' style="margin-left: 65px;margin-top: -10px; "><span class="glyphicon glyphicon-send"' +
                    ' style="color:gray "  title="Reply to this comment"   data-placement="bottom" ' +
                    'data-toggle="tooltip" ></span></button>'

                    + '<button  onclick="showtagcomment(' + data[index].id + ')" class="btn btn-default btn-xs inline"' +
                    ' style="margin-top: -10px;" ><span class="fa fa-tag"' +
                    ' style="color:gray "  title="Tag this comment to attribute"   data-placement="bottom" ' +
                    'data-toggle="popover" ></span></button>';

                //khi comment la mot ticket
                if (data[index].ticket) {

                    //Them nut change stt
                    groupbutton += '<button class="btn btn-default btn-xs inline" onclick="status(' + data[index].id + ')"' +
                        ' style="margin-top: -10px; "><span style="color:gray " ' +
                        'title="Change ticket status" data-toggle="tooltip"  data-placement="bottom" ' +
                        ' class="fa fa-navicon"></span></button>'

                    //khi nguoi dang nhap duoi quyen staff
                    if (data[index].staff) {
                        //them nut forward ticket
                        groupbutton += '<button class="btn btn-default btn-xs inline" onclick="forwardticket(' + data[index].id + ')"' +
                            ' style="margin-top: -10px; "><span style="color:gray " ' +
                            'title="Forward this ticket to another staff" data-toggle="tooltip"  data-placement="bottom" ' +
                            ' class="fa fa-group"></span></button>'
                        if (!data[index].ticketofstaff) {
                            groupbutton = "";
                        }
                        //khi nguoi dang nhap duoi quyen khac
                    } else {
                        //Them nut assign
                        groupbutton += '<button class="btn btn-default btn-xs inline" onclick="assign(' + data[index].id + ')"' +
                            ' style="margin-top: -10px; "><span style="color:gray " ' +
                            'title="Assign this ticket to staff" data-toggle="tooltip"  data-placement="bottom" ' +
                            ' class="fa fa-ticket"></span></button>'
                    }

                    //khi comment chua phai la ticket
                } else {
                    //khi nguoi dang nhap duoi quyen staff
                    if (data[index].staff) {
                        //them nut assign cho ban than no
                        groupbutton += '<button class="btn btn-default btn-xs inline" onclick="createTicketForTheStaff(' + data[index].id + ',' + data[index].postId + ')" ' +
                            ' style="margin-top: -10px; "><span style="color:gray " ' +
                            'title="Create new ticket for yourself" data-toggle="tooltip"  data-placement="bottom" ' +
                            ' class="fa fa-plus-square"></span></button>'

                        //khi nguoi dang nhap duoi quyen khac
                    } else {
                        //them nut create ticket
                        groupbutton += '<button  onclick="createticket(' + data[index].id + ',' + data[index].postId + ');" class="btn btn-default btn-xs inline"' +
                            ' style="margin-top: -10px; "><span class="fa fa-plus"' +
                            ' style="color:gray "  title="Reply to this comment"   data-placement="bottom" ' +
                            'data-toggle="tooltip" ></span></button>'
                    }
                }
                $('#comment-box').append(
                    '<div  class="cmt" >'
                    + '<div class="col-lg-10 cmtContent">' +
                    '<img onload="http://localhost:9000/img/user_img.jpg" src="http://graph.facebook.com/' + data[index].createdBy + '/picture" alt="user image">'
                    + '<p class="message">'
                    + '<a>'
                    + data[index].createdByName
                    + '<small class="text-muted" style="margin-left: 10px">'
                    + jQuery.format.prettyDate(new Date(data[index].createdAt))
                    + '</small>'
                    + ' </a>'
                    + '<p style="margin-left: 65px;margin-top: -10px " onclick="getticket(' + data[index].id + ',' + postId + ')">' + data[index].content + '</p>'
                    + '</p>'
                    //Day la nut reply
//                                +'<button onclick="replyToComment('+cmtId+')" class="btn btn-default btn-xs inline" style="margin-left: 65px;margin-top: -10px; "><span class="glyphicon glyphicon-send" style="color:gray "  title="Reply to this comment"   data-placement="bottom" data-toggle="tooltip" ></span></button>'
                    + groupbutton
                    + '</div>'
                    + '<div class="col-lg-2" style="margin-top: 30px">' + '<small class="' + senIcon + '" style="font-size: 20px;"></small>'
                    + '</div>'
                    + '</div>')
            });

            $("#happy-count").html(happycount);
            $("#sad-count").html(sadcount);
        }
    });

}


//Get post by id
function getPostById(postId) {
    $.ajax({
        url: '/postById',
        type: "GET",
        data: {postId: postId},
        dataType: "json",
        success: function (data) {
            var date = $.format.date(data.createdAt, "ddd, dd-MM-yyyy");
            var time = $.format.date(data.createdAt, "HH:mm");
            $('#post-content').html(data.content);
            $('#post-author').html(data.createdByName);
            $('#post-like-count').html('<span class="glyphicon glyphicon-thumbs-up"></span>&nbsp;' + data.likesCount + '&nbsp;Likes');
            $('#post-comment-count').html('<span class="glyphicon glyphicon-comment"></span>&nbsp;' + data.commentsCount + '&nbsp;Comments');
            $('#post-share-count').html('<span class="glyphicon glyphicon-share-alt"></span>&nbsp;' + data.sharesCount + '&nbsp;Shares');
            $('#post-image').attr('src', 'http://graph.facebook.com/' + data.createdBy + '/picture');
            $('#post-time').html(date + ' at ' + time);
            //Set onClick command for comment button
            $('#btnSendComment').unbind().click(function () {
                var objId = (pageID + "_" + postId);
                if ($('#txtComment').val().length > 0) {
                    sendComment(objId, $('#txtComment').val());
                }
                else {
                    alert("Enter some thing to comment..")
                }
            });
        }
    })
}
//Send comment button
function sendComment(objId, message, token) {
    $('#send-progress').attr('class', 'fa fa-spin fa-spinner');
    $.ajax({
        url: '/commentOnObj',
        type: "POST",
        data: {objId: objId, message: message, token: token},
        dataType: "json",
        success: function (data) {
            $('#send-progress').attr('class', 'fa fa-check');
            $('#txtComment').val('');
            $('#txtReply').val('');
            $('#replyModal').modal('hide');
        }
    });
}

//create ticket
function createticket(comtID, postID) {

    $.ajax({
        url: "/getallpriorityofbrand",
        type: "GET",
        success: function (data) {
            $("#priority").html("");
            for (var i = 0; i < data.length; i++) {
                $("#priority").append(
                    '<option value="' + data[i].id + '">' + data[i].name + '</option>'
                )
            }
        },
        error: function () {
            alert("fail to load priority for update")
        }
    })

    $.ajax({
        url: "getalluser",
        type: "GET",
        success: function (data) {
            $("#assignee").html("");
            for (var i = 0; i < data.length; i++) {
                $("#assignee").append(
                    '<option value="' + data[i].userid + '">' + data[i].firstname + ' ' + data[i].lastname + '</option>'
                );
            }
        },
        error: function () {
            alert("Fail to load list user");
        }
    })

    $('#createModal').modal('toggle');
    $('#btnCreate').unbind().click(function () {
        var priority = $('#priority').val();
        var assignee = $('#assignee').val();
        $.ajax({
            url: "/createticket",
            data: {"commentid": comtID, "assignee": assignee, "priority": priority},
            type: "POST",
            success: function () {
                $('#createModal').modal('toggle');
                alert("create ticket successful");
                getCommentById(postID);
            },
            error: function () {
                alert("fail to create ticket");
            }
        })
    })
}

//assign ticket
function assign(comID) {
    //Load list user de assign
    $.ajax({
        url: "getalluser",
        type: "GET",
        success: function (data) {
            $("#assign").html("");
            for (var i = 0; i < data.length; i++)
                $("#assign").append(
                    '<option value="' + data[i].userid + '">' + data[i].firstname + ' ' + data[i].lastname + '</option>'
                );
        },
        error: function () {
            alert("Fail to load list user");
        }
    })

    //assign ticket
    $('#assignModal').modal('toggle');
    $('#btnAssign').unbind().click(function () {
        var assignee = $('#assign').val();
        $.ajax({
            url: "/assignticket",
            data: {"commentid": comID, "assignee": assignee},
            type: "POST",
            success: function (data) {
                alert("assign ticket successful" + data.commentid);
                $('#assignModal').modal('toggle');
            },
            error: function () {
                alert("fail to assign ticket");
            }
        })
    })
}

//change status
function status(comID, postID) {
    $('#statusModal').modal('toggle');
    $('#btnStatus').unbind().click(function () {
        var status = $('#status').val();
        $.ajax({
            url: "/changeticketstatus",
            data: {"commentid": comID, "status": status},
            type: "POST",
            success: function (data) {
                $('#statusModal').modal('toggle');
                alert("change status successfull:");
            },
            error: function () {
                alert("fail to change ticket status");
            }
        })
    })
}

//forward ticket
function forwardticket(comID) {
    $("#forwardModal").modal('toggle');
    $.ajax({
        url: "getalluser",
        type: "GET",
        success: function (data) {
            $("#forward").html("");
            for (var i = 0; i < data.length; i++)
                $("#forward").append(
                    '<option value="' + data[i].userid + '">' + data[i].firstname + ' ' + data[i].lastname + '</option>'
                );
        },
        error: function () {
            alert("Fail to load list user");
        }
    })
    $('#btnForward').unbind().click(function () {
        var forwardnote = $('#forwardnote').val();
        var forwarduser = $('#forward').val();
        $.ajax({
            url: "forwardticket",
            type: "POST",
            data: {"commentid": comID, "forwarduser": forwarduser, "forwardnote": forwardnote},
            success: function (data) {
                alert("Forward ticket: " + data.ticketid + " to user: " + data.assignee)
            },
            error: function () {
                alert("Fail to forward ticket")
            }
        })
    })
}

//create ticket for the login staff
function createTicketForTheStaff(comID, postID) {
    $('#staffcreateticketModal').modal('toggle');

    $.ajax({
        url: "/getallpriorityofbrand",
        type: "GET",
        success: function (data) {
            $("#staffpriority").html("");
            for (var i = 0; i < data.length; i++) {
                $("#staffpriority").append(
                    '<option value="' + data[i].id + '">' + data[i].name + '</option>'
                )
            }
        },
        error: function () {
            alert("fail to load priority for update")
        }
    })

    $('#btnStaffCreate').unbind().click(function () {
        var priority = $('#staffpriority').val();
        $.ajax({
            url: "/createticketforstaff",
            data: {"commentid": comID, "priority": priority},
            type: "POST",
            success: function () {
                $('#staffcreateticketModal').modal('toggle');
                alert("create ticket successful");
                getCommentById(postID);
            },
            error: function () {
                alert("fail to create ticket");
            }
        })
    })
}

//get ticket detail
function getticket(comID) {
    $.ajax({
        url: "/getticket",
        data: {"commentid": comID},
        type: "POST",
        success: function (data) {
            if (data === null) {
                $('#tab_1').html("");
            } else {
                var date = new Date(data.createdtime).getDate();
                var month = new Date(data.createdtime).getMonth() + 1;
                var year = new Date(data.createdtime).getFullYear();
                $('#tab_1').html(
                    '<p><b>Ticket ID: </b>' + data.id + '</p>'
                    + '<p><b>Comment ID: </b>' + data.commentid + '</p>'
                    + '<p><b>Createby: </b>' + data.createby + '</p>'
                    + '<p><b>Create At: </b>' + date + '/' + month + '/' + year + '</p>'
                    + '<p><b>Status: </b>' + data.statusid + '</p>'
                    + '<p><b>Active: </b>' + data.active + '</p>'
                    + '<p><b>Assignee: </b>' + data.assignee + '</p>'
                    + '<p><b>Priority: </b>' + data.priority + '</p>'
                );
            }

        },
        error: function () {
            $('#tab_1').html("");
        }
    })

    $.ajax({
        url: "/gettickethistory",
        data: {"commentid": comID},
        type: "POST",
        success: function (data) {
            $('#tab_2').html("");
            for (var i = 0; i < data.length; i++) {
                var date = new Date(data[i].createdat).getDate();
                var month = new Date(data[i].createdat).getMonth() + 1;
                var year = new Date(data[i].createdat).getFullYear();
                var status = data[i].statusid;
                switch (status) {
                    case 1:
                        status = "change " + data[i].ticketid + " to unassign";
                        break;
                    case 2:
                        status = "assign " + data[i].ticketid + " to " + data[i].assignee;
                        break;
                    case 3:
                        status = "change " + data[i].ticketid + " to open";
                        break;
                    case 4:
                        status = "change " + data[i].ticketid + " to inprogess";
                        break;
                    case 5:
                        status = "change " + data[i].ticketid + " to solving";
                        break;
                    case 6:
                        status = "change " + data[i].ticketid + " to solved";
                        break;
                    case 7:
                        status = "change " + data[i].ticketid + " to close";
                        break;

                }
                $('#tab_2').append(
                    '<p><b>' + date + '/' + month + '/' + year + ' ,' + data[i].userid + ' ' + status + ' </b></p>'
                );
            }

        },
        error: function () {
            $('#tab_2').html("");
        }
    })

    $.ajax({
        url: "/commentbypost",
        data: {"postId": comID},
        type: "POST",
        success: function (data) {
            $('#tab_3').html("");
            for (var i = 0; i < data.length; i++) {
                var date = new Date(data[i].createdAt).getDate();
                var month = new Date(data[i].createdAt).getMonth();
                var year = new Date(data[i].createdAt).getFullYear();
                $('#tab_3').append(
                    '<p>' + date + '/' + month + '/' + year + ', ' + data[i].createdByName + ' say: ' + data[i].content + '</p>'
                );
            }
        },
        error: function () {
            $('#tab_3').html("");
        }
    })

}

//showtag comment modal
function showtagcomment(cmtid) {
    //Lay tat ca cac tag cua brand cho user dung
    $("#tagcommentModal").modal('toggle');
    loadcommenttag(cmtid);
    $('#btnAddTag').unbind().click(function () {
        var attributename = $('#attributename').val();
        $.ajax({
            url: "/createattribute",
            type: "POST",
            data: {"attributename": attributename},
            success: function (data) {
                alert("create attribute " + data.name + " successful");
                loadcommenttag(cmtid);
                $('#attributename').val("");
            },
            error: function () {
                alert("fail to create attribute")
            }
        })
    })
}

//load tag of comment
function loadcommenttag(cmtid) {

    $.ajax({
        url: "/getallbrandtags",
        type: "GET",
        data: {"commentid": cmtid},
        success: function (data) {
            $('#tagss').html("");
            for (var i = 0; i < data.length; i++) {
                var button;
                if (data[i].commentatt) {
                    button = '<button class="btn btn-success btn-xs" style="width: 25px" id="tagbtn" onclick="untagcomment(' + cmtid + ',' + data[i].id + ')"><i class="fa fa-check"></i></button>';
                } else {
                    button = '<button class="btn btn-default btn-xs" style="width: 25px" id="tagbtn" onclick="tagcomment(' + cmtid + ',' + data[i].id + ')"><i class="fa fa-check"></i></button>';
                }
                $('#tagss').append(
                    '<div class="btn-group" role="group" style="margin-left: 10px">'
                    + '<button class="btn btn-default btn-xs" >' + data[i].name + '</button>'
                    + button
                    + '</div>'
                );
            }
        },
        error: function () {
            alert("Fail to load tags");
        }
    })
}

//tag comment to attribute
function tagcomment(cmtid, attid) {
    $.ajax({
        url: "/tagcomment",
        type: "GET",
        data: {"commentid": cmtid, "tagid": attid},
        success: function (data) {
            alert("Tag comment: " + data.commentid + " to attribuet:" + data.attributeid)
            loadcommenttag(cmtid);
        },
        error: function () {
            alert("Tag comment fail")
        }
    })
}

//untag comment from attribute
function untagcomment(cmtid, attid) {
    $.ajax({
        url: "/untagcomment",
        type: "POST",
        data: {"commentid": cmtid, "tagid": attid},
        success: function () {
            alert("untag comment: " + cmtid + " to attribuet:" + attid)
            loadcommenttag(cmtid);
        },
        error: function () {
            alert("Tag comment fail")
        }
    })
}


//get all page for filter

function getAllCrawlPage() {
    $.ajax({
        url: "/page/allpage",
        type: "GET",
        dataType: "json",
        success: function (data) {
            $('#page-list').html('');
            $.each(data, function (index) {
                listPageFilter.push(data[index].pageid);
                $('#page-list').append('<div class="page-selected" onclick="select(this,' + data[index].pageid + ')" style=" cursor: pointer; padding: 10px 0px 10px 10px; margin-top: 0; margin-bottom: 10px; height: auto">' +
                    '<img height="50px" width="50px" src="' + graphImage + data[index].pageid + '/picture">' +
                    '<p style="margin-left: 65px; margin-top: -45px; font-weight: bold; color: #72afd2">' + data[index].name + '</p>' +
                    '<p style="margin-left: 65px; margin-top: -10px;">' + data[index].category + '</p></div>');
            });

            getAllPosts(listPageFilter);
            setInterval(function () {
                getAllPosts(listPageFilter);
            }, 5000);

        },
        error: function () {
            alert("Tag comment fail")
        }
    })
}

function select(e, id) {

    var newid = id.toString();
    if ($(e).attr("class") == "page-unselected") {
        listPageFilter.push(newid);
        $(e).attr("class", "page-selected");
        getAllPosts(listPageFilter);
    }
    else {
        var index = listPageFilter.indexOf(newid);
        listPageFilter.splice(index, 1);
        $(e).attr("class", "page-unselected");
        getAllPosts(listPageFilter);

    }
}

function commentPagination(pagecount, postid) {
    $('#page-nav').html('');
    for (i=0;i<pagecount;i++)
    {
        var count =i +1;
        $('#page-nav').append('<li onclick="getCommentById(' + postid + ','+count+')"><a href="#">'+count+'</a></li>');
    }
}