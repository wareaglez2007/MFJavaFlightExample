package com.tours.dao;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tours.entity.Flight;
import com.tours.test.utils.DBUnitJDBCUtility;
import com.tours.util.ServiceException;

public class FlightDAOTest
{
	private static DBUnitJDBCUtility testDatabase = null;
	private static final String FLIGHTS_FILE = "com/tours/test/data/flights.xml";
	private static final String FLIGHTS_DDL_FILE = "com/tours/test/ddl/Flights.sql";

	@BeforeClass
	public static void startUp()
	{
		try
		{
			testDatabase = new DBUnitJDBCUtility(FLIGHTS_DDL_FILE, FLIGHTS_FILE);
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
	}

	
	@Test(expected=ServiceException.class)  
	public void testFindByOriginAirportNullDataSource() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();

		handler.findFlightsByOriginAirport(null, "BOS");
	}
	
	@Test(expected=ServiceException.class)  
	public void testFindByOriginAirportNullAirport() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();
		
		DataSource ds = testDatabase.getDataSource();
		handler.findFlightsByOriginAirport(ds, null);
	}
	
	@Test(expected=ServiceException.class)  
	public void testFindByOriginAirportEmptyAirport() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();
		
		DataSource ds = testDatabase.getDataSource();
		handler.findFlightsByOriginAirport(ds, "");
	}
	
	@Test
	public void testFindByOriginAirport() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();

		DataSource ds = testDatabase.getDataSource();
		List<Flight> results = null;
		results = handler.findFlightsByOriginAirport(ds, "BOS");
		int expected = 6;
		int actual = results.size();
		assertEquals(expected, actual);
		
		Flight sample = results.get(3);
		String expectedFlightId = "AA1185";
		String acutalFlightID = sample.getFlightId();
		assertEquals(expectedFlightId, acutalFlightID);
	}
	
	@Test
	public void testFindByOriginAirportInvalidAirport() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();

		DataSource ds = testDatabase.getDataSource();
		List<Flight> results = null;
		results = handler.findFlightsByOriginAirport(ds, "XXX");
		int expected = 0;
		int actual = results.size();
		assertEquals(expected, actual);
	}

	
	@Test(expected=ServiceException.class)  
	public void testFindByDestAirportNullDataSource() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();

		handler.findFlightsByDestinationAirport(null, "BOS");
	}
	
	@Test(expected=ServiceException.class)  
	public void testFindByDestAirportNullAirport() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();
		
		DataSource ds = testDatabase.getDataSource();
		handler.findFlightsByDestinationAirport(ds, null);
	}

	@Test(expected=ServiceException.class)  
	public void testFindByDestAirportEmptyAirport() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();
		
		DataSource ds = testDatabase.getDataSource();
		handler.findFlightsByDestinationAirport(ds, "");
	}
	
	@Test
	public void testFindByDestinationAirport() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();

		DataSource ds = testDatabase.getDataSource();
		List<Flight> results = null;
		results = handler.findFlightsByDestinationAirport(ds, "BOS");
		int expected = 6;
		int actual = results.size();
		assertEquals(expected, actual);
		
		Flight sample = results.get(3);
		String expectedFlightId = "AA1186";
		String acutalFlightID = sample.getFlightId();
		assertEquals(expectedFlightId, acutalFlightID);
	}
	
	@Test
	public void testFindByDestAirportInvalidAirport() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();

		DataSource ds = testDatabase.getDataSource();
		List<Flight> results = null;
		results = handler.findFlightsByDestinationAirport(ds, "XXX");
		int expected = 0;
		int actual = results.size();
		assertEquals(expected, actual);
	}

	
	@Test(expected=ServiceException.class)  
	public void testFindByOriginCityNullDataSource() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();

		handler.findFlightsByOriginCity(null, "Boston");
	}
	
	@Test(expected=ServiceException.class)  
	public void testFindByOriginCityNullCity() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();
		
		DataSource ds = testDatabase.getDataSource();
		handler.findFlightsByOriginCity(ds, null);
	}
	
	@Test(expected=ServiceException.class)  
	public void testFindByOrginCityEmptyCity() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();
		
		DataSource ds = testDatabase.getDataSource();
		handler.findFlightsByOriginCity(ds, "");
	}

	@Test
	public void testFindByOriginCity() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();

		DataSource ds = testDatabase.getDataSource();
		List<Flight> results = null;
		results = handler.findFlightsByOriginCity(ds, "Boston");
		int expected = 6;
		int actual = results.size();
		assertEquals(expected, actual);
		
		Flight sample = results.get(3);
		String expectedFlightId = "AA1185";
		String acutalFlightID = sample.getFlightId();
		assertEquals(expectedFlightId, acutalFlightID);
	}

	@Test
	public void testFindByOriginCityInvalidCity() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();

		DataSource ds = testDatabase.getDataSource();
		List<Flight> results = null;
		results = handler.findFlightsByOriginCity(ds, "Reading");
		int expected = 0;
		int actual = results.size();
		assertEquals(expected, actual);
	}
	
	@Test(expected=ServiceException.class)  
	public void testFindByDestCityNullDataSource() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();

		handler.findFlightsByDestinationCity(null, "Boston");
	}
	
	@Test(expected=ServiceException.class)  
	public void testFindByDestCityNullCity() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();
		
		DataSource ds = testDatabase.getDataSource();
		handler.findFlightsByDestinationCity(ds, null);
	}

	@Test(expected=ServiceException.class)  
	public void testFindByDestCityEmptyCity() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();
		
		DataSource ds = testDatabase.getDataSource();
		handler.findFlightsByDestinationCity(ds, "");
	}
	
	@Test
	public void testFindByDestinationCity() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();

		DataSource ds = testDatabase.getDataSource();
		List<Flight> results = null;
		results = handler.findFlightsByDestinationCity(ds, "Boston");
		int expected = 6;
		int actual = results.size();
		assertEquals(expected, actual);
		
		Flight sample = results.get(3);
		String expectedFlightId = "AA1186";
		String acutalFlightID = sample.getFlightId();
		assertEquals(expectedFlightId, acutalFlightID);
	}
	
	@Test
	public void testFindByDestCityInvalidCity() 
			throws Exception
	{
		FlightDAO handler = new FlightDAOImpl();

		DataSource ds = testDatabase.getDataSource();
		List<Flight> results = null;
		results = handler.findFlightsByDestinationCity(ds, "Reading");
		int expected = 0;
		int actual = results.size();
		assertEquals(expected, actual);
	}

}
