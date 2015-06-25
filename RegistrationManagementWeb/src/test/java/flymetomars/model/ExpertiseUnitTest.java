package flymetomars.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by dell on 3/31/2015.
 */
public class ExpertiseUnitTest
{
    private Expertise e;

    @Before
    public void setUp()
    {
        e = new Expertise();
    }


    @Test
    public void descriptionNotNullOrEmpty()
    {
        try
        {
            e.setDescription(null);
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains descriptionNotNull", e.getMessage().contains("descriptionNotNull"));
        }


        try
        {
            e.setDescription("");
            fail("No exception thrown.");
        } catch (Exception e)
        {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains descriptionNotEmpty", e.getMessage().contains("descriptionNotEmpty"));
        }
    }



}


