/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import repository.entities.Book;
import repository.entities.BookCopy;

/**
 *
 * @author dell
 */
public class LibraryCatalogueGUIImpl extends JFrame implements LibraryCatalogueGUI
{

    private static final String[] TABLE_COLUMNS =
    {
        "Title", "Author", "Call Number"
    };
    private static final String[] TABLE_DETAIL_COLUMNS =
    {
        "ISBN","Call Number","Title", "Author","Publisher","Type","Thumbnail","Description","Preview URL"
    };
    private static final String[] COMBO_BOX_STRINGS =
    {
        "Title", "Author", "Call Number", "Type"
    };

    private final JComboBox searchCriteriaComboBox;
    private final JTextField serachKeywordJTextField;
    private final JButton searchButton;
    private final JTable resultTable;
    private final JTable resultDetailTable;

    private final JPanel searchPanel;


    public LibraryCatalogueGUIImpl(ActionListener actionListener, ListSelectionListener listSelectionListener)
    {
        super("Library Catalogue System");
        serachKeywordJTextField = new JTextField();
        searchButton = new JButton("Search");
        searchButton.addActionListener(actionListener);

        resultTable = new JTable(new DefaultTableModel(TABLE_COLUMNS, 0));
        resultTable.getSelectionModel().addListSelectionListener(listSelectionListener);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel resultTableColumnModel = resultTable.getColumnModel();
        resultTableColumnModel.getColumn(0).setPreferredWidth(200);
        resultTableColumnModel.getColumn(1).setPreferredWidth(200);
        resultTableColumnModel.getColumn(2).setPreferredWidth(200);

        resultDetailTable = new JTable(new DefaultTableModel(TABLE_DETAIL_COLUMNS, 0));
        resultDetailTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel resultDetailTableModel = resultDetailTable.getColumnModel();
        resultDetailTableModel.getColumn(0).setPreferredWidth(100);
        resultDetailTableModel.getColumn(1).setPreferredWidth(100);
        resultDetailTableModel.getColumn(2).setPreferredWidth(100);
         resultDetailTableModel.getColumn(6).setPreferredWidth(300);
        resultDetailTableModel.getColumn(6).setCellRenderer(resultDetailTable.getDefaultRenderer(ImageIcon.class));

        searchCriteriaComboBox = new JComboBox(COMBO_BOX_STRINGS);

        searchPanel = new JPanel();


        Container container = this.getContentPane();

        container.setLayout(new BorderLayout());
        searchPanel.setLayout(new GridLayout(1, 3, 10, 5));

        searchPanel.add(searchCriteriaComboBox);
        searchPanel.add(serachKeywordJTextField);
        searchPanel.add(searchButton);

  

        container.add(searchPanel, BorderLayout.NORTH);
        container.add(new JScrollPane(resultTable), BorderLayout.CENTER);
        container.add(new JScrollPane(resultDetailTable), BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();
        this.setVisible(true);

    }

    @Override
    public JButton getSearchButton()
    {
        return searchButton;
    }

    @Override
    public String getSelectedSearchCriteria()
    {
        if (searchCriteriaComboBox.getItemCount() > 0 && searchCriteriaComboBox.getSelectedIndex() > 0)
        {
            return (String) this.searchCriteriaComboBox.getSelectedItem();
        } else
        {
            return null;
        }
    }

    @Override
    public void displayResults(List<BookCopy> bookCopies)
    {
        try
        {
            this.clearResultTable();
            //this.clearInput();
            for (BookCopy bookCopy : bookCopies)
            {
                ((DefaultTableModel) this.resultTable.getModel()).addRow(new Object[]
                {
                    bookCopy.getBook().getTitle(),
                    bookCopy.getBook().getAuthor(),
                    bookCopy.getCallNumber()
                });
                System.out.println("sdfdsgdfgdfg");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void clearResultTable()
    {
        int numberOfRow = resultTable.getModel().getRowCount();

        if (numberOfRow > 0)
        {
            DefaultTableModel tableModel = (DefaultTableModel) this.resultTable.getModel();
            for (int index = (numberOfRow - 1); index >= 0; index--)
            {
                tableModel.removeRow(index);
            }
        }
    }

    @Override
    public void clearInput()
    {
        serachKeywordJTextField.setText("");
    }

    @Override
    public String getKeyword()
    {
        if (serachKeywordJTextField.getText() != null)
        {
            return serachKeywordJTextField.getText();
        } else
        {
            return null;
        }

    }

    @Override
    public JTable getResultTable()
    {
        return resultTable;
    }

    @Override
    public boolean isResultSelected()
    {
        return (this.resultTable.getSelectedRow() >= 0);
    }
    @Override
    public String getSelectedCallNumber() {
        int selectedRowIndex = this.resultTable.getSelectedRow();
        
        String callNumber = this.resultTable.getValueAt(selectedRowIndex, 2).toString();
        return callNumber; 
    }

    @Override
    public void displaySelectedDetail(BookCopy bookCopy)
    {
        try
        {
            this.clearDetailTable();

                ((DefaultTableModel) this.resultDetailTable.getModel()).addRow(new Object[]
                {
                    bookCopy.getBook().getIsbn(),
                    bookCopy.getCallNumber(),
                    bookCopy.getBook().getTitle(),
                    bookCopy.getBook().getAuthor(),
                    bookCopy.getBook().getPublisher(),
                    bookCopy.getBook().getType(),
                    getImageIcon(bookCopy.getBook().getThumbnail()),
                    bookCopy.getBook().getDescription(),
                    bookCopy.getBook().getUrl(),
                });
                System.out.println("sdfdsgdfgdfg");
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    
    @Override
    public void clearDetailTable()
    {
        int numberOfRow = resultDetailTable.getModel().getRowCount();

        if (numberOfRow > 0)
        {
            DefaultTableModel tableModel = (DefaultTableModel) resultDetailTable.getModel();
            for (int index = (numberOfRow - 1); index >= 0; index--)
            {
                tableModel.removeRow(index);
            }
        }
    }
    
    @Override
    public void displayMessageInDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    
    
    public ImageIcon getImageIcon(String imageAddress)
    {
        try
        {
            ImageIcon icon = new ImageIcon(imageAddress);
            return icon;
        } catch (Exception e)
        {
            
        }
        return null;
    }
    

}
