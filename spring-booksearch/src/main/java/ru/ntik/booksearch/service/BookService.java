package ru.ntik.booksearch.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ntik.bookloader.epub.EpubLoader;
import ru.ntik.booksearch.entity.Book;
import ru.ntik.booksearch.repository.BookRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class BookService {
    @Autowired
    public BookSearchService bookSearchService;
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book entity) {
        return  bookRepository.save(entity);
    }

    public Book findById(long id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Book loadEpubFromStream(InputStream inputStream) throws IOException {
        Book book = new Book();
        EpubLoader loader = new EpubLoader();
        loader.setImagesBaking(false);
        loader.loadFromSource(inputStream);
        for(int i = 0; i < loader.getPagesCount(); i++) {
            book.addPage(loader.getPageText(i+1));
        }
        book.setName(loader.getTitle());

        bookSearchService.isIndexUpToDate = false;
        return book;
    }
}
