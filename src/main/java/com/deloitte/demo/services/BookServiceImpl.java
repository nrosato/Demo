package com.deloitte.demo.services;

import com.deloitte.demo.entities.Book;
import com.deloitte.demo.exceptions.BookIdMismatchException;
import com.deloitte.demo.exceptions.BookNotFoundException;
import com.deloitte.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    /**
     * findByTitle
     * @param title of a book
     * @return List of books found with the corresponding title
     */
    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    /**
     * findAllBooks
     * @return list of all books in the database
     */
    @Override
    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * findById
     * @param id book ID
     * @return book with the corresponding ID, if not found throw BookNotFoundException
     */
    @Override
    public Book findById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    /**
     * createBook
     * @param book to be created
     * @return book that was created
     */
    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * deleteBookById
     * @param id of book to be deleted
     * @return book that was deleted
     */
    @Override
    public Book deleteBookById(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);

        return book;
    }

    /**
     * updateBookById
     * @param book (updated) to be saved
     * @param id of book to update
     * @return book that was updated or throw exception if id does not exist or mismatched
     */
    @Override
    public Book updateBookById(Book book, long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }
}
