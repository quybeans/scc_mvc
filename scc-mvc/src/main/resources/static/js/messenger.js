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
var isFirstLoadPage = true;
var currentMessageId = '0';


function getParameterByName(name, url) {
    if (!url) {
        url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}



$(document).ready(function () {

    getalluser();
    getallpriorityofbrand();

    var senderid = getParameterByName('senderid');
    var messageId = getParameterByName('messageid');
    var pageId = getParameterByName('receiverid');
    var isSenderPage = false;
    if (senderid != null && messageId != null && pageId != null) {
        $('select#ddlPages').find('option').each(function () {

            var page = $(this).val();
            if (senderid == page) {
                isSenderPage = true;
                currentPageName = $(this).text();
            }
        });
        if (isSenderPage) {
            currentPageId = senderid;
            searchConversationBySenderId(pageId);
        } else {
            currentPageId = pageId;
            searchConversationBySenderId(senderid);
        }

    } else {
        currentPageId = $("#ddlPages option:first").val();
        getAllConversationsByPageId(currentPageId);
    }

    $('#replyText').keypress(function (e) {
        if (e.keyCode == 13)
            $('#btnReply').click();
    });


    $('#txtSearchConversation').keydown(function (e) {
        var txtSearch = $('#txtSearchConversation').val();

        if (e.keyCode == 13)
            $('#btn-search-conversation').click();
    });
    showCustomerInfo();



});

function getAllConversation(a) {
    clearInterval(loadConversationInterval);
    clearInterval(currentInterval);
    currentPageId = "0";
    currentCustomer = "0";
    getAllConversationsByPageId(a.value);
}

function getAllConversationsByPageId(pageId) {
    $('#conversation-content-loading-icon').removeClass("hidden");
    currentPageName = $("#ddlPages option:selected").text();
    currentPageAvt = 'https://graph.facebook.com/' + pageId + '/picture';

    clearInterval(currentInterval);
    clearInterval(loadConversationInterval);
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
            $('#conversation-content-loading-icon').addClass("hidden");
            $('#messagesList').empty();
            $.each(data, function (i) {
                if (data[i].read) {
                    $('#messagesList').append(
                        '<div class="item" style="position: relative" id="conversation' + i + '" onclick="getConversationBySenderId(' + pageId + ',\'' + data[i].senderId + '\')">'
                        + '<div>' +
                        '<img class="senderAvt" src="' + data[i].senderPicture + '"><div class="conversation-sender-name pull-left">' + data[i].senderName + '</div>' +
                        '<div class="sentTime text-muted pull-right"> ' + $.format.date(data[i].sentTime, "HH:mm") + '</div>' +
                        '</div>'
                        + '<div class="crop">' + data[i].lastMessage + '</div>'
                        + '</div>'
                    )
                } else {
                    $('#messagesList').append(
                        '<div class="item" style="position: relative" id="conversation' + i + '" onclick="getConversationBySenderId(' + pageId + ',\'' + data[i].senderId + '\')">'
                        + '<div><img class="senderAvt" src="' + data[i].senderPicture + '"><div class="conversation-sender-name text-danger pull-left unread-conversation"><b>' + data[i].senderName + '</b></div>' +
                        '<div class="sentTime text-danger pull-right"> ' + $.format.date(data[i].sentTime, "HH:mm") + '</div>' +
                        '</div>'
                        + '<div class="crop text-danger unread-conversation"><b>' + data[i].lastMessage + '</b></div>'
                        + '</div>'
                    )
                }

            });
            $('#conversation0').click();
        }
    });
    if (!isFirstLoadPage) {
        $('#filter-page-modal').modal('toggle');
    }
    isFirstLoadPage = false;

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
                            '<div class="item" style="position: relative" id="conversation' + i + '" onclick="getConversationBySenderId(' + pageId + ',\'' + data[i].senderId + '\')">'
                            + '<div>' +
                            '<img class="senderAvt" src="' + data[i].senderPicture + '"><div class="conversation-sender-name text-info pull-left">' + data[i].senderName + '</div>' +
                            '<div class="sentTime text-muted pull-right"> ' + $.format.date(data[i].sentTime, "HH:mm") + '</div>' +
                            '</div>'
                            + '<div class="crop text-info">' + data[i].lastMessage + '</div>'
                            + '</div>'
                        )
                    } else {
                        $('#messagesList').append(
                            '<div class="item" style="position: relative" id="conversation' + i + '" onclick="getConversationBySenderId(' + pageId + ',\'' + data[i].senderId + '\')">'
                            + '<div><img class="senderAvt" src="' + data[i].senderPicture + '"><div class="conversation-sender-name text-danger pull-left unread-conversation"><b>' + data[i].senderName + '</b></div>' +
                            '<div class="sentTime text-muted pull-right"> ' + $.format.date(data[i].sentTime, "HH:mm") + '</div>' +
                            '</div>'
                            + '<div class="crop text-danger unread-conversation"><b>' + data[i].lastMessage + '</b></div>'
                            + '</div>'
                        )
                    }

                });
            }
        });
    }, 2000)
}

