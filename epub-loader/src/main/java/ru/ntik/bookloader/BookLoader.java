package ru.ntik.bookloader;

import java.io.IOException;
import java.io.InputStream;

public interface BookLoader {
    // Planning Epub, pdf and probably fb2 support
    /**
     * Loads book data from provided input stream
     * @param stream book file stream
     * @throws IOException if InputStream or underlying file does not exist or inaccessible
     */
    void loadFromSource(InputStream stream) throws IOException;

    /**
     * @param pageNumber
     * @return Html string for page with given number
     * @throws IndexOutOfBoundsException if there is no page with such number
     */
    String getPageHtml(int pageNumber) throws IndexOutOfBoundsException;

    /**
     * @param pageNumber
     * @return String of text for page with given number
     * @throws IndexOutOfBoundsException if there is no page with such number
     */
    String getPageText(int pageNumber) throws IndexOutOfBoundsException;

    /**
     * @return total pages number. If book isn't loaded or empty - returns 0
     */
    int getPagesCount();

    /**
     * @return title of the book
     */
    String getTitle();
}
