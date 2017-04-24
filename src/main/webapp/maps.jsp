<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="maps.interfaces.BusStop"%>
<%@page import="maps.interfaces.BusLine"%>
<%@page import="maps.interfaces.BusLineStop"%>
<%@page import="maps.interfaces.BusLineService"%>
<%
BusLine bl = null;
if ( request.getParameter("lineId") == null)
{
	response.sendRedirect("index.jsp");
}
else
{
	BusLineService busLineService = (BusLineService) request.getServletContext().getAttribute("busLineService");
	bl = busLineService.getBusLine(request.getParameter("lineId"));
	
	if ( bl == null )
	{
		response.sendRedirect("index.jsp");
	}
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css" />
	<title>Docker :: Maps</title>
	<jsp:include page="partial/_headers.jsp" />
		
	<style type="text/css">
	html,body { height: 100%; }
	#mapid,.container { height: 100%; }
	
	</style>			
</head>
<body>

	<jsp:include page="partial/_navbar.jsp" />
	<div class="container">
		<div id="mapid"></div>
	</div>
	<script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet.js"></script>
	<script type="text/javascript">
	var mymap = L.map('mapid').setView([51.505, -0.09], 13);

	//Layer OpenStreetMap
	L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
	    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
	}).addTo(mymap);

	<%
	if ( bl != null )
	{
		%>
		//TODO: Fare in Query ajax
		/*
		$.ajax({
				type: "GET",
				url: "getLineDetails.do?lineID=<%=request.getParameter("lineId")%>",
				dataType: 'json',
				success: function(data, textStatus, jqXHR)
				{
					latlngs=[];
					$.each(data.stops,function(stop_index,stop)
							{
								msg = "Fermata <b>"+stop.name+"</b><br>";
								$.each(stop.lines,function(line_index,line))
								{
									msg += "Linea <b>"+line.name+"</b> ("+line.description+")<br>";
								}
								L.marker([stop.lat,stop.lng ]).addTo(mymap)
									.bindPopup(msg);
								latlngs.push(L.latLng(stop.lat,stop.lng));
							});
					polyline = L.polyline(latlngs, {color: 'red'}).addTo(mymap);
					mymap.fitBounds(polyline.getBounds());
				},
				error: function (jqXHR, textStatus, errorThrown)
				{
					//switch(jqXHR.status)
				}
		});
			*/
		latlngs=[];
		<%
		for(BusLineStop bs : bl.getBusStops())
		{
			String msg = "Fermata <b>"+bs.getBusStop().getName()+"</b><br>";
			for(BusLineStop bsl : bs.getBusStop().getBusLineStops())
			{
				msg += "Linea <b>"+bsl.getBusLine().getLine()+"</b> ("+bsl.getBusLine().getDescription()+")<br>";
			}
			msg = msg.replace("'", "\\'");
			%>
			L.marker([<%=bs.getBusStop().getLat()%> ,<%= bs.getBusStop().getLng()%> ]).addTo(mymap)
				.bindPopup('<%=msg%>');
			latlngs.push(L.latLng(<%=bs.getBusStop().getLat()%> ,<%= bs.getBusStop().getLng()%>));
			<%
		}
		%>
		var polyline = L.polyline(latlngs, {color: 'red'}).addTo(mymap);
		mymap.fitBounds(polyline.getBounds());
		<%
	}
	%>
	</script>
</body>
</html>
