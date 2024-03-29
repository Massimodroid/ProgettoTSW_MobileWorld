package it.unisa.control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.bean.*;
import it.unisa.model.dao.IndirizzoDAO;
import it.unisa.model.dao.PagamentoDAO;


public class InserimentoFinalControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(InserimentoFinalControl.class.getName());
	
       private static final PagamentoDAO modelPagamento = new PagamentoDAO();
       private static final IndirizzoDAO modelIndirizzo = new IndirizzoDAO();
  

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Metodo vuoto - Nessuna implementazione richiesta per questa servlet
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		CartaBean carta = new CartaBean();
		IndirizzoBean indirizzo = new IndirizzoBean();
		UserBean user = (UserBean) request.getSession().getAttribute("Utente"); 
		carta.setCodiceSegreto(Integer.parseInt(request.getParameter("codS")));
		carta.setNumeroCarta(request.getParameter("numCarta"));
		
		carta.setDataScadenza(Date.valueOf(request.getParameter("data")));
		carta.setCircuito(request.getParameter("Circuito"));
		modelPagamento.doSave(carta,user);
		indirizzo.setIdIndirizzo(Integer.parseInt(request.getParameter("id")));
		indirizzo.setCitta(request.getParameter("citta"));
		indirizzo.setVia(request.getParameter("via"));
		int n = Integer.parseInt(request.getParameter("NCivico"));
		indirizzo.setNumeroCivico(n);
		if(request.getParameter("piano").equalsIgnoreCase("")) {
			indirizzo.setPiano(0);
		}else {
			indirizzo.setPiano(Integer.parseInt(request.getParameter("piano")));
		}
		if(request.getParameter("interno").equalsIgnoreCase("")) {
			indirizzo.setInterno(0);
		}else {
			indirizzo.setInterno(Integer.parseInt(request.getParameter("interno")));
		}
		if(request.getParameter("scala").equalsIgnoreCase("")) {
			indirizzo.setScala(null);
		}else {
			indirizzo.setScala(request.getParameter("scala"));
		}
		
		modelIndirizzo.doSave(indirizzo,user);
		
		} catch (SQLException e) {
			logger.log(Level.SEVERE, () -> "Errore Inserimento Final Control: " + e.getMessage());
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/catalogo");
		dispatcher.forward(request, response);
	}

}
