/**
 * Created by user on 11/13/2016.
 */
var ticketid = $("#ticketid").val();
var currentstatus;
var duration;
var staff;
var happyicon = 'fa fa-smile-o pull-right happy';
var sadicon = 'fa fa-frown-o pull-right sad';
var questionicon = 'fa fa-question-circle-o pull-right question'
getcurrentuser()
loadticketitem(ticketid)
getAllPageAccount();
getticket(ticketid)
function getticketduetime(ticketid) {
    $.ajax({
        url:"/getticketduetime",
        type:"GET",
        data:{"ticketid":ticketid},
        success:function (data) {
            var duetime = moment(data.duetime)+ (data.duration * 60 * 1000);
            var current = moment();
            var diff = current.diff(duetime)*(-1);
            window.ticketdue = diff;
            window.duration=data.duration;
        },
        error:function () {
            alert("fail to get ticket duetime");
        }
    })
}
getticketduetime(ticketid)
var ticketdue;


setInterval(function () {
    if(currentstatus==2 || currentstatus==1){
        ticketdue =moment.duration(ticketdue-1000,'milliseconds');
        if(ticketdue>0){
            $("#dueday").html("Due in "+ticketdue.hours()+":"+ticketdue.minutes()+":"+ticketdue.seconds());
        }else{
            updateexpiredticket(ticketid,duration)
        }
    }else if(currentstatus==4){
        $("#dueday").html(
            '<button class="btn btn-default" onclick="reopenticket('+ticketid+')"> Reopen ticket</button>'
        )
    }else if(currentstatus==3){
        if(staff){
            $("#dueday").html("");
        }else{
            $("#dueday").html(
                '<div class="btn-group">'
                +'<button class="btn btn-success" onclick="approve('+ticketid+')" style="width: 80px"> Close</button>'
                +'<button class="btn btn-danger" onclick="reject('+ticketid+')" style="width: 80px"> Reassign</button>'
                +'</div>'
            )
        }

    }else{
        $("#dueday").html("");
    }


},1000);

function updateexpiredticket(ticketid,duration) {
    $.ajax({
        url:"/updateexpiredticket",
        type:"POST",
        data:{"ticketid":ticketid,"duration":duration},
        success:function (data) {

            alert(data)
            getticketduetime(ticketid);
            getticket(ticketid);
            loadticketitem(ticketid);
            $("#historycheckbox").prop("checked", false);

        },
        error:function () {
            $("#dueday").html("Expired");
        }
    })
}

function getticket(ticketid) {
    $.ajax({
        url:"/getticketdetail",
        type:"GET",
        data:{"ticketid":ticketid},
        success:function (data) {
            var statuscolor= 'color:'+getstatuscolor(data.statusid);
            var statusname;
            switch (data.statusid){
                case 1:  statusname="Assigned"; break;
                case 2:  statusname="In progress"; break;
                case 3:  statusname="Solved"; break;
                case 4:  statusname="Close"; break;
                case 5:  statusname="Expired"; break;
            }
            window.currentstatus=data.statusid;
            var createat = moment(data.createdtime).format("D/MM/YYYY, hh:mm:ss");
            $("#ticketstatus").html(
                '<i class="fa fa-circle" aria-hidden="true" style="'+statuscolor+'; margin-right: 10px; font-size: 15px"></i>'
                +'<span style="font-size: 16px;">'+statusname+'</span>'
            );
            $("#ticketname").html(data.name);
            $("#createdat").html('<i  class="fa fa-minus" ></i><b style="margin-left: 5px;">Created At: </b>'+createat);
            $("#ticketnotedetail").html('<i  class="fa fa-minus" ></i><b style="margin-left: 5px">Note: </b>'+data.note);
            $("#ticketprioritydetail").html('<i  class="fa fa-minus" ></i><b style="margin-left: 5px">Priority: </b>'+data.currentpriority);
            $("#getassigner").html('<h4><i  class="fa fa-chevron-right" onclick="getassignerinfo('+data.createdby+')"></i><b style="margin-left: 5px">Assigner:</b> '+data.createbyuser+'</h4>');
            $("#getassignee").html('<h4><i  class="fa fa-chevron-right" onclick="getassigneeinfo('+data.assignee+')"></i><b style="margin-left: 5px">Assignee:</b> '+data.assigneeuser+'</h4>');
            var groupbtn;
            if(staff){
                groupbtn = '<button class="btn btn-default" style="width: 70px" onclick="forwardticket('+data.id+')">Foward</button>'
                    +'<button class="btn btn-default" style="width: 70px" onclick="status('+data.id+')">Status</button>'
                    +'<button class=" btn btn-default" style="width: 70px" onclick="updateticket('+data.id+')">Priority</button>'
            }else{
                groupbtn = '<button class="btn btn-default" style="width: 70px" onclick="assign('+data.id+')">Assign</button>'
                    +'<button class="btn btn-default" style="width: 70px" onclick="status('+data.id+')">Status</button>'
                    +'<button class=" btn btn-default" style="width: 70px" onclick="updateticket('+data.id+')">Priority</button>'
            }

            $("#ticketbutton").html(
                groupbtn
            )

        },
        error: function () {

        }

    })
}

