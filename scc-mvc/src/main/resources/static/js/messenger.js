/**
 * Created by Thien on 11/7/2016.
 */


//current page Id
var currentPageId = "0";
var currentCustomer = "0";
var currentPageNum = 0;
var currentInterval;
var loadConversationInterval;
var messageId = "0";
var currentCustomerName = '';
var currentCustomerAvt = '';
var currentPageName = '';
var currentPageAvt = '';
$(document).ready(function () {

    currentPageId = $("#ddlPages option:first").val();
    getAllConversationsByPageId(currentPageId);


    $('#replyText').keypress(function (e) {
        if (e.keyCode == 13)
            $('#btnReply').click();
    });
});

function getAllConversation(a) {
    clearInterval(loadConversationInterval);
    clearInterval(currentInterval);
    getAllConversationsByPageId(a.value);
}

function getAllConversationsByPageId(pageId) {
    currentPageName = $("#ddlPages option:selected").text();
    currentPageAvt = 'https://graph.facebook.com/' + pageId + '/picture';
    clearInterval(currentInterval);
    $('#conversationContent').empty();
    $('#messagesList').empty();
    $.ajax({
        url: '/messenger/getAllConversationsByPageId',
        type: "GET",
        data: {
            pageId: pageId
        },
        dataType: "json",
        success: function (data) {
            $('#messagesList').empty();
            $.each(data, function (i) {
                if (data[i].read) {
                    $('#messagesList').append(
                        '<div class="item" id="conversation' + i + '" onclick="getConversationBySenderId(' + pageId + ',\'' + data[i].senderId + '\')">'
                        + '<div><img style="max-height: 30px" src="' + data[i].senderPicture + '">' + data[i].senderName + '</div>'
                        // + '<div>' + data[i].senderName + '</div>'
                        + '<div>' + data[i].lastMessage + '</div>'
                        + '</div>'
                    )
                } else {
                    $('#messagesList').append(
                        '<div class="item" id="conversation' + i + '" onclick="getConversationBySenderId(' + pageId + ',\'' + data[i].senderId + '\')">'
                        + '<div><img style="max-height: 30px" src="' + data[i].senderPicture + '"><b>' + data[i].senderName + '</b></div>'
                        // + '<div>' + data[i].senderName + '</div>'
                        + '<div><b>' + data[i].lastMessage + '</b></div>'
                        + '</div>'
                    )
                }

            });
            $('#conversation0').click();
        }
    });

    loadConversationInterval = setInterval(function () {
        $.ajax({
            url: '/messenger/getAllConversationsByPageId',
            type: "GET",
            data: {
                pageId: pageId
            },
            dataType: "json",
            success: function (data) {
                $('#messagesList').empty();
                $.each(data, function (i) {
                    if (data[i].read) {
                        $('#messagesList').append(
                            '<div class="item" id="conversation' + i + '" onclick="getConversationBySenderId(' + pageId + ',\'' + data[i].senderId + '\')">'
                            + '<div><img style="max-height: 30px" src="' + data[i].senderPicture + '">' + data[i].senderName + '</div>'
                            // + '<div>' + data[i].senderName + '</div>'
                            + '<div>' + data[i].lastMessage + '</div>'
                            + '</div>'
                        )
                    } else {
                        $('#messagesList').append(
                            '<div class="item" id="conversation' + i + '" onclick="getConversationBySenderId(' + pageId + ',\'' + data[i].senderId + '\')">'
                            + '<div><img style="max-height: 30px" src="' + data[i].senderPicture + '"><b>' + data[i].senderName + '</b></div>'
                            // + '<div>' + data[i].senderName + '</div>'
                            + '<div><b>' + data[i].lastMessage + '</b></div>'
                            + '</div>'
                        )
                    }

                });
            }
        });
    }, 2000)
}

function setRead(pageId, senderId) {
    $.ajax({
        url: '/messenger/setMessageRead',
        type: "POST",
        data: {
            pageId: pageId,
            senderId: senderId
        },
        dataType: "json",
        success: function (data) {

        }
    });
}

