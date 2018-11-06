package servlets;

import com.google.gson.Gson;
import utils.GameManager;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PlayersAmountServlet", urlPatterns = "/playersAmount")
public class PlayersAmountServlet extends HttpServlet{

    private final String GAME_NAME = "gameName";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        String gameName = (String)request.getSession(false).getAttribute(GAME_NAME);
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        GameManager.GameInformation gameInformation = gameManager.GetGameInformationByGameName(gameName);
        if(gameInformation != null){
            try(PrintWriter out = response.getWriter()){
                Gson gson = new Gson();
                int playersAmount = gameInformation.getAttendingPlayers();
                String json = gson.toJson(playersAmount);
                out.println(json);
            } catch(Exception ex){
                response.setStatus(400);
            }
        }
        else{
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
}
