package ru.ntik.bookloader.epub;

import ru.ntik.bookloader.BookLoader;
import nl.siegmann.epublib.domain.Resources;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class EpubLoader implements BookLoader {
    int maxCharPerPage = 1800;
    private List<String> pages = new ArrayList<>();
    private final EpubReader epubReader = new EpubReader();
    private Map<String, String> imageMap = new HashMap<>();
    private String title;

    private boolean bakeImages = true;

    /**
     * Weither to load and integrate images into final html pages as base64 strings next time loadFromSource is called
     * @param baking default = true
     */
    public void setImagesBaking(boolean baking)
    {
        this.bakeImages = baking;
    }
    @Override
    public void loadFromSource(InputStream bookStream) throws IOException {
        nl.siegmann.epublib.domain.Book epubBook = epubReader.readEpub(bookStream);
        title = epubBook.getTitle();

        List<SpineReference> references = epubBook.getSpine().getSpineReferences();
        Resources resources = epubBook.getResources();

        if (bakeImages) {
            // first identifying and loading all images
            imageMap = new HashMap<>();
            for (Resource res : resources.getAll()) {
                if (MediatypeService.isBitmapImage(res.getMediaType())) {
                    imageMap.put(extractResourceFileName(res.getHref()), Base64.getEncoder().encodeToString(res.getData()));
                }
            }
        }

        // then clearing previously loaded pages
        pages = new ArrayList<>();

        // and slicing XHTML documents into pages
        StringBuilder stringBuilder;
        for(SpineReference ref : references) {
            Resource res = ref.getResource();
            if(res.getMediaType() == MediatypeService.XHTML) {
                stringBuilder = new StringBuilder();
                String line;

                BufferedReader bufferedReader = new BufferedReader(res.getReader());

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                Document document = Jsoup.parse(stringBuilder.toString());
                if (bakeImages) {
                    document = loadImagesIntoDocument(document);
                }
                pages.addAll(sliceDocumentToPages(document));
            }
        }
    }
    @Override
    public String getPageHtml(int pageNumber) throws IndexOutOfBoundsException {
        return pages.get(pageNumber-1);
    }

    @Override
    public String getPageText(int pageNumber) throws IndexOutOfBoundsException {
        return Jsoup.parse(getPageHtml(pageNumber)).text();
    }

    @Override
    public int getPagesCount() {
        return pages.size();
    }

    @Override
    public String getTitle() {
        return title;
    }

    private Document loadImagesIntoDocument(Document document) {
        // inserting previously identified images as base64
        Elements imgElements = document.select("img");
        System.out.println("Loading images into document...");
        for(Element img : imgElements) {
            String src = extractResourceFileName(img.attr("src"));
            System.out.println("Trying to find image: " + src);

            if(imageMap.containsKey(src)) {
                System.out.println("Replaced " + src + " with base64 image");
                img.attr("src", "data:image/png;base64, " + imageMap.get(src));
            } else {
                System.out.println("Image is missing: " + src);
            }
        }
        System.out.println("Images loaded.");
        return document;
    }

    List<String> sliceDocumentToPages(Document document) {
        // slicing document to pages
        List<Document> pageElements = HtmlSlicer.splitHtmlByContentLength(document, maxCharPerPage);

        return pageElements.stream().map(Element::outerHtml).toList();
    }

    /**
     * Extracting file name from resource path
     * (ex. "../images/section_1/cover.png" -> "cover.png"
     * @param path resource path
     * @return file name
     */
    String extractResourceFileName(String path) {
        int pos = path.lastIndexOf("/") + 1;
        if (pos > 0) {
            return path.substring(pos);
        } else {
            return path;
        }
    }

    // package-private getter for auto-testing
    Map<String, String> getImagesMap() {
        return imageMap;
    }
}