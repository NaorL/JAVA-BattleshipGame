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
import java.util.Date;
import java.util.List;

@WebServlet(name = "MakeMoveServlet", urlPatterns = "/makeMove")
public class MakeMoveServlet extends HttpServlet {

    private final String COL_INDEX = "colIndex";
    private final String ROW_INDEX = "rowIndex";
    private final String GAME_NAME = "gameName";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(request);
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        if (usernameFromSession == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
        } else {
            int row = Integer.parseInt(request.getParameter(ROW_INDEX));
            int col = Integer.parseInt(request.getParameter(COL_INDEX));
            String gameName = (String) request.getSession(false).getAttribute(GAME_NAME);
            GameManager.GameInformation gameInformation = gameManager.GetGameInformationByGameName(gameName);
            LogicUnitManager logicunit = gameInformation.getLogicUnitManager();

            //extract the current player that should play
            Player player = logicunit.GetCurrentPlayer();

            //if the request arrived from the right player then we should make the move
            if (player.GetPlayerName().compareTo(usernameFromSession) == 0) {
                try (PrintWriter out = response.getWriter()) {
                    try {
                        logicunit.MakeMove(row, col);
                    } catch (Exception ex) {
                        out.println(ex.getMessage());
                        response.setStatus(400);
                    }
                }
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
