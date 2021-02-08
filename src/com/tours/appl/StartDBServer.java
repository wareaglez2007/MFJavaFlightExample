package com.tours.appl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class StartDBServer {
	
	private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
	private static final String URL = "jdbc:derby:jar:";
	private static final String USER = null;
	private static final String PASSWORD = null;
	
	private static final String DB_JAR_NAME = "toursdb.jar";
	private static final String DB_NAME = "toursdb";
	private static final String DB_PATH = System.getProperty("user.dir");
	private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
	private static final String DERBY_TEMP = "derby.storage.tempDirectory";
	private static final String DERBY_LOG = "derby.stream.error.file";
	private static final String DERBY_LOG_NAME = "toursdb.log";
	
	private Connection conn = null;
	private String realURL = null;
	private BasicDataSource ds = null;
	
	public StartDBServer() 
			throws ClassNotFoundException
	{
		Properties props = System.getProperties();
		props.setProperty(DERBY_TEMP, TEMP_DIR);
		props.setProperty(DERBY_LOG, TEMP_DIR + File.separator + DERBY_LOG_NAME);
		
		realURL = URL + "(" + DB_PATH + File.separator + 
				DB_JAR_NAME + ")" + DB_NAME;
		
		Class.forName(DRIVER);	
		
		ds = new BasicDataSource();
	}
	
	public void start()
			throws SQLException
	{
		ds.setDriverClassName(DRIVER);
        ds.setUrl(realURL);
        ds.setUsername(USER);
        ds.setPassword(PASSWORD);
        
		conn = ds.getConnection();
	}
	
	public Connection getConnection() 
			throws SQLException
	{
		return ds.getConnection();
	}
	
	public DataSource getDataSource()
	{
		return ds;
	}

	public void stop() 
			throws SQLException
	{
		conn.close();
	}
}
