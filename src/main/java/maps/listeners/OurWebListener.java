package maps.listeners;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import maps.service.BusLineServiceHibernateImpl;
import maps.service.BusRouteServiceImpl;
import maps.service.BusStopServiceHibernateImpl;

@WebListener
public class OurWebListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		sce.getServletContext().setAttribute("radiusDistance",250.0);
		try
		{
			
			BusLineServiceHibernateImpl busLineServiceImpl = new BusLineServiceHibernateImpl();
			BusStopServiceHibernateImpl busStopServiceImpl = new BusStopServiceHibernateImpl();
			BusRouteServiceImpl busRouteServiceImpl = new BusRouteServiceImpl();

			sce.getServletContext().setAttribute("busStopService", busStopServiceImpl);
		    sce.getServletContext().setAttribute("busLineService", busLineServiceImpl);			
		    sce.getServletContext().setAttribute("busRouteService", busRouteServiceImpl);			

		}
		catch(Exception e)
		{
			throw new ExceptionInInitializerError(e);
		}
	}

}
