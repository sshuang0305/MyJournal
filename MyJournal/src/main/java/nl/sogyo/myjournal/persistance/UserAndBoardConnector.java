package nl.sogyo.myjournal.persistance;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import nl.sogyo.myjournal.domain.Scrumboard;
import nl.sogyo.myjournal.domain.UserAndBoardLinker;

public class UserAndBoardConnector {

	public static void save(int userID, int scrumboardID) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(UserAndBoardLinker.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();

	    Transaction tx = session.beginTransaction();
	    
		UserAndBoardLinker newLink= new UserAndBoardLinker(userID, scrumboardID);
	    session.save(newLink);
	    tx.commit();
	}
	
	public static ArrayList<Scrumboard> getBoards(int userID) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(UserAndBoardLinker.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();

	    Transaction tx = session.beginTransaction();
	    
	    Criteria cr = session.createCriteria(UserAndBoardLinker.class);
	    cr.add(Restrictions.eq("userID", userID));
	    List<UserAndBoardLinker> linkResults= cr.list();
	    
	    ArrayList<Integer> scrumboardIDs = new ArrayList<Integer>();
	    for (UserAndBoardLinker result : linkResults) {
	    	scrumboardIDs.add(result.getScrumboardID());
	    }
	    
	    tx.commit();
	    
	    config = new Configuration().configure().addAnnotatedClass(Scrumboard.class);
	    reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    sf = config.buildSessionFactory(reg);
	    session = sf.openSession();
	    tx = session.beginTransaction();
	    
	    ArrayList<Scrumboard> scrumboards = new ArrayList<Scrumboard>();
	    
	    for (int scrumboardID : scrumboardIDs) {
	    	cr = session.createCriteria(Scrumboard.class);
		    cr.add(Restrictions.eq("scrumboardID", scrumboardID));
		    Scrumboard scrumboard = (Scrumboard) cr.uniqueResult();
		    scrumboards.add(scrumboard);    
	    }
	    tx.commit();
	    
	    return scrumboards;
	}
	
	public static void delete(int userID, int scrumboardID) {
		
		System.out.println("doe ik dit");

	    Configuration config = new Configuration().configure().addAnnotatedClass(UserAndBoardLinker.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();

	    Transaction tx = session.beginTransaction();
	    Criteria cr = session.createCriteria(UserAndBoardLinker.class);
	    cr.add(Restrictions.eq("scrumboardID", scrumboardID));
	    cr.add(Restrictions.eq("userID", userID));
	    UserAndBoardLinker link = (UserAndBoardLinker) cr.uniqueResult();
	    session.delete(link);
		tx.commit();

	}
	
	public static void delete(int scrumboardID) {

	    Configuration config = new Configuration().configure().addAnnotatedClass(UserAndBoardLinker.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();

	    Transaction tx = session.beginTransaction();
	    Criteria cr = session.createCriteria(UserAndBoardLinker.class);
	    cr.add(Restrictions.eq("scrumboardID", scrumboardID));

	    List<UserAndBoardLinker> links = cr.list();
	    for (UserAndBoardLinker link : links) {
	    	session.delete(link);
	    }
		tx.commit();

	}

}
