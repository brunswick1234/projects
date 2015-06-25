/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Named;
import repository.BookRepository;
import repository.entities.BookCopy;

/**
 *
 * @author dell
 */
@Named(value = "searchController")
@ManagedBean
public class SearchController implements Serializable
{

    private String searchCriteria;
    private String searchKeyword;
    private List<BookCopy> bookCopies;
    private HtmlDataTable datatable;

    @EJB
    private BookRepository bookRepository;

    /**
     * Creates a new instance of SearchController
     */
    public SearchController()
    {
    }

    public List<BookCopy> getBookCopies()
    {
        return bookCopies;
    }

    public void setSearchCriteria(String searchCriteria)
    {
        this.searchCriteria = searchCriteria;
    }

    public void setSearchKeyword(String searchKeyword)
    {
        this.searchKeyword = searchKeyword;
    }

    public void setBookCopies(List<BookCopy> bookCopies)
    {
        this.bookCopies = bookCopies;
    }

    public String getSearchCriteria()
    {
        return searchCriteria;
    }

    public String getSearchKeyword()
    {
        return searchKeyword;
    }

    public void doSearch()
    {
        if (searchCriteria.equals("title"))
        {
            bookCopies = bookRepository.searchByTitel(searchKeyword);

        } else if (searchCriteria.equals("author"))
        {
            bookCopies = bookRepository.searchByAuthor(searchKeyword);
        } else if (searchCriteria.equals("callNumber"))
        {
            bookCopies = bookRepository.searchByCallNumber(searchKeyword);
        }else
        {
            bookCopies = bookRepository.searchByType(searchKeyword);
        }

    }

    public HtmlDataTable getDatatable()
    {
        return datatable;
    }

    public void setDatatable(HtmlDataTable datatable)
    {
        this.datatable = datatable;
    }

    public String goDetail()
    {
        //DetailsController detailsController = new DetailsController((BookCopy)datatable.getRowData());
        return "details";
    }

}
