/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "CUSTOMER")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustomerId", query = "SELECT c FROM Customer c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name"),
    @NamedQuery(name = "Customer.findByPhone", query = "SELECT c FROM Customer c WHERE c.phone = :phone"),
    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
    @NamedQuery(name = "Customer.findMaxId", query = "SELECT MAX(c.customerId) FROM Customer c"),
    @NamedQuery(name = "Customer.findByCreditCardNumber", query = "SELECT c FROM Customer c WHERE c.creditCardNumber = :creditCardNumber")
})
public class Customer implements Serializable
{
    public static final String FIND_MAX_ID = "Customer.findMaxId";
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CUSTOMER_ID")
    private Integer customerId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "CREDIT_CARD_NUMBER")
    private String creditCardNumber;
    @OneToMany(mappedBy = "customerId")
    private Collection<Booking> bookingCollection;

    public Customer()
    {
    }

    public Customer(Integer customerId, String name, String phone, String email, String creditCardNumber)
    {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.creditCardNumber = creditCardNumber;
    }
    
    

    public Customer(Integer customerId)
    {
        this.customerId = customerId;
    }

    public Integer getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Integer customerId)
    {
        this.customerId = customerId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getCreditCardNumber()
    {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber)
    {
        this.creditCardNumber = creditCardNumber;
    }

    @XmlTransient
    public Collection<Booking> getBookingCollection()
    {
        return bookingCollection;
    }

    public void setBookingCollection(Collection<Booking> bookingCollection)
    {
        this.bookingCollection = bookingCollection;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer))
        {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entity.Customer[ customerId=" + customerId + " ]";
    }
    
}
