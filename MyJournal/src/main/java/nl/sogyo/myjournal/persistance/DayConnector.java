package nl.sogyo.myjournal.persistance;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import nl.sogyo.myjournal.domain.Day;

public class DayConnector {

	public static Day connect(int userID, String date) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(Day.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();
	
	    Transaction tx = session.beginTransaction();
	
	    Criteria criteria = session.createCriteria(Day.class);
	    Day selectedDay = (Day) criteria
		    .add(Restrictions.eq("date", date))
		    .add(Restrictions.eq("userID", userID)).uniqueResult();
	    
	    if (selectedDay == null) {
	    	selectedDay = new Day(date, new ArrayList<String>(), "", 50, userID);
	    	session.save(selectedDay);

	    }

	    tx.commit();
	    return selectedDay;
	}
	
	public static Day addNotes(int userID, String date, String notes) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(Day.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();
	
	    Transaction tx = session.beginTransaction();
	
	    Criteria criteria = session.createCriteria(Day.class);
	    Day selectedDay = (Day) criteria
		    .add(Restrictions.eq("date", date))
		    .add(Restrictions.eq("userID", userID)).uniqueResult();

	    selectedDay.setNotes(notes);
	    session.save(selectedDay);

	    tx.commit();
	    return selectedDay;
	}
}
