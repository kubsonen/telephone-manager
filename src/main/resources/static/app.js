$( "#changeRowsOnPage" ).change(function() { this.submit(); });
$( "#changeShowMode" ).change(function() { this.submit(); });
function nextPage(p){
    $( "#actualPageFormField" ).val(p);
    $( "#changeRowsOnPage" ).submit();
}

var chatShow = 'ctrue';
var chatHide = 'cfalse';

//Chat logic
//On page loading
$( document ).ready(function() {
    if($( "#visibleChat" ).val() === chatShow){
        $( ".box-chat-container" ).removeClass( "box-chat-hide" )
    } else {
        $( ".box-chat-container" ).addClass( "box-chat-hide" )
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


