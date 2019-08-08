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
	
	private final User user;
	private final String date;
	private Day selectedDay;
	
	public DayConnector(int theUserID, String theDate) {
	    Configuration config = new Configuration().configure().addAnnotatedClass(User.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();
	    Transaction tx = session.beginTransaction();
	    Criteria criteria = session.createCriteria(User.class);
		this.user = (User) criteria.add(Restrictions.eq("userID", theUserID)).uniqueResult();
		this.date = theDate;
		tx.commit();
	}

	public Day getSelectedDay() {
	    Configuration config = new Configuration().configure().addAnnotatedClass(Day.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();
	    Transaction tx = session.beginTransaction();
	    Criteria criteria = session.createCriteria(Day.class);
		this.selectedDay = (Day) criteria.add(Restrictions.eq("user", this.user))
										 .add(Restrictions.eq("date", this.date))
										 .uniqueResult();
	    if (this.selectedDay == null) {
	    	this.selectedDay = new Day(this.date, this.user);
	    	session.save(this.selectedDay);
	    }
	    tx.commit();
	    return this.selectedDay;
	}
}
