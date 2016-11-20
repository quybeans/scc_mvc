/**
 * Created by QuyBean on 11/2/2016.
 */
var firstimerun = true;
//Ask me if you are confused QUYDV
var happyicon = 'fa fa-smile-o pull-right happy';
var sadicon = 'fa fa-frown-o pull-right sad';
var questionicon = 'fa fa-question-circle-o pull-right question'
var listPageFilter = new Array();
//Hard image link for account user.
var graphImage = "https://graph.facebook.com/";
var postListCurPage = 1;
var commentListCurPage = 1;
var currentPost;
var currentCmt;
//Sort setting 1: by question, 2: by neg, 3: by time
var sortCommentBy = 1;
startup();

function startup() {
//Get post every 5s
    getAllCrawlPage();
    $('#list-fb-account').empty();
    getAllPageAccount();
    getallpriorityofbrand();
    getalluser();
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

//Add comment to item
function addCommentToTicket(ticketId, cmtId) {
    $.ajax({
        url: '/comment/addToTicket',
        type: "GET",
        data: {ticketId: ticketId, cmtId: cmtId},
        dataType: "json",
        success: function (data) {
            alert('Success add to ticket');
        }
    })
}


//Show all ticket existed

function showTicket() {
    $('#ticket-list').empty();
    $.ajax({
        url: '/getallticket',
        type: "GET",
        dataType: "json",
        success: function (data) {
            $.each(data, function (index) {
                var statusColor=getstatuscolor(data[index].statusid);

                $('#ticket-list').append(
                    '<div class="ticket">'
                    + '<div class="title" style="background-color:  ' +statusColor+'">' + data[index].name + '</div>'
                    + '<div>Status:&nbsp;'
                    + '<span class="fa fa-circle" style="color:' +statusColor+'"></span>&nbsp;'
                    + data[index].currentstatus
                    + '</div>'
                    + '<div>Created by:&nbsp;<span style="color: black; font-weight: bold">'
                    + data[index].createbyuser
                    + '</span></div>'
                    + '<div>Current assignee:&nbsp;<span style="color: black; font-weight: bold">' + data[index].assigneeuser + '</span>'
                    + '</div>'
                    + '<div></div>'
                    + '</div>'
                )
            })
        }
    });
    $('#ticket-modal').modal('toggle');

}

//Set onclick account
function setOnclickReplyAccount(imgsrc, name, token) {
    $('#reply-img').attr("src", "http://graph.facebook.com/" + imgsrc + "/picture");
    $('#reply-name').html(name);
    $('#reply-token').val(token);
}

//get All Facebook account belong to user
function getAllFBAccount()  {
    $.ajax({
        url: '/allFbAccount',
        type: "GET",
        dataType: "json",
        success: function (data) {
            $('#list-fb-account').append('<li class="dropdown-header" style="text-align: center">Facebook account</li>');
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
            });
            setOnclickReplyAccount(data[0].pageid, data[0].name, data[0].accesstoken);
        }

    });
    getAllFBAccount();
}

