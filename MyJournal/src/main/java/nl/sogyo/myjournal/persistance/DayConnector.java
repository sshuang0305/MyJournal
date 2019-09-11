/**
 * DayConnector.java
 *
 * @author Shan Shan Huang
 * @since 08-07-19
 */

package nl.sogyo.myjournal.persistance;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import nl.sogyo.myjournal.domain.Day;
import nl.sogyo.myjournal.domain.User;

public class DayConnector {

	public static Day getSelectedDay(int userID, String date) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(User.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();
	    Transaction tx = session.beginTransaction();
	    Criteria criteria = session.createCriteria(User.class);
		User user = (User) criteria.add(Restrictions.eq("userID", userID)).uniqueResult();
		tx.commit();
		
	    config = new Configuration().configure().addAnnotatedClass(Day.class);
	    reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    sf = config.buildSessionFactory(reg);
	    session = sf.openSession();
	    tx = session.beginTransaction();
	    criteria = session.createCriteria(Day.class);
		Day selectedDay = (Day) criteria.add(Restrictions.eq("user", user))
										 .add(Restrictions.eq("date", date))
										 .uniqueResult();
	    if (selectedDay == null) {
	    	selectedDay = new Day(date, user);
	    	session.save(selectedDay);
	    }
	    tx.commit();
	    return selectedDay;
	}
	
	public static void saveRating(int dayID, int rating) {

	    Configuration config = new Configuration().configure().addAnnotatedClass(Day.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();
	    Transaction tx = session.beginTransaction();
	    Criteria criteria = session.createCriteria(Day.class);
		Day day = (Day) criteria.add(Restrictions.eq("dayID", dayID)).uniqueResult();
		day.setDayRating(rating);
		session.save(day);
		tx.commit();
	}
}
