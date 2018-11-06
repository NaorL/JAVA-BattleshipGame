var refreshRate = 1000; //mili seconds
var CHECK_PLAYERS_AMOUNT = "/playersAmount";
var GET_PLAYER_INFORMATION = "/playerInformation";
var MAKE_MOVE = "/makeMove";
var QUIT_GAME = "/quitGame";
var GAME_STATUS = "/gameStatus";
var GAME_STATISTICS = "/gameStatistics";
var GAME_TYPE = "/gameType";
var PLAYER_LEAVE = "/playerLeave";
var PLACE_MINE = "/placeMine"
var gameType;
var LOBY_URL = "/pages/loby/loby.html";
var GAME_CHAT = "/gameChat";
var GET_PLAYERS_NAMES = "/gamePlayersNames";

//activate the timer calls after the page is loaded
$(function() {

    //prevent IE from caching ajax calls
    $.ajaxSetup({cache: false});

    //The games list is refreshed automatically every second
    setTimeout(manageScreens, refreshRate);

    setWaitingScreen();

});

function setWaitingScreen() {
    $("#waitingDiv").show();
    $("#game-screen").hide();
    $("#statistics-screen").hide();
}

function initGameType() {
    $.ajax({
        url: GAME_TYPE,
        error: function () {

        },
        success: function (type) {
            gameType = type;
            if(gameType == "ADVANCE"){
                $(".player-info").append('<div id="mine"><table id="mine-table"></table></div>');
            }
        }
    });
}

function manageScreens() {
    $.ajax({
        url: CHECK_PLAYERS_AMOUNT,
        error: function () {

        },
        success: function (amount) {
            if (amount === 2){
                showGameScreen();
                initGameType();
                displayPlayersNames();
                triggerHandleGameStatus();
            }
            else
                manageScreens();
        }
    });
}

function displayPlayersNames() {
    $.ajax({
        url: GET_PLAYERS_NAMES,
        error: function () {

        },
        success: function (playersNames) {
            displayUsersList(playersNames);
        }
    });
}

function triggerHandleGameStatus(){
    setTimeout(handleGameStatus, refreshRate);
}

function handleGameStatus(){
    $.ajax({
        url: GAME_STATUS,
        error: function () {

        },
        success: function (isGameRunning) {
            if(isGameRunning){
                startAndRefreshGame();
                triggerHandleGameStatus();
            } else{
                gameFinished();
            }
        }
    });
}

function gameFinished(){
    $.ajax({
        url: GAME_STATISTICS,
        error: function () {

        },
        success: function (statistics) {
            displayStatistics(statistics)
        }
    });

    $.ajax({
        url: PLAYER_LEAVE
    });
}

function showGameScreen(){
    $("#waitingDiv").hide();
    $("#game-screen").show();
}

function showStatisticsScreen(){
    $("#game-screen").hide();
    $("#statistics-screen").show();
}


function displayStatistics(statistics) {
    showStatisticsScreen();
    $("#statistics-table").append(
        '<tr><td></td><td id="neon-label">' + statistics.firstPlayerName + '</td><td id="neon-label">' + statistics.secondPlayerName + '</td></tr>' +
        '<tr><td style="text-align: left; color:white">Score</td><td>' + statistics.firstPlayerScore + '</td><td>' + statistics.secondPlayerScore + '</td></tr>' +
        '<tr><td style="text-align: left; color:white"">Hit</td><td>' + statistics.firstPlayerHit + '</td><td>' + statistics.secondPlayerHit + '</td></tr>' +
        '<tr><td style="text-align: left; color:white"">Miss</td><td>' + statistics.firstPlayerMiss + '</td><td>' + statistics.secondPlayerMiss + '</td></tr>' +
        '<tr><td style="text-align: left; color:white"">Average Turn Time</td><td>' + statistics.firstPlayerAverageTimeToAttack + '</td><td>' + statistics.secondPlayerAverageTimeToAttack + '</td></tr>' +
        '<tr><td style="text-align: left; color:white"">Number of Turns</td><td>' + statistics.firstPlayerNumOfTurns + '</td><td>' + statistics.secondPlayerNumOfTurns + '</td></tr>');
    $("#winner-name").append('The winner is: ' + statistics.winnerName);
}


