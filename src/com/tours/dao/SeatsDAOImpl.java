package com.tours.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

import com.tours.entity.Flight;
import com.tours.entity.Seats;
import com.tours.util.ServiceException;

public class SeatsDAOImpl
	implements SeatsDAO
{
	 private static final String FIND_BY_FLIGHT_DETAILS = 
	            "Select * from FLIGHTAVAILABILITY " +
	            "where FLIGHT_ID = ? and " +
	                  "SEGMENT_NUMBER = ? and " +
	                  "FLIGHT_DATE = ?";


	@Override
	public Seats getSoldSeatsByFlight(DataSource ds, String flightId, int segmentNum, Date flightDate)
			throws ServiceException
	{
		if (ds == null)
		{
			throw new ServiceException("DataSource must be provided");
		}

		if (flightId == null || flightId.isEmpty())
		{
			throw new ServiceException("Flight Id must be provided");
		}
		
		if (segmentNum < 1)
		{
			throw new ServiceException("Segment Number must be a postive integer");
		}
		
		if (flightDate == null)
		{
			throw new ServiceException("Flight Date must be provided");
		}
		
		PreparedStatement findByFlightDetails = null;
        Connection conn = null;
        ResultSet results = null;
        Seats seats = null;
        try
        {
            conn = ds.getConnection();
            findByFlightDetails = conn.prepareStatement(FIND_BY_FLIGHT_DETAILS);


            findByFlightDetails.setString(1, flightId);
            findByFlightDetails.setInt(2, segmentNum);
            java.sql.Date sqlFlightDate = new java.sql.Date(flightDate.getTime());
            findByFlightDetails.setDate(3, sqlFlightDate);
            
            results = findByFlightDetails.executeQuery();

            if (results.next())
            {
                seats = buildSeats(results);
            }
        }
        catch (SQLException sqle)
        {
            throw new ServiceException(sqle);
        }
        finally
        {
            DbUtils.closeQuietly(conn, findByFlightDetails, results);
        }
        return seats;
	}

	@Override
	public Seats getSoldSeatsByFlight(DataSource ds, Flight flight, Date flightDate)
			throws ServiceException
	{
		if (ds == null)
		{
			throw new ServiceException("DataSource must be provided");
		}

		if (flight == null)
		{
			throw new ServiceException("Flight must be provided");
		}
		
		if (flightDate == null)
		{
			throw new ServiceException("Flight Date must be provided");
		}
		
		PreparedStatement findByFlightDetails = null;
        Connection conn = null;
        ResultSet results = null;
        Seats seats = null;
        try
        {
            conn = ds.getConnection();
            findByFlightDetails = conn.prepareStatement(FIND_BY_FLIGHT_DETAILS);


            findByFlightDetails.setString(1, flight.getFlightId());
            findByFlightDetails.setInt(2, flight.getSegmentNumber());
            java.sql.Date sqlFlightDate = new java.sql.Date(flightDate.getTime());
            findByFlightDetails.setDate(3, sqlFlightDate);
            
            results = findByFlightDetails.executeQuery();

            if (results.next())
            {
                seats = buildSeats(results);
            }
        }
        catch (SQLException sqle)
        {
            throw new ServiceException(sqle);
        }
        finally
        {
            DbUtils.closeQuietly(conn, findByFlightDetails, results);
        }
        return seats;
	}
	
	private Seats buildSeats(final ResultSet results) 
			throws ServiceException
	{
		try
		{
			Seats record = new Seats();

			record.setFlightId(results.getString("FLIGHT_ID"));
			record.setSegmentNumber(results.getInt("SEGMENT_NUMBER"));
			record.setFlightDate(results.getDate("FLIGHT_DATE"));
			record.setEconomySeats(results.getInt("ECONOMY_SEATS_TAKEN"));
			record.setBusinessSeats(results.getInt("BUSINESS_SEATS_TAKEN"));
			record.setFirstClassSeats(results.getInt("FIRSTCLASS_SEATS_TAKEN"));
			return record;
		}
		catch (Exception sqle)
		{
			throw new ServiceException(sqle);
		}
	}
}
