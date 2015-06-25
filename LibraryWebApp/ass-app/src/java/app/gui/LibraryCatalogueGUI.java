/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.gui;

import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import repository.entities.BookCopy;

/**
 *
 * @author dell
 */
public interface LibraryCatalogueGUI
{
    
    public JButton getSearchButton();
    public String getSelectedSearchCriteria();
    public void clearInput();
    public void clearResultTable();
    public void displayResults(List<BookCopy> bookCopies);
    public String getKeyword();
    public JTable getResultTable();
    public boolean isResultSelected();
    public String getSelectedCallNumber();
    public void displaySelectedDetail(BookCopy bookCopy);
    public void displayMessageInDialog(String message);
    public void clearDetailTable();
    public ImageIcon getImageIcon(String imageAddress);
   
    
}
