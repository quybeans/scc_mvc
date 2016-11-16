
/**
 * Created by user on 11/13/2016.
 */
var table = null;
var staff;

$(document).ready(function () {
    table= $("#table").DataTable({
        ajax: {
            url:"/getallticket",
            type: "GET"
        },
        sAjaxDataProp : "",
        columns: [
            {title: "ID", data:"id",visible:false},
            {title: "Name",data:"name" },
            {title: "Created By",data:"createbyuser"},
            {title: "Assignee",data:"assigneeuser"},
            {title: "Status",data:"currentstatus"},
            {title: "Created Time",data:"createdtime"},
            {title: "Priority", data:"currentpriority"},
            {title: "Note", data:"note"},
            {title:"Action",data:"name"},
        ],

        columnDefs:[
            {
                width:'20%',
                targets: 7,
                render: (data, type, row) => {
                return '<h5 style="overflow: hidden; max-height: 100px;word-wrap: break-word">'+row.note+'</h5>'
            },
    },
        {
            width:'20%',
            targets: 1,
            render: (data, type, row) => {
            return '<h5 style="overflow: hidden; max-height: 100px"><a href="followticket?ticketid='+row.id+'">'+row.name+'</a></h5>'
        },
},
    {
        width:'15%',
            targets: 5,
        render: (data, type, row) => {
        if(type=== 'display'){
            return moment(row.createdtime).format("D/MM/YYYY h:mm:ss");
        }
        return moment(data).format("D/MM/YYYY");
    },
    },
    {
        width:'15%',
            targets: 8,
        render: (data, type, row) => {
        if(type==='display'){
            if(staff){
                return '<div class="btn-group btn-xs">'
                    +'<button class="btn btn-success btn-xs" onclick="forwardticket('+row.id+')"><i class="fa fa-ticket"></i></button>'
                    +'<button class="btn btn-primary btn-xs" onclick="status('+row.id+')"><i class="fa fa-navicon"></i></button>'
                    +'<button class="btn btn-danger btn-xs" onclick="updateticket('+row.id+')"><i class="fa fa-pencil"></i></button>'
                    +'</div>'
            }else{
                return '<div class="btn-group btn-xs">'
                    +'<button class="btn btn-success btn-xs" onclick="assign('+row.id+')"><i class="fa fa-ticket"></i></button>'
                    +'<button class="btn btn-primary btn-xs" onclick="status('+row.id+')"><i class="fa fa-navicon"></i></button>'
                    +'<button class="btn btn-danger btn-xs" onclick="updateticket('+row.id+')"><i class="fa fa-pencil"></i></button>'
                    +'</div>'
            }

        }
        return data;
    },
    },
    {
        width:'10%',
            targets: 4,
        render: (data, type, row) => {
        if(type=== 'display'){
            var statuscolor;
            switch (row.statusid){
                case 1: statuscolor='color:#ffff00'; break;
                case 2: statuscolor='color:#00a65a'; break;
                case 3: statuscolor='color:#500a6f'; break;
                case 4: statuscolor='color:#01ff70'; break;
            }
            return '<p><i class="fa fa-circle" aria-hidden="true" style="'+statuscolor+'"></i>'+'  ' +row.currentstatus+'</p>'
        }
        return data;
    },
    },
    ],
    initComplete:  function() {
        this.api().columns().every( function (i) {
            if(i < 7 && i != 1 ){
                var column = this;
                var select = $('<select><option value=""></option></select>')
                    .appendTo( $(column.footer()).empty() )
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );

                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );
                var array = [];
                column.data().unique().sort().each( function ( d, j ) {
                    if(typeof d ==='number'){
                        if(jQuery.inArray(moment(d).format("D/MM/YYYY"), array) < 0) {
                            select.append( '<option value="'+moment(d).format("D/MM/YYYY")+'">'+moment(d).format("D/MM/YYYY")+'</option>' );
                        }
                        array.push(moment(d).format("D/MM/YYYY"));
                    }else{
                        select.append( '<option value="'+d+'">'+d+'</option>' )
                    }

                } );
            }
        } );
    }
});
});


getcurrentuser();
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
                table.ajax.reload();
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
                table.ajax.reload();
                $('#statusModal').modal('toggle');
                alert("change status successfull!");

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
                table.ajax.reload();
                alert("update ticket successful!");
                $('#changeticketModal').modal('toggle');

            },
            error: function () {
                alert("Fail ne");
            }
        })
    })
}

//show config priority modal
function configpriority() {
    $('#configPriorityModal').modal('toggle');
    getallpriority();
}

//load all priority
function getallpriority() {

    $.ajax({
        url: "/getallpriorityofbrand",
        type: "GET",
        success: function (data) {
            $('#brandpriority').html('');
            for (var i = 0; i < data.length; i++) {
                $('#brandpriority').append(
                    '<div class="form-inline">'
                    + '<input type="text" style="width: 20%" class="form-control" id="name' + data[i].id + '" value="' + data[i].name + '">'
                    + '<input type="number" style="width: 60%" class="form-control" id="duration' + data[i].id + '" value="' + data[i].duration + '"/>'
                    + '<input type="button" class="btn btn-primary btn-xs" value="Update" onclick="updatepriority(' + data[i].id + ')" /> '
                    + '<input type="button" class="btn btn-danger btn-xs" value="Delete" onclick="deletepriority(' + data[i].id + ')" /> '
                    + '</div>'
                )
            }
        },
        error: function () {
            alert("Fail to load brand priority");
        }
    })
}

