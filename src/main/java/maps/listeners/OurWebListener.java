package maps.listeners;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import maps.hibernate.service.BusLineServiceHibernateImpl;
import maps.hibernate.service.BusStopServiceHibernateImpl;

@WebListener
public class OurWebListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		sce.getServletContext().setAttribute("radiusDistance",250.0);
		try
		{
			
			BusLineServiceHibernateImpl busLineServiceImpl = new BusLineServiceHibernateImpl();
			BusStopServiceHibernateImpl busStopServiceImpl = new BusStopServiceHibernateImpl();
		    sce.getServletContext().setAttribute("busStopService", busStopServiceImpl);
		    sce.getServletContext().setAttribute("busLineService", busLineServiceImpl);			
		}
		catch(Exception e)
		{
			throw new ExceptionInInitializerError(e);
		}
	}

}