function getConversationBySenderId(pageId, senderId) {
    currentPageId = pageId;
    currentCustomer = senderId;
    currentPageNum = 1;


    setRead(pageId, senderId);
    document.getElementById('replyText').setAttribute('value', '');
    document.getElementById('replyText').select();

    getCustomerInfo(senderId);
    $('#btnReply').attr('onclick', 'sendMessage(' + pageId + ',' + senderId + ')');

    clearInterval(currentInterval)

    var isFirstLoad = true;
    if (isFirstLoad) {
        $.ajax({
            url: '/messenger/getConversationBySenderIdWithPage',
            type: "POST",
            data: {
                pageId: pageId,
                senderId: senderId,
                pageNum: 1
            },
            dataType: "json",
            success: function (data) {
                $('#conversationContent').empty();
                var dataReversed = data.reverse();
                var a;
                $.each(dataReversed, function (i) {
                    a = dataReversed[i].id;
                    score = dataReversed[i].sentimentScrore;
                    if (dataReversed[i].senderid == pageId) {
                        $('#conversationContent').append(
                            '<li class="right clearfix"><span class="chat-img pull-right">' +
                            ' <img src="' + currentPageAvt + '" alt="User Avatar"/>' +
                            ' </span>' +
                            '<div class="chat-body clearfix pull-right">' +
                            ' <div class="header">' +
                            '<small class=" text-muted ">' +
                            moment(dataReversed[i].createdAt).fromNow() +
                            '</small>' +
                            '<strong class="pull-right primary-font">' + currentPageName + '</strong>' +
                            '</div>' +
                            '<p style="text-align: right">' +
                            dataReversed[i].content +
                            '</p>' +
                            '</div>' +
                            '<span class="pull-right">' +
                            changeIconSentimentScore(score) +
                            '</br>' +
                            '<button value="' + a + '" onclick="showTicket(this)"><span class="fa fa-plus"></span></button>' +
                            '</span>' +
                            '</li>');
                    } else {
                        $('#conversationContent').append(
                            '<li class="left clearfix"><span class="chat-img pull-left"> ' +
                            '<img src="' + currentCustomerAvt + '"/> </span>' +
                            '<div class="chat-body clearfix pull-left">' +
                            '<div class="header">' +
                            '<strong class="primary-font">' + currentCustomerName + '</strong>' +
                            '<small class="pull-right text-muted">' +
                            moment(dataReversed[i].createdAt).fromNow() +
                            '</small>' +
                            '</div>' +
                            '<p>' +
                            dataReversed[i].content +
                            '</p>' +
                            '</div>' +
                            changeIconSentimentScore(score) +
                            '</br>' +
                            '<button value="' + a + '" onclick="showTicket(this)"><span class="fa fa-plus"></span></button>' +
                            '</li>');
                    }

                });
                $('#conversationContent').scrollTop($('#conversationContent').height() + 500);
            }
        });
    }


    currentInterval = setInterval(function () {
        $.ajax({
            url: '/messenger/getConversationBySenderIdWithPage',
            type: "POST",
            data: {
                pageId: pageId,
                senderId: senderId,
                pageNum: 1
            },
            dataType: "json",
            success: function (data) {
                $('#conversationContent').empty();
                var dataReversed = data.reverse();
                var a;
                $.each(dataReversed, function (i) {
                    a = dataReversed[i].id;
                    score = dataReversed[i].sentimentScrore;
                    if (dataReversed[i].senderid == pageId) {
                        $('#conversationContent').append(
                            '<li class="right clearfix"><span class="chat-img pull-right">' +
                            ' <img src="' + currentPageAvt + '" alt="User Avatar"/>' +
                            ' </span>' +
                            '<div class="chat-body clearfix pull-right">' +
                            ' <div class="header">' +
                            '<small class=" text-muted ">' +
                            moment(dataReversed[i].createdAt).fromNow() +
                            '</small>' +
                            '<strong class="pull-right primary-font">' + currentPageName + '</strong>' +
                            '</div>' +
                            '<p style="text-align: right">' +
                            dataReversed[i].content +
                            '</p>' +
                            '</div>' +
                            '<span class="pull-right">' +
                            changeIconSentimentScore(score) +
                            '</br>' +
                            '<button value="' + a + '" onclick="showTicket(this)"><span class="fa fa-plus"></span></button>' +
                            '</span>' +
                            '</li>'
                        )
                        ;
                    } else {
                        $('#conversationContent').append(
                            '<li class="left clearfix"><span class="chat-img pull-left"> ' +
                            '<img src="' + currentCustomerAvt + '"/> </span>' +
                            '<div class="chat-body clearfix pull-left">' +
                            '<div class="header">' +
                            '<strong class="primary-font">' + currentCustomerName + '</strong>' +
                            '<small class="pull-right text-muted">' +
                            moment(dataReversed[i].createdAt).fromNow() +
                            '</small>' +
                            '</div>' +
                            '<p>' +
                            dataReversed[i].content +
                            '</p>' +
                            '</div>' +
                            changeIconSentimentScore(score) +
                            '</br>' +
                            '<button value="' + a + '" onclick="showTicket(this)"><span class="fa fa-plus"></span></button>' +
                            '</li>'
                        );
                    }

                });
            }
        });

    }, 500);

}

