package ru.ntik.booksearch.service;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ntik.booksearch.entity.Page;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class BookSearchService {

    @Autowired
    @PersistenceContext
    EntityManager entityManager;
    boolean isIndexUpToDate = false;
    @Transactional
    public void rebuildIndex() throws InterruptedException {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer().startAndWait();
        isIndexUpToDate = true;
    }

    /**
     * Runs Hibernate search query to match given word
     * @param word single-word string to match
     * @return list of pages (strings) containing given string.
     */
    @Transactional
    public List<Page> findWord(String word) {
        getQueryBuilder();
        Query query = getQueryBuilder().keyword().onField("pageText").matching(word).createQuery();
        return runQuery(query);
    }

    /**
     * Runs Hibernate search query to match given string
     * @param phrase arbitrary string to match
     * @return list of pages (entities) containing given string.
     */
    @Transactional
    public List<Page> findPhrase(String phrase) {
        Query query = getQueryBuilder().phrase().onField("pageText").sentence(phrase).createQuery();
        return runQuery(query);
    }

    /**
     * Runs arbitrary query with Hibernate search and returns list of results
     * @param query to run
     * @return List of pages (entities) containing given text
     */
    @Transactional
    public List runQuery(Query query) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Page.class);
        return jpaQuery.getResultList();
    }

    QueryBuilder getQueryBuilder() {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Page.class).get();
    }
}
