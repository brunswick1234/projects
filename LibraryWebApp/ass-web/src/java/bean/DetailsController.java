/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.el.ELContext;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import repository.entities.BookCopy;

/**
 *
 * @author dell
 */
@Named(value = "detailsController")
@ManagedBean
public class DetailsController
{

    private BookCopy bookCopy;
    private String pageTitle;

    /**
     * Creates a new instance of DetailsController
     */
    public DetailsController()
    {
        ELContext context
                = FacesContext.getCurrentInstance().getELContext();
        SearchController app
                = (SearchController) FacesContext.getCurrentInstance()
                .getApplication()
                .getELResolver()
                .getValue(context, null, "searchController");
        bookCopy = (BookCopy) app.getDatatable().getRowData();

        pageTitle = bookCopy.getCallNumber() + " | " + bookCopy.getBook().getTitle();
    }
    
    
    public DetailsController(BookCopy bookCopy)
    {
        this.bookCopy = bookCopy;
    }

    public BookCopy getBookCopy()
    {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy)
    {
        this.bookCopy = bookCopy;
    }
    
    
    

}
