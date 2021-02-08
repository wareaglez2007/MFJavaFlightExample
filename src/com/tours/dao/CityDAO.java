package com.tours.dao;

import java.util.List;

import javax.sql.DataSource;

import com.tours.entity.City;
import com.tours.util.ServiceException;

public interface CityDAO {

	List<City> showAllCitiesFromDB(DataSource ds) throws ServiceException;

	List<City> showCitiesByCountryCode(DataSource ds, String country)
			throws ServiceException;

}
