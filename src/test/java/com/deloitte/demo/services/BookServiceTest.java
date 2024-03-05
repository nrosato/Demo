package com.deloitte.demo.services;

import com.deloitte.demo.entities.Book;
import com.deloitte.demo.exceptions.BookIdMismatchException;
import com.deloitte.demo.exceptions.BookNotFoundException;
import com.deloitte.demo.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {
    @MockBean
    BookRepository bookRepository;

    @Autowired
    BookServiceImpl bookService;

    @Test
    public void findByTitle_stubbedBook_found() {
        String title = "How store data in a database for dummies";
        Book book = new Book(title , "Deloitte");
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findByTitle(title)).thenReturn(books);

        assertEquals(books, bookService.findByTitle(title));
    }

    @Test
    public void findAllBooks_stubbedBooks_found() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Hello world", "Deloitte"));
        books.add(new Book("How to be a good software engineer for dummies", "Nick Rosato"));
        books.add(new Book("Spring Bootcamp 2024", "Deloitte"));

        when(bookRepository.findAll()).thenReturn(books);

        assertEquals(books, bookService.findAllBooks());
    }

    @Test
    public void findById_stubbedBook_found() {
        Book book = new Book("Stubbed Book", "Deloitte");
        long id = book.getId();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        assertEquals(book, bookService.findById(id));
    }

    @Test
    public void findById_idDoesNotExist_bookNotFoundException() {
        long id = 1L;
        String expectedMessage = "Book with id: " + id + " not found";

        when(bookRepository.findById(id)).thenThrow(new BookNotFoundException(expectedMessage));

        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.findById(id);
        });

        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void createBook_book_created() {
        Book book = new Book("New Book!", "Deloitte");

        when(bookRepository.save(book)).thenReturn(book);

        assertEquals(book, bookService.createBook(book));
    }

    @Test
    public void deleteBookById_book_deleted() {
        Book book = new Book("Old Book!", "Deloitte");
        long id = book.getId();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(id);

        assertEquals(book, bookService.deleteBookById(id));
    }

    @Test
    public void deleteBookById_nonExistentId_BookNotFoundException() {
        long id = 1L;
        String expectedMessage = "Book with id: " + id + " not found";

        doThrow(new BookNotFoundException(expectedMessage)).when(bookRepository).findById(id);

        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.findById(id);
        });

        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void updateBookById_book_updated() {
        Book book = new Book("Updated title", "Deloitte");
        long id = book.getId();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals(book, bookService.updateBookById(book, id));
    }

    @Test
    public void updateBookById_badId_bookIdMismatchException() {
        Book book = new Book("Why you should work every waking hour", "Not Deloitte");
        long id = book.getId();
        long badId = id + 1;

        Exception exception = assertThrows(BookIdMismatchException.class, () -> {
            bookService.updateBookById(book, badId);
        });
    }

    @Test
    public void updateBookById_bookDoesNotExist_bookNotFoundException() {
        Book book = new Book("Updated title", "Deloitte");
        long id = book.getId();

        when(bookRepository.findById(id)).thenThrow(new BookNotFoundException());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBookById(book, id);
        });
    }
}