function loadticketitem(ticketid) {
    $.ajax({
        url:"/loadticketitem",
        type:"POST",
        data:{"ticketid":ticketid},
        success:function (data) {
            $('#ticketitem').html("");
            for(var index =0;index<data.length;index++){
                var timelineitem;
                if(data[index].comment!==null){
                    var senIcon = sadicon;
                    if (data[index].comment.sentimentScore == 1)
                        senIcon = happyicon;
                    if (data[index].comment.sentimentScore == 3)
                        senIcon = questionicon;
                    var objid = "'"+data[index].comment.postId+ "_" +data[index].comment.id+"'";
                    timelineitem =
                        '<li class="time-label">'
                        +'<span class="bg-gray-active" id="createtime">'
                        +moment(data[index].createdAt).format("D/MM/YYYY , hh:mm:ss")
                        +'</span>'
                        +'<span class="bg-orange" id="createtime">'
                        +'<i class="glyphicon glyphicon-comment"></i>'
                        +'</span>'
                        +'</li>'
                        +'<li>'
                        +'<div class="timeline-item" >'
                        +'<h3 class="timeline-header">'
                        +'<a>'+data[index].addedby+'</a>'+' added this comment to ticket'
                        +'</h3>'
                        +'<div class="timeline-body">'
                        +'<img style="border-radius: 50px" onload="http://localhost:9000/img/user_img.jpg" src="http://graph.facebook.com/' + data[index].comment.createdBy + '/picture" alt="user image">'
                        + '<p class="message" style="margin-top: -53px">'
                        + '<a style="margin-left: 70px" href="https:/fb.com/' + data[index].comment.createdBy + '" target="_blank">'
                        + data[index].comment.createdByName
                        + ' </a>' +' Commented at'
                        + '<small class="text-muted" style="margin-left: 10px">'
                        + moment(data[index].comment.createdAt).format("D/MM/YYYY, hh:mm:ss")
                        + '</small>'
                        + '<p style="margin-left: 85px;margin-top: -10px ">' + data[index].comment.content + '</p>'
                        + '</p>'
                    // Day la nut reply
                    + '<button  onclick="replyToComment(' + objid + ');getReplyByCommentId(' + "'" + data[index].comment.id + "'" + ');" class="btn btn-default btn-xs inline"' +
                    ' style="margin-left: 65px;margin-top: -10px; "><span class="glyphicon glyphicon-comment"' +
                    ' style="color:gray;margin-right: 10px "  title="Reply to this comment"   data-placement="bottom" ' +
                    'data-toggle="tooltip" ></span>' + countReply(data[index].comment.id) + ' replies</button>'
                    + '<small class="' + senIcon + '" style="font-size: 20px;margin-top: -50px"></small>'
                    +'</div>'
                    +'</div>'
                     +'</li>'
                }
                if(data[index].message!==null){
                    var avt;
                    var senIcon = sadicon;
                    if (data[index].message.sentimentScrore == 1)
                        senIcon = happyicon;
                    if (data[index].message.sentimentScrore == 3)
                        senIcon = questionicon;

                    if(data[index].page){
                       avt='https://graph.facebook.com/' + data[index].senderavt + '/picture';
                    }else{
                        avt=data[index].senderavt;
                    }
                    timelineitem =
                        '<li class="time-label">'
                        +'<span class="bg-gray-active" id="createtime">'
                        +moment(data[index].createdAt).format("D/MM/YYYY , hh:mm:ss")
                        +'</span>'
                        +'<span class="bg-blue-gradient" id="createtime">'
                        +'<i class="fa fa-comments"></i>'
                        +'</span>'
                        +'</li>'
                        +'<li>'
                        +'<div class="timeline-item" >'
                        +'<h3 class="timeline-header">'
                        +'<a>'+data[index].addedby+'</a>'+' added this message to ticket'
                        +'</h3>'

                        +'<div class="timeline-body">'
                        +'<img src="'+avt+'" alt="user image" style="width: 50px;height: 50px;border-radius: 50px;margin-left: 5px" alt="user image"/>'
                        + '<p class="message" style="margin-top: -53px">'
                        + '<a style="margin-left: 70px" href="https:/fb.com/' + data[index].sendername + '" target="_blank">'
                        + data[index].sendername
                        + ' </a>' +' Sended message at'
                        + '<small class="text-muted" style="margin-left: 10px">'
                        + moment(data[index].message.createdAt).format("D/MM/YYYY, hh:mm:ss")
                        + '</small>'
                        + '</p>'
                        + '<p style="margin-left: 85px;margin-top: -10px ">'
                        +'<a target="_blank" href="messenger/ticket?senderid='+data[index].message.senderid+'&receiverid='+data[index].message.receiverid+'&messageid='+data[index].message.id+'&messageEnd='+data[index].endmessage+'">'+data[index].message.content+'</a>'
                        + '</p>'
                        + '<small class="' + senIcon + '" style="font-size: 20px;margin-top: -50px"></small>'
                        +'</div>'
                        +'</div>'
                        +'</li>'
                }
                if(data[index].history!==null){
                    var status;
                    switch (data[index].history.statusid){
                        case 1: status='<a>'+data[index].history.userid+'</a>' +' assigned this ticket to ' +'<a>'+data[index].history.assignee+'</a>'; break;
                        case 2: status='<a>'+data[index].history.userid+'</a>' +' change this ticket to '+'<span style="color:#00a65a ">In progress</span>'; break;
                        case 3: status='<a>'+data[index].history.userid+'</a>' +' change this ticket to '+'<span style="color:#500a6f ">Solved</span>'; break;
                        case 4: status='<a>'+data[index].history.userid+'</a>' +' Close this ticket '; break;
                        case 5: status='This ticket is expired '; break;
                        case 6: status='<a>'+data[index].history.userid+'</a>' +' forward this ticket to ' +'<a>'+data[index].history.assignee+'</a>'; break;
                        case 7: status='<a>'+data[index].history.userid+'</a>' +' reopen this ticket '; break;
                        case 8: status='<a>'+data[index].history.userid+'</a>' +' create ticket for ' +'<a>'+data[index].history.assignee+'</a>'; break;
                    }
                    var note="";
                    if(data[index].history.note!==" "){
                        note = ' with note: ' +'<i>'+data[index].history.note+'</i>';
                    }
                    if(data[index].history.priority!==null){
                        status='<a>'+data[index].history.userid+'</a>'+' change this ticket priority to ' +data[index].history.priority;
                    }
                    timelineitem=
                        '<li class="time-label tickethistory" style="display: none">'
                        +'<span class="bg-gray-active tickethistory" style="display: none">'
                        +moment(data[index].createdAt).format("D/MM/YYYY, hh:mm:ss")
                        +'</span>'
                        +'<span class="bg-green tickethistory" id="createtime" style="display: none">'
                        +'<i class="glyphicon glyphicon-info-sign tickethistory" style="display: none;"></i>'
                        +'</span>'
                        +'</li>'
                        +'<li style="display: none" class=" tickethistory">'
                        +'<div class="timeline-item tickethistory"style="display: none" >'
                        +'<h3 class="timeline-header tickethistory"style="display: none">'
                        +'<span style="margin-left: 10px;display: none" class=" tickethistory">'+status+ note + '</span>'
                        +'</h3>'
                        +'</div>'
                        +'</li>'
                }

                $('#ticketitem').append(timelineitem);
            }
        },
        error: function () {

        }
    })
}