function startAndRefreshGame() {
    $.ajax({
        url: GET_PLAYER_INFORMATION,
        error: function () {

        },
        success: function (playerInformation) {
            drawGameScreen(playerInformation);
        }
    });

    $.ajax({
        url: GAME_CHAT,
        error: function () {

        },
        success: function (chatEntries) {
            appendToChatArea(chatEntries);
        }
    })
}

function drawGameScreen(playerInformation) {
    drawBattleshipBoard(playerInformation.player.battleShipBoard);
    drawTrackingBoard(playerInformation.player.trackingBoard);
    drawPlayersInformation(playerInformation.player);
    drawEnemyInformation(playerInformation.player.enemyShipsList);
    drawCurrentPlayerName(playerInformation.currentPlayerName);
}

function drawBattleshipBoard(battleShipBoard) {
    $("#battleship-board").empty();
    $("#battleship-board").append('<tr><th scope="row" colspan="5" class="div-header">Battleship Board</th></tr>');
    for(var i = 0 ; i < battleShipBoard.boardSize; ++i){
        var obj = $('<tr>');
        for(var j = 0 ; j < battleShipBoard.boardSize; ++j){
            if(battleShipBoard.board[i][j].wasAttackedByPlayer || battleShipBoard.board[i][j].wasAttackedByBackFire)
                $('<td><button class="battleship-button attacked"></button></td>').appendTo(obj);
            else if(battleShipBoard.board[i][j].isBattleShipHere)
                $('<td><button class="battleship-button battleship"></button></td>').appendTo(obj);
            else if(battleShipBoard.board[i][j].isMineHere)
                $('<td><button class="battleship-button mine"></button></td>').appendTo(obj);
            else
                $('<td><button class="battleship-button" ondrop="placeMine(this, event)" ondragover="allowDrop(event)"></button></td>').appendTo(obj);
        }
        $('</tr>').appendTo(obj);
        $(obj).appendTo($("#battleship-board"));
    }
}

function drawTrackingBoard(trackingBoard) {
    $("#tracking-board").empty();
    $("#tracking-board").append('<tr><th scope="row" colspan="5" class="div-header">Tracking Board</th></tr>');
    for(var i = 0 ; i < trackingBoard.boardSize; ++i){
        var obj = $('<tr>');
        for(var j = 0 ; j < trackingBoard.boardSize; ++j){
            if (trackingBoard.board[i][j].HasAttacked){
                if (trackingBoard.board[i][j].isOccupied)
                    $('<td><button class="tracking-button">X</button></td>').appendTo(obj);
                else
                    $('<td><button class="tracking-button">O</button></td>').appendTo(obj);
            } else
                $('<td><button class="tracking-button" onclick="AttackMove(this)"></button></td>').appendTo(obj);

        }
        $('</tr>').appendTo(obj);
        $(obj).appendTo($("#tracking-board"));
    }
}

function drawPlayersInformation(playerInformation) {
    $("#player-name").empty();
    $("#player-score").empty();
    $("#player-hit").empty();
    $("#player-miss").empty();
    $("#player-avg-time").empty();
    $("#player-num-of-turns").empty();
    $("#player-name").append('Name: ' + playerInformation.name);
    $("#player-score").append('Score: ' + playerInformation.score);
    $("#player-hit").append('Hit: ' + playerInformation.hit);
    $("#player-miss").append('Miss: ' + playerInformation.miss);
    $("#player-avg-time").append('Average Turn Time: ' + playerInformation.avgTimeToAttack);
    $("#player-num-of-turns").append('Number of Turns: ' + playerInformation.numOfTurnsPlayed);
    if(gameType == "ADVANCE"){
        $("#mine-table").empty();
        $("#mine-table").append('<tr><td><button id="mine-button" class="mine-button" draggable="true" ondragstart="dragStart(event)"></button></td><td>' + playerInformation.battleShipBoard.numOfAvailableMines + '</td></tr>');
    }
}

