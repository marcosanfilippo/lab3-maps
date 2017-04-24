package maps.hibernate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TemporalType;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import maps.hibernate.BusStopHibernateImpl;
import maps.interfaces.BusStop;
import maps.interfaces.BusStopService;
import maps.utils.HibernateUtil;

public class BusStopServiceHibernateImpl implements BusStopService {

	private SessionFactory sf = HibernateUtil.getSessionFactory();

	
	public BusStopServiceHibernateImpl()
	{
	}
	
	@Override
	public Map<String, BusStop> getAll() {
		Session session=sf.getCurrentSession();
		String hql = "from maps.hibernate.BusStopHibernateImpl as o";
		@SuppressWarnings("unchecked")
		Query<BusStopHibernateImpl> query = session.createQuery(hql);
		HashMap<String,BusStop> ret = new HashMap<String,BusStop>();
		for ( BusStopHibernateImpl b : query.getResultList() )
		{
			ret.put(b.getId(), b);
		}
		return ret;
	}

	@Override
	public BusStop getBusStop(String id) {
		return null;
	}

	public void addBusStop(BusStop bs )
	{
	}

	@Override
	public List<BusStopHibernateImpl> getBusStopNear(Point location, Double radiusMeters)
	{
		
		/*Session session=sf.getCurrentSession();
		String hql = "from maps.hibernate.BusStopHibernateImpl as o where dwithin(o.latLng, :point,:radius) = true";
		@SuppressWarnings("unchecked")
		Query<BusStopHibernateImpl> query = session.createQuery(hql);
		query.setParameter("point", location);
		query.setParameter("radius", radiusMeters);

		return query.getResultList();*/
		
		//TODO OOOOOOOOO
		Session session=sf.getCurrentSession();
		String hql = "from maps.hibernate.BusStopHibernateImpl as o where dwithin(o.latLng, ST_GeographyFromText('SRID=4326; "+location.toText()+"'),:radius) = true";
		@SuppressWarnings("unchecked")
		Query<BusStopHibernateImpl> query = session.createQuery(hql);
		//query.setParameter("point", location);
		query.setParameter("radius", radiusMeters);
		return query.getResultList();
	}
	
	@Override
	public List<BusStopHibernateImpl> getBusStopNear(Double lat , Double lng , Double radiusMeters)
	{
		GeometryFactory geometryFactory = new GeometryFactory();
		Point location = geometryFactory.createPoint( new Coordinate( lat, lng ) );

		return getBusStopNear(location,radiusMeters);
	}
}
