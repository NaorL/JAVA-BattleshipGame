var refreshRate = 2000; //mili seconds
var USER_LIST_URL = "/userslist";
var GAME_LIST_URL = "/gameslist";
var JOIN_GAME_URL = "/join";
var DELETE_GAME_URL = "/delete";

//users = a list of usernames, essentially an array of javascript strings:
// ["moshe","nachum","nachche"...]
function refreshUsersList(users) {
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

function ajaxUsersList() {
    $.ajax({
        url: USER_LIST_URL,
        error: function () {
            console.log("Failed to retrieve users");
        },
        success: function(users) {
            refreshUsersList(users);
        }
    });
}

function refreshGamesList(games) {
    //clear all current games
    $("#gamestable").empty();
    drawHeaders();
    var sessionId = games.sessionId;
    var gamesList = games.gamesList;

    // rebuild the list of games: scan all games and add them to the list of games
    $.each(gamesList || [], function(index, game) {
        var row = $('<tr>' +
                       '<td>' + game.name + '</td>' +
                       '<td>' + game.creator+ '</td>' +
                       '<td>' + game.theGame.game.boardSize + '</td>' +
                       '<td>' + game.theGame.game.gameType + '</td>' +
                       '<td>' + game.attendingPlayers + '</td>' +
                       '<td><button class="btn btn-success" id="joinBtn" onclick="joinGame(this)">Join</button></td>');
        if(sessionId == game.creator)
            row.append('<td><button class="btn btn-danger" id="deleteBtn" onclick="deleteGame(this)">Delete</button></td>');

        (row.append('</tr>')).appendTo($("#gamestable"));
    });
}

function isRegistered(sessionId ,sessionIdsList) {
    for(var i=0; i < sessionIdsList.length ; ++i)
        if(sessionId === sessionIdsList[i])
            return true;
    return false;
}

function drawHeaders() {
    $('<tr>' +
        '<th>Game Name</th>' +
        '<th>Creator</th>' +
        '<th>Board Size</th>' +
        '<th>Game Type</th>' +
        '<th>Players</th>' +
        '<th>Join/Leave</th>' +
        '<th>Delete Game</th>' +
        '</tr>').appendTo($("#gamestable"));
}

function ajaxGamesList() {
    $.ajax({
        url: GAME_LIST_URL,
        error: function () {
            console.log("Failed to retrieve games");
        },
        success: function(games) {
            refreshGamesList(games);
        }
    });
}

//activate the timer calls after the page is loaded
$(function() {

    //prevent IE from caching ajax calls
    $.ajaxSetup({cache: false});

    //The users list is refreshed automatically every second
    setInterval(ajaxUsersList, refreshRate);

    //The games list is refreshed automatically every second
    setInterval(ajaxGamesList, refreshRate);
});

$(function() {
    $('#upload-form').ajaxForm({
        success: function() {
            alert("File has been uploaded successfully");
        },
        error: function(msg) {
            alert(msg.responseText);
        }
    });
});

function joinGame(pressedBtn) {
    var gameName = $(pressedBtn).closest('tr').find('td').eq(0).text();
    $.ajax({
        data: {"gameName":gameName},
        url: JOIN_GAME_URL,
        error: function () {
            alert("Game is full - failed to join game");
        },
        success: function(gameUrl){
            window.location.href = gameUrl;
        }
    });
}

function deleteGame(pressedBtn) {
    var gameName = $(pressedBtn).closest('tr').find('td').eq(0).text();
    $.ajax({
        data: {"gameName":gameName},
        url: DELETE_GAME_URL,
        error: function () {
            alert("Failed to delete game since it's populated");
        },
        success: function(lobyUrl){
            window.location.href = lobyUrl;
        }
    });
}