package utils;

import Logic.LogicUnitManager;

import java.util.*;

public class GameManager {
    private final Set<GameInformation> gamesSet;

    public GameManager() {
        gamesSet = new HashSet<>();
    }

    public void addGame(GameInformation game) {
        gamesSet.add(game);
    }

    public void removeGame(String gameName) {
        GameInformation gameToRemove = null;
        for (GameInformation game:gamesSet) {
            if(game.name.compareTo(gameName) == 0){
                gameToRemove = game;
                break;
            }
        }
        gamesSet.remove(gameToRemove);
    }

    public Set<GameInformation> getGames() {
        return Collections.unmodifiableSet(gamesSet);
    }

    public boolean isGameNameExists(String gameName) {
        for (GameInformation game:gamesSet) {
            if(game.name.compareTo(gameName) == 0)
                return true;
        }
        return false;
    }

    public GameInformation GetGameInformationByGameName(String name){
        for (GameInformation game: gamesSet) {
            if (game.name.compareTo(name) == 0)
                return game;
        }

        return null;
    }

    public class GameInformation{
        private LogicUnitManager theGame;
        private String name;
        private String creator;
        private int attendingPlayers;
        private List<String> sessionIds;
        private List<SingleChatEntry> chatMessages;

        public class SingleChatEntry{
            private final String chatString;
            private final String username;

            public SingleChatEntry(String chatString, String username) {
                this.chatString = chatString;
                this.username = username;
            }
        }

        public GameInformation(LogicUnitManager i_TheGame, String i_Name, String i_Creator){
            theGame = i_TheGame;
            name = i_Name;
            creator = i_Creator;
            attendingPlayers = 0;
            sessionIds = new ArrayList<>();
            chatMessages = new ArrayList<>();
        }

        public void setAttendingPlayers(int attendingPlayers) {
            this.attendingPlayers = attendingPlayers;
        }

        public int getAttendingPlayers(){
            return attendingPlayers;
        }

        public void addSession(String session){
            sessionIds.add(session);
        }

        public void removeSession(String session){
            sessionIds.remove(session);
        }

        public LogicUnitManager getLogicUnitManager() {
            return theGame;
        }

        public List<String> getPlayersNames(){
            return sessionIds;
        }

        public void addChatString(String chatString, String username) {
            chatMessages.add(new SingleChatEntry(chatString, username));
        }

        public List<SingleChatEntry> getChatEntries(int fromIndex){
            if (fromIndex < 0 || fromIndex >= chatMessages.size()) {
                fromIndex = 0;
            }
            return chatMessages.subList(fromIndex, chatMessages.size());
        }

        public void clearChatEntries(){
            chatMessages.clear();
        }
    }
}
