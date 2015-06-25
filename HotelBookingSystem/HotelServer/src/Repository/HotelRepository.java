/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import entity.Availability;
import entity.Hotel;
import java.util.ArrayList;
import java.util.HashSet;
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
public class HotelRepository
{

    private EntityManagerFactory entityMangerFactory;
    private EntityManager entityManager;
    public HotelRepository(String puName)
    {
        entityMangerFactory = Persistence.createEntityManagerFactory(puName);
        entityManager = entityMangerFactory.createEntityManager();
    }

    public Set<String> findAllCities()
    {
        Set<String> citiesSet = new HashSet<>();
        Query query = entityManager.createNamedQuery(Hotel.GET_ALL);
        if (query.getResultList() != null)
        {
            List<Hotel> hotels = query.getResultList();
            for (Hotel hotel : hotels)
            {
                System.out.println(hotel.getCity());
                citiesSet.add(hotel.getCity());
            }
        }

        return citiesSet;
    }

    public Hotel findByCity(String city)
    {
        Query query = entityManager.createNamedQuery(Hotel.FIND_BY_CITY);
        query.setParameter("city", city);
        if (!query.getResultList().isEmpty())
        {
            List<Hotel> hotels = query.getResultList();
            return hotels.get(0);
        } else
        {
            return null;
        }
    }
    
    
    public Hotel findByHotelId(int hotelId)
    {
        Query query = entityManager.createNamedQuery(Hotel.FIND_BY_HOTEL_ID);
        query.setParameter("hotelId", hotelId);
        if (!query.getResultList().isEmpty())
        {
            List<Hotel> hotels = query.getResultList();
            return hotels.get(0);
        } else
        {
            return null;
        }
    }
    
    
    
    
    

}
