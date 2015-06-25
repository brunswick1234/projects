/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repository;

import entity.Availability;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author dell
 */
public class AvailabilityRepository
{
    private EntityManagerFactory entityMangerFactory;
    private EntityManager entityManager;
    
     public AvailabilityRepository(String puName)
    {
        entityMangerFactory = Persistence.createEntityManagerFactory(puName);
        entityManager = entityMangerFactory.createEntityManager();
    }
     
     
     public Availability findByHotelIdAndDate(int hotelId, Date date)
     {
        Query query = entityManager.createNamedQuery(Availability.FIND_BY_HOTEL_ID_AND_DATE);
        query.setParameter("hotelId", hotelId);
        query.setParameter("date", date);
        List<Availability> result = query.getResultList();
        return result.get(0);
     }
     
     
     public void updateAvailabillity(Availability availability)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(availability);
            entityManager.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