function setRead() {
    $.ajax({
        url: '/messenger/setConversationRead',
        type: "POST",
        data: {
            pageId: currentPageId,
            senderId: currentCustomer
        },
        dataType: "json",
        success: function (data) {

        }
    });
}

function firstLoadConversationBySenderId() {
    $('#conversation-content-loading-icon').addClass("hidden");
    $.ajax({
        url: '/messenger/getConversationBySenderIdWithPage',
        type: "POST",
        data: {
            pageId: currentPageId,
            senderId: currentCustomer,
            pageNum: 1
        },
        dataType: "json",
        success: function (data) {
            // $('#conversation-content-loading-icon').removeClass("hidden");
            $('#conversationContent').empty();
            var dataReversed = data.reverse();
            var messageId;
            $.each(dataReversed, function (i) {
                messageId = dataReversed[i].id;
                score = dataReversed[i].sentimentScrore;
                if (dataReversed[i].senderid == currentPageId) {
                    $('#conversationContent').append(
                        '<li class="right clearfix"><span class="chat-img pull-right">' +
                        ' <img src="' + currentPageAvt + '" alt="User Avatar"/>' +
                        ' </span>' +
                        '<div class="chat-body clearfix pull-right">' +
                        ' <div class="header">' +
                        '<small class=" text-muted ">' +
                        // moment(dataReversed[i].createdAt).fromNow() +
                        $.format.date(dataReversed[i].createdAt, "HH:mm") +
                        '</small>' +
                        '<a class="pull-right primary-font">' + currentPageName + '</a>' +
                        '</div>' +
                        '<p style="text-align: right">' +
                        dataReversed[i].content +
                        '</p>' +
                        '</div>' +
                        '<span class="pull-right">' +
                        changeIconSentimentScore(score) +
                        '</br>' +
                        showButtonAddTicketAtPage(dataReversed[i].ticket, messageId) +
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
                        '<a class="primary-font">' + currentCustomerName + '</a>' +
                        '<small class="pull-right text-muted">' +
                        $.format.date(dataReversed[i].createdAt, "HH:mm") +
                        '</small>' +
                        '</div>' +
                        '<p  class="sender-chat-content">' +
                        dataReversed[i].content +
                        '</p>' +
                        '</div>' +
                        changeIconSentimentScore(score) +
                        '</br>' +
                        showButtonAddTicketAtSender(dataReversed[i].ticket, messageId) +
                        '</li>'
                    );
                }

            });
            $('#conversationContent').scrollTop($('#conversationContent').height() + 500);
        }
    });
}

