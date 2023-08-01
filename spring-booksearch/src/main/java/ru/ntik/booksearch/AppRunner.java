package ru.ntik.booksearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.ntik.booksearch.entity.Book;
import ru.ntik.booksearch.service.BookService;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    BookService bookService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption("load-book")) {
            String bookPath = args.getOptionValues("load-book").get(0);
            System.out.println(bookPath);
            File bookFile = new File(bookPath);
            if(bookFile.exists() && bookFile.canRead()) {
                InputStream bookStream = new FileInputStream(bookFile);
                Book book = bookService.loadEpubFromStream(bookStream);
                try {
                    bookService.save(book);
                    System.out.println("Loaded book");
                }
                finally {
                    bookStream.close();
                }
            } else {
                System.out.println("Book is unavailable");
            }
        }
    }
}
