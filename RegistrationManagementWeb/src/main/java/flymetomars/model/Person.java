package flymetomars.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yli on 10/03/15.
 */
@DatabaseTable(tableName = "persons")
public class Person extends SeriablizableEntity
{
    @DatabaseField
    private String firstName;

    @DatabaseField(canBeNull = false)
    private String lastName;

    @DatabaseField(canBeNull = false, unique = true)
    private String email;

    @DatabaseField(canBeNull = false)
    private String password;

    @ForeignCollectionField
    private Collection<Expertise> expertise;

    private Set<Mission> missionRegistered;
    private Set<Invitation> invitationsReceived;


    public Person()
    {
        expertise = new HashSet<>();
        missionRegistered = new HashSet<>();
        invitationsReceived = new HashSet<>();
    }


    public Person(String email)
    {
        this.email = email;
    }


    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        if (firstName == null)
        {
            throw new IllegalArgumentException("FirstName cannot be null.firstNameNotNull");
        } else if (firstName.trim().equals(""))
        {
            throw new IllegalArgumentException("FirstName cannot be empty.firstNameNotEmpty");
        }
        this.firstName = firstName;
    }

    public String getLastName()
    {

        return lastName;
    }

    public void setLastName(String lastName)
    {
        if (lastName == null)
        {
            throw new IllegalArgumentException("LastName cannot be null.lastNameNotNull");
        } else if (lastName.trim().equals(""))
        {
            throw new IllegalArgumentException("LastName cannot be empty.lastNameNotEmpty");
        }
        this.lastName = lastName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        if (null == password)
        {
            throw new IllegalArgumentException("Password cannot be null. passwordNotNull");
        } else if (password.trim().equals(""))
        {
            throw new IllegalArgumentException("Password cannot be empty. passwordNotEmpty");
        } else if (password.trim().length() < 6)
        {
            throw new IllegalArgumentException("Password cannot be shorter than 6 characters. passwordLengthLongerThanSix");
        } else if (!validatePasswordOnlyContainsNumberOrCharacters(password))
        {
            throw new IllegalArgumentException("Password only contains number or letter. passwordOnlyContainsNumberAndLetter");
        }
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        if (email == null)
            throw new IllegalArgumentException("Email cannot be null. emailNotNull");
        if (!validateEmail(email))
            throw new IllegalArgumentException("Invalid email format");
        this.email = email;
    }

    public Set<Mission> getMissionRegistered()
    {
        return missionRegistered;
    }

    public void setMissionRegistered(Set<Mission> missionRegistered)
    {
        for (Mission mission : missionRegistered)
        {
            if (!mission.getParticipantSet().contains(this))
                throw new IllegalArgumentException("All mission's participated should include the person, " +
                        "registerMissionsParticipateSetContainThisPerson");
        }
        this.missionRegistered = missionRegistered;
    }

    public Set<Invitation> getInvitationsReceived()
    {
        return invitationsReceived;
    }

    public void setInvitationsReceived(Set<Invitation> invitationsReceived)
    {
        if (invitationsReceived != null)
            for (Invitation invitation : invitationsReceived)
            {
                if (!invitation.getRecipient().equals(this))
                    throw new IllegalArgumentException("Recipient should be person himself. recipientOfInvitationBePersonHimself");
            }
        this.invitationsReceived = invitationsReceived;
    }

    public Collection<Expertise> getExpertise()
    {
        return expertise;
    }

    public void setExpertise(Set<Expertise> expertise)
    {
        for (Expertise e : expertise)
        {

            if (!e.getHolder().equals(this))
                throw new IllegalArgumentException("Holder should be this person. setExpertiseWithOtherPersonAsHolder");
        }
        this.expertise = expertise;
    }


    public boolean validatePasswordOnlyContainsNumberOrCharacters(String password)
    {
        char[] chars = password.trim().toCharArray();
        for (char c : chars)
        {
            if (c > '9')
                if (c < 'A' || c > 'Z')
                    if (c < 'a' || c > 'z')
                        return false;
        }

        return true;

    }


    public boolean validateEmail(String email)
    {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }


    public void addExpertise(Expertise exp)
    {
        if (null == exp)
        {
            throw new IllegalArgumentException("Expertise cannot be null.");
        } else if (null == exp.getDescription())
        {
            throw new IllegalArgumentException("Expertise cannot have null description.");
        } else if (exp.getDescription().trim().isEmpty())
        {
            throw new IllegalArgumentException("Expertise cannot have empty description.");
        } else if (!exp.getHolder().equals(this))
        {
            throw new IllegalArgumentException("Expertise's holder should be this person. newExpertiseWithOtherPersonAsHolder");
        }
        expertise.add(exp);
    }



    public ArrayList<Person> getFriends()
    {
        ArrayList<Person> friends = new ArrayList<>();
        for (Mission m : missionRegistered)
        {
            for(Person p : m.getParticipantSet())
                if (!p.equals(this) && !friends.contains(p))
                    friends.add(p);

        }

        return friends;

    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Person person = (Person) o;

        return email.equals(person.email);

    }

    @Override
    public int hashCode()
    {
        return 31 * email.hashCode();
    }



}
