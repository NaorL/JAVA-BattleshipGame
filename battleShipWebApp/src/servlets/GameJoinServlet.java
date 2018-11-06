package servlets;

import Logic.LogicUnitManager;
import com.google.gson.Gson;
import utils.GameManager;
import utils.ServletUtils;
import utils.SessionUtils;
import utils.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet(name = "GameJoinServlet", urlPatterns = "/join")
public class GameJoinServlet extends HttpServlet {

    private final String GAME_NAME = "gameName";
    private final String GAME_URL = "/pages/play/play.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(request);
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        if (usernameFromSession == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
        } else {
            String gameName = request.getParameter(GAME_NAME);
            GameManager.GameInformation gameInformation = gameManager.GetGameInformationByGameName(gameName);
            int currentPlayers = gameInformation.getAttendingPlayers();
            if(currentPlayers < 2){
                try(PrintWriter out = response.getWriter()) {
                    currentPlayers++;
                    gameInformation.setAttendingPlayers(currentPlayers);
                    if (currentPlayers == 2)
                        try {
                            gameInformation.getLogicUnitManager().InitializeGame();
                            gameInformation.clearChatEntries();
                        } catch (Exception ex) {
                            // should never be here because the game was successfully initialized
                            // when the game creator uploaded it for the first time under FileUploadServlet
                            out.println(ex.getMessage());
                        }

                    gameInformation.addSession(usernameFromSession);
                    request.getSession(true).setAttribute(GAME_NAME, gameName);
                    out.println(GAME_URL);
                    out.flush();
                }
            } else{
                response.setStatus(400);
            }
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
