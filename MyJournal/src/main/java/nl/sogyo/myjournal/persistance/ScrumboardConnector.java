package nl.sogyo.myjournal.persistance;

import java.util.ArrayList;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import nl.sogyo.myjournal.domain.Scrumboard;
import nl.sogyo.myjournal.domain.User;

public class ScrumboardConnector {

	public static Scrumboard save(String projectName, int userID, Set<String> userStories) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(User.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();

	    Transaction tx = session.beginTransaction();
	    Criteria cr = session.createCriteria(User.class);
	    cr.add(Restrictions.eq("userID", userID));
	    User user = (User) cr.uniqueResult();

		Scrumboard newScrumboard= new Scrumboard(projectName, userStories);
		user.addScrumboard(newScrumboard);
	    session.save(user);
	    tx.commit();
	    return newScrumboard;

	}
	
	public static ArrayList<Scrumboard> getBoards(int userID) {
		
		Configuration config = new Configuration().configure().addAnnotatedClass(User.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();

	    Transaction tx = session.beginTransaction();
	    Criteria cr = session.createCriteria(User.class);
	    cr.add(Restrictions.eq("userID", userID));
	    User user = (User) cr.uniqueResult();
	    
	    ArrayList<Scrumboard> scrumboards = new ArrayList<Scrumboard>();
	    for (Scrumboard scrumboard : user.getScrumboards()) {
	    	scrumboards.add(scrumboard);
	    }
	    tx.commit();
	    return scrumboards;

	}
	
	
}
//	
//	public static void update(String[] backlog, String[] todo, String[] inprogress, String[] done, int scrumboardID) {
//		
//	    Configuration config = new Configuration().configure().addAnnotatedClass(Scrumboard.class);
//	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
//	    SessionFactory sf = config.buildSessionFactory(reg);
//	    Session session = sf.openSession();
//
//	    Transaction tx = session.beginTransaction();
//	    Criteria cr = session.createCriteria(Scrumboard.class);
//	    cr.add(Restrictions.eq("scrumboardID", scrumboardID));
//	    Scrumboard scrumboard = (Scrumboard) cr.uniqueResult();
//	    
//		scrumboard.setBacklog(backlog);
//		scrumboard.setTodo(todo);
//		scrumboard.setInProgress(inprogress);
//		scrumboard.setDone(done);
//
//	    session.update(scrumboard);
//	    tx.commit();
//	}
//	
//	public static Scrumboard delete (int userID, int scrumboardID) {
//	    Configuration config = new Configuration().configure().addAnnotatedClass(Scrumboard.class);
//	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
//	    SessionFactory sf = config.buildSessionFactory(reg);
//	    Session session = sf.openSession();
//
//	    Transaction tx = session.beginTransaction();
//	    Criteria cr = session.createCriteria(Scrumboard.class);
//	    cr.add(Restrictions.eq("scrumboardID", scrumboardID));
//	    cr.add(Restrictions.eq("userID", userID));
//	    Scrumboard scrumboard = (Scrumboard) cr.uniqueResult();
//	
//	    session.delete(scrumboard);
//		tx.commit();
//		return scrumboard;
//	
//	}
//}
