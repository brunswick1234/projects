/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "book")
//@NamedQueries(
//{
//@NamedQuery(name = Book.GET_ALL_QUERY_NAME, query = "SELECT b FROM Book b"),
//@NamedQuery(name = Book.FIND_BY_TITLE_QUERY_NAME,query = "SELECT b FROM Book b WHERE b.title = :title"),
//@NamedQuery(name = Book.FIND_BY_AUTHOR_QUERY_NAME,query = "SELECT b FROM Book b WHERE b.author = :author"),
//@NamedQuery(name = Book.FIND_BY_TYPE_QUERY_NAME,query = "SELECT b FROM Book b WHERE b.type = :type"),
//})
public class Book implements Serializable
{
    public static final String GET_ALL_QUERY_NAME = "Book.getAll";
    public static final String FIND_BY_TITLE_QUERY_NAME = "Book.findByTitle";
    public static final String FIND_BY_AUTHOR_QUERY_NAME = "Book.findByAuthor";
    public static final String FIND_BY_TYPE_QUERY_NAME = "Book.findByType";
    
    @Id
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String type;
    private String thumbnail;
    private String description;
    private String url;
    @OneToMany(mappedBy = "book")
    private Set<BookCopy> copies;

    public Book()
    {
    }

    public String getIsbn()
    {
        return isbn;
    }

    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public String getType()
    {
        return type;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public String getDescription()
    {
        return description;
    }

    public String getUrl()
    {
        return url;
    }

    public Set<BookCopy> getCopies()
    {
        return copies;
    }
    
    
    
    
    
    
}
