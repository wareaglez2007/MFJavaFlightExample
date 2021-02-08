package com.tours.entity;

import java.io.Serializable;
import java.util.Date;

public class Flight 
  implements Serializable
{
	/*
	CREATE TABLE FLIGHTS
	   (
	      FLIGHT_ID CHAR(6) NOT NULL ,
	      SEGMENT_NUMBER INTEGER NOT NULL ,
	      ORIG_AIRPORT CHAR(3),
	      DEPART_TIME TIME,
	      DEST_AIRPORT CHAR(3),
	      ARRIVE_TIME TIME,
	      MEAL CHAR(1),
	      FLYING_TIME DOUBLE PRECISION,
	      MILES INTEGER,
	      AIRCRAFT VARCHAR(6)
	   );*/
	
	private static final long serialVersionUID = 1L;

	private String flightId;
	private int segmentNumber;
	private String originAirport;
	private Date departTime;
	private String destinationAirport ;
	private Date arrivalTime;
	private char meal;
	private double flightTime;
	private int miles;
	private String aircraft;
	
	public Flight() 
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

	public String getOriginAirport()
	{
		return originAirport;
	}

	public void setOriginAirport(String originAirport)
	{
		this.originAirport = originAirport;
	}

	public Date getDepartTime()
	{
		return departTime;
	}

	public void setDepartTime(Date departTime)
	{
		this.departTime = departTime;
	}

	public String getDestinationAirport()
	{
		return destinationAirport;
	}

	public void setDestinationAirport(String destinationAirport)
	{
		this.destinationAirport = destinationAirport;
	}

	public Date getArrivalTime()
	{
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime)
	{
		this.arrivalTime = arrivalTime;
	}

	public char getMeal()
	{
		return meal;
	}

	public void setMeal(char meal)
	{
		this.meal = meal;
	}

	public double getFlightTime()
	{
		return flightTime;
	}

	public void setFlightTime(double flightTime)
	{
		this.flightTime = flightTime;
	}

	public int getMiles()
	{
		return miles;
	}

	public void setMiles(int miles)
	{
		this.miles = miles;
	}

	public String getAircraft()
	{
		return aircraft;
	}

	public void setAircraft(String aircraft)
	{
		this.aircraft = aircraft;
	}

	@Override
	public String toString()
	{
		return "Flight [flightId=" + flightId + ", originAirport="
				+ originAirport + ", destinationAirport=" + destinationAirport
				+ "]";
	}
}
