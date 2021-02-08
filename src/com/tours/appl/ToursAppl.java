package com.tours.appl;

import java.util.List;

import javax.sql.DataSource;

import com.tours.dao.CityDAO;
import com.tours.dao.CityDAOImpl;
import com.tours.dao.FlightDAO;
import com.tours.dao.FlightDAOImpl;
import com.tours.entity.City;
import com.tours.entity.Flight;

public class ToursAppl {

	public static void main(String[] args) throws Exception {
		StartDBServer server = new StartDBServer();
		server.start();

		String boston = "BOS";
		FlightDAO flightsDB = new FlightDAOImpl();
		DataSource ds = server.getDataSource();

		List<Flight> flightsFromBoston = flightsDB.findFlightsByOriginAirport(
				ds, boston);

		for (Flight current : flightsFromBoston) {
			System.out.println(current);
		}
		// New to do:
		//
		// Java Database Assignment 1:
		//
		// Using my Flights Example
		//
		// Please add the following....
		//
		//
		// The User would now like to see:
		//
		// a list of Cities
		//
		// add the following Java code
		//
		// City.java -> An Entity class
		// CityDAO -> An Action Interface
		// CityDAOImpl -> The Action Implementation
		//
		// modify the main method to display the cities

		// What I need?
		// Entity object for City
		//City cities = new City(); //I wil use this later.
		// Interface DAO = Database Access Object
		// DAO Implementation class
		CityDAO citiesDB = new CityDAOImpl();
		// The interface will need to have methods
		// First lets query all the cities names from Cities table
		List<City> showAllCities = citiesDB.showAllCitiesFromDB(ds);
		System.out.println("----------------SHOW ALL CITIES------------------------");
		for (City city : showAllCities) {
			System.out.println(city);
		}
		String country = "US";
		// Later we can add more methods...
		List<City> showCitiesSelectedCIC = citiesDB.showCitiesByCountryCode(
				ds, country);
		System.out.println("--------------SEARCH BY COUNTRY-----------------------");
		for (City citybyCIC : showCitiesSelectedCIC) {
			System.out.println(citybyCIC);
		}
		server.stop();

	}

}