function getConversationBySenderIdWithPage() {

    clearInterval(currentInterval);
    currentInterval = setInterval(function () {
        $.ajax({
            url: '/messenger/getConversationBySenderIdWithPage',
            type: "POST",
            data: {
                pageId: currentPageId,
                senderId: currentCustomer,
                pageNum: currentPageNum
            },
            dataType: "json",
            success: function (data) {
                $('#conversationContent').empty();
                var dataReversed = data.reverse();
                var a;

                $.each(dataReversed, function (i) {
                    a = dataReversed[i].id;
                    score = dataReversed[i].sentimentScrore;
                    if (dataReversed[i].senderid == currentPageId) {
                        $('#conversationContent').append(
                            '<li class="right clearfix"><span class="chat-img pull-right">' +
                            ' <img src="' + currentPageAvt + '" alt="User Avatar"/>' +
                            ' </span>' +
                            '<div class="chat-body clearfix pull-right">' +
                            ' <div class="header">' +
                            '<small class=" text-muted ">' +
                            moment(dataReversed[i].createdAt).fromNow() +
                            '</small>' +
                            '<strong class="pull-right primary-font">' + currentPageName + '</strong>' +
                            '</div>' +
                            '<p style="text-align: right">' +
                            dataReversed[i].content +
                            '</p>' +
                            '</div>' +
                            '<span class="pull-right">' +
                            changeIconSentimentScore(score) +
                            '</br>' +
                            '<button value="' + a + '" onclick="showTicket(this)"><span class="fa fa-plus"></span></button>' +
                            '</span>' +
                            '</li>'
                        );
                    } else {
                        $('#conversationContent').append(
                            '<li class="left clearfix"><span class="chat-img pull-left"> ' +
                            '<img src="' + currentCustomerAvt + '"/> </span>' +
                            '<div class="chat-body clearfix pull-left">' +
                            '<div class="header">' +
                            '<strong class="primary-font">' + currentCustomerName + '</strong>' +
                            '<small class="pull-right text-muted">' +
                            moment(dataReversed[i].createdAt).fromNow() +
                            '</small>' +
                            '</div>' +
                            '<p>' +
                            dataReversed[i].content +
                            '</p>' +
                            '</div>' +
                            changeIconSentimentScore(score) +
                            '</br>' +
                            '<button value="' + a + '" onclick="showTicket(this)"><span class="fa fa-plus"></span></button>' +
                            '</li>'
                        );
                    }

                });

            }
        });

    }, 500);
    currentPageNum = currentPageNum + 1;
}

$(function lazyLoad() {
    var conversation = $('#conversationContent');
    conversation.scroll(function () {
        if (conversation.scrollTop() == 0)
            getConversationBySenderIdWithPage();
    });
});

function sendMessage(pageId, receiverId) {
    var content = $('#replyText').val();
    $.ajax({
        url: '/messenger/sendMessageToCustomer',
        type: "POST",
        data: {
            pageId: pageId,
            receiverId: receiverId,
            content: content
        },
        dataType: "json",
        success: function (data) {
        },
        error: function (data) {
        }
    });
    $('#replyText').val("");

}

function getCustomerInfo(customerId) {
    $.ajax({
        url: '/messenger/getCustomerInfo',
        type: "POST",
        data: {
            customerId: customerId
        },
        dataType: "json",
        success: function (data) {
            currentCustomerName = data.name;
            currentCustomerAvt = data.picture;
            $('#customer-info').empty();
            $('#customer-info').append(
                '<div>'
                + '<div><img style="max-height: 200px; text-align: center" src="' + data.picture + '"</div>'
                + '<div><label>Facebook ID: </label>' + data.facebookid + '</div>'
                + '<div><label>Name: </label>' + " " + data.name + '</div>'
                + '<div><label>Location: </label>' + " " + data.locale + '</div>'
                + '<div><label>Timezone: </label>' + " " + data.timezone + '</div>'
                + '<div><label>Gender: </label>' + " " + data.gender + '</div>'
                + '<div><label>Note: </label>' + " " + data.note + '</div>'
                + '</div>'
            )
        }
    });
}

function createTicket(ticketId,messageId) {
    $.ajax({
        url: '/messenger/addTicket',
        type: "POST",
        data: {
            ticketId: ticketId,
            messageId: messageId
        },
        dataType: "json",
        success: function (data) {
            alert("Add ticket successfully");
        },
        error: function (data) {
            alert("Add ticket fail, please try again later");
        }
    });
}

function endTicket(ticketId, messageId) {
    $.ajax({
        url: '/messenger/endTicket',
        type: "POST",
        data: {
            ticketId: ticketId,
            messageId: messageId
        },
        dataType: "json",
        success: function (data) {
        },
        error: function (data) {
        }
    });
}

function changeIconSentimentScore(score) {
    var result = '';
    switch (score) {
        case 1:
            result = "<span style='margin-left: 10px; margin-right: 10px' class='fa fa-smile-o happy'></span>";
            break;
        case 2:
            result = "<span style='margin-left: 10px; margin-right: 10px' class='fa fa-frown-o sad'></span>";
            break;
        case 3:
            result = "<span style='margin-left: 10px; margin-right: 10px' class='fa fa-question-circle-o question'></span>";
            break;
    }
    return result;
}

function showTicket(a) {
    var messageId = a.value;
    $('#ticket-list').empty();
    $.ajax({
        url: '/getallticket',
        type: "GET",
        dataType: "json",
        success: function (data) {
            $.each(data, function (index) {
                var statusColor = 'solved';
                if (data[index].statusid == 3) statusColor = 'process';
                else if (data[index].statusid == 2) statusColor = 'assigned';


                $('#ticket-list').append(
                    '<div id="' + data[index].id + '" class="ticket" onclick="createTicket(' + data[index].id + ', \'' + messageId + '\')">'
                    + '<div class="title ' + statusColor + '">' + data[index].name + '</div>'
                    + '<div>Status:&nbsp;'
                    + '<span class="fa fa-circle"></span>&nbsp;'
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