function reloadConversationBySenderId() {
    $.ajax({
        url: '/messenger/getConversationBySenderIdWithPage',
        type: "POST",
        data: {
            pageId: currentPageId,
            senderId: currentCustomer,
            pageNum: 1
        },
        dataType: "json",
        success: function (data) {
            $('#conversationContent').empty();
            var dataReversed = data.reverse();
            var messageId;
            $.each(dataReversed, function (i) {
                messageId = dataReversed[i].id;
                score = dataReversed[i].sentimentScrore;
                if (dataReversed[i].senderid == currentPageId) {
                    $('#conversationContent').append(
                        '<li onclick="checkMessageTicket(\'' + messageId + '\')" class="right clearfix"><span class="chat-img pull-right">' +
                        ' <img src="' + currentPageAvt + '" alt="User Avatar"/>' +
                        ' </span>' +
                        '<div class="chat-body clearfix pull-right">' +
                        ' <div class="header">' +
                        '<small class=" text-muted ">' +
                        $.format.date(dataReversed[i].createdAt, "HH:mm") +
                        '</small>' +
                        '<a class="pull-right primary-font">' + currentPageName + '</a>' +
                        '</div>' +
                        '<p style="text-align: right">' +
                        dataReversed[i].content +
                        '</p>' +
                        '</div>' +
                        '<span class="pull-right">' +
                        changeIconSentimentScore(score) +
                        '</br>' +
                        showButtonAddTicketAtPage(dataReversed[i].ticket, messageId) +
                        '</span>' +
                        '</li>'
                    )
                    ;
                } else {
                    $('#conversationContent').append(
                        '<li onclick="checkMessageTicket(\'' + messageId + '\')" class="left clearfix"><span class="chat-img pull-left"> ' +
                        '<img src="' + currentCustomerAvt + '"/> </span>' +
                        '<div class="chat-body clearfix pull-left">' +
                        '<div class="header">' +
                        '<a class="primary-font">' + currentCustomerName + '</a>' +
                        '<small class="pull-right text-muted">' +
                        $.format.date(dataReversed[i].createdAt, "HH:mm") +
                        '</small>' +
                        '</div>' +
                        '<p  class="sender-chat-content">' +
                        dataReversed[i].content +
                        '</p>' +
                        '</div>' +
                        changeIconSentimentScore(score) +
                        '</br>' +
                        showButtonAddTicketAtSender(dataReversed[i].ticket, messageId) +
                        '</li>'
                    );
                }

            });
        }
    });
}

function getConversationBySenderId(pageId, senderId) {

    currentPageId = pageId;
    currentCustomer = senderId;
    currentPageNum = 1;


    setRead();
    document.getElementById('replyText').setAttribute('value', '');
    document.getElementById('replyText').select();

    getCustomerInfo(senderId);

    clearInterval(currentInterval);

    var isFirstLoad = true;
    if (isFirstLoad) {
        firstLoadConversationBySenderId();
    }
    currentInterval = setInterval(reloadConversationBySenderId, 500);

}

