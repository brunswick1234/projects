package flymetomars.dataaccess;

import flymetomars.model.Person;

import java.sql.SQLException;

/**
 * Created by yli on 10/03/15.
 */
public interface PersonDAO extends DAO<Person> {
    Person getPersonByEmail(String email) throws SQLException;
}
