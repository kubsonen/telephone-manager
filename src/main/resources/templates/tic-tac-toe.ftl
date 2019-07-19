<html>
<head>
    <link rel="stylesheet" href="/tic-tac-toe.css">
</head>
<body>

<div class="game-content">

    <div class="game-control">
        <div class="game-button">
            <button>Game history</button>
        </div>
        <div class="game-button">
            <button id="playerXButton" onclick="choosePlayer('X')">Player X - join</button>
        </div>
        <div class="game-button">
            <button id="playerOButton" onclick="choosePlayer('O')">Player O - join</button>
        </div>
    </div>

    <div class="game-board">
        <table>
            <tr>
                <td id="moveLT" onclick="performGameMove('LT')"></td>
                <td id="moveCT" onclick="performGameMove('CT')"></td>
                <td id="moveRT" onclick="performGameMove('RT')"></td>
            </tr>
            <tr>
                <td id="moveLC" onclick="performGameMove('LC')"></td>
                <td id="moveCC" onclick="performGameMove('CC')"></td>
                <td id="moveRC" onclick="performGameMove('RC')"></td>
            </tr>
            <tr>
                <td id="moveLB" onclick="performGameMove('LB')"></td>
                <td id="moveCB" onclick="performGameMove('CB')"></td>
                <td id="moveRB" onclick="performGameMove('RB')"></td>
            </tr>
        </table>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"></script>
<script src="/tic-tac-toe.js"></script>
</body>
</html>