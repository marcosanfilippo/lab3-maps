package maps.json;

import maps.interfaces.BusStop;

public class JsonBusStop {
	public String id;
	public String name;
	public Double lat;
	public Double lng;
	
	public JsonBusStop(BusStop busStop) {
		this.id = busStop.getId();
		this.name = busStop.getName();
		this.lat = busStop.getLat();
		this.lng = busStop.getLng();
	}
	
	public JsonBusStop(String id, String name, Double lat, Double lng) {
		this.id = id;
		this.name = name;
		this.lat = lat;
		this.lng = lng;
	}
}
