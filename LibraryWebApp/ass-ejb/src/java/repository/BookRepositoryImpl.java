/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import repository.entities.Book;
import repository.entities.BookCopy;

/**
 *
 * @author dell
 */
@Stateless
public class BookRepositoryImpl implements BookRepository
{
    EntityManagerFactory entityMangerFactory = Persistence.createEntityManagerFactory("LibraryPU");
    EntityManager entityManager = entityMangerFactory.createEntityManager();
    //@PersistenceContext(unitName = "LibraryPU")
    //private EntityManager entityManager;
    

    @Override
    public List<BookCopy> searchByTitel(String title)
    {
        Query query = entityManager.createNamedQuery(BookCopy.FIND_BY_TITLE_QUERY_NAME);
        query.setParameter("title", title);
        if (query.getResultList() != null)
        {
            return query.getResultList();
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<BookCopy> searchByAuthor(String author)
    {
        Query query = entityManager.createNamedQuery(BookCopy.FIND_BY_AUTHOR_QUERY_NAME);
        query.setParameter("author", author);
        if (query.getResultList() != null)
        {
            System.out.println("11111111111");
            return query.getResultList();
        }
        else
        {
            return null;
        }

        
    }

    @Override
    public List<BookCopy> searchByCallNumber(String callNumber)
    {
        Query query = entityManager.createNamedQuery(BookCopy.FIND_BY_CALL_NUMBER_QUERY_NAME);
        query.setParameter("callNumber", callNumber);
        if (query.getResultList() != null)
        {
            return query.getResultList();
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<BookCopy> searchByType(String type)
    {
        Query query = entityManager.createNamedQuery(BookCopy.FIND_BY_TYPE_QUERY_NAME);
        query.setParameter("type", type);
        if (query.getResultList() != null)
        {
            return query.getResultList();
        }
        else
        {
            return null;
        }
    }

    @Override
    public void addBookCopy(BookCopy bookCopy)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeBookCopy(BookCopy bookCopy)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
