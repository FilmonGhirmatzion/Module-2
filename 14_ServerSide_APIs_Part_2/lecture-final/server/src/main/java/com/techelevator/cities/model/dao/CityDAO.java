package com.techelevator.cities.model.dao;

import com.techelevator.cities.model.City;

import java.util.List;

public interface CityDAO {

    List<City> list();
    City get(long cityId);
    List<City> searchByCountryCode(String countryCode);
    City add(City city);
    City update(City city);
    void delete(long cityId);
}