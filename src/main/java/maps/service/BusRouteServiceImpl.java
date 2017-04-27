package maps.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import maps.interfaces.BusRouteService;
import maps.interfaces.BusStop;
import maps.interfaces.BusStopService;
import maps.route.Edge;
import maps.route.MinPath;

public class BusRouteServiceImpl implements BusRouteService {

	@Override
	public MinPath getRoute(BusStop from, BusStop to) {
		return getRoute(from,to,Integer.MAX_VALUE);
	}

	public MinPath getRoute(BusStop from, BusStop to, int maxCost) {
		//TODO real implementation
		
		
		
		//Dummy
		BusStopService busStopService = new BusStopServiceHibernateImpl();
		
		MinPath mp = new MinPath();
		mp.setIdSource(from.getId());
		mp.setIdDestination(from.getId());
		
		int i=0;
		int cost=0;
		String line;
		List<Edge> list = new ArrayList<>();
		
		for(Entry<String, BusStop> e : busStopService.getAll().entrySet())
		{
			BusStop bs = e.getValue();
			
			if ( i%7 == 0 && i != 0)
			{
				line = null;
				cost += 100;
			}
			else
			{
				if (i%3== 0 ) line = "10N";
				else line="1C";
				cost += 10;
			}
			
			list.add(new Edge(bs.getId(),line,cost));
			
			i++;
			
			if ( i == 100) break;
		}
		mp.setEdges(list);
		mp.setTotalCost(cost);
		
		return mp;
	}
	
	@Override
	public MinPath getRoute(List<BusStop> from, List<BusStop> to) {
		MinPath min=null;
		int actualMin=Integer.MAX_VALUE;
		for(BusStop src : from)
		{
			for(BusStop dst: to)
			{
				MinPath actual = getRoute(src,dst,actualMin);
				
				if ( actual != null )
				{
					if ( min == null || actual.getTotalCost() < min.getTotalCost())
					{
						min = actual;
						actualMin = min.getTotalCost();
					}
				}
			}
		}
		return min;
	}

}
