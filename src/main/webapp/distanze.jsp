<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="maps.interfaces.BusStop"%>
<%@page import="maps.interfaces.BusLine"%>
<%@page import="maps.interfaces.BusLineStop"%>
<%@page import="maps.interfaces.BusStopService"%>

<%
Boolean debug = ( request.getParameter("debug") != null);
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
	var mymap = L.map('mapid').setView([45.0635138,7.6565683], 13);
	var latLngClick1=null;
	var latLngClick2=null;
	var srcIcon = new L.Icon({
			iconUrl: ' http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|2ecc71&chf=a,s,ee00FFFF',
		    iconSize: [21, 34], // size of the icon
		    iconAnchor: [10, 34], // point of the icon which will correspond to marker's location
		    popupAnchor: [0, -34] // point from which the popup should open relative to the iconAnchor                                 
	});
    var dstIcon = new L.Icon(
    	{
    		iconUrl: 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|e85141&chf=a,s,ee00FFFF',
   		    iconSize: [21, 34], // size of the icon
   		    iconAnchor: [10, 34], // point of the icon which will correspond to marker's location
   		    popupAnchor: [0,-34] // point from which the popup should open relative to the iconAnchor                                 
    	});
    var stopIcon = new L.Icon(
    	{
    		iconUrl: 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|abcdef&chf=a,s,ee00FFFF',
     		iconSize: [21, 34], // size of the icon
     		iconAnchor: [10, 34], // point of the icon which will correspond to marker's location
     		popupAnchor: [0, -34] // point from which the popup should open relative to the iconAnchor 
   		});

	//Layer OpenStreetMap
	L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
	    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
	}).addTo(mymap);

	mymap.on('click', function(e) {        
        var popLocation= e.latlng;
        
        if ( latLngClick1 == null )
        {
        	latLngClick1 = popLocation;
        	L.marker(popLocation,{icon: srcIcon}).addTo(mymap).bindPopup("Partenza");
        	<% if (debug) { %>
    		L.circle(popLocation, 250).addTo(mymap);
    	<% } %>
       	}
        else if ( latLngClick2 == null)
        {
        	latLngClick2 = popLocation;
        	L.marker(popLocation,{icon: dstIcon}).addTo(mymap).bindPopup("Destinazione");
        	near(latLngClick1,latLngClick2);
        	<% if (debug) { %>
        		L.circle(popLocation, 250).addTo(mymap);
        	<% } %>
        }
    });
	
	function near(latLngSrc, latLngDst)
	{
		$.ajax({
				type: "GET",
				url: "getNear.do?srcLat="+latLngSrc.lat+"&srcLng="+latLngSrc.lng+"&dstLat="+latLngDst.lat+"&dstLng="+latLngDst.lng,
				dataType: 'json',
				success: function(data, textStatus, jqXHR)
				{
					$.each(data,function(stop_index,stop)
							{
								msg = "Fermata <b>"+stop.name.replace("'","\'")+" "+stop.lat+" "+stop.lng+"</b><br>";
								//$.each(stop.lines,function(line_index,line))
								//{
								//	msg += "Linea <b>"+line.name+"</b> ("+line.description+")<br>";
								//}
								L.marker([stop.lat,stop.lng],{icon: stopIcon}).addTo(mymap)
									.bindPopup(msg);
							});
					//polyline = L.polyline(latlngs, {color: 'red'}).addTo(mymap);
					//mymap.fitBounds(polyline.getBounds());
				},
				error: function (jqXHR, textStatus, errorThrown)
				{
					//switch(jqXHR.status)
				}
		});
	}
	
	<%
	if(debug)
	{
		BusStopService busStopService = (BusStopService) request.getServletContext().getAttribute("busStopService");
	
		for(Entry e : busStopService.getAll().entrySet())
		{
			BusStop bs =  (BusStop)e.getValue();
			String msg = "Fermata <b>"+bs.getName().replace("'","\\'")+" "+bs.getLat()+" "+bs.getLng()+"</b><br>";
			%>
			L.marker([<%=bs.getLat()%> ,<%= bs.getLng()%> ]).addTo(mymap)
				.bindPopup('<%=msg%>');
			<%
		}
	}
	%>

	</script>
</body>
</html>
