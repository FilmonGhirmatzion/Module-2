package com.techelevator.dao.city;

import java.util.List;

public interface CityDAO {

	public void save(City newCity);
	public City findCityById(long id);
	public List<City> findCityByCountryCode(String countryCode);
	public List<City> findCityByDistrict(String district, String countrycode);
	public void update(City city);
	public void delete(long id);
}
