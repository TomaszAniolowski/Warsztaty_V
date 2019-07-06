create table books
(
    id        int auto_increment
        primary key,
    isbn      varchar(13)  null,
    title     varchar(100) null,
    author    varchar(100) null,
    publisher varchar(100) null,
    type      varchar(25)  null,
    constraint books_isbn_uindex
        unique (isbn)
);


