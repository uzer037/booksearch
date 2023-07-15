package ru.ntik.booksearch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import ru.ntik.booksearch.entity.Book;
import ru.ntik.booksearch.entity.Page;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void saveBook() {
        Book book = new Book();
        book.setName("Ostrov 2");

        for (int i = 0; i <10; i++) {
            book.addPage("Page " + i);
        }

        book = bookService.save(book);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    void findById() {
        Book book = bookService.findById(1);

        assertThat(book).isNotNull();
        assertThat(book.getPages()).isNotEmpty();
    }
}