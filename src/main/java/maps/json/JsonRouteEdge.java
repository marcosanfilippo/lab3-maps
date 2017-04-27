package maps.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import maps.interfaces.BusLine;
import maps.interfaces.BusStop;

@JsonAutoDetect
public class JsonRouteEdge {
	public JsonBusStop stop;
	public JsonBusLine line;
	
	
	public JsonRouteEdge(BusStop busStop, BusLine busLine) {
		this.stop = new JsonBusStop(busStop);
		if ( busLine != null )
		{
			this.line = new JsonBusLine(busLine);
		}
		else
		{
			this.line = null;
		}
	}

	public JsonRouteEdge(JsonBusStop stop, JsonBusLine line) {
		this.stop = stop;
		this.line = line;
	}
	
	
}
