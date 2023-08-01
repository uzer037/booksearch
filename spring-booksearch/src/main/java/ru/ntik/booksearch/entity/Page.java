package ru.ntik.booksearch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.Length;
import org.hibernate.annotations.Immutable;
import org.springframework.stereotype.Indexed;


@Entity
@Immutable
@Indexed

@Getter
@NoArgsConstructor
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private int pageNumber;

    @Column(name = "doc_txt", length = Length.LONG32)
    private String html;

    public Page(int pageNumber, String html, Book book) {
        this.pageNumber = pageNumber;
        this.html = html;
        this.book = book;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
