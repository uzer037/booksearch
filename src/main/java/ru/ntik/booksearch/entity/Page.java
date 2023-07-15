package ru.ntik.booksearch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Immutable;


@Entity
@Immutable

@Getter

@NoArgsConstructor
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private int pageNumber;

    private String text;

    public Page(int pageNumber, String text, Book book) {
        this.pageNumber = pageNumber;
        this.text = text;
        this.book = book;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
