/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "book_copy")
@NamedQueries(
{
@NamedQuery(name = BookCopy.GET_ALL_QUERY_NAME, query = "SELECT p FROM BookCopy p"),
@NamedQuery(name = BookCopy.FIND_BY_CALL_NUMBER_QUERY_NAME,query = "SELECT b FROM BookCopy b WHERE b.callNumber = :callNumber"),
@NamedQuery(name = BookCopy.FIND_BY_TITLE_QUERY_NAME,query = "SELECT b FROM BookCopy b WHERE b.book.title = :title"),
@NamedQuery(name = BookCopy.FIND_BY_AUTHOR_QUERY_NAME,query = "SELECT b FROM BookCopy b WHERE b.book.author = :author"),
@NamedQuery(name = BookCopy.FIND_BY_TYPE_QUERY_NAME,query = "SELECT b FROM BookCopy b WHERE b.book.type = :type")
})
public class BookCopy implements Serializable
{
    public static final String GET_ALL_QUERY_NAME = "BookCopy.getAll";
    public static final String FIND_BY_CALL_NUMBER_QUERY_NAME = "BookCopy.findByCallNumber";
    public static final String FIND_BY_TITLE_QUERY_NAME = "Book.findByTitle";
    public static final String FIND_BY_AUTHOR_QUERY_NAME = "Book.findByAuthor";
    public static final String FIND_BY_TYPE_QUERY_NAME = "Book.findByType";
    

    
    
    @Id
    @Column(name = "call_number")
    private String callNumber;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "idbn", nullable = false)
    private Book book;
    private boolean available;


    public BookCopy()
    {
    }

    public String getCallNumber()
    {
        return callNumber;
    }

    public Book getBook()
    {
        return book;
    }

    public boolean isAvailable()
    {
        return available;
    }
    
    
    
    
    
    
    
    
    
}