function getConversationBySenderIdWithPage() {
    $('#conversation-content-loading-icon').removeClass("hidden");
    clearInterval(currentInterval);
    currentPageNum = currentPageNum + 1;
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
                var messageId;

                $.each(dataReversed, function (i) {
                    messageId = dataReversed[i].id;
                    score = dataReversed[i].sentimentScrore;
                    if (dataReversed[i].senderid == currentPageId) {
                        $('#conversationContent').append(
                            '<li onclick="checkMessageTicket(\'' + messageId + '\')" class="right clearfix"><span class="chat-img pull-right">' +
                            ' <img src="' + currentPageAvt + '" alt="User Avatar"/>' +
                            ' </span>' +
                            '<div class="chat-body clearfix pull-right">' +
                            ' <div class="header">' +
                            '<small class=" text-muted ">' +
                            $.format.date(dataReversed[i].createdAt, "HH:mm") +
                            '</small>' +
                            '<a class="pull-right primary-font">' + currentPageName + '</a>' +
                            '</div>' +
                            '<p style="text-align: right">' +
                            dataReversed[i].content +
                            '</p>' +
                            '</div>' +
                            '<span class="pull-right">' +
                            changeIconSentimentScore(score) +
                            '</br>' +
                            showButtonAddTicketAtPage(dataReversed[i].ticket, messageId) +
                            '</span>' +
                            '</li>'
                        )
                        ;
                    } else {
                        $('#conversationContent').append(
                            '<li onclick="checkMessageTicket(\'' + messageId + '\')" class="left clearfix"><span class="chat-img pull-left"> ' +
                            '<img src="' + currentCustomerAvt + '"/> </span>' +
                            '<div class="chat-body clearfix pull-left">' +
                            '<div class="header">' +
                            '<a class="primary-font">' + currentCustomerName + '</a>' +
                            '<small class="pull-right text-muted">' +
                            $.format.date(dataReversed[i].createdAt, "HH:mm") +
                            '</small>' +
                            '</div>' +
                            '<p class="sender-chat-content">' +
                            dataReversed[i].content +
                            '</p>' +
                            '</div>' +
                            changeIconSentimentScore(score) +
                            '</br>' +
                            showButtonAddTicketAtSender(dataReversed[i].ticket, messageId) +
                            '</li>'
                        );
                    }

                });
                $('#conversation-content-loading-icon').addClass("hidden");
            }
        });

    }, 500);
}

$(function lazyLoad() {
    var conversation = $('#conversationContent');
    conversation.scroll(function () {
        if (conversation.scrollTop() == 0)
            getConversationBySenderIdWithPage();
    });
});

function sendMessage() {
    var content = $('#replyText').val();
    $.ajax({
        url: '/messenger/sendMessageToCustomer',
        type: "POST",
        data: {
            pageId: currentPageId,
            receiverId: currentCustomer,
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
            $('#currentSenderName').text(currentCustomerName);
            $("#customer-picture").attr("src", data.picture);
            $('#customer-facebook-id').text(currentCustomerName);
            $('#customer-facebook-id').attr('href', 'https://fb.com/' + getCustomerProfileId());
            $('#customer-name').text(data.name);
            $('#customer-timezone').text(convertNumberToTimezone(data.timezone));
            $('#customer-gender').text(convertGender(data.gender));
            $('#customer-locale').text(convertLocale(data.locale));
            // $('#customer-note').text(data.note);
            getCustomerProfileId();
        }
    });

}

function getCustomerProfileId() {
    var string = $('#customer-picture').attr('src');

    var objectID = string.substring(string.lastIndexOf('/')) + 1;
    var rs = objectID.split("_");
    return (rs[1]);
}

