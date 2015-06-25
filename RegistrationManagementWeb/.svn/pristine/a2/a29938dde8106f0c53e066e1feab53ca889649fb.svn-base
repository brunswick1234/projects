package flymetomars.mining;


import flymetomars.dataaccess.ExpertiseDAO;
import flymetomars.dataaccess.InvitationDAO;
import flymetomars.dataaccess.MissionDAO;
import flymetomars.dataaccess.PersonDAO;
import flymetomars.model.Expertise;
import flymetomars.model.Mission;
import flymetomars.model.Person;

import java.util.*;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class EntityMiner
{
    private PersonDAO personDAO;
    private MissionDAO missionDAO;
    private ExpertiseDAO expertiseDAO;
    private InvitationDAO invitationDAO;

    public EntityMiner(PersonDAO personDAO, MissionDAO missionDAO, ExpertiseDAO expertiseDAO, InvitationDAO invitationDAO)
    {
        this.personDAO = personDAO;
        this.missionDAO = missionDAO;
        this.expertiseDAO = expertiseDAO;
        this.invitationDAO = invitationDAO;
    }

    /**
     * Return the most popular person by the count of invitations received.
     *
     * @return The most popular person.
     */
    public Person getMostPopularPerson()
    {
        List<Person> persons = null;
        try
        {
            persons = personDAO.getAll();
        } catch (Exception e)
        {

        }
        int maxInvites = 0;
        Person result = null;
        for (Person p : persons)
        {
            int noInvites = p.getInvitationsReceived().size();
            if (maxInvites <= noInvites)
            {
                maxInvites = noInvites;
                result = p;
            }
        }
        return result;
    }


    /**
     * Get a list of persons of the given size with the most invitations received.
     *
     * @param size the size of the set to be returned.
     * @return the set of the most popular person by invitations received.
     */
    public List<Person> getCelebrarity(int size)
    {
        if (size < 0)
            throw new IllegalArgumentException("size should not less than 0"
                    + ", celebritiesSizeNotLessThan0");
        List<Person> persons = null;
        try
        {
            persons = personDAO.getAll();
        } catch (Exception e)
        {

        }
        if (size > persons.size())
            throw new IllegalArgumentException("size should not larger than" + persons.size()
                    + ", celebritiesSizeNotLargerThanNoOfAllPerson");
        ArrayList<Person> celebrities = new ArrayList<>();

        for (int i = 0; i < size; i++)
        {
            int maxInvites = 0;
            Person result = null;
            for (Person person : persons)
            {
                int noInvites = person.getInvitationsReceived().size();
                if (maxInvites < noInvites)
                {
                    maxInvites = noInvites;
                    result = person;
                }
            }
            celebrities.add(result);
            persons.remove(result);

        }
        return celebrities;
    }

    /**
     * Get the list of the given size of persons with the most missions registered.
     *
     * @param person the person of the buddies to be returned.
     * @param size   the size of the set to be returned.
     * @return the set of the busiest persons.
     */
    public List<Person> getBuddies(Person person, int size)
    {
        if (size < 0)
            throw new IllegalArgumentException("size should not less than 0"
                    + ", buddiesSizeNotLessThan0");
        Set<Mission> missions = person.getMissionRegistered();
        List<Person> persons = null;
        try
        {
            persons =personDAO.getAll();
        }catch (Exception e)
        {

        }
        ArrayList<Person> friends = person.getFriends();
        if (size > friends.size())
            throw new IllegalArgumentException("size should not larger than" + friends.size() + ",buddiesSizeNotLargerThanNoOfFriends");
        List<Person> buddies = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            int maxMissionCount = 0;
            Person result = null;
            for (Person p : persons)
            {
                if (p.equals(person))
                    continue;
                Set<Mission> ms = p.getMissionRegistered();
                ms.retainAll(missions);
                if (maxMissionCount < ms.size())
                {
                    maxMissionCount = ms.size();
                    result = p;
                }

            }
            buddies.add(result);
            persons.remove(result);
        }



        return buddies;
    }

    /**
     * Given a person, return the largest group of persons (including this person)
     * such that each pair of persons are connected by some mission (i.e., they all
     * know each other through these missions).
     *
     * @param person the person of the social circle.
     * @Return the social circle of the given person.
     */
    public Set<Person> getSocialCircle(Person person)
    {
        ArrayList<Person> friends = new ArrayList<>();
        Set<Mission> missions = person.getMissionRegistered();
        for (Mission m : missions)
        {

            for(Person p : m.getParticipantSet())
                if (!p.equals(person))
                    friends.add(p);

        }

        ArrayList<ArrayList<Person>> allSubset = getAllSubset(friends);


        int maxSize = 0;
        ArrayList<Person> result = new ArrayList<>();


        for (ArrayList<Person> subset : allSubset)
        {
            boolean brokenCircle = false;
            for (Person p1 : subset)
            {
                for (Person p2 : subset)
                {
                    if (p1.equals(p2))
                        continue;
                    if (!isFriends(p1, p2))
                    {
                        brokenCircle = true;
                        break;
                    }

                }
                if (brokenCircle)
                    break;
            }
            if (brokenCircle)
                continue;
            else
            if (maxSize < subset.size())
            {
                maxSize = subset.size();
                result = subset;
            }
        }

        Set<Person> socialCircle = new HashSet<>(result);
        socialCircle.add(person);

        return socialCircle;
    }

    /**
     * Given a person, return the top-k upcoming missions that his/her friends
     * have been invited to but he/she hasn’t been (ranked by the number of
     * his/her friends who have been invited).
     *
     * @param person the person of the sour grape.
     * @param size   the max number of upcoming missions that the person is not invited to.
     * @return the ranked list of the person's friends who are invited to
     * a mission that the person is not invited to.
     */
    public List<Mission> getSourGrapes(Person person, int size)
    {
        if (size < 0)
            throw new IllegalArgumentException("size should not less than 0"
                    + ", sourGrapesSizeNotLessThan0");
        List<Mission> sourGrapes = new ArrayList<>();
        Set<Mission> missions = person.getMissionRegistered();
        Set<Person> friends = new HashSet<>();
        for (Mission m : missions)
        {
            Set<Person> participantSet = m.getParticipantSet();
            for (Person p : participantSet)
            {
                if (!p.equals(person))
                    friends.add(p);
            }
        }

        List<Mission> allMission = null;
        try
        {
           allMission = missionDAO.getAll();
        } catch (Exception e)
        {

        }

        List<Mission> missionsNotParticipant = new ArrayList<>();
        for (Mission m : allMission)
        {
            if (!m.getParticipantSet().contains(person))
                missionsNotParticipant.add(m);
        }
        if (size > missionsNotParticipant.size())
            throw new IllegalArgumentException("size should not larger than " + missionsNotParticipant.size() +
                    ", sourGrapesSizeNotLargerThanNoOfMissionNotParticipate");
        for (int i = 0; i < size; i++)
        {
            int maxNoFriends = 0;
            Mission result = null;
            for (Mission m : missionsNotParticipant)
            {

                Set<Person> set = m.getParticipantSet();
                set.retainAll(friends);
                int numberFriends = set.size();

                if (maxNoFriends < numberFriends)
                {
                    maxNoFriends = numberFriends;
                    result = m;
                }
            }
            sourGrapes.add(result);
            missionsNotParticipant.remove(result);

        }

        return sourGrapes;
    }


    private ArrayList<ArrayList<Person>> getAllSubset(ArrayList<Person> persons)
    {
        ArrayList<ArrayList<Person>> allSet = new ArrayList<ArrayList<Person>>();
        allSet.add(new ArrayList<Person>());

        for (int i= 0; i<persons.size(); i++)
        {
            ArrayList<ArrayList<Person>> subset = new ArrayList<ArrayList<Person>>();

            for (int j= 0; j < allSet.size(); j++)
            {
                ArrayList<Person> element = new ArrayList<Person>();

                element.add(persons.get(i));

                element.addAll(allSet.get(j));

                subset.add(element);
            }
            allSet.addAll(subset);
        }

        return allSet;
    }






    public boolean isFriends(Person p1, Person p2)
    {
        Set<Mission> missions = p1.getMissionRegistered();
        for (Mission m : missions)
        {
            if (m.getParticipantSet().contains(p2))
                return true;
        }
        return false;
    }





}
