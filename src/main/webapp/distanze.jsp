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
	<div id="msg"></div>
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
    		L.circle(popLocation, <%=request.getServletContext().getAttribute("radiusDistance")%>).addTo(mymap);
    	<% } %>
       	}
        else if ( latLngClick2 == null)
        {
        	latLngClick2 = popLocation;
        	L.marker(popLocation,{icon: dstIcon}).addTo(mymap).bindPopup("Destinazione");
        	near(latLngClick1,latLngClick2);
        	<% if (debug) { %>
        		L.circle(popLocation, <%=request.getServletContext().getAttribute("radiusDistance")%>).addTo(mymap);
        	<% } %>
        }
    });
	
	function near(latLngSrc, latLngDst)
	{
		$.ajax({
				type: "GET",
				url: "getRoute.do?srcLat="+latLngSrc.lat+"&srcLng="+latLngSrc.lng+"&dstLat="+latLngDst.lat+"&dstLng="+latLngDst.lng,
				dataType: 'json',
				success: function(data, textStatus, jqXHR)
				{
					if ( !data.source || !data.destination || !data.route )
					{
						alert("Nessun percorso trovato =(");
						return;
					}
					prev = latLngSrc;
					prevLine = null;
					prevStopName = data.route[0].stop.name;
					first = true;
					msg = "";
					$.each(data.route,function(route_index,route)
					{
						stop = route.stop
						msgPopup = "Fermata <b>"+stop.name+" ("+stop.id+")</b><br>";

						act=L.latLng(stop.lat,stop.lng);
						
						L.marker(act,{icon: stopIcon}).addTo(mymap)
							.bindPopup(msgPopup);
						
						if (first)
						{
							msg += "Cammina fino alla fermata <b>"+stop.name+" N."+stop.id+"</b> (?? metri)<br>";
							polyline = L.polyline([prev,act], {color: 'grey'}).addTo(mymap);
							first = false;
							prevLine = route.line;
						}
						
						
						if (    (prevLine == null && route.line != null ) ||
								(prevLine != null && route.line == null ) || 
								(prevLine != null && route.line != null && prevLine.line != route.line.line ) )
						{
							if ( prevLine != null ) msg += "Prendi la linea <b>"+prevLine.line+" ("+prevLine.description+")</b> fino alla fermata <b>"+stop.name+" N."+stop.id+"</b> (?? metri)<br>";
							else msg += "Cammina fino alla fermata <b>"+stop.name+" N."+stop.id+"</b> (?? metri)<br>";
							
							prevLine = route.line;
						}
						
						//Ultima fermata
						if ( route.stop.id == data.destination.id )
						{
							if ( route.line )
							{
								msg += "Prendi la linea <b>"+route.line.line+" ("+route.line.description+")</b> fino alla fermata <b>"+stop.name+" N."+stop.id+"</b> (?? metri)<br>";
							}
							else
							{
								msg += "Cammina fino a <b>"+stop.name+" N."+stop.id+"</b> (?? metri)<br>";								
							}
						}
						
						if ( route.line != null) c = 'red'; //By bus
						else c = 'grey'; //By foot
						
						polyline = L.polyline([prev,act], {color: c}).addTo(mymap);
						prev=act;	
						prevStopName = stop.name;						
					});
					
					msg += "Cammina fino alla tua destinazione<br>";
					polyline = L.polyline([prev,latLngDst], {color: 'grey'}).addTo(mymap);
					
					$("#msg").html(msg);
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
	
		for(Entry<String,BusStop> e : busStopService.getAll().entrySet())
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
