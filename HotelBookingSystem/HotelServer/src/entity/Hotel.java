/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "HOTEL")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = Hotel.GET_ALL, query = "SELECT h FROM Hotel h"),
    @NamedQuery(name = "Hotel.findByHotelId", query = "SELECT h FROM Hotel h WHERE h.hotelId = :hotelId"),
    @NamedQuery(name = "Hotel.findByCity", query = "SELECT h FROM Hotel h WHERE h.city = :city"),
    @NamedQuery(name = "Hotel.findByRate", query = "SELECT h FROM Hotel h WHERE h.rate = :rate")
})
public class Hotel implements Serializable
{
    public static final String GET_ALL = "Hotel.findAll";
    public static final String FIND_BY_CITY = "Hotel.findByCity";
    public static final String FIND_BY_HOTEL_ID = "Hotel.findByHotelId";
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HOTEL_ID")
    private Integer hotelId;
    @Column(name = "CITY")
    private String city;
    @Column(name = "RATE")
    private Integer rate;

    public Hotel()
    {
    }

    public Hotel(Integer hotelId)
    {
        this.hotelId = hotelId;
    }

    public Integer getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(Integer hotelId)
    {
        this.hotelId = hotelId;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Integer getRate()
    {
        return rate;
    }

    public void setRate(Integer rate)
    {
        this.rate = rate;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (hotelId != null ? hotelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hotel))
        {
            return false;
        }
        Hotel other = (Hotel) object;
        if ((this.hotelId == null && other.hotelId != null) || (this.hotelId != null && !this.hotelId.equals(other.hotelId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entity.Hotel[ hotelId=" + hotelId + " ]";
    }
    
}
