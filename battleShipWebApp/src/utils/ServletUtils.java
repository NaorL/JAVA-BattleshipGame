package utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static constants.Constants.INT_PARAMETER_ERROR;

public class ServletUtils {

	private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
	private static final String GAME_MANAGER_ATTRIBUTE_NAME = "gameManager";


	public static UserManager getUserManager(ServletContext servletContext) {
		if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
			servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
		}
		return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
	}

	public static GameManager getGameManager(ServletContext servletContext) {
		if (servletContext.getAttribute(GAME_MANAGER_ATTRIBUTE_NAME) == null) {
			servletContext.setAttribute(GAME_MANAGER_ATTRIBUTE_NAME, new GameManager());
		}
		return (GameManager) servletContext.getAttribute(GAME_MANAGER_ATTRIBUTE_NAME);
	}

	public static int getIntParameter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException numberFormatException) {
			}
		}
		return INT_PARAMETER_ERROR;
	}
}
