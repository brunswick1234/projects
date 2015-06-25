package flymetomars.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by dell on 3/29/2015.
 */
public class InvitationUnitTest
{
    private Invitation i;

    @Before
    public void setUp()
    {
        i = new Invitation();
    }


    @Test
    public void missionCannotSetToNull()
    {
        try
        {
            i.setMission(null);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains missionCannotSetToNull", e.getMessage().contains("missionCannotSetToNull"));
        }
    }


    @Test
    public void creatorCannotSetToNull()
    {
        try
        {
            i.setCreator(null);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains creatorCannotSetToNull", e.getMessage().contains("creatorCannotSetToNull"));
        }
    }


    @Test
    public void recipientCannotSetToNull()
    {
        try
        {
            i.setRecipient(null);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains recipientCannotSetToNull", e.getMessage().contains("recipientCannotSetToNull"));
        }
    }


    @Test
    public void lastUpdatedCannotSetToNull()
    {
        try
        {
            i.setLastUpdated(null);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains lastUpdatedCannotSetToNull", e.getMessage().contains("lastUpdatedCannotSetToNull"));
        }
    }



    @Test
    public void invitationStatusCannotSetToNull()
    {
        try
        {
            i.setStatus(null);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains statusCannotSetToNull", e.getMessage().contains("statusCannotSetToNull"));
        }
    }







}
