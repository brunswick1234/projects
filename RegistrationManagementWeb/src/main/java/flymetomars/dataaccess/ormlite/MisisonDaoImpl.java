package flymetomars.dataaccess.ormlite;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import flymetomars.dataaccess.MissionDAO;
import flymetomars.model.Mission;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class MisisonDaoImpl extends AbstractEntityDAOImpl<Mission> implements MissionDAO {

    public MisisonDaoImpl(ConnectionSource connectionSource) throws SQLException {
        dao = DaoManager.createDao(connectionSource, Mission.class);
    }

    @Override
    public Set<Mission> getMissionsByCreator(Person person) throws SQLException {
        return new LinkedHashSet<>(dao.queryForEq("caption", person));
    }
}
