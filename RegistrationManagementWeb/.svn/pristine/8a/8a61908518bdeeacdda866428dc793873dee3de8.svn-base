package flymetomars.app;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;
import spark.resource.ClassPathResource;
import spark.utils.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import static org.h2.util.IOUtils.closeSilently;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class AppSystemTest {

    @BeforeClass
    public static void setUp() throws Exception {
        ClassPathResource resource = new ClassPathResource( "app.properties" );
        Properties properties = new Properties();
        InputStream stream = null;
        try {
            stream = resource.getInputStream();
            properties.load(stream);
            int port = Integer.parseInt(properties.getProperty("spark.port"));
            setBaseUrl("http://localhost:" + port);
        } finally {
            closeSilently(stream);
        }

        App.main(null);

        try {
            Thread.sleep(500);
        } catch (Exception ignored) {
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        Spark.stop();
    }

    @Test
    public void basePageShouldContainWelcome() {
        String path = "/";
        beginAt(path);
        assertTextPresent("Welcome");
    }

    /*@Test
    public void testRegister()
    {
        String path = "/register";
        beginAt(path);
        assertTextPresent("User Registration");
        setTextField("email","abcde@123.com");
        setTextField("password","123asd");
        setTextField("firstName","Jack");
        setTextField("lastName","Green");
        setTextField("expertise","running");
        clickButton("submitButton");
        assertTextPresent("Welcome back:");
    }*/

    @Test
    public void testLoginWithCorrectNameAndPassword()
    {
        String path = "/login";
        beginAt(path);
        assertTextPresent("User Login");
        setTextField("user_name","abcde@123.com");
        setTextField("password","123asd");
        clickButton("loginButton");
        assertTextPresent("Welcome back:");

    }


    @Test
    public void testLoginWithIncorrectName()
    {
        String path = "/login";
        beginAt(path);
        assertTextPresent("User Login");
        setTextField("user_name","xfdgdfg@df.com");
        setTextField("password","123asd");
        clickButton("loginButton");
        assertTextPresent("Invalid email/password combination.");
    }

    @Test
    public void testLoginWithIncorrectPassword()
    {
        String path = "/login";
        beginAt(path);
        assertTextPresent("User Login");
        setTextField("user_name","abcde@123.com");
        setTextField("password","123");
        clickButton("loginButton");
        assertTextPresent("Invalid email/password combination.");
    }

    /*
    @Test
    public void testRegisterWithInvalidEmailFormat()
    {
        String path = "/register";
        beginAt(path);
        assertTextPresent("User Registration");

        setTextField("email", "sdfdsfuhrfnfjnjf.cdg");


        setTextField("password","1esdfgr");
        setTextField("firstName","Len");
        setTextField("lastName","Green");
        setTextField("expertise","running");
        try
        {
            clickButton("submitButton");
        }
        catch (Exception e)
        {

        }

        assertTextPresent("Invalid email format");
    }*/


    @Test
    public void testLoginOut()
    {
        String path = "/login";
        beginAt(path);
        assertTextPresent("User Login");
        setTextField("user_name","abcde@123.com");
        setTextField("password","123asd");
        clickButton("loginButton");
        clickLink("logoutLink");
        assertTextPresent("Login or register.");

    }

    @Test
    public void clickPersonsDirectToPersonsPage()
    {
        String path = "/";
        beginAt(path);
        clickLink("personsLink");
        assertTextPresent("Person Listing Page");

    }


    @Test
    public void clickMissionsDirectToMissionsPage()
    {
        String path = "/";
        beginAt(path);
        clickLink("missionsLink");
        assertTextPresent("Mission Listing Page");

    }

    @Test
    public void clickPersonDirectToPersonPage()
    {
        String path = "/login";
        beginAt(path);
        assertTextPresent("User Login");
        setTextField("user_name","abcde@123.com");
        setTextField("password","123asd");
        clickButton("loginButton");
        clickLink("personLink");
        assertTextPresent("Jack Green's Page");

    }


    @Test
    public void noMissionDisplayedInPersonPageForPersonWithoutMission()
    {
        String path = "/login";
        beginAt(path);
        assertTextPresent("User Login");
        setTextField("user_name","abcde@123.com");
        setTextField("password","123asd");
        clickButton("loginButton");
        clickLink("personLink");
        assertTextPresent("Jack Green's Page");
        assertTextPresent("It appears there isn't any mission yet.");

    }


    @Test
    public void noInvitationDisplayedInPersonPageForPersonWithoutInvitation()
    {
        String path = "/login";
        beginAt(path);
        assertTextPresent("User Login");
        setTextField("user_name","abcde@123.com");
        setTextField("password","123asd");
        clickButton("loginButton");
        clickLink("personLink");
        assertTextPresent("Jack Green's Page");
        assertTextPresent("It appears there isn't any invitation yet.");

    }

    @Test
    public void displayAllMissionsInMissionsPage()
    {
        String path = "/missions";
        beginAt(path);
        assertTextPresent("go to moon");
        assertTextPresent("go to mars");

    }

    @Test
    public void displayAllPersonInPersonsPage()
    {
        String path = "/persons";
        beginAt(path);
        assertTextPresent("Jack Green");
    }


    @Test
    public void cannotCreateMissionWithoutLogin()
    {
        String path = "/mission/create";
        beginAt(path);
        setTextField("missionName","go to sun");
        setTextField("time","01/01/2016 10 am");
        setTextField("location","sun");
        setTextField("description","go to the sun");
        clickButton("createMission");
        assertTextPresent("Error: You need to log in before creating a mission.");

    }

    @Test
    public void cannotCreateInvitationWithoutLogin()
    {
        String path = "/mission/1/create_invitation";
        beginAt(path);
        setTextField("recipientEmail","abc@123.com");
        setTextField("lastUpdate","01/01/2016 10 am");
        setTextField("status","created");
        clickButton("createInvitation");
        assertTextPresent("Error: You need to log in before creating a invitation.");

    }

    @Test
    public void displayMissionsForAPersonInPersonPage()
    {
        String path = "/login";
        beginAt(path);
        assertTextPresent("User Login");
        setTextField("user_name","aaa@123.com");
        setTextField("password","123456");
        clickButton("loginButton");
        clickLink("personLink");
        assertTextPresent("go to mars");
        assertTextPresent("go to moon");

    }

    @Test
    public void displayInvitationsForAPersonInPersonPage()
    {

        String path = "/login";
        beginAt(path);
        assertTextPresent("User Login");
        setTextField("user_name","bbb@123.com");
        setTextField("password","123456");
        clickButton("loginButton");
        clickLink("personLink");
        assertTextPresent("go to moon, May 31, 2015 10:00:00 AM, send");

    }

    @Test
    public void cannotEditInvitationWithoutLogin()
    {
        String path = "/invitation/1/edit";
        beginAt(path);
        setTextField("recipientEmail","abc@123.com");
        setTextField("lastUpdate","01/01/2016 10 am");
        setTextField("status","created");
        clickButton("saveInvitation");
        assertTextPresent("Error: You need to log in before editing an invitation.");

    }


















}