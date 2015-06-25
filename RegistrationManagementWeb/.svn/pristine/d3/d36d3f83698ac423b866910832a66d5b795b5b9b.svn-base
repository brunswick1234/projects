package flymetomars.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

/**
 * Created by yli on 16/03/15.
 */
public class PersonUnitTest {
    private Person p;

    @Before
    public void setUp() {
        p = new Person();

    }

    @Test
    public void expertiseNotNull() {
        assertNotNull("expertise not null", p.getExpertise());
    }

    @Test
    public void passwordNotNullOrEmpty() {
        try {
            p.setPassword(null);
            fail("No exception thrown for null password");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains null", e.getMessage().contains("null"));
        }

        try {
            p.setPassword("");
            fail("No exception thrown for empty password");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains empty", e.getMessage().contains("empty"));
        }
    }

    @Test
    public void firstNameNotNullOrEmpty()
    {
        try {
            p.setFirstName(null);
            fail("No exception thrown.");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains firstNameNotNull", e.getMessage().contains("firstNameNotNull"));
        }

        try {
            p.setFirstName("");
            fail("No exception thrown.");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains firstNameNotEmpty", e.getMessage().contains("firstNameNotEmpty"));
        }
    }


    @Test
    public void lastNameNotNullOrEmpty()
    {
        try {
            p.setLastName(null);
            fail("No exception thrown.");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains lastNameNotNull", e.getMessage().contains("lastNameNotNull"));
        }

        try {
            p.setLastName("");
            fail("No exception thrown.");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains lastNameNotEmpty", e.getMessage().contains("lastNameNotEmpty"));
        }
    }


    @Test
    public void passwordLengthLongerThanSix()
    {
        try
        {
            p.setPassword("12345");
            fail("No exception thrown.");
        }catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains passwordLengthLongerThanSix", e.getMessage().contains("passwordLengthLongerThanSix"));
        }


    }

    @Test
    public void passwordOnlyContainsNumberAndLetter()
    {
        try
        {
            p.setPassword("@3aa4$% ");
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains passwordOnlyContainsNumberAndLetter",
                    e.getMessage().contains("passwordOnlyContainsNumberAndLetter"));
        }

    }


    @Test
    public void differentPersonNotEqual() {
        p.setEmail("abc@abc.net.au");
        Object o = "abc@abc.net.au";
        assertNotEquals("String not Person", p, o);

        Person q = new Person();
        assertNotEquals("q doesn't have an email", p, q);

        q.setEmail("abc@abc.net");
        assertNotEquals("Different emails", p, q);
    }


    @Test
    public void emailNotNull()
    {
        try
        {
            p.setEmail(null);
            fail("No exception thrown");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains emailNotNull", e.getMessage().contains("emailNotNull"));

        }
    }



    @Test
    public void emailFormatTest()
    {
        try
        {
            p.setEmail("dgfdgrdf.com");
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Should throw IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains email format", e.getMessage().contains("email format"));
        }
    }

    @Test
    public void sameEmailSamePerson()
    {
        String email = "abc@abc.net.au";
        p.setEmail(email);

        Person q = new Person();
        q.setEmail(email);

        assertEquals("Same person", p, q);

        p.setLastName("Foo");
        q.setLastName("Bar");

        assertEquals("Names don't matter", p, q);
    }

    @Test
    public void newNullExpertiseThrowsIAE() {
        try {
            p.addExpertise(null);
            fail("No exception thrown for null expertise");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains null", e.getMessage().contains("null"));
        }
    }

    @Test
    public void newExpertiseNullDescriptionThrowsIAE() {
        // mock an Expertise object with expected behaviour
        Expertise exp = mock(Expertise.class);
        when(exp.getDescription()).thenReturn(null);

        try {
            p.addExpertise(exp);
            fail("No exception thrown for null expertise description");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains null", e.getMessage().contains("null"));
            assertTrue("Message contains description", e.getMessage().contains("description"));
        }
    }

    @Test
    public void newExpertiseWithNonemptyDescription() {
        // mock an Expertise object with expected behaviour
        Expertise exp = mock(Expertise.class);
        when(exp.getDescription()).thenReturn("fishing");
        when(exp.getHolder()).thenReturn(p);
        assertEquals("Empty collection", 0, p.getExpertise().size());
        p.addExpertise(exp);
        assertEquals("1 expertise", 1, p.getExpertise().size());
    }



    @Test
    public void recipientOfInvitationBePersonHimself()
    {
        Set<Invitation> invitationsReceived = new HashSet<>();
        Person person = new Person();
        person.setEmail("dgf@email.com");
        Invitation invitation = mock(Invitation.class);
        when(invitation.getRecipient()).thenReturn(person);
        invitationsReceived.add(invitation);
        try
        {
            p.setInvitationsReceived(invitationsReceived);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Should throw IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains recipientOfInvitationBePersonHimself",
                    e.getMessage().contains("recipientOfInvitationBePersonHimself"));
        }
    }


    @Test
    public void registerMissionsParticipateSetContainThisPerson()
    {
        Person p1 = new Person("p1@mail.com");
        Person p2 = new Person("p2@mail.com");
        p.setEmail("p@mail.com");

        Mission mission = mock(Mission.class);
        when(mission.getParticipantSet()).thenReturn(new HashSet<Person>(Arrays.asList(p1,p2)));
        HashSet<Mission> set = new HashSet();
        set.add(mission);

        try
        {
            p.setMissionRegistered(set);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Should throw IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains registerMissionsParticipateSetContainThisPerson",
                    e.getMessage().contains("registerMissionsParticipateSetContainThisPerson"));
        }

    }


    @Test
    public void newExpertiseWithOtherPersonAsHolder()
    {
        Person person = new Person("asd@mail.com");
        Expertise exp = mock(Expertise.class);
        when(exp.getDescription()).thenReturn("description");
        when(exp.getHolder()).thenReturn(person);
        p.setEmail("aaa@mail.com");

        try
        {
            p.addExpertise(exp);
            fail("No exception thrown.");
        }catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains newExpertiseWithOtherPersonAsHolder", e.getMessage().contains("newExpertiseWithOtherPersonAsHolder"));
        }
    }


    @Test
    public void setExpertiseWithOtherPersonAsHolder()
    {
        Person person = new Person("asd@mail.com");
        Expertise exp = mock(Expertise.class);
        when(exp.getDescription()).thenReturn("description");
        when(exp.getHolder()).thenReturn(person);
        Expertise exp2 = mock(Expertise.class);
        when(exp2.getDescription()).thenReturn("description");
        when(exp2.getHolder()).thenReturn(p);
        p.setEmail("aaa@mail.com");

        try
        {
            p.setExpertise(new HashSet<>(Arrays.asList(exp,exp2)));
            fail("No exception thrown.");
        }catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains setExpertiseWithOtherPersonAsHolder",
                    e.getMessage().contains("setExpertiseWithOtherPersonAsHolder"));
        }
    }

}