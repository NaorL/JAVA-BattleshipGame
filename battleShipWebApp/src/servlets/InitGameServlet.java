package servlets;

import Logic.LogicUnitManager;
import Logic.Player;
import com.google.gson.Gson;
import utils.GameManager;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet(name = "InitGameServlet", urlPatterns = "/playerInformation")
public class InitGameServlet extends HttpServlet{

    private final String GAME_NAME = "gameName";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String usernameFromSession = SessionUtils.getUsername(request);
        String gameName = (String) request.getSession(false).getAttribute(GAME_NAME);
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        GameManager.GameInformation gameInformation = gameManager.GetGameInformationByGameName(gameName);
        if (gameInformation != null) {
            LogicUnitManager logicUnitManager = gameInformation.getLogicUnitManager();
            logicUnitManager.updatePlayersName(gameInformation.getPlayersNames());
            try (PrintWriter out = response.getWriter()) {
                Player player = logicUnitManager.GetPlayerInformationByName(usernameFromSession);
                String currentPlayerName = logicUnitManager.GetCurrentPlayer().GetPlayerName();
                GameScreenInformation gmi = new GameScreenInformation(player, currentPlayerName);
                Gson gson = new Gson();
                String json = gson.toJson(gmi);
                out.println(json);
            } catch (Exception ex) {
                response.setStatus(400);
            }
        } else {
            // should never be here - once a player joins a game, the game cannot be deleted
            response.setStatus(400);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public class GameScreenInformation{
        private Player player;
        private String currentPlayerName;

        public GameScreenInformation(Player player, String currentPlayerName){
            this.player = player;
            this.currentPlayerName = currentPlayerName;
        }
    }
}