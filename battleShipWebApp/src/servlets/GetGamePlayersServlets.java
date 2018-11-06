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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

@WebServlet(name = "GetGamePlayersServlets", urlPatterns = "/gamePlayersNames")
public class GetGamePlayersServlets extends HttpServlet {

    private final String GAME_NAME = "gameName";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String usernameFromSession = SessionUtils.getUsername(request);
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        if (usernameFromSession == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
        } else {
            String gameName = (String) request.getSession(false).getAttribute(GAME_NAME);
            GameManager.GameInformation gameInformation = gameManager.GetGameInformationByGameName(gameName);
            try (PrintWriter out = response.getWriter()) {
                List<String> playersNames = gameInformation.getPlayersNames();
                Gson gson = new Gson();
                String json = gson.toJson(playersNames);
                out.println(json);
                System.out.println("the list is: " + json);
            } catch (Exception ex) {
                response.setStatus(400);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
