package com.tours.test.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlWriter;

import com.tours.appl.StartDBServer;


public class ExtractDataFromDatabase
{
	private static String exportDir = "test/com/tours/test/data";
	
	private static final String DTD_NAME = "tours.dtd";
	private static final String DTD_FILE = exportDir + File.separator + DTD_NAME;
	private static final String FLIGHTS_FILE = exportDir + File.separator + "flights.xml";
	private static final String SEATS_FILE = exportDir + File.separator + "seats.xml";
	
	public static void main(String[] args) throws Exception
	{
		// database connection
		StartDBServer server = new StartDBServer();
		server.start();
		Connection jdbcConnection = server.getConnection();
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
		DatabaseConfig config = connection.getConfig(); 
		config.setProperty(DatabaseConfig.FEATURE_DATATYPE_WARNING, false);  
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new DefaultDataTypeFactory());                  
      
        // write DTD file
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream(DTD_FILE));

		// flights database export
        FlatXmlWriter flightsWriter = new FlatXmlWriter(new FileOutputStream(FLIGHTS_FILE));
        flightsWriter.setDocType(DTD_NAME); 
		QueryDataSet flightsDataSet = new QueryDataSet(connection);
		flightsDataSet.addTable("FLIGHTS");
		flightsDataSet.addTable("CITIES");
		flightsWriter.write(flightsDataSet);
		
		// seats database export
        FlatXmlWriter seatsWriter = new FlatXmlWriter(new FileOutputStream(SEATS_FILE));
        seatsWriter.setDocType(DTD_NAME); 
		QueryDataSet seatsDataSet = new QueryDataSet(connection);
		seatsDataSet.addTable("FLIGHTAVAILABILITY");
		seatsWriter.write(seatsDataSet);
		
		server.stop();
	}
}
