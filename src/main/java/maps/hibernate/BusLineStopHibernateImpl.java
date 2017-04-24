package maps.hibernate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import maps.interfaces.BusLine;
import maps.interfaces.BusLineStop;
import maps.interfaces.BusStop;

/*
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
@Table(name="BusLineStop")
@IdClass(BusLineStopIdHibernate.class)
public class BusLineStopHibernateImpl implements BusLineStop {

	@Id
	@ManyToOne
	@JoinColumn(name = "stopId", nullable = false)
	BusStopHibernateImpl busStop;
	@Id
	@ManyToOne
	@JoinColumn(name = "lineId", nullable = false)
	BusLineHibernateImpl busLine;
	@Column(name = "sequenceNumber")
	Short sequence;
	
	public BusLineStopHibernateImpl()  {
		super();
		this.busStop = null;
		this.busLine = null;
		this.sequence = null;

	}
	
	@Override
	public BusStop getBusStop() {
		return busStop;
	}

	@Override
	public void setBusStop(BusStop bs) throws Exception {
		if ( bs == busStop ) return;
		if ( ! (bs instanceof BusStopHibernateImpl) ) throw new Exception("Invalid parameters");
		BusStopHibernateImpl bshi = (BusStopHibernateImpl) bs;
		
		BusStopHibernateImpl old = busStop;
		busStop = bshi;
		
		old.removeBusLineStop(this);
		busStop.addBusLineStop(this);
	}

	@Override
	public BusLine getBusLine() {
		return busLine;
	}

	@Override
	public void setBusLine(BusLine bl) throws Exception {
		if ( bl == busLine ) return;
		if ( ! (bl instanceof BusLineHibernateImpl) ) throw new Exception("Invalid parameters");
		BusLineHibernateImpl blhi = (BusLineHibernateImpl) bl;
		
		BusLineHibernateImpl old = busLine;
		busLine = blhi;
		
		old.removeBusLineStop(this);
		busLine.addBusLineStop(this);

	}

	@Override
	public Integer getSequenceNumber() {
		return new Integer(sequence);
	}

	@Override
	public void setSequenceNumber(Integer seq) {
		sequence = new Short(seq.shortValue());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((busLine == null) ? 0 : busLine.hashCode());
		result = prime * result + ((busStop == null) ? 0 : busStop.hashCode());
		result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
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
		BusLineStopHibernateImpl other = (BusLineStopHibernateImpl) obj;
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
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		return true;
	}
	
}
