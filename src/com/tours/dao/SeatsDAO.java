package com.tours.dao;

import java.util.Date;

import javax.sql.DataSource;

import com.tours.entity.Flight;
import com.tours.entity.Seats;
import com.tours.util.ServiceException;

public interface SeatsDAO
{
	public Seats getSoldSeatsByFlight(DataSource ds, String flightId ,int segmentNum, Date flightDate)
			throws ServiceException;
	
	public Seats getSoldSeatsByFlight(DataSource ds, Flight flight, Date flightDate)
			throws ServiceException;
}
