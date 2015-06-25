package flymetomars.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yli on 10/03/15.
 */
@DatabaseTable(tableName = "missions")
public class Mission extends SeriablizableEntity {
    @DatabaseField(canBeNull = false)
    private Date time;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Person captain;
    @DatabaseField(canBeNull = false)
    private String location;
    @DatabaseField()
    private String description;

    private Set<Person> participantSet = new HashSet<Person>();
    private Set<Invitation> invitationSet = new HashSet<Invitation>();

    public Mission()
    {
        time = new Date();
        name = new String();
        captain = new Person();
        location = new String();
        description = new String();

    }

    public Mission(Date time, String name, Person captain, String location, String description, Set<Person> participantSet, Set<Invitation> invitationSet)
    {
        this.time = time;
        this.name = name;
        this.captain = captain;
        this.location = location;
        this.description = description;
        this.participantSet = participantSet;
        this.invitationSet = invitationSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new IllegalArgumentException("time cannot set to null. nameCannotSetToNull");
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        if (time == null)
            throw new IllegalArgumentException("time cannot set to null. timeCannotSetToNull");
        this.time = time;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location) {
        if (location == null)
            throw new IllegalArgumentException("location cannot set to null. locationCannotSetToNull");
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Invitation> getInvitationSet() {
        return invitationSet;
    }

    public void setInvitationSet(Set<Invitation> invitationSet) {
        if (invitationSet != null)
            for (Invitation invitation : invitationSet)
            {
                if (!invitation.getMission().equals(this))
                    throw new IllegalArgumentException("Mission field of the invitation should be mission itself" +
                            "missionOfInvitationSetBeTheMissionItself");
            }
        this.invitationSet = invitationSet;
    }

    public Set<Person> getParticipantSet() {
        return participantSet;
    }

    public void setParticipantSet(Set<Person> participantSet) {
        if (participantSet == null)
            throw new IllegalArgumentException("ParticipantSet cannot be null.participantSetNotNull");
        if (participantSet.size() < 1)
            throw new IllegalArgumentException("Participant in a mission should be more than 0 person.participantSetNotEmpty");
        this.participantSet = participantSet;
    }

    public Person getCaptain() {

        return captain;
    }

    public void setCaptain(Person captain) {
        if (captain == null)
            throw new IllegalArgumentException("Captain can't set to null.captainNotNull");
        if (!getParticipantSet().contains(captain))
            throw new IllegalArgumentException("Captain should be in participantSet.participantSetContainsCaptain");
        this.captain = captain;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Mission mission = (Mission)o;

        return name.equals(mission.name);

    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode();
    }


}