//update priority
function updatepriority(id) {
    var prioritynameelementid = "name" + id + "";
    var priorityedurationlementid = "duration" + id + "";
    var name = $("#" + prioritynameelementid + "").val();
    var duration = $("#" + priorityedurationlementid + "").val();
    $.ajax({
        url: "/updatepriority",
        type: "POST",
        data: {"priorityid": id, "priorityduration": duration, "priorityname": name},
        success: function (data) {
            alert("update priority successfully" + data.duration);
        },
        error: function () {
            alert("fail to update priority");
        }
    })
}

//create priority
function createpriority() {
    var priorityname = $('#newpriorityname').val();
    var priorityduration = $('#newpriorityduration').val();
    alert(priorityname + "  " + priorityduration)
    $.ajax({
        url: "/createpriority",
        type: "POST",
        data: {"priorityname": priorityname, "priorityduration": priorityduration},
        success: function (data) {
            getallpriority();
            $('#newpriorityname').val("");
            $('#newpriorityduration').val("");
        },
        error: function () {
            alert("Fail to create priority")
            $('#newpriorityname').val("");
            $('#newpriorityduration').val("");
        }
    })
}

//delete priority
function deletepriority(id) {
    $.ajax({
        url: "/deletepriority",
        type: "POST",
        data: {"priorityid": id},
        success: function () {
            alert("Delete priotiry" + id + " successfull");
            getallpriority();
        },
        error: function () {
            alert("Fail to delete priority");
        }
    })
}

function showticket(cmtid) {
    $('#ticketModal').modal('toggle');

    $.ajax({
        url:"/getcommentbyid",
        type:"GET",
        data:{"commentid":cmtid},
        success:function (data) {
            $('#comment').html('');
            $('#comment').append(
                '<div  class="cmt" >'
                +'<div class="col-lg-2 cmtContent">'
                +'<img onload="http://localhost:9000/img/user_img.jpg" src="http://graph.facebook.com/'+data.createdBy+'/picture" alt="user image">'
                +'</div>'
                +'<div class="col-lg-8">'
                +'<p class="message">'
                +'<a>'
                +data.createdByName
                +'<small class="text-muted" style="margin-left: 10px">'
                +jQuery.format.prettyDate( new Date(data.createdAt))
                +'</small>'
                +' </a>'
                +'<p style="margin-left: 65px;margin-top: -10px " onclick="">'+data.content+'</p>'
                +'</p>'
                +'</div>'
                +'<div class="col-lg-2" style="margin-top: 30px">' +'<small class="" style="font-size: 20px;"></small>'
                +'</div>'
                +'</div>'
            )
        },
        error:function () {
            alert("Fail to get ticket comment");
        }
    })

    $.ajax({
        url:"/getreply",
        type:"GET",
        data:{"commentid":cmtid},
        success:function (data) {
            $('#reply').html('');
            for(var i=0;i<data.length;i++){
                $('#reply').append(
                    '<div  class="cmt" >'
                    +'<div class="col-lg-2 cmtContent">' +
                    '<img onload="http://localhost:9000/img/user_img.jpg" src="http://graph.facebook.com/'+data[i].createdBy+'/picture" alt="user image">'
                    +'</div>'
                    +'<div class="col-lg-8">'
                    +'<p class="message">'
                    +'<a>'
                    +data[i].createdByName
                    +'<small class="text-muted" style="margin-left: 10px">'
                    +jQuery.format.prettyDate( new Date(data[i].createdAt))
                    +'</small>'
                    +' </a>'
                    +'<p style="margin-left: 65px;margin-top: -10px " onclick="">'+data[i].content+'</p>'
                    +'</p>'
                    +'</div>'
                    +'<div class="col-lg-2" style="margin-top: 30px">' +'<small class="" style="font-size: 20px;"></small>'
                    +'</div>'
                    +'</div>'
                )
            }
        },
        error:function () {
            alert("Fail to load reply")
        }
    })
}

function newticket() {
    $('#createticketModal').modal('toggle');

    $.ajax({
        url:"getalluser",
        type:"GET",
        success:function (data) {
            $("#assignee").html("");
            for (var i =0;i<data.length;i++)
                $("#assignee").append(
                    '<option value="'+data[i].userid+'">'+data[i].firstname+ ' ' +data[i].lastname +'</option>'
                );
        },
        error:function () {
            alert("Fail to load list user");
        }
    })

    $.ajax({
        url: "/getallpriorityofbrand",
        type: "GET",
        success: function (data) {
            $("#newticketpriority").html("");
            for (var i = 0; i < data.length; i++) {
                $("#newticketpriority").append(
                    '<option value="'+ data[i].id +'">'+ data[i].name+'</option>'
                )
            }
        },
        error: function () {
            alert("fail to load priority for update")
        }
    })


    $("#btnCreateTicket").unbind().click(function () {
        var ticketname = $('#newticketname').val();
        var priority = $("#newticketpriority").val();
        var note = $("#newticketnote").val();
        var assignee = $("#assignee").val();
        $.ajax({
            url:"/createticket",
            type:"POST",
            data:{"ticketname":ticketname,"priority":priority,"note":note,"assignee":assignee},
            success:function (data) {
                table.ajax.reload();
                alert("create ticket: "+data.id+" successful!");
                $('#createticketModal').modal('toggle');

            },
            error:function () {
                alert("fail to create ticket");
            }
        })
    })

}

