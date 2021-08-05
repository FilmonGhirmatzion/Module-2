START TRANSACTION;
ROLLBACK;
COMMIT;
-- Write queries to return the following:
-- The following changes are applied to the "dvdstore" database.**

-- 1. Add actors, Hampton Avenue, and Lisa Byway to the actor table.
 select *
 from actor;
 
INSERT INTO actor (actor_id, first_name, last_name)
VALUES (201 , 'Hampton', 'AVENUE'), (202, 'LISA','BYWAY');
 
-- 2. Add "Euclidean PI", "The epic story of Euclid as a pizza delivery boy in
-- ancient Greece", to the film table. The movie was released in 2008 in English.
-- Since its an epic, the run length is 3hrs and 18mins. There are no special
-- features, the film speaks for itself, and doesn't need any gimmicks.
START TRANSACTION;
ROLLBACK;
COMMIT;


INSERT INTO film (title, description, release_year, language_id, original_language_id, rental_duration, rental_rate, length, replacement_cost, rating)
VALUES ('Euclidean PI', 'The epic story of Euclid as a pizza delivery boy in ancient Greece', 2008, 1, null, 3, 4.99, 198, 15.99, 'G');

SELECT *
FROM film
WHERE title = 'Euclidean PI';

-- 3. Hampton Avenue plays Euclid, while Lisa Byway plays his slightly
-- overprotective mother, in the film, "Euclidean PI". Add them to the film.
START TRANSACTION;
ROLLBACK;
COMMIT;
 
INSERT INTO film_actor(actor_id,film_id)
VALUES ((SELECT actor_id FROM actor WHERE first_name = 'HAMPTON' AND last_name = 'AVENUE'),(SELECT film_id FROM film WHERE title = 'Euclidean PI')); 
 
INSERT INTO film_actor(actor_id,film_id)
VALUES ((SELECT actor_id FROM actor WHERE first_name = 'LISA' AND last_name = 'BYWAY'),(SELECT film_id FROM film WHERE title = 'Euclidean PI'));



SELECT *
FROM actor
WHERE first_name = 'LISA' AND last_name = 'BYWAY';

SELECT *
FROM film
WHERE title = 'Euclidean PI';

-- 4. Add Mathmagical to the category table.
START TRANSACTION;
ROLLBACK;
COMMIT;

INSERT INTO category (name)
VALUES ('Mathmagical');
        
SELECT *
FROM category
WHERE name = 'Mathmagical';

-- 5. Assign the Mathmagical category to the following films, "Euclidean PI",
-- "EGG IGBY", "KARATE MOON", "RANDOM GO", and "YOUNG LANGUAGE"

UPDATE film_category
SET category_id = (SELECT category_id FROM category WHERE category.name = 'Mathmagical')
FROM film
WHERE film.title IN ('Euclidean PI','EGG IGBY', 'KARATE MOON', 'RANDOM GO', 'YOUNG LANGUAGE') AND film_category.film_id = film.film_id;

SELECT *
FROM film_category
ORDER BY category_id DESC

-- 6. Mathmagical films always have a "G" rating, adjust all Mathmagical films
-- accordingly.
-- (5 rows affected)
START TRANSACTION;
ROLLBACK;
COMMIT;


UPDATE film SET rating = 'G'
WHERE film_id IN
(SELECT film_id FROM film_category WHERE category_id =
(SELECT category_id FROM category WHERE name = 'Mathmagical'))


SELECT title, rating FROM film
WHERE film_id IN
(SELECT film_id FROM film_category WHERE category_id =
(SELECT category_id FROM category WHERE name = 'Mathmagical'))

-- 7. Add a copy of "Euclidean PI" to all the stores.
START TRANSACTION;
ROLLBACK;
COMMIT;

SELECT *
FROM inventory
ORDER BY film_id DESC;

INSERT INTO inventory(film_id,store_id)
VALUES((SELECT film_id FROM film WHERE title = 'Euclidean PI'),1);

INSERT INTO inventory(film_id,store_id)
VALUES((SELECT film_id FROM film WHERE title = 'Euclidean PI'),2);




-- 8. The Feds have stepped in and have impounded all copies of the pirated film,
-- "Euclidean PI". The film has been seized from all stores, and needs to be
-- deleted from the film table. Delete "Euclidean PI" from the film table.
-- (Did it succeed? Why?)
-- <YOUR ANSWER HERE> it wouldn't delete because the key exist in another table
START TRANSACTION;
ROLLBACK;
COMMIT;

SELECT* 
FROM film
WHERE title = 'Euclidean PI';


DELETE FROM film WHERE title = 'Euclidean PI';

-- 9. Delete Mathmagical from the category table.
-- (Did it succeed? Why?)
-- <YOUR ANSWER HERE>'  it wouldn't delete because the key exist in another table
START TRANSACTION;
ROLLBACK;
COMMIT;

DELETE FROM category WHERE name = 'Mathmagical';


-- 10. Delete all links to Mathmagical in the film_category tale.
-- (Did it succeed? Why?)
-- <YOUR ANSWER HERE>  it worked beacuse, deleting the primary key wipes everything that's related to the the Primary Key.

START TRANSACTION;
ROLLBACK;
COMMIT;


SELECT* 
FROM film_category
WHERE category_id = 18;

DELETE FROM film_category WHERE category_id = 18;

-- 11. Retry deleting Mathmagical from the category table, followed by retrying
-- to delete "Euclidean PI".
-- (Did either deletes succeed? Why?)
-- <YOUR ANSWER HERE>
START TRANSACTION;
ROLLBACK;
COMMIT;

        

DELETE FROM category
WHERE name = 'Mathmagical';
-- 12. Check database metadata to determine all constraints of the film id, and
-- describe any remaining adjustments needed before the film "Euclidean PI" can
-- be removed from the film table.
