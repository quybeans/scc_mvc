/**
 * Created by Thien on 11/13/2016.
 */

var currentPageId = '0';
var currentPageAvt = '';
$(document).ready(function () {
    getAllActivePageUnreadMessage();
});

// $('#mess-page-list').addClass('collapse'); $('#chat-box').addClass('collapse')
function getAllActivePageUnreadMessage() {
    currentPageId = '0';
    currentPageAvt = '';
    $('#back-button').addClass('hidden');
    $.ajax({
        url: '/messenger/getAllActivePageUnreadMessage',
        type: "GET",
        dataType: "json",
        success: function (data) {
            $('#page-conversations').empty();
            $.each(data, function (i) {
                if (data[i].unreadMessage != 0) {
                    $('#page-conversations').append(
                        '<div class="page" onclick="loadConversationsByPage(' + data[i].pageid + ')">' +
                        '<img src="https://graph.facebook.com/' + data[i].pageid + '/picture">' +
                        '<div class="name">' + data[i].name + '</div>' +
                        '<div class="unread-count">' +
                        '<div class="circle">' + data[i].unreadMessage + '</div>' +
                        '<b>unread message</b>' +
                        '</div>' +
                        ' </div>'
                    )
                } else {
                    $('#page-conversations').append(
                        '<div class="page" onclick="loadConversationsByPage(' + data[i].pageid + ')">' +
                        '<img src="https://graph.facebook.com/' + data[i].pageid + '/picture">' +
                        '<div class="name">' + data[i].name + '</div>' +
                        '<div class="unread-count">' +
                        // '<div class="circle">' + data[i].unreadMessage + '</div>' +
                        'All messages are read' +
                        '</div>' +
                        ' </div>'
                    )
                }

            });
            $('#mess-page-list').removeClass('collapse');
            $('#chat-box').addClass('collapse');
        }
    });
}

function loadConversationsByPage(pageId) {
    currentPageId = pageId;
    currentPageAvt = 'https://graph.facebook.com/' + pageId + '/picture';
    $('#back-button').removeClass('hidden');
    $.ajax({
        url: '/messenger/getAllConversationsByPageId',
        type: "GET",
        data: {
            pageId: pageId
        },
        dataType: "json",
        success: function (data) {
            $('#chat-box').empty();
            $.each(data, function (i) {
                if (data[i].read) {
                    $('#chat-box').append(
                        '<div class="conversation" style="position: relative" id="conversation' + i + '" onclick="register_popup(\'' + data[i].senderId + '\', \'' +  data[i].senderName + '\')">'
                        + '<div>' +
                        '<img class="senderAvt" src="' + data[i].senderPicture + '"><div class="conversation-sender-name pull-left"><a>' + data[i].senderName + '</a></div>' +
                        '<div class="sentTime text-muted pull-right"> ' + $.format.date(data[i].sentTime, "HH:mm") + '</div>' +
                        '<div class="conversation-lastmessage pull-left crop">' + data[i].lastMessage + '</div>' +
                        '</div>' +
                        '</div>'
                    )
                } else {
                    $('#chat-box').append(
                        '<div class="conversation" style="position: relative" id="conversation' + i + '" onclick="register_popup(\'' + data[i].senderId + '\', \'' +  data[i].senderName + '\')">'
                        + '<div>' +
                        '<img class="senderAvt" src="' + data[i].senderPicture + '"><div class="conversation-sender-name pull-left"><a><b>' + data[i].senderName + '</b></a></div>' +
                        '<div class="sentTime text-muted pull-right"><b> ' + $.format.date(data[i].sentTime, "HH:mm") + '</b></div>' +
                        '<div class="conversation-lastmessage pull-left crop"><b>' + data[i].lastMessage + '</b></div>' +
                        '</div>' +
                        '</div>'
                    )
                }

            });
            $('#mess-page-list').addClass('collapse');
            $('#chat-box').removeClass('collapse');
        }
    });
}


/**
 * Created by Thien on 11/15/2016.
 */
//this function can remove a array element.
Array.remove = function (array, from, to) {
    var rest = array.slice((to || from) + 1 || array.length);
    array.length = from < 0 ? array.length + from : from;
    return array.push.apply(array, rest);
};

//this variable represents the total number of popups can be displayed according to the viewport width
var total_popups = 0;

//arrays of popups ids
var popups = [];

//this is used to close a popup
function close_popup(id) {
    for (var iii = 0; iii < popups.length; iii++) {
        if (id == popups[iii]) {
            Array.remove(popups, iii);

            document.getElementById(id).style.display = "none";

            calculate_popups();

            return;
        }
    }
}

//displays the popups. Displays based on the maximum number of popups that can be displayed on the current viewport width
function display_popups() {
    var right = 220;

    var iii = 0;
    for (iii; iii < total_popups; iii++) {
        if (popups[iii] != undefined) {
            var element = document.getElementById(popups[iii]);
            element.style.right = right + "px";
            right = right + 320;
            element.style.display = "block";
        }
    }

    for (var jjj = iii; jjj < popups.length; jjj++) {
        var element = document.getElementById(popups[jjj]);
        element.style.display = "none";
    }
}

