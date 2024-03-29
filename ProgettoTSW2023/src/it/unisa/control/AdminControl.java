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
import it.unisa.model.dao.*;
import it.unisa.model.bean.*;


public class AdminControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PRODOTTI = "prodotti";
	private static final String ERRORE_ADMIN = "Errore Admin Control";
	
	private static final ProdottoDAO modelProd = new ProdottoDAO();
	private static final ComponiDAO modelComponi = new ComponiDAO();
	Logger logger = Logger.getLogger(AdminControl.class.getName());
	
	//credenziali per accedere alla sezione admin: email= mobileworld.info@gmail.com password=Tsw12345@
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean stato=true;
		UserBean user = (UserBean) request.getSession().getAttribute("admin");
		
		if(user != null && user.isValid()) {
				ArrayList<ProdottoBean> prodotti = new ArrayList<>();
				try {
					prodotti = modelProd.doRetrieveAll();
				} catch (SQLException e) {
					
					logger.log(Level.SEVERE, () -> ERRORE_ADMIN + e.getMessage());

				}
					request.setAttribute(PRODOTTI, prodotti);

				String op= (String)request.getParameter("op");
				if(op!=null) {
					if(op.equalsIgnoreCase("modProd")) {
						int id = Integer.parseInt(request.getParameter("id"));
						try {
							stato=false;
							ProdottoBean bean = modelProd.doRetrieveByKey(id);
							request.setAttribute("bean", bean);
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin/ModificaAdmin.jsp");
							dispatcher.forward(request, response);
							
						} catch (SQLException e) {
							logger.log(Level.SEVERE, () -> ERRORE_ADMIN + e.getMessage());
						}
						
					}
					else if(op.equalsIgnoreCase("dettordini")) {
						int id = Integer.parseInt(request.getParameter("idOrdine"));
						ArrayList<ComponiBean> componi = new ArrayList<>();
						try {
							componi=modelComponi.doRetrieveByKey(id);
							if(componi!=null) {
								request.setAttribute("componi", componi);
							}
						} catch (SQLException e) {
							logger.log(Level.SEVERE, () -> ERRORE_ADMIN + e.getMessage());
						}
					}
					else if(op.equalsIgnoreCase("elimina")) {
						int id = Integer.parseInt(request.getParameter("id"));
						try {
							modelProd.doDelete(id);
							request.removeAttribute(PRODOTTI);
							prodotti = modelProd.doRetrieveAll();
							request.setAttribute(PRODOTTI, prodotti);
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin/Admin.jsp");
							dispatcher.forward(request, response);
							return;
							
						} catch (SQLException e) {
							logger.log(Level.SEVERE, () -> "context: " + e.getMessage());
						}
					}else if(op.equalsIgnoreCase("insert")) {
						stato=false;
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin/InsertAdmin.jsp");
						dispatcher.forward(request, response);
						return;
					}
					
			}
		}
		else {
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error/errorpage.jsp");
			dispatcher.forward(request, response);
			return;
		}
		if(stato) {
			
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin/Admin.jsp");
		dispatcher.forward(request, response);
		}
	

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
