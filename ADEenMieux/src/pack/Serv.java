package pack;

import java.io.IOException;
import java.util.Collection;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pack.Facade.LoginInfo;

/**
 * Servlet implementation class Serv
 */
@WebServlet("/Serv")
public class Serv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private Facade facade;

	/**
	 * Constructs a new Serv object.
	 */
	public Serv() {
		super();
	}

	/**
	 * Handles HTTP GET requests.
	 *
	 * @param request  the HttpServletRequest object that contains the request
	 *                 information
	 * @param response the HttpServletResponse object that contains the response
	 *                 information
	 * @throws ServletException if the servlet encounters a problem
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s1 = "";
		String s2 = "";
		try {
			if (request.getParameter("op").equals("login")) {
				LoginInfo logInfo = new LoginInfo();
				logInfo.setUsername(request.getParameter("username"));
				logInfo.setHashedPassword(LoginInfo.hashPassword(request.getParameter("password")));
				System.out.println("test login");
			} else {
				response.getWriter().println("<html><body>404 Not Found !!!</body></html>");
			}
		} catch (Exception e) {
			response.getWriter().println("<html><body>403 Forbidden !!!</body></html>");
		}
	}

	/**
	 * Handles HTTP POST requests.
	 *
	 * @param request  the HttpServletRequest object that contains the request
	 *                 information
	 * @param response the HttpServletResponse object that contains the response
	 *                 information
	 * @throws ServletException if the servlet encounters a problem
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
