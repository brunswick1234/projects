package flymetomars.dataaccess;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yli on 10/03/15.
 */
public interface DAO<T> {
    T load(Long id) throws SQLException;

    List<T> getAll() throws SQLException;

    void save(T object) throws SQLException;

    void update(T object) throws SQLException;

    void saveOrUpdate(T object) throws SQLException;

    T delete(T object) throws SQLException;

}
