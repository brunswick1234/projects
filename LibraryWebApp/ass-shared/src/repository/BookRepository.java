/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository;

import java.util.List;
import javax.ejb.Remote;
import repository.entities.BookCopy;

/**
 *
 * @author dell
 */
@Remote
public interface BookRepository
{
    public List<BookCopy> searchByTitel(String title);
    public List<BookCopy> searchByAuthor(String author);
    public List<BookCopy> searchByCallNumber(String callNumber);
    public List<BookCopy> searchByType(String type);
    
    public void addBookCopy(BookCopy bookCopy);
    public void removeBookCopy(BookCopy bookCopy);
    
    
    
    
    
    
}
