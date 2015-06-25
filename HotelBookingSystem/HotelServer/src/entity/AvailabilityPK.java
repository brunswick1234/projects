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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dell
 */
@Embeddable
public class AvailabilityPK implements Serializable
{
    @Basic(optional = false)
    @Column(name = "HOTEL_ID")
    private int hotelId;
    @Basic(optional = false)
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;

    public AvailabilityPK()
    {
    }

    public AvailabilityPK(int hotelId, Date date)
    {
        this.hotelId = hotelId;
        this.date = date;
    }

    public int getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(int hotelId)
    {
        this.hotelId = hotelId;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (int) hotelId;
        hash += (date != null ? date.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AvailabilityPK))
        {
            return false;
        }
        AvailabilityPK other = (AvailabilityPK) object;
        if (this.hotelId != other.hotelId)
        {
            return false;
        }
        if ((this.date == null && other.date != null) || (this.date != null && !this.date.equals(other.date)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entity.AvailabilityPK[ hotelId=" + hotelId + ", date=" + date + " ]";
    }
    
}
