<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.TreeMap"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="maps.interfaces.BusLine"%>
<%@ page import="maps.interfaces.BusLineService"%>


<!DOCTYPE html>
<html lang="en">
<head>
	<title>Docker :: Maps</title>
	<jsp:include page="partial/_headers.jsp" />
					
</head>
<body>

	<jsp:include page="partial/_navbar.jsp" />
	<br>
	<a href="distanze.jsp">Distanza dalle fermate</a>
	<br>
	<br>
	<br>
	<a href="distanze.jsp?debug=on">Distanza dalle fermate DEBUG ON</a>
	<br>
	<br>	
	
	<div class="container">
		<div class="row">
			<h3>Elenco</h3>
			<br>
			<%
				BusLineService busLineService = (BusLineService) request.getServletContext().getAttribute("busLineService");
				Map<String,BusLine> busLines = new TreeMap<String,BusLine>(busLineService.getAll());
			
				for(Entry<String,BusLine> e : busLines.entrySet())
				{
					%>
						Linea: <a href="maps.jsp?lineId=<%= e.getKey() %>"><%= e.getKey() %></a>, <%= e.getValue().getDescription() %>
						<br>
					<%
				}
			%>
		</div>
	</div>
	
</body>
</html>