//Set onclick account
function setOnclickReplyAccount(imgsrc, name, token) {
    $('#reply-img').attr("src", "http://graph.facebook.com/" + imgsrc + "/picture");
    $('#reply-name').html(name);
    $('#reply-token').val(token);
}

function getCommentReply(comId) {
    $.ajax({
        url: '/getreply',
        type: "GET",
        data: { "commentid": comId },
        success: function (data){
            var element= "reply"+comId;
            $("#"+element+"").html("");
            $("#"+element+"").toggle();
            for(var i =0;i<data.length;i++){
                var objid = "'"+data[i].postId+ "_" +data[i].id+"'";
                $("#"+element+"").append(
                    '<div class="timeline-item" style="background-color:white;border: 1px solid lightgray;">'
                    +'<h5 class="timeline-header" style="border-bottom: 1px solid lightgray;">'
                    +'<small class="time pull-right"><i class="fa fa-clock-o"></i>'
                    +moment(data[i].createdAt).format("D/MM/YYYY, hh:mm:ss")
                    +'</small>'
                    +'<img src="http://graph.facebook.com/'+data[i].createdBy+'/picture" alt="user image" style="width: 50px;height: 50px;border-radius: 50px;margin-left: 5px" alt="user image"/>'
                    +'<span style="margin-left: 10px">'+data[i].createdByName+'</span>'
                    +'</h5>'
                    +'<div class="timeline-body" style="margin-left: 20px">'
                    +data[i].content
                    +'</div>'
                    // +'<div class="timeline-footer">'
                    // +'<a class="btn btn-primary btn-xs" onclick="replyToComment('+objid+')"><i class="fa fa-send"></i>Reply</a>'
                    // +'</div>'
                    +'</div>'
                )
            }

        },
        error:function () {

        }
    })
}

