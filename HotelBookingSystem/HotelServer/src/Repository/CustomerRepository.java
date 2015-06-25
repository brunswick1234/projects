/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repository;

import entity.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author dell
 */
public class CustomerRepository
{
    private EntityManagerFactory entityMangerFactory;
    private EntityManager entityManager;
    
    public CustomerRepository(String puName)
    {
        entityMangerFactory = Persistence.createEntityManagerFactory(puName);
        entityManager = entityMangerFactory.createEntityManager();
    }
    
    
    public void addCustomer(Customer customer)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(customer);
            entityManager.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public int getNewCstomerId()
    {
        Query query = entityManager.createNamedQuery(Customer.FIND_MAX_ID);
        
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