//creates markup for a new popup. Adds the id to popups array.
function register_popup(id, name) {

    for (var iii = 0; iii < popups.length; iii++) {
        //already registered. Bring it to front.
        if (id == popups[iii]) {
            Array.remove(popups, iii);

            popups.unshift(id);

            calculate_popups();


            return;
        }
    }

    var element = '<div class="popup-box chat-popup" id="' + id + '">';
    element = element + '<div class="popup-head">';
    element = element + '<div class="popup-head-left">' + name + '</div>';
    element = element + '<div class="popup-head-right"><a href="javascript:close_popup(\'' + id + '\');">&#10005;</a></div>';
    element = element + '<div style="clear: both"></div></div><div class="popup-messages"></div></div>';



    $.ajax({
        url: '/messenger/getConversationBySenderIdWithPage',
        type: "POST",
        data: {
            pageId: currentPageId,
            senderId: id,
            pageNum: 1
        },
        dataType: "json",
        success: function (data) {
            var chatMessage = '';
            var dataReversed = data.reverse();
            var a;
            $.each(dataReversed, function (i) {
                a = dataReversed[i].id;
                score = dataReversed[i].sentimentScrore;
                if (dataReversed[i].senderid == currentPageId) {
                    chatMessage = chatMessage.concat(
                        '<li class="right clearfix"><span class="chat-img pull-right">' +
                        ' <img src="' + currentPageAvt + '" alt="User Avatar"/>' +
                        ' </span>' +
                        '<div class="chat-body clearfix pull-right">' +
                        ' <div class="header">' +
                        '<small class=" text-muted ">' +
                        $.format.date(dataReversed[i].createdAt, "HH:mm") +
                        '</small>' +
                        '<strong class="pull-right primary-font">currentPageName</strong>' +
                        '</div>' +
                        '<p style="text-align: right">' +
                        dataReversed[i].content +
                        '</p>' +
                        '</div>' +
                        '</li>'
                    )
                    ;
                } else {
                    chatMessage = chatMessage.concat(
                        '<li class="left clearfix"><span class="chat-img pull-left"> ' +
                        '<img src="currentCustomerAvt"/> </span>' +
                        '<div class="chat-body clearfix pull-left">' +
                        '<div class="header">' +
                        '<strong class="primary-font">' + name + '</strong>' +
                        '<small class="pull-right text-muted">' +
                        $.format.date(dataReversed[i].createdAt, "HH:mm") +
                        '</small>' +
                        '</div>' +
                        '<p>' +
                        dataReversed[i].content +
                        '</p>' +
                        '</div>' +
                        '</li>'
                    );
                }

            });

            var element3 = '<div class="popup-box chat-popup" id="' + id + '">' +
                '<div class="popup-head">' +
                '<div class="popup-head-left">' + name + '</div>' +
                '<div class="popup-head-right"><a href="javascript:close_popup(\'' + id + '\');">&#10005;</a></div>' +
                '<div style="clear: both"></div></div><div style="height: 220px" class="popup-messages">' +
                '<ul class="chat">' +
                chatMessage +
                '</ul>' +
                '</div></div>';
            $('body').append(element3);
        }
    });

    popups.unshift(id);

    calculate_popups();

}


//calculate the total number of popups suitable and then populate the toatal_popups variable.
function calculate_popups() {
    var width = window.innerWidth;
    if (width < 540) {
        total_popups = 0;
    }
    else {
        width = width - 200;
        //320 is width of a single popup box
        total_popups = parseInt(width / 320);
    }

    display_popups();

}

//recalculate when window is loaded and also when window is resized.
window.addEventListener("resize", calculate_popups);
window.addEventListener("load", calculate_popups);


function firstLoadConversationBySenderId(senderId, currentCustomerName) {
    var result = '';
    $.ajax({
        url: '/messenger/getConversationBySenderIdWithPage',
        async: false,
        type: "POST",
        data: {
            pageId: currentPageId,
            senderId: senderId,
            pageNum: 1
        },
        dataType: "json",
        success: function (data) {

            var dataReversed = data.reverse();
            var a;
            $.each(dataReversed, function (i) {
                a = dataReversed[i].id;
                score = dataReversed[i].sentimentScrore;
                if (dataReversed[i].senderid == currentPageId) {
                    result = result.concat(
                        '<li class="right clearfix"><span class="chat-img pull-right">' +
                        ' <img src="' + currentPageAvt + '" alt="User Avatar"/>' +
                        ' </span>' +
                        '<div class="chat-body clearfix pull-right">' +
                        ' <div class="header">' +
                        '<small class=" text-muted ">' +
                        $.format.date(dataReversed[i].createdAt, "HH:mm") +
                        '</small>' +
                        '<strong class="pull-right primary-font">' + currentPageName + '</strong>' +
                        '</div>' +
                        '<p style="text-align: right">' +
                        dataReversed[i].content +
                        '</p>' +
                        '</div>' +
                        '<span class="pull-right">' +
                        '?' +
                        '</br>' +
                        '<span style="margin-right: 12px;opacity: 0.3" id="' + a + '" class="fa fa-plus-square-o fa-2x pull-right"></span>' +
                        '</span>' +
                        '</li>'
                    )
                    ;
                } else {
                    result =   result.concat(
                        '<li class="left clearfix"><span class="chat-img pull-left"> ' +
                        '<img src="currentCustomerAvt"/> </span>' +
                        '<div class="chat-body clearfix pull-left">' +
                        '<div class="header">' +
                        '<strong class="primary-font">' + currentCustomerName + '</strong>' +
                        '<small class="pull-right text-muted">' +
                        $.format.date(dataReversed[i].createdAt, "HH:mm") +
                        '</small>' +
                        '</div>' +
                        '<p>' +
                        dataReversed[i].content +
                        '</p>' +
                        '</div>' +
                        '?' +
                        '</br>' +
                        '<span style="margin-left: 7px;opacity: 0.3"  id="' + a + '"class="fa fa-plus-square-o fa-2x pull-left"></span>' +
                        '</li>'
                    );
                }

            });
            return result;
        }
    });

    return result;
}