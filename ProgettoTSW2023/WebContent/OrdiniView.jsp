<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% 
    	ArrayList<?> ordini = (ArrayList<?>) request.getAttribute("ordini");
    	ComponiDAO model = new ComponiDAO();
    %>
<!DOCTYPE html>
<html lang="en">
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.unisa.model.bean.*,it.unisa.model.*,it.unisa.model.dao.*,java.text.DecimalFormat
"%>

<head>
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="script/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="script/countElem.js"></script>
<meta charset="UTF-8">
<link rel="icon" type="image/png" href="img/favicon.png" />
<title>MobileWorld: Ordini</title>
</head>
<body>
<%@include file="topdown/header.jsp" %>
<br>
<div class="content">
	<div class="ordinicontent">
		
			<%if(ordini!=null && ordini.size()!=0){
				DecimalFormat df = new DecimalFormat("###.##");
				for(int i=0;i<ordini.size();i++){
					double totale=0;
					OrdineBean ordine = (OrdineBean) ordini.get(i);
					ArrayList<ComponiBean>componi = model.doRetrieveByKey(ordine.getIdOrdine());
					for(int j=0;j<componi.size();j++){
						ComponiBean bean = (ComponiBean) componi.get(j);
						totale+=bean.getPrezzo()*bean.getQuantita();
					}%>
			<div class="ordine">
			<h3>Ordine effettuato il "<%=ordine.getData().toString() %>" con un prezzo totale di "€<%=df.format(totale) %>"</h3>
			<p>Ordine #<%=i+1 %></p>
			<hr>
			<div class="statodettagli">
			<p> Stato: <%=ordine.getStato() %><p>
			<div class="iconInfo" style="float:none;"><a href="./dettagliOrdine?id=<%=ordine.getIdOrdine() %>"><button><img src="img/icona-info.png" class="image" alt="Immagine Info"></button> Info prodotti</a></div>
			
			</div>
			</div>				
				<%}
			}else{%>
		
		<h2>Non ci sono ordini</h2>
		<% }%>
	</div>

</div>
<br>
<%@include file="topdown/footer.jsp" %>
</body>
</html>