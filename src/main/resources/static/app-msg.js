$( "#changeRowsOnPage" ).change(function() { this.submit(); });
$( "#changeShowMode" ).change(function() { this.submit(); });
function nextPage(p){
    $( "#actualPageFormField" ).val(p);
    $( "#changeRowsOnPage" ).submit();
}

function scrollDownChat() {
    $('#messages-box').scrollTop($('#messages-box')[0].scrollHeight);
}

//Checking that the tab is active
var originalTittle = document.title;
var focused = true;
$(function() {
    $(window).focus(function() {
        focused = true;
        document.title = originalTittle;
    }).blur(function() {
        focused = false;
    });
});

function addMessageToBox(data) {
    if(Array.isArray(data)){
        data.forEach(function (msg) {
            //Add to the messages box
            $('#messages-box').append(constructMsg(msg));
        });
    } else {
        $('#messages-box').append(constructMsg(data));
    }
}

function constructMsg(msg) {

    var username = 'undefined';
    if( msg.sender !== undefined && msg.sender !== null &&
        msg.sender.username !== undefined && msg.sender.username !== null){
        username = msg.sender.username;
    }

    var date = 'undefined';
    if(msg.messageDate !== null){
        date = msg.messageDate;
        date = moment(date).format('YYYY-MM-DD hh:mm:ss');
    }

    var message =
        '<div class="card w-100 mb-2 chat-message">' +
            '<div class="card-body">' +
                '<h6 class="card-subtitle mb-1">' + username + '</h6>' +
                '<p class="card-text">' +
                    msg.messageContent +
                '</p>' +
                '<div class="float-right"><small>' + date + '</small></div>' +
            '</div>' +
        '</div>'

    return message;

}

//Connect after last message loading
function loadLastMessages(){
    $.ajax({
        url: "/chat/lastMessages",
        method: "POST",
        contentType: "application/json",
        success: function(data){
            addMessageToBox(data);
            scrollDownChat();
            connect();
        }
    });
}

var stompClient = null;
var audio = new Audio('/alert.ogg');

//Connect to the chat
function connect(){
    var socket = new SockJS('/tm-chat-ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/receiveMessage', function (msg) {
            var msgBody = msg.body;
            if(msgBody !== null){
                var object = JSON.parse(msgBody);
                addMessageToBox(object);
                if(!focused){
                    var s = '';
                    if(object.sender !== null){
                        s = object.sender.username;
                    }
                    document.title =  s + ' text you';
                }
                audio.play();
                scrollDownChat();
            }
        });
    });
}

//Chat disconnect
function disconnect(){
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

function unloadMessage() {
   disconnect();
}

function sendMessage(message) {
    stompClient.send("/chat/sendMessage", {},
        JSON.stringify({'messageContent': message}));
}

var chatShow = 'ctrue';
var chatHide = 'cfalse';

//Chat logic
//On page loading
$( document ).ready(function() {
    window.onbeforeunload = unloadMessage;
    if($( "#visibleChat" ).val() === chatShow){
        $( ".box-chat-container" ).removeClass( "box-chat-hide" )
        loadLastMessages();
    } else {
        $( ".box-chat-container" ).addClass( "box-chat-hide" )
        disconnect();
    }
});

//Hide chat window
$( "#chat-box-close-icon" ).click(function(){
    $( "#visibleChat" ).val(chatHide);
    $( "#changeRowsOnPage" ).submit();
});

//Show chat window
$( ".show-chat-btn" ).click(function(){
    $( "#visibleChat" ).val(chatShow);
    $( "#changeRowsOnPage" ).submit();
});

//On enter key
$("#message-content").on('keyup', function (e) {
    if (e.keyCode == 13) {
        var m = $(this).val();
        $(this).val('');
        sendMessage(m);
    }
});

//Send message action
$( "#send-button" ).click(function () {
    var m = $("#message-content").val();
    console.log(m);
    $("#message-content").val('');
    sendMessage(m);
});
