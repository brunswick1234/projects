package flymetomars.dataaccess.ormlite;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import flymetomars.dataaccess.ExpertiseDAO;
import flymetomars.model.Expertise;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class ExpertiseDAOImpl extends AbstractEntityDAOImpl<Expertise> implements ExpertiseDAO {

    public ExpertiseDAOImpl(ConnectionSource connectionSource) throws SQLException {
        dao = DaoManager.createDao(connectionSource, Expertise.class);
    }

    @Override
    public Set<Expertise> getExpertiseByPerson(Person person) throws SQLException {
        return new LinkedHashSet<>(dao.queryForEq("holder", person));
    }
}
