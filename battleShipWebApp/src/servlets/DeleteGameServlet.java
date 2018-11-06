package servlets;

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

@WebServlet(name = "DeleteGameServlet", urlPatterns = "/delete")
public class DeleteGameServlet extends HttpServlet {

    private final String GAME_NAME = "gameName";
    private final String LOBY_URL = "/pages/loby/loby.html";

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
            if(currentPlayers == 0){
                try(PrintWriter out = response.getWriter()) {
                    gameManager.removeGame(gameName);
                    out.println(LOBY_URL);
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
