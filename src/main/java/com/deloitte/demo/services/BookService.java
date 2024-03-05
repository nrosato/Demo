package com.deloitte.demo.services;

import com.deloitte.demo.entities.Book;

import java.util.List;

public interface BookService {
    public abstract List<Book> findByTitle(String title);
    public abstract Iterable<Book> findAllBooks();
    public abstract Book findById(long id);
    public abstract Book createBook(Book book);
    public abstract Book deleteBookById(long id);
    public abstract Book updateBookById(Book book, long id);
}
