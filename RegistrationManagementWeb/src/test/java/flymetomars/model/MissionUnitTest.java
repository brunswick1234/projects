package flymetomars.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by dell on 3/29/2015.
 */
public class MissionUnitTest
{
    private Mission m;

    @Before
    public void setUp()
    {
        m = new Mission();
    }


    @Test
    public void timeCannotSetToNull()
    {
        try
        {
            m.setTime(null);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains timeCannotSetToNull", e.getMessage().contains("timeCannotSetToNull"));
        }
    }


    @Test
    public void locationCannotSetToNull()
    {
        try
        {
            m.setLocation(null);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains locationCannotSetToNull", e.getMessage().contains("locationCannotSetToNull"));
        }
    }


    @Test
    public void nameCannotSetToNull()
    {
        try
        {
            m.setName(null);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains nameCannotSetToNull", e.getMessage().contains("nameCannotSetToNull"));
        }
    }

    @Test
    public void participantSetNotNull()
    {
        try
        {

            m.setParticipantSet(null);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains participantSetNotNull", e.getMessage().contains("participantSetNotNull"));
        }

    }



    @Test
    public void participantSetNotEmpty()
    {
        try
        {
            Set<Person> participantSet = new HashSet<>();
            m.setParticipantSet(participantSet);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains participantSetNotEmpty", e.getMessage().contains("participantSetNotEmpty"));
        }

    }


    @Test
    public void captainNotNull()
    {
        try
        {
            m.setCaptain(null);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains captainNotNull", e.getMessage().contains("captainNotNull"));
        }
    }

    @Test
    public void participantSetContainsCaptain()
    {

        Person p2 = new Person();
        p2.setEmail("p2@mail.com");
        Person p3 = new Person();
        p3.setEmail("p3@mail.com");
        Set<Person> participantSet = new HashSet<>();
        participantSet.add(p2);
        participantSet.add(p3);
        m.setParticipantSet(participantSet);
        Person p1 = new Person();
        p1.setEmail("p1@mail.com");
        try
        {
            m.setCaptain(p1);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains participantSetContainsCaptain", e.getMessage().contains("participantSetContainsCaptain"));
        }


    }


    @Test
    public void missionOfInvitationSetBeTheMissionItself()
    {
        Mission mission = new Mission();
        mission.setName("m11");
        Invitation invitation = mock(Invitation.class);
        when(invitation.getMission()).thenReturn(mission);
        Set<Invitation> invitationSet = new HashSet<>();
        invitationSet.add(invitation);

        try
        {
            m.setInvitationSet(invitationSet);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains missionOfInvitationSetBeTheMissionItself",
                    e.getMessage().contains("missionOfInvitationSetBeTheMissionItself"));
        }

    }


    @Test
    public void sameNameSameMission()
    {
        String missionName = "Ma100";
        m.setName(missionName);

        Mission mission = new Mission();
        mission.setName(missionName);

        assertEquals("Same mission", m, mission);

    }


    @Test
    public void differentNameDifferentMission()
    {
        String missionName = "Ma100";
        m.setName(missionName);

        Mission mission = new Mission();
        mission.setName("Ma200");

        assertNotEquals("different mission", m, mission);

    }


}

