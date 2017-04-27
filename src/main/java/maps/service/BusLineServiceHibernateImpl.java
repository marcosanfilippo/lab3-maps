package maps.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import maps.hibernate.BusLineHibernateImpl;
import maps.interfaces.BusLine;
import maps.interfaces.BusLineService;
import maps.utils.HibernateUtil;

public class BusLineServiceHibernateImpl implements BusLineService {

	private SessionFactory sf = HibernateUtil.getSessionFactory();

	
	public BusLineServiceHibernateImpl()
	{
	}
	
	@Override
	public Map<String, BusLine> getAll() {
		HashMap<String,BusLine> ret = new HashMap<>();
		Session session=sf.getCurrentSession();
		String hql = "from maps.hibernate.BusLineHibernateImpl as o";
		@SuppressWarnings("unchecked")
		Query<BusLineHibernateImpl> query = session.createQuery(hql);
		List<BusLineHibernateImpl> lq =  query.list();
		
		for (BusLineHibernateImpl busLineHibernateImpl : lq) {
			ret.put(busLineHibernateImpl.getLine(), busLineHibernateImpl);
		}
		//session.close();

		return ret;
	}

	@Override
	public BusLine getBusLine(String line) {
		Session session=sf.getCurrentSession();
		String hql = "from maps.hibernate.BusLineHibernateImpl as o where line = :line";
		@SuppressWarnings("unchecked")
		Query<BusLineHibernateImpl> query = session.createQuery(hql);
		query.setParameter("line", line);
		try
		{
			return query.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}

	}

	public void addBusLine(BusLine bl )
	{
	}
}
