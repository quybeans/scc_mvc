/**
 * Created by user on 11/13/2016.
 */
var ticketid = $("#ticketid").val();
var currentstatus;
var duration;
var staff;
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
    if(currentstatus==2){
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
                +'<button class="btn btn-default" onclick="approve('+ticketid+')"> Approve</button>'
                +'<button class="btn btn-default" onclick="reject('+ticketid+')"> Reject</button>'
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

            alert("This ticket is expired ")
            getticketduetime(ticketid);
            getticket(ticketid);
            loadticketitem(ticketid);


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
            var statuscolor;
            var statusname;
            switch (data.statusid){
                case 1: statuscolor='color:#ffff00'; statusname="Assigned"; break;
                case 2: statuscolor='color:#00a65a'; statusname="Inprocess"; break;
                case 3: statuscolor='color:#500a6f'; statusname="Reviewing"; break;
                case 4: statuscolor='color:#01ff70'; statusname="Solved"; break;
            }
            window.currentstatus=data.statusid;
            var createat = moment(data.createdtime).format("D/MM/YYYY, hh:mm:ss");
            $("#ticketstatus").html(
                '<i class="fa fa-circle" aria-hidden="true" style="'+statuscolor+'"></i>'
                +statusname
            );
            $("#ticketname").html(data.name);
            $("#createdat").html('<i  class="fa fa-chevron-right" ></i><b style="margin-left: 5px">Created At: </b>'+createat);
            $("#ticketnotedetail").html('<i  class="fa fa-chevron-right" ></i><b style="margin-left: 5px">Note: </b>'+data.note);
            $("#ticketprioritydetail").html('<i  class="fa fa-chevron-right" ></i><b style="margin-left: 5px">Priority: </b>'+data.currentpriority);
            $("#getassigner").html('<h4><i  class="fa fa-chevron-right" onclick="getassignerinfo('+data.createdby+')"></i><b style="margin-left: 5px">Assigner:</b> '+data.createbyuser+'</h4>');
            $("#getassignee").html('<h4><i  class="fa fa-chevron-right" onclick="getassigneeinfo('+data.assignee+')"></i><b style="margin-left: 5px">Assignee:</b> '+data.assigneeuser+'</h4>');
            var groupbtn;
            if(staff){
                groupbtn = '<button class="btn btn-success" style="width: 60px" onclick="forwardticket('+data.id+')">Foward</button>'
                    +'<button class="btn btn-primary" style="width: 60px" onclick="status('+data.id+')">Status</button>'
                    +'<button class=" btn btn-danger" style="width: 60px" onclick="updateticket('+data.id+')">Edit</button>'
            }else{
                groupbtn = '<button class="btn btn-success" style="width: 60px" onclick="assign('+data.id+')">Assign</button>'
                    +'<button class="btn btn-primary" style="width: 60px" onclick="status('+data.id+')">Status</button>'
                    +'<button class=" btn btn-danger" style="width: 60px" onclick="updateticket('+data.id+')">Edit</button>'
            }

            $("#ticketbutton").html(
                groupbtn
            )

        },
        error: function () {
            alert("fail to get ticket");
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
                    var objid = "'"+data[index].comment.postId+ "_" +data[index].comment.id+"'";
                    timelineitem =
                        '<li class="time-label">'
                        +'<span class="bg-orange" id="createtime">'
                        +moment(data[index].createdAt).format("D/MM/YYYY , hh:mm:ss")
                        +'</span>'
                        +'</li>'
                        +'<li>'
                        +'<div class="timeline-item" >'
                        +'<span class="time"><i class="fa fa-clock-o"></i>'
                        +moment(data[index].comment.createdAt).format("D/MM/YYYY, hh:mm:ss")
                        +'</span>'
                        +'<h3 class="timeline-header">'
                        +'<img src="http://graph.facebook.com/'+data[index].comment.createdBy+'/picture" alt="user image" style="width: 50px;height: 50px;border-radius: 50px;margin-left: 5px" alt="user image"/>'
                        +'<span style="margin-left: 10px">'+data[index].comment.createdByName+'</span>'
                        +'</h3>'
                        +'<div class="timeline-body" onclick="getCommentReply('+data[index].comment.id+')">'
                        +data[index].comment.content
                        +'</div>'
                        +'<div class="timeline-footer">'
                        +'<button class="btn btn-primary btn-xs" onclick="replyToComment('+objid+')"><i class="fa fa-send"></i>Reply</button>'
                        +'</div>'
                        +'</div>'
                        +'<div class="pull-right"  id="reply'+data[index].comment.id+'" style="width: 70%;margin-right: 20px;margin-top: 10px;border-radius: 10px;display: none">'
                        +'</div>'
                        +'</li>'
                }
                if(data[index].message!==null){
                    var avt;
                    if(data[index].page){
                       avt='https://graph.facebook.com/' + data[index].senderavt + '/picture';
                    }else{
                        avt=data[index].senderavt;
                    }
                    timelineitem =
                        '<li class="time-label">'
                        +'<span class="bg-blue" id="createtime">'
                        +moment(data[index].createdAt).format("D/MM/YYYY , hh:mm:ss")
                        +'</span>'
                        +'</li>'
                        +'<li>'
                        +'<div class="timeline-item" >'
                        +'<span class="time"><i class="fa fa-clock-o"></i>'
                        +moment(data[index].message.createdAt).format("D/MM/YYYY, hh:mm:ss")
                        +'</span>'
                        +'<h3 class="timeline-header">'
                        +'<img src="'+avt+'" alt="user image" style="width: 50px;height: 50px;border-radius: 50px;margin-left: 5px" alt="user image"/>'
                        +'<span style="margin-left: 10px">'+data[index].sendername+'</span>'
                        +'</h3>'
                        +'<div class="timeline-body">'
                        +'<a href="messenger/ticket?senderid='+data[index].message.senderid+'&receiverid='+data[index].message.receiverid+'&messageid='+data[index].message.id+'&messageEnd='+data[index].endmessage+'">'+data[index].message.content+'</a>'
                        +'</div>'
                        +'</div>'
                        +'</li>'
                }
                if(data[index].history!==null){
                    var status;
                    switch (data[index].history.statusid){
                        case 1: status='<a>'+data[index].history.userid+'</a>' +' assigned this ticket to ' +'<a>'+data[index].history.assignee+'</a>'; break;
                        case 2: status='<a>'+data[index].history.userid+'</a>' +' change this ticket to '+'<span style="color:#00a65a ">Inprocess</span>'; break;
                        case 3: status='<a>'+data[index].history.userid+'</a>' +' change this ticket to '+'<span style="color:#500a6f ">Reviewing</span>'; break;
                        case 4: status='<a>'+data[index].history.userid+'</a>' +' change this ticket to '+'<span style="color:#01ff70 ">Solved</span>'; break;
                    }
                    var note="";
                    if(data[index].history.note!==null){
                        note = " with note: " +data[index].history.note;
                    }
                    if(data[index].history.priority!==null){
                        status='<a>'+data[index].history.userid+'</a>'+' change this ticket priority to ' +data[index].history.priority;
                    }
                    timelineitem=
                        '<li class="time-label tickethistory" style="display: none">'
                        +'<span class="bg-green tickethistory" style="display: none">'
                        +moment(data[index].createdAt).format("D/MM/YYYY, hh:mm:ss")
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
            alert("fail to load ticket item")
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
                    +'<div class="timeline-body" style="margin-left: 20px">'+data[i].content+'</div>'
                    +'<div class="timeline-footer">'
                    +'<a class="btn btn-primary btn-xs" onclick="replyToComment('+objid+')"><i class="fa fa-send"></i>Reply</a>'
                    +'</div>'
                    +'</div>'
                )
            }

        },
        error:function () {
            alert("fail to load reply");
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
            alert("fail to get user profile");
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
            alert("fail to get user profile");
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
                    '<option value="'+data[i].userid+'">'+data[i].firstname+ ' ' +data[i].lastname +'</option>'
                );

            }

        },
        error:function () {
            alert("Fail to load list user");
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
                alert("assign ticket successful"+data.assignee);
                $('#assignModal').modal('toggle');
                getticket(ticketid)
                loadticketitem(ticketid)
            },
            error:function () {
                alert("fail to assign ticket");
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
                alert("change status successfull!");
                getticket(ticketid)
                loadticketitem(ticketid)
            },
            error:function () {
                alert("fail to change ticket status");
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
            alert("fail to load priority for update")
        }
    })

    $.ajax({
        url: "/getupdateticket",
        type: "POST",
        data: {"ticketid": ticketid},
        success: function (data) {
            $("#ticketnote").val(data.note);
        },
        error: function () {
            alert("Fail ne");
        }
    })

    $("#btnChangeTicket").unbind().click(function () {
        var ticketnote= $("#ticketnote").val();
        var ticketpriority = $("#ticketpriority").val();
        $.ajax({
            url: "/updateticket",
            type: "POST",
            data: {"ticketid": ticketid,"ticketnote":ticketnote,"ticketpriority":ticketpriority},
            success: function (data) {
                alert("update ticket successful!");
                $('#changeticketModal').modal('toggle');
                getticket(ticketid)
                loadticketitem(ticketid)
                getticketduetime(ticketid)
            },
            error: function () {
                alert("Fail ne");
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
                    '<option value="2" >Inprocess</option>'
                    +'<option value="3" >Reviewing</option>'
                )
                window.staff=true;
            }else{
                $("#status").html(
                    '<option value="2" >Inprocess</option>'
                    +'<option value="3" >Reviewing</option>'
                    +'<option value="4" >Solved</option>'
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
        url:"/changeticketstatus",
        type:"POST",
        data:{"ticketid":ticketid,"status":1,"statusnote":"This ticket need to rework"},
        success:function () {
            alert("reopen ticket successfull");
            $('#ticketitem').html("");
            getticket(ticketid)
            getticketduetime(ticketid)
            loadticketitem(ticketid)
        },
        error:function () {
            alert("reopen fail");
        }
    })
}

function approve(ticketid) {
    $.ajax({
        url:"/changeticketstatus",
        type:"POST",
        data:{"ticketid":ticketid,"status":4,"statusnote":"This ticket is solved"},
        success:function () {
            alert("approve ticket successfull");
            $('#ticketitem').html("");
            getticket(ticketid)
            getticketduetime(ticketid)
            loadticketitem(ticketid)
        },
        error:function () {
            alert("approve fail");
        }
    })
}

function reject(ticketid) {
    $.ajax({
        url:"/changeticketstatus",
        type:"POST",
        data:{"ticketid":ticketid,"status":1,"statusnote":"Rework pls"},
        success:function () {
            alert("Reject ticket successfull");
            $('#ticketitem').html("");
            getticket(ticketid)
            getticketduetime(ticketid)
            loadticketitem(ticketid)
        },
        error:function () {
            alert("approve fail");
        }
    })
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
            data: {"ticketid": ticketid, "forwarduser": forwarduser, "forwardnote": forwardnote},
            success: function (data) {
                alert("Forward ticket: " + data.ticketid + " to user: " + data.assignee)
                $("#forwardModal").modal('toggle');
            },
            error: function () {
                alert("Fail to forward ticket")
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
        }
        else {
            alert("Enter some thing to comment..")
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
            getAllFBAccount();
        }
    });
}