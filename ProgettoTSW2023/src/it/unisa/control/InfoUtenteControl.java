package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.bean.CartaBean;
import it.unisa.model.bean.IndirizzoBean;
import it.unisa.model.bean.UserBean;
import it.unisa.model.dao.IndirizzoDAO;
import it.unisa.model.dao.PagamentoDAO;


public class InfoUtenteControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(InfoUtenteControl.class.getName());
	
    private static final PagamentoDAO model = new PagamentoDAO();  
    private static final IndirizzoDAO modelIndi = new IndirizzoDAO();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserBean user = (UserBean) request.getSession().getAttribute("Utente");
		try {
			CartaBean carta = model.doRetrieveCarta(user.getNumeroCarta());
			IndirizzoBean indi = modelIndi.doRetrieveIndirizzo(user);
			request.setAttribute("carta", carta);
			request.setAttribute("indirizzo", indi);

			
		} catch (SQLException e) {
			
			logger.log(Level.SEVERE, () -> "context: " + e.getMessage());
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserLoggedView.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
