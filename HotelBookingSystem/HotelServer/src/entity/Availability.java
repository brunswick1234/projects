/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "AVAILABILITY")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Availability.findAll", query = "SELECT a FROM Availability a"),
    @NamedQuery(name = "Availability.findByHotelId", query = "SELECT a FROM Availability a WHERE a.availabilityPK.hotelId = :hotelId"),
    @NamedQuery(name = "Availability.findByDate", query = "SELECT a FROM Availability a WHERE a.availabilityPK.date = :date"),
    @NamedQuery(name = "Availability.findByRoomAvailable", query = "SELECT a FROM Availability a WHERE a.roomAvailable = :roomAvailable"),
    @NamedQuery(name = "Availability.findByHotelIdAndDate", 
            query = "SELECT a FROM Availability a WHERE a.availabilityPK.hotelId = :hotelId AND a.availabilityPK.date = :date")
})
public class Availability implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_HOTEL_ID_AND_DATE = "Availability.findByHotelIdAndDate";
    @EmbeddedId
    protected AvailabilityPK availabilityPK;
    @Column(name = "ROOM_AVAILABLE")
    private Integer roomAvailable;
    @JoinColumn(name = "HOTEL_ID", referencedColumnName = "HOTEL_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Hotel hotel;

    public Availability()
    {
    }

    public Availability(AvailabilityPK availabilityPK, Integer roomAvailable, Hotel hotel)
    {
        this.availabilityPK = availabilityPK;
        this.roomAvailable = roomAvailable;
        this.hotel = hotel;
    }
    
    

    public Availability(AvailabilityPK availabilityPK)
    {
        this.availabilityPK = availabilityPK;
    }

    public Availability(int hotelId, Date date)
    {
        this.availabilityPK = new AvailabilityPK(hotelId, date);
    }

    public AvailabilityPK getAvailabilityPK()
    {
        return availabilityPK;
    }

    public void setAvailabilityPK(AvailabilityPK availabilityPK)
    {
        this.availabilityPK = availabilityPK;
    }

    public Integer getRoomAvailable()
    {
        return roomAvailable;
    }

    public void setRoomAvailable(Integer roomAvailable)
    {
        this.roomAvailable = roomAvailable;
    }

    public Hotel getHotel()
    {
        return hotel;
    }

    public void setHotel(Hotel hotel)
    {
        this.hotel = hotel;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (availabilityPK != null ? availabilityPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Availability))
        {
            return false;
        }
        Availability other = (Availability) object;
        if ((this.availabilityPK == null && other.availabilityPK != null) || (this.availabilityPK != null && !this.availabilityPK.equals(other.availabilityPK)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entity.Availability[ availabilityPK=" + availabilityPK + " ]";
    }
    
}
