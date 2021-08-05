-- The following queries utilize the "dvdstore" database.

-- 1. All of the films that Nick Stallone has appeared in
-- (30 rows)
--step 1 - how to find nick stallone
-- step 2 - how to connect Nick stalone to the film he is in
select *
from actor
join film_actor on actor.actor_id = film_actor.actor_id
join film on film_actor.film_id = film.film_id
where last_name ='STALLONE' and first_name ='NICK';


-- 2. All of the films that Rita Reynolds has appeared in
-- (20 rows)
select film.Title
from film
join film_actor on film.film_id = film_actor.film_id
join actor on film_actor.actor_id = actor.actor_id
where actor.first_name ='RITA' and actor.last_name = 'REYNOLDS';



-- 3. All of the films that Judy Dean or River Dean have appeared in
-- (46 rows)
select film.Title
from film
join film_actor on film.film_id = film_actor.film_id
join actor on film_actor.actor_id = actor.actor_id
where (actor.first_name ='JUDY' and actor.last_name = 'DEAN') or(actor.first_name = 'RIVER'   AND actor.last_name = 'DEAN');


-- 4. All of the the ‘Documentary’ films
-- (68 rows)
select film.title
from film
join film_category on film.film_id = film_category.film_id
join category on film_category.category_id = category.category_id
where category.name =  'Documentary';



-- 5. All of the ‘Comedy’ films
-- (58 rows)
select film.title
from film
join  film_category on film.film_id = film_category.film_id
join category on film_category.category_id = category.category_id
where category.name =  'Comedy';


-- 6. All of the ‘Children’ films that are rated ‘G’
-- (10 rows)
select film.title
from film
join film_category on film.film_id = film_category.film_id
join category on film_category.category_id = category.category_id
where category.name =  'Children' AND film.rating ='G';

-- 7. All of the ‘Family’ films that are rated ‘G’ and are less than 2 hours in length
-- (3 rows)
select film.title
from film
join film_category on film.film_id = film_category.film_id
join category ON film_category.category_id=category.category_id
where category.name =  'Family' and film.rating = 'G' and film.length <120;


-- 8. All of the films featuring actor Matthew Leigh that are rated ‘G’
-- (9 rows)
select film.title
from film
join film_actor ON film_actor.film_id = film.film_id
join actor on actor.actor_id = film_actor.actor_id
where actor.first_name =  'MATTHEW' and actor.last_name = 'LEIGH' and film.rating = 'G';

-- 9. All of the ‘Sci-Fi’ films released in 2006
-- (61 rows)
select film.title
from film
join film_category on film.film_id = film_category.film_id
join category on film_category.category_id = category.category_id
where category.name =  'Sci-Fi'
and film.release_year = 2006;

-- 10. All of the ‘Action’ films starring Nick Stallone
-- (2 rows)
select film.title
from film
join film_actor ON film_actor.film_id = film.film_id
join actor on actor.actor_id = film_actor.actor_id
join film_category ON film.film_id= film_category.film_id
join category ON film_category.category_id= category.category_id
where first_name = 'NICK' and last_name = 'STALLONE' And category.name = 'Action';

-- 11. The address of all stores, including street address, city, district, and country
-- (2 rows)
select address.address, address.district, city.city, country.country
from store
join address ON store.address_id = address.address_id
join city ON address.city_id = city.city_id
join country ON city.country_id = country.country_id;

-- 12. A list of all stores by ID, the store’s street address, and the name of the store’s manager
-- (2 rows)
select store.store_id, address.address, staff.first_name, staff.last_name
from store
join address ON store.address_id = address.address_id
join staff ON store.store_id = staff.store_id 
where store.manager_staff_id = staff.staff_id;

-- 13. The first and last name of the top ten customers ranked by number of rentals
-- (#1 should be “ELEANOR HUNT�? with 46 rentals, #10 should have 39 rentals)
select customer.first_name, customer.last_name, count(rental.rental_id) 
from customer
join rental ON customer.customer_id = rental.customer_id   
group by customer.first_name, customer.last_name 
order by count DESC   
limit 10;
-- 14. The first and last name of the top ten customers ranked by dollars spent
-- (#1 should be “KARL SEAL�? with 221.55 spent, #10 should be “ANA BRADLEY�? with 174.66 spent)
select customer.first_name, customer.last_name, SUM(payment.amount)
from customer
join payment ON payment.customer_id = customer.customer_id
group by customer.first_name, customer.last_name
order by  sum DESC
limit 10;

-- 15. The store ID, street address, total number of rentals, total amount of sales (i.e. payments), and average sale of each store.
-- (NOTE: Keep in mind that while a customer has only one primary store, they may rent from either store.)
-- (Store 1 has 7928 total rentals and Store 2 has 8121 total rentals)
select store.store_id, address.address, count(payment.amount), sum(payment.amount), avg(payment.amount)
from rental
join payment on rental.rental_id = payment.rental_id
join inventory on rental.inventory_id = inventory.inventory_id
join store on inventory.store_id = store.store_id
join address on store.address_id = address.address_id
group by store.store_id, address.address;


-- 16. The top ten film titles by number of rentals
-- (#1 should be “BUCKET BROTHERHOOD�? with 34 rentals and #10 should have 31 rentals)
select film.title, count(rental.rental_id)
from film
join inventory ON inventory.film_id = film.film_id
join rental ON rental.inventory_id = inventory.inventory_id
group by  film.title
order by count DESC
limit 10;
-- 17. The top five film categories by number of rentals
-- (#1 should be “Sports�? with 1179 rentals and #5 should be “Family�? with 1096 rentals)
select category.name, count(rental.rental_id)
from category
join film_category ON category.category_id = film_category.category_id
join film ON film.film_id = film_category.film_id
join inventory ON film.film_id = inventory.film_id
join rental ON inventory.inventory_id = rental.inventory_id
group by category.name
order by count DESC
limit 5;

-- 18. The top five Action film titles by number of rentals
-- (#1 should have 30 rentals and #5 should have 28 rentals)
select film.title, COUNT(rental.rental_id)
from film
join film_category ON film.film_id = film_category.film_id
join category ON film_category.category_id = category.category_id
join inventory ON film.film_id = inventory.film_id
join rental ON inventory.inventory_id = rental.inventory_id
where category.name = 'Action'
group by film.title
order by count DESC
limit 5;
-- 19. The top 10 actors ranked by number of rentals of films starring that actor
-- (#1 should be “GINA DEGENERES�? with 753 rentals and #10 should be “SEAN GUINESS�? with 599 rentals)
select actor.first_name, actor.last_name, COUNT(rental.rental_id)
from actor
join film_actor ON actor.actor_id = film_actor.actor_id
join film ON film.film_id = film_actor.film_id
join inventory ON film.film_id = inventory.film_id
join rental ON inventory.inventory_id = rental.inventory_id
group by actor.first_name, actor.last_name
order by COUNT DESC
limit 10;
-- 20. The top 5 “Comedy�? actors ranked by number of rentals of films in the “Comedy�? category starring that actor
-- (#1 should have 87 rentals and #5 should have 72 rentals)
select actor.first_name, actor.last_name, COUNT(rental.rental_id)
from actor
join film_actor ON actor.actor_id = film_actor.actor_id
join film ON film.film_id = film_actor.film_id
join film_category ON film.film_id = film_category.film_id
join category ON film_category.category_id = category.category_id
join inventory ON film.film_id = inventory.film_id
join rental ON inventory.inventory_id = rental.inventory_id
where category.name = 'Comedy'
group by    actor.first_name, actor.last_name 
order by COUNT DESC
limit 5;
