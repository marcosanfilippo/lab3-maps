package maps.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import maps.interfaces.BusRouteService;
import maps.interfaces.BusStop;
import maps.interfaces.BusStopService;
import maps.json.JsonRoute;
import maps.route.MinPath;

@WebServlet("/getRoute.do")
public class ServletRoute extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Double radius = (Double) request.getServletContext().getAttribute("radiusDistance");
		if ( request.getParameter("srcLat") != null &&
				request.getParameter("srcLng") != null &&
				request.getParameter("dstLat") != null &&
				request.getParameter("dstLng") != null )
		{
			Double srcLat = Double.parseDouble(request.getParameter("srcLat"));
			Double srcLng = Double.parseDouble(request.getParameter("srcLng"));
			Double dstLat = Double.parseDouble(request.getParameter("dstLat"));
			Double dstLng = Double.parseDouble(request.getParameter("dstLng"));
			
			BusStopService busStopService = (BusStopService) request.getServletContext().getAttribute("busStopService");
			BusRouteService busRouteService = (BusRouteService) request.getServletContext().getAttribute("busRouteService");
		
			@SuppressWarnings("unchecked")
			List<BusStop> bSrc = (List<BusStop>) busStopService.getBusStopNear(srcLat, srcLng, radius);
			@SuppressWarnings("unchecked")
			List<BusStop> bDst = (List<BusStop>) busStopService.getBusStopNear(dstLat, dstLng, radius);
		
			MinPath mp = busRouteService.getRoute(bSrc, bDst);
			JsonRoute jo = new JsonRoute(mp,bSrc,bDst);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonString="";
			try {
				jsonString = mapper.writeValueAsString(jo);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				response.setStatus(500);
				return;
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(200);
			
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonString);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				response.setStatus(500);
			}
				
		}
		else
		{
			response.setStatus(500);
		}

	}
}