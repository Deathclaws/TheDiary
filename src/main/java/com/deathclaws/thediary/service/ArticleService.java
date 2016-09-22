package com.deathclaws.thediary.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.deathclaws.thediary.model.Article;
import com.deathclaws.thediary.util.HibernateUtil;

/**
 * Hello world!
 *
 */
public class ArticleService {

	private static void doIndex() throws InterruptedException {
		Session session = HibernateUtil.getSession();

		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.createIndexer().startAndWait();

		fullTextSession.close();
	}

	private static List<Article> search(String queryString) {
		Session session = HibernateUtil.getSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);

		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();
		org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onFields("name").matching(queryString).createQuery();

		// wrap Lucene query in a javax.persistence.Query
		Query fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Article.class);

		List<Article> contactList = fullTextQuery.list();

		fullTextSession.close();

		return contactList;
	}

	private static void addRoger() {
		Session session = null;
		try {

			session = HibernateUtil.getSession();

			org.hibernate.Transaction transaction = session.beginTransaction();

			transaction.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally{
			if(session != null) {
				session.close();
			}
		}
	}

	private static void displayContactTableData() {
		Session session = null;

		try {
			session = HibernateUtil.getSession();
			List<Article> contactList = session.createQuery("from Contact").list();
			for (Article contact : contactList) {
				System.out.println(contact);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally{
			if(session != null) {
				session.close();
			}
		}
	}

}
