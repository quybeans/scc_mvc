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

    var element2 =
        '<div class="popup-box chat-popup" id="' + id + '">' +
        '<div class="popup-head">' +
        '<div class="popup-head-left">' + name + '</div>' +
        '<div class="popup-head-right"><a href="javascript:close_popup(\'' + id + '\');">&#10005;</a></div>' +
        '<div style="clear: both"></div></div><div class="popup-messages">' +
        '<div class="popup-message">' +
        '<div class="div-popup-sender-avt" ><img class="popup-sender-avt" src="https://scontent.xx.fbcdn.net/v/t1.0-1/13886456_686981624787425_1934763867833077960_n.jpg?oh=f1d6c4d676eda9d4844d1fb383cf62fe&oe=58C04AC6"></div>' +
        '<div class="popup-sender-message"><span>Message content</span></div>' +
        '</div>' +
        '<div class="popup-message">' +
        '<div class="div-popup-sender-avt" ><img class="popup-sender-avt" src="https://scontent.xx.fbcdn.net/v/t1.0-1/13886456_686981624787425_1934763867833077960_n.jpg?oh=f1d6c4d676eda9d4844d1fb383cf62fe&oe=58C04AC6"></div>' +
        '<div class="popup-sender-message"><span>Message content</span></div>' +
        '</div>' +
        '<div class="popup-message">' +
        '<div class="div-popup-sender-avt" ><img class="popup-sender-avt" src="https://scontent.xx.fbcdn.net/v/t1.0-1/13886456_686981624787425_1934763867833077960_n.jpg?oh=f1d6c4d676eda9d4844d1fb383cf62fe&oe=58C04AC6"></div>' +
        '<div class="popup-sender-message"><span>Message content</span></div>' +
        '</div>' +
        '<div class="popup-message">' +
        '<div class="div-popup-sender-avt pull-right" ><img class="popup-sender-avt" src="https://scontent.xx.fbcdn.net/v/t1.0-1/13886456_686981624787425_1934763867833077960_n.jpg?oh=f1d6c4d676eda9d4844d1fb383cf62fe&oe=58C04AC6"></div>' +
        '<div class="popup-sender-message pull-right"><span>Message content</span></div>' +
        '</div>' +
        '</div></div>';


    var chatMessage = '';

    var element3 = '<div class="popup-box chat-popup" id="' + id + '">' +
        '<div class="popup-head">' +
        '<div class="popup-head-left">' + name + '</div>' +
        '<div class="popup-head-right"><a href="javascript:close_popup(\'' + id + '\');">&#10005;</a></div>' +
        '<div style="clear: both"></div></div><div class="popup-messages">' +
        '<ul class="chat">' +
        chatMessage +
        '</ul>' +
        '</div></div>';


    $('body').append(element3);

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


function firstLoadConversationBySenderId() {
    var result = '';
    $.ajax({
        url: '/messenger/getConversationBySenderIdWithPage',
        async: false,
        type: "POST",
        data: {
            pageId: currentPageId,
            senderId: currentCustomer,
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
                    result.concat(
                        '<li class="right clearfix"><span class="chat-img pull-right">' +
                        ' <img src="' + currentPageAvt + '" alt="User Avatar"/>' +
                        ' </span>' +
                        '<div class="chat-body clearfix pull-right">' +
                        ' <div class="header">' +
                        '<small class=" text-muted ">' +
                        // moment(dataReversed[i].createdAt).fromNow() +
                        $.format.date(dataReversed[i].createdAt, "HH:mm") +
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
                        '<span style="margin-right: 12px;opacity: 0.3" id="' + a + '" onclick="showTicket(this)" class="fa fa-plus-square-o fa-2x pull-right"></span>' +
                        '</span>' +
                        '</li>'
                    )
                    ;
                } else {
                    result.concat(
                        '<li class="left clearfix"><span class="chat-img pull-left"> ' +
                        '<img src="' + currentCustomerAvt + '"/> </span>' +
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
                        changeIconSentimentScore(score) +
                        '</br>' +
                        '<span style="margin-left: 7px;opacity: 0.3"  id="' + a + '" onclick="showTicket(this)" class="fa fa-plus-square-o fa-2x pull-left"></span>' +
                        '</li>'
                    );
                }

            });
        }
    });
}