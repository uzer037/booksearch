package ru.ntik.booksearch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.ntik.booksearch.entity.Book;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void saveBook() {
        Book book = new Book();
        book.setName("Ostrov 2");

        book = bookService.save(book);
        assertThat(book.getId()).isNotNull();
    }

}