package ru.ntik.booksearch.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ntik.booksearch.entity.Page;
import ru.ntik.booksearch.repository.PageRepository;

@Service
public class PageService {

    private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public Page findByPageNumber(long bookId, int pageNumber) {
        return pageRepository.findByBookPage(bookId, pageNumber).orElseThrow(EntityNotFoundException::new);
    }
}
