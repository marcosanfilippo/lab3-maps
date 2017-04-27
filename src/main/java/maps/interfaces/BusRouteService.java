package maps.interfaces;

import java.util.List;

import maps.route.MinPath;

public interface BusRouteService {

	public MinPath getRoute(BusStop from, BusStop to);
	
	public MinPath getRoute(List<BusStop> from, List<BusStop> to);

}
