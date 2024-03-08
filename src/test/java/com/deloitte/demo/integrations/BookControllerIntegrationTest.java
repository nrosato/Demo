package com.deloitte.demo.integrations;

import com.deloitte.demo.controllers.BookController;
import com.deloitte.demo.entities.Book;
import com.deloitte.demo.exceptions.BookIdMismatchException;
import com.deloitte.demo.exceptions.BookNotFoundException;
import com.deloitte.demo.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration-test")
public class BookControllerIntegrationTest {
    @Autowired
    private BookController bookController;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findAllBooks_found() {
        Iterable<Book> foundBooks = bookController.findAll();
        assertEquals(bookRepository.findAll(), foundBooks);
    }

    @Test
    public void findByTitle_book1_found() {
        String title = "BOOK 1";
        List<Book> foundBooks = bookController.findByTitle(title);

        assertEquals(1, foundBooks.size());
        assertEquals(title, foundBooks.get(0).getTitle());
    }

    @Test
    public void findOne_1_found() {
        long id = 1;
        Book book = bookController.findOne(1);

        assertEquals(1, book.getId());
        assertEquals("BOOK 1", book.getTitle());
        assertEquals("DELOITTE", book.getAuthor());
    }

    @Test
    public void findOne_invalidBookId_notFound() {
        long id = -24;

        assertThrows(BookNotFoundException.class, () -> {
            bookController.findOne(id);
        });
    }

    @Test
    public void create_newBook_created() {
        Book book = new Book ("BOOK 4", "DELOITTE");
        book.setId(4);
        Book created = bookController.create(book);
        assertEquals(book, created);
    }

    @Test
    public void delete_book3_deleted() {
        Book book = bookController.delete(3);
        assertEquals(3, book.getId());
        assertEquals("BOOK 3", book.getTitle());
        assertEquals("DELOITTE", book.getAuthor());
    }

    @Test
    public void delete_invalidBook_bookNotFoundExceptionThrown() {
        assertThrows(BookNotFoundException.class, () -> {
            Book book = bookController.delete(-10);
        });
    }

    @Test
    public void updateBook_book1_updated() {
        String updatedTitle = "NEW BOOK 1 TITLE";
        String updatedAuthor = "NOT DELOITTE";
        long id = 1;
        Book book = new Book(updatedTitle, updatedAuthor);
        book.setId(id);

        Book updated = bookController.updateBook(book, id);

        assertEquals(updated.getId(), id);
        assertEquals(updated.getTitle(), updatedTitle);
        assertEquals(updated.getAuthor(), updatedAuthor);

        if (bookRepository.findById(id).isPresent()) {
            assertEquals(updated,  bookRepository.findById(id).get());
        } else {
            fail();
        }
    }

    @Test
    public void update_invalidBookId_bookIdMismatchExceptionThrown() {
        assertThrows(BookIdMismatchException.class, () -> {
            Book toBeUpdated = new Book("Foo", "Bar");
            toBeUpdated.setId(25);
            Book book = bookController.updateBook(toBeUpdated, 1);
        });
    }

    @Test
    public void update_missingBook_bookNotFoundExceptionThrown() {
        assertThrows(BookNotFoundException.class, () -> {
            long idThatDoesNotExist = 100;
            Book toBeUpdated = new Book("Foo", "Bar");
            toBeUpdated.setId(idThatDoesNotExist);
            Book book = bookController.updateBook(toBeUpdated, idThatDoesNotExist);
        });
    }
}