function getassignerinfo(userid) {
    $("#assigner").slideToggle();
    $.ajax({
        url:"/getuserprofile",
        type:"POST",
        data:{"userid":userid},
        success: function (data) {
            $("#assignerfullname").html("Fullname: "+data.firstname + " " + data.lastname);
            $("#assignerphone").html("Phone: "+data.phone);
            $("#assigneremail").html("Email: "+data.email);
        },
        error: function () {

        }
    })
}

function getassigneeinfo(userid){
    $("#assignee").slideToggle();
    $.ajax({
        url:"/getuserprofile",
        type:"POST",
        data:{"userid":userid},
        success: function (data) {
            $("#assigneefullname").html("Fullname: "+data.firstname + " " + data.lastname);
            $("#assigneephone").html("Phone: "+data.phone);
            $("#assigneeemail").html("Email: "+data.email);
        },
        error: function () {

        }
    })
}

//assign ticket
function assign(ticketid) {
    //Load list user de assign
    $.ajax({
        url:"getalluser",
        type:"GET",
        success:function (data) {
            $("#assign").html("");
            for (var i =0;i<data.length;i++){
                $("#assign").append(
                    '<option value="'+data[i].userid+'">'+data[i].firstname+ ' ' +data[i].lastname +' - '+data[i].role+'</option>'
                );

            }

        },
        error:function () {

        }
    })

    //assign ticket
    $('#assignModal').modal('toggle');
    $('#btnAssign').unbind().click(function () {
        var assignee = $('#assign').val();
        var assignnote = $("#assignnote").val();
        $.ajax({
            url:"/assignticket",
            data:{"ticketid":ticketid,"assignee":assignee,"assignnote":assignnote},
            type:"POST",
            success:function (data) {

                $("#historycheckbox").prop("checked", false);
                $('#assignModal').modal('toggle');
                getticket(ticketid)
                loadticketitem(ticketid)
            },
            error:function () {


            }
        })
    })
}

