package ru.ntik.booksearch.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;


@Entity
@Immutable
@Indexed

@Getter
@NoArgsConstructor
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private int pageNumber;

    @FullTextField
    @Column(length = 3500)
    private String pageText;

    public Page(int pageNumber, String text, Book book) {
        this.pageNumber = pageNumber;
        this.pageText = text;
        this.book = book;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
