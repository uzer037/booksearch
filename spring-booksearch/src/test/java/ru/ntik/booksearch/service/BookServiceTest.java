package ru.ntik.booksearch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.ntik.booksearch.entity.Book;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void saveBook() {
        Book book = new Book();
        Book resultBook = null;
        book.setName("Ostrov 2");

        for (int i = 0; i < 10; i++) {
            book.addPage("Page " + i);
        }

        resultBook = bookService.save(book);
        assertThat(resultBook).isNotNull();
    }

    @Test
    void findById() {
        Book original_book = new Book("Test Book");
        bookService.save(original_book);

        Book book = bookService.findById(original_book.getId());
        for (int i = 0; i < 10; i++) {
            book.addPage("Page " + i);
        }

        assertThat(book).isNotNull();
        assertThat(book.getPages()).isNotEmpty();
    }
}