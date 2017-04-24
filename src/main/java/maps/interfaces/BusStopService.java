package maps.interfaces;

import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Point;


public interface BusStopService {

	public Map<String, BusStop> getAll();
	public BusStop getBusStop(String id);
	
	public List<? extends BusStop> getBusStopNear(Point location , Double radiusMeters);

	public List<? extends BusStop> getBusStopNear(Double lat , Double lng , Double radiusMeters);

}
