/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "library_user")
public class LibraryUser implements Serializable
{
    @Id
    private int id;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    private String email;
    private String password;
    private String type;
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;

    public LibraryUser()
    {
    }

    
    public LibraryUser(int id, String lastName, String firstName, String email, String password, String type, String address, String phoneNumber)
    {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.type = type;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getId()
    {
        return id;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public String getType()
    {
        return type;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    
    
    
}
