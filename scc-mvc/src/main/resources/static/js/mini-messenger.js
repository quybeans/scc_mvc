/**
 * Created by Thien on 11/13/2016.
 */

$( document ).ready(function() {
    getAllActivePageUnreadMessage();
});

// $('#mess-page-list').addClass('collapse'); $('#chat-box').addClass('collapse')
function getAllActivePageUnreadMessage() {

    $.ajax({
        url: '/messenger/getAllActivePageUnreadMessage',
        type: "GET",
        dataType: "json",
        success: function (data) {
            $('#page-conversations').empty();
            $.each(data, function (i) {
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
            });
            $('#mess-page-list').removeClass('collapse');
            $('#chat-box').addClass('collapse');
        }
    });
}

function loadConversationsByPage(pageId) {

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
                        '<div class="conversation" style="position: relative" id="conversation' + i + '">'
                        + '<div>' +
                        '<img class="senderAvt" src="' + data[i].senderPicture + '"><div class="conversation-sender-name pull-left"><a>' + data[i].senderName + '</a></div>' +
                        '<div class="sentTime text-muted pull-right"> ' + $.format.date(data[i].sentTime, "HH:mm") + '</div>' +
                        '<div class="conversation-lastmessage pull-left crop">' + data[i].lastMessage + '</div>' +
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