$( "#changeRowsOnPage" ).change(function() { this.submit(); });
$( "#changeShowMode" ).change(function() { this.submit(); });
function nextPage(p){
    $( "#actualPageFormField" ).val(p);
    $( "#changeRowsOnPage" ).submit();
}

//Connect after last message loading
function loadLastMessages(){
    $.ajax({
        url: "/chat/lastMessages",
        method: "POST",
        contentType: "application/json",
        success: function(data){

        }
    });
}

var stompClient = null;

//Connect to the chat
function connect(){
    var socket = new SockJS('/tm-chat-ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/receiveMessage', function (msg) {

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
