package servlets;

import Logic.LogicUnitManager;
import com.google.gson.Gson;
import utils.GameManager;
import utils.ServletUtils;
import utils.SessionUtils;
import utils.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet(name = "FileUploadServlet", urlPatterns = "/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet{

    private final String FILE_PATH = this.getClass().getClassLoader().getResource(File.separator).getPath();
    private final String GAME_NAME = "gameName";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String gameName = request.getParameter(GAME_NAME);
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        String usernameFromSession = SessionUtils.getUsername(request);
        String errorMsg;
        try (PrintWriter out = response.getWriter()) {
            if (gameName.isEmpty()) {
                errorMsg = "Game name cannot be an empty string";
                out.println(errorMsg);
                response.setStatus(400);
            } else if (gameManager.isGameNameExists(gameName)) {
                errorMsg = "Game name already exists";
                out.println(errorMsg);
                response.setStatus(400);
            } else {
                Part filePart = request.getPart("fileInput"); // Retrieves <input type="file" name="file">
                String fileName = getSubmittedFileName(filePart);
                InputStream inputStream = filePart.getInputStream();
                OutputStream outputStream = new FileOutputStream(new File(FILE_PATH + fileName));
                saveInputStreamToFile(inputStream, outputStream);
                LogicUnitManager logicUnit = new LogicUnitManager();
                try {
                    logicUnit.LoadGameInformation(FILE_PATH + fileName, gameName, usernameFromSession);
                    logicUnit.InitializeGame();
                    GameManager.GameInformation gameInformation = new GameManager().new GameInformation(logicUnit, gameName, usernameFromSession);
                    gameManager.addGame(gameInformation);
                } catch (Exception ex) {
                    out.println(ex.getMessage());
                    response.setStatus(400);
                }
            }
        }

        getServletContext().getRequestDispatcher("/pages/loby/loby.html").forward(request, response);
    }

    private void saveInputStreamToFile(InputStream inputStream, OutputStream outputStream) throws IOException {
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
    }

    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
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
