package maps.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import maps.interfaces.BusLineStop;
import maps.interfaces.BusStop;

/*
 * create table if not exists BusStop (
  id varchar(20) not null,
  name varchar(255) not null,
  lat double precision,
  lng double precision,
  primary key (id)
);

create table if not exists BusLineStop (
  stopId varchar(20) not null,
  lineId varchar(20) not null,
  seqenceNumber smallint not null,
  primary key(stopId, lineId),
  foreign key (stopId) references BusStop(id),
  foreign key (lineId) references BusLine(line)
);
 */

@Entity
@Table(name="BusStop")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class BusStopHibernateImpl implements BusStop {

	@Id
	@Column(name="id")
	private String id;
	@Column(name="name",nullable=false)
	private String name;
	
	//@Column(name="lat")
	//private Double lat;
	//@Column(name="lng")
	//private Double lng;
	
	@Column(name="latlng")
	//@Type(type="org.hibernate.spatial.GeometryType")
	private Point latLng;
	
	@OneToMany(mappedBy = "busStop")
	@JsonIgnore
	private List<BusLineStopHibernateImpl> busLineStops;
	
	public BusStopHibernateImpl()  {
		super();
		this.id = null;
		this.name = null;
		this.latLng = null;
		this.busLineStops = new ArrayList<BusLineStopHibernateImpl>();

	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String _id) {
			id = _id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String _name) {
		name = _name;
	}

	@Override
	public Double getLat() {
		return latLng.getCoordinate().x;
	}

	@Override
	public void setLat(Double _lat) {
		setLatLng(_lat,getLng());
	}

	@Override
	public Double getLng() {
		return latLng.getCoordinate().y;
	}

	@Override
	public void setLng(Double _lng) {
		setLatLng(getLat(),_lng);
	}
	
	@Override
	public List<BusLineStop> getBusLineStops() {
		return new ArrayList<>(busLineStops);
	}

	@Override
	public void removeBusLineStop(BusLineStop busLineStop) throws Exception {
		if (! busLineStops.contains(busLineStop)) return;
		if ( ! (busLineStop instanceof BusLineStopHibernateImpl) ) throw new Exception("Invalid parameters");
		BusLineStopHibernateImpl blshi = (BusLineStopHibernateImpl) busLineStop;
		
		busLineStops.remove(blshi);
		
		busLineStop.setBusStop(null);
		if ( busLineStop.getBusLine() != null )
		{
			busLineStop.getBusLine().removeBusLineStop(blshi);
		}
	}

	@Override
	public void addBusLineStop(BusLineStop busLineStop) throws Exception {
		if (busLineStops.contains(busLineStop)) return;
		if ( ! (busLineStop instanceof BusLineStopHibernateImpl) ) throw new Exception("Invalid parameters");
		BusLineStopHibernateImpl blshi = (BusLineStopHibernateImpl) busLineStop;
		
		busLineStops.remove(blshi);
		
		busLineStop.setBusStop(this);
		if ( busLineStop.getBusLine() != null )
		{
			busLineStop.getBusLine().addBusLineStop(blshi);
		}	
	}
	
	@Override
	public void setLatLng(Double lat, Double lng) {
		GeometryFactory geometryFactory = new GeometryFactory();
		latLng = geometryFactory.createPoint( new Coordinate( lat, lng ) );

		//latLng = Geometries.mkPoint(new G2D(lat, lng), CoordinateReferenceSystems.WGS84);
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusStopHibernateImpl other = (BusStopHibernateImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
