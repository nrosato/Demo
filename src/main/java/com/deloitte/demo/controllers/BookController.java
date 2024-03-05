package com.deloitte.demo.controllers;

import com.deloitte.demo.services.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.deloitte.demo.entities.Book;


@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    /**
     * findAll
     * @return list of all books found
     */
    @GetMapping
    public Iterable<Book> findAll() {
        return bookService.findAllBooks();
    }

    /**
     * findByTitle
     * @param bookTitle of book to be found
     * @return book that was found
     */
    @GetMapping("/title/{bookTitle}")
    public List<Book> findByTitle(@PathVariable String bookTitle) {
        return bookService.findByTitle(bookTitle);
    }

    /**
     * findOne
     * @param id of book to be found
     * @return book with corresponding id
     */
    @GetMapping("/{id}")
    public Book findOne(@PathVariable long id) {
        return bookService.findById(id);
    }

    /**
     * create
     * @param book to be created
     * @return book that was created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    /**
     * delete
     * @param id of book to be deleted
     * @return book that was deleted
     */
    @DeleteMapping("/{id}")
    public Book delete(@PathVariable long id) {
        return bookService.deleteBookById(id);
    }

    /**
     * update book
     * @param book (updated)
     * @param id of book to updated
     * @return book that was updated
     */
    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable long id) {
        return bookService.updateBookById(book, id);
    }
}