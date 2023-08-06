package ru.ntik.booksearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.ntik.booksearch.entity.Book;
import ru.ntik.booksearch.service.BookSearchService;
import ru.ntik.booksearch.service.BookService;

import java.io.File;
import java.io.InputStream;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    BookService bookService;

    @Autowired
    BookSearchService bookSearchService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption("load-book")) {
            String bookPath = args.getOptionValues("load-book").get(0);
            System.out.println(bookPath);


            File bookFile = new File(bookPath);
            if (bookFile.exists() && bookFile.canRead()) {
                String bookName = "treasure-island.epub";
                InputStream bookStream = getClass().getClassLoader().getResourceAsStream(bookName);
                try (bookStream) {
                    Book book = bookService.loadEpubFromStream(bookStream);
                    bookService.save(book);
                    System.out.println("Loaded book");
                }
            } else {
                System.out.println("Book is unavailable");
            }
        }

        if (args.containsOption("rebuild-index")) {
            System.out.println("Rebuilding index.");
            bookSearchService.rebuildIndex();
            System.out.println("Index rebuilt.");
        }

        // keep it at the end to ensure all other options run correctly
        if (args.containsOption("autostop")) {
            System.out.println("Exiting...");
            BooksearchApplication.exit();
        }
    }
}
