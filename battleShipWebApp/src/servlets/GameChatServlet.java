package servlets;

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
import java.util.List;

@WebServlet(name = "GameChatServlet", urlPatterns = "/gameChat")
public class GameChatServlet extends HttpServlet {

    private final String GAME_NAME = "gameName";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        String gameName = (String) request.getSession(false).getAttribute(GAME_NAME);
        GameManager.GameInformation gameInformation = gameManager.GetGameInformationByGameName(gameName);
        List<GameManager.GameInformation.SingleChatEntry> chatEntryList = gameInformation.getChatEntries(0);
        try(PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(chatEntryList);
            out.println(json);
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
