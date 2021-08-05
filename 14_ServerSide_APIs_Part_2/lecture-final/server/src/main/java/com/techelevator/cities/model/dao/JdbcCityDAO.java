package com.techelevator.cities.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.cities.model.City;


public class JdbcCityDAO implements CityDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcCityDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<City> list() {
        List<City> cities = new ArrayList<City>();
        String sql = "SELECT id, name, countrycode, district, population FROM city";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
        while (rows.next()) {
            cities.add(mapRowToCity(rows));
        }

        return cities;
    }

    @Override
    public City get(long cityId) {
        City city = null;
        String sql = "SELECT id, name, countrycode, district, population FROM city WHERE id = ?";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, cityId);
        if (rows.next()) {
            city = mapRowToCity(rows);
        }

        return city;
    }

    @Override
    public List<City> searchByCountryCode(String countryCode) {
        List<City> cities = new ArrayList<City>();
        String sql = "SELECT id, name, countrycode, district, population FROM city WHERE countryCode = ?";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, countryCode);
        while (rows.next()) {
            cities.add(mapRowToCity(rows));
        }

        return cities;
    }

    @Override
    public City add(City city) {
        Long id = jdbcTemplate.queryForObject("INSERT INTO city (id, name, countrycode, district, population) VALUES (DEFAULT, ?, ?, ?, ?) RETURNING id",
                Long.class, city.getName(), city.getCountryCode(), city.getDistrict(), city.getPopulation());
        city.setId(id);
        return city;
    }

    @Override
    public City update(City city) {
        jdbcTemplate.update("UPDATE city SET name = ?, countrycode = ?, district = ?, population = ? WHERE id = ?",
                city.getName(), city.getCountryCode(), city.getDistrict(), city.getPopulation(), city.getId());
        return city;
    }

    @Override
    public void delete(long cityId) {
        jdbcTemplate.update("DELETE FROM city WHERE id = ?", cityId);
    }

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

