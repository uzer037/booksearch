package ru.ntik.booksearch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ntik.booksearch.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
