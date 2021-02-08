package com.tours.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

import com.tours.entity.Flight;
import com.tours.util.ServiceException;

public class FlightDAOImpl
	implements FlightDAO
{
	 private static final String FIND_BY_ORIG_AIRPORT = 
	            "Select * from FLIGHTS where ORIG_AIRPORT = ?";
	 private static final String FIND_BY_DEST_AIRPORT = 
	            "Select * from FLIGHTS where DEST_AIRPORT = ?";
	 private static final String FIND_BY_ORIG_CITY = 
	            "Select * from FLIGHTS F " +
                "  inner join CITIES C " +
                " on F.ORIG_AIRPORT = C.AIRPORT " +
                " where C.CITY_NAME = ?";
	 private static final String FIND_BY_DEST_CITY = 
	            "Select * from FLIGHTS F " +
                "  inner join CITIES C " +
                " on F.DEST_AIRPORT = C.AIRPORT " +
                " where C.CITY_NAME = ?";
	 
	@Override
	public List<Flight> findFlightsByOriginAirport(DataSource ds, String originAirport)
		throws ServiceException
	{
		if (ds == null)
		{
			throw new ServiceException("DataSource must be provided");
		}
		

		if (originAirport == null || originAirport.isEmpty())
		{
			throw new ServiceException("Origin Airport must be provided");
		}
		PreparedStatement findByOriginAirport = null;
        Connection conn = null;
        ResultSet results = null;
        List<Flight> data = new ArrayList<Flight>();
        try
        {
            conn = ds.getConnection();
            findByOriginAirport = conn.prepareStatement(FIND_BY_ORIG_AIRPORT);

            findByOriginAirport.setString(1, originAirport);
            results = findByOriginAirport.executeQuery();

            while (results.next())
            {
                data.add(buildFlight(results));
            }
        }
        catch (SQLException sqle)
        {
            throw new ServiceException(sqle);
        }
        finally
        {
            DbUtils.closeQuietly(conn, findByOriginAirport, results);
        }
        return data;
	}
	
	@Override
	public List<Flight> findFlightsByDestinationAirport(DataSource ds, String destinationAirport) 
			throws ServiceException
	{
		if (ds == null)
		{
			throw new ServiceException("DataSource must be provided");
		}
		

		if (destinationAirport == null || destinationAirport.isEmpty())
		{
			throw new ServiceException("Destination Airport must be provided");
		}
		PreparedStatement findByDestAirport = null;
        Connection conn = null;
        ResultSet results = null;
        List<Flight> data = new ArrayList<Flight>();
        try
        {
            conn = ds.getConnection();
            findByDestAirport = conn.prepareStatement(FIND_BY_DEST_AIRPORT);

            findByDestAirport.setString(1, destinationAirport);
            results = findByDestAirport.executeQuery();

            while (results.next())
            {
                data.add(buildFlight(results));
            }
        }
        catch (SQLException sqle)
        {
            throw new ServiceException(sqle);
        }
        finally
        {
            DbUtils.closeQuietly(conn, findByDestAirport, results);
        }
        return data;
	}
	
	@Override
	public List<Flight> findFlightsByOriginCity(DataSource ds, String originCity) 
			throws ServiceException
	{
		if (ds == null)
		{
			throw new ServiceException("DataSource must be provided");
		}
		

		if (originCity == null || originCity.isEmpty())
		{
			throw new ServiceException("Origin City must be provided");
		}
		PreparedStatement findByOriginCity = null;
        Connection conn = null;
        ResultSet results = null;
        List<Flight> data = new ArrayList<Flight>();
        try
        {
            conn = ds.getConnection();
            findByOriginCity = conn.prepareStatement(FIND_BY_ORIG_CITY);

            findByOriginCity.setString(1, originCity);
            results = findByOriginCity.executeQuery();

            while (results.next())
            {
                data.add(buildFlight(results));
            }
        }
        catch (SQLException sqle)
        {
            throw new ServiceException(sqle);
        }
        finally
        {
            DbUtils.closeQuietly(conn, findByOriginCity, results);
        }
        return data;
	}

	@Override
	public List<Flight> findFlightsByDestinationCity(DataSource ds, String destinationCity)
			throws ServiceException
	{
		if (ds == null)
		{
			throw new ServiceException("DataSource must be provided");
		}
		

		if (destinationCity == null || destinationCity.isEmpty())
		{
			throw new ServiceException("Destination City must be provided");
		}
		PreparedStatement findByDestCity = null;
        Connection conn = null;
        ResultSet results = null;
        List<Flight> data = new ArrayList<Flight>();
        try
        {
            conn = ds.getConnection();
            findByDestCity = conn.prepareStatement(FIND_BY_DEST_CITY);

            findByDestCity.setString(1, destinationCity);
            results = findByDestCity.executeQuery();

            while (results.next())
            {
                data.add(buildFlight(results));
            }
        }
        catch (SQLException sqle)
        {
            throw new ServiceException(sqle);
        }
        finally
        {
            DbUtils.closeQuietly(conn, findByDestCity, results);
        }
        return data;
	}
	
	private Flight buildFlight(final ResultSet results) 
        throws ServiceException
    {
        try
        {
            Flight record = new Flight();

            record.setFlightId(results.getString("FLIGHT_ID"));
            record.setSegmentNumber(results.getInt("SEGMENT_NUMBER"));
            record.setOriginAirport(results.getString("ORIG_AIRPORT"));
            record.setDepartTime(results.getTime("DEPART_TIME"));
            record.setDestinationAirport(results.getString("DEST_AIRPORT"));
            record.setArrivalTime(results.getTime("ARRIVE_TIME"));
            record.setMeal(results.getString("MEAL").trim().charAt(0));
            record.setFlightTime(results.getDouble("FLYING_TIME"));
            record.setMiles(results.getInt("Miles"));
            record.setAircraft(results.getString("AIRCRAFT"));
            return record;
        }
        catch (Exception sqle)
        {
            throw new ServiceException(sqle);
        }
    }

}
