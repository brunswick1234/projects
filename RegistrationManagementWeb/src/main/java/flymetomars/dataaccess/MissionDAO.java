package flymetomars.dataaccess;

import flymetomars.model.Mission;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.Set;

/**
 * Created by yli on 10/03/15.
 */
public interface MissionDAO extends DAO<Mission>{
    Set<Mission> getMissionsByCreator(Person person) throws SQLException;
}
