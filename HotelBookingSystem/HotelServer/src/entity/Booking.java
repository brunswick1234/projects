/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "BOOKING")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b"),
    @NamedQuery(name = "Booking.findByBookingId", query = "SELECT b FROM Booking b WHERE b.bookingId = :bookingId"),
    @NamedQuery(name = "Booking.findByCheckInDate", query = "SELECT b FROM Booking b WHERE b.checkInDate = :checkInDate"),
    @NamedQuery(name = "Booking.findByCheckOutDate", query = "SELECT b FROM Booking b WHERE b.checkOutDate = :checkOutDate"),
    @NamedQuery(name = "Booking.findMaxId", query = "SELECT MAX(b.bookingId) FROM Booking b"),
    @NamedQuery(name = "Booking.findByNumberOfRooms", query = "SELECT b FROM Booking b WHERE b.numberOfRooms = :numberOfRooms")
})
public class Booking implements Serializable
{
    public static final String FIND_MAX_ID = "Booking.findMaxId";
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "BOOKING_ID")
    private Integer bookingId;
    @Column(name = "CHECK_IN_DATE")
    @Temporal(TemporalType.DATE)
    private Date checkInDate;
    @Column(name = "CHECK_OUT_DATE")
    @Temporal(TemporalType.DATE)
    private Date checkOutDate;
    @Column(name = "NUMBER_OF_ROOMS")
    private Integer numberOfRooms;
    @JoinColumn(name = "HOTEL_ID", referencedColumnName = "HOTEL_ID")
    @ManyToOne
    private Hotel hotelId;
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
    @ManyToOne
    private Customer customerId;

    public Booking()
    {
    }

    public Booking(Integer bookingId, Date checkInDate, Date checkOutDate, Integer numberOfRooms, Hotel hotelId, Customer customerId)
    {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfRooms = numberOfRooms;
        this.hotelId = hotelId;
        this.customerId = customerId;
    }
    
    
    

    public Booking(Integer bookingId)
    {
        this.bookingId = bookingId;
    }

    public Integer getBookingId()
    {
        return bookingId;
    }

    public void setBookingId(Integer bookingId)
    {
        this.bookingId = bookingId;
    }

    public Date getCheckInDate()
    {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate)
    {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate()
    {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate)
    {
        this.checkOutDate = checkOutDate;
    }

    public Integer getNumberOfRooms()
    {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms)
    {
        this.numberOfRooms = numberOfRooms;
    }

    public Hotel getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(Hotel hotelId)
    {
        this.hotelId = hotelId;
    }

    public Customer getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Customer customerId)
    {
        this.customerId = customerId;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (bookingId != null ? bookingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking))
        {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.bookingId == null && other.bookingId != null) || (this.bookingId != null && !this.bookingId.equals(other.bookingId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entity.Booking[ bookingId=" + bookingId + " ]";
    }
    
}