function drawEnemyInformation(enemyShipsList) {
    $("#enemy-information").empty();
    $("#enemy-information").append('<tr><th scope="row" colspan="2" class="div-header">Enemy Information:</th></tr>');
    $.each(enemyShipsList || [], function(index, enemyShip) {
        var ship = drawShip(enemyShip);
        $('<tr class="blank_row"><td colspan="1"></td><td colspan="1"></td></tr>').appendTo($("#enemy-information"));
        $('<tr><td>' + ship + '</td><td>' + enemyShip.amount + '</td></tr>').appendTo($("#enemy-information"));
    });
}

function drawCurrentPlayerName(currentPlayerName){
    $("#current-player").empty();
    $("#current-player").append('Current player: ' + currentPlayerName);
}

function drawShip(enemyShip) {
    var obj = '<table><tr>';
    for (var i = 0; i < enemyShip.length; ++i)
        obj += '<td class="enemy-info-td"><button class="enemy-info-button "></button></td>';

    obj += '</tr>';
    if (enemyShip.category === "L_SHAPE")
        for (var i = 1; i < enemyShip.length; ++i)
            obj += '<tr><td class="enemy-info-td"><button class="enemy-info-button "></button></td></tr>';

    obj += '</table>';
    return obj;
}

/*extract button index and call a servlet to make move in the logic unit*/
function AttackMove(clickedBtn){
    var col = $(clickedBtn).parent().index();
    var row = $(clickedBtn).closest('tr').index() - 1;
    $.ajax({
        data: {"colIndex":col, "rowIndex":row},
        url: MAKE_MOVE,
        error: function (errorMsg) {
            alert(errorMsg.responseText);
        },
        success: function () {

        }
    });
}

function quitGame() {
    $.ajax({
        url: QUIT_GAME,
        error: function (errorMsg) {
            alert(errorMsg.responseText);
        }
    });
}

function returnToLobyWhileInWaitingDiv(){
    $.ajax({
        url: PLAYER_LEAVE
    });
    window.location.href = LOBY_URL;
}

function returnToLobyAfterGameFinished(){
    window.location.href = LOBY_URL;
}

function placeMine(clickedBtn, event){
    event.preventDefault();
    var col = $(clickedBtn).parent().index();
    var row = $(clickedBtn).closest('tr').index() - 1;
    $.ajax({
        data: {"colIndex":col, "rowIndex":row},
        url: PLACE_MINE,
        error: function (errorMsg) {
            alert(errorMsg.responseText);
            console.log(errorMsg);
        }
    });
}

function allowDrop(event) {
    event.preventDefault();
}

function dragStart(event) {
    event.dataTransfer.setData("Text", event.target.id);
}

function displayUsersList(users) {
    //clear all current users
    $("#userslist").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(users || [], function(index, username) {
        console.log("Adding user #" + index + ": " + username);
        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $('<li>' + username + '</li>').appendTo($("#userslist"));
    });
}

$(function() { // onload...do
    //add a function to the submit event
    $("#chatform").submit(function() {
        $.ajax({
            data: $(this).serialize(),
            url: this.action,
            timeout: 2000,
            error: function() {
                console.error("Failed to submit");
            },
            success: function(r) {
                //do not add the user string to the chat area
                //since it's going to be retrieved from the server
                //$("#result h1").text(r);
            }
        });

        $("#userstring").val("");
        // by default - we'll always return false so it doesn't redirect the user.
        return false;
    });
});

function appendToChatArea(entries) {
//    $("#chatarea").children(".success").removeClass("success");

    $("#chatarea").empty();
    // add the relevant entries
    $.each(entries || [], appendChatEntry);

    // handle the scroller to auto scroll to the end of the chat area
    var scroller = $("#chatarea");
    var height = scroller[0].scrollHeight - $(scroller).height();
    $(scroller).stop().animate({ scrollTop: height }, "slow");
}

function appendChatEntry(index, entry){
    var entryElement = createChatEntry(entry);
    $("#chatarea").append(entryElement).append("<br>");
}

function createChatEntry (entry){
    entry.chatString = entry.chatString.replace (":)", "<span class='smiley'></span>");
    return $("<span class=\"success\">").append(entry.username + "> " + entry.chatString);
}