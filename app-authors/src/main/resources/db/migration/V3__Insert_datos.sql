-- Eliminar los registros antiguos de las versiones 1 y 2
DELETE FROM books_authors;
DELETE FROM inventory;
DELETE FROM books;
DELETE FROM authors;

-- Insertar 5 nuevos autores
insert into authors (name) values ('Gabriel Garcia Marquez');
insert into authors (name) values ('Isabel Allende');
insert into authors (name) values ('Mario Vargas Llosa');
insert into authors (name) values ('Julio Cortazar');
insert into authors (name) values ('Jorge Luis Borges');

-- Insertar 5 nuevos libros
insert into books (isbn, title, price, version) values ('978-0-307-47472-8', 'Cien Años de Soledad', 15.99, 1);
insert into books (isbn, title, price, version) values ('978-0-553-27391-5', 'La Casa de los Espíritus', 12.50, 1);
insert into books (isbn, title, price, version) values ('978-84-204-7183-9', 'La Ciudad y los Perros', 14.00, 1);
insert into books (isbn, title, price, version) values ('978-84-376-0457-2', 'Rayuela', 18.99, 1);
insert into books (isbn, title, price, version) values ('978-0-307-95092-5', 'Ficciones', 11.99, 1);

-- Insertar inventario para los 5 nuevos libros
insert into inventory (book_isbn, sold, supplied, version) values ('978-0-307-47472-8', 500, 1000, 1);
insert into inventory (book_isbn, sold, supplied, version) values ('978-0-553-27391-5', 300, 500, 1);
insert into inventory (book_isbn, sold, supplied, version) values ('978-84-204-7183-9', 200, 400, 1);
insert into inventory (book_isbn, sold, supplied, version) values ('978-84-376-0457-2', 150, 300, 1);
insert into inventory (book_isbn, sold, supplied, version) values ('978-0-307-95092-5', 400, 800, 1);

-- Relacionar los libros con los autores 
-- (Los IDs generados serán 4, 5, 6, 7 y 8 porque el identity no se reinicia con el comando DELETE)
insert into books_authors (books_isbn, authors_id) values ('978-0-307-47472-8', 4);
insert into books_authors (books_isbn, authors_id) values ('978-0-553-27391-5', 5);
insert into books_authors (books_isbn, authors_id) values ('978-84-204-7183-9', 6);
insert into books_authors (books_isbn, authors_id) values ('978-84-376-0457-2', 7);
insert into books_authors (books_isbn, authors_id) values ('978-0-307-95092-5', 8);
