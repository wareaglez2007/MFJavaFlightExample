package com.tours.entity;

import java.io.Serializable;
import java.util.Date;

public class Seats implements Serializable
{

  /*
	CREATE TABLE FLIGHTAVAILABILITY
     (
      FLIGHT_ID CHAR(6) NOT NULL ,
      SEGMENT_NUMBER INTEGER NOT NULL ,
      FLIGHT_DATE DATE NOT NULL ,
      ECONOMY_SEATS_TAKEN INTEGER DEFAULT 0,
      BUSINESS_SEATS_TAKEN INTEGER DEFAULT 0,
      FIRSTCLASS_SEATS_TAKEN INTEGER DEFAULT 0
     );
 */

	private static final long serialVersionUID = 1L;

	private String flightId;
	private int segmentNumber;
	private Date flightDate;
	private int economySeats;
	private int businessSeats;
	private int firstClassSeats;

	public Seats()
	{
		super();
	}

	public String getFlightId()
	{
		return flightId;
	}

	public void setFlightId(String flightId)
	{
		this.flightId = flightId;
	}

	public int getSegmentNumber()
	{
		return segmentNumber;
	}

	public void setSegmentNumber(int segmentNumber)
	{
		this.segmentNumber = segmentNumber;
	}

	public Date getFlightDate()
	{
		return flightDate;
	}

	public void setFlightDate(Date flightDate)
	{
		this.flightDate = flightDate;
	}

	public int getEconomySeats()
	{
		return economySeats;
	}

	public void setEconomySeats(int economySeats)
	{
		this.economySeats = economySeats;
	}

	public int getBusinessSeats()
	{
		return businessSeats;
	}

	public void setBusinessSeats(int businessSeats)
	{
		this.businessSeats = businessSeats;
	}

	public int getFirstClassSeats()
	{
		return firstClassSeats;
	}

	public void setFirstClassSeats(int firstClassSeats)
	{
		this.firstClassSeats = firstClassSeats;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	@Override
	public String toString()
	{
		return "Seats [flightId=" + flightId + ", segmentNumber="
				+ segmentNumber + ", flightDate=" + flightDate
				+ ", economySeats=" + economySeats + ", businessSeats="
				+ businessSeats + ", firstClassSeats=" + firstClassSeats + "]";
	}

}
