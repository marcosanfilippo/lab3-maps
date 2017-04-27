package maps.route;

import maps.interfaces.BusLine;
import maps.interfaces.BusLineService;
import maps.interfaces.BusStop;
import maps.interfaces.BusStopService;
import maps.service.BusLineServiceHibernateImpl;
import maps.service.BusStopServiceHibernateImpl;

public class Edge {

	//TODO
	public String busStopId;
	public String line;
	public int cost;
	
	public Edge(String busStopId, String line,int cost) {
		this.busStopId = busStopId;
		this.line = line;
		this.cost = cost;
	}

	public BusStop getBusStop() {
		//TODO performance killer
		BusStopService busStopService = new BusStopServiceHibernateImpl();
		return busStopService.getBusStop(busStopId);
	}

	public BusLine getBusLine() {
		//TODO performance killer
		BusLineService busLineService = new BusLineServiceHibernateImpl();
		return busLineService.getBusLine(line);
	}

}
