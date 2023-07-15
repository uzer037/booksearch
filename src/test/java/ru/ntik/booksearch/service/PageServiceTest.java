package ru.ntik.booksearch.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.ntik.booksearch.entity.Page;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PageServiceTest {

    @Autowired
    private PageService pageService;

    @Test
    void testFindByPageNumber() {
        Page page = pageService.findByPageNumber(1L, 3);
        assertThat(page).isNotNull();

        assertThrows(EntityNotFoundException.class,
                () -> pageService.findByPageNumber(5, 120));

        assertThatCode(()->pageService.findByPageNumber(1,1)).doesNotThrowAnyException();
    }
}