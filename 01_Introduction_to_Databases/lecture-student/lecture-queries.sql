-- SELECT ... FROM
-- Selecting the names for all countries
SELECT name             -- SELECT  is the colum (filter the column to be returend
 FROM country;          -- From is the table to get the data from

-- Selecting the name and population of all countries
SELECT name, population
FROM country;
-- Selecting all columns from the city table
SELECT *
FROM city;
-- SELECT ... FROM ... WHERE
-- Selecting the cities in Ohio


-- Selecting countries that gained independence in the year 1776


-- Selecting countries not in Asia
SELECT name AS city_Name  FROM city;

-- Selecting countries that do not have an independence year

-- Selecting countries that do have an independence year
SELECT *
FROM city
WHERE district = 'Ohio';

-- Selecting countries that have a population greater than 5 million
SELECT *
FROM country
WHERE indepyear = 1776;


-- SELECT ... FROM ... WHERE ... AND/OR
-- Selecting cities in Ohio and Population greater than 400,000

-- Selecting country names on the continent North America or South America




-- SELECTING DATA w/arithmetic
-- Selecting the population, life expectancy, and population per area
--	note the use of the 'as' keyword



