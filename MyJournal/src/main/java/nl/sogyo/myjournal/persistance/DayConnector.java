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

public class DayConnector {
	
	private final int userID;
	private final String date;
	private Day selectedDay;
    private final Configuration config = new Configuration().configure().addAnnotatedClass(Day.class);
    private final ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
    private final SessionFactory sf = config.buildSessionFactory(reg);
    private final Session session = sf.openSession();
    private final Transaction tx = session.beginTransaction();
    private final Criteria criteria = session.createCriteria(Day.class);
	
	public DayConnector(int theUserID, String theDate) {
		this.userID = theUserID;
		this.date = theDate;
	    this.selectedDay = (Day) this.criteria
		    .add(Restrictions.eq("date", date))
		    .add(Restrictions.eq("userID", userID)).uniqueResult();
	}

	public Day connect() {
	    if (this.selectedDay == null) {
	    	this.selectedDay = new Day(date, userID);
	    	this.session.save(this.selectedDay);
	    }
	    tx.commit();
	    return this.selectedDay;
	}
	
	public Day addNotes(String notes) {
	    this.selectedDay.setNotes(notes);
	    this.session.save(this.selectedDay);
	    this.tx.commit();
	    return this.selectedDay;
	}
	
	public Day saveRating(int dayRating) {
	    this.selectedDay.setDayRating(dayRating);
	    this.session.save(this.selectedDay);
	    this.tx.commit();
	    return this.selectedDay;
	}
	
	public Day addTask(String task) {
	    this.selectedDay.addNewTask(task);
	    this.session.save(this.selectedDay);
	    this.tx.commit();
	    return this.selectedDay;
	}
	
	public Day deleteNotes(String notes) {
	    this.selectedDay.setNotes("");
	    this.session.save(this.selectedDay);
	    this.tx.commit();
	    return this.selectedDay;
	}
	
	public Day deleteTask(String task) {
	    this.selectedDay.deleteTask(task);
	    this.session.save(this.selectedDay);
	    this.tx.commit();
	    return selectedDay;
	}
}
