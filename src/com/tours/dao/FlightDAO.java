package com.tours.dao;

import java.util.List;

import javax.sql.DataSource;

import com.tours.entity.Flight;
import com.tours.util.ServiceException;

public interface FlightDAO
{
	public List<Flight> findFlightsByOriginAirport(DataSource dataSource, String originAirport)
		throws ServiceException;
	
	public List<Flight> findFlightsByDestinationAirport(DataSource dataSource, String destinationAirport)
			throws ServiceException;
	
	public List<Flight> findFlightsByOriginCity(DataSource dataSource, String originCity)
			throws ServiceException;
	
	public List<Flight> findFlightsByDestinationCity(DataSource dataSource, String destinationCity)
			throws ServiceException;
}
