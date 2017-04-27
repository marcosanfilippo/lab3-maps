package maps.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import maps.interfaces.BusStop;
import maps.route.Edge;
import maps.route.MinPath;

@JsonAutoDetect
public class JsonRoute {
	
	public JsonBusStop source;
	public JsonBusStop destination;
	public List<JsonRouteEdge> route;
	//For debug
	public List<BusStop> stopSrc;
	public List<BusStop> stopDst;

	public JsonRoute(MinPath mp,List<BusStop> bSrc , List<BusStop> bDst) {
		if ( mp != null )
		{
			this.stopSrc = bSrc;
			this.stopDst = bDst;
			
			this.source = new JsonBusStop(mp.getBusStopSource());
			this.destination = new JsonBusStop(mp.getBusStopDestination());
			
			List<JsonRouteEdge> route = new ArrayList<>();
			
			for(Edge e : mp.getEdges() )
			{
				route.add(new JsonRouteEdge(e.getBusStop(),e.getBusLine()));
					//	busStopService.getBusStop(e.busStopId),busLineService.getBusLine(e.line)));
			}
			this.route = route;
		}
		else
		{
			this.source = null;
			this.destination = null;
			this.route = null;
			this.stopSrc = null;
			this.stopDst = null;
		}
	}
}
