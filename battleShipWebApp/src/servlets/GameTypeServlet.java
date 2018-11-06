package servlets;

import Logic.LogicUnitManager;
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

@WebServlet(name = "GameTypeServlet ", urlPatterns = "/gameType")
public class GameTypeServlet extends HttpServlet {

    private final String GAME_NAME = "gameName";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        String usernameFromSession = SessionUtils.getUsername(request);
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        if (usernameFromSession == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
        } else {
            String gameName = (String) request.getSession(false).getAttribute(GAME_NAME);
            GameManager.GameInformation gameInformation = gameManager.GetGameInformationByGameName(gameName);
            LogicUnitManager logicunit = gameInformation.getLogicUnitManager();
            try (PrintWriter out = response.getWriter()) {
                String gameType = logicunit.GetGameType();
                Gson gson = new Gson();
                String json = gson.toJson(gameType);
                out.println(json);
            } catch (Exception ex) {
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