//change status
function status(ticketid) {

    $('#statusModal').modal('toggle');
    $('#btnStatus').unbind().click(function () {
        var status = $('#status').val();
        var statusnote = $("#statusnote").val();
        $.ajax({
            url:"/changeticketstatus",
            data:{"ticketid":ticketid,"status":status,"statusnote":statusnote},
            type:"POST",
            success:function (data) {
                $('#statusModal').modal('toggle');

                $("#historycheckbox").prop("checked", false);
                getticket(ticketid)
                loadticketitem(ticketid)
            },
            error:function () {

            }
        })
    })
}

//change priority,note
function updateticket(ticketid) {
    $('#changeticketModal').modal('toggle');
    $.ajax({
        url: "/getallpriorityofbrand",
        type: "GET",
        success: function (data) {
            $("#ticketpriority").html("");
            for (var i = 0; i < data.length; i++) {
                $("#ticketpriority").append(
                    '<option value="'+ data[i].id +'">'+ data[i].name+'</option>'
                )
            }
        },
        error: function () {

        }
    })

    // $.ajax({
    //     url: "/getupdateticket",
    //     type: "POST",
    //     data: {"ticketid": ticketid},
    //     success: function (data) {
    //         $("#ticketnote").val(data.note);
    //     },
    //     error: function () {
    //         alert("Fail ne");
    //     }
    // })

    $("#btnChangeTicket").unbind().click(function () {
        var ticketnote= $("#ticketnote").val();
        var ticketpriority = $("#ticketpriority").val();
        $.ajax({
            url: "/updateticket",
            type: "POST",
            data: {"ticketid": ticketid,"ticketnote":ticketnote,"ticketpriority":ticketpriority},
            success: function (data) {

                $("#historycheckbox").prop("checked", false);
                $('#changeticketModal').modal('toggle');
                getticket(ticketid)
                loadticketitem(ticketid)
                getticketduetime(ticketid)
            },
            error: function () {

            }
        })
    })
}

function getcurrentuser() {
    $.ajax({
        url:"/getcurrentuser",
        type:"GET",
        success:function (data) {
            if(data.roleid==4){
                $("#status").html(
                    '<option value="2" >In progress</option>'
                    +'<option value="3" >Solved</option>'
                )
                window.staff=true;
            }else{
                $("#status").html(
                    '<option value="2" >In progress</option>'
                    +'<option value="3" >Solved</option>'
                    +'<option value="4" >Close</option>'
                )
                window.staff=false;
            }
        }
    })

}

function showhistory() {
    $(".tickethistory").toggle();
}

function reopenticket(ticketid) {
    $.ajax({
        url:"/reopenticket",
        type:"POST",
        data:{"ticketid":ticketid},
        success:function () {

            $('#ticketitem').html("");
            $("#historycheckbox").prop("checked", false);
            getticket(ticketid)
            getticketduetime(ticketid)
            loadticketitem(ticketid)
        },
        error:function () {

        }
    })
}

function approve(ticketid) {
    var con = confirm("are you sure?");
    if(con){
        $.ajax({
            url:"/changeticketstatus",
            type:"POST",
            data:{"ticketid":ticketid,"status":4,"statusnote":"This ticket is solved"},
            success:function () {


                $('#ticketitem').html("");
                getticket(ticketid)
                getticketduetime(ticketid)
                loadticketitem(ticketid)
            },
            error:function () {

            }
        })
    }

}

function reject(ticketid) {
    var con = confirm("are you sure?");
    if(con){
        $.ajax({
            url:"/changeticketstatus",
            type:"POST",
            data:{"ticketid":ticketid,"status":1,"statusnote":"This ticket is not done yet"},
            success:function () {

                $('#ticketitem').html("");
                getticket(ticketid)
                getticketduetime(ticketid)
                loadticketitem(ticketid)
            },
            error:function () {

            }
        })
    }

}

