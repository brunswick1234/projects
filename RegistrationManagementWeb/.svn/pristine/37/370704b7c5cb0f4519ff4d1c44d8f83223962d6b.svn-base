package flymetomars.mining;

import flymetomars.dataaccess.ExpertiseDAO;
import flymetomars.dataaccess.InvitationDAO;
import flymetomars.dataaccess.MissionDAO;
import flymetomars.dataaccess.PersonDAO;
import flymetomars.model.Expertise;
import flymetomars.model.Invitation;
import flymetomars.model.Mission;
import flymetomars.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class EntityMinerTest
{
    private EntityMiner miner;
    private PersonDAO pDao;
    private MissionDAO mDao;
    private ExpertiseDAO eDao;
    private InvitationDAO iDao;

    @Before
    public void setUp()
    {
        pDao = mock(PersonDAO.class);
        mDao = mock(MissionDAO.class);
        eDao = mock(ExpertiseDAO.class);
        iDao = mock(InvitationDAO.class);

        miner = new EntityMiner(pDao, mDao, eDao, iDao);
    }

    @Test
    public void onePersonIsAlsoMostPopularPerson()
    {
        // create a single person
        Person p = new Person();
        p.setEmail("abc@abc.net.au");
        ArrayList<Person> list = new ArrayList<>();
        list.add(p);

        // mock the behaviour of pDao
        try
        {
            when(pDao.getAll()).thenReturn(list);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        Person mostPopularPerson = miner.getMostPopularPerson();
        assertEquals("One person is also most popular", p, mostPopularPerson);
    }


    @Test
    public void celebritiesSizeNotLargerThanNoOfAllPerson()
    {
        List<Person> list = new ArrayList<>(Arrays.asList(new Person(), new Person()));
        try
        {
            when(pDao.getAll()).thenReturn(list);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            miner.getCelebrarity(10);
            fail("No exception thrown");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains celebritiesSizeNotLargerThanNoOfAllPerson",
                    e.getMessage().contains("celebritiesSizeNotLargerThanNoOfAllPerson"));
        }

    }


    @Test
    public void celebritiesSizeNotLessThan0()
    {
        try
        {
            miner.getCelebrarity(-3);
            fail("No exception thrown");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains celebritiesSizeNotLessThan0",
                    e.getMessage().contains("celebritiesSizeNotLessThan0"));
        }

    }



    @Test
    public void getTop3PersonsMostInvited()
    {

        Person p1 = mock(Person.class);
        Set<Invitation> invitations1 = mock(HashSet.class);
        when(p1.getInvitationsReceived()).thenReturn(invitations1);
        when(invitations1.size()).thenReturn(1);

        Person p2 = mock(Person.class);
        Set<Invitation> invitations2 = mock(HashSet.class);
        when(p2.getInvitationsReceived()).thenReturn(invitations2);
        when(invitations2.size()).thenReturn(2);

        Person p3 = mock(Person.class);
        Set<Invitation> invitations3 = mock(HashSet.class);
        when(p3.getInvitationsReceived()).thenReturn(invitations3);
        when(invitations3.size()).thenReturn(3);

        Person p4 = mock(Person.class);
        Set<Invitation> invitations4 = mock(HashSet.class);
        when(p4.getInvitationsReceived()).thenReturn(invitations4);
        when(invitations4.size()).thenReturn(4);

        Person p5 = mock(Person.class);
        Set<Invitation> invitations5 = mock(HashSet.class);
        when(p5.getInvitationsReceived()).thenReturn(invitations5);
        when(invitations5.size()).thenReturn(5);

        ArrayList<Person> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);

        try
        {
            when(pDao.getAll()).thenReturn(list);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        List<Person> celebrities = miner.getCelebrarity(3);
        assertArrayEquals("Most invited person", celebrities.toArray(), new Person[]{p5, p4, p3});

    }


    @Test
    public void buddiesSizeNotLargerThanNoOfFriend()
    {
        Person p1 = mock(Person.class);
        Person p2 = new Person();
        when(p1.getFriends()).thenReturn(new ArrayList<Person>(Arrays.asList(p2)));
        try
        {
            miner.getBuddies(new Person(), 10);
            fail("No exception thrown");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains buddiesSizeNotLargerThanNoOfFriend",
                    e.getMessage().contains("buddiesSizeNotLargerThanNoOfFriend"));
        }

    }


    @Test
    public void buddiesSizeNotLessThan0()
    {
        try
        {
            miner.getBuddies(new Person (),- 3);
            fail("No exception thrown");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains buddiesSizeNotLessThan0",
                    e.getMessage().contains("buddiesSizeNotLessThan0"));
        }

    }



    @Test
    public void getTop3Buddies()
    {
        Mission m1 = mock(Mission.class);
        Mission m2 = mock(Mission.class);
        Mission m3 = mock(Mission.class);
        Mission m4 = mock(Mission.class);

        Person p1 = mock(Person.class);
        Person p2 = mock(Person.class);
        Person p3 = mock(Person.class);
        Person p4 = mock(Person.class);
        Person p5 = mock(Person.class);
        Person p6 = mock(Person.class);

        when(p1.getMissionRegistered()).thenReturn(new HashSet<>(Arrays.asList(m1, m2, m3, m4)));
        when(p1.getFriends()).thenReturn(new ArrayList<>(Arrays.asList(p2,p3,p4,p5,p6)));
        when(p2.getMissionRegistered()).thenReturn(new HashSet<>(Arrays.asList(m1, m2, m3, m4)));
        when(p3.getMissionRegistered()).thenReturn(new HashSet<>(Arrays.asList(m1, m2)));
        when(p4.getMissionRegistered()).thenReturn(new HashSet<>(Arrays.asList(m2, m3, m4)));
        when(p5.getMissionRegistered()).thenReturn(new HashSet<>(Arrays.asList(m1)));
        when(p6.getMissionRegistered()).thenReturn(new HashSet<>(Arrays.asList(m4)));


        List<Person> list = new ArrayList<>(Arrays.asList(p1, p2, p3, p4, p5));
        try
        {
            when(pDao.getAll()).thenReturn(list);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        List<Person> buddies = miner.getBuddies(p1, 3);
        assertArrayEquals("Most Interacts Person", buddies.toArray(), new Person[]{p2, p4, p3});


    }


    @Test
    public void testGetSocialCircle()
    {

        Person p1 = mock(Person.class);
        Person p2 = mock(Person.class);
        Person p3 = mock(Person.class);
        Person p4 = mock(Person.class);
        Person p5 = mock(Person.class);
        Person p6 = mock(Person.class);


        Mission m1 = mock(Mission.class);
        when(m1.getParticipantSet()).thenReturn(new HashSet<Person>(Arrays.asList(p1, p2, p3, p4)));
        Mission m2 = mock(Mission.class);
        when(m2.getParticipantSet()).thenReturn(new HashSet<Person>(Arrays.asList(p2, p6)));
        Mission m3 = mock(Mission.class);
        when(m3.getParticipantSet()).thenReturn(new HashSet<Person>(Arrays.asList(p1, p3, p5)));

        when(p1.getMissionRegistered()).thenReturn(new HashSet<Mission>(Arrays.asList(m1,m3)));
        when(p2.getMissionRegistered()).thenReturn(new HashSet<Mission>(Arrays.asList(m2,m1)));
        when(p3.getMissionRegistered()).thenReturn(new HashSet<Mission>(Arrays.asList(m1,m3)));
        when(p4.getMissionRegistered()).thenReturn(new HashSet<Mission>(Arrays.asList(m1)));
        when(p5.getMissionRegistered()).thenReturn(new HashSet<Mission>(Arrays.asList(m3)));
        when(p6.getMissionRegistered()).thenReturn(new HashSet<Mission>(Arrays.asList(m2)));

        Set<Person> socialCircle = miner.getSocialCircle(p1);
        assertEquals("test circle", socialCircle, new HashSet<Person>(Arrays.asList(p1, p2, p3, p4)));


    }

    @Test
    public void ifPersonHasNoFriendCircleOnlyContainsThisPerson()
    {
        Person p1 = mock(Person.class);
        Mission m1 = mock(Mission.class);
        when(m1.getParticipantSet()).thenReturn(new HashSet<Person>(Arrays.asList(p1)));
        when(p1.getMissionRegistered()).thenReturn(new HashSet<Mission>(Arrays.asList(m1)));

        Set<Person> socialCircle = miner.getSocialCircle(p1);
        assertEquals("Only this Person", socialCircle, new HashSet<Person>(Arrays.asList(p1)));

    }

    @Test
    public void ifPersonHasNot0MissionCircleOnlyContainsThisPerson()
    {
        Person p1 = mock(Person.class);
        when(p1.getMissionRegistered()).thenReturn(new HashSet<Mission>());
        Set<Person> socialCircle = miner.getSocialCircle(p1);
        assertEquals("Only this Person", socialCircle, new HashSet<Person>(Arrays.asList(p1)));

    }



    @Test
    public void sourGrapesSizeNotLargerThanNoOfMissionNotParticipate()
    {
        List<Mission> list = new ArrayList<>(Arrays.asList(new Mission(), new Mission(), new Mission()));

        try
        {
            when(mDao.getAll()).thenReturn(list);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        Person person = mock(Person.class);

        try
        {
            miner.getSourGrapes(person, 10);
            fail("No exception thrown");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains sourGrapesSizeNotLargerThanNoOfMissionNotParticipate",
                    e.getMessage().contains("sourGrapesSizeNotLargerThanNoOfMissionNotParticipate"));
        }
    }

    @Test
    public void sourGrapesSizeNotLessThan0()
    {
        try
        {
            miner.getSourGrapes(new Person(), -3);
            fail("No exception thrown");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains sourGrapesSizeNotLessThan0",
                    e.getMessage().contains("sourGrapesSizeNotLessThan0"));
        }

    }


    @Test
    public void getTop2SourGrapes()
    {
        Mission m1 = mock(Mission.class);
        m1.setName("m1");
        Mission m2 = mock(Mission.class);
        m2.setName("m2");
        Mission m3 = mock(Mission.class);
        m3.setName("m3");
        Mission m4 = mock(Mission.class);
        m4.setName("m4");
        Mission m5 = mock(Mission.class);
        m5.setName("m5");
        Mission m6 = mock(Mission.class);
        m6.setName("m6");

        Person p1 = mock(Person.class);
        p1.setEmail("p1@mail.com");

        Person p2 = mock(Person.class);
        p2.setEmail("p2@mail.com");
        Person p3 = mock(Person.class);
        p3.setEmail("p3@mail.com");
        Person p4 = mock(Person.class);
        p4.setEmail("p4@mail.com");
        Person p5 = mock(Person.class);
        p5.setEmail("p5@mail.com");
        Person p6 = mock(Person.class);
        p6.setEmail("p6@mail.com");

        //Register all person in one mission so that they all all friend to p1
        when(m1.getParticipantSet()).thenReturn(new HashSet<>(Arrays.asList(p1, p2, p3, p4, p5, p6)));
        when(p1.getMissionRegistered()).thenReturn(new HashSet<Mission>(Arrays.asList(m1)));

        when(m2.getParticipantSet()).thenReturn(new HashSet<>(Arrays.asList(p2, p3, p4, p5, p6)));
        when(m3.getParticipantSet()).thenReturn(new HashSet<>(Arrays.asList(p3, p2, p4, p5)));
        when(m4.getParticipantSet()).thenReturn(new HashSet<>(Arrays.asList(p2, p3, p4)));
        when(m5.getParticipantSet()).thenReturn(new HashSet<>(Arrays.asList(p3)));
        when(m6.getParticipantSet()).thenReturn(new HashSet<>(Arrays.asList(p2, p4)));

        try
        {
            when(mDao.getAll()).thenReturn(new ArrayList<Mission>(Arrays.asList(m1, m2, m3, m4, m5, m6)));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        List<Mission> sourGrapes = miner.getSourGrapes(p1, 2);
        assertArrayEquals("sour grapes", sourGrapes.toArray(), new Mission[]{m2, m3});

    }


    @Test
    public void ifTwoPersonHaveCommonMissionTheyAreFriend()
    {
        Person p1 = mock(Person.class);
        Person p2 = mock(Person.class);
        Mission m1 = mock(Mission.class);
        when(m1.getParticipantSet()).thenReturn(new HashSet<>(Arrays.asList(p1, p2)));
        when(p1.getMissionRegistered()).thenReturn(new HashSet<Mission>(Arrays.asList(m1)));
        when(p2.getMissionRegistered()).thenReturn(new HashSet<Mission>(Arrays.asList(m1)));

        assertTrue("they are friend",miner.isFriends(p1,p2));

    }




}