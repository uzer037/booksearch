package ru.ntik.booksearch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.ntik.booksearch.entity.Book;
import ru.ntik.booksearch.repository.BookRepository;

import javax.swing.text.Element;
import javax.swing.text.html.parser.Entity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional

class BooksearchApplicationTests {

	@Autowired
	private BookRepository repository;

	@Test
	void contextLoads() {
		Book book = new Book("Spring in action");
		repository.save(book);
		Book loadedBook = repository.findById(book.getId()).get();
		assertThat(book.getName()).isEqualTo(loadedBook.getName());
	}

}
