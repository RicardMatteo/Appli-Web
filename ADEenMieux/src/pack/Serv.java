package pack;

import java.io.IOException;
import java.util.Collection;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Serv
 */
@WebServlet("/Serv")
public class Serv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private Facade facade;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Serv() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String s1 = "";
		String s2 = "";
		try {
			if (request.getParameter("op").equals("ajoutpersonne")) {
				s1 = request.getParameter("nom");
				s2 = request.getParameter("prenom");
				// facade.ajoutPersonne(s1, s2);
				response.getWriter().println("<html><body>Personne ajoutée.</body></html>");
				request.getRequestDispatcher("index.html").forward(request, response);
				
			} else if (request.getParameter("op").equals("ajoutadresse")) {
				s1 = request.getParameter("rue");
				s2 = request.getParameter("ville");
				// facade.ajoutAdresse(s1, s2);
				response.getWriter().println("<html><body>Adresse ajoutée.</body></html>");
				request.getRequestDispatcher("index.html").forward(request, response);
				
			} else if (request.getParameter("op").equals("lister")) {
				// Collection<Personne> personnes = facade.listePersonnes();
				// request.setAttribute("personnes", personnes);
				request.getRequestDispatcher("lister.jsp").forward(request, response);
				
			} else if (request.getParameter("op").equals("associer")) {
				// Collection<Personne> personnes = facade.listePersonnes();
				// request.setAttribute("personnes", personnes);
				// Collection<Adresse> adresses = facade.listeAdresses();
				// request.setAttribute("adresses", adresses);
				request.getRequestDispatcher("associer.jsp").forward(request, response);
				
			} else if (request.getParameter("op").equals("association")) {
				response.getWriter().println("<html><body>Association.</body></html>");
				int personneId = Integer.parseInt(request.getParameter("pa"));
				int adresseId = Integer.parseInt(request.getParameter("aa"));
				// facade.associer(personneId, adresseId);
				request.getRequestDispatcher("index.html").forward(request, response);
			} else {
				response.getWriter().println("<html><body>404 Not Found !!!</body></html>");
			}
		} catch (Exception e) {
			response.getWriter().println("<html><body>403 Forbidden !!!</body></html>");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
