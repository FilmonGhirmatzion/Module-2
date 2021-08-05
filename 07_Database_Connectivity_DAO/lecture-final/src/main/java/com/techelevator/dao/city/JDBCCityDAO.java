package com.techelevator.dao.city;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCityDAO implements CityDAO {

	private JdbcTemplate jdbcTemplate;
	
	public JDBCCityDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/*
		INSERT
	*/
	@Override
	public void save(City newCity) {
		String sqlInsertCity = "INSERT INTO city(id, name, countrycode, district, population) " +
							   "VALUES(?, ?, ?, ?, ?)";
		// Calls the getNextCityId() method to get the next ID.  The sets it on the City object and uses it to
		// insert the record
		newCity.setId( getNextCityId() );
		jdbcTemplate.update(sqlInsertCity, newCity.getId(),
										  newCity.getName(),
										  newCity.getCountryCode(),
										  newCity.getDistrict(),
										  newCity.getPopulation());
	}
	
	/*
		SELECT single City by id
	*/
	@Override
	public City findCityById(long id) {
		City theCity = null;
		String sqlFindCityById = "SELECT id, name, countrycode, district, population "+
							   "FROM city "+
							   "WHERE id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindCityById, id);
		if(results.next()) {
			theCity = mapRowToCity(results);
		}
		return theCity;
	}

	/*
		SELECT List of Cities by country code
	*/
	@Override
	public List<City> findCityByCountryCode(String countryCode) {
		ArrayList<City> cities = new ArrayList<>();
		String sqlFindCityByCountryCode = "SELECT id, name, countrycode, district, population "+
										   "FROM city "+
										   "WHERE countrycode = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindCityByCountryCode, countryCode);
		while(results.next()) {
			City theCity = mapRowToCity(results);
			cities.add(theCity);
		}
		return cities;
	}

	/*
		SELECT List of Cities by district and country code
	*/
	@Override
	public List<City> findCityByDistrict(String district, String countryCode) {
		ArrayList<City> cities = new ArrayList<>();
		String sqlFindCityByCountryCode = "SELECT id, name, countrycode, district, population "+
				"FROM city "+
				"WHERE countrycode = ? AND district = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindCityByCountryCode, countryCode, district);
		while(results.next()) {
			City theCity = mapRowToCity(results);
			cities.add(theCity);
		}
		return cities;
	}

	/*
		UPDATES a City
	*/
	@Override
	public void update(City city) {
		String updateSql = "UPDATE city SET name = ?, countrycode = ?, district = ?, population = ? WHERE id = ?";
		jdbcTemplate.update(updateSql, city.getName(), city.getCountryCode(),
				city.getDistrict(), city.getPopulation(), city.getId());
	}

	/*
		DELETES a City
	*/
	@Override
	public void delete(long id) {
		String deleteSql = "DELETE FROM city WHERE id = ?";
		jdbcTemplate.update(deleteSql, id);
	}

	/*
	  Retrieves the next available Id using the Sequence
	  USED BY INSERT
	 */
	private long getNextCityId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_city_id')");
		if(nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new city");
		}
	}

	/*
		Maps the current Row in the ResultSet and uses the data to create and return the Data Object
		RESUSABLE CODE USED BY ALL METHODS THAT RETURN A CITY OR LIST OF CITIES (SELECTS) 
	*/
	private City mapRowToCity(SqlRowSet results) {
		City theCity;
		theCity = new City();
		theCity.setId(results.getLong("id"));
		theCity.setName(results.getString("name"));
		theCity.setCountryCode(results.getString("countrycode"));
		theCity.setDistrict(results.getString("district"));
		theCity.setPopulation(results.getInt("population"));
		return theCity;
	}
}
