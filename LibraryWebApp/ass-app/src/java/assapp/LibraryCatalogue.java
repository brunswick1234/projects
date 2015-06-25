/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assapp;

import app.gui.LibraryCatalogueGUI;
import app.gui.LibraryCatalogueGUIImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.ejb.EJB;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import repository.BookRepository;
import repository.entities.BookCopy;

/**
 *
 * @author dell
 */
public class LibraryCatalogue implements ActionListener, ListSelectionListener
{

    @EJB
    private static BookRepository bookRepository;
    private LibraryCatalogueGUI gui;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        LibraryCatalogue libraryCatalogue = new LibraryCatalogue();
        libraryCatalogue.run();
        // TODO code application logic here
    }

    public void run()
    {
        gui = new LibraryCatalogueGUIImpl(this, this);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == gui.getSearchButton())
        {
            search();
        }
        else
        {
            System.out.println("aaaaaaaaaaaaaaa");
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if ((e.getSource() == this.gui.getResultTable().getSelectionModel())
                && (!e.getValueIsAdjusting()))
        {
            try
            {
                if (this.gui.isResultSelected())
                {
                    String callNumber = this.gui.getSelectedCallNumber();

                    List<BookCopy> bookCopies = this.bookRepository.searchByCallNumber(callNumber);
                    this.gui.displaySelectedDetail(bookCopies.get(0));
                }
            } catch (Exception ee)
            {
                ee.printStackTrace();
                
            }
        }
    }

    public void search()
    {
        String searchCriteria = gui.getSelectedSearchCriteria();
        if (gui.getKeyword() == null)
        {

        } else
        {
            if (searchCriteria.equals("Title"))
            {
                List<BookCopy> bookCopies =bookRepository.searchByTitel(gui.getKeyword());
                showSearchResult(bookCopies);
            } else if (searchCriteria.equals("Author"))
            {
                List<BookCopy> bookCopies =bookRepository.searchByAuthor(gui.getKeyword());
                showSearchResult(bookCopies);

            } else if (searchCriteria.equals("Type"))
            {
                List<BookCopy> bookCopies =bookRepository.searchByTitel(gui.getKeyword());
                showSearchResult(bookCopies);

            } else if (searchCriteria.equals("Call Number"))
            {
                List<BookCopy> bookCopies =bookRepository.searchByCallNumber(gui.getKeyword());
                showSearchResult(bookCopies);

            }
        }
    }
    
    
    public void showSearchResult(List<BookCopy> bookCopies)
    {
        if (bookCopies.isEmpty())
        {
            System.out.println("No record found");
        }
        else
        {
            gui.displayResults(bookCopies);
        }
    }
}
