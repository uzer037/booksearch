package ru.ntik.booksearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ntik.booksearch.entity.Book;
import ru.ntik.booksearch.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book entity) {
        return  bookRepository.save(entity);
    }
}
