create database library;
use library;
create table book (Bookid int Primary Key, 
BookTitle varchar(40) Not Null, 
Author varchar(25), 
Keyword varchar(30), 
PublishYear year, 
Bstatus varchar(20) Default "Available");
create table login(id int primary key, 
name varchar(17), 
password varchar(15), 
role varchar(10));
insert into login values(1, 'Abi', '12345', 'admin');
insert into login values(2,'Vikram','90123','student');
insert into login values(3,'Arun','56789','librarian');
insert into book values(1, "The Adventure Chronicles", "John", "Fiction", 2020, "Unavailable");
insert into book values(2, "Science and Beyond", "Jane Scientist", "Science", 2022, "Available");
insert into book values(3, 'Mystery Manor', 'Sara Sleuth', 'Mystery', 2021 );
insert into book values(4, 'Historical Odyssey', 'David Historian', 'History', 2020, "Available");
insert into book values(5, 'Fantasy Realm', 'Emily Fantasywriter', 'Fantasy', 2024, "Unavailable");
INSERT INTO book 
VALUES (6, 'Chronicles', 'John', 'Fiction', 2020, 'Available');

INSERT INTO book 
VALUES (7, 'The Great Gatsby', 'F. Scott Fitzgerald', 'Classic', 1925, 'Available');

INSERT INTO book 
VALUES (9, 'To Kill a Mockingbird', 'Harper Lee', 'Fiction', 1960, 'Available');

INSERT INTO book  
VALUES (10, '1984', 'George Orwell', 'Dystopian', 1949, 'Available');

INSERT INTO book
VALUES (11, 'Pride and Prejudice', 'Jane Austen', 'Romance', 2006, 'Available');

INSERT INTO book
VALUES (12, 'The Catcher in the Rye', 'J.D. Salinger', 'Fiction', 1951, 'Available');

INSERT INTO book
VALUES (13, 'Harry Potter and the Philosopher''s Stone', 'J.K. Rowling', 'Fantasy', 1997, 'Available');

INSERT INTO book 
VALUES (14, 'The Hobbit', 'J.R.R. Tolkien', 'Fantasy', 1937, 'Available');

INSERT INTO book 
VALUES (16, 'The Lord of the Rings', 'J.R.R. Tolkien', 'Fantasy', 1954, 'Available');

INSERT INTO book 
VALUES (17, 'The Da Vinci Code', 'Dan Brown', 'Thriller', 2003, 'Available');
create table student (Studentid int Primary Key, 
StudentName varchar(40) Not Null, 
StudentStatus varchar(25) Default "Active"); 
insert into student values(1, "Vikram", "Active");
insert into student values(2, "Venkat", "Active");
insert into student values(3, "Sasi", "Blocked");
insert into student values(4, "Naveen", "Active");
insert into student values(5, "Vijay", "Active");
CREATE TABLE Transaction (
    Tid INT PRIMARY KEY,
    Bookid INT,
    Studentid INT,
    checkout INT default 1,
    BuyDate timestamp default current_timestamp,
	ReturnDate timestamp default (current_timestamp + interval 45 day ),
    RenewalCount INT default 0,
    FOREIGN KEY (Bookid) REFERENCES Book(Bookid),
    FOREIGN KEY (Studentid) REFERENCES Student(Studentid)
);