function createTicket(ticketId, messageId) {
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
            $('#ticket-modal').modal('toggle');
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

// function showTicket(a) {
//     var messageId = a.id;
//     currentMessageId = a.id;
//     $('#ticket-list').empty();
//     $.ajax({
//         url: '/getallticket',
//         type: "GET",
//         dataType: "json",
//         success: function (data) {
//             $.each(data, function (index) {
//                 var statusColor = 'solved';
//                 if (data[index].statusid == 3) statusColor = 'process';
//                 else if (data[index].statusid == 2) statusColor = 'assigned';
//
//
//                 $('#ticket-list').append(
//                     '<div id="' + data[index].id + '" class="ticket" onclick="createTicket(' + data[index].id + ', \'' + messageId + '\')">'
//                     + '<div class="title ' + statusColor + '">' + data[index].name + '</div>'
//                     + '<div>Status:&nbsp;'
//                     + '<span class="fa fa-circle"></span>&nbsp;'
//                     + data[index].currentstatus
//                     + '</div>'
//                     + '<div>Created by:&nbsp;<span style="color: black; font-weight: bold">'
//                     + data[index].createbyuser
//                     + '</span></div>'
//                     + '<div>Current assignee:&nbsp;<span style="color: black; font-weight: bold">' + data[index].assigneeuser + '</span>'
//                     + '</div>'
//                     + '<div></div>'
//                     + '</div>'
//                 )
//             })
//         }
//     });
//     $('#ticket-modal').modal('toggle');
// }

function showTicket(a) {
    $('#ticket-list').empty();
    var messageId = a.id;
    currentMessageId = a.id;
    $.ajax({
        url: '/getallticket',
        type: "GET",
        dataType: "json",
        success: function (data) {
            $.each(data, function (index) {
                var statusColor=getstatuscolor(data[index].statusid);

                $('#ticket-list').append(
                    '<div class="ticket" onclick="createTicket('+data[index].id+',\''+currentMessageId+'\')">'
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


function searchConversation() {
    // clearInterval(currentInterval);
    clearInterval(loadConversationInterval);
    $('#conversationContent').empty();
    var txtSearch = $('#txtSearchConversation').val();
    $.ajax({
        url: '/messenger/getAllConversationsByPageIdAndSenderName',
        type: "POST",
        data: {
            pageId: currentPageId,
            senderName: txtSearch
        },
        dataType: "json",
        success: function (data) {
            $('#messagesList').empty();
            $('#conversation-content-loading-icon').addClass("hidden");
            $.each(data, function (i) {
                if (data[i].read) {
                    $('#messagesList').append(
                        '<div class="item" style="position: relative" id="conversation' + i + '" onclick="getConversationBySenderId(' + currentPageId + ',\'' + data[i].senderId + '\')">'
                        + '<div>' +
                        '<img class="senderAvt" src="' + data[i].senderPicture + '"><div class="conversation-sender-name pull-left">' + data[i].senderName + '</div>' +
                        '<div class="sentTime text-muted pull-right"> ' + $.format.date(data[i].sentTime, "HH:mm") + '</div>' +
                        '</div>'
                        + '<div class="crop">' + data[i].lastMessage + '</div>'
                        + '</div>'
                    )
                } else {
                    $('#messagesList').append(
                        '<div class="item" style="position: relative" id="conversation' + i + '" onclick="getConversationBySenderId(' + currentPageId + ',\'' + data[i].senderId + '\')">'
                        + '<div><img class="senderAvt" src="' + data[i].senderPicture + '"><div class="conversation-sender-name text-danger pull-left unread-conversation"><b>' + data[i].senderName + '</b></div>' +
                        '<div class="sentTime text-danger pull-right"> ' + $.format.date(data[i].sentTime, "HH:mm") + '</div>' +
                        '</div>'
                        + '<div class="crop text-danger unread-conversation"><b>' + data[i].lastMessage + '</b></div>'
                        + '</div>'
                    )
                }

            });
            $('#conversation0').click();
        }
    });
}

function searchConversationBySenderId(senderId) {
    // clearInterval(currentInterval);
    clearInterval(loadConversationInterval);
    currentPageAvt = 'https://graph.facebook.com/' + currentPageId + '/picture';
    $('#conversationContent').empty();
    $.ajax({
        url: '/messenger/getAllConversationsByPageIdAndSenderId',
        type: "POST",
        data: {
            pageId: currentPageId,
            senderId: senderId
        },
        dataType: "json",
        success: function (data) {
            $('#messagesList').empty();
            $('#conversation-content-loading-icon').addClass("hidden");
            $.each(data, function (i) {
                if (data[i].read) {
                    $('#messagesList').append(
                        '<div class="item" style="position: relative" id="conversation' + i + '" onclick="getConversationBySenderId(' + currentPageId + ',\'' + data[i].senderId + '\')">'
                        + '<div>' +
                        '<img class="senderAvt" src="' + data[i].senderPicture + '"><div class="conversation-sender-name pull-left">' + data[i].senderName + '</div>' +
                        '<div class="sentTime text-muted pull-right"> ' + $.format.date(data[i].sentTime, "HH:mm") + '</div>' +
                        '</div>'
                        + '<div class="crop">' + data[i].lastMessage + '</div>'
                        + '</div>'
                    )
                } else {
                    $('#messagesList').append(
                        '<div class="item" style="position: relative" id="conversation' + i + '" onclick="getConversationBySenderId(' + currentPageId + ',\'' + data[i].senderId + '\')">'
                        + '<div><img class="senderAvt" src="' + data[i].senderPicture + '"><div class="conversation-sender-name text-danger pull-left unread-conversation"><b>' + data[i].senderName + '</b></div>' +
                        '<div class="sentTime text-danger pull-right"> ' + $.format.date(data[i].sentTime, "HH:mm") + '</div>' +
                        '</div>'
                        + '<div class="crop text-danger unread-conversation"><b>' + data[i].lastMessage + '</b></div>'
                        + '</div>'
                    )
                }

            });
            $('#conversation0').click();
        }
    });
}

function refreshConversations() {
    isFirstLoadPage = true;
    getAllConversationsByPageId(currentPageId);
}

function convertNumberToTimezone(number) {
    var result = number;
    number = parseInt(number);
    switch (number) {
        case 0:
            result = 'Greenwich Mean Time: Dublin, Edinburgh, Lisbon, London';
            break;
        case 1:
            result = 'Belgrade, Bratislava, Budapest, Ljubljana, Prague';
            break;
        case 2:
            result = 'Helsinki, Kiev, Riga, Sofia, Tallinn, Vilnius';
            break;
        case 3:
            result = 'Moscow, St. Petersburg, Volgograd';
            break;
        case 4:
            result = 'Baku, Tbilisi, Yerevan';
            break;
        case 5:
            result = 'Islamabad, Karachi, Tashkent';
            break;
        case 6:
            result = 'Astana, Dhaka';
            break;
        case 7:
            result = 'Bangkok, Hanoi, Jakarta';
            break;
        case 8:
            result = 'Kuala Lumpur, Singapore';
            break;
        case 9:
            result = 'Osaka, Sapporo, Tokyo';
            break;
        case 10:
            result = 'Canberra, Melbourne, Sydney';
            break;
        case 11:
            result = 'Magadan, Solomon Islands, New Caledonia';
            break;
        case 12:
            result = 'Fiji Islands, Kamchatka, Marshall Islands';
            break;
        case 13:
            result = "Nuku'alofa";
            break;
        case -1:
            result = 'Cape Verde Islands';
            break;
        case -2:
            result = 'Mid-Atlantic';
            break;
        case -3:
            result = 'Greenland';
            break;
        case -4:
            result = 'Atlantic Time (Canada)';
            break;
        case -5:
            result = 'Eastern Time (US and Canada)';
            break;
        case -6:
            result = 'Central Time (US and Canada)';
            break;
        case -7:
            result = 'Chihuahua, La Paz, Mazatlan';
            break;
        case -8:
            result = 'Pacific Time (US and Canada); Tijuana';
            break;
        case -9:
            result = 'Alaska';
            break;
        case -10:
            result = 'Hawaii';
            break;
        case -11:
            result = 'Midway Island, Samoa';
            break;
        case -12:
            result = 'International Date Line West';
            break;

    }
    return result;
}

function convertLocale(locale) {
    var result = locale;
    switch (locale) {
        case 'en_US':
            result = 'English - United States';
            break;
        case 'vi_VN':
            result = 'Vietnamese - Việt Nam';
            break;
        case 'en_GB':
            result = 'English - Global';
            break;
    }
    return result;
}

function convertGender(gender) {
    var result = 'Female';
    if (gender == 'male') {
        result = 'Male'
    }
    return result;
}

function showForm(a) {
    $("#testbtn").css("display", "block");
}

function hideForm(a) {
    $("#testbtn").css("display", "none");
}

function checkMessageTicket(messageId) {

    $.ajax({
        url: '/messenger/getTicketByMessage',
        type: "POST",
        async: false,
        data: {
            messageId: messageId
        },
        dataType: "json",
        success: function (data) {
            // $('#ticket-name').empty();
            // if (data.length > 0) {
            //     $.each(data, function (index) {
            //         if (index > 0) {
            //             $('#ticket-name').append(", ");
            //         }
            //         $('#ticket-name').append(data[index].name);
            //         $('#ticket-name-link').attr("href" , "/followticket?ticketid=" + data[index].id) ;
            //         $('#ticket-name-link').attr("target" , "_blank") ;
            //         $('#ticket-assigner').html(data[index].createbyuser);
            //         $('#ticket-assignee').html(data[index].assigneeuser);
            //
            //     });
            // } else {
            //     $('#ticket-name').append("This message is not belong to any ticket");
            //     $('#ticket-name-link').attr("href" , "#") ;
            //     $('#ticket-name-link').attr("target" , "") ;
            //     $('#ticket-assigner').html("");
            //     $('#ticket-assignee').html("");
            // }
            $.each(data, function (index){
                var ticketLink = window.open('/followticket?ticketid=' + data[index].id, '_blank');
                if (ticketLink) {
                    //Browser has allowed it to be opened
                    ticketLink.focus();
                } else {
                    //Browser has blocked it
                    alert('Please allow popups for this website');
                }
            });



        },
        error: function (error) {
            alert("Some error happened");
        }
    });
}

function showButtonAddTicketAtSender(isTicket, messageId) {
    var result = '';
    switch (isTicket) {
        case true:
            result = '<span style="cursor:pointer; margin-left: 7px;opacity: 0.3"  id="' + messageId + '" onclick="checkMessageTicket(messageId)" class="fa fa-ticket fa-2x pull-left"></span>';
            break;
        case false:
            result = '<span style="cursor:pointer; margin-left: 7px;opacity: 0.3"  id="' + messageId + '" onclick="showTicket(this)" class="fa fa-plus-square-o fa-2x pull-left"></span>';
            break
    }
    return result;
}

function showButtonAddTicketAtPage(a,b) {
    var result = '';
    switch (a) {
        case true:
            result = '<span style="cursor:pointer; margin-right: 12px;opacity: 0.3" id="' + b + '" onclick="checkMessageTicket(b)" class="fa fa-ticket fa-2x pull-right" title="Show ticket"></span>';
            break;
        case false:
            result = '<span style="cursor:pointer; margin-right: 12px;opacity: 0.3" id="' + b + '" onclick="showTicket(this)" class="fa fa-plus-square-o fa-2x pull-right" title="Add ticket"></span>';
            break
    }
    return result;
}

function showCustomerInfo(){
    $('#customer-button').addClass("active");
    $('#ticket-button').removeClass("active");
    $('#ticket-info-tab').hide();
    $('#customer-info-tab').show();
}

function showTicketInfo() {
    $('#ticket-button').addClass("active");
    $('#customer-button').removeClass("active");
    $('#customer-info-tab').hide();
    $('#ticket-info-tab').show();
}

/*Ticket Filter*/
function sortticketbytime(currentCmt) {
    sortticket("/sortbytime",currentCmt)
}
function sortticketbystatus() {
    sortticket("/sortbystatus",currentCmt)
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
        sortticketbytime(currentMessageId)
    }else{
        showallticket()
    }

});
$(document).on('change', '#tksttcheckbox', function(){
    if (this.checked) {
        sortticketbystatus(currentMessageId)
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
                    '<div class="ticket" onclick="createTicket('+data[index].id+',\''+currentMessageId+'\')">'
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
function sortticket(url,currentMessageId) {
    $.ajax({
        url:url,
        type:"GET",
        success:function (data) {
            $('#ticket-list').empty();
            $.each(data, function (index) {
                var statusColor=getstatuscolor(data[index].statusid);

                $('#ticket-list').append(
                    '<div class="ticket" onclick="createTicket('+data[index].id+',\''+currentMessageId+'\')">'
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
/*End Ticket Filter*/
