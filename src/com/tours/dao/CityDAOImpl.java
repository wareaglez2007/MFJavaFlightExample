package com.tours.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

import com.tours.entity.City;
import com.tours.entity.Flight;
import com.tours.util.ServiceException;

public class CityDAOImpl implements CityDAO {
	// 1ts lets create some default SQL QUARIES
	// showAllCitiesFromDB (SELECt * FROM CITIES)
	private static final String SHOW_ALL_CITIES_IN_TABLE = "SELECT * from CITIES";

	// showCitiesByCountry
	private static final String SHOW_ALL_CITIES_IN_TABLE_BY_COUNTRY_CODE = "SELECT * from CITIES where COUNTRY_ISO_CODE = ?";

	@Override
	public List<City> showAllCitiesFromDB(DataSource ds) {

		if (ds == null) {
			throw new ServiceException("DataSource must be provided");
		}

		PreparedStatement getAllCitiesInfo = null;
		Connection conn = null;
		ResultSet results = null;
		List<City> data = new ArrayList<City>();
		// Set prepared statement

		try {
			conn = ds.getConnection();
			getAllCitiesInfo = conn.prepareStatement(SHOW_ALL_CITIES_IN_TABLE);
			results = getAllCitiesInfo.executeQuery();

			while (results.next()) {
				data.add(composeFetchedData(results));
			}
		} catch (SQLException sqle) {
			throw new ServiceException(sqle);
		} finally {
			DbUtils.closeQuietly(conn, getAllCitiesInfo, results);
		}
		return data;

	}

	@Override
	public List<City> showCitiesByCountryCode(DataSource ds, String country) {
		if (ds == null) {
			throw new ServiceException("DataSource must be provided");
		}
		if (country == null || country.isEmpty()) {
			throw new ServiceException("Country CIC must be provided");
		}
		if (country.length() > 2) {
			throw new ServiceException("CIC must be 2 letter charecter");
		}
		PreparedStatement getCitiesByCIC = null;
		Connection conn = null;
		ResultSet results = null;
		List<City> data = new ArrayList<City>();
		try {
			conn = ds.getConnection();
			getCitiesByCIC = conn
					.prepareStatement(SHOW_ALL_CITIES_IN_TABLE_BY_COUNTRY_CODE);
			getCitiesByCIC.setString(1, country);
			results = getCitiesByCIC.executeQuery();

			while (results.next()) {
				data.add(composeFetchedData(results));
			}
		} catch (SQLException sqle) {
			throw new ServiceException(sqle);
		} finally {
			DbUtils.closeQuietly(conn, getCitiesByCIC, results);
		}

		return data;
	}

	private City composeFetchedData(ResultSet results) throws ServiceException {
		/**
		 * CREATE TABLE CITIES ( CITY_ID INTEGER NOT NULL , CITY_NAME
		 * VARCHAR(24) NOT NULL, COUNTRY VARCHAR(26) NOT NULL, AIRPORT
		 * VARCHAR(3), LANGUAGE VARCHAR(16), COUNTRY_ISO_CODE CHAR(2) );
		 * 
		 * 
		 * ALTER TABLE CITIES ADD CONSTRAINT CITIES_PK Primary Key ( CITY_ID);
		 * 
		 * ALTER TABLE CITIES ADD CONSTRAINT COUNTRIES_FK Foreign Key (
		 * COUNTRY_ISO_CODE) REFERENCES COUNTRIES ( COUNTRY_ISO_CODE)
		 */
		try {
			City record = new City();
			record.setCity_id(results.getInt("CITY_ID"));
			record.setCity_name(results.getString("CITY_NAME"));
			record.setCountry(results.getString("COUNTRY"));
			record.setAirport(results.getString("AIRPORT"));
			record.setCic(results.getString("COUNTRY_ISO_CODE"));

			return record;
		} catch (Exception sqle) {
			throw new ServiceException(sqle);
		}

	}

}
