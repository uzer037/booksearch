package ru.ntik.booksearch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Indexed;

import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed

@Getter
@Setter

@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Page> pages = new HashSet<>();

    public Book(String name) {
        this.name = name;
    }

    public void addPage(String html) {
        pages.add(new Page(pages.size() + 1, html, this));
    }

}
