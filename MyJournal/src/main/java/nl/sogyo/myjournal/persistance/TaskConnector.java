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
import nl.sogyo.myjournal.domain.Task;


public class TaskConnector {

	public static void addTask(int dayID, String newTask) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(Day.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();
	    Transaction tx = session.beginTransaction();
	    Criteria criteria = session.createCriteria(Day.class);
	    Day day = (Day) criteria.add(Restrictions.eq("dayID", dayID)).uniqueResult();
		tx.commit();
		
		config = new Configuration().configure().addAnnotatedClass(Task.class);
	    reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    sf = config.buildSessionFactory(reg);
	    session = sf.openSession();
	    tx = session.beginTransaction();
    	session.save(new Task(newTask, day));
	    tx.commit();
	}
	
	public static void deleteTask(int taskID) {
		Configuration config = new Configuration().configure().addAnnotatedClass(Task.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();
	    Transaction tx = session.beginTransaction();
	    Criteria criteria = session.createCriteria(Task.class);
	    Task task = (Task) criteria.add(Restrictions.eq("taskID", taskID)).uniqueResult();
	    session.delete(task);
		tx.commit();
	}
	
}
