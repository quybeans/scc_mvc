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
                    if (dataReversed[i].senderid == pageId) {
                        $('#conversationContent').append(
                            '<div style="text-align: right;">' +
                            '<h2 style="display: inline-block; background-color: #00a7d0">' + dataReversed[i].content + '</h2>' +
                            '<p>' + moment(dataReversed[i].createdAt).fromNow() + '' +
                            '<button value="' + a + '" onclick="createTicket(this)"><span class="fa fa-plus"></span></button>' +
                            '<button value="' + a + '" onclick="endTicket(this)"><span class="fa fa-check"></span></button>' +
                            '</p>' +
                            '</div>'
                        );
                    } else {
                        $('#conversationContent').append(
                            '<div style="text-align: left;">' +
                            '<h2 style="display: inline-block; background-color: #9d9d9d">' + dataReversed[i].content + '</h2>' +
                            '<p>' + moment(dataReversed[i].createdAt).fromNow() + '' +
                            '<button value="' + a + '" onclick="createTicket(this)"><span class="fa fa-plus"></span></button>' +
                            '<button value="' + a + '" onclick="endTicket(this)"><span class="fa fa-check"></span></button>' +
                            '</p>' +
                            '</div>'
                        );
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
                    if (dataReversed[i].senderid == pageId) {
                        $('#conversationContent').append(
                            '<div style="text-align: right;">' +
                            '<h2 style="display: inline-block; background-color: #00a7d0">' + dataReversed[i].content + '</h2>' +
                            '<p>' + moment(dataReversed[i].createdAt).fromNow() + '' +
                            '<button value="' + a + '" onclick="createTicket(this)"><span class="fa fa-plus"></span></button>' +
                            '<button value="' + a + '" onclick="endTicket(this)"><span class="fa fa-check"></span></button>' +
                            '</p>' +
                            '</div>'
                        );
                    } else {
                        $('#conversationContent').append(
                            '<div style="text-align: left;">' +
                            '<h2 style="display: inline-block; background-color: #9d9d9d">' + dataReversed[i].content + '</h2>' +
                            '<p>' + moment(dataReversed[i].createdAt).fromNow() + '' +
                            '<button value="' + a + '" onclick="createTicket(this)"><span class="fa fa-plus"></span></button>' +
                            '<button value="' + a + '" onclick="endTicket(this)"><span class="fa fa-check"></span></button>' +
                            '</p>' +
                            '</div>'
                        );
                    }

                });
            }
        });

    }, 2000);

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
                    if (dataReversed[i].senderid == currentPageId) {
                        $('#conversationContent').append(
                            '<div style="text-align: right;">' +
                            '<h2 style="display: inline-block; background-color: #00a7d0">' + dataReversed[i].content + '</h2>' +
                            '<p>' + moment(dataReversed[i].createdAt).fromNow() + '' +
                            '<button value="' + a + '" onclick="createTicket(this)"><span class="fa fa-plus"></span></button>' +
                            '<button value="' + a + '" onclick="endTicket(this)"><span class="fa fa-check"></span></button>' +
                            '</p>' +
                            '</div>'
                        );
                    } else {
                        $('#conversationContent').append(
                            '<div style="text-align: left;">' +
                            '<h2 style="display: inline-block; background-color: #9d9d9d;">' + dataReversed[i].content + '</h2>' +
                            '<p>' + moment(dataReversed[i].createdAt).fromNow() + '' +
                            '<button value="' + a + '" onclick="createTicket(this)"><span class="fa fa-plus"></span></button>' +
                            '<button value="' + a + '" onclick="endTicket(this)"><span class="fa fa-check"></span></button>' +
                            '</p>' +
                            '</div>'
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


function createTicket(a) {
    alert(a.value);
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
}

function endTicket(a) {
    alert(a.value);
}