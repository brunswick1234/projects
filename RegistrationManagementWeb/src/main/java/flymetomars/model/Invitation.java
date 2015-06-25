package flymetomars.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by yli on 10/03/15.
 */
@DatabaseTable(tableName = "invitation")
public class Invitation extends SeriablizableEntity {


    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Mission mission;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Person creator;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Person recipient;

    @DatabaseField(canBeNull = false)
    private Date lastUpdated;
    @DatabaseField(canBeNull = false)
    private String status;

    public Invitation() {
    }

    public Mission getMission() {
        return mission;
    }


    public void setMission(Mission mission) {
        if (mission == null)
            throw new IllegalArgumentException("Mission can't set to null.missionCannotSetToNull");
        this.mission = mission;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        if (creator == null)
            throw new IllegalArgumentException("Creator can't set to null.creatorCannotSetToNull");
        this.creator = creator;
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        if (status == null)
            throw new IllegalArgumentException("status can't set to null.statusCannotSetToNull");
        this.status = status;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        if (lastUpdated == null)
            throw new IllegalArgumentException("LastUpdated can't set to null.lastUpdatedCannotSetToNull");
        this.lastUpdated = lastUpdated;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person recipient) {
        if (recipient == null)
            throw new IllegalArgumentException("Recipient can't set to null.recipientCannotSetToNull");
        this.recipient = recipient;
    }



}
