package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.bean.ComponiBean;
import it.unisa.model.bean.UserBean;
import it.unisa.model.dao.ComponiDAO;



public class DettagliOrdineControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(DettagliOrdineControl.class.getName());
	
	private static final ComponiDAO model = new ComponiDAO();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		UserBean user = (UserBean) request.getSession().getAttribute("Utente");
		ArrayList<ComponiBean> bean = new ArrayList<>();
		try {
			if(user.isValid()) {
				bean = model.doRetrieveByKey(id);
				request.setAttribute("componi", bean);
			}
		
		
	} catch (SQLException e) {
		logger.log(Level.SEVERE, () -> "context: " + e.getMessage());		
	}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/DettagliOrdineView.jsp");
		dispatcher.forward(request, response);
}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
