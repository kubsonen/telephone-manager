var stompClient = null;

$( document ).ready(function() {
    console.log( "ready!" );
    connectToWs();
});

function connectToWs() {
    var socket = new SockJS('/tic-tac-toe-ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/tic-tac-toe-topic/updateGameContent', function(gameContent){
                updateGameContent(JSON.parse(gameContent.body));
            });
            stompClient.subscribe('/tic-tac-toe-topic/finishGame', function(){
                finishGame();
            });
            stompClient.subscribe('/tic-tac-toe-topic/updatePlayerState', function(playerState){
                updatePlayerState(JSON.parse(playerState.body));
            });
        });
}

function finishGame() {
    $('#playerXButton').html('Player X - join');
    $('#playerOButton').html('Player O - join');
}

function updateGameContent(content) {
    console.log(content);
    if (content.gameLayout){
        var gl = content.gameLayout;
        if(gl.LT) {$('#moveLT').html(gl.LT);} else {$('#moveLT').html('');}
        if(gl.CT) {$('#moveCT').html(gl.CT);} else {$('#moveCT').html('');}
        if(gl.RT) {$('#moveRT').html(gl.RT);} else {$('#moveRT').html('');}
        if(gl.LC) {$('#moveLC').html(gl.LC);} else {$('#moveLC').html('');}
        if(gl.CC) {$('#moveCC').html(gl.CC);} else {$('#moveCC').html('');}
        if(gl.RC) {$('#moveRC').html(gl.RC);} else {$('#moveRC').html('');}
        if(gl.LB) {$('#moveLB').html(gl.LB);} else {$('#moveLB').html('');}
        if(gl.CB) {$('#moveCB').html(gl.CB);} else {$('#moveCB').html('');}
        if(gl.RB) {$('#moveRB').html(gl.RB);} else {$('#moveRB').html('');}
    }
}

function updatePlayerState(stateObject) {
    if (stateObject) {

        var playerXButtonContent = '';
        playerXButtonContent += "Player X <br> Nickname: " + stateObject.playerXName + '<br>';
        playerXButtonContent += " Time remaining: " + stateObject.playerXMinuteRemaining + ':' + stateObject.playerXSecondsRemaining;


        $('#playerXButton').html(playerXButtonContent);

        var playerOButtonContent = '';
        playerOButtonContent += "Player O <br> Nickname: " + stateObject.playerOName + '<br>';
        playerOButtonContent += " Time remaining: " + stateObject.playerOMinuteRemaining + ':' + stateObject.playerOSecondsRemaining;

        $('#playerOButton').html(playerOButtonContent);

    }
}

function performGameMove(move){
    stompClient.send("/tic-tac-toe-game/move", {}, JSON.stringify({move}));
}

function choosePlayer(figure){
    stompClient.send("/tic-tac-toe-game/choosePlayer", {}, JSON.stringify({figure}));
}