function getcurrentuser() {
    $.ajax({
        url:"/getcurrentuser",
        type:"GET",
        success:function (data) {
            var btn;
            if(data.roleid==4){
                btn= '<button class="btn btn-success btn-md " type="button" onclick="createstaffticket()">'
                    +'<i class="fa fa-plus" aria-hidden="true"></i> <strong>New Ticket</strong>'
                    +'</button>'
                $("#status").html(
                    '<option value="2" >Inprocess</option>'
                    +'<option value="3" >Solved</option>'
                )
                window.staff=true;
            }else{
                btn= '<button class="btn btn-success btn-md " type="button" onclick="newticket()">'
                    +'<i class="fa fa-plus" aria-hidden="true"></i> <strong>New Ticket</strong>'
                    +'</button>'
                $("#status").html(
                    '<option value="2" >Inprocess</option>'
                    +'<option value="3" >Solved</option>'
                    +'<option value="4" >Close</option>'
                )
                window.staff=false;
            }
            $("#topbtn").append(
                '<button class="btn btn-primary btn-md " type="button" onclick="ticketrequest('+data.userid+')">'
                +'<i class="fa fa-flag" aria-hidden="true"></i> <strong>Ticket Request</strong>'
                +'</button>'
                +btn
            )

        }

    })

}

function ticketrequest(userid) {
    $("#ticketrequestModal").modal('toggle');
    $.ajax({
        url:"/getticketrequest",
        type:"GET",
        data:{"assignee":userid},
        success:function (data) {
            if(data.length!=0){
                for(var i=0;i<data.length;i++){
                    $("#request").append(
                        ' <tr>'
                        +'<td><h5>'+data[i].ticketname+'</h5></td>'
                        +'<td><h5>'+data[i].assignername+'</h5></td>'
                        +'<td><h5>'+moment(data[i].requestat).format("D/MM/YYYY, hh:mm:ss")+'</h5></td>'
                        +'<td><h5 style="max-width: 300px;word-wrap: break-word">'+data[i].note+'</h5></td>'
                        +'<td>'
                        +'<div class="btn-group">'
                        +'<button class="btn btn-success btn-md" onclick="acceptrequest('+data[i].id+')"><i class="fa fa-check"></i></button>'
                        +'<button class="btn btn-danger btn-md" onclick="denirequest('+data[i].id+')"><i class="fa fa-remove"></i></button>'
                        +'</div>'
                        +'</td>'
                        +'</tr>'
                    )
                }
            }else {
                $("#requestmodal").html(
                    '<h3 style="text-align: center;color: lightgreen">You have 0 ticket request</h3>'
                )
            }

        },
        error:function () {
            alert("fail to load ticket request")
        }
    })
}

function acceptrequest(requestid) {
    $.ajax({
        url:"/acceptrequest",
        type:"POST",
        data:{"requestid":requestid},
        success:function () {
            table.ajax.reload();
            $("#ticketrequestModal").modal('toggle');
            alert("accept request successfully");
        },
        error:function(){
            alert("fail to accept request")
        }
    })
}

function denirequest(requestid) {
    $.ajax({
        url:"/denirequest",
        type:"POST",
        data:{"requestid":requestid},
        success:function () {
            $("#ticketrequestModal").modal('toggle');
            alert("accept request successfully");
        },
        error:function(){
            alert("fail to accept request")
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

function createstaffticket(){
    $("#createticketstaffModal").modal('toggle');
    $.ajax({
        url: "/getallpriorityofbrand",
        type: "GET",
        success: function (data) {
            $("#newticketpriority").html("");
            for (var i = 0; i < data.length; i++) {
                $("#newticketstaffpriority").append(
                    '<option value="'+ data[i].id +'">'+ data[i].name+'</option>'
                )
            }
        },
        error: function () {
            alert("fail to load priority for update")
        }
    })

    $("#btnCreatestaffTicket").unbind().click(function () {
        var ticketname=$("#newticketstaffname").val();
        var ticketpriority=$("#newticketstaffpriority").val();
        var ticketnote = $("#newticketstaffnote").val();
        $.ajax({
            url:"/createticketforstaff",
            type:"POST",
            data:{"ticketname":ticketname,"ticketpriority":ticketpriority,"ticketnote":ticketnote},
            success:function () {
                alert("Create ticket for staff successfull")
                $("#createticketstaffModal").modal('toggle');
                table.ajax.reload();
            },
            error:function () {
                alert("fail to create ticket for staff")
            }
        })
    })
}

