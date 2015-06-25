/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "loan")
public class Loan implements Serializable
{
    @Id
    private int id;
    @OneToOne
    @JoinColumn(name = "member_id")
    private LibraryUser libraryUser;
    @Column(name = "date_loan_made")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateLoanMade;
    @Column(name = "date_loan_due")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataLoanDue;
    
    @JoinColumn(name ="book_in_loan")
    @OneToOne
    private Book bookInLoan;

    public Loan()
    {
    }
    
    
    
}