function forwardticket(ticketid) {
    $("#forwardModal").modal('toggle');
    $.ajax({
        url: "getalluser",
        type: "GET",
        success: function (data) {
            $("#forward").html("");
            for (var i = 0; i < data.length; i++)
                $("#forward").append(
                    '<option value="'+data[i].userid+'">'+data[i].firstname+ ' ' +data[i].lastname +' - '+data[i].role+'</option>'
                );
        },
        error: function () {

        }
    })
    $('#btnForward').unbind().click(function () {
        var forwardnote = $('#forwardnote').val();
        var forwarduser = $('#forward').val();
        $.ajax({
            url: "forwardticket",
            type: "POST",
            data: {"ticketid": ticketid, "forwarduser": forwarduser, "forwardnote": forwardnote},
            success: function (data) {

                $("#forwardModal").modal('toggle');
            },
            error: function () {

            }
        })
    })
}

function replyToComment(objId) {

    $('#send-progress').attr('class', 'fa fa-paper-plane');
    $('#replyModal').modal('toggle');
    $('#btnReply').unbind().click(function () {
        var token = $('#reply-token').val();
        if ($('#txtReply').val().length > 0) {

            sendComment(objId, $('#txtReply').val(), token);
            loadticketitem(ticketid);
        }
        else {
            
        }
    });
}

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

//get All Facebook account belong to user
function getAllFBAccount() {
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
    });getAllFBAccount();
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
        async: false,
        success: function (data) {
            $('#reply-list').empty();
            $.each(data, function (index) {
                var createdByUser = "";

                $.ajax({
                    url: "/comment/checkUserReply",
                    type: "GET",
                    data: {commentId: data[index].id},
                    dataType: "text",
                    async: false,
                    success: function (username) {
                        if (username.length > 0) {
                            createdByUser = '<span style="padding-left: 4px; padding-right: 4px; color:cornflowerblue; margin-left: 10px; border: 1px solid cornflowerblue; border-radius: 3px">' + username + '</span>';
                        }
                    }
                });

                $('#reply-list').append(
                    '<div class="cmt" style="max-height:15vh;">'
                    + '<div class="cmtContent">'
                    + '<img src="http://graph.facebook.com/' + data[index].createdBy + '/picture" height="50px" width="50px">'
                    + '<p class="message">'
                    + '<a>' + data[index].createdByName + createdByUser + '</a>'
                    + '<p style="margin-left: 65px; margin-top: -10px">' + data[index].content + '</p>'
                    + '</p>'
                    + '</div>'
                    + '</div>'
                )
            })

        }
    })
}
// //Ger reply for comment
// function getReplyByCommentId(commentId) {
//     $('#reply-list').empty();
//     $('#reply-list').html('Loading....');
//     $.ajax({
//         url: '/commentbypost',
//         type: "GET",
//         data: {postId: commentId},
//         dataType: "json",
//         success: function (data) {
//             $('#reply-list').empty();
//             $.each(data, function (index) {
//                 $('#reply-list').append(
//                     '<div class="cmt" style="max-height:10vh;">'
//                     + '<div class="cmtContent">'
//                     + '<img src="http://graph.facebook.com/' + data[index].createdBy + '/picture" height="50px" width="50px">'
//                     + '<p class="message">'
//                     + '<a>' + data[index].createdByName + '</a>'
//                     + '<p style="margin-left: 65px; margin-top: -10px">' + data[index].content + '</p>'
//                     + '</p>'
//                     + '</div>'
//                     + '</div>'
//                 )
//             })
//
//         }
//     })
// }

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

function getstatuscolor(statusid) {
    switch (statusid){
        case 1: return'#f4e842'; break;
        case 2: return'#00a65a'; break;
        case 3: return'#500a6f'; break;
        case 4: return'gray'; break;
        case 5: return'#000000'; break;
    }
}