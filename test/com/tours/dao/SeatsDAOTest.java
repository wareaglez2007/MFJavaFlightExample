package com.tours.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tours.entity.Flight;
import com.tours.entity.Seats;
import com.tours.test.utils.DBUnitJDBCUtility;
import com.tours.util.ServiceException;

public class SeatsDAOTest
{
	private static DBUnitJDBCUtility testDatabase = null;
	private static final String SEATS_FILE = "com/tours/test/data/seats.xml";
	private static final String SEATS_DDL_FILE = "com/tours/test/ddl/Seats.sql";
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	private SeatsDAO handler = null;

	@BeforeClass
	public static void startUp()
	{
		try
		{
			testDatabase = new DBUnitJDBCUtility(SEATS_DDL_FILE, SEATS_FILE);
		}
		catch (Exception e)
		{
			fail(e.getLocalizedMessage());
		}
	}  

	@AfterClass
	public static void shutdown()
	{
		testDatabase.shutdown();
	}

	@Before
	public void connect() 
			throws Exception
	{
		testDatabase.clean();
		handler = new SeatsDAOImpl();
	}
	
	@Test(expected=ServiceException.class)  
	public void testFindByOriginAirportNullDataSource() 
			throws Exception
	{
		Date date = buildDateFromString("2004-03-31");
		handler.getSoldSeatsByFlight(null, "AA1116", 1, date);
	}
	
	@Test(expected=ServiceException.class)  
	public void testFindByOriginAirportNullFlightId() 
			throws Exception
	{
		DataSource ds = testDatabase.getDataSource();
		Date date = buildDateFromString("2004-03-31");
		handler.getSoldSeatsByFlight(ds, null, 1, date);
	}
	
	@Test(expected=ServiceException.class)  
	public void testFindByOriginAirportNullDate() 
			throws Exception
	{
		DataSource ds = testDatabase.getDataSource();
		handler.getSoldSeatsByFlight(ds, "AA1116", 1, null);
	}
	
	@Test(expected=ServiceException.class)  
	public void testFindByOriginAirportInvalidSeq() 
			throws Exception
	{
		DataSource ds = testDatabase.getDataSource();
		Date date = buildDateFromString("2004-03-31");
		handler.getSoldSeatsByFlight(ds, "AA1116", 0, date);
	}
	
	@Test
	public void testSoldSeatsByFlightDetails() 
			throws Exception
	{
		DataSource ds = testDatabase.getDataSource();
		Date date = buildDateFromString("2004-04-20");
		Seats seats = handler.getSoldSeatsByFlight(ds, "AA1195", 1, date);
		assertNotNull(seats);
		int expectedEconomy = 10;
		int expectedBusiness = 0;
		int expectedFirst = 0;
		
		int actualEconomy = seats.getEconomySeats();
		int acutalBuseness = seats.getBusinessSeats();
		int actualFirst = seats.getFirstClassSeats();
		
		assertEquals(expectedEconomy, actualEconomy);
		assertEquals(expectedBusiness, acutalBuseness);
		assertEquals(expectedFirst, actualFirst);
	}
	
	@Test
	public void testSoldSeatsByFlightDetails2() 
			throws Exception
	{		  
		DataSource ds = testDatabase.getDataSource();
		Date date = buildDateFromString("2004-05-06");
		Seats seats = handler.getSoldSeatsByFlight(ds, "AA1181", 2, date);
		assertNotNull(seats);
		int expectedEconomy = 2;
		int expectedBusiness = 2;
		int expectedFirst = 2;
		
		int actualEconomy = seats.getEconomySeats();
		int acutalBuseness = seats.getBusinessSeats();
		int actualFirst = seats.getFirstClassSeats();
		
		assertEquals(expectedEconomy, actualEconomy);
		assertEquals(expectedBusiness, acutalBuseness);
		assertEquals(expectedFirst, actualFirst);
	}
	
	@Test
	public void testSoldSeatsByInvalidDate() 
			throws Exception
	{
		DataSource ds = testDatabase.getDataSource();
		Date date = buildDateFromString("2006-05-06");
		Seats seats = handler.getSoldSeatsByFlight(ds, "AA1116", 1, date);
		assertNull(seats);
	}
	

	@Test
	public void testSoldSeatsByInvalidFlight() 
			throws Exception
	{
		DataSource ds = testDatabase.getDataSource();
		Date date = buildDateFromString("2004-05-06");
		Seats seats = handler.getSoldSeatsByFlight(ds, "AA0000", 1, date);
		assertNull(seats);
	}
	
	@Test
	public void testSoldSeatsByFlight() 
			throws Exception
	{
		DataSource ds = testDatabase.getDataSource();
		Date date = buildDateFromString("2004-05-03");
		Flight flight = buildFlight("AA1203", 1);
		Seats seats = handler.getSoldSeatsByFlight(ds, flight, date);
		assertNotNull(seats);
		int expectedEconomy = 11;
		int expectedBusiness = 0;
		int expectedFirst = 0;
		
		int actualEconomy = seats.getEconomySeats();
		int acutalBuseness = seats.getBusinessSeats();
		int actualFirst = seats.getFirstClassSeats();
		
		assertEquals(expectedEconomy, actualEconomy);
		assertEquals(expectedBusiness, acutalBuseness);
		assertEquals(expectedFirst, actualFirst);
	}
	
	private Date buildDateFromString(String input)
	{
		try
		{
			return df.parse(input);
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	private static Flight buildFlight(String flightId, int seg)
	{
		Flight flight = new Flight();
		flight.setFlightId(flightId);
		flight.setSegmentNumber(seg);
		
		return flight;
	}

}
