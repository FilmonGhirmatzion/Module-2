package com.techelevator.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.dao.city.City;
import com.techelevator.dao.city.CityDAO;
import com.techelevator.dao.city.JDBCCityDAO;

import java.util.List;

public class DAOExample {

	public static void main(String[] args) {

		/*
		  1) Create the DataSource
		 */
		BasicDataSource worldDataSource = new BasicDataSource();
		worldDataSource.setUrl("jdbc:postgresql://localhost:5432/world");
		worldDataSource.setUsername("postgres");
		worldDataSource.setPassword("postgres1");

		/*
			2) Instantiate the Jdbc Class and pass it the datasource
			   NOTE that the variable is declared with the Interface
		 */
		CityDAO dao = new JDBCCityDAO(worldDataSource);

		/*
			3) Use the methods provided by the DAO with the Data Object to manipulate the database
		 */

		City smallville = new City();
		smallville.setCountryCode("USA");
		smallville.setDistrict("KS");
		smallville.setName("Smallville");
		smallville.setPopulation(42080);

		// INSERT the City
		dao.save(smallville);

		// SELECT: Retrieve the City using the Id, which was set to the City object by the save() method
		City theCity = dao.findCityById(smallville.getId());
		System.out.println("City found by Id: " + theCity);

		// SELECT: Retrieve all Cities in a country code
		List<City> cities = dao.findCityByCountryCode("GBR");
		System.out.println("Cities found by Country Code: " + cities );

		// SELECT: Retrieve all Cities with a specific Country Code and District
		List<City> citiesInOhio = dao.findCityByDistrict("Ohio", "USA");
		System.out.println("Cities in Ohio " + citiesInOhio );

		// UPDATE a city
		smallville.setPopulation(1);
		dao.update(smallville);

		// This code is getting the city again from the database so we can see the updated value
		City cityAfterUpdate = dao.findCityById(smallville.getId());
		System.out.println("City after Update: " + cityAfterUpdate);

		// DELETE a City
		dao.delete( smallville.getId() );

		// This code is getting the city again to show what is returned when no city is found!
		City cityAfterDelete = dao.findCityById( smallville.getId() );
		System.out.println("City after delete: " + cityAfterDelete);
		
	}

}
