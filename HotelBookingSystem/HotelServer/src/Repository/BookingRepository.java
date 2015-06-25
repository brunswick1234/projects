/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repository;

import entity.Booking;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author dell
 */
public class BookingRepository
{
    private EntityManagerFactory entityMangerFactory;
    private EntityManager entityManager;
    public BookingRepository(String puName)
    {
        entityMangerFactory = Persistence.createEntityManagerFactory(puName);
        entityManager = entityMangerFactory.createEntityManager();
    }
    
    
    public void addBooking(Booking booking)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(booking);
            entityManager.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
     public int getNewBookingId()
    {
        Query query = entityManager.createNamedQuery(Booking.FIND_MAX_ID);
        
       if (query.getSingleResult() != null)
        {
            
            int id = (int)query.getSingleResult();
            id++;
            return id;
        }
       else
       {
           return 1;
       }
    }
}
