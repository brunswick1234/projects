package flymetomars.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
@DatabaseTable(tableName = "person_mission")
public class PersonMission extends SeriablizableEntity {
    // https://github.com/j256/ormlite-jdbc/tree/master/src/test/java/com/j256/ormlite/examples/manytomany
    // https://stackoverflow.com/questions/9175198/what-is-the-best-way-to-implement-many-to-many-relationships-using-ormlite

    public static final String PERSON_ID_FIELD_NAME = "person_id";
    public static final String MISSION_ID_FIELD_NAME = "mission_id";

    @DatabaseField(foreign = true, columnName = PERSON_ID_FIELD_NAME)
    private Person person;

    @DatabaseField(foreign = true, columnName = MISSION_ID_FIELD_NAME)
    private Mission mission;

    public PersonMission() {
    }

    public PersonMission(Person person, Mission mission) {
        this.person = person;
        this.mission = mission;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
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

        Invitation invitation = (Invitation) o;

        return invitation.getId().equals(this.getId());

    }

    @Override
    public int hashCode()
    {
        return 31 * getId().hashCode();
    }
}
