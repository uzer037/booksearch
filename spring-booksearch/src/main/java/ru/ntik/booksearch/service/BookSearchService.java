package ru.ntik.booksearch.service;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
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
    public void rebuildIndex() {
        SearchSession session = Search.session(entityManager);
        MassIndexer indexer = session.massIndexer().idFetchSize(200).batchSizeToLoadObjects(25)
                .threadsToLoadObjects(8);
        try {
            indexer.startAndWait();
            isIndexUpToDate = true;
            System.out.println("Index created.");
        } catch (InterruptedException e) {
            // sonarlint: either re-interrupt this method or rethrow the "InterruptedException"
            // Am I really should do this?
            System.err.println("Unable to build index: " + e);
            isIndexUpToDate = false;
        }
    }

    /**
     * Runs Hibernate search query to match given word
     * @param word single-word string to match
     * @return list of pages (strings) containing given string.
     */

    /**
     * Runs Hibernate search query to match given string
     * @param phrase arbitrary string to match
     * @return list of pages (entities) containing given string.
     */
    @Transactional
    public List<Page> findPhrase(String phrase) {
        SearchSession session = Search.session(entityManager);
        return session.search(Page.class).where(p->p.match().field("pageText").matching(phrase))
                .fetchAll().hits();
    }

    /**
     * Runs arbitrary query with Hibernate search and returns list of results
     * @param query to run
     * @return List of pages (entities) containing given text
     */
}
