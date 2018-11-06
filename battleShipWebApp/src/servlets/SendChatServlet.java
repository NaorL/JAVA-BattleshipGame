package servlets;

import Logic.LogicUnitManager;
import Logic.Player;
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

@WebServlet(name = "SendChatServlet", urlPatterns = "/sendChat")
public class SendChatServlet extends HttpServlet {

    private final String GAME_NAME = "gameName";
    private final String CHAT_PARAMETER = "userstring";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(request);
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        String gameName = (String) request.getSession(false).getAttribute(GAME_NAME);
        GameManager.GameInformation gameInformation = gameManager.GetGameInformationByGameName(gameName);
        String userChatString = request.getParameter(CHAT_PARAMETER);
        if(gameInformation.getPlayersNames().contains(usernameFromSession))
            // only players in game should be able to send messages in chat
            if(!userChatString.isEmpty()){
                System.out.println("before");
                gameInformation.addChatString(userChatString, usernameFromSession);
                System.out.println("after");
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
