package flymetomars.dataaccess.ormlite;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import flymetomars.dataaccess.InvitationDAO;
import flymetomars.model.Expertise;
import flymetomars.model.Invitation;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by dell on 5/30/2015.
 */
public class InvitationDAOImpl extends AbstractEntityDAOImpl<Invitation> implements InvitationDAO
{

    public InvitationDAOImpl(ConnectionSource connectionSource) throws SQLException
    {
        dao = DaoManager.createDao(connectionSource, Invitation.class);
    }

    @Override
    public Set<Invitation> getInvitationsByCreator(Person person) throws SQLException
    {
        return new LinkedHashSet<>(dao.queryForEq("creator_id", person.getId()));
    }

    @Override
    public Set<Invitation> getInvitationsByRecipient(Person person) throws SQLException
    {
        return new LinkedHashSet<>(dao.queryForEq("recipient_id", person.getId()));
    }
}
