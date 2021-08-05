package com.techelevator.dao.city;

import java.util.List;

public interface CityDAO {

	 void save(City newCity);
	 City findCityById(long id);
	 List<City> findCityByCountryCode(String countryCode);
	 List<City> findCityByDistrict(String district, String countryCode);
	 void update(City city);
	 void delete(long id);
}