//Get all posts
function findPostByContent(pagelist) {

    var content = $('#txtSearchPost').val();

    $('#post-box').html('</br><div style="margin-right: 10px; text-align: center;font-size: 30px; width: 100%; height: 100%;"><span style=" color: cornflowerblue; " class="fa fa-circle-o-notch fa-spin"></span>Loading...</div>');
    if (pagelist.length != 0) {
        $.ajax({
            url: '/post/findbycontent',
            type: "POST",
            data: {content: content, pagelist: pagelist.toString()},
            dataType: "json",
            success: function (data) {
                $('#post-box').empty();
                $.each(data, function (index) {
                    $('#post-box').append(
                        '<div class="item" style="position:relative;" onclick="getCommentById(' + "'" + data[index].id + "'" + ')">'
                        + '<img onload="http://localhost:8080/img/user_img.jpg" src="http://graph.facebook.com/' + data[index].createdBy + '/picture" alt="user image">'
                        + '<p class="message">'
                        + '<small class="text-muted pull-right">'
                        + jQuery.format.prettyDate(new Date(data[index].createdAt))
                        + '</small>'
                        + '<a class="name"  style="text-overflow: ellipsis; max-width: 70%; overflow: hidden; white-space: nowrap">'
                        + data[index].createdByName
                        + ' </a>'
                        + '</p>'
                        + '<div style="margin-left: 65px;margin-top: -10px">'
                        + ' <span style="vertical-align:middle;">' + data[index].posCount + '</span> <span class="fa fa-smile-o happy" style="font-size:20px; vertical-align:middle; margin-right:10px; "></span>'
                        + ' <span style="vertical-align:middle;">' + data[index].negCount + '</span> <span class="fa fa-frown-o sad" style="font-size:20px; vertical-align:middle;"></span>'
                        + '</div>'
                        + '<p style="margin-top: 15px;height: 35px;overflow: hidden;">' + data[index].content + '</p>'
                        + '<div style="color: gray; bottom: 0px; position: absolute; width: 90%;text-align: center;">'
                        + '<div class="inline"  style="margin-right: 10px"><span class="glyphicon glyphicon-thumbs-up"></span>&nbsp;' + getRepString(data[index].likesCount) + '</div>'
                        + '<div class="inline"style="margin-right: 10px"><span class="glyphicon glyphicon-comment"></span>&nbsp;' + getRepString(data[index].commentsCount) + ' </div>'
                        + '<div class="inline" style="margin-right: 10px"> <span class="glyphicon glyphicon-share-alt"></span>&nbsp;' + getRepString(data[index].sharesCount) + '</div>'
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

function getAllPosts(pagelist, pageno) {
    if (pageno == 1)$('#post-box').html('</br><div style="margin-right: 10px; text-align: center;font-size: 30px; width: 100%; height: 100%;"><span style=" color: cornflowerblue; " class="fa fa-circle-o-notch fa-spin"></span>Loading...</div>');
    if (pagelist.length != 0) {
        $.ajax({
            // url: '/allPostsByBrand',
            url: '/post/allpostbypage',
            type: "POST",
            data: {pagelist: pagelist.toString(), pageno: pageno},
            dataType: "json",
            success: function (data) {
                if (pageno == 1) {
                    $('#post-box').empty();
                }
                $.each(data, function (index) {
                    $('#post-box').append(
                        '<div class="item" id="' + data[index].id + '" style="position:relative;" onclick="getCommentById(' + "'" + data[index].id + "'" + ')">'
                        + '<img onload="http://localhost:8080/img/user_img.jpg" src="http://graph.facebook.com/' + data[index].createdBy + '/picture" alt="user image">'
                        + '<p class="message">'
                        + '<small class="text-muted pull-right">'
                        + jQuery.format.prettyDate(new Date(data[index].createdAt))
                        + '</small>'
                        + '<a class="name"  style="text-overflow: ellipsis; max-width: 70%; overflow: hidden; white-space: nowrap">'
                        + data[index].createdByName
                        + ' </a>'
                        + '</p>'
                        + '<div style="margin-left: 65px;margin-top: -10px">'
                        + ' <span style="vertical-align:middle;">' + data[index].posCount + '</span> <span class="fa fa-question-circle-o question" style="font-size:20px; vertical-align:middle; margin-right:10px; "></span>'
                        + ' <span style="vertical-align:middle;">' + data[index].negCount + '</span> <span class="fa fa-frown-o sad" style="font-size:20px; vertical-align:middle;"></span>'
                        + '</div>'
                        + '<p style="margin-top: 15px;height: 35px;overflow: hidden;">' + data[index].content + '</p>'
                        + '<div style="color: gray; bottom: 0px; position: absolute; width: 90%;text-align: center;">'
                        + '<div class="inline"  style="margin-right: 10px"><span class="glyphicon glyphicon-thumbs-up"></span>&nbsp;' + getRepString(data[index].likesCount) + '</div>'
                        + '<div class="inline"style="margin-right: 10px"><span class="glyphicon glyphicon-comment"></span>&nbsp;' + getRepString(data[index].commentsCount) + ' </div>'
                        + '<div class="inline" style="margin-right: 10px"> <span class="glyphicon glyphicon-share-alt"></span>&nbsp;' + getRepString(data[index].sharesCount) + '</div>'
                        + '</div>'
                        + '</div>'
                    )

                });
                if (firstimerun == true) {
                    $('#' + data[0].id).click();
                    firstimerun = false
                }
                ;
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
// function getCommentById(postId,page) {
//     var happycount = 0;
//     var sadcount = 0;
//     $('#comment-box').empty();
//     $('#comment-box').html('Loading....');
//     getPostById(postId);
//
//     $.ajax({
//         url: 'post/sentimentcount',
//         type: "GET",
//         data: {postid: postId},
//         dataType: "json",
//         success: function (result) {happycount=result[0];sadcount=result[1]}});
//
//     $.ajax({
//         url: 'comment/getallcomment',
//         type: "GET",
//         data: {postid: postId, page:page},
//         dataType: "json",
//         success: function (result) {
//
//             // Pagination
//             var data = result.content;
//             commentPagination(parsseInt(result.totalPages),postId);
//
//
//             $('#comment-box').empty();
//             $.each(data, function (index) {
//                  var senIcon = sadicon;
//                 if (data[index].sentimentScore == 1)
//                     senIcon = happyicon;
//
//
//
//                 var cmtId = "'" + postId + "_" + data[index].id + "'";
//                 // groupbutton chua 2 nut là reply, danh tag
//                 var groupbutton = '<button  onclick="replyToComment(' + cmtId + ');getReplyByCommentId(' + data[index].id + ');" class="btn btn-default btn-xs inline"' +
//                     ' style="margin-left: 65px;margin-top: -10px; "><span class="glyphicon glyphicon-send"' +
//                     ' style="color:gray "  title="Reply to this comment"   data-placement="bottom" ' +
//                     'data-toggle="tooltip" ></span></button>'
//
//                     + '<button  onclick="showtagcomment(' + data[index].id + ')" class="btn btn-default btn-xs inline"' +
//                     ' style="margin-top: -10px;" ><span class="fa fa-tag"' +
//                     ' style="color:gray "  title="Tag this comment to attribute"   data-placement="bottom" ' +
//                     'data-toggle="popover" ></span></button>';
//
//                 //khi comment la mot ticket
//                 if (data[index].ticket) {
//
//                     //Them nut change stt
//                     groupbutton += '<button class="btn btn-default btn-xs inline" onclick="status(' + data[index].id + ')"' +
//                         ' style="margin-top: -10px; "><span style="color:gray " ' +
//                         'title="Change ticket status" data-toggle="tooltip"  data-placement="bottom" ' +
//                         ' class="fa fa-navicon"></span></button>'
//
//                     //khi nguoi dang nhap duoi quyen staff
//                     if (data[index].staff) {
//                         //them nut forward ticket
//                         groupbutton += '<button class="btn btn-default btn-xs inline" onclick="forwardticket(' + data[index].id + ')"' +
//                             ' style="margin-top: -10px; "><span style="color:gray " ' +
//                             'title="Forward this ticket to another staff" data-toggle="tooltip"  data-placement="bottom" ' +
//                             ' class="fa fa-group"></span></button>'
//                         if (!data[index].ticketofstaff) {
//                             groupbutton = "";
//                         }
//                         //khi nguoi dang nhap duoi quyen khac
//                     } else {
//                         //Them nut assign
//                         groupbutton += '<button class="btn btn-default btn-xs inline" onclick="assign(' + data[index].id + ')"' +
//                             ' style="margin-top: -10px; "><span style="color:gray " ' +
//                             'title="Assign this ticket to staff" data-toggle="tooltip"  data-placement="bottom" ' +
//                             ' class="fa fa-ticket"></span></button>'
//                     }
//
//                     //khi comment chua phai la ticket
//                 } else {
//                     //khi nguoi dang nhap duoi quyen staff
//                     if (data[index].staff) {
//                         //them nut assign cho ban than no
//                         groupbutton += '<button class="btn btn-default btn-xs inline" onclick="createTicketForTheStaff(' + data[index].id + ',' + data[index].postId + ')" ' +
//                             ' style="margin-top: -10px; "><span style="color:gray " ' +
//                             'title="Create new ticket for yourself" data-toggle="tooltip"  data-placement="bottom" ' +
//                             ' class="fa fa-plus-square"></span></button>'
//
//                         //khi nguoi dang nhap duoi quyen khac
//                     } else {
//                         //them nut create ticket
//                         groupbutton += '<button  onclick="createticket(' + data[index].id + ',' + data[index].postId + ');" class="btn btn-default btn-xs inline"' +
//                             ' style="margin-top: -10px; "><span class="fa fa-plus"' +
//                             ' style="color:gray "  title="Reply to this comment"   data-placement="bottom" ' +
//                             'data-toggle="tooltip" ></span></button>'
//                     }
//                 }
//                 $('#comment-box').append(
//                     '<div  class="cmt" >'
//                     + '<div class="col-lg-10 cmtContent">' +
//                     '<img onload="http://localhost:9000/img/user_img.jpg" src="http://graph.facebook.com/' + data[index].createdBy + '/picture" alt="user image">'
//                     + '<p class="message">'
//                     + '<a>'
//                     + data[index].createdByName
//                     + '<small class="text-muted" style="margin-left: 10px">'
//                     + jQuery.format.prettyDate(new Date(data[index].createdAt))
//                     + '</small>'
//                     + ' </a>'
//                     + '<p style="margin-left: 65px;margin-top: -10px " onclick="getticket(' + data[index].id + ',' + postId + ')">' + data[index].content + '</p>'
//                     + '</p>'
//                     //Day la nut reply
// //                                +'<button onclick="replyToComment('+cmtId+')" class="btn btn-default btn-xs inline" style="margin-left: 65px;margin-top: -10px; "><span class="glyphicon glyphicon-send" style="color:gray "  title="Reply to this comment"   data-placement="bottom" data-toggle="tooltip" ></span></button>'
//                     + groupbutton
//                     + '</div>'
//                     + '<div class="col-lg-2" style="margin-top: 30px">' + '<small class="' + senIcon + '" style="font-size: 20px;"></small>'
//                     + '</div>'
//                     + '</div>')
//             });
//
//             $("#happy-count").html(happycount);
//             $("#sad-count").html(sadcount);
//         }
//     });
//
// }

//ON POST CLICK
function getCommentById(postid) {
    getPostById(postid);
    currentPost = postid;
    commentListCurPage = 1;
    $('#post-box').find("*").removeClass("active");
    $('#' + postid).addClass('active');
    $('#comment-page-current').val(commentListCurPage);
    getCommentByPostIdwPage(postid, 1,"");
}

//Get comment by id
function getCommentByPostIdwPage(postId, page, searchContent) {

    var url;
    if (searchContent.length>0) url='/comment/bypostid/search';
    else if (sortCommentBy == 2)  url = '/comment/bypostid/negSort';
    else if (sortCommentBy == 3) url = '/comment/bypostid/timeSort';

    else url = 'comment/bypostid';

    $('#comment-box').empty();
    $('#comment-box').html('<div style=" padding-left: 10px"><span style="color: cornflowerblue;" class="fa fa-circle-o-notch fa-spin"></span> &nbsp;Loading comments ...</div>');
    $.ajax({
        url: url,
        type: "GET",
        data: {postid: postId, page: page, content: searchContent},
        dataType: "json",
        success: function (data) {
            $('#comment-box').empty();
            $.each(data, function (index) {
                var senIcon = sadicon;
                if (data[index].sentimentScore == 1)
                    senIcon = happyicon;
                if (data[index].sentimentScore == 3)
                    senIcon = questionicon;
                var cmtId = "'" + postId + "_" + data[index].id + "'";

                var btnTicket = '<button onclick="showTicket();currentCmt='+data[index].id+'" class="btn btn-default btn-xs inline" style="margin-left: 10px;margin-top: -10px;"><span class="fa fa-ticket" style="margin-right:10px"></span>Add to ticket</button>';

                var isTicket = false;
                $.ajax({
                    url: "/comment/checkTicket",
                    type: "GET",
                    data: {cmtID: data[index].id},
                    dataType: "json",
                    async: false,
                    success: function (ticket) {
                        if (ticket != null) isTicket = true;
                        btnTicket = '<a href=followticket?ticketid='+ticket.id+' class="btn btn-default btn-xs inline" style="margin-left: 10px;margin-top: -10px; border: 1px solid lightgreen; background-color: transparent;color: lightgreen">' + ticket.name + '</a>';
                    }
                });


                $('#comment-box').append(
                    '<div class="cmt" >'
                    + '<div class="col-lg-11 cmtContent">' +
                    '<img onload="http://localhost:9000/img/user_img.jpg" src="http://graph.facebook.com/' + data[index].createdBy + '/picture" alt="user image">'
                    + '<p class="message" style="margin-top: -53px">'
                    + '<a href="https:/fb.com/' + data[index].createdBy + '" target="_blank">'
                    + data[index].createdByName
                    + '<small class="text-muted" style="margin-left: 10px">'
                    + jQuery.format.prettyDate(new Date(data[index].createdAt))
                    + '</small>'
                    + ' </a>'
                    + '<p style="margin-left: 65px;margin-top: -10px " onclick="getticket(' + data[index].id + ',' + postId + ')">' + data[index].content + '</p>'
                    + '</p>'
                    // Day la nut reply
                    + '<button  onclick="replyToComment(' + cmtId + ');getReplyByCommentId(' + "'" + data[index].id + "'" + ');" class="btn btn-default btn-xs inline"' +
                    ' style="margin-left: 65px;margin-top: -10px; "><span class="glyphicon glyphicon-comment"' +
                    ' style="color:gray;margin-right: 10px "  title="Reply to this comment"   data-placement="bottom" ' +
                    'data-toggle="tooltip" ></span>' + countReply(data[index].id) + ' replies</button>'
                    + btnTicket
                    + '</div>'
                    + '<div class="col-lg-1" style="margin-top: 30px">' + '<small class="' + senIcon + '" style="font-size: 20px;"></small>'
                    + '</div>'
                    + '</div>')
            });
        }
    });
}
// //Search comment by content
// function searchByContent(postId, content, page) {
//     $('#comment-box').html('<div style=" padding-left: 10px"><span style="color: cornflowerblue;" class="fa fa-circle-o-notch fa-spin"></span> &nbsp;Loading comments ...</div>');
//     $.ajax({
//         url: 'comment/bypostid/search',
//         type: "GET",
//         data: {postid: postId, page: page, content: content},
//         dataType: "json",
//         success: function (data) {
//             $('#comment-box').empty();
//             $.each(data, function (index) {
//                 var senIcon = sadicon;
//                 if (data[index].sentimentScore == 1)
//                     senIcon = happyicon;
//                 if (data[index].sentimentScore == 3)
//                     senIcon = questionicon;
//                 var cmtId = "'" + postId + "_" + data[index].id + "'";
//
//                 $('#comment-box').append(
//                     '<div class="cmt" >'
//                     + '<div class="col-lg-11 cmtContent">' +
//                     '<img onload="http://localhost:9000/img/user_img.jpg" src="http://graph.facebook.com/' + data[index].createdBy + '/picture" alt="user image">'
//                     + '<p class="message" style="margin-top: -53px">'
//                     + '<a href="https:/fb.com/' + data[index].createdBy + '" target="_blank">'
//                     + data[index].createdByName
//                     + '<small class="text-muted" style="margin-left: 10px">'
//                     + jQuery.format.prettyDate(new Date(data[index].createdAt))
//                     + '</small>'
//                     + ' </a>'
//                     + '<p style="margin-left: 65px;margin-top: -10px " onclick="getticket(' + data[index].id + ',' + postId + ')">' + data[index].content + '</p>'
//                     + '</p>'
//                     // Day la nut reply
//                     + '<button  onclick="replyToComment(' + cmtId + ');getReplyByCommentId(' + data[index].id + ');" class="btn btn-default btn-xs inline"' +
//                     ' style="margin-left: 65px;margin-top: -10px; "><span class="glyphicon glyphicon-send"' +
//                     ' style="color:gray "  title="Reply to this comment"   data-placement="bottom" ' +
//                     'data-toggle="tooltip" ></span></button>'
//                     + '</div>'
//                     + '<div class="col-lg-1" style="margin-top: 30px">' + '<small class="' + senIcon + '" style="font-size: 20px;"></small>'
//                     + '</div>'
//                     + '</div>')
//             });
//         }
//     })
// }

//Search comment by content
function searchByContent(postId, content, page) {
    getCommentByPostIdwPage(postId,page,content);
}

//On post click
function getPostById(postId) {
    var happycount = 0;
    var sadcount = 0;
    var questioncount = 0;
    countComment(postId);
    //Count sentiment in post
    $.ajax({
        url: 'post/sentimentcount',
        type: "GET",
        data: {postid: postId},
        dataType: "json",
        success: function (result) {
            happycount = result[0];
            sadcount = result[1];
            questioncount = result[2];
            $("#happy-count").html(happycount);
            $("#sad-count").html(sadcount);
            $("#question-count").html(questioncount);
        }
    });


    //Get post detail
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
            $('#post-like-count').html('<span class="glyphicon glyphicon-thumbs-up"></span>&nbsp;' + getRepString(data.likesCount) + '&nbsp;Likes');
            $('#post-comment-count').html('<span class="glyphicon glyphicon-comment"></span>&nbsp;' + getRepString(data.commentsCount) + '&nbsp;Comments');
            $('#post-share-count').html('<span class="glyphicon glyphicon-share-alt"></span>&nbsp;' + getRepString(data.sharesCount) + '&nbsp;Shares');
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
//Get all ticket
function getTicket() {
    $.ajax({
        url: "/getallticket",
        type: "GET",
        success: function (data) {

        },
        error: function () {
            alert("Get all ticket failed")
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


//FILTER POST BY PAGES FUNCTIONS
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
            postListCurPage = 1;
            getAllPosts(listPageFilter, postListCurPage);
            // setInterval(function () {
            //     getAllPosts(listPageFilter);
            // }, 5000);

        },
        error: function () {
            alert(listPageFilter);
            alert("Getting page failed, list page filter: " + listPageFilter)
        }
    })
}

//Page list filter select
function select(e, id) {
    postListCurPage = 1;
    var newid = id.toString();
    if ($(e).attr("class") == "page-unselected") {
        listPageFilter.push(newid);
        $(e).attr("class", "page-selected");
        getAllPosts(listPageFilter, postListCurPage);
    }
    else {
        var index = listPageFilter.indexOf(newid);
        listPageFilter.splice(index, 1);
        $(e).attr("class", "page-unselected");
        getAllPosts(listPageFilter, postListCurPage);

    }
}

//Lazy load for post list
jQuery(function ($) {
    $('#post-list').on('scroll', function () {
        if ($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight) {
            postListCurPage = postListCurPage + 1;
            getAllPosts(listPageFilter, postListCurPage);
        }
    })
});


//PAGINATION COMMENT LIST FUNCTIONS
function countComment(postid) {
    //Count comment in post
    $.ajax({
        url: 'comment/bypostid/count',
        type: "GET",
        data: {postid: postid},
        dataType: "json",
        success: function (result) {
            $('#comment-page-count').html(result);
            commentListCurPage = 1;
        }
    });
}

function nextCommentPage() {
    commentListCurPage = commentListCurPage + 1;
    $('#comment-page-current').val(commentListCurPage);
    getCommentByPostIdwPage(currentPost, commentListCurPage,"");
}

function previousCommentPage() {
    if (commentListCurPage == 1) return;
    commentListCurPage = commentListCurPage - 1;
    $('#comment-page-current').val(commentListCurPage);
    getCommentByPostIdwPage(currentPost, commentListCurPage,"");
}

function checkNumberInputAndEnter(e) {
    commentListCurPage = parseInt($('#comment-page-current').val());
    if (e.keyCode == 13) {
        $(this).blur();
        getCommentByPostIdwPage(currentPost, commentListCurPage,"");
    }

}

function searchCommentEnter(e) {
    if (e.keyCode == 13) {
        $('#txtSearchComment').blur();
        searchByContent(currentPost, $('#txtSearchComment').val(), 1);
    }
}

// COMMENT SORT FUNCTIONS
function negSortClick() {
    sortCommentBy = 2;
    $('#more').click();
    getCommentById(currentPost);
}

function questSortClick() {
    sortCommentBy = 1;
    $('#more').click();
    getCommentById(currentPost);
}

function timeSortClick() {
    sortCommentBy = 3;
    $('#more').click();
    getCommentById(currentPost);
}

//UTILITIES

//Set which page is active
function activePage(a) {
    $('#page-nav>li.active').removeClass("active");
    $(a).addClass('active');
    $('#currentpage').html($(a).html());
}

//Number over thousand format
function getRepString(rep) {
    rep = rep + ''; // coerce to string
    if (rep < 1000) {
        return rep; // return the same number
    }
    // if (rep < 10000) { // place a comma between
    //     return rep.charAt(0) + '.' + rep.substring(1);
    // }
    // divide and format
    return (rep / 1000).toFixed(rep % 1000 != 0) + 'k';
}

//Count replies within comment
function countReply(commentId) {

    var rs = 0;
    $.ajax({
        url: 'comment/reply/count',
        type: "GET",
        data: {"commentId": commentId},
        dataType: "json",
        async: false,
        success: function (data) {
            rs = data;
        }
    });

    return rs;
}

function sortticketbytime() {
    sortticket("/sortbytime")
}

function sortticketbystatus() {
    sortticket("/sortbystatus")
}

function showallticket() {
    $('#ticket-list').empty();
    $.ajax({
        url: '/getallticket',
        type: "GET",
        dataType: "json",
        success: function (data) {
            $.each(data, function (index) {
                var statusColor=getstatuscolor(data[index].statusid);

                $('#ticket-list').append(
                    '<div class="ticket">'
                    + '<div class="title" style="background-color:  ' +statusColor+'">' + data[index].name + '</div>'
                    + '<div>Status:&nbsp;'
                    + '<span class="fa fa-circle" style="color:' +statusColor+'"></span>&nbsp;'
                    + data[index].currentstatus
                    + '</div>'
                    + '<div>Created by:&nbsp;<span style="color: black; font-weight: bold">'
                    + data[index].createbyuser
                    + '</span></div>'
                    + '<div>Current assignee:&nbsp;<span style="color: black; font-weight: bold">'+data[index].assigneeuser+'</span>'
                    + '</div>'
                    + '<div></div>'
                    + '</div>'
                )
            })
        }
    });

}

$(document).on('change', '#tktimecheckbox', function(){
    if (this.checked) {
        sortticketbytime()
    }else{
        showallticket()
    }

});

$(document).on('change', '#tksttcheckbox', function(){
    if (this.checked) {
        sortticketbystatus()
    }else{
        showallticket()
    }

});

function filterticket(){
   var status= $("#filterstatus").val();
    var priority= $("#filterpriority").val();
    var createdby= $("#filtercreatedby").val();
    var assignee=$("#filterassignee").val();
    $.ajax({
        url:"/filterticket",
        type:"POST",
        data:{"status":status,"priority":priority,"createdby":createdby,"assignee":assignee},
        success:function (data) {
            $('#ticket-list').empty();
            $.each(data, function (index) {
                var statusColor=getstatuscolor(data[index].statusid);


                $('#ticket-list').append(
                    '<div class="ticket">'
                    + '<div class="title" style="background-color:  ' +statusColor+'">' + data[index].name + '</div>'
                    + '<div>Status:&nbsp;'
                    + '<span class="fa fa-circle" style="color:' +statusColor+'"></span>&nbsp;'
                    + data[index].currentstatus
                    + '</div>'
                    + '<div>Created by:&nbsp;<span style="color: black; font-weight: bold">'
                    + data[index].createbyuser
                    + '</span></div>'
                    + '<div>Current assignee:&nbsp;<span style="color: black; font-weight: bold">'+data[index].assigneeuser+'</span>'
                    + '</div>'
                    + '<div></div>'
                    + '</div>'
                )
            })
        },
        error:function () {
            alert("filter fail")
        }

    })
}

function getallpriorityofbrand() {
    $.ajax({
        url: "/getallpriorityofbrand",
        type: "GET",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#filterpriority").append(
                    '<option value="' + data[i].id + '">' + data[i].name + '</option>'
                )
            }
        },
        error: function () {
            alert("fail to load priority for update")
        }
    })
}

function getalluser() {
    $.ajax({
        url: "getalluser",
        type: "GET",
        success: function (data) {
            for (var i = 0; i < data.length; i++){
                $("#filtercreatedby").append(
                    '<option value="' + data[i].userid + '">' + data[i].firstname + ' ' + data[i].lastname + '</option>'
                );
                $("#filterassignee").append(
                    '<option value="' + data[i].userid + '">' + data[i].firstname + ' ' + data[i].lastname + '</option>'
                );
            }

        },
        error: function () {
            alert("Fail to load list user");
        }
    })
}

function getstatuscolor(statusid) {
    switch (statusid){
        case 1: return'#ffff00'; break;
        case 2: return'#00a65a'; break;
        case 3: return'#500a6f'; break;
        case 4: return'gray'; break;
        case 5: return'#000000'; break;
    }
}

function sortticket(url) {
    $.ajax({
        url:url,
        type:"GET",
        success:function (data) {
            $('#ticket-list').empty();
            $.each(data, function (index) {
                var statusColor=getstatuscolor(data[index].statusid);

                $('#ticket-list').append(
                    '<div class="ticket">'
                    + '<div class="title" style="background-color:  ' +statusColor+'">' + data[index].name + '</div>'
                    + '<div>Status:&nbsp;'
                    + '<span class="fa fa-circle" style="color:' +statusColor+'"></span>&nbsp;'
                    + data[index].currentstatus
                    + '</div>'
                    + '<div>Created by:&nbsp;<span style="color: black; font-weight: bold">'
                    + data[index].createbyuser
                    + '</span></div>'
                    + '<div>Current assignee:&nbsp;<span style="color: black; font-weight: bold">'+data[index].assigneeuser+'</span>'
                    + '</div>'
                    + '<div></div>'
                    + '</div>'
                )


            })
        },
        error:function () {
            alert("Fail to")
        }
    })
}