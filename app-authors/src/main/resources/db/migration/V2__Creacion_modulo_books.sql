CREATE TABLE books
(
    isbn    VARCHAR(255) NOT NULL,
    title   VARCHAR(255),
    price   DECIMAL,
    version INTEGER,
    CONSTRAINT pk_books PRIMARY KEY (isbn)
);

CREATE TABLE inventory
(
    book_isbn VARCHAR(255) NOT NULL,
    sold      INTEGER,
    supplied  INTEGER,
    version   INTEGER,
    CONSTRAINT pk_inventory PRIMARY KEY (book_isbn)
);

ALTER TABLE inventory
    ADD CONSTRAINT FK_INVENTORY_ON_BOOK_ISBN FOREIGN KEY (book_isbn) REFERENCES books (isbn);

insert into books (isbn, title, price, version) values ('978-3-16-148410-0', 'The Great Gatsby', 10.99, 1);
insert into books (isbn, title, price, version) values ('978-0-14-118263-6', 'To Kill a Mockingbird', 8.99,1);
insert into books (isbn, title, price, version) values ('978-0-452-28423-4', '1984', 9.99, 1);

insert into inventory (book_isbn, sold, supplied, version) values ('978-3-16-148410-0', 100, 150, 1);
insert into inventory (book_isbn, sold, supplied, version) values ('978-0-14-118263-6', 200, 250, 1);
insert into inventory (book_isbn, sold, supplied, version) values ('978-0-452-28423-4', 150, 200, 1);