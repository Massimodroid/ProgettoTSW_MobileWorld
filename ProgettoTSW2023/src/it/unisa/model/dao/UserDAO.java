package it.unisa.model.dao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.unisa.model.bean.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
public class UserDAO {
	private static final String TABLE_NAME_UTENTE = "Utente";
	private static DataSource ds;
	
	static {
		try {
			Context init = new InitialContext();
			Context env = (Context) init.lookup("java:comp/env");
			
			ds = (DataSource) env.lookup("jdbc/smartphone");
			
		}catch(NamingException e) {
			Logger logger = Logger.getLogger(PagamentoDAO.class.getName());
			logger.log(Level.SEVERE, () -> "Errore UserDAO: " + e.getMessage());
		}
	}
	
	public void doSave(UserBean utente) throws SQLException{
		Connection con = null;
		PreparedStatement prSUtente = null;
		
		String insertUtenteSQL = "insert into "+ TABLE_NAME_UTENTE+" (Nome,Cognome,Email,Password,Ruolo)"+
								 " values(?,?,?,?,?)";
		try {
			con = ds.getConnection();
			prSUtente = con.prepareStatement(insertUtenteSQL);
			prSUtente.setString(1, utente.getNome());
			prSUtente.setString(2, utente.getCognome());
			prSUtente.setString(3, utente.getEmail());
			prSUtente.setString(4, utente.getPassword());
			prSUtente.setString(5, utente.getRuolo());
			
			
			prSUtente.executeUpdate();
	
		}finally {
			try {
				if(prSUtente != null) 
					prSUtente.close();
			}finally {
				if(con != null) 
					con.close();
				
				
			}
		}
		
	}
	public UserBean doRetrieveUtente(String email,String password) throws SQLException{
		ResultSet result;
        Connection con = null;
        PreparedStatement prS = null;
        String selectQuery = "Select * from "+TABLE_NAME_UTENTE+" WHERE email = ? and password = ?";
        UserBean user = new UserBean();
        try {
        	con = ds.getConnection();
        	prS = con.prepareStatement(selectQuery);
        	prS.setString(1, email);
        	prS.setString(2, password);
        	result = prS.executeQuery();
        	boolean esito = false;
     
        		if(result.next()) {
        			user.setIdUtente(result.getInt("ID"));
        			user.setNome(result.getString("Nome"));
        			user.setCognome(result.getString("Cognome"));
        			user.setEmail(result.getString("Email"));
        			user.setPassword(result.getString("Password"));
        			user.setNumeroCarta(result.getString("Numero_Carta"));
        			user.setRuolo(result.getString("Ruolo"));
        			esito = true;
        			user.setValidita(esito);
        			return user;
        		}
        		        	
        }finally {
        	try {
        		if(prS != null)
        			prS.close();
        		
        	}finally {
        		if(con != null)
        			con.close();
        	}
        }
        return null;
	}
	public ArrayList<UserBean> doRetrieveUtenteForName(String nome,String cognome) throws SQLException{
		ResultSet result;
        Connection con = null;
        PreparedStatement prS = null;
        String selectQuery = "Select * from "+TABLE_NAME_UTENTE+ " WHERE Nome = ? and Cognome = ?";
        ArrayList<UserBean> utenti = new  ArrayList<>();
        
        try {
        	con = ds.getConnection();
        	prS = con.prepareStatement(selectQuery);
        	prS.setString(1, nome);
        	prS.setString(2, cognome);
        	result = prS.executeQuery();
     
        		while(result.next()) {
        			UserBean user = new UserBean();
        			user.setIdUtente(result.getInt("ID"));
        			user.setNome(result.getString("Nome"));
        			user.setCognome(result.getString("Cognome"));
        			user.setEmail(result.getString("Email"));
        			user.setPassword(result.getString("Password"));
        			user.setNumeroCarta(result.getString("Numero_Carta"));
        			user.setRuolo(result.getString("Ruolo"));
        			utenti.add(user);
        		}
        	
        }finally {
        	try {
        		if(prS != null)
        			prS.close();
        		
        	}finally {
        		if(con != null)
        			con.close();
        	}
        }
        return utenti;
	}
	
	public UserBean doRetrievebyemail(String email) throws SQLException {
		ResultSet result;
        Connection con = null;
        PreparedStatement prS = null;
        String selectQuery = "Select * from "+TABLE_NAME_UTENTE+" WHERE Email = ?";
        UserBean user = new UserBean();
        try {
        	con = ds.getConnection();
        	prS = con.prepareStatement(selectQuery);
        	prS.setString(1, email);
        	result = prS.executeQuery();
        	boolean esito = false;
     
        		if(result.next()) {
        			user.setIdUtente(result.getInt("ID"));
        			user.setNome(result.getString("Nome"));
        			user.setCognome(result.getString("Cognome"));
        			user.setEmail(result.getString("Email"));
        			user.setPassword(result.getString("Password"));
        			user.setNumeroCarta(result.getString("Numero_Carta"));
        			user.setRuolo(result.getString("Ruolo"));
        			esito = true;
        		}
        		user.setValidita(esito);
        	
        }finally {
        	try {
        		if(prS != null)
        			prS.close();
        		
        	}finally {
        		if(con != null)
        			con.close();
        	}
        }
        return user;
	}
	
	
}
