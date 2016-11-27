/**
 * Created by user on 11/25/2016.
 */

var tickets=[];

var sortstatus=true;

getallpriorityofbrand();
getalluser();
getcurrentloginuser();

function showallticket() {
    $('#ticket-list').empty();
    $.ajax({
        url: '/getallticket',
        type: "GET",
        dataType: "json",
        success: function (data) {
            $('#ticket-list').empty();
            $.each(data, function (index) {
                tickets.push(data[index]);
                appendTicket(data[index]);
            })
        }
    });
}

function showTicketModal() {

    $('#ticket-modal').modal('toggle');
    showallticket();
}

function sortticket(property) {
    tickets.sort(dynamicsort(property));
    $('#ticket-list').empty();
    for(var i=0;i<tickets.length;i++){
        appendTicket(tickets[i]);
    }
}

function sortticketbystatus() {
    tickets.sort(comparestatus);
    $('#ticket-list').empty();
    for(var i=0;i<tickets.length;i++){
        appendTicket(tickets[i]);
    }
}




$(document).on('change', '#tktimecheckbox', function () {
    if (this.checked) {
        sortticketbytime(currentCmt)
    } else {
        showallticket()
    }

});

$(document).on('change', '#tksttcheckbox', function () {
    if (this.checked) {
        sortticketbystatus(currentCmt)
    } else {
        showallticket()
    }

});

function filterticket() {
    var status = $("#filterstatus").val();
    var priority = $("#filterpriority").val();
    var createdby = $("#filtercreatedby").val();
    var assignee = $("#filterassignee").val();
    $.ajax({
        url: "/filterticket",
        type: "POST",
        data: {"status": status, "priority": priority, "createdby": createdby, "assignee": assignee},
        success: function (data) {
            tickets=[];
            $('#ticket-list').empty();
            $.each(data, function (index) {
                var statusColor = getstatuscolor(data[index].statusid);
                tickets.push(data[index]);

                // $('#ticket-list').append(
                //     '<div class="ticket" onclick="addCommentToTicket(' + data[index].id + ',' + currentCmt + ')">'
                //     + '<div class="title" style="background-color:  ' + statusColor + '">' + data[index].name + '</div>'
                //     + '<div>Status:&nbsp;'
                //     + '<span class="fa fa-circle" style="color:' + statusColor + '"></span>&nbsp;'
                //     + data[index].currentstatus
                //     + '</div>'
                //     + '<div>Created by:&nbsp;<span style="color: black; font-weight: bold">'
                //     + data[index].createbyuser
                //     + '</span></div>'
                //     + '<div>Current assignee:&nbsp;<span style="color: black; font-weight: bold">' + data[index].assigneeuser + '</span>'
                //     + '</div>'
                //     + '<div></div>'
                //     + '</div>'
                // )
                appendTicket(data[index])
            })
        },
        error: function () {
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
            for (var i = 0; i < data.length; i++) {

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
        case 1: return'#f4e842'; break;
        case 2: return'#00a65a'; break;
        case 3: return'#500a6f'; break;
        case 4: return'gray'; break;
        case 5: return'#000000'; break;

    }
}

function appendTicket(data) {
    var statuscolor=getstatuscolor(data.statusid);
    if (currentCmt.includes("mid")){
        alert(data.id);
        $('#ticket-list').append(
            '<tr onclick="createTicket('+data.id+',\'' + currentCmt + '\')">'
            +'<td>'+data.name+'</td>'
            +'<td>'+data.createbyuser+'</td>'
            +'<td>'+data.assigneeuser+'</td>'
            +'<td><i class="fa fa-circle" aria-hidden="true" style="color:'+statuscolor+';margin-right: 5px"></i>'+data.currentstatus+'</td>'
            +'<td>'+data.currentpriority+'</td>'
            +'<td>'+moment(data.createdtime).format("hh:mm:ss, D/MM/YYYY")+'</td>'
            +'</tr>'
        )
    }else {
        $('#ticket-list').append(
            '<tr onclick="addCommentToTicket('+data.id+','+currentCmt+')">'
            +'<td>'+data.name+'</td>'
            +'<td>'+data.createbyuser+'</td>'
            +'<td>'+data.assigneeuser+'</td>'
            +'<td><i class="fa fa-circle" aria-hidden="true" style="color:'+statuscolor+';margin-right: 5px"></i>'+data.currentstatus+'</td>'
            +'<td>'+data.currentpriority+'</td>'
            +'<td>'+moment(data.createdtime).format("hh:mm:ss, D/MM/YYYY")+'</td>'
            +'</tr>'
        )
    }



}

function dynamicsort(property) {
    var order;
    if(sortstatus===true){
        sortstatus=false;
        order=1;
    }else{
        sortstatus=true;
        order=-1;
    }
    return function compare(t1,t2){
        if(t1[property] > t2[property]){

            return -1*order;
        }
          if(t1[property] < t2[property]){
            return 1*order;
        }
            return 0;


    }
    
}

function newticket() {
    $('#createticketModal').modal('toggle');

    $.ajax({
        url:"getalluser",
        type:"GET",
        success:function (data) {
            $("#newticketassignee").html("");
            for (var i =0;i<data.length;i++)
                $("#newticketassignee").append(
                    '<option value="'+data[i].userid+'">'+data[i].firstname+ ' ' +data[i].lastname +' - '+data[i].role+'</option>'
                );
        },
        error:function () {
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
        }
    })


    $("#btnCreateTicket").unbind().click(function () {
        var ticketname = $('#newticketname').val();
        var priority = $("#newticketpriority").val();
        var note = $("#newticketnote").val();
        var assignee = $("#newticketassignee").val();
        $.ajax({
            url:"/createticket",
            type:"POST",
            data:{"ticketname":ticketname,"priority":priority,"note":note,"assignee":assignee},
            success:function (data) {
                showallticket()
                $('#createticketModal').modal('toggle');

            },
            error:function () {
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
                $("#createticketstaffModal").modal('toggle');
                showallticket();
            },
            error:function () {
            }
        })
    })
}

function getcurrentloginuser() {
    $.ajax({
        url:"/getcurrentuser",
        type:"GET",
        success:function (data) {
            var btn;
            if(data.roleid==4){
                btn= '<button class="btn btn-success btn-md " type="button" onclick="createstaffticket()" style="margin-left: 3px">'
                    +'<i class="fa fa-plus" aria-hidden="true" ></i> <strong>New Ticket</strong>'
                    +'</button>'

            }else{
                btn= '<button class="btn btn-success btn-md " type="button" onclick="newticket()" style="margin-left: 3px">'
                    +'<i class="fa fa-plus" aria-hidden="true" ></i> <strong>New Ticket</strong>'
                    +'</button>'

            }
            $("#createbtn").append(
                btn
            )

        }

    })

}

function searchticketbyname(searchkey) {
    var searchlist=[];
    for(var m=0; m<tickets.length; m++){
        var ticketname=tickets[m].name;
        if(ticketname.includes(searchkey)){
            searchlist.push(tickets[m]);
        }
    }

    $('#ticket-list').empty();

    for(var j=0;j<tickets.length;j++){
        appendTicket(searchlist[j]);
    }
}

