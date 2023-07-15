package ru.ntik.booksearch.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ntik.booksearch.entity.Page;

import java.util.Optional;

@Repository
public interface PageRepository extends CrudRepository<Page, Long> {

    @Query("SELECT p FROM Page p WHERE p.book.id = :bookId AND p.pageNumber = :pageNumber")
    Optional<Page> findByBookPage(@Param("bookId") long bookId, @Param("pageNumber") int pageNumber);
}
