package ru.ntik.booksearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ntik.booksearch.entity.Page;
import ru.ntik.booksearch.service.BookSearchService;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    BookSearchService bookSearchService;
    @RequestMapping("/search")
    public String getPagesWith(Model model, @RequestParam(value = "query") String query) {
        System.out.println("Searching \"" + query + "\" in indexed content...");
        List<String> result = bookSearchService.findWord(query).stream().map(Page::getPageText).toList();
        System.out.println("Found " + result.size() + " results.");
        model.addAttribute("count", result.size());
        model.addAttribute("pages", result);
        return "pagesearch";
    }
    @RequestMapping("/")
    public String requestHomepage() {
        return "home";
    }
}
