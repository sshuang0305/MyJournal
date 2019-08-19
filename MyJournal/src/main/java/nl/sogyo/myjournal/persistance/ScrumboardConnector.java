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
import nl.sogyo.myjournal.domain.UserStory;

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
	
	
	public static void update(int storyID, String boardState) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(UserStory.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();

	    Transaction tx = session.beginTransaction();
	    Criteria cr = session.createCriteria(UserStory.class);
	    cr.add(Restrictions.eq("storyID", storyID));
	    UserStory userstory = (UserStory) cr.uniqueResult();
	    
	    userstory.setState(boardState);
	    session.update(userstory);
	    tx.commit();
	}
	
	public static void addMemberToScrumboard(int scrumboardID, int userID) {
		Configuration config = new Configuration().configure().addAnnotatedClass(Scrumboard.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();

	    Transaction tx = session.beginTransaction();
	    Criteria cr = session.createCriteria(Scrumboard.class);
	    cr.add(Restrictions.eq("scrumboardID", scrumboardID));
	    Scrumboard scrumboard = (Scrumboard) cr.uniqueResult();
	    
		config = new Configuration().configure().addAnnotatedClass(User.class);
	    reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    sf = config.buildSessionFactory(reg);
	    session = sf.openSession();

	    tx = session.beginTransaction();
	    cr = session.createCriteria(User.class);
	    cr.add(Restrictions.eq("userID", userID));
	    User user = (User) cr.uniqueResult();
	    user.addScrumboard(scrumboard);
	    session.merge(user);
	    tx.commit();
	}

	
	public static void delete (int scrumboardID) {
		Configuration config = new Configuration().configure().addAnnotatedClass(Scrumboard.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();

	    Transaction tx = session.beginTransaction();
	    Criteria cr = session.createCriteria(Scrumboard.class);
	    cr.add(Restrictions.eq("scrumboardID", scrumboardID));
	    Scrumboard scrumboard = (Scrumboard) cr.uniqueResult();
	    session.delete(scrumboard);	    

	    tx.commit();
	}
}
