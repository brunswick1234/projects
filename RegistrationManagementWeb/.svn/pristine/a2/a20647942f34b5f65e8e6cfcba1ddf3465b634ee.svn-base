package flymetomars.dataaccess.ormlite;

import com.j256.ormlite.dao.Dao;
import flymetomars.dataaccess.DAO;
import flymetomars.model.SeriablizableEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public abstract class AbstractEntityDAOImpl<T extends SeriablizableEntity> implements DAO<T> {
    protected Dao<T, Long> dao;

    @Override
    public T load(Long id) throws SQLException {
        return dao.queryForId(id);
    }

    @Override
    public List<T> getAll() throws SQLException {
        return dao.queryForAll();
    }

    @Override
    public void save(T object) throws SQLException {
        dao.create(object);
    }

    @Override
    public void update(T object) throws SQLException {
        dao.update(object);
    }

    @Override
    public void saveOrUpdate(T object) throws SQLException {
        if (null != object.getId() && null != load(object.getId())) {
            save(object);
        } else {
            update(object);
        }
    }

    @Override
    public T delete(T object) throws SQLException {
        dao.delete(object);
        return object;
    }
}
