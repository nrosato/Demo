package com.deloitte.demo.controllers;

import com.deloitte.demo.entities.Book;
import com.deloitte.demo.services.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookControllerTest {
    @Autowired
    private BookController bookController;

    @MockBean
    private BookServiceImpl bookService;

    @Test
    public void findAll_books_found() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Hello world", "Deloitte"));
        books.add(new Book("How to be a good software engineer for dummies", "Nick Rosato"));
        books.add(new Book("Spring Bootcamp 2024", "Deloitte"));

        when(bookService.findAllBooks()).thenReturn(books);

        assertEquals(bookController.findAll(), books);
    }

    @Test
    public void findByTitle_book_found() {
        Book book = new Book("How to mock objects", "Nick Rosato");
        List<Book> books = new ArrayList<Book>();
        books.add(book);

        when (bookService.findByTitle("How to mock objects")).thenReturn(books);

        assertEquals(bookController.findByTitle("How to mock objects"), books);
    }

    @Test
    public void findOne_book_found() {
        Book book = new Book("Book", "Deloitte");
        Long id = book.getId();

        when(bookService.findById(id)).thenReturn(book);

        assertEquals(bookController.findOne(id), book);
    }

    @Test
    public void create_book_created() {
        Book book = new Book("New Book", "Deloitte");

        when(bookService.createBook(book)).thenReturn(book);

        assertEquals(bookController.create(book), book);
    }

    @Test
    public void delete_book_deleted() {
        Book book = new Book("Book", "Deloitte");
        Long id = book.getId();

        when(bookService.deleteBookById(id)).thenReturn(book);

        assertEquals(bookController.delete(id), book);
    }

    @Test
    public void update_book_updated() {
        Book book = new Book("Updated Title", "Updated Author");
        Long id = book.getId();

        when(bookService.updateBookById(book, id)).thenReturn(book);

        assertEquals(bookController.updateBook(book, id), book);
    }
}
