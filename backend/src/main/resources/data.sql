-- Indsæt nogle ingredienser
INSERT INTO ingredient (name) VALUES ('Tomato');
INSERT INTO ingredient (name) VALUES ('Pasta');
INSERT INTO ingredient (name) VALUES ('Cheese');

-- Indsæt nogle opskrifter
INSERT INTO recipe (name, link_to_page, time_to_make, instructions, has_made)
VALUES ('Spaghetti Bolognese', 'http://example.com/spaghetti', 30, 'Boil pasta and cook sauce.', false);

INSERT INTO recipe (name, link_to_page, time_to_make, instructions, has_made)
VALUES ('Cheese Pizza', 'http://example.com/pizza', 20, 'Bake with cheese.', true);

-- Link ingredienser til opskrifter (hvis du har Recipe_ingredient tabel)
INSERT INTO recipe_ingredient (recipe_id, ingredient_id, amount)
VALUES (1, 2, '200g'), (1, 1, '3 pcs'), (2, 3, '150g');
