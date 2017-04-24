package maps.hibernate;

import java.io.Serializable;

public class BusLineStopIdHibernate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	BusStopHibernateImpl busStop;
	BusLineHibernateImpl busLine;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((busLine == null) ? 0 : busLine.hashCode());
		result = prime * result + ((busStop == null) ? 0 : busStop.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusLineStopIdHibernate other = (BusLineStopIdHibernate) obj;
		if (busLine == null) {
			if (other.busLine != null)
				return false;
		} else if (!busLine.equals(other.busLine))
			return false;
		if (busStop == null) {
			if (other.busStop != null)
				return false;
		} else if (!busStop.equals(other.busStop))
			return false;
		return true;
	}
	
}
