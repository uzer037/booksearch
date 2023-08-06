package ru.ntik.booksearch.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import ru.ntik.booksearch.entity.Book;
import ru.ntik.booksearch.entity.Page;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BookSearchServiceTest {

    @Autowired
    private BookSearchService bookSearchService;
    @Autowired
    private BookService bookService;

    // ========================== //
    // Making sure index is build //
    // ========================== //

    /**
     * Uploads test document to the database and builds index
     */
    @BeforeAll
    public static void prepareHibernateSearch(@Autowired BookService bookService,
                                              @Autowired BookSearchService bookSearchService) {
        // uploading test data
        uploadBook(bookService);
        // rebuilding index
        assertThatCode(bookSearchService::rebuildIndex).doesNotThrowAnyException();
    }

    /**
     * Must be in separate annotated method to make sure all data is committed before indexing
     */
    @Commit
    public static void uploadBook(@Autowired BookService bookService) {
        // open stream
        try (InputStream inputStream = BookSearchServiceTest.class.getClassLoader()
                .getResourceAsStream("treasure-island.epub")){
            assertThat(inputStream).isNotNull();
            Book book = bookService.loadEpubFromStream(inputStream);
            assertThat(book).isNotNull();
            bookService.save(book);
        } catch (IOException e) {
            fail("Unable to load book: " + e);
        }
    }

    // ============ //
    // Actual tests //
    // ============ //

    @Test
    void findPhrase() {
        List<Page> result = bookSearchService.findPhrase("John Silver");
        assertThat(result).isNotEmpty();
        System.out.println("Found " + result.size() + " phrase matches.");
        System.out.println(result.get(0).getPageText());
    }
}