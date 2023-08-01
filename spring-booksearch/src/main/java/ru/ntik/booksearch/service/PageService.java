package ru.ntik.booksearch.service;

import org.springframework.stereotype.Service;
import ru.ntik.booksearch.repository.PageRepository;

@Service
public class PageService {

    private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    // NOTE: do we really need this method?
    /*public Page findByPageNumber(long bookId, int pageNumber) {
        return pageRepository.findByBookPage(bookId, pageNumber).orElseThrow(EntityNotFoundException::new);
    }*/
}
