package flymetomars.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yli on 16/03/15.
 */
@DatabaseTable(tableName = "expertise")
public class Expertise extends SeriablizableEntity {
    @DatabaseField(canBeNull = false)
    private String description;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Person holder;

    public Expertise() {
        description = new String();
    }

    public Expertise(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null)
        {
            throw new IllegalArgumentException("description cannot be null.descriptionNotNull");
        } else if (description.trim().equals(""))
        {
            throw new IllegalArgumentException("description cannot be empty.descriptionNotEmpty");
        }
        this.description = description;
    }

    public Person getHolder() {
        return holder;
    }

    public void setHolder(Person holder) {
        this.holder = holder;
    }
}
